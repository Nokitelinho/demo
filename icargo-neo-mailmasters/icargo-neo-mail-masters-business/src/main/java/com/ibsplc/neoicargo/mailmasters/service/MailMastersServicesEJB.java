package com.ibsplc.neoicargo.mailmasters.service;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.lang.notation.BusinessService;
import com.ibsplc.neoicargo.mailmasters.component.MailController;
import com.ibsplc.neoicargo.mailmasters.exception.InvalidPartnerException;
import com.ibsplc.neoicargo.mailmasters.exception.MailSubClassException;
import com.ibsplc.neoicargo.mailmasters.exception.MailTrackingBusinessException;
import com.ibsplc.neoicargo.mailmasters.exception.OfficeOfExchangeException;
import com.ibsplc.neoicargo.mailmasters.mapper.MailTrackingDefaultsMapper;
import com.ibsplc.neoicargo.mailmasters.model.*;
import com.ibsplc.neoicargo.mailmasters.vo.*;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** 
 * @author
 * @ejb.bean description= MODULEdisplay-name= MODULE jndi-name= "com.ibsplc.icargo.services.mail.operations.MailTrackingDefaultsServices" name="MailTrackingDefaultsServices" type="Stateless" view-type="remote" remote-business-interface= "com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI"
 * @ejb.transaction type="Supports" Bean implementation class for EnterpriseBean: MailTrackingDefaultsServices
 */
@BusinessService
@Slf4j
@Component
public class MailMastersServicesEJB {
	@Autowired
	private MailTrackingDefaultsMapper mailTrackingDefaultsMapper;
	@Autowired
	private MailController mailController;
	private static final String MODULE = "MailTrackingDefaultsServicesEJB";
	private static final String MAILTRACKINGDEFAULTSEJB = "MailTrackingDefaultsEJB";
	private static final String MAIL_OPERATION_SERVICES = "mailOperationsFlowServices";

