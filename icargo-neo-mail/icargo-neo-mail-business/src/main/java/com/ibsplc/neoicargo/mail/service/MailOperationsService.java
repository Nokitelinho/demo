package com.ibsplc.neoicargo.mail.service;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailStatusVO;
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
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.lang.notation.BusinessService;
import com.ibsplc.neoicargo.mail.MailOperationsAPI;
import com.ibsplc.neoicargo.mail.component.*;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.AttachAWBException;
import com.ibsplc.neoicargo.mail.exception.CapacityBookingProxyException;
import com.ibsplc.neoicargo.mail.exception.CloseFlightException;
import com.ibsplc.neoicargo.mail.exception.ContainerAssignmentException;
import com.ibsplc.neoicargo.mail.exception.DuplicateDSNException;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.exception.FlightClosedException;
import com.ibsplc.neoicargo.mail.exception.FlightDepartedException;
import com.ibsplc.neoicargo.mail.exception.ForceAcceptanceException;
import com.ibsplc.neoicargo.mail.exception.InvalidFlightSegmentException;
import com.ibsplc.neoicargo.mail.exception.InvalidMailTagFormatException;
import com.ibsplc.neoicargo.mail.exception.MailBookingException;
import com.ibsplc.neoicargo.mail.exception.MailDefaultStorageUnitException;
import com.ibsplc.neoicargo.mail.exception.MailMLDBusniessException;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.exception.MailbagAlreadyAcceptedException;
import com.ibsplc.neoicargo.mail.exception.MailbagAlreadyReturnedException;
import com.ibsplc.neoicargo.mail.exception.MailbagIncorrectlyDeliveredException;
import com.ibsplc.neoicargo.mail.exception.ReassignmentException;
import com.ibsplc.neoicargo.mail.exception.ReturnNotPossibleException;
import com.ibsplc.neoicargo.mail.exception.StockcontrolDefaultsProxyException;
import com.ibsplc.neoicargo.mail.exception.ULDDefaultsProxyException;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.model.*;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** 
 * @author
 * @ejb.bean description= MODULEdisplay-name= MODULE jndi-name= "com.ibsplc.icargo.services.mail.operations.MailTrackingDefaultsServices" name="MailTrackingDefaultsServices" type="Stateless" view-type="remote" remote-business-interface= "com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI"
 * @ejb.transaction type="Supports" Bean implementation class for EnterpriseBean: MailTrackingDefaultsServices
 */
@BusinessService
@Component("mailOperationsService")
@Slf4j
public class MailOperationsService {
	@Autowired
	private MailOperationsMapper mailOperationsMapper;
	@Autowired
	private MailOperationsAPI mailOperationsAPI;
	@Autowired
	private ResditController resditController;
	@Autowired
	private MLDController mLDController;
	@Autowired
	private DocumentController documentController;
	@Autowired
	private MailUploadController mailUploadController;
	@Autowired
	private MailController mailController;
	@Autowired
	private MailOperationsComponent mailOperationsComponent;
	@Autowired
	MeasureMapper measureMapper;
	private static final String MODULE = "MailOperationsService";

	/**
	* @return void
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @author a-5526 This method is used to save mailbag scanned from HHT
	*/
	public void saveMailScannedDetails(Collection<MailScanDetailModel> mailScanDetailsModel) {
		Collection<MailScanDetailVO> mailScanDetailVOs = mailOperationsMapper
				.mailScanDetailModelsToMailScanDetailVO(mailScanDetailsModel);
		mailUploadController.saveMailScannedDetails(mailScanDetailVOs);
	}

	/**
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @author a-5526 This method is used to save mailbag scanned from HHT
	*/
	public ScannedMailDetailsModel saveMailUploadDetails(Collection<MailUploadModel> mailBagsModel, String scanningPort)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException {
		Collection<MailUploadVO> mailBagVOs = mailOperationsMapper.mailUploadModelsToMailUploadVO(mailBagsModel);
//TODO:Neo to correct
		//		try {
//			return mailTrackingDefaultsMapper.scannedMailDetailsVOToScannedMailDetailsModel(
//					mailUploadController.saveMailUploadDetails(mailBagVOs, scanningPort));
//		} catch (ForceAcceptanceException e) {
//			throw new MailHHTBusniessException(e);
//		}
		return null;
	}

