package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.component.proxy.*;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.*;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.neoicargo.mailmasters.MailTrackingDefaultsBI;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class MailOperationsValidator extends MailController {
	private static final String OFL_REASONCODE = "mailtracking.defaults.offload.reasoncode";
	private static final String DMG_RET_REASONCODE = "mailtracking.defaults.return.reasoncode";
	private static final String DOMESTICMRA_USPS = "mailtracking.domesticmra.usps";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String NO_OF_CHAR_ALLOWED_FOR_MAILTAG = "mailtracking.defaults.noofcharsallowedformailtag";
	private static final String DEFAULTCOMMODITYCODE_SYSPARAM = "mailtracking.defaults.booking.commodity";
	private static final String NA = "NA";
	private static final String ULD_SYSPAR_NOTINAIRPORT = "uld.defaults.errortype.notinairport";
	private static final String ULD_SYSPAR_NOTINSTOCK = "uld.defaults.errortype.notinstock";
	private static final String AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED = "operations.flthandling.aircraftcompatibilityrequireduldtypes";
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTINAIRPORT = "mail.defaults.warning.uldisnotinairport";
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTINAIRPORT = "mail.defaults.error.uldisnotinairport";
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTINSYSTEM = "mail.defaults.warning.uldisnotinthesystem";
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTINAIRSTOCK = "mail.defaults.warning.uldnotinairlinestock";
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM = "mail.defaults.error.uldisnotinthesystem";
	private static final String MAIL_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK = "mail.defaults.error.uldnotinairlinestock";
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTOPERATIONAL = "mail.defaults.warning.uldisnotoperational";
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTOPERATIONAL = "mail.defaults.error.uldisnotoperational";
	private static final String EMBARGO_VALIDATION_REQUIRED = "mail.operation.embargovalidationrequired";
	private static final String TBA_VALIDATION_BYPASS = "mail.operations.ignoretobeactionedflightvalidation";
	private static final String SYSTEM_EXCEPTION = "SystemException Caught";
	private static final String FINDER_EXCEPTION = "Finder Exception Caught";

	private LocalDate localDateUtil;


	public void validateScannedMailDetails(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		ContextUtil contextUtil = ContextUtil.getInstance();
		LoginProfile logonAttributes = null;
		Collection<MailbagVO> mailErrors = new ArrayList<MailbagVO>();
		if (scannedMailDetailsVO.getErrorMailDetails() == null
				|| scannedMailDetailsVO.getErrorMailDetails().size() == 0) {
			scannedMailDetailsVO.setErrorMailDetails(mailErrors);
		}
		try {
			logonAttributes = ContextUtil.getInstance().getBean(ContextUtil.class).callerLoginProfile();
		} catch (Exception e) {
			log.debug("Exception Caught");
			constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
		}
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
				|| scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)) {
			if (!validateAirport(scannedMailDetailsVO.getDestination(), scannedMailDetailsVO.getCompanyCode())) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_DESTINATION,
						MailHHTBusniessException.INVALID_DESTINATION, scannedMailDetailsVO);
			}
			if (scannedMailDetailsVO.getPou() != null && !("").equals(scannedMailDetailsVO.getPou())) {
				if (!validateAirport(scannedMailDetailsVO.getPou(), scannedMailDetailsVO.getCompanyCode())) {
					constructAndRaiseException(MailMLDBusniessException.INVALID_POU,
							MailHHTBusniessException.INVALID_POU, scannedMailDetailsVO);
				}
			}
			if (!validateAirport(scannedMailDetailsVO.getPol(), scannedMailDetailsVO.getCompanyCode())) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_POL, "Invalid Pol", scannedMailDetailsVO);
			}
		}
		if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())) {
			if ((scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0
					&& scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId() != null
					&& scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId().trim().length() < 29)) {
				if (!validateContainerNumber(scannedMailDetailsVO)) {
					constructAndRaiseException(MailMLDBusniessException.INVALID_ULD_FORMAT,
							MailHHTBusniessException.INVALID_ULD_FORMAT, scannedMailDetailsVO);
				}
			}
		}
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().iterator().hasNext()
				&& scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId() != null
				&& scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId().trim().length() > 0) {
			doMailbagValidation(scannedMailDetailsVO, oneTimes, logonAttributes);
			doEmbargoValidationForMailBag(scannedMailDetailsVO);
			doProcessPointSpecificMailbagValidations(scannedMailDetailsVO);
		}
		if (scannedMailDetailsVO.getContainerNumber() != null
				&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
			checkIfContainerExists(scannedMailDetailsVO, logonAttributes);
			doProcessPointSpecificContainerValidations(scannedMailDetailsVO);
		}
	}

	private void doEmbargoValidationForMailBag(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		MailController mailController=ContextUtil.getInstance().getBean(MailController.class);
		if (!MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
				&& (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint()))) {
			Collection<ShipmentDetailsVO> shipmentDetailsVOs = new ArrayList<ShipmentDetailsVO>();
			Collection<EmbargoDetailsVO> embargoDetails = null;
			Collection<MailbagVO> mailBagVOs = scannedMailDetailsVO.getMailDetails();
			if (mailBagVOs != null && mailBagVOs.size() > 0) {
				for (MailbagVO mailBagvo : mailBagVOs) {
					if (mailBagvo.getMailbagId() != null && mailBagvo.getMailbagId().trim().length() == 29) {
						shipmentDetailsVOs = populateShipmentDetailsVO(mailBagvo, scannedMailDetailsVO.getCompanyCode(),
								scannedMailDetailsVO);
						if (shipmentDetailsVOs != null && shipmentDetailsVOs.size() > 0) {
							log.info("" + "shipmentDetailsVOs" + " " + shipmentDetailsVOs);
							try {
								embargoDetails = mailController.checkEmbargoForMail(shipmentDetailsVOs);
							} catch (Exception e) {
								log.debug("Exception Caught");
							}
							if (embargoDetails != null && embargoDetails.size() > 0) {
								log.info("" + "VO is" + " " + embargoDetails);
								for (EmbargoDetailsVO embargoDetailsVO : embargoDetails) {
									if ("E".equals(embargoDetailsVO.getEmbargoLevel())) {
										constructAndRaiseException(embargoDetailsVO.getEmbargoDescription(),
												embargoDetailsVO.getEmbargoDescription(), scannedMailDetailsVO);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public ScannedMailDetailsVO doEmbargoValidationForAndroidMailBag(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		RecoDefaultsProxy recoDefaultsProxy = ContextUtil.getInstance().getBean(RecoDefaultsProxy.class);
		String embargoValidationRequired = findSystemParameterValue(EMBARGO_VALIDATION_REQUIRED);
		MailController mailController=ContextUtil.getInstance().getBean(MailController.class);
		if (!MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
				&& MailConstantsVO.FLAG_YES.equals(embargoValidationRequired)
				&& (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint()))) {
			Collection<ShipmentDetailsVO> shipmentDetailsVOs = new ArrayList<ShipmentDetailsVO>();
			Collection<EmbargoDetailsVO> embargoDetails = null;
			Collection<MailbagVO> mailBagVOs = scannedMailDetailsVO.getMailDetails();
			boolean isEmbargoValidationRequired = true;
			EmbargoFilterVO embargoFilterVO = constructEmbargoFilterVO(scannedMailDetailsVO);
			try {
				isEmbargoValidationRequired = recoDefaultsProxy.checkAnyEmbargoExists(embargoFilterVO);
			} finally {
			}
			if (isEmbargoValidationRequired && mailBagVOs != null && mailBagVOs.size() > 0) {
				for (MailbagVO mailBagvo : mailBagVOs) {
					if (mailBagvo.getMailbagId() != null && (mailBagvo.getMailbagId().trim().length() == 29
							|| (isValidMailtag(mailBagvo.getMailbagId().trim().length())))) {
						shipmentDetailsVOs = populateShipmentDetailsVO(mailBagvo, scannedMailDetailsVO.getCompanyCode(),
								scannedMailDetailsVO);
						if (shipmentDetailsVOs != null && shipmentDetailsVOs.size() > 0) {
							log.info("" + "shipmentDetailsVOs" + " " + shipmentDetailsVOs);
							try {
								embargoDetails = mailController.checkEmbargoForMail(shipmentDetailsVOs);
							} catch (Exception e) {
								log.debug("Exception Caught");
								constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
							}
							if (embargoDetails != null && embargoDetails.size() > 0) {
								log.info("" + "VO is" + " " + embargoDetails);
								for (EmbargoDetailsVO embargoDetailsVO : embargoDetails) {
									if ("W".equals(embargoDetailsVO.getEmbargoLevel())
											&& !MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())) {
										constructAndroidException(MailConstantsVO.EMBARGO_VALIDATION,
												embargoDetailsVO.getEmbargoDescription(), scannedMailDetailsVO);
									} else if ("E".equals(embargoDetailsVO.getEmbargoLevel())) {
										constructAndRaiseException(embargoDetailsVO.getEmbargoDescription(),
												embargoDetailsVO.getEmbargoDescription(), scannedMailDetailsVO);
									}
								}
							}
						}
					}
				}
			}
		}
		return scannedMailDetailsVO;
	}

	private void doProcessPointSpecificMailbagValidations(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			for (MailbagVO scannedMailbagVO : scannedMailDetailsVO.getMailDetails()) {
				if (scannedMailDetailsVO.getPou() == null || scannedMailDetailsVO.getPou().trim().length() == 0) {
					scannedMailDetailsVO.setPou(scannedMailbagVO.getPou());
				}
				if (scannedMailbagVO.getMailbagId() != null && scannedMailbagVO.getMailbagId().trim().length() == 29
						|| (scannedMailbagVO.getMailbagId() != null
								&& isValidMailtag(scannedMailbagVO.getMailbagId().length()))) {
					if (scannedMailDetailsVO.getProcessPoint() != null) {
						if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
								|| MailConstantsVO.MAIL_STATUS_RECEIVED
										.equals(scannedMailDetailsVO.getProcessPoint())) {
							if (!MailConstantsVO.FLAG_YES.equalsIgnoreCase(scannedMailbagVO.getReassignFlag())) {
								validateMailbagsForUploadAcceptance(scannedMailbagVO, scannedMailDetailsVO);
							}
						} else if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
							validateMailbagsForUploadArrival(scannedMailbagVO, scannedMailDetailsVO);
						} else if (MailConstantsVO.MAIL_STATUS_RETURNED
								.equals(scannedMailDetailsVO.getProcessPoint())) {
							validateMailbagsForUploadReturn(scannedMailbagVO, scannedMailDetailsVO);
						} else if (MailConstantsVO.MAIL_STATUS_DAMAGED.equals(scannedMailDetailsVO.getProcessPoint())) {
							validateMailbagsForUploadDamage(scannedMailbagVO, scannedMailDetailsVO);
						} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED
								.equals(scannedMailDetailsVO.getProcessPoint())) {
							validateMailbagsForUploadOffload(scannedMailbagVO, scannedMailDetailsVO);
						} else if (MailConstantsVO.MAIL_STATUS_DELIVERED
								.equals(scannedMailDetailsVO.getProcessPoint())) {
							validateMailbagsForUploadDeliver(scannedMailbagVO, scannedMailDetailsVO);
						} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED
								.equals(scannedMailDetailsVO.getProcessPoint())) {
							validateMailbagsForUploadTransfer(scannedMailbagVO, scannedMailDetailsVO);
						} else if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL
								.equals(scannedMailDetailsVO.getProcessPoint())) {
							validateMailbagsForUploadReassign(scannedMailbagVO, scannedMailDetailsVO);
						}
					}
				}
			}
		}
	}

	private void doProcessPointSpecificContainerValidations(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		if (scannedMailDetailsVO.getScannedContainerDetails() != null
				&& scannedMailDetailsVO.getScannedContainerDetails().size() > 0) {
			for (ContainerVO scannedContainerVO : scannedMailDetailsVO.getScannedContainerDetails()) {
				ContainerAssignmentVO containerAssignmentVO = null;
				if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
						&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
						&& MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
						&& (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
								|| (scannedMailDetailsVO.getMailDetails() == null
										|| scannedMailDetailsVO.getMailDetails().isEmpty()))) {
					containerAssignmentVO = findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO);
				} else {
					containerAssignmentVO = findLatestContainerAssignment(scannedContainerVO.getContainerNumber());
				}
				if (containerAssignmentVO == null) {
					scannedMailDetailsVO.setErrorDescription(MailHHTBusniessException.ULD_NOT_FOUND_EXCEPTION);
					constructAndRaiseException(MailMLDBusniessException.ULD_NOT_FOUND_EXCEPTION,
							MailHHTBusniessException.ULD_NOT_FOUND_EXCEPTION, scannedMailDetailsVO);
				}
				validateScannedContainersForUpload(scannedMailDetailsVO, containerAssignmentVO, scannedContainerVO);
			}
		}
	}

	private boolean checkIfFlightToBeCancelled(Collection<FlightValidationVO> flightDetailsVOs,
			ScannedMailDetailsVO scannedMailDetailsVO, String airportCode) {
		boolean isStatusTBAorTBC = false;
		int count = 0;
		String bypassTBAVal = null;
		try {
			bypassTBAVal = findSystemParameterValue(TBA_VALIDATION_BYPASS);
		} finally {
		}
		for (FlightValidationVO flightValidationVO : flightDetailsVOs) {
			if ((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
					&& flightValidationVO.getLegDestination().equals(airportCode))
					|| flightValidationVO.getLegOrigin().equals(airportCode)) {
				log.info("" + "VO is" + " " + flightValidationVO);
				if ((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())
						&& MailConstantsVO.FLAG_NO.equals(bypassTBAVal))
						|| FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())
						|| FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus())) {
					count++;
				}
			}
		}
		if (count == flightDetailsVOs.size()) {
			isStatusTBAorTBC = true;
		}
		return isStatusTBAorTBC;
	}

	private void checkIfContainerExists(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		ContainerAssignmentVO containerAssignmentVO = null;
		boolean airportFlag = false;
		String DEST_FLT = "-1";
		String airportCode = scannedMailDetailsVO.getAirportCode();
		if (scannedMailDetailsVO.getContainerNumber() != null) {
			try {
				if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
						&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
						&& MailConstantsVO.SCAN.equals(scannedMailDetailsVO.getMailSource())
						&& (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerDeliveryFlag())
								|| (scannedMailDetailsVO.getMailDetails() == null
										|| scannedMailDetailsVO.getMailDetails().isEmpty()))) {
					containerAssignmentVO = findLatestContainerAssignmentForUldDelivery(scannedMailDetailsVO);
				} else {
					containerAssignmentVO = findLatestContainerAssignment(scannedMailDetailsVO.getContainerNumber());
				}
				if (containerAssignmentVO != null
						&& MailConstantsVO.BULK_TYPE.equals(containerAssignmentVO.getContainerType())) {
					ContainerVO containerVO = new ContainerVO();
					containerVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
					containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
					containerVO.setAssignedPort(scannedMailDetailsVO.getAirportCode());
					containerAssignmentVO = findContainerAssignmentForUpload(containerVO);
				}
			} catch (Exception e) {
				log.debug("Exception Caught");
			}
		}
		if (containerAssignmentVO == null
				&& MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())) {
			constructAndRaiseException(MailMLDBusniessException.CONTAINER_CANNOT_ASSIGN,
					MailHHTBusniessException.CONTAINER_CANNOT_ASSIGN, scannedMailDetailsVO);
		}
		if (containerAssignmentVO != null) {
			if ("MLD".equals(scannedMailDetailsVO.getMailSource())) {
				if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
						&& !airportCode.equals(containerAssignmentVO.getAirportCode())
						&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())) {
					if (!containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
							|| containerAssignmentVO.getFlightSequenceNumber() != scannedMailDetailsVO
									.getFlightSequenceNumber()) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_CONTAINER_REUSE,
								MailHHTBusniessException.INVALID_CONTAINER_REUSE, scannedMailDetailsVO);
					}
				}
			}
			if ("WS".equals(scannedMailDetailsVO.getMailSource())
					&& !MailConstantsVO.CONTAINER_STATUS_TRANSFER
							.equals(scannedMailDetailsVO.getContainerProcessPoint())
					&& containerAssignmentVO != null && !airportCode.equals(containerAssignmentVO.getAirportCode())
					&& MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
					&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())) {
				constructAndRaiseException(MailMLDBusniessException.CONTAINER_CANNOT_ASSIGN,
						MailHHTBusniessException.CONTAINER_NOT_AVAILABLE_AT_AIRPORT, scannedMailDetailsVO);
			}
			if ("WS".equals(scannedMailDetailsVO.getMailSource())
					&& (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
							|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint()))
					&& containerAssignmentVO != null && airportCode.equals(containerAssignmentVO.getAirportCode())
					&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())) {
				boolean isFlightClosed = false;
				OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
				operationalFlightVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
				operationalFlightVO.setCarrierId(containerAssignmentVO.getCarrierId());
				operationalFlightVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
				operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
				operationalFlightVO.setFlightDate(containerAssignmentVO.getFlightDate());
				operationalFlightVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
				operationalFlightVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
				operationalFlightVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
				operationalFlightVO.setPol(containerAssignmentVO.getAirportCode());
				if (operationalFlightVO.getFlightSequenceNumber() > 0) {
					log.debug("Carrier assigned----->");
					isFlightClosed = isFlightClosedForOperations(operationalFlightVO);
				}
				log.debug("" + "isFlightClosed----->" + " " + isFlightClosed);
				if (isFlightClosed) {
					log.debug("Mailbag from carrier cannot be offloaded----->");
					constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
							MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
				}
			}
			if ((scannedMailDetailsVO.getFlightNumber() != null
					&& scannedMailDetailsVO.getFlightNumber().equals(containerAssignmentVO.getFlightNumber()))
					&& scannedMailDetailsVO.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())
					&& scannedMailDetailsVO.getCarrierId() == containerAssignmentVO.getCarrierId()
					&& scannedMailDetailsVO.getFlightSequenceNumber() == containerAssignmentVO.getFlightSequenceNumber()
					&& scannedMailDetailsVO.getLegSerialNumber() == containerAssignmentVO.getLegSerialNumber()
					&& !(scannedMailDetailsVO.getContainerType().equals(containerAssignmentVO.getContainerType()))) {
				constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONT_ALRDY_EXST_IN_SAME_FLT_WIT_DIFF_CONTYPE,
						MailHHTBusniessException.UPLOAD_EXCEPT_CONT_ALRDY_EXST_IN_SAME_FLT_WIT_DIFF_CONTYPE,
						scannedMailDetailsVO);
			}
			if (scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
					&& MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag())
					&& scannedMailDetailsVO.getFlightNumber().equals(containerAssignmentVO.getFlightNumber())
					&& scannedMailDetailsVO.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())
					&& scannedMailDetailsVO.getCarrierId() == containerAssignmentVO.getCarrierId()
					&& scannedMailDetailsVO.getFlightSequenceNumber() == containerAssignmentVO
							.getFlightSequenceNumber()) {
				constructAndRaiseException(MailMLDBusniessException.CONTAINER_ALREADY_ARRIVEDT,
						MailHHTBusniessException.INVALID_ACCEPTANCETO_ARRIVEDCONTAINER, scannedMailDetailsVO);
			}
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
					&& !MailConstantsVO.CONTAINER_STATUS_TRANSFER
							.equalsIgnoreCase(scannedMailDetailsVO.getContainerProcessPoint())) {
				if (containerAssignmentVO.getTransitFlag() != null
						&& containerAssignmentVO.getTransitFlag().equals(MailConstantsVO.FLAG_YES)
						&& (!containerAssignmentVO.getAirportCode()
								.equalsIgnoreCase(scannedMailDetailsVO.getAirportCode())
								|| (containerAssignmentVO.getArrivalFlag() != null && MailConstantsVO.FLAG_YES
										.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag())))
						&& containerAssignmentVO.getContainerType().equalsIgnoreCase(MailConstantsVO.ULD_TYPE)) {
					constructAndRaiseException(MailMLDBusniessException.INVALID_CONTAINER_REUSE,
							MailHHTBusniessException.INVALID_CONTAINER_REUSE, scannedMailDetailsVO);
				}
			}
			if (MailConstantsVO.CONTAINER_STATUS_TRANSFER
					.equalsIgnoreCase(scannedMailDetailsVO.getContainerProcessPoint())
					&& MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())
					&& MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getArrivalFlag())) {
				Collection<ContainerDetailsVO> conatinerDetails = null;
				Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
				containerDetailsVO.setContainerType(containerAssignmentVO.getContainerType());
				containerDetailsVO.setPol(containerAssignmentVO.getAirportCode());
				containerDetailsVO.setCarrierId(containerAssignmentVO.getCarrierId());
				containerDetailsVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
				containerDetailsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
				containerDetailsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
				containerDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
				containers.add(containerDetailsVO);
				try {
					conatinerDetails = MailAcceptance.findMailbagsInContainer(containers);
				} finally {
				}
				if (conatinerDetails != null) {
					for (ContainerDetailsVO containerVO : conatinerDetails) {
						int arrivedMails = 0;
						for (MailbagVO mailbagVO : containerVO.getMailDetails()) {
							ArrayList<MailbagHistoryVO> mailhistories = new ArrayList<MailbagHistoryVO>();
							mailhistories = (ArrayList<MailbagHistoryVO>) Mailbag.findMailbagHistories(
									scannedMailDetailsVO.getCompanyCode(), mailbagVO.getMailbagId(), 0l);
							if (mailhistories != null && mailhistories.size() > 0) {
								for (MailbagHistoryVO mailbaghistoryvo : mailhistories) {
									if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbaghistoryvo.getMailStatus())
											&& scannedMailDetailsVO.getAirportCode()
													.equals(mailbaghistoryvo.getScannedPort())) {
										arrivedMails++;
									}
								}
							}
						}
						if (arrivedMails > 0 && containerVO.getMailDetails() != null
								&& arrivedMails == containerVO.getMailDetails().size()) {
							scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_PREASSIGN);
						} else if (arrivedMails > 0 && containerVO.getMailDetails() != null
								&& arrivedMails != containerVO.getMailDetails().size()) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_CONTAINER_REUSE,
									MailHHTBusniessException.INVALID_CONTAINER_REUSE, scannedMailDetailsVO);
						}
					}
				}
			}
		}
		if (containerAssignmentVO != null && ((containerAssignmentVO.getContainerType() != null
				&& containerAssignmentVO.getContainerType().equals(scannedMailDetailsVO.getContainerType()))
				&& scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_EXPORT))) {
			if (airportCode.equals(containerAssignmentVO.getAirportCode())
					|| airportCode.equals(containerAssignmentVO.getPou())
					|| airportCode.equals(containerAssignmentVO.getDestination())) {
				airportFlag = true;
			}
			if (!airportFlag && containerAssignmentVO.getTransitFlag().equals(MailConstantsVO.FLAG_YES)) {
				StringBuilder errorString = new StringBuilder();
				errorString.append("This BULK/ULD is present in another flight/carrier ");
				if (containerAssignmentVO.getFlightNumber().trim().length() > 0
						&& !containerAssignmentVO.getFlightNumber().equals(DEST_FLT)) {
					errorString.append(containerAssignmentVO.getFlightNumber());
				}
				errorString.append(" ");
				if (containerAssignmentVO.getFlightDate() != null) {
					errorString.append(containerAssignmentVO.getFlightDate()
							.format(DateTimeFormatter.ofPattern(LocalDate.DATE_TIME_FORMAT)));
				}
				errorString.append(" at ");
				errorString.append(containerAssignmentVO.getAirportCode());
				String error = errorString.toString();
				constructAndRaiseException(MailMLDBusniessException.ULDBULK_ANOTHER_FLIGHT, error,
						scannedMailDetailsVO);
			} else if (containerAssignmentVO.getArrivalFlag() != null
					&& MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag())) {
				if (containerAssignmentVO.getTransitFlag().equals(MailConstantsVO.FLAG_NO)) {
				} else {
					constructAndRaiseException(MailMLDBusniessException.CONTAINER_ALREADY_ARRIVEDT,
							MailHHTBusniessException.INVALID_ACCEPTANCETO_ARRIVEDCONTAINER, scannedMailDetailsVO);
				}
			}
		}
	}

	private static MailbagInULDAtAirportPK constructMailbagInULDAtAirportPK(MailbagVO mailbagVO,
			ScannedMailDetailsVO scannedMailDetailsVO) {
		MailbagInULDAtAirportPK mailbagInULDAtAirportPK = new MailbagInULDAtAirportPK();
		mailbagInULDAtAirportPK.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		mailbagInULDAtAirportPK.setCarrierId(scannedMailDetailsVO.getCarrierId());
		mailbagInULDAtAirportPK.setAirportCode(scannedMailDetailsVO.getAirportCode());
		mailbagInULDAtAirportPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		if (MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
			mailbagInULDAtAirportPK.setUldNumber(
					MailConstantsVO.CONST_BULK + MailConstantsVO.SEPARATOR + scannedMailDetailsVO.getDestination());
		} else {
			mailbagInULDAtAirportPK.setUldNumber(scannedMailDetailsVO.getContainerNumber());
		}
		return mailbagInULDAtAirportPK;
	}

	private MailbagInULDForSegmentPK constructMailbagInULDForSegmentPK(MailbagVO mailbagvo,
			ScannedMailDetailsVO scannedMailDetailsVO) {
		MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
		mailbagInULDForSegmentPK.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		if (MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
			mailbagInULDForSegmentPK.setUldNumber(
					MailConstantsVO.CONST_BULK + MailConstantsVO.SEPARATOR + scannedMailDetailsVO.getDestination());
		} else {
			mailbagInULDForSegmentPK.setUldNumber(scannedMailDetailsVO.getContainerNumber());
		}
		mailbagInULDForSegmentPK.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		mailbagInULDForSegmentPK.setCarrierId(scannedMailDetailsVO.getCarrierId());
		mailbagInULDForSegmentPK.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		mailbagInULDForSegmentPK.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
		mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		return mailbagInULDForSegmentPK;
	}

	private void checkIfMailbagExists(MailbagVO mailBagVOFromUpload, ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String airportCode = scannedMailDetailsVO.getAirportCode();
		MailUploadController mailUploadController =ContextUtil.getInstance().getBean(MailUploadController.class);
		MailController mailController=ContextUtil.getInstance().getBean(MailController.class);
		boolean enableAutoArrival = false;
		String paCode = null;
		enableAutoArrival = mailUploadController.checkAutoArrival(scannedMailDetailsVO);
		MailbagInULDAtAirport mailbagInULDAtAirport = null;
		MailbagInULDForSegment mailbagInULDForSegment = null;
		paCode = mailBagVOFromUpload.getPaCode() != null && mailBagVOFromUpload.getPaCode().trim().length() > 0
				? mailBagVOFromUpload.getPaCode()
				: mailController.findPAForOfficeOfExchange(scannedMailDetailsVO.getCompanyCode(),
						mailBagVOFromUpload.getOoe());
		if (mailBagVOFromUpload.getMailbagId().trim().length() == 29
				|| isValidMailtag(mailBagVOFromUpload.getMailbagId().length())) {
			Mailbag mailBag = null;
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(mailBagVOFromUpload.getCompanyCode());
			mailbagPk
					.setMailSequenceNumber(mailBagVOFromUpload.getMailSequenceNumber() == 0
							? Mailbag.findMailBagSequenceNumberFromMailIdr(mailBagVOFromUpload.getMailbagId(),
									scannedMailDetailsVO.getCompanyCode())
							: mailBagVOFromUpload.getMailSequenceNumber());
			try {
				mailBag = Mailbag.find(mailbagPk);
			} catch (FinderException e) {
				log.error(FINDER_EXCEPTION);
			} catch (Exception e) {
				log.debug(e.getMessage());
				constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			}
			if (mailBag != null) {
				if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
						&& !(MailConstantsVO.MAIL_STATUS_NEW.equals(mailBag.getLatestStatus()))) {
					if (scannedMailDetailsVO.getFlightNumber() != null
							&& !scannedMailDetailsVO.getFlightNumber().isEmpty()
							&& scannedMailDetailsVO.getFlightSequenceNumber() > 0) {
						try {
							mailbagInULDForSegment = MailbagInULDForSegment
									.find(constructMailbagInULDForSegmentPK(mailBagVOFromUpload, scannedMailDetailsVO));
						} catch (FinderException finderException) {
							log.debug(finderException.getMessage());
						}
						if (mailbagInULDForSegment != null && mailbagInULDForSegment.getContainerNumber() != null
								&& !mailbagInULDForSegment.getContainerNumber().isEmpty() && mailbagInULDForSegment
										.getContainerNumber().equals(scannedMailDetailsVO.getContainerNumber())) {
							constructAndRaiseException(MailMLDBusniessException.MAILBAG_PRESENT,
									MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION, scannedMailDetailsVO);
						}
					} else {
						try {
							mailbagInULDAtAirport = MailbagInULDAtAirport
									.find(constructMailbagInULDAtAirportPK(mailBagVOFromUpload, scannedMailDetailsVO));
						} catch (FinderException finderException) {
							log.debug(finderException.getMessage());
						}
						if (mailbagInULDAtAirport != null && mailbagInULDAtAirport.getContainerNumber() != null
								&& !mailbagInULDAtAirport.getContainerNumber().isEmpty() && mailbagInULDAtAirport
										.getContainerNumber().equals(scannedMailDetailsVO.getContainerNumber())) {
							constructAndRaiseException(MailMLDBusniessException.MAILBAG_PRESENT,
									MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION, scannedMailDetailsVO);
						}
					}
				}
				if (mailBag.getLatestStatus() != null
						&& mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_DELIVERED)
						&& (scannedMailDetailsVO.getProcessPointBeforeArrival() == null || !scannedMailDetailsVO
								.getProcessPointBeforeArrival().equals(MailConstantsVO.MAIL_STATUS_DELIVERED))) {
					constructAndRaiseException(MailMLDBusniessException.MAILBAG_DELIVERED_EXCEPTION,
							MailHHTBusniessException.MAILBAG_DELIVERED_EXCEPTION, scannedMailDetailsVO);
				}
				if (mailBag.getLatestStatus() != null
						&& mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_RETURNED)
						&& (scannedMailDetailsVO.getProcessPointBeforeArrival() == null || !scannedMailDetailsVO
								.getProcessPointBeforeArrival().equals(MailConstantsVO.MAIL_STATUS_RETURNED))) {
					constructAndRaiseException(MailMLDBusniessException.MAILBAG_RETURN_EXCEPTION,
							MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION, scannedMailDetailsVO);
				}
				if ((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
						&& mailBagVOFromUpload.getDeliveredFlag() != null
						&& mailBagVOFromUpload.getDeliveredFlag().trim().length() != 0
						&& MailConstantsVO.FLAG_YES.equals(mailBagVOFromUpload.getDeliveredFlag()))
						|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())) {
					//MailController mailController = new MailController();
					boolean coterminusAvalilable = false;
					String destinationPort;
					if (mailBag.getDestination() != null && mailBag.getDestination().trim().length() > 0) {
						destinationPort = mailBag.getDestination();
					} else {
						OfficeOfExchangeVO destOfficeOfExchangeVO = mailController.validateOfficeOfExchange(
								scannedMailDetailsVO.getCompanyCode(), mailBagVOFromUpload.getDoe());
						if (destOfficeOfExchangeVO != null && destOfficeOfExchangeVO.getAirportCode() == null) {
							String destOfficeOfExchange = destOfficeOfExchangeVO.getCode();
							destinationPort = findNearestAirportOfCity(mailBagVOFromUpload.getCompanyCode(),
									destOfficeOfExchange);
							destOfficeOfExchangeVO.setAirportCode(destinationPort);
						} else {
							destinationPort = destOfficeOfExchangeVO != null ? destOfficeOfExchangeVO.getAirportCode()
									: null;
						}
					}
					ZonedDateTime dspDate = localDateUtil.getLocalDate(scannedMailDetailsVO.getAirportCode(), true);
					if (mailBag != null) {
						dspDate = localDateUtil.getLocalDateTime(mailBag.getDespatchDate(), null);
					}
					coterminusAvalilable = mailController.validateCoterminusairports(destinationPort,
							scannedMailDetailsVO.getAirportCode(), MailConstantsVO.RESDIT_DELIVERED, paCode, dspDate);
					if (isValidDestForCarditMissingDomesticMailbag(destinationPort)
							&& mailBag.getOrigin().equals(scannedMailDetailsVO.getAirportCode())) {
						destinationPort = null;
					}
					if (destinationPort == null || (!destinationPort.equals(airportCode) && !coterminusAvalilable
							&& !(mailUploadController.isCommonCityForAirport(scannedMailDetailsVO.getCompanyCode(),
									scannedMailDetailsVO.getAirportCode(), destinationPort)))) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
								MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
					}
				}
				if (scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)) {
					if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailBag.getLatestStatus())
							&& MailConstantsVO.FLAG_NO.equals(mailBagVOFromUpload.getDeliveredFlag())) {
						constructAndRaiseException(MailMLDBusniessException.MAILBAG_ALREADY_ARRIVAL_EXCEPTION,
								MailHHTBusniessException.MAILBAG_ALREADY_ARRIVAL_EXCEPTION, scannedMailDetailsVO);
					}
					if (mailBag.getLatestStatus() != null
							&& mailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_DELIVERED)
							&& (scannedMailDetailsVO.getProcessPointBeforeArrival() == null || !scannedMailDetailsVO
									.getProcessPointBeforeArrival().equals(MailConstantsVO.MAIL_STATUS_DELIVERED))) {
						constructAndRaiseException(MailMLDBusniessException.MAILBAG_DELIVERED_EXCEPTION,
								MailHHTBusniessException.MAILBAG_DELIVERED_EXCEPTION, scannedMailDetailsVO);
					}
				}
				if (scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED)
						&& MailConstantsVO.MAIL_STATUS_NEW.equals(mailBag.getLatestStatus())) {
					constructAndRaiseException(MailHHTBusniessException.OFFLOAD_NON_ACCEPTED_MAILBAG,
							MailHHTBusniessException.OFFLOAD_NON_ACCEPTED_MAILBAG, scannedMailDetailsVO);
				}
				checkIfMailbagFromPaBuilt(mailBagVOFromUpload, scannedMailDetailsVO, mailBag);
			} else if (scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED)
					|| scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_RETURNED)
					|| (scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_DELIVERED)
							&& !enableAutoArrival)) {
				if (scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED)) {
					constructAndRaiseException(MailHHTBusniessException.OFFLOAD_NON_ACCEPTED_MAILBAG,
							MailHHTBusniessException.OFFLOAD_NON_ACCEPTED_MAILBAG, scannedMailDetailsVO);
				}
				if (!(enableAutoArrival
						&& scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_RETURNED))) {
					constructAndRaiseException(MailHHTBusniessException.MAILBAG_NOT_FOUND_EXCEPTION,
							MailHHTBusniessException.MAILBAG_NOT_FOUND_EXCEPTION, scannedMailDetailsVO);
				}
			} else if ((MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
					&& mailBagVOFromUpload.getDeliveredFlag() != null
					&& mailBagVOFromUpload.getDeliveredFlag().trim().length() != 0
					&& MailConstantsVO.FLAG_YES.equals(mailBagVOFromUpload.getDeliveredFlag())
					&& scannedMailDetailsVO.getFlightNumber().isEmpty())
					|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())) {
				//MailController mailController = new MailController();
				boolean coterminusAvalilable = false;
				String destinationPort;
				OfficeOfExchangeVO destOfficeOfExchangeVO = mailController
						.validateOfficeOfExchange(scannedMailDetailsVO.getCompanyCode(), mailBagVOFromUpload.getDoe());
				if (destOfficeOfExchangeVO != null && destOfficeOfExchangeVO.getAirportCode() == null) {
					String destOfficeOfExchange = destOfficeOfExchangeVO.getCode();
					destinationPort = findNearestAirportOfCity(mailBagVOFromUpload.getCompanyCode(),
							destOfficeOfExchange);
					destOfficeOfExchangeVO.setAirportCode(destinationPort);
				} else {
					destinationPort = destOfficeOfExchangeVO != null ? destOfficeOfExchangeVO.getAirportCode() : null;
				}
				ZonedDateTime dspDate = localDateUtil.getLocalDate(scannedMailDetailsVO.getAirportCode(), true);
				coterminusAvalilable = mailController.validateCoterminusairports(destinationPort,
						scannedMailDetailsVO.getAirportCode(), MailConstantsVO.RESDIT_DELIVERED, paCode, dspDate);
				if (destinationPort == null || (!destinationPort.equals(airportCode) && !coterminusAvalilable
						&& !(mailUploadController.isCommonCityForAirport(scannedMailDetailsVO.getCompanyCode(),
								scannedMailDetailsVO.getAirportCode(), destinationPort)))) {
					constructAndRaiseException(MailMLDBusniessException.INVALID_DELIVERY_EXCEPTION,
							MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
				}
			}
		}
	}

	private void doMailbagValidation(ScannedMailDetailsVO scannedMailDetailsVO,
			Map<String, Collection<OneTimeVO>> oneTimes, LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		log.info("INSIDE doMailbagValidation : scannedMailDetailsVO =>" + scannedMailDetailsVO);
		Collection<MailbagVO> mailBagVOs = scannedMailDetailsVO.getMailDetails();
		if (mailBagVOs != null && mailBagVOs.size() > 0) {
			Collection<MailbagVO> mails = null;
			try {
				mails = validateScannedMailDetails(mailBagVOs);
			} catch (InvalidMailTagFormatException e) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_MAILTAG, "Invalid MailTag",
						scannedMailDetailsVO);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			if (mails != null) {
				for (MailbagVO mail : mails) {
					if (mail.getErrorDescription() != null) {
						if (mail.getErrorDescription().contains("Origin OE")
								|| mail.getErrorDescription().contains("Destination OE")) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_OFFICEOFEXCHANGE,
									MailHHTBusniessException.INVALID_OFFICEOFEXCHANGE, scannedMailDetailsVO);
						}
					} else if (scannedMailDetailsVO.getErrorDescription() != null
							&& scannedMailDetailsVO.getErrorDescription().trim().length() > 0) {
						if (scannedMailDetailsVO.getErrorDescription()
								.equals(MailHHTBusniessException.MAILBAG_NOT_FOUND_EXCEPTION)) {
							constructAndRaiseException(MailMLDBusniessException.MAILBAG_NOT_FOUND_EXCEPTION,
									MailHHTBusniessException.MAILBAG_NOT_FOUND_EXCEPTION, scannedMailDetailsVO);
						}
					}
					boolean mailCompanyCodevalid = false;
					Collection<OneTimeVO> mailCompanyCodeOneTimeVOs = new ArrayList<OneTimeVO>();
					if (oneTimes != null) {
						mailCompanyCodeOneTimeVOs = oneTimes.get("mailtracking.defaults.companycode");
					}
					if (mailCompanyCodeOneTimeVOs != null) {
						for (OneTimeVO mailCompanyCodeOneTimeVO : mailCompanyCodeOneTimeVOs) {
							if (mailCompanyCodeOneTimeVO.getFieldValue().equalsIgnoreCase(mail.getMailCompanyCode())) {
								mailCompanyCodevalid = true;
							}
						}
					}
					if (!mailCompanyCodevalid && mail.getMailCompanyCode() != null
							&& mail.getMailCompanyCode().trim().length() > 0) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_MAIL_COMPANY_CODE,
								MailHHTBusniessException.INVALID_MAIL_COMPANY_CODE, scannedMailDetailsVO);
					}
				}
			}
			for (MailbagVO mailBagVOFromUpload : mailBagVOs) {
				log.info("validating the Transfer From Carrier Code");
				if (mailBagVOFromUpload.getTransferFromCarrier() != null
						&& mailBagVOFromUpload.getTransferFromCarrier().trim().length() > 0) {
					AirlineValidationVO airlineValidationVO = validateCarrierCode(
							mailBagVOFromUpload.getTransferFromCarrier().toUpperCase(),
							scannedMailDetailsVO.getCompanyCode());
					if (airlineValidationVO == null) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_TRANSFERCARRIERCODE_EXCEPTION,
								MailHHTBusniessException.INVALID_TRANSFERCARRIERCODE_EXCEPTION, scannedMailDetailsVO);
					}
				}
				if (mailBagVOFromUpload.getMailbagId().trim().length() == 29) {
					if (mailBagVOFromUpload.getOoe().equals(mailBagVOFromUpload.getDoe())) {
						constructAndRaiseException(MailMLDBusniessException.EXCHANGE_OFFICE_SAME_EXCEPTION,
								MailHHTBusniessException.EXCHANGE_OFFICE_SAME_EXCEPTION, scannedMailDetailsVO);
					}
					if (oneTimes != null) {
						Collection<OneTimeVO> catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
						Collection<OneTimeVO> hniVOs = oneTimes.get("mailtracking.defaults.highestnumbermail");
						Collection<OneTimeVO> riVOs = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
						if (!checkForMatchInOneTime(mailBagVOFromUpload.getMailCategoryCode(), catVOs)) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_MAIL_CATEGORY_EXCEPTION,
									MailHHTBusniessException.INVALID_MAIL_CATEGORY_EXCEPTION, scannedMailDetailsVO);
						}
						if (!checkForMatchInOneTime(mailBagVOFromUpload.getMailbagId().substring(23, 24), hniVOs)) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_HNI_EXCEPTION,
									constructInvalidHNIException(hniVOs), scannedMailDetailsVO);
						}
						if (!checkForMatchInOneTime(mailBagVOFromUpload.getMailbagId().substring(24, 25), riVOs)) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_RI_EXCEPTION,
									constructInvalidRIException(riVOs), scannedMailDetailsVO);
						}
					}
					if (!validateMailSubClass(scannedMailDetailsVO.getCompanyCode(),
							mailBagVOFromUpload.getMailbagId().substring(13, 15))) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_SUBCLASS,
								MailHHTBusniessException.INVALID_SUBCLASS, scannedMailDetailsVO);
					}
					if (mailBagVOFromUpload.getOoe().equals(mailBagVOFromUpload.getDoe())) {
						constructAndRaiseException(MailMLDBusniessException.EXCHANGE_OFFICE_SAME_EXCEPTION,
								MailHHTBusniessException.EXCHANGE_OFFICE_SAME_EXCEPTION, scannedMailDetailsVO);
					}
					checkIfMailbagExists(mailBagVOFromUpload, scannedMailDetailsVO);
				}
			}
		}
	}

	private void checkIfMailbagFromPaBuilt(MailbagVO mailBagVOFromUpload, ScannedMailDetailsVO scannedMailDetailsVO,
			Mailbag mailBag) throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		if (scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED)
				|| scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_DAMAGED)) {
			ContainerVO containerVO = new ContainerVO();
			ContainerAssignmentVO containerAssignmentVO = null;
			containerVO.setAssignedPort(mailBagVOFromUpload.getScannedPort());
			containerVO.setCarrierId(mailBag.getCarrierId());
			containerVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
			containerVO.setContainerNumber(mailBag.getUldNumber());
			containerVO.setFlightNumber(mailBag.getFlightNumber());
			containerVO.setFlightSequenceNumber(mailBag.getFlightSequenceNumber());
			containerVO.setLegSerialNumber(mailBag.getSegmentSerialNumber());
			if (containerVO.getContainerNumber() != null && !containerVO.getContainerNumber().isEmpty()) {
				containerAssignmentVO = findContainerAssignment(containerVO);
			}
			Container container = null;
			if (containerAssignmentVO != null) {
				containerVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
				containerVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
				containerVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
				try {
					container = findContainer(containerVO);
				} catch (FinderException e) {
					container = null;
				} catch (Exception e) {
					log.debug(e.getMessage());
					constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
				}
				if (container != null && container.getPaBuiltFlag() != null
						&& container.getPaBuiltFlag().equals(MailConstantsVO.FLAG_YES)) {
					if (scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED)) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_OFFLOAD_FROM_PA_BUILT_ULD,
								MailHHTBusniessException.INVALID_OFFLOAD_FROM_PA_BUILT_ULD, scannedMailDetailsVO);
					}
					if (scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_DAMAGED)) {
						mailBagVOFromUpload.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
					}
				}
			}
		}
	}

	private boolean checkForMatchInOneTime(String validatedField, Collection<OneTimeVO> oneTimes) {
		boolean isValid = false;
		if (oneTimes != null) {
			for (OneTimeVO onetime : oneTimes) {
				if (onetime.getFieldValue().equals(validatedField)) {
					isValid = true;
					break;
				}
			}
		}
		return isValid;
	}

	public void doULDValidations(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes,
			MailUploadVO mailUploadVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		ULDDefaultsProxy uLDDefaultsProxy = ContextUtil.getInstance().getBean(ULDDefaultsProxy.class);
		if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
				|| scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED)
				|| scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL)
				|| scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_TRANSFERRED)
				|| scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)
				|| MailConstantsVO.MAIL_STATUS_EXPORT.equals(scannedMailDetailsVO.getProcessPoint())) {
			if (!MailConstantsVO.MAIL_STATUS_EXPORT.equalsIgnoreCase(scannedMailDetailsVO.getProcessPoint())
					&& "U".equals(scannedMailDetailsVO.getContainerType())) {
				if (!validateContainerNumber(scannedMailDetailsVO)) {
					constructAndRaiseException(MailMLDBusniessException.INVALID_ULD_FORMAT,
							MailHHTBusniessException.INVALID_ULD_FORMAT, scannedMailDetailsVO);
				}
			}
		}
		if (isULDIntegrationEnabled()) {
			boolean isValidUld = true;
			boolean isULDType = MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType());
			if (isULDType && scannedMailDetailsVO.getFlightSequenceNumber() > 0) {
				if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
						|| MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
					FlightDetailsVO flightDetails = new FlightDetailsVO();
					Collection<ULDInFlightVO> uldInFlightVos = new ArrayList<ULDInFlightVO>();
					ULDInFlightVO uldInFlightVo = new ULDInFlightVO();
					flightDetails.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
					if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())
							|| MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())) {
						flightDetails.setCurrentAirport(scannedMailDetailsVO.getPol());
						flightDetails.setDirection(MailConstantsVO.EXPORT);
					}
					if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
						flightDetails.setCurrentAirport(scannedMailDetailsVO.getPou());
						flightDetails.setDirection(MailConstantsVO.IMPORT);
					}
					if (scannedMailDetailsVO.getFlightSequenceNumber() > 0) {
						flightDetails.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
						flightDetails.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
						flightDetails.setFlightCarrierIdentifier(scannedMailDetailsVO.getCarrierId());
					}
					uldInFlightVo.setUldNumber(scannedMailDetailsVO.getContainerNumber());
					uldInFlightVo.setPointOfLading(scannedMailDetailsVO.getPol());
					uldInFlightVo.setPointOfUnLading(scannedMailDetailsVO.getPou());
					uldInFlightVos.add(uldInFlightVo);
					flightDetails.setUldInFlightVOs(uldInFlightVos);
					Collection<String> uldNumbers = new ArrayList<String>();
					uldNumbers.add(scannedMailDetailsVO.getContainerNumber());
					try {
						uLDDefaultsProxy.validateULDsForOperation(flightDetails);
					} catch (ULDDefaultsProxyException exception) {
						MailbagVO mailbagVO = new MailbagVO();
						mailbagVO.setErrorCode(exception.getMessage());
						if (isErrorMessage(exception)) {
							if (!MailConstantsVO.ONLOAD_MESSAGE.equals(scannedMailDetailsVO.getMailSource())) {
								raiseULDException(scannedMailDetailsVO, MailMLDBusniessException.ULD_VALID_FAILED);
							} else if (MailConstantsVO.ONLOAD_MESSAGE.equals(scannedMailDetailsVO.getMailSource())
									&& exception != null
									&& MailMLDBusniessException.MAIL_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM
											.equals(exception.getMessage())) {
								constructAndRaiseException(MailMLDBusniessException.ULD_VALID_FAILED,
										MailHHTBusniessException.CONTAINER_NOTIN_INVENTORY, scannedMailDetailsVO);
							}
							mailbagVO.setErrorType("Error");
						}
						if (MailConstantsVO.ONLOAD_MESSAGE.equals(scannedMailDetailsVO.getMailSource())
								|| MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())) {
							scannedMailDetailsVO.setValidateULDExists(true);
						} else {
							mailbagVO.setErrorType("Warning");
						}
						scannedMailDetailsVO.getErrorMailDetails().add(mailbagVO);
					} catch (Exception e) {
						log.debug(e.getMessage());
						constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
					}
				}
			}
			if (scannedMailDetailsVO.getContainerNumber() != null
					&& !"".equals(scannedMailDetailsVO.getContainerNumber())
					&& scannedMailDetailsVO.getContainerType() != null
					&& !"".equals(scannedMailDetailsVO.getContainerType())
					&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& !scannedMailDetailsVO.isValidateULDExists()) {
				isValidUld = validateUld(logonAttributes.getCompanyCode(), scannedMailDetailsVO.getContainerNumber());
			}
			if (!isValidUld) {
				raiseULDException(scannedMailDetailsVO, MailMLDBusniessException.ULD_VALID_FAILED);
			}
		}
	}

	/** 
	* @param scannedMailDetailsVO
	* @throws SystemException 
	* @throws MailHHTBusniessException 
	* @throws MailMLDBusniessException 
	* @throws ForceAcceptanceException 
	*/
	private void raiseULDException(ScannedMailDetailsVO scannedMailDetailsVO, String errorCode)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		NeoMastersServiceUtils neoMastersServiceUtils = ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class);
		String sysparValue_uldNotInAirport = null;
		String sysparValue_uldNotInStock = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(ULD_SYSPAR_NOTINAIRPORT);
		systemParameters.add(ULD_SYSPAR_NOTINSTOCK);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue_uldNotInAirport = systemParameterMap.get(ULD_SYSPAR_NOTINAIRPORT);
			sysparValue_uldNotInStock = systemParameterMap.get(ULD_SYSPAR_NOTINSTOCK);
		}
		if ("W".equals(sysparValue_uldNotInAirport) || "W".equals(sysparValue_uldNotInStock)) {
			constructAndroidException(errorCode, MailHHTBusniessException.ULD_NOT_EXIST, scannedMailDetailsVO);
		} else {
			constructAndRaiseException(errorCode, MailHHTBusniessException.ULD_NOT_EXIST, scannedMailDetailsVO);
		}
	}

	private boolean isErrorMessage(ULDDefaultsProxyException exception) {
		String WARNING = "warning";
		boolean errorFlag = true;
		if (exception != null && exception.getMessage().contains(WARNING)) {
			errorFlag = false;
		}
		return errorFlag;
	}
	/**
	* @param errorCode
	* @param errorDescriptionForHHT
	* @param scannedMailDetailsVO
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	*/
	public ScannedMailDetailsVO constructAndroidException(String errorCode, String errorDescriptionForHHT,
			ScannedMailDetailsVO scannedMailDetailsVO) {
		if (errorDescriptionForHHT != null && errorDescriptionForHHT.length() > 90) {
			errorDescriptionForHHT = errorDescriptionForHHT.substring(0, 90);
		} else if (errorDescriptionForHHT == null) {
			errorDescriptionForHHT = "Exception";
		}
		if (scannedMailDetailsVO != null) {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setErrorCode(errorCode);
			mailbagVO.setErrorType("Warning");
			if (MailConstantsVO.CONTAINER_REASSIGN_ERROR.equals(errorCode)) {
				mailbagVO.setErrorType("Error");
			}
			mailbagVO.setErrorDescription(errorDescriptionForHHT);
			scannedMailDetailsVO.getErrorMailDetails().add(mailbagVO);
		}
		return scannedMailDetailsVO;
	}

	/** 
	* Method : MailController.validateCarrierCode Added by : Used for : Parameters : @param scannedMailDetailsVO Parameters : @return Parameters : @throws SystemException Return type : boolean Modified for ICRD-94096
	*/
	public AirlineValidationVO validateCarrierCode(String carrierCode, String companyCode) {
		//SharedAirlineProxy sharedAirlineProxy = ContextUtil.getInstance().getBean(SharedAirlineProxy.class);
		log.debug("MAILCONTROLLER" + " : " + "validateCarrierCode" + " Entering");
		AirlineValidationVO airlineValidationVO = null;
		if (carrierCode != null && !"".equals(carrierCode)) {
			try {
				airlineValidationVO = ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class).validateAlphaCode(companyCode, carrierCode);
			} catch (SharedProxyException e) {
				log.debug(e.getMessage());
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		return airlineValidationVO;
	}

	/** 
	* Method : MailController.validateContainerNumber Added by : Used for : Parameters : @param scannedMailDetailsVO Parameters : @return Parameters : @throws SystemException Return type : boolean
	*/
	public boolean validateContainerNumber(ScannedMailDetailsVO scannedMailDetailsVO) {
		SharedULDProxy sharedULDProxy = ContextUtil.getInstance().getBean(SharedULDProxy.class);
		log.debug("MAILCONTROLLER" + " : " + "validateContainerNumber" + " Entering");
		ULDValidationVO uldValidationVO = null;
		String containerNumber = scannedMailDetailsVO.getContainerNumber();
		if (containerNumber != null && !"".equals(containerNumber)) {
			try {
				uldValidationVO = sharedULDProxy.validateULD(scannedMailDetailsVO.getCompanyCode(), containerNumber);
			} catch (SharedProxyException e) {
				return false;
			}
			if (uldValidationVO != null) {
				return true;
			}
		}
		return false;
	}

	/** 
	* Added by A-5526
	* @param companyCode
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException 
	*/
	private Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		SharedDefaultsProxy sharedDefaultsProxy = ContextUtil.getInstance().getBean(SharedDefaultsProxy.class);
		log.debug("UploadMailDetailsCommand" + " : " + "findOneTimeDescription" + " Entering");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(DMG_RET_REASONCODE);
			fieldValues.add(OFL_REASONCODE);
			fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			fieldValues.add("mailtracking.defaults.highestnumbermail");
			fieldValues.add("mailtracking.defaults.companycode");
			oneTimes = sharedDefaultsProxy.findOneTimeValues(companyCode, fieldValues);
		} catch (BusinessException e) {
			log.debug("ProxyException Caught");
			log.info(e.getMessage());
			constructAndRaiseException(e.getMessage(), e.getMessage(), null);
		} catch (Exception e) {
			log.info(e.getMessage());
			constructAndRaiseException(e.getMessage(), e.getMessage(), null);
		}
		log.debug("UploadMailDetailsCommand" + " : " + "findOneTimeDescription" + " Exiting");
		return oneTimes;
	}

	private FlightFilterVO createFlightFilterVO(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes,
			String airportCode) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
			flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
			flightFilterVO.setStation(airportCode);
		} else {
			flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
			flightFilterVO.setStation(airportCode);
		}
		flightFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(scannedMailDetailsVO.getCarrierId());
		flightFilterVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(scannedMailDetailsVO.getFlightDate()));
		flightFilterVO.setAirportCode(airportCode);
		return flightFilterVO;
	}

	/** 
	* @param currentPort
	* @param loginPort
	* @return
	*/
	private String constructDifferentPortMessageForUpload(String currentPort, String loginPort) {
		StringBuilder messageString = new StringBuilder("Mailbag is currently at port ");
		messageString.append(currentPort).append(" which is different from login port ").append(loginPort);
		return messageString.toString();
	}

	/** 
	* @param carrierCode
	* @param flightNumber
	* @param fltDat
	* @return
	*/
	private String constructClosedFlightMessageForUpload(String carrierCode, String flightNumber,
			ZonedDateTime fltDat) {
		StringBuilder messageString = new StringBuilder("Mailbag is currently in a closed flight ");
		messageString.append(carrierCode).append("-").append(flightNumber).append(" : ")
				.append(fltDat.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		return messageString.toString();
	}

	/** 
	* This method will validate and set Exception in each mailbagVO, according to the nature of the error. Exception can be a Warning or a Fatal error(which cannot be resolved) For a warining, the required values for continuation is set in the MailbagVO.
	* @param mailbagVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException 
	*/
	private void validateMailbagsForUploadAcceptance(MailbagVO mailbagVO, ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(createMailbagPK(mailbagVO.getCompanyCode(), mailbagVO));
			} catch (FinderException exception) {
				if (mailbagVO.getMailbagId().length() == 12) {
					String routIndex = mailbagVO.getMailbagId().substring(4, 8);
					Collection<RoutingIndexVO> routingIndexVOs = new ArrayList<RoutingIndexVO>();
					RoutingIndexVO routingIndexFilterVO = new RoutingIndexVO();
					routingIndexFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
					routingIndexFilterVO.setRoutingIndex(routIndex);
					routingIndexFilterVO.setScannedDate(mailbagVO.getScannedDate());
					routingIndexVOs = findRoutingIndex(routingIndexFilterVO);
					if (routingIndexVOs.size() == 0) {
						constructAndRaiseException(MailMLDBusniessException.PLAN_ROUTE_MISSING,
								MailHHTBusniessException.PLAN_ROUTE_MISSING, scannedMailDetailsVO);
					}
				}
			} catch (Exception e) {
				log.info(e.getMessage());
			}
			if (mailbag != null && !MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())) {
				String fltCarCode = "";
				ZonedDateTime fltDat = null;
				int legSerNum = 0;
				if (mailbag.getFlightSequenceNumber() > 0) {
					if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbag.getLatestStatus())
							|| (mailbagVO.getScannedPort().equals(mailbag.getScannedPort())
									&& MailConstantsVO.OPERATION_INBOUND.equals(mailbag.getOperationalStatus()))) {
						if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbag.getLatestStatus())) {
							constructAndRaiseException(MailMLDBusniessException.MAILBAG_DELIVERED_EXCEPTION,
									MailHHTBusniessException.MAILBAG_DELIVERED_EXCEPTION, scannedMailDetailsVO);
						}
					}
				} else {
					if ((mailbag.getLatestStatus() != null
							&& !MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus()))
							&& !(mailbagVO.getScannedPort().equals(mailbag.getScannedPort()))) {
						log.debug("Current port for mailbag is different from login port");
						constructAndRaiseException(MailMLDBusniessException.INVALID_LOGIN_PORT_EXCEPTION,
								constructDifferentPortMessageForUpload(mailbag.getScannedPort(),
										mailbagVO.getScannedPort()),
								scannedMailDetailsVO);
					} else {
						if (mailbag.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
							if (mailbagVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT
									|| mailbagVO.getCarrierId() != mailbag.getCarrierId()) {
							} else {
								if (mailbagVO.getCarrierId() == mailbag.getCarrierId()) {
									if (mailbagVO.getContainerNumber().equals(mailbag.getUldNumber())) {
									} else {
										log.debug("Reassign Mailbag ");
									}
								}
							}
						} else {
							if ((mailbag.getCarrierId() == mailbagVO.getCarrierId())
									&& (mailbag.getFlightNumber() != null
											&& mailbag.getFlightNumber().equals(mailbagVO.getFlightNumber()))
									&& (mailbag.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber())) {
								if (!mailbagVO.getContainerNumber().equals(mailbag.getUldNumber())) {
									log.debug("Reassign Mailbag ");
									mailbagVO.setReassignFlag("Y");
									boolean isFlightClosed = false;
									OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
									operationalFlightVO.setCarrierCode(fltCarCode);
									operationalFlightVO.setCarrierId(mailbag.getCarrierId());
									operationalFlightVO.setCompanyCode(mailbagVO.getCompanyCode());
									operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
									operationalFlightVO.setFlightDate(fltDat);
									operationalFlightVO.setFlightNumber(mailbag.getFlightNumber());
									operationalFlightVO.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
									operationalFlightVO.setLegSerialNumber(legSerNum);
									operationalFlightVO.setPol(mailbag.getScannedPort());
									isFlightClosed = isFlightClosedForOperations(operationalFlightVO);
									log.debug("" + "isFlightClosed----->" + " " + isFlightClosed);
									if (isFlightClosed) {
										constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
												constructClosedFlightMessageForUpload(fltCarCode,
														mailbag.getFlightNumber(), fltDat),
												scannedMailDetailsVO);
									}
								}
							} else {
								mailbagVO.setReassignFlag("Y");
								boolean isFlightClosed = false;
								OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
								operationalFlightVO.setCarrierCode(fltCarCode);
								operationalFlightVO.setCarrierId(mailbag.getCarrierId());
								operationalFlightVO.setCompanyCode(mailbagVO.getCompanyCode());
								operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
								operationalFlightVO.setFlightDate(fltDat);
								operationalFlightVO.setFlightNumber(mailbag.getFlightNumber());
								operationalFlightVO.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
								operationalFlightVO.setLegSerialNumber(legSerNum);
								operationalFlightVO.setPol(mailbag.getScannedPort());
								isFlightClosed = isFlightClosedForOperations(operationalFlightVO);
								log.debug("" + "isFlightClosed----->" + " " + isFlightClosed);
								if (isFlightClosed) {
									constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
											constructClosedFlightMessageForUpload(fltCarCode, mailbag.getFlightNumber(),
													fltDat),
											scannedMailDetailsVO);
								}
							}
						}
					}
				}
			}
		}
	}

	/** 
	* validateMailbagsForUploadArrival
	* @param scannedMailbagVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException 
	*/
	private void validateMailbagsForUploadArrival(MailbagVO scannedMailbagVO, ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		boolean isDuplicate = false;
		isDuplicate = checkDuplicateMailbag(scannedMailbagVO);
		if (MailbagHistory.isMailbagAlreadyArrived(scannedMailbagVO) && !isDuplicate) {
			constructAndRaiseException(MailMLDBusniessException.MAILBAG_ALREADY_ARRIVAL_EXCEPTION,
					MailHHTBusniessException.MAILBAG_ALREADY_ARRIVAL_EXCEPTION, scannedMailDetailsVO);
		} else {
			if (MailbagVO.OPERATION_FLAG_INSERT.equals(scannedMailbagVO.getOperationalFlag())) {
				Mailbag mailbag = null;
				try {
					mailbag = Mailbag.find(createMailbagPK(scannedMailbagVO.getCompanyCode(), scannedMailbagVO));
				} catch (FinderException exception) {
					if (scannedMailbagVO.getMailbagId().length() == 12) {
						String routIndex = scannedMailbagVO.getMailbagId().substring(4, 8);
						Collection<RoutingIndexVO> routingIndexVOs = new ArrayList<RoutingIndexVO>();
						RoutingIndexVO routingIndexFilterVO = new RoutingIndexVO();
						routingIndexFilterVO.setCompanyCode(scannedMailbagVO.getCompanyCode());
						routingIndexFilterVO.setRoutingIndex(routIndex);
						routingIndexFilterVO.setScannedDate(scannedMailbagVO.getScannedDate());
						routingIndexVOs = findRoutingIndex(routingIndexFilterVO);
						if (routingIndexVOs.size() == 0) {
							constructAndRaiseException(MailMLDBusniessException.PLAN_ROUTE_MISSING,
									MailHHTBusniessException.PLAN_ROUTE_MISSING, scannedMailDetailsVO);
						}
					}
				} catch (Exception e) {
					log.info(e.getMessage());
				}
			}
			if (scannedMailbagVO.getFlightSequenceNumber() > 0) {
				boolean isFlightClosed = false;
				OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
				operationalFlightVO.setCarrierCode(scannedMailbagVO.getCarrierCode());
				operationalFlightVO.setCarrierId(scannedMailbagVO.getCarrierId());
				operationalFlightVO.setCompanyCode(scannedMailbagVO.getCompanyCode());
				operationalFlightVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
				operationalFlightVO.setFlightDate(scannedMailbagVO.getFlightDate());
				operationalFlightVO.setFlightNumber(scannedMailbagVO.getFlightNumber());
				operationalFlightVO.setFlightSequenceNumber(scannedMailbagVO.getFlightSequenceNumber());
				operationalFlightVO.setLegSerialNumber(scannedMailbagVO.getLegSerialNumber());
				operationalFlightVO.setPou(scannedMailbagVO.getScannedPort());
				try {
					isFlightClosed = isFlightClosedForInboundOperations(operationalFlightVO);
				} catch (Exception e) {
					log.info(e.getMessage());
				}
				log.debug("" + "isFlightClosed----->" + " " + isFlightClosed);
				if (isFlightClosed) {
					if (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())) {
						constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
								constructClosedFlightMessageForUpload(scannedMailbagVO.getCarrierCode(),
										scannedMailbagVO.getFlightNumber(), scannedMailbagVO.getFlightDate()),
								scannedMailDetailsVO);
					} else {
						constructAndRaiseException(MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION,
								MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
					}
				}
			}
		}
	}

	/** 
	* validateMailbagsForUploadReturn
	* @param scannedMailbagVO
	* @param scannedMailDetailsVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException 
	*/
	private void validateMailbagsForUploadReturn(MailbagVO scannedMailbagVO, ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		MailbagVO newmailbagVO = Mailbag.findMailbagDetailsForUpload(scannedMailbagVO);
		Collection<MailbagHistoryVO> mailhistroryVos = null;
		String acceptedPort = null;
		String destinationPort = null;
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		if (newmailbagVO != null) {
			if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_ALREADY_RETURNED,
						MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION, scannedMailDetailsVO);
			} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_ALREADY_DELIVERED,
						MailHHTBusniessException.MAILBAG_DELIVERED_EXCEPTION, scannedMailDetailsVO);
			} else {
				boolean isFlightClosed = false;
				OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
				operationalFlightVO.setCarrierCode(scannedMailbagVO.getCarrierCode());
				operationalFlightVO.setCarrierId(scannedMailbagVO.getCarrierId());
				operationalFlightVO.setCompanyCode(scannedMailbagVO.getCompanyCode());
				operationalFlightVO.setFlightDate(scannedMailbagVO.getFlightDate());
				operationalFlightVO.setFlightNumber(scannedMailbagVO.getFlightNumber());
				operationalFlightVO.setFlightSequenceNumber(scannedMailbagVO.getFlightSequenceNumber());
				operationalFlightVO.setLegSerialNumber(scannedMailbagVO.getLegSerialNumber());
				operationalFlightVO.setPol(scannedMailbagVO.getPol());
				operationalFlightVO.setPou(scannedMailbagVO.getPou());
				if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailbagVO.getLatestStatus())) {
					operationalFlightVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
					isFlightClosed = isFlightClosedForInboundOperations(operationalFlightVO);
				} else {
					operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
					isFlightClosed = isFlightClosedForOperations(operationalFlightVO);
				}
				log.debug("" + "isFlightClosed----->" + " " + isFlightClosed);
				if (isFlightClosed) {
					constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
							MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
				}
			}
			try {
				mailhistroryVos =mailController.findMailbagHistories(scannedMailDetailsVO.getCompanyCode(),
						scannedMailbagVO.getMailbagId(), 0l);
			} finally {
			}
			if (mailhistroryVos != null && mailhistroryVos.size() > 0) {
				for (MailbagHistoryVO mailbagHistoryVO : mailhistroryVos) {
					if ("ACP".equals(mailbagHistoryVO.getMailStatus())) {
						acceptedPort = mailbagHistoryVO.getScannedPort();
						break;
					}
				}
			}
			String poaCode = null;
			if (newmailbagVO.getPaCode() != null) {
				poaCode = newmailbagVO.getPaCode();
			} else {
				OfficeOfExchangeVO originOfficeOfExchangeVO;
				originOfficeOfExchangeVO = mailController
						.validateOfficeOfExchange(scannedMailbagVO.getCompanyCode(), scannedMailbagVO.getOoe());
				poaCode = originOfficeOfExchangeVO.getPoaCode();
			}
			boolean coterminusAvalilable = false;
			if (acceptedPort != null && acceptedPort.equals(scannedMailDetailsVO.getAirportCode())) {
				coterminusAvalilable = true;
			} else {
				if (acceptedPort != null) {
					coterminusAvalilable = mailController.validateCoterminusairports(acceptedPort,
							scannedMailDetailsVO.getAirportCode(), MailConstantsVO.RESDIT_RECEIVED, poaCode,
							newmailbagVO.getConsignmentDate())
							|| mailController.validateCoterminusairports(acceptedPort,
									scannedMailDetailsVO.getAirportCode(), MailConstantsVO.RESDIT_UPLIFTED, poaCode,
									newmailbagVO.getConsignmentDate());
				}
			}
			if (newmailbagVO.getPou() != null && !newmailbagVO.getPou().equals(scannedMailDetailsVO.getAirportCode())
					&& !coterminusAvalilable) {
				if (!(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(newmailbagVO.getLatestStatus()))
						&& !(newmailbagVO.getScannedPort().equals(scannedMailDetailsVO.getAirportCode()))) {
					constructAndRaiseException(MailConstantsVO.RETURN_NOT_POSSIBLE_AT_IMPORT,
							MailHHTBusniessException.RETURN_NOT_POSSIBLE_AT_THIS_PORT, scannedMailDetailsVO);
				}
			}
			if (scannedMailbagVO.getDestination() != null && scannedMailbagVO.getDestination().trim().length() > 0) {
				destinationPort = scannedMailbagVO.getDestination();
			} else {
				OfficeOfExchangeVO destOfficeOfExchangeVO = mailController
						.validateOfficeOfExchange(scannedMailDetailsVO.getCompanyCode(), scannedMailbagVO.getDoe());
				if (destOfficeOfExchangeVO != null && destOfficeOfExchangeVO.getAirportCode() == null) {
					String destOfficeOfExchange = destOfficeOfExchangeVO.getCode();
					destinationPort = findNearestAirportOfCity(scannedMailbagVO.getCompanyCode(), destOfficeOfExchange);
					destOfficeOfExchangeVO.setAirportCode(destinationPort);
				} else {
					destinationPort = destOfficeOfExchangeVO != null ? destOfficeOfExchangeVO.getAirportCode() : null;
				}
			}
			boolean isAutoArrivalEnabled = ContextUtil.getInstance().getBean(MailUploadController.class).checkAutoArrival(scannedMailDetailsVO);
			if (isAutoArrivalEnabled) {
				String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
				if (MailConstantsVO.FLAG_YES.equals(isCoterminusConfigured) && destinationPort != null
						&& scannedMailDetailsVO.getAirportCode() != null && poaCode != null) {
					coterminusAvalilable = mailController.validateCoterminusairports(destinationPort,
							scannedMailDetailsVO.getAirportCode(), MailConstantsVO.RESDIT_DELIVERED, poaCode,
							newmailbagVO.getConsignmentDate());
				}
				if (coterminusAvalilable || (destinationPort != null
						&& (destinationPort.equals(scannedMailDetailsVO.getAirportCode())))) {
					constructAndRaiseException(MailConstantsVO.RETURN_NOT_POSSIBLE_AT_IMPORT,
							MailHHTBusniessException.RETURN_NOT_POSSIBLE_AT_DEST, scannedMailDetailsVO);
				}
			} else if (acceptedPort == null
					|| (acceptedPort != null && !(acceptedPort.equals(scannedMailDetailsVO.getAirportCode())))
							&& !coterminusAvalilable) {
				constructAndRaiseException(MailConstantsVO.RETURN_NOT_POSSIBLE_AT_IMPORT,
						MailHHTBusniessException.RETURN_NOT_POSSIBLE_AT_IMPORT, scannedMailDetailsVO);
			}
		} else {
			boolean isAutoArrivalEnabled = ContextUtil.getInstance().getBean(MailUploadController.class).checkAutoArrival(scannedMailDetailsVO);
			destinationPort = findNearestAirportOfCity(scannedMailbagVO.getCompanyCode(), scannedMailbagVO.getDoe());
			if (isAutoArrivalEnabled) {
				if (destinationPort.equals(scannedMailbagVO.getScannedPort())) {
					constructAndRaiseException(MailConstantsVO.RETURN_NOT_POSSIBLE_AT_IMPORT,
							MailHHTBusniessException.RETURN_NOT_POSSIBLE_AT_DEST, scannedMailDetailsVO);
				}
			} else
				constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_MAILBAG_DOESNOT_EXISTS,
						MailHHTBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION, scannedMailDetailsVO);
		}
	}

	/** 
	* validateMailbagsForUploadReturn
	* @param scannedMailbagVO
	* @param scannedMailDetailsVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException 
	*/
	private void validateMailbagsForUploadDamage(MailbagVO scannedMailbagVO, ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		MailbagVO newmailbagVO = Mailbag.findMailbagDetailsForUpload(scannedMailbagVO);
		if (newmailbagVO != null) {
			if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_RETURN_EXCEPTION,
						MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION, scannedMailDetailsVO);
			} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_DELIVERED_EXCEPTION,
						MailHHTBusniessException.MAILBAG_DELIVERED_EXCEPTION, scannedMailDetailsVO);
			}
		}
	}

	/** 
	* validateMailbagsForUploadReturn
	* @param scannedMailbagVO
	* @param scannedMailDetailsVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException 
	*/
	private void validateMailbagsForUploadOffload(MailbagVO scannedMailbagVO, ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		MailbagVO existingMailbagVO = Mailbag.findMailbagDetailsForUpload(scannedMailbagVO);
		if (existingMailbagVO != null) {
			if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_RETURN_EXCEPTION,
						MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION, scannedMailDetailsVO);
			} else if (!existingMailbagVO.getScannedPort().equals(scannedMailbagVO.getScannedPort())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION,
						MailHHTBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION, scannedMailDetailsVO);
			} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_DELIVERED_EXCEPTION,
						MailHHTBusniessException.MAILBAG_DELIVERED_EXCEPTION, scannedMailDetailsVO);
			} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_ALREADY_OFFLOADED_EXCEPTION,
						MailHHTBusniessException.MAILBAG_ALREADY_OFFLOADED_EXCEPTION, scannedMailDetailsVO);
			} else {
				if (scannedMailbagVO.getCarrierCode().equals(existingMailbagVO.getCarrierCode())
						&& scannedMailbagVO.getCarrierId() == existingMailbagVO.getCarrierId()
						&& scannedMailbagVO.getFlightNumber().equals(existingMailbagVO.getFlightNumber())
						&& scannedMailbagVO.getFlightSequenceNumber() == existingMailbagVO.getFlightSequenceNumber()) {
					boolean isFlightClosed = false;
					OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
					operationalFlightVO.setCarrierCode(scannedMailbagVO.getCarrierCode());
					operationalFlightVO.setCarrierId(scannedMailbagVO.getCarrierId());
					operationalFlightVO.setCompanyCode(scannedMailbagVO.getCompanyCode());
					operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
					operationalFlightVO.setFlightDate(scannedMailbagVO.getFlightDate());
					operationalFlightVO.setFlightNumber(scannedMailbagVO.getFlightNumber());
					operationalFlightVO.setFlightSequenceNumber(scannedMailbagVO.getFlightSequenceNumber());
					operationalFlightVO.setLegSerialNumber(scannedMailbagVO.getLegSerialNumber());
					operationalFlightVO.setPol(scannedMailbagVO.getPol());
					if (operationalFlightVO.getFlightSequenceNumber() > 0) {
						log.debug("Carrier assigned----->");
						isFlightClosed = isFlightClosedForOperations(operationalFlightVO);
					}
					log.debug("" + "isFlightClosed----->" + " " + isFlightClosed);
					if (operationalFlightVO.getFlightSequenceNumber() < 0) {
						log.debug("Mailbag from carrier cannot be offloaded----->");
						constructAndRaiseException(MailMLDBusniessException.OFFLOAD_FROM_CARRIER_EXCEPTION,
								MailHHTBusniessException.OFFLOAD_FROM_CARRIER_EXCEPTION, scannedMailDetailsVO);
					}
					if (!isFlightClosed) {
						constructAndRaiseException(MailMLDBusniessException.MAIL_CANNOT_OFFLOAD,
								constructFlightNotClosedMessageForUpload(scannedMailbagVO.getCarrierCode(),
										scannedMailbagVO.getFlightNumber(), scannedMailbagVO.getFlightDate()),
								scannedMailDetailsVO);
					}
				} else {
					constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_MAILBAG_DOESNOT_EXISTS,
							MailHHTBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION, scannedMailDetailsVO);
				}
			}
		}
	}

	/** 
	* Method		:	MailtrackingDefaultsValidator.constructInvalidRIException Added by 	:	A-4810 on 21-Sep-2015 Used for 	:   throwing error as part of ICRD-127210 Parameters	:	@param riVOs Parameters	:	@return Return type	: 	String
	*/
	private String constructInvalidRIException(Collection<OneTimeVO> riVOs) {
		String errMsg = null;
		StringBuilder messageString = new StringBuilder("Invalid Registered/Insured Indicator value. ");
		if (riVOs != null && riVOs.size() > 0) {
			messageString.append("RI can be either ");
			for (OneTimeVO riVO : riVOs) {
				messageString.append(riVO.getFieldValue()).append(",");
			}
		}
		errMsg = messageString.toString();
		return errMsg.substring(0, errMsg.length() - 1);
	}

	/** 
	* Method		:	MailtrackingDefaultsValidator.constructInvalidHNIException Added by 	:	A-4810 on 21-Sep-2015 Used for 	:   throwing error as part of ICRD-127210 Parameters	:	@param hniVOs Parameters	:	@return Return type	: 	String
	*/
	private String constructInvalidHNIException(Collection<OneTimeVO> hniVOs) {
		String errMsg = null;
		StringBuilder messageString = new StringBuilder("Invalid Highest Number indicator value. ");
		errMsg = messageString.toString();
		return errMsg.substring(0, errMsg.length() - 1);
	}

	/** 
	* @param carrierCode
	* @param flightNumber
	* @param fltDat
	* @return
	*/
	private String constructFlightNotClosedMessageForUpload(String carrierCode, String flightNumber,
			ZonedDateTime fltDat) {
		StringBuilder messageString = new StringBuilder("Cannot Offload. Mailbag is currently in an open flight ");
		messageString.append(carrierCode).append("-").append(flightNumber).append(" : ")
				.append(fltDat != null ? fltDat.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)) : "");
		return messageString.toString();
	}

	/** 
	* validateMailbagsForUploadDeliver
	* @param scannedMailbagVO
	* @param scannedMailDetailsVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException 
	*/
	private void validateMailbagsForUploadDeliver(MailbagVO scannedMailbagVO, ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		boolean isDuplicate = false;
		MailbagVO newmailbagVO = null;
		isDuplicate = checkDuplicateMailbag(scannedMailbagVO);
		if (!isDuplicate) {
			newmailbagVO = Mailbag.findMailbagDetailsForUpload(scannedMailbagVO);
		}
		if (newmailbagVO != null) {
			if (scannedMailDetailsVO.getFlightNumber() != null
					&& (scannedMailDetailsVO.getFlightNumber().trim().length() > 0)) {
				ZonedDateTime flightDate = newmailbagVO.getFlightDate();
				if (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())) {
					flightDate = localDateUtil.getLocalDateTime(newmailbagVO.getFlightDate(), null);
				}
				if ((!newmailbagVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
						|| scannedMailbagVO.getFlightSequenceNumber() != scannedMailDetailsVO.getFlightSequenceNumber())
						&& (newmailbagVO.getScannedPort().equals(scannedMailDetailsVO.getAirportCode()))) {
					constructAndRaiseException(MailMLDBusniessException.ARRIVED_FLIGHT_EXCEPTION,
							"Mailbag is arrived in another flight.So can't deliver ", scannedMailDetailsVO);
				}
			}
			if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_RETURN_EXCEPTION,
						MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION, scannedMailDetailsVO);
			} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_DELIVERED_EXCEPTION,
						MailHHTBusniessException.MAILBAG_DELIVERED_EXCEPTION, scannedMailDetailsVO);
			} else {
				boolean isFlightClosed = false;
				OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
				operationalFlightVO.setCarrierCode(scannedMailbagVO.getCarrierCode());
				operationalFlightVO.setCarrierId(scannedMailbagVO.getCarrierId());
				operationalFlightVO.setCompanyCode(scannedMailbagVO.getCompanyCode());
				operationalFlightVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
				operationalFlightVO.setFlightDate(scannedMailbagVO.getFlightDate());
				operationalFlightVO.setFlightNumber(scannedMailbagVO.getFlightNumber());
				operationalFlightVO.setFlightSequenceNumber(scannedMailbagVO.getFlightSequenceNumber());
				operationalFlightVO.setLegSerialNumber(scannedMailbagVO.getLegSerialNumber());
				operationalFlightVO.setPol(scannedMailbagVO.getPol());
				operationalFlightVO.setPou(scannedMailbagVO.getPou());
				try {
					isFlightClosed = isFlightClosedForInboundOperations(operationalFlightVO);
				} catch (Exception e) {
					log.info(e.getMessage());
				}
				log.debug("" + "isFlightClosed----->" + " " + isFlightClosed);
				if (isFlightClosed) {
					if (MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())) {
						constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
								constructClosedFlightMessageForUpload(scannedMailbagVO.getCarrierCode(),
										scannedMailbagVO.getFlightNumber(), scannedMailbagVO.getFlightDate()),
								scannedMailDetailsVO);
					} else {
						constructAndRaiseException(MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION,
								MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
					}
				}
			}
		} else {
		}
	}

	/** 
	* validateMailbagsForUploadReturn
	* @param scannedMailbagVO
	* @param scannedMailDetailsVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException 
	*/
	private void validateMailbagsForUploadTransfer(MailbagVO scannedMailbagVO,
			ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		boolean isAutoArrival = false;
		isAutoArrival = ContextUtil.getInstance().getBean(MailUploadController.class).checkAutoArrival(scannedMailDetailsVO);
		MailbagVO existingMailbagVO = Mailbag.findMailbagDetailsForUpload(scannedMailbagVO);
		if (existingMailbagVO != null) {
			if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_RETURN_EXCEPTION,
						MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION, scannedMailDetailsVO);
			} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_DELIVERED_EXCEPTION,
						MailHHTBusniessException.MAILBAG_DELIVERED_EXCEPTION, scannedMailDetailsVO);
			} else if (MailConstantsVO.FLAG_YES.equals(scannedMailbagVO.getTransferFlag())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_ALREADY_TRANSFERRED_EXCEPTION,
						MailHHTBusniessException.MAILBAG_ALREADY_TRANSFERRED_EXCEPTION, scannedMailDetailsVO);
			}
		} else {
			if (!isAutoArrival) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_ALREADY_TRANSFERRED_EXCEPTION,
						MailHHTBusniessException.INVALID_TRANSFER_EXCEPTION, scannedMailDetailsVO);
			}
		}
	}

	/** 
	* validateMailbagsForUploadReassign
	* @param scannedMailbagVO
	* @param scannedMailDetailsVO
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException 
	*/
	private void validateMailbagsForUploadReassign(MailbagVO scannedMailbagVO,
			ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		MailbagVO existingMailbagVO = Mailbag.findMailbagDetailsForUpload(scannedMailbagVO);
		if (existingMailbagVO != null) {
			if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_RETURN_EXCEPTION,
						MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION, scannedMailDetailsVO);
			} else if (!existingMailbagVO.getScannedPort().equals(scannedMailbagVO.getScannedPort())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION,
						MailHHTBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION, scannedMailDetailsVO);
			} else if (((scannedMailbagVO.getCarrierCode() != null
					&& scannedMailbagVO.getCarrierCode().equals(existingMailbagVO.getCarrierCode()))
					|| (scannedMailbagVO.getCarrierId() == existingMailbagVO.getCarrierId()))
					&& (scannedMailbagVO.getFlightNumber() != null
							&& scannedMailbagVO.getFlightNumber().equals(existingMailbagVO.getFlightNumber()))
					&& scannedMailbagVO.getFlightSequenceNumber() == existingMailbagVO.getFlightSequenceNumber()
					&& (existingMailbagVO.getLegSerialNumber() == scannedMailbagVO.getLegSerialNumber()
							|| (scannedMailbagVO.getLegSerialNumber() == -1))
					&& (existingMailbagVO.getContainerNumber() != null
							&& existingMailbagVO.getContainerNumber().equals(scannedMailbagVO.getContainerNumber()))) {
				boolean valid = false;
				if (scannedMailDetailsVO != null && MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource())
						&& scannedMailDetailsVO.getContainerProcessPoint() == null
						&& MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())) {
					valid = true;
				}
				if (!valid) {
					constructAndRaiseException(MailMLDBusniessException.DUPLICATE_MAILBAG_EXCEPTION,
							MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION, scannedMailDetailsVO);
				}
			} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailbagVO.getLatestStatus())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_DELIVERED_EXCEPTION,
						MailHHTBusniessException.MAILBAG_DELIVERED_EXCEPTION, scannedMailDetailsVO);
			} else if (MailConstantsVO.FLAG_YES.equals(scannedMailbagVO.getTransferFlag())) {
				constructAndRaiseException(MailMLDBusniessException.MAILBAG_ALREADY_TRANSFERRED_EXCEPTION,
						MailHHTBusniessException.MAILBAG_ALREADY_TRANSFERRED_EXCEPTION, scannedMailDetailsVO);
			} else if (existingMailbagVO.getFlightSequenceNumber() > 0
					&& !scannedMailbagVO.isFlightClosureCheckNotNeeded()) {
				OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
				operationalFlightVO.setCarrierCode(existingMailbagVO.getCarrierCode());
				operationalFlightVO.setCarrierId(existingMailbagVO.getCarrierId());
				operationalFlightVO.setCompanyCode(existingMailbagVO.getCompanyCode());
				operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
				operationalFlightVO.setFlightDate(existingMailbagVO.getFlightDate());
				operationalFlightVO.setFlightNumber(existingMailbagVO.getFlightNumber());
				operationalFlightVO.setFlightSequenceNumber(existingMailbagVO.getFlightSequenceNumber());
				operationalFlightVO.setLegSerialNumber(existingMailbagVO.getLegSerialNumber());
				operationalFlightVO.setPol(scannedMailbagVO.getPol());
				boolean isFlightClosed = false;
				isFlightClosed = isFlightClosedForOperations(operationalFlightVO);
				if (isFlightClosed) {
					constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
							constructFlightClosedMessageForUpload(existingMailbagVO.getCarrierCode(),
									existingMailbagVO.getFlightNumber(), existingMailbagVO.getFlightDate()),
							scannedMailDetailsVO);
				}
			}
		} else {
			constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_MAILBAG_DOESNOT_EXISTS,
					MailHHTBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION, scannedMailDetailsVO);
		}
	}

	/** 
	* @param carrierCode
	* @param flightNumber
	* @param fltDat
	* @return
	*/
	private String constructFlightClosedMessageForUpload(String carrierCode, String flightNumber,
			ZonedDateTime fltDat) {
		StringBuilder messageString = new StringBuilder(" Mailbag is currently in a closed flight ");
		messageString.append(carrierCode).append("-").append(flightNumber).append(" : ")
				.append(fltDat.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		return messageString.toString();
	}

	/** 
	* validateMailbagsForUploadReturn
	* @throws SystemException
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws ForceAcceptanceException 
	*/
	private void validateScannedContainersForUpload(ScannedMailDetailsVO scannedMailDetailsVO,
			ContainerAssignmentVO containerAssignmentVO, ContainerVO scannedContainerVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		if (containerAssignmentVO != null) {
			if ("WS".equals(scannedMailDetailsVO.getMailSource())) {
				if ((!MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint()))
						&& scannedContainerVO.getFinalDestination() != null) {
					if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
							&& MailConstantsVO.CONTAINER_STATUS_REASSIGN
									.equals(scannedMailDetailsVO.getContainerProcessPoint())
							&& "-1".equals(scannedMailDetailsVO.getFlightNumber()) && !scannedMailDetailsVO
									.getFlightNumber().equals(containerAssignmentVO.getFlightNumber())) {
						scannedContainerVO.setFinalDestination(containerAssignmentVO.getDestination());
					} else if (!scannedContainerVO.getFinalDestination()
							.equals(scannedMailDetailsVO.getDestination())) {
						scannedContainerVO.setFinalDestination(scannedMailDetailsVO.getDestination());
					}
				}
			}
			if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())) {
				if (MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getOffloadStatus())
						&& ("-1").equals(containerAssignmentVO.getFlightNumber())
						&& containerAssignmentVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT
						&& containerAssignmentVO.getLegSerialNumber() == MailConstantsVO.DESTN_FLT
						&& containerAssignmentVO.getSegmentSerialNumber() == MailConstantsVO.DESTN_FLT) {
					constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_ALREADY_OFFLOADED,
							"Container is already offloaded", scannedMailDetailsVO);
				} else if (MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getOffloadStatus())
						&& ("-1").equals(containerAssignmentVO.getFlightNumber())
						&& containerAssignmentVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT
						&& containerAssignmentVO.getLegSerialNumber() == MailConstantsVO.DESTN_FLT
						&& (containerAssignmentVO.getSegmentSerialNumber() == MailConstantsVO.DESTN_FLT
								|| containerAssignmentVO.getSegmentSerialNumber() == 0)) {
					constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_IN_FLIGHT,
							"Only Containers assigned to a flight can be offloaded", scannedMailDetailsVO);
				} else if (!MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(containerAssignmentVO.getFlightStatus())) {
					constructAndRaiseException(MailMLDBusniessException.FLIGHT_NOT_CLOSED_EXCEPTION,
							MailHHTBusniessException.FLIGHT_NOT_CLOSED_EXCEPTION, scannedMailDetailsVO);
				} else if (!scannedContainerVO.getAssignedPort().equals(containerAssignmentVO.getAirportCode())
						|| containerAssignmentVO.getArrivalFlag() != null
								&& MailConstantsVO.FLAG_YES.equalsIgnoreCase(containerAssignmentVO.getArrivalFlag())) {
					constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_IN_AIRPORT,
							"Container is not available at this airport", scannedMailDetailsVO);
				}
			} else if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())) {
				if (!scannedContainerVO.getAssignedPort().equals(containerAssignmentVO.getAirportCode())) {
					constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_IN_AIRPORT,
							"Container is not available at this airport", scannedMailDetailsVO);
				} else if (containerAssignmentVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
						&& scannedMailDetailsVO.getCarrierCode().equals(containerAssignmentVO.getCarrierCode())
						&& containerAssignmentVO.getFlightSequenceNumber() == scannedMailDetailsVO
								.getFlightSequenceNumber()
						&& containerAssignmentVO.getLegSerialNumber() == scannedMailDetailsVO.getLegSerialNumber()
						&& scannedContainerVO.getAssignedPort().equals(containerAssignmentVO.getAirportCode())) {
					constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_ALREADY_EXIST_IN_SAME_FLIGHT,
							"Container is already exists in the same flight", scannedMailDetailsVO);
				} else if (MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(containerAssignmentVO.getFlightStatus())) {
					constructAndRaiseException(MailMLDBusniessException.FLIGHT_CLOSED_EXCEPTION,
							MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION, scannedMailDetailsVO);
				}
				scannedContainerVO.setReassignFlag(true);
			}
		} else {
			constructAndRaiseException(MailConstantsVO.UPLOAD_EXCEPT_CONTAINER_NOT_ACP_TO_FLT,
					"Container is not accepted to any flight", scannedMailDetailsVO);
		}
	}

	/** 
	* @param mailbagVOToSave
	* @param companyCode
	* @return
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException 
	*/
	private Collection<ShipmentDetailsVO> populateShipmentDetailsVO(MailbagVO mailbagVOToSave, String companyCode,
			ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException, MailHHTBusniessException {
		SharedAreaProxy sharedAreaProxy = ContextUtil.getInstance().getBean(SharedAreaProxy.class);
		log.debug("MailUploadController" + " : " + "populateShipmentDetailsVO" + " Entering");
		log.info("" + "populateShipmentDetailsVO" + " " + mailbagVOToSave);
		ShipmentDetailsVO shipmentDetailsVO = new ShipmentDetailsVO();
		Collection<ShipmentDetailsVO> shipmentDetailsVOs = new ArrayList<ShipmentDetailsVO>();
		Map<String, Collection<String>> detailsMap = new HashMap<String, Collection<String>>();
		MailController mailController=ContextUtil.getInstance().getBean(MailController.class);
		String commodityCode = "";
		Set<String> flightNumberOrg = new HashSet<String>();
		Set<String> flightNumberDst = new HashSet<String>();
		Set<String> flightNumberVia = new HashSet<String>();
		Set<String> carrierOrg = new HashSet<String>();
		Set<String> carrierDst = new HashSet<String>();
		Set<String> carrierVia = new HashSet<String>();
		if (mailbagVOToSave.getOoe() != null && mailbagVOToSave.getDoe() != null) {
			shipmentDetailsVO.setCompanyCode(companyCode);
			shipmentDetailsVO.setOoe(mailbagVOToSave.getOoe());
			shipmentDetailsVO.setDoe(mailbagVOToSave.getDoe());
			Collection<String> officeOfExchanges = new ArrayList<String>();
			String ooe = null;
			String doe = null;
			String origin = null;
			String destination = null;
			String orgCountry = null;
			String desCountry = null;
			ooe = mailbagVOToSave.getOoe();
			doe = mailbagVOToSave.getDoe();
			officeOfExchanges.add(ooe);
			officeOfExchanges.add(doe);
			Collection<String> airportCodes = new ArrayList<String>();
			Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
			AirportValidationVO orgAirportValidationVO = null;
			AirportValidationVO desAirportValidationVO = null;
			origin = mailbagVOToSave.getOrigin();
			destination = mailbagVOToSave.getDestination();
			airportCodes.add(origin);
			airportCodes.add(destination);
			try {
				countryCodeMap = sharedAreaProxy.validateAirportCodes(companyCode, airportCodes);
			} finally {
			}
			if (countryCodeMap != null) {
				orgAirportValidationVO = countryCodeMap.get(origin);
				desAirportValidationVO = countryCodeMap.get(destination);
				if (orgAirportValidationVO != null) {
					orgCountry = orgAirportValidationVO.getCountryCode();
				}
				if (desAirportValidationVO != null) {
					desCountry = desAirportValidationVO.getCountryCode();
				}
			}
			if (origin != null && orgCountry != null) {
				shipmentDetailsVO.setOrgStation(origin);
				shipmentDetailsVO.setOrgCountry(orgCountry);
			}
			if (destination != null && desCountry != null) {
				shipmentDetailsVO.setDstStation(destination);
				shipmentDetailsVO.setDstCountry(desCountry);
			}
			shipmentDetailsVO.setShipmentID(mailbagVOToSave.getMailbagId());
			String orgPaCod = null;
			String dstPaCod = null;
			String subClassGrp = null;
			Collection<MailSubClassVO> mailSubClassVOs = null;
			Collection<String> mailclass = new ArrayList<String>();
			Collection<String> mailsubclass = new ArrayList<String>();
			Collection<String> mailcat = new ArrayList<String>();
			Collection<String> mailsubclassGrp = new ArrayList<String>();
			Collection<String> commodity = new ArrayList<String>();
			try {
				orgPaCod = mailbagVOToSave.getPaCode() != null && mailbagVOToSave.getPaCode().trim().length() > 0
						? mailbagVOToSave.getPaCode()
						:mailController.findPAForOfficeOfExchange(companyCode, mailbagVOToSave.getOoe());
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			try {
				dstPaCod = mailController.findPAForOfficeOfExchange(companyCode, mailbagVOToSave.getDoe());
			} catch (Exception e) {
				log.info(e.getMessage());
			}
			shipmentDetailsVO.setOrgPaCod(orgPaCod);
			shipmentDetailsVO.setDstPaCod(dstPaCod);
			String mailBagId = mailbagVOToSave.getMailbagId();
			StringBuilder flightNumber = new StringBuilder();
			flightNumber.append(scannedMailDetailsVO.getCarrierCode());
			if (scannedMailDetailsVO.getFlightNumber() != null) {
				flightNumber.append("~").append(scannedMailDetailsVO.getFlightNumber());
			}
			flightNumberOrg.add(flightNumber.toString());
			flightNumberVia.add(flightNumber.toString());
			flightNumberDst.add(flightNumber.toString());
			carrierOrg.add(scannedMailDetailsVO.getCarrierCode());
			carrierDst.add(scannedMailDetailsVO.getCarrierCode());
			carrierVia.add(scannedMailDetailsVO.getCarrierCode());
			detailsMap.put("CARORG", carrierOrg);
			detailsMap.put("CARVIA", carrierVia);
			detailsMap.put("CARDST", carrierDst);
			detailsMap.put("FLTNUMORG", flightNumberOrg);
			detailsMap.put("FLTNUMVIA", flightNumberVia);
			detailsMap.put("FLTNUMDST", flightNumberDst);
			if (mailBagId != null && mailBagId.trim().length() > 0) {
				if (mailBagId.length() == 29) {
					mailcat.add(mailBagId.substring(12, 13));
					mailsubclass.add(mailBagId.substring(13, 15));
					mailclass.add(mailBagId.substring(13, 15).substring(0, 1));
					detailsMap.put("MALCLS", mailclass);
					detailsMap.put("MALSUBCLS", mailsubclass);
					detailsMap.put("MALCAT", mailcat);
					try {
						mailSubClassVOs = mailController.findMailSubClassCodes(companyCode,
								mailBagId.substring(13, 15));
					} catch (Exception e) {
						log.debug(e.getMessage());
					}
					if (mailSubClassVOs != null && mailSubClassVOs.size() > 0) {
						subClassGrp = mailSubClassVOs.iterator().next().getSubClassGroup();
						if (subClassGrp != null) {
							mailsubclassGrp.add(subClassGrp);
							detailsMap.put("MALSUBCLSGRP", mailsubclassGrp);
						}
					}
				} else if (isValidMailtag(mailBagId.length())) {
					mailcat.add(mailbagVOToSave.getMailCategoryCode());
					mailsubclass.add(mailbagVOToSave.getMailSubclass());
					mailclass.add(mailbagVOToSave.getMailClass());
					detailsMap.put("MALCLS", mailclass);
					detailsMap.put("MALSUBCLS", mailsubclass);
					detailsMap.put("MALCAT", mailcat);
					try {
						mailSubClassVOs = mailController.findMailSubClassCodes(companyCode,
								mailbagVOToSave.getMailSubclass());
					} catch (Exception e) {
						log.debug(e.getMessage());
					}
					if (mailSubClassVOs != null && mailSubClassVOs.size() > 0) {
						subClassGrp = mailSubClassVOs.iterator().next().getSubClassGroup();
						if (subClassGrp != null) {
							mailsubclassGrp.add(subClassGrp);
							detailsMap.put("MALSUBCLSGRP", mailsubclassGrp);
						}
					}
				}
				CommodityValidationVO commodityValidationVO = null;
				try {
					commodityCode = findSystemParameterValue(DEFAULTCOMMODITYCODE_SYSPARAM);
					commodityValidationVO = mailController.validateCommodity(mailbagVOToSave.getCompanyCode(), commodityCode,
							orgPaCod);
				} finally {
				}
				if (commodityValidationVO != null)
					commodity.add(commodityCode);
				detailsMap.put("COM", commodity);
				shipmentDetailsVO.setMap(detailsMap);
			}
			if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
				shipmentDetailsVO.setApplicableTransaction("MALARR");
			} else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())) {
				shipmentDetailsVO.setApplicableTransaction("MALACP");
			} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
				shipmentDetailsVO.setApplicableTransaction("MALTRA");
			}
			LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
			localDateUtil.getLocalDate(LocalDateMapper.toLocalDate(mailbagVOToSave.getScannedDate(), false));
			shipmentDetailsVO.setShipmentDate(LocalDateMapper.toLocalDate(mailbagVOToSave.getScannedDate(), false));
			shipmentDetailsVOs.add(shipmentDetailsVO);
			log.info("" + "populateShipmentDetailsVO" + " " + shipmentDetailsVOs);
			return shipmentDetailsVOs;
		}
		return null;
	}

	public boolean checkIfPartnerCarrier(String airportCode, String carrierCode) {
		boolean isPartnerCarrier = true;
		MailController mailController=ContextUtil.getInstance().getBean(MailController.class);
		try {
			isPartnerCarrier = mailController.checkIfPartnerCarrier(airportCode, carrierCode);
		} catch (Exception e) {
			log.error("Other Exception Caught");
		}
		return isPartnerCarrier;
	}

	/** 
	* @author A-8236
	* @param scannedMailDetailsVO
	* @throws SystemException
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws ForceAcceptanceException 
	*/
	public ScannedMailDetailsVO validateMailBags(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ScannedMailDetailsVO scannedMailVO = new ScannedMailDetailsVO();
		Collection<MailbagVO> mailBagVOs = scannedMailDetailsVO.getMailDetails();
		MailController mailController=ContextUtil.getInstance().getBean(MailController.class);
		String paCode = null;
		String paCode_int = null;
		String paCode_dom = null;
		String scanPort = null;
		Collection<MailbagVO> newMailbagVOs = new ArrayList<MailbagVO>();
		paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
		paCode_dom = findSystemParameterValue(DOMESTICMRA_USPS);
		boolean latValidation = false;
		String originPort = null;
		boolean coterminusAvalilable = false;
		if (mailBagVOs != null && mailBagVOs.size() > 0) {
			for (MailbagVO mailBagvo : mailBagVOs) {
				if (mailBagvo.getPaCode() == null) {
					paCode = mailController.findPAForOfficeOfExchange(scannedMailDetailsVO.getCompanyCode(),
							mailBagvo.getOoe());
				} else {
					paCode = mailBagvo.getPaCode();
				}
				scanPort = mailBagvo.getScannedPort();
				if (paCode != null && paCode.trim().length() > 0 && ((paCode.equals(paCode_int)
						|| (paCode.equals(paCode_dom)) && scannedMailDetailsVO.getProcessPoint().equals("ACP")))) {
					mailBagvo.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
					mailBagvo.setFlightDate(scannedMailDetailsVO.getFlightDate());
					mailBagvo.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
					mailBagvo.setFlightDate(scannedMailDetailsVO.getFlightDate());
					mailBagvo.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
					mailBagvo.setCarrierId(scannedMailDetailsVO.getCarrierId());
					mailBagvo.setPol(scannedMailDetailsVO.getPol());
					mailBagvo.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
					newMailbagVOs.add(mailBagvo);
				}
				if (MailConstantsVO.FLAG_YES.equals(mailBagvo.getLatValidationNeeded())) {
					Mailbag mailBag = null;
					long sequencenum = 0;
					try {
						sequencenum = mailBagvo.getMailSequenceNumber() == 0
								? Mailbag.findMailBagSequenceNumberFromMailIdr(mailBagvo.getMailbagId(),
										scannedMailDetailsVO.getCompanyCode())
								: mailBagvo.getMailSequenceNumber();
					} finally {
					}
					if (sequencenum > 0) {
						MailbagPK mailbagPk = new MailbagPK();
						mailbagPk.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
						mailbagPk.setMailSequenceNumber(sequencenum);
						try {
							mailBag = Mailbag.find(mailbagPk);
						} catch (FinderException e) {
							log.info("" + "FinderException" + " " + e);
						}
					}
					if (mailBag != null && mailBag.getOrigin() != null) {
						originPort = mailBag.getOrigin();
					} else {
						OfficeOfExchangeVO orgOfficeOfExchangeVO =mailController
								.validateOfficeOfExchange(scannedMailDetailsVO.getCompanyCode(), mailBagvo.getOoe());
						if (orgOfficeOfExchangeVO.getAirportCode() == null) {
							String orgOfficeOfExchange = null;
							orgOfficeOfExchange = orgOfficeOfExchangeVO.getCode();
							originPort = findNearestAirportOfCity(mailBagvo.getCompanyCode(), orgOfficeOfExchange);
							orgOfficeOfExchangeVO.setAirportCode(originPort);
						} else {
							originPort = orgOfficeOfExchangeVO.getAirportCode();
						}
					}
					ZonedDateTime dspDate = localDateUtil.getLocalDate(scanPort, true);
					if (mailBag != null) {
						dspDate = localDateUtil.getLocalDateTime(mailBag.getDespatchDate(), null);
					}
					coterminusAvalilable = mailController.validateCoterminusairports(originPort,
							scannedMailDetailsVO.getAirportCode(), MailConstantsVO.RESDIT_RECEIVED, paCode, dspDate);
					if (originPort.equals(scannedMailDetailsVO.getAirportCode()) || coterminusAvalilable) {
						latValidation = true;
					}
				}
			}
		}
		if (newMailbagVOs != null && newMailbagVOs.size() > 0 && scannedMailDetailsVO.getProcessPoint().equals("ACP"))
			if (latValidation) {
				scannedMailVO =mailController.doLATValidation(newMailbagVOs, true);
			}
		if (scannedMailVO.getErrorDescription() != null) {
			if (scannedMailVO.getErrorDescription().equals(LAT_VIOLATED_ERR)) {
				constructAndroidException(MailConstantsVO.LAT_VIOLATED_ERR, MailHHTBusniessException.LAT_ERROR,
						scannedMailDetailsVO);
				constructAndRaiseException(MailConstantsVO.LAT_VIOLATED_ERR, MailHHTBusniessException.LAT_ERROR,
						scannedMailDetailsVO);
			} else {
				constructAndroidException(MailConstantsVO.LAT_VIOLATED_WAR, MailHHTBusniessException.LAT_WARNING,
						scannedMailDetailsVO);
			}
		}
		return doEmbargoValidationForAndroidMailBag(scannedMailDetailsVO);
	}

	/** 
	* @author A-8236
	* @param scannedMailDetailsVO
	* @throws MailHHTBusniessException 
	* @throws MailMLDBusniessException 
	* @throws SystemException 
	* @throws ForceAcceptanceException 
	*/
	public void validateMailDetailsForAndroid(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		Collection<MailbagVO> mailErrors = new ArrayList<MailbagVO>();
		Mailbag mailBag = null;
		if (scannedMailDetailsVO.getErrorMailDetails() == null
				|| scannedMailDetailsVO.getErrorMailDetails().isEmpty()) {
			scannedMailDetailsVO.setErrorMailDetails(mailErrors);
		}
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		oneTimes = findOneTimeDescription(scannedMailDetailsVO.getCompanyCode());
		Collection<MailbagVO> mailBagVOs = scannedMailDetailsVO.getMailDetails();
		if (mailBagVOs != null && !mailBagVOs.isEmpty()) {
			boolean isAcpAftrError = scannedMailDetailsVO.isForceAcpAfterErr();
			scannedMailDetailsVO.setForceAcpAfterErr(false);
			scannedMailDetailsVO.setMailbagValidationRequired(false);
			Collection<MailbagVO> mails = null;
			try {
				mails = validateScannedMailDetails(mailBagVOs);
			} catch (InvalidMailTagFormatException e) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_MAILTAG, "Invalid MailTag",
						scannedMailDetailsVO);
			}
			if (mails != null) {
				for (MailbagVO mail : mails) {
					MailbagPK mailbagPk = new MailbagPK();
					mailbagPk.setCompanyCode(mail.getCompanyCode());
					mailbagPk.setMailSequenceNumber(
							Mailbag.findMailBagSequenceNumberFromMailIdr(mail.getMailbagId(), mail.getCompanyCode()));
					try {
						mailBag = Mailbag.find(mailbagPk);
					} catch (FinderException e) {
						log.error("" + FINDER_EXCEPTION + " " + e);
					} catch (Exception e) {
						log.debug("" + e.getMessage() + " " + e);
					}
					if (mailBag == null && mail.getErrorDescription() != null) {
						if (mail.getErrorDescription().contains("Origin OE")
								|| mail.getErrorDescription().contains("Destination OE")) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_OFFICEOFEXCHANGE,
									MailHHTBusniessException.INVALID_OFFICEOFEXCHANGE, scannedMailDetailsVO);
						}
					} else if (scannedMailDetailsVO.getErrorDescription() != null
							&& scannedMailDetailsVO.getErrorDescription().trim().length() > 0) {
						if (scannedMailDetailsVO.getErrorDescription()
								.equals(MailHHTBusniessException.MAILBAG_NOT_FOUND_EXCEPTION)) {
							constructAndRaiseException(MailMLDBusniessException.MAILBAG_NOT_FOUND_EXCEPTION,
									MailHHTBusniessException.MAILBAG_NOT_FOUND_EXCEPTION, scannedMailDetailsVO);
						}
					}
					boolean mailCompanyCodevalid = false;
					Collection<OneTimeVO> mailCompanyCodeOneTimeVOs = new ArrayList<OneTimeVO>();
					if (oneTimes != null) {
						mailCompanyCodeOneTimeVOs = oneTimes.get("mailtracking.defaults.companycode");
					}
					if (mailCompanyCodeOneTimeVOs != null) {
						for (OneTimeVO mailCompanyCodeOneTimeVO : mailCompanyCodeOneTimeVOs) {
							if (mailCompanyCodeOneTimeVO.getFieldValue().equalsIgnoreCase(mail.getMailCompanyCode())) {
								mailCompanyCodevalid = true;
							}
						}
					}
					if (!mailCompanyCodevalid && mail.getMailCompanyCode() != null
							&& mail.getMailCompanyCode().trim().length() > 0) {
						constructAndRaiseException(MailMLDBusniessException.INVALID_MAIL_COMPANY_CODE,
								MailHHTBusniessException.INVALID_MAIL_COMPANY_CODE, scannedMailDetailsVO);
					}
				}
				for (MailbagVO mailBagVOFromUpload : mailBagVOs) {
					if ((MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
							|| MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint()))
							&& mailBagVOFromUpload.getDestination() != null
							&& scannedMailDetailsVO.getAirportCode() != null
							&& mailBagVOFromUpload.getDestination().equals(scannedMailDetailsVO.getAirportCode())) {
						boolean canIgnoreDestCheck = false;
						String paCodeForDestinationByPass = findSystemParameterValue(
								"mail.operations.pacodeforvalidationbypass");
						if (mailBagVOFromUpload.getPaCode() != null && paCodeForDestinationByPass != null
								&& paCodeForDestinationByPass.trim().length() > 0) {
							if (paCodeForDestinationByPass.contains(mailBagVOFromUpload.getPaCode())) {
								canIgnoreDestCheck = true;
							}
						}
						if (!canIgnoreDestCheck) {
							constructAndRaiseException(MailMLDBusniessException.MALBAG_OBSCAN_NOT_ALLOWED_DST,
									MailHHTBusniessException.MALBAG_OBSCAN_NOT_ALLOWED_DST, scannedMailDetailsVO);
						}
					}
					log.info("validating the Transfer From Carrier Code");
					if (mailBagVOFromUpload.getTransferFromCarrier() != null
							&& mailBagVOFromUpload.getTransferFromCarrier().trim().length() > 0) {
						AirlineValidationVO airlineValidationVO = validateCarrierCode(
								mailBagVOFromUpload.getTransferFromCarrier().toUpperCase(),
								scannedMailDetailsVO.getCompanyCode());
						if (airlineValidationVO == null) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_TRANSFERCARRIERCODE_EXCEPTION,
									MailHHTBusniessException.INVALID_TRANSFERCARRIERCODE_EXCEPTION,
									scannedMailDetailsVO);
						}
					}
					if (mailBagVOFromUpload.getMailbagId().trim().length() == 29) {
						if (mailBagVOFromUpload.getOoe().equals(mailBagVOFromUpload.getDoe())) {
							constructAndRaiseException(MailMLDBusniessException.EXCHANGE_OFFICE_SAME_EXCEPTION,
									MailHHTBusniessException.EXCHANGE_OFFICE_SAME_EXCEPTION, scannedMailDetailsVO);
						}
						if (oneTimes != null) {
							Collection<OneTimeVO> catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
							Collection<OneTimeVO> hniVOs = oneTimes.get("mailtracking.defaults.highestnumbermail");
							Collection<OneTimeVO> riVOs = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
							if (!checkForMatchInOneTime(mailBagVOFromUpload.getMailCategoryCode(), catVOs)) {
								constructAndRaiseException(MailMLDBusniessException.INVALID_MAIL_CATEGORY_EXCEPTION,
										MailHHTBusniessException.INVALID_MAIL_CATEGORY_EXCEPTION, scannedMailDetailsVO);
							}
							if (!checkForMatchInOneTime(mailBagVOFromUpload.getMailbagId().substring(23, 24), hniVOs)) {
								constructAndRaiseException(MailMLDBusniessException.INVALID_HNI_EXCEPTION,
										constructInvalidHNIException(hniVOs), scannedMailDetailsVO);
							}
							if (!checkForMatchInOneTime(mailBagVOFromUpload.getMailbagId().substring(24, 25), riVOs)) {
								constructAndRaiseException(MailMLDBusniessException.INVALID_RI_EXCEPTION,
										constructInvalidRIException(riVOs), scannedMailDetailsVO);
							}
						}
						if (!validateMailSubClass(scannedMailDetailsVO.getCompanyCode(),
								mailBagVOFromUpload.getMailbagId().substring(13, 15))) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_SUBCLASS,
									MailHHTBusniessException.INVALID_SUBCLASS, scannedMailDetailsVO);
						}
					} else if (isValidMailtag(
							scannedMailDetailsVO.getMailDetails().iterator().next().getMailbagId().length())) {
						if (scannedMailDetailsVO.getMailDetails().iterator().next().getMailSubclass() != null
								&& !validateMailSubClass(scannedMailDetailsVO.getCompanyCode(),
										scannedMailDetailsVO.getMailDetails().iterator().next().getMailSubclass())) {
							constructAndRaiseException(MailMLDBusniessException.INVALID_SUBCLASS,
									MailHHTBusniessException.INVALID_SUBCLASS, scannedMailDetailsVO);
						}
					}
					checkIfMailbagExists(mailBagVOFromUpload, scannedMailDetailsVO);
				}
			}
			scannedMailDetailsVO.setForceAcpAfterErr(isAcpAftrError);
		}
	}

	/** 
	* @param scannedMailDetailsVO
	* @throws MailHHTBusniessException
	* @throws MailMLDBusniessException
	* @throws SystemException
	* @throws ForceAcceptanceException 
	*/
	public void doProcessPointSpecificMailbagValidationsForAndroid(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException {
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			for (MailbagVO scannedMailbagVO : scannedMailDetailsVO.getMailDetails()) {
				if (scannedMailDetailsVO.getPou() == null || scannedMailDetailsVO.getPou().trim().length() == 0) {
					scannedMailDetailsVO.setPou(scannedMailbagVO.getPou());
				}
				if (scannedMailbagVO.getMailbagId() != null && scannedMailbagVO.getMailbagId().trim().length() == 29
						|| isValidMailtag(scannedMailbagVO.getMailbagId().trim().length())) {
					if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
							|| MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
						if (!MailConstantsVO.FLAG_YES.equalsIgnoreCase(scannedMailbagVO.getReassignFlag())) {
							validateMailbagsForUploadAcceptance(scannedMailbagVO, scannedMailDetailsVO);
						}
					} else if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
						validateMailbagsForUploadArrival(scannedMailbagVO, scannedMailDetailsVO);
					} else if (MailConstantsVO.MAIL_STATUS_RETURNED.equals(scannedMailDetailsVO.getProcessPoint())) {
						validateMailbagsForUploadReturn(scannedMailbagVO, scannedMailDetailsVO);
					} else if (MailConstantsVO.MAIL_STATUS_DAMAGED.equals(scannedMailDetailsVO.getProcessPoint())) {
						validateMailbagsForUploadDamage(scannedMailbagVO, scannedMailDetailsVO);
					} else if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(scannedMailDetailsVO.getProcessPoint())) {
						validateMailbagsForUploadOffload(scannedMailbagVO, scannedMailDetailsVO);
					} else if (MailConstantsVO.MAIL_STATUS_DELIVERED.equals(scannedMailDetailsVO.getProcessPoint())) {
						validateMailbagsForUploadDeliver(scannedMailbagVO, scannedMailDetailsVO);
					} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
						validateMailbagsForUploadTransfer(scannedMailbagVO, scannedMailDetailsVO);
					} else if (MailConstantsVO.MAIL_STATUS_REASSIGNMAIL
							.equals(scannedMailDetailsVO.getProcessPoint())) {
						scannedMailbagVO.setFlightClosureCheckNotNeeded(true);
						validateMailbagsForUploadReassign(scannedMailbagVO, scannedMailDetailsVO);
						scannedMailbagVO.setFlightClosureCheckNotNeeded(false);
					}
				}
			}
		}
	}

	/** 
	* @param mailtagLength
	* @return valid
	*/
	private boolean isValidMailtag(int mailtagLength) {
		boolean valid = false;
		String systemParameterValue = null;
		try {
			systemParameterValue = findSystemParameterValue(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
		} finally {
		}
		if (systemParameterValue != null && !systemParameterValue.equals("NA")) {
			String[] systemParameterVal = systemParameterValue.split(",");
			for (String a : systemParameterVal) {
				if (Integer.valueOf(a) == mailtagLength) {
					valid = true;
					break;
				}
			}
		}
		return valid;
	}

	/** 
	* @author A-8353
	* @param scannedMailDetailsVO
	* @return
	* @throws SystemException
	*/
	private boolean checkDuplicateMailbag(MailbagVO scannedMailDetailsVO) {
		MailController mailController=ContextUtil.getInstance().getBean(MailController.class);
		String paCode = null;
		boolean isDuplicate = false;
		paCode = scannedMailDetailsVO.getPaCode() != null && scannedMailDetailsVO.getPaCode().trim().length() > 0
				? scannedMailDetailsVO.getPaCode()
				: mailController.findPAForOfficeOfExchange(scannedMailDetailsVO.getCompanyCode(),
						scannedMailDetailsVO.getOoe());
		Mailbag mailBag = null;
		MailbagPK mailbagPk = new MailbagPK();
		mailbagPk.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		mailbagPk
				.setMailSequenceNumber(
						scannedMailDetailsVO.getMailSequenceNumber() == 0
								? Mailbag.findMailBagSequenceNumberFromMailIdr(scannedMailDetailsVO.getMailbagId(),
										scannedMailDetailsVO.getCompanyCode())
								: scannedMailDetailsVO.getMailSequenceNumber());
		try {
			mailBag = Mailbag.find(mailbagPk);
		} catch (FinderException e) {
			log.error(FINDER_EXCEPTION);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if (mailBag != null) {
			try {
				isDuplicate = checkForDuplicateMailbag(scannedMailDetailsVO.getCompanyCode(), paCode, mailBag);
			} catch (DuplicateMailBagsException e) {
				e.getMessage();
			}
		}
		if (isDuplicate) {
			return true;
		}
		return false;
	}

	/** 
	* @author A-8353
	* @param scannedMailDetailsVO
	* @return EmbargoFilterVO
	*/
	private EmbargoFilterVO constructEmbargoFilterVO(ScannedMailDetailsVO scannedMailDetailsVO) {
		EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO();
		embargoFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
			embargoFilterVO.setApplicableTransactions("MALARR");
		} else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())) {
			embargoFilterVO.setApplicableTransactions("MALACP");
		} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())) {
			embargoFilterVO.setApplicableTransactions("MALTRA");
		}
		return embargoFilterVO;
	}
	public void doFlightAndCarrierValidations(ScannedMailDetailsVO scannedMailDetailsVO)throws MailMLDBusniessException, MailHHTBusniessException, SystemException, ForceAcceptanceException {

		LoginProfile logonAttributes = ContextUtil.getInstance().getBean(ContextUtil.class).callerLoginProfile();

		if (MailConstantsVO.MAIL_STATUS_RECEIVED.equals(scannedMailDetailsVO.getProcessPoint()) ||
				scannedMailDetailsVO.getProcessPoint().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED) ||
				scannedMailDetailsVO.getProcessPoint().equals( MailConstantsVO.MAIL_STATUS_REASSIGNMAIL) ||
				scannedMailDetailsVO.getProcessPoint().equals( MailConstantsVO.MAIL_STATUS_TRANSFERRED) ||
				scannedMailDetailsVO.getProcessPoint().equals( MailConstantsVO.MAIL_STATUS_ARRIVED)||
				(scannedMailDetailsVO.getProcessPoint().equals( MailConstantsVO.MAIL_STATUS_EXPORT)&&
						scannedMailDetailsVO.getCarrierCode()!=null&&!"".equals(scannedMailDetailsVO.getCarrierCode())&&
						scannedMailDetailsVO.getFlightNumber()!=null&& !"".equals(scannedMailDetailsVO.getFlightNumber())) ){
			//Added as part of CR ICRD-89077
			if( MailConstantsVO.MLD.equals(scannedMailDetailsVO.getMailSource()) && scannedMailDetailsVO.getCarrierCode()==null){

				scannedMailDetailsVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
			}

			// Modified for ICRD-94096
			//Modified for ICRD-126624

			AirlineValidationVO airlineValidationVO  = validateCarrierCode(scannedMailDetailsVO.getCarrierCode(),scannedMailDetailsVO.getCompanyCode());
			if(airlineValidationVO==null){
				constructAndRaiseException(MailMLDBusniessException.INVALID_CARRIERCODE_EXCEPTION,
						MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION, scannedMailDetailsVO);
			}else{
				scannedMailDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
			}
		/*	if (!validateCarrierCode(scannedMailDetailsVO.getCarrierCode(),scannedMailDetailsVO.getCompanyCode())) {
				constructAndRaiseException(MailMLDBusniessException.INVALID_CARRIERCODE_EXCEPTION,
						MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION, scannedMailDetailsVO);
			}*/

			if (scannedMailDetailsVO.getFlightSequenceNumber() !=  MailConstantsVO.DESTN_FLT &&
					scannedMailDetailsVO.getFlightNumber() != null &&
					scannedMailDetailsVO.getFlightNumber().trim().length() > 0) {
				validateFlightForUpload(scannedMailDetailsVO, logonAttributes);
			}
		}
	}

	private boolean validateFlightForUpload(
			 ScannedMailDetailsVO scannedMailDetailsVO,
			LoginProfile logonAttributes) throws MailMLDBusniessException,
			MailHHTBusniessException, ForceAcceptanceException {
		String airportCode = scannedMailDetailsVO.getAirportCode();
	    MailController mailController=ContextUtil.getInstance().getBean(MailController.class);
		boolean validFlightFlag = false;
		boolean isStatusTBAorTBC = false;
		String errorMessage = null;
		String errorCode = null;
		FlightFilterVO flightFilterVO = createFlightFilterVO(
				scannedMailDetailsVO, logonAttributes, airportCode);
		Collection<FlightValidationVO> flightDetailsVOs = null;

		if(scannedMailDetailsVO.getFlightValidationVOS()!=null && scannedMailDetailsVO.getFlightValidationVOS().size()>0){
			flightDetailsVOs=scannedMailDetailsVO.getFlightValidationVOS();
		}else{
			try {
				flightDetailsVOs =mailController
						.validateFlight(flightFilterVO);
			} catch (Exception e) {
				//Added by A-7540 for ICRD-194243

				constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			}
		}
		//Added by A-5945 for ICRD-99294
		if (flightDetailsVOs != null && flightDetailsVOs.size()>0) {
			isStatusTBAorTBC=checkIfFlightToBeCancelled(flightDetailsVOs,scannedMailDetailsVO,airportCode);
			for (FlightValidationVO flightValidationVO : flightDetailsVOs) {
				if (!isStatusTBAorTBC && (( MailConstantsVO.MAIL_STATUS_ARRIVED
						.equals(scannedMailDetailsVO
								.getProcessPoint()) && flightValidationVO
						.getLegDestination().equals(airportCode))
						|| flightValidationVO.getLegOrigin()
						.equals(airportCode))) {


					validFlightFlag = true;

				}
			}
		}







		if(!validFlightFlag)
		{
			errorMessage = MailHHTBusniessException.INVALID_FLIGHT;
			errorCode =  MailConstantsVO.MLD_MSG_ERR_INVALID_FLIGHT;
		}


		if (isStatusTBAorTBC) {
			validFlightFlag = true;

			errorMessage = "The Flight is in To be actioned/To be cancelled/cancelled status";
			errorCode = MailMLDBusniessException.FLIGHT_TBA_TBC;

		}
		if (errorMessage!=null && errorMessage.trim().length()>0 ) {

			constructAndRaiseException(errorCode, errorMessage,
					scannedMailDetailsVO);

		}

		return validFlightFlag;

	}


	// Added as part of bug ICRD-95657 by A-5526 starts
	public void doContainerDestinationAndPouValidations(
			ScannedMailDetailsVO scannedMailDetailsVO,
			ContainerAssignmentVO containerAssignmentVO)
			throws MailMLDBusniessException, MailHHTBusniessException,
			SystemException, ForceAcceptanceException {
//Modified as part of bug ICRD-97897 by A-5526 starts
		String DEST_FLT = "-1";
		boolean skipContainerValidation=false;
		//Modified as part of bug ICRD-141152 by A-5526
		if ((scannedMailDetailsVO.getDestination()!=null && !(scannedMailDetailsVO.getDestination()
				.equals(containerAssignmentVO.getDestination())) &&

				( MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag()))  &&
				(!( MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())) || (DEST_FLT.equals(scannedMailDetailsVO.getFlightNumber()) && DEST_FLT.equals(containerAssignmentVO.getFlightNumber()) &&  MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType()))))
				&& scannedMailDetailsVO.getContainerJourneyId()==null) {
			//Modified as part of bug ICRD-97897 by A-5526 ends
			//

			if((scannedMailDetailsVO.getFlightSequenceNumber()>0 && (scannedMailDetailsVO.getFlightNumber().equals(containerAssignmentVO.getFlightNumber())) &&
					scannedMailDetailsVO.getFlightSequenceNumber()==containerAssignmentVO.getFlightSequenceNumber() ) || (DEST_FLT.equals(scannedMailDetailsVO.getFlightNumber()) )   ){
				skipContainerValidation=true;
			}
			/**if(!skipContainerValidation&& ! MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint()) &&
			 (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint()) && scannedMailDetailsVO.getFlightNumber()!= null )){
			 constructAndRaiseException(
			 MailMLDBusniessException.INVALID_DESTINATION_UPDATION,
			 MailHHTBusniessException.INVALID_DESTINATION_UPDATION,
			 scannedMailDetailsVO);
			 }**/
			//95515
			/*if( MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint()) && scannedMailDetailsVO.getFlightNumber()!= null &&
					 scannedMailDetailsVO.getFlightDate() != null ){
			constructAndRaiseException(
					MailMLDBusniessException.INVALID_DESTINATION_UPDATION,
					MailHHTBusniessException.INVALID_DESTINATION_UPDATION,
					scannedMailDetailsVO);
			}*/

		}
		if (!DEST_FLT.equals(scannedMailDetailsVO.getFlightNumber())&&scannedMailDetailsVO.getFlightNumber().equals(
				containerAssignmentVO.getFlightNumber())
				&& scannedMailDetailsVO.getCarrierCode().equals(
				containerAssignmentVO.getCarrierCode())
				&& scannedMailDetailsVO.getCarrierId() == containerAssignmentVO
				.getCarrierId()
				&& scannedMailDetailsVO.getFlightSequenceNumber() == containerAssignmentVO
				.getFlightSequenceNumber()
				&& scannedMailDetailsVO.getLegSerialNumber() == containerAssignmentVO
				.getLegSerialNumber()
				&& containerAssignmentVO
				.getPou()!=null
				&& !(scannedMailDetailsVO.getPou().equals(containerAssignmentVO
				.getPou()))) {

			constructAndRaiseException(
					MailMLDBusniessException.INVALID_POU_UPDATION,
					MailHHTBusniessException.INVALID_POU_UPDATION,
					scannedMailDetailsVO);

		}
	}
	/**
	 *
	 * @param errorCode
	 * @param errorDescriptionForHHT
	 * @param scannedMailDetailsVO
	 * @throws MailMLDBusniessException
	 * @throws MailHHTBusniessException
	 * @throws ForceAcceptanceException
	 */
	private void constructAndRaiseException(String errorCode, String errorDescriptionForHHT,
											ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException,
			MailHHTBusniessException, ForceAcceptanceException {
		if (errorDescriptionForHHT!=null && errorDescriptionForHHT.length() > 90) {
			errorDescriptionForHHT = errorDescriptionForHHT.substring(0, 90);
		}
		//Added as part of Bug ICRD-153992 by A-5526 starts
		else if (errorDescriptionForHHT == null || errorDescriptionForHHT.isEmpty()) {

			errorDescriptionForHHT = "Exception";
		}
		if (scannedMailDetailsVO.isForceAcpAfterErr()){
			ContextUtil.getInstance().getBean(MailUploadController.class).doAcceptanceAfterErrors(scannedMailDetailsVO);
			throw new ForceAcceptanceException(errorCode,errorDescriptionForHHT);
		}
		//Added as part of Bug ICRD-153992 by A-5526 ends
		if (scannedMailDetailsVO != null) {
			if ( MailConstantsVO.MLD
					.equals(scannedMailDetailsVO.getMailSource())) {
				throw new MailMLDBusniessException(errorCode);
			}
			scannedMailDetailsVO.setErrorDescription(errorDescriptionForHHT);
		}
		//throw new MailHHTBusniessException(errorDescriptionForHHT);
		throw new MailHHTBusniessException(errorCode,errorDescriptionForHHT);
	}

	public boolean validateULDonTabOut(ScannedMailDetailsVO scannedMailDetailsVO, LoginProfile logonAttributes)
			throws MailMLDBusniessException, MailHHTBusniessException {
		ULDDefaultsProxy uLDDefaultsProxy = ContextUtil.getInstance().getBean(ULDDefaultsProxy.class);
		boolean isValidUld = true;
		if (isULDIntegrationEnabled()) {
			boolean isULDType = MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType());
			if (isULDType && scannedMailDetailsVO.getContainerNumber() != null
					&& !"".equals(scannedMailDetailsVO.getContainerNumber())
					&& scannedMailDetailsVO.getContainerType() != null
					&& !"".equals(scannedMailDetailsVO.getContainerType())
					&& MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType())
					&& scannedMailDetailsVO.getAirportCode() != null
					&& scannedMailDetailsVO.getAirportCode().trim().length() > 0) {
				FlightDetailsVO flightDetails = new FlightDetailsVO();
				flightDetails.setCompanyCode(logonAttributes.getCompanyCode());
				flightDetails.setCurrentAirport(scannedMailDetailsVO.getAirportCode());
				ArrayList<ULDInFlightVO> uldInFlightVos = new ArrayList<>();
				ULDInFlightVO uldInFlightVo = new ULDInFlightVO();
				uldInFlightVo.setUldNumber(scannedMailDetailsVO.getContainerNumber());
				if (MailConstantsVO.OPERATION_OUTBOUND.equals(scannedMailDetailsVO.getOperationType())) {
					uldInFlightVo.setPointOfLading(scannedMailDetailsVO.getAirportCode());
				} else {
					uldInFlightVo.setPointOfUnLading(scannedMailDetailsVO.getAirportCode());
				}
				uldInFlightVos.add(uldInFlightVo);
				flightDetails.setUldInFlightVOs(uldInFlightVos);
				try {
					uLDDefaultsProxy.validateULDsForOperation(flightDetails);
				} catch (ULDDefaultsProxyException e) {
					Collection<MailbagVO> mailErrors = new ArrayList<>();
					if (scannedMailDetailsVO.getErrorMailDetails() == null
							|| scannedMailDetailsVO.getErrorMailDetails().isEmpty()) {
						scannedMailDetailsVO.setErrorMailDetails(mailErrors);
					}
					if (e.getMessage() != null && e.getMessage().equals(MAIL_DEFAULTS_WARNING_ULDISNOTINAIRPORT)) {
						log.debug("" + "ULDDefaultsProxyException" + " " + e.getMessage());
						new MailOperationsValidator().constructAndroidException(
								MailMLDBusniessException.ULD_VALID_FAILED,
								MailHHTBusniessException.ULD_IS_NOT_IN_AIRPORT, scannedMailDetailsVO);
					} else if (e.getMessage() != null && e.getMessage().equals(MAIL_DEFAULTS_ERROR_ULDISNOTINAIRPORT)) {
						new MailOperationsValidator().constructAndroidException(
								MailConstantsVO.CONTAINER_REASSIGN_ERROR,
								MailHHTBusniessException.ULD_IS_NOT_IN_AIRPORT, scannedMailDetailsVO);
					} else if (e.getMessage() != null && (e.getMessage().equals(MAIL_DEFAULTS_WARNING_ULDISNOTINSYSTEM)
							|| e.getMessage().equals(MAIL_DEFAULTS_WARNING_ULDISNOTINAIRSTOCK))) {
						new MailOperationsValidator().constructAndroidException(
								MailMLDBusniessException.ULD_VALID_FAILED, MailHHTBusniessException.ULD_NOT_EXIST,
								scannedMailDetailsVO);
					} else if (e.getMessage() != null && (e.getMessage().equals(MAIL_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM)
							|| e.getMessage().equals(MAIL_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK))) {
						new MailOperationsValidator().constructAndroidException(
								MailConstantsVO.CONTAINER_REASSIGN_ERROR, MailHHTBusniessException.ULD_NOT_EXIST,
								scannedMailDetailsVO);
					} else if (e.getMessage() != null
							&& e.getMessage().equals(MAIL_DEFAULTS_WARNING_ULDISNOTOPERATIONAL)) {
						new MailOperationsValidator().constructAndroidException(
								MailMLDBusniessException.ULD_VALID_FAILED,
								MailHHTBusniessException.ULD_IS_NOT_OPERATIONAL, scannedMailDetailsVO);
					} else if (e.getMessage() != null
							&& e.getMessage().equals(MAIL_DEFAULTS_ERROR_ULDISNOTOPERATIONAL)) {
						new MailOperationsValidator().constructAndroidException(
								MailConstantsVO.CONTAINER_REASSIGN_ERROR,
								MailHHTBusniessException.ULD_IS_NOT_OPERATIONAL, scannedMailDetailsVO);
					} else
						return true;
				}
			}
		}
		return isValidUld;
	}

	/**
	 * validateULDIncomatibility
	 * @author A-5526 for IASCB-34124
	 * @throws SystemException
	 * @throws MailHHTBusniessException
	 * @throws MailMLDBusniessException
	 */
	public void validateULDIncomatibility(FlightValidationVO flightValidationVO,
										  ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException, MailHHTBusniessException {
		SharedULDProxy sharedULDProxy = ContextUtil.getInstance().getBean(SharedULDProxy.class);
		NeoMastersServiceUtils neoMastersServiceUtils = ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class);
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
		Map<String, String> systemParameters = null;
		try {
			systemParameters = neoMastersServiceUtils.findSystemParameterByCodes(parameterCodes);
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
		ArrayList<String> uldTypeCodes = new ArrayList<String>();
		ArrayList<String> uldNumberCodes = new ArrayList<String>();
		if (scannedMailDetailsVO.getContainerNumber() != null
				&& scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
			String uldType = scannedMailDetailsVO.getContainerNumber().substring(0, 3);
			if (!uldTypeCodes.contains(uldType.toUpperCase())) {
				uldTypeCodes.add(uldType.toUpperCase());
			}
			uldNumberCodes.add(scannedMailDetailsVO.getContainerNumber());
		}
		Collection<ULDPositionFilterVO> filterVOs = new ArrayList<ULDPositionFilterVO>();
		if (flightValidationVO != null) {
			Collection<String> aircraftTypes = new ArrayList<String>();
			aircraftTypes.add(flightValidationVO.getAircraftType());
			ULDPositionFilterVO filterVO = null;
			Collection<String> validatedUldTypeCodes = validateAirCraftCompatibilityforUldTypes(uldTypeCodes,
					systemParameters);
			if (validatedUldTypeCodes != null && validatedUldTypeCodes.size() > 0) {
				for (String uldType : validatedUldTypeCodes) {
					filterVO = new ULDPositionFilterVO();
					filterVO.setAircraftTypes(aircraftTypes);
					filterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
					filterVO.setUldCode(uldType);
					filterVOs.add(filterVO);
				}
			}
		}
		if (filterVOs != null && filterVOs.size() > 0) {
			//TODO: Neo to implement
//			try {
//				sharedULDProxy.findULDPosition(filterVOs);
//			} catch (Exception uldDefaultsException) {
//				if (MailConstantsVO.ULD_INCOMPATIBLEAIRCRAFT.equals(uldDefaultsException.getMessage())) {
//					throw new SystemException(MailConstantsVO.ULD_INCOMPATIBLEAIRCRAFT);
//				}
//			}
		}
	}

	/**
	 * @author A-5526 for IASCB-34124validateAirCraftCompatibilityforUldTypes
	 * @param uldTypeCodes
	 * @param systemParameterMap
	 * @return
	 */
	public Collection<String> validateAirCraftCompatibilityforUldTypes(Collection<String> uldTypeCodes,
																	   Map<String, String> systemParameterMap) {
		log.debug("SaveAcceptanceCommand" + " : " + "validateAirCraftCompatibilityforUldTypes" + " Entering");
		ArrayList<String> uldTypeCodesForValidation = null;
		if (systemParameterMap != null && systemParameterMap.size() > 0) {
			String configuredTypes = systemParameterMap.get(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
			if (configuredTypes != null && configuredTypes.length() > 0 && !"N".equals(configuredTypes)) {
				if ("*".equals(configuredTypes)) {
					for (String uldType : uldTypeCodes) {
						if (uldTypeCodesForValidation == null) {
							uldTypeCodesForValidation = new ArrayList<String>();
						}
						uldTypeCodesForValidation.add(uldType);
					}
				} else {
					List<String> configuredTypesList = Arrays.asList(configuredTypes.split(","));
					if (uldTypeCodes != null && uldTypeCodes.size() > 0) {
						for (String uldType : uldTypeCodes) {
							if (configuredTypesList.contains(uldType)) {
								if (uldTypeCodesForValidation == null) {
									uldTypeCodesForValidation = new ArrayList<String>();
								}
								uldTypeCodesForValidation.add(uldType);
							}
						}
					}
				}
			}
		}
		log.debug("SaveAcceptanceCommand" + " : " + "validateAirCraftCompatibilityforUldTypes" + " Exiting");
		return uldTypeCodesForValidation;
	}
}
