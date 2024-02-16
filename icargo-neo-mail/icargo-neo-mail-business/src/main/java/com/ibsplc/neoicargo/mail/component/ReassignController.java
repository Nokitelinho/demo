package com.ibsplc.neoicargo.mail.component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.mail.exception.*;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.MailMRAProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedAirlineProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedAreaProxy;
import com.ibsplc.neoicargo.mail.component.proxy.OperationsFltHandlingProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedULDProxy;
import com.ibsplc.neoicargo.mail.component.proxy.ULDDefaultsProxy;
import com.ibsplc.neoicargo.mail.vo.AssignedFlightSegmentVO;
import com.ibsplc.neoicargo.mail.vo.AssignedFlightVO;
import com.ibsplc.neoicargo.mail.vo.ContainerAssignmentVO;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.neoicargo.mail.vo.DSNInConsignmentForContainerSegmentVO;
import com.ibsplc.neoicargo.mail.vo.DSNInConsignmentForULDSegmentVO;
import com.ibsplc.neoicargo.mail.vo.DSNInContainerAtAirportVO;
import com.ibsplc.neoicargo.mail.vo.DSNInContainerForSegmentVO;
import com.ibsplc.neoicargo.mail.vo.DSNInULDAtAirportVO;
import com.ibsplc.neoicargo.mail.vo.DSNInULDForSegmentVO;
import com.ibsplc.neoicargo.mail.vo.DespatchDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagInULDAtAirportVO;
import com.ibsplc.neoicargo.mail.vo.MailbagInULDForSegmentVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OnwardRouteForSegmentVO;
import com.ibsplc.neoicargo.mail.vo.OnwardRoutingVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import com.ibsplc.neoicargo.mail.vo.ULDAtAirportVO;
import com.ibsplc.neoicargo.mail.vo.ULDForSegmentVO;
import com.ibsplc.neoicargo.mail.vo.converter.MailOperationsVOConverter;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * @author a-2553
 */
@Component
@Slf4j
public class ReassignController {

	@Autowired
	MailOperationsMapper mailOperationsMapper;
	@Autowired
	private Quantities quantities;
	@Autowired
	private SharedDefaultsProxy sharedDefaultsProxy;
	@Autowired
	private SharedULDProxy sharedULDProxy;
	@Autowired
	private ULDDefaultsProxy uLDDefaultsProxy;
	@Autowired
	private SharedAreaProxy sharedAreaProxy;
	@Autowired
	private OperationsFltHandlingProxy operationsFltHandlingProxy;
	@Autowired
	private SharedAirlineProxy sharedAirlineProxy;
	@Autowired
	private MailMRAProxy mailOperationsMRAProxy;
	@Autowired
	private FlightOperationsProxy flightOperationsProxy;
	@Autowired
	private LocalDate localDateUtil;
	@Autowired
	private ContextUtil contextUtil;
	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;
	@Autowired
	private MailController mailController;
	private static final String DEFAULT_STORAGEUNITFORMAIL = "mailtracking.defaults.defaultstorageunitformail";
	private static final String CONST_YES = "Y";
	private static final String WEIGHT_SCALE_AVAILABLE = "mail.operation.weighscaleformailavailable";
	private static final String MAIL_CONTROLLER = "mAilcontroller";

	/**
	 * A-1739
	 * @param mailbagVO
	 * @return
	 */
	private OperationalFlightVO constructOpFlightVOFromMailbag(MailbagVO mailbagVO) {
		OperationalFlightVO opFlightVO = new OperationalFlightVO();
		opFlightVO.setCompanyCode(mailbagVO.getCompanyCode());
		opFlightVO.setCarrierId(mailbagVO.getCarrierId());
		opFlightVO.setFlightNumber(mailbagVO.getFlightNumber());
		opFlightVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		opFlightVO.setFlightDate(mailbagVO.getFlightDate());
		opFlightVO.setPol(mailbagVO.getScannedPort());
		opFlightVO.setCarrierCode(mailbagVO.getCarrierCode());
		log.debug("" + "THE OPERATIONAL FLIGHT VO FROM MAIL BAG" + " " + opFlightVO);
		return opFlightVO;
	}

	/**
	 * @author a-1936 This a FlightProductProxyCall .This method is used tovalidate the FlightForAirport
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<FlightValidationVO> validateFlight(FlightFilterVO flightFilterVO) {
		log.debug("ReassignController" + " : " + "validateFlight" + " Entering");
		return flightOperationsProxy.validateFlightForAirport(flightFilterVO);
	}

	private FlightValidationVO validateOperationalFlight(OperationalFlightVO opFlightVO, boolean isInbound) {
		log.debug("ReassignController" + " : " + "validateOperationalFlight" + " Entering");
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(opFlightVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(opFlightVO.getCarrierId());
		flightFilterVO.setFlightNumber(opFlightVO.getFlightNumber());
		flightFilterVO.setFlightDate(LocalDateMapper.toLocalDate(opFlightVO.getFlightDate()));
		if (opFlightVO.getFlightSequenceNumber() > 0) {
			flightFilterVO.setFlightSequenceNumber(opFlightVO.getFlightSequenceNumber());
		}
		if (isInbound) {
			flightFilterVO.setDirection(FlightFilterVO.INBOUND);
			flightFilterVO.setStation(opFlightVO.getPou());
		} else {
			flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
			flightFilterVO.setStation(opFlightVO.getPol());
		}
		Collection<FlightValidationVO> flightValidationVOs = validateFlight(flightFilterVO);
		FlightValidationVO toReturnVO = null;
		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				if (flightValidationVO.getFlightSequenceNumber() == opFlightVO.getFlightSequenceNumber()) {
					toReturnVO = flightValidationVO;
				}
			}
		}
		log.debug("ReassignController" + " : " + "validateOperationalFlight" + " Exiting");
		log.debug("" + "THE FLIGHT FILTER VO IS " + " " + flightFilterVO);
		return toReturnVO;
	}

	/**
	 * @author A-5991
	 * @param flightVO
	 * @return
	 */
	private AssignedFlightPK constructInboundFlightPK(OperationalFlightVO flightVO) {
		AssignedFlightPK assignedFlightPK = new AssignedFlightPK();
		assignedFlightPK.setCompanyCode(flightVO.getCompanyCode());
		assignedFlightPK.setCarrierId(flightVO.getCarrierId());
		assignedFlightPK.setFlightNumber(flightVO.getFlightNumber());
		assignedFlightPK.setFlightSequenceNumber(flightVO.getFlightSequenceNumber());
		assignedFlightPK.setLegSerialNumber(flightVO.getLegSerialNumber());
		assignedFlightPK.setAirportCode(flightVO.getPou());
		return assignedFlightPK;
	}

