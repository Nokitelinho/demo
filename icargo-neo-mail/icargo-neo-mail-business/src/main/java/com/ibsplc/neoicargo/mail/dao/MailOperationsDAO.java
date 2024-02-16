package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.FlightListingFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportFlightOperationsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightULDVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;

/** 
 * @author a-5991
 */
public interface MailOperationsDAO extends QueryDAO {
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
	* @author A-2037 This method is used to find the History of a Mailbag
	* @param companyCode
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	Collection<MailbagHistoryVO> findMailbagHistories(String companyCode, String mailBagId, long mailSequenceNumbe,
			String mldMsgGenerateFlagr) throws PersistenceException;

	/** 
	* TODO Purpose Oct 6, 2006, a-1739
	* @param mailbagVO
	* @return
	*/
	MailbagVO findMailbagDetailsForUpload(MailbagVO mailbagVO) throws PersistenceException;

	/** 
	* Method		:	MailTrackingDefaultsDAO.findAirportCityForMLD Added by 	:	A-4803 on 14-Nov-2014 Used for 	:	finding airport city for mld Parameters	:	@param companyCode Parameters	:	@param destination Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	String public String findAirportCityForMLD(String companyCode, String destination) throws SystemException, PersistenceException; /
	* @author A-5991
	* @param flightAssignedContainerVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public int findNumberOfBarrowsPresentinFlightorCarrier(ContainerVO flightAssignedContainerVO)
			throws PersistenceException;

	/** 
	* Find the flight details on which the container had arrived at a port Sep 12, 2007, a-1739
	* @param containerVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	ContainerAssignmentVO findArrivalDetailsForULD(ContainerVO containerVO) throws PersistenceException;

	/** 
	* Finds the arrival details for a mailbag Sep 12, 2007, a-1739
	* @param mailbagVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	MailbagVO findArrivalDetailsForMailbag(MailbagVO mailbagVO) throws PersistenceException;

	/** 
	* @author A-2553
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public String findMailType(MailbagVO mailbagVO) throws PersistenceException;

	/** 
	* @param mailbagVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public boolean isMailbagAlreadyArrived(MailbagVO mailbagVO) throws PersistenceException;

	/** 
	* @author A-5526
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<MLDConfigurationVO> findMLDCongfigurations(MLDConfigurationFilterVO mLDConfigurationFilterVO)
			throws PersistenceException;

	/** 
	* @author A-5991
	* @param mailId
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public long findMailSequenceNumber(String mailId, String companyCode);

	/** 
	* @author a-1936This method  is used to find out the Mail Bags in the Containers
	* @param containers
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	Collection<ContainerDetailsVO> findMailbagsInContainer(Collection<ContainerDetailsVO> containers)
			throws PersistenceException;

	Collection<ContainerDetailsVO> findMailbagsInContainerWithoutAcceptance(Collection<ContainerDetailsVO> containers)
			throws PersistenceException;

	/** 
	* @author A-2521For fetching already Stamped flight details for Collection of Mail Events
	*/
	public HashMap<String, Collection<MailResditVO>> findResditFlightDetailsForMailbagEvents(
			Collection<MailResditVO> mailResditVOs) throws PersistenceException;

	/** 
	* @author A-1739 This method is used to findFlaggedResditSeqNum
	* @param mailResditVO
	* @param isSentCheckNeeded
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	boolean checkResditExists(MailResditVO mailResditVO, boolean isSentCheckNeeded) throws PersistenceException;

	/** 
	* Method		:	MailTrackingDefaultsDAO.checkResditExists Added by 	:	A-5201 on 21-Nov-2014 Used for 	: Parameters	:	@param mailResditVO Parameters	:	@param isSentCheckNeeded Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	boolean
	*/
	boolean checkResditExistsFromReassign(MailResditVO mailResditVO, boolean isSentCheckNeeded)
			throws PersistenceException;

	/** 
	* @author A-4072For Mail 4 Finding PA for Mailbags . If cardit exists then sender of cardit else PA of OOE //Added for ICRD-63167 moving Cardit Resdit from QF to Base
	*/
	public HashMap<String, String> findPAForMailbags(Collection<MailbagVO> mailbagVOs) throws PersistenceException;

	/** 
	* @author A-1739 This method is used to check whether the Cardit is Presentfor MailBag
	* @param companyCode
	* @param mailbagId
	* @return
	* @throws SystemException
	*/
	String findCarditForMailbag(String companyCode, String mailbagId) throws PersistenceException;

	/** 
	* TODO Purpose Feb 5, 2007, A-1739
	* @param companyCode
	* @return
	*/
	Collection<CarditTransportationVO> findCarditTransportationDetails(String companyCode, String carditKey)
			throws PersistenceException;

	/** 
	* @author A-1936 ADDED AS THE PART OF NCA-CR This method is used to findthe CarditDetails for the MailBags which will be used while cardits are being flagged for all the Transferred Mails..
	* @param companyCode
	* @param mailID
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	MailbagVO findCarditDetailsForAllMailBags(String companyCode, long mailID) throws PersistenceException;

	public MailbagVO findTransferFromInfoFromCarditForMailbags(MailbagVO mailbagVO) throws PersistenceException;

	/** 
	* Find the ResditConfiguration for a txn Feb 5, 2007, A-1739
	* @param companyCode
	* @param carrierId
	* @param txnId
	* @return
	*/
	ResditTransactionDetailVO findResditConfurationForTxn(String companyCode, int carrierId, String txnId)
			throws PersistenceException;

	/** 
	* @author A-2553
	* @param companyCode
	* @param mailbagId
	* @return
	* @throws SystemException
	*/
	public MailbagVO findExistingMailbags(String companyCode, long mailbagId) throws PersistenceException;

