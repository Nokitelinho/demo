package com.ibsplc.neoicargo.mail;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mailsecurityandscreening.SecurityAndScreeningMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.FlightListingFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportFlightOperationsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.operations.vo.StorageUnitCheckinVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.LocationEnquiryFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.lang.notation.apis.PrivateAPI;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.*;
import com.ibsplc.neoicargo.mail.model.*;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author a-1303
 */
@PrivateAPI
public interface MailOperationsAPI {
	/**
	 *
	 * @param mailScanDetailsModel
	 */
	@Path("/v1/mail/savemailscanneddetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveMailScannedDetails(Collection<MailScanDetailModel> mailScanDetailsModel);

	@Path("/v1/mail/updateghtformailbags")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void updateGHTformailbags(Collection<OperationalFlightModel> operationalFlightsModel);

	/**
	 *
	 * @param mailBagsModel
	 * @param scanningPort
	 * @return
	 */
	@Path("/v1/mail/savemailuploaddetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public ScannedMailDetailsModel saveMailUploadDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<MailUploadModel> mailBagsModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String scanningPort) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException;

	@Path("/v1/mail/savemaildetailsfromjob")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void saveMailDetailsFromJob(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<MailUploadModel> mailBagsModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String scanningPort) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException;

	/**
	 * MTK064
	 */
	@Path("/v1/mail/delivermailbags")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void deliverMailbags(MailArrivalModel mailArrivalModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param deliverVosForSaveModel
	 */
	@Path("/v1/mail/savescanneddelivermails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveScannedDeliverMails(Collection<MailArrivalModel> deliverVosForSaveModel) throws BusinessException;

	/**
	 *
	 * @param mailAcceptanceModel
	 * @return
	 */
	@Path("/v1/mail/saveacceptancedetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<ScannedMailDetailsModel> saveAcceptanceDetails(MailAcceptanceModel mailAcceptanceModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param offloadVoModel
	 * @return
	 */
	@Path("/v1/mail/offload")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<ContainerDetailsModel> offload(OffloadModel offloadVoModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param mailbagsToReturnModel
	 * @return
	 */
	@Path("/v1/mail/returnmailbags")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<ContainerDetailsModel> returnMailbags(Collection<MailbagModel> mailbagsToReturnModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param scannedMailDetailsModel
	 */
	@Path("/v1/mail/saveandprocessmailbags")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveAndProcessMailBags(ScannedMailDetailsModel scannedMailDetailsModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException;

	/**
	 *
	 * @param flightFilterVO
	 * @return
	 */
	@Path("/v1/mail/validateflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<FlightValidationVO> validateFlight(FlightFilterVO flightFilterVO);

	/** 
	* Method : MailTrackingDefaultsBI.validateMailFlight Added by : A-5160 on 26-Nov-2014 Used for : validate flight for mail Parameters : @param flightFilterVO Parameters : @return Parameters : @throws RemoteException Parameters : @throws SystemException Return type : Collection
	*/
	@Path("/v1/mail/validatemailflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<FlightValidationVO> validateMailFlight(FlightFilterVO flightFilterVO);

	/**
	 *
	 * @param filterVO
	 * @return
	 */
	@Path("/v1/mail/findwarehousetransactionlocations")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Map<String, Collection<String>> findWarehouseTransactionLocations(LocationEnquiryFilterVO filterVO);

	/**
	 *
	 * @param containersModel
	 * @return
	 */
	@Path("/v1/mail/findmailbagsincontainer")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<ContainerDetailsModel> findMailbagsInContainer(Collection<ContainerDetailsModel> containersModel);

	/**
	 *
	 * @param companyCode
	 * @param airportCode
	 * @return
	 */
	@Path("/v1/mail/findallwarehouses")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Collection<WarehouseVO> findAllWarehouses(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String airportCode);

	/**
	 *
	 * @param airportCode
	 * @param containerModel
	 * @return
	 */
	@Path("/v1/mail/validatecontainer")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	ContainerModel validateContainer(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String airportCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) ContainerModel containerModel) throws BusinessException;

	/**
	 *
	 * @param operationalFlightModel
	 * @return
	 */
	@Path("/v1/mail/isflightclosedformailoperations")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	boolean isFlightClosedForMailOperations(OperationalFlightModel operationalFlightModel);

	/**
	 * MTK064
	 */
	@Path("/v1/mail/validatemailbags")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	boolean validateMailBags(Collection<MailbagModel> mailbagVosModel) throws BusinessException;

	/** 
	* @author a-1936 This method is used to validate the WareHouse Location
	* @param companyCode
	* @param airportCode
	* @param warehouseCode
	* @param locationCode
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/validatelocation")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	LocationValidationVO validateLocation(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String airportCode,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String warehouseCode,
			@Multipart(value = "3", type = MediaType.APPLICATION_JSON) String locationCode);

	/**
	 *
	 * @param companyCode
	 * @param despatchDetailssModel
	 * @return
	 */
	@Path("/v1/mail/validateconsignmentdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<DespatchDetailsModel> validateConsignmentDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) Collection<DespatchDetailsModel> despatchDetailssModel);

	/**
	 *
	 * @param dsnVosModel
	 * @return
	 */
	@Path("/v1/mail/validatedsns")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	boolean validateDSNs(Collection<DSNModel> dsnVosModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param reassignedFlightValidationVO
	 * @return
	 */
	@Path("/v1/mail/findalluldsinassignedflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ContainerModel> findAllULDsInAssignedFlight(FlightValidationVO reassignedFlightValidationVO);

	@Path("/v1/mail/reassigncontainers")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void reassignContainers(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<ContainerModel> containersToReassignModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) OperationalFlightModel toFlightModel) throws MailOperationsBusinessException;

	/** 
	* @author A-2037 This method is used to find Preadvice for outbound mailand it gives the details of the ULDs and the receptacles based on CARDIT

	*/
	@Path("/v1/mail/findpreadvice")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public PreAdviceModel findPreAdvice(OperationalFlightModel operationalFlightModel);

	/** 
	* @author a-1936This method is used to find the Transfer Manifest for the Different Transactions
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/findtransfermanifest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Page<TransferManifestModel> findTransferManifest(TransferManifestFilterModel tranferManifestFilterVoModel);

	/** 
	* @author a-1936 This method is used to find the containers and itsassociated DSNs in the Flight.
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/findcontainersinflightformanifest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailManifestModel findContainersInFlightForManifest(OperationalFlightModel operationalFlightVoModel);

	/** 
	* @author a-1883
	* @param companyCode
	* @param airportCode
	* @param isGHA
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/findstockholderformail")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findStockHolderForMail(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String airportCode,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) Boolean isGHA);

	/** 
	* @author a-1883
	* @return AWBDetailVO
	* @throws SystemException
	*/
	@Path("/v1/mail/findawbdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public AWBDetailModel findAWBDetails(AWBFilterModel aWBFilterModel) throws MailOperationsBusinessException;

	/** 
	* This method is used to find the Offload Details for a Flight say at different levels say Containers,DSNS,MailBags
	* @return
	* @throws SystemException
	*/
	// TODO to be corrected ---renaming due to similar method conflict
	@Path("/v1/mail/findoffloaddetailss")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public OffloadModel findOffloadDetails(OffloadFilterModel offloadFilterModel);

	/** 
	* @author a-1936 This method is used to reopen the Flight
	* @throws SystemException
	*/
	@Path("/v1/mail/reopenflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void reopenFlight(OperationalFlightModel operationalFlightModel);

	/**
	 *
	 * @param operationalFlightModel
	 * @param mailAcceptanceModel
	 */
	@Path("/v1/mail/closeflightacceptance")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void closeFlightAcceptance(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) OperationalFlightModel operationalFlightModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) MailAcceptanceModel mailAcceptanceModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param mailbagEnquiryFilterModel
	 * @param pageNumber
	 * @return
	 */
	@Path("/v1/mail/findmailbags")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailbagModel> findMailbags(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailbagEnquiryFilterModel mailbagEnquiryFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);


	/**
	 *
	 * @param containersModel
	 * @param operationalFlightModel
	 * @param printFlag
	 * @return
	 */
	@Path("/v1/mail/transfercontainers")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	TransferManifestModel transferContainers(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<ContainerModel> containersModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) OperationalFlightModel operationalFlightModel,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String printFlag) throws MailOperationsBusinessException;

	/** 
	* MTK064
	*/
	@Path("/v1/mail/findmailbaghistories")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Collection<MailbagHistoryModel> findMailbagHistories(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String mailBagId,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) long mailSequenceNumber);

	@Path("/v1/mail/findmailbagnotes")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<MailHistoryRemarksModel> findMailbagNotes(String mailBagId);

	@Path("/v1/mail/findmailbaghistoriesfromwebscreen")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Collection<MailbagHistoryModel> findMailbagHistoriesFromWebScreen(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON)String mailBagId,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) long mailSequenceNumber);

	/**
	 *
	 * @param containerDetailssModel
	 */
	@Path("/v1/mail/unassignemptyulds")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void unassignEmptyULDs(Collection<ContainerDetailsModel> containerDetailssModel);

	/**
	 * MTK064
	 */
	@Path("/v1/mail/findflightassignedcontainers")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ContainerModel> findFlightAssignedContainers(OperationalFlightModel operationalFlightModel);

	/** 
	* @author a-1936 This method is used to return all the containers assignedto a particular destination and Carrier
	* @return Collection<ContainerVO>
	* @throws SystemException
	*/
	@Path("/v1/mail/finddestinationassignedcontainers")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ContainerModel> findDestinationAssignedContainers(DestinationFilterModel destinationFilterModel);

	/**
	 *
	 * @param mailbagsToReassignModel
	 * @param toContainerModel
	 * @return
	 */
	@Path("/v1/mail/reassignmailbags")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<ContainerDetailsModel> reassignMailbags(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<MailbagModel> mailbagsToReassignModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) ContainerModel toContainerModel) throws MailOperationsBusinessException;

	/**
	 * MTK064
	 */
	@Path("/v1/mail/autoacquitcontainers")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void autoAcquitContainers(Collection<ContainerDetailsModel> conatinerstoAcquitModel);

	/**
	 *
	 * @param containerNumber
	 * @return
	 */
	@Path("/v1/mail/findlatestcontainerassignment")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public ContainerAssignmentModel findLatestContainerAssignment(String containerNumber) throws MailOperationsBusinessException;

	/**
	 *
	 * @param mailArrivalsModel
	 */
	@Path("/v1/mail/savechangeflightdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void saveChangeFlightDetails(Collection<MailArrivalModel> mailArrivalsModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param carditEnquiryModel
	 */
	@Path("/v1/mail/sendresdit")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void sendResdit(CarditEnquiryModel carditEnquiryModel) throws MailOperationsBusinessException;


	/** 
	* @author a-1883 This method returns Consignment Details
	* @return ConsignmentDocumentVO
	* @throws SystemException
	*/
	@Path("/v1/mail/findconsignmentdocumentdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public ConsignmentDocumentModel findConsignmentDocumentDetails(ConsignmentFilterModel consignmentFilterModel);

	/**
	 *
	 * @param containersModel
	 */
	@Path("/v1/mail/deletecontainers")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void deleteContainers(Collection<ContainerModel> containersModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param consignmentDocumentModel
	 */
	@Path("/v1/mail/deleteconsignmentdocumentdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void deleteConsignmentDocumentDetails(ConsignmentDocumentModel consignmentDocumentModel) throws MailOperationsBusinessException;

	/**
	 * MTK064
	 */
	@Path("/v1/mail/savedamagedetailsformailbag")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void saveDamageDetailsForMailbag(Collection<MailbagModel> mailbagsModel);

	/**
	 *
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 */
	@Path("/v1/mail/findmailbagdamages")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Collection<DamagedMailbagModel> findMailbagDamages(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String mailbagId);

	/**
	 *
	 * @param searchContainerFilterModel
	 * @param pageNumber
	 * @return
	 */
	@Path("/v1/mail/findcontainers")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Page<ContainerModel> findContainers(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) SearchContainerFilterModel searchContainerFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/checkembargoformail")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<EmbargoDetailsVO> checkEmbargoForMail(Collection<ShipmentDetailsVO> shipmentDetailsVos);

	/**
	 *
	 * @param carditEnquiryFilterModel
	 * @param pageNumber
	 * @return
	 */
	@Path("/v1/mail/findcarditmails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailbagModel> findCarditMails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) CarditEnquiryFilterModel carditEnquiryFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber)throws BusinessException;

	/**
	 * MTK064
	 */
	@Path("/v1/mail/findarrivaldetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	MailArrivalModel findArrivalDetails(MailArrivalFilterModel mailArrivalFilterModel);

	/**
	 * MTK064
	 */
	@Path("/v1/mail/validateinboundflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	OperationalFlightModel validateInboundFlight(OperationalFlightModel flightModel);

	/**
	 * MTK064
	 */
	@Path("/v1/mail/savearrivaldetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void saveArrivalDetails(MailArrivalModel mailArrivalModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param operationalFlightModel
	 * @return
	 */
	@Path("/v1/mail/isflightclosedforinboundoperations")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	boolean isFlightClosedForInboundOperations(OperationalFlightModel operationalFlightModel);

	/**
	 * MTK064
	 */
	@Path("/v1/mail/undoarrivecontainer")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void undoArriveContainer(MailArrivalModel mailarrivalvoModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param operationalFlightModel
	 * @return
	 */
	@Path("/v1/mail/findmaildiscrepancies")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<MailDiscrepancyModel> findMailDiscrepancies(OperationalFlightModel operationalFlightModel);

	/**
	 *
	 * @param consignmentDocumentModel
	 * @return
	 */
	@Path("/v1/mail/saveconsignmentdocument")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Integer saveConsignmentDocument(ConsignmentDocumentModel consignmentDocumentModel) throws BusinessException;

	/**
	 *
	 * @param consignmentFilterModel
	 * @return
	 */
	@Path("/v1/mail/findcartids")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<MailbagModel> findCartIds(ConsignmentFilterModel consignmentFilterModel);

	/**
	 * MTK064
	 */
	@Path("/v1/mail/transfermail")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	TransferManifestModel transferMail(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON, required = false) Collection<DespatchDetailsModel> despatchDetailssModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) Collection<MailbagModel> mailbagsModel,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) ContainerModel containerModel,
			@Multipart(value = "3", type = MediaType.APPLICATION_JSON) String toPrint) throws MailOperationsBusinessException;

	@Path("/v1/mail/saveconsignmentformanifesteddsn")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveConsignmentForManifestedDSN(ConsignmentDocumentModel consignmentDocumentModel) throws MailOperationsBusinessException;

	/** 
	* Method		:	MailTrackingDefaultsBI.saveMailUploadDetailsFromMLD Added by 	:	A-4803 on 14-Oct-2014 Used for 	:   Saving details from MLD messages Parameters	:	@param mldMasterVOs Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	void
	* @throws MailHHTBusniessException
	*/
	@Path("/v1/mail/savemailuploaddetailsfrommld")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Map<String, Collection<MLDMasterModel>> saveMailUploadDetailsFromMLD(
			Collection<MLDMasterModel> mldMastersModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException;


	@Path("/v1/mail/initiateuldacquittance")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void initiateULDAcquittance(OperationalFlightModel operationalFlightModel);

	/** 
	* @author A-5526This method is to send MLD messages
	* @param companyCode
	* @throws SystemException
	*/
	@Path("/v1/mail/triggermldmessages")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void triggerMLDMessages(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int recordCount);

	/** 
	* Close inbound flight for mail operation.
	* @param companyCode the company code
	* @throws SystemException the system exception
	*/
	@Path("/v1/mail/closeinboundflightformailoperation")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void closeInboundFlightForMailOperation(String companyCode);

	/** 
	* @author A-1885
	* @param companyCode
	* @param time
	* @throws SystemException
	*/
	@Path("/v1/mail/closeflightformailoperation")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void closeFlightForMailOperation(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int time,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String airportCode);

	/** 
	* @author A-5166Added for ICRD-36146 on 07-Mar-2013
	* @throws SystemException
	*/
	@Path("/v1/mail/initiatearrivalforflights")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void initiateArrivalForFlights(ArriveAndImportMailModel arriveAndImportMailModel);

	/**
	* @param pageNumber
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/finddsns")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Page<DespatchDetailsModel> findDSNs(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) DSNEnquiryFilterModel dSNEnquiryFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	/**
	 *
	 * @param ediInterchangeModel
	 * @return
	 */
	@Path("/v1/mail/savecarditmessages")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<ErrorVO> saveCarditMessages(EDIInterchangeModel ediInterchangeModel) throws BusinessException, InvocationTargetException, IllegalAccessException;

	/**
	 *
	 * @param mailbagsModel
	 * @return
	 */
	@Path("/v1/mail/validatescannedmailbagdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<MailbagModel> validateScannedMailbagDetails(
			Collection<MailbagModel> mailbagsModel) throws MailOperationsBusinessException;

	/** 
	* Invokes the RDT_EVT_RCR procedure A-1739
	* @param companyCode the company code
	* @throws SystemException
	*/
	@Path("/v1/mail/invokeresditreceiver")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void invokeResditReceiver(String companyCode);

	/** 
	* Starts resdit building Sep 8, 2006, a-1739
	* @param companyCode
	* @throws SystemException
	*/
	@Path("/v1/mail/checkforresditevents")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<ResditEventModel> checkForResditEvents(String companyCode);

	/** 
	* @author A-1936 This method is used to find the MailBags Accepted to aParticularFlight and Flag the Uplifted Resdits for the Same
	* @throws SystemException
	*/
	@Path("/v1/mail/flagupliftedresditformailbags")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void flagUpliftedResditForMailbags(Collection<OperationalFlightModel> operationalFlightsModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.flagTransportCompletedResditForMailbags Added by 	: Used for 	: Parameters	:	@param operationalFlightVOs Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	void
	*/
	@Path("/v1/mail/flagtransportcompletedresditformailbags")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void flagTransportCompletedResditForMailbags(Collection<OperationalFlightModel> operationalFlightsModel);

	/**
	 *
	 * @param containersModel
	 * @return
	 */
	@Path("/v1/mail/findcontainerassignments")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Map<String, ContainerAssignmentModel> findContainerAssignments(Collection<ContainerModel> containersModel);

	/**
	 *
	 * @param resditMessageVO
	 */
	@Path("/v1/mail/updateresditsendstatus")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void updateResditSendStatus(ResditMessageVO resditMessageVO);

	/**
	 *
	 * @param messageModel
	 * @return
	 */
	@Path("/v1/mail/handlemrdmessage")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ErrorVO> handleMRDMessage(MailMRDModel messageModel) throws MailHHTBusniessException, MailOperationsBusinessException;

	/**
	 *
	 * @param resditEventsModel
	 */
	@Path("/v1/mail/buildresdit")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void buildResdit(Collection<ResditEventModel> resditEventsModel);

	/** 
	* @param companyCode
	* @param txnId
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/gettxnparameters")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Object[] getTxnParameters(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String txnId);

	@Path("/v1/mail/resolvetransaction")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void resolveTransaction(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String txnId,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String remarks);


	/** 
	* @author a-1883
	* @param documentFilterVO
	* @return DocumentValidationVO
	* @throws SystemException
	*/
	@Path("/v1/mail/validatedocumentinstock")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	DocumentValidationVO validateDocumentInStock(DocumentFilterVO documentFilterVO);

	/**
	 *
	 * @param aWBDetailModel
	 * @param containerDetailsModel
	 */
	@Path("/v1/mail/attachawbdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	void attachAWBDetails(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) AWBDetailModel aWBDetailModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) ContainerDetailsModel containerDetailsModel) throws PersistenceException;

	/** 
	* This method deletes document from stock
	* @author a-1883
	* @param documentFilterVO
	* @throws SystemException
	*/
	@Path("/v1/mail/deletedocumentfromstock")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void deleteDocumentFromStock(DocumentFilterVO documentFilterVO) throws MailOperationsBusinessException;

	/** 
	* Attatches awb by grouping and taking from stock Apr 11, 2007, a-1739
	* @throws SystemException
	*/
	@Path("/v1/mail/autoattachawbdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Collection<ContainerDetailsModel> autoAttachAWBDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<ContainerDetailsModel> containerDetailssModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) OperationalFlightModel operationalFlightModel);

	/**
	 *
	 * @param containerVoModel
	 */
	@Path("/v1/mail/updateactualweightformailuld")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void updateActualWeightForMailULD(ContainerModel containerVoModel);

	/**
	 *
	 * @param webServicesVosModel
	 * @param scanningPort
	 * @return
	 */
	@Path("/v1/mail/performmailoperationforgha")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public List<MailUploadModel> performMailOperationForGHA(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<MailWebserviceModel> webServicesVosModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String scanningPort) throws MailHHTBusniessException, MailOperationsBusinessException, PersistenceException;

	/** 
	* @author a-1936This  method is used to find the mailbags  and  the Despatches in the Flight  in a container for  the Manifest..
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/findmailbagsincontainerforimportmanifest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<ContainerDetailsModel> findMailbagsInContainerForImportManifest(
			Collection<ContainerDetailsModel> containersModel);

	/**
	 *
	 * @param searchContainerFilterModel
	 * @param pageNumber
	 * @return
	 */
	@Path("/v1/mail/findmailonhanddetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailOnHandDetailsModel> findMailOnHandDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) SearchContainerFilterModel searchContainerFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	/**
	 *
	 * @param messageModel
	 * @return
	 */
	@Path("/v1/mail/handlemrdho22message")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ErrorVO> handleMRDHO22Message(MailMRDModel messageModel) throws MailHHTBusniessException, MailOperationsBusinessException;

	@Path("/v1/mail/saveallvalidmailbags")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveAllValidMailBags(Collection<ScannedMailDetailsModel> validScannedMailsModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException;

	@Path("/v1/mail/createmailscanvosforerrorstamping")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<MailUploadModel> createMailScanVOSForErrorStamping(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<MailWebserviceModel> mailWebservicesModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String scannedPort,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) StringBuilder errorString,
			@Multipart(value = "3", type = MediaType.APPLICATION_JSON) String errorFromMapping);

	@Path("/v1/mail/findmailawbdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	MailManifestModel findMailAWBDetails(OperationalFlightModel operationalFlightModel);

	/**
	 *
	 * @param operationalFlightModel
	 */
	@Path("/v1/mail/closemailexportflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void closeMailExportFlight(OperationalFlightModel operationalFlightModel);

	/**
	 *
	 * @param operationalFlightModel
	 */
	@Path("/v1/mail/closemailimportflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void closeMailImportFlight(OperationalFlightModel operationalFlightModel);

	/**
	 *
	 * @param fileUploadFilterVO
	 * @return
	 */
	@Path("/v1/mail/processmailoperationfromfile")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String processMailOperationFromFile(FileUploadFilterVO fileUploadFilterVO);

	/** 
	* fetchDataForOfflineUpload
	* @param companyCode
	* @param fileType
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/fetchdataforofflineupload")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<MailUploadModel> fetchDataForOfflineUpload(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String fileType);

	/**
	 *
	 * @param fileUploadFilterVO
	 */
	@Path("/v1/mail/removedatafromtemptable")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO);

	/** 
	* Method		:	MailTrackingDefaultsBI.findOneTimeDescription Added by 	:	A-6991 on 13-Jul-2017 Used for 	:   ICRD-208718 Parameters	:	@param companyCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Parameters	:	@throws MailTrackingBusinessException Return type	: 	Map<String,Collection<OneTimeVO>>
	*/
	@Path("/v1/mail/findonetimedescription")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String oneTimeCode);

	/** 
	* Method		:	MailTrackingDefaultsBI.validateULDsForOperation Added by 	:	A-7794 on 25-Sept-2017 Used for 	:   ICRD-223303
	* @param flightDetailsVo
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/validateuldsforoperation")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void validateULDsForOperation(FlightDetailsVO flightDetailsVo);

	/**
	 *
	 * @param mailAuditFilterModel
	 * @return
	 */
	@Path("/v1/mail/findconauditdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<AuditDetailsVO> findCONAuditDetails(MailAuditFilterModel mailAuditFilterModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.findMailDetailsForMailTag Added by 	:	a-6245 on 07-Jun-2017 Used for 	: Parameters	:	@param companyCode Parameters	:	@param mailId Parameters	:	@param airportCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	MailbagVO
	*/
	@Path("/v1/mail/findmaildetailsformailtag")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public MailbagModel findMailDetailsForMailTag(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String mailId);

	/** 
	* Method		:	MailTrackingDefaultsBI.findMailbagIdForMailTag Added by 	:	a-6245 on 22-Jun-2017 Used for 	: Parameters	:	@param companyCode Parameters	:	@param mailId Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	MailbagVO
	*/
	@Path("/v1/mail/findmailbagidformailtag")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel findMailbagIdForMailTag(MailbagModel mailbagModel);

	/**
	 *
	 * @param mailAuditHistoryFilterModel
	 * @return
	 */
	@Path("/v1/mail/findmailaudithistorydetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<MailBagAuditHistoryModel> findMailAuditHistoryDetails(
			MailAuditHistoryFilterModel mailAuditHistoryFilterModel);

	/**
	 *
	 * @param mailbagEnquiryFilterModel
	 * @return
	 */
	@Path("/v1/mail/findmailstatusdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	Collection<MailbagHistoryModel> findMailStatusDetails(MailbagEnquiryFilterModel mailbagEnquiryFilterModel);

	/** 
	* @param entities
	* @param b
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/findaudittransactioncodes")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	HashMap<String, String> findAuditTransactionCodes(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<String> entities,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) boolean b,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String companyCode);

	@Path("/v1/mail/validateflightsforairport")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public HashMap<String, Collection<FlightValidationVO>> validateFlightsForAirport(
			Collection<FlightFilterVO> flightFilterVOs);

	/** 
	* @author A-5526 This method is used to find the MailBags Accepted to aParticularFlight and Flag MLD-UPL messages for the Same
	* @throws SystemException
	*/
	@Path("/v1/mail/flagmldforupliftedmailbags")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void flagMLDForUpliftedMailbags(Collection<OperationalFlightModel> operationalFlightsModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.findMailAWBDetails Added by 	:	A-6991 on 03-May-2017 Used for 	: Parameters	:	@param operationalFlightVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	MailManifestVO
	*/
	@Path("/v1/mail/closeflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void closeFlight(OperationalFlightModel operationalFlightModel);

	/**
	 *
	 * @param containerModel
	 * @return
	 */
	@Path("/v1/mail/findmailbagcountincontainer")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public int findMailbagcountInContainer(ContainerModel containerModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.performMailAWBTransactions Added by 	:	a-7779 on 31-Aug-2017 Used for 	: Parameters	:	@param mailFlightSummaryVO Parameters	:	@param eventCode Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	void
	*/
	@Path("/v1/mail/performmailawbtransactions")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void performMailAWBTransactions(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailFlightSummaryModel mailFlightSummaryModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String eventCode);

	@Path("/v1/mail/closeinboundflightafteruldacquitalforproxy")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void closeInboundFlightAfterULDAcquitalForProxy(OperationalFlightModel operationalFlightModel);

	@Path("/v1/mail/closeinboundflightafteruldacquital")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void closeInboundFlightAfterULDAcquital(OperationalFlightModel operationalFlightModel);

	@Path("/v1/mail/releasingmailsforuldacquittance")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void releasingMailsForULDAcquittance(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailArrivalModel mailArrivalModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) OperationalFlightModel operationalFlightModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.findNextDocumentNumber Added by 	:	U-1267 on 09-Nov-2017 Used for 	:	ICRD-211205 Parameters	:	@param documnetFilterVO Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Parameters	:	@throws MailTrackingBusinessException Return type	: 	DocumentValidationVO
	*/
	@Path("/v1/mail/findnextdocumentnumber")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public DocumentValidationVO findNextDocumentNumber(DocumentFilterVO documnetFilterVO) throws MailOperationsBusinessException;

	/** 
	* Method		:	MailTrackingDefaultsBI.detachAWBDetails Added by 	:	U-1267 on 09-Nov-2017 Used for 	:	ICRD-211205 Parameters	:	@param containerDetailsVO Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	void
	*/
	@Path("/v1/mail/detachawbdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void detachAWBDetails(ContainerDetailsModel containerDetailsModel);

	/** 
	* MTK064
	*/
	@Path("/v1/mail/transfercontainersatexport")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public TransferManifestModel transferContainersAtExport(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<ContainerModel> containersModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) OperationalFlightModel operationalFlightModel,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String printFlag);

	/** 
	* Method		:	MailTrackingDefaultsBI.transferMailAtExport Added by 	:	A-7371 on 05-Jan-2018 Used for 	:	ICRD-133987
	*/
	@Path("/v1/mail/transfermailatexport")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public TransferManifestModel transferMailAtExport(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<MailbagModel> mailbagsModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) ContainerModel containerModel,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON, required = false) String toPrint) throws MailOperationsBusinessException;

	/**
	 *
	 * @param companyCode
	 * @param paCode
	 * @param days
	 */
	@Path("/v1/mail/generateresditpublishreport")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void generateResditPublishReport(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String paCode,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) int days);

	/**
	 *
	 * @param carditEnquiryFilterModel
	 * @return
	 */
	@Path("/v1/mail/findgrandtotals")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String[] findGrandTotals(CarditEnquiryFilterModel carditEnquiryFilterModel);

	/**
	 *
	 * @param newMailbgsModel
	 * @param isScanned
	 * @return
	 */
	@Path("/v1/mail/dolatvalidation")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public ScannedMailDetailsModel doLATValidation(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<MailbagModel> newMailbgsModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) boolean isScanned) throws MailHHTBusniessException;


	/**
	 *
	 * @param mailBagModel
	 * @param scanningPort
	 * @return
	 */
	@Path("/v1/mail/savemailuploaddetailsforandroid")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public ScannedMailDetailsModel saveMailUploadDetailsForAndroid(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailUploadModel mailBagModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String scanningPort) throws PersistenceException, MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException;

	/** 
	* Method		:	MailTrackingDefaultsBI.validateMailBagDetails Added by 	:	A-7871 on 13-Jul-2018 Used for 	:	ICRD-227884 Parameters	:	@param MailUploadVO Parameters	:	@throws SystemException Return type	: 	void
	*/
	@Path("/v1/mail/validatemailbagdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public ScannedMailDetailsModel validateMailBagDetails(MailUploadModel mailUploadModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException, ForceAcceptanceException;

	/**
	 *
	 * @param fileUploadFilterVO
	 * @return
	 */
	@Path("/v1/mail/validatefromfile")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String validateFromFile(FileUploadFilterVO fileUploadFilterVO);

	/** 
	* Method		:	MailTrackingDefaultsBI.validateFlightAndContainer Added by 	:	A-8164 on 28-Feb-2019 Used for 	: Parameters	:	@param mailUploadVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Parameters	:	@throws MailHHTBusniessException Parameters	:	@throws MailMLDBusniessException Parameters	:	@throws MailTrackingBusinessException  Return type	: 	ScannedMailDetailsVO
	*/
	@Path("/v1/mail/validateflightandcontainer")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public ScannedMailDetailsModel validateFlightAndContainer(MailUploadModel mailUploadModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException;

	@Path("/v1/mail/findoutboundflightsdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailAcceptanceModel> findOutboundFlightsDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) OperationalFlightModel operationalFlightModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/getacceptedcontainers")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<ContainerDetailsModel> getAcceptedContainers(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) OperationalFlightModel operationalFlightModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/getmailbagsincontainer")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailbagModel> getMailbagsinContainer(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) ContainerDetailsModel containerModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/getmailbagsincontainerdsnview")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<DSNModel> getMailbagsinContainerdsnview(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) ContainerDetailsModel containerModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/findcarditsummaryview")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel findCarditSummaryView(CarditEnquiryFilterModel carditEnquiryFilterModel);

	@Path("/v1/mail/findgroupedcarditmails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailbagModel> findGroupedCarditMails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) CarditEnquiryFilterModel carditEnquiryFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/findlyinglistsummaryview")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel findLyinglistSummaryView(MailbagEnquiryFilterModel mailbagEnquiryFilterModel);

	@Path("/v1/mail/findgroupedlyinglist")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailbagModel> findGroupedLyingList(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailbagEnquiryFilterModel mailbagEnquiryFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/findoutboundcarrierdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailAcceptanceModel> findOutboundCarrierDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) OperationalFlightModel operationalFlightModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/getmailbagsincarriercontainer")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailbagModel> getMailbagsinCarrierContainer(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) ContainerDetailsModel containerModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/getmailbagsincarrierdsnview")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<DSNModel> getMailbagsinCarrierdsnview(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) ContainerDetailsModel containerModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/getdsnsforcontainer")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<DSNModel> getDSNsForContainer(ContainerDetailsModel containerModel);

	@Path("/v1/mail/getroutinginfofordsn")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<DSNModel> getRoutingInfoforDSN(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<DSNModel> dsnVosModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) ContainerDetailsModel containerDetailsModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.listFlightDetails Added by 	:	A-8164 on 24-Sep-2018 Used for 	: Parameters	:	@param mailArrivalVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException  Return type	: 	Collection<MailArrivalVO>
	*/
	@Path("/v1/mail/listflightdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Page<MailArrivalModel> listFlightDetails(MailArrivalModel mailArrivalModel);

	/** 
	* MTK064 closeInboundFlights
	*/
	@Path("/v1/mail/closeinboundflights")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void closeInboundFlights(Collection<OperationalFlightModel> operationalFlightsModel) throws MailOperationsBusinessException;

	/** 
	* MTK064 reopenInboundFlights
	*/
	@Path("/v1/mail/reopeninboundflights")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void reopenInboundFlights(Collection<OperationalFlightModel> operationalFlightsModel);

	/** 
	* MTK064
	*/
	@Path("/v1/mail/populatemailarrivalvoforinbound")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailArrivalModel populateMailArrivalVOForInbound(OperationalFlightModel operationalFlightModel);

	@Path("/v1/mail/findarrivedcontainersforinbound")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Page<ContainerDetailsModel> findArrivedContainersForInbound(MailArrivalFilterModel mailArrivalFilterModel) throws MailOperationsBusinessException;

	@Path("/v1/mail/findarrivedmailbagsforinbound")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Page<MailbagModel> findArrivedMailbagsForInbound(MailArrivalFilterModel mailArrivalFilterModel) throws MailOperationsBusinessException;

	@Path("/v1/mail/findarriveddsnsforinbound")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Page<DSNModel> findArrivedDsnsForInbound(MailArrivalFilterModel mailArrivalFilterModel) throws MailOperationsBusinessException;

	/** 
	* This method is used to find the Offload Details for a Flight say at different levels say Containers,DSNS,MailBags Author A-7929
	*
	*/
	@Path("/v1/mail/findoffloaddetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public OffloadModel findOffLoadDetails(OffloadFilterModel offloadFilterModel);

	@Path("/v1/mail/populatepcidetailsforusps")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public MailInConsignmentModel populatePCIDetailsforUSPS(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailInConsignmentModel mailInConsignmentModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String airport,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "3", type = MediaType.APPLICATION_JSON) String rcpOrg,
			@Multipart(value = "4", type = MediaType.APPLICATION_JSON) String rcpDest,
			@Multipart(value = "5", type = MediaType.APPLICATION_JSON) String year);

	@Path("/v1/mail/calculateuldcontentid")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void calculateULDContentId(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<ContainerDetailsModel> containersModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) OperationalFlightModel toFlightModel);

	@Path("/v1/mail/savescreeningdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveScreeningDetails(ScannedMailDetailsModel scannedMailDetailsModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.findRunnerFlights Added by 	:	A-5526 on 12-Oct-2018 Added for 	: CRQ ICRD-239811 Parameters	:	@param runnerFlightFilterVO Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	Page<RunnerFlightVO>
	*/
	@Path("/v1/mail/findrunnerflights")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Page<RunnerFlightVO> findRunnerFlights(RunnerFlightFilterVO runnerFlightFilterVO) throws PersistenceException;

	/** 
	* Method		:	MailTrackingDefaultsBI.findMailbagsForTruckFlight Added by 	:	A-7929 on 23-Oct-2018 Added for 	:  CRQ ICRD-241437 Parameters	:	@param mailbagEnquiryFilterVO,pageNumber Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	Page<MailbagVO>
	*/
	@Path("/v1/mail/findmailbagsfortruckflight")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailbagModel> findMailbagsForTruckFlight(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailbagEnquiryFilterModel mailbagEnquiryFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/finddsnandrsnformailbag")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel findDsnAndRsnForMailbag(MailbagModel mailbagModel);

	/**
	 *
	 * @param filterModel
	 * @param pageNumber
	 * @return
	 */
	@Path("/v1/mail/listforcemajeureapplicablemails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<ForceMajeureRequestModel> listForceMajeureApplicableMails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) ForceMajeureRequestFilterModel filterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	/**
	 *
	 * @param filterModel
	 */
	@Path("/v1/mail/saveforcemajeurerequest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveForceMajeureRequest(ForceMajeureRequestFilterModel filterModel);

	/** 
	* @param invoiceTransactionLogVO
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/inittxnforforcemajeure")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public InvoiceTransactionLogVO initTxnForForceMajeure(InvoiceTransactionLogVO invoiceTransactionLogVO);

	/**
	 *
	 * @param filterModel
	 * @param pageNumber
	 * @return
	 */
	@Path("/v1/mail/listforcemajeuredetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<ForceMajeureRequestModel> listForceMajeureDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) ForceMajeureRequestFilterModel filterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	/**
	 *
	 * @param filterModel
	 * @param pageNumber
	 * @return
	 */
	@Path("/v1/mail/listforcemajeurerequestids")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<ForceMajeureRequestModel> listForceMajeureRequestIds(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) ForceMajeureRequestFilterModel filterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	/**
	 *
	 * @param requestsModel
	 */
	@Path("/v1/mail/deleteforcemajeurerequest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void deleteForceMajeureRequest(Collection<ForceMajeureRequestModel> requestsModel);

	/**
	 *
	 * @param requestModel
	 */
	@Path("/v1/mail/updateforcemajeurerequest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void updateForceMajeureRequest(ForceMajeureRequestFilterModel requestModel);

	/** 
	* @author a-7540
	* @param reassignedFlightValidationVO
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/findallcontainersinassignedflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ContainerModel> findAllContainersInAssignedFlight(
			FlightValidationVO reassignedFlightValidationVO);

	/**
	 *
	 * @param mailScanDetailModel
	 * @return
	 */
	@Path("/v1/mail/processresditmails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ErrorVO> processResditMails(Collection<MailScanDetailModel> mailScanDetailModel) throws MailOperationsBusinessException, MailHHTBusniessException, MailMLDBusniessException;

	/**
	 *
	 * @param mailIdr
	 * @param companyCode
	 * @return
	 */
	@Path("/v1/mail/findmailbagsequencenumberfrommailidr")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public long findMailBagSequenceNumberFromMailIdr(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String mailIdr,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String companyCode);

	/** 
	* @author A-7794
	* @param fileUploadFilterVO
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/processmaildatafromexcel")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO);

	/** 
	* @author A-5526Added for CRQ ICRD-233864
	*/
	@Path("/v1/mail/onstatustoreadyfordelivery")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void onStatustoReadyforDelivery(MailArrivalModel mailArrivalModel) throws MailOperationsBusinessException;

	/** 
	* @author A-7929
	* @return
	* @throws SystemException
	*/
	@Path("/v1/mail/savecontentid")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveContentID(Collection<ContainerModel> containersModel);

	/**
	 *
	 * @param paramMailbagModel
	 */
	@Path("/v1/mail/updateactualweightformailbag")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public abstract void updateActualWeightForMailbag(MailbagModel paramMailbagModel);

	/**
	 *
	 * @param filterModel
	 * @return
	 */
	@Path("/v1/mail/getperformancemonitordetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<MailMonitorSummaryModel> getPerformanceMonitorDetails(MailMonitorFilterModel filterModel);

	/**
	 *
	 * @param filterModel
	 * @param type
	 * @param pageNumber
	 * @return
	 */
	@Path("/v1/mail/getperformancemonitormailbags")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailbagModel> getPerformanceMonitorMailbags(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailMonitorFilterModel filterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String type,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) int pageNumber);

	/**
	 *
	 * @param OperationalFlightModel
	 * @return
	 */
	@Path("/v1/mail/findmailbagmanifest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailManifestModel findMailbagManifest(OperationalFlightModel OperationalFlightModel);

	@Path("/v1/mail/findmailawbmanifest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailManifestModel findMailAWBManifest(OperationalFlightModel OperationalFlightModel);


	@Path("/v1/mail/finddsnmailbagmanifest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailManifestModel findDSNMailbagManifest(OperationalFlightModel OperationalFlightModel);
	@Path("/v1/mail/finddestncatmanifest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailManifestModel findDestnCatManifest(OperationalFlightModel OperationalFlightModel);
	@Path("/v1/mail/generatemailhandedoverrt")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<MailHandedOverModel> generateMailHandedOverRT(MailHandedOverFilterModel mailHandedOverFilterModel);

	/**
	 *
	 * @param mailbagEnquiryFilterModel
	 * @return
	 */
	@Path("/v1/mail/findmailbagdetailsformailbagenquiryhht")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel findMailbagDetailsForMailbagEnquiryHHT(MailbagEnquiryFilterModel mailbagEnquiryFilterModel);

	/**
	 *
	 * @param mailbagEnquiryFilterModel
	 * @return
	 */
	@Path("/v1/mail/findmailbagdetailsformailinboundhht")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel findMailbagDetailsForMailInboundHHT(MailbagEnquiryFilterModel mailbagEnquiryFilterModel);

	/** 
	* @param flightFilterVOs
	* @throws SystemException
	* @author A-8176
	*/
	@Path("/v1/mail/fetchflightcapacitydetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<FlightSegmentCapacitySummaryVO> fetchFlightCapacityDetails(
			Collection<FlightFilterVO> flightFilterVOs);

	/**
	 *
	 * @param dwsMasterVO
	 * @param containerNumber
	 * @param assignedAirport
	 * @return
	 */
	@Path("/v1/mail/fetchmailcontentids")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String fetchMailContentIDs(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) DWSMasterVO dwsMasterVO,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String containerNumber,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String assignedAirport) throws FinderException;

	/** 
	* MTK064
	*/
	@Path("/v1/mail/findduplicatemailbag")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public ArrayList<MailbagModel> findDuplicateMailbag(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String mailBagId);

	/** 
	* Method		:	MailTrackingDefaultsBI.sendULDAnnounceForFlight Added by 	:	A-8164 on 12-Feb-2021 Used for 	:	Sending UCS Announce from mail outbound Parameters	:	@param mailManifestVO Parameters	:	@throws SystemException Parameters	:	@throws RemoteException  Return type	: 	void
	*/
	@Path("/v1/mail/senduldannounceforflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void sendULDAnnounceForFlight(MailManifestModel mailManifestModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.updateMailULDDetailsFromMHS Added by 	:	A-8164 on 15-Feb-2021 Used for 	: Parameters	:	@param storageUnitCheckinVO Parameters	:	@throws SystemException Parameters	:	@throws ProxyException  Return type	: 	boolean
	*/
	@Path("/v1/mail/updatemailulddetailsfrommhs")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public boolean updateMailULDDetailsFromMHS(StorageUnitCheckinVO storageUnitCheckinVO);

	@Path("/v1/mail/finddeviationmailbags")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailbagModel> findDeviationMailbags(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailbagEnquiryFilterModel mailbagEnquiryFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber);

	@Path("/v1/mail/findproductsbyname")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findProductsByName(@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String product);

	@Path("/v1/mail/buildresditproxy")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void buildResditProxy(Collection<ResditEventModel> resditEventModel);

	@Path("/v1/mail/savecardittempmessages")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveCarditTempMessages(Collection<CarditTempMsgVO> carditTempMsgVOs) throws MailOperationsBusinessException;

	@Path("/v1/mail/gettempcarditmessages")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<CarditTempMsgVO> getTempCarditMessages(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String includeMailBoxIdr,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String excludeMailBoxIdr,
			@Multipart(value = "3", type = MediaType.APPLICATION_JSON) String includedOrigins,
			@Multipart(value = "4", type = MediaType.APPLICATION_JSON) String excludedOrigins,
			@Multipart(value = "5", type = MediaType.APPLICATION_JSON) int pageSize,
			@Multipart(value = "6", type = MediaType.APPLICATION_JSON) int noOfdays) throws FinderException;

	/** 
	* Method		:	MailTrackingDefaultsBI.saveCarditMsgs Added by 	:	A-6287 on 03-Mar-2020 Used for 	: Parameters	:	@param ediInterchangeVO Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Parameters	:	@throws MailTrackingBusinessException  Return type	: 	void
	*/
	@Path("/v1/mail/savecarditmsgs")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ErrorVO> saveCarditMsgs(EDIInterchangeModel ediInterchangeModel) throws MailOperationsBusinessException;

	@Path("/v1/mail/deleteemptycontainer")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void deleteEmptyContainer(ContainerDetailsModel containerDetailsModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.updateGateClearStatus Added by 	:	U-1467 on 09-Mar-2020	: Parameters	:	@param operationalFlightVO Parameters	:	@param gateClearanceStatus Parameters	:	@throws RemoteException Parameters	:	@throws SystemException  Return type	: 	void
	*/
	@Path("/v1/mail/updategateclearstatus")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void updateGateClearStatus(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) OperationalFlightModel operationalFlightModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String gateClearanceStatus);

	/**
	 *
	 * @param mailScanDetailModel
	 * @return
	 */
	@Path("/v1/mail/processresditmailsforallevents")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ErrorVO> processResditMailsForAllEvents(Collection<MailUploadModel> mailScanDetailModel) throws MailOperationsBusinessException, MailHHTBusniessException, MailMLDBusniessException;

	/**
	 *
	 * @param scannedMailDetailsModel
	 */
	@Path("/v1/mail/flagresditforacceptanceintruck")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void flagResditForAcceptanceInTruck(ScannedMailDetailsModel scannedMailDetailsModel) throws MailHHTBusniessException, MailMLDBusniessException;

	/**
	 *
	 * @param ediInterchangeModel
	 * @return
	 */
	@Path("/v1/mail/savecdtmessages")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ErrorVO> saveCDTMessages(EDIInterchangeModel ediInterchangeModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param offloadVoModel
	 */
	@Path("/v1/mail/removefrominbound")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void removeFromInbound(OffloadModel offloadVoModel) throws MailOperationsBusinessException;

	@Path("/v1/mail/validatecontainernumberfordeviatedmailbags")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public ErrorVO validateContainerNumberForDeviatedMailbags(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) ContainerDetailsModel containerDetailsModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) long mailSequenceNumber) throws MailOperationsBusinessException;

	/** 
	* @author A-5526 This method is used to find the History of a Mailbag
	* @param companyCode
	*/
	@Path("/v1/mail/findapprovedforcemajeuredetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	Collection<ForceMajeureRequestModel> findApprovedForceMajeureDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String mailBagId,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) long mailSequenceNumber);

	/** 
	* @author A-8353
	* @throws SystemException
	*/
	@Path("/v1/mail/getmanifestinfo")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagInULDForSegmentModel getManifestInfo(MailbagModel mailbagModel) throws PersistenceException;

	/** 
	* @author A-8353 
	* @throws SystemException
	*/
	@Path("/v1/mail/checkmailinuldexistfornextseg")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String checkMailInULDExistForNextSeg(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String containerNumber,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String airpotCode,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String companyCode);

	/**
	 * MTK064
	 */
	@Path("/v1/mail/updateretainflagforcontainer")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void updateRetainFlagForContainer(ContainerModel containerModel) throws FinderException;

	/** 
	* Parameters	:	@param autoAttachAWBJobScheduleVO Parameters	:	@throws RemoteException Parameters	:	@throws SystemException  Return type	: 	void
	*/
	@Path("/v1/mail/createautoattachawbjobschedule")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void createAutoAttachAWBJobSchedule(AutoAttachAWBJobScheduleModel autoAttachAWBJobScheduleModel);

	/**
	 * MTK070 screen method
	 */
	@Path("/v1/mail/findconsignmentscreeningdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public ConsignmentDocumentModel findConsignmentScreeningDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String consignmentNumber,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "2", type = MediaType.APPLICATION_JSON) String poaCode) throws MailOperationsBusinessException;

	/** 
	* Parameters	:	@param TransferManifestVO Parameters	:	@throws RemoteException Parameters	:	@throws SystemException  Return type	: 	void
	*/
	@Path("/v1/mail/savetransferfrommanifest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveTransferFromManifest(TransferManifestModel transferManifestModel) throws MailBookingException, CapacityBookingProxyException, MailOperationsBusinessException, InvalidFlightSegmentException;

	/** 
	* Parameters	:	@param TransferManifestVO Parameters	:	@throws RemoteException Parameters	:	@throws SystemException  Return type	: 	void
	*/
	@Path("/v1/mail/rejecttransferfrommanifest")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void rejectTransferFromManifest(TransferManifestModel transferManifestModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.findTransferManifestConsignmentDetails Added by 	:	U-1418 on 11-Nov-2020 Used for 	: Parameters	:	@param transferManifestVO Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException  Return type	: 	Collection<ConsignmentDocumentVO>
	*/
	@Path("/v1/mail/findtransfermanifestconsignmentdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ConsignmentDocumentModel> findTransferManifestConsignmentDetails(
			TransferManifestModel transferManifestModel);

	/**
	 *
	 * @param mailbagModel
	 * @return
	 */
	@Path("/v1/mail/findmailbillingstatus")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel findMailBillingStatus(MailbagModel mailbagModel);

	@Path("/v1/mail/saveuploadedforcemajeuredata")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String saveUploadedForceMajeureData(FileUploadFilterVO fileUploadFilterVO);

	@Path("/v1/mail/findautopopulatesubtype")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String findAutoPopulateSubtype(DocumentFilterVO documnetFilterVO) throws MailOperationsBusinessException;

	/** 
	* MTK064
	*/
	@Path("/v1/mail/findofficeofexchangeforcarditmissingdommail")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findOfficeOfExchangeForCarditMissingDomMail(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String airportCode);

	/** 
	* Method		:	MailTrackingDefaultsBI.saveMailUploadDetailsFromMailOnload Added by 	:	A-8061 on 07-Apr-2021 Used for 	: Parameters	:	@param mailUploadVOs Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Parameters	:	@throws MailHHTBusniessException Parameters	:	@throws MailMLDBusniessException Parameters	:	@throws MailTrackingBusinessException  Return type	: 	Map<String,ErrorVO>
	*/
	@Path("/v1/mail/savemailuploaddetailsfrommailonload")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Map<String, ErrorVO> saveMailUploadDetailsFromMailOnload(Collection<MailUploadModel> mailUploadsModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException;

	/**
	 *
	 * @param mailbagsModel
	 */
	@Path("/v1/mail/updateoriginanddestinationformailbag")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void updateOriginAndDestinationForMailbag(Collection<MailbagModel> mailbagsModel);

	@Path("/v1/mail/findinboundflightoperationsdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<ImportFlightOperationsVO> findInboundFlightOperationsDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) ImportOperationsFilterVO filterVO,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException;

	@Path("/v1/mail/findoffloadulddetailsatairport")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO> findOffloadULDDetailsAtAirport(
			com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO filterVO) throws PersistenceException;

	@Path("/v1/mail/findexportflightoperationsdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<ManifestVO> findExportFlightOperationsDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) ImportOperationsFilterVO filterVO,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException;

	@Path("/v1/mail/fetchconsignmentdetailsforupload")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ConsignmentDocumentModel> fetchConsignmentDetailsForUpload(FileUploadFilterVO fileUploadFilterVO);

	@Path("/v1/mail/saveuploadedconsignmentdata")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveUploadedConsignmentData(Collection<ConsignmentDocumentModel> consignmentDocumentsModel) throws MailOperationsBusinessException;

	@Path("/v1/mail/savemailbaghistory")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveMailbagHistory(Collection<MailbagHistoryModel> mailbagHistorysModel);

	/**
	 *
	 * @param containersToBeReleasedModel
	 */
	@Path("/v1/mail/releasecontainers")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void releaseContainers(Collection<ContainerModel> containersToBeReleasedModel);

	@Path("/v1/mail/findcontainerjourneyid")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ContainerDetailsModel> findContainerJourneyID(ConsignmentFilterModel consignmentFilterModel);

	@Path("/v1/mail/stampresdits")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void stampResdits(Collection<MailResditModel> mailResditsModel) throws MailOperationsBusinessException;

	@Path("/v1/mail/findarrivaldetailsforreleasingmails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ContainerDetailsModel> findArrivalDetailsForReleasingMails(
			OperationalFlightModel operationalFlightModel);

	/**
	 *
	 * @param mailBagModel
	 * @param scanningPort
	 */
	@Path("/v1/mail/performerrorstampingforfoundmailwebservices")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void performErrorStampingForFoundMailWebServices(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailUploadModel mailBagModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String scanningPort);

	@Path("/v1/mail/getfoundarrivalmailbags")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<MailbagModel> getFoundArrivalMailBags(MailArrivalModel mailArrivalModel);

	/**
	 *
	 * @param mailAuditFilterModel
	 * @return
	 */
	@Path("/v1/mail/findassignflightauditdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterModel mailAuditFilterModel);

	@Path("/v1/mail/dettachmailbookingdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void dettachMailBookingDetails(Collection<MailbagModel> mailbagsModel);

	@Path("/v1/mail/attachawbformail")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public void attachAWBForMail(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) Collection<MailBookingDetailModel> mailBookingDetailsModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) Collection<MailbagModel> mailbagsModel);

	@Path("/v1/mail/findnearestairportofcity")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findNearestAirportOfCity(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String exchangeCode);

	@Path("/v1/mail/markunmarkuldindicator")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void markUnmarkUldIndicator(ContainerModel containerModel);

	@Path("/v1/mail/insertmailbagandhistory")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public long insertMailbagAndHistory(MailbagModel mailbagModel);

	/**
	 *
	 * @param mailBagModel
	 * @param scanningPort
	 * @return
	 */
	@Path("/v1/mail/savemailuploaddetailsforuldfulindicator")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public ScannedMailDetailsModel saveMailUploadDetailsForULDFULIndicator(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailUploadModel mailBagModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String scanningPort) throws PersistenceException, MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException;

	@Path("/v1/mail/triggeremailforpuretransfercontainers")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void triggerEmailForPureTransferContainers(Collection<OperationalFlightModel> operationalFlightsModel);

	/**
	 * MTK070 screen method
	 */
	@Path("/v1/mail/savesecuritydetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveSecurityDetails(Collection<ConsignmentScreeningModel> consignmentScreeningsModel);

	@Path("/v1/mail/listmailbagsecuritydetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel listmailbagSecurityDetails(MailScreeningFilterModel mailScreeningFilterVoModel);

	/**
	 * MTK070 screen method
	 */
	@Path("/v1/mail/editscreeningdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void editscreeningDetails(Collection<ConsignmentScreeningModel> consignmentScreeningsModel);

	/**
	 *
	 * @param consignmentScreeningsModel
	 */
	@Path("/v1/mail/deletescreeningdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void deletescreeningDetails(Collection<ConsignmentScreeningModel> consignmentScreeningsModel);


	/** 
	* Method		:	MailTrackingDefaultsBI.saveMailSecurityStatus Added by 	:	A-4809 on 18-May-2022 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@throws SystemException Parameters	:	@throws RemoteException  Return type	: 	void
	*/
	@Path("/v1/mail/savemailsecuritystatus")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveMailSecurityStatus(MailbagModel mailbagModel);

	/** 
	* Method		:	MailTrackingDefaultsBI.saveScreeningConsginorDetails Added by 	:	A-4809 on 19-May-2022 Used for 	: Parameters	:	@param contTransferMap Parameters	:	@throws SystemException Parameters	:	@throws RemoteException  Return type	: 	void
	*/
	@Path("/v1/mail/savescreeningconsginordetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveScreeningConsginorDetails(Map<String, Object> contTransferMap);

	/**
	 *
	 * @param mailbagModel
	 * @return
	 */
	@Path("/v1/mail/findairportfrommailbag")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel findAirportFromMailbag(MailbagModel mailbagModel) throws FinderException;

	/**
	 *
	 * @param flightLoadPlanContainersModel
	 */
	@Path("/v1/mail/savefligthloadplanformail")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveFligthLoadPlanForMail(Collection<FlightLoadPlanContainerModel> flightLoadPlanContainersModel) throws BusinessException;

	/** 
	* Method		:	MailTrackingDefaultsBI.findLoadPlandetails Added by 	:	A-9477 on 13-JUL-2022 Used for 	: Parameters	:	@param SearchContainerFilterVO Parameters	:	@throws SystemException Parameters	:	@throws RemoteException  Return type	: 	void
	*/
	@Path("/v1/mail/findloadplandetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<FlightLoadPlanContainerModel> findLoadPlandetails(
			SearchContainerFilterModel searchContainerFilterModel);

    /**
	* MTK070 screen method
	*/
	@Path("/v1/mail/saveconsignmentdetailsmaster")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void saveConsignmentDetailsMaster(ConsignmentDocumentModel consignmentDocumentModel);

	/**
	 *
	 * @param securityAndScreeningMessageVO
	 * @return
	 */
	@Path("/v1/mail/savesecurityscreeningfromservice")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Map<String, ErrorVO> saveSecurityScreeningFromService(
			SecurityAndScreeningMessageVO securityAndScreeningMessageVO) throws MailOperationsBusinessException;

	@Path("/v1/mail/findairportparametercode")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Map<String, String> findAirportParameterCode(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) FlightFilterVO flightFilterVO,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) Collection<String> parCodes);

	@Path("/v1/mail/fetchmailindicatorforprogress")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> fetchMailIndicatorForProgress(
			Collection<FlightListingFilterVO> flightListingFilterVOs);

	/**
	 * MTK064
	 */
	@Path("/v1/mail/findsecurityscreeningvalidations")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<SecurityScreeningValidationModel> findSecurityScreeningValidations(
			SecurityScreeningValidationFilterModel securityScreeningValidationFilterModel) throws MailOperationsBusinessException;

	/**
	 *
	 * @param operationalFlightModel
	 * @param selectedContainersModel
	 * @return
	 */
	@Path("/v1/mail/dosecurityandscreeningvalidationatcontainerlevel")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public SecurityScreeningValidationModel doSecurityAndScreeningValidationAtContainerLevel(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) OperationalFlightModel operationalFlightModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) Collection<ContainerModel> selectedContainersModel) throws BusinessException;

	@Path("/v1/mail/updateintflgasnformailbagsinconatiner")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void updateIntFlgAsNForMailBagsInConatiner(HbaMarkingModel hbaMarkingModel);

	@Path("/v1/mail/generateautomaticbarrowid")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public String generateAutomaticBarrowId(String cmpcod);

	/** 
	* Method		:	MailTrackingDefaultsBI.findCN46TransferManifestDetails Added by 	:	A-10647 on 27-Oct-2022 Used for 	: Parameters	:	@param transferManifestVO Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	Collection<ConsignmentDocumentVO>
	*/
	@Path("/v1/mail/findcn46transfermanifestdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ConsignmentDocumentModel> findCN46TransferManifestDetails(
			TransferManifestModel transferManifestModel);

	/** 
	* @author A-8353
	*/
	@Path("/v1/mail/doapplicableregulationflagvalidationforpabuidcontainer")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Collection<SecurityScreeningValidationModel> doApplicableRegulationFlagValidationForPABuidContainer(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailbagModel mailbagModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) SecurityScreeningValidationFilterModel securityScreeningValidationFilterModel) throws BusinessException;

	/**
	 *
	 * @param flightFilterVOs
	 * @return
	 */
	@Path("/v1/mail/fetchflightpreadvicedetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<MailAcceptanceModel> fetchFlightPreAdviceDetails(Collection<FlightFilterVO> flightFilterVOs);

	@Path("/v1/mail/updateactualweightformailcontainer")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public ContainerModel updateActualWeightForMailContainer(ContainerModel containerModel);

	@Path("/v1/mail/findflightsformailinboundautoattachawb")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<OperationalFlightModel> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleModel mailInboundAutoAttachAWBJobScheduleModel);

	@Path("/v1/mail/findmailtransit")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public Page<MailTransitModel> findMailTransit(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) MailTransitFilterModel mailTransitFilterModel,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) int pageNumber) throws PersistenceException;

	@Path("/v1/mail/findflightlistings")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<FlightSegmentCapacitySummaryVO> findFlightListings(FlightFilterVO filterVO);

	@Path("/v1/mail/findactiveallotments")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Page<FlightSegmentCapacitySummaryVO> findActiveAllotments(FlightSegmentCapacityFilterVO filterVo);

	@Path("/v1/mail/findmailconsumed")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel findMailConsumed(MailTransitFilterModel filterVoModel);

	/**
	 *
	 * @param noOfDays
	 */
	@Path("/v1/mail/createpawbforconsignment")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void createPAWBForConsignment(int noOfDays) throws PersistenceException;

	/* @param operationalFlightVO
	* @throws SystemException
	* @throws RemoteException
	* @throws MailTrackingBusinessException
	*/
	@Path("/v1/mail/closeinboundflight")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	void closeInboundFlight(OperationalFlightModel operationalFlightModel);
	@Path("/v1/mail/generateconsignmentsummaryreportdtls")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	ConsignmentDocumentModel generateConsignmentSummaryReportDtls(ConsignmentFilterModel consignmentFilterModel);

	@Path("/v1/mail/generatetransfermanifestreportdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	TransferManifestModel generateTransferManifestReportDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String transferManifestId);

	@Path("/v1/mail/generateconsignmentsecurityreportdtls")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	ConsignmentDocumentModel generateConsignmentSecurityReportDtls(ConsignmentFilterModel consignmentFilterModel);

	@Path("/v1/mail/generatemailtagdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<MailbagModel> generateMailTagDetails(Collection<MailbagModel> mailbagModel);

	@Path("/v1/mail/generateconsignmentreportdtls")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	ConsignmentDocumentModel generateConsignmentReportDtls(ConsignmentFilterModel consignmentFilterModel);


	@Path("/v1/mail/fetchmailsecuritydetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailbagModel fetchMailSecurityDetails(MailScreeningFilterModel mailScreeningFilterModel);
	@Path("/v1/mail/findroutingdetails")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public String findRoutingDetails(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) long malseqnum);
	@Path("/v1/mail/generatecn46consignmentreportdtls")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ConsignmentDocumentModel> generateCN46ConsignmentReportDtls(ConsignmentFilterModel consignmentFilterModel);

	@Path("/v1/mail/generatecn46consignmentsummaryreportdtls")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<ConsignmentDocumentModel> generateCN46ConsignmentSummaryReportDtls(ConsignmentFilterModel consignmentFilterModel);
 @Path("/v1/mail/generatemailstatusrt")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<MailStatusModel> generateMailStatusRT(MailStatusFilterModel mailStatusFilterModel);

	@Path("/v1/mail/findtransfermanifestdetailsfortransfer")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public List<TransferManifestModel> findTransferManifestDetailsForTransfer(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String tranferManifestId);	 
@Path("/v1/mail/generatedailymailstationrt")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<DailyMailStationReportModel> generateDailyMailStationRT(DailyMailStationFilterModel filterModel);
@Path("/v1/mail/finddamagemailreport")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Collection<DamagedMailbagModel> findDamageMailReport(DamageMailFilterModel damageMailReportFiltermodel);

	@Path("/v1/mail/findimportmanifestdetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public MailManifestModel findImportManifestDetails(OperationalFlightModel operationalFlightVo);

    @Path("/v1/mail/findmailbagdamageimages")
	@POST
	@Consumes("multipart/mixed")
	@Produces("application/json")
	public byte[] findMailbagDamageImages(
			@Multipart(value = "0", type = MediaType.APPLICATION_JSON) String companyCode,
			@Multipart(value = "1", type = MediaType.APPLICATION_JSON) String id);

	@Path("/v1/mail/publishmaildetails")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public void publishMailDetails(MailMasterDataFilterModel mailMasterDataFilterModel);
}