	/**
	 * @author a-1883
	 * @param operationalFlightVO
	 * @return boolean
	 * @throws SystemException
	 */
	private boolean isFlightClosedForInboundOperations(OperationalFlightVO operationalFlightVO) {
		log.debug("ReassignController" + " : " + "isFlightClosedForInboundOperations" + " Entering");
		boolean isFlightClosedForInbound = false;
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPK = constructInboundFlightPK(operationalFlightVO);
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPK);
		} catch (FinderException ex) {
			log.info("FINDER EXCEPTION IS THROWN");
			throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
		if (assignedFlight != null) {
			isFlightClosedForInbound = MailConstantsVO.FLIGHT_STATUS_CLOSED
					.equals(assignedFlight.getImportClosingFlag());
			log.debug("" + "The Flight Status is found to be " + " " + isFlightClosedForInbound);
		}
		log.debug("ReassignController" + " : " + "isFlightClosedForInboundOperations" + " Exiting");
		return isFlightClosedForInbound;
	}

	/**
	 * @author a-1936 This method is used to check whether the Flight is closedfor Operations
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	private boolean isFlightClosedForOperations(OperationalFlightVO operationalFlightVO) {
		log.debug("ReassignController" + " : " + "isFlightClosedForOperations" + " Entering");
		boolean isFlightClosedForOperations = false;
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
		assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.info("FINDER EXCEPTION IS THROWN");
		}
		if (assignedFlight != null) {
			isFlightClosedForOperations = MailConstantsVO.FLIGHT_STATUS_CLOSED
					.equals(assignedFlight.getExportClosingFlag());
			log.debug("" + "The Flight Status is found to be " + " " + isFlightClosedForOperations);
		}
		return isFlightClosedForOperations;
	}

	/**
	 * This method checks if all the mailbags are eligible for Reassignment It checks the following 1. If the flight in which it is currently sitting is closed, if so it throws FlightClosedException 2. If the container in which the mailbag in put is a PABuilt ULD If so it throws a reassignmentExcepiton, since no mailbags can be moved from a PABuilt ULD A-1739
	 * @param mailbagsToReassign
	 * @param flightAssignedMailbags
	 * @param destAssignedMailbags
	 * @return true if all the mailbags are reassignable
	 * @throws SystemException
	 * @throws FlightClosedException
	 */
	public boolean isReassignableMailbags(Collection<MailbagVO> mailbagsToReassign,
										  Collection<MailbagVO> flightAssignedMailbags, Collection<MailbagVO> destAssignedMailbags)
			throws FlightClosedException {
		log.debug("ReassignController" + " : " + "isReassignableMailbags" + " Entering");
		Boolean isIntermediateReturn = false;
		for (MailbagVO mailbagVO : mailbagsToReassign) {
			if (mailbagVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT
					|| mailbagVO.getFlightSequenceNumber() == 0) {
				destAssignedMailbags.add(mailbagVO);
				mailbagVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
			} else if (mailbagVO.getFlightSequenceNumber() > 0) {
				OperationalFlightVO opFlightVO = constructOpFlightVOFromMailbag(mailbagVO);
				FlightValidationVO flightValidationVO = null;
				if (MailConstantsVO.OPERATION_INBOUND.equals(mailbagVO.getOperationalStatus())) {
					opFlightVO.setPou(mailbagVO.getScannedPort());
					if (mailbagVO.getLegSerialNumber() == 0) {
						flightValidationVO = validateOperationalFlight(opFlightVO, true);
						if (flightValidationVO != null) {
							opFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
						}
					} else {
						opFlightVO.setLegSerialNumber(mailbagVO.getLegSerialNumber());
					}
					if (isFlightClosedForInboundOperations(opFlightVO)) {
						throw new FlightClosedException(FlightClosedException.FLIGHT_STATUS_CLOSED, new String[] {
								new StringBuilder().append(opFlightVO.getCarrierCode()).append(" ")
										.append(opFlightVO.getFlightNumber()).toString(),
								opFlightVO.getFlightDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)),
								mailbagVO.getMailbagId() });
					}
				} else {
					opFlightVO.setPol(mailbagVO.getScannedPort());
					if (mailbagVO.getLegSerialNumber() == 0) {
						flightValidationVO = validateOperationalFlight(opFlightVO, false);
						if (flightValidationVO != null) {
							opFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
						}
					} else {
						opFlightVO.setLegSerialNumber(mailbagVO.getLegSerialNumber());
					}
					if (isFlightClosedForOperations(opFlightVO) && !mailbagVO.isFlightClosureCheckNotNeeded()) {
						throw new FlightClosedException(FlightClosedException.FLIGHT_STATUS_CLOSED, new String[] {
								new StringBuilder().append(opFlightVO.getCarrierCode()).append(" ")
										.append(opFlightVO.getFlightNumber()).toString(),
								opFlightVO.getFlightDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)),
								mailbagVO.getMailbagId() });
					}
				}
				if (mailbagVO.getDamagedMailbags() != null && !mailbagVO.getDamagedMailbags().isEmpty()
						&& "Y".equals(mailbagVO.getDamagedMailbags().iterator().next().getReturnedFlag())
						&& "Y".equals(mailbagVO.getArrivedFlag())) {
					isIntermediateReturn = true;
				}
				if (!isIntermediateReturn) {
					flightAssignedMailbags.add(mailbagVO);
				}
				if (mailbagVO.getLegSerialNumber() == 0) {
					mailbagVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				}
			}
		}
		log.debug("ReassignController" + " : " + "isReassignableMailbags" + " Exiting");
		return true;
	}

	private ULDAtAirportVO createULDAtAirportVO(ContainerVO toDestinationContainerVO) {
		ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
		uldAtAirportVO.setCompanyCode(toDestinationContainerVO.getCompanyCode());
		uldAtAirportVO.setAirportCode(toDestinationContainerVO.getAssignedPort());
		uldAtAirportVO.setCarrierId(toDestinationContainerVO.getCarrierId());
		uldAtAirportVO.setCarrierCode(toDestinationContainerVO.getCarrierCode());
		uldAtAirportVO.setFinalDestination(toDestinationContainerVO.getFinalDestination());
		return uldAtAirportVO;
	}

	/**
	 * A-1739
	 * @return
	 */
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
	 * This method is used to reasign the mails to Destination A-1936
	 * @param flightAssignedMailbags
	 * @param toDestinationContainerVO
	 * @throws SystemException
	 */
	public void reassignMailToDestination(Collection<MailbagVO> flightAssignedMailbags,
										  ContainerVO toDestinationContainerVO) {
		log.debug("ReassignController" + " : " + "reassignMailToDestination" + " Entering");
		int bagCount = flightAssignedMailbags.size();
		ULDAtAirportVO uldAtAirportVO = createULDAtAirportVO(toDestinationContainerVO);
		ULDAtAirport uldAtAirport = null;
		String uldnumber = toDestinationContainerVO.getContainerNumber();
		if (MailConstantsVO.BULK_TYPE.equals(toDestinationContainerVO.getType())) {
			uldnumber = constructBulkULDNumber(toDestinationContainerVO.getFinalDestination(),
					toDestinationContainerVO.getCarrierCode());
		}
		uldAtAirportVO.setUldNumber(uldnumber);
		try {
			uldAtAirport = ULDAtAirport.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));
			if(toDestinationContainerVO.getULDLastUpdateTime()!=null) {
				uldAtAirport.setLastUpdatedTime(Timestamp.valueOf(toDestinationContainerVO.getULDLastUpdateTime().toLocalDateTime()));
			}
		} catch (FinderException ex) {
			log.info("THE FINDER EXCEPTION IS THROWN ");
			log.info("Create ULDAtAirport");
			uldAtAirport = new ULDAtAirport(uldAtAirportVO);
		}
		uldAtAirport.reassignMailToDestination(flightAssignedMailbags, toDestinationContainerVO);
	}

	/**
	 * This method is used to update the MailBags in DSN for reassignment mails to the Destnation A-1936
	 * @param flightAssignedMailbags
	 * @param toContainerVO
	 * @throws SystemException
	 */
	public void updateMailbagsForReassign(Collection<MailbagVO> flightAssignedMailbags, ContainerVO toContainerVO) {
		new Mailbag().updateReassignedMailbags(flightAssignedMailbags, toContainerVO);
		flagHistoryFormailbagReassign(flightAssignedMailbags, toContainerVO);
	}

	/**
	 * This method is used to construct the AssignedFlightSegmentPK from the MailBagVos A-1936
	 * @param flightAssignedMailbagVO
	 * @return
	 */
	private AssignedFlightSegmentPK constructAsgFlightPKForMailbag(MailbagVO flightAssignedMailbagVO) {
		AssignedFlightSegmentPK assignedFlightPK = new AssignedFlightSegmentPK();
		assignedFlightPK.setCompanyCode(flightAssignedMailbagVO.getCompanyCode());
		assignedFlightPK.setFlightNumber(flightAssignedMailbagVO.getFlightNumber());
		assignedFlightPK.setCarrierId(flightAssignedMailbagVO.getCarrierId());
		assignedFlightPK.setFlightSequenceNumber(flightAssignedMailbagVO.getFlightSequenceNumber());
		assignedFlightPK.setSegmentSerialNumber(flightAssignedMailbagVO.getSegmentSerialNumber());
		return assignedFlightPK;
	}

	/**
	 * reassigsn dSNs from flt to destn and also returns ULDs which become empty by the process. But barrows are removed by the system A-1739
	 * @param despatchDetailsVOs
	 * @param toContainerVO
	 * @return the collection of empty ULDs
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> reassignDSNsFromFlightToDestination(
			Collection<DespatchDetailsVO> despatchDetailsVOs, ContainerVO toContainerVO) {
		Collection<ContainerDetailsVO> emptyContainers = reassignDSNsFromFlight(despatchDetailsVOs);
		reassignDSNsToDestination(despatchDetailsVOs, toContainerVO);
		updateContainerAcceptance(toContainerVO);
		log.debug("ReassignController" + " : " + "reassignDSNsFromFlightToDestination" + " Exiting");
		return emptyContainers;
	}

	/**
	 * This method is used to reassign the DSNs from Flight Also returns ULDs which become empty in the process A-1936
	 * @param despatchDetailsVOs
	 * @return the empty ULDs to return
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> reassignDSNsFromFlight(Collection<DespatchDetailsVO> despatchDetailsVOs) {
		log.debug("ReassignController" + " : " + "reassignDSNFromFlight" + " Entering");
		HashMap<AssignedFlightSegmentPK, Collection<DespatchDetailsVO>> assignedFlightSegmentMap = new HashMap<AssignedFlightSegmentPK, Collection<DespatchDetailsVO>>();
		Collection<DespatchDetailsVO> newMailbagVos = null;
		for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
			AssignedFlightSegmentPK assignedFlightSegmentPK = constructAssignedFlightSegmentPK(despatchDetailsVO);
			newMailbagVos = assignedFlightSegmentMap.get(assignedFlightSegmentPK);
			if (newMailbagVos == null) {
				newMailbagVos = new ArrayList<DespatchDetailsVO>();
				assignedFlightSegmentMap.put(assignedFlightSegmentPK, newMailbagVos);
			}
			newMailbagVos.add(despatchDetailsVO);
		}
		Collection<ContainerDetailsVO> containersToReturn = new ArrayList<ContainerDetailsVO>();
		if (assignedFlightSegmentMap != null && assignedFlightSegmentMap.size() > 0) {
			log.debug("" + "THE No of the AssignedFlights Found are " + " " + assignedFlightSegmentMap.size());
			AssignedFlightSegment assignedFlightSegment = null;
			try {
				for (AssignedFlightSegmentPK flightSegmentPK : assignedFlightSegmentMap.keySet()) {
					assignedFlightSegment = AssignedFlightSegment.find(flightSegmentPK);
					containersToReturn.addAll(assignedFlightSegment
							.reassignDSNsFromFlight(assignedFlightSegmentMap.get(flightSegmentPK)));
				}
			} catch (FinderException ex) {
				log.info("DATA INCONSISTENT");
				throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
			}
		}
		log.debug("" + "containersToReturn " + " " + containersToReturn);
		log.debug("ReassignController" + " : " + "reassignDSNFromFlight" + " Exiting");
		return containersToReturn;
	}

	/**
	 * This method is used to constructAssignedFlightPK From DespatchDetailsVO A-1936
	 * @param despatchDetailsVO
	 * @return
	 */
	private AssignedFlightSegmentPK constructAssignedFlightSegmentPK(DespatchDetailsVO despatchDetailsVO) {
		log.debug("ReassignController" + " : " + "constructAssignedFlightSegmentPK" + " Entering");
		AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
		assignedFlightSegmentPK.setCompanyCode(despatchDetailsVO.getCompanyCode());
		assignedFlightSegmentPK.setCarrierId(despatchDetailsVO.getCarrierId());
		assignedFlightSegmentPK.setFlightNumber(despatchDetailsVO.getFlightNumber());
		assignedFlightSegmentPK.setFlightSequenceNumber(despatchDetailsVO.getFlightSequenceNumber());
		assignedFlightSegmentPK.setSegmentSerialNumber(despatchDetailsVO.getSegmentSerialNumber());
		log.debug("ReassignController" + " : " + "constructAssignedFlightSegmentPK" + " Exiting");
		return assignedFlightSegmentPK;
	}

	/**
	 * This method is used to reassign the DSns To a Destination A-1936
	 * @param despatchesToReassign
	 * @param toContainerVO
	 * @throws SystemException
	 */
	private void reassignDSNsToDestination(Collection<DespatchDetailsVO> despatchesToReassign,
										   ContainerVO toContainerVO) {
		log.debug("MailControler" + " : " + "ReassignDSNsToDestination" + " Entering");
		log.debug("ReassignController" + " : " + "reassignMailToDestination" + " Entering");
		ULDAtAirportVO uldAtAirportVO = createULDAtAirportVO(toContainerVO);
		ULDAtAirport uldAtAirport = null;
		String uldnumber = toContainerVO.getContainerNumber();
		if (MailConstantsVO.BULK_TYPE.equals(toContainerVO.getType())) {
			uldnumber = constructBulkULDNumber(toContainerVO.getFinalDestination(), toContainerVO.getCarrierCode());
		}
		uldAtAirportVO.setUldNumber(uldnumber);
		try {
			uldAtAirport = ULDAtAirport.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));
		} catch (FinderException ex) {
			log.info("THE FINDER EXCEPTION IS THROWN ");
			log.info("Create ULDAtAirport");
			uldAtAirport = new ULDAtAirport(uldAtAirportVO);
		}
		uldAtAirport.reassignDSNsToDestination(despatchesToReassign, toContainerVO);
	}

	/**
	 * This method removes the assignment of mailbags from its current flight Group the MailBags based on FlightSegments say 1.F1-SEG1 2.F1-SEG2 3.F2-SEG1 4.F2-SEG2 etc and find out the MailBags associated withg this Segments.. A-1739
	 * @param flightAssignedMailbags
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> reassignMailFromFlight(Collection<MailbagVO> flightAssignedMailbags) {
		log.debug("ReassignController" + " : " + "reassignMailFromFlight" + " Entering");
		HashMap<AssignedFlightSegmentPK, Collection<MailbagVO>> assignedFlightSegmentMap = new HashMap<AssignedFlightSegmentPK, Collection<MailbagVO>>();
		Collection<MailbagVO> newMailbagVos = null;
		for (MailbagVO mailbagVo : flightAssignedMailbags) {
			log.debug("reassignMailbagsFromFlightToDestination" + " : " + "Group the mailBags for DifferentSegments"
					+ " Entering");
			AssignedFlightSegmentPK assignedFlightSegmentPK = constructAsgFlightPKForMailbag(mailbagVo);
			newMailbagVos = assignedFlightSegmentMap.get(assignedFlightSegmentPK);
			if (newMailbagVos == null) {
				newMailbagVos = new ArrayList<MailbagVO>();
				assignedFlightSegmentMap.put(assignedFlightSegmentPK, newMailbagVos);
			}
			newMailbagVos.add(mailbagVo);
		}
		Collection<ContainerDetailsVO> containersToReturn = new ArrayList<ContainerDetailsVO>();
		if (assignedFlightSegmentMap != null && assignedFlightSegmentMap.size() > 0) {
			AssignedFlightSegment assignedFlightSegment = null;
			try {
				for (AssignedFlightSegmentPK flightSegmentPK : assignedFlightSegmentMap.keySet()) {
					assignedFlightSegment = AssignedFlightSegment.find(flightSegmentPK);
					containersToReturn.addAll(assignedFlightSegment
							.reassignMailFromFlight(assignedFlightSegmentMap.get(flightSegmentPK)));
				}
			} catch (FinderException ex) {
				log.info("DATA INCONSISTENT ");
				throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
			}
		}
		log.debug("" + "containersToReturn " + " " + containersToReturn);
		log.debug("ReassignController" + " : " + "reassignMailFromFlight" + " Exiting");
		return containersToReturn;
	}

	/**
	 * Utilty for finding syspar Mar 23, 2007, A-1739
	 * @param syspar
	 * @return
	 * @throws SystemException
	 */
	private String findSystemParameterValue(String syspar) {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/**
	 * This method is used to construct the ContainerDetailsVo from the ContainerVo A-1936
	 * @param containerVos
	 * @return
	 */
	private Collection<ContainerDetailsVO> constructConDetailsVOsForResdit(Collection<ContainerVO> containerVos) {
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		if (containerVos != null && containerVos.size() > 0) {
			containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
			for (ContainerVO containerVo : containerVos) {
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setCompanyCode(containerVo.getCompanyCode());
				containerDetailsVO.setPol(containerVo.getAssignedPort());
				containerDetailsVO.setContainerNumber(containerVo.getContainerNumber());
				containerDetailsVO.setCarrierCode(containerVo.getCarrierCode());
				containerDetailsVO.setCarrierId(containerVo.getCarrierId());
				containerDetailsVO.setFlightNumber(containerVo.getFlightNumber());
				containerDetailsVO.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
				containerDetailsVO.setSegmentSerialNumber(containerVo.getSegmentSerialNumber());
				containerDetailsVO.setOwnAirlineCode(containerVo.getOwnAirlineCode());
				containerDetailsVOs.add(containerDetailsVO);
			}
		}
		return containerDetailsVOs;
	}

	/**
	 * @author a-1936 This method is used to collect the AuditDetails
	 * @param container
	 * @param containerAuditVO
	 * @throws SystemException
	 */
	//TODO: Neo to rewrite audit
//	private void collectContainerAuditDetails(Container container, ContainerAuditVO containerAuditVO) {
//		log.debug("---------Setting ContainerAuditVO Details-------");
//		StringBuffer additionalInfo = new StringBuffer();
//		log.info("" + " container.getContainerPK() " + " " + container.getContainerPK());
//		String triggeringPoint = contextUtil.getTxContext(ContextUtil.TRIGGER_POINT);
//		containerAuditVO.setCompanyCode(container.getContainerPK().getCompanyCode());
//		containerAuditVO.setContainerNumber(container.getContainerPK().getContainerNumber());
//		containerAuditVO.setAssignedPort(container.getContainerPK().getAssignmentPort());
//		containerAuditVO.setCarrierId(container.getContainerPK().getCarrierId());
//		containerAuditVO.setFlightNumber(container.getContainerPK().getFlightNumber());
//		containerAuditVO.setFlightSequenceNumber(container.getContainerPK().getFlightSequenceNumber());
//		containerAuditVO.setLegSerialNumber(container.getContainerPK().getLegSerialNumber());
//		containerAuditVO.setUserId(container.getLastUpdateUser());
//		additionalInfo.append("Deleted from ");
//		if (!"-1".equals(container.getContainerPK().getFlightNumber())) {
//			additionalInfo.append(container.getCarrierCode()).append(" ")
//					.append(container.getContainerPK().getFlightNumber()).append(", ");
//		} else {
//			additionalInfo.append(container.getCarrierCode()).append(", ");
//		}
//		additionalInfo.append(localDateUtil.getLocalDate(container.getContainerPK().getAssignmentPort(), true)
//				.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))).append(", ");
//		if (!"-1".equals(container.getContainerPK().getFlightNumber())) {
//			additionalInfo.append(container.getContainerPK().getAssignmentPort()).append(" - ")
//					.append(container.getPou()).append(" ");
//		}
//		additionalInfo.append("in ").append(container.getContainerPK().getAssignmentPort());
//		containerAuditVO.setAdditionalInformation(additionalInfo.toString());
//		containerAuditVO.setTriggerPnt(triggeringPoint);
//		log.debug("collectContainerAuditDetails" + " : " + "...Finished construction of AuditVO" + " Exiting");
//	}

	/**
	 * This method is used to update the Container Status as Accepted during reassignment of mailbags when a mailbag is reassigned to a nonaccepted container A-1936
	 * @param toContainerVO
	 * @throws SystemException
	 */
	private void updateContainerAcceptance(ContainerVO toContainerVO) {
		log.debug("ReassignController" + " : " + "updateContainerAcceptance" + " Entering");
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(toContainerVO.getCompanyCode());
		containerPk.setContainerNumber(toContainerVO.getContainerNumber());
		containerPk.setAssignmentPort(toContainerVO.getAssignedPort());
		containerPk.setFlightNumber(toContainerVO.getFlightNumber());
		containerPk.setCarrierId(toContainerVO.getCarrierId());
		containerPk.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(toContainerVO.getLegSerialNumber());
		if (toContainerVO.getAssignedDate() == null) {
			toContainerVO.setAssignedDate(localDateUtil.getLocalDate(toContainerVO.getAssignedPort(), true));
		}
		Container container = null;
		try {
			container = Container.find(containerPk);

		if(toContainerVO.getLastUpdateTime()!=null){
			container.setLastUpdatedTime(Timestamp.valueOf(toContainerVO.getLastUpdateTime().toLocalDateTime()));
		}
		} catch (FinderException ex) {

			if (toContainerVO.isOflToRsnFlag())
				container = new Container(toContainerVO);
			else
				throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
		}
		if (MailConstantsVO.FLAG_YES.equals(container.getPaBuiltFlag())
				&& MailConstantsVO.FLAG_NO.equals(container.getAcceptanceFlag())) {
			Collection<ContainerVO> containers = new ArrayList<ContainerVO>();
			containers.add(toContainerVO);
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			mailController.flagResditsForULDAcceptance(constructConDetailsVOsForResdit(containers),
					toContainerVO.getAssignedPort());
		}
		updateContainerAcceptanceAudit(toContainerVO);
		container.setAcceptanceFlag(MailConstantsVO.FLAG_YES);

		log.debug("ReassignController" + " : " + "updateContainerAcceptance" + " Exiting");
	}

	private static void updateContainerAcceptanceAudit (ContainerVO toContainerVO) {
		StringBuilder additInfo = null;
		additInfo = new StringBuilder();
		additInfo.append(" Actual Weight: ").append(toContainerVO.getActualWeight());
		if (Objects.nonNull(toContainerVO.getContentId())) {
			additInfo.append(" Content ID: ").append(toContainerVO.getContentId());
		}
		additInfo.append(" Flight No: ").append(toContainerVO.getFlightNumber())
				.append(" Flight SequenceNumber: ").append(toContainerVO.getFlightSequenceNumber())
				.append(" POU: ").append(toContainerVO.getPou()).append(" Final Destination: ")
				.append(toContainerVO.getFinalDestination()).append(" POA Flag: ").append(toContainerVO.getPaBuiltFlag())
				.append(" TRN Flag: ").append(toContainerVO.getTransferFlag()).append(" OFL Flag: ")
				.append(toContainerVO.getOffloadFlag()).append(" TRA Flag: ").append(toContainerVO.getTransitFlag());
		ContextUtil.getInstance().getBean(MailController.class).auditContainerUpdates(toContainerVO,"CONACP", toContainerVO.getTransactionCode(), toContainerVO.toString(), toContainerVO.getTriggerPoint());
	}

	/**
	 * This method is used to reassign the MailBags From the Flight to Destination.. A-1739
	 * @param flightAssignedMailbags
	 * @param toDestinationContainerVO
	 * @param isOffload
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> reassignMailFromFlightToDestination(
			Collection<MailbagVO> flightAssignedMailbags, ContainerVO toDestinationContainerVO, boolean isOffload) {
		Collection<ContainerDetailsVO> emptyContainers = reassignMailFromFlight(flightAssignedMailbags);
		boolean isRemove = false;
		if (isOffload && flightAssignedMailbags != null && !flightAssignedMailbags.isEmpty()) {
			isRemove = flightAssignedMailbags.iterator().next().isRemove();
		}
		reassignMailToDestination(flightAssignedMailbags, toDestinationContainerVO);
		updateMailbagsForReassign(flightAssignedMailbags, toDestinationContainerVO);
		new MailController().validateAndReImportMailbagsToMRA(flightAssignedMailbags);
		if (isRemove) {
			return emptyContainers;
		}
		updateContainerAcceptance(toDestinationContainerVO);
		if (isOffload) {
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			mailController.flagResditForMailbagsFromReassign(MailConstantsVO.RESDIT_PENDING,
					toDestinationContainerVO.getAssignedPort(), flightAssignedMailbags);
		}
		log.debug("" + "THE 2222 FINAL DESTN IS  " + " " + toDestinationContainerVO.getFinalDestination());
		return emptyContainers;
	}

	/**
	 * @author a-1936 This method is used to construct the ULDAtAirportPK.
	 * @param mailbag
	 * @return
	 */
	private ULDAtAirportPK constructULDAtAirportPK(MailbagVO mailbag) {
		log.debug("ReassignController" + " : " + "constructULDAtAirportPK" + " Entering");
		ULDAtAirportPK uldAtAirportPK = new ULDAtAirportPK();
		uldAtAirportPK.setCompanyCode(mailbag.getCompanyCode());
		uldAtAirportPK.setAirportCode(mailbag.getScannedPort());
		uldAtAirportPK.setCarrierId(mailbag.getCarrierId());
		if (mailbag.getUldNumber() != null) {
			uldAtAirportPK.setUldNumber(mailbag.getUldNumber());
		} else {
			uldAtAirportPK.setUldNumber(mailbag.getContainerNumber());
		}
		if (mailbag.getContainerNumber() != null && MailConstantsVO.BULK_TYPE.equals(mailbag.getContainerType())
				&& !(mailbag.isOffloadAndReassign() && mailbag.getUldNumber() != null
				&& !mailbag.getUldNumber().isEmpty())) {
			log.info("THE MAL BAG IS  ASSOCIATED WITH A BARROW");
			uldAtAirportPK
					.setUldNumber(constructBulkULDNumber(mailbag.getFinalDestination(), mailbag.getCarrierCode()));
		}
		return uldAtAirportPK;
	}

	/**
	 * This method is used to reassign the mails from Destination.. Group the MailBags say U1-ARP U2-ARP U3-ARP A-1936
	 * @param destinationAssignedMailbags
	 * @throws SystemException
	 */
	public void reassignMailFromDestination(Collection<MailbagVO> destinationAssignedMailbags) {
		log.debug("ReassignController" + " : " + "reassignMailFromDestination" + " Entering");
		Map<ULDAtAirportPK, Collection<MailbagVO>> uldAtAirportMap = new HashMap<ULDAtAirportPK, Collection<MailbagVO>>();
		Collection<MailbagVO> mailbagVos = null;
		if (destinationAssignedMailbags != null && destinationAssignedMailbags.size() > 0) {
			for (MailbagVO mailbag : destinationAssignedMailbags) {
				ULDAtAirportPK uldAtAirportPK = constructULDAtAirportPK(mailbag);
				mailbagVos = uldAtAirportMap.get(uldAtAirportPK);
				if (mailbagVos == null) {
					mailbagVos = new ArrayList<MailbagVO>();
					uldAtAirportMap.put(uldAtAirportPK, mailbagVos);
				}
				mailbagVos.add(mailbag);
			}
			try {
				for (ULDAtAirportPK uldAtAirportPK : uldAtAirportMap.keySet()) {
					Collection<MailbagVO> mailbagVosFromMap = uldAtAirportMap.get(uldAtAirportPK);
					if (uldAtAirportPK.getUldNumber() != null) {
						ULDAtAirport uldAtAirport = ULDAtAirport.find(uldAtAirportPK);
						uldAtAirport.reassignMailFromDestination(mailbagVosFromMap);
					}
				}
			} catch (FinderException ex) {
				log.info("DATA INCONSISTENT ULDATAIRPORT NOT FOUND}");
			}
		}
		log.debug("ReassignController" + " : " + "reassignMailFromDestination" + " Exiting");
	}

	/**
	 * @author a-1936 This method is used to reassign the mailbags FromDestination to Destination
	 * @param mailbags
	 * @param toContainerVO
	 * @throws SystemException
	 */
	public void reassignMailFromDestnToDestn(Collection<MailbagVO> mailbags, ContainerVO toContainerVO) {
		log.debug("ReassignController" + " : " + "reassignMailFromDestinationToDestination" + " Entering");
		String sysParForInvetory = "mailtracking.defaults.inventoryenabled";
		boolean isInventory = MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(sysParForInvetory));
		for (MailbagVO mailbagVO : mailbags) {
			if (mailbagVO.getUldNumber() == null) {
				mailbagVO.setUldNumber(toContainerVO.getContainerNumber());
			}
		}
		reassignMailFromDestination(mailbags);
		reassignMailToDestination(mailbags, toContainerVO);
		updateMailbagsForReassign(mailbags, toContainerVO);
		updateContainerAcceptance(toContainerVO);
		log.debug("" + "IS INVNETORY ENABLED -- TRUE " + " " + isInventory);
		if (!isInventory) {
			flagResditsForMailbagReassign(mailbags, toContainerVO);
		}
		String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		if (Objects.nonNull(importEnabled) && !importEnabled.isEmpty() && importEnabled.contains("D")) {
			new MailController().reImportPABuiltMailbagsToMRA(mailbags);
		}
		boolean provisionalRateImport = false;
		Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(toContainerVO, mailbags,
				MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport);
		log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
		if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
			if (importEnabled != null && importEnabled.contains("M")) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		String provisionalRateimportEnabled = findSystemParameterValue(
				MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
			provisionalRateImport = true;
			Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toContainerVO, mailbags,
					MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport);
			if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		log.debug("ReassignController" + " : " + "reassignMailFromDestinationToDestination" + " Exiting");
	}

	private AssignedFlightSegment findAssignedFlightSegment(String companyCode, int carrierId, String flightNumber,
															long flightSequenceNumber, int segmentSerialNumber) throws FinderException {
		AssignedFlightSegmentPK assignedFlightSegPK = new AssignedFlightSegmentPK();
		assignedFlightSegPK.setCompanyCode(companyCode);
		assignedFlightSegPK.setCarrierId(carrierId);
		assignedFlightSegPK.setFlightNumber(flightNumber);
		assignedFlightSegPK.setFlightSequenceNumber(flightSequenceNumber);
		assignedFlightSegPK.setSegmentSerialNumber(segmentSerialNumber);
		return AssignedFlightSegment.find(assignedFlightSegPK);
	}

	public void reassignMailToFlight(Collection<MailbagVO> mailbags, ContainerVO toContainerVO) {
		log.debug("ReassignController" + " : " + "reassignMailToFlight" + " Entering");
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		AssignedFlightSegmentVO assignedFlightSegmentVO = new AssignedFlightSegmentVO();
		if (toContainerVO.isOflToRsnFlag()) {
			assignedFlightSegmentVO.setCompanyCode(toContainerVO.getCompanyCode());
			assignedFlightSegmentVO.setCarrierId(toContainerVO.getCarrierId());
			assignedFlightSegmentVO.setFlightNumber(toContainerVO.getFlightNumber());
			assignedFlightSegmentVO.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
			assignedFlightSegmentVO.setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
			assignedFlightSegmentVO.setPol(logonAttributes.getAirportCode());
			assignedFlightSegmentVO.setPou(toContainerVO.getPou());
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			operationalFlightVO.setAirportCode(logonAttributes.getAirportCode());
			operationalFlightVO.setCompanyCode(toContainerVO.getCompanyCode());
			operationalFlightVO.setFlightNumber(toContainerVO.getFlightNumber());
			operationalFlightVO.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
			operationalFlightVO.setLegSerialNumber(toContainerVO.getLegSerialNumber());
			operationalFlightVO.setCarrierId(toContainerVO.getCarrierId());
			operationalFlightVO.setPol(logonAttributes.getAirportCode());
			operationalFlightVO.setFlightDate(toContainerVO.getFlightDate());
			try {
				validateAndCreateAssignedFlight(operationalFlightVO);
			} catch (ContainerAssignmentException e) {
				throw new SystemException(e.getMessage(), e.getMessage(), e);
			}
		}
		if (mailbags != null && mailbags.size() > 0) {
			AssignedFlightSegment assignedFlightSegment = null;
			try {
				assignedFlightSegment = findAssignedFlightSegment(toContainerVO.getCompanyCode(),
						toContainerVO.getCarrierId(), toContainerVO.getFlightNumber(),
						toContainerVO.getFlightSequenceNumber(), toContainerVO.getSegmentSerialNumber());
			} catch (FinderException exception) {
				if (toContainerVO.isOflToRsnFlag()) {
					assignedFlightSegment = new AssignedFlightSegment(assignedFlightSegmentVO);
				} else {
					throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
				}
			}
			assignedFlightSegment.reassignMailToFlight(mailbags, toContainerVO);
		}
		log.debug("ReassignController" + " : " + "reassignMailToFlight" + " Exiting");
	}

	/**
	 * @param mailbags
	 * @param toContainerVO
	 * @throws SystemException
	 */
	private void updateMailbagVOsForResdit(Collection<MailbagVO> mailbags, ContainerVO toContainerVO) {
		for (MailbagVO mailbagVO : mailbags) {
			mailbagVO.setCarrierCode(toContainerVO.getCarrierCode());
			mailbagVO.setCarrierId(toContainerVO.getCarrierId());
			mailbagVO.setFlightNumber(toContainerVO.getFlightNumber());
			mailbagVO.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
			mailbagVO.setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
			mailbagVO.setUldNumber(toContainerVO.getContainerNumber());
			mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
		}
	}

	/**
	 * Flags the resdits for all mailbags which were reassigned. Before calling the resditcontroller, the method updates the mailbags with the required details for RESDIT A-1739
	 * @param mailbags
	 * @param toContainerVO
	 * @throws SystemException
	 */
	private void flagResditsForMailbagReassign(Collection<MailbagVO> mailbags, ContainerVO toContainerVO) {
		log.debug("ReassignController" + " : " + "flagResditsForMailbagReassign" + " Exiting");
		Collection<MailbagVO> mailbagVOsForResdit = new ArrayList<>();
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.debug("" + "Resdit Enabled " + " " + resditEnabled);
			populateMailbagVOsForResdit(mailbags, mailbagVOsForResdit);
			updateMailbagVOsForResdit(mailbagVOsForResdit, toContainerVO);
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			mailController.flagResditsForMailbagReassign(mailbagVOsForResdit, toContainerVO);
			log.debug("ReassignController" + " : " + "flagResditsForMailbagReassign" + " Exiting");
		}
	}

	/**
	 * @param mailbags
	 * @param toContainerVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> reassignMailFromFlightToFlight(Collection<MailbagVO> mailbags,
																		 ContainerVO toContainerVO) {
		log.debug("ReassignController" + " : " + "reassignMailbagsFlightToFlight" + " Entering");
		Collection<ContainerDetailsVO> emptyContainers = reassignMailFromFlight(mailbags);
		reassignMailToFlight(mailbags, toContainerVO);
		updateMailbagsForReassign(mailbags, toContainerVO);
		updateContainerAcceptance(toContainerVO);
		flagResditsForMailbagReassign(mailbags, toContainerVO);
		String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		if (Objects.nonNull(importEnabled) && !importEnabled.isEmpty() && importEnabled.contains("D")) {
			new MailController().reImportPABuiltMailbagsToMRA(mailbags);
		}
		boolean provisionalRateImport = false;
		Collection<RateAuditVO> rateAuditVOs = mailController.createRateAuditVOs(toContainerVO, mailbags,
				MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport);
		log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
		if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
			if (importEnabled != null && importEnabled.contains("M")) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		String provisionalRateimportEnabled = findSystemParameterValue(
				MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
			provisionalRateImport = true;
			Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toContainerVO, mailbags,
					MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport);
			if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		log.debug("ReassignController" + " : " + "reassignMailbagsFlightToFlight" + " Exiting");
		return emptyContainers;
	}

	/**
	 * Reassigns mailbags Sep 12, 2007, a-1739 Revision 2 Included chks for transferred mailbags
	 * @param toContainerVO
	 * @return
	 * @throws SystemException
	 * @throws FlightClosedException
	 * @throws InvalidFlightSegmentException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	public Collection<ContainerDetailsVO> reassignMailbags(Collection<MailbagVO> mailbagsToReassign,
														   ContainerVO toContainerVO) throws FlightClosedException {
		log.debug("ReassignController" + " : " + "reassignMailbags" + " Entering");
		Collection<ContainerDetailsVO> emptyContainers = null;
		if (mailbagsToReassign != null && mailbagsToReassign.size() > 0) {
			Collection<MailbagVO> flightAssignedMailbags = new ArrayList<MailbagVO>();
			Collection<MailbagVO> destAssignedMailbags = new ArrayList<MailbagVO>();
			if (isReassignableMailbags(mailbagsToReassign, flightAssignedMailbags, destAssignedMailbags)) {
				if (toContainerVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
					if (flightAssignedMailbags != null && flightAssignedMailbags.size() > 0) {
						emptyContainers = reassignMailFromFlightToDestination(flightAssignedMailbags, toContainerVO,
								false);
					}
					if (destAssignedMailbags != null && destAssignedMailbags.size() > 0) {
						reassignMailFromDestnToDestn(destAssignedMailbags, toContainerVO);
					}
				} else {
					if (flightAssignedMailbags != null && flightAssignedMailbags.size() > 0) {
						emptyContainers = reassignMailFromFlightToFlight(flightAssignedMailbags, toContainerVO);
					}
					if (destAssignedMailbags != null && destAssignedMailbags.size() > 0) {
						reassignMailFromDestinationToFlight(destAssignedMailbags, toContainerVO);
					}
				}
			}
		}
		log.debug("ReassignController" + " : " + "reassignMailbags" + " Exiting");
		return emptyContainers;
	}

	/**
	 * A-1739
	 * @param destAssignedMailbags
	 * @param toContainerVO
	 * @throws SystemException
	 */
	private void reassignMailFromDestinationToFlight(Collection<MailbagVO> destAssignedMailbags,
													 ContainerVO toContainerVO) {
		log.debug("ReassignController" + " : " + "reassignMailFromDestinationToFlight" + " Entering");
		reassignMailFromDestination(destAssignedMailbags);
		reassignMailToFlight(destAssignedMailbags, toContainerVO);
		updateMailbagsForReassign(destAssignedMailbags, toContainerVO);
		updateContainerAcceptance(toContainerVO);
		flagResditsForMailbagReassign(destAssignedMailbags, toContainerVO);
		String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		Collection<MailbagVO> mailbagsToImport = new MailController()
				.validateAndReImportMailbagsToMRA(destAssignedMailbags);
		boolean provisionalRateImport = false;
		Collection<RateAuditVO> rateAuditVOs = mailController.createRateAuditVOs(toContainerVO, mailbagsToImport,
				MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport);
		log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
		if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
			if (importEnabled != null && importEnabled.contains("M")) {
				try {
					mailOperationsMRAProxy.importMRAData(rateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		String provisionalRateimportEnabled = findSystemParameterValue(
				MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
			provisionalRateImport = true;
			Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toContainerVO,
					mailbagsToImport, MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport);
			if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
				try {
					mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage(), e.getMessage(), e);
				}
			}
		}
		log.debug("ReassignController" + " : " + "reassignMailFromDestinationToFlight" + " Exiting");
	}

	private AssignedFlightPK constructAssignedFlightPK(OperationalFlightVO operationalFlightVO) {
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
		assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		return assignedFlightPk;
	}

	/**
	 * Validates an assignedFlight
	 * @param flightVO
	 * @return the operationalflightVO
	 * @throws SystemException
	 */
	private OperationalFlightVO validateAssignedFlight(OperationalFlightVO flightVO) {
		log.debug("ReassignController" + " : " + "validateAssignedFlight" + " Entering");
		AssignedFlightPK flightPK = constructAssignedFlightPK(flightVO);
		try {
			AssignedFlight.find(flightPK);
			return flightVO;
		} catch (FinderException ex) {
			log.debug("no assignedflight");
		}
		log.debug("ReassignController" + " : " + "validateAssignedFlight" + " Exiting");
		return null;
	}

	/**
	 * A-1739
	 * @param operationalFlightVO
	 * @return
	 */
	private Object[] constructFltErrorData(OperationalFlightVO operationalFlightVO) {
		log.debug("" + " " + " " + operationalFlightVO);
		return new String[] {
				new StringBuilder().append(operationalFlightVO.getCarrierCode()).append(" ")
						.append(operationalFlightVO.getFlightNumber()).toString(),
				operationalFlightVO.getFlightDate().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)) };
	}

	private OperationalFlightVO constructFlightVOForContainer(ContainerVO containerToReassign) {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(containerToReassign.getCompanyCode());
		operationalFlightVO.setCarrierCode(containerToReassign.getCarrierCode());
		operationalFlightVO.setCarrierId(containerToReassign.getCarrierId());
		operationalFlightVO.setFlightNumber(containerToReassign.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(containerToReassign.getFlightSequenceNumber());
		operationalFlightVO.setLegSerialNumber(containerToReassign.getLegSerialNumber());
		operationalFlightVO.setPol(containerToReassign.getAssignedPort());
		operationalFlightVO.setFlightDate(containerToReassign.getFlightDate());
		return operationalFlightVO;
	}

	/**
	 * This method checks if the to and from flights are closed for operation. It also groups the containers into two depending on whether assigned to destination or whether assigned to a flight A-1739
	 * @param containersToReassign
	 * @param operationalFlightVO
	 * @param destAssignedContainers
	 * @param flightAssignedContainers
	 * @return true if all the containers are reassignable
	 * @throws SystemException
	 * @throws FlightClosedException
	 * @throws ContainerAssignmentException
	 */
	private boolean isReassignableContainers(Collection<ContainerVO> containersToReassign,
											 OperationalFlightVO operationalFlightVO, Collection<ContainerVO> destAssignedContainers,
											 Collection<ContainerVO> flightAssignedContainers)
			throws FlightClosedException, ContainerAssignmentException {
		log.debug("ReassignController" + " : " + "isReassignable" + " Entering");
		ZonedDateTime GHTtime = null;
		if (operationalFlightVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT
				&& validateAssignedFlight(operationalFlightVO) != null
				&& isFlightClosedForOperations(operationalFlightVO)
				&& !(containersToReassign != null && !containersToReassign.isEmpty() && MailConstantsVO.ONLOAD_MESSAGE
				.equals(containersToReassign.iterator().next().getMailSource()))) {
			throw new FlightClosedException(FlightClosedException.FLIGHT_STATUS_CLOSED,
					constructFltErrorData(operationalFlightVO));
		}
		if (containersToReassign != null && !containersToReassign.isEmpty()) {
			GHTtime = new ULDForSegment().findGHTForMailbags(operationalFlightVO);
		}
		if (containersToReassign != null && containersToReassign.size() > 0) {
			for (ContainerVO containerToReassign : containersToReassign) {
				containerToReassign.setGHTtime(GHTtime);
				if (containerToReassign.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
					destAssignedContainers.add(containerToReassign);
				} else {
					OperationalFlightVO containerFlight = constructFlightVOForContainer(containerToReassign);
					if (!MailConstantsVO.ONLOAD_MESSAGE.equals(containerToReassign.getMailSource())
							&& isFlightClosedForOperations(containerFlight)) {
						throw new FlightClosedException(FlightClosedException.FLIGHT_STATUS_CLOSED,
								constructFltErrorData(containerFlight));
					}
					flightAssignedContainers.add(containerToReassign);
				}
			}
		}
		log.debug("ReassignController" + " : " + "isReassignable" + " Exiting");
		return true;
	}

	private void groupULDsForReassignOrTsfr(Collection<ContainerVO> containers,
											Collection<ContainerVO> containersToTsfr, Collection<ContainerVO> containersToReassign,
											Collection<ContainerVO> containersToRem) throws MailDefaultStorageUnitException {
		log.debug("ReassignController" + " : " + "groupULDsForInventoryReassign" + " Entering");
		boolean isfromHHT = false;
		String storageUnit = findSystemParameterValue(DEFAULT_STORAGEUNITFORMAIL);
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		for (ContainerVO containerVO : containers) {
			if (containerVO.getContainerNumber() != null && storageUnit != null && !"".equals(storageUnit)
					&& (storageUnit.equals(containerVO.getContainerNumber()))) {
				throw new MailDefaultStorageUnitException(
						MailOperationsBusinessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT, null);
			}
			if (containerVO.getFlightSequenceNumber() > 0) {
				containersToReassign.add(containerVO);
			} else if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
				containersToReassign.add(containerVO);
			} else if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
				ContainerAssignmentVO conAsgVO = Container.findArrivalDetailsForULD(containerVO);
				if (conAsgVO != null
						&& (conAsgVO.getFlightNumber() != null
						&& conAsgVO.getFlightNumber().equals(containerVO.getFlightNumber()))
						&& conAsgVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber()) {
				} else {
					conAsgVO = null;
				}
				if (conAsgVO == null) {
					containersToReassign.add(containerVO);
					log.debug("" + "THE CONTAINER ASSIGNMENT VO IS " + " " + conAsgVO);
				} else if (ContainerVO.FLAG_YES.equals(conAsgVO.getArrivalFlag())
						&& !MailConstantsVO.FLAG_YES.equals(conAsgVO.getTransferFlag())) {
					ContainerAssignmentVO latestContainerAssignmentVO = new MailController()
							.findLatestContainerAssignment(containerVO.getContainerNumber());
					if ("WS".equals(containerVO.getSource())) {
						if (latestContainerAssignmentVO != null
								&& !"Y".equals(latestContainerAssignmentVO.getArrivalFlag())) {
							if (MailConstantsVO.DESTN_FLT == latestContainerAssignmentVO.getFlightSequenceNumber()) {
								containersToReassign.add(containerVO);
								isfromHHT = true;
							}
						}
					}
					if (!isfromHHT) {
						log.debug("Container Collected for the Transfer");
						ContainerVO containerToTsfr = new ContainerVO();
						containerToTsfr.setCompanyCode(containerVO.getCompanyCode());
						containerToTsfr.setContainerNumber(containerVO.getContainerNumber());
						containerToTsfr.setCarrierId(conAsgVO.getCarrierId());
						containerToTsfr.setCarrierCode(conAsgVO.getCarrierCode());
						containerToTsfr.setPou(containerVO.getAssignedPort());
						containerToTsfr.setType(containerVO.getType());
						containerToTsfr.setFlightNumber(conAsgVO.getFlightNumber());
						containerToTsfr.setFlightSequenceNumber(conAsgVO.getFlightSequenceNumber());
						containerToTsfr.setLegSerialNumber(conAsgVO.getLegSerialNumber());
						containerToTsfr.setSegmentSerialNumber(conAsgVO.getSegmentSerialNumber());
						containerToTsfr.setAssignedPort(conAsgVO.getAirportCode());
						containerToTsfr.setArrivedStatus(conAsgVO.getArrivalFlag());
						containerToTsfr.setTransferFlag(conAsgVO.getTransferFlag());
						containerToTsfr.setMailSource(containerVO.getMailSource());
						containerToTsfr.setFinalDestination(containerVO.getFinalDestination());
						containersToTsfr.add(containerToTsfr);
						containersToRem.add(containerVO);
					}
				} else {
					containersToReassign.add(containerVO);
				}
			}
		}
		log.debug("ReassignController" + " : " + "groupULDsForInventoryReassign" + " Exiting");
	}

	/**
	 * This method updates the ULDAtAirportVO with the new details A-1739
	 * @param uldAtAirportVO
	 * @param toDestinationVO
	 * @param assignedContainerVO
	 */
	public void updateULDAtAirportVO(ULDAtAirportVO uldAtAirportVO, OperationalFlightVO toDestinationVO,
									 ContainerVO assignedContainerVO) {
		uldAtAirportVO.setCompanyCode(toDestinationVO.getCompanyCode());
		uldAtAirportVO.setCarrierId(toDestinationVO.getCarrierId());
		uldAtAirportVO.setCarrierCode(toDestinationVO.getCarrierCode());
		uldAtAirportVO.setAirportCode(toDestinationVO.getPol());
		uldAtAirportVO.setFinalDestination(assignedContainerVO.getFinalDestination());
		if (assignedContainerVO.isUldTobarrow()) {
			String fromULDNumber = constructBulkULDNumber(assignedContainerVO.getFinalDestination(),
					assignedContainerVO.getCarrierCode());
			uldAtAirportVO.setUldNumber(fromULDNumber);
		}
		if (assignedContainerVO.isBarrowToUld()) {
			uldAtAirportVO.setUldNumber(assignedContainerVO.getContainerNumber());
		}
		if (uldAtAirportVO.getMailbagInULDAtAirportVOs() != null
				&& !uldAtAirportVO.getMailbagInULDAtAirportVOs().isEmpty()) {
			uldAtAirportVO.getMailbagInULDAtAirportVOs().forEach(mailbagInULDAtAirport -> {
				mailbagInULDAtAirport.setCarrierId(toDestinationVO.getCarrierId());
				mailbagInULDAtAirport.setMailSource(assignedContainerVO.getMailSource());
			});
		}
		if (!MailConstantsVO.BULK_TYPE.equals(assignedContainerVO.getType())) {
			uldAtAirportVO.setRemarks(assignedContainerVO.getRemarks());
		} else {
			Collection<DSNInULDAtAirportVO> dsnsInULD = uldAtAirportVO.getDsnInULDAtAirportVOs();
			if (dsnsInULD != null) {
				int totalbags = 0;
				double totalweight = 0;
				for (DSNInULDAtAirportVO dsnInULD : dsnsInULD) {
					totalbags += dsnInULD.getAcceptedBags();
					totalweight += dsnInULD.getAcceptedWeight().getRoundedValue().doubleValue();
					Collection<DSNInContainerAtAirportVO> dsnsInCon = dsnInULD.getDsnInContainerAtAirports();
					if (dsnsInCon != null) {
						for (DSNInContainerAtAirportVO dsnInCon : dsnsInCon) {
							if (dsnInCon.getContainerNumber().equals(assignedContainerVO.getContainerNumber())) {
								dsnInCon.setRemarks(assignedContainerVO.getRemarks());
							}
						}
					}
				}
				uldAtAirportVO.setNumberOfBags(totalbags);
				uldAtAirportVO
						.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(totalweight)));
			}
		}
	}

	/**
	 * A-1739
	 * @param destAssignedContainerVO
	 * @param fromULDNumber
	 * @return
	 */
	private ULDAtAirportPK constructULDArpPKFromContainer(ContainerVO destAssignedContainerVO, String fromULDNumber) {
		ULDAtAirportPK uldArpPK = new ULDAtAirportPK();
		uldArpPK.setCompanyCode(destAssignedContainerVO.getCompanyCode());
		uldArpPK.setCarrierId(destAssignedContainerVO.getCarrierId());
		uldArpPK.setAirportCode(destAssignedContainerVO.getAssignedPort());
		uldArpPK.setUldNumber(fromULDNumber);
		return uldArpPK;
	}

	/**
	 * This method is used to reassign a container from 1 destn to another A-1739 isRemOnly needs to checked before using toDestinationVO******** isRemOnly needs to checked before using toDestinationVO
	 * @param destAssignedContainers
	 * @param toDestinationVO
	 * @throws SystemException
	 */
	public Collection<ContainerVO> reassignContainerFromDestToDest(Collection<ContainerVO> destAssignedContainers,
																   OperationalFlightVO toDestinationVO) {
		log.debug("ReassignController" + " : " + "reassignFromDestToDest" + " Entering");
		boolean isRemOnly = false;
		int numberOfBarrowspresent = 0;
		if (toDestinationVO == null) {
			isRemOnly = true;
		}
		Collection<ContainerVO> paBuiltContainers = new ArrayList<ContainerVO>();
		Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>();
		for (ContainerVO destAssignedContainerVO : destAssignedContainers) {
			boolean isNotAccepted = true;
			boolean isReassignSuccess = false;
			Collection<MailbagVO> mailbagToFlag = new ArrayList<MailbagVO>();
			if (CONST_YES.equals(destAssignedContainerVO.getAcceptanceFlag())) {
				isNotAccepted = false;
				String fromULDNumber = destAssignedContainerVO.getContainerNumber();
				ULDAtAirport uldAtAirport1 = null;
				if (MailConstantsVO.BULK_TYPE.equals(destAssignedContainerVO.getType())) {
					if (destAssignedContainerVO.isContainerDestChanged()) {
						ContainerPK containerPk = new ContainerPK();
						containerPk.setCompanyCode(destAssignedContainerVO.getCompanyCode());
						containerPk.setContainerNumber(destAssignedContainerVO.getContainerNumber());
						containerPk.setCarrierId(destAssignedContainerVO.getCarrierId());
						containerPk.setFlightNumber(destAssignedContainerVO.getFlightNumber());
						containerPk.setFlightSequenceNumber(destAssignedContainerVO.getFlightSequenceNumber());
						containerPk.setAssignmentPort(destAssignedContainerVO.getAssignedPort());
						containerPk.setLegSerialNumber(destAssignedContainerVO.getLegSerialNumber());
						Container prevContainer = null;
						try {
							prevContainer = Container.find(containerPk);
						} catch (FinderException finderException) {
						}
						if (prevContainer != null) {
							fromULDNumber = constructBulkULDNumber(prevContainer.getFinalDestination(),
									destAssignedContainerVO.getCarrierCode());
						}
					} else
						fromULDNumber = constructBulkULDNumber(destAssignedContainerVO.getFinalDestination(),
								destAssignedContainerVO.getCarrierCode());
				}
				ULDAtAirport uldAtAirport = null;
				try {
					uldAtAirport = ULDAtAirport
							.find(constructULDArpPKFromContainer(destAssignedContainerVO, fromULDNumber));
				} catch (FinderException finderException) {
					if (!destAssignedContainerVO.isContainerDestChanged()) {
						return null;
					}
				}
				Collection<MailbagInULDAtAirportVO> dsnsToReassign = null;
				String containerNumber = destAssignedContainerVO.getContainerNumber();
				ULDAtAirportVO uldAtAirportVO = null;
				if (MailConstantsVO.ULD_TYPE.equals(destAssignedContainerVO.getType())) {
					if (destAssignedContainerVO.isUldTobarrow()) {
						dsnsToReassign = uldAtAirport.reassignBulkContainer(containerNumber);
					}
					uldAtAirportVO = uldAtAirport.retrieveVO();
					uldAtAirport.remove();
					if (!isRemOnly) {
						updateULDAtAirportVO(uldAtAirportVO, toDestinationVO, destAssignedContainerVO);
						if (destAssignedContainerVO.isUldTobarrow()) {
							try {
								uldAtAirport = ULDAtAirport.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));
								uldAtAirport.assignBulkContainer(dsnsToReassign);
							} catch (FinderException finderException) {
								new ULDAtAirport(uldAtAirportVO);
							}
						} else {
							new ULDAtAirport(uldAtAirportVO);
						}
						log.debug("" + "retrieved VO -->" + " " + uldAtAirportVO);
						isReassignSuccess = true;
					}
				} else if (MailConstantsVO.BULK_TYPE.equals(destAssignedContainerVO.getType())) {
					String toULDNumber = constructBulkULDNumber(destAssignedContainerVO.getFinalDestination(),
							destAssignedContainerVO.getCarrierCode());
					if (uldAtAirport != null) {
						dsnsToReassign = uldAtAirport.reassignBulkContainer(containerNumber);
					}
					log.debug("" + "retrieved  -->" + " " + dsnsToReassign);
					try {
						if (destAssignedContainerVO.isBarrowToUld()) {
							uldAtAirportVO = new ULDAtAirportVO();
							uldAtAirportVO.setUldNumber(destAssignedContainerVO.getContainerNumber());
							uldAtAirportVO.setMailbagInULDAtAirportVOs(dsnsToReassign);
							updateULDAtAirportVO(uldAtAirportVO, toDestinationVO, destAssignedContainerVO);
							log.debug("" + "retrieved uld for bulk -->" + " " + uldAtAirportVO);
							new ULDAtAirport(uldAtAirportVO);
							isReassignSuccess = true;
						} else {
							ULDAtAirport toULDAtAirport = ULDAtAirport
									.find(constructULDArpPKFromOpFlt(toDestinationVO, toULDNumber));
							toULDAtAirport.assignBulkContainer(dsnsToReassign);
							isReassignSuccess = true;
						}
					} catch (FinderException exception) {
						uldAtAirportVO = new ULDAtAirportVO();
						uldAtAirportVO.setUldNumber(toULDNumber);
						uldAtAirportVO.setMailbagInULDAtAirportVOs(dsnsToReassign);
						updateULDAtAirportVO(uldAtAirportVO, toDestinationVO, destAssignedContainerVO);
						log.debug("" + "retrieved uld for bulk -->" + " " + uldAtAirportVO);
						new ULDAtAirport(uldAtAirportVO);
						isReassignSuccess = true;
					}
				}
				if (isReassignSuccess) {
					if (dsnsToReassign == null && uldAtAirportVO.getMailbagInULDAtAirportVOs() != null) {
						dsnsToReassign = uldAtAirportVO.getMailbagInULDAtAirportVOs();
					}
					if (dsnsToReassign != null && !dsnsToReassign.isEmpty()) {
						Collection<MailbagVO> mailbagVOs = constructMailbagVOsFromAirport(dsnsToReassign,
								toDestinationVO.getCompanyCode());
						updateDSNForConReassign(mailbagVOs, toDestinationVO, destAssignedContainerVO,
								MailConstantsVO.DESTN_FLT, null);
						mailbagToFlag.addAll(mailbagVOs);
					}
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.flagHistoryForContainerReassignment(toDestinationVO, destAssignedContainerVO,
							mailbagToFlag, mailController.getTriggerPoint());
					mailController.flagAuditForContainerReassignment(toDestinationVO, destAssignedContainerVO,
							mailbagToFlag);
					boolean provisionalRateImport = false;
					Collection<RateAuditVO> rateAuditVOs = ContextUtil.getInstance().getBean(MailController.class)
							.createRateAuditVOs(toDestinationVO,
							destAssignedContainerVO, mailbagToFlag, MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,
							provisionalRateImport);
					log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
					if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
						String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
						if (importEnabled != null && importEnabled.contains("M")) {
							try {
								mailOperationsMRAProxy.importMRAData(rateAuditVOs);
							} catch (BusinessException e) {
								throw new SystemException(e.getMessage(), e.getMessage(), e);
							}
						}
					}
					String provisionalRateimportEnabled = findSystemParameterValue(
							MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
					if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
						provisionalRateImport = true;
						Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(
								toDestinationVO, destAssignedContainerVO, mailbagToFlag,
								MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport);
						log.debug("" + "ProvisionalRateAuditVO-->" + " " + provisionalRateAuditVOs);
						if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
							try {
								mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
							} catch (BusinessException e) {
								throw new SystemException(e.getMessage(), e.getMessage(), e);
							}
						}
					}
				}
			}
			if (isReassignSuccess || isNotAccepted || isRemOnly) {
				updateReassignedContainer(destAssignedContainerVO, toDestinationVO, MailConstantsVO.DESTN_FLT);
				if (isReassignSuccess) {
					if (MailConstantsVO.FLAG_YES.equals(destAssignedContainerVO.getPaBuiltFlag())) {
						paBuiltContainers.add(destAssignedContainerVO);
					}
				}
			}
			if (!isRemOnly) {
				ContainerVO containerReturnVO = new ContainerVO();
				containerReturnVO= mailOperationsMapper.copyContainerVO( destAssignedContainerVO);
				containerReturnVO.setFlightSequenceNumber(toDestinationVO.getFlightSequenceNumber());
				containerReturnVO.setFlightNumber(toDestinationVO.getFlightNumber());
				containerReturnVO.setLegSerialNumber(toDestinationVO.getLegSerialNumber());
				containerReturnVO.setCarrierCode(toDestinationVO.getCarrierCode());
				containerReturnVO.setFlightDate(toDestinationVO.getFlightDate());
				containersForReturn.add(containerReturnVO);
			}
			if (paBuiltContainers.size() > 0 || mailbagToFlag.size() > 0) {
				flagResditsForContainerReassign(mailbagToFlag, paBuiltContainers, toDestinationVO,
						MailConstantsVO.DESTN_FLT);
			}
		}
		log.debug("ReassignController" + " : " + "reassignFromDestToDest" + " Exiting");
		return containersForReturn;
	}

	/**
	 * Flags the RESDITs related to a ULD reassign
	 * @param paBuiltContainers
	 * @param toFlightVO
	 * @param segmentSerialNumber
	 */
	private void flagResditsForContainerReassign(Collection<MailbagVO> mailbagVOs,
												 Collection<ContainerVO> paBuiltContainers, OperationalFlightVO toFlightVO, int segmentSerialNumber) {
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.debug("" + "Resdit Enabled " + " " + resditEnabled);
			log.debug("ReassignController" + " : " + "flagResditsForContainerReassign" + " Entering");
			Collection<ContainerDetailsVO> flightUpdatedContainers = updateFlightOfReassignedContainers(
					paBuiltContainers, toFlightVO, segmentSerialNumber);
			boolean hasFlightDeparted = MailConstantsVO.FLIGHT_STATUS_DEPARTED.equals(toFlightVO.getFlightStatus())
					|| toFlightVO.isAtdCaptured();
			if (toFlightVO.getFlightNumber() != null && !"-1".equals(toFlightVO.getFlightNumber())) {
				for (MailbagVO mailVO : mailbagVOs) {
					mailVO.setFlightNumber(toFlightVO.getFlightNumber());
					mailVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
					mailVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
					mailVO.setFlightDate(toFlightVO.getFlightDate());
					mailVO.setSegmentSerialNumber(toFlightVO.getSegSerNum());
				}
			}
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			mailController.flagResditForContainerReassign(mailbagVOs, flightUpdatedContainers, toFlightVO,
					hasFlightDeparted);
		}
	}

	/**
	 * This method updates the MALBAGMST with the latest flight details. There is only one dummy mailbagvo in the dsnvo given to the DSN entity This is because All the mailbags in that entity are to be updated with the same flight details. It also find the mailbags in the DSN for flagging RESDITs for all those receptacles These mailbags are used in the reassignflighttodest case. A-1739
	 * @param toFlightVO
	 * @param containerVO
	 * @param toFlightSegmentSerialNum
	 * @throws SystemException
	 */
	private void updateDSNForConReassign(Collection<MailbagVO> mailbagVOs, OperationalFlightVO toFlightVO,
										 ContainerVO containerVO, int toFlightSegmentSerialNum, Collection<String> mailIdsForMonitorSLAs) {
		log.debug("ReassignController" + " : " + "updateDSN" + " Entering");
		boolean isFlightToDest = false;
		boolean canUpdateDSNArp = false;
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = ContextUtil.getInstance().getBean(ContextUtil.class).callerLoginProfile();
		} finally {
		}
		if (containerVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT
				&& toFlightVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
			isFlightToDest = true;
			canUpdateDSNArp = true;
		}
		if (containerVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT
				&& toFlightVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT) {
			canUpdateDSNArp = true;
		}
		MailbagPK mailbagPK = null;
		Mailbag mailbag = null;
		try {
			if (mailbagVOs != null) {
				for (MailbagVO mailbagvo : mailbagVOs) {
					mailbagPK = new MailbagPK();
					mailbagPK.setCompanyCode(mailbagvo.getCompanyCode());
					mailbagPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
					mailbag = Mailbag.find(mailbagPK);
					if (mailbag != null) {
						mailbagvo.setMailbagId(mailbag.getMailIdr());
						mailbagvo.setOoe(mailbag.getOrginOfficeOfExchange());
						mailbagvo.setDoe(mailbag.getDestinationOfficeOfExchange());
						mailbagvo.setFlightNumber(mailbag.getFlightNumber());
						mailbagvo.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
						mailbagvo.setSegmentSerialNumber(mailbag.getSegmentSerialNumber());
						mailbagvo.setMailSubclass(mailbag.getMailSubClass());
						mailbagvo.setMailCategoryCode(mailbag.getMailCategory());
						mailbagvo.setUldNumber(mailbag.getUldNumber());
						mailbagvo.setPaCode(mailbag.getPaCode());
						mailbagvo.setMailClass(mailbag.getMailClass());
						mailbagvo.setScannedDate(ContextUtil.getInstance().getBean(LocalDate.class).getLocalDateTime(mailbag.getScannedDate(), null));
						AirlineValidationVO airlineValidationVO = null;
						if (mailbagvo.getCarrierCode() == null) {
							try {
								airlineValidationVO = ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class).findAirline(mailbagvo.getCompanyCode(),
										mailbag.getCarrierId());
							} catch (SharedProxyException sharedProxyException) {
								sharedProxyException.getMessage();
							}
							mailbagvo.setCarrierCode(airlineValidationVO.getAlphaCode());
						}
						mailbagvo.setCarrierId(mailbag.getCarrierId());
						try {
							mailbagvo = ContextUtil.getInstance().getBean(MailController.class).constructOriginDestinationDetails(mailbagvo);
						} finally {
						}
						String serviceLevel = null;
						if (mailbag.getMailServiceLevel() == null) {
							try {
								serviceLevel = ContextUtil.getInstance().getBean(MailController.class).findMailServiceLevel(mailbagvo);
								mailbag.setMailServiceLevel(serviceLevel);
							} finally {
							}
						}
					}
				}
			}
			updateMailbagFlightForReassignVos(toFlightVO, containerVO, toFlightSegmentSerialNum, mailbagVOs);
			Collection<MailbagVO> mailbagVos = new ArrayList<MailbagVO>();
			Collection<MailbagVO> irregularMailbagVOs = new ArrayList<MailbagVO>();
			if (mailbag != null) {
				if (containerVO.getContainerNumber().equals(mailbag.getUldNumber())) {
					MailbagVO mailbagVO = new MailbagVO();
					mailbagVO.setCompanyCode(mailbag.getCompanyCode());
					mailbagVO.setMailSequenceNumber(mailbag.getMailSequenceNumber());
					mailbagVO.setMailClass(mailbag.getMailClass());
					mailbagVO.setUldNumber(mailbag.getUldNumber());
					mailbagVO.setCarrierId(mailbag.getCarrierId());
					mailbagVO.setFlightNumber(mailbag.getFlightNumber());
					mailbagVO.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
					mailbagVO.setSegmentSerialNumber(mailbag.getSegmentSerialNumber());
					mailbagVO.setScannedDate(ContextUtil.getInstance().getBean(LocalDate.class).getLocalDate(mailbag.getScannedPort(), true));
					if (mailbag.getScannedUser() != null) {
						mailbagVO.setScannedUser(mailbag.getScannedUser());
					} else {
						mailbagVO.setScannedUser(logonAttributes.getUserId());
					}
					mailbagVO.setCarrierCode(toFlightVO.getCarrierCode());
					mailbagVos.add(mailbagVO);
					if (containerVO.isOffload() && mailIdsForMonitorSLAs != null) {
						mailIdsForMonitorSLAs.add(mailbag.getMailIdr());
					}
				}
				if (irregularMailbagVOs.size() > 0) {
					ContainerVO toContainerVO = new ContainerVO();
					toContainerVO.setCarrierId(toFlightVO.getCarrierId());
					toContainerVO.setFlightNumber(toFlightVO.getFlightNumber());
					toContainerVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
					toContainerVO.setFlightDate(toFlightVO.getFlightDate());
				}
			}
		} catch (FinderException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		log.debug("ReassignController" + " : " + "updateDSN" + " Exiting");
	}

	/**
	 * @author A-1739
	 * @param toFlightVO
	 * @param containerVO
	 * @param toFlightSegmentSerialNum
	 * @throws SystemException
	 */
	public void updateMailbagFlightForReassignVos(OperationalFlightVO toFlightVO, ContainerVO containerVO,
												  int toFlightSegmentSerialNum, Collection<MailbagVO> mailbagVOs) {
		HashMap<String, String> routingCache = new HashMap<String, String>();
		boolean isRDTUpdateReq = false;
		StringBuilder routingKey = null;
		String routingDetails = null;
		if (mailbagVOs != null) {
			for (MailbagVO mailbagvo : mailbagVOs) {
				MailbagPK mailbagPK = new MailbagPK();
				Mailbag mailbag = null;
				mailbagPK.setCompanyCode(mailbagvo.getCompanyCode());
				mailbagPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
				try {
					mailbag = Mailbag.findMailbagDetails(mailbagPK);
				} catch (FinderException e) {
					log.error("" + "System Exception Caught" + " " + e);
				}
				if (mailbag != null) {
					if (containerVO.getContainerNumber().equals(mailbag.getUldNumber())) {
						isRDTUpdateReq = true;
						if (mailbag.getConsignmentNumber() != null) {
							mailbagvo.setPaCode(mailbag.getPaCode());
							mailbagvo.setConsignmentNumber(mailbag.getConsignmentNumber());
							mailbagvo.setDestination(mailbag.getDestination());
							routingKey = new StringBuilder();
							routingKey.append(mailbagvo.getPaCode()).append(mailbagvo.getConsignmentNumber())
									.append(mailbagvo.getDestination());
							if (!routingCache.containsKey(routingKey.toString())) {
								routingDetails = Mailbag.findRoutingDetailsForConsignment(mailbagvo);
								if (routingDetails != null) {
									routingCache.put(routingKey.toString(), routingDetails);
									isRDTUpdateReq = false;
								}
							} else {
								isRDTUpdateReq = false;
							}
						}
						mailbag.updateFlightForReassign(toFlightVO, containerVO, toFlightSegmentSerialNum,
								isRDTUpdateReq);
						if (containerVO.isOffload()) {
							mailbagvo.setTriggerForReImport(MailConstantsVO.MAIL_STATUS_OFFLOADED);
							if (MailConstantsVO.FLAG_YES.equals(containerVO.getPaBuiltFlag())) {
								mailbagvo.setAcceptancePostalContainerNumber(null);
								mailbagvo.setPaContainerNumberUpdate(true);
							}
						} else {
							mailbagvo.setTriggerForReImport(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
							if (MailConstantsVO.FLAG_YES.equals(containerVO.getPaBuiltFlag())) {
								mailbagvo.setAcceptancePostalContainerNumber(containerVO.getContainerNumber());
								mailbagvo.setPaContainerNumberUpdate(true);
							}
						}
						mailbag.updatePrimaryAcceptanceDetails(mailbagvo);
					}
				}
			}
		}
	}

	/**
	 * This method returns all the DSNs involved in this Reassign i.e., all the DSNs which were moved when the Containers were moved A-1739
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	private Collection<MailbagVO> constructMailbagVOsFromAirport(
			Collection<MailbagInULDAtAirportVO> mailBagInUldToReassign, String companyCode) {
		log.debug("ReassignController" + " : " + "getDSNVOsAtAirport" + " Entering");
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		for (MailbagInULDAtAirportVO mailbagInULDAtAirportVO : mailBagInUldToReassign) {
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode(companyCode);
			mailbagPK.setMailSequenceNumber(mailbagInULDAtAirportVO.getMailSequenceNumber());
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(mailbagPK);
			} catch (FinderException e) {
				log.error("FinderException Caught");
			}
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setCompanyCode(companyCode);
			mailbagVO.setMailSequenceNumber(mailbagInULDAtAirportVO.getMailSequenceNumber());
			if (mailbag != null) {
				mailbagVO.setMailbagId(mailbag.getMailIdr());
			}
			mailbagVO.setAcceptedBags(mailbagInULDAtAirportVO.getAcceptedBags());
			mailbagVO.setAcceptedWeight(mailbagInULDAtAirportVO.getAcceptedWgt());
			mailbagVO.setUldNumber(mailbagInULDAtAirportVO.getContainerNumber());
			mailbagVOs.add(mailbagVO);
		}
		log.debug("ReassignController" + " : " + "getDSNVOsAtAirport" + " Exiting");
		return mailbagVOs;
	}

	/**
	 * @param toFlightVO
	 * @return
	 * @throws SystemException
	 * @throws FlightClosedException
	 * @throws ContainerAssignmentException
	 * @throws InvalidFlightSegmentException
	 * @throws ULDDefaultsProxyException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	public Collection<ContainerVO> reassignContainers(Collection<ContainerVO> containers,
													  OperationalFlightVO toFlightVO)
			throws FlightClosedException, ContainerAssignmentException, InvalidFlightSegmentException,
			ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException,
			MailDefaultStorageUnitException, MailBookingException {
		log.debug("ReassignController" + " : " + "reassignContainers" + " Entering");
		Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>();
		Collection<ContainerVO> containerVOs = null;
		String pou = toFlightVO.getPou();
		for (ContainerVO cont : containers) {
			toFlightVO.setPou(pou);
			containerVOs = new ArrayList<>(Arrays.asList(cont));
			if (toFlightVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT) {
				if (cont.getPou() != null && cont.getPou().trim().length() > 0) {
					toFlightVO.setPou(cont.getPou());
				}
			}
			Collection<ContainerVO> containersToReassign = new ArrayList<ContainerVO>();
			Collection<ContainerVO> fltAssignedcontainerVOs = new ArrayList<ContainerVO>();
			Collection<ContainerVO> carrierAssignedcontainerVOs = new ArrayList<ContainerVO>();
			Collection<ContainerVO> transferredContainerVOs = null;
			if (toFlightVO.getFlightSequenceNumber() > 0 || toFlightVO.isTransferStatus()) {
				if (!MailConstantsVO.ONLOAD_MESSAGE.equals(cont.getMailSource())) {
					isReassignableContainers(null, toFlightVO, null, null);
				}
				Collection<ContainerVO> containersToTsfr = new ArrayList<ContainerVO>();
				Collection<ContainerVO> containersToRem = new ArrayList<ContainerVO>();
				groupULDsForReassignOrTsfr(containerVOs, containersToTsfr, containersToReassign, containersToRem);
				log.debug("" + "THE CONTAINERS TO REASSIGN " + " " + containersToReassign);
				log.debug("" + "THE CONTAINERS TO TRANSFER " + " " + containersToTsfr);
				log.debug("" + "THE CONTAINERS TO REMOVE " + " " + containersToRem);
				if (containersToTsfr != null && containersToTsfr.size() > 0) {
					log.debug("" + "containers to be removed from inv and tsfr " + " " + containersToRem);
					reassignContainerFromDestToDest(containersToRem, null);
					Map<String, Object> transferMap = new MailTransfer().transferContainers(containersToTsfr,
							toFlightVO, MailConstantsVO.FLAG_NO);
					if (transferMap.get(MailConstantsVO.CONST_CONTAINER) != null) {
						transferredContainerVOs = (Collection<ContainerVO>) transferMap
								.get(MailConstantsVO.CONST_CONTAINER);
					}
				}
			} else {
				containersToReassign = containerVOs;
			}
			int toFlightSegSerialNo = -1;
			boolean isUld = false;
			if (containersToReassign != null && containersToReassign.size() > 0) {
				log.debug("" + "containers to be reassigned " + " " + containersToReassign);
				Collection<ContainerVO> flightAssignedContainers = new ArrayList<ContainerVO>();
				Collection<ContainerVO> destAssignedContainers = new ArrayList<ContainerVO>();
				if (isReassignableContainers(containersToReassign, toFlightVO, destAssignedContainers,
						flightAssignedContainers)) {
					if (toFlightVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT) {
						new MailController().calculateContentID(containersToReassign, toFlightVO);
						for (ContainerVO containerVO : containersToReassign) {
							if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
								isUld = true;
							}
							toFlightSegSerialNo = findFlightSegment(toFlightVO.getCompanyCode(),
									toFlightVO.getCarrierId(), toFlightVO.getFlightNumber(),
									toFlightVO.getFlightSequenceNumber(), toFlightVO.getPol(), toFlightVO.getPou());
						}
						validateAndCreateAssignedFlight(toFlightVO);
						validateCreateAssignedFlightSegment(toFlightVO, toFlightSegSerialNo);
						fltAssignedcontainerVOs = reassignContainerFromFlightToFlight(flightAssignedContainers,
								toFlightVO, toFlightSegSerialNo);
						toFlightVO.setSegSerNum(toFlightSegSerialNo);
						carrierAssignedcontainerVOs = reassignContainerFromDestToFlight(destAssignedContainers,
								toFlightVO, toFlightSegSerialNo);
						String enableMLDSend = findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
						if (MailConstantsVO.FLAG_YES.equals(enableMLDSend)) {
							MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
							mailController.flagMLDForContainerReassign(containersToReassign, toFlightVO);
						}
					} else {
						fltAssignedcontainerVOs = reassignContainerFromFlightToDest(flightAssignedContainers,
								toFlightVO, null);
						carrierAssignedcontainerVOs = reassignContainerFromDestToDest(destAssignedContainers,
								toFlightVO);
						MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
						mailController.flagMLDForContainerReassign(containersToReassign, toFlightVO);
					}
					if (fltAssignedcontainerVOs != null) {
						containersForReturn.addAll(fltAssignedcontainerVOs);
					}
					if (carrierAssignedcontainerVOs != null) {
						containersForReturn.addAll(carrierAssignedcontainerVOs);
					}
					if (MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD))
							&& isUld) {
						updateOperationalULDsInFlight(containersToReassign, toFlightVO);
					}
				}
			}
			if (transferredContainerVOs != null && transferredContainerVOs.size() > 0) {
				containersForReturn.addAll(transferredContainerVOs);
			}
		}
		log.debug("ReassignController" + " : " + "reassignContainers" + " Exiting");
		return containersForReturn;
	}

	/**
	 * @author A-5991
	 * @param containersToReassign
	 * @param toFlightVO
	 * @throws SystemException
	 */
	private void updateOperationalULDsInFlight(Collection<ContainerVO> containersToReassign,
											   OperationalFlightVO toFlightVO) {
		log.debug("ReassignController" + " : " + "updateOperationalULDsInFlight" + " Entering");
		Collection<UldInFlightVO> uldInFlights = constructUldInFlightsForReassign(containersToReassign, toFlightVO);
		if (uldInFlights != null && uldInFlights.size() > 0) {
			operationsFltHandlingProxy.saveOperationalULDsInFlight(uldInFlights);
		}
		log.debug("ReassignController" + " : " + "updateOperationalULDsInFlight" + " Exiting");
	}

	/**
	 * @author A-5991
	 * @param containersToReassign
	 * @param toFlightVO
	 * @return
	 */
	private Collection<UldInFlightVO> constructUldInFlightsForReassign(Collection<ContainerVO> containersToReassign,
																	   OperationalFlightVO toFlightVO) {
		log.debug("ReassignController" + " : " + "constructUldInFlightsForReassign" + " Entering");
		Collection<UldInFlightVO> uldInFlights = new ArrayList<UldInFlightVO>();
		for (ContainerVO containerVO : containersToReassign) {
			if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
				UldInFlightVO uldInFlightVO = new UldInFlightVO();
				uldInFlightVO.setCompanyCode(containerVO.getCompanyCode());
				uldInFlightVO.setUldNumber(containerVO.getContainerNumber());
				uldInFlightVO.setAirportCode(containerVO.getAssignedPort());
				uldInFlightVO.setCarrierId(toFlightVO.getCarrierId());
				if (toFlightVO.getFlightSequenceNumber() > 0) {
					uldInFlightVO.setFlightNumber(toFlightVO.getFlightNumber());
					uldInFlightVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
					uldInFlightVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
				}
				uldInFlightVO.setPou(toFlightVO.getPou());
				uldInFlightVO.setPol(toFlightVO.getPol());
				uldInFlightVO.setFlightDirection(MailConstantsVO.OPERATION_OUTBOUND);
				uldInFlights.add(uldInFlightVO);
			}
		}
		return uldInFlights;
	}

	private ULDForSegmentVO constructULDForSegmentFromAirport(ULDAtAirportVO uldAtAirportVO) {
		log.debug("ReassignController" + " : " + "getULDForSegmentFromAirport" + " Entering");
		ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
		uldForSegmentVO.setCompanyCode(uldAtAirportVO.getCompanyCode());
		uldForSegmentVO.setUldNumber(uldAtAirportVO.getUldNumber());
		uldForSegmentVO.setNoOfBags(uldAtAirportVO.getNumberOfBags());
		uldForSegmentVO.setTotalWeight(uldAtAirportVO.getTotalWeight());
		uldForSegmentVO.setWarehouseCode(uldAtAirportVO.getWarehouseCode());
		uldForSegmentVO.setLocationCode(uldAtAirportVO.getLocationCode());
		uldForSegmentVO.setTransferFromCarrier(uldAtAirportVO.getTransferFromCarrier());
		Collection<MailbagInULDAtAirportVO> dsnsInULDAtAirport = uldAtAirportVO.getMailbagInULDAtAirportVOs();
		if (dsnsInULDAtAirport != null && dsnsInULDAtAirport.size() > 0) {
			Collection<MailbagInULDForSegmentVO> mailbagInULDForSegs = constructDSNsInULDForSegFromArp(
					dsnsInULDAtAirport, uldAtAirportVO.getAirportCode());
			uldForSegmentVO.setMailbagInULDForSegmentVOs(mailbagInULDForSegs);
		}
		Collection<MailbagInULDAtAirportVO> mailbagInULDAtAirportVOs = uldAtAirportVO.getMailbagInULDAtAirportVOs();
		if (mailbagInULDAtAirportVOs != null && mailbagInULDAtAirportVOs.size() > 0) {
			Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs = MailOperationsVOConverter
					.convertToMailbagInULDForSegmentVOs(mailbagInULDAtAirportVOs, uldAtAirportVO.getAirportCode());
			uldForSegmentVO.setMailbagInULDForSegmentVOs(mailbagInULDForSegmentVOs);
		}
		log.debug("ReassignController" + " : " + "getULDForSegmentFromAirport" + " Exiting");
		return uldForSegmentVO;
	}

	/**
	 * This method reassigns from destination to flight A-1739
	 * @param destAssignedContainers
	 * @param toFlightVO
	 * @param toFlightSegSerialNo
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	Collection<ContainerVO> reassignContainerFromDestToFlight(Collection<ContainerVO> destAssignedContainers,
															  OperationalFlightVO toFlightVO, int toFlightSegSerialNo)
			throws ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException {
		log.debug("ReassignController" + " : " + "reassignFromDestToFlight" + " Entering");
		Collection<ContainerVO> paBuiltContainers = new ArrayList<ContainerVO>();
		Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>();
		ULDInFlightVO uldInFlightVO = null;
		Collection<ULDInFlightVO> uldInFlightVOs = null;
		FlightDetailsVO flightDetailsVO = null;
		boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
		int numberOfBarrowspresent = 0;
		boolean isULDType = false;
		for (ContainerVO destAssignedContainerVO : destAssignedContainers) {
			boolean isNotAccepted = true;
			boolean isReassignSuccess = false;
			Collection<MailbagVO> mailbagToFlag = new ArrayList<MailbagVO>();
			isULDType = false;
			if (destAssignedContainerVO.getAcceptanceFlag().equals(CONST_YES)) {
				isNotAccepted = false;
				String fromULDNumber = destAssignedContainerVO.getContainerNumber();
				if (MailConstantsVO.BULK_TYPE.equals(destAssignedContainerVO.getType())) {
					fromULDNumber = constructBulkULDNumber(destAssignedContainerVO.getFinalDestination(),
							destAssignedContainerVO.getCarrierCode());
				}
				ULDAtAirport uldAtAirport = null;
				try {
					uldAtAirport = ULDAtAirport
							.find(constructULDArpPKFromContainer(destAssignedContainerVO, fromULDNumber));
				} catch (FinderException exception) {
					log.error("uldArp not found");
					throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
				}
				AssignedFlightSegment toAssignedFlightSegment = null;
				try {
					toAssignedFlightSegment = findAssignedFlightSegment(toFlightVO.getCompanyCode(),
							toFlightVO.getCarrierId(), toFlightVO.getFlightNumber(),
							toFlightVO.getFlightSequenceNumber(), toFlightSegSerialNo);
				} catch (FinderException ex) {
					throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
				}
				ULDForSegmentVO uldForSegmentVO = null;
				Collection<MailbagInULDForSegmentVO> mailbagsToReassign = null;
				if (destAssignedContainerVO.isUldTobarrow()) {
					destAssignedContainerVO.setType(MailConstantsVO.BULK_TYPE);
				}
				if (destAssignedContainerVO.isBarrowToUld()) {
					destAssignedContainerVO.setType(MailConstantsVO.ULD_TYPE);
				}
				String containerNumber = destAssignedContainerVO.getContainerNumber();
				if (MailConstantsVO.ULD_TYPE.equals(destAssignedContainerVO.getType())) {
					ULDAtAirportVO uldAtAirportVO = uldAtAirport.retrieveVO();
					if (destAssignedContainerVO.isBarrowToUld()) {
						uldAtAirportVO.setUldNumber(containerNumber);
					}
					log.debug("" + "uldairport retrieved for uld --> " + " " + uldAtAirportVO);
					double actualWgtCal = 0.0;
					if (uldAtAirportVO.getMailbagInULDAtAirportVOs() != null
							&& uldAtAirportVO.getMailbagInULDAtAirportVOs().size() > 0) {
						Collection<MailbagInULDAtAirportVO> mailbagInULDAtAirportVOs = new ArrayList<MailbagInULDAtAirportVO>();
						for (MailbagInULDAtAirportVO mailbagInULDAtAirportVO : uldAtAirportVO
								.getMailbagInULDAtAirportVOs()) {
							if (uldAtAirport != null && (uldAtAirport.getUldNumber()
									.equals(mailbagInULDAtAirportVO.getContainerNumber())
									|| containerNumber.equals(mailbagInULDAtAirportVO.getContainerNumber()))) {
								if (mailbagInULDAtAirportVO.getWeight() != null) {
									actualWgtCal += mailbagInULDAtAirportVO.getWeight().getValue().doubleValue();
								}
								mailbagInULDAtAirportVOs.add(mailbagInULDAtAirportVO);
							}
						}
						uldAtAirportVO.setMailbagInULDAtAirportVOs(mailbagInULDAtAirportVOs);
					}
					uldAtAirport.remove();
					uldForSegmentVO = constructULDForSegmentFromAirport(uldAtAirportVO);
					if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null
							&& !uldForSegmentVO.getMailbagInULDForSegmentVOs().isEmpty()) {
						uldForSegmentVO.getMailbagInULDForSegmentVOs()
								.forEach(mailbagInULDForSegmentVO -> mailbagInULDForSegmentVO
										.setGhttim(destAssignedContainerVO.getGHTtime()));
					}
					updateULDForSegmentVO(uldForSegmentVO, toFlightVO, destAssignedContainerVO, toFlightSegSerialNo);
					actualWgtCal = calculateActualWeightForULD(uldForSegmentVO, destAssignedContainerVO, fromULDNumber);
					destAssignedContainerVO.setActualWeight(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(actualWgtCal)));
					try {
						if (destAssignedContainerVO.isUldTobarrow()) {
							String bulkNumber = constructBulkULDNumber(toFlightVO.getPou(),
									toFlightVO.getCarrierCode());
							uldForSegmentVO.setUldNumber(bulkNumber);
						}
						ULDForSegment toULDForSegment = AssignedFlightSegment.findULDForSegment(
								constructULDForSegmentPK(toFlightVO.getCompanyCode(), uldForSegmentVO.getUldNumber(),
										toFlightVO.getCarrierId(), toFlightVO.getFlightNumber(),
										toFlightVO.getFlightSequenceNumber(), toFlightSegSerialNo));
						if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null) {
							toULDForSegment.populateMailbagDetails(uldForSegmentVO.getMailbagInULDForSegmentVOs());
						}
					} catch (FinderException e) {
						if (destAssignedContainerVO.isUldTobarrow()) {
							String bulkNumber = constructBulkULDNumber(toFlightVO.getPou(),
									toFlightVO.getCarrierCode());
							uldForSegmentVO.setUldNumber(bulkNumber);
						}
						toAssignedFlightSegment.createULDForSegment(uldForSegmentVO);
					}
					if (isUldIntegrationEnbled) {
						flightDetailsVO = new FlightDetailsVO();
						flightDetailsVO.setCompanyCode(toFlightVO.getCompanyCode());
						flightDetailsVO.setFlightCarrierIdentifier(toFlightVO.getCarrierId());
						flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(toFlightVO.getFlightDate()));
						flightDetailsVO.setFlightNumber(toFlightVO.getFlightNumber());
						flightDetailsVO.setFlightSequenceNumber(toFlightSegSerialNo);
						flightDetailsVO.setCarrierCode(toFlightVO.getCarrierCode());
						flightDetailsVO.setDirection(MailConstantsVO.EXPORT);
						uldInFlightVOs = new ArrayList<ULDInFlightVO>();
						flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
					}
					uldForSegmentVO.setPou(toFlightVO.getPou());
					uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
					uldForSegmentVO.setFlightNumber(toFlightVO.getFlightNumber());
					uldForSegmentVO.setFlightDate(toFlightVO.getFlightDate());
					uldForSegmentVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
					uldForSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
					updateBookingForFlight(uldForSegmentVO, toFlightVO, "UPDATE_BOOKING_TO_FLIGHT");
					isReassignSuccess = true;
				} else if (MailConstantsVO.BULK_TYPE.equals(destAssignedContainerVO.getType())) {
					String toULDNumber = constructBulkULDNumber(toFlightVO.getPou(), toFlightVO.getCarrierCode());
					if (destAssignedContainerVO.isBarrowToUld()) {
						toULDNumber = destAssignedContainerVO.getContainerNumber();
					}
					mailbagsToReassign = constructDSNsInULDForSegFromArp(
							uldAtAirport.reassignBulkContainer(containerNumber), toFlightVO.getPol());
					mailbagsToReassign = MailOperationsVOConverter.convertToMailbagInULDForSegmentVOs(
							uldAtAirport.reassignBulkContainerFormail(containerNumber), toFlightVO.getPol());
					double actualWgtCal = 0.0;
					actualWgtCal = calculateActualWeightForBulk(mailbagsToReassign, destAssignedContainerVO);
					destAssignedContainerVO.setActualWeight(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(actualWgtCal)));
					if (destAssignedContainerVO.isUldTobarrow()) {
						uldAtAirport.remove();
					}
					if (mailbagsToReassign != null && !mailbagsToReassign.isEmpty()) {
						mailbagsToReassign.forEach(
								mailbagToReassign -> mailbagToReassign.setGhttim(destAssignedContainerVO.getGHTtime()));
					}
					try {
						ULDForSegment toULDForSegment = AssignedFlightSegment
								.findULDForSegment(constructULDForSegmentPK(toFlightVO.getCompanyCode(), toULDNumber,
										toFlightVO.getCarrierId(), toFlightVO.getFlightNumber(),
										toFlightVO.getFlightSequenceNumber(), toFlightSegSerialNo));
						toAssignedFlightSegment.assignBulkContainer(toULDForSegment, mailbagsToReassign);
						uldForSegmentVO = toULDForSegment.retrieveVO();
						uldForSegmentVO.setPou(toFlightVO.getPou());
						uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
						uldForSegmentVO.setFlightNumber(toFlightVO.getFlightNumber());
						uldForSegmentVO.setFlightDate(toFlightVO.getFlightDate());
						uldForSegmentVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
						uldForSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
						updateBookingForFlight(uldForSegmentVO, toFlightVO, "UPDATE_BOOKING_TO_FLIGHT");
						isReassignSuccess = true;
					} catch (FinderException finderException) {
						uldForSegmentVO = new ULDForSegmentVO();
						uldForSegmentVO.setUldNumber(toULDNumber);
						uldForSegmentVO.setMailbagInULDForSegmentVOs(mailbagsToReassign);
						updateULDForSegmentVO(uldForSegmentVO, toFlightVO, destAssignedContainerVO,
								toFlightSegSerialNo);
						log.debug("" + "uldforsegment retrieved for bulk --> " + " " + uldForSegmentVO);
						toAssignedFlightSegment.createULDForSegment(uldForSegmentVO);
						uldForSegmentVO.setPou(toFlightVO.getPou());
						uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
						uldForSegmentVO.setFlightNumber(toFlightVO.getFlightNumber());
						uldForSegmentVO.setFlightDate(toFlightVO.getFlightDate());
						uldForSegmentVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
						uldForSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
						updateBookingForFlight(uldForSegmentVO, toFlightVO, "UPDATE_BOOKING_TO_FLIGHT");
						isReassignSuccess = true;
					}
				}
				if (isReassignSuccess) {
					if (mailbagsToReassign == null) {
						mailbagsToReassign = uldForSegmentVO.getMailbagInULDForSegmentVOs();
					}
					if (mailbagsToReassign == null) {
						mailbagsToReassign = uldForSegmentVO.getMailbagInULDForSegmentVOs();
					}
					if (mailbagsToReassign != null && !mailbagsToReassign.isEmpty()) {
						Collection<MailbagVO> mailbagVOs = constructMailBagVOFromSeg(mailbagsToReassign,
								toFlightVO.getCompanyCode());
						updateDSNForConReassign(mailbagVOs, toFlightVO, destAssignedContainerVO, toFlightSegSerialNo,
								null);
						updateTrfCarrierForResdits(mailbagsToReassign, mailbagVOs);
						mailbagToFlag.addAll(mailbagVOs);
					}
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.flagHistoryForContainerReassignment(toFlightVO, destAssignedContainerVO,
							mailbagToFlag, mailController.getTriggerPoint());
					mailController.flagAuditForContainerReassignment(toFlightVO, destAssignedContainerVO,
							mailbagToFlag);
					boolean provisionalRateImport = false;
					Collection<MailbagVO> mailbagsToImport = new MailController()
							.validateAndReImportMailbagsToMRA(mailbagToFlag);
					Collection<RateAuditVO> rateAuditVOs = mailController.createRateAuditVOs(toFlightVO,
							destAssignedContainerVO, mailbagsToImport, MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,
							provisionalRateImport);
					log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
					if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
						String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
						if (importEnabled != null && importEnabled.contains("M")) {
							try {
								mailOperationsMRAProxy.importMRAData(rateAuditVOs);
							} catch (BusinessException e) {
								throw new SystemException(e.getMessage(), e.getMessage(), e);
							}
						}
					}
					String provisionalRateimportEnabled = findSystemParameterValue(
							MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
					if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
						provisionalRateImport = true;
						Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toFlightVO,
								destAssignedContainerVO, mailbagsToImport, MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,
								provisionalRateImport);
						if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
							try {
								mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
							} catch (BusinessException e) {
								throw new SystemException(e.getMessage(), e.getMessage(), e);
							}
						}
					}
				}
			}
			if (isReassignSuccess || isNotAccepted) {
				updateReassignedContainer(destAssignedContainerVO, toFlightVO, toFlightSegSerialNo);
				if (isReassignSuccess) {
					if (MailConstantsVO.FLAG_YES.equals(destAssignedContainerVO.getPaBuiltFlag())) {
						paBuiltContainers.add(destAssignedContainerVO);
					}
				}
			}
			if (MailConstantsVO.ULD_TYPE.equals(destAssignedContainerVO.getType())) {
				ULDValidationVO uldValidationVO = null;
				try {
					uldValidationVO = sharedULDProxy.validateULD(destAssignedContainerVO.getCompanyCode(),
							destAssignedContainerVO.getContainerNumber());
					if (uldValidationVO != null) {
						isULDType = true;
					} else {
						isULDType = false;
					}
				} catch (SharedProxyException e) {
					isULDType = false;
				}
			}
			if (isUldIntegrationEnbled && isULDType) {
				uldInFlightVO = new ULDInFlightVO();
				uldInFlightVO.setUldNumber(destAssignedContainerVO.getContainerNumber());
				uldInFlightVO.setPointOfLading(destAssignedContainerVO.getAssignedPort());
				uldInFlightVO.setPointOfUnLading(destAssignedContainerVO.getFinalDestination());
				uldInFlightVO.setRemark(destAssignedContainerVO.getRemarks());
				uldInFlightVOs.add(uldInFlightVO);
			}
			ContainerVO containerReturnVO = new ContainerVO();
			containerReturnVO = mailOperationsMapper.copyContainerVO(destAssignedContainerVO);
			containerReturnVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
			containerReturnVO.setFlightNumber(toFlightVO.getFlightNumber());
			containerReturnVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
			containerReturnVO.setSegmentSerialNumber(toFlightSegSerialNo);
			containerReturnVO.setCarrierCode(toFlightVO.getCarrierCode());
			containerReturnVO.setFlightDate(toFlightVO.getFlightDate());
			containersForReturn.add(containerReturnVO);
			if (MailConstantsVO.ULD_TYPE.equals(destAssignedContainerVO.getType())) {
				if (isUldIntegrationEnbled && uldInFlightVOs.size() > 0) {
					flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
					flightDetailsVO.setRemark(destAssignedContainerVO.getRemarks());
					flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);
					uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
				}
			}
			if (paBuiltContainers.size() > 0 || mailbagToFlag.size() > 0) {
				flagResditsForContainerReassign(mailbagToFlag, paBuiltContainers, toFlightVO, toFlightSegSerialNo);
			}
		}
		log.debug("ReassignController" + " : " + "reassignFromDestToFlight" + " Exiting");
		return containersForReturn;
	}

	/**
	 * This method checks if an assignedFlightSegment exists and creates if if not exists A-1739
	 * @param toFlightVO
	 * @param toFlightSegSerialNo
	 * @throws SystemException
	 */
	private void validateCreateAssignedFlightSegment(OperationalFlightVO toFlightVO, int toFlightSegSerialNo) {
		AssignedFlightSegmentPK segmentPK = new AssignedFlightSegmentPK();
		segmentPK.setCompanyCode(toFlightVO.getCompanyCode());
		segmentPK.setCarrierId(toFlightVO.getCarrierId());
		segmentPK.setFlightNumber(toFlightVO.getFlightNumber());
		segmentPK.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
		segmentPK.setSegmentSerialNumber(toFlightSegSerialNo);
		try {
			AssignedFlightSegment.find(segmentPK);
		} catch (FinderException exception) {
			AssignedFlightSegmentVO assignedFlightSegmentVO = new AssignedFlightSegmentVO();
			assignedFlightSegmentVO.setCarrierId(toFlightVO.getCarrierId());
			assignedFlightSegmentVO.setCompanyCode(toFlightVO.getCompanyCode());
			assignedFlightSegmentVO.setFlightNumber(toFlightVO.getFlightNumber());
			assignedFlightSegmentVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
			assignedFlightSegmentVO.setPol(toFlightVO.getPol());
			assignedFlightSegmentVO.setPou(toFlightVO.getPou());
			assignedFlightSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
			new AssignedFlightSegment(assignedFlightSegmentVO);
		}
	}

	private Collection<MailbagInULDForSegmentVO> constructDSNsInULDForSegFromArp(
			Collection<MailbagInULDAtAirportVO> mailsInAirportToReassign, String airportCode) {
		log.debug("ReassignController" + " : " + "getDSNInULDForSegmentsFromAirport" + " Entering");
		Collection<MailbagInULDForSegmentVO> mailsInULDForSegments = new ArrayList<MailbagInULDForSegmentVO>();
		for (MailbagInULDAtAirportVO mailInULDAtAirport : mailsInAirportToReassign) {
			MailbagInULDForSegmentVO mailInULDForSegmentVO = new MailbagInULDForSegmentVO();
			mailInULDForSegmentVO.setMailSubclass(mailInULDAtAirport.getMailSubclass());
			mailInULDForSegmentVO.setMailCategoryCode(mailInULDAtAirport.getMailCategoryCode());
			mailInULDForSegmentVO.setMailClass(mailInULDAtAirport.getMailClass());
			mailInULDForSegmentVO.setMailId(mailInULDAtAirport.getMailId());
			mailInULDForSegmentVO.setMailSequenceNumber(mailInULDAtAirport.getMailSequenceNumber());
			mailsInULDForSegments.add(mailInULDForSegmentVO);
		}
		log.debug("ReassignController" + " : " + "getDSNInULDForSegmentsFromAirport" + " Exiting");
		return mailsInULDForSegments;
	}

	/**
	 * A-1739
	 * @param toDestinationVO
	 * @param toULDNumber
	 * @return
	 */
	private ULDAtAirportPK constructULDArpPKFromOpFlt(OperationalFlightVO toDestinationVO, String toULDNumber) {
		ULDAtAirportPK uldArpPK = new ULDAtAirportPK();
		uldArpPK.setCompanyCode(toDestinationVO.getCompanyCode());
		uldArpPK.setCarrierId(toDestinationVO.getCarrierId());
		uldArpPK.setAirportCode(toDestinationVO.getPol());
		uldArpPK.setUldNumber(toULDNumber);
		return uldArpPK;
	}

	/**
	 * This method updates the CONMST with the new flight of this container A-1739
	 * @param assignedContainerVO
	 * @param toFlightVO
	 * @param toFlightSegSerialNumber
	 * @throws SystemException
	 */
	private void updateReassignedContainer(ContainerVO assignedContainerVO, OperationalFlightVO toFlightVO,
										   int toFlightSegSerialNumber) {
		log.debug("ReassignController" + " : " + "updateReassignedContainer" + " Entering");
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(assignedContainerVO.getCompanyCode());
		containerPk.setContainerNumber(assignedContainerVO.getContainerNumber());
		containerPk.setCarrierId(assignedContainerVO.getCarrierId());
		containerPk.setFlightNumber(assignedContainerVO.getFlightNumber());
		containerPk.setFlightSequenceNumber(assignedContainerVO.getFlightSequenceNumber());
		containerPk.setAssignmentPort(assignedContainerVO.getAssignedPort());
		containerPk.setLegSerialNumber(assignedContainerVO.getLegSerialNumber());
		Container prevFlightContainer = null;
		try {
			prevFlightContainer = Container.find(containerPk);
		} catch (FinderException finderException) {
			return;
		}
		ContainerVO containerVO = prevFlightContainer.retrieveVO();

		collectContainerAuditDetails(prevFlightContainer,assignedContainerVO);
		containerVO.setTransactionCode(assignedContainerVO.getTransactionCode());
		if (assignedContainerVO.isContainerDestChanged()) {
			containerVO.setTransactionCode(MailConstantsVO.MAIL_STATUS_ASSIGNED);
		}
		if (assignedContainerVO.getAssignedUser() != null) {
			containerVO.setAssignedUser(assignedContainerVO.getAssignedUser());
		} else if (assignedContainerVO.getLastUpdateUser() != null) {
			containerVO.setAssignedUser(assignedContainerVO.getLastUpdateUser());
		}
		containerVO.setLastUpdateUser(assignedContainerVO.getLastUpdateUser());
		if (toFlightVO != null) {
			if (toFlightVO.getFlightSequenceNumber() > 0) {
				if (toFlightVO.getPou() == null) {
					containerVO.setPou(assignedContainerVO.getPou());
				} else {
					containerVO.setPou(toFlightVO.getPou());
				}
			} else {
				containerVO.setPou(null);
			}
		}
		prevFlightContainer.remove();
		if (toFlightVO != null) {
			containerVO.setReassignFlag(true);
			containerVO.setCarrierId(toFlightVO.getCarrierId());
			containerVO.setFlightNumber(toFlightVO.getFlightNumber());
			containerVO.setCarrierCode(toFlightVO.getCarrierCode());
			containerVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
			containerVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
			containerVO.setSegmentSerialNumber(toFlightSegSerialNumber);
			containerVO.setCarrierCode(toFlightVO.getCarrierCode());
			containerVO.setPol(toFlightVO.getPol());
			containerVO.setTransitFlag(MailConstantsVO.FLAG_YES);
			containerVO.setRemarks(assignedContainerVO.getRemarks());
			containerVO.setOnwardRoutings(assignedContainerVO.getOnwardRoutings());
			if (assignedContainerVO.getActualWeight() != null) {
				containerVO.setActualWeight(assignedContainerVO.getActualWeight());
			}
			if (assignedContainerVO.getContentId() != null) {
				containerVO.setContentId(assignedContainerVO.getContentId());
			}
			containerVO.setAssignedDate(ContextUtil.getInstance().getBean(LocalDate.class).getLocalDate(containerVO.getAssignedPort(), true));
			if (assignedContainerVO.isUldTobarrow()) {
				containerVO.setType("B");
			}
			if (assignedContainerVO.isBarrowToUld()) {
				containerVO.setType("U");
				Quantity containerTareWeight = new MailController().getUldTareWeight(
						assignedContainerVO.getCompanyCode(), assignedContainerVO.getContainerNumber());
				assignedContainerVO.setContainerWeight(containerTareWeight);
			}
			if (assignedContainerVO.isOffload()) {
				containerVO.setOffloadFlag(MailConstantsVO.FLAG_YES);
				containerVO.setTransitFlag(MailConstantsVO.FLAG_YES);
				containerVO.setOffload(true);
			} else {
				containerVO.setOffloadFlag(MailConstantsVO.FLAG_NO);
				containerVO.setOffload(false);
				if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType()) && containerVO.getPou() != null) {
					containerVO.setFinalDestination(containerVO.getPou());
				} else {
					containerVO.setFinalDestination(assignedContainerVO.getFinalDestination());
				}
			}
			containerVO.setFromFltNum(assignedContainerVO.getFlightNumber());
			containerVO.setPrevFlightPou(assignedContainerVO.getPou());
			containerVO.setTransferAudit(assignedContainerVO.isTransferAudit());
			if (containerVO.getUldFulIndFlag() == null) {
				containerVO.setUldFulIndFlag(assignedContainerVO.getUldFulIndFlag());
			}
			if (containerVO.getUldReferenceNo() == 0) {
				containerVO.setUldReferenceNo(assignedContainerVO.getUldReferenceNo());
			}
			log.debug("" + "container mst " + " " + containerVO);
			createContainer(containerVO);
		}
		log.debug("ReassignController" + " : " + "updateReassignedContainer" + " Exiting");
	}

	/**
	 * @author A-5991
	 * @param paBuiltContainers
	 * @param toFlightVO
	 * @param segmentSerialNumber
	 * @return
	 * @throws SystemException
	 */
	private Collection<ContainerDetailsVO> updateFlightOfReassignedContainers(Collection<ContainerVO> paBuiltContainers,
																			  OperationalFlightVO toFlightVO, int segmentSerialNumber) {
		Collection<ContainerDetailsVO> flightUpdatedULDs = new ArrayList<ContainerDetailsVO>();
		for (ContainerVO paContainer : paBuiltContainers) {
			ContainerDetailsVO containerDetailsVO = MailOperationsVOConverter
					.convertToContainerDetails(paContainer);
			containerDetailsVO.setContainerJnyId(paContainer.getContainerJnyID());
			containerDetailsVO.setPaCode(paContainer.getShipperBuiltCode());
			containerDetailsVO.setCarrierId(toFlightVO.getCarrierId());
			containerDetailsVO.setCarrierCode(toFlightVO.getCarrierCode());
			containerDetailsVO.setFlightNumber(toFlightVO.getFlightNumber());
			containerDetailsVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
			containerDetailsVO.setFlightDate(toFlightVO.getFlightDate());
			containerDetailsVO.setSegmentSerialNumber(segmentSerialNumber);
			containerDetailsVO.setPol(toFlightVO.getPol());
			flightUpdatedULDs.add(containerDetailsVO);
		}
		return flightUpdatedULDs;
	}

	/**
	 * find the segment serial number of the segment to which this container is assigned A-1739
	 * @param companyCode
	 * @param carrierId
	 * @param flightNumber
	 * @param flightSequenceNumber
	 * @param pol
	 * @param pou
	 * @return the segmentserialnumber for the pol-pou of the flight
	 * @throws SystemException
	 */
	private int findFlightSegment(String companyCode, int carrierId, String flightNumber, long flightSequenceNumber,
								  String pol, String pou) throws InvalidFlightSegmentException {
		log.debug("ReassignController" + " : " + "findFlightSegment" + " Entering");
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		flightSegments = flightOperationsProxy.findFlightSegments(companyCode, carrierId, flightNumber,
				flightSequenceNumber);
		String containerSegment = new StringBuilder().append(pol).append(pou).toString();
		String flightSegment = null;
		int segmentSerNum = 0;
		for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
			flightSegment = new StringBuilder().append(segmentSummaryVO.getSegmentOrigin())
					.append(segmentSummaryVO.getSegmentDestination()).toString();
			log.debug("" + "from proxy -- >" + " " + flightSegment);
			log.debug("" + "from container  -- >" + " " + containerSegment);
			if (flightSegment.equals(containerSegment)) {
				segmentSerNum = segmentSummaryVO.getSegmentSerialNumber();
			}
		}
		if (segmentSerNum == 0) {
			throw new InvalidFlightSegmentException(new String[] { containerSegment });
		}
		log.debug("ReassignController" + " : " + "findFlightSegment" + " Exiting");
		return segmentSerNum;
	}

	/**
	 * @author a-1936 This method is used to validateflight for closed statusand if not exists create
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws ContainerAssignmentException
	 */
	private void validateAndCreateAssignedFlight(OperationalFlightVO operationalFlightVO)
			throws ContainerAssignmentException {
		AssignedFlightPK assignedFlightPk = constructAssignedFlightPK(operationalFlightVO);
		try {
			AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.info("FINDER EXCEPTION IS THROWN");
			createAssignedFlight(operationalFlightVO);
		}
	}

	/**
	 * @author a-1936 This method is used to create the assignedFlight
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	private void createAssignedFlight(OperationalFlightVO operationalFlightVO) {
		AssignedFlightVO assignedFlightVO = new AssignedFlightVO();
		assignedFlightVO.setAirportCode(operationalFlightVO.getPol());
		assignedFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		assignedFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
		assignedFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		assignedFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
		assignedFlightVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		AssignedFlight assignedFlight = new AssignedFlight(assignedFlightVO);
//TODO: Neo to correct audit code
//		AssignedFlightAuditVO assignedFlightAuditVO = new AssignedFlightAuditVO(AssignedFlightVO.MODULE,
//				AssignedFlightVO.SUBMODULE, AssignedFlightVO.ENTITY);
//		assignedFlightAuditVO.setAdditionalInformation("Flight Created");
//		performAssignedFlightAudit(assignedFlightAuditVO, assignedFlight, MailConstantsVO.AUDIT_FLT_CREAT);
	}

	/**
	 * @author A-1936
	 * @param assignedFlightAuditVO
	 * @param assignedFlight
	 * @param actionCode
	 * @throws SystemException
	 */
	//TODO: Neo to re-write audit