	/** 
	* @author a-2518
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	String findPostalAuthorityCode(String companyCode, String officeOfExchange) throws PersistenceException;

	/** 
	* @author a-2518
	* @param companyCode
	* @param gpaCode
	* @param origin
	* @param destination
	* @param mailCategory
	* @param activity
	* @param scanDate
	* @return MailActivityDetailVO
	* @throws SystemException
	* @throws PersistenceException
	*/
	MailActivityDetailVO findServiceTimeAndSLAId(String companyCode, String gpaCode, String origin, String destination,
			String mailCategory, String activity, ZonedDateTime scanDate) throws PersistenceException;

	/** 
	* @author A-3227This method fetches the latest Container Assignment irrespective of the PORT to which it is assigned. This to know the current assignment of the Container.
	* @param containerNumber
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	ContainerAssignmentVO findLatestContainerAssignment(String companyCode, String containerNumber)
			throws PersistenceException;

	/** 
	* @author a-1936 This method Checks whether the container is alreadyassigned to a flight/destn from the current airport
	* @param companyCode
	* @param containerNumber
	* @param pol
	* @throws SystemException
	* @throws PersistenceException
	*/
	ContainerAssignmentVO findContainerAssignment(String companyCode, String containerNumber, String pol)
			throws PersistenceException;

	/** 
	* @author A-3227findCityForOfficeOfExchange
	* @param companyCode
	* @param officeOfExchanges
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public HashMap<String, String> findCityForOfficeOfExchange(String companyCode, Collection<String> officeOfExchanges)
			throws PersistenceException;

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

	Collection<MailRdtMasterVO> findRdtMasterDetails(RdtMasterFilterVO filterVO) throws PersistenceException;

	/** 
	* This method finds mail sequence number
	* @param mailInConsignmentVO
	* @return int
	* @throws SystemException
	* @throws PersistenceException
	*/
	int findMailSequenceNumber(MailInConsignmentVO mailInConsignmentVO) throws PersistenceException;

	/** 
	* @author a-1883
	* @param consignmentDocumentVO
	* @return ConsignmentDocumentVO
	* @throws SystemException
	* @throws PersistenceException
	*/
	ConsignmentDocumentVO checkConsignmentDocumentExists(ConsignmentDocumentVO consignmentDocumentVO)
			throws PersistenceException;

	/** 
	* @author A-2037 This method is used to get theConsignmentDetailsForMailBag
	* @param companyCode
	* @param mailId
	* @param airportCode
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	MailInConsignmentVO findConsignmentDetailsForMailbag(String companyCode, String mailId, String airportCode)
			throws PersistenceException;

	/** 
	* Method		:	MailTrackingDefaultsDAO.findAirportCityForMLD Added by 	:	A-4803 on 14-Nov-2014 Used for 	:	finding airport city for mld Parameters	:	@param companyCode Parameters	:	@param destination Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	String
	*/
	public String findAirportCityForMLD(String companyCode, String destination) throws PersistenceException;

	/** 
	* @author A-2037 This method is used to find Local PAs
	* @param companyCode
	* @param countryCode
	* @return Collection<PostalAdministrationVO>
	* @throws SystemException
	* @throws PersistenceException
	*/
	Collection<PostalAdministrationVO> findLocalPAs(String companyCode, String countryCode) throws PersistenceException;

	public long findResditSequenceNumber(MailResditVO mailResditVO) throws PersistenceException;

	public Collection<MailbagVO> findMailBagForDespatch(MailbagVO mailbagVO) throws PersistenceException;

