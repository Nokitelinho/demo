package com.ibsplc.neoicargo.mail.controller;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.flight.operation.vo.*;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mailsecurityandscreening.SecurityAndScreeningMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.FlightListingFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportFlightOperationsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.operations.vo.StorageUnitCheckinVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.LocationEnquiryFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.*;
import com.ibsplc.neoicargo.mail.model.*;
import com.ibsplc.neoicargo.mail.MailOperationsAPI;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.ConsignmentFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailScreeningFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import org.springframework.stereotype.Service;
import com.ibsplc.neoicargo.mail.service.MailOperationsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class MailOperationsController implements MailOperationsAPI {
	@Autowired
	private MailOperationsService mailOperationsService;

	public void saveMailScannedDetails(Collection<MailScanDetailModel> mailScanDetailsModel) {

			mailOperationsService.saveMailScannedDetails(mailScanDetailsModel);

	}

	public void updateGHTformailbags(Collection<OperationalFlightModel> operationalFlightsModel) {
		mailOperationsService.updateGHTformailbags(operationalFlightsModel);
	}

	public ScannedMailDetailsModel saveMailUploadDetails(Collection<MailUploadModel> mailBagsModel,
			String scanningPort) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException {
		return mailOperationsService.saveMailUploadDetails(mailBagsModel, scanningPort);
	}

	public void saveMailDetailsFromJob(Collection<MailUploadModel> mailBagsModel, String scanningPort) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException {
		 mailOperationsService.saveMailDetailsFromJob(mailBagsModel, scanningPort);
	}

	public void deliverMailbags(MailArrivalModel mailArrivalModel) throws MailOperationsBusinessException {
		 mailOperationsService.deliverMailbags(mailArrivalModel);
	}

	public void saveScannedDeliverMails(Collection<MailArrivalModel> deliverVosForSaveModel) throws BusinessException {
		 mailOperationsService.saveScannedDeliverMails(deliverVosForSaveModel);
	}

	public Collection<ScannedMailDetailsModel> saveAcceptanceDetails(MailAcceptanceModel mailAcceptanceModel) throws MailOperationsBusinessException {
		return mailOperationsService.saveAcceptanceDetails(mailAcceptanceModel);
	}

	public Collection<ContainerDetailsModel> offload(OffloadModel offloadVoModel) throws MailOperationsBusinessException {
		return mailOperationsService.offload(offloadVoModel);
	}

	public Collection<ContainerDetailsModel> returnMailbags(Collection<MailbagModel> mailbagsToReturnModel) throws MailOperationsBusinessException {
		return mailOperationsService.returnMailbags(mailbagsToReturnModel);
	}

	public void saveAndProcessMailBags(ScannedMailDetailsModel scannedMailDetailsModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException {
		 mailOperationsService.saveAndProcessMailBags(scannedMailDetailsModel);
	}

	public Collection<FlightValidationVO> validateFlight(FlightFilterVO flightFilterVO) {
		return mailOperationsService.validateFlight(flightFilterVO);
	}

	public Collection<FlightValidationVO> validateMailFlight(FlightFilterVO flightFilterVO) {
		return mailOperationsService.validateMailFlight(flightFilterVO);
	}

	public Map<String, Collection<String>> findWarehouseTransactionLocations(LocationEnquiryFilterVO filterVO) {
		return mailOperationsService.findWarehouseTransactionLocations(filterVO);
	}

	public Collection<ContainerDetailsModel> findMailbagsInContainer(
			Collection<ContainerDetailsModel> containersModel) {
		return mailOperationsService.findMailbagsInContainer(containersModel);
	}

	public Collection<WarehouseVO> findAllWarehouses(String companyCode, String airportCode) {
		return mailOperationsService.findAllWarehouses(companyCode, airportCode);
	}

	public ContainerModel validateContainer(String airportCode, ContainerModel containerModel) throws BusinessException {
		return mailOperationsService.validateContainer(airportCode, containerModel);
	}

	public boolean isFlightClosedForMailOperations(OperationalFlightModel operationalFlightModel) {
		return mailOperationsService.isFlightClosedForMailOperations(operationalFlightModel);
	}

	public boolean validateMailBags(Collection<MailbagModel> mailbagVosModel) throws BusinessException {
		return mailOperationsService.validateMailBags(mailbagVosModel);
	}

	public LocationValidationVO validateLocation(String companyCode, String airportCode, String warehouseCode,
												 String locationCode) {
		return mailOperationsService.validateLocation(companyCode, airportCode, warehouseCode, locationCode);
	}

	public Collection<DespatchDetailsModel> validateConsignmentDetails(String companyCode,
			Collection<DespatchDetailsModel> despatchDetailssModel) {
		return mailOperationsService.validateConsignmentDetails(companyCode, despatchDetailssModel);
	}

	public boolean validateDSNs(Collection<DSNModel> dsnVosModel) throws MailOperationsBusinessException {
		return mailOperationsService.validateDSNs(dsnVosModel);
	}

	public Collection<ContainerModel> findAllULDsInAssignedFlight(FlightValidationVO reassignedFlightValidationVO) {
		return mailOperationsService.findAllULDsInAssignedFlight(reassignedFlightValidationVO);
	}

	public void reassignContainers(Collection<ContainerModel> containersToReassignModel,
			OperationalFlightModel toFlightModel) throws MailOperationsBusinessException {
		 mailOperationsService.reassignContainers(containersToReassignModel, toFlightModel);
	}

	public PreAdviceModel findPreAdvice(OperationalFlightModel operationalFlightModel) {
		return mailOperationsService.findPreAdvice(operationalFlightModel);
	}

	public Page<TransferManifestModel> findTransferManifest(TransferManifestFilterModel tranferManifestFilterVoModel) {
		return mailOperationsService.findTransferManifest(tranferManifestFilterVoModel);
	}

	public MailManifestModel findContainersInFlightForManifest(OperationalFlightModel operationalFlightVoModel) {
		return mailOperationsService.findContainersInFlightForManifest(operationalFlightVoModel);
	}

	public String findStockHolderForMail(String companyCode, String airportCode, Boolean isGHA) {
		return mailOperationsService.findStockHolderForMail(companyCode, airportCode, isGHA);
	}

	public AWBDetailModel findAWBDetails(AWBFilterModel aWBFilterModel) throws MailOperationsBusinessException {
		return mailOperationsService.findAWBDetails(aWBFilterModel);

	}

	public OffloadModel findOffloadDetails(OffloadFilterModel offloadFilterModel) {
		return mailOperationsService.findOffloadDetails(offloadFilterModel);
	}

	public void reopenFlight(OperationalFlightModel operationalFlightModel) {
		 mailOperationsService.reopenFlight(operationalFlightModel);
	}

	public void closeFlightAcceptance(OperationalFlightModel operationalFlightModel,
			MailAcceptanceModel mailAcceptanceModel) throws MailOperationsBusinessException {
		mailOperationsService.closeFlightAcceptance(operationalFlightModel, mailAcceptanceModel);
	}

	public Page<MailbagModel> findMailbags(MailbagEnquiryFilterModel mailbagEnquiryFilterModel, int pageNumber) {
		return mailOperationsService.findMailbags(mailbagEnquiryFilterModel, pageNumber);
	}

	public TransferManifestModel transferContainers(Collection<ContainerModel> containersModel,
			OperationalFlightModel operationalFlightModel, String printFlag) throws MailOperationsBusinessException {
		return mailOperationsService.transferContainers(containersModel, operationalFlightModel, printFlag);
	}

	public Collection<MailbagHistoryModel> findMailbagHistories(String companyCode, String mailBagId,
			long mailSequenceNumber) {
		return mailOperationsService.findMailbagHistories(companyCode, mailBagId, mailSequenceNumber);
	}

	public Collection<MailHistoryRemarksModel> findMailbagNotes(String mailBagId) {
		return mailOperationsService.findMailbagNotes(mailBagId);
	}

	@Override
	public Collection<MailbagHistoryModel> findMailbagHistoriesFromWebScreen(String companyCode, String mailBagId, long mailSequenceNumber) {
		return mailOperationsService.findMailbagHistoriesFromWebScreen(companyCode, mailBagId, mailSequenceNumber);
	}

	public void unassignEmptyULDs(Collection<ContainerDetailsModel> containerDetailssModel) {
		mailOperationsService.unassignEmptyULDs(containerDetailssModel);
	}

	public Collection<ContainerModel> findFlightAssignedContainers(OperationalFlightModel operationalFlightModel) {
		return mailOperationsService.findFlightAssignedContainers(operationalFlightModel);
	}

	public Collection<ContainerModel> findDestinationAssignedContainers(DestinationFilterModel destinationFilterModel) {
		return mailOperationsService.findDestinationAssignedContainers(destinationFilterModel);
	}

	public Collection<ContainerDetailsModel> reassignMailbags(Collection<MailbagModel> mailbagsToReassignModel,
			ContainerModel toContainerModel) throws MailOperationsBusinessException {
		return mailOperationsService.reassignMailbags(mailbagsToReassignModel, toContainerModel);
	}

	public void autoAcquitContainers(Collection<ContainerDetailsModel> conatinerstoAcquitModel) {
		 mailOperationsService.autoAcquitContainers(conatinerstoAcquitModel);
	}

	public ContainerAssignmentModel findLatestContainerAssignment(String containerNumber) throws MailOperationsBusinessException {
		return mailOperationsService.findLatestContainerAssignment(containerNumber);
	}

	public void saveChangeFlightDetails(Collection<MailArrivalModel> mailArrivalsModel) throws MailOperationsBusinessException {
		 mailOperationsService.saveChangeFlightDetails(mailArrivalsModel);
	}

	public void sendResdit(CarditEnquiryModel carditEnquiryModel) throws MailOperationsBusinessException {
		 mailOperationsService.sendResdit(carditEnquiryModel);
	}

	public ConsignmentDocumentModel findConsignmentDocumentDetails(ConsignmentFilterModel consignmentFilterModel) {
		return mailOperationsService.findConsignmentDocumentDetails(consignmentFilterModel);
	}

	public void deleteContainers(Collection<ContainerModel> containersModel) throws MailOperationsBusinessException {
		mailOperationsService.deleteContainers(containersModel);
	}

	public void deleteConsignmentDocumentDetails(ConsignmentDocumentModel consignmentDocumentModel) throws MailOperationsBusinessException {
		mailOperationsService.deleteConsignmentDocumentDetails(consignmentDocumentModel);
	}

	public void saveDamageDetailsForMailbag(Collection<MailbagModel> mailbagsModel) {
		mailOperationsService.saveDamageDetailsForMailbag(mailbagsModel);
	}

	public Collection<DamagedMailbagModel> findMailbagDamages(String companyCode, String mailbagId) {
		return mailOperationsService.findMailbagDamages(companyCode, mailbagId);
	}

	public Page<ContainerModel> findContainers(SearchContainerFilterModel searchContainerFilterModel, int pageNumber) {
		return mailOperationsService.findContainers(searchContainerFilterModel, pageNumber);
	}

	public Collection<EmbargoDetailsVO> checkEmbargoForMail(Collection<ShipmentDetailsVO> shipmentDetailsVos) {
		return mailOperationsService.checkEmbargoForMail(shipmentDetailsVos);
	}

	public Page<MailbagModel> findCarditMails(CarditEnquiryFilterModel carditEnquiryFilterModel, int pageNumber) throws BusinessException {
		return mailOperationsService.findCarditMails(carditEnquiryFilterModel, pageNumber);
	}

	public MailArrivalModel findArrivalDetails(MailArrivalFilterModel mailArrivalFilterModel) {
		return mailOperationsService.findArrivalDetails(mailArrivalFilterModel);
	}

	public OperationalFlightModel validateInboundFlight(OperationalFlightModel flightModel) {
		return mailOperationsService.validateInboundFlight(flightModel);
	}

	public void saveArrivalDetails(MailArrivalModel mailArrivalModel) throws MailOperationsBusinessException {
		mailOperationsService.saveArrivalDetails(mailArrivalModel);
	}

	public boolean isFlightClosedForInboundOperations(OperationalFlightModel operationalFlightModel) {
		return mailOperationsService.isFlightClosedForInboundOperations(operationalFlightModel);
	}

	public void undoArriveContainer(MailArrivalModel mailarrivalvoModel) throws MailOperationsBusinessException {
		mailOperationsService.undoArriveContainer(mailarrivalvoModel);
	}

	public Collection<MailDiscrepancyModel> findMailDiscrepancies(OperationalFlightModel operationalFlightModel) {
		return mailOperationsService.findMailDiscrepancies(operationalFlightModel);
	}

	public Integer saveConsignmentDocument(ConsignmentDocumentModel consignmentDocumentModel) throws BusinessException {
		return mailOperationsService.saveConsignmentDocument(consignmentDocumentModel);
	}

	public Collection<MailbagModel> findCartIds(ConsignmentFilterModel consignmentFilterModel) {
		return mailOperationsService.findCartIds(consignmentFilterModel);
	}

	public TransferManifestModel transferMail(Collection<DespatchDetailsModel> despatchDetailssModel,
			Collection<MailbagModel> mailbagsModel, ContainerModel containerModel, String toPrint) throws MailOperationsBusinessException {
		return mailOperationsService.transferMail(despatchDetailssModel, mailbagsModel, containerModel,
				toPrint);
	}

	public void saveConsignmentForManifestedDSN(ConsignmentDocumentModel consignmentDocumentModel) throws MailOperationsBusinessException {
		mailOperationsService.saveConsignmentForManifestedDSN(consignmentDocumentModel);
	}


	public Map<String, Collection<MLDMasterModel>> saveMailUploadDetailsFromMLD(
			Collection<MLDMasterModel> mldMastersModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException {
		return mailOperationsService.saveMailUploadDetailsFromMLD(mldMastersModel);
	}


	public void initiateULDAcquittance(OperationalFlightModel operationalFlightModel) {
		 mailOperationsService.initiateULDAcquittance(operationalFlightModel);
	}

	public void triggerMLDMessages(String companyCode, int recordCount) {
		 mailOperationsService.triggerMLDMessages(companyCode, recordCount);
	}

	public void closeInboundFlightForMailOperation(String companyCode) {
		 mailOperationsService.closeInboundFlightForMailOperation(companyCode);
	}

	public void closeFlightForMailOperation(String companyCode, int time, String airportCode) {
		 mailOperationsService.closeFlightForMailOperation(companyCode, time, airportCode);
	}

	public void initiateArrivalForFlights(ArriveAndImportMailModel arriveAndImportMailModel) {
		 mailOperationsService.initiateArrivalForFlights(arriveAndImportMailModel);
	}

	public Page<DespatchDetailsModel> findDSNs(DSNEnquiryFilterModel dSNEnquiryFilterModel, int pageNumber) {
		return mailOperationsService.findDSNs(dSNEnquiryFilterModel, pageNumber);
	}

	public Collection<ErrorVO> saveCarditMessages(EDIInterchangeModel ediInterchangeModel) throws BusinessException, InvocationTargetException, IllegalAccessException {
		return mailOperationsService.saveCarditMessages(ediInterchangeModel);
	}

	public Collection<MailbagModel> validateScannedMailbagDetails(
			Collection<MailbagModel> mailbagsModel) throws MailOperationsBusinessException {
		return mailOperationsService.validateScannedMailbagDetails(mailbagsModel);
	}

	public void invokeResditReceiver(String companyCode) {
		 mailOperationsService.invokeResditReceiver(companyCode);
	}

	public Collection<ResditEventModel> checkForResditEvents(String companyCode) {
		return mailOperationsService.checkForResditEvents(companyCode);
	}

	public void flagUpliftedResditForMailbags(Collection<OperationalFlightModel> operationalFlightsModel) {
		 mailOperationsService.flagUpliftedResditForMailbags(operationalFlightsModel);
	}

	public void flagTransportCompletedResditForMailbags(Collection<OperationalFlightModel> operationalFlightsModel) {
		 mailOperationsService.flagTransportCompletedResditForMailbags(operationalFlightsModel);
	}

	public Map<String, ContainerAssignmentModel> findContainerAssignments(Collection<ContainerModel> containersModel) {
		return mailOperationsService.findContainerAssignments(containersModel);
	}

	public void updateResditSendStatus(ResditMessageVO resditMessageVO) {
		 mailOperationsService.updateResditSendStatus(resditMessageVO);
	}

	public Collection<ErrorVO> handleMRDMessage(MailMRDModel messageModel) throws MailHHTBusniessException, MailOperationsBusinessException {
		return mailOperationsService.handleMRDMessage(messageModel);
	}

	public void buildResdit(Collection<ResditEventModel> resditEventsModel) {
		 mailOperationsService.buildResdit(resditEventsModel);
	}

	public Object[] getTxnParameters(String companyCode, String txnId) {
		//TODO: Neo to implement below method
		//return mailTrackingDefaultsServicesEJB.getTxnParameters(companyCode, txnId);
		return null;
	}

	public void resolveTransaction(String companyCode, String txnId, String remarks) {
		mailOperationsService.resolveTransaction(companyCode, txnId, remarks);
	}
	public DocumentValidationVO validateDocumentInStock(DocumentFilterVO documentFilterVO) {
		return mailOperationsService.validateDocumentInStock(documentFilterVO);
	}

	public void attachAWBDetails(AWBDetailModel aWBDetailModel, ContainerDetailsModel containerDetailsModel) throws PersistenceException {
		 mailOperationsService.attachAWBDetails(aWBDetailModel, containerDetailsModel);
	}

	public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO) throws MailOperationsBusinessException {
		 mailOperationsService.deleteDocumentFromStock(documentFilterVO);
	}

	public Collection<ContainerDetailsModel> autoAttachAWBDetails(
			Collection<ContainerDetailsModel> containerDetailssModel, OperationalFlightModel operationalFlightModel) {
		try {
			return mailOperationsService.autoAttachAWBDetails(containerDetailssModel, operationalFlightModel);
		} catch (MailOperationsBusinessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateActualWeightForMailULD(ContainerModel containerVoModel) {
		 mailOperationsService.updateActualWeightForMailULD(containerVoModel);
	}

	public List<MailUploadModel> performMailOperationForGHA(Collection<MailWebserviceModel> webServicesVosModel,
															String scanningPort) throws MailHHTBusniessException, MailOperationsBusinessException, PersistenceException {
		return mailOperationsService.performMailOperationForGHA(webServicesVosModel, scanningPort);
	}

	public Collection<ContainerDetailsModel> findMailbagsInContainerForImportManifest(
			Collection<ContainerDetailsModel> containersModel) {
		return mailOperationsService.findMailbagsInContainerForImportManifest(containersModel);
	}

	public Page<MailOnHandDetailsModel> findMailOnHandDetails(SearchContainerFilterModel searchContainerFilterModel,
			int pageNumber) {
		return mailOperationsService.findMailOnHandDetails(searchContainerFilterModel, pageNumber);
	}

	public Collection<ErrorVO> handleMRDHO22Message(MailMRDModel messageModel) throws MailHHTBusniessException, MailOperationsBusinessException {
		return mailOperationsService.handleMRDHO22Message(messageModel);
	}

	public void saveAllValidMailBags(Collection<ScannedMailDetailsModel> validScannedMailsModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException {
		 mailOperationsService.saveAllValidMailBags(validScannedMailsModel);
	}

	public Collection<MailUploadModel> createMailScanVOSForErrorStamping(
			Collection<MailWebserviceModel> mailWebservicesModel, String scannedPort, StringBuilder errorString,
			String errorFromMapping) {
		return mailOperationsService.createMailScanVOSForErrorStamping(mailWebservicesModel, scannedPort,
				errorString, errorFromMapping);
	}

	public MailManifestModel findMailAWBDetails(OperationalFlightModel operationalFlightModel) {
		return mailOperationsService.findMailAWBDetails(operationalFlightModel);
	}

	public void closeMailExportFlight(OperationalFlightModel operationalFlightModel) {
		try {
			 mailOperationsService.closeMailExportFlight(operationalFlightModel);
		} catch (MailOperationsBusinessException e) {
			e.printStackTrace();
		}

	}

	public void closeMailImportFlight(OperationalFlightModel operationalFlightModel) {
		try {
			 mailOperationsService.closeMailImportFlight(operationalFlightModel);
		} catch (MailOperationsBusinessException e) {
			e.printStackTrace();
		}
	}

	public String processMailOperationFromFile(FileUploadFilterVO fileUploadFilterVO) {
		return mailOperationsService.processMailOperationFromFile(fileUploadFilterVO);
	}

	public Collection<MailUploadModel> fetchDataForOfflineUpload(String companyCode, String fileType) {
		return mailOperationsService.fetchDataForOfflineUpload(companyCode, fileType);
	}

	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO) {
		 mailOperationsService.removeDataFromTempTable(fileUploadFilterVO);
	}

	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode, String oneTimeCode) {
		return mailOperationsService.findOneTimeDescription(companyCode, oneTimeCode);
	}

	public void validateULDsForOperation(FlightDetailsVO flightDetailsVo) {
		 mailOperationsService.validateULDsForOperation(flightDetailsVo);
	}

	public Collection<AuditDetailsVO> findCONAuditDetails(MailAuditFilterModel mailAuditFilterModel) {
		return mailOperationsService.findCONAuditDetails(mailAuditFilterModel);
	}

	public MailbagModel findMailDetailsForMailTag(String companyCode, String mailId) {
		return mailOperationsService.findMailDetailsForMailTag(companyCode, mailId);
	}

	public MailbagModel findMailbagIdForMailTag(MailbagModel mailbagModel) {
		return mailOperationsService.findMailbagIdForMailTag(mailbagModel);
	}

	public Collection<MailBagAuditHistoryModel> findMailAuditHistoryDetails(
			MailAuditHistoryFilterModel mailAuditHistoryFilterModel) {
		return mailOperationsService.findMailAuditHistoryDetails(mailAuditHistoryFilterModel);
	}

	public Collection<MailbagHistoryModel> findMailStatusDetails(MailbagEnquiryFilterModel mailbagEnquiryFilterModel) {
		return mailOperationsService.findMailStatusDetails(mailbagEnquiryFilterModel);
	}

	public HashMap<String, String> findAuditTransactionCodes(Collection<String> entities, boolean b,
															 String companyCode) {
		return mailOperationsService.findAuditTransactionCodes(entities, b, companyCode);
	}

	public HashMap<String, Collection<FlightValidationVO>> validateFlightsForAirport(
			Collection<FlightFilterVO> flightFilterVOs) {
		return mailOperationsService.validateFlightsForAirport(flightFilterVOs);
	}

	public void flagMLDForUpliftedMailbags(Collection<OperationalFlightModel> operationalFlightsModel) {
		 mailOperationsService.flagMLDForUpliftedMailbags(operationalFlightsModel);
	}

	public void closeFlight(OperationalFlightModel operationalFlightModel) {
		 mailOperationsService.closeFlight(operationalFlightModel);
	}

	public int findMailbagcountInContainer(ContainerModel containerModel) {
		try {
			return mailOperationsService.findMailbagcountInContainer(containerModel);
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void performMailAWBTransactions(MailFlightSummaryModel mailFlightSummaryModel, String eventCode) {
		 mailOperationsService.performMailAWBTransactions(mailFlightSummaryModel, eventCode);
	}

	public void closeInboundFlightAfterULDAcquitalForProxy(OperationalFlightModel operationalFlightModel) {
		 mailOperationsService.closeInboundFlightAfterULDAcquitalForProxy(operationalFlightModel);
	}

	public void closeInboundFlightAfterULDAcquital(OperationalFlightModel operationalFlightModel) {
		 mailOperationsService.closeInboundFlightAfterULDAcquital(operationalFlightModel);
	}

	public void releasingMailsForULDAcquittance(MailArrivalModel mailArrivalModel,
			OperationalFlightModel operationalFlightModel) {
		 mailOperationsService.releasingMailsForULDAcquittance(mailArrivalModel,
				operationalFlightModel);
	}

	public DocumentValidationVO findNextDocumentNumber(DocumentFilterVO documnetFilterVO) throws  MailOperationsBusinessException{
		return mailOperationsService.findNextDocumentNumber(documnetFilterVO);

	}

	public void detachAWBDetails(ContainerDetailsModel containerDetailsModel) {
		 mailOperationsService.detachAWBDetails(containerDetailsModel);
	}

	public TransferManifestModel transferContainersAtExport(Collection<ContainerModel> containersModel,
			OperationalFlightModel operationalFlightModel, String printFlag) {
		try {
			return mailOperationsService.transferContainersAtExport(containersModel, operationalFlightModel,
					printFlag);
		} catch (MailOperationsBusinessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public TransferManifestModel transferMailAtExport(Collection<MailbagModel> mailbagsModel,
			ContainerModel containerModel, String toPrint) throws MailOperationsBusinessException {

		return mailOperationsService.transferMailAtExport(mailbagsModel, containerModel, toPrint);

	}

	public void generateResditPublishReport(String companyCode, String paCode, int days) {
		 mailOperationsService.generateResditPublishReport(companyCode, paCode, days);
	}

	public String[] findGrandTotals(CarditEnquiryFilterModel carditEnquiryFilterModel) {
		return mailOperationsService.findGrandTotals(carditEnquiryFilterModel);
	}

	public ScannedMailDetailsModel doLATValidation(Collection<MailbagModel> newMailbgsModel, boolean isScanned) throws MailHHTBusniessException {
		return mailOperationsService.doLATValidation(newMailbgsModel, isScanned);
	}


	public ScannedMailDetailsModel saveMailUploadDetailsForAndroid(MailUploadModel mailBagModel, String scanningPort) throws PersistenceException, MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException {
		return mailOperationsService.saveMailUploadDetailsForAndroid(mailBagModel, scanningPort);
	}

	public ScannedMailDetailsModel validateMailBagDetails(MailUploadModel mailUploadModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException, ForceAcceptanceException {
		return mailOperationsService.validateMailBagDetails(mailUploadModel);
	}

	public String validateFromFile(FileUploadFilterVO fileUploadFilterVO) {
		return mailOperationsService.validateFromFile(fileUploadFilterVO);
	}

	public ScannedMailDetailsModel validateFlightAndContainer(MailUploadModel mailUploadModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException {
		return mailOperationsService.validateFlightAndContainer(mailUploadModel);
	}

	public Page<MailAcceptanceModel> findOutboundFlightsDetails(OperationalFlightModel operationalFlightModel,
			int pageNumber) {
		return mailOperationsService.findOutboundFlightsDetails(operationalFlightModel, pageNumber);
	}

	public Page<ContainerDetailsModel> getAcceptedContainers(OperationalFlightModel operationalFlightModel,
			int pageNumber) {
		return mailOperationsService.getAcceptedContainers(operationalFlightModel, pageNumber);
	}

	public Page<MailbagModel> getMailbagsinContainer(ContainerDetailsModel containerModel, int pageNumber) {
		return mailOperationsService.getMailbagsinContainer(containerModel, pageNumber);
	}

	public Page<DSNModel> getMailbagsinContainerdsnview(ContainerDetailsModel containerModel, int pageNumber) {
		return mailOperationsService.getMailbagsinContainerdsnview(containerModel, pageNumber);
	}

	public MailbagModel findCarditSummaryView(CarditEnquiryFilterModel carditEnquiryFilterModel) {
		return mailOperationsService.findCarditSummaryView(carditEnquiryFilterModel);
	}

	public Page<MailbagModel> findGroupedCarditMails(CarditEnquiryFilterModel carditEnquiryFilterModel,
			int pageNumber) {
		return mailOperationsService.findGroupedCarditMails(carditEnquiryFilterModel, pageNumber);
	}

	public MailbagModel findLyinglistSummaryView(MailbagEnquiryFilterModel mailbagEnquiryFilterModel) {
		return mailOperationsService.findLyinglistSummaryView(mailbagEnquiryFilterModel);
	}

	public Page<MailbagModel> findGroupedLyingList(MailbagEnquiryFilterModel mailbagEnquiryFilterModel,
			int pageNumber) {
		return mailOperationsService.findGroupedLyingList(mailbagEnquiryFilterModel, pageNumber);
	}

	public Page<MailAcceptanceModel> findOutboundCarrierDetails(OperationalFlightModel operationalFlightModel,
			int pageNumber) {
		return mailOperationsService.findOutboundCarrierDetails(operationalFlightModel, pageNumber);
	}

	public Page<MailbagModel> getMailbagsinCarrierContainer(ContainerDetailsModel containerModel, int pageNumber) {
		return mailOperationsService.getMailbagsinCarrierContainer(containerModel, pageNumber);
	}

	public Page<DSNModel> getMailbagsinCarrierdsnview(ContainerDetailsModel containerModel, int pageNumber) {
		return mailOperationsService.getMailbagsinCarrierdsnview(containerModel, pageNumber);
	}

	public Collection<DSNModel> getDSNsForContainer(ContainerDetailsModel containerModel) {
		return mailOperationsService.getDSNsForContainer(containerModel);
	}

	public Collection<DSNModel> getRoutingInfoforDSN(Collection<DSNModel> dsnVosModel,
			ContainerDetailsModel containerDetailsModel) {
		return mailOperationsService.getRoutingInfoforDSN(dsnVosModel, containerDetailsModel);
	}

	public Page<MailArrivalModel> listFlightDetails(MailArrivalModel mailArrivalModel) {
		try {
			return mailOperationsService.listFlightDetails(mailArrivalModel);
		} catch (PersistenceException | MailOperationsBusinessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void closeInboundFlights(Collection<OperationalFlightModel> operationalFlightsModel) throws MailOperationsBusinessException {
		mailOperationsService.closeInboundFlights(operationalFlightsModel);
	}

	public void reopenInboundFlights(Collection<OperationalFlightModel> operationalFlightsModel) {
		 mailOperationsService.reopenInboundFlights(operationalFlightsModel);
	}

	public MailArrivalModel populateMailArrivalVOForInbound(OperationalFlightModel operationalFlightModel) {
		try {
			return mailOperationsService.populateMailArrivalVOForInbound(operationalFlightModel);
		} catch (MailOperationsBusinessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Page<ContainerDetailsModel> findArrivedContainersForInbound(MailArrivalFilterModel mailArrivalFilterModel) throws MailOperationsBusinessException {
		return mailOperationsService.findArrivedContainersForInbound(mailArrivalFilterModel);
	}

	public Page<MailbagModel> findArrivedMailbagsForInbound(MailArrivalFilterModel mailArrivalFilterModel) throws MailOperationsBusinessException {
		return mailOperationsService.findArrivedMailbagsForInbound(mailArrivalFilterModel);
	}

	public Page<DSNModel> findArrivedDsnsForInbound(MailArrivalFilterModel mailArrivalFilterModel) throws MailOperationsBusinessException {
		return mailOperationsService.findArrivedDsnsForInbound(mailArrivalFilterModel);
	}

	public OffloadModel findOffLoadDetails(OffloadFilterModel offloadFilterModel) {
		return mailOperationsService.findOffLoadDetails(offloadFilterModel);
	}

	public MailInConsignmentModel populatePCIDetailsforUSPS(MailInConsignmentModel mailInConsignmentModel,
			String airport, String companyCode, String rcpOrg, String rcpDest, String year) {
		return mailOperationsService.populatePCIDetailsforUSPS(mailInConsignmentModel, airport, companyCode,
				rcpOrg, rcpDest, year);
	}

	public void calculateULDContentId(Collection<ContainerDetailsModel> containersModel,
			OperationalFlightModel toFlightModel) {
		mailOperationsService.calculateULDContentId(containersModel, toFlightModel);
	}

	public void saveScreeningDetails(ScannedMailDetailsModel scannedMailDetailsModel) {
		mailOperationsService.saveScreeningDetails(scannedMailDetailsModel);
	}

	public Page<RunnerFlightVO> findRunnerFlights(RunnerFlightFilterVO runnerFlightFilterVO) throws PersistenceException {
		return mailOperationsService.findRunnerFlights(runnerFlightFilterVO);
	}

	public Page<MailbagModel> findMailbagsForTruckFlight(MailbagEnquiryFilterModel mailbagEnquiryFilterModel,
			int pageNumber) {
		return mailOperationsService.findMailbagsForTruckFlight(mailbagEnquiryFilterModel, pageNumber);
	}

	public MailbagModel findDsnAndRsnForMailbag(MailbagModel mailbagModel) {
		return mailOperationsService.findDsnAndRsnForMailbag(mailbagModel);
	}

	public Page<ForceMajeureRequestModel> listForceMajeureApplicableMails(ForceMajeureRequestFilterModel filterModel,
			int pageNumber) {
		return mailOperationsService.listForceMajeureApplicableMails(filterModel, pageNumber);
	}

	public void saveForceMajeureRequest(ForceMajeureRequestFilterModel filterModel) {
		 mailOperationsService.saveForceMajeureRequest(filterModel);
	}

	public InvoiceTransactionLogVO initTxnForForceMajeure(InvoiceTransactionLogVO invoiceTransactionLogVO) {
		return mailOperationsService.initTxnForForceMajeure(invoiceTransactionLogVO);
	}

	public Page<ForceMajeureRequestModel> listForceMajeureDetails(ForceMajeureRequestFilterModel filterModel,
			int pageNumber) {
		return mailOperationsService.listForceMajeureDetails(filterModel, pageNumber);
	}

	public Page<ForceMajeureRequestModel> listForceMajeureRequestIds(ForceMajeureRequestFilterModel filterModel,
			int pageNumber) {
		return mailOperationsService.listForceMajeureRequestIds(filterModel, pageNumber);
	}

	public void deleteForceMajeureRequest(Collection<ForceMajeureRequestModel> requestsModel) {
		 mailOperationsService.deleteForceMajeureRequest(requestsModel);
	}

	public void updateForceMajeureRequest(ForceMajeureRequestFilterModel requestModel) {
		 mailOperationsService.updateForceMajeureRequest(requestModel);
	}

	public Collection<ContainerModel> findAllContainersInAssignedFlight(
			FlightValidationVO reassignedFlightValidationVO) {
		return mailOperationsService.findAllContainersInAssignedFlight(reassignedFlightValidationVO);
	}

	public Collection<ErrorVO> processResditMails(Collection<MailScanDetailModel> mailScanDetailModel) throws MailOperationsBusinessException, MailHHTBusniessException, MailMLDBusniessException {
		return mailOperationsService.processResditMails(mailScanDetailModel);
	}

	public long findMailBagSequenceNumberFromMailIdr(String mailIdr, String companyCode) {
		return mailOperationsService.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
	}

	public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) {
		try {
			return mailOperationsService.processMailDataFromExcel(fileUploadFilterVO);
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void onStatustoReadyforDelivery(MailArrivalModel mailArrivalModel) throws MailOperationsBusinessException {
		 mailOperationsService.onStatustoReadyforDelivery(mailArrivalModel);
	}

	public void saveContentID(Collection<ContainerModel> containersModel) {
		 mailOperationsService.saveContentID(containersModel);
	}

	public void updateActualWeightForMailbag(MailbagModel paramMailbagModel) {
		 mailOperationsService.updateActualWeightForMailbag(paramMailbagModel);
	}

	public Collection<MailMonitorSummaryModel> getPerformanceMonitorDetails(MailMonitorFilterModel filterModel) {
		return mailOperationsService.getPerformanceMonitorDetails(filterModel);
	}

	public Page<MailbagModel> getPerformanceMonitorMailbags(MailMonitorFilterModel filterModel, String type,
			int pageNumber) {
		return mailOperationsService.getPerformanceMonitorMailbags(filterModel, type, pageNumber);
	}

	public MailManifestModel findMailbagManifest(OperationalFlightModel OperationalFlightModel) {
		return mailOperationsService.findMailbagManifest(OperationalFlightModel);
	}
	public MailManifestModel findMailAWBManifest(OperationalFlightModel OperationalFlightModel) {
		return mailOperationsService.findMailAWBManifest(OperationalFlightModel);
	}
	public MailManifestModel findDSNMailbagManifest(OperationalFlightModel OperationalFlightModel) {
		return mailOperationsService.findDSNMailbagManifest(OperationalFlightModel);
	}
	public MailManifestModel findDestnCatManifest(OperationalFlightModel OperationalFlightModel) {
		return mailOperationsService.findDestnCatManifest(OperationalFlightModel);
	}

	public MailbagModel findMailbagDetailsForMailbagEnquiryHHT(MailbagEnquiryFilterModel mailbagEnquiryFilterModel) {
		return mailOperationsService.findMailbagDetailsForMailbagEnquiryHHT(mailbagEnquiryFilterModel);
	}

	public MailbagModel findMailbagDetailsForMailInboundHHT(MailbagEnquiryFilterModel mailbagEnquiryFilterModel) {
		return mailOperationsService.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterModel);
	}

	public Collection<FlightSegmentCapacitySummaryVO> fetchFlightCapacityDetails(
			Collection<FlightFilterVO> flightFilterVOs) {
		return mailOperationsService.fetchFlightCapacityDetails(flightFilterVOs);
	}

	public String fetchMailContentIDs(DWSMasterVO dwsMasterVO, String containerNumber, String assignedAirport) throws FinderException {
		return mailOperationsService.fetchMailContentIDs(dwsMasterVO, containerNumber, assignedAirport);
	}

	public ArrayList<MailbagModel> findDuplicateMailbag(String companyCode, String mailBagId) {
		return mailOperationsService.findDuplicateMailbag(companyCode, mailBagId);
	}

	public void sendULDAnnounceForFlight(MailManifestModel mailManifestModel) {
		 mailOperationsService.sendULDAnnounceForFlight(mailManifestModel);
	}

	public boolean updateMailULDDetailsFromMHS(StorageUnitCheckinVO storageUnitCheckinVO) {
		return mailOperationsService.updateMailULDDetailsFromMHS(storageUnitCheckinVO);
	}

	public Page<MailbagModel> findDeviationMailbags(MailbagEnquiryFilterModel mailbagEnquiryFilterModel,
			int pageNumber) {
		return mailOperationsService.findDeviationMailbags(mailbagEnquiryFilterModel, pageNumber);
	}

	public String findProductsByName(String companyCode, String product) {
		return mailOperationsService.findProductsByName(companyCode, product);
	}

	public void buildResditProxy(Collection<ResditEventModel> resditEventModel) {
		 mailOperationsService.buildResditProxy(resditEventModel);
	}

	public void saveCarditTempMessages(Collection<CarditTempMsgVO> carditTempMsgVOs) throws MailOperationsBusinessException {
		 mailOperationsService.saveCarditTempMessages(carditTempMsgVOs);
	}

	public Collection<CarditTempMsgVO> getTempCarditMessages(String companyCode, String includeMailBoxIdr,
			String excludeMailBoxIdr, String includedOrigins, String excludedOrigins, int pageSize, int noOfdays) throws FinderException {
		return mailOperationsService.getTempCarditMessages(companyCode, includeMailBoxIdr, excludeMailBoxIdr,
				includedOrigins, excludedOrigins, pageSize, noOfdays);
	}

	public Collection<ErrorVO> saveCarditMsgs(EDIInterchangeModel ediInterchangeModel) throws MailOperationsBusinessException {
		return mailOperationsService.saveCarditMsgs(ediInterchangeModel);
	}

	public void deleteEmptyContainer(ContainerDetailsModel containerDetailsModel) {
		 mailOperationsService.deleteEmptyContainer(containerDetailsModel);
	}

	public void updateGateClearStatus(OperationalFlightModel operationalFlightModel,
			String gateClearanceStatus) {
		 mailOperationsService.updateGateClearStatus(operationalFlightModel, gateClearanceStatus);
	}

	public Collection<ErrorVO> processResditMailsForAllEvents(Collection<MailUploadModel> mailScanDetailModel) throws MailOperationsBusinessException, MailHHTBusniessException, MailMLDBusniessException {
		return mailOperationsService.processResditMailsForAllEvents(mailScanDetailModel);
	}

	public void flagResditForAcceptanceInTruck(ScannedMailDetailsModel scannedMailDetailsModel) throws MailHHTBusniessException, MailMLDBusniessException {
		 mailOperationsService.flagResditForAcceptanceInTruck(scannedMailDetailsModel);
	}

	public Collection<ErrorVO> saveCDTMessages(EDIInterchangeModel ediInterchangeModel) throws MailOperationsBusinessException {
		return mailOperationsService.saveCDTMessages(ediInterchangeModel);
	}

	public void removeFromInbound(OffloadModel offloadVoModel) throws MailOperationsBusinessException {
		 mailOperationsService.removeFromInbound(offloadVoModel);
	}

	public ErrorVO validateContainerNumberForDeviatedMailbags(ContainerDetailsModel containerDetailsModel,
			long mailSequenceNumber) throws MailOperationsBusinessException {
		return mailOperationsService.validateContainerNumberForDeviatedMailbags(containerDetailsModel,
				mailSequenceNumber);
	}

	public Collection<ForceMajeureRequestModel> findApprovedForceMajeureDetails(String companyCode, String mailBagId,
			long mailSequenceNumber) {
		return mailOperationsService.findApprovedForceMajeureDetails(companyCode, mailBagId,
				mailSequenceNumber);
	}

	public MailbagInULDForSegmentModel getManifestInfo(MailbagModel mailbagModel) throws PersistenceException {
		return mailOperationsService.getManifestInfo(mailbagModel);
	}

	public String checkMailInULDExistForNextSeg(String containerNumber, String airpotCode, String companyCode) {
		return mailOperationsService.checkMailInULDExistForNextSeg(containerNumber, airpotCode, companyCode);
	}

	public void updateRetainFlagForContainer(ContainerModel containerModel) throws FinderException {
		 mailOperationsService.updateRetainFlagForContainer(containerModel);
	}

	public void createAutoAttachAWBJobSchedule(AutoAttachAWBJobScheduleModel autoAttachAWBJobScheduleModel) {
		 mailOperationsService.createAutoAttachAWBJobSchedule(autoAttachAWBJobScheduleModel);
	}

	public ConsignmentDocumentModel findConsignmentScreeningDetails(String consignmentNumber, String companyCode,
			String poaCode) throws MailOperationsBusinessException {

		return mailOperationsService.findConsignmentScreeningDetails(consignmentNumber, companyCode, poaCode);
	}

	public void saveTransferFromManifest(TransferManifestModel transferManifestModel) throws MailBookingException, CapacityBookingProxyException, MailOperationsBusinessException, InvalidFlightSegmentException {
		 mailOperationsService.saveTransferFromManifest(transferManifestModel);
	}

	public void rejectTransferFromManifest(TransferManifestModel transferManifestModel) {
		 mailOperationsService.rejectTransferFromManifest(transferManifestModel);
	}

	public Collection<ConsignmentDocumentModel> findTransferManifestConsignmentDetails(
			TransferManifestModel transferManifestModel) {
		return mailOperationsService.findTransferManifestConsignmentDetails(transferManifestModel);
	}

	public MailbagModel findMailBillingStatus(MailbagModel mailbagModel) {
		return mailOperationsService.findMailBillingStatus(mailbagModel);
	}

	public String saveUploadedForceMajeureData(FileUploadFilterVO fileUploadFilterVO) {
		return mailOperationsService.saveUploadedForceMajeureData(fileUploadFilterVO);
	}

	public String findAutoPopulateSubtype(DocumentFilterVO documnetFilterVO) throws MailOperationsBusinessException {
		return mailOperationsService.findAutoPopulateSubtype(documnetFilterVO);
	}

	public String findOfficeOfExchangeForCarditMissingDomMail(String companyCode, String airportCode) {
		return mailOperationsService.findOfficeOfExchangeForCarditMissingDomMail(companyCode, airportCode);
	}

	public Map<String, ErrorVO> saveMailUploadDetailsFromMailOnload(Collection<MailUploadModel> mailUploadsModel) throws MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException {
		return mailOperationsService.saveMailUploadDetailsFromMailOnload(mailUploadsModel);
	}

	public void updateOriginAndDestinationForMailbag(Collection<MailbagModel> mailbagsModel) {
		mailOperationsService.updateOriginAndDestinationForMailbag(mailbagsModel);
	}

	public Collection<ImportFlightOperationsVO> findInboundFlightOperationsDetails(
			ImportOperationsFilterVO filterVO, Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException {
		return mailOperationsService.findInboundFlightOperationsDetails(filterVO, manifestFilterVOs);
	}

	public Collection<OffloadULDDetailsVO> findOffloadULDDetailsAtAirport(
			com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO filterVO) throws PersistenceException {
		return mailOperationsService.findOffloadULDDetailsAtAirport(filterVO);
	}

	public Collection<ManifestVO> findExportFlightOperationsDetails(ImportOperationsFilterVO filterVO,
																	Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException {
		return mailOperationsService.findExportFlightOperationsDetails(filterVO, manifestFilterVOs);
	}

	public Collection<ConsignmentDocumentModel> fetchConsignmentDetailsForUpload(
			FileUploadFilterVO fileUploadFilterVO) {
		return mailOperationsService.fetchConsignmentDetailsForUpload(fileUploadFilterVO);
	}

	public void saveUploadedConsignmentData(Collection<ConsignmentDocumentModel> consignmentDocumentsModel) throws MailOperationsBusinessException {
		 mailOperationsService.saveUploadedConsignmentData(consignmentDocumentsModel);
	}

	public void saveMailbagHistory(Collection<MailbagHistoryModel> mailbagHistorysModel) {
		 mailOperationsService.saveMailbagHistory(mailbagHistorysModel);
	}

	public void releaseContainers(Collection<ContainerModel> containersToBeReleasedModel) {
		 mailOperationsService.releaseContainers(containersToBeReleasedModel);
	}

	public Collection<ContainerDetailsModel> findContainerJourneyID(ConsignmentFilterModel consignmentFilterModel) {
		return mailOperationsService.findContainerJourneyID(consignmentFilterModel);
	}

	public void stampResdits(Collection<MailResditModel> mailResditsModel) throws MailOperationsBusinessException {
		 mailOperationsService.stampResdits(mailResditsModel);
	}

	public Collection<ContainerDetailsModel> findArrivalDetailsForReleasingMails(
			OperationalFlightModel operationalFlightModel) {
		return mailOperationsService.findArrivalDetailsForReleasingMails(operationalFlightModel);
	}

	public void performErrorStampingForFoundMailWebServices(MailUploadModel mailBagModel, String scanningPort) {
		mailOperationsService.performErrorStampingForFoundMailWebServices(mailBagModel, scanningPort);
	}

	public Collection<MailbagModel> getFoundArrivalMailBags(MailArrivalModel mailArrivalModel) {
		return mailOperationsService.getFoundArrivalMailBags(mailArrivalModel);
	}

	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterModel mailAuditFilterModel) {
		return mailOperationsService.findAssignFlightAuditDetails(mailAuditFilterModel);
	}

	public void dettachMailBookingDetails(Collection<MailbagModel> mailbagsModel) {
		 mailOperationsService.dettachMailBookingDetails(mailbagsModel);
	}

	public void attachAWBForMail(Collection<MailBookingDetailModel> mailBookingDetailsModel,
			Collection<MailbagModel> mailbagsModel) {
		 mailOperationsService.attachAWBForMail(mailBookingDetailsModel, mailbagsModel);
	}

	public String findNearestAirportOfCity(String companyCode, String exchangeCode) {
		return mailOperationsService.findNearestAirportOfCity(companyCode, exchangeCode);
	}

	public void markUnmarkUldIndicator(ContainerModel containerModel) {
		 mailOperationsService.markUnmarkUldIndicator(containerModel);
	}

	public long insertMailbagAndHistory(MailbagModel mailbagModel) {
		 return mailOperationsService.insertMailbagAndHistory(mailbagModel);
	}

	public ScannedMailDetailsModel saveMailUploadDetailsForULDFULIndicator(MailUploadModel mailBagModel,
			String scanningPort) throws PersistenceException, MailHHTBusniessException, MailOperationsBusinessException, MailMLDBusniessException {
		return mailOperationsService.saveMailUploadDetailsForULDFULIndicator(mailBagModel, scanningPort);
	}

	public void triggerEmailForPureTransferContainers(Collection<OperationalFlightModel> operationalFlightsModel) {
		 mailOperationsService.triggerEmailForPureTransferContainers(operationalFlightsModel);
	}

	public void saveSecurityDetails(Collection<ConsignmentScreeningModel> consignmentScreeningsModel) {
		mailOperationsService.saveSecurityDetails(consignmentScreeningsModel);
	}

	public MailbagModel listmailbagSecurityDetails(MailScreeningFilterModel mailScreeningFilterVoModel) {
		return mailOperationsService.listmailbagSecurityDetails(mailScreeningFilterVoModel);
	}

	public void editscreeningDetails(Collection<ConsignmentScreeningModel> consignmentScreeningsModel) {
		 mailOperationsService.editscreeningDetails(consignmentScreeningsModel);
	}

	public void deletescreeningDetails(Collection<ConsignmentScreeningModel> consignmentScreeningsModel) {
		 mailOperationsService.deletescreeningDetails(consignmentScreeningsModel);
	}

	public void saveMailSecurityStatus(MailbagModel mailbagModel) {
		 mailOperationsService.saveMailSecurityStatus(mailbagModel);
	}

	public void saveScreeningConsginorDetails(Map<String, Object> contTransferMap) {
		//TODO: Neo to copy method from classic
		// mailTrackingDefaultsServicesEJB.saveScreeningConsginorDetails(contTransferMap);
	}

	public MailbagModel findAirportFromMailbag(MailbagModel mailbagModel) throws FinderException {
		return mailOperationsService.findAirportFromMailbag(mailbagModel);
	}

	public void saveFligthLoadPlanForMail(Collection<FlightLoadPlanContainerModel> flightLoadPlanContainersModel) throws BusinessException {
		 mailOperationsService.saveFligthLoadPlanForMail(flightLoadPlanContainersModel);
	}

	public Collection<FlightLoadPlanContainerModel> findLoadPlandetails(
			SearchContainerFilterModel searchContainerFilterModel) {
		return mailOperationsService.findLoadPlandetails(searchContainerFilterModel);
	}

	public void saveConsignmentDetailsMaster(ConsignmentDocumentModel consignmentDocumentModel) {
		 mailOperationsService.saveConsignmentDetailsMaster(consignmentDocumentModel);
	}

	public Map<String, ErrorVO> saveSecurityScreeningFromService(
			SecurityAndScreeningMessageVO securityAndScreeningMessageVO) throws MailOperationsBusinessException {
		return mailOperationsService.saveSecurityScreeningFromService(securityAndScreeningMessageVO);
	}

	public Map<String, String> findAirportParameterCode(FlightFilterVO flightFilterVO, Collection<String> parCodes) {
		return mailOperationsService.findAirportParameterCode(flightFilterVO, parCodes);
	}

	public Collection<SecurityScreeningValidationModel> findSecurityScreeningValidations(
			SecurityScreeningValidationFilterModel securityScreeningValidationFilterModel) throws MailOperationsBusinessException {
		return mailOperationsService.findSecurityScreeningValidations(securityScreeningValidationFilterModel);
	}

	public SecurityScreeningValidationModel doSecurityAndScreeningValidationAtContainerLevel(
			OperationalFlightModel operationalFlightModel, Collection<ContainerModel> selectedContainersModel) throws BusinessException {
		return mailOperationsService.doSecurityAndScreeningValidationAtContainerLevel(operationalFlightModel,
				selectedContainersModel);
	}

	public void updateIntFlgAsNForMailBagsInConatiner(HbaMarkingModel hbaMarkingModel) {
		 mailOperationsService.updateIntFlgAsNForMailBagsInConatiner(hbaMarkingModel);
	}

	public String generateAutomaticBarrowId(String cmpcod) {
		return mailOperationsService.generateAutomaticBarrowId(cmpcod);
	}

	public Collection<ConsignmentDocumentModel> findCN46TransferManifestDetails(
			TransferManifestModel transferManifestModel) {
		return mailOperationsService.findCN46TransferManifestDetails(transferManifestModel);
	}

	public Collection<SecurityScreeningValidationModel> doApplicableRegulationFlagValidationForPABuidContainer(
			MailbagModel mailbagModel, SecurityScreeningValidationFilterModel securityScreeningValidationFilterModel) throws BusinessException {
		return mailOperationsService.doApplicableRegulationFlagValidationForPABuidContainer(mailbagModel,
				securityScreeningValidationFilterModel);
	}

	public Collection<MailAcceptanceModel> fetchFlightPreAdviceDetails(Collection<FlightFilterVO> flightFilterVOs) {
		return mailOperationsService.fetchFlightPreAdviceDetails(flightFilterVOs);
	}

	public ContainerModel updateActualWeightForMailContainer(ContainerModel containerModel) {
		return mailOperationsService.updateActualWeightForMailContainer(containerModel);
	}

	public Collection<OperationalFlightModel> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleModel mailInboundAutoAttachAWBJobScheduleModel) {
		return mailOperationsService
				.findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleModel);
	}

	public Page<MailTransitModel> findMailTransit(MailTransitFilterModel mailTransitFilterModel, int pageNumber)
			throws PersistenceException {
		return mailOperationsService.findMailTransit(mailTransitFilterModel, pageNumber);
	}

	public Collection<FlightSegmentCapacitySummaryVO> findFlightListings(FlightFilterVO filterVO) {
		return mailOperationsService.findFlightListings(filterVO);
	}

	public Page<FlightSegmentCapacitySummaryVO> findActiveAllotments(FlightSegmentCapacityFilterVO filterVo) {
		return mailOperationsService.findActiveAllotments(filterVo);
	}

	public MailbagModel findMailConsumed(MailTransitFilterModel filterVoModel) {
		return mailOperationsService.findMailConsumed(filterVoModel);
	}

	public void createPAWBForConsignment(int noOfDays) throws PersistenceException {
		 mailOperationsService.createPAWBForConsignment(noOfDays);
	}
	public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> fetchMailIndicatorForProgress(
			Collection<FlightListingFilterVO> flightListingFilterVOs) {
		return mailOperationsService.fetchMailIndicatorForProgress(flightListingFilterVOs);
	}

	public void closeInboundFlight(OperationalFlightModel operationalFlightModel) {
		 mailOperationsService.closeInboundFlight(operationalFlightModel);
	}
	public ConsignmentDocumentModel generateConsignmentSummaryReportDtls(ConsignmentFilterModel consignmentFilterModel)  {
		return mailOperationsService.generateConsignmentSummaryReportDtls(consignmentFilterModel);
	}

	public TransferManifestModel generateTransferManifestReportDetails(String cmpcod,String transferManifestId) {
		return mailOperationsService.generateTransferManifestReportDetails(cmpcod, transferManifestId);
	}

	public ConsignmentDocumentModel generateConsignmentSecurityReportDtls(ConsignmentFilterModel consignmentFilterModel) {
		return mailOperationsService.generateConsignmentSecurityReportDtls(consignmentFilterModel);
	}
	public Collection<MailbagModel> generateMailTagDetails(Collection<MailbagModel>mailbagModels) {
		return mailOperationsService.generateMailTagDetails(mailbagModels);
	}
	public ConsignmentDocumentModel generateConsignmentReportDtls(ConsignmentFilterModel consignmentFilterModel)  {
		return mailOperationsService.generateConsignmentReportDtls(consignmentFilterModel);
	}
	public MailbagModel fetchMailSecurityDetails(MailScreeningFilterModel mailScreeningFilterModel)  {
		return mailOperationsService.fetchMailSecurityDetails(mailScreeningFilterModel);
	}
	public String findRoutingDetails(String companyCode,long malseqnum){
		return mailOperationsService.findRoutingDetails(companyCode,malseqnum);
	}
	public Collection<ConsignmentDocumentModel> generateCN46ConsignmentReportDtls(ConsignmentFilterModel consignmentFilterModel)  {
		return mailOperationsService.generateCN46ConsignmentReportDtls(consignmentFilterModel);
	}

	public Collection<ConsignmentDocumentModel> generateCN46ConsignmentSummaryReportDtls(ConsignmentFilterModel consignmentFilterModel) {
		return mailOperationsService.generateCN46ConsignmentSummaryReportDtls(consignmentFilterModel);
	}


	public List<TransferManifestModel> findTransferManifestDetailsForTransfer(String companyCode, String tranferManifestId) {
		return mailOperationsService.findTransferManifestDetailsForTransfer(companyCode,tranferManifestId);
	}

	  public Collection<MailStatusModel> generateMailStatusRT(MailStatusFilterModel mailStatusFilterModel) {
		return mailOperationsService.generateMailStatusRT(mailStatusFilterModel);
  	}
	 public Collection<DailyMailStationReportModel> generateDailyMailStationRT(DailyMailStationFilterModel filterModel) {
		return mailOperationsService.generateDailyMailStationRT(filterModel);
	}

	public Collection<MailHandedOverModel> generateMailHandedOverRT(MailHandedOverFilterModel mailHandedOverFilterModel)  {
		return mailOperationsService. generateMailHandedOverRT( mailHandedOverFilterModel);
	}
	public Collection<DamagedMailbagModel> findDamageMailReport(DamageMailFilterModel damageMailReportFiltermodel){
		return mailOperationsService.findDamageMailReport(damageMailReportFiltermodel);
	}

	public MailManifestModel findImportManifestDetails(OperationalFlightModel operationalFlightModel){
		return mailOperationsService.findImportManifestDetails(operationalFlightModel);
	}

	public byte[] findMailbagDamageImages(String companyCode, String id) {
		return mailOperationsService.findMailbagDamageImages(companyCode,id);
	}

	public void publishMailDetails(MailMasterDataFilterModel mailMasterDataFilterModel) {
		mailOperationsService.publishMailDetails(mailMasterDataFilterModel);
	}
}