	/**
	* @author A-2037 Method for OfficeOfExchangeLOV containing code anddescription
	*/
	public Page<OfficeOfExchangeModel> findOfficeOfExchangeLov(OfficeOfExchangeModel officeofExchangeModel,
															   int pageNumber, int defaultSize) {
		OfficeOfExchangeVO officeofExchangeVO = mailTrackingDefaultsMapper
				.officeOfExchangeModelToOfficeOfExchangeVO(officeofExchangeModel);
		log.debug(MODULE + " : " + "findOfficeOfExchangeLov" + " Entering");
		Page<OfficeOfExchangeVO> officeOfExchangeVO=mailController.findOfficeOfExchangeLov(officeofExchangeVO, pageNumber, defaultSize);
		List<OfficeOfExchangeModel> officeOfExchangeModes= new ArrayList<>();
		officeOfExchangeModes.addAll(mailTrackingDefaultsMapper.officeOfExchangeVOsToOfficeOfExchangeModel(
				officeOfExchangeVO));
		return new Page(officeOfExchangeModes,officeOfExchangeVO.getPageNumber(), officeOfExchangeVO.getDefaultPageSize(), officeOfExchangeVO.getActualPageSize(), officeOfExchangeVO.getStartIndex(), officeOfExchangeVO.getEndIndex(), officeOfExchangeVO.hasNextPage(), officeOfExchangeVO.getTotalRecordCount());
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
	public Page<MailSubClassModel> findMailSubClassCodeLov(String companyCode, String code, String description,
														   int pageNumber, int defaultSize) {
		Page<MailSubClassVO> subclassvos = mailController.findMailSubClassCodeLov(companyCode, code, description, pageNumber, defaultSize);
		List<MailSubClassModel> mailSubClassModels = new ArrayList<MailSubClassModel>();
		mailSubClassModels.addAll(mailTrackingDefaultsMapper.mailSubClassVOsToMailSubClassModel(
				subclassvos));
		return new Page(mailSubClassModels, subclassvos.getPageNumber(), subclassvos.getDefaultPageSize(), subclassvos.getActualPageSize(), subclassvos.getStartIndex(), subclassvos.getEndIndex(), subclassvos.hasNextPage(), subclassvos.getTotalRecordCount());
	}
	/** 
	* @param companyCode
	* @param paCode
	* @return PostalAdministrationVO
	* @throws SystemException
	* @author A-2037 This method is used to find Postal Administration CodeDetails
	*/
	public PostalAdministrationModel findPACode(String companyCode, String paCode) {
		log.debug(MODULE + " : " + "findPACode" + " Entering");
		return mailTrackingDefaultsMapper
				.postalAdministrationVOToPostalAdministrationModel(mailController.findPACode(companyCode, paCode));
	}

	/** 
	* @param companyCode
	* @return Collection<PartnerCarrierVO>
	* @throws SystemException
	* @author a-1876 This method is used to list the PartnerCarriers..
	* refer findAllPartnerCarriers */
	public Collection<PartnerCarrierModel> findAllPartnerCarriers(String companyCode, String ownCarrierCode,
																  String airportCode) {
		log.debug(MODULE + " : " + "findAllPartnerCarriers" + " Entering");
		return mailTrackingDefaultsMapper.partnerCarrierVOsToPartnerCarrierModel(
				mailController.findAllPartnerCarriers(companyCode, ownCarrierCode, airportCode));
	}

	public Collection<CoTerminusModel> findAllCoTerminusAirports(CoTerminusFilterModel filterModel) {
		CoTerminusFilterVO filterVO = mailTrackingDefaultsMapper.coTerminusFilterModelToCoTerminusFilterVO(filterModel);
		log.debug(MODULE + " : " + "findAllCoTerminusAirports" + " Entering");
		return mailTrackingDefaultsMapper
				.coTerminusVOsToCoTerminusModel(mailController.findAllCoTerminusAirports(filterVO));
	}

	public Page<MailServiceStandardModel> listServiceStandardDetails(
			MailServiceStandardFilterModel mailServiceStandardFilterModel, int pageNumber) {
		MailServiceStandardFilterVO mailServiceStandardFilterVO = mailTrackingDefaultsMapper
				.mailServiceStandardFilterModelToMailServiceStandardFilterVO(mailServiceStandardFilterModel);
		Page<MailServiceStandardVO> mailServiceStandardVO = mailController.listServiceStandardDetails(mailServiceStandardFilterVO, pageNumber);
		log.debug(MODULE + " : " + "listServiceStandardDetails" + " Entering");
		List<MailServiceStandardModel> mailServiceStandardModels=new ArrayList<>();
		if(mailTrackingDefaultsMapper.mailServiceStandardVOsToMailServiceStandardModel(
				mailServiceStandardVO)!=null){
		mailServiceStandardModels.addAll(mailTrackingDefaultsMapper.mailServiceStandardVOsToMailServiceStandardModel(
				mailServiceStandardVO));
		return new Page(mailServiceStandardModels, mailServiceStandardVO.getPageNumber(), mailServiceStandardVO.getDefaultPageSize(), mailServiceStandardVO.getActualPageSize(), mailServiceStandardVO.getStartIndex(), mailServiceStandardVO.getEndIndex(), mailServiceStandardVO.hasNextPage(), mailServiceStandardVO.getTotalRecordCount());
	}
		else{
			return mailTrackingDefaultsMapper.mailServiceStandardVOsToMailServiceStandardModel(
					mailController.listServiceStandardDetails(mailServiceStandardFilterVO, pageNumber));

		}
	}

	public Collection<MailRdtMasterModel> findRdtMasterDetails(RdtMasterFilterModel filterModel) {
		RdtMasterFilterVO filterVO = mailTrackingDefaultsMapper.rdtMasterFilterModelToRdtMasterFilterVO(filterModel);
		log.debug(MODULE + " : " + "findAllCoTerminusAirports" + " Entering");
		return mailTrackingDefaultsMapper
				.mailRdtMasterVOsToMailRdtMasterModel(mailController.findRdtMasterDetails(filterVO));
	}

	/** 
	* @return
	* @throws SystemException
	*/
	public OfficeOfExchangeModel validateOfficeOfExchange(String companyCode, String officeOfExchange) {
		log.debug("Ram MailTrackingDefaultsServicesEJB" + " : " + "validateOfficeOfExchange" + " Entering");
		return mailTrackingDefaultsMapper.officeOfExchangeVOToOfficeOfExchangeModel(
				mailController.validateOfficeOfExchange(companyCode, officeOfExchange));
	}

	/**
	 *
	 * @param postalAdministrationModel
	 * @throws MailTrackingBusinessException
	 */
	public void savePACode(PostalAdministrationModel postalAdministrationModel)
			throws SystemException {
		PostalAdministrationVO postalAdministrationVO = mailTrackingDefaultsMapper
				.postalAdministrationModelToPostalAdministrationVO(postalAdministrationModel);
		log.debug(MODULE + " : " + "savePACode" + " Entering");

			mailController.savePACode(postalAdministrationVO);

	}

	/** 
	* This method is used to save office of Exchange Code
	* @throws MailTrackingBusinessException
	*/

	public void saveOfficeOfExchange(Collection<OfficeOfExchangeModel> officeOfExchangesModel) throws BusinessException {
		Collection<OfficeOfExchangeVO> officeOfExchangeVOs = mailTrackingDefaultsMapper
				.officeOfExchangeModelsToOfficeOfExchangeVO(officeOfExchangesModel);
		log.debug(MODULE + " : " + "saveOfficeOfExchange" + " Entering");
		try {
			mailController.saveOfficeOfExchange(officeOfExchangeVOs);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getErrorCode());
		} catch (OfficeOfExchangeException ex) {
			throw new BusinessException(ex.getErrors());
		}
	}

	/**
	 * This method is used to save Mail sub class codes
	 * @throws MailTrackingBusinessException
	 */