	/** 
	* @author A-5526 Added for CRQ-ICRD-103713
	* @param companyCode
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	String findUpuCodeNameForPA(String companyCode, String paCode) throws PersistenceException;

	/** 
	* @param companyCode
	* @param ExchangeOfficeCode
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public String findCityForOfficeOfExchange(String companyCode, String ExchangeOfficeCode)
			throws PersistenceException;

	Map<Long, Collection<MailbagHistoryVO>> findMailbagHistoriesMap(String companyCode, long[] malseqnum)
			throws PersistenceException;

	Map<Long, MailInConsignmentVO> findAllConsignmentDetailsForMailbag(String companyCode, long[] malseqnum)
			throws PersistenceException;

	/** 
	* @param resditVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public String findMailboxId(MailResditVO resditVO) throws PersistenceException;

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
	* @author A-7371
	* @param mailbagVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Timestamp fetchSegmentSTA(MailbagVO mailbagVO) throws PersistenceException;

	/** 
	* @author A-7371 
	* @param mailbagVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public String fetchRDTOffset(MailbagVO mailbagVO, String paCodDom) throws PersistenceException;

	/** 
	* @author A-6986
	* @param mailHandoverFilterVO
	* @return
	*/
	public Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO, int pageNumber)
			throws PersistenceException;

	/** 
	* @author A-7871Used for ICRD-240184
	* @param currentAirport
	* @param paCode
	* @return receiveFromTruckEnabled
	* @throws SystemException 
	*/
	public String checkReceivedFromTruckEnabled(String currentAirport, String orginAirport, String paCode,
			ZonedDateTime dspDate);

	/** 
	* Method		:	MailTrackingDefaultsDAO.findRotingIndex Added by 	:	A-7531 on 30-Oct-2018 Used for 	: Parameters	:	@param routeIndex Parameters	:	@param companycode Parameters	:	@return Return type	: 	RoutingIndexVO
	*/
	public Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO) throws PersistenceException;

	/** 
	* @author A-7794
	* @return
	* @throws SystemException
	*/
	public String checkScanningWavedDest(MailbagVO mailbagVO);

	public ScannedMailDetailsVO findAWBAttachedMailbags(ShipmentSummaryVO shipmentSummaryVO,
			MailFlightSummaryVO mailFlightSummaryVO);

	/** 
	* @author A-8464
	* @return serviceStandard
	* @throws SystemException
	* @throws PersistenceException
	*/
	public int findServiceStandard(MailbagVO mailbagVo) throws PersistenceException;

	/** 
	* @author A-8514 
	* @param scannedMailDetailsVO Added as part of ICRD-229584 
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public MailbagInULDForSegmentVO getManifestInfo(ScannedMailDetailsVO scannedMailDetailsVO)
			throws PersistenceException;

	/** 
	* @author A-8061
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public String findServiceResponsiveIndicator(MailbagVO mailbagVO);

	/** 
	* @author A-8923
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	public String findMailboxIdFromConfig(MailbagVO mailbagVO);

	public String findRoutingDetailsForConsignment(MailbagVO mailbagVO);

	/** 
	* @author A-5526Added as part of CRQ IASCB-44518
	* @param containerNumber
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public Collection<MailbagVO> findMailbagsFromOALinResditProcessing(String containerNumber, String companyCode)
			throws PersistenceException;

	/** 
	* @author U-1439
	* @param containerNumber
	* @param companyCode
	* @param fromDate
	* @param toDate
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<MailbagVO> findMailbagsForPABuiltContainerSave(String containerNumber, String companyCode,
			ZonedDateTime fromDate, ZonedDateTime toDate) throws PersistenceException;

	/** 
	* @param mailbagVO
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public MailbagVO findNotupliftedMailsInCarrierforDeviationlist(MailbagVO mailbagVO) throws PersistenceException;

	public String findMailHandoverDetails(MailHandoverVO mailHandoverVO) throws PersistenceException;

	public String findMailboxIdForPA(MailbagVO mailbagVO) throws PersistenceException;

	/** 
	* @author A-8353
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public MailbagInULDForSegmentVO getManifestInfoForNextSeg(MailbagVO mailbagVO) throws PersistenceException;

	/** 
	* @author A-8353
	* @param companyCode
	* @param malSeqNum
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public String findTransferManifestId(String companyCode, long malSeqNum) throws PersistenceException;

	public String findAgentCodeFromUpuCode(String cmpCode, String upuCode);

	public int findScreeningDetails(String mailBagId, String companyCode);

	public Collection<MailbagVO> findAWBAttachedMailbags(MailbagVO mailbag, String consignmentNumber);

	public MailbagVO findMailbagDetails(String mailId, String companyCode);

	/** 
	* @author U-1532findLatestContainerAssignment
	* @param scannedMailDetailsVO
	* @return
	*/
	public ContainerAssignmentVO findLatestContainerAssignmentForUldDelivery(ScannedMailDetailsVO scannedMailDetailsVO)
			throws PersistenceException;

	/** 
	* @author A-8353
	* @param consignmentScreeningVO
	* @return
	* @throws SystemException 
	*/
	public long findLatestRegAgentIssuing(ConsignmentScreeningVO consignmentScreeningVO);

	/** 
	* @author A-8353
	* @return 
	* @throws SystemException 
	*/
	public Collection<ConsignmentScreeningVO> findScreeningMethodsForStampingRegAgentIssueMapping(
			ConsignmentScreeningVO consignmentScreeningVO);

	/** 
	* @author A-8353
	* @param companyCode
	* @param sequencenum
	* @throws SystemException 
	*/
	public String findApplicableRegFlagForMailbag(String companyCode, long sequencenum);

	public Collection<CarditPawbDetailsVO> findMailbagsForPAWBCreation(int noOfDays);

	/** 
	* @author a-9998
	* @param carditVO
	* @return AWBDetailVO
	* @throws SystemException
	* @throws PersistenceException
	*/
	AWBDetailVO findMstDocNumForAWBDetails(CarditVO carditVO) throws PersistenceException;

	/**
	 * @param mailMasterDataFilterVO
	 * @return MailbagDetailsVo
	 * @throws com.ibsplc.xibase.server.framework.exceptions.SystemException
	 * @throws PersistenceException
	 * @author 204082
	 * Added for IASCB-159267 on 20-Oct-2022
	 */
	public Collection<MailbagDetailsVO> getMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO)
			throws SystemException, PersistenceException;
	/**
	 * This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	ConsignmentDocumentVO findConsignmentDocumentDetails(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException,
			PersistenceException;
	/**
	 * @author A-1936 This method is used to find the Uplift
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<MailbagVO> findMailBagsForUpliftedResdit(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;

	/**
	 * This method finds Cardit Details of Maiibag
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	MailbagHistoryVO findCarditDetailsOfMailbag(String companyCode, String mailBagId, long mailSequenceNumber)
			throws PersistenceException;
	Collection<MailHistoryRemarksVO> findMailbagNotes(String mailBagId) throws PersistenceException;

	/**
	 * @param mailbagEnquiryFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<MailbagHistoryVO> findMailStatusDetails(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws PersistenceException;

	/**
	 * @author A-1885
	 * @param mailUploadVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailUploadVO> findMailbagAndContainer(MailUploadVO mailUploadVo) throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findAlreadyAssignedTrolleyNumberForMLD Added by 	:	A-4803 on 28-Oct-2014 Used for 	:	To find whether a container is already presnet for the mail bag Parameters	:	@param mldMasterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	String
	 */
	public String findAlreadyAssignedTrolleyNumberForMLD(MLDMasterVO mldMasterVO) throws PersistenceException;

	/**
	 * @author A-1876 This method is used to find the Uplift
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerDetailsVO> findUldsForUpliftedResdit(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;

	/**
	 * @author A-1936 This method is used to validate teh OfficeofExchange
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	OfficeOfExchangeVO validateOfficeOfExchange(String companyCode, String officeOfExchange)
			throws PersistenceException;

	/**
	 * @author A-2037 This method is used to find the Mailbags in CARDIT
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<PreAdviceDetailsVO> findMailbagsInCARDIT(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;

	/**
	 * @author A-5991
	 * @param companyCode
	 * @param consignmentId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	CarditVO findCarditDetailsForResdit(String companyCode, String consignmentId) throws PersistenceException;

	/**
	 * Jun 3, 2008, a-1739
	 * @param uldResditVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<UldResditVO> findULDResditStatus(UldResditVO uldResditVO) throws PersistenceException;

	/**
	 * Find the PA corresponding to a Office of Exchange Sep 13, 2006, a-1739
	 * @param companyCode
	 * @param officeOfExchange
	 * @return the PA Code
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String findPAForOfficeOfExchange(String companyCode, String officeOfExchange) throws PersistenceException;

	/**
	 * @author a-1883 NCA CR
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<MailDiscrepancyVO> findMailDiscrepancies(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerVO> findULDsInInboundFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;
	/**
	 * @author A-3227
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public DespatchDetailsVO findConsignmentDetailsForDespatch(String companyCode, DespatchDetailsVO despatchDetailsVO)
			throws PersistenceException;

	/**
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MLDMasterVO> findMLDDetails(String companyCode, int recordCount) throws PersistenceException;

	/**
	 * @author a-2553
	 * @param carditEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws PersistenceException;

	/**
	 * @author A-2107
	 * @param consignmentFilterVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagVO> findCartIdsMailbags(ConsignmentFilterVO consignmentFilterVO);

	/**
	 * @param companyCode
	 * @param mailbagID
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public CarditReceptacleVO findDuplicateMailbagsInCardit(String companyCode, String mailbagID)
			throws PersistenceException;

	/**
	 * @param opFltVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ContainerAssignmentVO findContainerDetailsForMRD(OperationalFlightVO opFltVo, String mailBag)
			throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.validateMailBagsForUPL Added by 	:	A-4803 on 24-Nov-2014 Used for 	:	validating mail bags for MLD UPL Parameters	:	@param flightValidationVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	Collection<String>
	 */
	Collection<String> validateMailBagsForUPL(FlightValidationVO flightValidationVO) throws PersistenceException;

	/**
	 * @author A-1739 This method is used to find the FlightLegSerialNumber
	 * @param containerVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	int findFlightLegSerialNumber(ContainerVO containerVO) throws PersistenceException;
	/**
	 * Method		:	MailTrackingDefaultsDAO.validateMailFlight Added by 	:	A-5160 on 26-Nov-2014 Used for 	:	validating mail flight Parameters	:	@param flightFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	Collection<FlightValidationVO>
	 */
	public Collection<FlightValidationVO> validateMailFlight(FlightFilterVO flightFilterVO) throws PersistenceException;

	/**
	 * @author A-3227  - AUG 12, 2008
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public boolean checkRoutingsForMails(OperationalFlightVO operationalFlightVO, DSNVO dSNVO, String type)
			throws PersistenceException;

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerVO> findULDsInAssignedFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;

	/**
	 * @author a-1936This  method is used to find the Transfer Manifest Details
	 * @param tranferManifestFilterVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo)
			throws PersistenceException;

	/**
	 * @author a-1936
	 * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	MailManifestVO findContainersInFlightForManifest(OperationalFlightVO operationalFlightVo)
			throws PersistenceException;

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerVO> findContainersInAssignedFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;

	/**
	 * @author a-1936
	 * @param operationalFlightVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> findMailBagsInClosedFlight(OperationalFlightVO operationalFlightVo) throws PersistenceException;

	/**
	 * This method is used to find all the Containers that can be Offloaded for a ParticularFlight
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerVO> findAcceptedContainersForOffload(OffloadFilterVO offloadFilterVO)
			throws PersistenceException;

	Page<ContainerVO> findAcceptedContainersForOffLoad(OffloadFilterVO offloadFilterVO) throws PersistenceException;

	/**
	 * This method is used to find all the DSNS,that can be Offloaded for a ParticularFlight
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<DespatchDetailsVO> findAcceptedDespatchesForOffload(OffloadFilterVO offloadFilterVO)
			throws PersistenceException;

	/**
	 * This method is used to find all the MailBags that can be Offloaded for a ParticularFlight
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<MailbagVO> findAcceptedMailBagsForOffload(OffloadFilterVO offloadFilterVO) throws PersistenceException;

	/**
	 * @author a-1936 This method is used to find the MailBags ...
	 * @param mailbagEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<MailbagVO> findMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws PersistenceException;

	/**
	 * @author a-1936 This method is used to return all the containers which arealready assigned to a particular Flight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerVO> findFlightAssignedContainers(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;

	/**
	 * /
	 * @author a-1936 This method is used to find the containers
	 * @param searchContainerFilterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<ContainerVO> findContainers(SearchContainerFilterVO searchContainerFilterVO, int pageNumber)
			throws PersistenceException;

	/**
	 * @author A-1936 This method is used to findDestinationAssignedContainers
	 * @param destinationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerVO> findDestinationAssignedContainers(DestinationFilterVO destinationFilterVO)
			throws PersistenceException;

	/**
	 * For finds mailbags for manifest Jan 18, 2007, A-1739
	 * @param opFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	MailManifestVO findMailbagManifestDetails(OperationalFlightVO opFlightVO) throws PersistenceException;

	/**
	 * Find finding AWBs for manfist Jan 18, 2007, A-1739
	 * @param opFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	MailManifestVO findMailAWBManifestDetails(OperationalFlightVO opFlightVO) throws PersistenceException;

    MailManifestVO findDSNMailbagManifest(OperationalFlightVO opFlightVO) throws  PersistenceException;

    MailManifestVO findManifestbyDestination(OperationalFlightVO opFlightVO) throws  PersistenceException;

    Collection<AWBDetailVO> findAWBDetailsForFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;
    /**
	 * @author A-1739 Thi smethod is used to find the ArrivedContainers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	List<ContainerDetailsVO> findArrivedContainers(MailArrivalFilterVO mailArrivalFilterVO) throws PersistenceException;

	/**
	 * @author a-1936 ADDED AS THE PART OF NCA-CR findMailDetail This method isused to find out the MailDetais For all MailBags for which Resdits are not sent and having the Search Mode as Despatch..
	 * @param despatchDetailVos
	 * @param unsentResditEvent
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<MailbagVO> findMailDetailsForDespatches(Collection<DespatchDetailsVO> despatchDetailVos,
													   String unsentResditEvent) throws PersistenceException;

	/**
	 * @author a-1936 ADDED AS THE PART OF NCA-CR This method is used to findout the MailDetais For all MailBags for which Resdits are not sent and having the Search Mode as Document..
	 * @param consignmentDocumentVos
	 * @param unsentResditEvent
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<MailbagVO> findMailDetailsForDocument(Collection<ConsignmentDocumentVO> consignmentDocumentVos,
													 String unsentResditEvent) throws PersistenceException;

	/**
	 * @param partyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findPartyName(String companyCode, String partyCode) throws PersistenceException;

	/**
	 * TODO Purpose Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param receptacleID
	 * @return offload reason
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String findOffloadReasonForMailbag(String companyCode, String receptacleID) throws PersistenceException;

	/**
	 * TODO Purpose Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param containerNumber
	 * @return offload reason
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String findOffloadReasonForULD(String companyCode, String containerNumber) throws PersistenceException;

	/**
	 * Find the reason for return of a mailbag Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param receptacleID
	 * @param airportCode TODO
	 * @return the damagereason
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String findDamageReason(String companyCode, String receptacleID, String airportCode) throws PersistenceException;

	/**
	 * @author A-2037 This method is used to find the Damaged Mailbag Details
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<DamagedMailbagVO> findMailbagDamages(String companyCode, String mailbagId) throws PersistenceException;

	/**
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<CarditReferenceInformationVO> findCCForSendResdit(ConsignmentInformationVO consgmntInfo)
			throws PersistenceException;

	/**
	 * @param consignmentInformationVOsForXX
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public HashMap<String, String> findRecepientForXXResdits(
			Collection<ConsignmentInformationVO> consignmentInformationVOsForXX) throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findOnlineFlightsAndConatiners Added by 	:	A-4809 on Sep 30, 2015 Used for 	: Parameters	:	@param companyCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	Collection<ContainerDetailsVO>
	 */
	public List<MailArrivalVO> findOnlineFlightsAndConatiners(String companyCode) throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findFlightsForArrival Added by 	:	A-4809 on Sep 30, 2015 Used for 	: Parameters	:	@param companyCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	Collection<OperationalFlightVO>
	 */
	public Collection<OperationalFlightVO> findFlightsForArrival(String companyCode) throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findArrivalDetailsForReleasingMails Added by 	:	A-4809 on Sep 30, 2015 Used for 	: Parameters	:	@param flightVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	MailArrivalVO
	 */
	public List<ContainerDetailsVO> findArrivalDetailsForReleasingMails(OperationalFlightVO flightVO)
			throws PersistenceException;

	/**
	 * @author A-1885
	 * @param companyCode
	 * @param time
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<OperationalFlightVO> findFlightForMailOperationClosure(String companyCode, int time,
																			 String airportCode) throws PersistenceException;

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findAnyContainerInAssignedFlight(OperationalFlightVO operationalFlightVO) throws PersistenceException;

	/**
	 * @author A-5166Added for ICRD-36146 on 07-Mar-2013
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<OperationalFlightVO> findImportFlghtsForArrival(String companyCode) throws PersistenceException;

	/**
	 * @param dSNEnquiryFilterVO
	 * @param pageNumber
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Page<DespatchDetailsVO> findDSNs(DSNEnquiryFilterVO dSNEnquiryFilterVO, int pageNumber)
			throws PersistenceException;
	/**
	 * Find the PA corresponding to a Mailbox ID MAY 23, 2016, a-5526
	 * @param companyCode
	 * @param mailboxId
	 * @return the PA Code
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String findPAForMailboxID(String companyCode, String mailboxId, String originOE) throws PersistenceException;

	/**
	 * A-1739
	 * @param companyCode
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	void invokeResditReceiver(String companyCode) throws PersistenceException;

	/**
	 * Checks for any flagged resdit events and returns them Sep 8, 2006, a-1739
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ResditEventVO> findResditEvents(String companyCode) throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findMailBagsForTransportCompletedResdit Added by 	: Used for 	: Parameters	:	@param operationalFlightVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	Collection<MailbagVO>
	 */
	public Collection<MailbagVO> findMailBagsForTransportCompletedResdit(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findUldsForTransportCompletedResdit Added by 	: Used for 	: Parameters	:	@param operationalFlightVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException Return type	: 	Collection<ContainerDetailsVO>
	 */
	public Collection<ContainerDetailsVO> findUldsForTransportCompletedResdit(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;

	/**
	 * @param handoverVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<OperationalFlightVO> findOperationalFlightForMRD(HandoverVO handoverVO)
			throws PersistenceException;

	/**
	 * @param resditEvents
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<CarditVO> findCarditDetailsForResdit(Collection<ResditEventVO> resditEvents)
			throws PersistenceException;

	/**
	 * @param carditEnqFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<ConsignmentRoutingVO> findConsignmentRoutingDetails(CarditEnquiryFilterVO carditEnqFilterVO)
			throws PersistenceException;
	/**
	 * @param carditEnqFilterVO
	 * @param airport
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<TransportInformationVO> findRoutingDetailsFromCardit(CarditEnquiryFilterVO carditEnqFilterVO,
																		   String airport) throws PersistenceException;

	/**
	 * @param resditEventVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ReceptacleInformationVO> findMailbagDetailsForResdit(ResditEventVO resditEventVO)
			throws PersistenceException;

	/**
	 * @param resditEventVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ReceptacleInformationVO> findMailbagDetailsForXXResdit(ResditEventVO resditEventVO)
			throws PersistenceException;

	/**
	 * @param resditEventVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<TransportInformationVO> findTransportDetailsForULD(ResditEventVO resditEventVO)
			throws PersistenceException;

	/**
	 * @param resditEventVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerInformationVO> findULDDetailsForResdit(ResditEventVO resditEventVO) throws PersistenceException;

	/**
	 * @param resditEventVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerInformationVO> findULDDetailsForResditWithoutCardit(ResditEventVO resditEventVO)
			throws PersistenceException;
	/**
	 * @author a-1936This  method is used to find the mailbags in the Container for the Manifest
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(Collection<ContainerDetailsVO> containers)
			throws PersistenceException;

	/**
	 * @author A-6371For fetching mailonhandlist
	 */
	public Page<MailOnHandDetailsVO> findMailOnHandDetails(SearchContainerFilterVO searchContainerFilterVO,
														   int pageNumber) throws PersistenceException;

	/**
	 * @author A-7871 for ICRD-257316
	 */
	public int findMailbagcountInContainer(ContainerVO containerVO);

	/**
	 * @author A-5526
	 * @param fileUploadFilterVO
	 * @return
	 */
	public String processMailOperationFromFile(FileUploadFilterVO fileUploadFilterVO) throws PersistenceException;

	/**
	 * fetchDataForOfflineUpload
	 * @param companyCode
	 * @param fileType
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailUploadVO> fetchDataForOfflineUpload(String companyCode, String fileType)
			throws PersistenceException;

	/**
	 * removeDataFromTempTable
	 * @param fileUploadFilterVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO) throws PersistenceException;

	/**
	 * @author a-7794 This method is used to list the Audit details
	 * @param mailAuditFilterVO
	 * @return Collection<AuditDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException ICRD-229934
	 */
	Collection<AuditDetailsVO> findCONAuditDetails(MailAuditFilterVO mailAuditFilterVO) throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findMailbagIdForMailTag Added by 	:	a-6245 on 22-Jun-2017 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException  Return type	: 	MailbagVO
	 */
	public MailbagVO findMailbagIdForMailTag(MailbagVO mailbagVO) throws PersistenceException;
	/**
	 * Method		:	MailTrackingDefaultsDAO.findAgentCodeForPA Added by 	:	U-1267 on Nov 1, 2017 Used for 	:	findAgentCodeForPA Parameters	:	@param companyCode Parameters	:	@param officeOfExchange Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException  Return type	: 	String
	 */
	public String findAgentCodeForPA(String companyCode, String officeOfExchange) throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findMailbagVOsForDsnVOs Added by 	:	U-1267 on 08-Nov-2017 Used for 	:	ICRD-211205 Parameters	:	@param containerDetailsVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException  Return type	: 	Collection <MailbagVO>
	 */
	public Collection<MailbagVO> findMailbagVOsForDsnVOs(ContainerDetailsVO containerDetailsVO)
			throws PersistenceException;

	/**
	 * @author A-8061
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagHistoryVO> findMailbagResditEvents(String companyCode, String mailbagId)
			throws PersistenceException;

	/**
	 * @author A-8061
	 * @param carditEnquiryFilterVO
	 * @return
	 */
	public String[] findGrandTotals(CarditEnquiryFilterVO carditEnquiryFilterVO) throws PersistenceException;

	public Page<MailAcceptanceVO> findOutboundFlightsDetails(OperationalFlightVO operationalFlightVO, int pageNumber)
			throws PersistenceException;

	public Page<ContainerDetailsVO> findContainerDetails(OperationalFlightVO operationalFlightVO, int pageNumber)
			throws PersistenceException;

	public Page<MailbagVO> findMailbagsinContainer(ContainerDetailsVO containerDetailsVO, int pageNumber)
			throws PersistenceException;

	public Page<DSNVO> findMailbagsinContainerdsnview(ContainerDetailsVO containerDetailsVO, int pageNumber)
			throws PersistenceException;

	public MailbagVO findCarditSummaryView(CarditEnquiryFilterVO carditEnquiryFilterVO) throws PersistenceException;

	public Page<MailbagVO> findGroupedCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws PersistenceException;

	public MailbagVO findLyinglistSummaryView(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws PersistenceException;

	public Page<MailbagVO> findGroupedLyingList(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws PersistenceException;

	public Page<MailAcceptanceVO> findOutboundCarrierDetails(OperationalFlightVO operationalFlightVO, int pageNumber)
			throws PersistenceException;

	public Page<MailbagVO> getMailbagsinCarrierContainer(ContainerDetailsVO containerDetailsVO, int pageNumber)
			throws PersistenceException;

	public Page<DSNVO> getMailbagsinCarrierContainerdsnview(ContainerDetailsVO containerDetailsVO, int pageNumber)
			throws PersistenceException;

	public Collection<DSNVO> getDSNsForContainer(ContainerDetailsVO containerDetailsVO) throws PersistenceException;

	public Collection<DSNVO> getDSNsForCarrier(ContainerDetailsVO containerDetailsVO) throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.listFlightDetails Added by 	:	A-8164 on 25-Sep-2018 Used for 	: Parameters	:	@param mailArrivalVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	Collection<MailArrivalVO>
	 */
	public Page<MailArrivalVO> listFlightDetails(MailArrivalVO mailArrivalVO);

	public Collection<MailArrivalVO> listManifestDetails(MailArrivalVO mailArrivalVO);

	/**
	 * Method		:	MailTrackingDefaultsDAO.findArrivedContainersForInbound Added by 	:	A-8164 on 29-Dec-2018 Used for 	: Parameters	:	@param mailArrivalFilterVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	Collection<ContainerDetailsVO>
	 */
	public Page<ContainerDetailsVO> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO);

	/**
	 * Method		:	MailTrackingDefaultsDAO.findArrivedMailbagsForInbound Added by 	:	A-8164 on 29-Dec-2018 Used for 	: Parameters	:	@param mailArrivalFilterVO Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	Collection<ContainerDetailsVO>
	 */
	public Page<MailbagVO> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO);

	/**
	 * Method		:	MailTrackingDefaultsDAO.findArrivedDsnsForInbound Added by 	:	A-8164 on 29-Dec-2018 Used for 	: Parameters	:	@param mailArrivalFilterVO Parameters	:	@return  Return type	: 	Collection<DSNVO>
	 */
	public Page<DSNVO> findArrivedDsnsForInbound(MailArrivalFilterVO mailArrivalFilterVO);

	/**
	 * Method		:	MailTrackingDefaultsDAO.listCarditDsnDetails Added by 	:	A-8164 on 04-Sep-2019 Used for 	:	List Cardit DSN Details Parameters	:	@param dsnEnquiryFilterVO Parameters	:	@return  Return type	: 	Page<DSNVO>
	 * @throws SystemException
	 */
	public Page<DSNVO> listCarditDsnDetails(DSNEnquiryFilterVO dsnEnquiryFilterVO);

	/**
	 * Method		:	MailTrackingDefaultsDAO.findRunnerFlights Added by 	:	A-5526 on 12-Oct-2018 Used for 	:   ICRD-239811 Parameters	:	@param runnerFlightFilterVO Parameters	:	@return Return type	: 	Page<RunnerFlightVO>
	 */
	public Page<RunnerFlightVO> findRunnerFlights(RunnerFlightFilterVO runnerFlightFilterVO)
			throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findRunnerFlights Added by 	:	A-5526 on 12-Oct-2018 Used for 	:   ICRD-239811 Parameters	:	@param RunnerFlightVO, RunnerFlightULDVO Parameters	:	@return Return type	: 	ContainerVO
	 */
	public ContainerVO findContainerDetails(RunnerFlightVO runnerFlightVO, RunnerFlightULDVO runnerFlightULDVO)
			throws PersistenceException;
	/**
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureApplicableMails(ForceMajeureRequestFilterVO filterVO,
																	   int pageNumber) throws PersistenceException;

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String saveForceMajeureRequest(ForceMajeureRequestFilterVO filterVO) throws PersistenceException;

	/**
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureDetails(ForceMajeureRequestFilterVO filterVO, int pageNumber)
			throws PersistenceException;

	/**
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureRequestIds(ForceMajeureRequestFilterVO filterVO, int pageNumber)
			throws PersistenceException;

	/**
	 * @param requestVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String updateForceMajeureRequest(ForceMajeureRequestFilterVO requestVO) throws PersistenceException;

	/**
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerVO> findAllContainersInAssignedFlight(OperationalFlightVO operationalFlightVO)
			throws PersistenceException;
	/**
	 * @author A-7794
	 * @param fileUploadFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) throws PersistenceException;

	/**
	 * @author A-7794
	 * @param companyCode
	 * @param fileType
	 * @return
	 * @throws SystemException
	 */
	public Collection<ConsignmentDocumentVO> fetchMailDataForOfflineUpload(String companyCode, String fileType);

	public List<MailMonitorSummaryVO> getServiceFailureDetails(MailMonitorFilterVO filterVO)
			throws PersistenceException;

	public List<MailMonitorSummaryVO> getOnTimePerformanceDetails(MailMonitorFilterVO filterVO)
			throws PersistenceException;

	public List<MailMonitorSummaryVO> getForceMajeureCountDetails(MailMonitorFilterVO filterVO)
			throws PersistenceException;

	public Page<MailbagVO> getPerformanceMonitorMailbags(MailMonitorFilterVO filterVO, String type, int pageNumber)
			throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findMailbagDetailsForMailbagEnquiryHHT Added by 	:   A-8464 on 26-Mar-2018 Used for 	:	ICRD-273761 Parameters	:	@param mailbagEnquiryFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public MailbagVO findMailbagDetailsForMailbagEnquiryHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findDuplicateMailbag Added by 	:	A-7531 on 16-May-2019 Used for 	: Parameters	:	@param companyCode Parameters	:	@param mailBagId Parameters	:	@return  Return type	: 	ArrayList<MailbagVO>
	 */
	public ArrayList<MailbagVO> findDuplicateMailbag(String companyCode, String mailBagId);

	public Page<MailbagVO> findDeviationMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.getTempCarditMessages Added by 	:	A-6287 on 01-Mar-2020 Used for 	: Parameters	:	@param companyCode Parameters	:	@return  Return type	: 	Collection<CarditTempMsgVO>
	 * @throws SystemException
	 */
	public Collection<CarditTempMsgVO> getTempCarditMessages(String companyCode, String includeMailBoxIdr,
															 String excludeMailBoxIdr, String includedOrigins, String excludedOrigins, int pageSize, int noOfDays);

	public int findbulkcountInFlight(ContainerDetailsVO containerDetailsVO);
	/**
	 * @author a-9529This method  is used to find out the Mail Bags in the Containers from in bound react screen
	 * @param containers
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ContainerDetailsVO> findMailbagsInContainerFromInboundForReact(Collection<ContainerDetailsVO> containers)
			throws PersistenceException;

	public String findContainerInfoForDeviatedMailbag(ContainerDetailsVO containerDetailsVO, long mailSequenceNumber);

	/**
	 * @return
	 * @throws SystemException
	 */
	public Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode, String mailBagId,
																			 long mailSequenceNumber) throws PersistenceException;

	/**
	 * @author A-8353
	 * @return
	 * @throws SystemException
	 */
	public String checkMailInULDExistForNextSeg(String containerNumber, String airpotCode, String companyCode);

	/**
	 * @param consignmentNumber
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ConsignmentDocumentVO findConsignmentScreeningDetails(String consignmentNumber, String companyCode,
																 String poaCode) throws PersistenceException;

	/**
	 * @param mailbagVO
	 * @return
	 * @throws SystemException
	 */
	public MailbagVO findMailbagBillingStatus(MailbagVO mailbagVO);

	public String saveUploadedForceMajeureData(FileUploadFilterVO fileUploadFilterVO);

	public Collection<ImportFlightOperationsVO> findInboundFlightOperationsDetails(ImportOperationsFilterVO filterVO,
																				   Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException;

	public Collection<OffloadULDDetailsVO> findOffloadULDDetailsAtAirport(
			com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO filterVO) throws PersistenceException;

	public Collection<ManifestVO> findExportFlightOperationsDetails(ImportOperationsFilterVO filterVO,
																	Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException;

	public Collection<ConsignmentDocumentVO> fetchConsignmentDetailsForUpload(FileUploadFilterVO fileUploadFilterVO);

	public Collection<ContainerDetailsVO> findContainerJourneyID(ConsignmentFilterVO consignmentFilterVO);

	public Collection<MailbagVO> getFoundArrivalMailBags(MailArrivalVO mailArrivalVO) throws PersistenceException;

	/**
	 * @author A-9084
	 * @param mailAuditFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterVO mailAuditFilterVO);

	public Collection<MailAcceptanceVO> findContainerVOs(MailAcceptanceVO mailAcceptanceVO) throws PersistenceException;

	public MailbagVO listmailbagSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo);

	public Collection<MailInConsignmentVO> findMailInConsignment(ConsignmentFilterVO consignmentFilterVO);

	/**
	 * @author A-8353
	 * @param cmpcod
	 * @param malSeqNum
	 * @return
	 * @throws SystemException
	 */
	public ConsignmentScreeningVO findRegulatedCarrierForMailbag(String cmpcod, long malSeqNum);

	/**
	 * @author A-10647
	 * @return FlightLoadPlanContainerVOs
	 * @throws SystemException
	 */
	public Collection<FlightLoadPlanContainerVO> findPreviousLoadPlanVersionsForContainer(
			FlightLoadPlanContainerVO loadPlanVO);

	/**
	 * @author A-9477
	 * @return FlightLoadPlanContainerVO
	 * @throws SystemException
	 */
	Collection<FlightLoadPlanContainerVO> findLoadPlandetails(SearchContainerFilterVO searchContainerFilterVO);

	public Collection<ConsignmentScreeningVO> findRAacceptingForMailbag(String companyCode, long mailSequenceNumber);

	public String findRoutingDetailsForMailbag(String companyCode, long malseqnum, String airportCode);

	public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> fetchMailIndicatorForProgress(
			Collection<FlightListingFilterVO> flightListingFilterVOs);

	public Collection<ConsignmentDocumentVO> findCN46TransferManifestDetails(TransferManifestVO transferManifestVO);
	/**
	 * @author A-10555
	 * @param flightFilterVOs
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailAcceptanceVO> fetchFlightPreAdviceDetails(Collection<FlightFilterVO> flightFilterVOs)
			throws PersistenceException;

	public Collection<OperationalFlightVO> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO);

	Page<MailTransitVO> findMailTransit(MailTransitFilterVO mailTransitFilterVO, int pageNumber)
			throws PersistenceException;

	/**
	 * Method		:	MailTrackingDefaultsDAO.findMailbagDetailsForMailInboundHHT Parameters	:	@throws SystemException Parameters	:	@throws PersistenceException
	 */
	public MailbagVO findMailbagDetailsForMailInboundHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
			throws PersistenceException;

	MailbagVO findMailConsumed(MailTransitFilterVO filterVo);

	Boolean isMailAsAwb(MailbagVO mailbagVO);

    MailbagVO findLatestFlightDetailsOfMailbag(MailbagVO mailbagVO);

    ConsignmentDocumentVO  generateConsignmentSummaryReport(ConsignmentFilterVO consignmentFilterVO);

	TransferManifestVO generateTransferManifestReport(String companyCode,String transferManifestId)
			throws SystemException,PersistenceException;

    ConsignmentDocumentVO generateConsignmentSecurityReportDtls(ConsignmentFilterVO consignmentFilterVO);
	public Collection<MailbagVO> findMailTagDetails(Collection<MailbagVO> mailbagVOs)throws SystemException,
			PersistenceException;

	ConsignmentDocumentVO generateConsignmentReport(ConsignmentFilterVO consignmentFilterVO) throws SystemException;
    String findRoutingDetails(String companyCode,long malseqnum)  throws SystemException;

	Collection<ConsignmentDocumentVO> findTransferManifestConsignmentDetails(TransferManifestVO transferManifestVO) throws SystemException;

	Collection<ConsignmentDocumentVO> generateCN46ConsignmentReport(ConsignmentFilterVO consignmentFilterVO);

	Collection<ConsignmentDocumentVO> generateCN46ConsignmentSummaryReport(ConsignmentFilterVO consignmentFilterVO);

	Page<ContainerVO> findContainerDetailsForReassignment(SearchContainerFilterVO searchContainerFilterVO, int pageNumber);

	Collection<SecurityScreeningValidationVO> checkForSecurityScreeningValidation(SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO);

	List<TransferManifestVO> findTransferManifestDetailsForTransfer(String companyCode,String tranferManifestId)throws SystemException, PersistenceException;

	Collection<MailHandedOverVO> generateMailHandedOverReport(MailHandedOverFilterVO mailHandedOverFilterVO) throws SystemException, PersistenceException;

	Collection<DamagedMailbagVO> findDamageMailReport(DamageMailFilterVO damageMailFilterVO) throws SystemException, PersistenceException;
	Collection<MailStatusVO> generateMailStatusReport(MailStatusFilterVO mailStatusFilterVO) throws SystemException, PersistenceException;

	Collection<DailyMailStationReportVO> generateDailyMailStationReport(DailyMailStationFilterVO filterVO)
			throws SystemException;

	MailManifestVO findImportManifestDetails(OperationalFlightVO operationalFlightVo) throws SystemException,PersistenceException;
	public HashMap<String, String> findPAForShipperbuiltULDs(
			Collection<UldResditVO> uldResditVOs,boolean isFromCardit)	throws SystemException,PersistenceException;
	Collection<MailResditVO> findResditFlightDetailsForMailbag (
			MailResditVO mailResditVO) throws SystemException,PersistenceException;


}

