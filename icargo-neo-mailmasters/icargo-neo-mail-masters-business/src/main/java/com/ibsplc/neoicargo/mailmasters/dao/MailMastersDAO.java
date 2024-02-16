package com.ibsplc.neoicargo.mailmasters.dao;

import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;
import java.rmi.RemoteException;
import java.util.*;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.neoicargo.mailmasters.component.MailEventPK;
import com.ibsplc.neoicargo.mailmasters.vo.*;

/** 
 * @author a-5991
 */
public interface MailMastersDAO extends QueryDAO {
	/** 
	* @author a-2037 This method is used to find all the mail subclass codes
	* @param companyCode
	* @param subclassCode
	* @return Collection<MailSubClassVO>
	* @throws SystemException
	* @throws PersistenceException
	*/
	Collection<MailSubClassVO> findMailSubClassCodes(String companyCode, String subclassCode)
			throws PersistenceException;

	/** 
	* @author a-1936 This method is used validate the MailSubClass
	* @param companyCode
	* @param mailSubClass
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	boolean validateMailSubClass(String companyCode, String mailSubClass) throws PersistenceException;

	/** 
	* @author a-1876 This method is used to list the PartnerCarriers.
	* @param companyCode
	* @param ownCarrierCode
	* @param airportCode
	* @return Collection<PartnerCarrierVO>
	* @throws SystemException
	* @throws PersistenceException
	*/
	Collection<PartnerCarrierVO> findAllPartnerCarriers(String companyCode, String ownCarrierCode, String airportCode)
			throws PersistenceException;

	Collection<CoTerminusVO> findAllCoTerminusAirports(CoTerminusFilterVO filterVO) throws PersistenceException;

	Collection<MailRdtMasterVO> findRdtMasterDetails(RdtMasterFilterVO filterVO) throws PersistenceException;

	Page<MailServiceStandardVO> listServiceStandardDetails(MailServiceStandardFilterVO mailServiceStandardFilterVO,
			int pageNumeber) throws PersistenceException;

	boolean validateCoterminusairports(String actualAirport, String eventAirport, String eventCode, String paCode,
			ZonedDateTime dspDate) throws PersistenceException;

	/** 
	* @author A-2037 This method is used to find Local PAs
	* @param companyCode
	* @param countryCode
	* @return Collection<PostalAdministrationVO>
	* @throws SystemException
	* @throws PersistenceException
	*/
	Collection<PostalAdministrationVO> findLocalPAs(String companyCode, String countryCode) throws PersistenceException;

	/** 
	* @author A-2037 Method for OfficeOfExchangeLOV containing code anddescription
	* @param companyCode
	* @param code
	* @param description
	* @param pageNumber
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	Page<OfficeOfExchangeVO> findOfficeOfExchangeLov(OfficeOfExchangeVO officeofExchangeVO, int pageNumber,
			int defaultSize) throws PersistenceException;

	/** 
	* @author A-2037 Method for MailSubClassLOV containing code and description
	* @param companyCode
	* @param code
	* @param description
	* @param pageNumber
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	Page<MailSubClassVO> findMailSubClassCodeLov(String companyCode, String code, String description, int pageNumber,
			int defaultSize) throws PersistenceException;

	/** 
	* @author A-5931 Method for MBI LOV containing mailboxCode and mailboxDescription
	* @param companyCode
	* @param mailboxCode
	* @param mailboxDescription
	* @param pageNumber
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	Page<MailBoxIdLovVO> findMailBoxIdLov(String companyCode, String mailboxCode, String mailboxDesc, int pageNumber,
			int defaultSize) throws PersistenceException;

	/** 
	* @author A-2037 Method for PALov containing PACode and PADescription
	* @param companyCode
	* @param paCode
	* @param paName
	* @param pageNumber
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	Page<PostalAdministrationVO> findPALov(String companyCode, String paCode, String paName, int pageNumber,
			int defaultSize) throws PersistenceException;

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	PostalAdministrationVO findPADetails(String companyCode, String officeOfExchange) throws PersistenceException;

	/** 
	* @author A-3251
	* @param postalAdministrationDetailsVO
	* @throws SystemException
	*/
	public PostalAdministrationDetailsVO validatePoaDetails(PostalAdministrationDetailsVO postalAdministrationDetailsVO)
			throws PersistenceException;