	public void saveMailSubClassCodes(Collection<MailSubClassModel> mailSubClasssModel) throws SystemException,
			MailTrackingBusinessException {
		Collection<MailSubClassVO> mailSubClassVOs = mailTrackingDefaultsMapper
				.mailSubClassModelsToMailSubClassVO(mailSubClasssModel);
		log.debug(MODULE + " : " + "saveMailSubClassCodes" + " Entering");
		try {
			mailController.saveMailSubClassCodes(mailSubClassVOs);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getErrorCode());
		} catch (MailSubClassException ex) {
			throw new MailTrackingBusinessException(ex);
		}
	}

	/** 
	* @param companyCode
	* @param countryCode
	* @return Collection<PostalAdministrationVO>
	* @throws SystemException
	* @author A-2037 This method is used to find Local PAs
	*/
	public Collection<PostalAdministrationModel> findLocalPAs(String companyCode, String countryCode) {
		log.debug(MODULE + " : " + "findLocalPAs" + " Entering");
		return mailTrackingDefaultsMapper.postalAdministrationVOsToPostalAdministrationModel(
				mailController.findLocalPAs(companyCode, countryCode));
	}

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*/
	public String findPAForOfficeOfExchange(String companyCode, String officeOfExchange) {
		log.debug(MODULE + " : " + "findPAForOfficeOfExchange" + " Entering");
		return mailController.findPAForOfficeOfExchange(companyCode, officeOfExchange);
	}

	/** 
	* @param companyCode
	* @param subclassCode
	* @return Collection<MailSubClassVO>
	* @throws SystemException
	* @author a-2037 This method is used to find all the mail subclass codes
	*/
	public Collection<MailSubClassModel> findMailSubClassCodes(String companyCode, String subclassCode) {
		log.debug(MODULE + " : " + "findMailSubClassCodes" + " Entering");
		return mailTrackingDefaultsMapper
				.mailSubClassVOsToMailSubClassModel(mailController.findMailSubClassCodes(companyCode, subclassCode));
	}

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @param pageNumber
	* @author a-10552 This method is used to find findOfficeOfExchange
	*/
	public Page<OfficeOfExchangeModel> findOfficeOfExchange(String companyCode, String officeOfExchange,
			int pageNumber) {
		log.debug(MODULE + " : " + "findOfficeOfExchange" + " Entering");
		if(Objects.isNull(officeOfExchange)){
			return null;
		}
		Page<OfficeOfExchangeVO> OfficeOfExchangeVOs = mailController.findOfficeOfExchange(companyCode, officeOfExchange, pageNumber);
		if (Objects.nonNull(OfficeOfExchangeVOs) && OfficeOfExchangeVOs.size() > 0) {
			log.info("Office of exchanges {} {} {} {} {} {} {} {}",OfficeOfExchangeVOs, OfficeOfExchangeVOs.getPageNumber(),
					OfficeOfExchangeVOs.getDefaultPageSize(), OfficeOfExchangeVOs.getActualPageSize(),
					OfficeOfExchangeVOs.getStartIndex(), OfficeOfExchangeVOs.getEndIndex(),
					OfficeOfExchangeVOs.hasNextPage(), OfficeOfExchangeVOs.getTotalRecordCount());
			List<OfficeOfExchangeModel> officeOfExchangeModels = new ArrayList<>();
			for (OfficeOfExchangeVO officeOfExchangeVO : OfficeOfExchangeVOs) {
				officeOfExchangeModels.add(mailTrackingDefaultsMapper.officeOfExchangeVOToOfficeOfExchangeModel(officeOfExchangeVO));
			}
			return new Page(officeOfExchangeModels, OfficeOfExchangeVOs.getPageNumber(),
					OfficeOfExchangeVOs.getDefaultPageSize(), OfficeOfExchangeVOs.getActualPageSize(),
					OfficeOfExchangeVOs.getStartIndex(), OfficeOfExchangeVOs.getEndIndex(),
					OfficeOfExchangeVOs.hasNextPage(), OfficeOfExchangeVOs.getTotalRecordCount());
		} else {
			return mailTrackingDefaultsMapper.officeOfExchangeVOsToOfficeOfExchangeModel(
					mailController.findOfficeOfExchange(companyCode, officeOfExchange, pageNumber));
		}
	}

	/** 
	* @param companyCode
	* @param mailboxCode
	* @param pageNumber
	* @return
	* @throws SystemException
	* @author A-5931 Method for MailBoxIdLovVO containing Code and Description
	*/
	public Page<MailBoxIdLovModel> findMailBoxIdLov(String companyCode, String mailboxCode, String mailboxDesc,
			int pageNumber, int defaultSize) {
		log.debug(MODULE + " : " + "findMailBoxIdLov" + " Entering");
		Page<MailBoxIdLovVO> mailBoxIdLovVOS =
		mailController.findMailBoxIdLov(companyCode, mailboxCode, mailboxDesc, pageNumber, defaultSize);

		List<MailBoxIdLovModel> mailBoxIdLovModels = new ArrayList<>();

		for ( MailBoxIdLovVO mailBoxIdLovVO : mailBoxIdLovVOS ) {
			mailBoxIdLovModels.add(mailTrackingDefaultsMapper
					.mailBoxIdLovVOToMailBoxIdLovModel(mailBoxIdLovVO));
		}
		return new
				Page(mailBoxIdLovModels,mailBoxIdLovVOS.getPageNumber(),
				mailBoxIdLovVOS.getDefaultPageSize(),mailBoxIdLovVOS.getActualPageSize(),
				mailBoxIdLovVOS.getStartIndex(),mailBoxIdLovVOS.getEndIndex()
				, mailBoxIdLovVOS.hasNextPage(),mailBoxIdLovVOS.getTotalRecordCount());
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
	public Page<PostalAdministrationModel> findPALov(String companyCode, String paCode, String paName, int pageNumber,
			int defaultSize) {
	    log.debug(MODULE + " : " + "findPALov" + " Entering");
		Page<PostalAdministrationVO> postalAdministrationVOs =
				mailController.findPALov(companyCode, paCode, paName, pageNumber, defaultSize);

		List<PostalAdministrationModel> postalAdministrationModels = new ArrayList<>();

		for ( PostalAdministrationVO postalAdministrationVO : postalAdministrationVOs ) {
			postalAdministrationModels.add(mailTrackingDefaultsMapper
					.postalAdministrationVOToPostalAdministrationModel(postalAdministrationVO));
		}
		return new
				Page(postalAdministrationModels,postalAdministrationVOs.getPageNumber(),
				postalAdministrationVOs.getDefaultPageSize(),postalAdministrationVOs.getActualPageSize(),
				postalAdministrationVOs.getStartIndex(),postalAdministrationVOs.getEndIndex()
				, postalAdministrationVOs.hasNextPage(),postalAdministrationVOs.getTotalRecordCount());

	}

	/** 
	* @param companyCode
	* @param officeOfExchanges
	* @return
	* @throws SystemException
	* @author A-3227  - FEB 18, 2009
	*/
	public Collection<ArrayList<String>> findCityAndAirportForOE(String companyCode,
			Collection<String> officeOfExchanges) throws SystemException {
		log.debug(MODULE + " : " + "findCityAndAirportForOE" + " Entering");
		return mailController.findCityAndAirportForOE(companyCode, officeOfExchanges);
	}

	/** 
	* @param companyCode
	* @param airportCode
	* @return
	* @throws SystemException Added for icrd-95515
	* @author A-4810
	*/
	public Collection<String> findOfficeOfExchangesForAirport(String companyCode, String airportCode) {
		log.debug(MODULE + " : " + "findCityAndAirportForOE" + " Entering");
		return mailController.findOfficeOfExchangesForAirport(companyCode, airportCode);
	}

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*/
	public PostalAdministrationModel findPADetails(String companyCode, String officeOfExchange) {
		log.debug(MODULE + " : " + "findPADetails" + " Entering");
		PostalAdministrationVO postalAdministrationVO = mailController.findPADetails(companyCode, officeOfExchange);
		log.debug(MODULE + " : " + "findPADetails" + " Exiting");
		return mailTrackingDefaultsMapper.postalAdministrationVOToPostalAdministrationModel(postalAdministrationVO);
	}

	/** 
	* @param companyCode
	* @param subclass
	* @return
	* @throws SystemException
	* @author A-3227 Reno K Abraham
	*/
	public boolean validateMailSubClass(String companyCode, String subclass) {
		log.debug("MailTracking Defaults Services EJB" + " : " + "validateMailSubClass" + " Entering");
		return mailController.validateMailSubClass(companyCode, subclass);
	}

	/**
	 *
	 * @param postalAdministrationDetailsModel
	 * @return
	 */
	public PostalAdministrationDetailsModel validatePoaDetails(
			PostalAdministrationDetailsModel postalAdministrationDetailsModel) {
		PostalAdministrationDetailsVO postalAdministrationDetailsVO = mailTrackingDefaultsMapper
				.postalAdministrationDetailsModelToPostalAdministrationDetailsVO(postalAdministrationDetailsModel);
		return mailTrackingDefaultsMapper.postalAdministrationDetailsVOToPostalAdministrationDetailsModel(
				mailController.validatePoaDetails(postalAdministrationDetailsVO));
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#findAllPACodes(com.ibsplc.icargo.business.mailtracking.mra.gpabilling.vo.GenerateInvoiceFilterVO) Added by 			: A-4809 on 08-Jan-2014 Used for 	: Parameters	:	@param generateInvoiceFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public Collection<PostalAdministrationModel> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO) {
		log.debug(MODULE + " : " + "findAllPACodes" + " Entering");
		return mailTrackingDefaultsMapper.postalAdministrationVOsToPostalAdministrationModel(
				mailController.findAllPACodes(generateInvoiceFilterVO));
	}

	/** 
	*/
	public void saveMLDConfigurations(Collection<MLDConfigurationModel> mLDConfigurationsModel) throws BusinessException {
		Collection<MLDConfigurationVO> mLDConfigurationVOs = mailTrackingDefaultsMapper
				.mLDConfigurationModelsToMLDConfigurationVO(mLDConfigurationsModel);
		log.debug(MODULE + " : " + "saveMLDConfiguarions" + " Entering");
		try {
			mailController.saveMLDConfigurations(mLDConfigurationVOs);
		} catch (MailTrackingBusinessException e) {
			throw new BusinessException(e.getErrors());
		}
	}

	/** 
	*/
	public Collection<MLDConfigurationModel> findMLDConfigurations(
			MLDConfigurationFilterModel mLDConfigurationFilterModel) {
		MLDConfigurationFilterVO mLDConfigurationFilterVO = mailTrackingDefaultsMapper
				.mLDConfigurationFilterModelToMLDConfigurationFilterVO(mLDConfigurationFilterModel);
		log.debug(MODULE + " : " + "findMLDConfigurations" + " Entering");
		return mailTrackingDefaultsMapper.mLDConfigurationVOsToMLDConfigurationModel(
				mailController.findMLDConfigurations(mLDConfigurationFilterVO));
	}

	/** 
	* @throws MailTrackingBusinessException
	* @author A-1936 This method is used to save the PartnerCarriers..
	*/
	public void savePartnerCarriers(Collection<PartnerCarrierModel> partnerCarriersModel){
		Collection<PartnerCarrierVO> partnerCarrierVOs = mailTrackingDefaultsMapper
				.partnerCarrierModelsToPartnerCarrierVO(partnerCarriersModel);
		log.debug(MODULE + " : " + "savePartnerCarriers" + " Entering");
		try {
			mailController.savePartnerCarriers(partnerCarrierVOs);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		} catch (InvalidPartnerException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	public void saveCoterminusDetails(Collection<CoTerminusModel> coterminussModel){
		Collection<CoTerminusVO> coterminusVOs = mailTrackingDefaultsMapper
				.coTerminusModelsToCoTerminusVO(coterminussModel);
		log.debug(MODULE + " : " + "saveCoterminusDetails" + " Entering");
		try {
			mailController.saveCoterminusDetails(coterminusVOs);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	public void saveServiceStandardDetails(Collection<MailServiceStandardModel> mailServiceStandardsModel,
			Collection<MailServiceStandardModel> mailServiceStandardstodeleteModel) {
		Collection<MailServiceStandardVO> mailServiceStandardVOstodelete = mailTrackingDefaultsMapper
				.mailServiceStandardModelsToMailServiceStandardVO(mailServiceStandardstodeleteModel);
		Collection<MailServiceStandardVO> mailServiceStandardVOs = mailTrackingDefaultsMapper
				.mailServiceStandardModelsToMailServiceStandardVO(mailServiceStandardsModel);
		log.debug(MODULE + " : " + "saveServiceStandardDetails" + " Entering");
		try {
			mailController.saveServiceStandardDetails(mailServiceStandardVOs, mailServiceStandardVOstodelete);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	public void saveRdtMasterDetails(Collection<MailRdtMasterModel> mailRdtMastersModel){
		Collection<MailRdtMasterVO> mailRdtMasterVOs = mailTrackingDefaultsMapper
				.mailRdtMasterModelsToMailRdtMasterVO(mailRdtMastersModel);
		log.debug(MODULE + " : " + "saveCoterminusDetails" + " Entering");
		try {
			mailController.saveRdtMasterDetails(mailRdtMasterVOs);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	public Collection<ErrorVO> saveRdtMasterDetailsXls(Collection<MailRdtMasterModel> mailRdtMastersModel){
		Collection<MailRdtMasterVO> mailRdtMasterVOs = mailTrackingDefaultsMapper
				.mailRdtMasterModelsToMailRdtMasterVO(mailRdtMastersModel);
		log.debug(MODULE + " : " + "saveMailServiceLevelDtls" + " Entering");
		return mailController.saveRdtMasterDetailsXls(mailRdtMasterVOs);
	}

	public boolean validateCoterminusairports(String actualAirport, String eventAirport, String eventCode,
			String paCode, ZonedDateTime dspDate) {
		log.debug(MODULE + " : " + "validateCoterminusairports" + " Entering");
		return mailController.validateCoterminusairports(actualAirport, eventAirport, eventCode, paCode, dspDate);
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.findOfficeOfExchangeForPA Added by 	:	a-6245 on 10-Jul-2017 Used for 	: Parameters	:	@param companyCode Parameters	:	@param paCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	Map<String,String>
	*/
	public Map<String, String> findOfficeOfExchangeForPA(String companyCode, String paCode) {
		log.debug(MODULE + " : " + "findOfficeOfExchangeForAirports" + " Entering");
		return mailController.findOfficeOfExchangeForPA(companyCode, paCode);
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.findAgentCodeForPA Added by 	:	U-1267 on 07-Nov-2017 Used for 	:	ICRD-211205 Parameters	:	@param companyCode Parameters	:	@param paCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	String
	*/
	public String findAgentCodeForPA(String companyCode, String paCode) {
		log.debug(MODULE + " : " + "findAgentCodeForPA" + " Entering");
		return mailController.findAgentCodeForPA(companyCode, paCode);
	}

	/**
	* @author A-6986
	*/
	public Collection<ErrorVO> saveMailServiceLevelDtls(Collection<MailServiceLevelModel> mailServiceLevelsModel) {
		Collection<MailServiceLevelVO> mailServiceLevelVOs = mailTrackingDefaultsMapper
				.mailServiceLevelModelsToMailServiceLevelVO(mailServiceLevelsModel);
		log.debug(MODULE + " : " + "saveMailServiceLevelDtls" + " Entering");
		return mailController.saveMailServiceLevelDtls(mailServiceLevelVOs);
	}

	public Collection<USPSPostalCalendarModel> listPostalCalendarDetails(
			USPSPostalCalendarFilterModel listPostalCalendarDetailsModel) {
		USPSPostalCalendarFilterVO listPostalCalendarDetails = mailTrackingDefaultsMapper
				.uSPSPostalCalendarFilterModelToUSPSPostalCalendarFilterVO(listPostalCalendarDetailsModel);
		log.debug(MODULE + " : " + "saveMailServiceLevelDtls" + " Entering");
		return mailTrackingDefaultsMapper.uSPSPostalCalendarVOsToUSPSPostalCalendarModel(
				mailController.listPostalCalendarDetails(listPostalCalendarDetails));
	}

	public void savePostalCalendar(Collection<USPSPostalCalendarModel> uSPSPostalCalendarsModel) {
		Collection<USPSPostalCalendarVO> uSPSPostalCalendarVOs = mailTrackingDefaultsMapper
				.uSPSPostalCalendarModelsToUSPSPostalCalendarVO(uSPSPostalCalendarsModel);
		log.debug(MODULE + " : " + "savePostalCalendar" + " Entering");
		try {
			mailController.savePostalCalendar(uSPSPostalCalendarVOs);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	* @author A-6986
	*/
	public void saveMailHandoverDetails(Collection<MailHandoverModel> MailHandoversModel) {
		Collection<MailHandoverVO> MailHandoverVOs = mailTrackingDefaultsMapper
				.mailHandoverModelsToMailHandoverVO(MailHandoversModel);
		log.debug(MODULE + " : " + "saveMailHandoverDetails" + " Entering");
		mailController.saveMailHandoverDetails(MailHandoverVOs);
	}

	/**
	* @author A-6986
	*/
	public Page<MailHandoverModel> findMailHandoverDetails(MailHandoverFilterModel mailHandoverFilterModel,
			int pageNumber) {
		MailHandoverFilterVO mailHandoverFilterVO = mailTrackingDefaultsMapper
				.mailHandoverFilterModelToMailHandoverFilterVO(mailHandoverFilterModel);
		Page<MailHandoverVO> mailHandoverVO = mailController.findMailHandoverDetails(mailHandoverFilterVO, pageNumber);
		List<MailHandoverModel> mailHandoverModels = new ArrayList<>();
		if(mailTrackingDefaultsMapper.mailHandoverVOsToMailHandoverModel(
				mailHandoverVO)!=null) {
			mailHandoverModels.addAll(mailTrackingDefaultsMapper.mailHandoverVOsToMailHandoverModel(
					mailHandoverVO));
			return new Page(mailHandoverModels, mailHandoverVO.getPageNumber(), mailHandoverVO.getDefaultPageSize(), mailHandoverVO.getActualPageSize(), mailHandoverVO.getStartIndex(), mailHandoverVO.getEndIndex(), mailHandoverVO.hasNextPage(), mailHandoverVO.getTotalRecordCount());
		}
else{
			return mailTrackingDefaultsMapper.mailHandoverVOsToMailHandoverModel(
					mailController.findMailHandoverDetails(mailHandoverFilterVO, pageNumber));
		}
	}

	/**
	* @author A-6986
	*/
	public void saveContractDetails(Collection<GPAContractModel> gpaContractsModel) throws BusinessException {
		Collection<GPAContractVO> gpaContractVOs = mailTrackingDefaultsMapper
				.gPAContractModelsToGPAContractVO(gpaContractsModel);
		log.debug(MODULE + " : " + "saveMailHandoverDetails" + " Entering");

		mailController.saveContractDetails(gpaContractVOs);

	}

	/**
	* @author A-6986
	*/
	public Collection<GPAContractModel> listContractDetails(GPAContractFilterModel gpaContractFilterModel) {
		GPAContractFilterVO gpaContractFilterVO = mailTrackingDefaultsMapper
				.gPAContractFilterModelToGPAContractFilterVO(gpaContractFilterModel);
		return mailTrackingDefaultsMapper
				.gPAContractVOsToGPAContractModel(mailController.listContractDetails(gpaContractFilterVO));
	}

	/**
	* @author A-6986
	*/
	public Collection<IncentiveConfigurationModel> findIncentiveConfigurationDetails(
			IncentiveConfigurationFilterModel incentiveConfigurationFilterModel) {
		IncentiveConfigurationFilterVO incentiveConfigurationFilterVO = mailTrackingDefaultsMapper
				.incentiveConfigurationFilterModelToIncentiveConfigurationFilterVO(incentiveConfigurationFilterModel);
		log.debug(MODULE + " : " + "findIncentiveConfigurationDetails" + " Entering");
		return mailTrackingDefaultsMapper.incentiveConfigurationVOsToIncentiveConfigurationModel(
				mailController.findIncentiveConfigurationDetails(incentiveConfigurationFilterVO));
	}

	/**
	* @author A-6986
	*/
	public void saveIncentiveConfigurationDetails(Collection<IncentiveConfigurationModel> incentiveConfigurationsModel)
	{
		Collection<IncentiveConfigurationVO> incentiveConfigurationVOs = mailTrackingDefaultsMapper
				.incentiveConfigurationModelsToIncentiveConfigurationVO(incentiveConfigurationsModel);
		log.debug(MODULE + " : " + "saveIncentiveConfigurationDetails" + " Entering");

			mailController.saveIncentiveConfigurationDetails(incentiveConfigurationVOs);

	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.saveRoutingIndexDetails Added by 	:	A-7531 on 08-Oct-2018 Used for 	: Parameters	:	@param routingIndexVOs Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Parameters	:	@throws MailTrackingBusinessException Return type	: 	void
	* @throws FinderException
	*/
	public void saveRoutingIndexDetails(Collection<RoutingIndexModel> routingIndexsModel){
		Collection<RoutingIndexVO> routingIndexVOs = mailTrackingDefaultsMapper
				.routingIndexModelsToRoutingIndexVO(routingIndexsModel);
		log.debug(MODULE + " : " + "saveRoutingIndexDetails" + " Entering");
		mailController.saveRoutingIndexDetails(routingIndexVOs);
		log.debug(MODULE + " : " + "saveRoutingIndexDetails" + " Exiting");
	}

	public Collection<RoutingIndexModel> findRoutingIndex(RoutingIndexModel routingIndexModel)
			{
		RoutingIndexVO routingIndexVO = mailTrackingDefaultsMapper.routingIndexModelToRoutingIndexVO(routingIndexModel);
		log.debug(MODULE + " : " + "findRoutingIndex" + " Entering");
		return mailTrackingDefaultsMapper
				.routingIndexVOsToRoutingIndexModel(mailController.findRoutingIndex(routingIndexVO));
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#validateFrmToDateRange Added by 			: A-8527 on 07-Feb-2018 Used for 	: USPSPostalCalendarVO Parameters	:	@param USPSPostalCalendarFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public Collection<USPSPostalCalendarModel> validateFrmToDateRange(
			USPSPostalCalendarFilterModel uSPSPostalCalendarFilterModel) {
		USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO = mailTrackingDefaultsMapper
				.uSPSPostalCalendarFilterModelToUSPSPostalCalendarFilterVO(uSPSPostalCalendarFilterModel);
		log.debug(MODULE + " : " + "validateFrmToDateRange" + " Entering");
		return mailTrackingDefaultsMapper.uSPSPostalCalendarVOsToUSPSPostalCalendarModel(
				mailController.validateFrmToDateRange(uSPSPostalCalendarFilterVO));
	}

	public USPSPostalCalendarModel findInvoicPeriodDetails(
			USPSPostalCalendarFilterModel uspsPostalCalendarFilterModel) {
		USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO = mailTrackingDefaultsMapper
				.uSPSPostalCalendarFilterModelToUSPSPostalCalendarFilterVO(uspsPostalCalendarFilterModel);
		log.debug(MODULE + " : " + "findInvoicPeriodDetails" + " Entering");
		return mailTrackingDefaultsMapper.uSPSPostalCalendarVOToUSPSPostalCalendarModel(
				mailController.findInvoicPeriodDetails(uspsPostalCalendarFilterVO));
	}

	public MailboxIdModel findMailboxId(MailboxIdModel mailboxIdModel)  {
		MailboxIdVO mailboxIdVO = mailTrackingDefaultsMapper.mailboxIdModelToMailboxIdVO(mailboxIdModel);
		log.debug(MODULE + " : " + "findMailboxId" + " Entering");
		MailboxIdVO MailboxIdVO = null;
		MailboxIdVO = mailController.findMailBoxId(mailboxIdVO);
		return mailTrackingDefaultsMapper.mailboxIdVOToMailboxIdModel(MailboxIdVO);
	}

	public void saveMailboxId(MailboxIdModel mailboxIdModel)  {
		MailboxIdVO mailboxIdVO = mailTrackingDefaultsMapper.mailboxIdModelToMailboxIdVO(mailboxIdModel);
		log.debug(MODULE + " : " + "saveMailboxId" + " Entering");
		mailController.saveMailboxId(mailboxIdVO);
	}

	public void saveMailRuleConfiguration(MailRuleConfigModel mailRuleConfigModel)  {
		MailRuleConfigVO mailRuleConfigVO = mailTrackingDefaultsMapper
				.mailRuleConfigModelToMailRuleConfigVO(mailRuleConfigModel);
		log.debug(MODULE + " : " + "saveMailRuleConfiguration" + " Entering");
		mailController.saveMailRuleConfiguration(mailRuleConfigVO);
	}

	public String findMailboxIdFromConfig(MailbagModel mailbagModel) {
		MailbagVO mailbagVO = mailTrackingDefaultsMapper.mailbagModelToMailbagVO(mailbagModel);
		log.debug(MODULE + " : " + "findMailboxIdFromConfig" + " Entering");
		return mailController.findMailboxIdFromConfig(mailbagVO);
	}

	/**
	* @author 204082Added for IASCB-159276 on 27-Sep-2022
	*/
	public void publishMasterDataForMail(MailMasterDataFilterModel mailMasterDataFilterModel) {
		MailMasterDataFilterVO mailMasterDataFilterVO = mailTrackingDefaultsMapper
				.mailMasterDataFilterModelToMailMasterDataFilterVO(mailMasterDataFilterModel);
		log.debug(MODULE + " : " + "publishMasterDataForMail" + " Entering");
		mailController.publishMasterDataForMail(mailMasterDataFilterVO);
		log.debug(MODULE + " : " + "publishMasterDataForMail" + " Exiting");
	}

	public Collection<RoutingIndexModel> getPlannedRoutingIndexDetails(RoutingIndexModel routingIndexModel) {
		RoutingIndexVO routingIndexVO = mailTrackingDefaultsMapper.routingIndexModelToRoutingIndexVO(routingIndexModel);
		log.debug(MAIL_OPERATION_SERVICES + " : " + "getPlannedRoutingIndexDetails" + " Entering");
		return mailTrackingDefaultsMapper.routingIndexVOsToRoutingIndexModel(
				mailController.getPlannedRoutingIndexDetails(routingIndexVO.getDestination()));
	}

	/** 
	* @param companyCode
	* @param airportCode
	* @return OfficeOfExchangeVO
	* @author 204082Added for IASCB-164537 on 09-Nov-2022
	*/
	public Collection<OfficeOfExchangeModel> getExchangeOfficeDetails(String companyCode, String airportCode) {
		log.debug(MODULE + " : " + "getExchangeOfficeDetails" + " Entering");
		return mailTrackingDefaultsMapper.officeOfExchangeVOsToOfficeOfExchangeModel(
				mailController.getExchangeOfficeDetails(companyCode, airportCode));
	}

    public String findPAForMailboxID(String companyCode, String mailboxId, String originOE) {
		return mailController.findPAForMailboxID(companyCode,mailboxId,originOE);
    }	
public Map<String, String> findAirportForOfficeOfExchange(OfficeOfExchangeFilterModel filterModel) {
		log.debug(MODULE + " : " + "findAirportForOfficeOfExchanges" + " Entering");
		return mailController.findAirportForOfficeOfExchange(filterModel.getCompanyCode(),filterModel.getOfficeOfExchanges());
	}

	public String findUpuCodeNameForPA(String companyCode, String paCode) throws PersistenceException {
		return mailController.findUpuCodeNameForPA(companyCode, paCode);
	   }
	public Map<String, String> findCityForOfficeOfExchange(OfficeOfExchangeFilterModel filterModel) {
		log.debug(MODULE + " : " + "findCityForOfficeOfExchange" + " Entering");
		return mailController.findCityForOfficeOfExchange(filterModel.getCompanyCode(),filterModel.getOfficeOfExchanges());
	}
	public String findOfficeOfExchangeDescription(String companyCode, String exchangeCode) {
		log.debug(MODULE + " : " + "findOfficeOfExchangeDescription" + " Entering");
		return mailController.findOfficeOfExchangeDescription(companyCode,exchangeCode);
	
	}
	public Collection<MailEventModel>  findMailEvent(MailEventModel mailEventModel) {

		return mailTrackingDefaultsMapper.mailEventVOToMailEventModel
				(mailController.findMailEvent(mailTrackingDefaultsMapper.mailEventModelToMailEventVO(mailEventModel)));

	}
	public String findPartyIdentifierForPA(String companyCode, String paCode) {
		return mailController.findPartyIdentifierForPA(companyCode, paCode);
	}	
public String getMLDVersion(int carrierIdentifier, String companyCode, String airportCode) {
		log.debug(MODULE + " : " + "getMLDVersion" + " Entering");
		return mailController.getMLDVersion(carrierIdentifier,companyCode,airportCode);
	}
}