//	private void performAssignedFlightAudit(AssignedFlightAuditVO assignedFlightAuditVO, AssignedFlight assignedFlight,
//			String actionCode) {
//		assignedFlightAuditVO.setCompanyCode(assignedFlight.getCompanyCode());
//		assignedFlightAuditVO.setAirportCode(assignedFlight.getAirportCode());
//		assignedFlightAuditVO.setFlightNumber(assignedFlight.getFlightNumber());
//		assignedFlightAuditVO.setFlightSequenceNumber(assignedFlight.getFlightSequenceNumber());
//		assignedFlightAuditVO.setCarrierId(assignedFlight.getCarrierId());
//		assignedFlightAuditVO.setLegSerialNumber(assignedFlight.getLegSerialNumber());
//		assignedFlightAuditVO.setActionCode(actionCode);
//		AuditUtils.performAudit(assignedFlightAuditVO);
//		log.debug("DSN" + " : " + "performAssignedFlightAudit" + " Exiting");
//	}

	/**
	 * @author a-1936 This method is used to create a new Container in theSystem
	 * @param containerVo
	 * @throws SystemException
	 * @throws FinderException
	 */
	private Container createContainer(ContainerVO containerVo) {
		String triggeringPoint = contextUtil.getTxContext(ContextUtil.TRIGGER_POINT);
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(containerVo.getCompanyCode());
		containerPk.setContainerNumber(containerVo.getContainerNumber());
		containerPk.setAssignmentPort(containerVo.getAssignedPort());
		containerPk.setFlightNumber(containerVo.getFlightNumber());
		containerPk.setCarrierId(containerVo.getCarrierId());
		containerPk.setFlightSequenceNumber(containerVo.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(containerVo.getLegSerialNumber());
		Container container = null;
		try {
			container = Container.find(containerPk);
			if (MailConstantsVO.FLAG_YES.equals(container.getArrivedStatus())) {
				containerVo.setArrivedStatus(MailConstantsVO.FLAG_YES);
			}
			if (!container.getContainerType().equals(containerVo.getType())) {
				containerVo.setType(container.getContainerType());
			}
			containerDetailsAudit(container,containerVo,containerPk,triggeringPoint);
			container.update(containerVo);
		} catch (FinderException ex) {
			containerDetailsAudit(container,containerVo,containerPk,triggeringPoint);
			container = new Container(containerVo);
			MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
			if (!"B".equals(containerVo.getType())) {
				mailController.flagMLDForMailOperationsInULD(containerVo, MailConstantsVO.MLD_STG);
			}
		}

	return container;
	}

	/**
	 * @author A-5991
	 * @param companyCode
	 * @param uldNumber
	 * @param carrierId
	 * @param flightNumber
	 * @param flightSequenceNumber
	 * @param segmentSerialNumber
	 * @return
	 */
	private ULDForSegmentPK constructULDForSegmentPK(String companyCode, String uldNumber, int carrierId,
													 String flightNumber, long flightSequenceNumber, int segmentSerialNumber) {
		ULDForSegmentPK uldForSegmentPK = new ULDForSegmentPK();
		uldForSegmentPK.setCompanyCode(companyCode);
		uldForSegmentPK.setUldNumber(uldNumber);
		uldForSegmentPK.setCarrierId(carrierId);
		uldForSegmentPK.setFlightNumber(flightNumber);
		uldForSegmentPK.setFlightSequenceNumber(flightSequenceNumber);
		uldForSegmentPK.setSegmentSerialNumber(segmentSerialNumber);
		return uldForSegmentPK;
	}

	/**
	 * This method assignes a group of containers from a flight to a flight A-1739
	 * @param flightAssignedContainers
	 * @param toFlightVO
	 * @param toFlightSegSerialNo
	 * @throws SystemException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	Collection<ContainerVO> reassignContainerFromFlightToFlight(Collection<ContainerVO> flightAssignedContainers,
																OperationalFlightVO toFlightVO, int toFlightSegSerialNo)
			throws CapacityBookingProxyException, MailBookingException {
		log.debug("ReassignController" + " : " + "reassignFromFlightToFlight" + " Entering");
		Collection<ContainerVO> paBuiltContainers = new ArrayList<ContainerVO>();
		Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>();
		for (ContainerVO flightAssignedContainerVO : flightAssignedContainers) {
			boolean isNotAccepted = true;
			boolean isReassignSuccess = false;
			Collection<MailbagVO> mailbagForResdit = new ArrayList<MailbagVO>();
			int numberOfBarrowspresent = 0;
			if (CONST_YES.equals(flightAssignedContainerVO.getAcceptanceFlag())) {
				isNotAccepted = false;
				String fromULDNumber = flightAssignedContainerVO.getContainerNumber();
				AssignedFlightSegment fromAssignedFlightSegment = null;
				try {
					fromAssignedFlightSegment = findAssignedFlightSegment(flightAssignedContainerVO.getCompanyCode(),
							flightAssignedContainerVO.getCarrierId(), flightAssignedContainerVO.getFlightNumber(),
							flightAssignedContainerVO.getFlightSequenceNumber(),
							flightAssignedContainerVO.getSegmentSerialNumber());
				} catch (FinderException ex) {
					throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
				}
				if (MailConstantsVO.BULK_TYPE.equals(flightAssignedContainerVO.getType())) {
					fromULDNumber = constructBulkULDNumber(fromAssignedFlightSegment.getPou(),
							flightAssignedContainerVO.getCarrierCode());
				}
				ULDForSegment uldForSegment = null;
				try {
					uldForSegment = AssignedFlightSegment.findULDForSegment(constructULDForSegmentPK(
							flightAssignedContainerVO.getCompanyCode(), fromULDNumber,
							flightAssignedContainerVO.getCarrierId(), flightAssignedContainerVO.getFlightNumber(),
							flightAssignedContainerVO.getFlightSequenceNumber(),
							flightAssignedContainerVO.getSegmentSerialNumber()));
				} catch (FinderException finderException) {
					throw new SystemException(finderException.getMessage(), finderException.getMessage(),
							finderException);
				}
				ULDForSegmentVO uldForSegmentVO = null;
				AssignedFlightSegment toAssignedFlightSegment = null;
				try {
					toAssignedFlightSegment = findAssignedFlightSegment(toFlightVO.getCompanyCode(),
							toFlightVO.getCarrierId(), toFlightVO.getFlightNumber(),
							toFlightVO.getFlightSequenceNumber(), toFlightSegSerialNo);
				} catch (FinderException ex) {
					throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
				}
				String containerNumber = flightAssignedContainerVO.getContainerNumber();
				Collection<MailbagInULDForSegmentVO> dsnsToReassign = null;
				if (MailConstantsVO.ULD_TYPE.equals(flightAssignedContainerVO.getType())) {
					uldForSegmentVO = uldForSegment.retrieveVO();
					double actualWgtCal = 0.0;
					actualWgtCal = calculateActualWeightForULD(uldForSegmentVO, flightAssignedContainerVO,
							fromULDNumber);
					flightAssignedContainerVO.setActualWeight(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(actualWgtCal)));
					ULDForSegmentVO fromULDForSegVO = uldForSegment.retrieveVO();
					fromULDForSegVO.setPou(flightAssignedContainerVO.getPou());
					fromULDForSegVO.setCarrierId(flightAssignedContainerVO.getCarrierId());
					fromULDForSegVO.setFlightNumber(flightAssignedContainerVO.getFlightNumber());
					fromULDForSegVO.setFlightDate(flightAssignedContainerVO.getFlightDate());
					fromULDForSegVO.setFlightSequenceNumber(flightAssignedContainerVO.getFlightSequenceNumber());
					fromULDForSegVO.setSegmentSerialNumber(flightAssignedContainerVO.getSegmentSerialNumber());
					updateBookingForFlight(fromULDForSegVO, null, "UPDATE_BOOKING_FROM_FLIGHT");
					fromAssignedFlightSegment.removeULDForSegment(uldForSegment);
					updateULDForSegmentVO(uldForSegmentVO, toFlightVO, flightAssignedContainerVO, toFlightSegSerialNo);
					log.debug("" + "retrieved VO for ULD after flight update ---------> " + " " + uldForSegmentVO);
					uldForSegmentVO.setPou(toFlightVO.getPou());
					uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
					uldForSegmentVO.setFlightNumber(toFlightVO.getFlightNumber());
					uldForSegmentVO.setFlightDate(toFlightVO.getFlightDate());
					uldForSegmentVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
					uldForSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
					String bulkNumber = null;
					if (flightAssignedContainerVO.isUldTobarrow()) {
						bulkNumber = constructBulkULDNumber(flightAssignedContainerVO.getPou(),
								flightAssignedContainerVO.getCarrierCode());
						if (bulkNumber != null) {
							uldForSegmentVO.setUldNumber(bulkNumber);
						}
					}
					if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null
							&& !uldForSegmentVO.getMailbagInULDForSegmentVOs().isEmpty()) {
						uldForSegmentVO.getMailbagInULDForSegmentVOs()
								.forEach(mailbagInULDForSegmentVO -> mailbagInULDForSegmentVO
										.setGhttim(flightAssignedContainerVO.getGHTtime()));
					}
					try {
						ULDForSegment toULDForSegment = AssignedFlightSegment.findULDForSegment(
								constructULDForSegmentPK(toFlightVO.getCompanyCode(), uldForSegmentVO.getUldNumber(),
										toFlightVO.getCarrierId(), toFlightVO.getFlightNumber(),
										toFlightVO.getFlightSequenceNumber(), toFlightSegSerialNo));
						if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null) {
							toULDForSegment.populateMailbagDetails(uldForSegmentVO.getMailbagInULDForSegmentVOs());
						}
					} catch (FinderException e) {
						toAssignedFlightSegment.createULDForSegment(uldForSegmentVO);
					}
					boolean isUMSUpdateNeeded = isULDIntegrationEnabled();
					if (isUMSUpdateNeeded) {
						ULDInFlightVO uldFltVo = new ULDInFlightVO();
						uldFltVo.setUldNumber(flightAssignedContainerVO.getContainerNumber());
						if (flightAssignedContainerVO.getPol() != null) {
							uldFltVo.setPointOfLading(flightAssignedContainerVO.getPol());
						} else {
							uldFltVo.setPointOfLading(flightAssignedContainerVO.getAssignedPort());
						}
						uldFltVo.setPointOfUnLading(flightAssignedContainerVO.getPou());
						uldFltVo.setRemark(flightAssignedContainerVO.getRemarks());
						AssignedFlightSegment.updateULDForOperationsForContainerAcceptance(toFlightVO, uldFltVo);
					}
					updateBookingForFlight(uldForSegmentVO, toFlightVO, "UPDATE_BOOKING_TO_FLIGHT");
					isReassignSuccess = true;
				} else if (MailConstantsVO.BULK_TYPE.equals(flightAssignedContainerVO.getType())) {
					ULDForSegmentVO fromULDForSegVO = uldForSegment.retrieveVO();
					fromULDForSegVO.setPou(flightAssignedContainerVO.getPou());
					fromULDForSegVO.setCarrierId(flightAssignedContainerVO.getCarrierId());
					fromULDForSegVO.setFlightNumber(flightAssignedContainerVO.getFlightNumber());
					fromULDForSegVO.setFlightDate(flightAssignedContainerVO.getFlightDate());
					fromULDForSegVO.setFlightSequenceNumber(flightAssignedContainerVO.getFlightSequenceNumber());
					fromULDForSegVO.setSegmentSerialNumber(flightAssignedContainerVO.getSegmentSerialNumber());
					updateBookingForFlight(fromULDForSegVO, null, "UPDATE_BOOKING_FROM_FLIGHT");
					String toULDNumber = null;
					if (flightAssignedContainerVO.isBarrowToUld()) {
						toULDNumber = flightAssignedContainerVO.getContainerNumber();
					} else {
						toULDNumber = constructBulkULDNumber(toFlightVO.getPou(), toFlightVO.getCarrierCode());
					}
					dsnsToReassign = fromAssignedFlightSegment.reassignBulkContainer(uldForSegment, containerNumber);
					double actualWgtCal = 0.0;
					actualWgtCal = calculateActualWeightForBulk(dsnsToReassign, flightAssignedContainerVO);
					flightAssignedContainerVO.setActualWeight(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(actualWgtCal)));
					if (uldForSegment.getNumberOfBags() == 0) {
						try {
							numberOfBarrowspresent = AssignedFlightSegment
									.findNumberOfBarrowsPresentinFlightorCarrier(flightAssignedContainerVO);
						} finally {
						}
						log.debug("" + "numberOfBarrowspresent-- > " + " " + numberOfBarrowspresent);
						if (numberOfBarrowspresent == 1) {
							log.debug("reassing bulk  removing bulk uld since cnt 0");
							fromAssignedFlightSegment.removeULDForSegment(uldForSegment);
						}
					}
					log.debug("" + "dsnsToReassing -- > " + " " + dsnsToReassign);
					if (dsnsToReassign != null && !dsnsToReassign.isEmpty()) {
						dsnsToReassign.forEach(
								dsnToReassign -> dsnToReassign.setGhttim(flightAssignedContainerVO.getGHTtime()));
					}
					try {
						if (flightAssignedContainerVO.isBarrowToUld()) {
							uldForSegmentVO = new ULDForSegmentVO();
							uldForSegmentVO.setUldNumber(toULDNumber);
							uldForSegmentVO.setMailbagInULDForSegmentVOs(dsnsToReassign);
							updateULDForSegmentVO(uldForSegmentVO, toFlightVO, flightAssignedContainerVO,
									toFlightSegSerialNo);
							log.debug("" + "uldforsegment retrieved for bulk --> " + " " + uldForSegmentVO);
							toAssignedFlightSegment.createULDForSegment(uldForSegmentVO);
							uldForSegmentVO.setPou(toFlightVO.getPou());
							uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
							uldForSegmentVO.setFlightNumber(toFlightVO.getFlightNumber());
							uldForSegmentVO.setFlightDate(toFlightVO.getFlightDate());
							uldForSegmentVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
							uldForSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
							updateBookingForFlight(uldForSegmentVO, toFlightVO, "UPDATE_BOOKING_TO_FLIGHT");
							isReassignSuccess = true;
						} else {
							ULDForSegment toULDForSegment = AssignedFlightSegment
									.findULDForSegment(constructULDForSegmentPK(toFlightVO.getCompanyCode(),
											toULDNumber, toFlightVO.getCarrierId(), toFlightVO.getFlightNumber(),
											toFlightVO.getFlightSequenceNumber(), toFlightSegSerialNo));
							toAssignedFlightSegment.assignBulkContainer(toULDForSegment, dsnsToReassign);
							ULDForSegmentVO toULDForSegVO = toULDForSegment.retrieveVO();
							toULDForSegVO.setPou(toFlightVO.getPou());
							toULDForSegVO.setCarrierId(toFlightVO.getCarrierId());
							toULDForSegVO.setFlightNumber(toFlightVO.getFlightNumber());
							toULDForSegVO.setFlightDate(toFlightVO.getFlightDate());
							toULDForSegVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
							toULDForSegVO.setSegmentSerialNumber(toFlightSegSerialNo);
							updateBookingForFlight(toULDForSegVO, toFlightVO, "UPDATE_BOOKING_TO_FLIGHT");
							isReassignSuccess = true;
						}
					} catch (FinderException finderException) {
						log.error("" + "finder exception for bulk container" + " " + fromULDNumber);
						log.debug("creating new container");
						uldForSegmentVO = new ULDForSegmentVO();
						uldForSegmentVO.setUldNumber(toULDNumber);
						uldForSegmentVO.setMailbagInULDForSegmentVOs(dsnsToReassign);
						updateULDForSegmentVO(uldForSegmentVO, toFlightVO, flightAssignedContainerVO,
								toFlightSegSerialNo);
						log.debug("" + "uldforsegment retrieved for bulk --> " + " " + uldForSegmentVO);
						toAssignedFlightSegment.createULDForSegment(uldForSegmentVO);
						uldForSegmentVO.setPou(toFlightVO.getPou());
						uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
						uldForSegmentVO.setFlightNumber(toFlightVO.getFlightNumber());
						uldForSegmentVO.setFlightDate(toFlightVO.getFlightDate());
						uldForSegmentVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
						uldForSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
						updateBookingForFlight(uldForSegmentVO, toFlightVO, "UPDATE_BOOKING_TO_FLIGHT");
						isReassignSuccess = true;
					}
				}
				if (isReassignSuccess) {
					if (dsnsToReassign == null) {
						dsnsToReassign = uldForSegmentVO.getMailbagInULDForSegmentVOs();
					}
					if (dsnsToReassign != null && dsnsToReassign.size() > 0) {
						Collection<MailbagVO> mailbagVOs = constructMailBagVOFromSeg(dsnsToReassign,
								toFlightVO.getCompanyCode());
						updateDSNForConReassign(mailbagVOs, toFlightVO, flightAssignedContainerVO, toFlightSegSerialNo,
								null);
						mailbagForResdit.addAll(mailbagVOs);
					}
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					mailController.flagHistoryForContainerReassignment(toFlightVO, flightAssignedContainerVO,
							mailbagForResdit, mailController.getTriggerPoint());
					mailController.flagAuditForContainerReassignment(toFlightVO, flightAssignedContainerVO,
							mailbagForResdit);
					boolean provisionalRateImport = false;
					toFlightVO.setSegSerNum(toFlightSegSerialNo);
					Collection<RateAuditVO> rateAuditVOs = ContextUtil.getInstance().getBean(MailController.class).createRateAuditVOs(toFlightVO,
							flightAssignedContainerVO, mailbagForResdit, MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,
							provisionalRateImport);
					log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
					if (rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
						String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
						if (importEnabled != null && importEnabled.contains("M")) {
							try {
								mailOperationsMRAProxy.importMRAData(rateAuditVOs);
							} catch (BusinessException e) {
								throw new SystemException(e.getMessage(), e.getMessage(), e);
							}
						}
					}
					String provisionalRateimportEnabled = findSystemParameterValue(
							MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
					if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
						provisionalRateImport = true;
						Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toFlightVO,
								flightAssignedContainerVO, mailbagForResdit, MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,
								provisionalRateImport);
						if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
							try {
								mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
							} catch (BusinessException e) {
								throw new SystemException(e.getMessage(), e.getMessage(), e);
							}
						}
					}
				}
			}
			if (isReassignSuccess || isNotAccepted) {
				updateReassignedContainer(flightAssignedContainerVO, toFlightVO, toFlightSegSerialNo);
				if (isReassignSuccess) {
					if (MailConstantsVO.FLAG_YES.equals(flightAssignedContainerVO.getPaBuiltFlag())) {
						paBuiltContainers.add(flightAssignedContainerVO);
					}
				}
			}
			if (paBuiltContainers.size() > 0 || mailbagForResdit.size() > 0) {
				flagResditsForContainerReassign(mailbagForResdit, paBuiltContainers, toFlightVO, toFlightSegSerialNo);
			}
			ContainerVO containerReturnVO = new ContainerVO();
			containerReturnVO= mailOperationsMapper.copyContainerVO( flightAssignedContainerVO);
			containerReturnVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
			containerReturnVO.setFlightNumber(toFlightVO.getFlightNumber());
			containerReturnVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
			containerReturnVO.setSegmentSerialNumber(toFlightSegSerialNo);
			containerReturnVO.setCarrierCode(toFlightVO.getCarrierCode());
			containerReturnVO.setFlightDate(toFlightVO.getFlightDate());
			containersForReturn.add(containerReturnVO);
		}
		log.debug("ReassignController" + " : " + "reassignFromFlightToFlight" + " Exiting");
		return containersForReturn;
	}

	/**
	 * @author A-7779
	 * @param dsnsToReassign
	 * @param flightAssignedContainerVO
	 * @return
	 */
	private double calculateActualWeightForBulk(Collection<MailbagInULDForSegmentVO> dsnsToReassign,
												ContainerVO flightAssignedContainerVO) {
		double actualWgtCal = 0.0;
		log.debug("ReassignController" + " : " + "calculateActualWeightForBulk" + " Entering");
		Map airportParameters = null;
		try {
			airportParameters = sharedAreaProxy.findAirportParametersByCode(flightAssignedContainerVO.getCompanyCode(),
					flightAssignedContainerVO.getAssignedPort(), getAirportParameterCodes());
		} finally {
		}
		if ((airportParameters != null && airportParameters.size() > 0
				&& !("Y".equals((String) airportParameters.get(WEIGHT_SCALE_AVAILABLE))))
				|| airportParameters.size() == 0) {
			if (dsnsToReassign != null && dsnsToReassign.size() > 0) {
				for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : dsnsToReassign) {
					if (mailbagInULDForSegmentVO.getWeight() != null) {
						//TODO: system value: to be verified as part of neo coding
						actualWgtCal += mailbagInULDForSegmentVO.getWeight().getValue().doubleValue();
					}
				}
			}
			if (flightAssignedContainerVO.isBarrowToUld()) {
				String fromULDNumber = flightAssignedContainerVO.getContainerNumber();
				ULDVO uldVO = null;
				try {
					uldVO = uLDDefaultsProxy.findULDDetails(flightAssignedContainerVO.getCompanyCode(), fromULDNumber);
				} catch (ULDDefaultsProxyException e1) {
					log.debug("ULDDefaultsProxyException");
				}
				Quantity actualTareWeight = null;
				if (uldVO != null) {
					//TODO: To be verified as part of neo coding
					actualTareWeight = quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(uldVO.getTareWeight().getRoundedSystemValue()));
					//actualTareWeight = uldVO.getTareWeight();
				} else {
					ULDValidationFilterVO uLDValidationFilterVO = new ULDValidationFilterVO();
					uLDValidationFilterVO.setCompanyCode(flightAssignedContainerVO.getCompanyCode());
					uLDValidationFilterVO.setUldTypeCode(fromULDNumber.substring(0, 3));
					uLDValidationFilterVO.setSerialNumber(fromULDNumber.substring(3, fromULDNumber.length() - 2));
					uLDValidationFilterVO.setUldAirlineCode(
							fromULDNumber.substring(fromULDNumber.length() - 2, fromULDNumber.length()));
					uLDValidationFilterVO.setUldNumber(fromULDNumber);
					try {
						actualTareWeight = sharedULDProxy.findULDTareWeight(uLDValidationFilterVO);
					} catch (SharedProxyException e) {
						e.getMessage();
					}
				}
				if (actualTareWeight != null) {
					actualWgtCal += actualTareWeight.getValue().doubleValue();
				}
			}
		} else if (flightAssignedContainerVO.getActualWeight() != null) {
			actualWgtCal = flightAssignedContainerVO.getActualWeight().getValue().doubleValue();
		}
		log.debug("ReassignController" + " : " + "calculateActualWeightForBulk" + " Exiting");
		return actualWgtCal;
	}

	/**
	 * @author A-7779
	 * @param uldForSegmentVO
	 * @param flightAssignedContainerVO
	 * @return
	 */
	private double calculateActualWeightForULD(ULDForSegmentVO uldForSegmentVO, ContainerVO flightAssignedContainerVO,
											   String fromULDNumber) {
		log.debug("ReassignController" + " : " + "calculateActualWeightForULD" + " Entering");
		double actualWgtCal = 0.0;
		Map airportParameters = null;
		try {
			airportParameters = sharedAreaProxy.findAirportParametersByCode(flightAssignedContainerVO.getCompanyCode(),
					flightAssignedContainerVO.getAssignedPort(), getAirportParameterCodes());
		} finally {
		}
		if ((airportParameters != null && airportParameters.size() > 0
				&& !("Y".equals((String) airportParameters.get(WEIGHT_SCALE_AVAILABLE))))
				|| airportParameters.size() == 0) {
			if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null
					&& uldForSegmentVO.getMailbagInULDForSegmentVOs().size() > 0) {
				Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs = new ArrayList<MailbagInULDForSegmentVO>();
				for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : uldForSegmentVO
						.getMailbagInULDForSegmentVOs()) {
					if (uldForSegmentVO != null
							&& uldForSegmentVO.getUldNumber().equals(mailbagInULDForSegmentVO.getContainerNumber())) {
						if (mailbagInULDForSegmentVO.getWeight() != null) {
							//TODO: To be verified as part of neo coding
							actualWgtCal += mailbagInULDForSegmentVO.getWeight().getValue().doubleValue();
						}
						mailbagInULDForSegmentVOs.add(mailbagInULDForSegmentVO);
					}
				}
				uldForSegmentVO.setMailbagInULDForSegmentVOs(mailbagInULDForSegmentVOs);
			}
			if (!flightAssignedContainerVO.isUldTobarrow()) {
				Quantity actualTareWeight = null;
				ULDVO uldVO = null;
				try {
					uldVO = uLDDefaultsProxy.findULDDetails(flightAssignedContainerVO.getCompanyCode(), fromULDNumber);
				} catch (ULDDefaultsProxyException e1) {
					log.debug("ULDDefaultsProxyException");
				}
				if (uldVO != null) {
					actualTareWeight = quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(uldVO.getTareWeight().getRoundedSystemValue()));
					//actualTareWeight = uldVO.getTareWeight();

				} else {
					ULDValidationFilterVO uLDValidationFilterVO = new ULDValidationFilterVO();
					uLDValidationFilterVO.setCompanyCode(flightAssignedContainerVO.getCompanyCode());
					uLDValidationFilterVO.setUldTypeCode(fromULDNumber.substring(0, 3));
					uLDValidationFilterVO.setSerialNumber(fromULDNumber.substring(3, fromULDNumber.length() - 2));
					uLDValidationFilterVO.setUldAirlineCode(
							fromULDNumber.substring(fromULDNumber.length() - 2, fromULDNumber.length()));
					uLDValidationFilterVO.setUldNumber(fromULDNumber);
					try {
						actualTareWeight = sharedULDProxy.findULDTareWeight(uLDValidationFilterVO);
					} catch (SharedProxyException e) {
						e.getMessage();
					}
				}
				if (actualTareWeight != null) {
					actualWgtCal += actualTareWeight.getValue().doubleValue();
				}
			}
		} else if (flightAssignedContainerVO.getActualWeight() != null) {
			actualWgtCal = flightAssignedContainerVO.getActualWeight().getValue().doubleValue();
		}
		log.debug("ReassignController" + " : " + "calculateActualWeightForULD" + " Exiting");
		return actualWgtCal;
	}

	/**
	 * This Method will create the Coll. Of Mailbags & Despatches from uldForSegmentVO and will eventually Update the booking made for a flight.
	 * @author A-3227 RENO K ABRAHAM, Added on 22/09/08
	 * @param uldForSegmentVO
	 * @param updateFlight
	 * @throws CapacityBookingProxyException
	 * @throws SystemException
	 * @throws MailBookingException
	 */
	public void updateBookingForFlight(ULDForSegmentVO uldForSegmentVO, OperationalFlightVO toFlightVO,
									   String updateFlight) throws CapacityBookingProxyException, MailBookingException {
		log.debug("ReassignController" + " : " + "updateBookingForFlight" + " Entering");
		Collection<MailbagVO> flightAssignedMailbags = new ArrayList<MailbagVO>();
		Collection<DespatchDetailsVO> flightAssignedDespatches = new ArrayList<DespatchDetailsVO>();
		if (uldForSegmentVO != null) {
			Collection<DSNInULDForSegmentVO> dsnInULDForSegmentVOs = uldForSegmentVO.getDsnInULDForSegmentVOs();
			if (dsnInULDForSegmentVOs != null && dsnInULDForSegmentVOs.size() > 0) {
				for (DSNInULDForSegmentVO dsNInULDForSegmentVO : dsnInULDForSegmentVOs) {
					if (dsNInULDForSegmentVO.getMailBags() != null && dsNInULDForSegmentVO.getMailBags().size() > 0) {
						for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : dsNInULDForSegmentVO.getMailBags()) {
							MailbagVO mailbagVO = new MailbagVO();
							mailbagVO.setAcceptanceFlag(mailbagInULDForSegmentVO.getAcceptanceFlag());
							mailbagVO.setArrivedFlag(mailbagInULDForSegmentVO.getArrivalFlag());
							mailbagVO.setMailClass(mailbagInULDForSegmentVO.getMailClass());
							mailbagVO.setMailbagId(mailbagInULDForSegmentVO.getMailId());
							mailbagVO.setScannedDate(mailbagInULDForSegmentVO.getScannedDate());
							mailbagVO.setScannedPort(mailbagInULDForSegmentVO.getScannedPort());
							mailbagVO.setWeight(mailbagInULDForSegmentVO.getWeight());
							mailbagVO.setDoe(dsNInULDForSegmentVO.getDestinationOfficeOfExchange());
							mailbagVO.setDespatchSerialNumber(dsNInULDForSegmentVO.getDsn());
							mailbagVO.setMailCategoryCode(dsNInULDForSegmentVO.getMailCategoryCode());
							mailbagVO.setMailSubclass(dsNInULDForSegmentVO.getMailSubclass());
							mailbagVO.setOoe(dsNInULDForSegmentVO.getOriginOfficeOfExchange());
							mailbagVO.setYear(dsNInULDForSegmentVO.getYear());
							mailbagVO.setCarrierId(uldForSegmentVO.getCarrierId());
							mailbagVO.setCompanyCode(uldForSegmentVO.getCompanyCode());
							mailbagVO.setFlightNumber(uldForSegmentVO.getFlightNumber());
							mailbagVO.setFlightDate(uldForSegmentVO.getFlightDate());
							mailbagVO.setFlightSequenceNumber(uldForSegmentVO.getFlightSequenceNumber());
							mailbagVO.setSegmentSerialNumber(uldForSegmentVO.getSegmentSerialNumber());
							mailbagVO.setPou(uldForSegmentVO.getPou());
							if (toFlightVO != null) {
								if (toFlightVO.getPol() != null && toFlightVO.getPol().trim().length() > 0) {
									mailbagVO.setPol(toFlightVO.getPol());
								}
								if (toFlightVO.getCarrierCode() != null
										&& toFlightVO.getCarrierCode().trim().length() > 0) {
									mailbagVO.setCarrierCode(toFlightVO.getCarrierCode());
								}
							}
							mailbagVO.setContainerNumber(uldForSegmentVO.getUldNumber());
							mailbagVO.setUldNumber(uldForSegmentVO.getUldNumber());
							if (mailbagVO.getContainerNumber() != null
									&& mailbagVO.getContainerNumber().trim().length() > 0) {
								if (mailbagVO.getContainerNumber().startsWith("BULK")) {
									mailbagVO.setContainerType(MailConstantsVO.BULK_TYPE);
								} else {
									mailbagVO.setContainerType(MailConstantsVO.ULD_TYPE);
								}
							}
							flightAssignedMailbags.add(mailbagVO);
						}
					}
					if (dsNInULDForSegmentVO.getDsnInConsignments() != null
							&& dsNInULDForSegmentVO.getDsnInConsignments().size() > 0) {
						for (DSNInConsignmentForULDSegmentVO dsnInConsignmentForULDSegmentVO : dsNInULDForSegmentVO
								.getDsnInConsignments()) {
							DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
							despatchDetailsVO.setAcceptedBags(dsnInConsignmentForULDSegmentVO.getAcceptedBags());
							despatchDetailsVO.setAcceptedWeight(dsnInConsignmentForULDSegmentVO.getAcceptedWeight());
							despatchDetailsVO.setAcceptedDate(dsnInConsignmentForULDSegmentVO.getAcceptedDate());
							despatchDetailsVO.setAcceptedUser(dsnInConsignmentForULDSegmentVO.getAcceptedUser());
							despatchDetailsVO.setConsignmentDate(dsnInConsignmentForULDSegmentVO.getConsignmentDate());
							despatchDetailsVO
									.setConsignmentNumber(dsnInConsignmentForULDSegmentVO.getConsignmentNumber());
							despatchDetailsVO.setMailClass(dsnInConsignmentForULDSegmentVO.getMailClass());
							despatchDetailsVO.setPaCode(dsnInConsignmentForULDSegmentVO.getPaCode());
							despatchDetailsVO
									.setConsignmentSequenceNumber(dsnInConsignmentForULDSegmentVO.getSequenceNumber());
							despatchDetailsVO.setStatedBags(dsnInConsignmentForULDSegmentVO.getStatedBags());
							despatchDetailsVO.setStatedWeight(dsnInConsignmentForULDSegmentVO.getStatedWeight());
							despatchDetailsVO.setDestinationOfficeOfExchange(
									dsNInULDForSegmentVO.getDestinationOfficeOfExchange());
							despatchDetailsVO.setDsn(dsNInULDForSegmentVO.getDsn());
							despatchDetailsVO.setYear(dsNInULDForSegmentVO.getYear());
							despatchDetailsVO.setMailCategoryCode(dsNInULDForSegmentVO.getMailCategoryCode());
							despatchDetailsVO.setMailSubclass(dsNInULDForSegmentVO.getMailSubclass());
							despatchDetailsVO
									.setOriginOfficeOfExchange(dsNInULDForSegmentVO.getOriginOfficeOfExchange());
							despatchDetailsVO.setPltEnabledFlag(dsNInULDForSegmentVO.getPltEnabledFlag());
							despatchDetailsVO.setCarrierId(uldForSegmentVO.getCarrierId());
							despatchDetailsVO.setCompanyCode(uldForSegmentVO.getCompanyCode());
							despatchDetailsVO.setFlightNumber(uldForSegmentVO.getFlightNumber());
							despatchDetailsVO.setFlightDate(uldForSegmentVO.getFlightDate());
							despatchDetailsVO.setFlightSequenceNumber(uldForSegmentVO.getFlightSequenceNumber());
							despatchDetailsVO.setSegmentSerialNumber(uldForSegmentVO.getSegmentSerialNumber());
							despatchDetailsVO.setPou(uldForSegmentVO.getPou());
							if (toFlightVO != null) {
								if (toFlightVO.getPol() != null && toFlightVO.getPol().trim().length() > 0) {
									despatchDetailsVO.setAirportCode(toFlightVO.getPol());
								}
								if (toFlightVO.getCarrierCode() != null
										&& toFlightVO.getCarrierCode().trim().length() > 0) {
									despatchDetailsVO.setCarrierCode(toFlightVO.getCarrierCode());
								}
							}
							despatchDetailsVO.setUldNumber(uldForSegmentVO.getUldNumber());
							despatchDetailsVO.setContainerNumber(uldForSegmentVO.getUldNumber());
							if (despatchDetailsVO.getContainerNumber() != null
									&& despatchDetailsVO.getContainerNumber().trim().length() > 0) {
								if (despatchDetailsVO.getContainerNumber().startsWith("BULK")) {
									despatchDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
								} else {
									despatchDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
								}
							}
							flightAssignedDespatches.add(despatchDetailsVO);
						}
					}
					if (dsNInULDForSegmentVO.getDsnInContainerForSegs() != null
							&& dsNInULDForSegmentVO.getDsnInContainerForSegs().size() > 0) {
						for (DSNInContainerForSegmentVO dsnInContainerForSegmentVO : dsNInULDForSegmentVO
								.getDsnInContainerForSegs()) {
							if (dsnInContainerForSegmentVO.getDsnInConsignments() != null
									&& dsnInContainerForSegmentVO.getDsnInConsignments().size() > 0) {
								for (DSNInConsignmentForContainerSegmentVO dsnInCsgForConSegVO : dsnInContainerForSegmentVO
										.getDsnInConsignments()) {
									DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
									despatchDetailsVO.setAcceptedBags(dsnInCsgForConSegVO.getAcceptedBags());
									despatchDetailsVO.setAcceptedWeight(dsnInCsgForConSegVO.getAcceptedWeight());
									despatchDetailsVO.setAcceptedDate(dsnInCsgForConSegVO.getAcceptedDate());
									despatchDetailsVO.setAcceptedUser(dsnInCsgForConSegVO.getAcceptedUser());
									despatchDetailsVO.setConsignmentDate(dsnInCsgForConSegVO.getConsignmentDate());
									despatchDetailsVO.setConsignmentNumber(dsnInCsgForConSegVO.getConsignmentNumber());
									despatchDetailsVO.setMailClass(dsnInCsgForConSegVO.getMailClass());
									despatchDetailsVO.setPaCode(dsnInCsgForConSegVO.getPaCode());
									despatchDetailsVO
											.setConsignmentSequenceNumber(dsnInCsgForConSegVO.getSequenceNumber());
									despatchDetailsVO.setStatedBags(dsnInCsgForConSegVO.getStatedBags());
									despatchDetailsVO.setStatedWeight(dsnInCsgForConSegVO.getStatedWeight());
									despatchDetailsVO.setDestinationOfficeOfExchange(
											dsNInULDForSegmentVO.getDestinationOfficeOfExchange());
									despatchDetailsVO.setDsn(dsNInULDForSegmentVO.getDsn());
									despatchDetailsVO.setYear(dsNInULDForSegmentVO.getYear());
									despatchDetailsVO.setMailCategoryCode(dsNInULDForSegmentVO.getMailCategoryCode());
									despatchDetailsVO.setMailSubclass(dsNInULDForSegmentVO.getMailSubclass());
									despatchDetailsVO.setOriginOfficeOfExchange(
											dsNInULDForSegmentVO.getOriginOfficeOfExchange());
									despatchDetailsVO.setPltEnabledFlag(dsNInULDForSegmentVO.getPltEnabledFlag());
									despatchDetailsVO.setCarrierId(uldForSegmentVO.getCarrierId());
									despatchDetailsVO.setCompanyCode(uldForSegmentVO.getCompanyCode());
									despatchDetailsVO.setFlightNumber(uldForSegmentVO.getFlightNumber());
									despatchDetailsVO.setFlightDate(uldForSegmentVO.getFlightDate());
									despatchDetailsVO
											.setFlightSequenceNumber(uldForSegmentVO.getFlightSequenceNumber());
									despatchDetailsVO.setSegmentSerialNumber(uldForSegmentVO.getSegmentSerialNumber());
									despatchDetailsVO.setPou(uldForSegmentVO.getPou());
									if (toFlightVO != null) {
										if (toFlightVO.getPol() != null && toFlightVO.getPol().trim().length() > 0) {
											despatchDetailsVO.setAirportCode(toFlightVO.getPol());
										}
										if (toFlightVO.getCarrierCode() != null
												&& toFlightVO.getCarrierCode().trim().length() > 0) {
											despatchDetailsVO.setCarrierCode(toFlightVO.getCarrierCode());
										}
									}
									despatchDetailsVO.setUldNumber(uldForSegmentVO.getUldNumber());
									despatchDetailsVO.setContainerNumber(uldForSegmentVO.getUldNumber());
									if (despatchDetailsVO.getContainerNumber() != null
											&& despatchDetailsVO.getContainerNumber().trim().length() > 0) {
										if (despatchDetailsVO.getContainerNumber().startsWith("BULK")) {
											despatchDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
										} else {
											despatchDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
										}
									}
									flightAssignedDespatches.add(despatchDetailsVO);
								}
							}
						}
					}
				}
			}
			if (updateFlight != null && updateFlight.trim().length() > 0) {
				if ("UPDATE_BOOKING_FROM_FLIGHT".equals(updateFlight)) {
					log.debug("UPDATE_BOOKING_FROM_FLIGHT");
				} else if ("UPDATE_BOOKING_TO_FLIGHT".equals(updateFlight)) {
					log.debug("UPDATE_BOOKING_TO_FLIGHT");
				}
			}
		}
		log.debug("ReassignController" + " : " + "updateBookingForFlight" + " Exiting");
	}

	private void updateULDForSegmentVO(ULDForSegmentVO uldForSegmentVO, OperationalFlightVO toFlightVO,
									   ContainerVO assignedContainerVO, int toFlightSegSerialNo) {
		uldForSegmentVO.setCompanyCode(toFlightVO.getCompanyCode());
		uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
		uldForSegmentVO.setFlightNumber(toFlightVO.getFlightNumber());
		uldForSegmentVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
		uldForSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
		uldForSegmentVO.setRemarks(assignedContainerVO.getRemarks());
		uldForSegmentVO.setOnwardRoutings(constructOnwardRoutingForSegments(assignedContainerVO.getOnwardRoutings()));
		if (!MailConstantsVO.BULK_TYPE.equals(assignedContainerVO.getType())) {
			uldForSegmentVO.setRemarks(assignedContainerVO.getRemarks());
		} else {
			Collection<DSNInULDForSegmentVO> dsnsInULD = uldForSegmentVO.getDsnInULDForSegmentVOs();
			if (dsnsInULD != null) {
				int totalBags = 0;
				double totalWeight = 0;
				for (DSNInULDForSegmentVO dsnInULD : dsnsInULD) {
					Collection<DSNInContainerForSegmentVO> dsnsInCon = dsnInULD.getDsnInContainerForSegs();
					totalBags += dsnInULD.getAcceptedBags();
					totalWeight += dsnInULD.getAcceptedWeight().getRoundedValue().doubleValue();
					if (dsnsInCon != null) {
						for (DSNInContainerForSegmentVO dsnInCon : dsnsInCon) {
							if (dsnInCon.getContainerNumber().equals(assignedContainerVO.getContainerNumber())) {
								dsnInCon.setRemarks(assignedContainerVO.getRemarks());
							}
						}
					}
					uldForSegmentVO.setMailbagInULDForSegmentVOs(dsnInULD.getMailBags());
				}
				uldForSegmentVO.setNoOfBags(totalBags);
				uldForSegmentVO
						.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(totalWeight)));
			}
		}
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(toFlightVO.getCompanyCode());
		containerPk.setContainerNumber(assignedContainerVO.getContainerNumber());
		containerPk.setAssignmentPort(assignedContainerVO.getAssignedPort());
		containerPk.setFlightNumber(toFlightVO.getFlightNumber());
		containerPk.setCarrierId(toFlightVO.getCarrierId());
		containerPk.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(toFlightVO.getLegSerialNumber());
		Container container = null;
		try {
			container = Container.find(containerPk);
			if (MailConstantsVO.BULK_TYPE.equals(container.getContainerType())
					&& !assignedContainerVO.isBarrowToUld()) {
				String fromULDNumber = constructBulkULDNumber(assignedContainerVO.getFinalDestination(),
						assignedContainerVO.getCarrierCode());
				uldForSegmentVO.setUldNumber(fromULDNumber);
			}
		} catch (FinderException ex) {
			container = null;
		}
	}

	private Collection<OnwardRouteForSegmentVO> constructOnwardRoutingForSegments(
			Collection<OnwardRoutingVO> onwardRoutings) {
		Collection<OnwardRouteForSegmentVO> onwardRouteForSegmentVOs = new ArrayList<OnwardRouteForSegmentVO>();
		if (onwardRoutings != null && onwardRoutings.size() > 0) {
			for (OnwardRoutingVO onwardRoutingVO : onwardRoutings) {
				OnwardRouteForSegmentVO onwardRouteForSegmentVO = new OnwardRouteForSegmentVO();
				onwardRouteForSegmentVO.setOnwardCarrierCode(onwardRoutingVO.getOnwardCarrierCode());
				onwardRouteForSegmentVO.setOnwardFlightNumber(onwardRoutingVO.getOnwardFlightNumber());
				onwardRouteForSegmentVO.setOnwardFlightDate(onwardRoutingVO.getOnwardFlightDate());
				onwardRouteForSegmentVO.setOnwardCarrierId(onwardRoutingVO.getOnwardCarrierId());
				onwardRouteForSegmentVO.setPou(onwardRoutingVO.getPou());
				onwardRouteForSegmentVOs.add(onwardRouteForSegmentVO);
			}
		}
		return onwardRouteForSegmentVOs;
	}

	/**
	 * @author A-5991
	 * @param mailbagToReassign
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	private Collection<MailbagVO> constructMailBagVOFromSeg(Collection<MailbagInULDForSegmentVO> mailbagToReassign,
															String companyCode) {
		log.debug("ReassignController" + " : " + "getDSNVOsOfSeg" + " Entering");
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		if (mailbagToReassign != null) {
			for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : mailbagToReassign) {
				MailbagVO mailbagVO = new MailbagVO();
				mailbagVO.setCompanyCode(companyCode);
				mailbagVO.setMailSequenceNumber(mailbagInULDForSegmentVO.getMailSequenceNumber());
				mailbagVO.setTransferFromCarrier(mailbagInULDForSegmentVO.getTransferFromCarrier());
				mailbagVOs.add(mailbagVO);
			}
		}
		return mailbagVOs;
	}

	/**
	 * @author A-1936
	 */
	private void updateTrfCarrierForResdits(Collection<MailbagInULDForSegmentVO> mailsInUldForSegments,
											Collection<MailbagVO> mailbagVOs) {
		boolean canBreak = false;
		log.debug("" + "THE DSNVO'S BEFORE" + " " + mailbagVOs);
		if (mailsInUldForSegments != null && mailsInUldForSegments.size() > 0) {
			for (MailbagInULDForSegmentVO mailBagInUld : mailsInUldForSegments) {
				canBreak = false;
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for (MailbagVO mailbag : mailbagVOs) {
						if (mailBagInUld.getMailSequenceNumber() == (mailbag.getMailSequenceNumber())) {
							mailbag.setTransferFromCarrier(mailBagInUld.getTransferFromCarrier());
							canBreak = true;
							log.debug("THE TRANSFER FROM CARRIER IS UPDATED");
							break;
						}
					}
					if (canBreak) {
						break;
					}
				}
			}
		}
		log.debug("" + "THE DSNVO'S BEFORE" + " " + mailbagVOs);
	}

	/**
	 * This method checks whether ULD integration Enabled or not
	 * @return
	 * @throws SystemException
	 */
	private boolean isULDIntegrationEnabled() {
		boolean isULDIntegrationEnabled = false;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.ULD_INTEGRATION_ENABLED);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = ContextUtil.getInstance().getBean(NeoMastersServiceUtils.class).findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		log.debug("" + " systemParameterMap " + " " + systemParameterMap);
		if (systemParameterMap != null
				&& ContainerVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.ULD_INTEGRATION_ENABLED))) {
			isULDIntegrationEnabled = true;
		}
		log.debug("" + " isULDIntegrationEnabled :" + " " + isULDIntegrationEnabled);
		return isULDIntegrationEnabled;
	}

	/**
	 * This methods copies the details from the ForSegmentVOs to the AirportVOs A-1739
	 * @param uldForSegmentVO
	 * @param airport
	 * @param isOffload
	 * @return
	 * @throws SystemException
	 */
	private ULDAtAirportVO constructULDAtAirportVOFromSegment(ULDForSegmentVO uldForSegmentVO, String airport,
															  boolean isOffload) {
		log.debug("ReassignController" + " : " + "getULDAtAirportVOFromSegment" + " Entering");
		ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
		uldAtAirportVO.setCompanyCode(uldForSegmentVO.getCompanyCode());
		uldAtAirportVO.setUldNumber(uldForSegmentVO.getUldNumber());
		uldAtAirportVO.setNumberOfBags(uldForSegmentVO.getNoOfBags());
		uldAtAirportVO.setTotalWeight(uldForSegmentVO.getTotalWeight());
		uldAtAirportVO.setRemarks(uldForSegmentVO.getRemarks());
		uldAtAirportVO.setWarehouseCode(uldForSegmentVO.getWarehouseCode());
		uldAtAirportVO.setLocationCode(uldForSegmentVO.getLocationCode());
		Collection<MailbagInULDForSegmentVO> dsnsInULDForSegment = uldForSegmentVO.getMailbagInULDForSegmentVOs();
		if (dsnsInULDForSegment != null && dsnsInULDForSegment.size() > 0) {
			Collection<MailbagInULDAtAirportVO> dsnInULDAtAirports = constructDSNsInULDAtArpFromSeg(dsnsInULDForSegment,
					airport, isOffload);
			uldAtAirportVO.setMailbagInULDAtAirportVOs(dsnInULDAtAirports);
		}
		Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVO = uldForSegmentVO.getMailbagInULDForSegmentVOs();
		if (mailbagInULDForSegmentVO != null && mailbagInULDForSegmentVO.size() > 0) {
			Collection<MailbagInULDAtAirportVO> mailbagVOs = constructMailbagInULDAtAirportVOs(mailbagInULDForSegmentVO,
					airport);
			uldAtAirportVO.setMailbagInULDAtAirportVOs(mailbagVOs);
		}
		log.debug("ReassignController" + " : " + "getULDAtAirportVOFromSegment" + " Exiting");
		return uldAtAirportVO;
	}

	/**
	 * This method converts the VO for Segment to that for an Airport. All details fo ULDForSegment transferred to a ULDAtAirport A-1739
	 * @param dsnsToReassign
	 * @param airportCode
	 * @param isOffload
	 * @return
	 * @throws SystemException
	 */
	private Collection<MailbagInULDAtAirportVO> constructDSNsInULDAtArpFromSeg(
			Collection<MailbagInULDForSegmentVO> dsnsToReassign, String airportCode, boolean isOffload) {
		log.debug("ReassignController" + " : " + "constructDSNsInULDAtArpFromSeg" + " Entering");
		Collection<MailbagInULDAtAirportVO> mailsInULDAtArp = new ArrayList<MailbagInULDAtAirportVO>();
		for (MailbagInULDForSegmentVO mailInULDForSeg : dsnsToReassign) {
			MailbagInULDAtAirportVO mailInULDAtAirport = new MailbagInULDAtAirportVO();
			mailInULDAtAirport.setMailSubclass(mailInULDForSeg.getMailSubclass());
			mailInULDAtAirport.setMailCategoryCode(mailInULDForSeg.getMailCategoryCode());
			mailInULDAtAirport.setMailClass(mailInULDForSeg.getMailClass());
			mailInULDAtAirport.setMailId(mailInULDForSeg.getMailId());
			mailInULDAtAirport.setMailSequenceNumber(mailInULDForSeg.getMailSequenceNumber());
			log.debug("" + "before containers" + " " + mailInULDAtAirport);
			log.debug("" + "before containers mailbags" + " " + mailInULDAtAirport);
			MailbagInULDAtAirportVO mailbagInULDArp = new MailbagInULDAtAirportVO();
			log.debug("" + "containers mailbags" + " " + mailInULDForSeg);
			mailbagInULDArp.setComapnyCode(mailInULDForSeg.getCompanyCode());
			mailbagInULDArp.setAirportCode(airportCode);
			mailbagInULDArp.setMailId(mailInULDForSeg.getMailId());
			mailbagInULDArp.setMailCategoryCode(mailInULDForSeg.getMailCategoryCode());
			mailbagInULDArp.setMailClass(mailInULDForSeg.getMailClass());
			mailbagInULDArp.setMailSubclass(mailInULDForSeg.getMailSubclass());
			mailbagInULDArp.setContainerNumber(mailInULDForSeg.getContainerNumber());
			mailbagInULDArp.setDamageFlag(mailInULDForSeg.getDamageFlag());
			mailbagInULDArp.setScannedDate(mailInULDForSeg.getScannedDate());
			mailbagInULDArp.setSealNumber(mailInULDForSeg.getSealNumber());
			mailbagInULDArp.setMailSequenceNumber(mailInULDForSeg.getMailSequenceNumber());
			mailbagInULDArp.setTransferFromCarrier(mailInULDForSeg.getTransferFromCarrier());
			mailbagInULDArp.setWeight(mailInULDForSeg.getWeight());
			log.debug("" + "containers mailbags copied" + " " + mailbagInULDArp);
			mailbagInULDArp.setScannedDate(ContextUtil.getInstance().getBean(LocalDate.class).getLocalDate(airportCode, true));
			mailbagInULDArp.setMailClass(mailInULDForSeg.getMailClass());
			mailsInULDAtArp.add(mailbagInULDArp);
			log.debug("" + "containers mailbags copied final" + " " + mailbagInULDArp);
			log.debug("" + "before containers mailbags" + " " + mailsInULDAtArp);
		}
		log.debug("ReassignController" + " : " + "constructDSNsInULDAtArpFromSeg" + " Exiting");
		return mailsInULDAtArp;
	}

	/**
	 * This method reassigns containers assigned to a flight to their destination
	 * @param flightAssignedContainers the containers assigned to flight
	 * @param toDestinationVO the FlightVO representing the to destination
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	public Collection<ContainerVO> reassignContainerFromFlightToDest(Collection<ContainerVO> flightAssignedContainers,
																	 OperationalFlightVO toDestinationVO, Collection<String> mailIdsForMonitorSLAs)
			throws ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException {
		log.debug("ReassignController" + " : " + "reassignFromFlightToDest" + " Entering");
		boolean isUldIntegrationEnabled = isULDIntegrationEnabled();
		int numberOfBarrowspresent = 0;
		ULDInFlightVO uldInFlightVO = null;
		Collection<ULDInFlightVO> uldInFlightVOs = null;
		FlightDetailsVO flightDetailsVO = null;
		if (isUldIntegrationEnabled) {
			uldInFlightVOs = new ArrayList<ULDInFlightVO>();
			flightDetailsVO = new FlightDetailsVO();
			flightDetailsVO.setCompanyCode(toDestinationVO.getCompanyCode());
		}
		Collection<ContainerVO> paBuiltContainers = new ArrayList<ContainerVO>();
		Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>();
		Collection<MailbagVO> mailbagsForContainerOffload = new ArrayList<MailbagVO>();
		boolean isRemove = false;
		if (flightAssignedContainers != null && !flightAssignedContainers.isEmpty()) {
			isRemove = flightAssignedContainers.iterator().next().isRemove();
		}
		for (ContainerVO flightAssignedContainerVO : flightAssignedContainers) {
			boolean isNotAccepted = true;
			boolean isReassignSuccess = false;
			Collection<MailbagVO> mailbagsToFlag = new ArrayList<MailbagVO>();
			Collection<MailbagVO> mailbagsForOffload = new ArrayList<>();
			Collection<MailbagVO> mailbagVOs = null;
			if (CONST_YES.equals(flightAssignedContainerVO.getAcceptanceFlag())) {
				isNotAccepted = false;
				String fromULDNumber = flightAssignedContainerVO.getContainerNumber();
				if (MailConstantsVO.BULK_TYPE.equals(flightAssignedContainerVO.getType())) {
					fromULDNumber = constructBulkULDNumber(flightAssignedContainerVO.getPou(),
							flightAssignedContainerVO.getCarrierCode());
				}
				ULDForSegment uldForSegment = null;
				try {
					uldForSegment = AssignedFlightSegment.findULDForSegment(constructULDForSegmentPK(
							flightAssignedContainerVO.getCompanyCode(), fromULDNumber,
							flightAssignedContainerVO.getCarrierId(), flightAssignedContainerVO.getFlightNumber(),
							flightAssignedContainerVO.getFlightSequenceNumber(),
							flightAssignedContainerVO.getSegmentSerialNumber()));
				} catch (FinderException finderException) {
					throw new SystemException("No such ULD at this port", finderException.getMessage(),
							finderException);
				}
				AssignedFlightSegment fromAssignedFlightSegment = null;
				try {
					fromAssignedFlightSegment = findAssignedFlightSegment(flightAssignedContainerVO.getCompanyCode(),
							flightAssignedContainerVO.getCarrierId(), flightAssignedContainerVO.getFlightNumber(),
							flightAssignedContainerVO.getFlightSequenceNumber(),
							flightAssignedContainerVO.getSegmentSerialNumber());
				} catch (FinderException ex) {
					throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
				}
				Collection<MailbagInULDAtAirportVO> dsnsToReassign = null;
				String containerNumber = flightAssignedContainerVO.getContainerNumber();
				ULDAtAirportVO uldAtAirportVO = null;
				if (MailConstantsVO.ULD_TYPE.equals(flightAssignedContainerVO.getType())) {
					ULDForSegmentVO fromULDForSegVO = uldForSegment.retrieveVO();
					fromULDForSegVO.setPou(flightAssignedContainerVO.getPou());
					fromULDForSegVO.setCarrierId(flightAssignedContainerVO.getCarrierId());
					fromULDForSegVO.setFlightNumber(flightAssignedContainerVO.getFlightNumber());
					fromULDForSegVO.setFlightDate(flightAssignedContainerVO.getFlightDate());
					fromULDForSegVO.setFlightSequenceNumber(flightAssignedContainerVO.getFlightSequenceNumber());
					fromULDForSegVO.setSegmentSerialNumber(flightAssignedContainerVO.getSegmentSerialNumber());
					updateBookingForFlight(fromULDForSegVO, null, "UPDATE_BOOKING_FROM_FLIGHT");
					uldAtAirportVO = constructULDAtAirportVOFromSegment(uldForSegment.retrieveVO(),
							toDestinationVO.getPol(), flightAssignedContainerVO.isOffload());
					if (flightAssignedContainerVO.isUldTobarrow()) {
						dsnsToReassign = constructDSNsInULDAtArpFromSeg(
								fromAssignedFlightSegment.reassignBulkContainer(uldForSegment, containerNumber),
								toDestinationVO.getPol(), flightAssignedContainerVO.isOffload());
					}
					fromAssignedFlightSegment.removeULDForSegment(uldForSegment);
					updateULDAtAirportVO(uldAtAirportVO, toDestinationVO, flightAssignedContainerVO);
					log.debug("" + "udlatarp VO --> " + " " + uldAtAirportVO);
					try {
						ULDAtAirportPK uldAtAirportPK = new ULDAtAirportPK();
						uldAtAirportPK.setAirportCode(uldAtAirportVO.getAirportCode());
						uldAtAirportPK.setCarrierId(uldAtAirportVO.getCarrierId());
						uldAtAirportPK.setCompanyCode(uldAtAirportVO.getCompanyCode());
						uldAtAirportPK.setUldNumber(uldAtAirportVO.getUldNumber());
						ULDAtAirport toULDAtAirport = ULDAtAirport.find(uldAtAirportPK);
						toULDAtAirport.assignBulkContainer(dsnsToReassign);
					} catch (FinderException exception) {
						log.debug("" + "udlatarp VO exception--> " + " " + exception.getMessage());
						new ULDAtAirport(uldAtAirportVO);
					}
					isReassignSuccess = true;
				} else if (MailConstantsVO.BULK_TYPE.equals(flightAssignedContainerVO.getType())) {
					ULDForSegmentVO fromULDForSegVO = uldForSegment.retrieveVO();
					fromULDForSegVO.setPou(flightAssignedContainerVO.getPou());
					fromULDForSegVO.setCarrierId(flightAssignedContainerVO.getCarrierId());
					fromULDForSegVO.setFlightNumber(flightAssignedContainerVO.getFlightNumber());
					fromULDForSegVO.setFlightDate(flightAssignedContainerVO.getFlightDate());
					fromULDForSegVO.setFlightSequenceNumber(flightAssignedContainerVO.getFlightSequenceNumber());
					fromULDForSegVO.setSegmentSerialNumber(flightAssignedContainerVO.getSegmentSerialNumber());
					updateBookingForFlight(fromULDForSegVO, null, "UPDATE_BOOKING_FROM_FLIGHT");
					String toULDNumber = null;
					if (flightAssignedContainerVO.isBarrowToUld()) {
						toULDNumber = flightAssignedContainerVO.getContainerNumber();
					} else {
						toULDNumber = constructBulkULDNumber(flightAssignedContainerVO.getFinalDestination(),
								flightAssignedContainerVO.getCarrierCode());
					}
					dsnsToReassign = constructDSNsInULDAtArpFromSeg(
							fromAssignedFlightSegment.reassignBulkContainer(uldForSegment, containerNumber),
							toDestinationVO.getPol(), flightAssignedContainerVO.isOffload());
					if (uldForSegment.getNumberOfBags() == 0) {
						try {
							numberOfBarrowspresent = AssignedFlightSegment
									.findNumberOfBarrowsPresentinFlightorCarrier(flightAssignedContainerVO);
						} finally {
						}
						log.debug("" + "numberOfBarrowspresent-- > " + " " + numberOfBarrowspresent);
						if (numberOfBarrowspresent == 1) {
							log.debug("reassing bulk  removing bulk uld since cnt 0");
							fromAssignedFlightSegment.removeULDForSegment(uldForSegment);
						}
					}
					log.debug("" + "retrieved dsns for uldtype --> " + " " + dsnsToReassign);
					try {
						ULDAtAirport toUldAtAirport = ULDAtAirport
								.find(constructULDArpPKFromOpFlt(toDestinationVO, toULDNumber));
						uldAtAirportVO = toUldAtAirport.retrieveVO();
						updateULDAtAirportVO(uldAtAirportVO, toDestinationVO, flightAssignedContainerVO);
						toUldAtAirport.assignBulkContainer(dsnsToReassign);
						isReassignSuccess = true;
					} catch (FinderException finderException) {
						uldAtAirportVO = new ULDAtAirportVO();
						uldAtAirportVO.setUldNumber(toULDNumber);
						uldAtAirportVO.setMailbagInULDAtAirportVOs(dsnsToReassign);
						updateULDAtAirportVO(uldAtAirportVO, toDestinationVO, flightAssignedContainerVO);
						log.debug("" + "udlatarp VO --> in to filgt" + " " + uldAtAirportVO);
						new ULDAtAirport(uldAtAirportVO);
						isReassignSuccess = true;

					}
				}
				if (isReassignSuccess) {
					if (dsnsToReassign == null) {
						if (uldAtAirportVO.getMailbagInULDAtAirportVOs() != null
								&& uldAtAirportVO.getMailbagInULDAtAirportVOs().size() > 0) {

							mailbagVOs = new ReassignController().constructMailbagVOsFromAirport(uldAtAirportVO.getMailbagInULDAtAirportVOs(),
									toDestinationVO.getCompanyCode());
							updateDSNForConReassign(mailbagVOs, toDestinationVO, flightAssignedContainerVO,
									MailConstantsVO.DESTN_FLT, mailIdsForMonitorSLAs);
							if (flightAssignedContainerVO.isOffload()) {
								if (!MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD
										.equals(flightAssignedContainerVO.getMailSource())
										&& !flightAssignedContainerVO.isRemove()) {
									flagResditForMailbags(mailbagVOs, toDestinationVO.getPol(),
											MailConstantsVO.RESDIT_PENDING);
								}
								mailbagsForContainerOffload.addAll(mailbagVOs);
								mailbagsForOffload.addAll(mailbagVOs);
							} else {
								mailbagsToFlag.addAll(mailbagVOs);
							}
						}
					}
					if (dsnsToReassign != null && dsnsToReassign.size() > 0) {
						mailbagVOs = constructMailbagVOsFromAirport(dsnsToReassign, toDestinationVO.getCompanyCode());
						updateDSNForConReassign(mailbagVOs, toDestinationVO, flightAssignedContainerVO,
								MailConstantsVO.DESTN_FLT, mailIdsForMonitorSLAs);
						if (flightAssignedContainerVO.isOffload()) {
							flagResditForMailbags(mailbagVOs, toDestinationVO.getPol(), MailConstantsVO.RESDIT_PENDING);
							mailbagsForContainerOffload.addAll(mailbagVOs);
							mailbagsForOffload.addAll(mailbagVOs);
						} else {
							mailbagsToFlag.addAll(mailbagVOs);
						}
					}
					MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
					if (flightAssignedContainerVO.isOffload()) {
						mailController.flagHistoryForContainerReassignment(toDestinationVO, flightAssignedContainerVO,
								mailbagsForOffload, mailController.getTriggerPoint());
						mailController.flagAuditForContainerReassignment(toDestinationVO, flightAssignedContainerVO,
								mailbagsForContainerOffload);
					} else {
						mailController.flagHistoryForContainerReassignment(toDestinationVO, flightAssignedContainerVO,
								mailbagsToFlag, mailController.getTriggerPoint());
						mailController.flagAuditForContainerReassignment(toDestinationVO, flightAssignedContainerVO,
								mailbagsToFlag);
					}
					if (Objects.nonNull(mailbagVOs) && !mailbagVOs.isEmpty()) {
						ContextUtil.getInstance().getBean(MailController.class).validateAndReImportMailbagsToMRA(mailbagVOs);
					}
				}
			}
			if (isReassignSuccess || (isNotAccepted && !flightAssignedContainerVO.isRemove())) {
				updateReassignedContainer(flightAssignedContainerVO, toDestinationVO, MailConstantsVO.DESTN_FLT);
				if (!flightAssignedContainerVO.isOffload()) {
					if (isReassignSuccess
							&& MailConstantsVO.FLAG_YES.equals(flightAssignedContainerVO.getPaBuiltFlag())) {
						paBuiltContainers.add(flightAssignedContainerVO);
					}
				}
			}
			if (isUldIntegrationEnabled && !(MailConstantsVO.BULK_TYPE.equals(flightAssignedContainerVO.getType()))) {
				log.info("Creating ULDInFlightVO");
				uldInFlightVO = new ULDInFlightVO();
				uldInFlightVO.setUldNumber(flightAssignedContainerVO.getContainerNumber());
				uldInFlightVO.setPointOfLading(flightAssignedContainerVO.getAssignedPort());
				uldInFlightVO.setPointOfUnLading(flightAssignedContainerVO.getPou());
				uldInFlightVO.setRemark(flightAssignedContainerVO.getRemarks());
				flightDetailsVO.setRemark(flightAssignedContainerVO.getRemarks());
				uldInFlightVOs.add(uldInFlightVO);
			}
			ContainerVO containerReturnVO = new ContainerVO();
			containerReturnVO = mailOperationsMapper.copyContainerVO( flightAssignedContainerVO);
			containerReturnVO.setFlightSequenceNumber(toDestinationVO.getFlightSequenceNumber());
			containerReturnVO.setFlightNumber(toDestinationVO.getFlightNumber());
			containerReturnVO.setLegSerialNumber(toDestinationVO.getLegSerialNumber());
			containerReturnVO.setCarrierCode(toDestinationVO.getCarrierCode());
			containerReturnVO.setFlightDate(toDestinationVO.getFlightDate());
			containersForReturn.add(containerReturnVO);
			if ((paBuiltContainers.size() > 0 || mailbagsToFlag.size() > 0) && !flightAssignedContainerVO.isRemove()) {
				flagResditsForContainerReassign(mailbagsToFlag, paBuiltContainers, toDestinationVO,
						MailConstantsVO.DESTN_FLT);
			}
		}
		if (isUldIntegrationEnabled && !isRemove) {
			flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
			flightDetailsVO.setAction(FlightDetailsVO.OFFLOADED);
			for (MailbagVO mailbagvo : mailbagsForContainerOffload) {
				flightDetailsVO.setFlightNumber(mailbagvo.getFlightNumber());
				flightDetailsVO.setFlightSequenceNumber(mailbagvo.getFlightSequenceNumber());
			}
			uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
		}
		log.debug("ReassignController" + " : " + "reassignFromFlightToDest" + " Exiting");
		return containersForReturn;
	}

	/**
	 * This method is used for flagging various resdits. This inturn call various private methods for flagging different resdits A-1739
	 * @param eventAirport
	 * @param eventCode
	 * @throws SystemException
	 */
	private void flagResditForMailbags(Collection<MailbagVO> mailbagsToFlag, String eventAirport, String eventCode) {
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagResditForMailbagsFromReassign(eventCode, eventAirport, mailbagsToFlag);
	}

	/**
	 * @author A-5991
	 * @param mailbagsInULDSeg
	 * @param airportCode
	 * @return
	 */
	public Collection<MailbagInULDAtAirportVO> constructMailbagInULDAtAirportVOs(
			Collection<MailbagInULDForSegmentVO> mailbagsInULDSeg, String airportCode) {
		Collection<MailbagInULDAtAirportVO> mailbagsInULDArp = new ArrayList<MailbagInULDAtAirportVO>();
		if (mailbagsInULDSeg != null && mailbagsInULDSeg.size() > 0) {
			for (MailbagInULDForSegmentVO mailbagInULDSeg : mailbagsInULDSeg) {
				MailbagInULDAtAirportVO mailbagInULDArp = new MailbagInULDAtAirportVO();
				log.debug("" + "containers mailbags" + " " + mailbagInULDSeg);
				mailbagInULDArp.setComapnyCode(mailbagInULDSeg.getCompanyCode());
				mailbagInULDArp.setAirportCode(airportCode);
				mailbagInULDArp.setUldNumber(mailbagInULDSeg.getContainerNumber());
				mailbagInULDArp.setMailId(mailbagInULDSeg.getMailId());
				mailbagInULDArp.setMailSequenceNumber(mailbagInULDSeg.getMailSequenceNumber());
				mailbagInULDArp.setMailCategoryCode(mailbagInULDSeg.getMailCategoryCode());
				mailbagInULDArp.setMailClass(mailbagInULDSeg.getMailClass());
				mailbagInULDArp.setMailSubclass(mailbagInULDSeg.getMailSubclass());
				mailbagInULDArp.setContainerNumber(mailbagInULDSeg.getContainerNumber());
				mailbagInULDArp.setDamageFlag(mailbagInULDSeg.getDamageFlag());
				mailbagInULDArp.setScannedDate(mailbagInULDSeg.getScannedDate());
				mailbagInULDArp.setSealNumber(mailbagInULDSeg.getSealNumber());
				mailbagInULDArp.setTransferFromCarrier(mailbagInULDSeg.getTransferFromCarrier());
				mailbagInULDArp.setWeight(mailbagInULDSeg.getWeight());
				log.debug("" + "containers mailbags copied" + " " + mailbagInULDArp);
				mailbagInULDArp.setScannedDate(ContextUtil.getInstance().getBean(LocalDate.class).getLocalDate(airportCode, true));
				mailbagInULDArp.setMailClass(mailbagInULDSeg.getMailClass());
				mailbagsInULDArp.add(mailbagInULDArp);
				log.debug("" + "containers mailbags copied final" + " " + mailbagInULDArp);
			}
		}
		return mailbagsInULDArp;
	}

	public void reassignDSNsFromDestination(Collection<DespatchDetailsVO> dsnsToReassign) {
		log.debug("ReassignController" + " : " + "reassignDSNsFromDestination" + " Entering");
		Map<ULDAtAirportPK, Collection<DespatchDetailsVO>> uldDespatchMap = groupULDDespatches(dsnsToReassign);
		ULDAtAirport uldArp = null;
		try {
			for (Map.Entry<ULDAtAirportPK, Collection<DespatchDetailsVO>> uldDespatch : uldDespatchMap.entrySet()) {
				ULDAtAirportPK uldArpPK = uldDespatch.getKey();
				Collection<DespatchDetailsVO> uldDespatches = uldDespatch.getValue();
				uldArp = ULDAtAirport.find(uldArpPK);
				if (uldArp.getNumberOfBags() == 0) {
					uldArp.remove();
					log.debug("uldArp for bulk removed coz cnt 0");
					if (!uldArp.getUldNumber().startsWith(MailConstantsVO.CONST_BULK)) {
						removeContainerForDespatch(uldArpPK, uldDespatches);
					}
				}
			}
		} catch (FinderException exception) {
		}
		log.debug("ReassignController" + " : " + "reassignDSNsFromDestination" + " Exiting");
	}

	/**
	 * Removes a barrow from the ContainerMaster. This got empty when all the dsns and maiblags in it were moved from it A-1739
	 * @param uldAtAirportPK
	 * @param despatchesInCon
	 * @throws SystemException
	 */
	private void removeContainerForDespatch(ULDAtAirportPK uldAtAirportPK,
											Collection<DespatchDetailsVO> despatchesInCon) {
		log.debug("DSNInULDAtAirport" + " : " + "updateContainerDetails" + " Entering");
		ContainerPK containerPK = constructContainerPKForDespatch(uldAtAirportPK, despatchesInCon);
		Container destAssignedContainer = null;
		try {
			destAssignedContainer = Container.find(containerPK);
		} catch (FinderException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		UldInFlightVO uldInFlightVO = new UldInFlightVO();
		uldInFlightVO.setCompanyCode(containerPK.getCompanyCode());
		uldInFlightVO.setUldNumber(containerPK.getContainerNumber());
		uldInFlightVO.setPou(destAssignedContainer.getPou());
		uldInFlightVO.setAirportCode(containerPK.getAssignmentPort());
		uldInFlightVO.setCarrierId(containerPK.getCarrierId());
		if (containerPK.getFlightSequenceNumber() > 0) {
			uldInFlightVO.setFlightNumber(containerPK.getFlightNumber());
			uldInFlightVO.setFlightSequenceNumber(containerPK.getFlightSequenceNumber());
			uldInFlightVO.setLegSerialNumber(containerPK.getLegSerialNumber());
		}
		uldInFlightVO.setFlightDirection(MailConstantsVO.OPERATION_INBOUND);
		String conType = destAssignedContainer.getContainerType();
		destAssignedContainer.remove();
		log.debug("removed con");
		Collection<UldInFlightVO> operationalUlds = new ArrayList<UldInFlightVO>();
		operationalUlds.add(uldInFlightVO);
		boolean isOprUldEnabled = MailConstantsVO.FLAG_YES
				.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD));
		if (isOprUldEnabled) {
			if (operationalUlds != null && operationalUlds.size() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(conType)) {
					operationsFltHandlingProxy.saveOperationalULDsInFlight(operationalUlds);
				}
			}
		}
		log.debug("DSNInULDAtAirport" + " : " + "updateContainerDetails" + " Exiting");
	}

	/**
	 * A-1739
	 * @param dsnsToReassign
	 * @return
	 */
	private Map<ULDAtAirportPK, Collection<DespatchDetailsVO>> groupULDDespatches(
			Collection<DespatchDetailsVO> dsnsToReassign) {
		Map<ULDAtAirportPK, Collection<DespatchDetailsVO>> uldDespatchMap = new HashMap<ULDAtAirportPK, Collection<DespatchDetailsVO>>();
		for (DespatchDetailsVO despatchDetailsVO : dsnsToReassign) {
			ULDAtAirportPK uldArpPK = constructULDArpPKFromDSN(despatchDetailsVO);
			Collection<DespatchDetailsVO> uldDespatches = uldDespatchMap.get(uldArpPK);
			if (uldDespatches == null) {
				uldDespatches = new ArrayList<DespatchDetailsVO>();
				uldDespatchMap.put(uldArpPK, uldDespatches);
			}
			uldDespatches.add(despatchDetailsVO);
		}
		return uldDespatchMap;
	}

	/**
	 * A-1739
	 * @param despatchDetailsVO
	 * @return
	 */
	private ULDAtAirportPK constructULDArpPKFromDSN(DespatchDetailsVO despatchDetailsVO) {
		ULDAtAirportPK uldArpPK = new ULDAtAirportPK();
		uldArpPK.setCompanyCode(despatchDetailsVO.getCompanyCode());
		uldArpPK.setCarrierId(despatchDetailsVO.getCarrierId());
		uldArpPK.setAirportCode(despatchDetailsVO.getAirportCode());
		uldArpPK.setUldNumber(despatchDetailsVO.getUldNumber());
		if (MailConstantsVO.BULK_TYPE.equals(despatchDetailsVO.getContainerType())) {
			uldArpPK.setUldNumber(
					constructBulkULDNumber(despatchDetailsVO.getDestination(), despatchDetailsVO.getCarrierCode()));
		}
		return uldArpPK;
	}

	private ContainerPK constructContainerPKForDespatch(ULDAtAirportPK uldAtAirportPK,
														Collection<DespatchDetailsVO> despatchesInCon) {
		String asgPort = null;
		for (DespatchDetailsVO despatchInCon : despatchesInCon) {
			asgPort = despatchInCon.getAirportCode();
			break;
		}
		ContainerPK containerPK = new ContainerPK();
		containerPK.setCompanyCode(uldAtAirportPK.getCompanyCode());
		containerPK.setAssignmentPort(asgPort);
		containerPK.setContainerNumber(uldAtAirportPK.getUldNumber());
		containerPK.setCarrierId(uldAtAirportPK.getCarrierId());
		containerPK.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
		containerPK.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
		containerPK.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
		return containerPK;
	}

	private void flagHistoryFormailbagReassign(Collection<MailbagVO> mailbags, ContainerVO toContainerVO) {
		log.debug("ReassignController" + " : " + "flagHistoryFormailbagReassign" + " Entering");
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.flagHistoryDetailsForMailbagsFromReassign(mailbags, toContainerVO);
		mailController.flagAuditDetailsForMailbagsFromReassign(mailbags, toContainerVO);
		log.debug("ReassignController" + " : " + "flagHistoryFormailbagReassign" + " Exiting");
	}

	private void populateMailbagVOsForResdit(Collection<MailbagVO> mailbags,
											 Collection<MailbagVO> mailbagVOsForResdit) {
		if (mailbags != null && mailbags.size() > 0) {
			for (MailbagVO mailbagVO : mailbags) {
				MailbagVO mailbagForResdit = new MailbagVO();
				mailbagForResdit = mailOperationsMapper.copyMailbagVO(mailbagVO);
				mailbagVOsForResdit.add(mailbagForResdit);
			}
		}
	}

	private Collection<String> getAirportParameterCodes() {
		Collection<String> parameterTypes = new ArrayList();
		parameterTypes.add(WEIGHT_SCALE_AVAILABLE);
		return parameterTypes;
	}
	private void containerDetailsAudit(Container container, ContainerVO containerVo, ContainerPK containerPk,String triggeringPoint) {
		StringBuilder additInfo = new StringBuilder();
		if (containerVo.isOffload()) {
			additInfo.append("Offloaded from ");
		} else if (containerVo.isTransferAudit()) {
			additInfo.append("Transferred to ");
		} else {
			additInfo.append("Reassigned to ");
		}
		if (containerVo.isOffload()) {
			if (!"-1".equals(containerVo.getFromFltNum())) {
				additInfo.append(containerVo.getCarrierCode()).append(" ").append(containerVo.getFromFltNum())
						.append(", ");
			} else {
				additInfo.append(containerVo.getCarrierCode()).append(", ");
			}
		} else {
			if (!"-1".equals(containerVo.getFlightNumber())) {
				additInfo.append(containerVo.getCarrierCode()).append(" ")
						.append(containerVo.getFlightNumber()).append(", ");
			} else {
				additInfo.append(containerVo.getCarrierCode()).append(", ");
			}
		}
		additInfo.append(localDateUtil.getLocalDate(containerPk.getAssignmentPort(), true)
				.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))).append(", ");
		if (containerVo.isOffload()) {
			if (!"-1".equals(containerVo.getFromFltNum())) {
				additInfo.append(containerPk.getAssignmentPort()).append(" - ").append(containerVo.getPrevFlightPou())
						.append(" ");
			}
		} else {
			if (!"-1".equals(containerVo.getFlightNumber())) {
				additInfo.append(containerPk.getAssignmentPort()).append(" - ").append(containerVo.getPou())
						.append(" ");
			}
		}
		additInfo.append("in ").append(containerPk.getAssignmentPort());
		String action=null;
		String transaction=null;
		if (containerVo.isOffload()) {
			action=MailConstantsVO.CONTAINER_OFFLOAD;

		} else if (containerVo.isTransferAudit()) {
			action=MailConstantsVO.CONTAINER_TRANSFER;

		} else {
			action=MailConstantsVO.CONTAINER_REASSIGN;
		}
		ContextUtil.getInstance().getBean(MailController.class).auditContainerUpdates(containerVo,action,"Container Update",additInfo.toString(),triggeringPoint);

	}
	private void collectContainerAuditDetails(Container container,ContainerVO assignedContainerVO) throws SystemException {
		StringBuffer additionalInfo = new StringBuffer();
		if(!"-1".equals(container.getFlightNumber())){
			additionalInfo.append(container.getCarrierCode()).append(" ").append(container.getFlightNumber()).append(", ");
		} else {
			additionalInfo.append(container.getCarrierCode()).append(", ");
		}
		additionalInfo.append(ContextUtil.getInstance().getBean(LocalDate.class).getLocalDate(container.getAssignmentPort(),true).format(MailConstantsVO.DISPLAY_DATE_ONLY_FORMAT)).append(", ");
		if(!"-1".equals(container.getFlightNumber())){
			additionalInfo.append(container.getAssignmentPort()).append(" - ").append(container.getPou()).append(" ");
		}
		additionalInfo.append("in ").append(container.getAssignmentPort());
		ContextUtil.getInstance().getBean(MailController.class).auditContainerUpdates(assignedContainerVO,"DELETE",assignedContainerVO.getTransactionCode(),additionalInfo.toString(),assignedContainerVO.getTriggerPoint());


	}

}