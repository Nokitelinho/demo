package com.ibsplc.neoicargo.mailmasters.component;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mailmasterdata.MailSubClassMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mailmasterdata.OfficeOfExchangeMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mailmasterdata.PostalAdministrationMessageVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.message.vo.MessageConfigConstants;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorUtils;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfigurationBuilder;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mailmasters.component.proxy.MsgBrokerMessageProxy;
import com.ibsplc.neoicargo.mailmasters.component.proxy.SharedAreaProxy;
import com.ibsplc.neoicargo.mailmasters.component.proxy.SharedCommodityProxy;
import com.ibsplc.neoicargo.mailmasters.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import com.ibsplc.neoicargo.mailmasters.exception.*;
import com.ibsplc.neoicargo.mailmasters.mapper.MailTrackingDefaultsMapper;
import com.ibsplc.neoicargo.mailmasters.vo.*;
import com.ibsplc.neoicargo.masters.area.airport.AirportBusinessException;
import com.ibsplc.neoicargo.masters.area.airport.AirportComponent;
import com.ibsplc.neoicargo.masters.area.airport.modal.AirportModal;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.neoicargo.masters.area.city.CityComponent;
import com.ibsplc.neoicargo.masters.area.city.modal.CityModal;
import com.ibsplc.neoicargo.masters.country.CountryComponent;
import com.ibsplc.neoicargo.masters.country.modal.CountryModal;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** 
 * @author a-1303
 */
@Component
@Slf4j
public class MailController {
	@Resource
	private MailController mailController;
	@Autowired
	private Quantities quantities;
	@Autowired
	private SharedAreaProxy sharedAreaProxy;
	@Autowired
	private SharedDefaultsProxy sharedDefaultsProxy;
	@Autowired
	private SharedCommodityProxy sharedCommodityProxy;
	@Autowired
	private LocalDate localDateUtil;
	@Autowired
	private ContextUtil contextUtil;
	@Autowired
	private CityComponent cityComponent;
	@Autowired
	private CountryComponent countryComponent;	
@Autowired
	private AirlineWebComponent airlineWebComponent;
@Autowired
private MailTrackingDefaultsMapper mailTrackingDefaultsMapper;
	@Autowired
	private AuditUtils auditUtils;

	@Autowired
	private MsgBrokerMessageProxy msgBrokerMessageProxy;
	private static final String CLASS = "MailController";
	private static final String HYPHEN = "-";
	private static final String CSGDOCNUM_GEN_KEY = "CSGDOCNUM_GEN_KEY";
	private static final String ID_SEP = "~";
	private static final String FINDEREXCEPTIO_STRING = "FINDER EXCEPTION IS THROWN";
	private static final String OFFLOADED_FOR_MLD_UPL = "Flight Departed-MLD";
	private static final String SPACE = " ";
	private static final String MODULENAME = "mail.masters";
	private static final String TRANSFER_MANIFEST_KEY = "TRF_MFT_KEY";
	private static final String TRFMFT_KEYTABLE = "MALTRFMFTKEY";
	private static final String CONTAINER_ASSIGNEDFORFLIGHT = "F";
	private static final String CITY_CACHE = "CITY";
	private static final String MODEOFTRANSPORT_ROAD = "3";
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	private static final String COUNTRY_ORIGIN = "COUNTRYORIGIN";
	private static final String COUNTRY_DESTINATION = "COUNTRYDESTINATION";
	private static final String COUNTRY_CACHE = "COUNTRY";
	private static final String EXCHANGE_CACHE = "EXCHANGE";
	private static final String ORIGINEXCHANGE = "OOE";
	private static final String DESTINATIONEXCHANGE = "DOE";
	private static final String SUBCLS_CACHE = "MailSubClass";
	private static final String ERROR_CACHE = "ERROR";
	private static final String CITY_ORIGIN = "CITYORIGIN";
	private static final String CITY_DESTINATION = "CITYDESTINATION";
	private static final String PAIR_ORIGIN = "CITYPAIRORIGIN";
	private static final String PAIR_DESTINATION = "CITYPAIRDESTINATION";
	private static final String CITYPAIR_CACHE = "CITYPAIRDESTINATION";
	private static final String PA_CACHE = "PACODE";
	private static final String MODULE = "MailController";
	private static final String MTK_IMP_FLT = "MTK_IMP_FLT";
	private static final String MTK_INB_ONLINEFLT_CLOSURE = "MTK_INB_ONLINEFLT_CLOSURE";
	private static final String DEFAULTCOMMODITYCODE_SYSPARAM = "mailtracking.defaults.booking.commodity";
	private static final String IMPORTMRA_REQUIRED = "mailtracking.defaults.importmailstomra";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String MAILSERVICELEVELS = "mail.operations.mailservicelevels";
	private static final String MAILCLASS = "mailtracking.defaults.mailclass";
	private static final String CONTRACTID_ALREADY_EXISTS = "mailtracking.defaults.ux.mailperformance.msg.err.contractidforodpairalreadyexists";
	private static final String CONTRACTID_ALREADY_EXISTS_FOR_SAME_DATE_SPAN = "mailtracking.defaults.ux.mailperformance.msg.err.contractidforsametimeperiodalreadyexists";
	public static final String LAT_VIOLATED_ERR = "mailtracking.defaults.err.latvalidation";
	public static final String LAT_VIOLATED_WAR = "mailtracking.defaults.war.latvalidation";
	private static final String MAIL_ROUTE_INDEX_ERROR = "mailtracking.operations.msg.err.routeindex";
	private static final String LST_DGT_OF_YEAR_FMT = "y";
	public static final String COUNTRY = "logonattributes.country";
	public static final String MAILAWB_STATUS_DETACH = "mail.operations.malawb.status.det";
	private static final String FORMULA_ALREADY_EXISTS_WITH_SAME_PERCENTAGE_FOR_SAME_PRODUCT = "mailtracking.defaults.ux.mailperformance.msg.err.formulaalreadyexistswithsamepercentforsameproduct";
	private static final String BOTH_CONFIGURATION_NOT_ALLOWED = "mailtracking.defaults.ux.mailperformance.msg.err.bothconfigurationnotallowed";
	private static final String SERVICE_RESPONSIVE = "Y";
	private static final String NON_SERVICE_RESPONSIVE = "N";
	private static final String BOTH = "B";
	private static final String DUPLICATE_RECORD = "mailtracking.defaults.ux.mailperformance.msg.err.duplicaterecord";
	private static final String INCENTIVEPRODUCTPARAMETER = "PRD";
	private static final String ERROR_ALREADY_READY_FOR_DELIVED_MARKED = "mailtracking.defaults.mailarrival.readyfordeliverymarkedalready";
	private static final String ERROR_ALREADY_READY_FOR_DELIVED_RESDIT_MARKED = "mailtracking.defaults.mailarrival.readyfordeliveryresdittrigerred";
	public static final String INVALID_DELIVERY_AIRPORT = "mailtracking.defaults.InvalidDeliveryAirportException";
	private Map<String, String> exchangeOfficeMap;
	public static final String INVALID_READYFOR_DELIVERY_AIRPORT = "mailtracking.defaults.InvalidReadyForDeliveryAirportException";
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
	private static final String AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED = "operations.flthandling.aircraftcompatibilityrequireduldtypes";
	private static final String AUTOARRIVALFUNCTIONPOINTS = "mail.operations.autoarrivalfunctionpoints";
	private static final String HAS_PRIVILEGE_FOR_VIEW_ALL_MAIL_TRUCK = "mail.operations.viewalltruckflightsforoperatios";
	private static final String TRANSER_STATUS_REJECT = "TRFREJ";
	private static final String MAIL_CATEGORY = "mailtracking.defaults.mailcategory";
	private static final String LIST_TRANSFER_MANIFEST_SCREENID = "MTK027";
	private static final String DEST_FOR_CDT_MISSING_DOM_MAL = "mail.operation.destinationforcarditmissingdomesticmailbag";
	private static final String PA_LIST_FOR_PA_BUILT_RATING = "mailtracking.mra.PAlisttoapplycontainerrate";
	private static final String BLANK = "";
	private static final String CONSIGNMENTCAPTURE = "C";
	private static final String IMPORTTRIGGER_DELIVERY = "D";
	private static final String PROXYEXCEPTION = "ProxyException";
	private static final String MAIL_REASSIGN_FROM_CLOSED_FLIGHT = "mail.operations.offloadonreassignment";
	private static final String TRANSFER_END_FROM_OPS = "EXPFLTFIN_TRAMAL";
	private static final String MAIL_CONTROLLER_BEAN = "mAilcontroller";
	public static final String MAIL_OPERATIONS_TRANSFER_TRANSACTION = "mail.operation.transferoutinonetransaction";
	private static final String MAIL_OPS_TRAEND = "TRAEND";
	private static final String SECURITY_SCREENING_MESSGE_TYPE = "SECANDSCREENING";
	private static final String PUBLISH = "PUBLISH";
	private static final String MAIL_OPERATION_SERVICES = "mailOperationsFlowServices";
	private static final String REGULATED_AGENTACCEPTING_MAIL = "mail.operations.regulatedagentacceptingmail";
	private static final String THIRDPARTYRA_ISSUE_MAIL = "mail.operations.thirdpartyraissuemail";
	private static final String RA_ACCEPTANCE_VALIDATION_OVERRIDE = "mail.operations.raacceptancevalidationoverride";
	private static final String FLT_CLS = "FLTCLS";
	private static final String MAIL_MASTER_DATA_TYPE_MALBAGINF = "MALBAGINF";
	private static final String MAIL_MASTER_DATA_TYPE_PACOD = "PACOD";
	private static final String MAIL_MASTER_DATA_TYPE_SUBCLS = "SUBCLS";
	private static final String MAIL_MASTER_DATA_TYPE_EXCHANGE_OFFICE = "EXGOFC";
	private static final String MACC = "MACC";
	private static final String NO_VALUE = "NO_VALUE";
	private static final String DSTARPCNTGRP = "DSTARPCNTGRP";
	private static final String NO_GROUP = "NO_GRP";
	private static final String DUPLICATE_MLD_CONFIGURATION = "mailtracking.defaults.duplictae.mld.configurations";


