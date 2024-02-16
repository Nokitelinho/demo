package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.Criterion;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.CriterionBuilder;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyCondition;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyUtils;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.MailMRAProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.WarehouseDefaultsProxy;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.exception.*;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.neoicargo.mail.vo.converter.MailOperationsVOConverter;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.ibsplc.neoicargo.mail.vo.MailConstantsVO.DESTN_FLT;

/** 
 * @author A-1303This class is not persisted and it represents the Mail acceptance Information
 */
@Slf4j
@Component
public class MailAcceptance {
	private static final String MODULE = "mail.operations";
	private static final String HYPHEN = "-";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private Map<String, String> exchangeOfficeMap;

	@Autowired
	private MailOperationsMapper mailOperationsMapper;

	@Autowired
	private MailController mailController;

	/** 
	* @author a-1936
	* @param containers
	* @return
	* @throws SystemException
	*/
	public static Collection<ContainerDetailsVO> findMailbagsInContainer(Collection<ContainerDetailsVO> containers) {
		try {
			return constructDAO().findMailbagsInContainer(containers);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/** 
	* @author a-1936 methods the DAO instance ..
	* @return
	* @throws SystemException
	*/
	private static MailOperationsDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/** 
	* This method is used to save the Acceptance Details in the System
	* @param mailAcceptanceVO
	* @param mailBagsForMonitorSLA
	* @throws SystemException
	* @throws FlightClosedException
	* @throws DuplicateMailBagsException 
	*/
	public void saveAcceptanceDetails(MailAcceptanceVO mailAcceptanceVO, Collection<MailbagVO> mailBagsForMonitorSLA)
			throws FlightClosedException, DuplicateMailBagsException {
		MailMRAProxy mailOperationsMRAProxy = ContextUtil.getInstance().getBean(MailMRAProxy.class);
		log.debug("MailAcceptance" + " : " + "saveAcceptanceDetails" + " Entering");
		boolean isAcceptanceToFlt = false;
		boolean hasFlightDeparted = false;
		if (mailAcceptanceVO.getFlightSequenceNumber() != DESTN_FLT) {
			isAcceptanceToFlt = true;
			if (!MailConstantsVO.ONLOAD_MESSAGE.equals(mailAcceptanceVO.getMailSource()) && mailAcceptanceVO.isScanned()
					&& !mailAcceptanceVO.isFromDeviationList() && !mailAcceptanceVO.isModifyAfterExportOpr()) {
				checkForClosedFlight(mailAcceptanceVO);
			}
			if (mailAcceptanceVO.getFlightStatus() == null) {
				hasFlightDeparted = checkForDepartedFlight(mailAcceptanceVO);
			} else if (MailConstantsVO.FLIGHT_STATUS_DEPARTED.equals(mailAcceptanceVO.getFlightStatus())) {
				hasFlightDeparted = true;
			}
		}
		Collection<MailbagVO> mailbagVOs = getMailBags(mailAcceptanceVO, mailAcceptanceVO.getContainerDetails());
		boolean hasUpdated = false;
		if (isAcceptanceToFlt) {
			updateAcceptedMailbags(mailbagVOs);
			hasUpdated = saveFlightAcceptanceDetails(mailAcceptanceVO);
		} else {
			hasUpdated = saveDestnAcceptanceDetails(mailAcceptanceVO);
		}
		if (hasUpdated) {
			updateContainers(mailAcceptanceVO);
			if (!mailAcceptanceVO.isInventory()) {
				log.debug("Flagging Resdits For acceptance !!!");
				flagResditsForAcceptance(mailAcceptanceVO, mailbagVOs, hasFlightDeparted);
				flagHistoryDetailsOfMailbags(mailOperationsMapper.copyMailAcceptanceVO(mailAcceptanceVO), mailbagVOs);
			}
			String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
			if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
				MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
				mailController.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);
			}
			String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
			boolean isReImported = false;
			if (Objects.nonNull(importEnabled) && !importEnabled.isEmpty() && importEnabled.contains("D")) {
				isReImported = mailController.reImportPABuiltMailbagsToMRA(mailbagVOs);
			}
			boolean provisionalRateImport = false;
			Collection<RateAuditVO> rateAuditVOs = ContextUtil.getInstance().getBean(MailController.class).createRateAuditVOs(
					mailAcceptanceVO.getContainerDetails(), MailConstantsVO.MAIL_STATUS_ACCEPTED,
					provisionalRateImport);
			log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
			if (rateAuditVOs != null && !rateAuditVOs.isEmpty() && importEnabled != null
					&& (mailAcceptanceVO.getFlightSequenceNumber() != DESTN_FLT)
					&& (importEnabled.contains("M") || importEnabled.contains("D")) && !isReImported) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
			String provisionalRateimportEnabled = findSystemParameterValue(
					MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
			if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
				provisionalRateImport = true;
				Collection<RateAuditVO> provisionalRateAuditVOs = ContextUtil.getInstance().getBean(MailController.class).createRateAuditVOs(
						mailAcceptanceVO.getContainerDetails(), MailConstantsVO.MAIL_STATUS_ACCEPTED,
						provisionalRateImport);
				log.debug("" + "RateAuditVO-->" + " " + provisionalRateAuditVOs);
				if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
					try {
						mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
					} catch (BusinessException e) {
						throw new SystemException(e.getMessage(), e.getMessage(), e);
					}
				}
			}
			if (mailAcceptanceVO.getFlightSequenceNumber() != DESTN_FLT) {
				mailAcceptanceVO.setAssignedToFlight(true);
				flagResditsForAcceptance(mailAcceptanceVO, mailbagVOs, hasFlightDeparted);
				flagHistoryDetailsOfMailbags(mailAcceptanceVO, mailbagVOs);
				if (mailAcceptanceVO.isFromDeviationList()) {
					flagArrivalHistoryForDeviationMailbags(mailAcceptanceVO);
				}
			}
		}
		log.debug("MailAcceptance" + " : " + "saveAcceptanceDetails" + " Exiting");
	}

	/** 
	* This method will stamp mailbag arrival for deviation list
	* @param mailAcceptanceVO
	* @throws SystemException
	*/
	private void flagArrivalHistoryForDeviationMailbags(MailAcceptanceVO mailAcceptanceVO) {
		if (mailAcceptanceVO != null) {
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			MailArrivalVO mailArrivalVO = new MailArrivalVO();
			MailbagVO mailbagVOForArrival = null;
			ContainerDetailsVO arrivalContainerDetailsVO = null;
			Collection<ContainerDetailsVO> arrivalContainerDetailsVOs = new ArrayList<>();
			Collection<MailbagVO> mailbagVosForArrival = new ArrayList<>();
			mailArrivalVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
			if (mailAcceptanceVO.getContainerDetails() != null) {
				for (ContainerDetailsVO containerDetailsVO : mailAcceptanceVO.getContainerDetails()) {
					arrivalContainerDetailsVO = new ContainerDetailsVO();
					arrivalContainerDetailsVO = mailOperationsMapper.copyContainerDetailsVO(containerDetailsVO);
					if (containerDetailsVO.getMailDetails() != null) {
						for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
							mailbagVOForArrival = new MailbagVO();
							mailbagVOForArrival = mailOperationsMapper.copyMailbagVO(mailbagVO);
							mailbagVOForArrival.setArrivedFlag(MailConstantsVO.FLAG_YES);
							mailbagVOForArrival.setAutoArriveMail(MailConstantsVO.FLAG_YES);
							mailbagVOForArrival.setScannedPort(containerDetailsVO.getPou());
							mailbagVosForArrival.add(mailbagVOForArrival);
						}
						arrivalContainerDetailsVO.setMailDetails(mailbagVosForArrival);
					}
					arrivalContainerDetailsVOs.add(arrivalContainerDetailsVO);
				}
				mailArrivalVO.setContainerDetails(arrivalContainerDetailsVOs);
			}
			mailController.flagMailbagAuditForArrival(mailArrivalVO);
			mailController.flagMailbagHistoryForArrival(mailOperationsMapper.copymailArrivalVO(mailArrivalVO), mailController.getTriggerPoint());
		}
	}

	/** 
	* @author a-1936 This method is used to check whether the Flight is closedfor Operations
	* @param mailAcceptanceVO
	* @return
	* @throws SystemException
	* @throws FlightClosedException
	*/
	public void checkForClosedFlight(MailAcceptanceVO mailAcceptanceVO) throws FlightClosedException {
		log.debug("MailAcceptance" + " : " + "isFlightClosedForOperations" + " Entering");
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(mailAcceptanceVO.getPol());
		assignedFlightPk.setCompanyCode(mailAcceptanceVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(mailAcceptanceVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
		assignedFlightPk.setCarrierId(mailAcceptanceVO.getCarrierId());
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.info("No Assigned Flight Found");
		}
		if (assignedFlight != null) {
			if (MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(assignedFlight.getExportClosingFlag())) {
				throw new FlightClosedException(FlightClosedException.FLIGHT_STATUS_CLOSED, new String[] {
						new StringBuilder().append(mailAcceptanceVO.getFlightCarrierCode()).append(" ")
								.append(mailAcceptanceVO.getFlightNumber()).toString(),
						mailAcceptanceVO.getFlightDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)) });
			}
		}
		log.debug("MailAcceptance" + " : " + "isFlightClosedForOperations" + " Exiting");
	}

	/** 
	* A-1739
	* @param mailAcceptanceVO
	* @return
	* @throws SystemException
	*/
	public boolean checkForDepartedFlight(MailAcceptanceVO mailAcceptanceVO) {
		FlightOperationsProxy flightOperationsProxy = ContextUtil.getInstance().getBean(FlightOperationsProxy.class);
		Collection<FlightValidationVO> flightValidationVOs = null;
		flightValidationVOs = flightOperationsProxy.validateFlightForAirport(createFlightFilterVO(mailAcceptanceVO));
		if (flightValidationVOs != null) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				if (flightValidationVO.getFlightSequenceNumber() == mailAcceptanceVO.getFlightSequenceNumber()
						&& flightValidationVO.getLegSerialNumber() == mailAcceptanceVO.getLegSerialNumber()) {
					if (flightValidationVO.getAtd() != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/** 
	* A-1739
	* @param mailAcceptanceVO
	* @return
	*/
	private FlightFilterVO createFlightFilterVO(MailAcceptanceVO mailAcceptanceVO) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(mailAcceptanceVO.getCarrierId());
		flightFilterVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
		flightFilterVO.setStation(mailAcceptanceVO.getPol());
		flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
		return flightFilterVO;
	}

	/** 
	* @author A-1739
	* @param mailAcceptanceVO
	* @return
	* @throws SystemException
	*/
	private boolean saveFlightAcceptanceDetails(MailAcceptanceVO mailAcceptanceVO) {
		log.debug("MailAcceptance" + " : " + "saveFlightAcceptanceDetails" + " Entering");
		Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> uldSegmentMap = groupULDForSegments(
				mailAcceptanceVO.getContainerDetails());
		boolean hasUpdated = true;
		boolean isUpdateSuccessfull = false;
		AssignedFlightSegment assignedFlightSegment = null;
		for (Map.Entry<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> flightSegULDMap : uldSegmentMap
				.entrySet()) {
			try {
				assignedFlightSegment = AssignedFlightSegment.find(flightSegULDMap.getKey());
			} catch (FinderException ex) {
				throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
			}
			isUpdateSuccessfull = assignedFlightSegment.saveFlightAcceptanceDetails(mailAcceptanceVO,
					flightSegULDMap.getValue());
			if (isUpdateSuccessfull) {
				hasUpdated = true;
			}
		}
		log.debug("MailAcceptance" + " : " + "saveFlightAcceptanceDetails" + " Exiting");
		return hasUpdated;
	}

	/** 
	* A-1739
	* @param mailAcceptanceVO
	* @throws SystemException
	* @throws DuplicateMailBagsException 
	*/
	private boolean saveDestnAcceptanceDetails(MailAcceptanceVO mailAcceptanceVO) throws DuplicateMailBagsException {
		log.debug("MailAcceptance" + " : " + "saveDestnAcceptanceDetails" + " Entering");
		Collection<ContainerDetailsVO> containers = mailAcceptanceVO.getContainerDetails();
		boolean hasUpdated = false;
		for (ContainerDetailsVO containerDetailsVO : containers) {
			if (ContainerDetailsVO.OPERATION_FLAG_INSERT.equals(containerDetailsVO.getOperationFlag())) {
				insertContainerDestAcceptanceDtls(mailAcceptanceVO, containerDetailsVO);
				hasUpdated = true;
			} else if (ContainerDetailsVO.OPERATION_FLAG_UPDATE.equals(containerDetailsVO.getOperationFlag())) {
				updateContainerDestAcceptanceDtls(mailAcceptanceVO, containerDetailsVO);
				hasUpdated = true;
			}
		}
		log.debug("MailAcceptance" + " : " + "saveDestnAcceptanceDetails" + " Exiting");
		return hasUpdated;
	}

	/** 
	* @author A-1739
	* @param mailAcceptanceVO
	* @throws SystemException
	*/
	private void updateContainers(MailAcceptanceVO mailAcceptanceVO) {
		Collection<ContainerDetailsVO> containerDetails = mailAcceptanceVO.getContainerDetails();
		for (ContainerDetailsVO containerDetailsVO : containerDetails) {
			if (ContainerDetailsVO.OPERATION_FLAG_INSERT.equals(containerDetailsVO.getOperationFlag())) {
				ContainerPK containerPK = new ContainerPK();
				containerPK.setCompanyCode(mailAcceptanceVO.getCompanyCode());
				containerPK.setCarrierId(mailAcceptanceVO.getCarrierId());
				containerPK.setFlightNumber(mailAcceptanceVO.getFlightNumber());
				containerPK.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
				containerPK.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
				containerPK.setAssignmentPort(containerDetailsVO.getPol());
				containerPK.setContainerNumber(containerDetailsVO.getContainerNumber());
				Container container = null;
				try {
					container = Container.find(containerPK);
				} catch (FinderException ex) {
					throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
				}
				container.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
				if(Objects.nonNull(containerDetailsVO.getLastUpdateTime())){
					container.setLastUpdatedTime(Timestamp.valueOf(containerDetailsVO.getLastUpdateTime().toLocalDateTime()));
				}
			}
		}
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.auditContainer(mailAcceptanceVO);
	}

	/** 
	* @author A-1739
	* @param containers
	* @return
	*/
	private Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> groupULDForSegments(
			Collection<ContainerDetailsVO> containers) {
		log.debug("MailAcceptance" + " : " + "groupULDForSegments" + " Entering");
		Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> uldSegmentMap = new HashMap<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>>();
		Collection<ContainerDetailsVO> segmentContainers = null;
		for (ContainerDetailsVO containerDetailsVO : containers) {
			if (containerDetailsVO.getOperationFlag() != null) {
				AssignedFlightSegmentPK assignedFlightSegPK = constructAssignedFlightSegPK(containerDetailsVO);
				segmentContainers = uldSegmentMap.get(assignedFlightSegPK);
				if (segmentContainers == null) {
					segmentContainers = new ArrayList<ContainerDetailsVO>();
					uldSegmentMap.put(assignedFlightSegPK, segmentContainers);
				}
				segmentContainers.add(containerDetailsVO);
				log.debug("" + "containerDetailsVO" + " " + containerDetailsVO);
			}
		}
		log.debug("MailAcceptance" + " : " + "groupULDForSegments" + " Exiting");
		return uldSegmentMap;
	}

	/** 
	* A-1739
	* @param mailAcceptanceVO
	* @param containerDetailsVO
	* @throws SystemException
	* @throws DuplicateMailBagsException 
	*/
	private void insertContainerDestAcceptanceDtls(MailAcceptanceVO mailAcceptanceVO,
			ContainerDetailsVO containerDetailsVO) throws DuplicateMailBagsException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug("MailAcceptance" + " : " + "insertContainerDestAcceptanceDtls" + " Entering");
		ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
		uldAtAirportVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
		uldAtAirportVO.setCarrierId(mailAcceptanceVO.getCarrierId());
		uldAtAirportVO.setAirportCode(mailAcceptanceVO.getPol());
		uldAtAirportVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
		uldAtAirportVO.setLastUpdateUser(mailAcceptanceVO.getAcceptedUser());
		if (!mailAcceptanceVO.isInventory()) {
			containerDetailsVO.setMailUpdateFlag(true);
		}
		int bags = 0;
		double weight = 0;
		if (containerDetailsVO.getMailDetails() != null && containerDetailsVO.getMailDetails().size() > 0) {
			for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
				bags = bags + 1;
				if (mailbagVO.getWeight() != null) {
					weight = weight + mailbagVO.getWeight().getValue().doubleValue();
				}
			}
		}
		uldAtAirportVO.setNumberOfBags(bags);
		uldAtAirportVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(weight)));
		ULDAtAirport uldAtAirport = null;
		boolean isBulkContainer = false;
		if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())) {
			uldAtAirportVO.setUldNumber(containerDetailsVO.getContainerNumber());
			uldAtAirportVO.setRemarks(containerDetailsVO.getRemarks());
			uldAtAirportVO.setFinalDestination(containerDetailsVO.getDestination());
			uldAtAirportVO.setWarehouseCode(containerDetailsVO.getWareHouse());
			uldAtAirportVO.setLocationCode(containerDetailsVO.getLocation());
			uldAtAirportVO.setTransferFromCarrier(containerDetailsVO.getTransferFromCarrier());
			uldAtAirport = new ULDAtAirport(uldAtAirportVO);
		} else if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) {
			isBulkContainer = true;
			uldAtAirportVO.setUldNumber(
					constructBulkULDNumber(containerDetailsVO.getDestination(), containerDetailsVO.getCarrierCode()));
			log.debug("" + "uldatarp --> " + " " + uldAtAirportVO);
			try {
				uldAtAirport = ULDAtAirport.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));
			} catch (FinderException finderException) {
				uldAtAirportVO.setFinalDestination(containerDetailsVO.getDestination());
				uldAtAirport = new ULDAtAirport(uldAtAirportVO);
			}
		}
		if (uldAtAirport != null) {
			uldAtAirport.insertMailBagInULDAtArpAcceptanceDtls(containerDetailsVO,
					mailAcceptanceVO.isInventoryForArrival());
		}
		log.debug("MailAcceptance" + " : " + "insertContainerDestAcceptanceDtls" + " Exiting");
	}

	/** 
	* @author A-1739
	* @param mailAcceptanceVO
	* @param containerDetailsVO
	* @throws SystemException
	* @throws DuplicateMailBagsException 
	*/
	private void updateContainerDestAcceptanceDtls(MailAcceptanceVO mailAcceptanceVO,
			ContainerDetailsVO containerDetailsVO) throws DuplicateMailBagsException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
		uldAtAirportVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
		uldAtAirportVO.setCarrierId(mailAcceptanceVO.getCarrierId());
		uldAtAirportVO.setAirportCode(mailAcceptanceVO.getPol());
		uldAtAirportVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
		uldAtAirportVO.setLastUpdateUser(mailAcceptanceVO.getAcceptedUser());
		if (!mailAcceptanceVO.isInventory()) {
			containerDetailsVO.setMailUpdateFlag(true);
		}
		boolean isScanned = mailAcceptanceVO.isScanned();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
		if (containerDetailsVO.getMailDetails() != null) {
			mailbagVOs.addAll(containerDetailsVO.getMailDetails());
		}
		if (despatchDetailsVOs != null) {
			for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
				mailbagVOs.add(MailOperationsVOConverter.convertToMailBagVO(despatchDetailsVO));
			}
		}
		ULDAtAirport uldAtAirport = null;
		boolean isBulkContainer = false;
		if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())) {
			uldAtAirportVO.setUldNumber(containerDetailsVO.getContainerNumber());
			try {
				uldAtAirport = ULDAtAirport.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));
				uldAtAirport.setRemarks(containerDetailsVO.getRemarks());
				uldAtAirport.setWarehouseCode(containerDetailsVO.getWareHouse());
				uldAtAirport.setLocationCode(containerDetailsVO.getLocation());
				uldAtAirport.setLastUpdatedUser(mailAcceptanceVO.getAcceptedUser());
				uldAtAirport.setTransferFromCarrier(containerDetailsVO.getTransferFromCarrier());
				if(Objects.nonNull(containerDetailsVO.getUldLastUpdateTime())) {
					uldAtAirport.setLastUpdatedTime(Timestamp.valueOf(containerDetailsVO.getUldLastUpdateTime().toLocalDateTime()));
				}
			} catch (FinderException exception) {
				uldAtAirport = new ULDAtAirport(uldAtAirportVO);
			}
		} else if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) {
			isBulkContainer = true;
			uldAtAirportVO.setUldNumber(
					constructBulkULDNumber(containerDetailsVO.getDestination(), containerDetailsVO.getCarrierCode()));
			try {
				uldAtAirport = ULDAtAirport.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));
				uldAtAirport.setLastUpdatedUser(mailAcceptanceVO.getAcceptedUser());
				//TODO:TO verify in Neo
				//uldAtAirport.setLastUpdatedTime(localDateUtil.getLocalDate(null, true).toLocalDateTime());
			} catch (FinderException finderException) {
				uldAtAirport = new ULDAtAirport(uldAtAirportVO);
			}
		}
		new ULDAtAirport().insertMailBagInULDAtArpAcceptanceDtls(containerDetailsVO,
				mailAcceptanceVO.isInventoryForArrival());
	}

	/** 
	* A-1739
	* @param mailbagVO
	* @param mailAcceptanceVO
	* @param containerDetailsVO
	* @return
	*/
	private Collection<MailbagHistoryVO> constructMailbagHistories(MailbagVO mailbagVO,
			MailAcceptanceVO mailAcceptanceVO, ContainerDetailsVO containerDetailsVO) {
		log.debug("MailAcceptance" + " : " + "constructMailbagHistories" + " Entering");
		MailbagHistoryVO historyVO = new MailbagHistoryVO();
		historyVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		historyVO.setCarrierId(mailAcceptanceVO.getCarrierId());
		historyVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
		historyVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
		historyVO.setFlightDate(mailAcceptanceVO.getFlightDate());
		historyVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
		if (historyVO.getCarrierCode() == null) {
			historyVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
		}
		historyVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		historyVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		historyVO.setContainerType(containerDetailsVO.getContainerType());
		historyVO.setPou(containerDetailsVO.getPou());
		historyVO.setScannedPort(mailbagVO.getScannedPort());
		historyVO.setScanDate(mailbagVO.getScannedDate());
		historyVO.setScanUser(mailbagVO.getScannedUser());
		historyVO.setMailClass(mailbagVO.getMailClass());
		historyVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
		historyVO.setMailSource(mailbagVO.getMailSource());
		historyVO.setMessageVersion(mailbagVO.getMessageVersion());
		Collection<MailbagHistoryVO> histories = new ArrayList<MailbagHistoryVO>();
		histories.add(historyVO);
		log.debug("MailAcceptance" + " : " + "constructMailbagHistories" + " Exiting");
		return histories;
	}

	/** 
	* A-1739
	* @param containerDetailsVO
	* @return
	*/
	private AssignedFlightSegmentPK constructAssignedFlightSegPK(ContainerDetailsVO containerDetailsVO) {
		AssignedFlightSegmentPK assignedFlightSegPK = new AssignedFlightSegmentPK();
		assignedFlightSegPK.setCompanyCode(containerDetailsVO.getCompanyCode());
		assignedFlightSegPK.setCarrierId(containerDetailsVO.getCarrierId());
		assignedFlightSegPK.setFlightNumber(containerDetailsVO.getFlightNumber());
		assignedFlightSegPK.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		assignedFlightSegPK.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		return assignedFlightSegPK;
	}

	private String constructBulkULDNumber(String airport, String carrierCode) {
		if (airport != null && airport.trim().length() > 0) {
			return new StringBuilder().append(MailConstantsVO.CONST_BULK).append(MailConstantsVO.SEPARATOR)
					.append(airport).toString();
		} else {
			return MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR).concat(carrierCode);
		}
	}

	/** 
	* @author A-1739
	* @param uldAtAirportVO
	* @return
	*/
	private ULDAtAirportPK constructULDArpPKFromULDArpVO(ULDAtAirportVO uldAtAirportVO) {
		ULDAtAirportPK uldArpPK = new ULDAtAirportPK();
		uldArpPK.setCompanyCode(uldAtAirportVO.getCompanyCode());
		uldArpPK.setCarrierId(uldAtAirportVO.getCarrierId());
		uldArpPK.setAirportCode(uldAtAirportVO.getAirportCode());
		uldArpPK.setUldNumber(uldAtAirportVO.getUldNumber());
		return uldArpPK;
	}

	/** 
	* @author A-1739
	* @param mailAcceptanceVO
	* @param hasFlightDeparted
	* @throws SystemException
	*/
	public void flagResditsForAcceptance(MailAcceptanceVO mailAcceptanceVO, Collection<MailbagVO> acceptedMailbags,
			boolean hasFlightDeparted) {
		ContextUtil contextUtil = ContextUtil.getInstance();
		log.debug("MailAcceptance" + " : " + "flagResditsForAcceptance" + " Exiting");
		Collection<ContainerDetailsVO> acceptedUlds = null;
		if (mailAcceptanceVO.getContainerDetails() != null && mailAcceptanceVO.getContainerDetails().size() > 0) {
			acceptedUlds = new ArrayList<ContainerDetailsVO>();
			for (ContainerDetailsVO containerDetailsVO : mailAcceptanceVO.getContainerDetails()) {
				if (ContainerDetailsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())
						&& MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())) {
					acceptedUlds.add(containerDetailsVO);
				}
			}
		}
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagResditsForAcceptance(mailAcceptanceVO, hasFlightDeparted, acceptedMailbags, acceptedUlds);
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		if (mailAcceptanceVO.isFromDeviationList() && mailAcceptanceVO.isAssignedToFlight()) {
			for (MailbagVO mailbagVO : acceptedMailbags) {
				if (mailbagVO.getLastUpdateUser() == null) {
					mailbagVO.setLastUpdateUser(logonAttributes.getUserId());
				}
				Collection<FlightValidationVO> flightVOs = null;
				FlightFilterVO flightFilterVO = new FlightFilterVO();
				flightFilterVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
				flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
				flightFilterVO.setStation(mailAcceptanceVO.getPol());
				flightFilterVO.setPageNumber(1);
				flightFilterVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
				flightFilterVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
				flightVOs = mailController.validateFlight(flightFilterVO);
				if (flightVOs != null && !flightVOs.isEmpty() && mailbagVO != null) {
					mailController.flagHistoryforFlightArrival(mailbagVO, flightVOs, mailController.getTriggerPoint());
					mailController.flagAuditforFlightArrival(mailbagVO, flightVOs);
				}
			}
		}
	}

	/** 
	* @author A-1739
	* @param mailbagVOs
	* @throws SystemException
	* @throws DuplicateMailBagsException 
	*/
	public void updateAcceptedMailbags(Collection<MailbagVO> mailbagVOs) throws DuplicateMailBagsException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug("DSN" + " : " + "addMailbags" + " Entering");
		for (MailbagVO mailbagVO : mailbagVOs) {
			mailbagVO = ContextUtil.getInstance().getBean(MailController.class)
					.constructOriginDestinationDetails(mailbagVO);
			Mailbag mailbag = null;
			try {
				mailbag = findMailbag(constructMailbagPK(mailbagVO));
			} catch (FinderException e) {
			}
			boolean isDuplicate = false;
			if (mailbag != null) {
				if (!MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())
						&& !mailbagVO.isFromDeviationList() && !"CARDIT".equals(mailbagVO.getFromPanel())) {
					isDuplicate = mailController.checkForDuplicateMailbag(mailbagVO.getCompanyCode(),
							mailbagVO.getPaCode(), mailbag);
				}
				if (mailbagVO.getFlightNumber() != null && mailbagVO.getMailbagId() != null
						&& (mailbag.getMailIdr().equals(mailbagVO.getMailbagId()))
						&& !mailbagVO.getFlightNumber().equals(mailbag.getFlightNumber())) {
					mailbagVO.setMailUpdateFlag(true);
				} else if (mailbagVO.getFlightNumber().equals(mailbag.getFlightNumber())
						&& (MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())
								|| MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailbag.getLatestStatus()))) {
					mailbagVO.setMailUpdateFlag(true);
				}
				if (!isDuplicate) {
					if (mailbag != null ) {
						mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
					}
					mailbag.updateAcceptanceFlightDetails(mailbagVO);
				}
				mailbag.updateAcceptanceDamage(mailbagVO);
				if(Objects.nonNull(mailbagVO.getLastUpdateTime())) {
					mailbag.setLastUpdatedTime(Timestamp.valueOf(mailbagVO.getLastUpdateTime().toLocalDateTime()));
				}
				mailbag.setMailRemarks(mailbagVO.getMailRemarks());
				mailbag.setMailbagSource(mailbag.getMailbagSource());
				mailbag.setPaCode(mailbagVO.getPaCode());
				mailbag.updatePrimaryAcceptanceDetails(mailbagVO);
			}
			if (mailbag == null || isDuplicate) {
				String paCode = findSystemParameterValue(USPS_DOMESTIC_PA);
				if (mailbagVO.getMailbagId().length() == 12
						|| mailbagVO.getMailbagId().length() == 10 && paCode.equals(mailbagVO.getPaCode())) {
					String routIndex = mailbagVO.getMailbagId().substring(4, 8);
					String org = null;
					String dest = null;
					Collection<RoutingIndexVO> routingIndexVOs = new ArrayList<RoutingIndexVO>();
					RoutingIndexVO routingIndexFilterVO = new RoutingIndexVO();
					routingIndexFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
					routingIndexFilterVO.setRoutingIndex(routIndex);
					routingIndexFilterVO.setScannedDate(mailbagVO.getScannedDate());
					routingIndexVOs = findRoutingIndex(routingIndexFilterVO);
					exchangeOfficeMap = new HashMap<String, String>();
					for (RoutingIndexVO routingIndexVO : routingIndexVOs) {
						if (routingIndexVO != null && routingIndexVO.getRoutingIndex() != null) {
							org = routingIndexVO.getOrigin();
							dest = routingIndexVO.getDestination();
							exchangeOfficeMap = findOfficeOfExchangeForPA(mailbagVO.getCompanyCode(),
									findSystemParameterValue(USPS_DOMESTIC_PA));
							if (exchangeOfficeMap != null && !exchangeOfficeMap.isEmpty()) {
								if (exchangeOfficeMap.containsKey(org)) {
									mailbagVO.setOoe(exchangeOfficeMap.get(org));
								}
								if (exchangeOfficeMap.containsKey(dest)) {
									mailbagVO.setDoe(exchangeOfficeMap.get(dest));
								}
							}
							mailbagVO.setMailCategoryCode("B");
							String mailClass = mailbagVO.getMailbagId().substring(3, 4);
							mailbagVO.setMailClass(mailClass);
							mailbagVO.setMailSubclass(mailClass + "X");
							mailbagVO.setOrigin(org);
							mailbagVO.setDestination(dest);

							int lastTwoDigits = Year.now().getValue() % 100;
							String lastDigitOfYear = String.valueOf(lastTwoDigits).substring(1, 2);
							mailbagVO.setYear(Integer.parseInt(lastDigitOfYear));
							mailbagVO.setHighestNumberedReceptacle("9");
							mailbagVO.setRegisteredOrInsuredIndicator("9");
							mailbagVO.setDespatchSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_DSNVAL);
							mailbagVO.setReceptacleSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_RSNVAL);
							mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal
									.valueOf(Double.parseDouble(mailbagVO.getMailbagId().substring(10, 12)))));
							mailbagVO.setAcceptedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal
									.valueOf(Double.parseDouble(mailbagVO.getMailbagId().substring(10, 12)))));
							mailbagVO.setStrWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal
									.valueOf(Double.parseDouble(mailbagVO.getMailbagId().substring(10, 12)))));
						}
					}
				}
				if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
					mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());
					String scanWaved = constructDAO().checkScanningWavedDest(mailbagVO);
					if (scanWaved != null) {
						mailbagVO.setScanningWavedFlag(scanWaved);
					}
					if (mailController.isUSPSMailbag(mailbagVO)) {
						mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
					} else {
						mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
					}
					ContextUtil.getInstance().getBean(MailController.class).calculateAndUpdateLatestAcceptanceTime(mailbagVO);
					MailAcceptance.populatePrimaryAcceptanceDetails(mailbagVO);
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					String actionCode = Objects.isNull(mailbagVO.getFlightNumber()) ||
							StringUtils.equalsIgnoreCase(mailbagVO.getFlightNumber(), MailConstantsVO.DESTN_FLT_STR)?
					MailbagAuditVO.MAILBAG_ACCEPTANCE:MailbagAuditVO.MAILBAG_ASSIGNED;
					mailController.auditMailDetailUpdates(mailbagVO,actionCode,"Insert",
							mailController.getAdditionalInfoForAssignOrAcceptance(mailbagVO,actionCode));
					Mailbag mailbagForAdd = new Mailbag(mailbagVO);

					if (mailbagForAdd != null ) {
						mailbagVO.setMailSequenceNumber(mailbagForAdd.getMailSequenceNumber());


					}
				} else {
					throw new SystemException("NO MAIL BAG FOUND");
				}
			}
		}
		log.debug("DSN" + " : " + "addMailbags" + " Exiting");
	}

	/** 
	* @author A-5991
	* @param mailbagVO
	* @return
	* @throws SystemException
	*/
	private MailbagPK constructMailbagPK(MailbagVO mailbagVO) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
		if (mailbagVO.getMailSequenceNumber() > 0) {
			mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		} else {
			mailbagPK.setMailSequenceNumber(
					findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
		}
		return mailbagPK;
	}

	/** 
	* @author A-5991
	* @param mailBagId
	* @param companyCode
	* @return
	* @throws SystemException
	*/
	public long findMailSequenceNumber(String mailBagId, String companyCode) {
		return constructDAO().findMailSequenceNumber(mailBagId, companyCode);
	}

	private Mailbag findMailbag(MailbagPK mailbagPK) throws FinderException {
		return Mailbag.find(mailbagPK);
	}

	/** 
	* Utilty for finding syspar Mar 23, 2007, A-1739
	* @param syspar
	* @return
	* @throws SystemException
	*/
	private String findSystemParameterValue(String syspar)  {
		NeoMastersServiceUtils neoMastersServiceUtils =
				ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class);
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		}  catch (BusinessException e) {
			return null;
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/** 
	* @author A-5991
	* @param containerDetailsVOs
	* @return
	* @throws SystemException 
	*/
	public Collection<MailbagVO> getMailBags(MailAcceptanceVO mailAcceptanceVO,
			Collection<ContainerDetailsVO> containerDetailsVOs) {
		Collection<MailbagVO> mailbagVOs = null;
		Collection<DespatchDetailsVO> despatchDetailsVOs = null;
		if (containerDetailsVOs != null) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				if (mailbagVOs == null) {
					mailbagVOs = new ArrayList<MailbagVO>();
				}
				if (containerDetailsVO.getMailDetails() != null) {
					for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
						if (!(("I".equals(mailbagVO.getOperationalFlag()))
								|| "U".equals(mailbagVO.getOperationalFlag()))
								|| (MailbagVO.FLAG_YES.equals(mailbagVO.getTransferFlag())
										&& mailbagVO.isFromDeviationList())) {
							continue;
						}
						mailbagVO.setMailbagHistories(
								constructMailbagHistories(mailbagVO, mailAcceptanceVO, containerDetailsVO));
						mailbagVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
						if (mailbagVO.getPou() == null || mailbagVO.getPou().isEmpty()) {
							mailbagVO.setPou(containerDetailsVO.getDestination());
						}
						if (mailAcceptanceVO.getCompanyCode() != null) {
							mailbagVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
						}
						if (mailAcceptanceVO.getShowWarning() != null) {
							mailbagVO.setLatValidationNeeded(mailAcceptanceVO.getShowWarning());
						}
						if (!mailAcceptanceVO.isInventory()) {
							mailbagVO.setMailUpdateFlag(true);
						}
						String paCode = null;
						String companyCode = mailbagVO.getCompanyCode();
						String originOfFiceExchange = mailbagVO.getOoe();
						if (mailbagVO.getPaCode() == null) {
							try {
								paCode = mailController.findPAForOfficeOfExchange(companyCode,
										originOfFiceExchange);
							} finally {
							}
							mailbagVO.setPaCode(paCode);
						}
						String scanWavedAirport = constructDAO().checkScanningWavedDest(mailbagVO);
						if (scanWavedAirport != null) {
							mailbagVO.setScanningWavedFlag(scanWavedAirport);
						}
						if (mailAcceptanceVO.isFromDeviationList()) {
							mailbagVO.setFromDeviationList(true);
						}
						mailbagVO.setMailSource(mailAcceptanceVO.getMailSource());
						mailbagVO.setMailbagDataSource(mailAcceptanceVO.getMailDataSource());
						mailbagVO.setMessageVersion(mailAcceptanceVO.getMessageVersion());
						mailbagVOs.add(mailbagVO);
					}
				}
				if (containerDetailsVO.getDesptachDetailsVOs() != null) {
					for (DespatchDetailsVO despatchDetailsVO : containerDetailsVO.getDesptachDetailsVOs()) {
						MailbagVO mailbagVO = MailOperationsVOConverter.convertToMailBagVO(despatchDetailsVO);
						mailbagVO.setMailbagHistories(
								constructMailbagHistories(mailbagVO, mailAcceptanceVO, containerDetailsVO));
						mailbagVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
						mailbagVO.setPou(containerDetailsVO.getDestination());
						mailbagVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
						if (mailAcceptanceVO.getCompanyCode() != null) {
							mailbagVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
						}
						mailbagVOs.add(mailbagVO);
					}
				}
			}
		}
		return mailbagVOs;
	}

	/** 
	* @author A-1936This method is used to
	* @param mailAcceptanceVO
	* @throws ContainerAssignmentException
	* @throws FlightClosedException
	* @throws InvalidFlightSegmentException
	* @throws SystemException
	* @throws ULDDefaultsProxyException
	* @throws CapacityBookingProxyException
	* @throws MailBookingException
	* @throws DuplicateMailBagsException 
	*/
	public void saveInventoryDetailsForArrival(MailAcceptanceVO mailAcceptanceVO)
			throws ContainerAssignmentException, FlightClosedException, InvalidFlightSegmentException,
			ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException,
			MailDefaultStorageUnitException, DuplicateMailBagsException {
		log.debug("MailAcceptance" + " : " + "saveInventoryDetailsForArrival" + " Entering");
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.saveAcceptedContainers(mailAcceptanceVO);
		saveAirportDetailsForArrival(mailAcceptanceVO);
		log.debug("MailAcceptance" + " : " + "saveInventoryDetailsForArrival" + " Exiting");
	}

	/** 
	* @author A-2553This method is used to  save arrival details to airport
	* @param mailAcceptanceVO
	* @throws DuplicateMailBagsException 
	*/
	private void saveAirportDetailsForArrival(MailAcceptanceVO mailAcceptanceVO) throws DuplicateMailBagsException {
		log.debug("MailAcceptance" + " : " + "saveAirportDetailsForArrival" + " Entering");
		boolean isAcceptanceToFlt = false;
		Collection<MailbagVO> mailBagsForMonitorSLA = null;
		boolean hasUpdated = false;
		hasUpdated = saveDestnAcceptanceDetails(mailAcceptanceVO);
		if (hasUpdated) {
			log.info("" + "THE HAS UPDATED IN THE MAILACCEPTANCE" + " " + hasUpdated);
			updateContainers(mailAcceptanceVO);
		}
		log.debug("MailAcceptance" + " : " + "saveAirportDetailsForArrival" + " Exiting");
	}

	public void flagHistoryDetailsOfMailbags(MailAcceptanceVO mailAcceptanceVO, Collection<MailbagVO> mailbagVOs) {
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.insertOrUpdateHistoryDetailsForAcceptance(mailAcceptanceVO, mailbagVOs);
		//mailController.insertOrUpdateAuditDetailsForAcceptance(mailAcceptanceVO, mailbagVOs);
	}

	public static Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO) {
		Collection<RoutingIndexVO> routingIndexVOs = null;
		try {
			routingIndexVOs = constructDAO().findRoutingIndex(routingIndexVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		return routingIndexVOs;
	}

	public Map<String, String> findOfficeOfExchangeForPA(String companyCode, String paCode) {
		log.debug(MODULE + " : " + "findOfficeOfExchangeForAirports" + " Entering");
		return ContextUtil.getInstance().getBean(MailController.class).findOfficeOfExchangeForPA(companyCode, paCode);
	}

	/** 
	* Method		:	MailAcceptance.populatePrimaryAcceptanceDetails Added by 	:	A-6245 on 26-Feb-2021 Used for 	:	IASCB-96538 Parameters	:	@param scannedMailDetailsVO Parameters	:	@param mailbagVO Return type	: 	void
	*/
	public static void populatePrimaryAcceptanceDetails(MailbagVO mailbagVO) {
		if ((MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getLatestStatus())
				|| MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagVO.getLatestStatus()))) {
			mailbagVO.setAcceptanceAirportCode(mailbagVO.getScannedPort());
			mailbagVO.setAcceptanceScanDate(mailbagVO.getScannedDate());
			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getPaBuiltFlag())) {
				mailbagVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
				mailbagVO.setAcceptancePostalContainerNumber(mailbagVO.getContainerNumber());
			} else {
				mailbagVO.setPaBuiltFlag(null);
			}
		}
	}

	public static Collection<ContainerDetailsVO> findMailbagsInContainerWithoutAcceptance(
			Collection<ContainerDetailsVO> containers) {
		try {
			return constructDAO().findMailbagsInContainerWithoutAcceptance(containers);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}
	/**
	 * @author A-1936 This method is used to find the MailBags Tat has beenaccepted to the Flight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MailbagVO> findMailBagsForUpliftedResdit(OperationalFlightVO operationalFlightVO) {
		Collection<MailbagVO> mailbags = null;
		try {
			mailbags = constructDAO().findMailBagsForUpliftedResdit(operationalFlightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		return mailbags;
	}

	/**
	 * @author A-1876 This method is used to find the ULDs That has beenaccepted to the Flight
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ContainerDetailsVO> findUldsForUpliftedResdit(OperationalFlightVO operationalFlightVO) {
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		try {
			containerDetailsVOs = constructDAO().findUldsForUpliftedResdit(operationalFlightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		return containerDetailsVOs;
	}
	/**
	 * @author A-1936
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param transactionCodes
	 * @return
	 * @throws SystemException
	 */
	public static Map<String, Collection<String>> findWarehouseTransactionLocations(String companyCode,
																					String airportCode, String warehouseCode, Collection<String> transactionCodes) {
		WarehouseDefaultsProxy warehouseDefaultsProxy = ContextUtil.getInstance().getBean(WarehouseDefaultsProxy.class);
		return warehouseDefaultsProxy.findWarehouseTransactionLocations(companyCode, airportCode, warehouseCode,
				transactionCodes);
	}

	/**
	 * @author a-1936 This method is used to find all the WareHouses for theGiven Airport
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	public static Collection<WarehouseVO> findAllWarehouses(String companyCode, String airportCode) {
		WarehouseDefaultsProxy warehouseDefaultsProxy = ContextUtil.getInstance().getBean(WarehouseDefaultsProxy.class);
		return warehouseDefaultsProxy.findAllWarehouses(companyCode, airportCode);
	}

	/**
	 * @author a-1936 This method is used to validate the Location
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param locationCode
	 * @return
	 * @throws SystemException
	 */
	public static LocationValidationVO validateLocation(String companyCode, String airportCode, String warehouseCode,
														String locationCode) {

		WarehouseDefaultsProxy warehouseDefaultsProxy = ContextUtil.getInstance().getBean(WarehouseDefaultsProxy.class);
		return warehouseDefaultsProxy.validateLocation(companyCode, airportCode, warehouseCode, locationCode);

	}

	/**
	 * @author A-1936 This method is used to find the Offload details
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static OffloadVO findOffloadDetails(OffloadFilterVO offloadFilterVO) {
		OffloadVO offloadVo = null;
		if (MailConstantsVO.OFFLOAD_CONTAINER.equals(offloadFilterVO.getOffloadType())) {
			offloadVo = findAcceptedContainersForOffload(offloadFilterVO);
		} else if (MailConstantsVO.OFFLOAD_DSN.equals(offloadFilterVO.getOffloadType())) {
			offloadVo = findAcceptedDespatchesForOffload(offloadFilterVO);
		} else {
			offloadVo = findAcceptedMailBagsForOffload(offloadFilterVO);
		}
		return offloadVo;
	}

	/**
	 * @author A-1936 This method is used to findAcceptedContainersForOffload
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 */
	private static OffloadVO findAcceptedContainersForOffload(OffloadFilterVO offloadFilterVO) {
		Collection<ContainerVO> containerVos = null;
		OffloadVO offloadVo = null;
		try {
			containerVos = constructDAO().findAcceptedContainersForOffload(offloadFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		if (containerVos != null && containerVos.size() > 0) {
			offloadVo = constructOffloadVO(offloadFilterVO);
			offloadVo.setOffloadContainers(containerVos);
		}
		return offloadVo;
	}

	/**
	 * @author A-1936 This method is used to findAcceptedDespatchesForOffload
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 */
	private static OffloadVO findAcceptedDespatchesForOffload(OffloadFilterVO offloadFilterVO) {
		Page<DespatchDetailsVO> despatchDetailsVOs = null;
		OffloadVO offloadVo = null;
		try {
			despatchDetailsVOs = constructDAO().findAcceptedDespatchesForOffload(offloadFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
			offloadVo = constructOffloadVO(offloadFilterVO);
			offloadVo.setOffloadDSNs(despatchDetailsVOs);
		}
		return offloadVo;
	}

	/**
	 * @author A-1936 This method is used to findAcceptedMailBagsForOffload
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 */
	private static OffloadVO findAcceptedMailBagsForOffload(OffloadFilterVO offloadFilterVO) {
		Page<MailbagVO> mailbagVOs = null;
		OffloadVO offloadVo = null;
		try {
			mailbagVOs = constructDAO().findAcceptedMailBagsForOffload(offloadFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			offloadVo = constructOffloadVO(offloadFilterVO);
			offloadVo.setOffloadMailbags(mailbagVOs);
		}
		return offloadVo;
	}

	/**
	 * @author A-1936 This method is used to construct the OffloadVO
	 * @param offloadFilterVO
	 * @return
	 */
	private static OffloadVO constructOffloadVO(OffloadFilterVO offloadFilterVO) {
		OffloadVO offloadVo = new OffloadVO();
		offloadVo.setCompanyCode(offloadFilterVO.getCompanyCode());
		offloadVo.setFlightNumber(offloadFilterVO.getFlightNumber());
		offloadVo.setFlightSequenceNumber(offloadFilterVO.getFlightSequenceNumber());
		offloadVo.setPol(offloadFilterVO.getPol());
		offloadVo.setCarrierId(offloadFilterVO.getCarrierId());
		offloadVo.setLegSerialNumber(offloadFilterVO.getLegSerialNumber());
		offloadVo.setCarrierCode(offloadFilterVO.getCarrierCode());
		offloadVo.setFlightDate(offloadFilterVO.getFlightDate());
		return offloadVo;
	}

	/**
	 * TODO Purpose Jan 19, 2007, A-1739
	 * @param flightVO
	 * @return
	 */
	public static MailManifestVO findMailbagManifest(OperationalFlightVO flightVO) {
		try {
			return constructDAO().findMailbagManifestDetails(flightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}

	/**
	 * TODO Purpose Jan 19, 2007, A-1739
	 * @param flightVO
	 * @return
	 * @throws SystemException
	 */
	public static MailManifestVO findMailAWBManifest(OperationalFlightVO flightVO) {
		try {
			return constructDAO().findMailAWBManifestDetails(flightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}
	public static MailManifestVO findDSNMailbagManifest(OperationalFlightVO flightVO) {
		try {
			return constructDAO().findDSNMailbagManifest(flightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}
	public static MailManifestVO findDestnCatManifest(OperationalFlightVO flightVO) {
		try {
			return constructDAO().findManifestbyDestination(flightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
	}
	/**
	 * Method		:	MailAcceptance.findMailBagsForTransportCompletedResdit Added by 	: Used for 	: Parameters	:	@param operationalFlightVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	Collection<MailbagVO>
	 */
	public static Collection<MailbagVO> findMailBagsForTransportCompletedResdit(
			OperationalFlightVO operationalFlightVO) {
		Collection<MailbagVO> mailbags = null;
		try {
			mailbags = constructDAO().findMailBagsForTransportCompletedResdit(operationalFlightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		return mailbags;
	}

	/**
	 * Method		:	MailAcceptance.findUldsForTransportCompletedResdit Added by 	: Used for 	: Parameters	:	@param operationalFlightVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	Collection<ContainerDetailsVO>
	 */
	public static Collection<ContainerDetailsVO> findUldsForTransportCompletedResdit(
			OperationalFlightVO operationalFlightVO) {
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		try {
			containerDetailsVOs = constructDAO().findUldsForTransportCompletedResdit(operationalFlightVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		return containerDetailsVOs;
	}
	/**
	 * @author A-7929
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static OffloadVO findOffLoadDetails(OffloadFilterVO offloadFilterVO) {
		OffloadVO offloadVo = null;
		if (MailConstantsVO.OFFLOAD_CONTAINER.equals(offloadFilterVO.getOffloadType())) {
			offloadVo = findAcceptedContainersForOffLoad(offloadFilterVO);
		} else if (MailConstantsVO.OFFLOAD_DSN.equals(offloadFilterVO.getOffloadType())) {
			offloadVo = findAcceptedDespatchesForOffload(offloadFilterVO);
		} else {
			offloadVo = findAcceptedMailBagsForOffload(offloadFilterVO);
		}
		return offloadVo;
	}

	/**
	 * @author A-7929
	 * @param offloadFilterVO
	 * @return
	 * @throws SystemException
	 */
	private static OffloadVO findAcceptedContainersForOffLoad(OffloadFilterVO offloadFilterVO) {
		Page<ContainerVO> containerVos = null;
		OffloadVO offloadVo = null;
		try {
			containerVos = constructDAO().findAcceptedContainersForOffLoad(offloadFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
		if (containerVos != null && containerVos.size() > 0) {
			offloadVo = constructOffloadVO(offloadFilterVO);
			offloadVo.setOffloadContainerDetails(containerVos);
		}
		return offloadVo;
	}
	private static String generateDespatchSerialNumber(String currentKey, MailbagVO maibagVO) {
		String key = null;
		StringBuilder keyValue = new StringBuilder();
		keyValue.append(maibagVO.getYear());
		KeyCondition keyCondition =  new KeyCondition();
		keyCondition.setKey("rsn");
		keyCondition.setValue(keyValue.toString());
		Criterion criterion = new CriterionBuilder()
				.withSequence("DOM_USPS_DSN")
				.withKeyCondition(keyCondition)
				.withPrefix("").build();
		KeyUtils keyUtils = ContextUtil.getInstance().getBean(KeyUtils.class);
		key =  (keyUtils.getKey(criterion));
		//TODO: Neo to correct
		if (MailConstantsVO.FLAG_YES.equals(currentKey) && key.length() > 4) {
			key = "9999";
		//	KeyUtils.resetKey(criterion, "0");
		} else if (MailConstantsVO.FLAG_YES.equals(currentKey) && !"1".equals(key)) {
			key = String.valueOf(Long.parseLong(key) - 1);
			//KeyUtils.resetKey(criterion, key);
		}
		return checkLength(key, 4);

	}

	private static String generateReceptacleSerialNumber(String dsn, MailbagVO maibagVO) {
		String key = null;
		StringBuilder keyValue = new StringBuilder();
		keyValue.append(maibagVO.getYear()).append(dsn);

		KeyCondition keyCondition =  new KeyCondition();
		keyCondition.setKey("rsn");
		keyCondition.setValue(keyValue.toString());
		Criterion criterion = new CriterionBuilder()
				.withSequence("DOM_USPS_RSN")
				.withKeyCondition(keyCondition)
				.withPrefix("").build();
		KeyUtils keyUtils = ContextUtil.getInstance().getBean(KeyUtils.class);
		 key =  (keyUtils.getKey(criterion));
		String rsn = checkLength(key, 3);
		return rsn;

		//TODO: Neo to correct

//		if (key.length() > 3) {
//			key = "999";
//			KeyUtils.resetKey(criterion, "0");


	}

	private static String checkLength(String key, int maxLength) {
		String modifiedKey = null;
		StringBuilder buildKey = new StringBuilder();
		modifiedKey = new StringBuilder().append(key).toString();
		int keyLength = modifiedKey.length();
		if (modifiedKey.length() < maxLength) {
			int diff = maxLength - keyLength;
			String val = null;
			for (int i = 0; i < diff; i++) {
				val = buildKey.append("0").toString();
			}
			modifiedKey = new StringBuilder().append(val).append(key).toString();
		}
		return modifiedKey;
	}

	/**
	 * Method		:	MailAcceptance.findDsnAndRsnForMailbag Added by 	:	A-7531 on 31-Oct-2018 Used for 	: Parameters	:	@param maibagVO Parameters	:	@return  Return type	: 	MailbagVO
	 * @throws SystemException
	 */
	public static MailbagVO findDsnAndRsnForMailbag(MailbagVO mailbagVO) {
		String despacthNumber = generateDespatchSerialNumber(MailConstantsVO.FLAG_YES, mailbagVO);
		String rsn = generateReceptacleSerialNumber(despacthNumber, mailbagVO);
		if (rsn.length() > 3) {
			generateDespatchSerialNumber(MailConstantsVO.FLAG_NO, mailbagVO);
			despacthNumber = (generateDespatchSerialNumber(MailConstantsVO.FLAG_YES, mailbagVO));
			rsn = generateReceptacleSerialNumber(despacthNumber, mailbagVO);
		}
		mailbagVO.setDespatchSerialNumber(despacthNumber);
		mailbagVO.setReceptacleSerialNumber(rsn);
		return mailbagVO;
	}

}
