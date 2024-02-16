package com.ibsplc.neoicargo.mailmasters;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.lang.notation.apis.PrivateAPI;
import com.ibsplc.neoicargo.mailmasters.exception.MailTrackingBusinessException;
import com.ibsplc.neoicargo.mailmasters.model.*;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/** 
 * @author a-1303
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Service
@PrivateAPI
public interface MailTrackingDefaultsBI {
	/** 
	* @author A-2037 Method for OfficeOfExchangeLOV containing code anddescription
	* @param pageNumber
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mailmasters/findofficeofexchangelov")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Page<OfficeOfExchangeModel> findOfficeOfExchangeLov(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) OfficeOfExchangeModel officeofExchangeModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON,required=false) int defaultSize);

	/**
	 //* @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	@Path("/v1/mailmasters/findPAForMailboxID")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findPAForMailboxID(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String mailboxId,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String originOE);

	/** 
	* @author A-2037 Method for MailSubClassLOV containing code and description
	* @param companyCode
	* @param code
	* @param description
	* @param pageNumber
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mailmasters/findmailsubclasscodelov")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Page<MailSubClassModel> findMailSubClassCodeLov(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String code,
			@Multipart(value = "2",required=false, type = MediaType.APPLICATION_JSON) String description,
			@Multipart(value = "3", type = MediaType.APPLICATION_JSON) int pageNumber,
			@Multipart(value = "4", type = MediaType.APPLICATION_JSON) int defaultSize);

	/** 
	* * @author A-2037 This method is used to find Postal Administration Code
	* @param companyCode
	* @param paCode
	* @return PostalAdministrationVO
	* @throws SystemException
	*/
	@Path("/v1/mailmasters/findpacode")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	PostalAdministrationModel findPACode(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String paCode);

	@Path("/v1/mailmasters/findallpartnercarriers")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<PartnerCarrierModel> findAllPartnerCarriers(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String ownCarrierCode,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String airportCode);

	@Path("/v1/mailmasters/findallcoterminusairports")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<CoTerminusModel> findAllCoTerminusAirports(CoTerminusFilterModel filterModel);

	@Path("/v1/mailmasters/listservicestandarddetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailServiceStandardModel> listServiceStandardDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailServiceStandardFilterModel mailServiceStandardFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	/** 
	* Method		:	MailTrackingDefaultsBI.findRdtMasterDetails Added by 	:	A-6991 on 17-Jul-2018 Used for 	:   ICRD-212544 Parameters	:	@param filterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	Collection<MailRdtMasterVO>
	*/
	@Path("/v1/mailmasters/findrdtmasterdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<MailRdtMasterModel> findRdtMasterDetails(RdtMasterFilterModel filterModel);

	@Path("/v1/mailmasters/validatecoterminusairports")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public boolean validateCoterminusairports(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON, required = false) String actualAirport ,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON, required = false) String eventAirport,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON, required = false) String eventCode,
			@Multipart(value = "3", type = MediaType.APPLICATION_JSON, required = false) String paCode,
			@Multipart(value = "4", type = MediaType.APPLICATION_JSON, required = false) ZonedDateTime dspDate);

	/** 
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mailmasters/validateofficeofexchange")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public OfficeOfExchangeModel validateOfficeOfExchange(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String officeOfExchange);

	/** 
	* This method is used to save Postal Administration Code
	*/
	@Path("/v1/mailmasters/savepacode")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void savePACode(PostalAdministrationModel postalAdministrationModel) throws SystemException;

	/** 
	* This method is used to save office of Exchange Code
	* @throws SystemException
	*/
	@Path("/v1/mailmasters/saveofficeofexchange")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void saveOfficeOfExchange(Collection<OfficeOfExchangeModel> officeOfExchangesModel)throws BusinessException;

	/** 
	* @author a-2037 This method is used to save Mail sub class codes
	* @throws MailTrackingBusinessException
	 */
	@Path("/v1/mailmasters/savemailsubclasscodes")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void saveMailSubClassCodes(Collection<MailSubClassModel> mailSubClasssModel) throws MailTrackingBusinessException ;

	/** 
	* @author A-2037 This method is used to find Local PAs
	* @param companyCode
	* @param countryCode
	* @return Collection<PostalAdministrationVO>
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findlocalpas")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Collection<PostalAdministrationModel> findLocalPAs(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String countryCode);

	/** 
	* @author A-2037 Method for PALov containing PACode and PADescription
	* @param companyCode
	* @param paCode
	* @param paName
	* @param pageNumber
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findpalov")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Page<PostalAdministrationModel> findPALov(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String paCode,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON, required = false) String paName,
			@Multipart(value = "3", type = MediaType.APPLICATION_JSON) int pageNumber,
			@Multipart(value = "4", type = MediaType.APPLICATION_JSON) int defaultSize);

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findpaforofficeofexchange")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findPAForOfficeOfExchange(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String officeOfExchange);

	/** 
	* @author a-2037 This method is used to find all the mail subclass codes
	* @param companyCode
	* @param subclassCode
	* @return Collection<MailSubClassVO>
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findmailsubclasscodes")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<MailSubClassModel> findMailSubClassCodes(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String subclassCode);

	/** 
	* @author a-2037 This method is used to find all the mail subclass codes
	* @param companyCode
	* @param officeOfExchange
	* @param pageNumber
	* @return Collection<OfficeOfExchangeVO>
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findofficeofexchange")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Page<OfficeOfExchangeModel> findOfficeOfExchange(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON, required = false) String officeOfExchange,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) int pageNumber);

	/** 
	* @author A-5931 Method for MailBoxIdLov containing mailboxCode and mailboxDesc
	* @param companyCode
	* @param mailboxCode
	* @param mailboxDesc
	* @param pageNumber
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findmailboxidlov")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Page<MailBoxIdLovModel> findMailBoxIdLov(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String mailboxCode,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON, required = false) String mailboxDesc,
			@Multipart(value = "3", type = MediaType.APPLICATION_JSON) int pageNumber,
			@Multipart(value = "4", type = MediaType.APPLICATION_JSON) int defaultSize);

	/** 
	* @author A-3227  - FEB 18, 2009
	* @param companyCode
	* @param officeOfExchanges
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findcityandairportforoe")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<ArrayList<String>> findCityAndAirportForOE(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) Collection<String> officeOfExchanges);

	/** 
	* Added for icrd-95515
	* @param companyCode
	* @param airportCode
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findofficeofexchangesforairport")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<String> findOfficeOfExchangesForAirport(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String airportCode);

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findpadetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	PostalAdministrationModel findPADetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String officeOfExchange);

	/** 
	* @author A-3227 RENO K ABRAHAM
	* @param companyCode
	* @param subclass
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/validatemailsubclass")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	boolean validateMailSubClass(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String subclass);

	/** 
	* @author A-3251
	* @throws SystemException
	*	 
	*/
	@Path("/v1/mailmasters/validatepoadetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public PostalAdministrationDetailsModel validatePoaDetails(
			PostalAdministrationDetailsModel postalAdministrationDetailsModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.findAllPACodes Added by 	:	A-4809 on 08-Jan-2014 Used for 	:	ICRD-42160 to find all activePAs Parameters	:	@param generateInvoiceFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	Collection<PostalAdministrationVO>
	*/
	@Path("/v1/mailmasters/findallpacodes")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<PostalAdministrationModel> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO);

	/**
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findmldcongfigurations")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<MLDConfigurationModel> findMLDCongfigurations(
			MLDConfigurationFilterModel mLDConfigurationFilterModel);

	/**
	 *
	 * @param mLDConfigurationsModel
	 */
	@Path("/v1/mailmasters/savemldconfigurations")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void saveMLDConfigurations(Collection<MLDConfigurationModel> mLDConfigurationsModel) throws BusinessException;

	/** 
	* @author A-1936 This method is used to save the PartnerCarriers..
	*/
	@Path("/v1/mailmasters/savepartnercarriers")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void savePartnerCarriers(Collection<PartnerCarrierModel> partnerCarriersModel);

	@Path("/v1/mailmasters/savecoterminusdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveCoterminusDetails(Collection<CoTerminusModel> coterminussModel);

	@Path("/v1/mailmasters/saveservicestandarddetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void saveServiceStandardDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<MailServiceStandardModel> mailServiceStandardsModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) Collection<MailServiceStandardModel> mailServiceStandardstodeleteModel);

	@Path("/v1/mailmasters/saverdtmasterdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveRdtMasterDetails(Collection<MailRdtMasterModel> mailRdtMastersModel);

	@Path("/v1/mailmasters/saverdtmasterdetailsxls")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ErrorVO> saveRdtMasterDetailsXls(Collection<MailRdtMasterModel> mailRdtMastersModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.findOfficeOfExchangeForAirports Added by 	:	a-6245 on 10-Jul-2017 Used for 	: Parameters	:	@param companyCode Parameters	:	@param paCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	Map<String,String>
	*/
	@Path("/v1/mailmasters/findofficeofexchangeforpa")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Map<String, String> findOfficeOfExchangeForPA(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String paCode);

	/** 
	* Method		:	MailTrackingDefaultsBI.findAgentCodeForPA Added by 	:	U-1267 on 07-Nov-2017 Used for 	:	ICRD-211205 Parameters	:	@param companyCode Parameters	:	@param paCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	String
	*/
	@Path("/v1/mailmasters/findagentcodeforpa")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findAgentCodeForPA(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String paCode);

	/** 
	* @author A-6986
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/savemailserviceleveldtls")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ErrorVO> saveMailServiceLevelDtls(Collection<MailServiceLevelModel> mailServiceLevelsModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.listPostalCalendarDetails Added by 	:	A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param uSPSPostalCalendarFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Parameters	:	@throws MailTrackingBusinessException Return type	: 	Collection<USPSPostalCalendarVO>
	*/
	@Path("/v1/mailmasters/listpostalcalendardetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<USPSPostalCalendarModel> listPostalCalendarDetails(
			USPSPostalCalendarFilterModel uSPSPostalCalendarFilterModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.savePostalCalendar Added by 	:	A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param uSPSPostalCalendarVOs Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Parameters	:	@throws MailTrackingBusinessException Return type	: 	void
	*/
	@Path("/v1/mailmasters/savepostalcalendar")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void savePostalCalendar(Collection<USPSPostalCalendarModel> uSPSPostalCalendarsModel);

	/** 
	* @author A-6986
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/savecontractdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveContractDetails(Collection<GPAContractModel> gpaContractsModel)throws BusinessException;

	/** 
	* @author A-6986
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/listcontractdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<GPAContractModel> listContractDetails(GPAContractFilterModel gpaContractFilterModel);

	/** 
	* a-6986
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/savemailhandoverdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveMailHandoverDetails(Collection<MailHandoverModel> MailHandoversModel);

	/** 
	* a-6986
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findmailhandoverdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailHandoverModel> findMailHandoverDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailHandoverFilterModel mailHandoverFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	/** 
	* @author A-6986
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findincentiveconfigurationdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<IncentiveConfigurationModel> findIncentiveConfigurationDetails(
			IncentiveConfigurationFilterModel incentiveConfigurationFilterModel);

	/** 
	* @author A-6986
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/saveincentiveconfigurationdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveIncentiveConfigurationDetails(Collection<IncentiveConfigurationModel> incentiveConfigurationsModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.saveRoutingIndexDetails Added by 	:	A-7531 on 12-Oct-2018 Used for 	: Parameters	:	@param routingIndexVOs Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Parameters	:	@throws MailTrackingBusinessException Parameters	:	@throws FinderException Return type	: 	void
	*/
	@Path("/v1/mailmasters/saveroutingindexdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveRoutingIndexDetails(Collection<RoutingIndexModel> routingIndexsModel);

	/**
	* Method		:	MailTrackingDefaultsBI.findRoutingIndex Added by 	:	A-7531 on 30-Oct-2018 Used for 	: Parameters	:	@param routingIndex Parameters	:	@param companycode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	Collection<RoutingIndexVO>
	*/
	@Path("/v1/mailmasters/findroutingindex")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<RoutingIndexModel> findRoutingIndex(RoutingIndexModel routingIndexModel) ;

	/**
	* @throws SystemException
	* @author A-8527
	*/
	@Path("/v1/mailmasters/validatefrmtodaterange")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<USPSPostalCalendarModel> validateFrmToDateRange(
			USPSPostalCalendarFilterModel uSPSPostalCalendarFilterModel);

	/** 
	* @author A-7371
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/findinvoicperioddetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public USPSPostalCalendarModel findInvoicPeriodDetails(USPSPostalCalendarFilterModel uspsPostalCalendarFilterModel);

	@Path("/v1/mailmasters/findmailboxid")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailboxIdModel findMailboxId(MailboxIdModel mailboxIdModel) ;

	@Path("/v1/mailmasters/savemailboxid")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveMailboxId(MailboxIdModel mailboxIdModel) ;

	@Path("/v1/mailmasters/savemailruleconfiguration")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveMailRuleConfiguration(MailRuleConfigModel mailRuleConfigModel)  ;

	@Path("/v1/mailmasters/findmailboxidfromconfig")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String findMailboxIdFromConfig(MailbagModel mailbagModel);

	/**
	* @author 204082Added for IASCB-159276 on 27-Sep-2022
	*/
	@Path("/v1/mailmasters/publishmasterdataformail")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void publishMasterDataForMail(MailMasterDataFilterModel mailMasterDataFilterModel);

	/** 
	* -
	* @author 204084Added as part of CRQ IASCB-164529
	* @return
	* @throws SystemException
	*
	*/
	@Path("/v1/mailmasters/getplannedroutingindexdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<RoutingIndexModel> getPlannedRoutingIndexDetails(RoutingIndexModel routingIndexModel);

	/** 
	* @param companyCode
	* @param airportCode
	* @return OfficeOfExchangeVO
	*
	* @author 204082Added for IASCB-164537 on 09-Nov-2022
	*/
	@Path("/v1/mailmasters/getexchangeofficedetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Collection<OfficeOfExchangeModel> getExchangeOfficeDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String airportCode);
	/**
	 *
	 * @param filterModel
	 * @return Map<String,String>
	 */
	@Path("/v1/mailmasters/findairportforofficeofexchange")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Map<String,String> findAirportForOfficeOfExchange(OfficeOfExchangeFilterModel filterModel);

	/**
	 *
	 * @param companyCode
	 * @param paCode
	 * @return UpuCode
	 * @throws PersistenceException
	 */

	@Path("/v1/mailmasters/findUpuCodeNameForPA")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findUpuCodeNameForPA(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String paCode) throws PersistenceException;
	@Path("/v1/mailmasters/findcityforofficeofexchange")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Map<String,String> findCityForOfficeOfExchange(OfficeOfExchangeFilterModel filterModel);

	@Path("/v1/mailmasters/findofficeofexchangedescription")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findOfficeOfExchangeDescription(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String exchangeCode);
	@Path("/v1/mailmasters/findmailevent")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<MailEventModel> findMailEvent(MailEventModel mailEventModel);
	@Path("/v1/mailmasters/findpartyidentifierforpa")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findPartyIdentifierForPA(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String paCode);	

@Path("/v1/mailmasters/getmldversion")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String getMLDVersion(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) int carrierIdentifier,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String airportCode);
}