	/** 
	* Method		:	MailTrackingDefaultsDAO.findAllPACodes Added by 	:	A-4809 on 08-Jan-2014 Used for 	:	ICRD-42160 Parameters	:	@param generateInvoiceFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	Collection<PostalAdministrationVO>
	*/
	public Collection<PostalAdministrationVO> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO)
			throws PersistenceException;

	/** 
	* Method		:	MailTrackingDefaultsDAO.findAgentCodeForPA Added by 	:	U-1267 on Nov 1, 2017 Used for 	:	findAgentCodeForPA Parameters	:	@param companyCode Parameters	:	@param officeOfExchange Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException  Return type	: 	String
	*/
	public String findAgentCodeForPA(String companyCode, String officeOfExchange) throws PersistenceException;

	/** 
	* @author A-6986
	* @param mailServiceLevelVO
	* @return
	*/
	public String findMailServiceLevelForIntPA(MailServiceLevelVO mailServiceLevelVO) throws PersistenceException;

	/** 
	* @author A-6986
	* @param mailServiceLevelVO
	* @return
	*/
	public String findMailServiceLevelForDomPA(MailServiceLevelVO mailServiceLevelVO) throws PersistenceException;

	/** 
	* Method		:	MailTrackingDefaultsDAO.listPostalCalendarDetails Added by 	:	A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param uSPSPostalCalendarFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException  Return type	: 	Collection<USPSPostalCalendarVO>
	*/
	public Collection<USPSPostalCalendarVO> listPostalCalendarDetails(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) throws PersistenceException;

	/** 
	* @author A-6986
	* @param contractFilterVO
	* @param pageNumber
	* @return
	*/
	public Collection<GPAContractVO> listContractdetails(GPAContractFilterVO contractFilterVO)
			throws PersistenceException;

	/** 
	* @author A-6986
	* @param mailHandoverFilterVO
	* @return
	*/
	public Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO, int pageNumber)
			throws PersistenceException;

	/** 
	* Method		:	MailTrackingDefaultsDAO.findRotingIndex Added by 	:	A-7531 on 30-Oct-2018 Used for 	: Parameters	:	@param routeIndex Parameters	:	@param companycode Parameters	:	@return Return type	: 	RoutingIndexVO
	*/
	public Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO) throws PersistenceException;

	/** 
	* @author A-6986
	* @param incentiveConfigurationFilterVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<IncentiveConfigurationVO> findIncentiveConfigurationDetails(
			IncentiveConfigurationFilterVO incentiveConfigurationFilterVO) throws PersistenceException;

	/** 
	* @author A-8464
	* @param findServiceStandard
	* @return serviceStandard
	* @throws SystemException
	* @throws PersistenceException
	*/
	public int findServiceStandard(MailbagVO mailbagVo) throws PersistenceException;

	/** 
	* Method		:	MailTrackingDefaultsDAO.validateFrmToDateRange Added by 	:	A-8527 on 14-March-2019 Used for 	:	ICRD-262471 Parameters	:	@param uSPSPostalCalendarFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException  Return type	: 	Collection<USPSPostalCalendarVO>
	*/
	public Collection<USPSPostalCalendarVO> validateFrmToDateRange(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) throws PersistenceException;

	/** 
	* @author A-7371
	* @param uspsPostalCalendarFilterVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public USPSPostalCalendarVO findInvoicPeriodDetails(USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO)
			throws PersistenceException;

	/** 
	* @author A-8923
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public String findMailboxIdFromConfig(MailbagVO mailbagVO);

	public Collection<MailEventVO> findMailEvent(MailEventPK maileventPK);

	public String findMailHandoverDetails(MailHandoverVO mailHandoverVO) throws PersistenceException;

	public String findMailboxIdForPA(MailbagVO mailbagVO) throws PersistenceException;

	/** 
	* @param companyCode
	* @return PostalAdministrationVO
	* @throws SystemException
	* @throws PersistenceException
	* @author 204082Added for IASCB-159276 on 27-Sep-2022
	*/
	public Collection<PostalAdministrationVO> getPADetails(String companyCode) throws PersistenceException;

	/** 
	* @param companyCode
	* @return PostalAdministrationVO
	* @throws SystemException
	* @throws PersistenceException
	* @author 204083Added for IASCB-159276 on 27-Sep-2022
	*/
	Collection<OfficeOfExchangeVO> getOfficeOfExchangeDetails(String companyCode) throws PersistenceException;

	/** 
	* @param mailMasterDataFilterVO
	* @return MailbagDetailsVo
	* @throws SystemException
	* @throws PersistenceException
	* @author 204082Added for IASCB-159267 on 20-Oct-2022
	*/
	public Collection<MailbagDetailsVO> getMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO)
			throws PersistenceException;

	/** 
	* @param destinationAirportCode
	* @return
	* @throws SystemException
	* @author 204084Added as part of CRQ IASCB-164529
	*/
	public Collection<RoutingIndexVO> getPlannedRoutingIndexDetails(String destinationAirportCode);

	/** 
	* @param companyCode
	* @return MailSubClassVO
	* @throws SystemException
	* @throws PersistenceException
	* @author 204084Added for IASCB-172483 on 15-Oct-2022
	*/
	public Collection<MailSubClassVO> getSubclassDetails(String companyCode) throws PersistenceException;

	/** 
	* @param companyCode
	* @param airportCode
	* @return OfficeOfExchangeVO
	* @throws RemoteException
	* @author 204082Added for IASCB-164537 on 09-Nov-2022
	*/
	public Collection<OfficeOfExchangeVO> getExchangeOfficeDetails(String companyCode, String airportCode);

	PostalAdministrationVO findPACode(String companyCode, String paCode)
			throws SystemException;
			
			/**
	 * @author A-10552 Method for findOfficeOfExchange
	 * @param companyCode
	 * @param officeOfExchange
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<OfficeOfExchangeVO> findOfficeOfExchange(String companyCode, String officeOfExchange, int pageNumber)
			throws SystemException, PersistenceException;

	Collection<String> findOfficeOfExchangesForAirport(String companyCode, String airportCode) throws SystemException,
			PersistenceException;

	HashMap<String, String> findOfficeOfExchangeForPA(String companyCode, String paCode) throws SystemException, PersistenceException;
	public HashMap<String,String> findCityForOfficeOfExchange(
			String companyCode, Collection<String> officeOfExchanges) throws SystemException,
			PersistenceException;

    Collection<MLDConfigurationVO> findMLDCongfigurations(MLDConfigurationFilterVO mLDConfigurationFilterVO);

	String findPAForMailboxID(String companyCode, String mailboxId, String originOE);	

	 public HashMap<String,String> findAirportForOfficeOfExchange(
			String companyCode, Collection<String> officeOfExchanges) throws SystemException,
			PersistenceException;

	String findUpuCodeNameForPA(String companyCode, String paCode) throws PersistenceException;
	String findPartyIdentifierForPA(String companyCode, String paCode)
			throws SystemException, PersistenceException;
}