	/** 
	* Utilty for finding syspar Mar 23, 2007, A-1739
	* @param syspar
	* @return
	* @throws SystemException
	*/
	public String findSystemParameterValue(String syspar) {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		HashMap<String, String> systemParameterMap = sharedDefaultsProxy.findSystemParameterByCodes(systemParameters);
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/** 
	* findCityForOfficeOfExchange
	* @param companyCode
	* @param officeOfExchanges
	* @return MAP<CITY   ,   OFFICE_OF_EXCHANGE>
	* @throws SystemException
	*/
	public HashMap<String, String> findCityForOfficeOfExchange(String companyCode,
			Collection<String> officeOfExchanges) {
		log.debug(CLASS + " : " + "findCityForOfficeOfExchange" + " Entering");

		return OfficeOfExchange.findCityForOfficeOfExchange(companyCode, officeOfExchanges);
	}

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*/
	public OfficeOfExchangeVO validateOfficeOfExchange(String companyCode, String officeOfExchange) {
		Page<OfficeOfExchangeVO> officeOfExchangePage = null;
		OfficeOfExchangeVO officeOfExchangeVO = null;
		officeOfExchangePage = mailController.findOfficeOfExchange(companyCode, officeOfExchange, 1);
		if (officeOfExchangePage != null && !officeOfExchangePage.isEmpty()
				&& officeOfExchangePage.iterator().next().isActive()) {
			officeOfExchangeVO = officeOfExchangePage.iterator().next();
		}
		return officeOfExchangeVO;
	}

	/**
	* @author A-1936 Added By Karthick v as the part of the NCA Mail TrackingCR -(Auto Create UBrs For the Booking) Validating commodity codes in each Shipment DetailVO and calculating the Volume by using the Density factor of the corrosponding commodity if it is not specified by the user.. Since this is an despacth to the Booking Server Straight away the Booking Client Code is skipped where these calculations afre actually done so its better to do the Volume Calculations in MailTracking System even before the Despacth..
	*/
	public CommodityValidationVO validateCommodity(String companyCode, String commodityCode, String poaCode) {
		log.debug("ValidateBookingCommand" + " : " + "doCommodityCodeValidation" + " Entering");
		CommodityValidationVO commodityValidationVo = null;
		Collection<String> commodityColl = new ArrayList<String>();
		Map<String, CommodityValidationVO> commodityMap = null;
		String densityFactor = null;
		if (poaCode != null && !poaCode.isEmpty()) {
			densityFactor = findDensityfactorForPA(companyCode, poaCode);
		}
		if (densityFactor != null && !densityFactor.isEmpty()) {
			commodityValidationVo = new CommodityValidationVO();
			commodityValidationVo.setCompanyCode(companyCode);
			commodityValidationVo.setDensityFactor(Double.parseDouble(densityFactor));
			return commodityValidationVo;
		}
		commodityColl.add(commodityCode);
		try {
			commodityMap = sharedCommodityProxy.validateCommodityCodes(companyCode, commodityColl);
		} catch (SystemException ex) {
			throw new SystemException(ex.getMessage());
		}
		if (commodityMap != null && commodityMap.size() > 0) {
			commodityValidationVo = commodityMap.get(commodityCode);
		}
		return commodityValidationVo;
	}

	/** 
	* @param companyCode
	* @param subclass
	* @return
	* @throws SystemException
	* @author A-3227 Reno K AbrahamThis method is used to validate the MailSubClass
	*/
	public boolean validateMailSubClass(String companyCode, String subclass) {
		log.debug(CLASS + " : " + "validateMailSubClass" + " Entering");
		return OfficeOfExchange.validateMailSubClass(companyCode, subclass);
	}

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*/
	public String findPAForOfficeOfExchange(String companyCode, String officeOfExchange) {
		log.debug(CLASS + " : " + "findPAForOfficeOfExchange" + " Entering");
		String paCode = null;
		Page<OfficeOfExchangeVO> officeOfExchangePage = null;
		officeOfExchangePage = mailController.findOfficeOfExchange(companyCode, officeOfExchange, 1);
		if (officeOfExchangePage != null && !officeOfExchangePage.isEmpty()
				&& officeOfExchangePage.iterator().next().getPoaCode() != null
				&& officeOfExchangePage.iterator().next().isActive()) {
			paCode = officeOfExchangePage.iterator().next().getPoaCode();
		}
		return paCode;
	}

	/** 
	* @param companyCode
	* @param subclassCode
	* @return Collection<MailSubClassVO>
	* @throws SystemException
	* @author a-2037 This method is used to find all the mail subclass codes
	*/
	public Collection<MailSubClassVO> findMailSubClassCodes(String companyCode, String subclassCode) {
		log.debug(CLASS + " : " + "findMailSubClassCodes" + " Entering");

		return MailSubClass.findMailSubClassCodes(companyCode, subclassCode);
	}

	/** 
	* @param companyCode
	* @param officeOfExchanges
	* @return
	* @author A-3227This method returns Collection<ArrayList<String>> in which, the inner collection contains the values in the order : 1.OFFICE OF EXCHANGE 2.CITY 3.NEAREST AIRPORT
	*/
	public Collection<ArrayList<String>> findCityAndAirportForOE(String companyCode,
			Collection<String> officeOfExchanges) throws SystemException {
		log.debug(CLASS + " : " + "findCityForOfficeOfExchange" + " Entering");
		HashMap<String, String> cityForOE = null;
		Map<String, CityVO> cityAirtportMap = null;
		Collection<String> cityCodes = new ArrayList<String>();
		ArrayList<String> groupedCodes = new ArrayList<String>();
		Collection<ArrayList<String>> groupedOECityArpCodes = new ArrayList<ArrayList<String>>();
		if (officeOfExchanges != null && officeOfExchanges.size() > 0) {
			cityForOE = findCityForOfficeOfExchange(companyCode, officeOfExchanges);
			if (cityForOE != null && cityForOE.size() > 0) {
				for (String oe : officeOfExchanges) {
					cityCodes.add(cityForOE.get(oe));
				}
				if (cityCodes != null && cityCodes.size() > 0) {
					cityAirtportMap = findCityAirportMap(companyCode, cityCodes);
				}
			}
		}
		if (cityForOE != null && cityForOE.size() > 0 && cityAirtportMap != null && cityAirtportMap.size() > 0) {
			for (String oe : officeOfExchanges) {
				String city = cityForOE.get(oe);
				CityVO cityVO = cityAirtportMap.get(city);
				groupedCodes = new ArrayList<String>();
				groupedCodes.add(oe);
				groupedCodes.add(city);
				groupedCodes.add(cityVO.getNearestAirport());
				groupedOECityArpCodes.add(groupedCodes);
			}
		}
		return groupedOECityArpCodes;
	}

	/** 
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	private Map<String, CityVO> findCityAirportMap(String companyCode, Collection<String> cities) throws SystemException {
		List<String> cityCodes = new ArrayList<String>();
		for (String cityCode : cities) {
			if (!cityCodes.contains(cityCode)) {
				cityCodes.add(cityCode);
			}
		}
		log.debug("" + "CityCodes->" + " " + cityCodes);

		List<CityModal> cityModals=	 validateCityCode(cityCodes);
		if(cityModals.isEmpty()){
			throw new SystemException("shared.area.invalidcity");
		}
		List<CityVO> cityVOs=mailTrackingDefaultsMapper.cityModelsToCityVO(cityModals);

		Map<String, CityVO> validCityCodes = new HashMap<String, CityVO>();
		String err[]=new String[cityCodes.size()-cityVOs.size()];
		int index = 0;
		if (cityVOs.size() == cityCodes.size()) {
			String key = null;
			for (CityVO cityVO : cityVOs) {
				key = cityVO.getCityCode();
				validCityCodes.put(key, cityVO);
			}
		}
		else {
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			for (String code : cityCodes) {
				boolean isValid = false;
				for (CityVO cityVO : cityVOs) {
					if (code.equals(cityVO.getCityCode())) {
						isValid = true;
						break;
					}
				}
				if (!isValid) {
					err[index] = code;
					index++;
				}
			}
		}
		log.debug("" + "CityMap-->" + " " + validCityCodes);
		return validCityCodes;
	}

	/** 
	* @param companyCode
	* @param ownCarrierCode
	* @param airportCode
	* @return Collection<PartnerCarrierVO>
	* @throws SystemException
	* @author a-1876 This method is used to list the PartnerCarriers..
	*/
	public Collection<PartnerCarrierVO> findAllPartnerCarriers(String companyCode, String ownCarrierCode,
			String airportCode) {
		log.debug(CLASS + " : " + "findAllPartnerCarriers" + " Entering");
		return PartnerCarrier.findAllPartnerCarriers(companyCode, ownCarrierCode, airportCode);
	}

	public Collection<CoTerminusVO> findAllCoTerminusAirports(CoTerminusFilterVO filterVO) {
		log.debug(CLASS + " : " + "findAllCoTerminusAirports" + " Entering");
		return CoterminusAirport.findAllCoTerminusAirports(filterVO);
	}

	public Page<MailServiceStandardVO> listServiceStandardDetails(
			MailServiceStandardFilterVO mailServiceStandardFilterVO, int pageNumber) {
		log.debug(CLASS + " : " + "listServiceStandardDetails" + " Entering");
		return MailServiceStandard.listServiceStandardDetails(mailServiceStandardFilterVO, pageNumber);
	}

	/** 
	* Method		:	MailController.findAllCoTerminusAirports Added by 	:	A-6991 on 17-Jul-2018 Used for 	:   ICRD-212544 Parameters	:	@param filterVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	Collection<CoTerminusVO>
	*/
	public Collection<MailRdtMasterVO> findRdtMasterDetails(RdtMasterFilterVO filterVO) {
		log.debug(CLASS + " : " + "findAllCoTerminusAirports" + " Entering");
		return MailRdtMaster.findRdtMasterDetails(filterVO);
	}

	/** 
	* @param companyCode
	* @param countryCode
	* @return Collection<PostalAdministrationVO>
	* @throws SystemException
	* @author A-2037 This method is used to find Local PAs
	*/
	public Collection<PostalAdministrationVO> findLocalPAs(String companyCode, String countryCode) {
		log.debug(CLASS + " : " + "findLocalPAs" + " Entering");
		return PostalAdministration.findLocalPAs(companyCode, countryCode);
	}

	private static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULENAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	* @param pageNumber
	* @return
	* @throws SystemException
	* @author A-2037 Method for OfficeOfExchangeLOV containing code anddescription
	*/
	public Page<OfficeOfExchangeVO> findOfficeOfExchangeLov(OfficeOfExchangeVO officeofExchangeVO, int pageNumber,
			int defaultSize) {
		log.debug(CLASS + " : " + "findOfficeOfExchangeLov" + " Entering");
		return OfficeOfExchange.findOfficeOfExchangeLov(officeofExchangeVO, pageNumber, defaultSize);
	}

	/** 
	* @param companyCode
	* @param code
	* @param description
	* @param pageNumber
	* @return
	* @throws SystemException
	* @author A-2037 Method for MailSubClassLOV containing code and description
	*/
	public Page<MailSubClassVO> findMailSubClassCodeLov(String companyCode, String code, String description,
			int pageNumber, int defaultSize) {
		log.debug(CLASS + " : " + "findMailSubClassCodeLov" + " Entering");
		return MailSubClass.findMailSubClassCodeLov(companyCode, code, description, pageNumber, defaultSize);
	}

	/** 
	* @param companyCode
	* @param paCode
	* @return PostalAdministrationVO
	* @throws SystemException
	* @author A-2037 This method is used to find Postal Administration CodeDetails
	*/
	@Cacheable(
			value = {"postalAdministrationCache"},
			key = "#paCode"
	)
	public PostalAdministrationVO findPACode(String companyCode, String paCode) {
		log.debug(CLASS + " : " + "findPACode" + " Entering");
		return  PostalAdministration.findPACode(companyCode, paCode);
	}

	/** 
	* overriding compare method and passing objects by reference Added for ICRD-197379
	* @author a-7540
	* @return
	*/
	//TODO: Neo to correct
//	class DestinationExchangeOfficeComparator implements Comparator {
//	}

	/** 
	* @param postalAdministrationVO
	* @throws SystemException
	* @throws SharedProxyException
	*/
	@CacheEvict(value = {"postalAdministrationCache","postalAdministrationPartyIdentifierCache"},key="#postalAdministrationVO.paCode")
	public void savePACode(PostalAdministrationVO postalAdministrationVO) throws SystemException {
		PostalAdministration postalAdministration = null;
		String STATUS_NEW = "NEW";
		if (postalAdministrationVO.getOperationFlag() != null
				&& !PostalAdministrationVO.OPERATION_FLAG_DELETE.equals(postalAdministrationVO.getOperationFlag())) {
			String countryCode = postalAdministrationVO.getCountryCode();
			Collection<String> countryCodes = new ArrayList<String>();
			countryCodes.add(countryCode);
		}
		if (PostalAdministrationVO.OPERATION_FLAG_INSERT.equals(postalAdministrationVO.getOperationFlag())) {
			log.info("!!!!   INSERT   !!!!!!!!!!!!!!!!!!!");
			postalAdministrationVO.setStatus(STATUS_NEW);
			new PostalAdministration(postalAdministrationVO);
		}
		if (PostalAdministrationVO.OPERATION_FLAG_UPDATE.equals(postalAdministrationVO.getOperationFlag())) {
			log.info("!!!!   UPDATE   !!!!!!!!!!!!!!!!!!!");
			try {
				postalAdministration = PostalAdministration.find(postalAdministrationVO.getCompanyCode(),
						postalAdministrationVO.getPaCode());
				postalAdministration.update(postalAdministrationVO);
			} catch (FinderException ex) {
				throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
			}
		}
		if (PostalAdministrationVO.OPERATION_FLAG_DELETE.equals(postalAdministrationVO.getOperationFlag())) {
			log.info("!!!!   DELETE   !!!!!!!!!!!!!!!!!!!");
			try {
				postalAdministration = PostalAdministration.find(postalAdministrationVO.getCompanyCode(),
						postalAdministrationVO.getPaCode());
				postalAdministration.setLastUpdatedTime(Timestamp.valueOf(postalAdministrationVO.getLastUpdateTime().toLocalDateTime()));
			} catch (FinderException ex) {
				throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
			}
			try {
				postalAdministration.remove();
			} catch (RemoveException ex) {
				throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
			}
		}
	}

	/** 
	* This method is used to save office of Exchange Code A-1739
	* @param officeOfExchangeVOs
	* @throws SystemException
	* @throws RemoveException
	* @throws OfficeOfExchangeException
	*/
	@CacheEvict(value = {"mailOfficeOfExchangeCache","officeOfExchangesForAirportCache",
	"officeOfExchangesForPaCodeCache","airportForOfficeOfExchangeCache"}, allEntries = true)
	public void saveOfficeOfExchange(Collection<OfficeOfExchangeVO> officeOfExchangeVOs)
			throws RemoveException, OfficeOfExchangeException {
		OfficeOfExchange officeOfExchange = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		StringBuilder code = new StringBuilder();
		for (OfficeOfExchangeVO officeOfExchangeVO : officeOfExchangeVOs) {
			List<String> cityCodes=new ArrayList<>();
			cityCodes.add(officeOfExchangeVO.getCityCode());
			if (OfficeOfExchangeVO.OPERATION_FLAG_INSERT.equals(officeOfExchangeVO.getOperationFlag())) {
				log.info("!!!!   INSERT   !!!!!!!!!!!!!!!!!!!");
				try {
					officeOfExchange = OfficeOfExchange.find(officeOfExchangeVO.getCompanyCode(),
							officeOfExchangeVO.getCode());
					if (officeOfExchange != null) {
						if (code.length() == 0) {
							code = new StringBuilder(String.valueOf(officeOfExchangeVO.getCode()));
						} else {
							code = code.append(",").append(officeOfExchangeVO.getCode());
						}
					}
				} catch (FinderException finderException) {
					validateCountryCode(officeOfExchangeVO.getCountryCode());
					validateCityCode(cityCodes);
					validatePostalAdmin(officeOfExchangeVO.getCompanyCode(),officeOfExchangeVO.getPoaCode());
					log.info("!!!!   INSERT  inside catch..CREATE !!!!!!!");
					new OfficeOfExchange(officeOfExchangeVO);
				}
			} else if (OfficeOfExchangeVO.OPERATION_FLAG_UPDATE.equals(officeOfExchangeVO.getOperationFlag())) {
				log.info("!!!!   UPDATE   !!!!!!!!!!!!!!!!!!!");
				try {
					officeOfExchange = OfficeOfExchange.find(officeOfExchangeVO.getCompanyCode(),
							officeOfExchangeVO.getCode());
				} catch (FinderException ex) {
					throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
				}
				validateCountryCode(officeOfExchangeVO.getCountryCode());
			List<CityModal> cityModals = validateCityCode(cityCodes);
			if(cityModals.isEmpty()){
				throw new OfficeOfExchangeException("shared.area.invalidcity");
			}
				validatePostalAdmin(officeOfExchangeVO.getCompanyCode(),officeOfExchangeVO.getPoaCode());
				officeOfExchange.update(officeOfExchangeVO);
			} else if (OfficeOfExchangeVO.OPERATION_FLAG_DELETE.equals(officeOfExchangeVO.getOperationFlag())) {
				try {
					officeOfExchange = OfficeOfExchange.find(officeOfExchangeVO.getCompanyCode(),
							officeOfExchangeVO.getCode());
					officeOfExchange.setLastUpdatedTime(
							Timestamp.valueOf(officeOfExchangeVO.getLastUpdateTime().toLocalDateTime()));

				} catch (FinderException ex) {
					throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
				}
				officeOfExchange.remove();
			}
		}
		if (code.length() > 0) {
			log.info("" + "!!!!!!!!!!ERRORDATA!!!!!!!!!!!!!!" + " " + code.toString());
			throw new OfficeOfExchangeException(OfficeOfExchangeException.OFFICEOFEXCHANGE_ALREADY_EXISTS,
					new String[] { code.toString() });
		}
	}
	/**
	 * TODO Purpose Nov 1, 2006,
	 * @throws SystemException
	 * @throws OfficeOfExchangeException
	 */
	/**
	 * This method is used to validateCountryCode A-10552
	 * @param countyCode
	 * @throws OfficeOfExchangeException
	 */
	public void validateCountryCode(String countyCode) throws OfficeOfExchangeException {
		log.debug("countyCode" + " : " + "validateCountryCode" + " Entering");
		String countryCd = countyCode;
		CountryModal countryModal=null;
			countryModal = countryComponent.findCountry(countryCd);
		if(Objects.isNull(countryModal)) {
			throw new OfficeOfExchangeException(OfficeOfExchangeException.INVALID_COUNTRYCODE,
					new Object[] { countryCd });
		}
	}
	/**
	 * This method is used to validateCityCode A-10552
	 * @param cityCode
	 * @throws OfficeOfExchangeException
	 */
	private List<CityModal>  validateCityCode(List<String> cityCode)  {

		return cityComponent.findCities(cityCode);

	}
	/**
	 * This method is used to validatePostalAdmin A-10552
	 * @param companyCode
	 * @param paCode
	 * @throws OfficeOfExchangeException
	 */
	private  void validatePostalAdmin(String companyCode,String paCode) throws OfficeOfExchangeException {
		if (new MailController().findPACode(companyCode,paCode ) == null) {
			throw new OfficeOfExchangeException(OfficeOfExchangeException.INVALID_POSTAL_ADMIN,
					new Object[] {paCode });
		}
		log.debug("paCode" + " : " + "validatePostalAdmin" + " Exiting");
	}
	/** 
	* This method is used to save Mail sub class codes A-2037
	* @param mailSubClassVOs
	* @throws SystemException
	* @throws RemoveException
	* @throws MailSubClassException
	*/
	public void saveMailSubClassCodes(Collection<MailSubClassVO> mailSubClassVOs)
			throws RemoveException, MailSubClassException {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		StringBuilder code = new StringBuilder();
		MailSubClass mailSubClass = null;
		for (MailSubClassVO mailSubClassVO : mailSubClassVOs) {
			if (MailSubClassVO.OPERATION_FLAG_INSERT.equals(mailSubClassVO.getOperationFlag())) {
				log.info("!!!!   INSERT   !!!!!!!!!!!!!!!!!!!");
				try {
					mailSubClass = MailSubClass.find(mailSubClassVO.getCompanyCode(), mailSubClassVO.getCode());
					if (mailSubClass != null) {
						if (code.length() == 0) {
							code = new StringBuilder(String.valueOf(mailSubClassVO.getCode()));
						} else {
							code = code.append(",").append(mailSubClassVO.getCode());
						}
					}
				} catch (FinderException finderexception) {
					log.info("!!!!   INSERT  inside catch..CREATE !!!!!!!");
					new MailSubClass(mailSubClassVO);
				}
			} else if (MailSubClassVO.OPERATION_FLAG_UPDATE.equals(mailSubClassVO.getOperationFlag())) {
				log.info("!!!!   UPDATE   !!!!!!!!!!!!!!!!!!!");
				try {
					mailSubClass = MailSubClass.find(mailSubClassVO.getCompanyCode(), mailSubClassVO.getCode());
				} catch (FinderException ex) {
					throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
				}
				mailSubClass.update(mailSubClassVO);
				log.info("!!!!   AFTER UPDATE   !!!!!!!!!!!!!!!!!!!");
			} else if (MailSubClassVO.OPERATION_FLAG_DELETE.equals(mailSubClassVO.getOperationFlag())) {
				log.info("!!!!   DELETE   !!!!!!!!!!!!!!!!!!!");
				try {
					mailSubClass = MailSubClass.find(mailSubClassVO.getCompanyCode(), mailSubClassVO.getCode());
					mailSubClass.setLastUpdatedTime(
							Timestamp.valueOf(mailSubClassVO.getLastUpdateTime().toLocalDateTime()));
				} catch (FinderException ex) {
					throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
				}
				mailSubClass.remove();
				log.info("!!!!   AFTER DELETE   !!!!!!!!!!!!!!!!!!!");
			}
		}
		if (code.length() > 0) {
			ErrorVO error = ErrorUtils.getError(MailSubClassException.MAILSUBCLASS_ALREADY_EXISTS, code.toString());
			log.info("" + "!!!!!!!!!!ERRORDATA!!!!!!!!!!!!!!" + " " + code.toString());
			errors.add(error);
		}
		if (errors.size() > 0) {
			MailSubClassException exception = new MailSubClassException();
			exception.addErrors(errors);
			throw exception;
		}
	}

	/** 
	* This method is used to find all the mail subclass codes A-2037
	* @param companyCode
	* @param officeOfExchange
	* @param pageNumber
	* @return Page of officeExchangeVOs
	* @throws SystemException
	*/
	@Cacheable(
			value = {"mailOfficeOfExchangeCache"},
			key = "{#officeOfExchange}",
			condition="#officeOfExchange != ''"
	)
	public Page<OfficeOfExchangeVO> findOfficeOfExchange(String companyCode, String officeOfExchange, int pageNumber) {
		log.debug(CLASS + " : " + "findOfficeOfExchange" + " Entering");
		return OfficeOfExchange.findOfficeOfExchange(companyCode, officeOfExchange,pageNumber);
	}

	/** 
	* @param companyCode
	* @param mailboxCode
	* @param mailboxDesc
	* @param pageNumber
	* @return
	* @throws SystemException
	* @author A-5931 Method for MailboxId containing mailboxCode and mailboxDesc
	*/
	public Page<MailBoxIdLovVO> findMailBoxIdLov(String companyCode, String mailboxCode, String mailboxDesc,
			int pageNumber, int defaultSize) {
		log.debug(CLASS + " : " + "findMailBoxIdLov" + " Entering");
		try {
			return constructDAO().findMailBoxIdLov(companyCode, mailboxCode, mailboxDesc, pageNumber, defaultSize);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @param companyCode
	* @param paCode
	* @param paName
	* @param pageNumber
	* @return
	* @throws SystemException
	* @author A-2037 Method for PALov containing PACode and PADescription
	*/
	public Page<PostalAdministrationVO> findPALov(String companyCode, String paCode, String paName, int pageNumber,
			int defaultSize) {
		log.debug(CLASS + " : " + "findPALov" + " Entering");
		return PostalAdministration.findPALov(companyCode, paCode, paName, pageNumber, defaultSize);
	}

	/** 
	* Added for icrd-95515
	* @param companyCode
	* @param airportCode
	* @return
	* @throws SystemException
	*/
	@Cacheable( value = {"officeOfExchangesForAirportCache"}, key = "#airportCode")
	public Collection<String> findOfficeOfExchangesForAirport(String companyCode, String airportCode) {
		log.debug("MailTrackingDefaultsServicesEJB" + " : " + "findCityAndAirportForOE" + " Entering");

		return OfficeOfExchange.findOfficeOfExchangesForAirport(companyCode, airportCode);
	}

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*/
	public PostalAdministrationVO findPADetails(String companyCode, String officeOfExchange) {
		log.debug(CLASS + " : " + "findPADetails" + " Entering");
		PostalAdministrationVO postalAdministrationVO = PostalAdministration.findPADetails(companyCode,
				officeOfExchange);
		log.debug(CLASS + " : " + "findPADetails" + " Exiting");
		return postalAdministrationVO;
	}

	/** 
	* @param postalAdministrationDetailsVO
	* @throws SystemException
	* @throws RemoteException	 
	* @author A-3251
	*/
	public PostalAdministrationDetailsVO validatePoaDetails(
			PostalAdministrationDetailsVO postalAdministrationDetailsVO) {
		return PostalAdministrationDetails.validatePoaDetails(postalAdministrationDetailsVO);
	}

	/** 
	* Method		:	MailController.findAllPACodes Added by 	:	A-4809 on 08-Jan-2014 Used for 	:	ICRD-42160 Parameters	:	@param generateInvoiceFilterVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	Collection<PostalAdministrationVO>
	*/
	public Collection<PostalAdministrationVO> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO) {
		log.debug(MODULE + " : " + "findAllPACodes" + " Entering");
		log.debug(MODULE + " : " + "findAllPACodes" + " Exiting");
		return PostalAdministration.findAllPACodes(generateInvoiceFilterVO);
	}

	/** 
	* This method is used to save the PartnerCarriers A-1936
	* @param partnerCarrierVOs
	* @throws SystemException
	* @throws RemoveException
	* @throws InvalidPartnerException
	*/
	public void savePartnerCarriers(Collection<PartnerCarrierVO> partnerCarrierVOs)
			throws RemoveException, InvalidPartnerException {
		log.debug(CLASS + " : " + "savePartnerCarriers" + " Entering");
		PartnerCarrier partnerCarrier = null;
		PartnerCarrierPK partnerCarrierPk = null;
		StringBuilder errors = null;
		StringBuilder duplicatePartners = null;
		if (partnerCarrierVOs != null && partnerCarrierVOs.size() > 0) {
			for (PartnerCarrierVO partnerCarrierVO : partnerCarrierVOs) {
				if (PartnerCarrierVO.OPERATION_FLAG_INSERT.equals(partnerCarrierVO.getOperationFlag())) {
					boolean isValid = validatePartnerCarrier(partnerCarrierVO);
					if (isValid) {
						partnerCarrierPk = constructPartnerCarrierPK(partnerCarrierVO);
						try {
							partnerCarrier = PartnerCarrier.find(partnerCarrierPk);
							if (partnerCarrier != null) {
								if (duplicatePartners == null) {
									duplicatePartners = new StringBuilder();
								}
								duplicatePartners.append(partnerCarrierVO.getPartnerCarrierCode()).append(",");
							}
						} catch (FinderException ex) {
							new PartnerCarrier(partnerCarrierVO);
						}
					} else {
						if (errors == null) {
							errors = new StringBuilder();
						}
						errors.append(partnerCarrierVO.getPartnerCarrierCode()).append(",");
					}
				} else if (PartnerCarrierVO.OPERATION_FLAG_UPDATE.equals(partnerCarrierVO.getOperationFlag())) {
					partnerCarrierPk = constructPartnerCarrierPK(partnerCarrierVO);
					try {
						partnerCarrier = PartnerCarrier.find(partnerCarrierPk);
					} catch (FinderException ex) {
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
					partnerCarrier.update(partnerCarrierVO);
				} else if (PartnerCarrierVO.OPERATION_FLAG_DELETE.equals(partnerCarrierVO.getOperationFlag())) {
					partnerCarrierPk = constructPartnerCarrierPK(partnerCarrierVO);
					try {
						partnerCarrier = PartnerCarrier.find(partnerCarrierPk);
					} catch (FinderException ex) {
						partnerCarrier.setLastUpdatedTime(Timestamp.valueOf(partnerCarrierVO.getLastUpdateTime().toLocalDateTime()));
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
					partnerCarrier.remove();
				}
			}
			if (duplicatePartners != null && duplicatePartners.length() > 0) {
				log.debug("" + "The Invalid PartnerCarrierCodes are" + " " + duplicatePartners);
				throw new InvalidPartnerException(InvalidPartnerException.DUPLICATE_PARTNER,
						new Object[] { duplicatePartners.deleteCharAt(duplicatePartners.length() - 1).toString() });
			}
			if (errors != null && errors.length() > 0) {
				String error = errors.deleteCharAt(errors.length() - 1).toString();
				log.debug("" + "The Invalid PartnerCarrierCodes are" + " " + error);
				throw new InvalidPartnerException(InvalidPartnerException.INVALID_PARTNER, new Object[] { error });
			}
		}
	}

	/** 
	* @param partnerCarrierVO
	* @author A-1936 This method is used to construct the PartnerCarrierPK
	*/
	private PartnerCarrierPK constructPartnerCarrierPK(PartnerCarrierVO partnerCarrierVO) {
		PartnerCarrierPK partnerCarrierPk = new PartnerCarrierPK();
		partnerCarrierPk.setCompanyCode(partnerCarrierVO.getCompanyCode());
		partnerCarrierPk.setAirportCode(partnerCarrierVO.getAirportCode());
		partnerCarrierPk.setOwnCarrierCode(partnerCarrierVO.getOwnCarrierCode());
		partnerCarrierPk.setPartnerCarrierCode(partnerCarrierVO.getPartnerCarrierCode());
		return partnerCarrierPk;
	}

	/** 
	* @param partnerCarrierVO
	* @throws SystemException
	* @author A-1936 This method is used to validate whether thePartnersCarrierCode is Valid.If Valid Set the Airline Identifier in the partnerCarrierVO
	*/
	private boolean validatePartnerCarrier(PartnerCarrierVO partnerCarrierVO) {
		boolean isValid = false;
		List<String> carrierCodes = new ArrayList<String>();
		carrierCodes.add(partnerCarrierVO.getPartnerCarrierCode());
		List<AirlineModel> airlineModel= new ArrayList<AirlineModel>();
			try {
				airlineModel = validateAlphaCode(carrierCodes);
			} catch (BusinessException e) {
				isValid = false;
				log.debug("" + "BusinessException are" + " " + e);
			}
		if (airlineModel.size() >0) {
			partnerCarrierVO.setPartnerCarrierId(String.valueOf(airlineModel.get(0).getAirlineIdentifier()));
			isValid = true;
		}
		return isValid;
	}

	public List<AirlineModel> validateAlphaCode(List<String> carrierCodes) throws BusinessException{
		List<AirlineModel> airlineModel=new ArrayList<>();
		airlineModel = airlineWebComponent.validateAlphaCode(carrierCodes);
		return airlineModel;
	}
	public boolean validateCoterminusairports(String actualAirport, String eventAirport, String eventCode,
			String paCode, ZonedDateTime dspDate) {
		String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
		log.debug("" + "isCoterminusConfigured? : " + " " + isCoterminusConfigured);
		boolean validateCoterminusairports = false;
		if (MailConstantsVO.FLAG_YES.equals(isCoterminusConfigured) && actualAirport != null && eventAirport != null
				&& eventCode != null && paCode != null) {
			validateCoterminusairports = new CoterminusAirport().validateCoterminusairports(actualAirport, eventAirport,
					eventCode, paCode, dspDate);
		}
		if (!validateCoterminusairports) {
			if (MailConstantsVO.RESDIT_DELIVERED.equals(eventCode)
					|| MailConstantsVO.RESDIT_READYFOR_DELIVERY.equals(eventCode)) {
				String paCodeForDestinationByPass = findSystemParameterValue(
						"mail.operations.pacodeforvalidationbypass");
				if (paCodeForDestinationByPass != null && paCodeForDestinationByPass.trim().length() > 0 && paCode!=null) {
					if (paCodeForDestinationByPass.contains(paCode)
							|| (isValidDestForCarditMissingDomesticMailbag(actualAirport))) {
						validateCoterminusairports = true;
					}
				}
			}
		}
		return validateCoterminusairports;
	}

	public void saveCoterminusDetails(Collection<CoTerminusVO> coterminusVOs) throws RemoveException {
		log.debug(CLASS + " : " + "saveCoterminusDetails" + " Entering");
		CoterminusAirport coterminus = null;
		CoterminusAirportPK coterminusPK = null;
		StringBuilder duplicateCoTerminus = null;
		log.debug("" + "The coterminusVOs  are " + " " + coterminusVOs);
		log.debug("" + "The number of coterminusVOs  are " + " " + coterminusVOs.size());
		if (coterminusVOs != null && coterminusVOs.size() > 0) {
			for (CoTerminusVO coterminusVO : coterminusVOs) {
				log.debug("" + "The coterminusVO  are " + " " + coterminusVO);
				if (CoTerminusVO.OPERATION_FLAG_INSERT.equals(coterminusVO.getCoOperationFlag())) {
					coterminusPK = constructCoterminusAirportPK(coterminusVO);
					log.debug("" + "if: The coterminusPK  are " + " " + coterminusPK);
					try {
						coterminus = CoterminusAirport.find(coterminusPK);
						if (coterminus != null) {
							if (duplicateCoTerminus == null) {
								duplicateCoTerminus = new StringBuilder();
							}
							duplicateCoTerminus.append(coterminusVO.getCoAirportCodes()).append(",");
							new CoterminusAirport(coterminusVO);
						}
					} catch (FinderException ex) {
						new CoterminusAirport(coterminusVO);
					}
				} else if (CoTerminusVO.OPERATION_FLAG_UPDATE.equals(coterminusVO.getCoOperationFlag())) {
					coterminusPK = constructCoterminusAirportPK(coterminusVO);
					log.debug("" + "else if1: The coterminusPK  are " + " " + coterminusPK);
					try {
						coterminus = CoterminusAirport.find(coterminusPK);
					} catch (FinderException ex) {
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
					coterminus.update(coterminusVO);
				} else if (CoTerminusVO.OPERATION_FLAG_DELETE.equals(coterminusVO.getCoOperationFlag())) {
					log.debug("" + "else if2: The coterminusPK  are " + " " + coterminusPK);
					try {
						CoTerminusFilterVO filterVO = new CoTerminusFilterVO();
						filterVO.setAirportCodes(coterminusVO.getCoAirportCodes());
						filterVO.setCompanyCode(coterminusVO.getCompanyCode());
						filterVO.setGpaCode(coterminusVO.getGpaCode());
						filterVO.setResditModes(coterminusVO.getResditModes());
						Collection<CoTerminusVO> coTerminusVOsFronDb = findAllCoTerminusAirports(filterVO);
						if (coTerminusVOsFronDb != null && coTerminusVOsFronDb.size() > 0) {
							for (CoTerminusVO coTerminusVO : coTerminusVOsFronDb)
								coterminusPK = constructCoterminusAirportPK(coTerminusVO);
							coterminus = CoterminusAirport.find(coterminusPK);
							coterminus.remove();
						}
					} catch (FinderException ex) {
						coterminus.setLastUpdatedTime(
								Timestamp.valueOf(coterminusVO.getLastUpdateTime().toLocalDateTime()));
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
				}
			}
		}
	}

	public void saveRdtMasterDetails(Collection<MailRdtMasterVO> mailRdtMasterVOs) throws RemoveException {
		log.debug(CLASS + " : " + "saveCoterminusDetails" + " Entering");
		MailRdtMaster rdtDetails = null;
		MailRdtMasterPK rdtmasterPK = null;
		StringBuilder duplicateRdt = null;
		log.debug("" + "The coterminusVOs  are " + " " + mailRdtMasterVOs);
		log.debug("" + "The number of coterminusVOs  are " + " " + mailRdtMasterVOs.size());
		if (mailRdtMasterVOs != null && mailRdtMasterVOs.size() > 0) {
			for (MailRdtMasterVO mailRdtMasterVO : mailRdtMasterVOs) {
				log.debug("" + "The coterminusVO  are " + " " + mailRdtMasterVOs);
				if (MailRdtMasterVO.OPERATION_FLAG_INSERT.equals(mailRdtMasterVO.getOperationFlag())) {
					rdtmasterPK = constructRdtMasterPK(mailRdtMasterVO);
					log.debug("" + "if: The coterminusPK  are " + " " + rdtmasterPK);
					try {
						rdtDetails = MailRdtMaster.find(rdtmasterPK);
						if (rdtDetails != null) {
							if (duplicateRdt == null) {
								duplicateRdt = new StringBuilder();
							}
							duplicateRdt.append(mailRdtMasterVO.getAirportCodes()).append(",");
						}
					} catch (FinderException ex) {
						new MailRdtMaster(mailRdtMasterVO);
					}
				} else if (CoTerminusVO.OPERATION_FLAG_DELETE.equals(mailRdtMasterVO.getOperationFlag())) {
					rdtmasterPK = constructRdtMasterPK(mailRdtMasterVO);
					log.debug("" + "else if2: The coterminusPK  are " + " " + rdtmasterPK);
					try {
						rdtDetails = MailRdtMaster.find(rdtmasterPK);
					} catch (FinderException ex) {
						rdtDetails.setLastUpdatedTime(Timestamp.valueOf(mailRdtMasterVO.getLastUpdateTime().toLocalDateTime()));
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
					rdtDetails.remove();
				} else {
					rdtmasterPK = constructRdtMasterPK(mailRdtMasterVO);
					log.debug("" + "else if1: The coterminusPK  are " + " " + rdtmasterPK);
					try {
						rdtDetails = MailRdtMaster.find(rdtmasterPK);
					} catch (FinderException ex) {
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
					rdtDetails.update(mailRdtMasterVO);
				}
			}
		}
	}

	/** 
	* Method		:	MailController.saveRdtMasterDetailsXls Added by 	:	A-6991 on 23-Jul-2018 Used for 	:   ICRD-212544 Parameters	:	@param mailRdtMasterVOs Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoveException Return type	: 	Collection<ErrorVO>
	*/
	public Collection<ErrorVO> saveRdtMasterDetailsXls(Collection<MailRdtMasterVO> mailRdtMasterVOs) {
		log.debug(CLASS + " : " + "saveCoterminusDetails" + " Entering");
		log.debug("" + "The coterminusVOs  are " + " " + mailRdtMasterVOs);
		log.debug("" + "The number of coterminusVOs  are " + " " + mailRdtMasterVOs.size());
		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ZonedDateTime currentDate = localDateUtil.getLocalDate(null, false);
		LoginProfile logonAtributes = getLogonAttributes();
		Collection<MailRdtMasterVO> prevmailRdtMasterVOs = null;
		String companyCode = getLogonAttributes().getCompanyCode();
		RdtMasterFilterVO filterVO = new RdtMasterFilterVO();
		filterVO.setCompanyCode(companyCode);
		filterVO.setMailType("D");
		try {
			prevmailRdtMasterVOs = findRdtMasterDetails(filterVO);
		} finally {
		}
		if (prevmailRdtMasterVOs != null && prevmailRdtMasterVOs.size() > 0) {
			for (MailRdtMasterVO mailRdtMasterVO : prevmailRdtMasterVOs) {
				MailRdtMasterPK mailRdtMasterPK = constructRdtMasterPK(mailRdtMasterVO);
				MailRdtMaster mailRdtMaster = null;
				try {
					mailRdtMaster = MailRdtMaster.find(mailRdtMasterPK);
				} catch (FinderException e) {
					mailRdtMaster = null;
				}
				if (mailRdtMaster != null)
					mailRdtMaster.remove();
			}
		}
		if (mailRdtMasterVOs != null && mailRdtMasterVOs.size() > 0) {
			for (MailRdtMasterVO mailRdtMasterVO : mailRdtMasterVOs) {
				log.debug("" + "The coterminusVO  are " + " " + mailRdtMasterVOs);
				mailRdtMasterVO.setCompanyCode(logonAtributes.getCompanyCode());
				mailRdtMasterVO.setLastUpdateUser(logonAtributes.getUserId());
				mailRdtMasterVO.setLastUpdateTime(currentDate);
				mailRdtMasterVO.setMailType("D");
				String paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
				mailRdtMasterVO.setGpaCode(paCode_dom);
				if (mailRdtMasterVO.getRdtRule() != null && mailRdtMasterVO.getRdtRule().trim().length() > 0) {
					String[] ruleDetails = mailRdtMasterVO.getRdtRule().split("\\s+");
					int timeInMinutes = 0;
					String rule = mailRdtMasterVO.getRdtRule();
					String subString = new String("Day");
					int index = rule.indexOf(subString);
					if (index == 9) {
						String amOrPm = ruleDetails[1];
						if ("AM".endsWith(amOrPm)) {
							if (!ruleDetails[0].startsWith("0")) {
								ruleDetails[0] = "0" + ruleDetails[0];
							}
							timeInMinutes = (Integer.parseInt(ruleDetails[0].substring(0, 2)) * 60)
									+ (Integer.parseInt(ruleDetails[2].substring(3, 5)));
						} else {
							timeInMinutes = ((Integer.parseInt(ruleDetails[0].substring(0, 2)) + 12) * 60)
									+ (Integer.parseInt(ruleDetails[2].substring(3, 5)));
						}
						int day = (Integer.parseInt(ruleDetails[3]));
						mailRdtMasterVO.setRdtOffset(timeInMinutes);
						mailRdtMasterVO.setRdtDay(day);
					} else if (index == 0) {
						String amOrPm = ruleDetails[3];
						if (!ruleDetails[2].startsWith("0")) {
							ruleDetails[2] = "0" + ruleDetails[2];
						}
						if ("AM".endsWith(amOrPm)) {
							timeInMinutes = (Integer.parseInt(ruleDetails[2].substring(0, 2)) * 60)
									+ (Integer.parseInt(ruleDetails[2].substring(3, 5)));
						} else {
							timeInMinutes = ((Integer.parseInt(ruleDetails[2].substring(0, 2)) + 12) * 60)
									+ (Integer.parseInt(ruleDetails[2].substring(3, 5)));
						}
						int day = (Integer.parseInt(ruleDetails[1]));
						mailRdtMasterVO.setRdtOffset(timeInMinutes);
						mailRdtMasterVO.setRdtDay(day);
					}
				}
				if (mailRdtMasterVO.getMailServiceLevel().length() > 2) {
					Map<String, Collection<OneTimeVO>> oneTimes = null;
					oneTimes = findOneTimeDescription(companyCode, MAILSERVICELEVELS);
					log.debug("\n oneTimes******************" + oneTimes);
					Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
					oneTimeVOs = oneTimes.get(MAILSERVICELEVELS);
					for (OneTimeVO oneTimeVO : oneTimeVOs) {
						if (mailRdtMasterVO.getMailServiceLevel().equals(oneTimeVO.getFieldDescription())) {
							mailRdtMasterVO.setMailServiceLevel(oneTimeVO.getFieldValue());
							break;
						}
					}
				}
				if (mailRdtMasterVO.getMailClass().length() > 1) {
					Map<String, Collection<OneTimeVO>> oneTimes = null;
					oneTimes = findOneTimeDescription(companyCode, MAILCLASS);
					log.debug("\n oneTimes******************" + oneTimes);
					Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
					oneTimeVOs = oneTimes.get(MAILCLASS);
					for (OneTimeVO oneTimeVO : oneTimeVOs) {
						if (mailRdtMasterVO.getMailClass().equals(oneTimeVO.getFieldDescription())) {
							mailRdtMasterVO.setMailClass(oneTimeVO.getFieldValue());
							break;
						}
					}
				}
				try {
					new MailRdtMaster(mailRdtMasterVO);
				} finally {
				}
			}
		}
		errors.add(error);
		return errors;
	}

	private MailRdtMasterPK constructRdtMasterPK(MailRdtMasterVO mailRdtMasterVO) {
		MailRdtMasterPK mailRdtMasterPK = new MailRdtMasterPK();
		mailRdtMasterPK.setCompanyCode(mailRdtMasterVO.getCompanyCode());
		if (mailRdtMasterVO.getSeqnum() != 0)
			mailRdtMasterPK.setSERNUM(mailRdtMasterVO.getSeqnum());
		return mailRdtMasterPK;
	}

	public void saveServiceStandardDetails(Collection<MailServiceStandardVO> mailServiceStandardVOs,
			Collection<MailServiceStandardVO> mailServiceStandardVOstodelete) throws RemoveException {
		log.debug(CLASS + " : " + "saveServiceStandardDetails" + " Entering");
		MailServiceStandard mailServiceStandard = null;
		MailServiceStandardPK mailServiceStandardPK = null;
		MailServiceStandardPK mailServiceStandardPKTodelete = null;
		MailServiceStandard mailServiceStandardTodelete = null;
		log.debug("" + "The mailServiceStandardVOs  are " + " " + mailServiceStandardVOs);
		log.debug("" + "The number of mailServiceStandardVOs  are " + " " + mailServiceStandardVOs.size());
		if (mailServiceStandardVOs != null && mailServiceStandardVOs.size() > 0) {
			for (MailServiceStandardVO mailServiceStandardVO : mailServiceStandardVOs) {
				log.debug("" + "The mailServiceStandardVO  is " + " " + mailServiceStandardVO);
				if (mailServiceStandardVO.OPERATION_FLAG_INSERT.equals(mailServiceStandardVO.getOperationFlag())) {
					mailServiceStandardPK = constructMailServiceStandardPK(mailServiceStandardVO);
					log.debug("" + "Insert: The mailServiceStandardPK  is " + " " + mailServiceStandardPK);
					try {
						mailServiceStandard = MailServiceStandard.find(mailServiceStandardPK);
					} catch (FinderException ex) {
						new MailServiceStandard(mailServiceStandardVO);
					}
				} else if (mailServiceStandardVO.OPERATION_FLAG_UPDATE
						.equals(mailServiceStandardVO.getOperationFlag())) {
					mailServiceStandardPK = constructMailServiceStandardPK(mailServiceStandardVO);
					log.debug("" + "Update: The mailServiceStandardPK  is " + " " + mailServiceStandardPK);
					try {
						mailServiceStandard = MailServiceStandard.find(mailServiceStandardPK);
					} catch (FinderException ex) {
						int index = ((ArrayList<MailServiceStandardVO>) mailServiceStandardVOs)
								.lastIndexOf(mailServiceStandardVO);
						MailServiceStandardVO mailServiceStandardVOTodelete = null;
						if (mailServiceStandardVOstodelete.size() >= index + 1) {
							mailServiceStandardVOTodelete = ((ArrayList<MailServiceStandardVO>) mailServiceStandardVOstodelete)
									.get(index);
							mailServiceStandardPKTodelete = constructMailServiceStandardPK(
									mailServiceStandardVOTodelete);
							try {
								mailServiceStandardTodelete = MailServiceStandard.find(mailServiceStandardPKTodelete);
							} catch (FinderException finderException) {
								throw new SystemException(finderException.getErrorCode(), finderException.getMessage(),
										finderException);
							}
							mailServiceStandardTodelete.remove();
						}
						new MailServiceStandard(mailServiceStandardVO);
					}
					if (mailServiceStandard != null) {
						mailServiceStandard.update(mailServiceStandardVO);
					}
				} else if (mailServiceStandardVO.OPERATION_FLAG_DELETE
						.equals(mailServiceStandardVO.getOperationFlag())) {
					mailServiceStandardPK = constructMailServiceStandardPK(mailServiceStandardVO);
					log.debug("" + "Delete: The mailServiceStandardPK  is " + " " + mailServiceStandardPK);
					try {
						mailServiceStandard = MailServiceStandard.find(mailServiceStandardPK);
					} catch (FinderException ex) {
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
					mailServiceStandard.remove();
				}
			}
		}
	}

	private MailServiceStandardPK constructMailServiceStandardPK(MailServiceStandardVO mailServiceStandardVO) {
		MailServiceStandardPK mailServiceStandardPK = new MailServiceStandardPK();
		mailServiceStandardPK.setCompanyCode(mailServiceStandardVO.getCompanyCode());
		mailServiceStandardPK.setGpaCode(mailServiceStandardVO.getGpaCode());
		mailServiceStandardPK.setOriginCode(mailServiceStandardVO.getOriginCode());
		mailServiceStandardPK.setDestCode(mailServiceStandardVO.getDestinationCode());
		mailServiceStandardPK.setServiceLevel(mailServiceStandardVO.getServicelevel());
		return mailServiceStandardPK;
	}

	private CoterminusAirportPK constructCoterminusAirportPK(CoTerminusVO coterminusVO) {
		CoterminusAirportPK cotermuAirportPK = new CoterminusAirportPK();
		cotermuAirportPK.setCompanyCode(coterminusVO.getCompanyCode());
		cotermuAirportPK.setGpaCode(coterminusVO.getGpaCode());
		if (coterminusVO.getSeqnum() != 0)
			cotermuAirportPK.setSernum(coterminusVO.getSeqnum());
		return cotermuAirportPK;
	}

	/** 
	* Method		:	MailController.findOneTimeDescription Added by 	:	A-6991 on 14-Jul-2017 Used for 	:   ICRD-208718 Parameters	:	@param companyCode Parameters	:	@param oneTimeCode Parameters	:	@return Return type	: 	Map<String,Collection<OneTimeVO>>
	*/
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode, String oneTimeCode) {
		log.debug(CLASS + " : " + "findOneTimeDescription" + " Entering");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(oneTimeCode);
			oneTimes = sharedDefaultsProxy.findOneTimeValues(companyCode, fieldValues);
		} catch (SystemException e) {
			log.info(e.getMessage());
		}
		log.debug(CLASS + " : " + "findOneTimeDescription" + " Exiting");
		return oneTimes;
	}

	/** 
	* Method		:	MailController.findOfficeOfExchangeForPA Added by 	:	a-6245 on 10-Jul-2017 Used for 	: Parameters	:	@param companyCode Parameters	:	@param officeOfExchange Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	String
	*/
	@Cacheable(
			value = {"officeOfExchangesForPaCodeCache"},
			key = "#paCode"
	)
	public Map<String, String> findOfficeOfExchangeForPA(String companyCode, String paCode) {
		log.debug(CLASS + " : " + "findOfficeOfExchangeForAirports" + " Entering");
		return OfficeOfExchange.findOfficeOfExchangeForPA(companyCode, paCode);
	}

	/** 
	* Method		:	MailController.findAgentCodeForPA Added by 	:	U-1267 on Nov 1, 2017 Used for 	:	ICRD-211205 Parameters	:	@param companyCode Parameters	:	@param officeOfExchange Parameters	:	@return Return type	: 	String
	*/
	public String findAgentCodeForPA(String companyCode, String officeOfExchange) {
		log.debug(CLASS + " : " + "findAgentCodeOfPA" + " Entering");
		return OfficeOfExchange.findAgentCodeForPA(companyCode, officeOfExchange);
	}

	/** 
	* @param mailServiceLevelVOs
	* @return
	* @throws SystemException
	* @throws PersistenceException
	* @throws RemoveException
	* @author A-6986
	*/
	public Collection<ErrorVO> saveMailServiceLevelDtls(Collection<MailServiceLevelVO> mailServiceLevelVOs) {
		log.debug(CLASS + " : " + "saveMailServiceLevelDtls" + " Entering");
		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ZonedDateTime currentDate = localDateUtil.getLocalDate(null, false);
		LoginProfile logonAtributes = getLogonAttributes();
		Collection<MailServiceLevel> mailServiceLevels = null;
		String companyCode = getLogonAttributes().getCompanyCode();
		try {
			mailServiceLevels = getEntityManagerForObjectDAO()
					.findServiceLevelDtls(getLogonAttributes().getCompanyCode());
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e.getMessage(), e);
		}
		if (mailServiceLevels != null && mailServiceLevels.size() > 0) {
			for (MailServiceLevel serviceLevel : mailServiceLevels) {
				removeMailServiceLevel(serviceLevel);
			}
		}
		if (mailServiceLevelVOs != null && mailServiceLevelVOs.size() > 0) {
			for (MailServiceLevelVO mailServiceLevelVO : mailServiceLevelVOs) {
				mailServiceLevelVO.setCompanyCode(logonAtributes.getCompanyCode());
				mailServiceLevelVO.setLastUpdatedUser(logonAtributes.getUserId());
				mailServiceLevelVO.setLastUpdatedTime(currentDate);
				if (mailServiceLevelVO.getMailCategory().equals("")
						&& mailServiceLevelVO.getMailCategory().length() == 0) {
					mailServiceLevelVO.setMailCategory(HYPHEN);
				}
				if (mailServiceLevelVO.getMailClass().equals("") && mailServiceLevelVO.getMailClass().length() == 0) {
					mailServiceLevelVO.setMailClass(HYPHEN);
				}
				if (mailServiceLevelVO.getMailSubClass().equals("")
						&& mailServiceLevelVO.getMailSubClass().length() == 0) {
					mailServiceLevelVO.setMailSubClass(HYPHEN);
				}
				if (mailServiceLevelVO.getMailServiceLevel().length() > 2) {
					Map<String, Collection<OneTimeVO>> oneTimes = null;
					oneTimes = findOneTimeDescription(companyCode, MAILSERVICELEVELS);
					log.debug("\n oneTimes******************" + oneTimes);
					Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
					oneTimeVOs = oneTimes.get(MAILSERVICELEVELS);
					for (OneTimeVO oneTimeVO : oneTimeVOs) {
						if (mailServiceLevelVO.getMailServiceLevel().equals(oneTimeVO.getFieldDescription())) {
							mailServiceLevelVO.setMailServiceLevel(oneTimeVO.getFieldValue());
							break;
						}
					}
				}
				try {
					saveMailServiceLevel(mailServiceLevelVO);
				} catch (CreateException e) {
					error = new ErrorVO(e.getMessage());
				}
			}
		}
		errors.add(error);
		return errors;
	}

	/** 
	* @param
	* @return
	* @throws SystemException
	* @author A-6986
	*/
	public LoginProfile getLogonAttributes() {
		return contextUtil.callerLoginProfile();
	}

	/** 
	* @param
	* @return
	* @throws SystemException
	* @author A-6986
	*/
	public MailtrackingDefaultsObjectInterface getEntityManagerForObjectDAO() throws PersistenceException {
		return PersistenceController.getEntityManager().getObjectQueryDAO(MODULENAME);
	}

	/** 
	* @param mailServiceLevel
	* @return
	* @throws SystemException
	* @author A-6986
	*/
	public void removeMailServiceLevel(MailServiceLevel mailServiceLevel) {
		try {
			mailServiceLevel.remove();
		} catch (RemoveException e) {
			throw new SystemException(e.getMessage(), e.getMessage(), e);
		}
	}

	/** 
	* @param newMailServiceLevelVO
	* @return
	* @throws SystemException
	* @throws CreateException
	* @author A-6986
	*/
	public void saveMailServiceLevel(MailServiceLevelVO newMailServiceLevelVO) throws CreateException {
		new MailServiceLevel(newMailServiceLevelVO);
	}

	/**
	* @return
	* @throws SystemException
	* @author A-6986
	*/
	public String findMailServiceLevel(MailbagVO mailBagVO) {
		String serviceLevel = null;
		String paCode_int = null;
		String paCode_dom = null;
		paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
		paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
		MailServiceLevelVO mailServiceLevelVO = new MailServiceLevelVO();
		mailServiceLevelVO.setCompanyCode(mailBagVO.getCompanyCode());
		mailServiceLevelVO.setPoaCode(mailBagVO.getPaCode());
		if (paCode_int.equals(mailBagVO.getPaCode())) {
			mailServiceLevelVO.setMailCategory(mailBagVO.getMailCategoryCode());
			if (mailBagVO.getMailClass() != null && mailBagVO.getMailClass().length() > 0)
				mailServiceLevelVO.setMailClass(mailBagVO.getMailClass());
			else
				mailServiceLevelVO.setMailClass(HYPHEN);
			if (mailBagVO.getMailSubclass() != null && mailBagVO.getMailSubclass().length() > 0)
				mailServiceLevelVO.setMailSubClass(mailBagVO.getMailSubclass());
			else
				mailServiceLevelVO.setMailSubClass(HYPHEN);
		} else if (paCode_dom.equals(mailBagVO.getPaCode())) {
			mailServiceLevelVO.setMailClass(mailBagVO.getMailClass());
			if (mailBagVO.getMailCategoryCode() != null)
				mailServiceLevelVO.setMailCategory(mailBagVO.getMailCategoryCode());
			else
				mailServiceLevelVO.setMailCategory(HYPHEN);
			if (mailBagVO.getMailSubclass() != null)
				mailServiceLevelVO.setMailSubClass(mailBagVO.getMailSubclass());
			else
				mailServiceLevelVO.setMailSubClass(HYPHEN);
		} else {
			return serviceLevel;
		}
		try {
			if (paCode_int.equals(mailBagVO.getPaCode())) {
				serviceLevel = constructDAO().findMailServiceLevelForIntPA(mailServiceLevelVO);
			} else if (paCode_dom.equals(mailBagVO.getPaCode())) {
				serviceLevel = constructDAO().findMailServiceLevelForDomPA(mailServiceLevelVO);
			} else {
				return serviceLevel;
			}
		} catch (PersistenceException ex) {
			log.debug(" ++++  Mail Service Level Not Found  +++");
		}
		return serviceLevel;
	}

	/** 
	* Method		:	MailController.listPostalCalendarDetails Added by 	:	A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param uSPSPostalCalendarFilterVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	Collection<USPSPostalCalendarVO>
	*/
	public Collection<USPSPostalCalendarVO> listPostalCalendarDetails(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) {
		log.debug(CLASS + " : " + "listPostalCalendarDetails" + " Entering");
		return USPSPostalCalendar.listPostalCalendarDetails(uSPSPostalCalendarFilterVO);
	}

	/** 
	* Method		:	MailController.savePostalCalendar Added by 	:	A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param uSPSPostalCalendarVOs Parameters	:	@throws SystemException Parameters	:	@throws RemoveException Return type	: 	void
	*/
	public void savePostalCalendar(Collection<USPSPostalCalendarVO> uSPSPostalCalendarVOs) throws RemoveException {
		log.debug(CLASS + " : " + "savePostalCalendar" + " Entering");
		USPSPostalCalendar uSPSPostalCalendar = null;
		USPSPostalCalendarPK uSPSPostalCalendarPK = null;
		LoginProfile logonAttributes = null;
		PostalCalendarAuditVO auditVO = null;
		log.debug("" + "The uSPSPostalCalendarVOs  are " + " " + uSPSPostalCalendarVOs);
		log.debug("" + "The number of uSPSPostalCalendarVOs  are " + " " + uSPSPostalCalendarVOs.size());
		if (uSPSPostalCalendarVOs != null && !uSPSPostalCalendarVOs.isEmpty()) {
			for (USPSPostalCalendarVO uSPSPostalCalendarVO : uSPSPostalCalendarVOs) {
				log.debug("" + "The uSPSPostalCalendarVO  are " + " " + uSPSPostalCalendarVO);
				if (USPSPostalCalendarVO.OPERATION_FLAG_INSERT.equals(uSPSPostalCalendarVO.getCalOperationFlags())) {
					uSPSPostalCalendarPK = constructUSPSPostalCalendarPK(uSPSPostalCalendarVO);
					log.debug("" + "if: The uSPSPostalCalendarPK  are " + " " + uSPSPostalCalendarPK);
					try {
						uSPSPostalCalendar = USPSPostalCalendar.find(uSPSPostalCalendarPK);
					} catch (FinderException ex) {
						new USPSPostalCalendar(uSPSPostalCalendarVO);
					}
				} else if (USPSPostalCalendarVO.OPERATION_FLAG_UPDATE
						.equals(uSPSPostalCalendarVO.getCalOperationFlags())) {
					uSPSPostalCalendarPK = constructUSPSPostalCalendarPK(uSPSPostalCalendarVO);
					log.debug("" + "else if1: The uSPSPostalCalendarPK  are " + " " + uSPSPostalCalendarPK);
					try {
						uSPSPostalCalendar = USPSPostalCalendar.find(uSPSPostalCalendarPK);
					} catch (FinderException ex) {
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
					uSPSPostalCalendar.update(uSPSPostalCalendarVO);
					//TODO: NEO Audit to be corrected
					AuditConfigurationBuilder auditConfigurationBuilder = new AuditConfigurationBuilder();
					if ("I".equals(uSPSPostalCalendarVO.getFilterCalender())) {
						auditUtils.performAudit(auditConfigurationBuilder
								.withBusinessObject(uSPSPostalCalendarVO)
								.withTriggerPoint("MTK055")
								.withActionCode("Update")
								.withEventName("postalCalendarUpdate")
								.withtransaction("Save Update").build());

					}

				} else if (USPSPostalCalendarVO.OPERATION_FLAG_DELETE
						.equals(uSPSPostalCalendarVO.getCalOperationFlags())) {
					uSPSPostalCalendarPK = constructUSPSPostalCalendarPK(uSPSPostalCalendarVO);
					log.debug("" + "else if2: The uSPSPostalCalendarPK  are " + " " + uSPSPostalCalendarPK);
					try {
						uSPSPostalCalendar = USPSPostalCalendar.find(uSPSPostalCalendarPK);
					} catch (FinderException ex) {
						uSPSPostalCalendar.setLastUpdatedTime(Timestamp.valueOf(uSPSPostalCalendarVO.getLstUpdTime().toLocalDateTime()));
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
					uSPSPostalCalendar.remove();
				}
			}
		}
	}

	/** 
	* Method		:	MailController.constructUSPSPostalCalendarPK Added by 	:	A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param uSPSPostalCalendarVO Parameters	:	@return Return type	: 	USPSPostalCalendarPK
	*/
	private USPSPostalCalendarPK constructUSPSPostalCalendarPK(USPSPostalCalendarVO uSPSPostalCalendarVO) {
		USPSPostalCalendarPK uSPSPostalCalendarPK = new USPSPostalCalendarPK();
		uSPSPostalCalendarPK.setCompanyCode(uSPSPostalCalendarVO.getCompanyCode());
		uSPSPostalCalendarPK.setGpacod(uSPSPostalCalendarVO.getGpacod());
		uSPSPostalCalendarPK.setFilterCalender(uSPSPostalCalendarVO.getFilterCalender());
		if (uSPSPostalCalendarVO.getCalSeqnum() != 0)
			uSPSPostalCalendarPK.setSerialNumber(uSPSPostalCalendarVO.getCalSeqnum());
		return uSPSPostalCalendarPK;
	}

	/**
	* @return
	* @throws SystemException
	* @throws CreateException
	* @author A-6986
	*/
	public void saveMailHandoverDetails(Collection<MailHandoverVO> mailHandoverVOs) {
		log.debug(CLASS + " : " + "saveMailHandoverDetails" + " Entering");
		for (MailHandoverVO mailHandoverVO : mailHandoverVOs) {
			MailHandoverTime mailHandoverTime = null;
			if (MailHandoverVO.OPERATION_FLAG_INSERT.equals(mailHandoverVO.getHoOperationFlags())) {
				log.info("!!!!!!!!!!   INSERT   !!!!!!!!!!!!!");
				try {
					mailHandoverTime = findMailHandOverTime(mailHandoverVO);
				} finally {
				}
				if (mailHandoverTime == null) {
					saveMailHandOverTime(mailHandoverVO);
				}
			} else if (MailHandoverVO.OPERATION_FLAG_UPDATE.equals(mailHandoverVO.getHoOperationFlags())) {
				log.info("!!!!   UPDATE   !!!!!!!!!!!!!!!!!!!");
				mailHandoverTime = findMailHandOverTime(mailHandoverVO);
				mailHandoverTime.update(mailHandoverVO);
			} else if (MailHandoverVO.OPERATION_FLAG_DELETE.equals(mailHandoverVO.getHoOperationFlags())) {
				log.info("!!!!   DELETE   !!!!!!!!!!!!!!!!!!!");
				mailHandoverTime = findMailHandOverTime(mailHandoverVO);
				if (mailHandoverTime != null) {
					removeMailHandOverTime(mailHandoverTime);
				}
			}
		}
	}

	/** 
	* @param mailHandoverFilterVO
	* @throws SystemException
	* @author A-6986
	*/
	public Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO, int pageNumber) {
		log.debug(CLASS + " : " + "findMailHandoverDetails" + " Entering");
		return MailHandoverTime.findMailHandoverDetails(mailHandoverFilterVO, pageNumber);
	}

	public MailHandoverTime findMailHandOverTime(MailHandoverVO mailHandoverVO) {
		MailHandoverTime mailHandoverTime = new MailHandoverTime();
		mailHandoverTime = MailHandoverTime.findMailHandoverTime(mailHandoverVO);
		return mailHandoverTime;
	}

	public void removeMailHandOverTime(MailHandoverTime mailHandoverTime) {
		try {
			mailHandoverTime.remove();
		} catch (RemoveException e) {
			throw new SystemException(e.getMessage(), e.getMessage(), e);
		}
	}

	public void saveMailHandOverTime(MailHandoverVO mailHandoverVO) {
		new MailHandoverTime(mailHandoverVO);
	}

	/** 
	* @param gpaContractVOs
	* @throws SystemException
	* @throws RemoteException
	* @author A-6986
	*/
	public void saveContractDetails(Collection<GPAContractVO> gpaContractVOs) throws BusinessException {
		log.debug(CLASS + " : " + "\n\nsaveContractDetails" + " Entering");
		List<ErrorVO> errors = new ArrayList<ErrorVO>();
		GPAContract gpaContract = null;
		GPAContractPK gpaContractPK = null;
		if (gpaContractVOs != null && gpaContractVOs.size() > 0) {
			for (GPAContractVO gpaContractVO : gpaContractVOs) {
				log.debug("" + "\n\nGPAContractVO  : " + " " + gpaContractVO);
				gpaContractPK = new GPAContractPK();
				gpaContractPK.setCompanyCode(gpaContractVO.getCompanyCode());
				gpaContractPK.setGpaCode(gpaContractVO.getPaCode());
				gpaContractPK.setSernum(gpaContractVO.getSernum());
				if (GPAContractVO.OPERATION_FLAG_INSERT.equals(gpaContractVO.getConOperationFlags())) {
					errors = validateContractIds(gpaContractVO);
					if (errors == null || errors.size() == 0) {
						new GPAContract(gpaContractVO);
					}
				} else if (GPAContractVO.OPERATION_FLAG_UPDATE.equals(gpaContractVO.getConOperationFlags())) {
					try {
						gpaContract = gpaContract.find(gpaContractPK);
					} catch (FinderException ex) {
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
					gpaContract.update(gpaContractVO);
				} else if (GPAContractVO.OPERATION_FLAG_DELETE.equals(gpaContractVO.getConOperationFlags())) {
					try {
						gpaContract = gpaContract.find(gpaContractPK);
					} catch (FinderException ex) {
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
					try {
						gpaContract.remove();
					} catch (RemoveException ex) {
						throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
					}
				}
			}
		}
		//TODO: Neo to correct exception handling
		if (errors.size() > 0) {

			BusinessException exception = new BusinessException(errors);

			throw exception;
		}
	}

	/** 
	* @param gpaContractFilterVO
	* @throws SystemException
	* @throws RemoteException
	* @author A-6986
	*/
	public Collection<GPAContractVO> listContractDetails(GPAContractFilterVO gpaContractFilterVO) {
		log.debug(CLASS + " : " + "listContractDetails" + " Entering");
		return GPAContract.listContractdetails(gpaContractFilterVO);
	}

	private List<ErrorVO> validateContractIds(GPAContractVO contractVO) {
		ErrorVO error = null;
		Object[] errorCodes = new Object[3];
		List<ErrorVO> errors = new ArrayList<ErrorVO>();
		GPAContractFilterVO filterVO = new GPAContractFilterVO();
		Collection<GPAContractVO> contractVOs = null;
		filterVO.setCompanyCode(contractVO.getCompanyCode());
		filterVO.setContractID(contractVO.getContractIDs());
		filterVO.setPaCode(contractVO.getPaCode());
		contractVOs = GPAContract.listODForContract(filterVO);
		if (contractVOs == null || contractVOs.isEmpty()) {
			filterVO = new GPAContractFilterVO();
			filterVO.setCompanyCode(contractVO.getCompanyCode());
			filterVO.setOrigin(contractVO.getOriginAirports());
			filterVO.setDestination(contractVO.getDestinationAirports());
			filterVO.setPaCode(contractVO.getPaCode());
			contractVOs = GPAContract.listODForContract(filterVO);
		}
		if (contractVOs != null && contractVOs.size() > 0) {
			for (GPAContractVO gpaContract : contractVOs) {
				if (((gpaContract.getOriginAirports().equals(contractVO.getOriginAirports()))
						&& (gpaContract.getDestinationAirports().equals(contractVO.getDestinationAirports()))
						&& gpaContract.getCidFromDates().equals(contractVO.getCidFromDates())
						&& gpaContract.getCidToDates().equals(contractVO.getCidToDates())
						&& gpaContract.getContractIDs().equals(contractVO.getContractIDs()))) {
					error = new ErrorVO(CONTRACTID_ALREADY_EXISTS);
					errorCodes[0] = gpaContract.getOriginAirports();
					errorCodes[1] = gpaContract.getDestinationAirports();
					error.setErrorData(errorCodes);
					errors.add(error);
				}
				if (((gpaContract.getOriginAirports().equals(contractVO.getOriginAirports()))
						&& (gpaContract.getDestinationAirports().equals(contractVO.getDestinationAirports())))) {
					ZonedDateTime fromDate = localDateUtil.getLocalDate(null, false);
					fromDate = LocalDate.withDate(fromDate, contractVO.getCidFromDates());
					ZonedDateTime toDate = localDateUtil.getLocalDate(null, false);
					toDate = LocalDate.withDate(toDate, contractVO.getCidToDates());
					ZonedDateTime fromDateDb = localDateUtil.getLocalDate(null, false);
					fromDateDb = LocalDate.withDate(fromDateDb, gpaContract.getCidFromDates());
					ZonedDateTime toDateDb = localDateUtil.getLocalDate(null, false);
					toDateDb = LocalDate.withDate(toDateDb, gpaContract.getCidToDates());
					if ((fromDate.compareTo(fromDateDb) >= 0 && fromDate.compareTo(toDateDb) <= 0)
							|| (toDate.compareTo(fromDateDb) >= 0 && toDate.compareTo(toDateDb) <= 0)) {
						error = new ErrorVO(CONTRACTID_ALREADY_EXISTS_FOR_SAME_DATE_SPAN);
						errorCodes[0] = gpaContract.getCidFromDates();
						errorCodes[1] = gpaContract.getCidToDates();
						error.setErrorData(errorCodes);
						errors.add(error);
						break;
					}
				}
			}
		}
		return errors;
	}

	/** 
	* @param incentiveConfigurationFilterVO
	* @return Collection<IncentiveConfigurationVO>
	* @throws SystemException
	* @throws RemoteException
	* @author A-6986Used for ICRD-232361
	*/
	public Collection<IncentiveConfigurationVO> findIncentiveConfigurationDetails(
			IncentiveConfigurationFilterVO incentiveConfigurationFilterVO) {
		//this.log.entering(CLASS, "findIncentiveConfigurationDetails");
		return MailIncentiveMaster.findIncentiveConfigurationDetails(incentiveConfigurationFilterVO);
	}

	/** 
	* @param incentiveConfigurationVOs
	* @throws SystemException
	* @throws RemoteException
	* @throws RemoveException
	* @throws MailTrackingBusinessException
	* @author A-6986Used for ICRD-232361
	*/
	public void saveIncentiveConfigurationDetails(Collection<IncentiveConfigurationVO> incentiveConfigurationVOs)
			 {
		log.debug(CLASS + " : " + "saveIncentiveConfigurationDetails" + " Entering");
		MailIncentiveMaster mailIncentiveMaster = null;
		MailIncentiveMasterPK mailIncentiveMasterPK = null;
		if ((incentiveConfigurationVOs != null) && (incentiveConfigurationVOs.size() > 0)) {
			for (IncentiveConfigurationVO incentiveConfigurationVO : incentiveConfigurationVOs) {
				mailIncentiveMasterPK = new MailIncentiveMasterPK();
				mailIncentiveMasterPK.setCompanyCode(incentiveConfigurationVO.getCompanyCode());
				if (incentiveConfigurationVO.getIncentiveSerialNumber() > 0) {
					mailIncentiveMasterPK.setIncentiveSerialNumber(incentiveConfigurationVO.getIncentiveSerialNumber());
				}
				if ((IncentiveConfigurationVO.OPERATION_FLAG_INSERT
						.equals(incentiveConfigurationVO.getIncOperationFlags()))
						|| (IncentiveConfigurationVO.OPERATION_FLAG_INSERT
								.equals(incentiveConfigurationVO.getDisIncOperationFlags()))) {
					new MailIncentiveMaster(incentiveConfigurationVO);
				} else if ((IncentiveConfigurationVO.OPERATION_FLAG_UPDATE
						.equals(incentiveConfigurationVO.getIncOperationFlags()))
						|| (IncentiveConfigurationVO.OPERATION_FLAG_UPDATE
								.equals(incentiveConfigurationVO.getDisIncOperationFlags()))) {
					try {
						mailIncentiveMaster = MailIncentiveMaster.find(mailIncentiveMasterPK);
					} catch (FinderException e) {
						throw new SystemException(e.getErrorCode(), e.getMessage(), e);
					}
					mailIncentiveMaster.update(incentiveConfigurationVO);
				} else if (IncentiveConfigurationVO.OPERATION_FLAG_DELETE
						.equals(incentiveConfigurationVO.getIncOperationFlags())
						|| IncentiveConfigurationVO.OPERATION_FLAG_DELETE
								.equals(incentiveConfigurationVO.getDisIncOperationFlags())) {
					try {
						mailIncentiveMaster = MailIncentiveMaster.find(mailIncentiveMasterPK);
					} catch (FinderException e) {
						throw new SystemException(e.getErrorCode(), e.getMessage(), e);
					}
					try {
					mailIncentiveMaster.remove();
					} catch (RemoveException e) {
						throw new SystemException(e.getErrorCode(), e.getMessage(), e);
					}
				}
			}
		}
	}

	/** 
	* Method		:	MailController.saveRoutingIndexDetails Added by 	:	A-7531 on 08-Oct-2018 Used for 	: Parameters	:	@param routingIndexVOs Return type	: 	void
	* @throws FinderException
	*/
	public void saveRoutingIndexDetails(Collection<RoutingIndexVO> routingIndexVOs){
		if (routingIndexVOs != null) {
			for (RoutingIndexVO routingIndexVO : routingIndexVOs) {
				new RoutingIndex(routingIndexVO);
			}
		}
	}

	/** 
	* Method		:	MailController.findRoutingIndex Added by 	:	A-7531 on 30-Oct-2018 Used for 	: Parameters	:	@param routingIndexVO Parameters	:	@return Return type	: 	Collection<RoutingIndexVO>
	* @throws SystemException
	*/
	public Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO){
		Collection<RoutingIndexVO> routingIndexVOs = null;
		try {
			routingIndexVOs = constructDAO().findRoutingIndex(routingIndexVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		return routingIndexVOs;
	}
	public ZonedDateTime calculateTransportServiceWindowEndTime(MailbagVO mailbagVO) {
		ZonedDateTime transportServiceWindowEndTime = null;
		MailHandoverVO mailHandoverVO = new MailHandoverVO();
		if (mailbagVO.getConsignmentDate() != null) {
			ZonedDateTime consDate = localDateUtil.getLocalDate(mailbagVO.getDestination(), true);
			//TODO: Neo to correct below code
			// String consignmentDateString = mailbagVO.getConsignmentDate().toDisplayFormat(false);
			String consignmentDateString = null;
			if (consignmentDateString.trim().length() == 11) {
				consDate = LocalDate.withDate(consDate, consignmentDateString);
			} else {
				consDate = LocalDate.withDateAndTime(consDate, consignmentDateString);
			}
			ArrayList<String> systemParameters = new ArrayList<String>();
			systemParameters.add("mail.operations.USPSCloseoutoffsettime");
			HashMap<String, String> systemParameterMap = null;
			systemParameterMap = sharedDefaultsProxy.findSystemParameterByCodes(systemParameters);
			log.debug("" + " systemParameterMap " + " " + systemParameterMap);
			if (systemParameterMap != null) {
				String closeOutTime = systemParameterMap.get("mail.operations.USPSCloseoutoffsettime");
				int timeInMinutes = Integer.parseInt(closeOutTime);
				timeInMinutes = timeInMinutes * -1;
				consDate.plusMinutes(timeInMinutes);
			}
			transportServiceWindowEndTime = localDateUtil.getLocalDate(mailbagVO.getDestination(), true);
			String consDateString = consDate.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT));
			transportServiceWindowEndTime = LocalDate.withDate(transportServiceWindowEndTime, consDateString);
			int serviceStandard = 0;
			try {
				if (mailbagVO.getMailServiceLevel() != null)
					serviceStandard = constructDAO().findServiceStandard(mailbagVO);
			} catch (PersistenceException e1) {
			}
			if (serviceStandard != 0) {

				transportServiceWindowEndTime.plusDays(serviceStandard);
			}
			mailHandoverVO.setCompanyCode(mailbagVO.getCompanyCode());
			mailHandoverVO.setGpaCode(mailbagVO.getPaCode());
			mailHandoverVO.setHoAirportCodes(mailbagVO.getDestination());
			boolean mailClassCharCheck = false;
			try {
				Integer.parseInt(mailbagVO.getMailClass());
			} catch (NumberFormatException e) {
				mailClassCharCheck = true;
			}
			if (mailClassCharCheck) {
				mailHandoverVO.setMailClass(mailbagVO.getMailClass());
			} else {
				mailHandoverVO.setMailSubClass(mailbagVO.getMailSubclass());
			}
			if (mailbagVO.getDoe() != null && mailbagVO.getDoe().length() == 6)
				;
			mailHandoverVO.setExchangeOffice(mailbagVO.getDoe().substring(0, 5));
			String mailHandoverTime = null;
			try {
				try {
					mailHandoverTime = constructDAO().findMailHandoverDetails(mailHandoverVO);
				} catch (PersistenceException e) {
				}
			} finally {
			}
			if (mailHandoverTime != null) {
				String handOverTime = mailHandoverTime;
				int timeInMinutes = 0;
				timeInMinutes = (Integer.parseInt(handOverTime.substring(0, 2)) * 60
						+ Integer.parseInt(handOverTime.substring(2, 4)));

				transportServiceWindowEndTime.plusMinutes(timeInMinutes);
			}
		}
		return transportServiceWindowEndTime;
	}

	public void saveMailRuleConfiguration(MailRuleConfigVO mailRuleConfigVO) {
		log.debug(CLASS + " : " + "saveMailRuleConfiguration" + " Entering");
		new MailMessageConfiguration(mailRuleConfigVO);
		log.debug(CLASS + " : " + "saveMailRuleConfiguration" + " Exiting");
	}

	/** 
	* Method		:	MailController.updatePostalCalendarAuditHisotry Added by 	:	A-5219 on 22-Aug-2020 Used for 	: Parameters	:	@param auditVO Parameters	:	@param calendarVO Return type	: 	void
	*/
	//TODO: Audit to be moved to Neo
	/*
	private void updatePostalCalendarAuditHisotry(PostalCalendarAuditVO auditVO, USPSPostalCalendarVO calendarVO) {
		StringBuilder addInfo = new StringBuilder("Postal Calendar Updated for the period ");
		addInfo.append(calendarVO.getPeriods()).append(". ");
		for (AuditFieldVO auditField : auditVO.getAuditFields()) {
			if (auditField != null) {
				String oldValue = (auditField.getOldValue() != null && auditField.getOldValue().trim().length() > 0)
						? auditField.getOldValue().substring(0, 11)
						: "";
				String newValue = (auditField.getNewValue() != null && auditField.getNewValue().trim().length() > 0)
						? auditField.getNewValue().substring(0, 11)
						: "";
				if (!oldValue.equals(newValue)) {
					addInfo.append(auditField.getDescription()).append(": Old Value = ").append(oldValue).append(" , ");
					addInfo.append(" New Value = ").append(newValue).append(" . ");
				}
			}
		}
		auditVO.setAdditionalInformation(addInfo.substring(0, addInfo.length() - 2));
	}
	*/

	/** 
	* @author A-8527
	* @return
	* @throws SystemException
	*/
	public Collection<USPSPostalCalendarVO> validateFrmToDateRange(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) {
		log.debug(CLASS + " : " + "validateFrmToDateRange" + " Entering");
		return USPSPostalCalendar.validateFrmToDateRange(uSPSPostalCalendarFilterVO);
	}

	/** 
	* @author A-7371
	* @param uspsPostalCalendarFilterVO
	* @return
	* @throws SystemException
	*/
	public USPSPostalCalendarVO findInvoicPeriodDetails(USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO) {
		log.debug(CLASS + " : " + "validateFrmToDateRange" + " Entering");
		return USPSPostalCalendar.findInvoicPeriodDetails(uspsPostalCalendarFilterVO);
	}

	public MailboxIdVO findMailBoxId(MailboxIdVO mailboxIdVO)  {
		log.debug(CLASS + " : " + "findMailBoxId" + " Entering");
		MailBoxIdPk mailboxidpk = new MailBoxIdPk();
		LoginProfile logonAttributes = (LoginProfile) contextUtil.callerLoginProfile();
		MailBoxId mailboxId = null;
		mailboxidpk.setMailboxCode(mailboxIdVO.getMailboxID());
		mailboxidpk.setCompanyCode(mailboxIdVO.getCompanyCode());
		MailboxIdVO mailboxidVO = new MailboxIdVO();
		MailEventPK maileventPK = new MailEventPK();
		try {
			mailboxId = MailBoxId.find(mailboxidpk);
			mailboxidVO.setMailboxName(mailboxId.getMailboxDescription());
			mailboxidVO.setOwnerCode(mailboxId.getOwnerCode());
			mailboxidVO.setResditTriggerPeriod(mailboxId.getResditTriggerPeriod());
			if (MailConstantsVO.FLAG_YES.equals(mailboxId.getMsgEventLocationNeeded())) {
				mailboxidVO.setMsgEventLocationNeeded(true);
			} else {
				mailboxidVO.setMsgEventLocationNeeded(false);
			}
			if (MailConstantsVO.FLAG_YES.equals(mailboxId.getPartialResdit())) {
				mailboxidVO.setPartialResdit(true);
			} else {
				mailboxidVO.setPartialResdit(false);
			}
			mailboxidVO.setResditversion(mailboxId.getResditversion());
			mailboxidVO.setMessagingEnabled(mailboxId.getMessagingEnabled());
			mailboxidVO.setMailboxStatus(mailboxId.getMailboxStatus());
			mailboxidVO.setMailboxOwner(mailboxId.getMailboxOwner());
			mailboxidVO.setRemarks(mailboxId.getRemarks());
			mailboxidVO.setMailboxID(mailboxId.getMailboxCode());
			mailboxidVO.setCompanyCode(mailboxId.getCompanyCode());
			if (("P").equals(mailboxidVO.getMessagingEnabled())) {
				maileventPK.setCompanyCode(mailboxIdVO.getCompanyCode());
				maileventPK.setMailboxId(mailboxIdVO.getMailboxID());
				mailboxidVO.setMailEventVOs(constructDAO().findMailEvent(maileventPK));
			}
		} catch (FinderException finderException) {
			mailboxidVO = null;
		}
		log.debug(CLASS + " : " + "findMailBoxId" + " Exiting");
		return mailboxidVO;
	}

	public void saveMailboxId(MailboxIdVO mailboxIdVO) {
		log.debug(CLASS + " : " + "saveMailboxId" + " Entering");
		try {
			MailBoxId mailboxid = null;
			mailboxid = MailBoxId.find(mailboxIdVO.getCompanyCode(), mailboxIdVO.getMailboxID());
			mailboxid.update(mailboxIdVO);
		} catch (FinderException finderException) {
			new MailBoxId(mailboxIdVO);
		}
		log.debug(CLASS + " : " + "saveMailboxId" + " Exiting");
	}

	public boolean isValidDestForCarditMissingDomesticMailbag(String airportCode) {
		boolean isDestForCdtMissingDomMail = false;
		String destForCdtMissingDomMail = null;
		try {
			destForCdtMissingDomMail = findSystemParameterValue(DEST_FOR_CDT_MISSING_DOM_MAL);
		} finally {
		}
		if (destForCdtMissingDomMail != null && !"NA".equals(destForCdtMissingDomMail) && airportCode != null
				&& destForCdtMissingDomMail.equals(airportCode)) {
			isDestForCdtMissingDomMail = true;
		}
		return isDestForCdtMissingDomMail;
	}

	public Quantity calculateActualWeightForZeroWeightMailbags(MailbagVO mailbagVO) {
		Quantity actualWeight = null;
		//TODO: Neo display unit hardcoding to be corrected
		String overwriteValue = findSystemParameterValue("mailtracking.mra.defaults.overwriteweightvalue");
		if (mailbagVO.getWeight() != null && mailbagVO.getMailbagId().length() == 29
				&& Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29)) == 0 && overwriteValue != null) {
			if (mailbagVO.getWeight().getDisplayUnit().equals(overwriteValue.split(",")[0].split("=")[0])) {
				actualWeight = quantities.getQuantity(Quantities.WEIGHT,
						BigDecimal.valueOf(Double.parseDouble(overwriteValue.split(",")[0].split("=")[1])),
						BigDecimal.valueOf(0.0), "K");
			}
			if (mailbagVO.getWeight().getDisplayUnit().equals(overwriteValue.split(",")[1].split("=")[0])) {
				actualWeight = quantities.getQuantity(Quantities.WEIGHT,
						BigDecimal.valueOf(Double.parseDouble(overwriteValue.split(",")[1].split("=")[1])),
						BigDecimal.valueOf(0.0), "K");
			}
		}
		if (mailbagVO.getWeight() != null && mailbagVO.getMailbagId().length() == 12
				&& Double.parseDouble(mailbagVO.getMailbagId().substring(10, 12)) == 0 && overwriteValue != null) {
			if (mailbagVO.getWeight().getDisplayUnit().equals(overwriteValue.split(",")[0].split("=")[0])) {
				actualWeight = quantities.getQuantity(Quantities.MAIL_WGT,
						BigDecimal.valueOf(Double.parseDouble(overwriteValue.split(",")[0].split("=")[1])));
			}
			if (mailbagVO.getWeight().getDisplayUnit().equals(overwriteValue.split(",")[1].split("=")[0])) {
				actualWeight = quantities.getQuantity(Quantities.MAIL_WGT,
						BigDecimal.valueOf(Double.parseDouble(overwriteValue.split(",")[1].split("=")[1])));
			}
		}
		return actualWeight;
	}

	public String findMailboxIdFromConfig(MailbagVO mailbagVO) {
		return MailMessageConfiguration.findMailboxIdFromConfig(mailbagVO);
	}

	/** 
	* @param mailMasterDataFilterVO
	* @throws SystemException
	* @throws RemoteException
	* @author 204082Added for IASCB-159276 on 27-Sep-2022
	*/
	public void publishMasterDataForMail(MailMasterDataFilterVO mailMasterDataFilterVO) {
		log.debug(MODULE + " : " + "publishMasterDataForMail" + " Entering");
		String masterType = mailMasterDataFilterVO.getMasterType();
		List<String> masterTypes = Arrays.asList(masterType.split(","));
		if (masterTypes.contains(MAIL_MASTER_DATA_TYPE_PACOD)) {
			publishGPADetails(mailMasterDataFilterVO.getCompanyCode());
		}
		//TODO: Neo to correct below code- refer classic
//		if (masterTypes.contains(MAIL_MASTER_DATA_TYPE_MALBAGINF)) {
//			publishMailbagDetails(mailMasterDataFilterVO);
//		}
		if (masterTypes.contains(MAIL_MASTER_DATA_TYPE_SUBCLS)) {
			publishSubclassDetails(mailMasterDataFilterVO.getCompanyCode());
		}
		if (masterTypes.contains(MAIL_MASTER_DATA_TYPE_EXCHANGE_OFFICE)) {
			publishExchangeOfficeDetails(mailMasterDataFilterVO.getCompanyCode());
		}
		log.debug(MODULE + " : " + "publishMasterDataForMail" + " Exiting");
	}

	/** 
	* @param companyCode
	* @throws SystemException
	* @throws PersistenceException
	* @author 204082Added for IASCB-159276 on 27-Sep-2022
	*/
	void publishGPADetails(String companyCode) {
		log.debug(MODULE + " : " + "publishGPADetails" + " Entering");
		//TODO: check this mapping of classic and neo VOs
		Collection<PostalAdministrationVO> paDetails = getPADetails(companyCode);
		Collection<com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO> postalAdministrationVOS =
				mailTrackingDefaultsMapper.postalAdministrationVOSNeoTopostalAdministrationVOSClassic(paDetails);
		PostalAdministrationMessageVO postalAdministrationMessageVO = new PostalAdministrationMessageVO();
		postalAdministrationMessageVO.setCompanyCode(companyCode);
		postalAdministrationMessageVO.setMessageType("MAILMASTERDATA_GPO");
		postalAdministrationMessageVO.setMessageStandard(MessageConfigConstants.TXN_PUBLISH);
		postalAdministrationMessageVO.setPaDetails(postalAdministrationVOS);
		try {
			msgBrokerMessageProxy.encodeAndSaveMessageAsync(postalAdministrationMessageVO);
		} catch (SystemException exception) {
			log.info("Exception :", exception);
		}
		log.debug(MODULE + " : " + "publishGPADetails" + " Exiting");
	}

	/** 
	* @param companyCode
	* @return PostalAdministrationVO
	* @throws SystemException
	* @throws PersistenceException
	* @author 204082Added for IASCB-159276 on 27-Sep-2022
	*/
	Collection<PostalAdministrationVO> getPADetails(String companyCode) {
		log.debug(MODULE + " : " + "getPADetails" + " Entering");
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		try {
			postalAdministrationVOs = constructDAO().getPADetails(companyCode);
		}catch (PersistenceException ex){

		}
		return postalAdministrationVOs;
	}

	/** 
	* @param mailMasterDataFilterVO
	* @throws SystemException
	* @author 204082Added for IASCB-159267 on 20-Oct-2022
	*/

	/** 
	* @param mailMasterDataFilterVO
	* @return MailbagDetailsVO
	* @throws SystemException
	* @author 204082Added for IASCB-159276 on 20-Oct-2022
	*/
	Collection<MailbagDetailsVO> getMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO) {

		Collection<MailbagDetailsVO>  mailbagDetailsVOS= null;
		try{
			mailbagDetailsVOS = constructDAO().getMailbagDetails(mailMasterDataFilterVO);
		}catch (PersistenceException ex){

		}
		return mailbagDetailsVOS;
	}

	/** 
	* @param companyCode
	* @throws SystemException
	* @author 204083Added for IASCB-172488
	*/
	void publishExchangeOfficeDetails(String companyCode) {
		log.debug(MODULE + " : " + "publishExchangeOfficeDetails" + " Entering");
		Collection<OfficeOfExchangeVO> officeOfExchangeDetails = getOfficeOfExchangeDetails(companyCode);
		Collection<com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO> officeOfExchangeVOS =
				mailTrackingDefaultsMapper.officeOfExchangeVOSNeoToofficeOfExchangeVOSClassic(officeOfExchangeDetails);
		OfficeOfExchangeMessageVO officeOfExchangeMessageVO = new OfficeOfExchangeMessageVO();
		officeOfExchangeMessageVO.setCompanyCode(companyCode);
		officeOfExchangeMessageVO.setMessageType("EXCHANGEOFFICE");
		officeOfExchangeMessageVO.setMessageStandard(PUBLISH);


		officeOfExchangeMessageVO.setOfficeOfExchangeDetails(officeOfExchangeVOS);
		try {
			msgBrokerMessageProxy.encodeAndSaveMessageAsync(officeOfExchangeMessageVO);
		} catch (SystemException systemException ) {
			log.info("Exception :", systemException);
		}
		log.debug(MODULE + " : " + "publishExchangeOfficeDetails" + " Exiting");
	}

	/** 
	* @param companyCode
	* @return OfficeOfExchangeVO
	* @throws SystemException
	* @throws PersistenceException
	* @author 204083Added for IASCB-172488
	*/
	public Collection<OfficeOfExchangeVO> getOfficeOfExchangeDetails(String companyCode) {
		log.debug(MODULE + " : " + "getOfficeOfExchangeDetails" + " Entering");
		Collection<OfficeOfExchangeVO> officeOfExchangeVOS = null;
		try {
			officeOfExchangeVOS = constructDAO().getOfficeOfExchangeDetails(companyCode);
		}catch(PersistenceException ex){

		}
		return officeOfExchangeVOS;
	}

//	public void updateInterfaceFlag(Collection<MailbagVO> mailBags, String interfaceFlag) {
//		for (MailbagVO mailbagVO : mailBags) {
//			MailbagPK mailbagPk = new MailbagPK();
//			mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
//			mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
//			Mailbag mailbag = null;
//			try {
//				mailbag = Mailbag.find(mailbagPk);
//			} catch (FinderException ex) {
//				throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
//			}
//			if (mailbag != null) {
//				mailbag.setIntFlg(interfaceFlag);
//			}
//		}
//	}

	/** 
	* @author 204084Added as part of CRQ IASCB-164529
	* @param destinationAirportCode
	* @return
	* @throws SystemException
	*/
	public Collection<RoutingIndexVO> getPlannedRoutingIndexDetails(String destinationAirportCode) {
		return constructDAO().getPlannedRoutingIndexDetails(destinationAirportCode);
	}

	/** 
	* @param companyCode
	* @throws SystemException
	* @author 204084Added for IASCB-172483 on 15-Oct-2022
	*/
	void publishSubclassDetails(String companyCode) {
		log.debug(MODULE + " : " + "publishSubclassDetails" + " Entering");
		Collection<MailSubClassVO> subclassDetails = getSubclassDetails(companyCode);
		Collection<com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO> subClassVOS =
				mailTrackingDefaultsMapper.mailSubClassVOsNeoToMailSubClassVOsClassic(subclassDetails);
		MailSubClassMessageVO mailSubClassMessageVO = new MailSubClassMessageVO();
		mailSubClassMessageVO.setCompanyCode(companyCode);
		mailSubClassMessageVO.setMessageType("MAILSUBCLSDATA");
		mailSubClassMessageVO.setMessageStandard(MessageConfigConstants.TXN_PUBLISH);
		mailSubClassMessageVO.setSubclassDetails(subClassVOS);
		try {
			msgBrokerMessageProxy.encodeAndSaveMessageAsync(mailSubClassMessageVO);
		} catch (SystemException systemException) {
			log.info("Exception :", systemException);
		}
		log.debug(MODULE + " : " + "publishSubclassDetails" + " Exiting");
	}

	/** 
	* @param companyCode
	* @return MailSubClassVO
	* @throws SystemException
	* @author 204084Added for IASCB-172483 on 15-Oct-2022
	*/
	Collection<MailSubClassVO> getSubclassDetails(String companyCode) {
		log.debug(MODULE + " : " + "getSubclassDetails" + " Entering");

		Collection<MailSubClassVO> mailSubClassVOS = null;
		try{
			mailSubClassVOS = constructDAO().getSubclassDetails(companyCode);

		}catch(PersistenceException e){

		}
		return mailSubClassVOS;
	}

	/** 
	* @param companyCode
	* @param airportCode
	* @return OfficeOfExchangeVO
	* @throws RemoteException
	* @author 204082Added for IASCB-164537 on 09-Nov-2022
	*/
	public Collection<OfficeOfExchangeVO> getExchangeOfficeDetails(String companyCode, String airportCode) {
		log.debug(MODULE + " : " + "getExchangeOfficeDetails" + " Entering");
		return constructDAO().getExchangeOfficeDetails(companyCode, airportCode);
	}

	/** 
	* @author U-1532
	* @param companyCode
	* @param paCode
	* @returns
	* @throws SystemException
	*/
	public String findDensityfactorForPA(String companyCode, String paCode) {
		log.debug(CLASS + " : " + "findPartyIdentifierForPA" + " Entering");

		return findDensityfactorForPA(companyCode, paCode);
	}

	public Collection<MLDConfigurationVO> findMLDConfigurations(
			MLDConfigurationFilterVO mLDConfigurationFilterVO)
			throws SystemException {
		log.debug(MODULE + " : " + "findMLDConfigurations" + " Entering");
		//Added as part of Bug ICRD-143797 by A-5526 starts
		if(StringUtils.isNotBlank(mLDConfigurationFilterVO.getCarrierCode())){
			mLDConfigurationFilterVO.setCarrierIdentifier(findCarrierIdentifier(mLDConfigurationFilterVO.getCarrierCode()));
		}
		//Added as part of Bug ICRD-143797 by A-5526 ends
		Collection<MLDConfigurationVO> mLDConfigurationVOs = MLDConfiguration
				.findMLDCongfigurations(mLDConfigurationFilterVO);
		//Added as part of Bug ICRD-143797 by A-5526 starts

		for(MLDConfigurationVO mLDConfigurationVO:mLDConfigurationVOs){
			AirlineModel airlineModel = new AirlineModel();
			airlineModel.setAirlineIdentifier(mLDConfigurationVO.getCarrierIdentifier());
			List<AirlineModel> airlineModels = airlineWebComponent.findAirlineValidityDetails(List.of(airlineModel));
			Optional<AirlineModel> airlineModelOptional = airlineModels.parallelStream().filter(airline ->
							airline.getAirlineIdentifier()==mLDConfigurationVO.getCarrierIdentifier())
					.findFirst();
			if(airlineModelOptional.isPresent()){
				mLDConfigurationVO.setCarrierCode(airlineModelOptional.get().getAlphaCode());
			}
		}
		//Added as part of Bug ICRD-143797 by A-5526 ends
		log.debug(MODULE + " : " + "findMLDConfigurations" + " Exiting");
		return mLDConfigurationVOs;

	}

	/**
	 * Method : MLDController.saveMLDConfiguarions Added by : A-5526 on Dec 16,
	 * 2015 Used for : Save MLD Configurations Parameters : @param
	 * mLDConfigurationVOs Parameters : @throws SystemException Return type :
	 * void
	 *
	 * @throws SystemException
	 * @throws MailTrackingBusinessException
	 */
	public void saveMLDConfigurations(
			Collection<MLDConfigurationVO> mLDConfigurationVOs)
			throws SystemException, MailTrackingBusinessException {
		log.debug(CLASS + " : " + "saveMLDConfigurations" + " Entering");
		log.debug("mLDConfigurationVOs for save {}", mLDConfigurationVOs);

		for (MLDConfigurationVO mLDConfigurationVO : mLDConfigurationVOs) {
			MLDConfiguration mLDConfiguration = null;
			MLDConfigurationPK mLDConfigurationPK = new MLDConfigurationPK();
			mLDConfigurationPK.setCompanyCode(mLDConfigurationVO
					.getCompanyCode());
			mLDConfigurationPK.setAirportCode(mLDConfigurationVO
					.getAirportCode());
			mLDConfigurationPK.setCarrierIdentifier(mLDConfigurationVO
					.getCarrierIdentifier());
			try {
				mLDConfiguration = MLDConfiguration.find(mLDConfigurationPK);
			} catch (FinderException e) {
				new MLDConfiguration(mLDConfigurationVO);
			}

			//Added mLDConfiguration null check as part of Bug ICRD-143797 by A-5526
			if(mLDConfiguration!=null){
				if (MailConstantsVO.OPERATION_FLAG_DELETE
						.equals(mLDConfigurationVO.getOperationFlag())) {
					mLDConfiguration.remove();
				} else {

					if (MailConstantsVO.OPERATION_FLAG_INSERT
							.equals(mLDConfigurationVO.getOperationFlag())
							&& mLDConfiguration != null) {
						throw new MailTrackingBusinessException(
								DUPLICATE_MLD_CONFIGURATION,
								new Object[] { mLDConfigurationVO.getCarrierCode(),
										mLDConfigurationVO.getAirportCode() });
					} else {
						log.debug("mLDConfiguration is already present {}",
								mLDConfiguration);
						mLDConfiguration.setAllocationRequired(mLDConfigurationVO
								.getAllocatedRequired());
						//Removed carrier code setting as part of Bug ICRD-143797 by A-5526
						mLDConfiguration.setDeliveredRequired(mLDConfigurationVO
								.getDeliveredRequired());
						mLDConfiguration.setHndRequired(mLDConfigurationVO
								.gethNDRequired());
						mLDConfiguration.setReceivedRequired(mLDConfigurationVO
								.getReceivedRequired());
						mLDConfiguration.setUpliftedRequired(mLDConfigurationVO
								.getUpliftedRequired());
						//Added for CRQ ICRD-135130 by A-8061 starts
						mLDConfiguration.setMldversion(mLDConfigurationVO.getMldversion());
						mLDConfiguration.setStagedRequired(mLDConfigurationVO.getStagedRequired());
						mLDConfiguration.setNestedRequired(mLDConfigurationVO.getNestedRequired());
						mLDConfiguration.setReceivedFromFightRequired(mLDConfigurationVO.getReceivedFromFightRequired());
						mLDConfiguration.setTransferredFromOALRequired(mLDConfigurationVO.getTransferredFromOALRequired());
						mLDConfiguration.setReceivedFromOALRequired(mLDConfigurationVO.getReceivedFromOALRequired());
						mLDConfiguration.setReturnedRequired(mLDConfigurationVO.getReturnedRequired());
						//Added for CRQ ICRD-135130 by A-8061 end
					}

				}
			}

		}
		log.debug(CLASS + " : " + "saveMLDConfigurations" + " Exiting");

	}

	private int findCarrierIdentifier(String carrierCode) {
		int carrierIdentifier = 0;
		try {
			List<AirlineModel> airlineModels = validateAlphaCode(List.of(carrierCode));
			Optional<AirlineModel> airlineModel = airlineModels.stream().findFirst();
			if(airlineModel.isPresent()){
				carrierIdentifier = airlineModel.get().getAirlineIdentifier();
			}

		} catch (BusinessException e) {
			log.debug("" + "BusinessException are" + " " + e);
		}
		return carrierIdentifier;
	}





	@Cacheable(
			value = {"mailboxidCache"},
			key = "#mailboxId"
	)
	public String findPAForMailboxID(String companyCode, String mailboxId, String originOE) {
		log.debug(CLASS + " : " + "findPAForMailboxID" + " Entering");
		return OfficeOfExchange.findPAForMailboxID(companyCode, mailboxId,originOE);
	}   
	@Cacheable(
			value = {"airportForOfficeOfExchangeCache"},
			key = "#officeOfExchanges"
	)
	public Map<String, String> findAirportForOfficeOfExchange(String companyCode, Collection<String> officeOfExchanges) {
		log.debug(CLASS + " : " + "findOfficeOfExchangeForAirports" + " Entering");
		return OfficeOfExchange.findAirportForOfficeOfExchange(companyCode, officeOfExchanges);
	}
	public String findUpuCodeNameForPA(String companyCode, String paCode) throws PersistenceException {
		log.debug(CLASS + " : " + "findUpuCodeNameForPA" + " Entering");
		return PostalAdministration.findUpuCodeNameForPA(companyCode,paCode);
	}

	public String findOfficeOfExchangeDescription(String companyCode, String exchangeCode) {
		log.debug(CLASS + " : " + "findOfficeOfExchangeDescription" + " Entering");
		OfficeOfExchange officeOfExchange = null;
		try {
			officeOfExchange =	OfficeOfExchange.find(companyCode,exchangeCode);
		} catch (FinderException e) {
			throw new RuntimeException(e);
		}
		if (officeOfExchange != null) {
			return officeOfExchange.getCodeDescription();
		} else {
			return null;
		}
	}
	@Cacheable(
			value = {"mailEventCache"},
			key = "#mailEventVO.getMailboxId()"
	)
	public Collection<MailEventVO>  findMailEvent(MailEventVO mailEventVO) {
		log.debug(CLASS + " : " + "findPAForMailboxID" + " Entering");
		return MailEvent.findMailEvent(mailEventVO.getCompanyCode(),mailEventVO.getMailboxId());
	}
	@Cacheable(
			value = {"postalAdministrationPartyIdentifierCache"},
			key = "#paCode"
	)
	public String findPartyIdentifierForPA(String companyCode, String paCode) {
		log.debug(CLASS + " : " + "findPartyIdentifierForPA" + " Entering");
		String partyIdentifier =   PostalAdministration.findPartyIdentifierForPA(companyCode, paCode);
		return partyIdentifier;
	}

	   public String getMLDVersion(int carrierIdentifier,String companyCode,String airportCode) throws SystemException{
		String mldVersion="";
		MLDConfiguration mLDConfiguration = null;
		MLDConfigurationPK mLDConfigurationPK = new MLDConfigurationPK();
		mLDConfigurationPK.setCompanyCode(companyCode);
		mLDConfigurationPK.setAirportCode(airportCode);
		mLDConfigurationPK.setCarrierIdentifier(carrierIdentifier);
		try {
			mLDConfiguration = MLDConfiguration.find(mLDConfigurationPK);
			if(mLDConfiguration!=null){
				mldVersion=mLDConfiguration.getMldversion();
			}

		} catch (FinderException e) {

			log.debug( "Finder Exception Caught");
		}
		return mldVersion;
	}
}