	public void saveMailDetailsFromJob(Collection<MailUploadModel> mailBagsModel, String scanningPort)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException {
		Collection<MailUploadVO> mailBagVOs = mailOperationsMapper.mailUploadModelsToMailUploadVO(mailBagsModel);
		Transaction txn = null;
		try {
			TransactionProvider tm = PersistenceController.__getTransactionProvider();
			txn = tm.getNewTransaction(false);
			mailUploadController.saveMailUploadDetails(mailBagVOs, scanningPort);
			txn.commit();
		} catch (MailHHTBusniessException t) {
			if (txn != null) {
				txn.rollback();
			}
			throw t;
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (ForceAcceptanceException e) {
			e.printStackTrace();
		}
	}

	public void deliverMailbags(MailArrivalModel mailArrivalModel) throws MailOperationsBusinessException {
		MailArrivalVO mailArrivalVO = mailOperationsMapper.mailArrivalModelToMailArrivalVO(mailArrivalModel);
		log.debug(MODULE + " : " + "deliverMailbags" + " Entering");
		try {
			mailController.deliverMailbags(mailArrivalVO);
		} catch (ContainerAssignmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (DuplicateMailBagsException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailbagIncorrectlyDeliveredException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (InvalidFlightSegmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (FlightClosedException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (ULDDefaultsProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (CapacityBookingProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailBookingException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailOperationsBusinessException ex) {
			throw new MailOperationsBusinessException(ex);
		}
		log.debug(MODULE + " : " + "deliverMailbags" + " Exiting");
	}

	/**
	* @throws MailOperationsBusinessException
	*/
	public void saveScannedDeliverMails(Collection<MailArrivalModel> deliverVosForSaveModel)
			throws BusinessException {
		Collection<MailArrivalVO> deliverVosForSave = mailOperationsMapper
				.mailArrivalModelsToMailArrivalVO(deliverVosForSaveModel);
		log.debug(MODULE + " : " + "saveScannedMails" + " Entering");
		try {
			mailController.saveScannedDeliverMails(deliverVosForSave);
		} catch (ContainerAssignmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (DuplicateMailBagsException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailbagIncorrectlyDeliveredException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (InvalidFlightSegmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (FlightClosedException ex) {
			throw new BusinessException(ex);
		} catch (ULDDefaultsProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (CapacityBookingProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailBookingException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailOperationsBusinessException ex) {
			throw new MailOperationsBusinessException(ex);
		}
		log.debug(MODULE + " : " + "saveScannedMails" + " Exiting");
	}

	/** 
	* This method is used to saveAcceptanceDetails
	* @throws MailOperationsBusinessException
	*/
	public Collection<ScannedMailDetailsModel> saveAcceptanceDetails(MailAcceptanceModel mailAcceptanceModel)
			throws MailOperationsBusinessException {
		MailAcceptanceVO mailAcceptanceVO = mailOperationsMapper
				.mailAcceptanceModelToMailAcceptanceVO(mailAcceptanceModel);
		log.debug(MODULE + " : " + "saveAcceptanceDetails" + " Entering");
		try {
			return mailOperationsMapper.scannedMailDetailsVOsToScannedMailDetailsModel(
					mailController.saveAcceptanceDetails(mailAcceptanceVO));
		} catch (FlightClosedException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (DuplicateMailBagsException ex) {
			throw new MailOperationsBusinessException(ex.getErrors());
		} catch (ContainerAssignmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (InvalidFlightSegmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (ULDDefaultsProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (DuplicateDSNException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (CapacityBookingProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailBookingException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailDefaultStorageUnitException ex) {
			throw new MailOperationsBusinessException(ex);
		}
	}

	/**
	* @throws MailOperationsBusinessException
	* @author a-1936
	*/
	public Collection<ContainerDetailsModel> offload(OffloadModel offloadVoModel) throws MailOperationsBusinessException {
		OffloadVO offloadVo = mailOperationsMapper.offloadModelToOffloadVO(offloadVoModel);
		try {
			return mailOperationsMapper
					.containerDetailsVOsToContainerDetailsModel(mailController.offload(offloadVo));
		} catch (FlightClosedException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (FlightDepartedException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (ReassignmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (ULDDefaultsProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (CapacityBookingProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailBookingException ex) {
			throw new MailOperationsBusinessException(ex);
		}
	}

	/** */
	public Collection<ContainerDetailsModel> returnMailbags(Collection<MailbagModel> mailbagsToReturnModel)
			throws MailOperationsBusinessException {
		Collection<MailbagVO> mailbagsToReturn = mailOperationsMapper
				.mailbagModelsToMailbagVO(mailbagsToReturnModel);
		log.debug(MODULE + " : " + "returnMailbags" + " Entering");
		try {
			return mailOperationsMapper
					.containerDetailsVOsToContainerDetailsModel(mailController.returnMailbags(mailbagsToReturn));
		} catch (MailbagAlreadyReturnedException e) {
			throw new MailOperationsBusinessException(e);
		} catch (FlightClosedException e) {
			throw new MailOperationsBusinessException(e);
		} catch (ReturnNotPossibleException e) {
			throw new MailOperationsBusinessException(e);
		} catch (ReassignmentException e) {
			throw new MailOperationsBusinessException(e);
		} catch (CapacityBookingProxyException e) {
			throw new MailOperationsBusinessException(e);
		} catch (MailBookingException e) {
			throw new MailOperationsBusinessException(e);
		} catch (DuplicateMailBagsException e) {
			throw new MailOperationsBusinessException(e);
		}
	}

	/**
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @author a-5526 This method is used to save mailbag scanned from HHT
	*/
	public void saveAndProcessMailBags(ScannedMailDetailsModel scannedMailDetailsModel)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException {
		ScannedMailDetailsVO scannedMailDetailsVO = mailOperationsMapper
				.scannedMailDetailsModelToScannedMailDetailsVO(scannedMailDetailsModel);
		try {
			mailUploadController.saveAndProcessMailBags(scannedMailDetailsVO);
		} catch (ForceAcceptanceException e) {
			throw new MailHHTBusniessException(e.getErrorCode());
		} catch (RemoteException e) {
			throw new MailHHTBusniessException(e.getMessage());
		}
	}

	/** 
	* @param flightFilterVO
	* @return Collection<FlightValidationVO>
	* @throws SystemException
	* @author a-1936 This method is used to validate the Flight
	*/
	public Collection<FlightValidationVO> validateFlight(FlightFilterVO flightFilterVO) {
		log.debug(MODULE + " : " + "isFlightClosedForMailOperations" + " Entering");
		return mailController.validateFlight(flightFilterVO);
	}

	/** 
	* @param flightFilterVO
	* @return Collection<FlightValidationVO>
	* @throws SystemException
	* @author a-5160 This method is used to validate Mail Flight
	*/
	public Collection<FlightValidationVO> validateMailFlight(FlightFilterVO flightFilterVO) {
		log.debug(MODULE + " : " + "isFlightClosedForMailOperations" + " Entering");
		return mailController.validateMailFlight(flightFilterVO);
	}

	/** 
	* This method is used to get the Location of the Warehouse for acceptmail
	*/
	public Map<String, Collection<String>> findWarehouseTransactionLocations(LocationEnquiryFilterVO filterVO) {
		log.debug(MODULE + " : " + "findWarehouseTransactionLocations" + " Entering");
		return mailController.findWarehouseTransactionLocations(filterVO);
	}

	/** 
	* @param companyCode
	* @param airportCode
	* @return
	* @throws SystemException
	* @author a-1936 This method is used to findallthe Warehouses for the GivenAirport
	*/
	public Collection<WarehouseVO> findAllWarehouses(String companyCode, String airportCode) {
		log.debug(MODULE + " : " + "findAllWarehouses" + " Entering");
		return mailController.findAllWarehouses(companyCode, airportCode);
	}

	/**
	* @return
	* @throws SystemException
	* @author a-1936
	*/
	public Collection<ContainerDetailsModel> findMailbagsInContainer(
			Collection<ContainerDetailsModel> containersModel) {
		Collection<ContainerDetailsVO> containers = mailOperationsMapper
				.containerDetailsModelsToContainerDetailsVO(containersModel);
		return mailOperationsMapper
				.containerDetailsVOsToContainerDetailsModel(mailController.findMailbagsInContainer(containers));
	}

	/**
	* @return
	* @author a-1936 This method is used to validate the Container whether itcan be assigned to a particularFlight or CarrierDestination
	*/
	public ContainerModel validateContainer(String airportCode, ContainerModel containerModel)
			throws BusinessException {
		ContainerVO containerVO = mailOperationsMapper.containerModelToContainerVO(containerModel);
		log.debug(MODULE + " : " + "validateContainer" + " Entering");
		try {
			return mailOperationsMapper
					.containerVOToContainerModel(mailController.validateContainer(airportCode, containerVO));
		} catch (ContainerAssignmentException ex) {
			throw new BusinessException(ex.getErrors());
		} catch (ULDDefaultsProxyException uldDefaultsProxyException) {
			throw new BusinessException(uldDefaultsProxyException.getErrors());
		}
	}

	/**
	* @throws SystemException
	* @author a-1936 This method is used to check whether the Flight is closedfor Operations
	*/
	public boolean isFlightClosedForMailOperations(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "isFlightClosedForMailOperations" + " Entering");
		return mailController.isFlightClosedForOperations(operationalFlightVO);
	}

	/**
	* @author a-1936 This method is used to validate the MailBags and themailTagFormat
	*/
	public boolean validateMailBags(Collection<MailbagModel> mailbagVosModel) throws BusinessException {
		Collection<MailbagVO> mailbagVos = mailOperationsMapper.mailbagModelsToMailbagVO(mailbagVosModel);
		log.debug(MODULE + " : " + "validateMailBags" + " Entering");
		try {
			return mailController.validateMailBags(mailbagVos);
		} catch (InvalidMailTagFormatException ex) {
			throw new BusinessException(ex.getErrors());
		}
	}

	/** 
	* @param companyCode
	* @param airportCode
	* @param warehouseCode
	* @param locationCode
	* @return
	* @throws SystemException
	* @author a-1936 This method is used to validate the location ..
	*/
	public LocationValidationVO validateLocation(String companyCode, String airportCode, String warehouseCode,
			String locationCode) {
		log.debug(MODULE + " : " + "validateLocation" + " Entering");
		log.debug(MODULE + " : " + "validateLocation" + " Entering");
		return mailController.validateLocation(companyCode, airportCode, warehouseCode, locationCode);
	}

	/** 
	* @param companyCode
	* @return
	* @throws SystemException
	* @author A-3227 - FEB 10, 2009
	*/
	public Collection<DespatchDetailsModel> validateConsignmentDetails(String companyCode,
			Collection<DespatchDetailsModel> despatchDetailssModel) {
		Collection<DespatchDetailsVO> despatchDetailsVOs = mailOperationsMapper
				.despatchDetailsModelsToDespatchDetailsVO(despatchDetailssModel);
		log.debug(MODULE + " : " + "validateConsignmentDetails" + " Entering");
		return mailOperationsMapper.despatchDetailsVOsToDespatchDetailsModel(
				mailController.validateConsignmentDetails(companyCode, despatchDetailsVOs));
	}

	/**
	 *
	 * @param dsnVosModel
	 * @return
	 * @throws MailOperationsBusinessException
	 */
	public boolean validateDSNs(Collection<DSNModel> dsnVosModel) throws MailOperationsBusinessException {
		Collection<DSNVO> dsnVos = mailOperationsMapper.dSNModelsToDSNVO(dsnVosModel);
		log.debug(MODULE + " : " + "validateDSNs" + " Entering");
		try {
			return mailController.validateDSNs(dsnVos);
		} catch (InvalidMailTagFormatException e) {
			throw new MailOperationsBusinessException(e);
		}
	}

	/**
	* @throws SystemException
	*/
	public Collection<ContainerModel> findAllULDsInAssignedFlight(FlightValidationVO reassignedFlightValidationVO) {
		log.debug(MODULE + " : " + "findAllULDsInAssignedFlight" + " Entering");
		return mailOperationsMapper
				.containerVOsToContainerModel(mailController.findAllULDsInAssignedFlight(reassignedFlightValidationVO));
	}

	/**
	* @throws SystemException
	* @throws MailOperationsBusinessException
	 * */
	public void reassignContainers(Collection<ContainerModel> containersToReassignModel,
			OperationalFlightModel toFlightModel) throws MailOperationsBusinessException {
		OperationalFlightVO toFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(toFlightModel);
		Collection<ContainerVO> containersToReassign = mailOperationsMapper
				.containerModelsToContainerVO(containersToReassignModel);
		try {
			mailController.reassignContainers(containersToReassign, toFlightVO);
		} catch (FlightClosedException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (ContainerAssignmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (InvalidFlightSegmentException ex) {
			throw new MailOperationsBusinessException(ex.getErrors());
		} catch (ULDDefaultsProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (CapacityBookingProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailBookingException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailDefaultStorageUnitException ex) {
			throw new MailOperationsBusinessException(ex);
		}
	}

	/**
	* @author A-2037 This method is used to find Preadvice for outbound mailand it gives the details of the ULDs and the receptacles based on CARDIT
	*/
	public PreAdviceModel findPreAdvice(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "findPreAdvice" + " Entering");
		return mailOperationsMapper
				.preAdviceVOToPreAdviceModel(mailController.findPreAdvice(operationalFlightVO));
	}

	/**
	* @author a-1936This method is used to find the Transfer Manifest for the Different Transactions
	*/
	public Page<TransferManifestModel> findTransferManifest(TransferManifestFilterModel tranferManifestFilterVoModel) {
		TransferManifestFilterVO tranferManifestFilterVo = mailOperationsMapper
					.transferManifestFilterModelToTransferManifestFilterVO(tranferManifestFilterVoModel);

		Page<TransferManifestVO>  transferManifestVO=mailController.findTransferManifest(tranferManifestFilterVo);
		List<TransferManifestModel> transferManifestModel= new ArrayList<>();
		transferManifestModel.addAll(mailOperationsMapper.transferManifestVOsToTransferManifestModel(transferManifestVO));

		return new Page(transferManifestModel,transferManifestVO.getPageNumber(), transferManifestVO.getDefaultPageSize(),
					transferManifestVO.getActualPageSize(), transferManifestVO.getStartIndex(), transferManifestVO.getEndIndex(),
					transferManifestVO.hasNextPage(), transferManifestVO.getTotalRecordCount());
	}
	/**
	* @author a-1936 This method is used to find the containers and itsassociated DSNs in the Flight.
	*/
	public MailManifestModel findContainersInFlightForManifest(OperationalFlightModel operationalFlightVoModel) {
		OperationalFlightVO operationalFlightVo = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightVoModel);
		log.debug("MailTracking Defaults Services EJB" + " : " + "findContainersInFlight" + " Entering");
		return mailOperationsMapper.mailManifestVOToMailManifestModel(
				mailController.findContainersInFlightForManifest(operationalFlightVo));
	}

	/** 
	* @param companyCode
	* @param airportCode
	* @param isGHA
	* @return
	* @throws SystemException
	* @author a-1883
	*/
	public String findStockHolderForMail(String companyCode, String airportCode, Boolean isGHA) {
		log.debug(MODULE + " : " + "findStockHolderForMail" + " Entering");
		return mailController.findStockHolderForMail(companyCode, airportCode, isGHA);
	}

	/**
	* @return AWBDetailVO
	* @throws SystemException
	* @throws MailOperationsBusinessException
	* @author a-1883
	*/
	public AWBDetailModel findAWBDetails(AWBFilterModel aWBFilterModel) throws MailOperationsBusinessException {
			log.debug(MODULE + " : " + "findAWBDetails" + " Entering");

		try {
			return mailOperationsMapper.aWBDetailVOToAWBDetailModel(
					mailController.findAWBDetails(mailOperationsMapper.aWBFilterModelToAWBFilterVO(aWBFilterModel)));
		} catch (AttachAWBException e) {
			throw new MailOperationsBusinessException(e);
		}

	}

	/** 
	* This method is used to find the Offload Details for a Flight say at different levels say Containers,DSNS,MailBags
	* @return
	* @throws SystemException
	*/
	public OffloadModel findOffloadDetails(OffloadFilterModel offloadFilterModel) {
		OffloadFilterVO offloadFilterVO = mailOperationsMapper
				.offloadFilterModelToOffloadFilterVO(offloadFilterModel);
		OffloadVO offloadVo = mailController.findOffloadDetails(offloadFilterVO);
		Page<ContainerVO> containerVOS = offloadVo.getOffloadContainerDetails();
		Page<ContainerModel> containerModels = new Page();
		log.debug(MODULE + " : " + "findOffloadDetails" + " Entering");
		OffloadModel offloadModel=  mailOperationsMapper.offloadVOToOffloadModel(offloadVo);
		offloadModel.setOffloadContainerDetails(containerModels);
		return offloadModel;
	}

	/**
	* @author a-1936 This method is used to reopen the Flight
	*/
	public void reopenFlight(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "reopenFlight" + " Entering");
		mailController.reopenFlight(operationalFlightVO);
		log.debug(MODULE + " : " + "reopenFlight" + " Exiting");
	}

	/**
	* @throws SystemException
	* @throws MailOperationsBusinessException
	* @author a-3251 SREEJITH P.C.
	*/
	public void closeFlightAcceptance(OperationalFlightModel operationalFlightModel,
			MailAcceptanceModel mailAcceptanceModel) throws MailOperationsBusinessException {
		MailAcceptanceVO mailAcceptanceVO = mailOperationsMapper
				.mailAcceptanceModelToMailAcceptanceVO(mailAcceptanceModel);
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "closeFlightAcceptance" + " Entering");
		try {
			mailController.closeFlightAcceptance(operationalFlightVO, mailAcceptanceVO);
		} catch (ULDDefaultsProxyException e) {
			throw new MailOperationsBusinessException(e);
		} catch (CloseFlightException e) {
			throw new MailOperationsBusinessException(e);
		}
		log.debug(MODULE + " : " + "closeFlightAcceptance" + " Exiting");
	}

	/**
	* @author a-1936 This method is used to find the mailBags
	*/
	public Page<MailbagModel> findMailbags(MailbagEnquiryFilterModel mailbagEnquiryFilterModel, int pageNumber) {
		log.debug(MODULE + " : " + "findMailbags" + " Entering");
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = mailOperationsMapper
				.mailbagEnquiryFilterModelToMailbagEnquiryFilterVO(mailbagEnquiryFilterModel);
		Page<MailbagVO> mailbagVOS =
				mailController.findMailbags(mailbagEnquiryFilterVO, pageNumber);
		if ( mailbagVOS == null ) {
			return null;
		}
		List<MailbagModel> mailbagModels = new ArrayList<>();
			mailbagModels.addAll(mailOperationsMapper
					.mailbagVOsToMailbagModel(mailbagVOS));
		return new
				Page(mailbagModels,mailbagVOS.getPageNumber(),
				mailbagVOS.getDefaultPageSize(),mailbagVOS.getActualPageSize(),
				mailbagVOS.getStartIndex(),mailbagVOS.getEndIndex()
				, mailbagVOS.hasNextPage(),mailbagVOS.getTotalRecordCount());
	}
	/**
	* @throws MailOperationsBusinessException
	*/
	public TransferManifestModel transferContainers(Collection<ContainerModel> containersModel,
			OperationalFlightModel operationalFlightModel, String printFlag) throws MailOperationsBusinessException {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		Collection<ContainerVO> containerVOs = mailOperationsMapper.containerModelsToContainerVO(containersModel);
		log.debug(MODULE + " : " + "transferContainers" + " Entering");
		try {
			return mailOperationsMapper.transferManifestVOToTransferManifestModel(
					mailController.transferContainers(containerVOs, operationalFlightVO, printFlag));
		} catch (ContainerAssignmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (InvalidFlightSegmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (ULDDefaultsProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (CapacityBookingProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailBookingException ex) {
			throw new MailOperationsBusinessException(ex);
		}
	}

	/** 
	* @param companyCode
	* @author A-2037 This method is used to find the History of a Mailbag
	*/
	public Collection<MailbagHistoryModel> findMailbagHistories(String companyCode, String mailBagId,
			long mailSequenceNumber) {
		log.debug(MODULE + " : " + "findMailbagHistories" + " Entering");
		return mailOperationsMapper.mailbagHistoryVOsToMailbagHistoryModel(
				mailController.findMailbagHistories(companyCode, mailBagId, mailSequenceNumber));
	}

	public Collection<MailHistoryRemarksModel> findMailbagNotes(String mailBagId) {
		log.debug(MODULE + " : " + "findMailbagHistories" + " Entering");
		return mailOperationsMapper
				.mailHistoryRemarksVOsToMailHistoryRemarksModel(mailController.findMailbagNotes(mailBagId));
	}

	public Collection<MailbagHistoryModel> findMailbagHistoriesFromWebScreen(String companyCode, String mailBagId,
																				 long mailSequenceNumber) {
		log.debug(MODULE + " : " + "findMailbagHistoriesFromWebScreen" + " Entering");
		return mailOperationsMapper.mailbagHistoryVOsToMailbagHistoryModel(
				mailController.findMailbagHistoriesFromWebScreen(companyCode, mailBagId, mailSequenceNumber));
	}


	/** 
	* @throws SystemException
	* @author a-1936 This method is used to remove the EmptyULDs(ULDs with noMailBags\Despatches)
	*/
	public void unassignEmptyULDs(Collection<ContainerDetailsModel> containerDetailssModel) {
		Collection<ContainerDetailsVO> containerDetailsVOs = mailOperationsMapper
				.containerDetailsModelsToContainerDetailsVO(containerDetailssModel);
		log.debug(MODULE + " : " + "unassignEmptyULDs" + " Entering");
		mailController.unassignEmptyULDs(containerDetailsVOs);
	}

	/**
	* @return Collection<ContainerVO>
	* @throws SystemException
	* @author a-1936 This method is used to return all the containers assignedto a particularFlight
	*/
	public Collection<ContainerModel> findFlightAssignedContainers(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "findFlightAssignedContainers" + " Entering");
		return mailOperationsMapper
				.containerVOsToContainerModel(mailController.findFlightAssignedContainers(operationalFlightVO));
	}

	/**
	* @return Collection<ContainerVO>
	* @throws SystemException
	* @author a-1936 This method is used to return all the containers assignedto a particular destination and Carrier
	*/
	public Collection<ContainerModel> findDestinationAssignedContainers(DestinationFilterModel destinationFilterModel) {
		DestinationFilterVO destinationFilterVO = mailOperationsMapper
				.destinationFilterModelToDestinationFilterVO(destinationFilterModel);
		log.debug(MODULE + " : " + "findDestinationAssignedContainers" + " Entering");
		return mailOperationsMapper
				.containerVOsToContainerModel(mailController.findDestinationAssignedContainers(destinationFilterVO));
	}

	/** 
	* This method is used to reassign the MailBags
	* @throws MailOperationsBusinessException
	*/
	public Collection<ContainerDetailsModel> reassignMailbags(Collection<MailbagModel> mailbagsToReassignModel,
			ContainerModel toContainerModel) throws MailOperationsBusinessException {
		ContainerVO toContainerVO = mailOperationsMapper.containerModelToContainerVO(toContainerModel);
		Collection<MailbagVO> mailbagsToReassign = mailOperationsMapper
				.mailbagModelsToMailbagVO(mailbagsToReassignModel);
		try {
			return mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(
					mailController.reassignMailbags(mailbagsToReassign, toContainerVO));
		} catch (FlightClosedException ex) {
			throw new MailOperationsBusinessException(ex.getErrors());
		} catch (ReassignmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (InvalidFlightSegmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (CapacityBookingProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailBookingException ex) {
			throw new MailOperationsBusinessException(ex);
		}
	}

	/**
	* @return ConsignmentDocumentVO
	* @throws SystemException
	* @author a-1883 This method returns Consignment Details
	*/
	public ConsignmentDocumentModel findConsignmentDocumentDetails(ConsignmentFilterModel consignmentFilterModel) {
		ConsignmentFilterVO consignmentFilterVO = mailOperationsMapper
				.consignmentFilterModelToConsignmentFilterVO(consignmentFilterModel);
		log.debug(MODULE + " : " + "findConsignmentDocumentDetails" + " Entering");
		ConsignmentDocumentVO consignmentDocumentVO =documentController.findConsignmentDocumentDetails(consignmentFilterVO);
		List<MailInConsignmentModel>mailInConsignmentModels = new ArrayList<>();
		Page<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO.getMailInConsignmentVOs();
		ConsignmentDocumentModel consignmentDocumentModel = mailOperationsMapper.consignmentDocumentVOToConsignmentDocumentModel(consignmentDocumentVO);
		if(Objects.nonNull(mailInConsignmentVOs) && mailInConsignmentVOs.size()>0) {
			for (MailInConsignmentVO mailInconsignmnet : mailInConsignmentVOs) {
				mailInConsignmentModels.add(mailOperationsMapper.mailInConsignmentVOToMailInConsignmentModel(mailInconsignmnet));
			}
			Page<MailInConsignmentModel> mailInConsignmentModelPage = new Page(mailInConsignmentModels, mailInConsignmentVOs.getPageNumber(),
					mailInConsignmentVOs.getDefaultPageSize(), mailInConsignmentVOs.getActualPageSize(),
					mailInConsignmentVOs.getStartIndex(), mailInConsignmentVOs.getEndIndex(),
					mailInConsignmentVOs.hasNextPage(), mailInConsignmentVOs.getTotalRecordCount());
			consignmentDocumentModel.setMailInConsignmentVOs(mailInConsignmentModelPage);
		}
		return consignmentDocumentModel;
	}

	/**
	* @throws SystemException
	* @throws MailOperationsBusinessException
	* @author a-1936
	*/
	public void deleteContainers(Collection<ContainerModel> containersModel) throws MailOperationsBusinessException {
		Collection<ContainerVO> containerVOs = mailOperationsMapper.containerModelsToContainerVO(containersModel);
		log.debug(MODULE + " : " + "deleteContainers" + " Entering");
		try {
			mailController.deleteContainers(containerVOs);
		} catch (ContainerAssignmentException e) {
			throw new MailOperationsBusinessException(e);
		}
	}

	/**
	* @author a-1739
	*/
	public void saveDamageDetailsForMailbag(Collection<MailbagModel> mailbagsModel) {
		Collection<MailbagVO> mailbagVOs = mailOperationsMapper.mailbagModelsToMailbagVO(mailbagsModel);
		log.debug(MODULE + " : " + "saveDamageDetailsForMailbag" + " Entering");
		mailController.saveDamageDetailsForMailbag(mailbagVOs);
		log.debug(MODULE + " : " + "saveDamageDetailsForMailbag" + " Exiting");
	}

	/** 
	* @param companyCode
	* @param mailbagId
	* @return
	* @throws SystemException
	* @author A-2037 This method is used to find the Damaged Mailbag Details
	*/
	public Collection<DamagedMailbagModel> findMailbagDamages(String companyCode, String mailbagId) {
		log.debug(MODULE + " : " + "findMailbagDamages" + " Entering");
		return mailOperationsMapper
				.damagedMailbagVOsToDamagedMailbagModel(mailController.findMailbagDamages(companyCode, mailbagId));
	}

	/**
	* @throws SystemException
	* @author a-1936 This method is used tom find the containers
	*/
	public Page<ContainerModel> findContainers(SearchContainerFilterModel searchContainerFilterModel, int pageNumber) {
		SearchContainerFilterVO searchContainerFilterVO = mailOperationsMapper
				.searchContainerFilterModelToSearchContainerFilterVO(searchContainerFilterModel);

		Page<ContainerVO> containerVOs = mailController.findContainers(searchContainerFilterVO, pageNumber);
		if ( Objects.isNull(containerVOs) ) {
			return null;
		}
		List<ContainerModel> containerModels = new ArrayList<>();
		containerModels.addAll(mailOperationsMapper
				.containerVOsToContainerModel(containerVOs));

		return new
				Page(containerModels,containerVOs.getPageNumber(),
				containerVOs.getDefaultPageSize(),containerVOs.getActualPageSize(),
				containerVOs.getStartIndex(),containerVOs.getEndIndex()
				, containerVOs.hasNextPage(),containerVOs.getTotalRecordCount());


	}

	public Collection<EmbargoDetailsVO> checkEmbargoForMail(Collection<ShipmentDetailsVO> shipmentDetailsVos) {
		log.debug(MODULE + " : " + "checkEmbargoForMail" + " Entering");
		return mailController.checkEmbargoForMail(shipmentDetailsVos);
	}

	/** 
	* a-2553
	* @return
	* @throws SystemException
	*/
	public Page<MailbagModel> findCarditMails(CarditEnquiryFilterModel carditEnquiryFilterModel, int pageNumber) throws BusinessException {
		CarditEnquiryFilterVO carditEnquiryFilterVO = mailOperationsMapper
				.carditEnquiryFilterModelToCarditEnquiryFilterVO(carditEnquiryFilterModel);
		log.debug(MODULE + " : " + "findCarditMails" + " Entering");
		Page<MailbagVO>  mailbagVO=mailController.findCarditMails(carditEnquiryFilterVO, pageNumber);
		if ( mailbagVO == null ) {
			return null;
		}
		List<MailbagModel> mailbagModel= new ArrayList<>();
		mailbagModel.addAll(mailOperationsMapper.mailbagVOsToMailbagModel(mailbagVO));
		return new Page(mailbagModel,mailbagVO.getPageNumber(), mailbagVO.getDefaultPageSize(), mailbagVO.getActualPageSize(), mailbagVO.getStartIndex(), mailbagVO.getEndIndex(), mailbagVO.hasNextPage(), mailbagVO.getTotalRecordCount());
	}

	/**
	 *
	 * @param mailArrivalFilterModel
	 * @return
	 */
	public MailArrivalModel findArrivalDetails(MailArrivalFilterModel mailArrivalFilterModel) {
		MailArrivalFilterVO mailArrivalFilterVO = mailOperationsMapper
				.mailArrivalFilterModelToMailArrivalFilterVO(mailArrivalFilterModel);
		log.debug(MODULE + " : " + "findArrivalDetails" + " Entering");
		return mailOperationsMapper
				.mailArrivalVOToMailArrivalModel(mailController.findArrivalDetails(mailArrivalFilterVO));
	}

	/** 
	* For validating inb flt Oct 6, 2006, a-1739*/
	public OperationalFlightModel validateInboundFlight(OperationalFlightModel flightModel) {
		OperationalFlightVO flightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(flightModel);
		return mailOperationsMapper
				.operationalFlightVOToOperationalFlightModel(mailController.validateInboundFlight(flightVO));
	}

	/** 
	* This method is used to saveArrivalDetails
	* @throws SystemException
	* @throws MailOperationsBusinessException
	*/
	public void saveArrivalDetails(MailArrivalModel mailArrivalModel) throws MailOperationsBusinessException {
		MailArrivalVO mailArrivalVO = mailOperationsMapper.mailArrivalModelInboundToMailArrivalVO(mailArrivalModel, measureMapper);
		log.debug(MODULE + " : " + "saveArrivalDetails" + " Entering");
		mailController.saveArrivalDetails(mailArrivalVO);
	}

	/**
	* @author a-1883
	*/
	public boolean isFlightClosedForInboundOperations(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "isFlightClosedForInboundOperations" + " Entering");
		return mailController.isFlightClosedForInboundOperations(operationalFlightVO);
	}

	/**
	* @throws MailOperationsBusinessException
	* @author A-5991
	*/
	public void undoArriveContainer(MailArrivalModel mailArrivalModel) throws MailOperationsBusinessException {
		MailArrivalVO mailArrivalVO = mailOperationsMapper.mailArrivalModelToMailArrivalVO(mailArrivalModel);
		log.debug(MODULE + " : " + "saveUndoArrivalDetails" + " Entering");
		try {
			mailController.undoArriveContainer(mailArrivalVO);
		} catch (MailOperationsBusinessException ex) {
			throw new MailOperationsBusinessException(ex);
		}
	}

	/**
	* @author a-1883
	*/
	public Collection<MailDiscrepancyModel> findMailDiscrepancies(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug("MailEJB" + " : " + "findMailDiscrepancies" + " Entering");
		return mailOperationsMapper
				.mailDiscrepancyVOsToMailDiscrepancyModel(mailController.findMailDiscrepancies(operationalFlightVO));
	}

	/**
	 *
	 * @param conatinerstoAcquitModel
	 */
	public void autoAcquitContainers(Collection<ContainerDetailsModel> conatinerstoAcquitModel) {
		Collection<ContainerDetailsVO> conatinerstoAcquit = mailOperationsMapper
				.containerDetailsModelsToContainerDetailsVO(conatinerstoAcquitModel);
		log.debug(MODULE + " : " + "autoAcquitContainers" + " Entering");
		mailController.autoAcquitContainers(conatinerstoAcquit);
		log.debug(MODULE + " : " + "autoAcquitContainers" + " Exiting");
	}

	/** 
	* This method fetches the latest Container Assignment irrespective of the PORT to which it is assigned. This to know the current assignment of the Container.
	* @param containerNumber
	* @return
	* @throws SystemException
	* @throws MailOperationsBusinessException
	*/
	public ContainerAssignmentModel findLatestContainerAssignment(String containerNumber)
			throws MailOperationsBusinessException {
		log.debug(MODULE + " : " + "findLatestContainerAssignment" + " Entering");
		return mailOperationsMapper.containerAssignmentVOToContainerAssignmentModel(
				mailController.findLatestContainerAssignment(containerNumber));
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.saveChangeFlightDetails Added by 	:	A-6245 Used for 	: Parameters	:	@param mailArrivalVOs Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	void
	*/
	public void saveChangeFlightDetails(Collection<MailArrivalModel> mailArrivalsModel)
			throws MailOperationsBusinessException {
		Collection<MailArrivalVO> mailArrivalVOs = mailOperationsMapper
				.mailArrivalModelsToMailArrivalVO(mailArrivalsModel);
		log.debug(MODULE + " : " + "saveChangeFlightDetails" + " Entering");
		mailController.saveChangeFlightDetails(mailArrivalVOs);
		log.debug(MODULE + " : " + "saveChangeFlightDetails" + " Exiting");
	}

	/** 
	* Send Resdit manually Feb 9, 2007, A-1739*/
	public void sendResdit(CarditEnquiryModel carditEnquiryModel) throws MailOperationsBusinessException {
		CarditEnquiryVO carditEnquiryVO = mailOperationsMapper
				.carditEnquiryModelToCarditEnquiryVO(carditEnquiryModel);
		log.debug(MODULE + " : " + "sendResdit" + " Entering");
		try {
			mailController.sendResdit(carditEnquiryVO);
		} catch (ContainerAssignmentException e) {
			throw new MailOperationsBusinessException(e);
		}
		log.debug(MODULE + " : " + "sendResdit" + " Exiting");
	}

	/**
	* @return Integer
	* @throws SystemException
	* @throws MailOperationsBusinessException
	* @author a-1883
	*/
	public Integer saveConsignmentDocument(ConsignmentDocumentModel consignmentDocumentModel)
			throws BusinessException {
		ConsignmentDocumentVO consignmentDocumentVO = mailOperationsMapper
				.consignmentDocumentModelToConsignmentDocumentVO(consignmentDocumentModel);
		log.debug(MODULE + " : " + "saveConsignmentDocument" + " Entering");
		Integer consignmentSeqNumber = null;
		try {
			consignmentSeqNumber = documentController.saveConsignmentDocument(consignmentDocumentVO);
		} catch (MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException) {
			throw new BusinessException(mailbagAlreadyAcceptedException.getErrors());
		} catch (InvalidMailTagFormatException invalidMailTagFormatException) {
			throw new BusinessException(invalidMailTagFormatException.getErrors());
		} catch (DuplicateDSNException duplicateDSNException) {
			throw new BusinessException(duplicateDSNException.getErrors());
		} catch (DuplicateMailBagsException ex) {
			throw new BusinessException(ex.getErrors());
		}
		log.debug(MODULE + " : " + "saveConsignmentDocument" + " Exiting");
		return consignmentSeqNumber;
	}

	/** 
	* This method deletes Consignment document details and its childs
	* @throws MailOperationsBusinessException
	* @author a-1883
	*/
	public void deleteConsignmentDocumentDetails(ConsignmentDocumentModel consignmentDocumentModel)
			throws MailOperationsBusinessException {
		ConsignmentDocumentVO consignmentDocumentVO = mailOperationsMapper
				.consignmentDocumentModelToConsignmentDocumentVO(consignmentDocumentModel);
		log.debug(MODULE + " : " + "deleteConsignmentDocumentDetails" + " Entering");
		try {
			documentController.deleteConsignmentDocumentDetails(consignmentDocumentVO);
		} catch (MailbagAlreadyAcceptedException e) {
			throw new MailOperationsBusinessException(e);
		}
		log.debug(MODULE + " : " + "deleteConsignmentDocumentDetails" + " Exiting");
	}

	/**
	* @author A-2107
	*/
	public Collection<MailbagModel> findCartIds(ConsignmentFilterModel consignmentFilterModel) {
		ConsignmentFilterVO consignmentFilterVO = mailOperationsMapper
				.consignmentFilterModelToConsignmentFilterVO(consignmentFilterModel);
		log.debug(MODULE + " : " + "findCartIds" + " Entering");
		return mailOperationsMapper.mailbagVOsToMailbagModels(mailController.findCartIds(consignmentFilterVO));
	}

	public TransferManifestModel transferMail(Collection<DespatchDetailsModel> despatchDetailssModel,
			Collection<MailbagModel> mailbagsModel, ContainerModel containerModel, String toPrint)
			throws MailOperationsBusinessException {
		log.debug(MODULE + " : " + "transferMail" + " Entering");
		ContainerVO containerVO = mailOperationsMapper.containerModelToContainerVO(containerModel);
		Collection<MailbagVO> mailbagVOs = mailOperationsMapper.mailbagModelsToMailbagVO(mailbagsModel);
		Collection<DespatchDetailsVO> despatchDetailsVOs = mailOperationsMapper
				.despatchDetailsModelsToDespatchDetailsVO(despatchDetailssModel);
		try {
			return mailOperationsMapper.transferManifestVOToTransferManifestModel(
					mailController.transferMail(despatchDetailsVOs, mailbagVOs, containerVO, toPrint));
		} catch (InvalidFlightSegmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (CapacityBookingProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailBookingException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailOperationsBusinessException ex) {
			throw new MailOperationsBusinessException(ex);
		}
	}

	/**
	* @throws MailOperationsBusinessException
	* @throws DuplicateDSNException
	* @author A-3227 JUN 24, 2009
	*/
	public void saveConsignmentForManifestedDSN(ConsignmentDocumentModel consignmentDocumentModel)
			throws MailOperationsBusinessException {
		ConsignmentDocumentVO consignmentDocumentVO = mailOperationsMapper
				.consignmentDocumentModelToConsignmentDocumentVO(consignmentDocumentModel);
		log.debug(MODULE + " : " + "saveConsignmentForManifestedDSN" + " Entering");
		try {
			documentController.saveConsignmentForManifestedDSN(consignmentDocumentVO);
		} catch (MailbagAlreadyAcceptedException e) {
			throw new MailOperationsBusinessException(e);
		} catch (InvalidMailTagFormatException e) {
			throw new MailOperationsBusinessException(e);
		} catch (DuplicateDSNException e) {
			throw new MailOperationsBusinessException(e);
		} catch (DuplicateMailBagsException e) {
			throw new MailOperationsBusinessException(e);
		}
		log.debug(MODULE + " : " + "saveConsignmentForManifestedDSN" + " Exiting");
	}
	/**
	* Overriding Method	:	@see saveMailUploadDetailsFromMLD(java.util.Collection) Added by 			: A-4803 on 20-Oct-2014 Used for 	:   saving mail details from MLD messages Parameters	:	@param mldMasterVOs Parameters	:	@throws RemoteException Parameters	:	@throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	*/
	public Map<String, Collection<MLDMasterModel>> saveMailUploadDetailsFromMLD(
			Collection<MLDMasterModel> mldMastersModel)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException {
		Collection<MLDMasterVO> mldMasterVOs = mailOperationsMapper.mLDMasterModelsToMLDMasterVO(mldMastersModel);
		log.debug(MODULE + " : " + "saveMailUploadDetailsFromMLD" + " Entering");
		try {
			return mailOperationsMapper
					.mLDMasterVOsToMLDMasterModel(mailUploadController.saveMailUploadDetailsFromMLD(mldMasterVOs));
		} catch (ForceAcceptanceException e) {
			throw new MailOperationsBusinessException(e);
		}
	}
	/**
	* This method does the ULD Acquittal at Non Mechanized port
	* @throws SystemException
	* @author A-3227  RENO K ABRAHAM - 09/09/2009
	*/
	public void initiateULDAcquittance(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "initiateULDAcquittance" + " Entering");
		mailController.initiateULDAcquittance(operationalFlightVO);
		log.debug(MODULE + " : " + "initiateULDAcquittance" + " Exiting");
	}

	/**
	* @throws MailOperationsBusinessException
	* @author A-5526 This method is used to send MLD messages..
	*/
	public void triggerMLDMessages(String companyCode, int recordCount) {
		log.debug(MODULE + " : " + "saveMLDConfiguarions" + " Entering");
		mLDController.triggerMLDMessages(companyCode, recordCount);
	}

	/** 
	*/
	public void closeInboundFlightForMailOperation(String companyCode) {
		log.debug(MODULE + " : " + "closeInboundFlightForMailOperation" + " Entering");
		mailController.closeInboundFlightForMailOperation(companyCode);
		log.debug(MODULE + " : " + "closeInboundFlightForMailOperation" + " Exiting");
	}

	public void closeInboundFlightAfterULDAcquitalForProxy(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "closeInboundFlightForMailOperation" + " Entering");
		mailController.closeInboundFlightAfterULDAcquitalForProxy(operationalFlightVO);
		log.debug(MODULE + " : " + "closeInboundFlightForMailOperation" + " Exiting");
	}

	public void closeInboundFlightAfterULDAcquital(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "closeInboundFlightForMailOperation" + " Entering");
		mailController.closeInboundFlightAfterULDAcquital(operationalFlightVO);
		log.debug(MODULE + " : " + "closeInboundFlightForMailOperation" + " Exiting");
	}

	/** 
	* @author A-1885
	*/
	public void closeFlightForMailOperation(String companyCode, int time, String airportCode) {
		log.debug(MODULE + " : " + "closeFlightForMailOperation" + " Entering");
		mailController.closeFlightForMailOperation(companyCode, time, airportCode);
	}

	/** 
	* @throws SystemException
	* @author A-5166Added for ICRD-36146 on 07-Mar-2013
	*/
	public void initiateArrivalForFlights(ArriveAndImportMailModel arriveAndImportMailModel) {
		ArriveAndImportMailVO arriveAndImportMailVO = mailOperationsMapper
				.arriveAndImportMailModelToArriveAndImportMailVO(arriveAndImportMailModel);
		log.debug(MODULE + " : " + "initiateArrivalForFlights" + " Entering");
		mailController.initiateArrivalForFlights(arriveAndImportMailVO);
		log.debug(MODULE + " : " + "initiateArrivalForFlights" + " Exiting");
	}

	/** 
	*/
	public Page<DespatchDetailsModel> findDSNs(DSNEnquiryFilterModel dSNEnquiryFilterModel, int pageNumber) {
		DSNEnquiryFilterVO dSNEnquiryFilterVO = mailOperationsMapper
				.dSNEnquiryFilterModelToDSNEnquiryFilterVO(dSNEnquiryFilterModel);
		log.debug(MODULE + " : " + "findDSNs" + " Entering");
		return mailOperationsMapper
				.despatchDetailsVOsToDespatchDetailsModel(mailController.findDSNs(dSNEnquiryFilterVO, pageNumber));
	}

	/** 
	* This method is used to saveCarditMessages
	* @throws SystemException
	* @throws InvocationTargetException 
	* @throws IllegalAccessException 
	*/
	public Collection<ErrorVO> saveCarditMessages(EDIInterchangeModel ediInterchangeModel)
			throws BusinessException, IllegalAccessException, InvocationTargetException {
		EDIInterchangeVO ediInterchangeVO = mailOperationsMapper
				.eDIInterchangeModelToEDIInterchangeVO(ediInterchangeModel);
		log.debug(MODULE + " : " + "saveCarditMessages" + " Entering");
		Collection<ErrorVO> errors = new ArrayList<>();
		try {
			errors = mailController.saveCarditMessages(ediInterchangeVO);
		} catch (DuplicateMailBagsException e) {
			throw new MailOperationsBusinessException(e);
		}
		log.debug(MODULE + " : " + "saveCarditMessages" + " Exiting");
		return errors;
	}

	/** 
	* TODO Purpose Oct 9, 2006, a-1739
	* @throws MailOperationsBusinessException
	*/
	public Collection<MailbagModel> validateScannedMailbagDetails(
			Collection<MailbagModel> mailbagsModel)
			throws MailOperationsBusinessException {
		Collection<MailbagVO> mailbags = mailOperationsMapper
				.mailbagModelsToMailbagVO(mailbagsModel);
		log.debug(MODULE + " : " + "validateScannedMailbagDetails" + " Entering");
		try {
			return mailOperationsMapper
					.mailbagVOsToMailbagModels(mailController.validateScannedMailbagDetails(mailbags));
		} catch (InvalidMailTagFormatException e) {
			throw new MailOperationsBusinessException(e);
		}
	}

	/** 
	* This method invokes the resdit reciever procedure
	* @param companyCode
	* @throws SystemException
	*/
	public void invokeResditReceiver(String companyCode) {
		log.debug(MODULE + " : " + "invokeResditReceiver" + " Entering");
		mailController.invokeResditReceiver(companyCode);
		log.debug(MODULE + " : " + "invokeResditReceiver" + " Exiting");
	}

	/** 
	* Starts Resdit processing
	* @param companyCode
	* @return Collection<ResditEventVO>
	* @throws SystemException
	*/
	public Collection<ResditEventModel> checkForResditEvents(String companyCode) {
		log.debug(MODULE + " : " + "invokeResditReceiver" + " Entering");
		return mailOperationsMapper
				.resditEventVOsToResditEventModel(resditController.checkForResditEvents(companyCode));
	}

	/**
	* @author A-1936 This method is used to find the MailBags Accepted to aParticularFlight and Flag the Uplifted Resdits for the Same
	*/
	public void flagUpliftedResditForMailbags(Collection<OperationalFlightModel> operationalFlightsModel) {
		Collection<OperationalFlightVO> operationalFlightVOs = mailOperationsMapper
				.operationalFlightModelsToOperationalFlightVO(operationalFlightsModel);
		log.debug(MODULE + " : " + "flagUpliftedResditForMailbags" + " Entering");
		mailController.flagUpliftedResditForMailbags(operationalFlightVOs);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#flagTransportCompletedResditForMailbags(java.util.Collection) Added by 			: Used for 	: Parameters	:	@param operationalFlightVOs Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public void flagTransportCompletedResditForMailbags(Collection<OperationalFlightModel> operationalFlightsModel) {
		Collection<OperationalFlightVO> operationalFlightVOs = mailOperationsMapper
				.operationalFlightModelsToOperationalFlightVO(operationalFlightsModel);
		log.debug(MODULE + " : " + "flagTransportCompletedResditForMailbags" + " Entering");
		mailController.flagTransportCompletedResditForMailbags(operationalFlightVOs);
	}

	public Map<String, ContainerAssignmentModel> findContainerAssignments(Collection<ContainerModel> containersModel) {
		Collection<ContainerVO> containers = mailOperationsMapper.containerModelsToContainerVO(containersModel);
		return mailOperationsMapper
				.containerAssignmentVOsToContainerAssignmentModel(mailController.findContainerAssignments(containers));
	}

	/**
	* @throws SystemException
	* @author A-2572
	*/
	public void updateResditSendStatus(ResditMessageVO resditMessageVO) {
		log.debug(MODULE + " : " + "updateResditSendStatus" + " Entering");
		resditController.updateResditSendStatus(resditMessageVO);
		log.debug(MODULE + " : " + "updateResditSendStatus" + " Exiting");
	}

	/** 
	* @param controllerBean
	* @return
	* @throws SystemException
	*/
	public Object getController(String controllerBean) {
		Object controllerobj = null;
		try {
			controllerobj = mailUploadController;
		} finally {
		}
		return controllerobj;
	}


	public Collection<ErrorVO> handleMRDMessage(MailMRDModel messageModel)
			throws MailHHTBusniessException, MailOperationsBusinessException {
		MailMRDVO messageVO = mailOperationsMapper.mailMRDModelToMailMRDVO(messageModel);
		log.debug(MODULE + " : " + "handleMRDMessage" + " Entering");
		String controllerBeanString = "-mailUploadcontroller";
		try {
			return mailUploadController.handleMRDMessage(messageVO);
		} catch (ForceAcceptanceException e) {
			throw new MailOperationsBusinessException(e);
		}
	}

	/**
	*/
	public void buildResdit(Collection<ResditEventModel> resditEventsModel) {
		Collection<ResditEventVO> resditEvents = mailOperationsMapper
				.resditEventModelsToResditEventVO(resditEventsModel);
		log.debug(MODULE + " : " + "buildResdit" + " Entering");
		resditController.buildResdit(resditEvents);
		log.debug(MODULE + " : " + "buildResdit" + " Exiting");
	}

	public void resolveTransaction(String companyCode, String txnId, String remarks) {
		log.debug("AdminMonitoringServicesEJB" + " : " + "resolveTransaction" + " Entering");
		mailController.resolveTransaction(companyCode, txnId, remarks);
	}

	/** 
	* @param documentFilterVO
	* @return DocumentValidationVO
	* @throws SystemException
	* @author a-1883
	* @throws MailOperationsBusinessException
	*/
	public DocumentValidationVO validateDocumentInStock(DocumentFilterVO documentFilterVO) {
		log.debug(MODULE + " : " + "validateDocumentInStock" + " Entering");
		return mailController.validateDocumentInStock(documentFilterVO);
	}

	/**
	* @throws SystemException
	* @author a-1883
	*/
	public void attachAWBDetails(AWBDetailModel aWBDetailModel, ContainerDetailsModel containerDetailsModel)
			throws PersistenceException {
		ContainerDetailsVO containerDetailsVO = mailOperationsMapper
				.containerDetailsModelToContainerDetailsVO(containerDetailsModel);
		AWBDetailVO aWBDetailVO = mailOperationsMapper.aWBDetailModelToAWBDetailVO(aWBDetailModel);
		log.debug(MODULE + " : " + "attachAWBDetails" + " Entering");
		mailController.attachAWBDetails(aWBDetailVO, containerDetailsVO);
		log.debug(MODULE + " : " + "attachAWBDetails" + " Exiting");
	}

	/** 
	* This method deletes document from stock
	* @param documentFilterVO
	* @throws SystemException
	* @author a-1883
	*/
	public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO)
			throws MailOperationsBusinessException, SystemException {
		log.debug(MODULE + " : " + "deleteDocumentFromStock" + " Entering");
		try {
			mailController.deleteDocumentFromStock(documentFilterVO);
		} catch (StockcontrolDefaultsProxyException e) {
			throw new MailOperationsBusinessException(e);
		}
		log.debug(MODULE + " : " + "deleteDocumentFromStock" + " Exiting");
	}

	/** 
	* This method used to attach AWB details to all DSNs . if they are not attached to any AWB, creates new a Shipment and attach AWB details to all.
	* @throws SystemException
	* @throws MailOperationsBusinessException
	*/
	public Collection<ContainerDetailsModel> autoAttachAWBDetails(
			Collection<ContainerDetailsModel> containerDetailssModel, OperationalFlightModel operationalFlightModel)
			throws MailOperationsBusinessException {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		Collection<ContainerDetailsVO> containerDetailsVOs = mailOperationsMapper
				.containerDetailsModelsToContainerDetailsVO(containerDetailssModel);
		log.debug("MailEJB" + " : " + "autoAttachAWBDetails" + " Entering");
		Collection<ContainerDetailsVO> containerVOs = null;
		containerVOs = mailController.autoAttachAWBDetails(containerDetailsVOs, operationalFlightVO);
		log.debug("MailEJB" + " : " + "autoAttachAWBDetails" + " Exiting");
		return mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(containerVOs);
	}

	/**
	* @author
	*/
	public void updateActualWeightForMailULD(ContainerModel containerModel) {
		ContainerVO containerVO = mailOperationsMapper.containerModelToContainerVO(containerModel);
		log.debug(MODULE + " : " + "updateActualWeightForMailULD" + " Entering");
		mailController.updateActualWeightForMailULD(containerVO);
		log.debug(MODULE + " : " + "updateActualWeightForMailULD" + " Exiting");
	}

	/** 
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @author A-1885
	*/
	public List<MailUploadModel> performMailOperationForGHA(Collection<MailWebserviceModel> webServicesVosModel,
			String scanningPort) throws MailHHTBusniessException, MailOperationsBusinessException, PersistenceException {
		Collection<MailWebserviceVO> webServicesVos = mailOperationsMapper
				.mailWebserviceModelsToMailWebserviceVO(webServicesVosModel);
		log.debug(MODULE + " : " + "performMailOperationForGHA" + " Entering");
		return (List<MailUploadModel>) mailOperationsMapper.mailUploadVOsToMailUploadModel(
				mailUploadController.performMailOperationForGHA(webServicesVos, scanningPort));
	}

	/**
	* @author a-1936This method is used to find out the Mail Bags and Despatches  in the Containers for the Manifest..
	*/
	public Collection<ContainerDetailsModel> findMailbagsInContainerForImportManifest(
			Collection<ContainerDetailsModel> containersModel) {
		Collection<ContainerDetailsVO> containers = mailOperationsMapper
				.containerDetailsModelsToContainerDetailsVO(containersModel);
		log.debug("MailTracking Defaults Services EJB" + " : " + "findContainersInFlight" + " Entering");
		return mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(
				mailController.findMailbagsInContainerForImportManifest(containers));
	}

	/**
	* @throws SystemException
	* @author a-6371 This method is used to list the mail ON HAND LIST details
	*/
	public Page<MailOnHandDetailsModel> findMailOnHandDetails(SearchContainerFilterModel searchContainerFilterModel,
			int pageNumber) {
		SearchContainerFilterVO searchContainerFilterVO = mailOperationsMapper
				.searchContainerFilterModelToSearchContainerFilterVO(searchContainerFilterModel);
		log.debug(MODULE + " : " + "findMailOnHandDetails" + " Entering");
		log.debug("welcome" + " : " + "welcomefindMailOnHandDetails" + " Entering");
		return mailOperationsMapper.mailOnHandDetailsVOsToMailOnHandDetailsModel(
				mailController.findMailOnHandDetails(searchContainerFilterVO, pageNumber));
	}

	/**
	 *
	 * @param messageModel
	 * @return
	 * @throws MailHHTBusniessException
	 * @throws MailOperationsBusinessException
	 */
	public Collection<ErrorVO> handleMRDHO22Message(MailMRDModel messageModel)
			throws MailHHTBusniessException, MailOperationsBusinessException {
		MailMRDVO messageVO = mailOperationsMapper.mailMRDModelToMailMRDVO(messageModel);
		log.debug(MODULE + " : " + "handleMRDHO22Message" + " Entering");
		String controllerBeanString = "-mailUploadcontroller";

		Collection<ErrorVO> allErrorVOs = new ArrayList<ErrorVO>();

		try {
			allErrorVOs= mailUploadController.handleMRDHO22Message(messageVO);
			if(allErrorVOs.size()>0) {
				for (ErrorVO allErrorVO : allErrorVOs) {
					if("Processed successfully".equalsIgnoreCase(allErrorVO.getErrorCode())){
						Collection<ErrorVO> allErrorVOsEmpty = new ArrayList<ErrorVO>();
						return allErrorVOsEmpty;
					}
					throw new MailOperationsBusinessException(allErrorVO.getErrorCode(),allErrorVO.getErrorData());
				}
			}
		} catch (ForceAcceptanceException e) {
			throw new MailOperationsBusinessException(e);
		}
		return allErrorVOs;
	}

	public void saveAllValidMailBags(Collection<ScannedMailDetailsModel> validScannedMailsModel)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException {
		Collection<ScannedMailDetailsVO> validScannedMailVOs = mailOperationsMapper
				.scannedMailDetailsModelsToScannedMailDetailsVO(validScannedMailsModel);
		log.debug(MODULE + " : " + "clearDSNAcceptanceTemp" + " Entering");
		Transaction txn = null;
		TransactionProvider tm = PersistenceController.__getTransactionProvider();
		txn = tm.getNewTransaction(false);
		txn.commit();
		try {
			mailUploadController.saveAllValidMailBags(validScannedMailVOs);
		} catch (ForceAcceptanceException e) {
			throw new MailOperationsBusinessException(e);
		} catch (RemoteException re) {
			throw new SystemException(re.getMessage());
		}
		log.debug(MODULE + " : " + "clearDSNAcceptanceTemp" + " Exiting");
	}

	public Collection<MailUploadModel> createMailScanVOSForErrorStamping(
			Collection<MailWebserviceModel> mailWebservicesModel, String scannedPort, StringBuilder errorString,
			String errorFromMapping) {
		Collection<MailWebserviceVO> mailWebserviceVOs = mailOperationsMapper
				.mailWebserviceModelsToMailWebserviceVO(mailWebservicesModel);
		log.debug(MODULE + " : " + "createMailScanVOS" + " Entering");
		return mailOperationsMapper.mailUploadVOsToMailUploadModel(mailUploadController
				.createMailScanVOSForErrorStamping(mailWebserviceVOs, scannedPort, errorString, errorFromMapping));
	}

	/** 
	* TODO Purpose Jan 30, 2007, A-1739*/
	public MailManifestModel findMailAWBDetails(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "findMailAWBDetails" + " Entering");
		return mailOperationsMapper
				.mailManifestVOToMailManifestModel(mailController.findMailAWBDetails(operationalFlightVO));
	}

	/** 
	* Called from operations module to close Export side flight for mail on closing Cargo operations
	* @throws MailOperationsBusinessException
	* @throws CloseFlightException
	* @throws ULDDefaultsProxyException
	*/
	public void closeMailExportFlight(OperationalFlightModel operationalFlightModel)
			throws MailOperationsBusinessException {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "closeMailExportFlight" + " Entering");
		try {
			mailController.closeMailExportFlight(operationalFlightVO);
		} catch (ULDDefaultsProxyException e) {
			throw new MailOperationsBusinessException(e);
		} catch (CloseFlightException e) {
			throw new MailOperationsBusinessException(e);
		}
		log.debug(MODULE + " : " + "closeMailExportFlight" + " Exiting");
	}

	/** 
	* Called from operations module to close import side flight for mail on closing Cargo operations
	* @throws CloseFlightException
	* @throws ULDDefaultsProxyException
	* @throws MailOperationsBusinessException
	*/
	public void closeMailImportFlight(OperationalFlightModel operationalFlightModel)
			throws MailOperationsBusinessException {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "closeMailImportFlight" + " Entering");
		try {
			mailController.closeMailImportFlight(operationalFlightVO);
		} catch (ULDDefaultsProxyException e) {
			throw new MailOperationsBusinessException(e);
		} catch (CloseFlightException e) {
			throw new MailOperationsBusinessException(e);
		}
		log.debug(MODULE + " : " + "closeMailImportFlight" + " Exiting");
	}

	/** 
	* Called from shared module to extract data coming in excel to the table SHRGENEXTTAB
	* @param fileUploadFilterVO
	* @return
	* @throws SystemException
	*/
	public String processMailOperationFromFile(FileUploadFilterVO fileUploadFilterVO) {
		log.debug(MODULE + " : " + "processMailOperationFromFile" + " Entering");
		return mailUploadController.processMailOperationFromFile(fileUploadFilterVO);
	}

	/** 
	* fetchDataForOfflineUpload
	* @param companyCode
	* @param fileType
	* @return
	* @throws SystemException
	*/
	public Collection<MailUploadModel> fetchDataForOfflineUpload(String companyCode, String fileType) {
		log.debug(MODULE + " : " + "fetchDataForOfflineUpload" + " Entering");
		return mailOperationsMapper
				.mailUploadVOsToMailUploadModel(mailUploadController.fetchDataForOfflineUpload(companyCode, fileType));
	}

	/** 
	* Added as part of CRQ ICRD-204806 removeDataFromTempTable
	* @param fileUploadFilterVO
	* @throws SystemException
	*/
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO) {
		log.debug(MODULE + " : " + "removeDataFromTempTable" + " Entering");
		mailUploadController.removeDataFromTempTable(fileUploadFilterVO);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findOneTimeDescription(java.lang.String) Added by 			: A-6991 on 13-Jul-2017 Used for 	:         ICRD-208718 Parameters	:	@param companyCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode, String oneTimeCode) {
		log.debug(MODULE + " : " + "findOneTimeDescription" + " Entering");
		return mailController.findOneTimeDescription(companyCode, oneTimeCode);
	}

	/** 
	* Method		:	MailTrackingDefaultsBI.validateULDsForOperation Added by 	:	A-7794 on 25-Sept-2017 Used for 	:   ICRD-223303
	* @param flightDetailsVo
	* @return
	* @throws SystemException
	* @throws ULDDefaultsProxyException
	*/
	public void validateULDsForOperation(FlightDetailsVO flightDetailsVo) {
		log.debug(MODULE + " : " + "validateULDsForOperation" + " Entering");
		mailController.validateULDsForOperation(flightDetailsVo);
	}

	/**
	* @author a-7794 This method is used to fetch Audit details
	*/
	public Collection<AuditDetailsVO> findCONAuditDetails(MailAuditFilterModel mailAuditFilterModel) {
		MailAuditFilterVO mailAuditFilterVO = mailOperationsMapper
				.mailAuditFilterModelToMailAuditFilterVO(mailAuditFilterModel);
		log.debug(MODULE + " : " + "findCONAuditDetails" + " Entering");
		return mailController.findCONAuditDetails(mailAuditFilterVO);
	}

	/** 
	* Find mail master details for mailbag Method		:	MailTrackingDefaultsServicesEJB.findMailDetailsForMailTag Added by 	:	a-6245 on 07-Jun-2017 Used for 	: Parameters	:	@param companyCode Parameters	:	@param mailId Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	MailInConsignmentVO
	*/
	public MailbagModel findMailDetailsForMailTag(String companyCode, String mailId) {
		log.debug(MODULE + " : " + "findMailDetailsForMailTag" + " Entering");
		return mailOperationsMapper
				.mailbagVOToMailbagModel(mailController.findMailDetailsForMailTag(companyCode, mailId));
	}

	/** 
	* Find mail id and other master from master Method		:	MailTrackingDefaultsServicesEJB.findMailbagIdForMailTag Added by 	:	a-6245 on 22-Jun-2017 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	MailbagVO
	*/
	public MailbagModel findMailbagIdForMailTag(MailbagModel mailbagModel) {
		MailbagVO mailbagVO = mailOperationsMapper.mailbagModelToMailbagVO(mailbagModel);
		log.debug(MODULE + " : " + "findMailbagIdForMailTag" + " Entering");
		return mailOperationsMapper.mailbagVOToMailbagModel(mailController.findMailbagIdForMailTag(mailbagVO));
	}

	/** 
	*/
	public Collection<MailBagAuditHistoryModel> findMailAuditHistoryDetails(
			MailAuditHistoryFilterModel mailAuditHistoryFilterModel) {
		MailAuditHistoryFilterVO mailAuditHistoryFilterVO = mailOperationsMapper
				.mailAuditHistoryFilterModelToMailAuditHistoryFilterVO(mailAuditHistoryFilterModel);
		return mailOperationsMapper.mailBagAuditHistoryVOsToMailBagAuditHistoryModel(
				mailController.findMailAuditHistoryDetails(mailAuditHistoryFilterVO));
	}

	/** 
	*/
	public Collection<MailbagHistoryModel> findMailStatusDetails(MailbagEnquiryFilterModel mailbagEnquiryFilterModel) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = mailOperationsMapper
				.mailbagEnquiryFilterModelToMailbagEnquiryFilterVO(mailbagEnquiryFilterModel);
		return mailOperationsMapper
				.mailbagHistoryVOsToMailbagHistoryModel(mailController.findMailStatusDetails(mailbagEnquiryFilterVO));
	}

	/** 
	*/
	public HashMap<String, String> findAuditTransactionCodes(Collection<String> entities, boolean b,
			String companyCode) {
		return mailController.findAuditTransactionCodes(entities, b, companyCode);
	}

	public HashMap<String, Collection<FlightValidationVO>> validateFlightsForAirport(
			Collection<FlightFilterVO> flightFilterVOs) {
		return mailController.validateFlightsForAirport(flightFilterVOs);
	}

	public void closeFlight(OperationalFlightModel fltModel) {
		OperationalFlightVO fltVO = mailOperationsMapper.operationalFlightModelToOperationalFlightVO(fltModel);
		log.debug(MODULE + " : " + "cargoFlightStatus" + " Entering");
		try {
			mailController.closeFlight(fltVO);
		} catch (ULDDefaultsProxyException e) {
			e.printStackTrace();
		} catch (CloseFlightException e) {
			e.printStackTrace();
		}
	}

	/**
	* @author A-5526 This method is used to find the MailBags Accepted to aParticularFlight and Flag MLD-UPL for the Same
	*/
	public void flagMLDForUpliftedMailbags(Collection<OperationalFlightModel> operationalFlightsModel) {
		Collection<OperationalFlightVO> operationalFlightVOs = mailOperationsMapper
				.operationalFlightModelsToOperationalFlightVO(operationalFlightsModel);
		log.debug(MODULE + " : " + "flagMLDForUpliftedMailbags" + " Entering");
		mLDController.flagMLDForUpliftedMailbagsForATDCapture(operationalFlightVOs);
	}

	/**
	* @author A-7871for ICRD-257316
	*/
	public int findMailbagcountInContainer(ContainerModel containerModel) throws PersistenceException {
		ContainerVO containerVO = mailOperationsMapper.containerModelToContainerVO(containerModel);
		log.debug(MODULE + " : " + "findMailbagcountInContainer" + " Entering");
		return mailController.findMailbagcountInContainer(containerVO);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#performMailAWBTransactions(com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO, java.lang.String) Added by 			: a-7779 on 31-Aug-2017 Used for 	: Parameters	:	@param mailFlightSummaryVO Parameters	:	@param eventCode Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public void performMailAWBTransactions(MailFlightSummaryModel mailFlightSummaryModel, String eventCode) {
		MailFlightSummaryVO mailFlightSummaryVO = mailOperationsMapper
				.mailFlightSummaryModelToMailFlightSummaryVO(mailFlightSummaryModel);
		log.debug(MODULE + " : " + "performMailAWBTransactions" + " Entering");
		//TODO: Neo to correct below code
//		((MailTrackingDefaultsBI) SpringAdapter.getInstance().getBean(MAIL_OPERATION_SERVICES))
//				.performMailAWBTransactions(mailFlightSummaryVO, eventCode);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#releasingMailsForULDAcquittance(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO, com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO) Added by 			: A-5219 on 18-Aug-2018 Used for 	: Parameters	:	@param mailArrivalVO Parameters	:	@param operationalFlightVO Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public void releasingMailsForULDAcquittance(MailArrivalModel mailArrivalModel,
			OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		MailArrivalVO mailArrivalVO = mailOperationsMapper.mailArrivalModelToMailArrivalVO(mailArrivalModel);
		try {
			mailController.releasingMailsForULDAcquittance(mailArrivalVO, operationalFlightVO);
		} catch (InvalidFlightSegmentException
				| CapacityBookingProxyException
				| MailBookingException
				| ContainerAssignmentException
				| DuplicateMailBagsException
				| MailbagIncorrectlyDeliveredException
				| FlightClosedException
				| ULDDefaultsProxyException
				| MailOperationsBusinessException e) {

			e.getMessage();
		}
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.findNextDocumentNumber Added by 	:	U-1267 on 09-Nov-2017 Used for 	:	ICRD-211205 Parameters	:	@param documnetFilterVO Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Parameters	:	@throws MailTrackingBusinessException Return type	: 	DocumentValidationVO
	*/
	public DocumentValidationVO findNextDocumentNumber(DocumentFilterVO documnetFilterVO)
			throws MailOperationsBusinessException {
		log.debug(MODULE + " : " + "findNextDocumentNumber" + " Entering");
		try {
			return mailController.findNextDocumentNumber(documnetFilterVO);
		} catch (StockcontrolDefaultsProxyException e) {
			throw new MailOperationsBusinessException(e);
		}
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.detachAWBDetails Added by 	:	U-1267 on 09-Nov-2017 Used for 	:	ICRD-211205 Parameters	:	@param containerDetailsVO Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	void
	*/
	public void detachAWBDetails(ContainerDetailsModel containerDetailsModel) {
		ContainerDetailsVO containerDetailsVO = mailOperationsMapper
				.containerDetailsModelToContainerDetailsVO(containerDetailsModel);
		log.debug(MODULE + " : " + "detachAWBDetails" + " Entering");
		mailController.detachAWBDetails(containerDetailsVO);
	}

	public TransferManifestModel transferContainersAtExport(Collection<ContainerModel> containersModel,
			OperationalFlightModel operationalFlightModel, String printFlag) throws MailOperationsBusinessException {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		Collection<ContainerVO> containerVOs = mailOperationsMapper.containerModelsToContainerVO(containersModel);
		log.debug(MODULE + " : " + "transferContainers" + " Entering");

		try {
			return mailOperationsMapper.transferManifestVOToTransferManifestModel(
					mailController.transferContainersAtExport(containerVOs, operationalFlightVO, printFlag));
		} catch (ContainerAssignmentException e) {
			throw new MailOperationsBusinessException(e);
		} catch (InvalidFlightSegmentException e) {
			throw new MailOperationsBusinessException(e);
		} catch (ULDDefaultsProxyException e) {
			throw new MailOperationsBusinessException(e);
		} catch (CapacityBookingProxyException e) {
			throw new MailOperationsBusinessException(e);
		} catch (MailBookingException e) {
			throw new MailOperationsBusinessException(e);
		} catch (FlightClosedException e) {
			throw new MailOperationsBusinessException(e);
		} catch (MailDefaultStorageUnitException e) {
			throw new MailOperationsBusinessException(e);
		}

	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.transferContainersAtExport Added by 	:	A-7371 on 05-Jan-2018 Used for 	:	ICRD-133987
	* @throws MailOperationsBusinessException
	* @throws InvalidFlightSegmentException
	*/
	public TransferManifestModel transferMailAtExport(Collection<MailbagModel> mailbagsModel,
			ContainerModel containerModel, String toPrint) throws MailOperationsBusinessException {
		ContainerVO containerVO = mailOperationsMapper.containerModelToContainerVO(containerModel);
		Collection<MailbagVO> mailbagVOs = mailOperationsMapper.mailbagModelsToMailbagVO(mailbagsModel);
		log.debug("MailTracking Defaults Services EJB" + " : " + "transferMail" + " Entering");
		try {
			return mailOperationsMapper.transferManifestVOToTransferManifestModel(
					mailController.transferMailAtExport(mailbagVOs, containerVO, toPrint));
		} catch (InvalidFlightSegmentException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (CapacityBookingProxyException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailBookingException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (MailOperationsBusinessException ex) {
			throw new MailOperationsBusinessException(ex);
		} catch (FlightClosedException ex) {
			throw new MailOperationsBusinessException(ex);
		}
	}

	/**
	* @throws SystemException
	* @author A-7540
	*/
	public void generateResditPublishReport(String companyCode, String paCode, int days)  {
		log.debug(MODULE + " : " + "generateResditPublishReport" + " Entering");
		try {
			mailController.generateResditPublishReport(companyCode, paCode, days);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	/** 
	* a-8061
	* @return
	* @throws SystemException
	*/
	public String[] findGrandTotals(CarditEnquiryFilterModel carditEnquiryFilterModel) {
		CarditEnquiryFilterVO carditEnquiryFilterVO = mailOperationsMapper
				.carditEnquiryFilterModelToCarditEnquiryFilterVO(carditEnquiryFilterModel);
		log.debug(MODULE + " : " + "findGrandTotals" + " Entering");
		return mailController.findGrandTotals(carditEnquiryFilterVO);
	}

	public void updateGHTformailbags(Collection<OperationalFlightModel> operationalFlightsModel) {
		Collection<OperationalFlightVO> operationalFlightVOs = mailOperationsMapper
				.operationalFlightModelsToOperationalFlightVO(operationalFlightsModel);
		log.debug(MODULE + " : " + "updateGHTformailbags" + " Entering");
		try {
			mailController.updateGHTformailbags(operationalFlightVOs);
		} catch (FinderException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
	/**
	* @author A-7540
	*/
	public ScannedMailDetailsModel doLATValidation(Collection<MailbagModel> newMailbgsModel, boolean isScanned)
			throws MailHHTBusniessException {
		Collection<MailbagVO> newMailbgVOs = mailOperationsMapper.mailbagModelsToMailbagVO(newMailbgsModel);
		log.debug(MODULE + " : " + "doLATValidation" + " Entering");
		return mailOperationsMapper
				.scannedMailDetailsVOToScannedMailDetailsModel(mailController.doLATValidation(newMailbgVOs, isScanned));
	}

	/**
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws MailOperationsBusinessException
	* @throws PersistenceException
	* @author A-8236
	*/
	public ScannedMailDetailsModel saveMailUploadDetailsForAndroid(MailUploadModel mailBagModel, String scanningPort)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException,
			PersistenceException {
		MailUploadVO mailBagVO = mailOperationsMapper.mailUploadModelToMailUploadVO(mailBagModel);

		return mailOperationsMapper.scannedMailDetailsVOToScannedMailDetailsModel(
				mailUploadController.saveMailUploadDetailsForAndroid(mailBagVO, scanningPort));

	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#performMailAWBTransactions(com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO, java.lang.String) Added by 			: a-7871 on 13-Jul-2018 Used for 	: ICRD-227884 Parameters	:	@param mailUploadVO
	* @throws SystemException
	*/
	public ScannedMailDetailsModel validateMailBagDetails(MailUploadModel mailUploadModel)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException,ForceAcceptanceException {
		MailUploadVO mailUploadVO = mailOperationsMapper.mailUploadModelToMailUploadVO(mailUploadModel);
		log.debug(MODULE + " : " + "validateMailBagDetails" + " Entering");log.debug(MODULE + " : " + "validateMailBagDetails" + " Entering");
		ScannedMailDetailsVO scannedMailDetailsVO = mailUploadController.validateMailBagDetails(mailUploadVO);
		return mailOperationsMapper.scannedMailDetailsVOToScannedMailDetailsModel(
				scannedMailDetailsVO);
	}

	public Page<MailAcceptanceModel> findOutboundFlightsDetails(OperationalFlightModel operationalFlightModel,
			int pageNumber) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		Page<MailAcceptanceVO> mailAcceptanceVOS = mailController.findOutboundFlightsDetails(operationalFlightVO, pageNumber);
		List<MailAcceptanceModel> mailAcceptanceModels = new ArrayList<>();
		if(Objects.nonNull(mailAcceptanceVOS) && mailAcceptanceVOS.size()>0) {
			for (MailAcceptanceVO mailAcceptanceVO : mailAcceptanceVOS) {
				mailAcceptanceModels.add(mailOperationsMapper
						.mailAcceptanceVOToMailAcceptanceModel(mailAcceptanceVO));
			}
			return new
					Page(mailAcceptanceModels, mailAcceptanceVOS.getPageNumber(),
					mailAcceptanceVOS.getDefaultPageSize(), mailAcceptanceVOS.getActualPageSize(),
					mailAcceptanceVOS.getStartIndex(), mailAcceptanceVOS.getEndIndex()
					, mailAcceptanceVOS.hasNextPage(), mailAcceptanceVOS.getTotalRecordCount());
		}
		return new Page<>();
	}

	public Page<ContainerDetailsModel> getAcceptedContainers(OperationalFlightModel operationalFlightModel,
			int pageNumber) {
		log.debug(MODULE + " : " + "findFlightDetails" + " Entering");
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		Page<ContainerDetailsVO> containerDetailsVOS = mailController.getContainersinFlight(operationalFlightVO, pageNumber);
		List<ContainerDetailsModel> containerDetailsModels = new ArrayList<>();
		if(Objects.nonNull(containerDetailsVOS) && containerDetailsVOS.size()>0) {
			containerDetailsModels.addAll(mailOperationsMapper
					.containerDetailsVOsToContainerDetailsModel(containerDetailsVOS));
			return new
					Page(containerDetailsModels, containerDetailsVOS.getPageNumber(),
					containerDetailsVOS.getDefaultPageSize(), containerDetailsVOS.getActualPageSize(),
					containerDetailsVOS.getStartIndex(), containerDetailsVOS.getEndIndex()
					, containerDetailsVOS.hasNextPage(), containerDetailsVOS.getTotalRecordCount());
		}
		return new Page<>();
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.listFlightDetails Added by 	:	A-8164 on 05-Nov-2018 Used for 	: Parameters	:	@param mailArrivalVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Parameters	:	@throws PersistenceException Return type	: 	Collection<MailArrivalVO>
	*/
	public Page<MailArrivalModel> listFlightDetails(MailArrivalModel mailArrivalModel) throws PersistenceException, MailOperationsBusinessException {
		MailArrivalVO mailArrivalVO = mailOperationsMapper.mailArrivalModelToMailArrivalVO(mailArrivalModel);
		return mailOperationsComponent.listFlightDetails(mailArrivalVO);
	}

	public Page<MailbagModel> getMailbagsinContainer(ContainerDetailsModel containerModel, int pageNumber) {
		ContainerDetailsVO containerVO = mailOperationsMapper
				.containerDetailsModelToContainerDetailsVO(containerModel);
		Page<MailbagVO> mailbagVOS = mailController.getMailbagsinContainer(containerVO, pageNumber);
		List<MailbagModel> mailbagModels = new ArrayList<>();
		if(Objects.nonNull(mailbagVOS) && mailbagVOS.size()>0){
			mailbagModels.addAll(mailOperationsMapper.mailbagVOsToMailbagModel(mailbagVOS));
			return new Page(mailbagModels,mailbagVOS.getPageNumber(), mailbagVOS.getDefaultPageSize(),
					mailbagVOS.getActualPageSize(), mailbagVOS.getStartIndex(), mailbagVOS.getEndIndex(),
					mailbagVOS.hasNextPage(), mailbagVOS.getTotalRecordCount());
		}
		return new Page<>();

	}

	public Page<DSNModel> getMailbagsinContainerdsnview(ContainerDetailsModel containerModel, int pageNumber) {
		ContainerDetailsVO containerVO = mailOperationsMapper
				.containerDetailsModelToContainerDetailsVO(containerModel);
		Page<DSNVO> dsnvos =  mailController.getMailbagsinContainerdsnview(containerVO, pageNumber);
		List<DSNModel> dsnModels =  new ArrayList<>();
		if(Objects.nonNull(dsnvos) && dsnvos.size()>0){
			dsnModels.addAll(mailOperationsMapper
					.dSNVOsToDSNModel(dsnvos));
			return new Page(dsnModels,dsnvos.getPageNumber(), dsnvos.getDefaultPageSize(),
					dsnvos.getActualPageSize(), dsnvos.getStartIndex(), dsnvos.getEndIndex(),
					dsnvos.hasNextPage(), dsnvos.getTotalRecordCount());
		}
		return new Page<>();
	}

	public MailbagModel findCarditSummaryView(CarditEnquiryFilterModel carditEnquiryFilterModel) {
		CarditEnquiryFilterVO carditEnquiryFilterVO = mailOperationsMapper
				.carditEnquiryFilterModelToCarditEnquiryFilterVO(carditEnquiryFilterModel);
		log.debug(MODULE + " : " + "findFlightDetails" + " Entering");
		return mailOperationsMapper
				.mailbagVOToMailbagModel(mailController.findCarditSummaryView(carditEnquiryFilterVO));
	}

	public Page<MailbagModel> findGroupedCarditMails(CarditEnquiryFilterModel carditEnquiryFilterModel,
													 int pageNumber) {
		CarditEnquiryFilterVO carditEnquiryFilterVO = mailOperationsMapper
				.carditEnquiryFilterModelToCarditEnquiryFilterVO(carditEnquiryFilterModel);
		log.debug(MODULE + " : " + "findGroupedCarditMails" + " Entering");
		Page<MailbagVO> mailbagVOS = mailController.findGroupedCarditMails(carditEnquiryFilterVO, pageNumber);
		if ( mailbagVOS == null ) {
			return null;
		}
		List<MailbagModel> mailbagModels = new ArrayList<>();
		mailbagModels.addAll(mailOperationsMapper.mailbagVOsToMailbagModel(mailbagVOS));
		return new Page(mailbagModels,mailbagVOS.getPageNumber(), mailbagVOS.getDefaultPageSize(),mailbagVOS.getActualPageSize(),
				mailbagVOS.getStartIndex(),mailbagVOS.getEndIndex(), mailbagVOS.hasNextPage(),mailbagVOS.getTotalRecordCount());
	}

	public MailbagModel findLyinglistSummaryView(MailbagEnquiryFilterModel mailbagEnquiryFilterModel) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = mailOperationsMapper
				.mailbagEnquiryFilterModelToMailbagEnquiryFilterVO(mailbagEnquiryFilterModel);
		log.debug(MODULE + " : " + "findLyinglistSummaryView" + " Entering");
		return mailOperationsMapper
				.mailbagVOToMailbagModel(mailController.findLyinglistSummaryView(mailbagEnquiryFilterVO));
	}

	public Page<MailbagModel> findGroupedLyingList(MailbagEnquiryFilterModel mailbagEnquiryFilterModel,
			int pageNumber) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = mailOperationsMapper
				.mailbagEnquiryFilterModelToMailbagEnquiryFilterVO(mailbagEnquiryFilterModel);
		log.debug(MODULE + " : " + "findGroupedCarditMails" + " Entering");
		Page<MailbagVO> mailbagVOS = mailController.findGroupedLyingList(mailbagEnquiryFilterVO, pageNumber);
		if ( mailbagVOS == null ) {
			return null;
		}
		List<MailbagModel> mailbagModels = new ArrayList<>();
		mailbagModels.addAll(mailOperationsMapper.mailbagVOsToMailbagModel(mailbagVOS));
		return new Page(mailbagModels,mailbagVOS.getPageNumber(), mailbagVOS.getDefaultPageSize(),mailbagVOS.getActualPageSize(),
				mailbagVOS.getStartIndex(),mailbagVOS.getEndIndex(), mailbagVOS.hasNextPage(),mailbagVOS.getTotalRecordCount());
	}



	/** 
	* @author a-7540
	*/
	public String validateFromFile(FileUploadFilterVO fileUploadFilterVO) {
		log.debug(MODULE + " : " + "validateFromFile" + " Entering");
		return mailUploadController.validateFromFile(fileUploadFilterVO);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#validateFlightAndContainer(com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO) Added by 			: A-8164 on 20-Feb-2019 Used for 	: Parameters	:	@param mailUploadVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Parameters	:	@throws MailHHTBusniessException Parameters	:	@throws MailMLDBusniessException Parameters	:	@throws MailTrackingBusinessException
	*/
	public ScannedMailDetailsModel validateFlightAndContainer(MailUploadModel mailUploadModel)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException {
		MailUploadVO mailUploadVO = mailOperationsMapper.mailUploadModelToMailUploadVO(mailUploadModel);
		log.debug(MODULE + " : " + "validateFlightAndContainer" + " Entering");
		try {
			return mailOperationsMapper.scannedMailDetailsVOToScannedMailDetailsModel(
					mailUploadController.validateFlightAndContainer(mailUploadVO));
		} catch (ForceAcceptanceException e) {
			throw new MailOperationsBusinessException(e);
		}
	}

	public void reopenInboundFlights(Collection<OperationalFlightModel> operationalFlightsModel) {
		var operationalFlightVOs = mailOperationsMapper
				.operationalFlightModelsToOperationalFlightVO(operationalFlightsModel);
		log.debug(MODULE + " : " + "reopenInboundFlights" + " Entering");
		mailController.reopenInboundFlights(operationalFlightVOs);
		log.debug(MODULE + " : " + "reopenInboundFlights" + " Exiting");
	}

	public void closeInboundFlights(Collection<OperationalFlightModel> operationalFlightsModel)
			throws MailOperationsBusinessException {
		var operationalFlightVOs = mailOperationsMapper
				.operationalFlightModelsToOperationalFlightVO(operationalFlightsModel);
		log.debug(MODULE + " : " + "closeInboundFlights" + " Entering");
		mailController.closeInboundFlights(operationalFlightVOs);
		log.debug(MODULE + " : " + "closeInboundFlights" + " Exiting");
	}

	public MailArrivalModel populateMailArrivalVOForInbound(OperationalFlightModel operationalFlightModel)
			throws MailOperationsBusinessException {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		return mailOperationsMapper
				.mailArrivalVOToMailArrivalModel(mailController.populateMailArrivalVOForInbound(operationalFlightVO));
	}

	public Page<ContainerDetailsModel> findArrivedContainersForInbound(MailArrivalFilterModel mailArrivalFilterModel)
			throws MailOperationsBusinessException {
		MailArrivalFilterVO mailArrivalFilterVO = mailOperationsMapper
				.mailArrivalFilterModelToMailArrivalFilterVO(mailArrivalFilterModel);
		return mailOperationsComponent.findArrivedContainersForInbound(mailArrivalFilterVO);
	}

	public Page<MailbagModel> findArrivedMailbagsForInbound(MailArrivalFilterModel mailArrivalFilterModel)
			throws MailOperationsBusinessException {
		MailArrivalFilterVO mailArrivalFilterVO = mailOperationsMapper
				.mailArrivalFilterModelToMailArrivalFilterVO(mailArrivalFilterModel);
		return mailOperationsComponent.findArrivedMailbagsForInbound(mailArrivalFilterVO);
	}

	public Page<DSNModel> findArrivedDsnsForInbound(MailArrivalFilterModel mailArrivalFilterModel)
			throws MailOperationsBusinessException {
		MailArrivalFilterVO mailArrivalFilterVO = mailOperationsMapper
				.mailArrivalFilterModelToMailArrivalFilterVO(mailArrivalFilterModel);
		return mailOperationsMapper
				.dSNVOsToDSNModel(mailController.findArrivedDsnsForInbound(mailArrivalFilterVO));
	}

	public Page<MailAcceptanceModel> findOutboundCarrierDetails(OperationalFlightModel operationalFlightModel,
			int pageNumber) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		Page<MailAcceptanceVO> mailAcceptanceVOS =
				mailController.findOutboundCarrierDetails(operationalFlightVO, pageNumber);
		List<MailAcceptanceModel> mailAcceptanceModels = new ArrayList<>();
		if(Objects.nonNull(mailAcceptanceVOS) && mailAcceptanceVOS.size()>0) {
			for (MailAcceptanceVO mailAcceptanceVO : mailAcceptanceVOS) {
				mailAcceptanceModels.add(mailOperationsMapper
						.mailAcceptanceVOToMailAcceptanceModel(mailAcceptanceVO));
			}
			return new
					Page(mailAcceptanceModels, mailAcceptanceVOS.getPageNumber(),
					mailAcceptanceVOS.getDefaultPageSize(), mailAcceptanceVOS.getActualPageSize(),
					mailAcceptanceVOS.getStartIndex(), mailAcceptanceVOS.getEndIndex()
					, mailAcceptanceVOS.hasNextPage(), mailAcceptanceVOS.getTotalRecordCount());
		}
		return new Page<>();
	}

	public Page<MailbagModel> getMailbagsinCarrierContainer(ContainerDetailsModel containerModel, int pageNumber) {
		ContainerDetailsVO containerVO = mailOperationsMapper
				.containerDetailsModelToContainerDetailsVO(containerModel);
		log.debug(MODULE + " : " + "getMailbagsinContainer" + " Entering");
		Page<MailbagVO> mailbagVos = mailController.getMailbagsinCarrierContainer(containerVO, pageNumber);
		List<MailbagModel> mailbagModels = new ArrayList<>();
		if(Objects.nonNull(mailbagVos) && mailbagVos.size()>0) {
			for(MailbagVO mailbagVO : mailbagVos){
				mailbagModels.add(mailOperationsMapper.mailbagVOToMailbagModel(mailbagVO));
			}
			return new
					Page(mailbagModels, mailbagVos.getPageNumber(),
					mailbagVos.getDefaultPageSize(), mailbagVos.getActualPageSize(),
					mailbagVos.getStartIndex(), mailbagVos.getEndIndex()
					, mailbagVos.hasNextPage(), mailbagVos.getTotalRecordCount());
		}
		return new Page<>();

	}

	public Page<DSNModel> getMailbagsinCarrierdsnview(ContainerDetailsModel containerModel, int pageNumber) {
		ContainerDetailsVO containerVO = mailOperationsMapper
				.containerDetailsModelToContainerDetailsVO(containerModel);
		log.debug(MODULE + " : " + "getMailbagsinCarrierdsnview" + " Entering");
		Page<DSNVO> dsnvos = mailController.getMailbagsinCarrierdsnview(containerVO, pageNumber);
		if(Objects.nonNull(dsnvos) && dsnvos.size()>0){
			List<DSNModel> dsnModels = new ArrayList<>();
			for(DSNVO dsnvo : dsnvos){
				dsnModels.add(mailOperationsMapper.dsnVoTodsnModel(dsnvo));
			}
			return new
					Page(dsnModels, dsnvos.getPageNumber(),
					dsnvos.getDefaultPageSize(), dsnvos.getActualPageSize(),
					dsnvos.getStartIndex(), dsnvos.getEndIndex()
					, dsnvos.hasNextPage(), dsnvos.getTotalRecordCount());
		}
		return new Page<>();
	}

	public Collection<DSNModel> getDSNsForContainer(ContainerDetailsModel containerModel) {
		ContainerDetailsVO containerVO = mailOperationsMapper
				.containerDetailsModelToContainerDetailsVO(containerModel);
		log.debug(MODULE + " : " + "getDSNsForContainer" + " Entering");
		return mailOperationsMapper.dSNVOsToDSNModel(mailController.getDSNsForContainer(containerVO));
	}

	public Collection<DSNModel> getRoutingInfoforDSN(Collection<DSNModel> dsnVosModel,
			ContainerDetailsModel containerDetailsModel) {
		ContainerDetailsVO containerDetailsVO = mailOperationsMapper
				.containerDetailsModelToContainerDetailsVO(containerDetailsModel);
		Collection<DSNVO> dsnVos = mailOperationsMapper.dSNModelsToDSNVO(dsnVosModel);
		log.debug(MODULE + " : " + "getRoutingInfoforDSN" + " Entering");
		return mailOperationsMapper
				.dSNVOsToDSNModel(mailController.getRoutingInfoforDSN(dsnVos, containerDetailsVO));
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI.findOffLoadDetails Added by 			: A-7929 on 18-Feb-2018 Used for 	: Parameters	:	@param offloadFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public OffloadModel findOffLoadDetails(OffloadFilterModel offloadFilterModel) {
		OffloadFilterVO offloadFilterVO = mailOperationsMapper
				.offloadFilterModelToOffloadFilterVO(offloadFilterModel);
		log.debug(MODULE + " : " + "findOffLoadDetails" + " Entering");
		OffloadVO offloadVo = mailController.findOffLoadDetails(offloadFilterVO);
		OffloadModel offloadModel=  mailOperationsMapper.offloadVOToOffloadModel(offloadVo);
		if(Objects.nonNull(offloadVo) && offloadVo.getOffloadContainerDetails()!=null) {
			Page<ContainerVO> containerVOS = offloadVo.getOffloadContainerDetails();
			List<ContainerModel> containerModels = new ArrayList<>();
			if (Objects.nonNull(containerVOS) && containerVOS.size() > 0) {
				containerModels.addAll(mailOperationsMapper.containerVOsToContainerModel(containerVOS));
			}
			Page<ContainerModel> containers = new Page(containerModels, containerVOS.getPageNumber(),
					containerVOS.getDefaultPageSize(), containerVOS.getActualPageSize(),
					containerVOS.getStartIndex(), containerVOS.getEndIndex()
					, containerVOS.hasNextPage(), containerVOS.getTotalRecordCount());
			offloadModel.setOffloadContainerDetails(containers);
		}
if(Objects.nonNull(offloadVo) &&offloadVo.getOffloadMailbags()!=null) {
	Page<MailbagVO> mailbagVOs = offloadVo.getOffloadMailbags();
	List<MailbagModel> mailbagModel = new ArrayList<>();
	if (Objects.nonNull(mailbagVOs) && mailbagVOs.size() > 0) {
		mailbagModel.addAll(mailOperationsMapper.mailbagVOsToMailbagModel(mailbagVOs));
	}
	Page<MailbagModel> mailbags = new Page(mailbagModel, mailbagVOs.getPageNumber(),
			mailbagVOs.getDefaultPageSize(), mailbagVOs.getActualPageSize(),
			mailbagVOs.getStartIndex(), mailbagVOs.getEndIndex()
			, mailbagVOs.hasNextPage(), mailbagVOs.getTotalRecordCount());
	offloadModel.setOffloadMailbags(mailbags);

}
if(Objects.nonNull(offloadVo) &&offloadVo.getOffloadDSNs()!=null) {
	Page<DespatchDetailsVO> dsnVOs = offloadVo.getOffloadDSNs();
	List<DespatchDetailsModel> dsnModel = new ArrayList<>();
	if (Objects.nonNull(dsnVOs) && dsnVOs.size() > 0) {
		dsnModel.addAll(mailOperationsMapper.despatchDetailsVOsToDespatchDetailsModel(dsnVOs));

	}
	Page<DespatchDetailsModel> dsns = new Page(dsnModel, dsnVOs.getPageNumber(),
			dsnVOs.getDefaultPageSize(), dsnVOs.getActualPageSize(),
			dsnVOs.getStartIndex(), dsnVOs.getEndIndex()
			, dsnVOs.hasNextPage(), dsnVOs.getTotalRecordCount());


	offloadModel.setOffloadDSNs(dsns);
}
		return offloadModel;


	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI.findOffLoadDetails Added by 			: A-7929 on 18-Feb-2018 Used for 	: Parameters	:	@param offloadFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public MailInConsignmentModel populatePCIDetailsforUSPS(MailInConsignmentModel mailInConsignmentModel,
			String airport, String companyCode, String rcpOrg, String rcpDest, String year) {
		MailInConsignmentVO mailInConsignment = mailOperationsMapper
				.mailInConsignmentModelToMailInConsignmentVO(mailInConsignmentModel);
		log.debug(MODULE + " : " + "findOffLoadDetails" + " Entering");
		return mailOperationsMapper.mailInConsignmentVOToMailInConsignmentModel(mailController
				.populatePCIDetailsforUSPS(mailInConsignment, airport, companyCode, rcpOrg, rcpDest, year));
	}

	public void calculateULDContentId(Collection<ContainerDetailsModel> containersModel,
			OperationalFlightModel toFlightModel) {
		OperationalFlightVO toFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(toFlightModel);
		Collection<ContainerDetailsVO> containerVOs = mailOperationsMapper
				.containerDetailsModelsToContainerDetailsVO(containersModel);
		log.debug(MODULE + " : " + "calculateULDContentId" + " Entering");
		//TODO: TO fix
		//aAMailController.calculateULDContentId(containerVOs, toFlightVO);
	}

	public void saveScreeningDetails(ScannedMailDetailsModel scannedMailDetailsModel) {
		ScannedMailDetailsVO scannedMailDetailsVO = mailOperationsMapper
				.scannedMailDetailsModelToScannedMailDetailsVO(scannedMailDetailsModel);
		log.debug(MODULE + " : " + "saveScreeningDetails" + " Entering");
		mailUploadController.saveScreeningDetails(scannedMailDetailsVO);
	}

	/** 
	* Added by 			: A-5526 on 12-Oct-2018 Used for 	: Parameters	:	@param runnerFlightFilterVO Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException
	* @throws PersistenceException
	*/
	public Page<RunnerFlightVO> findRunnerFlights(RunnerFlightFilterVO runnerFlightFilterVO)
			throws PersistenceException {
		return mailController.findRunnerFlights(runnerFlightFilterVO);
	}

	/** 
	* Added by 	:	A-7929 on 23-Oct-2018 Added for 	:   CRQ ICRD-241437 Parameters	:	@param mailbagEnquiryFilterVO,pageNumber Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	Page<MailbagVO>
	*/
	public Page<MailbagModel> findMailbagsForTruckFlight(MailbagEnquiryFilterModel mailbagEnquiryFilterModel,
			int pageNumber) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = mailOperationsMapper
				.mailbagEnquiryFilterModelToMailbagEnquiryFilterVO(mailbagEnquiryFilterModel);
		log.debug(MODULE + " : " + "findMailbagsForTruckFlight" + " Entering");
		return mailOperationsMapper.mailbagVOsToMailbagModel(
				mailController.findMailbagsForTruckFlight(mailbagEnquiryFilterVO, pageNumber));
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findDsnAndRsnForMailbag(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO) Added by 			: A-7531 on 31-Oct-2018 Used for 	: Parameters	:	@param maibagVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public MailbagModel findDsnAndRsnForMailbag(MailbagModel mailbagModel) {
		MailbagVO mailbagVO = mailOperationsMapper.mailbagModelToMailbagVO(mailbagModel);
		return mailOperationsMapper.mailbagVOToMailbagModel(mailController.findDsnAndRsnForMailbag(mailbagVO));
	}

	/** 
	*/
	public Page<ForceMajeureRequestModel> listForceMajeureApplicableMails(ForceMajeureRequestFilterModel filterModel,
			int pageNumber) {
		ForceMajeureRequestFilterVO filterVO = mailOperationsMapper
				.forceMajeureRequestFilterModelToForceMajeureRequestFilterVO(filterModel);
		log.debug(MODULE + " : " + "listForceMajeureApplicableMails" + " Entering");
		return mailOperationsMapper.forceMajeureRequestVOsToForceMajeureRequestModel(
				mailController.listForceMajeureApplicableMails(filterVO, pageNumber));
	}

	/** 
	*/
	public void saveForceMajeureRequest(ForceMajeureRequestFilterModel filterModel) {
		ForceMajeureRequestFilterVO filterVO = mailOperationsMapper
				.forceMajeureRequestFilterModelToForceMajeureRequestFilterVO(filterModel);
		log.debug(MODULE + " : " + "saveForceMajeureRequest" + " Entering");
		mailController.saveForceMajeureRequest(filterVO);
		log.debug(MODULE + " : " + "saveForceMajeureRequest" + " Exiting");
	}

	/** 
	*/
	public InvoiceTransactionLogVO initTxnForForceMajeure(InvoiceTransactionLogVO invoiceTransactionLogVO) {
		log.debug(MODULE + " : " + "initTxnForForceMajeure" + " Entering");
		return mailController.initTxnForForceMajeure(invoiceTransactionLogVO);
	}

	/** 
	*/
	public Page<ForceMajeureRequestModel> listForceMajeureDetails(ForceMajeureRequestFilterModel filterModel,
			int pageNumber) {
		ForceMajeureRequestFilterVO filterVO = mailOperationsMapper
				.forceMajeureRequestFilterModelToForceMajeureRequestFilterVO(filterModel);
		log.debug(MODULE + " : " + "listForceMajeureApplicableMails" + " Entering");
		return mailOperationsMapper.forceMajeureRequestVOsToForceMajeureRequestModel(
				mailController.listForceMajeureDetails(filterVO, pageNumber));
	}

	/** 
	*/
	public Page<ForceMajeureRequestModel> listForceMajeureRequestIds(ForceMajeureRequestFilterModel filterModel,
			int pageNumber) {
		ForceMajeureRequestFilterVO filterVO = mailOperationsMapper
				.forceMajeureRequestFilterModelToForceMajeureRequestFilterVO(filterModel);
		log.debug(MODULE + " : " + "listForceMajeureRequestIds" + " Entering");
		return mailOperationsMapper.forceMajeureRequestVOsToForceMajeureRequestModel(
				mailController.listForceMajeureRequestIds(filterVO, pageNumber));
	}

	/** 
	*/
	public void deleteForceMajeureRequest(Collection<ForceMajeureRequestModel> requestsModel) {
		Collection<ForceMajeureRequestVO> requestVOs = mailOperationsMapper
				.forceMajeureRequestModelsToForceMajeureRequestVO(requestsModel);
		log.debug(MODULE + " : " + "deleteForceMajeureRequest" + " Entering");
		mailController.deleteForceMajeureRequest(requestVOs);
		log.debug(MODULE + " : " + "deleteForceMajeureRequest" + " Exiting");
	}

	/** 
	*/
	public void updateForceMajeureRequest(ForceMajeureRequestFilterModel requestModel) {
		ForceMajeureRequestFilterVO requestVO = mailOperationsMapper
				.forceMajeureRequestFilterModelToForceMajeureRequestFilterVO(requestModel);
		log.debug(MODULE + " : " + "updateForceMajeureRequest" + " Entering");
		mailController.updateForceMajeureRequest(requestVO);
		log.debug(MODULE + " : " + "updateForceMajeureRequest" + " Exiting");
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findAllContainersInAssignedFlight(com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO) Added by 			: A-7540 on 05-DEC-2018 Used for 	: Parameters	:	@param operationalFlightVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public Collection<ContainerModel> findAllContainersInAssignedFlight(
			FlightValidationVO reassignedFlightValidationVO) {
		log.debug(MODULE + " : " + "isFlightClosedForInboundOperations" + " Entering");
		return mailOperationsMapper.containerVOsToContainerModel(
				mailController.findAllContainersInAssignedFlight(reassignedFlightValidationVO));
	}

	/** 
	* @throws MailMLDBusniessException
	* @throws MailOperationsBusinessException
	* @throws MailHHTBusniessException
	* @author A-7540
	*/
	public Collection<ErrorVO> processResditMails(Collection<MailScanDetailModel> mailScanDetailModel)
			throws MailOperationsBusinessException, MailMLDBusniessException, MailHHTBusniessException {
		Collection<MailScanDetailVO> mailScanDetailVO = mailOperationsMapper
				.mailScanDetailModelsToMailScanDetailVO(mailScanDetailModel);
		log.debug(MODULE + " : " + "saveMailServiceLevelDtls" + " Entering");
		return mailUploadController.processResditMails(mailScanDetailVO);
	}

	/** 
	* @author A-5526
	*/
	public long findMailBagSequenceNumberFromMailIdr(String mailIdr, String companyCode) {
		log.debug(MODULE + " : " + "findPALov" + " Entering");
		return mailController.findMailSequenceNumber(mailIdr, companyCode);
	}

	/** 
	* @author A-7794
	*/
	public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) throws PersistenceException {
		log.debug(MODULE + " : " + "processMailDataFromExcel" + " Entering");
		return documentController.processMailDataFromExcel(fileUploadFilterVO);
	}

	/**
	* @author A-5526Added for CRQ ICRD-233864
	*/
	public void onStatustoReadyforDelivery(MailArrivalModel mailArrivalModel) throws MailOperationsBusinessException {
		MailArrivalVO mailArrivalVO = mailOperationsMapper.mailArrivalModelToMailArrivalVO(mailArrivalModel);
		log.debug(MODULE + " : " + "saveAutoArrivalAndReadyForDelivery" + " Entering");
		mailController.onStatustoReadyforDelivery(mailArrivalVO);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveContentID Added by 			: A-7929 on 04-Feb-2018 Used for 	: Parameters	:	@param containerActionData Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public void saveContentID(Collection<ContainerModel> containersModel) {
		Collection<ContainerVO> containerVOs = mailOperationsMapper.containerModelsToContainerVO(containersModel);
		log.debug(MODULE + " : " + "saveContentID" + " Entering");
		mailController.saveContentID(containerVOs);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#updateActualWeightForMailbag Added by 			: A-8672 on 04-Feb-2018 Used for 	: Parameters	:	@param MailbagVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public void updateActualWeightForMailbag(MailbagModel mailbagModel) {
		MailbagVO mailbagVO = mailOperationsMapper.mailbagModelToMailbagVO(mailbagModel);
		mailController.updateActualWeightForMailbag(mailbagVO);
	}

	public Collection<MailMonitorSummaryModel> getPerformanceMonitorDetails(MailMonitorFilterModel filterModel) {
		MailMonitorFilterVO filterVO = mailOperationsMapper
				.mailMonitorFilterModelToMailMonitorFilterVO(filterModel);
		log.debug(MODULE + " : " + "saveContentID" + " Entering");
		return mailOperationsMapper
				.mailMonitorSummaryVOsToMailMonitorSummaryModel(mailController.getPerformanceMonitorDetails(filterVO));
	}

	public Page<MailbagModel> getPerformanceMonitorMailbags(MailMonitorFilterModel filterModel, String type,
			int pageNumber) {
		MailMonitorFilterVO filterVO = mailOperationsMapper
				.mailMonitorFilterModelToMailMonitorFilterVO(filterModel);
		return mailOperationsMapper
				.mailbagVOsToMailbagModel(mailController.getPerformanceMonitorMailbags(filterVO, type, pageNumber));
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveContentID Added by 			: A-8438 on 07-Feb-2018 Used for 	: MailManifestVO Parameters	:	@param findMailbagManifest Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public MailManifestModel findMailbagManifest(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "findMailbagManifest" + " Entering");
		return mailOperationsMapper
				.mailManifestVOToMailManifestModel(mailController.findMailbagManifest(operationalFlightVO));
	}

	public MailManifestModel findMailAWBManifest(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "findMailAWBManifest" + " Entering");
		return mailOperationsMapper
				.mailManifestVOToMailManifestModel(mailController.findMailAWBManifest(operationalFlightVO));
	}
	public MailManifestModel findDSNMailbagManifest(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "findDSNMailbagManifest" + " Entering");
		return mailOperationsMapper
				.mailManifestVOToMailManifestModel(mailController.findDSNMailbagManifest(operationalFlightVO));
	}
	public MailManifestModel findDestnCatManifest(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "findDestnCatManifest" + " Entering");
		return mailOperationsMapper
				.mailManifestVOToMailManifestModel(mailController.findDestnCatManifest(operationalFlightVO));
	}

	/**
	* Added by 			: A-8464 on 26-Mar-2019 Used for 	: ICRD-273761 Parameters	:	@param MailbagEnquiryFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public MailbagModel findMailbagDetailsForMailbagEnquiryHHT(MailbagEnquiryFilterModel mailbagEnquiryFilterModel) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = mailOperationsMapper
				.mailbagEnquiryFilterModelToMailbagEnquiryFilterVO(mailbagEnquiryFilterModel);
		log.debug(MODULE + " : " + "findMailbagDetailsForMailbagEnquiryHHT" + " Entering");
		return mailOperationsMapper
				.mailbagVOToMailbagModel(mailController.findMailbagDetailsForMailbagEnquiryHHT(mailbagEnquiryFilterVO));
	}

	/** 
	* Added by 	: A-8176 on 24-Apr-2019 Used for 	: ICRD-197279 Parameters	:	@param flightFilterVOs Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public Collection<FlightSegmentCapacitySummaryVO> fetchFlightCapacityDetails(
			Collection<FlightFilterVO> flightFilterVOs) {
		return mailController.fetchFlightCapacityDetails(flightFilterVOs);
	}

	/** 
	* Overriding Method	:	populateUldManifestVOWithContentID Added by 			: A-7929 on 04-March-2019 Used for 	: Parameters	:	@param DWSMasterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	* @throws FinderException
	*/
	public String fetchMailContentIDs(DWSMasterVO dwsMasterVO, String containerNumber, String assignedAirport)
			throws FinderException {
		log.debug(MODULE + " : " + "fetchMailContentIDs" + " Entering");
		//TODO: TO fix
		//return aAMailController.fetchMailContentIDs(dwsMasterVO, containerNumber, assignedAirport);
		return null;
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.findDuplicateMailbag Added by 	:	A-7531 on 16-May-2019 Used for 	: Parameters	:	@param companyCode Parameters	:	@param mailBagId Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Return type	: 	Collection<MailbagHistoryVO>
	* @throws PersistenceException
	*/
	public ArrayList<MailbagModel> findDuplicateMailbag(String companyCode, String mailBagId) {
		log.debug(MODULE + " : " + "findDuplicateMailbag" + " Entering");
		return mailOperationsMapper
				.mailbagVOsToMailbagModel(mailController.findDuplicateMailbag(companyCode, mailBagId));
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.sendULDAnnounceForFlight Added by 	:	A-8164 on 12-Feb-2021 Used for 	:	Sending UCS Announce from mail outbound Parameters	:	@param mailManifestVO Parameters	:	@throws SystemException Parameters	:	@throws RemoteException  Return type	: 	void
	*/
	public void sendULDAnnounceForFlight(MailManifestModel mailManifestModel) {
		MailManifestVO mailManifestVO = mailOperationsMapper.mailManifestModelToMailManifestVO(mailManifestModel);
		log.debug(MODULE + " : " + "sendULDAnnounceForFlight" + " Entering");
		//TODO: TO fix
		//tkMailController.sendULDAnnounceForFlight(mailManifestVO);
		log.debug(MODULE + " : " + "sendULDAnnounceForFlight" + " Exiting");
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#updateMailULDDetailsFromMHS(com.ibsplc.icargo.business.warehouse.defaults.operations.vo.StorageUnitCheckinVO) Added by 			: A-8164 on 18-Feb-2021 Used for 	: Parameters	:	@param storageUnitCheckinVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws ProxyException
	*/
	public boolean updateMailULDDetailsFromMHS(StorageUnitCheckinVO storageUnitCheckinVO) {
		log.debug(MODULE + " : " + "sendULDAnnounceForFlight" + " Entering");
		log.debug(MODULE + " : " + "sendULDAnnounceForFlight" + " Exiting");
		return mailController.updateMailULDDetailsFromMHS(storageUnitCheckinVO);
	}

	public Page<MailbagModel> findDeviationMailbags(MailbagEnquiryFilterModel mailbagEnquiryFilterModel,
													int pageNumber) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = mailOperationsMapper
				.mailbagEnquiryFilterModelToMailbagEnquiryFilterVO(mailbagEnquiryFilterModel);
		log.debug(MODULE + " : " + "findDeviationMailbags" + " Entering");
		Page<MailbagVO> mailbagVOS = mailController.findDeviationMailbags(mailbagEnquiryFilterVO, pageNumber);
		List<MailbagModel> mailbagModels = new ArrayList<>();
		if(Objects.nonNull (mailbagVOS)){
		mailbagModels.addAll(mailOperationsMapper.mailbagVOsToMailbagModels(mailbagVOS));
		return new Page(mailbagModels,mailbagVOS.getPageNumber(), mailbagVOS.getDefaultPageSize(),
				mailbagVOS.getActualPageSize(), mailbagVOS.getStartIndex(), mailbagVOS.getEndIndex(),
				mailbagVOS.hasNextPage(), mailbagVOS.getTotalRecordCount());}
		else
			return null;
	}

	public String findProductsByName(String companyCode, String product) {
		log.debug(MODULE + " : " + "findProductsByName" + " Entering");
		return mailController.findProductsByName(companyCode, product);
	}

	public void buildResditProxy(Collection<ResditEventModel> resditEventModel) {
		Collection<ResditEventVO> resditEvent = mailOperationsMapper
				.resditEventModelsToResditEventVO(resditEventModel);
		log.debug(MODULE + " : " + "buildResditProxy" + " Entering");
		resditController.buildResditProxy(resditEvent);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveCarditTempMessages(java.util.Collection) Added by 			: A-6287 on 01-Mar-2020 Used for 	: Parameters	:	@param carditTempMsgVOs Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Parameters	:	@throws MailTrackingBusinessException
	*/
	public void saveCarditTempMessages(Collection<CarditTempMsgVO> carditTempMsgVOs)
			throws MailOperationsBusinessException {
		log.debug(MODULE + " : " + "saveCarditMessages" + " Entering");
		mailController.saveCarditTempMessages(carditTempMsgVOs);
		log.debug(MODULE + " : " + "saveCarditMessages" + " Exiting");
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#getTempCarditMessages(java.lang.String) Added by 			: A-6287 on 01-Mar-2020 Used for 	: Parameters	:	@param companyCode Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Parameters	:	@throws FinderException
	*/
	public Collection<CarditTempMsgVO> getTempCarditMessages(String companyCode, String includeMailBoxIdr,
			String excludeMailBoxIdr, String includedOrigins, String excludedOrigins, int pageSize, int noOfdays)
			throws FinderException {
		return mailController.getTempCarditMessages(companyCode, includeMailBoxIdr, excludeMailBoxIdr, includedOrigins,
				excludedOrigins, pageSize, noOfdays);
	}

	public Collection<ErrorVO> saveCarditMsgs(EDIInterchangeModel ediInterchangeModel)
			throws MailOperationsBusinessException {
		EDIInterchangeVO ediInterchangeVO = mailOperationsMapper
				.eDIInterchangeModelToEDIInterchangeVO(ediInterchangeModel);
		log.debug(MODULE + " : " + "saveCarditMsgs" + " Entering");
		String errorCode = null;
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		try {
			errorVOs = mailController.saveCarditMessages(ediInterchangeVO);

		} catch (DuplicateMailBagsException e) {
			throw new MailOperationsBusinessException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			throw new MailOperationsBusinessException(e);
		} finally {

		}
		return errorVOs;
	}

	public void deleteEmptyContainer(ContainerDetailsModel containerDetailsModel) {
		ContainerDetailsVO containerDetailsVO = mailOperationsMapper
				.containerDetailsModelToContainerDetailsVO(containerDetailsModel);
		log.debug(MODULE + " : " + "findProductsByName" + " Entering");
		mailController.deleteEmptyContainer(containerDetailsVO);
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.updateGateClearStatus Added by 	:	U-1467 on 09-Mar-2020 Parameters	:	@param operationalFlightVO Parameters	:	@param gateClearanceStatus Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	void
	*/
	public void updateGateClearStatus(OperationalFlightModel operationalFlightModel,
			String gateClearanceStatus) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		mailController.updateGateClearStatus(operationalFlightVO, gateClearanceStatus);
	}

	/** 
	* @throws MailMLDBusniessException
	* @throws MailOperationsBusinessException
	* @throws MailHHTBusniessException
	* @author U-1317
	*/
	public Collection<ErrorVO> processResditMailsForAllEvents(Collection<MailUploadModel> mailScanDetailModel)
			throws MailOperationsBusinessException, MailMLDBusniessException, MailHHTBusniessException {
		Collection<MailUploadVO> mailScanDetailVO = mailOperationsMapper
				.mailUploadModelsToMailUploadVO(mailScanDetailModel);
		log.debug(MODULE + " : " + "processResditMailsForAllEvents" + " Entering");
		return mailUploadController.processResditMailsForAllEvents(mailScanDetailVO);
	}

	/** 
	* Added by     : a-8353 Used for 	: IASCB-45360 Parameters	:	@param ScannedMailDetailsVO
	* @throws SystemException
	*/
	public void flagResditForAcceptanceInTruck(ScannedMailDetailsModel scannedMailDetailsModel)
			throws MailMLDBusniessException, MailHHTBusniessException {
		ScannedMailDetailsVO scannedMailDetailsVO = mailOperationsMapper
				.scannedMailDetailsModelToScannedMailDetailsVO(scannedMailDetailsModel);
		log.debug(MODULE + " : " + "flagResditForAcceptanceInTruck" + " Entering");
		//TODO: To fix in Neo
//		((MailTrackingDefaultsBI) SpringAdapter.getInstance().getBean(MAIL_OPERATION_SERVICES))
//				.flagResditForAcceptanceInTruck(scannedMailDetailsVO);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#listCarditDsnDetails(com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO) Added by 			: A-8164 on 04-Sep-2019 Used for 	:	List Cardit DSN Details Parameters	:	@param dsnEnquiryFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException Parameters	:	@throws MailTrackingBusinessException
	*/
	public Page<DSNModel> listCarditDsnDetails(DSNEnquiryFilterModel dsnEnquiryFilterModel)
			throws MailOperationsBusinessException {
		DSNEnquiryFilterVO dsnEnquiryFilterVO = mailOperationsMapper
				.dSNEnquiryFilterModelToDSNEnquiryFilterVO(dsnEnquiryFilterModel);
		return mailOperationsMapper.dSNVOsToDSNModel(mailController.listCarditDsnDetails(dsnEnquiryFilterVO));
	}

	/**
	 * @author A-7540
	 * @throws SystemException
	 * @throws MailOperationsBusinessException
	 */
	public Collection<ErrorVO> saveCDTMessages(EDIInterchangeModel ediInterchangeModel)
			throws MailOperationsBusinessException {
		EDIInterchangeVO ediInterchangeVO = mailOperationsMapper
				.eDIInterchangeModelToEDIInterchangeVO(ediInterchangeModel);
		Collection<ErrorVO> errorVO = new ArrayList<>();
		Transaction tx = null;
		String errorCode = null;
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		boolean canCommit = false;
		try {
			TransactionProvider tm = PersistenceController.__getTransactionProvider();
			tx = tm.getNewTransaction(false);
			errorVO = mailController.saveCDTMessages(ediInterchangeVO);
		} catch (DuplicateMailBagsException e) {
			throw new MailOperationsBusinessException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			return errorVO;
		} finally {
			log.debug(MODULE + " : " + "saveCarditMsgs" + " Exiting");
			if (tx == null) {
				log.debug(errorCode);
			} else if (canCommit) {
				tx.commit();
			} else {
				tx.rollback();
			}
		}
		log.debug(MODULE + " : " + "saveCDTMessages" + " Exiting");
		return errorVO;
	}

	public ErrorVO validateContainerNumberForDeviatedMailbags(ContainerDetailsModel containerDetailsModel,
			long mailSequenceNumber) throws MailOperationsBusinessException {
		ContainerDetailsVO containerDetailsVO = mailOperationsMapper
				.containerDetailsModelToContainerDetailsVO(containerDetailsModel);
		return mailController.validateContainerNumberForDeviatedMailbags(containerDetailsVO, mailSequenceNumber);
	}

	/** 
	* @param companyCode
	* @author A-5526 This method is used to find the details of approved Force Meajure info of a Mailbag
	*/
	public Collection<ForceMajeureRequestModel> findApprovedForceMajeureDetails(String companyCode, String mailBagId,
			long mailSequenceNumber) {
		return mailOperationsMapper.forceMajeureRequestVOsToForceMajeureRequestModel(
				mailController.findApprovedForceMajeureDetails(companyCode, mailBagId, mailSequenceNumber));
	}

	/**
	* @return
	* @throws SystemException
	* @throws PersistenceException
	* @author A-8353
	*/
	public MailbagInULDForSegmentModel getManifestInfo(MailbagModel mailbagModel) throws PersistenceException {
		MailbagVO mailbagVO = mailOperationsMapper.mailbagModelToMailbagVO(mailbagModel);
		return mailOperationsMapper
				.mailbagInULDForSegmentVOToMailbagInULDForSegmentModel(mailController.getManifestInfo(mailbagVO));
	}

	/** 
	* @author A-8353
	* @throws PersistenceException
	* @throws SystemException
	*/
	public String checkMailInULDExistForNextSeg(String containerNumber, String airpotCode, String companyCode) {
		return mailController.checkMailInULDExistForNextSeg(containerNumber, airpotCode, companyCode);
	}

	/** 
	* @author A-8672
	* @throws SystemException
	* @throws FinderException
	*/
	public void updateRetainFlagForContainer(ContainerModel containerModel) throws FinderException {
		ContainerVO containerVO = mailOperationsMapper.containerModelToContainerVO(containerModel);
		mailController.updateRetainFlagForContainer(containerVO);
	}
	public void createAutoAttachAWBJobSchedule(AutoAttachAWBJobScheduleModel autoAttachAWBJobScheduleModel) {
		AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleVO = mailOperationsMapper
				.autoAttachAWBJobScheduleModelToAutoAttachAWBJobScheduleVO(autoAttachAWBJobScheduleModel);
		mailController.createAutoAttachAWBJobSchedule(autoAttachAWBJobScheduleVO);
	}

	/** 
	*/
	public Collection<ConsignmentDocumentModel> findTransferManifestConsignmentDetails(
			TransferManifestModel transferManifestModel) {
		TransferManifestVO transferManifestVO = mailOperationsMapper
				.transferManifestModelToTransferManifestVO(transferManifestModel);
		return mailOperationsMapper.consignmentDocumentVOsToConsignmentDocumentModel(
				mailController.findTransferManifestConsignmentDetails(transferManifestVO));
	}

	public ConsignmentDocumentModel findConsignmentScreeningDetails(String consignmentNumber, String companyCode,
			String poaCode) throws MailOperationsBusinessException {
		return mailOperationsComponent.findConsignmentScreeningDetails(consignmentNumber, companyCode, poaCode);
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.saveTransferFromManifest Added by 	:	A-8893 on 06-Nov-2020 Parameters	:	@param TransferManifestVO Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	void
	*/
	public void saveTransferFromManifest(TransferManifestModel transferManifestModel)
			throws MailBookingException, CapacityBookingProxyException,
			MailOperationsBusinessException, InvalidFlightSegmentException {
		TransferManifestVO transferManifestVO = mailOperationsMapper
				.transferManifestModelToTransferManifestVO(transferManifestModel);
		try {
			mailController.saveTransferFromManifest(transferManifestVO);
		} finally {
		}
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.rejectTransferFromManifest Added by 	:	A-8893 on 06-Nov-2020 Parameters	:	@param TransferManifestVO Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Return type	: 	void
	*/
	public void rejectTransferFromManifest(TransferManifestModel transferManifestModel) {
		TransferManifestVO transferManifestVO = mailOperationsMapper
				.transferManifestModelToTransferManifestVO(transferManifestModel);
		mailController.rejectTransferFromManifest(transferManifestVO);
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.performFlushAndClear Added for 	:	IASCB-79746 Parameters	:	@throws SystemException  Return type	: 	void
	*/
	private static void performFlushAndClear() {
		try {
			PersistenceController.getEntityManager().flush();
			PersistenceController.getEntityManager().clear();
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	/** 
	*/
	public void removeFromInbound(OffloadModel offloadVoModel) throws MailOperationsBusinessException {
		OffloadVO offloadVo = mailOperationsMapper.offloadModelToOffloadVO(offloadVoModel);
		mailController.removeFromInbound(offloadVo);
	}

	/**
	* @return
	* @throws SystemException
	*/
	public MailbagModel findMailBillingStatus(MailbagModel mailbagModel) {
		MailbagVO mailbagVO = mailOperationsMapper.mailbagModelToMailbagVO(mailbagModel);
		log.debug(MODULE + " : " + "findMailBillingStatus" + " Entering");
		return mailOperationsMapper.mailbagVOToMailbagModel(mailController.findMailBillingStatus(mailbagVO));
	}

	public String saveUploadedForceMajeureData(FileUploadFilterVO fileUploadFilterVO) {
		log.debug(MODULE + " : " + "saveUploadedForceMajeureData" + " Entering");
		return mailController.saveUploadedForceMajeureData(fileUploadFilterVO);
	}

	public String findAutoPopulateSubtype(DocumentFilterVO documnetFilterVO) throws MailOperationsBusinessException {
		log.debug(MODULE + " : " + "findAutoPopulateSubtype" + " Entering");
		try {
			return mailController.findAutoPopulateSubtype(documnetFilterVO);
		} catch (BusinessException e) {
			throw new MailOperationsBusinessException(e);
		}
	}

	public String findOfficeOfExchangeForCarditMissingDomMail(String companyCode, String airportCode) {
		log.debug(MODULE + " : " + "findOfficeOfExchangeForCarditMissingDomMail" + " Entering");
		return mailController.findOfficeOfExchangeForCarditMissingDomMail(companyCode, airportCode);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveMailUploadDetailsFromMailOnload(java.util.Collection) Added by 			: A-8061 on 07-Apr-2021 Used for 	: Parameters	:	@param mailUploadVOs Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException Parameters	:	@throws MailHHTBusniessException Parameters	:	@throws MailMLDBusniessException Parameters	:	@throws MailTrackingBusinessException
	*/
	public Map<String, ErrorVO> saveMailUploadDetailsFromMailOnload(Collection<MailUploadModel> mailUploadsModel)
			throws MailHHTBusniessException, MailMLDBusniessException, MailOperationsBusinessException {
		Collection<MailUploadVO> mailUploadVOs = mailOperationsMapper
				.mailUploadModelsToMailUploadVO(mailUploadsModel);
		return mailUploadController.saveMailUploadDetailsFromMailOnload(mailUploadVOs);
	}

	/** 
	* @author A-8353
	* @throws SystemException
	*/
	public void updateOriginAndDestinationForMailbag(Collection<MailbagModel> mailbagsModel) {
		Collection<MailbagVO> mailbagVOs = mailOperationsMapper.mailbagModelsToMailbagVO(mailbagsModel);
		mailController.updateOriginAndDestinationForMailbag(mailbagVOs);
	}

	public long insertMailbagAndHistory(MailbagModel mailbagModel) {
		MailbagVO mailbagVO = mailOperationsMapper.mailbagModelToMailbagVO(mailbagModel);
		return mailController.insertMailbagAndHistory(mailbagVO);
	}

	public Collection<ImportFlightOperationsVO> findInboundFlightOperationsDetails(ImportOperationsFilterVO filterVO,
			Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException {
		return mailUploadController.findInboundFlightOperationsDetails(filterVO, manifestFilterVOs);
	}

	public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO> findOffloadULDDetailsAtAirport(
			com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO filterVO)
			throws PersistenceException {

		return mailUploadController.findOffloadULDDetailsAtAirport(filterVO);
	}

	public Collection<ManifestVO> findExportFlightOperationsDetails(ImportOperationsFilterVO filterVO,
			Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException {
		log.debug(MODULE + " : " + "findExportFlightOperationsDetails" + " Entering");
		return mailUploadController.findExportFlightOperationsDetails(filterVO, manifestFilterVOs);
	}

	public void saveUploadedConsignmentData(Collection<ConsignmentDocumentModel> consignmentDocumentsModel)
			throws MailOperationsBusinessException {
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = mailOperationsMapper
				.consignmentDocumentModelsToConsignmentDocumentVO(consignmentDocumentsModel);
		log.debug(MODULE + " : " + "saveUploadedConsignmentData" + " Entering");
		documentController.saveUploadedConsignmentData(consignmentDocumentVOs);
	}

	public Collection<ConsignmentDocumentModel> fetchConsignmentDetailsForUpload(
			FileUploadFilterVO fileUploadFilterVO) {
		log.debug(MODULE + " : " + "fetchConsignmentDetailsForUpload" + " Entering");
		return mailOperationsMapper.consignmentDocumentVOsToConsignmentDocumentModel(
				mailController.fetchConsignmentDetailsForUpload(fileUploadFilterVO));
	}

	public void saveMailbagHistory(Collection<MailbagHistoryModel> mailbagHistorysModel) {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = mailOperationsMapper
				.mailbagHistoryModelsToMailbagHistoryVO(mailbagHistorysModel);
		log.debug(MODULE + " : " + "saveMailbagHistory" + " Entering");
		mailController.saveMailbagHistory(mailbagHistoryVOs);
	}

	/**
	* @author A-8893
	*/
	public void releaseContainers(Collection<ContainerModel> containersToBeReleasedModel) {
		Collection<ContainerVO> containerVOsToBeReleased = mailOperationsMapper
				.containerModelsToContainerVO(containersToBeReleasedModel);
		log.debug(MODULE + " : " + "releaseContainers" + " Entering");
		mailController.releaseContainers(containerVOsToBeReleased);
	}

	/** 
	* Method		:	MailTrackingDefaultsServicesEJB.findContainerJourneyID Parameters	:	@param containerFilterVO Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException  Return type	: 	Collection<ContainerDetailsVO>
	*/
	public Collection<ContainerDetailsModel> findContainerJourneyID(ConsignmentFilterModel consignmentFilterModel) {
		ConsignmentFilterVO consignmentFilterVO = mailOperationsMapper
				.consignmentFilterModelToConsignmentFilterVO(consignmentFilterModel);
		log.debug(MODULE + " : " + "findContainerJourneyID" + " Entering");
		return mailOperationsMapper
				.containerDetailsVOsToContainerDetailsModel(mailController.findContainerJourneyID(consignmentFilterVO));
	}

	public void stampResdits(Collection<MailResditModel> mailResditsModel) throws MailOperationsBusinessException {
		Collection<MailResditVO> mailResditVOs = mailOperationsMapper
				.mailResditModelsToMailResditVO(mailResditsModel);
		log.debug(MODULE + " : " + "stampResdits" + " Entering");
		mailController.stampResdits(mailResditVOs);
	}

	public Collection<ContainerDetailsModel> findArrivalDetailsForReleasingMails(
			OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "findArrivalDetailsForReleasingMails" + " Entering");
		return mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(
				mailController.findArrivalDetailsForReleasingMails(operationalFlightVO));
	}

	/** 
	* @author A-8353
	* @throws SystemException
	*/
	public void performErrorStampingForFoundMailWebServices(MailUploadModel mailBagModel, String scanningPort) {
		MailUploadVO mailBagVO = mailOperationsMapper.mailUploadModelToMailUploadVO(mailBagModel);
		mailUploadController.performErrorStampingForFoundMailWebServices(mailBagVO, scanningPort);
	}

	/** 
	* @author A-9084
	* @throws SystemException 
	*/
	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterModel mailAuditFilterModel) {
		MailAuditFilterVO mailAuditFilterVO = mailOperationsMapper
				.mailAuditFilterModelToMailAuditFilterVO(mailAuditFilterModel);
		return mailController.findAssignFlightAuditDetails(mailAuditFilterVO);
	}

	public Collection<MailbagModel> getFoundArrivalMailBags(MailArrivalModel mailArrivalModel) {
		MailArrivalVO mailArrivalVO = mailOperationsMapper.mailArrivalModelToMailArrivalVO(mailArrivalModel);
		log.debug(MODULE + " : " + "getFoundArrivalMailBags" + " Entering");
		return mailOperationsMapper
				.mailbagVOsToMailbagModels(mailController.getFoundArrivalMailBags(mailArrivalVO));
	}
	public void dettachMailBookingDetails(Collection<MailbagModel> mailbagsModel) {
		Collection<MailbagVO> mailbagVOs = mailOperationsMapper.mailbagModelsToMailbagVO(mailbagsModel);
		log.debug(MODULE + " : " + "getFoundArrivalMailBags" + " Entering");
		mailController.dettachMailBookingDetails(mailbagVOs);
	}

	public void attachAWBForMail(Collection<MailBookingDetailModel> mailBookingDetailsModel,
			Collection<MailbagModel> mailbagsModel) {
		Collection<MailbagVO> mailbagVOs = mailOperationsMapper.mailbagModelsToMailbagVO(mailbagsModel);
		Collection<MailBookingDetailVO> mailBookingDetailVOs = mailOperationsMapper
				.mailBookingDetailModelsToMailBookingDetailVO(mailBookingDetailsModel);
		mailController.attachAWBForMailForAddons(mailBookingDetailVOs, mailbagVOs);
	}

	public String findNearestAirportOfCity(String companyCode, String exchangeCode) {
		return mailController.findNearestAirportOfCity(companyCode, exchangeCode);
	}

	public void markUnmarkUldIndicator(ContainerModel containerModel) {
		ContainerVO containerVO = mailOperationsMapper.containerModelToContainerVO(containerModel);
		log.debug(MODULE + " : " + "markUnmarkUldIndicator" + " Entering");
		mailController.markUnmarkUldIndicator(containerVO);
	}

	/**
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws MailOperationsBusinessException
	* @throws PersistenceException
	* @author A-9998
	*/
	public ScannedMailDetailsModel saveMailUploadDetailsForULDFULIndicator(MailUploadModel mailBagModel,
			String scanningPort) throws MailHHTBusniessException, MailMLDBusniessException,
			MailOperationsBusinessException, PersistenceException {
		MailUploadVO mailBagVO = mailOperationsMapper.mailUploadModelToMailUploadVO(mailBagModel);
		return mailOperationsMapper.scannedMailDetailsVOToScannedMailDetailsModel(
				mailUploadController.saveMailUploadDetailsForULDFULIndicator(mailBagVO, scanningPort));
	}

	public void triggerEmailForPureTransferContainers(Collection<OperationalFlightModel> operationalFlightsModel) {
		Collection<OperationalFlightVO> operationalFlightVOs = mailOperationsMapper
				.operationalFlightModelsToOperationalFlightVO(operationalFlightsModel);
		log.debug(MODULE + " : " + "triggerEmailForPureTransferContainers" + " Entering");
		mailController.triggerEmailForPureTransferContainers(operationalFlightVOs);
	}

	public MailbagModel listmailbagSecurityDetails(MailScreeningFilterModel mailScreeningFilterVoModel) {
		MailScreeningFilterVO mailScreeningFilterVo = mailOperationsMapper
				.mailScreeningFilterModelToMailScreeningFilterVO(mailScreeningFilterVoModel);
		log.debug(MODULE + " : " + "listmailbagSecurityDetails" + " Entering");
		return mailOperationsMapper
				.mailbagVOToMailbagModel(mailController.listmailbagSecurityDetails(mailScreeningFilterVo));
	}

	public void editscreeningDetails(Collection<ConsignmentScreeningModel> consignmentScreeningsModel) {
		log.debug(MODULE + " : " + "editscreeningDetails" + " Entering");
		var consignmentScreeningVOs = mailOperationsMapper
				.consignmentScreeningModelsToConsignmentScreeningVO(consignmentScreeningsModel);
		mailOperationsComponent.editScreeningDetails(consignmentScreeningVOs);
	}

	/** 
	* @author A-10383Method		:	MailTrackingDefaultsServicesEJB.deletescreeningDetails
	*/
	public void deletescreeningDetails(Collection<ConsignmentScreeningModel> consignmentScreeningsModel) {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = mailOperationsMapper
				.consignmentScreeningModelsToConsignmentScreeningVO(consignmentScreeningsModel);
		log.debug(MODULE + " : " + "deletescreeningDetails" + " Entering");
		mailController.deletescreeningDetails(consignmentScreeningVOs);
	}

	/**
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveMailSecurityStatus(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO) Added by 			: A-4809 on 18-May-2022 Used for 	: Parameters	:	@param mailbagVO Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public void saveMailSecurityStatus(MailbagModel mailbagModel) {
		MailbagVO mailbagVO = mailOperationsMapper.mailbagModelToMailbagVO(mailbagModel);
		log.debug(MODULE + " : " + "saveMailSecurityStatus" + " Entering");
		mailController.saveMailSecurityStatus(mailbagVO);
	}

	public MailbagModel findAirportFromMailbag(MailbagModel mailbagvoModel) throws FinderException {
		MailbagVO mailbagvo = mailOperationsMapper.mailbagModelToMailbagVO(mailbagvoModel);
		log.debug(MODULE + " : " + "findAirportFromMailbag" + " Entering");
		return mailOperationsMapper.mailbagVOToMailbagModel(mailController.findAirportFromMailbag(mailbagvo));
	}

	public void saveFligthLoadPlanForMail(Collection<FlightLoadPlanContainerModel> flightLoadPlanContainersModel)
			throws BusinessException {
		Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs = mailOperationsMapper
				.flightLoadPlanContainerModelsToFlightLoadPlanContainerVO(flightLoadPlanContainersModel);
		log.debug(MODULE + " : " + "saveFligthLoadPlanForMail" + " Entering");
		mailController.saveFligthLoadPlanForMail(flightLoadPlanContainerVOs);
	}

	public Collection<FlightLoadPlanContainerModel> findLoadPlandetails(
			SearchContainerFilterModel searchContainerFilterModel) {
		SearchContainerFilterVO searchContainerFilterVO = mailOperationsMapper
				.searchContainerFilterModelToSearchContainerFilterVO(searchContainerFilterModel);
		log.debug(MODULE + " : " + "findLoadPlandetails" + " Entering");
		return mailOperationsMapper.flightLoadPlanContainerVOsToFlightLoadPlanContainerModel(
				mailController.findLoadPlandetails(searchContainerFilterVO));
	}

	/** 
	* @author A-10383Method		:	MailTrackingDefaultsServicesEJB.saveConsignmentDetailsMaster
	*/
	public void saveConsignmentDetailsMaster(ConsignmentDocumentModel consignmentDocumentModel) {
		log.debug(MODULE + " : " + "saveConsignmentDetailsMaster" + " Entering");
		var consignmentDocumentVO = mailOperationsMapper
				.consignmentDocumentModelToConsignmentDocumentVO(consignmentDocumentModel);
		mailOperationsComponent.saveConsignmentDetailsMaster(consignmentDocumentVO);
	}

	/** 
	* @author A-8353
	* @param securityAndScreeningMessageVO
	* @return
	* @throws SystemException
	* @throws MailOperationsBusinessException
	*/
	public Map<String, ErrorVO> saveSecurityScreeningFromService(
			SecurityAndScreeningMessageVO securityAndScreeningMessageVO) throws MailOperationsBusinessException {
		return mailController.saveSecurityScreeningFromService(securityAndScreeningMessageVO);
	}

	/** 
	* @author A-9998Method		:	MailTrackingDefaultsServicesEJB.findAirportParameterCode
	* @param flightFilterVO
	* @param parCodes
	* @throws SystemException
	*/
	public Map<String, String> findAirportParameterCode(FlightFilterVO flightFilterVO, Collection<String> parCodes) {
		log.debug(MODULE + " : " + "findAirportParameterCode" + " Entering");
		return mailController.findAirportParameterCode(flightFilterVO, parCodes);
	}
	public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> fetchMailIndicatorForProgress(
			Collection<FlightListingFilterVO> flightListingFilterVOs) {
		log.debug(MODULE + " : " + "fetchMailIndicatorForProgress" + " Entering");

				return mailController.fetchMailIndicatorForProgress(flightListingFilterVOs);
	}

	public Collection<SecurityScreeningValidationModel> findSecurityScreeningValidations(
			SecurityScreeningValidationFilterModel securityScreeningValidationFilterModel)
			throws MailOperationsBusinessException {
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = mailOperationsMapper
				.securityScreeningValidationFilterModelToSecurityScreeningValidationFilterVO(
						securityScreeningValidationFilterModel);
		return mailOperationsMapper.securityScreeningValidationVOsToSecurityScreeningValidationModel(
				mailController.findSecurityScreeningValidations(securityScreeningValidationFilterVO));
	}

	public SecurityScreeningValidationModel doSecurityAndScreeningValidationAtContainerLevel(
			OperationalFlightModel operationalFlightModel, Collection<ContainerModel> selectedContainersModel)
			throws BusinessException {
		Collection<ContainerVO> selectedContainerVOs = mailOperationsMapper
				.containerModelsToContainerVO(selectedContainersModel);
		OperationalFlightVO operationalFlightVO = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		return mailOperationsMapper.securityScreeningValidationVOToSecurityScreeningValidationModel(mailController
				.doSecurityAndScreeningValidationAtContainerLevel(operationalFlightVO, selectedContainerVOs));
	}

	public void updateIntFlgAsNForMailBagsInConatiner(HbaMarkingModel hbaMarkingModel) {
		HbaMarkingVO hbaMarkingVO = mailOperationsMapper.hbaMarkingModelToHbaMarkingVO(hbaMarkingModel);
		log.debug("LhMailOperationsEJB" + " : " + "markHba" + " Entering");
		mailController.updateIntFlgAsNForMailBagsInConatiner(hbaMarkingVO);
	}

	public String generateAutomaticBarrowId(String cmpcod) {
		log.debug(MODULE + " : " + "generateAutomaticBarrowId" + " Entering");
		return mailController.generateAutomaticBarrowId(cmpcod);
	}

	/** 
	* Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findCN46TransferManifestDetails(com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO) Added by 			: A-10647 on 27-Oct-2022 Used for 	: Parameters	:	@param transferManifestVO Parameters	:	@return Parameters	:	@throws RemoteException Parameters	:	@throws SystemException
	*/
	public Collection<ConsignmentDocumentModel> findCN46TransferManifestDetails(
			TransferManifestModel transferManifestModel) {
		TransferManifestVO transferManifestVO = mailOperationsMapper
				.transferManifestModelToTransferManifestVO(transferManifestModel);
		return mailOperationsMapper.consignmentDocumentVOsToConsignmentDocumentModel(
				mailController.findCN46TransferManifestDetails(transferManifestVO));
	}

	/** 
	* @author A-8353
	*/
	public Collection<SecurityScreeningValidationModel> doApplicableRegulationFlagValidationForPABuidContainer(
			MailbagModel mailbagModel, SecurityScreeningValidationFilterModel securityScreeningValidationFilterModel)
			throws BusinessException {
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = mailOperationsMapper
				.securityScreeningValidationFilterModelToSecurityScreeningValidationFilterVO(
						securityScreeningValidationFilterModel);
		MailbagVO mailbagVO = mailOperationsMapper.mailbagModelToMailbagVO(mailbagModel);
		return mailOperationsMapper.securityScreeningValidationVOsToSecurityScreeningValidationModel(
				mailController.doApplicableRegulationFlagValidationForPABuidContainer(mailbagVO,
						securityScreeningValidationFilterVO));
	}

	public Collection<MailAcceptanceModel> fetchFlightPreAdviceDetails(Collection<FlightFilterVO> flightFilterVOs) {
		return mailOperationsMapper
				.mailAcceptanceVOsToMailAcceptanceModel(mailController.fetchFlightPreAdviceDetails(flightFilterVOs));
	}

	public ContainerModel updateActualWeightForMailContainer(ContainerModel containerModel) {
		ContainerVO containerVO = mailOperationsMapper.containerModelToContainerVO(containerModel);
		return mailOperationsMapper
				.containerVOToContainerModel(mailController.updateActualWeightForMailContainer(containerVO));
	}

	public Collection<OperationalFlightModel> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleModel mailInboundAutoAttachAWBJobScheduleModel) {
		MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO = mailOperationsMapper
				.mailInboundAutoAttachAWBJobScheduleModelToMailInboundAutoAttachAWBJobScheduleVO(
						mailInboundAutoAttachAWBJobScheduleModel);
		return mailOperationsMapper.operationalFlightVOsToOperationalFlightModel(
				mailController.findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleVO));
	}

	public Page<MailTransitModel> findMailTransit(MailTransitFilterModel mailTransitFilterModel, int pageNumber)
			throws PersistenceException {
		MailTransitFilterVO mailTransitFilterVO = mailOperationsMapper
				.mailTransitFilterModelToMailTransitFilterVO(mailTransitFilterModel);
		log.debug(MODULE + " : " + "findMailTransit" + " Entering");
		return mailOperationsMapper
				.mailTransitVOsToMailTransitModel(mailController.findMailTransit(mailTransitFilterVO, pageNumber));
	}

	/** 
	* Added by 			: U-1519 on 14-Mar-2023 Used for 	: IASCB-200804 Parameters	:	@param MailbagEnquiryFilterVO Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws RemoteException
	*/
	public MailbagModel findMailbagDetailsForMailInboundHHT(MailbagEnquiryFilterModel mailbagEnquiryFilterModel) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = mailOperationsMapper
				.mailbagEnquiryFilterModelToMailbagEnquiryFilterVO(mailbagEnquiryFilterModel);
		log.debug(MODULE + " : " + "findMailbagDetailsForMailbagEnquiryHHT" + " Entering");
		return mailOperationsMapper
				.mailbagVOToMailbagModel(mailController.findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO));
	}

	public Collection<FlightSegmentCapacitySummaryVO> findFlightListings(FlightFilterVO filterVo) {
		log.debug(MODULE + " : " + "findFlightListings" + " Entering");
		return mailController.findFlightListings(filterVo);
	}

	public Page<FlightSegmentCapacitySummaryVO> findActiveAllotments(FlightSegmentCapacityFilterVO filterVo) {
		log.debug(MODULE + " : " + "findActiveAllotments" + " Entering");
		return mailController.findActiveAllotments(filterVo);
	}

	public MailbagModel findMailConsumed(MailTransitFilterModel filterVoModel) {
		MailTransitFilterVO filterVo = mailOperationsMapper
				.mailTransitFilterModelToMailTransitFilterVO(filterVoModel);
		log.debug(MODULE + " : " + "findMailConsumed" + " Entering");
		return mailOperationsMapper.mailbagVOToMailbagModel(mailController.findMailConsumed(filterVo));
	}

	/**
	* @throws PersistenceException 
	* @author A-9998Added for IASCB-158296
	*/
	public void createPAWBForConsignment(int noOfDays) throws PersistenceException {
		log.debug(MODULE + " : " + "createPAWBForConsignment" + " Entering");
		//TODO: To correct in neo
//		((MailTrackingDefaultsBI) SpringAdapter.getInstance().getBean(MAIL_OPERATION_SERVICES))
//				.createPAWBForConsignment(noOfDays);
		log.debug(MODULE + " : " + "createPAWBForConsignment" + " Exiting");
	}
	public AWBDetailModel findAWBDetails(AWBFilterVO aWBFilterVO)
			throws MailOperationsBusinessException {
//TODO: Neo to copy the method from classic
		//return mailController.findAWBDetails(aWBFilterVO);
		return null;
	}
//TODO: Neo to copy from classic
	public void closeInboundFlight(OperationalFlightModel operationalFlightModel) {
	}
	public ConsignmentDocumentModel generateConsignmentSummaryReportDtls(ConsignmentFilterModel consignmentFilterModel)  {
		ConsignmentFilterVO consignmentFilterVO = mailOperationsMapper.consignmentFilterModelToConsignmentFilterVO(consignmentFilterModel);
		return mailOperationsMapper.consignmentDocumentVOToConsignmentDocumentModel( documentController.generateConsignmentSummaryReportDtls(consignmentFilterVO));
	}

    public TransferManifestModel generateTransferManifestReportDetails(String cmpcod,String transferManifestId) {
		log.debug(MODULE + " : " + "generateTransferManifestReportDetails" + " Entering");
		return mailOperationsMapper.transferManifestVOToTransferManifestModel(mailController.generateTransferManifestReportDetails(cmpcod,transferManifestId));
	}

    public ConsignmentDocumentModel generateConsignmentSecurityReportDtls(ConsignmentFilterModel consignmentFilterModel) {
		var consignmentFilterVO = mailOperationsMapper.consignmentFilterModelToConsignmentFilterVO(consignmentFilterModel);
        var consignmentDocumentVO = documentController.generateConsignmentSecurityReportDtls(consignmentFilterVO);

        return mailOperationsMapper.consignmentDocumentVOToConsignmentDocumentModel(consignmentDocumentVO);
    }
	public Collection<MailbagModel> generateMailTagDetails(Collection<MailbagModel>mailbagModels){
		Collection<MailbagVO> mailbagVOs = mailOperationsMapper.mailbagModelsToMailbagVO(mailbagModels);
		return mailOperationsMapper.mailbagVOsToMailbagModels (mailController.generateMailTagDetails(mailbagVOs));
}
	public ConsignmentDocumentModel generateConsignmentReportDtls(ConsignmentFilterModel consignmentFilterModel)  {
		ConsignmentFilterVO consignmentFilterVO = mailOperationsMapper.consignmentFilterModelToConsignmentFilterVO(consignmentFilterModel);
		return mailOperationsMapper.consignmentDocumentVOToConsignmentDocumentModel( documentController.generateConsignmentReportDtls(consignmentFilterVO));
	}

	public boolean validateMailSubClass(String companyCode, String subclass) {
		log.debug(MODULE + " : " + "validateMailSubClass" + " Entering");
		return mailController.validateMailSubClass(companyCode, subclass);
	}

	public void saveSecurityDetails(Collection<ConsignmentScreeningModel> consignmentScreeningModels) {
		var consignmentScreeningVOs = mailOperationsMapper
				.consignmentScreeningModelsToConsignmentScreeningVO(consignmentScreeningModels);
		mailOperationsComponent.saveSecurityDetails(consignmentScreeningVOs);
	}
	public MailbagModel fetchMailSecurityDetails(MailScreeningFilterModel mailScreeningFilterModel) {
		MailScreeningFilterVO mailScreeningFilterVO = mailOperationsMapper
				.mailScreeningFilterModelToMailScreeningFilterVO(mailScreeningFilterModel);
		log.debug(MODULE + " : " + "fetchMailSecurityDetails" + " Entering");
		return mailOperationsMapper
				.mailbagVOToMailbagModel(mailController.fetchMailSecurityDetails(mailScreeningFilterVO));
	}
	public String findRoutingDetails(String companyCode,long malseqnum) {
		log.debug(MODULE + " : " + "findRoutingDetails" + " Entering");
		return mailController.findRoutingDetails(companyCode,malseqnum);
	}
	public Collection<ConsignmentDocumentModel> generateCN46ConsignmentReportDtls(ConsignmentFilterModel consignmentFilterModel) {
		ConsignmentFilterVO consignmentFilterVO = mailOperationsMapper.consignmentFilterModelToConsignmentFilterVO(consignmentFilterModel);
		return mailOperationsMapper.consignmentDocumentVOsToConsignmentDocumentModel( documentController.generateCN46ConsignmentReportDtls(consignmentFilterVO));

	}

	public Collection<ConsignmentDocumentModel> generateCN46ConsignmentSummaryReportDtls(ConsignmentFilterModel consignmentFilterModel) {
		ConsignmentFilterVO consignmentFilterVO = mailOperationsMapper.consignmentFilterModelToConsignmentFilterVO(consignmentFilterModel);
		return mailOperationsMapper.consignmentDocumentVOsToConsignmentDocumentModel( documentController.generateCN46ConsignmentSummaryReportDtls(consignmentFilterVO));

	}

	public List<TransferManifestModel> findTransferManifestDetailsForTransfer(String companyCode, String tranferManifestId) {
		log.debug(MODULE + " : " + "findTransferManifestDetailsForTransfer" + " Entering");
		return mailOperationsMapper.transferManifestVOToTransferManifestModels(mailController.findTransferManifestDetailsForTransfer(companyCode, tranferManifestId));
	}
	public Collection<MailHandedOverModel> generateMailHandedOverRT(MailHandedOverFilterModel mailHandedOverFilterModel) {
		MailHandedOverFilterVO mailHandedOverFilterVO = mailOperationsMapper.mailHandedOverFilterModelToMailHandedOverFilterVO(mailHandedOverFilterModel);
		return mailOperationsMapper.mailHandedOverFilterVOsToMailHandedOverModel( mailController.generateMailHandedOverRT(mailHandedOverFilterVO));
	}

	  public Collection<MailStatusModel> generateMailStatusRT(MailStatusFilterModel mailStatusFilterModel) {
		MailStatusFilterVO mailStatusFilterVO = mailOperationsMapper
				.mailStatusFilterModelToMailStatusFilterVO(mailStatusFilterModel);
		log.debug(MODULE + " : " + "generateMailStatusRT" + " Entering");
		return mailOperationsMapper
				.mailStatusVOsToMailStatusModel(mailController.generateMailStatusRT(mailStatusFilterVO));
	}
	
	public Collection<DailyMailStationReportModel> generateDailyMailStationRT(DailyMailStationFilterModel filterModel) {
		DailyMailStationFilterVO filterVO = mailOperationsMapper
				.dailyMailStationFilterModelToDailyMailStationFilterVO(filterModel);
		log.debug(MODULE + " : " + "generateMailStatusRT" + " Entering");
		return mailOperationsMapper
				.dailyMailStationReportVOsToDailyMailStationReportModel(mailController.generateDailyMailStationRT(filterVO));
	}
	public Collection<DamagedMailbagModel> findDamageMailReport(DamageMailFilterModel damageMailReportFiltermodel) {
		DamageMailFilterVO damageMailReportFilterVO = mailOperationsMapper.damageMailFilterModelToDamageMailFilterVO(damageMailReportFiltermodel);
		log.debug(MODULE + " : " + "findDamageMailReport" + " Entering");
		return mailOperationsMapper
				.damagedMailbagVOsToDamagedMailbagModel(mailController.findDamageMailReport(damageMailReportFilterVO));
	}

	public MailManifestModel findImportManifestDetails(OperationalFlightModel operationalFlightModel) {
		OperationalFlightVO operationalFlightVo = mailOperationsMapper
				.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		log.debug(MODULE + " : " + "findImportManifestDetails" + " Entering");
		return mailOperationsMapper
				.mailManifestVOToMailManifestModel(mailController.findImportManifestDetails(operationalFlightVo));
	}
	public void flagResditsForAcceptance (MailAcceptanceModel  mailAcceptanceModel,boolean hasFlightDeparted,Collection<MailbagModel> acceptedMailbags,
										  Collection<ContainerDetailsModel> acceptedUlds){
		MailAcceptanceVO mailAcceptanceVO = mailOperationsMapper.mailAcceptanceModelToMailAcceptanceVO(mailAcceptanceModel);
		Collection<MailbagVO>acceptedMailbagVOS =mailOperationsMapper.mailbagModelsToMailbagVO(acceptedMailbags);
		Collection<ContainerDetailsVO>acceptedUldVos =mailOperationsMapper.containerDetailsModelsToContainerDetailsVO(acceptedUlds);
		resditController.flagResditsForAcceptance(mailAcceptanceVO,hasFlightDeparted,acceptedMailbagVOS,acceptedUldVos);

	}
	public void flagResditsForFlightDeparture (String companyCode,int carrierId,Collection<MailbagModel> mailbags,
											   Collection<ContainerDetailsModel> containerDetails,String pol){
		Collection<MailbagVO>mailbagVOs =mailOperationsMapper.mailbagModelsToMailbagVO(mailbags);
		Collection<ContainerDetailsVO>containerDetailsVOs =mailOperationsMapper.containerDetailsModelsToContainerDetailsVO(containerDetails);
		resditController.flagResditsForFlightDeparture(companyCode,carrierId,mailbagVOs,containerDetailsVOs,pol);

	}
	public  void flagResditForMailbags(Collection<MailbagModel>mailbags,String eventAirport,String eventCode){
		Collection<MailbagVO>mailbagVOs =mailOperationsMapper.mailbagModelsToMailbagVO(mailbags);
		resditController.flagResditForMailbags(mailbagVOs,eventAirport,eventCode);

	}
	public  void flagResditsForMailbagTransfer(Collection<MailbagModel>mailbags,ContainerModel containerModel){
		Collection<MailbagVO>mailbagVOs =mailOperationsMapper.mailbagModelsToMailbagVO(mailbags);
		ContainerVO containerVO = mailOperationsMapper.containerModelToContainerVO(containerModel);
		resditController.flagResditsForMailbagTransfer(mailbagVOs,containerVO);

	}
	public  void flagResditsForMailbagReassign(Collection<MailbagModel>mailbags,ContainerModel containerModel){
		Collection<MailbagVO>mailbagVOs =mailOperationsMapper.mailbagModelsToMailbagVO(mailbags);
		ContainerVO containerVO = mailOperationsMapper.containerModelToContainerVO(containerModel);
		resditController.flagResditsForMailbagReassign(mailbagVOs,containerVO);

	}
	public  void flagResditForMailbagsFromReassign(Collection<MailbagModel>mailbags,String eventAirport,String eventCode){
		Collection<MailbagVO>mailbagVOs =mailOperationsMapper.mailbagModelsToMailbagVO(mailbags);
		resditController.flagResditForMailbags(mailbagVOs,eventAirport,eventCode);

	}
	public  void flagResditsForContainerTransfer(Collection<MailbagModel>mailbags,Collection<ContainerModel> containers,OperationalFlightModel operationalFlight){
		Collection<MailbagVO>mailbagVOs =mailOperationsMapper.mailbagModelsToMailbagVO(mailbags);
		Collection<ContainerVO> containerVOs = mailOperationsMapper.containerModelsToContainerVO(containers);
		OperationalFlightVO operationalFlightVO = mailOperationsMapper.operationalFlightModelToOperationalFlightVO(operationalFlight);
		resditController.flagResditsForContainerTransfer(mailbagVOs,containerVOs,operationalFlightVO);

	}
	public  void flagResditForContainerReassign(Collection<MailbagModel>mailbags,Collection<ContainerDetailsModel> containers,OperationalFlightModel operationalFlight,boolean hasFlightDeparted){
		Collection<MailbagVO>mailbagVOs =mailOperationsMapper.mailbagModelsToMailbagVO(mailbags);
		Collection<ContainerDetailsVO> containerDetails = mailOperationsMapper.containerDetailsModelsToContainerDetailsVO(containers);
		OperationalFlightVO operationalFlightVO = mailOperationsMapper.operationalFlightModelToOperationalFlightVO(operationalFlight);
		resditController.flagResditForContainerReassign(mailbagVOs,containerDetails,operationalFlightVO,hasFlightDeparted);

	}
	public  void flagResditsForArrival(MailArrivalModel mailArrivalModel,Collection<MailbagModel>mailbags,Collection<ContainerDetailsModel> containers){
		MailArrivalVO mailArrivalVO = mailOperationsMapper.mailArrivalModelToMailArrivalVO(mailArrivalModel);
		Collection<MailbagVO>mailbagVOs =mailOperationsMapper.mailbagModelsToMailbagVO(mailbags);
		Collection<ContainerDetailsVO> containerDetails = mailOperationsMapper.containerDetailsModelsToContainerDetailsVO(containers);
		resditController.flagResditsForArrival(mailArrivalVO,mailbagVOs,containerDetails);

	}

    public byte[] findMailbagDamageImages(String companyCode, String id) {
		log.debug(MODULE + " : " + "findMailbagDamageImages" + " Entering");
		return mailController.findMailbagDamageImages(companyCode, id);

	}
	public void flagResditsForTransportCompleted (String companyCode,int carrierId,Collection<MailbagModel> mailbags,
												  Collection<ContainerDetailsModel> containerDetails,String eventPort,String flightArrivedPort){
		Collection<MailbagVO>mailbagVOs =mailOperationsMapper.mailbagModelsToMailbagVO(mailbags);
		Collection<ContainerDetailsVO>containerDetailsVOs =mailOperationsMapper.containerDetailsModelsToContainerDetailsVO(containerDetails);
		resditController.flagResditsForTransportCompleted(companyCode,carrierId,mailbagVOs,containerDetailsVOs,eventPort,flightArrivedPort);

	}
	public void flagResditsForFoundArrival(MailArrivalModel mailArrivalModel){
		MailArrivalVO mailArrivalVO =mailOperationsMapper.mailArrivalModelToMailArrivalVO(mailArrivalModel);
		resditController.flagResditsForFoundArrival(mailArrivalVO);

	}

	public void flagLostResditsForMailbags(OperationalFlightModel operationalFlightModel){
		OperationalFlightVO operationalFlightVO = mailOperationsMapper.operationalFlightModelToOperationalFlightVO(operationalFlightModel);
		resditController.flagLostResditsForMailbags(operationalFlightVO);

	}

	public void publishMailDetails(MailMasterDataFilterModel mailMasterDataFilterModel) {
		var vo = mailOperationsMapper.mailMasterDataFilterModelToMailMasterDataFilterVO(mailMasterDataFilterModel);
		mailController.publishMailbagDetails(vo);
	}
}
