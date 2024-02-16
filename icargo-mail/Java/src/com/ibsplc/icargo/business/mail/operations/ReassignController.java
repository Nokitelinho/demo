/*
 * ReassignController.java Created on Aug 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsMRAProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsFltHandlingProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedULDProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.ULDDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNInConsignmentForContainerSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNInConsignmentForULDSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNInContainerAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNInContainerForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNInULDAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNInULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRouteForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.event.annotations.Raise;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2553
 *
 */

public class ReassignController {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String DEFAULT_STORAGEUNITFORMAIL = "mailtracking.defaults.defaultstorageunitformail";
	private static final String CONST_YES = "Y";
    private static final String  WEIGHT_SCALE_AVAILABLE="mail.operation.weighscaleformailavailable";
    private static final String MAIL_CONTROLLER="mAilcontroller";

	/**
	 * A-1739
	 *
	 * @param mailbagVO
	 * @param toContainerVO
	 * @return
	 */
	private OperationalFlightVO constructOpFlightVOFromMailbag(
			MailbagVO mailbagVO) {
		OperationalFlightVO opFlightVO = new OperationalFlightVO();
		opFlightVO.setCompanyCode(mailbagVO.getCompanyCode());
		opFlightVO.setCarrierId(mailbagVO.getCarrierId());
		opFlightVO.setFlightNumber(mailbagVO.getFlightNumber());
		opFlightVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		opFlightVO.setFlightDate(mailbagVO.getFlightDate());
		opFlightVO.setPol(mailbagVO.getScannedPort());
		opFlightVO.setCarrierCode(mailbagVO.getCarrierCode());
		log.log(Log.FINE, "THE OPERATIONAL FLIGHT VO FROM MAIL BAG", opFlightVO);
		return opFlightVO;
	}

	/**
	 * @author a-1936 This a FlightProductProxyCall .This method is used to
	 *         validate the FlightForAirport
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<FlightValidationVO> validateFlight(
			FlightFilterVO flightFilterVO) throws SystemException {
		log.entering("ReassignController", "validateFlight");

		return new FlightOperationsProxy()
				.validateFlightForAirport(flightFilterVO);

	}

	private FlightValidationVO validateOperationalFlight(
			OperationalFlightVO opFlightVO, boolean isInbound)
			throws SystemException {
		log.entering("ReassignController", "validateOperationalFlight");
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(opFlightVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(opFlightVO.getCarrierId());
		flightFilterVO.setFlightNumber(opFlightVO.getFlightNumber());
		flightFilterVO.setFlightDate(opFlightVO.getFlightDate());
		if(opFlightVO.getFlightSequenceNumber()>0){
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
				if (flightValidationVO.getFlightSequenceNumber() == opFlightVO
						.getFlightSequenceNumber()) {
					toReturnVO = flightValidationVO;
				}
			}
		}
		log.exiting("ReassignController", "validateOperationalFlight");
		log.log(Log.FINE, "THE FLIGHT FILTER VO IS ", flightFilterVO);
		return toReturnVO;
	}

	/**
	 * @author A-5991
	 * @param flightVO
	 * @return
	 */
	private AssignedFlightPK constructInboundFlightPK(
			OperationalFlightVO flightVO) {
		AssignedFlightPK assignedFlightPK = new AssignedFlightPK();
		assignedFlightPK.setCompanyCode(flightVO.getCompanyCode());
		assignedFlightPK.setCarrierId(flightVO.getCarrierId());
		assignedFlightPK.setFlightNumber(flightVO.getFlightNumber());
		assignedFlightPK.setFlightSequenceNumber(flightVO
				.getFlightSequenceNumber());
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
	private boolean isFlightClosedForInboundOperations(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		log.entering("ReassignController", "isFlightClosedForInboundOperations");
		boolean isFlightClosedForInbound = false;
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPK = constructInboundFlightPK(operationalFlightVO);

		try {
			assignedFlight = AssignedFlight.find(assignedFlightPK);
		} catch (FinderException ex) {
			log.log(Log.INFO, "FINDER EXCEPTION IS THROWN");
			throw new SystemException(ex.getMessage(), ex);
		}
		if (assignedFlight != null) {
			isFlightClosedForInbound = MailConstantsVO.FLIGHT_STATUS_CLOSED
					.equals(assignedFlight.getImportClosingFlag());
			log.log(Log.FINE, "The Flight Status is found to be ",
					isFlightClosedForInbound);
		}
		log.exiting("ReassignController", "isFlightClosedForInboundOperations");
		return isFlightClosedForInbound;
	}

	/**
	 * @author a-1936 This method is used to check whether the Flight is closed
	 *         for Operations
	 * @param operationalFlightVO
	 * @return
	 * @throws SystemException
	 */
	private boolean isFlightClosedForOperations(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		log.entering("ReassignController", "isFlightClosedForOperations");
		boolean isFlightClosedForOperations = false;
		AssignedFlight assignedFlight = null;
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
		assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(operationalFlightVO
				.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(operationalFlightVO
				.getLegSerialNumber());
		assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		try {
			assignedFlight = AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.log(Log.INFO, "FINDER EXCEPTION IS THROWN");
			//throw new SystemException(ex.getMessage(), ex);//Commented by A-8164 for ICRD-322210
		}
		if (assignedFlight != null) {
			isFlightClosedForOperations = MailConstantsVO.FLIGHT_STATUS_CLOSED
					.equals(assignedFlight.getExportClosingFlag());
			log.log(Log.FINE, "The Flight Status is found to be ",
					isFlightClosedForOperations);
		}
		return isFlightClosedForOperations;
	}

	/**
	 * This method checks if all the mailbags are eligible for Reassignment It
	 * checks the following 1. If the flight in which it is currently sitting is
	 * closed, if so it throws FlightClosedException 2. If the container in
	 * which the mailbag in put is a PABuilt ULD If so it throws a
	 * reassignmentExcepiton, since no mailbags can be moved from a PABuilt ULD
	 * A-1739
	 *
	 * @param mailbagsToReassign
	 * @param flightAssignedMailbags
	 * @param destAssignedMailbags
	 * @return true if all the mailbags are reassignable
	 * @throws SystemException
	 * @throws FlightClosedException
	 * @throws ReassignmentException
	 */
	public boolean isReassignableMailbags(
			Collection<MailbagVO> mailbagsToReassign,
			Collection<MailbagVO> flightAssignedMailbags,
			Collection<MailbagVO> destAssignedMailbags) throws SystemException,
			FlightClosedException {
		log.entering("ReassignController", "isReassignableMailbags");
		Boolean isIntermediateReturn=false;
		for (MailbagVO mailbagVO : mailbagsToReassign) {
			/*
			 * From scanner new mailbags can come which don't have any flight or
			 * destination assignment. These don't have to be removed from the
			 * segment table. Just add their entry in the master and histories
			 */
			if (mailbagVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT
					|| mailbagVO.getFlightSequenceNumber() == 0) {
				destAssignedMailbags.add(mailbagVO);
				mailbagVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
			} else if (mailbagVO.getFlightSequenceNumber() > 0) {
				OperationalFlightVO opFlightVO = constructOpFlightVOFromMailbag(mailbagVO);
				FlightValidationVO flightValidationVO = null;
				if (MailConstantsVO.OPERATION_INBOUND.equals(mailbagVO
						.getOperationalStatus())) {
					/* For Inbound */
					opFlightVO.setPou(mailbagVO.getScannedPort());
					// Added by A-5160 , when flight status changes to TBA leg
					// serial number in flight table and mail table will be
					// different
					if (mailbagVO.getLegSerialNumber() == 0) {
						flightValidationVO = validateOperationalFlight(
								opFlightVO, true);
						if (flightValidationVO != null) {
							opFlightVO.setLegSerialNumber(flightValidationVO
									.getLegSerialNumber());
						}
					} else {
						opFlightVO.setLegSerialNumber(mailbagVO
								.getLegSerialNumber());
					}
					if (isFlightClosedForInboundOperations(opFlightVO)) {
						throw new FlightClosedException(
								FlightClosedException.FLIGHT_STATUS_CLOSED,
								new String[] {
										new StringBuilder()
												.append(opFlightVO
														.getCarrierCode())
												.append(" ")
												.append(opFlightVO
														.getFlightNumber())
												.toString(),
										opFlightVO.getFlightDate()
												.toDisplayDateOnlyFormat(),
										mailbagVO.getMailbagId() });
					}
				} else {
					opFlightVO.setPol(mailbagVO.getScannedPort());
					if (mailbagVO.getLegSerialNumber() == 0) {
						flightValidationVO = validateOperationalFlight(
								opFlightVO, false);
						if (flightValidationVO != null) {
							opFlightVO.setLegSerialNumber(flightValidationVO
									.getLegSerialNumber());
						}
					} else {
						opFlightVO.setLegSerialNumber(mailbagVO
								.getLegSerialNumber());
					}
					if (isFlightClosedForOperations(opFlightVO)&&!mailbagVO.isFlightClosureCheckNotNeeded()) {
						throw new FlightClosedException(
								FlightClosedException.FLIGHT_STATUS_CLOSED,
								new String[] {
										new StringBuilder()
												.append(opFlightVO
														.getCarrierCode())
												.append(" ")
												.append(opFlightVO
														.getFlightNumber())
												.toString(),
										opFlightVO.getFlightDate()
												.toDisplayDateOnlyFormat(),
										mailbagVO.getMailbagId() });
					}
				}
				
				
	              if(mailbagVO.getDamagedMailbags()!=null && !mailbagVO.getDamagedMailbags().isEmpty()
	                		&&"Y".equals(mailbagVO.getDamagedMailbags().iterator().next().getReturnedFlag())
	                		&& "Y".equals(mailbagVO.getArrivedFlag())){
	                	isIntermediateReturn=true;
	                }
	            if(!isIntermediateReturn) { 
				flightAssignedMailbags.add(mailbagVO);
	            }
				

				if (mailbagVO.getLegSerialNumber() == 0) {
					mailbagVO.setLegSerialNumber(flightValidationVO
							.getLegSerialNumber());
				}
			}
		}
		// checkForPaBuiltContainersForMail(destAssignedMailbags);
		// checkForPaBuiltContainersForMail(flightAssignedMailbags);
		log.exiting("ReassignController", "isReassignableMailbags");
		return true;
	}

	private double calculateWeightofBags(Collection<MailbagVO> mailbags) {
		double totalWeight = 0;
		for (MailbagVO mailbagVO : mailbags) {
			totalWeight += mailbagVO.getWeight().getSystemValue();
		}
		return totalWeight;
	}

	private ULDAtAirportVO createULDAtAirportVO(
			ContainerVO toDestinationContainerVO) {
		ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
		uldAtAirportVO
				.setCompanyCode(toDestinationContainerVO.getCompanyCode());
		uldAtAirportVO.setAirportCode(toDestinationContainerVO
				.getAssignedPort());
		uldAtAirportVO.setCarrierId(toDestinationContainerVO.getCarrierId());
		uldAtAirportVO
				.setCarrierCode(toDestinationContainerVO.getCarrierCode());
		uldAtAirportVO.setFinalDestination(toDestinationContainerVO
				.getFinalDestination());
		return uldAtAirportVO;
	}

	/**
	 * A-1739
	 *
	 * @param pou
	 * @return
	 */
	private String constructBulkULDNumber(String airport, String carrierCode) {
		/*
		 * This "airport" can be the POU / Destination
		 */
		if (airport != null && airport.trim().length() > 0) {
			return new StringBuilder().append(MailConstantsVO.CONST_BULK)
					.append(MailConstantsVO.SEPARATOR).append(airport)
					.toString();
		} else {
			return MailConstantsVO.CONST_BULK_ARR_ARP.concat(
					MailConstantsVO.SEPARATOR).concat(carrierCode);
		}
	}

	/**
	 * @author A-1739
	 * @param uldAtAirportVO
	 * @return
	 */
	private ULDAtAirportPK constructULDArpPKFromULDArpVO(
			ULDAtAirportVO uldAtAirportVO) {
		ULDAtAirportPK uldArpPK = new ULDAtAirportPK();
		uldArpPK.setCompanyCode(uldAtAirportVO.getCompanyCode());
		uldArpPK.setCarrierId(uldAtAirportVO.getCarrierId());
		uldArpPK.setAirportCode(uldAtAirportVO.getAirportCode());
		uldArpPK.setUldNumber(uldAtAirportVO.getUldNumber());
		return uldArpPK;
	}

	/**
	 * This method is used to reasign the mails to Destination A-1936
	 *
	 * @param flightAssignedMailbags
	 * @param toDestinationContainerVO
	 * @param dsnAtAirportMap
	 * @throws SystemException
	 */
	public void reassignMailToDestination(
			Collection<MailbagVO> flightAssignedMailbags,
			ContainerVO toDestinationContainerVO) throws SystemException {
		log.entering("ReassignController", "reassignMailToDestination");
		int bagCount = flightAssignedMailbags.size();
		ULDAtAirportVO uldAtAirportVO = createULDAtAirportVO(toDestinationContainerVO);
		ULDAtAirport uldAtAirport = null;
		String uldnumber = toDestinationContainerVO.getContainerNumber();
		if (MailConstantsVO.BULK_TYPE
				.equals(toDestinationContainerVO.getType())) {
			uldnumber = constructBulkULDNumber(
					toDestinationContainerVO.getFinalDestination(),
					toDestinationContainerVO.getCarrierCode());
		}
		uldAtAirportVO.setUldNumber(uldnumber);
		try {
			uldAtAirport = ULDAtAirport
					.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));
			uldAtAirport.setLastUpdateTime(toDestinationContainerVO
					.getULDLastUpdateTime());
		} catch (FinderException ex) {
			log.log(Log.INFO, "THE FINDER EXCEPTION IS THROWN ");
			log.log(Log.INFO, "Create ULDAtAirport");
			uldAtAirport = new ULDAtAirport(uldAtAirportVO);
		}
		/*
		 * uldAtAirport.setNumberOfBags(uldAtAirport.getNumberOfBags() +
		 * bagCount); uldAtAirport.setTotalWeight(uldAtAirport.getTotalWeight()
		 * + calculateWeightofBags(flightAssignedMailbags));
		 */
		uldAtAirport.reassignMailToDestination(flightAssignedMailbags,
				toDestinationContainerVO);
	}

	/**
	 * This method is used to update the MailBags in DSN for reassignment mails
	 * to the Destnation A-1936
	 *
	 * @param flightAssignedMailbags
	 * @param toContainerVO
	 * @throws SystemException
	 */
	public void updateMailbagsForReassign(
			Collection<MailbagVO> flightAssignedMailbags,
			ContainerVO toContainerVO) throws SystemException {
		
		new Mailbag().updateReassignedMailbags(flightAssignedMailbags,
				toContainerVO);
		flagHistoryFormailbagReassign(flightAssignedMailbags, toContainerVO);
	}

	/**
	 * This method is used to construct the AssignedFlightSegmentPK from the
	 * MailBagVos A-1936
	 *
	 * @param flightAssignedMailbagVO
	 * @return
	 */
	private AssignedFlightSegmentPK constructAsgFlightPKForMailbag(
			MailbagVO flightAssignedMailbagVO) {
		AssignedFlightSegmentPK assignedFlightPK = new AssignedFlightSegmentPK();
		assignedFlightPK.setCompanyCode(flightAssignedMailbagVO
				.getCompanyCode());
		assignedFlightPK.setFlightNumber(flightAssignedMailbagVO
				.getFlightNumber());
		assignedFlightPK.setCarrierId(flightAssignedMailbagVO.getCarrierId());
		assignedFlightPK.setFlightSequenceNumber(flightAssignedMailbagVO
				.getFlightSequenceNumber());
		assignedFlightPK.setSegmentSerialNumber(flightAssignedMailbagVO
				.getSegmentSerialNumber());
		return assignedFlightPK;
	}

	/**
	 * reassigsn dSNs from flt to destn and also returns ULDs which become empty
	 * by the process. But barrows are removed by the system A-1739
	 *
	 * @param despatchDetailsVOs
	 * @param toContainerVO
	 * @return the collection of empty ULDs
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> reassignDSNsFromFlightToDestination(
			Collection<DespatchDetailsVO> despatchDetailsVOs,
			ContainerVO toContainerVO) throws SystemException {
		// log.entering("ReassignController",
		// "reassignDSNsFromFlightToDestination");
		Collection<ContainerDetailsVO> emptyContainers = reassignDSNsFromFlight(despatchDetailsVOs);
		// Map<DSNAtAirportPK, DSNAtAirportVO> dsnAtAirportMap = new
		// HashMap<DSNAtAirportPK, DSNAtAirportVO>();
		reassignDSNsToDestination(despatchDetailsVOs, toContainerVO);
		// updateDSNAtAirport(dsnAtAirportMap);
		updateContainerAcceptance(toContainerVO);
		log.exiting("ReassignController", "reassignDSNsFromFlightToDestination");
		return emptyContainers;
	}

	/*private void updateULDAtAirportCount(ULDAtAirport uldArp,
			Collection<DespatchDetailsVO> uldDespatches, boolean isAddition) {
		int noAcpBags = 0;
		double wtAcpBags = 0;
		for (DespatchDetailsVO despatchDetailsVO : uldDespatches) {
			noAcpBags += despatchDetailsVO.getAcceptedBags();
			wtAcpBags += despatchDetailsVO.getAcceptedWeight();
		}
		if (isAddition) {
			uldArp.setNumberOfBags(uldArp.getNumberOfBags() + noAcpBags);
			uldArp.setTotalWeight(uldArp.getTotalWeight() + wtAcpBags);
		} else {
			uldArp.setNumberOfBags(uldArp.getNumberOfBags() - noAcpBags);
			uldArp.setTotalWeight(uldArp.getTotalWeight() - wtAcpBags);
		}
	}*/

	/**
	 * This method is used to reassign the DSNs from Flight Also returns ULDs
	 * which become empty in the process A-1936
	 *
	 * @param despatchDetailsVOs
	 * @return the empty ULDs to return
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> reassignDSNsFromFlight(
			Collection<DespatchDetailsVO> despatchDetailsVOs)
			throws SystemException {
		log.entering("ReassignController", "reassignDSNFromFlight");
		HashMap<AssignedFlightSegmentPK, Collection<DespatchDetailsVO>> assignedFlightSegmentMap = new HashMap<AssignedFlightSegmentPK, Collection<DespatchDetailsVO>>();

		Collection<DespatchDetailsVO> newMailbagVos = null;
		for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {

			AssignedFlightSegmentPK assignedFlightSegmentPK = constructAssignedFlightSegmentPK(despatchDetailsVO);
			newMailbagVos = assignedFlightSegmentMap
					.get(assignedFlightSegmentPK);
			if (newMailbagVos == null) {
				newMailbagVos = new ArrayList<DespatchDetailsVO>();
				assignedFlightSegmentMap.put(assignedFlightSegmentPK,
						newMailbagVos);
			}
			newMailbagVos.add(despatchDetailsVO);
		}

		Collection<ContainerDetailsVO> containersToReturn = new ArrayList<ContainerDetailsVO>();

		if (assignedFlightSegmentMap != null
				&& assignedFlightSegmentMap.size() > 0) {
			log.log(Log.FINE, "THE No of the AssignedFlights Found are ",
					assignedFlightSegmentMap.size());
			AssignedFlightSegment assignedFlightSegment = null;
			try {
				for (AssignedFlightSegmentPK flightSegmentPK : assignedFlightSegmentMap
						.keySet()) {

					assignedFlightSegment = AssignedFlightSegment
							.find(flightSegmentPK);
					containersToReturn.addAll(assignedFlightSegment
							.reassignDSNsFromFlight(assignedFlightSegmentMap
									.get(flightSegmentPK)));
				}
			} catch (FinderException ex) {
				log.log(Log.INFO, "DATA INCONSISTENT");
				throw new SystemException(ex.getMessage(), ex);
			}

		}
		log.log(Log.FINEST, "containersToReturn ", containersToReturn);
		log.exiting("ReassignController", "reassignDSNFromFlight");
		return containersToReturn;
	}

	/**
	 * This method is used to constructAssignedFlightPK From DespatchDetailsVO
	 * A-1936
	 *
	 * @param despatchDetailsVO
	 * @return
	 */
	private AssignedFlightSegmentPK constructAssignedFlightSegmentPK(
			DespatchDetailsVO despatchDetailsVO) {
		log.entering("ReassignController", "constructAssignedFlightSegmentPK");
		AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
		assignedFlightSegmentPK.setCompanyCode(despatchDetailsVO
				.getCompanyCode());
		assignedFlightSegmentPK.setCarrierId(despatchDetailsVO.getCarrierId());
		assignedFlightSegmentPK.setFlightNumber(despatchDetailsVO
				.getFlightNumber());
		assignedFlightSegmentPK.setFlightSequenceNumber(despatchDetailsVO
				.getFlightSequenceNumber());
		assignedFlightSegmentPK.setSegmentSerialNumber(despatchDetailsVO
				.getSegmentSerialNumber());
		log.exiting("ReassignController", "constructAssignedFlightSegmentPK");
		return assignedFlightSegmentPK;
	}

	/**
	 * This method is used to reassign the DSns To a Destination A-1936
	 *
	 * @param despatchesToReassign
	 * @param toContainerVO
	 * @param dsnAtAirportMap
	 * @throws SystemException
	 */
	private void reassignDSNsToDestination(
			Collection<DespatchDetailsVO> despatchesToReassign,
			ContainerVO toContainerVO) throws SystemException {
		log.entering("MailControler", "ReassignDSNsToDestination");
		log.entering("ReassignController", "reassignMailToDestination");
		ULDAtAirportVO uldAtAirportVO = createULDAtAirportVO(toContainerVO);
		ULDAtAirport uldAtAirport = null;
		String uldnumber = toContainerVO.getContainerNumber();
		if (MailConstantsVO.BULK_TYPE.equals(toContainerVO.getType())) {
			uldnumber = constructBulkULDNumber(
					toContainerVO.getFinalDestination(),
					toContainerVO.getCarrierCode());
		}
		uldAtAirportVO.setUldNumber(uldnumber);
		try {
			uldAtAirport = ULDAtAirport
					.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));
		} catch (FinderException ex) {
			log.log(Log.INFO, "THE FINDER EXCEPTION IS THROWN ");
			log.log(Log.INFO, "Create ULDAtAirport");
			uldAtAirport = new ULDAtAirport(uldAtAirportVO);
		}

		//updateULDAtAirportCount(uldAtAirport, despatchesToReassign, true);
		uldAtAirport.reassignDSNsToDestination(despatchesToReassign,
				toContainerVO);
	}

	/**
	 * This method removes the assignment of mailbags from its current flight
	 *
	 * Group the MailBags based on FlightSegments say 1.F1-SEG1 2.F1-SEG2
	 * 3.F2-SEG1 4.F2-SEG2 etc and find out the MailBags associated withg this
	 * Segments.. A-1739
	 *
	 * @param flightAssignedMailbags
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> reassignMailFromFlight(
			Collection<MailbagVO> flightAssignedMailbags)
			throws SystemException {
		log.entering("ReassignController", "reassignMailFromFlight");
		HashMap<AssignedFlightSegmentPK, Collection<MailbagVO>> assignedFlightSegmentMap = new HashMap<AssignedFlightSegmentPK, Collection<MailbagVO>>();

		Collection<MailbagVO> newMailbagVos = null;
		for (MailbagVO mailbagVo : flightAssignedMailbags) {
			log.entering("reassignMailbagsFromFlightToDestination",
					"Group the mailBags for DifferentSegments");
			AssignedFlightSegmentPK assignedFlightSegmentPK = constructAsgFlightPKForMailbag(mailbagVo);
			newMailbagVos = assignedFlightSegmentMap
					.get(assignedFlightSegmentPK);
			if (newMailbagVos == null) {
				newMailbagVos = new ArrayList<MailbagVO>();
				assignedFlightSegmentMap.put(assignedFlightSegmentPK,
						newMailbagVos);
			}
			newMailbagVos.add(mailbagVo);
		}

		Collection<ContainerDetailsVO> containersToReturn = new ArrayList<ContainerDetailsVO>();
		if (assignedFlightSegmentMap != null
				&& assignedFlightSegmentMap.size() > 0) {
			AssignedFlightSegment assignedFlightSegment = null;
			try {
				for (AssignedFlightSegmentPK flightSegmentPK : assignedFlightSegmentMap
						.keySet()) {

					assignedFlightSegment = AssignedFlightSegment
							.find(flightSegmentPK);
					containersToReturn.addAll(assignedFlightSegment
							.reassignMailFromFlight(assignedFlightSegmentMap
									.get(flightSegmentPK)));
				}
			} catch (FinderException ex) {
				log.log(Log.INFO, "DATA INCONSISTENT ");
				throw new SystemException(ex.getMessage(), ex);
			}
		}
		log.log(Log.FINEST, "containersToReturn ", containersToReturn);
		log.exiting("ReassignController", "reassignMailFromFlight");
		return containersToReturn;
	}

	/**
	 * Utilty for finding syspar Mar 23, 2007, A-1739
	 *
	 * @param syspar
	 * @return
	 * @throws SystemException
	 */
	private String findSystemParameterValue(String syspar)
			throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		HashMap<String, String> systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class)
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	/**
	 * This method is used to construct the ContainerDetailsVo from the
	 * ContainerVo A-1936
	 *
	 * @param containerVos
	 * @return
	 */
	private Collection<ContainerDetailsVO> constructConDetailsVOsForResdit(
			Collection<ContainerVO> containerVos) {
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		if (containerVos != null && containerVos.size() > 0) {
			containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
			for (ContainerVO containerVo : containerVos) {
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setCompanyCode(containerVo.getCompanyCode());
				containerDetailsVO.setPol(containerVo.getAssignedPort());
				containerDetailsVO.setContainerNumber(containerVo
						.getContainerNumber());
				containerDetailsVO.setCarrierCode(containerVo.getCarrierCode());
				containerDetailsVO.setCarrierId(containerVo.getCarrierId());
				containerDetailsVO.setFlightNumber(containerVo
						.getFlightNumber());
				containerDetailsVO.setFlightSequenceNumber(containerVo
						.getFlightSequenceNumber());
				containerDetailsVO.setSegmentSerialNumber(containerVo
						.getSegmentSerialNumber());
				containerDetailsVO.setOwnAirlineCode(containerVo
						.getOwnAirlineCode());
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
	private void collectContainerAuditDetails(Container container,
			ContainerAuditVO containerAuditVO) throws SystemException {
		log.log(Log.FINE, "---------Setting ContainerAuditVO Details-------");
		StringBuffer additionalInfo = new StringBuffer();
		log.log(Log.INFO, " container.getContainerPK() ",
				container.getContainerPK());
		String triggeringPoint = ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		containerAuditVO.setCompanyCode(container.getContainerPK()
				.getCompanyCode());
		containerAuditVO.setContainerNumber(container.getContainerPK()
				.getContainerNumber());
		containerAuditVO.setAssignedPort(container.getContainerPK()
				.getAssignmentPort());
		containerAuditVO
				.setCarrierId(container.getContainerPK().getCarrierId());
		containerAuditVO.setFlightNumber(container.getContainerPK()
				.getFlightNumber());
		containerAuditVO.setFlightSequenceNumber(container.getContainerPK()
				.getFlightSequenceNumber());
		containerAuditVO.setLegSerialNumber(container.getContainerPK()
				.getLegSerialNumber());
		containerAuditVO.setUserId(container.getLastUpdateUser());
		additionalInfo.append("Deleted from ");
		if(!"-1".equals(container.getContainerPK().getFlightNumber())){
			additionalInfo.append(container.getCarrierCode()).append(" ").append(container.getContainerPK().getFlightNumber()).append(", ");
				} else {
			additionalInfo.append(container.getCarrierCode()).append(", ");
				}
		additionalInfo.append(new LocalDate(container.getContainerPK().getAssignmentPort(), Location.ARP, true).toDisplayDateOnlyFormat()).append(", ");
		if(!"-1".equals(container.getContainerPK().getFlightNumber())){
			additionalInfo.append(container.getContainerPK().getAssignmentPort()).append(" - ").append(container.getPou()).append(" ");
			}
		additionalInfo.append("in ").append(container.getContainerPK().getAssignmentPort());
		containerAuditVO.setAdditionalInformation(additionalInfo.toString());
		containerAuditVO.setTriggerPnt(triggeringPoint);
		log.exiting("collectContainerAuditDetails",
				"...Finished construction of AuditVO");
	}

	/**
	 * This method is used to update the Container Status as Accepted during
	 * reassignment of mailbags when a mailbag is reassigned to a nonaccepted
	 * container A-1936
	 *
	 * @param toContainerVO
	 * @throws SystemException
	 */
	private void updateContainerAcceptance(ContainerVO toContainerVO)
			throws SystemException {
		log.entering("ReassignController", "updateContainerAcceptance");
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(toContainerVO.getCompanyCode());
		containerPk.setContainerNumber(toContainerVO.getContainerNumber());
		containerPk.setAssignmentPort(toContainerVO.getAssignedPort());
		containerPk.setFlightNumber(toContainerVO.getFlightNumber());
		containerPk.setCarrierId(toContainerVO.getCarrierId());
		containerPk.setFlightSequenceNumber(toContainerVO
				.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(toContainerVO.getLegSerialNumber());
		if(toContainerVO.getAssignedDate() == null){
			toContainerVO.setAssignedDate(new LocalDate(toContainerVO.getAssignedPort(), Location.ARP, true));
		}
		Container container = null;
		try {
			container = Container.find(containerPk);
			container.setLastUpdateTime(toContainerVO.getLastUpdateTime());
		} catch (FinderException ex) {
			if(toContainerVO.isOflToRsnFlag())
				container = new Container(toContainerVO);
			else
			throw new SystemException(ex.getMessage(), ex);
		}

		// flag resdist
		if (MailConstantsVO.FLAG_YES.equals(container.getPaBuiltFlag())
				&& MailConstantsVO.FLAG_NO
						.equals(container.getAcceptanceFlag())) {
			Collection<ContainerVO> containers = new ArrayList<ContainerVO>();
			containers.add(toContainerVO);
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
			mailController.flagResditsForULDAcceptance(
					constructConDetailsVOsForResdit(containers),
					toContainerVO.getAssignedPort());
			/*String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
			if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
				log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
				new ResditController().flagResditsForULDAcceptance(
						constructConDetailsVOsForResdit(containers),
						toContainerVO.getAssignedPort());
			}*/
		}

		container.setAcceptanceFlag(MailConstantsVO.FLAG_YES);

		ContainerAuditVO containerAuditVO = new ContainerAuditVO(
				ContainerVO.MODULE, ContainerVO.SUBMODULE, ContainerVO.ENTITY);
		containerAuditVO.setActionCode(MailConstantsVO.AUDIT_CONACP);
		collectContainerAuditDetails(container, containerAuditVO);
		//added as part of IASCB-67053
		StringBuilder additInfo= null;
		additInfo = new StringBuilder();
		additInfo.append(" Actual Weight: ").append(container.getActualWeight());
		if(Objects.nonNull(container.getContentId())){
			additInfo.append(" Content ID: ").append(container.getContentId());
		}
		additInfo.append(" Flight No: ").append(container.getContainerPK().getFlightNumber())
		.append(" Flight SequenceNumber: ").append(container.getContainerPK().getFlightSequenceNumber())
		.append(" POU: ").append(container.getPou()).append(" Final Destination: ").append(container.getFinalDestination())
		.append(" POA Flag: ").append(container.getPaBuiltFlag()).append(" TRN Flag: ").append(container.getTransferFlag())
		.append(" OFL Flag: ").append(container.getOffloadFlag()).append(" TRA Flag: ").append(container.getTransitFlag());
		if(container.getAssignedOn()!=null) {
			 additInfo.append(" Assigned On: ").append(new LocalDate(containerPk
						.getAssignmentPort(), Location.ARP, container.getAssignedOn(), true).toDisplayFormat());
		 }
		containerAuditVO.setAdditionalInformation(additInfo.toString());
		containerAuditVO.setAuditRemarks(MailConstantsVO.MAIL_ULD_ASSIGNED);
		AuditUtils.performAudit(containerAuditVO);

		log.exiting("ReassignController", "updateContainerAcceptance");
	}

	/**
	 * This method is used to reassign the MailBags From the Flight to
	 * Destination.. A-1739
	 *
	 * @param flightAssignedMailbags
	 * @param toDestinationContainerVO
	 * @param isOffload
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> reassignMailFromFlightToDestination(
			Collection<MailbagVO> flightAssignedMailbags,
			ContainerVO toDestinationContainerVO, boolean isOffload)
			throws SystemException {
		Collection<ContainerDetailsVO> emptyContainers = reassignMailFromFlight(flightAssignedMailbags);
		
		boolean isRemove  = false;
        if(isOffload && flightAssignedMailbags!=null && !flightAssignedMailbags.isEmpty()) {
        	isRemove = flightAssignedMailbags.iterator().next().isRemove();
        }
        
        reassignMailToDestination(flightAssignedMailbags,
				toDestinationContainerVO);
        
		updateMailbagsForReassign(flightAssignedMailbags,
				toDestinationContainerVO);
		new MailController().validateAndReImportMailbagsToMRA(flightAssignedMailbags);
		
        //if it is remove from inbound, no need to invoke below code
		if (isRemove) {
        	return emptyContainers;
        }
		updateContainerAcceptance(toDestinationContainerVO);
		if (isOffload) {
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
			mailController.flagResditForMailbagsFromReassign(
					MailConstantsVO.RESDIT_PENDING,
					toDestinationContainerVO.getAssignedPort(),
					flightAssignedMailbags);
        }
		log.log(Log.FINE, "THE 2222 FINAL DESTN IS  ",
				toDestinationContainerVO.getFinalDestination());
		return emptyContainers;
	}

	/**
	 *
	 * @author a-1936 This method is used to construct the ULDAtAirportPK.
	 * @param mailbag
	 * @return
	 */
	private ULDAtAirportPK constructULDAtAirportPK(MailbagVO mailbag) {
		log.entering("ReassignController", "constructULDAtAirportPK");
		ULDAtAirportPK uldAtAirportPK = new ULDAtAirportPK();
		uldAtAirportPK.setCompanyCode(mailbag.getCompanyCode());
		uldAtAirportPK.setAirportCode(mailbag.getScannedPort());
		uldAtAirportPK.setCarrierId(mailbag.getCarrierId());
		 if(mailbag.getUldNumber()!=null){
		uldAtAirportPK.setUldNumber(mailbag.getUldNumber());
		 }
		 else{
			 uldAtAirportPK.setUldNumber(mailbag.getContainerNumber()) ;
		 }
		// uldAtAirportPK.setUldNumber
		// (MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR).concat(mailbag.getCarrierCode()));
		if (mailbag.getContainerNumber() != null && MailConstantsVO.BULK_TYPE.equals(mailbag.getContainerType())
				&& !(mailbag.isOffloadAndReassign() && mailbag.getUldNumber() != null
						&& !mailbag.getUldNumber().isEmpty())) {
			log.log(Log.INFO, "THE MAL BAG IS  ASSOCIATED WITH A BARROW");
			uldAtAirportPK
					.setUldNumber(constructBulkULDNumber(mailbag.getFinalDestination(), mailbag.getCarrierCode()));
		}

		return uldAtAirportPK;
	}

	private ContainerPK constructContainerPKForMailbag(
			ULDAtAirportPK uldAtAirportPK, Collection<MailbagVO> mailbagsInCon) {
		String asgPort = null;
		for (MailbagVO mailbagInCon : mailbagsInCon) {
			asgPort = mailbagInCon.getScannedPort();
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

	/**
	 * @author A-5991
	 * @param uldAtAirportPK
	 * @param mailbagsInCon
	 * @throws SystemException
	 */
	private void removeContainerAcceptanceForMailbag(
			ULDAtAirportPK uldAtAirportPK, Collection<MailbagVO> mailbagsInCon)
			throws SystemException {
		log.entering("ReassignController",
				"removeContainerAcceptanceForMailbag");

		ContainerPK containerPK = constructContainerPKForMailbag(
				uldAtAirportPK, mailbagsInCon);
		Container destAssignedContainer = null;
		try {
			destAssignedContainer = Container.find(containerPK);
		} catch (FinderException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

		String conType = destAssignedContainer.getContainerType();
		if (MailConstantsVO.ULD_TYPE.equals(conType)) {
			UldInFlightVO uldInFlightVO = new UldInFlightVO();
			uldInFlightVO.setCompanyCode(containerPK.getCompanyCode());
			uldInFlightVO.setUldNumber(containerPK.getContainerNumber());
			uldInFlightVO.setPou(destAssignedContainer.getPou());
			uldInFlightVO.setAirportCode(containerPK.getAssignmentPort());
			uldInFlightVO.setCarrierId(containerPK.getCarrierId());
			if (containerPK.getFlightSequenceNumber() > 0) {
				uldInFlightVO.setFlightNumber(containerPK.getFlightNumber());
				uldInFlightVO.setFlightSequenceNumber(containerPK
						.getFlightSequenceNumber());
				uldInFlightVO.setLegSerialNumber(containerPK
						.getLegSerialNumber());
			}
			uldInFlightVO.setFlightDirection(MailConstantsVO.OPERATION_INBOUND);

			Collection<UldInFlightVO> operationalUlds = new ArrayList<UldInFlightVO>();
			operationalUlds.add(uldInFlightVO);

			boolean isOprUldEnabled = MailConstantsVO.FLAG_YES
					.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD));
			if (isOprUldEnabled) {
				if (operationalUlds != null && operationalUlds.size() > 0) {
					new OperationsFltHandlingProxy()
							.saveOperationalULDsInFlight(operationalUlds);
				}
			}
		}
		destAssignedContainer.remove();
		log.log(Log.FINE, "removed con");
		log.exiting("ReassignController", "removeContainerAcceptanceForMailbag");
	}

	/**
	 * This method is used to reassign the mails from Destination.. Group the
	 * MailBags say U1-ARP U2-ARP U3-ARP A-1936
	 *
	 * @param destinationAssignedMailbags
	 * @throws SystemException
	 */
	public void reassignMailFromDestination(
			Collection<MailbagVO> destinationAssignedMailbags)
			throws SystemException {
		log.entering("ReassignController", "reassignMailFromDestination");
		Map<ULDAtAirportPK, Collection<MailbagVO>> uldAtAirportMap = new HashMap<ULDAtAirportPK, Collection<MailbagVO>>();
		Collection<MailbagVO> mailbagVos = null;
		if (destinationAssignedMailbags != null
				&& destinationAssignedMailbags.size() > 0) {
			for (MailbagVO mailbag : destinationAssignedMailbags) {
				// if(mailbag.getUldNumber()!=null){
				ULDAtAirportPK uldAtAirportPK = constructULDAtAirportPK(mailbag);
				mailbagVos = uldAtAirportMap.get(uldAtAirportPK);
				if (mailbagVos == null) {
					mailbagVos = new ArrayList<MailbagVO>();
					uldAtAirportMap.put(uldAtAirportPK, mailbagVos);
				}
				mailbagVos.add(mailbag);
				// }
			}
			try {
				for (ULDAtAirportPK uldAtAirportPK : uldAtAirportMap.keySet()) {

					Collection<MailbagVO> mailbagVosFromMap = uldAtAirportMap
							.get(uldAtAirportPK);
					if (uldAtAirportPK.getUldNumber() != null) {
						ULDAtAirport uldAtAirport = ULDAtAirport
								.find(uldAtAirportPK);

						/*uldAtAirport.setNumberOfBags(uldAtAirport
								.getNumberOfBags() - mailbagVosFromMap.size());
						uldAtAirport.setTotalWeight(uldAtAirport
								.getTotalWeight()
								- calculateWeightofBags(mailbagVosFromMap));*/

						uldAtAirport
								.reassignMailFromDestination(mailbagVosFromMap);

						//Added and then removed due to performance issue by A-6991 for ICRD-259413 
						//if(uldAtAirport.getMailbagInULDAtAirports() == null || uldAtAirport.getMailbagInULDAtAirports().size()==0){
						
						/*if (uldAtAirport.getNumberOfBags() == 0) {

						
							log.log(Log.FINE, "REMOVING THE ULDATAIRORT");
							// Changed as part of bug ICRD-110575 by A-5526
							// starts
							// uldAtAirport.remove();
							// Changed as part of bug ICRD-110575 by A-5526 ends
							// uldAtAirport.remove();
							log.log(Log.FINE,
									"uldArp for bulk removed coz cnt 0");
							
							 * BULK container removal should happen along with
							 * the ULDAtAirport, if the flow is coming as a part
							 * of Transfer. Else the BULK will has beed already
							 * removed as a part of DSNInULDAtAirport removal.
							 
							// Changed as part of bug ICRD-110575 by A-5526
							// starts
							if ((uldAtAirport.getUldAtAirportPK()
									.getUldNumber()
									.startsWith(MailConstantsVO.CONST_BULK_ARR_ARP))
							
							 * ||
							 * !(uldAtAirport.getUldAtAirportPK().getUldNumber()
							 * .startsWith(MailConstantsVO.CONST_BULK))
							 
							) { // Changed as part of bug ICRD-110575 by A-5526
								// starts
								// Bulk Container was already removed in
								// DSNInULDAtAirport
								removeContainerAcceptanceForMailbag(
										uldAtAirportPK, mailbagVosFromMap);
							}
						}*/
					}
				}
			} catch (FinderException ex) {
				log.log(Log.INFO, "DATA INCONSISTENT ULDATAIRPORT NOT FOUND}");
				//throw new SystemException(ex.getErrorCode(), ex);
			}
		}
		log.exiting("ReassignController", "reassignMailFromDestination");
	}

	/**
	 * @author a-1936 This method is used to reassign the mailbags From
	 *         Destination to Destination
	 * @param mailbags
	 * @param toContainerVO
	 * @throws SystemException
	 */
	public void reassignMailFromDestnToDestn(Collection<MailbagVO> mailbags,
			ContainerVO toContainerVO) throws SystemException {
		log.entering("ReassignController",
				"reassignMailFromDestinationToDestination");
		String sysParForInvetory = "mailtracking.defaults.inventoryenabled";
		boolean isInventory = MailConstantsVO.FLAG_YES
				.equals(findSystemParameterValue(sysParForInvetory));
		
		for(MailbagVO mailbagVO:mailbags){        
			if(mailbagVO.getUldNumber()==null){
				mailbagVO.setUldNumber(toContainerVO.getContainerNumber())	;
			}            
		}
		
		reassignMailFromDestination(mailbags);
		reassignMailToDestination(mailbags, toContainerVO);
		updateMailbagsForReassign(mailbags, toContainerVO);
		updateContainerAcceptance(toContainerVO);
		log.log(Log.FINE, "IS INVNETORY ENABLED -- TRUE ", isInventory);
		
		  if(!isInventory){ flagResditsForMailbagReassign(mailbags,
		  toContainerVO); }
		String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		if (Objects.nonNull(importEnabled) && !importEnabled.isEmpty() && importEnabled.contains("D")) {
			new MailController().reImportPABuiltMailbagsToMRA(mailbags);
		}
		//importMRAData
		boolean provisionalRateImport = false;
        Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(toContainerVO,mailbags,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport) ;
        log.log(Log.FINEST, "RateAuditVO-->", rateAuditVOs);
        if(rateAuditVOs!=null && !rateAuditVOs.isEmpty()){
        	
	        	if(importEnabled!=null && importEnabled.contains("M")){
        try {
			new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		}
        }
     // import Provisonal rate Data to malmraproint for upront rate Calculation
		MailController mailController = (MailController)SpringAdapter.getInstance().getBean(MAIL_CONTROLLER);
		String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if(MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
			provisionalRateImport = true;
      	Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toContainerVO,mailbags,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,provisionalRateImport) ;
      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
        try {
        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		}
        }
		log.exiting("ReassignController",
				"reassignMailFromDestinationToDestination");
	}

	private AssignedFlightSegment findAssignedFlightSegment(String companyCode,
			int carrierId, String flightNumber, long flightSequenceNumber,
			int segmentSerialNumber) throws SystemException, FinderException {

		AssignedFlightSegmentPK assignedFlightSegPK = new AssignedFlightSegmentPK();
		assignedFlightSegPK.setCompanyCode(companyCode);
		assignedFlightSegPK.setCarrierId(carrierId);
		assignedFlightSegPK.setFlightNumber(flightNumber);
		assignedFlightSegPK.setFlightSequenceNumber(flightSequenceNumber);
		assignedFlightSegPK.setSegmentSerialNumber(segmentSerialNumber);

		return AssignedFlightSegment.find(assignedFlightSegPK);
	}

	public void reassignMailToFlight(Collection<MailbagVO> mailbags,
			ContainerVO toContainerVO) throws SystemException {
		log.entering("ReassignController", "reassignMailToFlight");
		/**
		 * Added these check so wen the object is empty mailBags collection
		 * assigned Flight Find Need not Happen
		 *
		 */
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		AssignedFlightSegmentVO assignedFlightSegmentVO = new AssignedFlightSegmentVO();
		if(toContainerVO.isOflToRsnFlag()){
			assignedFlightSegmentVO.setCompanyCode(toContainerVO.getCompanyCode());
			assignedFlightSegmentVO.setCarrierId(toContainerVO.getCarrierId());
			assignedFlightSegmentVO.setFlightNumber(toContainerVO.getFlightNumber());
			assignedFlightSegmentVO.setFlightSequenceNumber(toContainerVO.getFlightSequenceNumber());
			assignedFlightSegmentVO.setSegmentSerialNumber(toContainerVO.getSegmentSerialNumber());
			assignedFlightSegmentVO.setPol(logonAttributes.getAirportCode());
			assignedFlightSegmentVO.setPou(toContainerVO.getPou());
			//To create MALFLT if required
			//Added as part of bug ICRD-334352 by A-5526 (mail as an AWB case only) starts
			OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
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
				throw new SystemException(e.getMessage(), e);
		}
			//Added as part of bug ICRD-334352 by A-5526 (mail as an AWB case only) ends
		}

		if (mailbags != null && mailbags.size() > 0) {
			AssignedFlightSegment assignedFlightSegment = null;
			try {
				assignedFlightSegment = findAssignedFlightSegment(
						toContainerVO.getCompanyCode(),
						toContainerVO.getCarrierId(),
						toContainerVO.getFlightNumber(),
						toContainerVO.getFlightSequenceNumber(),
						toContainerVO.getSegmentSerialNumber());
			} catch (FinderException exception) {
				if(toContainerVO.isOflToRsnFlag()){
					assignedFlightSegment = new AssignedFlightSegment(assignedFlightSegmentVO);
				}else{
				throw new SystemException(exception.getMessage(), exception);
				}
			}
			assignedFlightSegment.reassignMailToFlight(mailbags, toContainerVO);
		}
		log.exiting("ReassignController", "reassignMailToFlight");
	}

	/**
	 * @param mailbags
	 * @param toContainerVO
	 * @throws SystemException 
	 */
	private void updateMailbagVOsForResdit(Collection<MailbagVO> mailbags,
			ContainerVO toContainerVO) throws SystemException {
		for (MailbagVO mailbagVO : mailbags) {
			mailbagVO.setCarrierCode(toContainerVO.getCarrierCode());
			mailbagVO.setCarrierId(toContainerVO.getCarrierId());
			mailbagVO.setFlightNumber(toContainerVO.getFlightNumber());
			mailbagVO.setFlightSequenceNumber(toContainerVO
					.getFlightSequenceNumber());
			mailbagVO.setSegmentSerialNumber(toContainerVO
					.getSegmentSerialNumber());
			mailbagVO.setUldNumber(toContainerVO.getContainerNumber());
			mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
		//added by A-8353  for ICRD-346644
		}
	}

	/**
	 * Flags the resdits for all mailbags which were reassigned. Before calling
	 * the resditcontroller, the method updates the mailbags with the required
	 * details for RESDIT A-1739
	 *
	 * @param mailbags
	 * @param toContainerVO
	 * @throws SystemException
	 */
	private void flagResditsForMailbagReassign(Collection<MailbagVO> mailbags,
			ContainerVO toContainerVO) throws SystemException {
		log.exiting("ReassignController", "flagResditsForMailbagReassign");
		Collection<MailbagVO> mailbagVOsForResdit = new ArrayList<>();
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
			populateMailbagVOsForResdit(mailbags,mailbagVOsForResdit);
			updateMailbagVOsForResdit(mailbagVOsForResdit, toContainerVO);
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
			mailController.flagResditsForMailbagReassign(mailbagVOsForResdit,
					toContainerVO);
			/*new ResditController().flagResditsForMailbagReassign(mailbags,
					toContainerVO);*/
			log.exiting("ReassignController", "flagResditsForMailbagReassign");
		}
	}

	/**
	 *
	 * @param mailbags
	 * @param toContainerVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ContainerDetailsVO> reassignMailFromFlightToFlight(
			Collection<MailbagVO> mailbags, ContainerVO toContainerVO)
			throws SystemException {
		log.entering("ReassignController", "reassignMailbagsFlightToFlight");
		// remove Assignment from flight
		Collection<ContainerDetailsVO> emptyContainers = reassignMailFromFlight(mailbags);

		// assign to the next flight and populate dsnairportmap
		reassignMailToFlight(mailbags, toContainerVO);

		updateMailbagsForReassign(mailbags, toContainerVO);
		updateContainerAcceptance(toContainerVO);

		flagResditsForMailbagReassign(mailbags, toContainerVO);
		String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		if (Objects.nonNull(importEnabled) && !importEnabled.isEmpty() && importEnabled.contains("D")) {
			new MailController().reImportPABuiltMailbagsToMRA(mailbags);
		} 
		//importMRAData
		boolean provisionalRateImport = false;
        Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(toContainerVO,mailbags,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport) ;
        log.log(Log.FINEST, "RateAuditVO-->", rateAuditVOs);
        if(rateAuditVOs!=null && !rateAuditVOs.isEmpty()){
        	
	        	if(importEnabled!=null && importEnabled.contains("M")){
        try {
			new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		}
        }
     // import Provisonal rate Data to malmraproint for upront rate Calculation
        MailController mailController = (MailController)SpringAdapter.getInstance().getBean(MAIL_CONTROLLER);
		String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if(MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
			provisionalRateImport = true;
      	Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toContainerVO,mailbags,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,provisionalRateImport) ;
      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
        try {
        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		}
        }
		log.exiting("ReassignController", "reassignMailbagsFlightToFlight");
		return emptyContainers;
	}

	/**
	 * Reassigns mailbags Sep 12, 2007, a-1739 Revision 2 Included chks for
	 * transferred mailbags
	 *
	 * @param mailbagVOs
	 * @param toContainerVO
	 * @return
	 * @throws SystemException
	 * @throws FlightClosedException
	 * @throws ReassignmentException
	 * @throws InvalidFlightSegmentException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	@Raise(module = "mail", submodule = "operations", event = "SAVE_DWS_EVENT", methodId = "mail.operations.reassignMailbags")
	public Collection<ContainerDetailsVO> reassignMailbags(
			Collection<MailbagVO> mailbagsToReassign, ContainerVO toContainerVO)
			throws SystemException, FlightClosedException {
		log.entering("ReassignController", "reassignMailbags");
		Collection<ContainerDetailsVO> emptyContainers = null;
		if (mailbagsToReassign != null && mailbagsToReassign.size() > 0) {
			Collection<MailbagVO> flightAssignedMailbags = new ArrayList<MailbagVO>();
			Collection<MailbagVO> destAssignedMailbags = new ArrayList<MailbagVO>();
			if (isReassignableMailbags(mailbagsToReassign,
					flightAssignedMailbags, destAssignedMailbags)) {
				if (toContainerVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
					if (flightAssignedMailbags != null
							&& flightAssignedMailbags.size() > 0) {
						// REASSIGNING MAILBAGS FROM FLIGHT TO DESTINATION
						emptyContainers = reassignMailFromFlightToDestination(
								flightAssignedMailbags, toContainerVO, false);
					}
					if (destAssignedMailbags != null
							&& destAssignedMailbags.size() > 0) {
						// REASSIGNING MAILBAGS FROM DESTINATION TO DESTINATION
						reassignMailFromDestnToDestn(destAssignedMailbags,
								toContainerVO);
					}
				} else {
					if (flightAssignedMailbags != null
							&& flightAssignedMailbags.size() > 0) {
						// REASSIGNING MAILBAGS FROM FLIGHT TO FLIGHT
						emptyContainers = reassignMailFromFlightToFlight(
								flightAssignedMailbags, toContainerVO);
					}
					if (destAssignedMailbags != null
							&& destAssignedMailbags.size() > 0) {
						// REASSIGNING MAILBAGS FROM DESTINATION TO FLIGHT
						reassignMailFromDestinationToFlight(
								destAssignedMailbags, toContainerVO);
					}
				}
			}
		}
		log.exiting("ReassignController", "reassignMailbags");
		return emptyContainers;
	}

	/**
	 * A-1739
	 *
	 * @param destAssignedMailbags
	 * @param toContainerVO
	 * @throws SystemException
	 */
	private void reassignMailFromDestinationToFlight(
			Collection<MailbagVO> destAssignedMailbags,
			ContainerVO toContainerVO) throws SystemException {
		log.entering("ReassignController",
				"reassignMailFromDestinationToFlight");

		reassignMailFromDestination(destAssignedMailbags);
		// assign to the next flight and populate dsnairportmap

		reassignMailToFlight(destAssignedMailbags, toContainerVO);
		updateMailbagsForReassign(destAssignedMailbags, toContainerVO);
		// updateDSNAtAirport(dsnAtAirportMap);
		updateContainerAcceptance(toContainerVO);
		flagResditsForMailbagReassign(destAssignedMailbags, toContainerVO);
		String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		Collection<MailbagVO> mailbagsToImport = new MailController().validateAndReImportMailbagsToMRA(destAssignedMailbags);		
		//importMRAData
		boolean provisionalRateImport = false;
        Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(toContainerVO,mailbagsToImport,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport) ;
        log.log(Log.FINEST, "RateAuditVO-->", rateAuditVOs);
        if(rateAuditVOs!=null && !rateAuditVOs.isEmpty()){
	        	if(importEnabled!=null && importEnabled.contains("M")){
        try {
			new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		}
        }
     // import Provisonal rate Data to malmraproint for upront rate Calculation
		MailController mailController = (MailController)SpringAdapter.getInstance().getBean(MAIL_CONTROLLER);
        String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
		if(MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
			provisionalRateImport = true;
      	Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toContainerVO,mailbagsToImport,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,provisionalRateImport) ;
      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
        try {
        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		}
        }
		log.exiting("ReassignController", "reassignMailFromDestinationToFlight");
	}

	private AssignedFlightPK constructAssignedFlightPK(
			OperationalFlightVO operationalFlightVO) {
		AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
		assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
		assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightPk.setFlightSequenceNumber(operationalFlightVO
				.getFlightSequenceNumber());
		assignedFlightPk.setLegSerialNumber(operationalFlightVO
				.getLegSerialNumber());
		assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
		return assignedFlightPk;
	}

	/**
	 * Validates an assignedFlight
	 *
	 * @param flightVO
	 * @return the operationalflightVO
	 * @throws SystemException
	 */
	private OperationalFlightVO validateAssignedFlight(
			OperationalFlightVO flightVO) throws SystemException {
		log.entering("ReassignController", "validateAssignedFlight");
		AssignedFlightPK flightPK = constructAssignedFlightPK(flightVO);
		try {
			AssignedFlight.find(flightPK);
			return flightVO;
		} catch (FinderException ex) {
			log.log(Log.FINE, "no assignedflight");
		}
		log.exiting("ReassignController", "validateAssignedFlight");
		return null;
	}

	/**
	 * A-1739
	 *
	 * @param operationalFlightVO
	 * @return
	 */
	private Object[] constructFltErrorData(
			OperationalFlightVO operationalFlightVO) {
		log.log(Log.FINE, " ", operationalFlightVO);
		return new String[] {
				new StringBuilder()
						.append(operationalFlightVO.getCarrierCode())
						.append(" ")
						.append(operationalFlightVO.getFlightNumber())
						.toString(),
				operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat() };
	}

	private OperationalFlightVO constructFlightVOForContainer(
			ContainerVO containerToReassign) {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO
				.setCompanyCode(containerToReassign.getCompanyCode());
		operationalFlightVO
				.setCarrierCode(containerToReassign.getCarrierCode());
		operationalFlightVO.setCarrierId(containerToReassign.getCarrierId());
		operationalFlightVO.setFlightNumber(containerToReassign
				.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(containerToReassign
				.getFlightSequenceNumber());
		operationalFlightVO.setLegSerialNumber(containerToReassign
				.getLegSerialNumber());
		operationalFlightVO.setPol(containerToReassign.getAssignedPort());
		operationalFlightVO.setFlightDate(containerToReassign.getFlightDate());
		return operationalFlightVO;
	}

	/**
	 * This method checks if the to and from flights are closed for operation.
	 * It also groups the containers into two depending on whether assigned to
	 * destination or whether assigned to a flight A-1739
	 *
	 * @param containersToReassign
	 * @param operationalFlightVO
	 * @param destAssignedContainers
	 * @param flightAssignedContainers
	 * @return true if all the containers are reassignable
	 * @throws SystemException
	 * @throws FlightClosedException
	 * @throws ContainerAssignmentException
	 */
	private boolean isReassignableContainers(
			Collection<ContainerVO> containersToReassign,
			OperationalFlightVO operationalFlightVO,
			Collection<ContainerVO> destAssignedContainers,
			Collection<ContainerVO> flightAssignedContainers)
			throws SystemException, FlightClosedException,
			ContainerAssignmentException {
		log.entering("ReassignController", "isReassignable");
		LocalDate GHTtime=null;
		if (operationalFlightVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT
				&&
				// if reassigned to a flight check for flight assignment and
				// status of the to flight
				validateAssignedFlight(operationalFlightVO) != null
				&& isFlightClosedForOperations(operationalFlightVO) && !(containersToReassign!=null && !containersToReassign.isEmpty() && MailConstantsVO.ONLOAD_MESSAGE.equals(containersToReassign.iterator().next().getMailSource()))) {
			throw new FlightClosedException(
					FlightClosedException.FLIGHT_STATUS_CLOSED,
					constructFltErrorData(operationalFlightVO));
		}
		if(containersToReassign!=null && !containersToReassign.isEmpty()){//IASCB-48967
        GHTtime=new ULDForSegment().findGHTForMailbags(operationalFlightVO);
		}

		if (containersToReassign != null && containersToReassign.size() > 0) {
			for (ContainerVO containerToReassign : containersToReassign) {
				
				containerToReassign.setGHTtime(GHTtime);//IASCB-48967
				
				//Modified by A-7794 as part of ICRD-226708
				//if(containerToReassign.getRemarks()==null)
				//containerToReassign.setRemarks(MailConstantsVO.MAIL_ULD_REASSIGN);
				if (containerToReassign.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
					destAssignedContainers.add(containerToReassign);
				} else {
					// if reassigned from a flight check for flight assignment
					// and status of the from flight
					OperationalFlightVO containerFlight = constructFlightVOForContainer(containerToReassign);
					if (!MailConstantsVO.ONLOAD_MESSAGE.equals(containerToReassign.getMailSource())&&isFlightClosedForOperations(containerFlight)) {
						throw new FlightClosedException(
								FlightClosedException.FLIGHT_STATUS_CLOSED,
								constructFltErrorData(containerFlight));
					}
					flightAssignedContainers.add(containerToReassign);
				}
			}
		}
		log.exiting("ReassignController", "isReassignable");
		return true;
	}

	private void groupULDsForReassignOrTsfr(Collection<ContainerVO> containers,
			Collection<ContainerVO> containersToTsfr,
			Collection<ContainerVO> containersToReassign,
			Collection<ContainerVO> containersToRem) throws SystemException,
			MailDefaultStorageUnitException {
		log.entering("ReassignController", "groupULDsForInventoryReassign");
		boolean isfromHHT = false;
		// ADDED for icrd-92446
		String storageUnit = findSystemParameterValue(DEFAULT_STORAGEUNITFORMAIL);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		for (ContainerVO containerVO : containers) {
			if (containerVO.getContainerNumber() != null && storageUnit != null
					&& !"".equals(storageUnit)
					&& (storageUnit.equals(containerVO.getContainerNumber()))) {
				throw new MailDefaultStorageUnitException(
						MailTrackingBusinessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
						null);
			}
			if (containerVO.getFlightSequenceNumber() > 0) {
				// not a container in inventory
				// it's either with destn or in a flight
				containersToReassign.add(containerVO);
			} else if (MailConstantsVO.BULK_TYPE.equals(containerVO.getType())) {
				// bulks won't need to be transferred TODO??
				containersToReassign.add(containerVO);
			} else if (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())) {
				ContainerAssignmentVO conAsgVO = Container
						.findArrivalDetailsForULD(containerVO);
				if (conAsgVO != null
						&& (conAsgVO.getFlightNumber() != null && conAsgVO
								.getFlightNumber().equals(
										containerVO.getFlightNumber()))
						&& conAsgVO.getFlightSequenceNumber() == containerVO
								.getFlightSequenceNumber()) {
					// Nothing to do further
				} else {
					// ULD was arrived in another flight.UlD is now reused to
					// carrier.
					conAsgVO = null;
				}
				if (conAsgVO == null) {
					containersToReassign.add(containerVO);
					log.log(Log.FINE, "THE CONTAINER ASSIGNMENT VO IS ",
							conAsgVO);
				} else if (ContainerVO.FLAG_YES.equals(conAsgVO
						.getArrivalFlag())
						&& !MailConstantsVO.FLAG_YES.equals(conAsgVO
								.getTransferFlag())) {
					ContainerAssignmentVO latestContainerAssignmentVO = new MailController()
							.findLatestContainerAssignment(containerVO
									.getContainerNumber());
					if ("WS".equals(containerVO.getSource())) {
						if (latestContainerAssignmentVO != null
								&& !"Y".equals(latestContainerAssignmentVO
										.getArrivalFlag())) {
							if (MailConstantsVO.DESTN_FLT == latestContainerAssignmentVO
									.getFlightSequenceNumber()) {
								containersToReassign.add(containerVO);
								isfromHHT = true;
							}
						}
					}
					if (!isfromHHT) {
						// adding containers for transfer
						log.log(Log.FINE,
								"Container Collected for the Transfer");
						ContainerVO containerToTsfr = new ContainerVO();
						containerToTsfr.setCompanyCode(containerVO
								.getCompanyCode());
						containerToTsfr.setContainerNumber(containerVO
								.getContainerNumber());
						// Changed by A-5945 for ICRD-96105
						containerToTsfr.setCarrierId(conAsgVO.getCarrierId());
						containerToTsfr.setCarrierCode(conAsgVO
								.getCarrierCode());
						containerToTsfr.setPou(containerVO.getAssignedPort());
						containerToTsfr.setType(containerVO.getType());
						containerToTsfr.setFlightNumber(conAsgVO
								.getFlightNumber());
						containerToTsfr.setFlightSequenceNumber(conAsgVO
								.getFlightSequenceNumber());
						containerToTsfr.setLegSerialNumber(conAsgVO
								.getLegSerialNumber());
						containerToTsfr.setSegmentSerialNumber(conAsgVO
								.getSegmentSerialNumber());
						containerToTsfr.setAssignedPort(conAsgVO
								.getAirportCode());
						containerToTsfr.setArrivedStatus(conAsgVO
								.getArrivalFlag());
						containerToTsfr.setTransferFlag(conAsgVO
								.getTransferFlag());
						containerToTsfr.setMailSource(containerVO
								.getMailSource());// Added for ICRD-156218

						// Edited for AirNZ BUG : 39041
						// containerToTsfr.setFinalDestination(conAsgVO.getDestination());
						containerToTsfr.setFinalDestination(containerVO
								.getFinalDestination());
						// END AirNZ BUG : 39041

						containersToTsfr.add(containerToTsfr);

						// adding containers to remove from inventory
						containersToRem.add(containerVO);
					}
				} else {
					/**
					 * Added by A-4809 as part of solving ICRD-91386 Fix done
					 * for issue ICRD-90918 if conAsgVO != null then conatiner
					 * vo to be added to containersToReassign
					 */
					containersToReassign.add(containerVO);
				}
			}
		}
		log.exiting("ReassignController", "groupULDsForInventoryReassign");

	}

	/**
	 * This method updates the ULDAtAirportVO with the new details A-1739
	 *
	 * @param uldAtAirportVO
	 * @param toDestinationVO
	 * @param assignedContainerVO
	 */
	public void updateULDAtAirportVO(ULDAtAirportVO uldAtAirportVO,
			OperationalFlightVO toDestinationVO, ContainerVO assignedContainerVO) {
		uldAtAirportVO.setCompanyCode(toDestinationVO.getCompanyCode());
		uldAtAirportVO.setCarrierId(toDestinationVO.getCarrierId());
		uldAtAirportVO.setCarrierCode(toDestinationVO.getCarrierCode());
		uldAtAirportVO.setAirportCode(toDestinationVO.getPol());
		uldAtAirportVO.setFinalDestination(assignedContainerVO
				.getFinalDestination());
		//Added by A-8893 for IASCB-38903 starts
		if(assignedContainerVO.isUldTobarrow()){
			String fromULDNumber = constructBulkULDNumber(
					assignedContainerVO.getFinalDestination(),
					assignedContainerVO.getCarrierCode());
			
			uldAtAirportVO.setUldNumber(fromULDNumber);
		}
		if(assignedContainerVO.isBarrowToUld()){
			uldAtAirportVO.setUldNumber(assignedContainerVO.getContainerNumber());
		}
		
		//Added by A-8893 for IASCB-38903 ends
		if(uldAtAirportVO.getMailbagInULDAtAirportVOs()!=null&&!uldAtAirportVO.getMailbagInULDAtAirportVOs().isEmpty()){
			uldAtAirportVO.getMailbagInULDAtAirportVOs().forEach(mailbagInULDAtAirport->{
				mailbagInULDAtAirport.setCarrierId(toDestinationVO.getCarrierId());
				mailbagInULDAtAirport.setMailSource(assignedContainerVO.getMailSource());
			});
		}
		if (!MailConstantsVO.BULK_TYPE.equals(assignedContainerVO.getType())) {
			uldAtAirportVO.setRemarks(assignedContainerVO.getRemarks());
		} else {
			Collection<DSNInULDAtAirportVO> dsnsInULD = uldAtAirportVO
					.getDsnInULDAtAirportVOs();
			if (dsnsInULD != null) {
				int totalbags = 0;
				double totalweight = 0;
				for (DSNInULDAtAirportVO dsnInULD : dsnsInULD) {
					totalbags += dsnInULD.getAcceptedBags();
					//totalweight += dsnInULD.getAcceptedWeight();
					totalweight += dsnInULD.getAcceptedWeight().getRoundedSystemValue();//added by A-7371
					Collection<DSNInContainerAtAirportVO> dsnsInCon = dsnInULD
							.getDsnInContainerAtAirports();
					if (dsnsInCon != null) {
						for (DSNInContainerAtAirportVO dsnInCon : dsnsInCon) {
							if (dsnInCon.getContainerNumber().equals(
									assignedContainerVO.getContainerNumber())) {
								dsnInCon.setRemarks(assignedContainerVO
										.getRemarks());
							}
						}
					}
				}
				uldAtAirportVO.setNumberOfBags(totalbags);
				//uldAtAirportVO.setTotalWeight(totalweight);
				uldAtAirportVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,totalweight));//added by A-7371
			}
		}
	}

	/**
	 * A-1739
	 *
	 * @param destAssignedContainerVO
	 * @param fromULDNumber
	 * @return
	 */
	private ULDAtAirportPK constructULDArpPKFromContainer(
			ContainerVO destAssignedContainerVO, String fromULDNumber) {
		ULDAtAirportPK uldArpPK = new ULDAtAirportPK();
		uldArpPK.setCompanyCode(destAssignedContainerVO.getCompanyCode());
		uldArpPK.setCarrierId(destAssignedContainerVO.getCarrierId());
		uldArpPK.setAirportCode(destAssignedContainerVO.getAssignedPort());
		uldArpPK.setUldNumber(fromULDNumber);
		return uldArpPK;
	}

	/**
	 * This method is used to reassign a container from 1 destn to another
	 * A-1739
	 ****************************************************************
	 ********** isRemOnly needs to checked before using toDestinationVO******** isRemOnly
	 * needs to checked before using toDestinationVO*
	 ****************************************************************
	 ****************************************************************
	 * @param destAssignedContainers
	 * @param toDestinationVO
	 * @throws SystemException
	 */
	public Collection<ContainerVO> reassignContainerFromDestToDest(
			Collection<ContainerVO> destAssignedContainers,
			OperationalFlightVO toDestinationVO) throws SystemException {

		log.entering("ReassignController", "reassignFromDestToDest");
		/*
		 * FLag to check whether only reassignFromDest alone * A-1739
		 * ***************************************************************
		 * *********isRemOnly needs to checked before using
		 * toDestinationVO************************isRemOnly needs to checked
		 * before using toDestinationVO*
		 * ***************************************************************
		 * ***************************************************************
		 */
		boolean isRemOnly = false;
		int numberOfBarrowspresent = 0;
		if (toDestinationVO == null) {
			isRemOnly = true;
		}
		Collection<ContainerVO> paBuiltContainers = new ArrayList<ContainerVO>();
		Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>();
		// Collection<DSNVO> dsnToFlag = new ArrayList<DSNVO>();
		for (ContainerVO destAssignedContainerVO : destAssignedContainers) {
			boolean isNotAccepted = true;
			boolean isReassignSuccess = false;
			Collection<MailbagVO> mailbagToFlag = new ArrayList<MailbagVO>();
			
			if (CONST_YES.equals(destAssignedContainerVO.getAcceptanceFlag())) {
				isNotAccepted = false;
				String fromULDNumber = destAssignedContainerVO
						.getContainerNumber();
				ULDAtAirport uldAtAirport1 = null;
//				if(destAssignedContainerVO.isUldTobarrow()){
//					fromULDNumber = constructBulkULDNumber(
//							destAssignedContainerVO.getFinalDestination(),
//							destAssignedContainerVO.getCarrierCode());	
//				}
				if (MailConstantsVO.BULK_TYPE.equals(destAssignedContainerVO
						.getType())) {
					//Added by a-7779 for iascb-39312 starts
					if(destAssignedContainerVO.isContainerDestChanged()){
						ContainerPK containerPk = new ContainerPK();
						containerPk.setCompanyCode(destAssignedContainerVO.getCompanyCode());
						containerPk
								.setContainerNumber(destAssignedContainerVO.getContainerNumber());
						containerPk.setCarrierId(destAssignedContainerVO.getCarrierId());
						containerPk.setFlightNumber(destAssignedContainerVO.getFlightNumber());
						containerPk.setFlightSequenceNumber(destAssignedContainerVO
								.getFlightSequenceNumber());
						containerPk.setAssignmentPort(destAssignedContainerVO.getAssignedPort());
						containerPk
								.setLegSerialNumber(destAssignedContainerVO.getLegSerialNumber());
						Container prevContainer = null;
						try {
							prevContainer = Container.find(containerPk);
						} catch (FinderException finderException) {
							/*throw new SystemException(finderException.getMessage(),
									finderException);*/
							}
						if(prevContainer!=null){
					fromULDNumber = constructBulkULDNumber(
								prevContainer.getFinalDestination(),
								destAssignedContainerVO.getCarrierCode());
						}////Added by a-7779 for iascb-39312 ends
					}else
					fromULDNumber = constructBulkULDNumber(
							destAssignedContainerVO.getFinalDestination(),
							destAssignedContainerVO.getCarrierCode());
				}

				ULDAtAirport uldAtAirport = null;
				try {
					uldAtAirport = ULDAtAirport
							.find(constructULDArpPKFromContainer(
									destAssignedContainerVO, fromULDNumber));
				} catch (FinderException finderException) {
					/*throw new SystemException("No such container in this port",
							finderException);*/
					if(!destAssignedContainerVO.isContainerDestChanged()){
					return null;
				}
				}

				Collection<MailbagInULDAtAirportVO> dsnsToReassign = null;
				String containerNumber = destAssignedContainerVO
						.getContainerNumber();
				ULDAtAirportVO uldAtAirportVO = null;

				if (MailConstantsVO.ULD_TYPE.equals(destAssignedContainerVO
						.getType())) {
					//Added by A-8893 for IASCB-38903 starts
					if(destAssignedContainerVO.isUldTobarrow()){
					dsnsToReassign = uldAtAirport
							.reassignBulkContainer(containerNumber);
					}
					//Added by A-8893 for IASCB-38903 ends
					uldAtAirportVO = uldAtAirport.retrieveVO();
					uldAtAirport.remove();

					if (!isRemOnly) {
						updateULDAtAirportVO(uldAtAirportVO, toDestinationVO,
								destAssignedContainerVO);
						//Added by A-8893 for IASCB-38903 starts
						if(destAssignedContainerVO.isUldTobarrow()){
						try {
							uldAtAirport = ULDAtAirport
									.find(constructULDArpPKFromULDArpVO(
											uldAtAirportVO));
							
							uldAtAirport.assignBulkContainer(dsnsToReassign);
						
						} catch (FinderException finderException) {
							/*throw new SystemException("No such container in this port",
									finderException);*/
							new ULDAtAirport(uldAtAirportVO);
						}
						}
						//Added by A-8893 for IASCB-38903 ends
						else{
							new ULDAtAirport(uldAtAirportVO);
						}
						log.log(Log.FINEST, "retrieved VO -->", uldAtAirportVO);
						
						isReassignSuccess = true;
					}
				} else if (MailConstantsVO.BULK_TYPE
						.equals(destAssignedContainerVO.getType())) {

					String toULDNumber = constructBulkULDNumber(
							destAssignedContainerVO.getFinalDestination(),
							destAssignedContainerVO.getCarrierCode());
					if(uldAtAirport!=null){
					dsnsToReassign = uldAtAirport
							.reassignBulkContainer(containerNumber);
					
					
					}
					log.log(Log.FINEST, "retrieved  -->", dsnsToReassign);
					try {
						if(destAssignedContainerVO.isBarrowToUld()){
							uldAtAirportVO = new ULDAtAirportVO();
							uldAtAirportVO.setUldNumber(destAssignedContainerVO
									.getContainerNumber());
							uldAtAirportVO.setMailbagInULDAtAirportVOs(dsnsToReassign);
							updateULDAtAirportVO(uldAtAirportVO, toDestinationVO,
									destAssignedContainerVO);
							log.log(Log.FINEST, "retrieved uld for bulk -->",
									uldAtAirportVO);
							new ULDAtAirport(uldAtAirportVO);
							isReassignSuccess = true;
						}
						else{
						ULDAtAirport toULDAtAirport = ULDAtAirport
								.find(constructULDArpPKFromOpFlt(
										toDestinationVO, toULDNumber));

						toULDAtAirport.assignBulkContainer(dsnsToReassign);
						

						isReassignSuccess = true;
						}
					} catch (FinderException exception) {
						uldAtAirportVO = new ULDAtAirportVO();
						uldAtAirportVO.setUldNumber(toULDNumber);
						uldAtAirportVO.setMailbagInULDAtAirportVOs(dsnsToReassign);
						updateULDAtAirportVO(uldAtAirportVO, toDestinationVO,
								destAssignedContainerVO);

						log.log(Log.FINEST, "retrieved uld for bulk -->",
								uldAtAirportVO);
						new ULDAtAirport(uldAtAirportVO);
						isReassignSuccess = true;
					}
				}
				if (isReassignSuccess) {
					if (dsnsToReassign == null&& uldAtAirportVO.getMailbagInULDAtAirportVOs()!=null) {
						dsnsToReassign = uldAtAirportVO.getMailbagInULDAtAirportVOs();
					}
					if (dsnsToReassign!=null&& !dsnsToReassign.isEmpty()) {  
						Collection<MailbagVO> mailbagVOs = constructMailbagVOsFromAirport(
								dsnsToReassign,   
								toDestinationVO.getCompanyCode());
						updateDSNForConReassign(mailbagVOs, toDestinationVO,
								destAssignedContainerVO,
								MailConstantsVO.DESTN_FLT, null);
						/*
						 * updateDSNSegment(dsnVOs, destAssignedContainerVO,
						 * toDestinationVO, MailConstantsVO.DESTN_FLT, null,
						 * null);
						 */

						mailbagToFlag.addAll(mailbagVOs);

					}
					MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					mailController.flagHistoryForContainerReassignment(toDestinationVO,destAssignedContainerVO,mailbagToFlag);
					mailController.flagAuditForContainerReassignment(toDestinationVO,destAssignedContainerVO,mailbagToFlag);
					//importMRAData
					boolean provisionalRateImport =false;
					Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(toDestinationVO ,destAssignedContainerVO,mailbagToFlag,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport) ;
					log.log(Log.FINEST, "RateAuditVO-->", rateAuditVOs);
					 if(rateAuditVOs!=null && !rateAuditVOs.isEmpty()){
						 String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		  		        	if(importEnabled!=null && importEnabled.contains("M")){
			        try {
						new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
					} catch (ProxyException e) {
						throw new SystemException(e.getMessage(), e);
					}
					}
					 }
					// import Provisonal rate Data to malmraproint for upront rate Calculation
						String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
						if(MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
							provisionalRateImport = true;
				      	Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toDestinationVO ,destAssignedContainerVO,mailbagToFlag,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,provisionalRateImport) ;
				          log.log(Log.FINEST, "ProvisionalRateAuditVO-->", provisionalRateAuditVOs);  
				      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
				        try {
				        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
					} catch (ProxyException e) {
						throw new SystemException(e.getMessage(), e);
					}
					}
					 }
				}
			}

			/*
			 * Added By Karthick V a-1936 To incoporate the Usage of this Method
			 * for the ReassignFromDestination alone with the Usage of the Flag
			 * isRemOnly ..which will be the case when we reassign the
			 * containers from the Destination to the Flight.. Note:- Important
			 * to understand the significance of these Flags..
			 * 1.isReassignSucess --- Normal Resassign Flow From Destination to
			 * Destination 2.isNotAccepted --- Flag to skip the Acceptance Flows
			 * 3.isRemOnly ---- will be True wen the call is From the Transfer
			 * Containers From the Carrier. where reassign Containers
			 * FromDestToDest is called Internally ..
			 */

			if (isReassignSuccess || isNotAccepted || isRemOnly) {
				updateReassignedContainer(destAssignedContainerVO,
						toDestinationVO, MailConstantsVO.DESTN_FLT);
				if (isReassignSuccess) {
					if (MailConstantsVO.FLAG_YES.equals(destAssignedContainerVO
							.getPaBuiltFlag())) {
						paBuiltContainers.add(destAssignedContainerVO);
					}
				}
			}
			if (!isRemOnly) {
				ContainerVO containerReturnVO = new ContainerVO();
				BeanHelper.copyProperties(containerReturnVO,
						destAssignedContainerVO);
				containerReturnVO.setFlightSequenceNumber(toDestinationVO
						.getFlightSequenceNumber());
				containerReturnVO.setFlightNumber(toDestinationVO
						.getFlightNumber());
				containerReturnVO.setLegSerialNumber(toDestinationVO
						.getLegSerialNumber());
				// containerReturnVO.setSegmentSerialNumber(toFlightSegSerialNo);
				containerReturnVO.setCarrierCode(toDestinationVO
						.getCarrierCode());
				containerReturnVO
						.setFlightDate(toDestinationVO.getFlightDate());
				containersForReturn.add(containerReturnVO);
			}
			if (paBuiltContainers.size() > 0 || mailbagToFlag.size() > 0) {
				flagResditsForContainerReassign(mailbagToFlag, paBuiltContainers,
						toDestinationVO, MailConstantsVO.DESTN_FLT);
			}
		}
		
		log.exiting("ReassignController", "reassignFromDestToDest");
		return containersForReturn;
	}

	/**
	 * Flags the RESDITs related to a ULD reassign
	 *
	 * @param dsnsForResdit
	 * @param paBuiltContainers
	 * @param toFlightVO
	 * @param segmentSerialNumber
	 * @throws SystemExceptionR
	 */
	private void flagResditsForContainerReassign(
			Collection<MailbagVO> mailbagVOs,
			Collection<ContainerVO> paBuiltContainers,
			OperationalFlightVO toFlightVO, int segmentSerialNumber)
			throws SystemException {
		String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
			log.entering("ReassignController",
					"flagResditsForContainerReassign");
			// Collection<MailbagVO> mailbagVOs =
			// mergeMailbagsofDSNs(dsnsForResdit);

			Collection<ContainerDetailsVO> flightUpdatedContainers = updateFlightOfReassignedContainers(
					paBuiltContainers, toFlightVO, segmentSerialNumber);

			boolean hasFlightDeparted = MailConstantsVO.FLIGHT_STATUS_DEPARTED
					.equals(toFlightVO.getFlightStatus())||toFlightVO.isAtdCaptured();
			

			/*
			 * if (toFlightVO.getFlightSequenceNumber() !=
			 * MailConstantsVO.DESTN_FLT) { FlightValidationVO
			 * flightValidationVO = validateOperationalFlight(toFlightVO,
			 * false); if (MailConstantsVO.FLIGHT_STATUS_DEPARTED
			 * .equals(flightValidationVO.getLegStatus())) { hasFlightDeparted =
			 * true; } }
			 */
			if(toFlightVO.getFlightNumber() != null && !"-1".equals(toFlightVO.getFlightNumber())){
				for(MailbagVO mailVO : mailbagVOs){
					mailVO.setFlightNumber(toFlightVO.getFlightNumber());
					mailVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
					mailVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
					mailVO.setFlightDate(toFlightVO.getFlightDate());
					mailVO.setSegmentSerialNumber(toFlightVO.getSegSerNum());
				}
			}
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
			mailController.flagResditForContainerReassign(mailbagVOs,
					flightUpdatedContainers, toFlightVO, hasFlightDeparted);
			/*new ResditController().flagResditForContainerReassign(mailbagVOs,
					flightUpdatedContainers, toFlightVO, hasFlightDeparted);*/
		}
	}

	/**
	 * This method updates the MALBAGMST with the latest flight details. There
	 * is only one dummy mailbagvo in the dsnvo given to the DSN entity This is
	 * because All the mailbags in that entity are to be updated with the same
	 * flight details. It also find the mailbags in the DSN for flagging RESDITs
	 * for all those receptacles These mailbags are used in the
	 * reassignflighttodest case. A-1739
	 *
	 * @param dsnVOs
	 * @param toFlightVO
	 * @param containerVO
	 * @param toFlightSegmentSerialNum
	 * @throws SystemException
	 */
	private void updateDSNForConReassign(Collection<MailbagVO> mailbagVOs,
			OperationalFlightVO toFlightVO, ContainerVO containerVO,
			int toFlightSegmentSerialNum,
			Collection<String> mailIdsForMonitorSLAs) throws SystemException {
		log.entering("ReassignController", "updateDSN");

		boolean isFlightToDest = false;
		boolean canUpdateDSNArp = false;

		LogonAttributes logonAttributes = null;
		try {
			logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
		} catch (SystemException e) {
			log.log(Log.FINE, e.getMessage());
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
					mailbagPK.setMailSequenceNumber(mailbagvo
							.getMailSequenceNumber());
					mailbag = Mailbag.find(mailbagPK);
					if(mailbag!=null){
						mailbagvo.setMailbagId(mailbag.getMailIdr());
						mailbagvo.setOoe(mailbag.getOrginOfficeOfExchange());
						mailbagvo.setDoe(mailbag.getDestinationOfficeOfExchange());
						mailbagvo.setFlightNumber(mailbag.getFlightNumber());
						mailbagvo.setFlightSequenceNumber(mailbag.getFlightSequenceNumber());
						mailbagvo.setSegmentSerialNumber(mailbag.getSegmentSerialNumber());//ICRD-304364
						mailbagvo.setMailSubclass(mailbag.getMailSubClass());
						mailbagvo.setMailCategoryCode(mailbag.getMailCategory());
						mailbagvo.setUldNumber(mailbag.getUldNumber());
						mailbagvo.setPaCode(mailbag.getPaCode());//Added for IASCB-33505
						mailbagvo.setMailClass(mailbag.getMailClass());//Added for IASCB-33505
						mailbagvo.setScannedDate(
								new LocalDate(LocalDate.NO_STATION, Location.NONE, mailbag.getScannedDate(), true));
						AirlineValidationVO airlineValidationVO = null;
						if(mailbagvo.getCarrierCode()==null){
				    	    try{
				     	        	airlineValidationVO= new SharedAirlineProxy()
						     .findAirline(mailbagvo.getCompanyCode(), mailbag.getCarrierId());
				     	  }catch (SharedProxyException sharedProxyException) {
				     		sharedProxyException.getMessage();
						   } catch (SystemException ex) {
							ex.getMessage();
						   }
				    	    mailbagvo.setCarrierCode(airlineValidationVO.getAlphaCode());   
						}
						//Added by A-7540 for ICRD-346150
						mailbagvo.setCarrierId(mailbag.getCarrierId());
						try {
							mailbagvo = new MailController().constructOriginDestinationDetails(mailbagvo);
						} catch (SystemException e) {
							e.getMessage();
						}
						//Added for IASCB-33505 starts
						String serviceLevel = null;
						if (mailbag.getMailServiceLevel() == null) {
							try {
								serviceLevel = new MailController().findMailServiceLevel(mailbagvo);
								mailbag.setMailServiceLevel(serviceLevel);
							} catch (SystemException e) {
								log.log(Log.SEVERE, "System Exception Caught: "+e.getMessage());
							}
						}
						//Added for IASCB-33505 ends
					}


				}
			}

			updateMailbagFlightForReassignVos(toFlightVO, containerVO,
					toFlightSegmentSerialNum, mailbagVOs);
			// mailbags are fetched for flagging the resdits
			Collection<MailbagVO> mailbagVos = new ArrayList<MailbagVO>();
			// Added For CR : AirNZ683 - Irregularity Report
			Collection<MailbagVO> irregularMailbagVOs = new ArrayList<MailbagVO>();

			if (mailbag != null) {

				// update mailbags present only in this container
				if (containerVO.getContainerNumber().equals(
						mailbag.getUldNumber())) {
					MailbagVO mailbagVO = new MailbagVO();
					mailbagVO.setCompanyCode(mailbag.getMailbagPK()
							.getCompanyCode());
					mailbagVO.setMailSequenceNumber(mailbag.getMailbagPK()
							.getMailSequenceNumber());

					mailbagVO.setMailClass(mailbag.getMailClass());
					mailbagVO.setUldNumber(mailbag.getUldNumber());
					mailbagVO.setCarrierId(mailbag.getCarrierId());
					mailbagVO.setFlightNumber(mailbag.getFlightNumber());
					mailbagVO.setFlightSequenceNumber(mailbag
							.getFlightSequenceNumber());
					mailbagVO.setSegmentSerialNumber(mailbag
							.getSegmentSerialNumber());
					mailbagVO.setScannedDate(new LocalDate(mailbag
							.getScannedPort(), Location.ARP, true));
					if (mailbag.getScannedUser() != null) {
						mailbagVO.setScannedUser(mailbag.getScannedUser());
					} else {
						// Modified as part of Bug ICRD-144099 by A-5526
						mailbagVO.setScannedUser(logonAttributes.getUserId());
					}
					mailbagVO.setCarrierCode(toFlightVO.getCarrierCode());
					mailbagVos.add(mailbagVO);
					/*
					 * Added By Karthick V to fetch all the MailBags when a
					 * container is Offloaded From the Flight to Destination For
					 * Monitoring the Service Level Activity Only if it is
					 * Offload and the Reference Exists we need to collect the
					 * MailBags for MonitorSLa.. Else it is an Normal Reassign
					 * or the MonitorSLA is Disabled.
					 */
					if (containerVO.isOffload()
							&& mailIdsForMonitorSLAs != null) {
						mailIdsForMonitorSLAs.add(mailbag.getMailIdr());
					}

				}

				/*
				 * Added For CR : AirNZ683 - Irregularity report START
				 */
				if (irregularMailbagVOs.size() > 0) {
					ContainerVO toContainerVO = new ContainerVO();
					toContainerVO.setCarrierId(toFlightVO.getCarrierId());
					toContainerVO.setFlightNumber(toFlightVO.getFlightNumber());
					toContainerVO.setFlightSequenceNumber(toFlightVO
							.getFlightSequenceNumber());
					toContainerVO.setFlightDate(toFlightVO.getFlightDate());

					/*
					 * The below code is commented for Time being. Done as a
					 * work around. Need to be changed later
					 */

				}

				// END
			}

		} catch (FinderException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("ReassignController", "updateDSN");
	}
	/**
	 * @author A-1739
	 * @param toFlightVO
	 * @param containerVO
	 * @param toFlightSegmentSerialNum
	 * @throws SystemException
	 */
	public void updateMailbagFlightForReassignVos(OperationalFlightVO toFlightVO,
			ContainerVO containerVO, int toFlightSegmentSerialNum,
 Collection<MailbagVO> mailbagVOs)
			throws SystemException {

		HashMap<String,String> routingCache= new HashMap<String,String>();
		boolean isRDTUpdateReq=false;
		StringBuilder routingKey=null;
		String routingDetails = null;
		if (mailbagVOs != null) {

			for (MailbagVO mailbagvo : mailbagVOs) {

				MailbagPK mailbagPK = new MailbagPK();
				Mailbag mailbag = null;
				mailbagPK.setCompanyCode(mailbagvo.getCompanyCode());
				mailbagPK.setMailSequenceNumber(mailbagvo
						.getMailSequenceNumber());
				try {
					mailbag=Mailbag.findMailbagDetails(mailbagPK);/*A-9619 changed to specific as part of IASCB-55196*/
				} catch (FinderException e) {
					log.log(Log.SEVERE, "System Exception Caught",e);
				}
				if (mailbag != null) {
					if (containerVO.getContainerNumber().equals(
							mailbag.getUldNumber())) {
						isRDTUpdateReq=true;
						if(mailbag.getConsignmentNumber()!=null){
							mailbagvo.setPaCode(mailbag.getPaCode());
							mailbagvo.setConsignmentNumber(mailbag.getConsignmentNumber());
							mailbagvo.setDestination(mailbag.getDestination());
							routingKey=new StringBuilder();
							routingKey.append(mailbagvo.getPaCode()).append(mailbagvo.getConsignmentNumber()).append(mailbagvo.getDestination());
							if(!routingCache.containsKey(routingKey.toString())){
							routingDetails =Mailbag.findRoutingDetailsForConsignment(mailbagvo);
							if(routingDetails!=null){
								routingCache.put(routingKey.toString(), routingDetails);
								isRDTUpdateReq=false;
							}
							}else{
								isRDTUpdateReq=false;
							}
						}
						mailbag.updateFlightForReassign(toFlightVO,
								containerVO, toFlightSegmentSerialNum,isRDTUpdateReq);
						if(containerVO.isOffload()){
							mailbagvo.setTriggerForReImport(MailConstantsVO.MAIL_STATUS_OFFLOADED);
							if (MailConstantsVO.FLAG_YES.equals(containerVO.getPaBuiltFlag())) {
								mailbagvo.setAcceptancePostalContainerNumber(null);
								mailbagvo.setPaContainerNumberUpdate(true);
							}
						}else{
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
	 * This method returns all the DSNs involved in this Reassign i.e., all the
	 * DSNs which were moved when the Containers were moved A-1739
	 *
	 * @param dsnsInAirportToReassign
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	private Collection<MailbagVO> constructMailbagVOsFromAirport(
			Collection<MailbagInULDAtAirportVO> mailBagInUldToReassign,
			String companyCode) throws SystemException {

		log.entering("ReassignController", "getDSNVOsAtAirport");
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();

		for (MailbagInULDAtAirportVO mailbagInULDAtAirportVO : mailBagInUldToReassign) {
			MailbagPK mailbagPK = new MailbagPK();
			mailbagPK.setCompanyCode(companyCode);
			mailbagPK.setMailSequenceNumber(mailbagInULDAtAirportVO
					.getMailSequenceNumber());
			Mailbag mailbag = null;
			try {
				mailbag = Mailbag.find(mailbagPK);
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE, "FinderException Caught");
			}
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setCompanyCode(companyCode);
			mailbagVO.setMailSequenceNumber(mailbagInULDAtAirportVO
					.getMailSequenceNumber());
			if(mailbag!=null){
				mailbagVO.setMailbagId(mailbag.getMailIdr());
			}
			mailbagVO
					.setAcceptedBags(mailbagInULDAtAirportVO.getAcceptedBags());
			mailbagVO.setAcceptedWeight(mailbagInULDAtAirportVO
					.getAcceptedWgt());
			mailbagVO.setUldNumber(mailbagInULDAtAirportVO.getContainerNumber());
			mailbagVOs.add(mailbagVO);
		}
		log.exiting("ReassignController", "getDSNVOsAtAirport");
		return mailbagVOs;
	}

	/**
	 *
	 * @param containersToReassign
	 * @param toFlightVO
	 * @return
	 * @throws SystemException
	 * @throws FlightClosedException
	 * @throws ContainerAssignmentException
	 * @throws InvalidFlightSegmentException
	 * @throws ULDDefaultsProxyException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 * @throws com.ibsplc.icargo.business.mail.operations.MailBookingException
	 */
	public Collection<ContainerVO> reassignContainers(
			Collection<ContainerVO> containers, OperationalFlightVO toFlightVO)
			throws SystemException, FlightClosedException,
			ContainerAssignmentException, InvalidFlightSegmentException,
			ULDDefaultsProxyException, CapacityBookingProxyException,
			MailBookingException, MailDefaultStorageUnitException,
			com.ibsplc.icargo.business.mail.operations.MailBookingException {
		log.entering("ReassignController", "reassignContainers");
		Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>(); 
		Collection<ContainerVO> containerVOs = null;
		String pou = toFlightVO.getPou();
        for(ContainerVO cont: containers) {
        //setting pou to initial value in each iteration 
        toFlightVO.setPou(pou);	
        containerVOs = new ArrayList<>( Arrays.asList(cont));
        if(toFlightVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT) {
        	if(cont.getPou()!=null && cont.getPou().trim().length()>0) {
        		toFlightVO.setPou(cont.getPou()); 
        	}
        }
		Collection<ContainerVO> containersToReassign = new ArrayList<ContainerVO>();
		Collection<ContainerVO> fltAssignedcontainerVOs = new ArrayList<ContainerVO>();
		Collection<ContainerVO> carrierAssignedcontainerVOs = new ArrayList<ContainerVO>();
		//Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>();
		Collection<ContainerVO> transferredContainerVOs = null;

		if (toFlightVO.getFlightSequenceNumber() > 0 || toFlightVO.isTransferStatus()) {
			if(!MailConstantsVO.ONLOAD_MESSAGE.equals(cont.getMailSource())){
			isReassignableContainers(null, toFlightVO, null, null);
			}
			Collection<ContainerVO> containersToTsfr = new ArrayList<ContainerVO>();
			Collection<ContainerVO> containersToRem = new ArrayList<ContainerVO>();
			groupULDsForReassignOrTsfr(containerVOs, containersToTsfr,
					containersToReassign, containersToRem);

			log.log(Log.FINE, "THE CONTAINERS TO REASSIGN ",
					containersToReassign);
			log.log(Log.FINE, "THE CONTAINERS TO TRANSFER ", containersToTsfr);
			log.log(Log.FINE, "THE CONTAINERS TO REMOVE ", containersToRem);
			if (containersToTsfr != null && containersToTsfr.size() > 0) {
				/*
				 * bfore calling transfer we've to remove the containers in
				 * inventory Incase of reassign, tht method itself will do this.
				 */

				log.log(Log.FINEST,
						"containers to be removed from inv and tsfr ",
						containersToRem);
				reassignContainerFromDestToDest(containersToRem, null);

				/*
				 * Direct call is done, otherwise the mailcontroller transfer
				 * method again deos a find on the inventory
				 */
				Map<String, Object> transferMap = new MailTransfer()
						.transferContainers(containersToTsfr, toFlightVO,
								MailConstantsVO.FLAG_NO);
				if (transferMap.get(MailConstantsVO.CONST_CONTAINER) != null) {
					transferredContainerVOs = (Collection<ContainerVO>) transferMap
							.get(MailConstantsVO.CONST_CONTAINER);
				}

			}
		} else {
			containersToReassign = containerVOs;
		}
		int toFlightSegSerialNo = -1;
		boolean isUld=false;
		if (containersToReassign != null && containersToReassign.size() > 0) {
			log.log(Log.FINEST, "containers to be reassigned ",
					containersToReassign);
			Collection<ContainerVO> flightAssignedContainers = new ArrayList<ContainerVO>();
			Collection<ContainerVO> destAssignedContainers = new ArrayList<ContainerVO>();

			if (isReassignableContainers(containersToReassign, toFlightVO,
					destAssignedContainers, flightAssignedContainers)) {

				if (toFlightVO.getFlightSequenceNumber() != MailConstantsVO.DESTN_FLT) {

					new MailController().calculateContentID(containersToReassign, toFlightVO);

					for(ContainerVO containerVO : containersToReassign){
						if(MailConstantsVO.ULD_TYPE.equals(containerVO.getType())){
							isUld=true;
						}
					toFlightSegSerialNo = findFlightSegment(
							toFlightVO.getCompanyCode(),
							toFlightVO.getCarrierId(),
							toFlightVO.getFlightNumber(),
							toFlightVO.getFlightSequenceNumber(),
								toFlightVO.getPol(), toFlightVO.getPou());
					}

					validateAndCreateAssignedFlight(toFlightVO);

					validateCreateAssignedFlightSegment(toFlightVO,
							toFlightSegSerialNo);

					fltAssignedcontainerVOs = reassignContainerFromFlightToFlight(
							flightAssignedContainers, toFlightVO,
							toFlightSegSerialNo);

					toFlightVO.setSegSerNum(toFlightSegSerialNo);
					
					carrierAssignedcontainerVOs = reassignContainerFromDestToFlight(
							destAssignedContainers, toFlightVO,
							toFlightSegSerialNo);
					// Added as part of CRQ ICRD-93584 by A-5526 starts
					// new
//					new MLDController().flagMLDForContainerReassign(
//							containersToReassign, toFlightVO);
					//Added by A-8527 for IASCB-34446 start
					String enableMLDSend= findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
					if(MailConstantsVO.FLAG_YES.equals(enableMLDSend)){
					//Added by A-8527 for IASCB-34446 Ends	
					MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					mailController.flagMLDForContainerReassign(containersToReassign, toFlightVO);
					}
					// Added as part of CRQ ICRD-93584 by A-5526 ends
				} else {

					fltAssignedcontainerVOs = reassignContainerFromFlightToDest(
							flightAssignedContainers, toFlightVO, null);
					carrierAssignedcontainerVOs = reassignContainerFromDestToDest(
							destAssignedContainers, toFlightVO);
					MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					mailController.flagMLDForContainerReassign(containersToReassign, toFlightVO);
				}
				if (fltAssignedcontainerVOs != null) {
					containersForReturn.addAll(fltAssignedcontainerVOs);
				}
				if (carrierAssignedcontainerVOs != null) {
					containersForReturn.addAll(carrierAssignedcontainerVOs);
				}
				if (MailConstantsVO.FLAG_YES
						.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD)) && isUld) {
					updateOperationalULDsInFlight(containersToReassign,
							toFlightVO);
				}
			}
		}
		if (transferredContainerVOs != null
				&& transferredContainerVOs.size() > 0) {
			containersForReturn.addAll(transferredContainerVOs);
		}
		
        }
		log.exiting("ReassignController", "reassignContainers");
		return containersForReturn;
	}

	/**
	 * @author A-5991
	 * @param containersToReassign
	 * @param toFlightVO
	 * @throws SystemException
	 */
	private void updateOperationalULDsInFlight(
			Collection<ContainerVO> containersToReassign,
			OperationalFlightVO toFlightVO) throws SystemException {
		log.entering("ReassignController", "updateOperationalULDsInFlight");

		Collection<UldInFlightVO> uldInFlights = constructUldInFlightsForReassign(
				containersToReassign, toFlightVO);
		if (uldInFlights != null && uldInFlights.size() > 0) {
			new OperationsFltHandlingProxy()
					.saveOperationalULDsInFlight(uldInFlights);
		}
		log.exiting("ReassignController", "updateOperationalULDsInFlight");

	}

	/**
	 * @author A-5991
	 * @param containersToReassign
	 * @param toFlightVO
	 * @return
	 */
	private Collection<UldInFlightVO> constructUldInFlightsForReassign(
			Collection<ContainerVO> containersToReassign,
			OperationalFlightVO toFlightVO) {
		log.entering("ReassignController", "constructUldInFlightsForReassign");
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
					uldInFlightVO.setFlightSequenceNumber(toFlightVO
							.getFlightSequenceNumber());
					uldInFlightVO.setLegSerialNumber(toFlightVO
							.getLegSerialNumber());
				}
				uldInFlightVO.setPou(toFlightVO.getPou());
				//Added by A-7794 As part of ICRD-208677
				uldInFlightVO.setPol(toFlightVO.getPol());
				uldInFlightVO
						.setFlightDirection(MailConstantsVO.OPERATION_OUTBOUND);
				uldInFlights.add(uldInFlightVO);
			}
		}
		return uldInFlights;
	}

	private ULDForSegmentVO constructULDForSegmentFromAirport(
			ULDAtAirportVO uldAtAirportVO) throws SystemException {
		log.entering("ReassignController", "getULDForSegmentFromAirport");
		ULDForSegmentVO uldForSegmentVO = new ULDForSegmentVO();
		uldForSegmentVO.setCompanyCode(uldAtAirportVO.getCompanyCode());
		uldForSegmentVO.setUldNumber(uldAtAirportVO.getUldNumber());
		uldForSegmentVO.setNoOfBags(uldAtAirportVO.getNumberOfBags());
		uldForSegmentVO.setTotalWeight(uldAtAirportVO.getTotalWeight());
		uldForSegmentVO.setWarehouseCode(uldAtAirportVO.getWarehouseCode());
		uldForSegmentVO.setLocationCode(uldAtAirportVO.getLocationCode());
		uldForSegmentVO.setTransferFromCarrier(uldAtAirportVO.getTransferFromCarrier());

		// DSNs
		Collection<MailbagInULDAtAirportVO> dsnsInULDAtAirport = uldAtAirportVO.getMailbagInULDAtAirportVOs();
				
		if (dsnsInULDAtAirport != null && dsnsInULDAtAirport.size() > 0) {
			Collection<MailbagInULDForSegmentVO> mailbagInULDForSegs = constructDSNsInULDForSegFromArp(
					dsnsInULDAtAirport, uldAtAirportVO.getAirportCode());
			uldForSegmentVO.setMailbagInULDForSegmentVOs(mailbagInULDForSegs);
		}
		Collection<MailbagInULDAtAirportVO> mailbagInULDAtAirportVOs = uldAtAirportVO
				.getMailbagInULDAtAirportVOs();
		if (mailbagInULDAtAirportVOs != null
				&& mailbagInULDAtAirportVOs.size() > 0) {
			Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs = MailtrackingDefaultsVOConverter
					.convertToMailbagInULDForSegmentVOs(
							mailbagInULDAtAirportVOs,
							uldAtAirportVO.getAirportCode());
			uldForSegmentVO
					.setMailbagInULDForSegmentVOs(mailbagInULDForSegmentVOs);
		}
		log.exiting("ReassignController", "getULDForSegmentFromAirport");
		return uldForSegmentVO;
	}

	/**
	 * This method reassigns from destination to flight A-1739
	 *
	 * @param destAssignedContainers
	 * @param toFlightVO
	 * @param toFlightSegSerialNo
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	 Collection<ContainerVO> reassignContainerFromDestToFlight(
			Collection<ContainerVO> destAssignedContainers,
			OperationalFlightVO toFlightVO, int toFlightSegSerialNo)
			throws SystemException, ULDDefaultsProxyException,
			CapacityBookingProxyException, MailBookingException {
		log.entering("ReassignController", "reassignFromDestToFlight");
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
				String fromULDNumber = destAssignedContainerVO
						.getContainerNumber();

				if (MailConstantsVO.BULK_TYPE.equals(destAssignedContainerVO
						.getType())) {
						fromULDNumber = constructBulkULDNumber(
								destAssignedContainerVO.getFinalDestination(),
								destAssignedContainerVO.getCarrierCode());
				}
				ULDAtAirport uldAtAirport = null;
				try {
					uldAtAirport = ULDAtAirport
							.find(constructULDArpPKFromContainer(
									destAssignedContainerVO, fromULDNumber));
				} catch (FinderException exception) {
					log.log(Log.SEVERE, "uldArp not found");
					throw new SystemException(exception.getMessage(), exception);
				}

				AssignedFlightSegment toAssignedFlightSegment = null;
				try {
					toAssignedFlightSegment = findAssignedFlightSegment(
							toFlightVO.getCompanyCode(),
							toFlightVO.getCarrierId(),
							toFlightVO.getFlightNumber(),
							toFlightVO.getFlightSequenceNumber(),
							toFlightSegSerialNo);
				} catch (FinderException ex) {
					throw new SystemException(ex.getMessage(), ex);
				}

				ULDForSegmentVO uldForSegmentVO = null;

				//Collection<DSNInULDForSegmentVO> dsnToReassign = null;
				Collection<MailbagInULDForSegmentVO> mailbagsToReassign = null;
				//Added for IASCB-48787 starts
				if(destAssignedContainerVO.isUldTobarrow()){
					destAssignedContainerVO.setType(MailConstantsVO.BULK_TYPE);
				}
				//Added for IASCB-48787 Ends
				//Added by A-8893 for IASCB-55805 starts
				if(destAssignedContainerVO.isBarrowToUld()){
					destAssignedContainerVO.setType(MailConstantsVO.ULD_TYPE);
				}
				//Added by A-8893 for IASCB-55805 ends
				String containerNumber = destAssignedContainerVO
						.getContainerNumber();

				if (MailConstantsVO.ULD_TYPE.equals(destAssignedContainerVO
						.getType())) {
					ULDAtAirportVO uldAtAirportVO = uldAtAirport.retrieveVO();
					if(destAssignedContainerVO.isBarrowToUld()){
					uldAtAirportVO.setUldNumber(containerNumber);
					}
					log.log(Log.FINEST, "uldairport retrieved for uld --> ",
							uldAtAirportVO);
					double actualWgtCal=0.0;
				if(uldAtAirportVO.getMailbagInULDAtAirportVOs()!=null && uldAtAirportVO.getMailbagInULDAtAirportVOs().size()>0){
						Collection<MailbagInULDAtAirportVO> mailbagInULDAtAirportVOs=new ArrayList<MailbagInULDAtAirportVO>();
						for(MailbagInULDAtAirportVO mailbagInULDAtAirportVO:uldAtAirportVO.getMailbagInULDAtAirportVOs()){
							if(uldAtAirport!=null && (uldAtAirport.getUldAtAirportPK().getUldNumber().equals(mailbagInULDAtAirportVO.getContainerNumber())||containerNumber.equals(mailbagInULDAtAirportVO.getContainerNumber()))){
								if(mailbagInULDAtAirportVO.getWeight()!=null){
									actualWgtCal+=mailbagInULDAtAirportVO.getWeight().getSystemValue();
								}
								mailbagInULDAtAirportVOs.add(mailbagInULDAtAirportVO);         
							}
						}
						
						uldAtAirportVO.setMailbagInULDAtAirportVOs(mailbagInULDAtAirportVOs);        
					}
					uldAtAirport.remove();
					 /*if((airportParameters!=null &&airportParameters.size()>0 && !("Y".equals((String)airportParameters.get(WEIGHT_SCALE_AVAILABLE))))||airportParameters.size()==0){
						ULDVO uldVO = null;	
							
							Measure actualTareWeight=null;    
							try {
								uldVO = new ULDDefaultsProxy().findULDDetails(destAssignedContainerVO.getCompanyCode(), fromULDNumber);
							} catch (ULDDefaultsProxyException e1) {
								log.log(Log.FINE, "ULDDefaultsProxyException");
							} catch (SystemException e1) {
								log.log(Log.FINE, "SystemException");
							}
								if(uldVO!=null){
									actualTareWeight = uldVO.getTareWeight();
								}else{
								   ULDValidationFilterVO  uLDValidationFilterVO=new ULDValidationFilterVO();
					        	   uLDValidationFilterVO.setCompanyCode(destAssignedContainerVO.getCompanyCode());
					        	   uLDValidationFilterVO.setUldTypeCode(fromULDNumber.substring(0,3));
					        	   uLDValidationFilterVO.setSerialNumber(fromULDNumber.substring(3,fromULDNumber.length()-2));
					        	   uLDValidationFilterVO.setUldAirlineCode(fromULDNumber.substring(fromULDNumber.length()-2,fromULDNumber.length()));
                                   uLDValidationFilterVO.setUldNumber(fromULDNumber);
					        	    
					        	     try{
					        		     actualTareWeight=new SharedULDProxy().findULDTareWeight(uLDValidationFilterVO);
									  }catch(SystemException e){
										 e.getMessage();
								      } catch (SharedProxyException e) {
										e.getMessage();
									 }
									 }
					        	   if (actualTareWeight!=null){
					        	    	actualWgtCal+=actualTareWeight.getSystemValue();  
						        	  destAssignedContainerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,actualWgtCal)); 
							       }
						   
					  }*/
					uldForSegmentVO = constructULDForSegmentFromAirport(uldAtAirportVO);

					if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null
							&& !uldForSegmentVO.getMailbagInULDForSegmentVOs().isEmpty()) {
						uldForSegmentVO.getMailbagInULDForSegmentVOs()
								.forEach(mailbagInULDForSegmentVO -> mailbagInULDForSegmentVO
										.setGhttim(destAssignedContainerVO.getGHTtime()));//IASCB-48967
					}

					updateULDForSegmentVO(uldForSegmentVO, toFlightVO,
							destAssignedContainerVO, toFlightSegSerialNo);
					//Modified by a-7779 for IASCB-46530
						actualWgtCal  = calculateActualWeightForULD(uldForSegmentVO,destAssignedContainerVO,fromULDNumber);
						destAssignedContainerVO
						.setActualWeight(new Measure(UnitConstants.MAIL_WGT, actualWgtCal));
					//Added as part of bug ICRD-241542 by A-5526 starts
					//In case the toFlight having the container with same name and different type (as BULK in the toFlight) then
					//no insert will go to MALULDSEG as there will be an entry with uldNumber as BULK-POU format.
					//Only need the insertion to MALULDSEGDTL table.
					try {
						if(destAssignedContainerVO.isUldTobarrow()){
							String bulkNumber = constructBulkULDNumber(
									toFlightVO.getPou(),
									toFlightVO.getCarrierCode());
							uldForSegmentVO.setUldNumber(bulkNumber);
						}
						ULDForSegment toULDForSegment = AssignedFlightSegment
								.findULDForSegment(constructULDForSegmentPK(
										toFlightVO.getCompanyCode(),
										uldForSegmentVO.getUldNumber(), toFlightVO.getCarrierId(),
										toFlightVO.getFlightNumber(),
										toFlightVO.getFlightSequenceNumber(),
										toFlightSegSerialNo));
						
						if(uldForSegmentVO.getMailbagInULDForSegmentVOs()!=null){
							toULDForSegment.populateMailbagDetails(uldForSegmentVO.getMailbagInULDForSegmentVOs());
					        }
					} catch (FinderException e) {
						if(destAssignedContainerVO.isUldTobarrow()){
							String bulkNumber = constructBulkULDNumber(
									toFlightVO.getPou(),
									toFlightVO.getCarrierCode());
							uldForSegmentVO.setUldNumber(bulkNumber);
						}
					toAssignedFlightSegment
							.createULDForSegment(uldForSegmentVO);
					  }
					//Added as part of bug ICRD-241542 by A-5526 ends
					
					//Modified by A-7794 as part of ICRD-208677
					if (isUldIntegrationEnbled) {
						flightDetailsVO = new FlightDetailsVO();
						flightDetailsVO.setCompanyCode(toFlightVO.getCompanyCode());
						flightDetailsVO.setFlightCarrierIdentifier(toFlightVO
								.getCarrierId());
						flightDetailsVO.setFlightDate(toFlightVO.getFlightDate());
						flightDetailsVO.setFlightNumber(toFlightVO.getFlightNumber());
						flightDetailsVO.setFlightSequenceNumber(toFlightSegSerialNo);
						flightDetailsVO.setCarrierCode(toFlightVO.getCarrierCode());
						flightDetailsVO.setDirection(MailConstantsVO.EXPORT);
						uldInFlightVOs = new ArrayList<ULDInFlightVO>();
						//Added by A-7794 as part of ICRD-208677
						flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST); 
					}

					// For CR :AirNZ404 : Mail Allocation STARTS
					uldForSegmentVO.setPou(toFlightVO.getPou());
					uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
					uldForSegmentVO.setFlightNumber(toFlightVO
							.getFlightNumber());
					uldForSegmentVO.setFlightDate(toFlightVO.getFlightDate());
					uldForSegmentVO.setFlightSequenceNumber(toFlightVO
							.getFlightSequenceNumber());
					uldForSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
					// Updating Booking Details
					updateBookingForFlight(uldForSegmentVO, toFlightVO,
							"UPDATE_BOOKING_TO_FLIGHT");
					// CR :AirNZ404 :Changes ENDS
					isReassignSuccess = true;
				} else if (MailConstantsVO.BULK_TYPE
						.equals(destAssignedContainerVO.getType())) {

					String toULDNumber = constructBulkULDNumber(
							toFlightVO.getPou(), toFlightVO.getCarrierCode());
					if(destAssignedContainerVO.isBarrowToUld()){
						toULDNumber=destAssignedContainerVO.getContainerNumber();
					}

					mailbagsToReassign = constructDSNsInULDForSegFromArp(
							uldAtAirport.reassignBulkContainer(containerNumber),
							toFlightVO.getPol());
					mailbagsToReassign = MailtrackingDefaultsVOConverter
							.convertToMailbagInULDForSegmentVOs(
									uldAtAirport
											.reassignBulkContainerFormail(containerNumber),
									toFlightVO.getPol());
					double actualWgtCal=0.0;
					//Modified by A-7779 for IASCB-46530
					actualWgtCal = calculateActualWeightForBulk(mailbagsToReassign,destAssignedContainerVO);
					destAssignedContainerVO
										.setActualWeight(new Measure(UnitConstants.MAIL_WGT, actualWgtCal));
					
				/*	if((airportParameters!=null &&airportParameters.size()>0 &&!("Y".equals((String)airportParameters.get(WEIGHT_SCALE_AVAILABLE))))||airportParameters.size()==0){
						 if(mailbagsToReassign!=null &&mailbagsToReassign.size()>0){
							     if(destAssignedContainerVO.getActualWeight()==null){
					                for(MailbagInULDForSegmentVO mailbagInULDForSegmentVO:mailbagsToReassign){
						                if(mailbagInULDForSegmentVO.getWeight()!=null){
						                	actualWgtCal+=mailbagInULDForSegmentVO.getWeight().getSystemValue();
						                }
					                }
					               destAssignedContainerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,actualWgtCal));  
					             }
					       }
					 }*/
					
					if(destAssignedContainerVO.isUldTobarrow()){
						uldAtAirport.remove();
					}

					if(mailbagsToReassign!=null&&!mailbagsToReassign.isEmpty()){
						mailbagsToReassign.forEach(mailbagToReassign->mailbagToReassign.setGhttim(destAssignedContainerVO.getGHTtime()));//IASCB-48967
					}
					
					try {
						
						ULDForSegment toULDForSegment = AssignedFlightSegment
								.findULDForSegment(constructULDForSegmentPK(
										toFlightVO.getCompanyCode(),
										toULDNumber, toFlightVO.getCarrierId(),
										toFlightVO.getFlightNumber(),
										toFlightVO.getFlightSequenceNumber(),
										toFlightSegSerialNo));

						toAssignedFlightSegment.assignBulkContainer(
								toULDForSegment, mailbagsToReassign);

						// For CR :AirNZ404 : Mail Allocation STARTS
						 uldForSegmentVO = toULDForSegment
								.retrieveVO();
						uldForSegmentVO.setPou(toFlightVO.getPou());
						uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
						uldForSegmentVO.setFlightNumber(toFlightVO
								.getFlightNumber());
						uldForSegmentVO.setFlightDate(toFlightVO.getFlightDate());
						uldForSegmentVO.setFlightSequenceNumber(toFlightVO
								.getFlightSequenceNumber());
						uldForSegmentVO
								.setSegmentSerialNumber(toFlightSegSerialNo);
						// Updating Booking Details of Current Flight
						updateBookingForFlight(uldForSegmentVO, toFlightVO,
								"UPDATE_BOOKING_TO_FLIGHT");
						// CR :AirNZ404 :Changes ENDS

						isReassignSuccess = true;
					} catch (FinderException finderException) {

						uldForSegmentVO = new ULDForSegmentVO();
						uldForSegmentVO.setUldNumber(toULDNumber);
						uldForSegmentVO.setMailbagInULDForSegmentVOs(mailbagsToReassign);
						updateULDForSegmentVO(uldForSegmentVO, toFlightVO,
								destAssignedContainerVO, toFlightSegSerialNo);

						log.log(Log.FINEST,
								"uldforsegment retrieved for bulk --> ",
								uldForSegmentVO);
						toAssignedFlightSegment
								.createULDForSegment(uldForSegmentVO);
						// For CR :AirNZ404 : Mail Allocation STARTS
						uldForSegmentVO.setPou(toFlightVO.getPou());
						uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
						uldForSegmentVO.setFlightNumber(toFlightVO
								.getFlightNumber());
						uldForSegmentVO.setFlightDate(toFlightVO
								.getFlightDate());
						uldForSegmentVO.setFlightSequenceNumber(toFlightVO
								.getFlightSequenceNumber());
						uldForSegmentVO
								.setSegmentSerialNumber(toFlightSegSerialNo);
						// Updating Booking Details
						updateBookingForFlight(uldForSegmentVO, toFlightVO,
								"UPDATE_BOOKING_TO_FLIGHT");
						// CR :AirNZ404 :Changes ENDS
						isReassignSuccess = true;
					}

				}
				if (isReassignSuccess) {
					if (mailbagsToReassign == null) {
						mailbagsToReassign = uldForSegmentVO
								.getMailbagInULDForSegmentVOs();
					}
					if (mailbagsToReassign == null) {
						mailbagsToReassign = uldForSegmentVO
								.getMailbagInULDForSegmentVOs();
					}

					if (mailbagsToReassign!=null && !mailbagsToReassign.isEmpty()) {
						Collection<MailbagVO> mailbagVOs = constructMailBagVOFromSeg(
								mailbagsToReassign,
								toFlightVO.getCompanyCode());
						updateDSNForConReassign(mailbagVOs, toFlightVO,
								destAssignedContainerVO, toFlightSegSerialNo,
								null);
						/*
						 * updateDSNSegment(dsnVOs,destAssignedContainerVO,
						 * toFlightVO, toFlightSegSerialNo, null,
						 * toAssignedFlightSegment);
						 */
						updateTrfCarrierForResdits(mailbagsToReassign,
								mailbagVOs);
						mailbagToFlag.addAll(mailbagVOs);
					}
					MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					mailController.flagHistoryForContainerReassignment(toFlightVO,destAssignedContainerVO,mailbagToFlag);
					mailController.flagAuditForContainerReassignment(toFlightVO,destAssignedContainerVO,mailbagToFlag);
					//importMRAData
					boolean provisionalRateImport =false;
					Collection<MailbagVO> mailbagsToImport = new MailController().validateAndReImportMailbagsToMRA(mailbagToFlag);
					Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(toFlightVO ,destAssignedContainerVO,mailbagsToImport,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport) ;
					log.log(Log.FINEST, "RateAuditVO-->", rateAuditVOs);
					 if(rateAuditVOs!=null && !rateAuditVOs.isEmpty()){
						 String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		  		        	if(importEnabled!=null && importEnabled.contains("M")){
			        try {
						new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
					} catch (ProxyException e) {
						throw new SystemException(e.getMessage(), e);
					}
					}
					 }
					// import Provisonal rate Data to malmraproint for upront rate Calculation
						String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
						if(MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
							provisionalRateImport = true;
				      	Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toFlightVO ,destAssignedContainerVO,mailbagsToImport,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,provisionalRateImport) ;
				      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
				        try {
				        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
					} catch (ProxyException e) {
						throw new SystemException(e.getMessage(), e);
					}
					}
					 }
				}
			}
			if (isReassignSuccess || isNotAccepted) {
				updateReassignedContainer(destAssignedContainerVO, toFlightVO,
						toFlightSegSerialNo);
				if (isReassignSuccess) {
					if (MailConstantsVO.FLAG_YES.equals(destAssignedContainerVO
							.getPaBuiltFlag())) {
						paBuiltContainers.add(destAssignedContainerVO);
					}
				}
			}
			/*
			 * Added By Karthick V as the part of the NCA Mail Tracking Bug Fix
			 * .. Include the check to ignore the Bulks which are not ULds from
			 * beingh passed to the Method UpdateULDForOperations ..
			 */
			if (MailConstantsVO.ULD_TYPE.equals(destAssignedContainerVO.getType())) {   
				// Collection<String> uldTypes = new ArrayList<String>();
				// uldTypes.add(destAssignedContainerVO.getContainerNumber().substring(0,3));
				ULDValidationVO uldValidationVO = null;
				try {
					uldValidationVO = Proxy.getInstance().get(SharedULDProxy.class).validateULD(
							destAssignedContainerVO.getCompanyCode(),
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
				uldInFlightVO.setUldNumber(destAssignedContainerVO
						.getContainerNumber());
				uldInFlightVO.setPointOfLading(destAssignedContainerVO
						.getAssignedPort());
				//Modified by A-7794 as part of ICRD-226088
				uldInFlightVO.setPointOfUnLading(destAssignedContainerVO.getFinalDestination());
				//Modified by A-7794 as part of ICRD-226708
				uldInFlightVO.setRemark(destAssignedContainerVO.getRemarks());
				uldInFlightVOs.add(uldInFlightVO);
			}
			ContainerVO containerReturnVO = new ContainerVO();
			BeanHelper.copyProperties(containerReturnVO,
					destAssignedContainerVO);
			containerReturnVO.setFlightSequenceNumber(toFlightVO
					.getFlightSequenceNumber());
			containerReturnVO.setFlightNumber(toFlightVO.getFlightNumber());
			containerReturnVO.setLegSerialNumber(toFlightVO
					.getLegSerialNumber());
			containerReturnVO.setSegmentSerialNumber(toFlightSegSerialNo);
			containerReturnVO.setCarrierCode(toFlightVO.getCarrierCode());
			containerReturnVO.setFlightDate(toFlightVO.getFlightDate());
			containersForReturn.add(containerReturnVO);
			//Added by A-7794 as part of ICRD-208677
			if(MailConstantsVO.ULD_TYPE.equals(destAssignedContainerVO
						.getType())){
				//Modified by A-7794 as part of ICRD-226088
				//No need to check the operational flag for Reassign;
		if (isUldIntegrationEnbled && uldInFlightVOs.size() > 0) {
			flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
				//Modified by A-7794 as part of ICRD-226708
				flightDetailsVO.setRemark(destAssignedContainerVO.getRemarks());
			flightDetailsVO.setAction(FlightDetailsVO.ACCEPTANCE);

			Proxy.getInstance().get(ULDDefaultsProxy.class).updateULDForOperations(flightDetailsVO);
		}
			}
			if (paBuiltContainers.size() > 0 || mailbagToFlag.size() > 0) {
				flagResditsForContainerReassign(mailbagToFlag, paBuiltContainers,
						toFlightVO, toFlightSegSerialNo);
			}
		}
		
		
		log.exiting("ReassignController", "reassignFromDestToFlight");
		return containersForReturn;
	}

	/**
	 * This method return all the DSN involved in this reassignments The
	 * returned collection is used for updating various masters A-1739
	 *
	 * @param dsnsToReassign
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	private Collection<DSNVO> constructDSNVOsFromSeg(
			Collection<DSNInULDForSegmentVO> dsnsToReassign, String companyCode)
			throws SystemException {
		log.entering("ReassignController", "getDSNVOsOfSeg");

		Collection<DSNVO> dsnVOs = new ArrayList<DSNVO>();

		if (dsnsToReassign != null) {
			for (DSNInULDForSegmentVO dsnInULDVO : dsnsToReassign) {
				log.log(Log.FINE, "The DSN IN ULD FOR SEG VO ", dsnInULDVO);
				DSNVO dsnVO = new DSNVO();
				dsnVO.setCompanyCode(companyCode);
				dsnVO.setDsn((dsnInULDVO.getDsn()));
				dsnVO.setOriginExchangeOffice(dsnInULDVO
						.getOriginOfficeOfExchange());
				dsnVO.setDestinationExchangeOffice(dsnInULDVO
						.getDestinationOfficeOfExchange());
				dsnVO.setMailClass(dsnInULDVO.getMailClass());
				dsnVO.setMailSubclass(dsnInULDVO.getMailSubclass());
				dsnVO.setMailCategoryCode(dsnInULDVO.getMailCategoryCode());
				dsnVO.setYear(dsnInULDVO.getYear());
				dsnVO.setWeight(dsnInULDVO.getAcceptedWeight());
				dsnVO.setBags(dsnInULDVO.getAcceptedBags());
				dsnVOs.add(dsnVO);
			}
		}
		log.exiting("ReassignController", "getDSNVOsOfSeg");
		return dsnVOs;
	}

	/**
	 * This method checks if an assignedFlightSegment exists and creates if if
	 * not exists A-1739
	 *
	 * @param toFlightVO
	 * @param toFlightSegSerialNo
	 * @throws SystemException
	 */
	private void validateCreateAssignedFlightSegment(
			OperationalFlightVO toFlightVO, int toFlightSegSerialNo)
			throws SystemException {
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
			assignedFlightSegmentVO.setFlightNumber(toFlightVO
					.getFlightNumber());
			assignedFlightSegmentVO.setFlightSequenceNumber(toFlightVO
					.getFlightSequenceNumber());
			assignedFlightSegmentVO.setPol(toFlightVO.getPol());
			assignedFlightSegmentVO.setPou(toFlightVO.getPou());
			assignedFlightSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
			new AssignedFlightSegment(assignedFlightSegmentVO);
		}
	}

	private Collection<MailbagInULDForSegmentVO> constructDSNsInULDForSegFromArp(
			Collection<MailbagInULDAtAirportVO> mailsInAirportToReassign,
			String airportCode) throws SystemException {
		log.entering("ReassignController", "getDSNInULDForSegmentsFromAirport");

		Collection<MailbagInULDForSegmentVO> mailsInULDForSegments = new ArrayList<MailbagInULDForSegmentVO>();
		for (MailbagInULDAtAirportVO mailInULDAtAirport : mailsInAirportToReassign) {
			MailbagInULDForSegmentVO mailInULDForSegmentVO = new MailbagInULDForSegmentVO();
			mailInULDForSegmentVO.setMailSubclass(mailInULDAtAirport
					.getMailSubclass());
			mailInULDForSegmentVO.setMailCategoryCode(mailInULDAtAirport
					.getMailCategoryCode());
			mailInULDForSegmentVO.setMailClass(mailInULDAtAirport.getMailClass());
			mailInULDForSegmentVO.setMailId(mailInULDAtAirport.getMailId());
			mailInULDForSegmentVO.setMailSequenceNumber(mailInULDAtAirport.getMailSequenceNumber());

		

			
			mailsInULDForSegments.add(mailInULDForSegmentVO);
		}
		log.exiting("ReassignController", "getDSNInULDForSegmentsFromAirport");
		return mailsInULDForSegments;
	}

	/**
	 * This method returns all the DSNs involved in this Reassign i.e., all the
	 * DSNs which were moved when the Containers were moved A-1739
	 *
	 * @param dsnsInAirportToReassign
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	private Collection<DSNVO> constructDSNVOsFromAirport(
			Collection<DSNInULDAtAirportVO> dsnsInAirportToReassign,
			String companyCode) throws SystemException {

		log.entering("ReassignController", "getDSNVOsAtAirport");
		Collection<DSNVO> dsnVOs = new ArrayList<DSNVO>();

		for (DSNInULDAtAirportVO dsnInULDVO : dsnsInAirportToReassign) {
			DSNVO dsnVO = new DSNVO();
			dsnVO.setCompanyCode(companyCode);
			dsnVO.setDsn((dsnInULDVO.getDsn()));
			dsnVO.setOriginExchangeOffice(dsnInULDVO
					.getOriginOfficeOfExchange());
			dsnVO.setDestinationExchangeOffice(dsnInULDVO
					.getDestinationOfficeOfExchange());

			dsnVO.setMailClass(dsnInULDVO.getMailClass());
			dsnVO.setMailSubclass(dsnInULDVO.getMailSubclass());
			dsnVO.setMailCategoryCode(dsnInULDVO.getMailCategoryCode());
			dsnVO.setYear(dsnInULDVO.getYear());
			dsnVO.setWeight(dsnInULDVO.getAcceptedWeight());
			dsnVO.setBags(dsnInULDVO.getAcceptedBags());
			dsnVOs.add(dsnVO);
		}
		log.exiting("ReassignController", "getDSNVOsAtAirport");
		return dsnVOs;
	}

	/**
	 * A-1739
	 *
	 * @param toDestinationVO
	 * @param toULDNumber
	 * @return
	 */
	private ULDAtAirportPK constructULDArpPKFromOpFlt(
			OperationalFlightVO toDestinationVO, String toULDNumber) {
		ULDAtAirportPK uldArpPK = new ULDAtAirportPK();
		uldArpPK.setCompanyCode(toDestinationVO.getCompanyCode());
		uldArpPK.setCarrierId(toDestinationVO.getCarrierId());
		uldArpPK.setAirportCode(toDestinationVO.getPol());
		uldArpPK.setUldNumber(toULDNumber);
		return uldArpPK;
	}

	/**
	 * @author A-1739
	 * @param toFlightVO
	 * @param containerVO
	 * @param toFlightSegmentSerialNum
	 * @throws SystemException
	 */
	public void updateMailbagFlightForReassign(OperationalFlightVO toFlightVO,
			ContainerVO containerVO, int toFlightSegmentSerialNum,
			Mailbag mailbag) throws SystemException {
		if (mailbag != null) {
			// update mailbags present only in this container
			if (containerVO.getContainerNumber().equals(mailbag.getUldNumber())) {
				
				 String routingDetails = null;
			        if(mailbag.getConsignmentNumber()!=null){
			        	MailbagVO mailbagVO = new MailbagVO();
				        mailbagVO.setCompanyCode(mailbag.getMailbagPK().getCompanyCode());
				        mailbagVO.setPaCode(mailbag.getPaCode());
				        mailbagVO.setConsignmentNumber(mailbag.getConsignmentNumber());
				        mailbagVO.setDestination(mailbag.getDestination());
						routingDetails = Mailbag.findRoutingDetailsForConsignment(mailbagVO);
			        }
			        
				// MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(
				// MailbagVO.MODULE, MailbagVO.SUBMODULE,
				// MailbagVO.ENTITY);
				// mailbagAuditVO.setActionCode(MailConstantsVO.AUDIT_REASSIGN_CONTAINER);
				// mailbagAuditVO = (MailbagAuditVO) AuditUtils
				// .populateAuditDetails(mailbagAuditVO, mailbag);
				mailbag.updateFlightForReassign(toFlightVO, containerVO,
						toFlightSegmentSerialNum,routingDetails==null?true:false);
				// performMailbagAudit(mailbagAuditVO, mailbag,
				// AuditVO.UPDATE_ACTION,
				// constructAddInfoForCoReassign(containerVO, toFlightVO));
			}
		}
	}

	/**
	 * This method updates the CONMST with the new flight of this container
	 * A-1739
	 *
	 * @param assignedContainerVO
	 * @param toFlightVO
	 * @param toFlightSegSerialNumber
	 * @throws SystemException
	 */
	private void updateReassignedContainer(ContainerVO assignedContainerVO,
			OperationalFlightVO toFlightVO, int toFlightSegSerialNumber)
			throws SystemException{
		log.entering("ReassignController", "updateReassignedContainer");
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(assignedContainerVO.getCompanyCode());
		containerPk
				.setContainerNumber(assignedContainerVO.getContainerNumber());
		containerPk.setCarrierId(assignedContainerVO.getCarrierId());
		containerPk.setFlightNumber(assignedContainerVO.getFlightNumber());
		containerPk.setFlightSequenceNumber(assignedContainerVO
				.getFlightSequenceNumber());
		containerPk.setAssignmentPort(assignedContainerVO.getAssignedPort());
		containerPk
				.setLegSerialNumber(assignedContainerVO.getLegSerialNumber());

		Container prevFlightContainer = null;

		try {
			prevFlightContainer = Container.find(containerPk);
		} catch (FinderException finderException) {
			/*throw new SystemException(finderException.getMessage(),
					finderException);*/
			return;
		}

		ContainerVO containerVO = prevFlightContainer.retrieveVO();

		// remove current assignment
		// Added by Karthick.V to include the Audit
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(
				ContainerVO.MODULE, ContainerVO.SUBMODULE, ContainerVO.ENTITY);
		containerAuditVO = (ContainerAuditVO) AuditUtils.populateAuditDetails(
				containerAuditVO, prevFlightContainer, false);
		collectContainerAuditDetails(prevFlightContainer, containerAuditVO);
		containerAuditVO.setActionCode(AuditVO.DELETE_ACTION);
		containerAuditVO.setAuditRemarks(prevFlightContainer.getRemarks());

		// added by paulson for transaction code to be stamped when reassigning
		// container
		containerVO
				.setTransactionCode(assignedContainerVO.getTransactionCode());
		//Added by a-7779 for iascb-39312 
		if(assignedContainerVO.isContainerDestChanged()){
			containerVO
			.setTransactionCode(MailConstantsVO.MAIL_STATUS_ASSIGNED);
		}
		if(assignedContainerVO.getAssignedUser()!=null){      
			containerVO
			.setAssignedUser(assignedContainerVO.getAssignedUser());
		}else if (assignedContainerVO.getLastUpdateUser() != null) {
			containerVO
					.setAssignedUser(assignedContainerVO.getLastUpdateUser());
		}
		containerVO.setLastUpdateUser(assignedContainerVO.getLastUpdateUser());
		
		// POU only for reassign TO flight
		// POU for reassign dest to flight
		// since POU is not present in opFlight while reassign thru assign
		// container
		/*
		 * Added By Karthick V Not needed in case of the ToFlightVo being null
		 */
		if (toFlightVO != null) {
			if (toFlightVO.getFlightSequenceNumber() > 0) {
				if (toFlightVO.getPou() == null) {
					containerVO.setPou(assignedContainerVO.getPou());
				} else {
					containerVO.setPou(toFlightVO.getPou());
				}
			}
			else {
				containerVO.setPou(null);
			}
		}
		prevFlightContainer.remove();
		AuditUtils.performAudit(containerAuditVO);
		if (toFlightVO != null) {
			// Inorder to avoid new barrow number generation while updating
			// the CONMST
			// Also for audit as reassign
			containerVO.setReassignFlag(true);

			containerVO.setCarrierId(toFlightVO.getCarrierId());
			containerVO.setFlightNumber(toFlightVO.getFlightNumber());
			containerVO.setCarrierCode(toFlightVO.getCarrierCode());
			containerVO.setFlightSequenceNumber(toFlightVO
					.getFlightSequenceNumber());
			containerVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
			containerVO.setSegmentSerialNumber(toFlightSegSerialNumber);
			containerVO.setCarrierCode(toFlightVO.getCarrierCode());
			containerVO.setPol(toFlightVO.getPol());
			containerVO.setTransitFlag(MailConstantsVO.FLAG_YES);

			containerVO.setRemarks(assignedContainerVO.getRemarks());
			containerVO.setOnwardRoutings(assignedContainerVO
					.getOnwardRoutings());
			if(assignedContainerVO.getActualWeight()!=null){
				containerVO.setActualWeight(assignedContainerVO.getActualWeight());
			}
			if(assignedContainerVO.getContentId()!=null){
				containerVO.setContentId(assignedContainerVO.getContentId());
			}
				
				containerVO.setAssignedDate(new LocalDate(containerVO.getAssignedPort(),Location.ARP,true));  
						       
			//Added by A-8893 for IASCB-38903 starts
				 if(assignedContainerVO.isUldTobarrow()){
				 containerVO.setType("B");
			      }
				 if(assignedContainerVO.isBarrowToUld()){
					 containerVO.setType("U");
					 Measure containerTareWeight = new MailController().
							 getUldTareWeight(assignedContainerVO.getCompanyCode(), assignedContainerVO.getContainerNumber());
					 assignedContainerVO.setContainerWeight(containerTareWeight);
				      }
			//Added by A-8893 for IASCB-38903 ends
				 
			if (assignedContainerVO.isOffload()) {
				containerVO.setOffloadFlag(MailConstantsVO.FLAG_YES);
				containerVO.setTransitFlag(MailConstantsVO.FLAG_YES);//added by A-9619 for IASCB-57266, changed to Y offload always happen for flight to dest
				containerVO.setOffload(true);
			} else {
				containerVO.setOffloadFlag(MailConstantsVO.FLAG_NO);
				containerVO.setOffload(false);
				// Added for ICRD-128582 for updating the finaldestination to
				// Toflight pou
				
					if(MailConstantsVO.BULK_TYPE.equals(containerVO.getType()) && containerVO.getPou()!=null){
						containerVO.setFinalDestination(containerVO.getPou());	
					}
					else{
				   containerVO.setFinalDestination(assignedContainerVO
								.getFinalDestination());
				}
			}
			
			containerVO.setFromFltNum(assignedContainerVO.getFlightNumber());
			containerVO.setPrevFlightPou(assignedContainerVO.getPou());
			containerVO.setTransferAudit(assignedContainerVO.isTransferAudit());
			containerVO.setActWgtSta(assignedContainerVO.getActWgtSta());
			if(containerVO.getUldFulIndFlag() == null) {
			containerVO.setUldFulIndFlag(assignedContainerVO.getUldFulIndFlag());
			}
			if(containerVO.getUldReferenceNo() == 0){
			containerVO.setUldReferenceNo(assignedContainerVO.getUldReferenceNo());
			}
			log.log(Log.FINE, "container mst ", containerVO);
			createContainer(containerVO);
			
			 
		}

		log.exiting("ReassignController", "updateReassignedContainer");
	}

	/**
	 * @author A-5991
	 * @param paBuiltContainers
	 * @param toFlightVO
	 * @param segmentSerialNumber
	 * @return
	 * @throws SystemException
	 */
	private Collection<ContainerDetailsVO> updateFlightOfReassignedContainers(
			Collection<ContainerVO> paBuiltContainers,
			OperationalFlightVO toFlightVO, int segmentSerialNumber)
			throws SystemException {

		Collection<ContainerDetailsVO> flightUpdatedULDs = new ArrayList<ContainerDetailsVO>();
		for (ContainerVO paContainer : paBuiltContainers) {
			ContainerDetailsVO containerDetailsVO = MailtrackingDefaultsVOConverter
					.convertToContainerDetails(paContainer);
			// BeanHelper.copyProperties(containerDetailsVO, paContainer);
			containerDetailsVO.setContainerJnyId(paContainer
					.getContainerJnyID());
			containerDetailsVO.setPaCode(paContainer.getShipperBuiltCode());

			containerDetailsVO.setCarrierId(toFlightVO.getCarrierId());
			containerDetailsVO.setCarrierCode(toFlightVO.getCarrierCode());
			containerDetailsVO.setFlightNumber(toFlightVO.getFlightNumber());
			containerDetailsVO.setFlightSequenceNumber(toFlightVO
					.getFlightSequenceNumber());
			containerDetailsVO.setFlightDate(toFlightVO.getFlightDate());
			containerDetailsVO.setSegmentSerialNumber(segmentSerialNumber);
			containerDetailsVO.setPol(toFlightVO.getPol());
			flightUpdatedULDs.add(containerDetailsVO);
		}
		return flightUpdatedULDs;
	}

	/**
	 * find the segment serial number of the segment to which this container is
	 * assigned A-1739
	 *
	 * @param companyCode
	 * @param carrierId
	 * @param flightNumber
	 * @param flightSequenceNumber
	 * @param pol
	 * @param pou
	 * @return the segmentserialnumber for the pol-pou of the flight
	 * @throws SystemException
	 */
	private int findFlightSegment(String companyCode, int carrierId,
			String flightNumber, long flightSequenceNumber, String pol,
			String pou) throws SystemException, InvalidFlightSegmentException {
		log.entering("ReassignController", "findFlightSegment");
		Collection<FlightSegmentSummaryVO> flightSegments = null;

		flightSegments = Proxy.getInstance().get(FlightOperationsProxy.class).findFlightSegments(
				companyCode, carrierId, flightNumber, flightSequenceNumber);

		String containerSegment = new StringBuilder().append(pol).append(pou)
				.toString();
		String flightSegment = null;

		int segmentSerNum = 0;
		for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
			flightSegment = new StringBuilder()
					.append(segmentSummaryVO.getSegmentOrigin())
					.append(segmentSummaryVO.getSegmentDestination())
					.toString();
			log.log(Log.FINEST, "from proxy -- >", flightSegment);
			log.log(Log.FINEST, "from container  -- >", containerSegment);
			if (flightSegment.equals(containerSegment)) {

				segmentSerNum = segmentSummaryVO.getSegmentSerialNumber();
			}
		}
		if (segmentSerNum == 0) {
			throw new InvalidFlightSegmentException(
					new String[] { containerSegment });
		}
		log.exiting("ReassignController", "findFlightSegment");
		return segmentSerNum;
	}

	/**
	 * @author a-1936 This method is used to validateflight for closed status
	 *         and if not exists create
	 * @param operationalFlightVO
	 * @throws SystemException
	 * @throws ContainerAssignmentException
	 */
	private void validateAndCreateAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException,
			ContainerAssignmentException {

		AssignedFlightPK assignedFlightPk = constructAssignedFlightPK(operationalFlightVO);
		try {
			AssignedFlight.find(assignedFlightPk);
		} catch (FinderException ex) {
			log.log(Log.INFO, "FINDER EXCEPTION IS THROWN");
			createAssignedFlight(operationalFlightVO);
		}
	}

	/**
	 *
	 * @author a-1936 This method is used to create the assignedFlight
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	private void createAssignedFlight(OperationalFlightVO operationalFlightVO)
			throws SystemException {
		AssignedFlightVO assignedFlightVO = new AssignedFlightVO();
		assignedFlightVO.setAirportCode(operationalFlightVO.getPol());
		assignedFlightVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		assignedFlightVO.setCarrierId(operationalFlightVO.getCarrierId());
		assignedFlightVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		assignedFlightVO.setFlightDate(operationalFlightVO.getFlightDate());
		assignedFlightVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		assignedFlightVO.setFlightSequenceNumber(operationalFlightVO
				.getFlightSequenceNumber());
		assignedFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
		assignedFlightVO.setLegSerialNumber(operationalFlightVO
				.getLegSerialNumber());
		AssignedFlight assignedFlight = new AssignedFlight(assignedFlightVO);

		AssignedFlightAuditVO assignedFlightAuditVO = new AssignedFlightAuditVO(
				AssignedFlightVO.MODULE, AssignedFlightVO.SUBMODULE,
				AssignedFlightVO.ENTITY);
		assignedFlightAuditVO.setAdditionalInformation("Flight Created");
		performAssignedFlightAudit(assignedFlightAuditVO, assignedFlight,
				MailConstantsVO.AUDIT_FLT_CREAT);
	}

	/**
	 * @author A-1936
	 * @param assignedFlightAuditVO
	 * @param assignedFlight
	 * @param actionCode
	 * @throws SystemException
	 */
	private void performAssignedFlightAudit(
			AssignedFlightAuditVO assignedFlightAuditVO,
			AssignedFlight assignedFlight, String actionCode)
			throws SystemException {
		AssignedFlightPK assignedFlightPK = assignedFlight
				.getAssignedFlightPk();
		assignedFlightAuditVO.setCompanyCode(assignedFlightPK.getCompanyCode());
		assignedFlightAuditVO.setAirportCode(assignedFlightPK.getAirportCode());
		assignedFlightAuditVO.setFlightNumber(assignedFlightPK
				.getFlightNumber());
		assignedFlightAuditVO.setFlightSequenceNumber(assignedFlightPK
				.getFlightSequenceNumber());
		assignedFlightAuditVO.setCarrierId(assignedFlightPK.getCarrierId());
		assignedFlightAuditVO.setLegSerialNumber(assignedFlightPK
				.getLegSerialNumber());
		assignedFlightAuditVO.setActionCode(actionCode);

		AuditUtils.performAudit(assignedFlightAuditVO);
		log.exiting("DSN", "performAssignedFlightAudit");
	}

	/**
	 * @author a-1936 This method is used to create a new Container in the
	 *         System
	 * @param containerVo
	 * @throws SystemException
	 * @throws FinderException 
	 */
	private Container createContainer(ContainerVO containerVo)
			throws SystemException {
		String triggeringPoint = ContextUtils.getRequestContext().getParameter("REQ_TRIGGERPOINT");
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(containerVo.getCompanyCode());
		containerPk.setContainerNumber(containerVo.getContainerNumber());
		containerPk.setAssignmentPort(containerVo.getAssignedPort());
		containerPk.setFlightNumber(containerVo.getFlightNumber());
		containerPk.setCarrierId(containerVo.getCarrierId());
		containerPk.setFlightSequenceNumber(containerVo
				.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(containerVo.getLegSerialNumber());

		Container container = null;
		try {
			container = Container.find(containerPk);
			if (MailConstantsVO.FLAG_YES.equals(container.getArrivedStatus())) {
				containerVo.setArrivedStatus(MailConstantsVO.FLAG_YES);
			}
			//Added as part of bug ICRD-241542 by A-5526 starts
			if(!container.getContainerType().equals(containerVo.getType())){
				containerVo.setType(container.getContainerType())	;                 
			}
			//Added as part of bug ICRD-241542 by A-5526 ends
			container.update(containerVo);
		} catch (FinderException ex) {
			container = new Container(containerVo);
			MailController mailController = (MailController) SpringAdapter.getInstance().getBean(MAIL_CONTROLLER);
			if(!"B".equals(containerVo.getType()))	{
			 mailController.flagMLDForMailOperationsInULD(containerVo,MailConstantsVO.MLD_STG);
			}
          } 

		ContainerAuditVO containerAuditVO = new ContainerAuditVO(
				ContainerVO.MODULE, ContainerVO.SUBMODULE, ContainerVO.ENTITY);
		containerAuditVO = (ContainerAuditVO) AuditUtils.populateAuditDetails(
				containerAuditVO, container, true);
		collectContainerAuditDetails(container, containerAuditVO);
		StringBuilder additInfo = new StringBuilder();
		if (containerVo.isOffload()) {
			additInfo.append("Offloaded from ");
		}else if (containerVo.isTransferAudit()) {
			additInfo.append("Transferred to ");
		}else{
			additInfo.append("Reassigned to ");
		 }
		if(containerVo.isOffload()){
		if(!"-1".equals(containerVo.getFromFltNum())){
			additInfo.append(container.getCarrierCode()).append(" ").append(containerVo.getFromFltNum()).append(", ");
		}else{
			additInfo.append(container.getCarrierCode()).append(", ");
			}
		}else{
			if(!"-1".equals(container.getContainerPK().getFlightNumber())){
				additInfo.append(container.getCarrierCode()).append(" ").append(container.getContainerPK().getFlightNumber()).append(", ");
			}else{
				additInfo.append(container.getCarrierCode()).append(", ");
				}
		}
		additInfo.append(new LocalDate(containerPk.getAssignmentPort(), Location.ARP, true).toDisplayDateOnlyFormat()).append(", ");
		if(containerVo.isOffload()){
		if(!"-1".equals(containerVo.getFromFltNum())){
		additInfo.append(containerPk.getAssignmentPort()).append(" - ").append(containerVo.getPrevFlightPou()).append(" ");
			}
		}else{
			if(!"-1".equals(container.getContainerPK().getFlightNumber())){
				additInfo.append(containerPk.getAssignmentPort()).append(" - ").append(containerVo.getPou()).append(" ");
			}
		}
		additInfo.append("in ").append(containerPk.getAssignmentPort());
		containerAuditVO.setAdditionalInformation(additInfo.toString());
		containerAuditVO.setTriggerPnt(triggeringPoint);
		// Added By Karthick .V to identify the Business involved During the
		// CreateOperation
		if (containerVo.isOffload()) {
			containerAuditVO.setActionCode(MailConstantsVO.CONTAINER_OFFLOAD);
			containerAuditVO.setAuditRemarks(MailConstantsVO.MAIL_ULD_OFFLOAD);
		}else if (containerVo.isTransferAudit()) {
			containerAuditVO.setActionCode(MailConstantsVO.CONTAINER_TRANSFER);
			containerAuditVO.setAuditRemarks(MailConstantsVO.MAIL_ULD_TRANSFER);
		} else {
			containerAuditVO.setActionCode(MailConstantsVO.CONTAINER_REASSIGN);
			containerAuditVO.setAuditRemarks(MailConstantsVO.MAIL_ULD_REASSIGN);
		}

		AuditUtils.performAudit(containerAuditVO);
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
	private ULDForSegmentPK constructULDForSegmentPK(String companyCode,
			String uldNumber, int carrierId, String flightNumber,
			long flightSequenceNumber, int segmentSerialNumber) {
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
	 * This method assignes a group of containers from a flight to a flight
	 * A-1739
	 *
	 * @param flightAssignedContainers
	 * @param toFlightVO
	 * @param toFlightSegSerialNo
	 * @throws SystemException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	 Collection<ContainerVO> reassignContainerFromFlightToFlight(
			Collection<ContainerVO> flightAssignedContainers,
			OperationalFlightVO toFlightVO, int toFlightSegSerialNo)
			throws SystemException, CapacityBookingProxyException,
			MailBookingException {
		log.entering("ReassignController", "reassignFromFlightToFlight");

		Collection<ContainerVO> paBuiltContainers = new ArrayList<ContainerVO>();
		Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>();
		

		for (ContainerVO flightAssignedContainerVO : flightAssignedContainers) {
			boolean isNotAccepted = true;
			boolean isReassignSuccess = false;
			Collection<MailbagVO> mailbagForResdit = new ArrayList<MailbagVO>();
			int numberOfBarrowspresent = 0;
			if (CONST_YES.equals(flightAssignedContainerVO.getAcceptanceFlag())) {
				isNotAccepted = false;

				String fromULDNumber = flightAssignedContainerVO
						.getContainerNumber();

				// If the container is Bulk type the the uldNumber is
				// constructed as
				
				// AssignedFlightSegment
				AssignedFlightSegment fromAssignedFlightSegment = null;
				try {
					fromAssignedFlightSegment = findAssignedFlightSegment(
							flightAssignedContainerVO.getCompanyCode(),
							flightAssignedContainerVO.getCarrierId(),
							flightAssignedContainerVO.getFlightNumber(),
							flightAssignedContainerVO.getFlightSequenceNumber(),
							flightAssignedContainerVO.getSegmentSerialNumber());
				} catch (FinderException ex) {
					throw new SystemException(ex.getMessage(), ex);
				}
				// BULK-{the pou}
				if (MailConstantsVO.BULK_TYPE.equals(flightAssignedContainerVO
						.getType())) {
					fromULDNumber = constructBulkULDNumber(
							fromAssignedFlightSegment.getPou(),
							flightAssignedContainerVO.getCarrierCode());
				}

				/*
				 * For both bulk and ULD this gives the from assignment details.
				 * Only that the operations on this entity changes
				 */
				ULDForSegment uldForSegment = null;
				try {
					uldForSegment = AssignedFlightSegment
							.findULDForSegment(constructULDForSegmentPK(
									flightAssignedContainerVO.getCompanyCode(),
									fromULDNumber,
									flightAssignedContainerVO.getCarrierId(),
									flightAssignedContainerVO.getFlightNumber(),
									flightAssignedContainerVO
											.getFlightSequenceNumber(),
									flightAssignedContainerVO
											.getSegmentSerialNumber()));
				} catch (FinderException finderException) {
					// data inconsistency
					throw new SystemException(finderException.getMessage(),
							finderException);
				}

				/*
				 * This is used to get the final assgnment details of the ULD
				 */
				ULDForSegmentVO uldForSegmentVO = null;

				

				AssignedFlightSegment toAssignedFlightSegment = null;
				try {
					toAssignedFlightSegment = findAssignedFlightSegment(
							toFlightVO.getCompanyCode(),
							toFlightVO.getCarrierId(),
							toFlightVO.getFlightNumber(),
							toFlightVO.getFlightSequenceNumber(),
							toFlightSegSerialNo);
				} catch (FinderException ex) {
					throw new SystemException(ex.getMessage(), ex);
				}

				/*
				 * In case of bulk container this is used
				 */
				String containerNumber = flightAssignedContainerVO
						.getContainerNumber();

				/*
				 * In case of bulk container only move the DSNInULDForSegment to
				 * the toULDForSegment. No remove of this ULDForSegment
				 */
				

				Collection<MailbagInULDForSegmentVO> dsnsToReassign = null;

				if (MailConstantsVO.ULD_TYPE.equals(flightAssignedContainerVO
						.getType())) {
					uldForSegmentVO = uldForSegment.retrieveVO();
					double actualWgtCal = 0.0;
					//Modified by a-7779 for IASCB-46530
					actualWgtCal  = calculateActualWeightForULD(uldForSegmentVO,flightAssignedContainerVO,fromULDNumber);
								flightAssignedContainerVO
										.setActualWeight(new Measure(UnitConstants.MAIL_WGT, actualWgtCal));
					// For CR :AirNZ404 : Mail Allocation STARTS

					ULDForSegmentVO fromULDForSegVO = uldForSegment
							.retrieveVO();
					fromULDForSegVO.setPou(flightAssignedContainerVO.getPou());
					fromULDForSegVO.setCarrierId(flightAssignedContainerVO
							.getCarrierId());
					fromULDForSegVO.setFlightNumber(flightAssignedContainerVO
							.getFlightNumber());
					fromULDForSegVO.setFlightDate(flightAssignedContainerVO
							.getFlightDate());
					fromULDForSegVO
							.setFlightSequenceNumber(flightAssignedContainerVO
									.getFlightSequenceNumber());
					fromULDForSegVO
							.setSegmentSerialNumber(flightAssignedContainerVO
									.getSegmentSerialNumber());
					// Updating Booking Details
					updateBookingForFlight(fromULDForSegVO, null,
							"UPDATE_BOOKING_FROM_FLIGHT");
					// CR :AirNZ404 :Changes ENDS

					fromAssignedFlightSegment
							.removeULDForSegment(uldForSegment);

					// update the flightdetails of all the despatches and mail
					// bags in this container
					// need calrification

					updateULDForSegmentVO(uldForSegmentVO, toFlightVO,
							flightAssignedContainerVO, toFlightSegSerialNo);

					log.log(Log.FINEST,
							"retrieved VO for ULD after flight update ---------> ",
							uldForSegmentVO);

					uldForSegmentVO.setPou(toFlightVO.getPou());
					uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
					

					
						uldForSegmentVO.setFlightNumber(toFlightVO
								.getFlightNumber());
						uldForSegmentVO.setFlightDate(toFlightVO.getFlightDate());
						uldForSegmentVO.setFlightSequenceNumber(toFlightVO
								.getFlightSequenceNumber());
						uldForSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
						String bulkNumber =null;
						//Added by A-8893 for IASCB-38903 starts
						if(flightAssignedContainerVO.isUldTobarrow()){
								 bulkNumber = constructBulkULDNumber(
										flightAssignedContainerVO.getPou(),
										flightAssignedContainerVO.getCarrierCode());
								 if(bulkNumber!=null){
										uldForSegmentVO.setUldNumber(bulkNumber);
									}
									} 
							
						//Added by A-8893 for IASCB-38903 ends
						
						if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null
								&& !uldForSegmentVO.getMailbagInULDForSegmentVOs().isEmpty()) {
							uldForSegmentVO.getMailbagInULDForSegmentVOs()
									.forEach(mailbagInULDForSegmentVO -> mailbagInULDForSegmentVO
											.setGhttim(flightAssignedContainerVO.getGHTtime()));//IASCB-48967
						}
						
						
					//Added as part of bug ICRD-241542 by A-5526 starts
					
					try {
						ULDForSegment toULDForSegment = AssignedFlightSegment
								.findULDForSegment(constructULDForSegmentPK(
										toFlightVO.getCompanyCode(),
										uldForSegmentVO.getUldNumber(), toFlightVO.getCarrierId(),
										toFlightVO.getFlightNumber(),
										toFlightVO.getFlightSequenceNumber(),
										toFlightSegSerialNo));
						if(uldForSegmentVO.getMailbagInULDForSegmentVOs()!=null){
							toULDForSegment.populateMailbagDetails(uldForSegmentVO.getMailbagInULDForSegmentVOs());
					        }
					} catch (FinderException e) {
					toAssignedFlightSegment
							.createULDForSegment(uldForSegmentVO);
					 }
					//Added as part of bug ICRD-241542 by A-5526 ends
					
					//Added by A-7794 as part of ICRD-208677
	                boolean isUMSUpdateNeeded = isULDIntegrationEnabled();
	                if (isUMSUpdateNeeded) {
	                	ULDInFlightVO uldFltVo = new ULDInFlightVO();
	         			uldFltVo.setUldNumber(flightAssignedContainerVO.getContainerNumber());
	         			if(flightAssignedContainerVO.getPol() != null){
	         				uldFltVo.setPointOfLading(flightAssignedContainerVO.getPol());
	         			}else{
	         				uldFltVo.setPointOfLading(flightAssignedContainerVO.getAssignedPort());
	         			}
	         			uldFltVo.setPointOfUnLading(flightAssignedContainerVO.getPou());
	         			//Modified by A-7794 as part of ICRD-226708
	         			uldFltVo.setRemark(flightAssignedContainerVO.getRemarks());
	                	AssignedFlightSegment.updateULDForOperationsForContainerAcceptance(toFlightVO , uldFltVo);
	        		}

					// For CR :AirNZ404 : Mail Allocation STARTS

					// Updating Booking Details
					updateBookingForFlight(uldForSegmentVO, toFlightVO,
							"UPDATE_BOOKING_TO_FLIGHT");
					// CR :AirNZ404 :Changes ENDS

					isReassignSuccess = true;

				} else if (MailConstantsVO.BULK_TYPE
						.equals(flightAssignedContainerVO.getType())) {

					// For CR :AirNZ404 : Mail Allocation STARTS
					ULDForSegmentVO fromULDForSegVO = uldForSegment
							.retrieveVO();
					fromULDForSegVO.setPou(flightAssignedContainerVO.getPou());
					fromULDForSegVO.setCarrierId(flightAssignedContainerVO
							.getCarrierId());
					fromULDForSegVO.setFlightNumber(flightAssignedContainerVO
							.getFlightNumber());
					fromULDForSegVO.setFlightDate(flightAssignedContainerVO
							.getFlightDate());
					fromULDForSegVO
							.setFlightSequenceNumber(flightAssignedContainerVO
									.getFlightSequenceNumber());
					fromULDForSegVO
							.setSegmentSerialNumber(flightAssignedContainerVO
									.getSegmentSerialNumber());
					// Updating Booking Details of Current Flight
					updateBookingForFlight(fromULDForSegVO, null,
							"UPDATE_BOOKING_FROM_FLIGHT");
					// CR :AirNZ404 :Changes ENDS

					// find the current entities of this container
					String toULDNumber=null;
					if(flightAssignedContainerVO.isBarrowToUld()){
						toULDNumber=flightAssignedContainerVO.getContainerNumber();
						
					}else{
						toULDNumber = constructBulkULDNumber(
							toFlightVO.getPou(), toFlightVO.getCarrierCode());
						
					}
					dsnsToReassign = fromAssignedFlightSegment
							.reassignBulkContainer(uldForSegment,
									containerNumber);
					double actualWgtCal = 0.0;
					//Modified by A-7779 for IASCB-46530
					actualWgtCal = calculateActualWeightForBulk(dsnsToReassign,flightAssignedContainerVO);
								flightAssignedContainerVO
										.setActualWeight(new Measure(UnitConstants.MAIL_WGT, actualWgtCal));
					if (uldForSegment.getNumberOfBags() == 0) {
						// This method is to find the number of bulks present in
						// the flight .Added by A--5945 for ICRD-128827
						try {
							numberOfBarrowspresent = AssignedFlightSegment
									.findNumberOfBarrowsPresentinFlightorCarrier(flightAssignedContainerVO);
						} catch (SystemException e) {
							log.log(Log.FINE, e.getMessage());
						}
						log.log(Log.FINEST, "numberOfBarrowspresent-- > ",
								numberOfBarrowspresent);
						/*
						 * Earlier even if there are empty bulks present in the
						 * flights, the entry from MTKULDSEG got deleted. Hence
						 * when we try to add a new mailbag to the empty bulk ,
						 * internal server error wolud come. So here we are
						 * checking if there are more than one bulk in the
						 * flight . If there are more than one bulk in the
						 * flight, no need to remove the entry from MTKULDSEG
						 */
						if (numberOfBarrowspresent == 1) {
							log.log(Log.FINE,
									"reassing bulk  removing bulk uld since cnt 0");
							fromAssignedFlightSegment
									.removeULDForSegment(uldForSegment);
						}

					}
					log.log(Log.FINEST, "dsnsToReassing -- > ", dsnsToReassign);

					/*
					 * After removing the DNSInCOntainer, its VO will be
					 * returned
					 */
					// Commented as part of BUG ICRD-104507 by A-5526
					// if (dsnsToReassign != null && dsnsToReassign.size() > 0)
					// {

					// create new Container
					/*
					 * For this DSN find its corresponding Segment, then add to
					 * that segment
					 */
					
					if(dsnsToReassign!=null&&!dsnsToReassign.isEmpty()){
						dsnsToReassign.forEach(dsnToReassign->dsnToReassign.setGhttim(flightAssignedContainerVO.getGHTtime()));//IASCB-48967
					}
					
					
					try {
						if(flightAssignedContainerVO.isBarrowToUld()){
							uldForSegmentVO = new ULDForSegmentVO();
							uldForSegmentVO.setUldNumber(toULDNumber);
							uldForSegmentVO.setMailbagInULDForSegmentVOs(dsnsToReassign);
									
							updateULDForSegmentVO(uldForSegmentVO, toFlightVO,
									flightAssignedContainerVO, toFlightSegSerialNo);
							log.log(Log.FINEST,
									"uldforsegment retrieved for bulk --> ",
									uldForSegmentVO);
							toAssignedFlightSegment
									.createULDForSegment(uldForSegmentVO);
							// For CR :AirNZ404 : Mail Allocation STARTS
							uldForSegmentVO.setPou(toFlightVO.getPou());
							uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
							uldForSegmentVO.setFlightNumber(toFlightVO
									.getFlightNumber());
							uldForSegmentVO.setFlightDate(toFlightVO
									.getFlightDate());
							uldForSegmentVO.setFlightSequenceNumber(toFlightVO
									.getFlightSequenceNumber());
							uldForSegmentVO
									.setSegmentSerialNumber(toFlightSegSerialNo);
							// Updating Booking Details of Current Flight
							updateBookingForFlight(uldForSegmentVO, toFlightVO,
									"UPDATE_BOOKING_TO_FLIGHT");
							// CR :AirNZ404 :Changes ENDS
							isReassignSuccess = true;
						}else{
						ULDForSegment toULDForSegment = AssignedFlightSegment
								.findULDForSegment(constructULDForSegmentPK(
										toFlightVO.getCompanyCode(),
										toULDNumber, toFlightVO.getCarrierId(),
										toFlightVO.getFlightNumber(),
										toFlightVO.getFlightSequenceNumber(),
										toFlightSegSerialNo));

						toAssignedFlightSegment.assignBulkContainer(
								toULDForSegment, dsnsToReassign);

						// For CR :AirNZ404 : Mail Allocation STARTS
						ULDForSegmentVO toULDForSegVO = toULDForSegment
								.retrieveVO();
						toULDForSegVO.setPou(toFlightVO.getPou());
						toULDForSegVO.setCarrierId(toFlightVO.getCarrierId());
						toULDForSegVO.setFlightNumber(toFlightVO
								.getFlightNumber());
						toULDForSegVO.setFlightDate(toFlightVO.getFlightDate());
						toULDForSegVO.setFlightSequenceNumber(toFlightVO
								.getFlightSequenceNumber());
						toULDForSegVO
								.setSegmentSerialNumber(toFlightSegSerialNo);
						// Updating Booking Details of Current Flight
						updateBookingForFlight(toULDForSegVO, toFlightVO,
								"UPDATE_BOOKING_TO_FLIGHT");
						// CR :AirNZ404 :Changes ENDS

						isReassignSuccess = true;
						}
					} catch (FinderException finderException) {
						log.log(Log.SEVERE,
								"finder exception for bulk container",
								fromULDNumber);
						log.log(Log.FINE, "creating new container");

						uldForSegmentVO = new ULDForSegmentVO();
						uldForSegmentVO.setUldNumber(toULDNumber);
						uldForSegmentVO.setMailbagInULDForSegmentVOs(dsnsToReassign);
								
						updateULDForSegmentVO(uldForSegmentVO, toFlightVO,
								flightAssignedContainerVO, toFlightSegSerialNo);

						log.log(Log.FINEST,
								"uldforsegment retrieved for bulk --> ",
								uldForSegmentVO);
						toAssignedFlightSegment
								.createULDForSegment(uldForSegmentVO);

						// For CR :AirNZ404 : Mail Allocation STARTS
						uldForSegmentVO.setPou(toFlightVO.getPou());
						uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
						uldForSegmentVO.setFlightNumber(toFlightVO
								.getFlightNumber());
						uldForSegmentVO.setFlightDate(toFlightVO
								.getFlightDate());
						uldForSegmentVO.setFlightSequenceNumber(toFlightVO
								.getFlightSequenceNumber());
						uldForSegmentVO
								.setSegmentSerialNumber(toFlightSegSerialNo);
						// Updating Booking Details of Current Flight
						updateBookingForFlight(uldForSegmentVO, toFlightVO,
								"UPDATE_BOOKING_TO_FLIGHT");
						// CR :AirNZ404 :Changes ENDS

						isReassignSuccess = true;
					}
					// }//Commented as part of BUG ICRD-104507 by A-5526
				}
				if (isReassignSuccess) {
					if (dsnsToReassign == null) {
						dsnsToReassign = uldForSegmentVO
								.getMailbagInULDForSegmentVOs();
					}
					if (dsnsToReassign!= null
							&& dsnsToReassign
									.size() > 0) {
						//for(MailbagInULDForSegmentVO dsnInULDForSegmentVO:dsnsToReassign){
//							if(dsnInULDForSegmentVO.get!=null
//									&&dsnInULDForSegmentVO.getMailBags().size()>0){
						Collection<MailbagVO> mailbagVOs = constructMailBagVOFromSeg(
								dsnsToReassign,
								toFlightVO.getCompanyCode());

						// log.log(Log.FINE, "THE DSNS TO FLAG ", dsnVOs);
						updateDSNForConReassign(mailbagVOs, toFlightVO,
								flightAssignedContainerVO, toFlightSegSerialNo,
								null);

						/*
						 * updateDSNSegment(dsnVOs, flightAssignedContainerVO,
						 * toFlightVO, toFlightSegSerialNo,
						 * fromAssignedFlightSegment, toAssignedFlightSegment);
						 */

						// log.log(Log.FINE, "THE DSNS TO FLAG ", dsnVOs);
						mailbagForResdit.addAll(mailbagVOs);
						//}
					//}
					}
					MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					mailController.flagHistoryForContainerReassignment(toFlightVO,flightAssignedContainerVO,mailbagForResdit);
					mailController.flagAuditForContainerReassignment(toFlightVO,flightAssignedContainerVO,mailbagForResdit);					
					//importMRAData
					boolean provisionalRateImport =false;
					toFlightVO.setSegSerNum(toFlightSegSerialNo);//ICRD-304364
					Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(toFlightVO ,flightAssignedContainerVO,mailbagForResdit,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL, provisionalRateImport) ;
					log.log(Log.FINEST, "RateAuditVO-->", rateAuditVOs);
					 if(rateAuditVOs!=null && !rateAuditVOs.isEmpty()){
						 String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
		  		        	if(importEnabled!=null && importEnabled.contains("M")){
			        try {
						new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
					} catch (ProxyException e) {
						throw new SystemException(e.getMessage(), e);
					}
					}
					 }
					// import Provisonal rate Data to malmraproint for upront rate Calculation
						String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
						if( MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
							provisionalRateImport = true;
				      	Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(toFlightVO ,flightAssignedContainerVO,mailbagForResdit,MailConstantsVO.MAIL_STATUS_REASSIGNMAIL,provisionalRateImport) ;
				      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
				        try {
				        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
					} catch (ProxyException e) {
						throw new SystemException(e.getMessage(), e);
					}
					}
					 }
			        
				}
			}
			if (isReassignSuccess || isNotAccepted) {
				updateReassignedContainer(flightAssignedContainerVO,
						toFlightVO, toFlightSegSerialNo);
				if (isReassignSuccess) {
					if (MailConstantsVO.FLAG_YES
							.equals(flightAssignedContainerVO.getPaBuiltFlag())) {
						paBuiltContainers.add(flightAssignedContainerVO);
					}
				}
			}
			if (paBuiltContainers.size() > 0 || mailbagForResdit.size() > 0) {
				flagResditsForContainerReassign(mailbagForResdit,
						paBuiltContainers, toFlightVO, toFlightSegSerialNo);
			}
			ContainerVO containerReturnVO = new ContainerVO();
			BeanHelper.copyProperties(containerReturnVO,
					flightAssignedContainerVO);
			containerReturnVO.setFlightSequenceNumber(toFlightVO
					.getFlightSequenceNumber());
			containerReturnVO.setFlightNumber(toFlightVO.getFlightNumber());
			containerReturnVO.setLegSerialNumber(toFlightVO
					.getLegSerialNumber());
			containerReturnVO.setSegmentSerialNumber(toFlightSegSerialNo);
			containerReturnVO.setCarrierCode(toFlightVO.getCarrierCode());
			containerReturnVO.setFlightDate(toFlightVO.getFlightDate());
			containersForReturn.add(containerReturnVO);
		}
		
		log.exiting("ReassignController", "reassignFromFlightToFlight");
		return containersForReturn;
	}
	/**
	 * @author A-7779
	 * @param dsnsToReassign
	 * @param flightAssignedContainerVO
	 * @return
	 */
	private double calculateActualWeightForBulk(
			Collection<MailbagInULDForSegmentVO> dsnsToReassign,ContainerVO flightAssignedContainerVO) {
		double actualWgtCal = 0.0;
		log.entering("ReassignController", "calculateActualWeightForBulk");
		Map airportParameters = null;
		SharedAreaProxy sharedAreaProxy = new SharedAreaProxy();
		try {
			airportParameters = sharedAreaProxy.findAirportParametersByCode(
					flightAssignedContainerVO.getCompanyCode(), flightAssignedContainerVO.getAssignedPort(),
					getAirportParameterCodes());
		} catch (SystemException e) {
			e.getMessage();
		}
		if ((airportParameters != null && airportParameters.size() > 0
				&& !("Y".equals((String) airportParameters.get(WEIGHT_SCALE_AVAILABLE))))
				|| airportParameters.size() == 0) {
			if (dsnsToReassign != null && dsnsToReassign.size() > 0) {
					for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : dsnsToReassign) {
						if (mailbagInULDForSegmentVO.getWeight() != null) {
							actualWgtCal += mailbagInULDForSegmentVO.getWeight().getSystemValue();
						}
				}
			}
		
		if(flightAssignedContainerVO.isBarrowToUld()){
			String fromULDNumber = flightAssignedContainerVO.getContainerNumber();
			ULDVO uldVO = null;
			try {
				uldVO = new ULDDefaultsProxy().findULDDetails(flightAssignedContainerVO.getCompanyCode(), fromULDNumber);
			} catch (ULDDefaultsProxyException e1) {
				log.log(Log.FINE, "ULDDefaultsProxyException");
			} catch (SystemException e1) {
				log.log(Log.FINE, "SystemException");
			}
			Measure actualTareWeight = null;
			if(uldVO!=null){
				actualTareWeight = uldVO.getTareWeight();
			}else{
			ULDValidationFilterVO uLDValidationFilterVO = new ULDValidationFilterVO();
			uLDValidationFilterVO.setCompanyCode(flightAssignedContainerVO.getCompanyCode());
			uLDValidationFilterVO.setUldTypeCode(fromULDNumber.substring(0, 3));
			uLDValidationFilterVO
					.setSerialNumber(fromULDNumber.substring(3, fromULDNumber.length() - 2));
			uLDValidationFilterVO.setUldAirlineCode(
					fromULDNumber.substring(fromULDNumber.length() - 2, fromULDNumber.length()));
			uLDValidationFilterVO.setUldNumber(fromULDNumber);
			
			try {
				actualTareWeight = new SharedULDProxy().findULDTareWeight(uLDValidationFilterVO);
			} catch (SystemException e) {
				e.getMessage();
			} catch (SharedProxyException e) {
				e.getMessage();
			}
			}
			if (actualTareWeight != null) {
				actualWgtCal += actualTareWeight.getSystemValue();
			}
		}
		//Added by A-8527 For IASCB-49670 starts
		}
		else if(flightAssignedContainerVO.getActualWeight()!=null){
			actualWgtCal=flightAssignedContainerVO.getActualWeight().getSystemValue();
			}
		
		//Added by A-8527 For IASCB-49670 Ends
		log.exiting("ReassignController", "calculateActualWeightForBulk");
		return actualWgtCal;
	}
	/**
	 * @author A-7779
	 * @param uldForSegmentVO
	 * @param flightAssignedContainerVO
	 * @return
	 */
	private double calculateActualWeightForULD(ULDForSegmentVO uldForSegmentVO,
			ContainerVO flightAssignedContainerVO, String fromULDNumber) {
		log.entering("ReassignController", "calculateActualWeightForULD");
		double actualWgtCal = 0.0;
		Map airportParameters = null;
		SharedAreaProxy sharedAreaProxy = new SharedAreaProxy();
		try {
			airportParameters = Proxy.getInstance().get(SharedAreaProxy.class).findAirportParametersByCode(
					flightAssignedContainerVO.getCompanyCode(), flightAssignedContainerVO.getAssignedPort(),
					getAirportParameterCodes());
		} catch (SystemException e) {
			e.getMessage();
		}
		if ((airportParameters != null && airportParameters.size() > 0
				&& !("Y".equals((String) airportParameters.get(WEIGHT_SCALE_AVAILABLE))))
				|| airportParameters.size() == 0) {
			//Moved code from above to inside condition by A-8527 For IASCB-49670 starts
		if (uldForSegmentVO.getMailbagInULDForSegmentVOs() != null
				&& uldForSegmentVO.getMailbagInULDForSegmentVOs().size() > 0) {
			Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs = new ArrayList<MailbagInULDForSegmentVO>();
			for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : uldForSegmentVO
					.getMailbagInULDForSegmentVOs()) {
				if (uldForSegmentVO != null && uldForSegmentVO.getUldNumber()
						.equals(mailbagInULDForSegmentVO.getContainerNumber())) {
					if (mailbagInULDForSegmentVO.getWeight() != null) {
						actualWgtCal  +=mailbagInULDForSegmentVO.getWeight().getSystemValue();
					}
					mailbagInULDForSegmentVOs.add(mailbagInULDForSegmentVO);
				}
			}
			uldForSegmentVO.setMailbagInULDForSegmentVOs(mailbagInULDForSegmentVOs);
		}
			//Moved code from above to inside condition by A-8527 For IASCB-49670 Ends
			if (!flightAssignedContainerVO.isUldTobarrow()) {
				Measure actualTareWeight = null;
				ULDVO uldVO = null;
				try {
					uldVO = new ULDDefaultsProxy().findULDDetails(flightAssignedContainerVO.getCompanyCode(), fromULDNumber);
				} catch (ULDDefaultsProxyException e1) {
					log.log(Log.FINE, "ULDDefaultsProxyException");
				} catch (SystemException e1) {
					log.log(Log.FINE, "SystemException");
				}
				if(uldVO!=null){
					actualTareWeight = uldVO.getTareWeight();
				}else{
				ULDValidationFilterVO uLDValidationFilterVO = new ULDValidationFilterVO();
				uLDValidationFilterVO.setCompanyCode(flightAssignedContainerVO.getCompanyCode());
				uLDValidationFilterVO.setUldTypeCode(fromULDNumber.substring(0, 3));
				uLDValidationFilterVO
						.setSerialNumber(fromULDNumber.substring(3, fromULDNumber.length() - 2));
				uLDValidationFilterVO.setUldAirlineCode(
						fromULDNumber.substring(fromULDNumber.length() - 2, fromULDNumber.length()));
				uLDValidationFilterVO.setUldNumber(fromULDNumber);
				
				try {
					actualTareWeight = new SharedULDProxy().findULDTareWeight(uLDValidationFilterVO);
				} catch (SystemException e) {
					e.getMessage();
				} catch (SharedProxyException e) {
					e.getMessage();
				}
				}
				if (actualTareWeight != null) {
					actualWgtCal += actualTareWeight.getSystemValue();
				}
			}
		}
		//Added by A-8527 For IASCB-49670 starts
		else if(flightAssignedContainerVO.getActualWeight()!=null){
			actualWgtCal=flightAssignedContainerVO.getActualWeight().getSystemValue();
			}
		
		//Added by A-8527 For IASCB-49670 Ends
		log.exiting("ReassignController", "calculateActualWeightForULD");
		return actualWgtCal;
	}

	/**
	 * This Method will create the Coll. Of Mailbags & Despatches from
	 * uldForSegmentVO and will eventually Update the booking made for a flight.
	 *
	 * @author A-3227 RENO K ABRAHAM, Added on 22/09/08
	 * @param uldForSegmentVO
	 * @param pol
	 * @param updateFlight
	 * @throws CapacityBookingProxyException
	 * @throws SystemException
	 * @throws MailBookingException
	 */
	public void updateBookingForFlight(ULDForSegmentVO uldForSegmentVO,
			OperationalFlightVO toFlightVO, String updateFlight)
			throws CapacityBookingProxyException, SystemException,
			MailBookingException {
		log.entering("ReassignController", "updateBookingForFlight");
		Collection<MailbagVO> flightAssignedMailbags = new ArrayList<MailbagVO>();
		Collection<DespatchDetailsVO> flightAssignedDespatches = new ArrayList<DespatchDetailsVO>();
		if (uldForSegmentVO != null) {
			Collection<DSNInULDForSegmentVO> dsnInULDForSegmentVOs = uldForSegmentVO
					.getDsnInULDForSegmentVOs();
			if (dsnInULDForSegmentVOs != null
					&& dsnInULDForSegmentVOs.size() > 0) {
				for (DSNInULDForSegmentVO dsNInULDForSegmentVO : dsnInULDForSegmentVOs) {
					if (dsNInULDForSegmentVO.getMailBags() != null
							&& dsNInULDForSegmentVO.getMailBags().size() > 0) {
						for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : dsNInULDForSegmentVO
								.getMailBags()) {
							MailbagVO mailbagVO = new MailbagVO();
							mailbagVO
									.setAcceptanceFlag(mailbagInULDForSegmentVO
											.getAcceptanceFlag());
							mailbagVO.setArrivedFlag(mailbagInULDForSegmentVO
									.getArrivalFlag());
							mailbagVO.setMailClass(mailbagInULDForSegmentVO
									.getMailClass());
							mailbagVO.setMailbagId(mailbagInULDForSegmentVO
									.getMailId());
							mailbagVO.setScannedDate(mailbagInULDForSegmentVO
									.getScannedDate());
							mailbagVO.setScannedPort(mailbagInULDForSegmentVO
									.getScannedPort());
							mailbagVO.setWeight(mailbagInULDForSegmentVO
									.getWeight());
							mailbagVO.setDoe(dsNInULDForSegmentVO
									.getDestinationOfficeOfExchange());
							mailbagVO
									.setDespatchSerialNumber(dsNInULDForSegmentVO
											.getDsn());
							mailbagVO.setMailCategoryCode(dsNInULDForSegmentVO
									.getMailCategoryCode());
							mailbagVO.setMailSubclass(dsNInULDForSegmentVO
									.getMailSubclass());
							mailbagVO.setOoe(dsNInULDForSegmentVO
									.getOriginOfficeOfExchange());
							mailbagVO.setYear(dsNInULDForSegmentVO.getYear());
							mailbagVO.setCarrierId(uldForSegmentVO
									.getCarrierId());
							mailbagVO.setCompanyCode(uldForSegmentVO
									.getCompanyCode());
							mailbagVO.setFlightNumber(uldForSegmentVO
									.getFlightNumber());
							mailbagVO.setFlightDate(uldForSegmentVO
									.getFlightDate());
							mailbagVO.setFlightSequenceNumber(uldForSegmentVO
									.getFlightSequenceNumber());
							mailbagVO.setSegmentSerialNumber(uldForSegmentVO
									.getSegmentSerialNumber());
							mailbagVO.setPou(uldForSegmentVO.getPou());
							if (toFlightVO != null) {
								if (toFlightVO.getPol() != null
										&& toFlightVO.getPol().trim().length() > 0) {
									mailbagVO.setPol(toFlightVO.getPol());
								}
								if (toFlightVO.getCarrierCode() != null
										&& toFlightVO.getCarrierCode().trim()
												.length() > 0) {
									mailbagVO.setCarrierCode(toFlightVO
											.getCarrierCode());
								}
							}
							mailbagVO.setContainerNumber(uldForSegmentVO
									.getUldNumber());
							mailbagVO.setUldNumber(uldForSegmentVO
									.getUldNumber());
							if (mailbagVO.getContainerNumber() != null
									&& mailbagVO.getContainerNumber().trim()
											.length() > 0) {
								if (mailbagVO.getContainerNumber().startsWith(
										"BULK")) {
									mailbagVO
											.setContainerType(MailConstantsVO.BULK_TYPE);
								} else {
									mailbagVO
											.setContainerType(MailConstantsVO.ULD_TYPE);
								}
							}

							flightAssignedMailbags.add(mailbagVO);
						}
					}
					if (dsNInULDForSegmentVO.getDsnInConsignments() != null
							&& dsNInULDForSegmentVO.getDsnInConsignments()
									.size() > 0) {
						for (DSNInConsignmentForULDSegmentVO dsnInConsignmentForULDSegmentVO : dsNInULDForSegmentVO
								.getDsnInConsignments()) {
							DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
							despatchDetailsVO
									.setAcceptedBags(dsnInConsignmentForULDSegmentVO
											.getAcceptedBags());
							despatchDetailsVO
									.setAcceptedWeight(dsnInConsignmentForULDSegmentVO
											.getAcceptedWeight());
							despatchDetailsVO
									.setAcceptedDate(dsnInConsignmentForULDSegmentVO
											.getAcceptedDate());
							despatchDetailsVO
									.setAcceptedUser(dsnInConsignmentForULDSegmentVO
											.getAcceptedUser());
							despatchDetailsVO
									.setConsignmentDate(dsnInConsignmentForULDSegmentVO
											.getConsignmentDate());
							despatchDetailsVO
									.setConsignmentNumber(dsnInConsignmentForULDSegmentVO
											.getConsignmentNumber());
							despatchDetailsVO
									.setMailClass(dsnInConsignmentForULDSegmentVO
											.getMailClass());
							despatchDetailsVO
									.setPaCode(dsnInConsignmentForULDSegmentVO
											.getPaCode());
							despatchDetailsVO
									.setConsignmentSequenceNumber(dsnInConsignmentForULDSegmentVO
											.getSequenceNumber());
							despatchDetailsVO
									.setStatedBags(dsnInConsignmentForULDSegmentVO
											.getStatedBags());
							despatchDetailsVO
									.setStatedWeight(dsnInConsignmentForULDSegmentVO
											.getStatedWeight());
							despatchDetailsVO
									.setDestinationOfficeOfExchange(dsNInULDForSegmentVO
											.getDestinationOfficeOfExchange());
							despatchDetailsVO.setDsn(dsNInULDForSegmentVO
									.getDsn());
							despatchDetailsVO.setYear(dsNInULDForSegmentVO
									.getYear());
							despatchDetailsVO
									.setMailCategoryCode(dsNInULDForSegmentVO
											.getMailCategoryCode());
							despatchDetailsVO
									.setMailSubclass(dsNInULDForSegmentVO
											.getMailSubclass());
							despatchDetailsVO
									.setOriginOfficeOfExchange(dsNInULDForSegmentVO
											.getOriginOfficeOfExchange());
							despatchDetailsVO
									.setPltEnabledFlag(dsNInULDForSegmentVO
											.getPltEnabledFlag());
							despatchDetailsVO.setCarrierId(uldForSegmentVO
									.getCarrierId());
							despatchDetailsVO.setCompanyCode(uldForSegmentVO
									.getCompanyCode());
							despatchDetailsVO.setFlightNumber(uldForSegmentVO
									.getFlightNumber());
							despatchDetailsVO.setFlightDate(uldForSegmentVO
									.getFlightDate());
							despatchDetailsVO
									.setFlightSequenceNumber(uldForSegmentVO
											.getFlightSequenceNumber());
							despatchDetailsVO
									.setSegmentSerialNumber(uldForSegmentVO
											.getSegmentSerialNumber());
							despatchDetailsVO.setPou(uldForSegmentVO.getPou());
							if (toFlightVO != null) {
								if (toFlightVO.getPol() != null
										&& toFlightVO.getPol().trim().length() > 0) {
									despatchDetailsVO.setAirportCode(toFlightVO
											.getPol());
								}
								if (toFlightVO.getCarrierCode() != null
										&& toFlightVO.getCarrierCode().trim()
												.length() > 0) {
									despatchDetailsVO.setCarrierCode(toFlightVO
											.getCarrierCode());
								}
							}
							despatchDetailsVO.setUldNumber(uldForSegmentVO
									.getUldNumber());
							despatchDetailsVO
									.setContainerNumber(uldForSegmentVO
											.getUldNumber());
							if (despatchDetailsVO.getContainerNumber() != null
									&& despatchDetailsVO.getContainerNumber()
											.trim().length() > 0) {
								if (despatchDetailsVO.getContainerNumber()
										.startsWith("BULK")) {
									despatchDetailsVO
											.setContainerType(MailConstantsVO.BULK_TYPE);
								} else {
									despatchDetailsVO
											.setContainerType(MailConstantsVO.ULD_TYPE);
								}
							}
							flightAssignedDespatches.add(despatchDetailsVO);
						}
					}
					if (dsNInULDForSegmentVO.getDsnInContainerForSegs() != null
							&& dsNInULDForSegmentVO.getDsnInContainerForSegs()
									.size() > 0) {
						for (DSNInContainerForSegmentVO dsnInContainerForSegmentVO : dsNInULDForSegmentVO
								.getDsnInContainerForSegs()) {
							if (dsnInContainerForSegmentVO
									.getDsnInConsignments() != null
									&& dsnInContainerForSegmentVO
											.getDsnInConsignments().size() > 0) {
								for (DSNInConsignmentForContainerSegmentVO dsnInCsgForConSegVO : dsnInContainerForSegmentVO
										.getDsnInConsignments()) {

									DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
									despatchDetailsVO
											.setAcceptedBags(dsnInCsgForConSegVO
													.getAcceptedBags());
									despatchDetailsVO
											.setAcceptedWeight(dsnInCsgForConSegVO
													.getAcceptedWeight());
									despatchDetailsVO
											.setAcceptedDate(dsnInCsgForConSegVO
													.getAcceptedDate());
									despatchDetailsVO
											.setAcceptedUser(dsnInCsgForConSegVO
													.getAcceptedUser());
									despatchDetailsVO
											.setConsignmentDate(dsnInCsgForConSegVO
													.getConsignmentDate());
									despatchDetailsVO
											.setConsignmentNumber(dsnInCsgForConSegVO
													.getConsignmentNumber());
									despatchDetailsVO
											.setMailClass(dsnInCsgForConSegVO
													.getMailClass());
									despatchDetailsVO
											.setPaCode(dsnInCsgForConSegVO
													.getPaCode());
									despatchDetailsVO
											.setConsignmentSequenceNumber(dsnInCsgForConSegVO
													.getSequenceNumber());
									despatchDetailsVO
											.setStatedBags(dsnInCsgForConSegVO
													.getStatedBags());
									despatchDetailsVO
											.setStatedWeight(dsnInCsgForConSegVO
													.getStatedWeight());
									despatchDetailsVO
											.setDestinationOfficeOfExchange(dsNInULDForSegmentVO
													.getDestinationOfficeOfExchange());
									despatchDetailsVO
											.setDsn(dsNInULDForSegmentVO
													.getDsn());
									despatchDetailsVO
											.setYear(dsNInULDForSegmentVO
													.getYear());
									despatchDetailsVO
											.setMailCategoryCode(dsNInULDForSegmentVO
													.getMailCategoryCode());
									despatchDetailsVO
											.setMailSubclass(dsNInULDForSegmentVO
													.getMailSubclass());
									despatchDetailsVO
											.setOriginOfficeOfExchange(dsNInULDForSegmentVO
													.getOriginOfficeOfExchange());
									despatchDetailsVO
											.setPltEnabledFlag(dsNInULDForSegmentVO
													.getPltEnabledFlag());
									despatchDetailsVO
											.setCarrierId(uldForSegmentVO
													.getCarrierId());
									despatchDetailsVO
											.setCompanyCode(uldForSegmentVO
													.getCompanyCode());
									despatchDetailsVO
											.setFlightNumber(uldForSegmentVO
													.getFlightNumber());
									despatchDetailsVO
											.setFlightDate(uldForSegmentVO
													.getFlightDate());
									despatchDetailsVO
											.setFlightSequenceNumber(uldForSegmentVO
													.getFlightSequenceNumber());
									despatchDetailsVO
											.setSegmentSerialNumber(uldForSegmentVO
													.getSegmentSerialNumber());
									despatchDetailsVO.setPou(uldForSegmentVO
											.getPou());
									if (toFlightVO != null) {
										if (toFlightVO.getPol() != null
												&& toFlightVO.getPol().trim()
														.length() > 0) {
											despatchDetailsVO
													.setAirportCode(toFlightVO
															.getPol());
										}
										if (toFlightVO.getCarrierCode() != null
												&& toFlightVO.getCarrierCode()
														.trim().length() > 0) {
											despatchDetailsVO
													.setCarrierCode(toFlightVO
															.getCarrierCode());
										}
									}
									despatchDetailsVO
											.setUldNumber(uldForSegmentVO
													.getUldNumber());
									despatchDetailsVO
											.setContainerNumber(uldForSegmentVO
													.getUldNumber());
									if (despatchDetailsVO.getContainerNumber() != null
											&& despatchDetailsVO
													.getContainerNumber()
													.trim().length() > 0) {
										if (despatchDetailsVO
												.getContainerNumber()
												.startsWith("BULK")) {
											despatchDetailsVO
													.setContainerType(MailConstantsVO.BULK_TYPE);
										} else {
											despatchDetailsVO
													.setContainerType(MailConstantsVO.ULD_TYPE);
										}
									}
									flightAssignedDespatches
											.add(despatchDetailsVO);
								}
							}
						}
					}
				}
			}
			if (updateFlight != null && updateFlight.trim().length() > 0) {
				if ("UPDATE_BOOKING_FROM_FLIGHT".equals(updateFlight)) {
					log.log(Log.FINE, "UPDATE_BOOKING_FROM_FLIGHT");
				} else if ("UPDATE_BOOKING_TO_FLIGHT".equals(updateFlight)) {
					log.log(Log.FINE, "UPDATE_BOOKING_TO_FLIGHT");
				}
			}
		}
		log.exiting("ReassignController", "updateBookingForFlight");
	}

	private void updateULDForSegmentVO(ULDForSegmentVO uldForSegmentVO,
			OperationalFlightVO toFlightVO, ContainerVO assignedContainerVO,
			int toFlightSegSerialNo) throws SystemException {
		uldForSegmentVO.setCompanyCode(toFlightVO.getCompanyCode());
		uldForSegmentVO.setCarrierId(toFlightVO.getCarrierId());
		uldForSegmentVO.setFlightNumber(toFlightVO.getFlightNumber());
		uldForSegmentVO.setFlightSequenceNumber(toFlightVO
				.getFlightSequenceNumber());
		uldForSegmentVO.setSegmentSerialNumber(toFlightSegSerialNo);
		uldForSegmentVO.setRemarks(assignedContainerVO.getRemarks());
		uldForSegmentVO
				.setOnwardRoutings(constructOnwardRoutingForSegments(assignedContainerVO
						.getOnwardRoutings()));

		if (!MailConstantsVO.BULK_TYPE.equals(assignedContainerVO.getType())) {
			uldForSegmentVO.setRemarks(assignedContainerVO.getRemarks());
		} else {
			Collection<DSNInULDForSegmentVO> dsnsInULD = uldForSegmentVO
					.getDsnInULDForSegmentVOs();
			if (dsnsInULD != null) {
				int totalBags = 0;
				double totalWeight = 0;
				for (DSNInULDForSegmentVO dsnInULD : dsnsInULD) {
					Collection<DSNInContainerForSegmentVO> dsnsInCon = dsnInULD
							.getDsnInContainerForSegs();
					totalBags += dsnInULD.getAcceptedBags();
					//totalWeight += dsnInULD.getAcceptedWeight();
					totalWeight += dsnInULD.getAcceptedWeight().getRoundedSystemValue();//added by A-7371
					if (dsnsInCon != null) {
						for (DSNInContainerForSegmentVO dsnInCon : dsnsInCon) {
							if (dsnInCon.getContainerNumber().equals(
									assignedContainerVO.getContainerNumber())) {
								dsnInCon.setRemarks(assignedContainerVO
										.getRemarks());
							}
						}
					}
					uldForSegmentVO.setMailbagInULDForSegmentVOs(dsnInULD
							.getMailBags());
				}
				uldForSegmentVO.setNoOfBags(totalBags);
				//uldForSegmentVO.setTotalWeight(totalWeight);
				uldForSegmentVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,totalWeight));//added by A-7371
			}

		}
		
		//Added as part of bug ICRD-241542 by A-5526 starts
		//In case the toFlight having the container with same name and different type (as BULK in the toFlight) then
		//existing type need to be maintained and the bags need to be saved in that.
		//Hence changing the uldNuber as bulk format BULK-POU
		ContainerPK containerPk = new ContainerPK();
		containerPk.setCompanyCode(toFlightVO.getCompanyCode());
		containerPk.setContainerNumber(assignedContainerVO.getContainerNumber());
		containerPk.setAssignmentPort(assignedContainerVO.getAssignedPort());
		containerPk.setFlightNumber(toFlightVO.getFlightNumber());
		containerPk.setCarrierId(toFlightVO.getCarrierId());
		containerPk.setFlightSequenceNumber(toFlightVO
				.getFlightSequenceNumber());
		containerPk.setLegSerialNumber(toFlightVO.getLegSerialNumber());

		Container container = null;      
		try {
			container = Container.find(containerPk);
			if (MailConstantsVO.BULK_TYPE.equals(container
					.getContainerType())&&!assignedContainerVO.isBarrowToUld()) {
				
					String fromULDNumber = constructBulkULDNumber(
							assignedContainerVO.getFinalDestination(),
							assignedContainerVO.getCarrierCode());
					uldForSegmentVO.setUldNumber(fromULDNumber);
				
			}
			
		} catch (FinderException ex) {
			container=null;
		}
		//Added as part of bug ICRD-241542 by A-5526 ends
	}

	private Collection<OnwardRouteForSegmentVO> constructOnwardRoutingForSegments(
			Collection<OnwardRoutingVO> onwardRoutings) {
		Collection<OnwardRouteForSegmentVO> onwardRouteForSegmentVOs = new ArrayList<OnwardRouteForSegmentVO>();
		if (onwardRoutings != null && onwardRoutings.size() > 0) {
			for (OnwardRoutingVO onwardRoutingVO : onwardRoutings) {
				OnwardRouteForSegmentVO onwardRouteForSegmentVO = new OnwardRouteForSegmentVO();
				onwardRouteForSegmentVO.setOnwardCarrierCode(onwardRoutingVO
						.getOnwardCarrierCode());
				onwardRouteForSegmentVO.setOnwardFlightNumber(onwardRoutingVO
						.getOnwardFlightNumber());
				onwardRouteForSegmentVO.setOnwardFlightDate(onwardRoutingVO
						.getOnwardFlightDate());
				onwardRouteForSegmentVO.setOnwardCarrierId(onwardRoutingVO
						.getOnwardCarrierId());
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
	private Collection<MailbagVO> constructMailBagVOFromSeg(
			Collection<MailbagInULDForSegmentVO> mailbagToReassign,
			String companyCode) throws SystemException {
		log.entering("ReassignController", "getDSNVOsOfSeg");

		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();

		if (mailbagToReassign != null) {
			for (MailbagInULDForSegmentVO mailbagInULDForSegmentVO : mailbagToReassign) {

				MailbagVO mailbagVO = new MailbagVO();
				mailbagVO.setCompanyCode(companyCode);
				mailbagVO.setMailSequenceNumber(mailbagInULDForSegmentVO
						.getMailSequenceNumber());
				mailbagVO.setTransferFromCarrier(mailbagInULDForSegmentVO
						.getTransferFromCarrier());
				mailbagVOs.add(mailbagVO);
			}

		}
		return mailbagVOs;
	}

	/**
	 * @author A-1936
	 * @param dsnInUldForSegments
	 * @param dsnVos
	 */
	private void updateTrfCarrierForResdits(
			Collection<MailbagInULDForSegmentVO> mailsInUldForSegments,
			Collection<MailbagVO> mailbagVOs) {
		boolean canBreak = false;
		log.log(Log.FINE, "THE DSNVO'S BEFORE", mailbagVOs);
		// for (DSNInULDForSegmentVO dsnInUldForSegment : dsnInUldForSegments) {
		if (mailsInUldForSegments != null && mailsInUldForSegments.size() > 0) {
			for (MailbagInULDForSegmentVO mailBagInUld : mailsInUldForSegments) {
				canBreak = false;
				// for (DSNVO dsn : dsnVos) {
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for (MailbagVO mailbag : mailbagVOs) {
						if (mailBagInUld.getMailSequenceNumber() == (mailbag
								.getMailSequenceNumber())) {
							mailbag.setTransferFromCarrier(mailBagInUld
									.getTransferFromCarrier());
							canBreak = true;
							log.log(Log.FINE,
									"THE TRANSFER FROM CARRIER IS UPDATED");
							break;
						}
					}
					if (canBreak) {
						break;
					}
				}
				// }
			}
		}
		// }
		log.log(Log.FINE, "THE DSNVO'S BEFORE", mailbagVOs);
	}

	/**
	 * This method checks whether ULD integration Enabled or not
	 *
	 * @return
	 * @throws SystemException
	 */
	private boolean isULDIntegrationEnabled() throws SystemException {
		boolean isULDIntegrationEnabled = false;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(MailConstantsVO.ULD_INTEGRATION_ENABLED);
		HashMap<String, String> systemParameterMap = null;
		systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class)
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null
				&& ContainerVO.FLAG_YES.equals(systemParameterMap
						.get(MailConstantsVO.ULD_INTEGRATION_ENABLED))) {
			isULDIntegrationEnabled = true;
		}
		log.log(Log.FINE, " isULDIntegrationEnabled :", isULDIntegrationEnabled);
		return isULDIntegrationEnabled;
	}

	/**
	 * This methods copies the details from the ForSegmentVOs to the AirportVOs
	 * A-1739
	 *
	 * @param uldForSegmentVO
	 * @param airport
	 * @param isOffload
	 * @return
	 * @throws SystemException
	 */
	private ULDAtAirportVO constructULDAtAirportVOFromSegment(
			ULDForSegmentVO uldForSegmentVO, String airport, boolean isOffload)
			throws SystemException {
		log.entering("ReassignController", "getULDAtAirportVOFromSegment");

		ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
		uldAtAirportVO.setCompanyCode(uldForSegmentVO.getCompanyCode());
		uldAtAirportVO.setUldNumber(uldForSegmentVO.getUldNumber());

		uldAtAirportVO.setNumberOfBags(uldForSegmentVO.getNoOfBags());
		uldAtAirportVO.setTotalWeight(uldForSegmentVO.getTotalWeight());
		uldAtAirportVO.setRemarks(uldForSegmentVO.getRemarks());
		uldAtAirportVO.setWarehouseCode(uldForSegmentVO.getWarehouseCode());
		uldAtAirportVO.setLocationCode(uldForSegmentVO.getLocationCode());

		// DSNs
		Collection<MailbagInULDForSegmentVO> dsnsInULDForSegment = uldForSegmentVO.getMailbagInULDForSegmentVOs();
				
		if (dsnsInULDForSegment != null && dsnsInULDForSegment.size() > 0) {
			Collection<MailbagInULDAtAirportVO> dsnInULDAtAirports = constructDSNsInULDAtArpFromSeg(
					dsnsInULDForSegment, airport, isOffload);
			uldAtAirportVO.setMailbagInULDAtAirportVOs(dsnInULDAtAirports);
		}
		// MailbagInULDForSegmentVO
		Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVO = uldForSegmentVO
				.getMailbagInULDForSegmentVOs();
		if (mailbagInULDForSegmentVO != null
				&& mailbagInULDForSegmentVO.size() > 0) {
			Collection<MailbagInULDAtAirportVO> mailbagVOs = constructMailbagInULDAtAirportVOs(
					mailbagInULDForSegmentVO, airport);

			uldAtAirportVO.setMailbagInULDAtAirportVOs(mailbagVOs);
		}
		log.exiting("ReassignController", "getULDAtAirportVOFromSegment");
		return uldAtAirportVO;
	}

	/**
	 * This method converts the VO for Segment to that for an Airport. All
	 * details fo ULDForSegment transferred to a ULDAtAirport A-1739
	 *
	 * @param dsnsToReassign
	 * @param airportCode
	 * @param isOffload
	 * @return
	 * @throws SystemException
	 */
	private Collection<MailbagInULDAtAirportVO> constructDSNsInULDAtArpFromSeg(
			Collection<MailbagInULDForSegmentVO> dsnsToReassign,
			String airportCode, boolean isOffload) throws SystemException {
		log.entering("ReassignController", "constructDSNsInULDAtArpFromSeg");
		Collection<MailbagInULDAtAirportVO> mailsInULDAtArp = new ArrayList<MailbagInULDAtAirportVO>();
		for (MailbagInULDForSegmentVO mailInULDForSeg : dsnsToReassign) {
			MailbagInULDAtAirportVO mailInULDAtAirport = new MailbagInULDAtAirportVO();
			mailInULDAtAirport.setMailSubclass(mailInULDForSeg.getMailSubclass());
			mailInULDAtAirport.setMailCategoryCode(mailInULDForSeg.getMailCategoryCode());
			mailInULDAtAirport.setMailClass(mailInULDForSeg.getMailClass());
			mailInULDAtAirport.setMailId(mailInULDForSeg.getMailId());
			mailInULDAtAirport.setMailSequenceNumber(mailInULDForSeg.getMailSequenceNumber());
			log.log(Log.FINE, "before containers",mailInULDAtAirport);
			
			log.log(Log.FINE, "before containers mailbags",mailInULDAtAirport);
			// mailbags
			
			//if (mailbagsInULDSeg != null && mailbagsInULDSeg.size() > 0) {
				//Collection<MailbagInULDAtAirportVO> mailbagsInULDArp = new ArrayList<MailbagInULDAtAirportVO>();
				//for (MailbagInULDForSegmentVO mailbagInULDSeg : mailbagsInULDSeg) {
					MailbagInULDAtAirportVO mailbagInULDArp = new MailbagInULDAtAirportVO();
					log.log(Log.FINE, "containers mailbags",mailInULDForSeg);
					//BeanHelper.copyProperties(mailbagInULDArp, mailbagInULDSeg);
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
					log.log(Log.FINE, "containers mailbags copied",mailbagInULDArp);
					mailbagInULDArp.setScannedDate(new LocalDate(airportCode,
							Location.ARP, true));
					mailbagInULDArp.setMailClass(mailInULDForSeg.getMailClass());
					mailsInULDAtArp.add(mailbagInULDArp);
					log.log(Log.FINE, "containers mailbags copied final",mailbagInULDArp);
				//}
					
			//}
			log.log(Log.FINE, "before containers mailbags",mailsInULDAtArp);
			
		
			
		}
		log.exiting("ReassignController", "constructDSNsInULDAtArpFromSeg");
		return mailsInULDAtArp;
	}

	/**
	 * This method reassigns containers assigned to a flight to their
	 * destination
	 *
	 * @param flightAssignedContainers
	 *            the containers assigned to flight
	 * @param toDestinationVO
	 *            the FlightVO representing the to destination
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @throws CapacityBookingProxyException
	 * @throws MailBookingException
	 */
	public Collection<ContainerVO> reassignContainerFromFlightToDest(
			Collection<ContainerVO> flightAssignedContainers,
			OperationalFlightVO toDestinationVO,
			Collection<String> mailIdsForMonitorSLAs) throws SystemException,
			ULDDefaultsProxyException, CapacityBookingProxyException,
			MailBookingException {
		log.entering("ReassignController", "reassignFromFlightToDest");
		// ULD integration
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
		// ULD integration
		Collection<ContainerVO> paBuiltContainers = new ArrayList<ContainerVO>();
		Collection<ContainerVO> containersForReturn = new ArrayList<ContainerVO>();
		Collection<MailbagVO> mailbagsForContainerOffload = new ArrayList<MailbagVO>();
		boolean isRemove = false;
		if(flightAssignedContainers!=null && !flightAssignedContainers.isEmpty()) {
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
				String fromULDNumber = flightAssignedContainerVO
						.getContainerNumber();

				// If the container is Bulk type the the uldNumber is
				// constructed as
				// BULK-{the pou}
				if (MailConstantsVO.BULK_TYPE.equals(flightAssignedContainerVO
						.getType())) {
					fromULDNumber = constructBulkULDNumber(
							flightAssignedContainerVO.getPou(),
							flightAssignedContainerVO.getCarrierCode());
				}

				/*
				 * For both bulk and ULD this gives the from assignment details.
				 * Only that the operations on this entity changes
				 */
				ULDForSegment uldForSegment = null;
				try {
					uldForSegment = AssignedFlightSegment
							.findULDForSegment(constructULDForSegmentPK(
									flightAssignedContainerVO.getCompanyCode(),
									fromULDNumber,
									flightAssignedContainerVO.getCarrierId(),
									flightAssignedContainerVO.getFlightNumber(),
									flightAssignedContainerVO
											.getFlightSequenceNumber(),
									flightAssignedContainerVO
											.getSegmentSerialNumber()));
				} catch (FinderException finderException) {
					// data inconsistency
					throw new SystemException("No such ULD at this port",
							finderException);
				}

				AssignedFlightSegment fromAssignedFlightSegment = null;
				try {
					fromAssignedFlightSegment = findAssignedFlightSegment(
							flightAssignedContainerVO.getCompanyCode(),
							flightAssignedContainerVO.getCarrierId(),
							flightAssignedContainerVO.getFlightNumber(),
							flightAssignedContainerVO.getFlightSequenceNumber(),
							flightAssignedContainerVO.getSegmentSerialNumber());
				} catch (FinderException ex) {
					throw new SystemException(ex.getMessage(), ex);
				}

				Collection<MailbagInULDAtAirportVO> dsnsToReassign = null;
				String containerNumber = flightAssignedContainerVO
						.getContainerNumber();
				ULDAtAirportVO uldAtAirportVO = null;

				if (MailConstantsVO.ULD_TYPE.equals(flightAssignedContainerVO
						.getType())) {

					// For CR :AirNZ404 : Mail Allocation STARTS
					ULDForSegmentVO fromULDForSegVO = uldForSegment
							.retrieveVO();
					fromULDForSegVO.setPou(flightAssignedContainerVO.getPou());
					fromULDForSegVO.setCarrierId(flightAssignedContainerVO
							.getCarrierId());
					fromULDForSegVO.setFlightNumber(flightAssignedContainerVO
							.getFlightNumber());
					fromULDForSegVO.setFlightDate(flightAssignedContainerVO
							.getFlightDate());
					fromULDForSegVO
							.setFlightSequenceNumber(flightAssignedContainerVO
									.getFlightSequenceNumber());
					fromULDForSegVO
							.setSegmentSerialNumber(flightAssignedContainerVO
									.getSegmentSerialNumber());
					// Updating Booking Details
					updateBookingForFlight(fromULDForSegVO, null,
							"UPDATE_BOOKING_FROM_FLIGHT");
					// CR :AirNZ404 :Changes ENDS

					uldAtAirportVO = constructULDAtAirportVOFromSegment(
							uldForSegment.retrieveVO(),
							toDestinationVO.getPol(),
							flightAssignedContainerVO.isOffload());

					
					//Added by A-8893 for IASCB-38903 starts
					if(flightAssignedContainerVO.isUldTobarrow()){
						dsnsToReassign = constructDSNsInULDAtArpFromSeg(
								fromAssignedFlightSegment.reassignBulkContainer(
										uldForSegment, containerNumber),
								toDestinationVO.getPol(),
								flightAssignedContainerVO.isOffload());
					}
					//Added by A-8893 for IASCB-38903 ends
					fromAssignedFlightSegment
							.removeULDForSegment(uldForSegment);
					
					
					updateULDAtAirportVO(uldAtAirportVO, toDestinationVO,
							flightAssignedContainerVO);

					log.log(Log.FINEST, "udlatarp VO --> ", uldAtAirportVO);
					
					try{
						ULDAtAirportPK uldAtAirportPK = new ULDAtAirportPK();
						uldAtAirportPK.setAirportCode(uldAtAirportVO.getAirportCode());
						uldAtAirportPK.setCarrierId(uldAtAirportVO.getCarrierId());
						uldAtAirportPK.setCompanyCode(uldAtAirportVO.getCompanyCode());
						uldAtAirportPK.setUldNumber(uldAtAirportVO.getUldNumber());
						ULDAtAirport toULDAtAirport=ULDAtAirport.find(uldAtAirportPK);
						toULDAtAirport.assignBulkContainer(dsnsToReassign);
					}catch(FinderException exception){
						log.log(Log.FINE, "udlatarp VO exception--> ", exception.getMessage());
					new ULDAtAirport(uldAtAirportVO);
					}catch(SystemException exception){
						log.log(Log.FINE, "udlatarp VO exception--> ", exception.getMessage());
						new ULDAtAirport(uldAtAirportVO);
					}
					
					isReassignSuccess = true;
				} else if (MailConstantsVO.BULK_TYPE
						.equals(flightAssignedContainerVO.getType())) {

					// For CR :AirNZ404 : Mail Allocation STARTS
					ULDForSegmentVO fromULDForSegVO = uldForSegment
							.retrieveVO();
					fromULDForSegVO.setPou(flightAssignedContainerVO.getPou());
					fromULDForSegVO.setCarrierId(flightAssignedContainerVO
							.getCarrierId());
					fromULDForSegVO.setFlightNumber(flightAssignedContainerVO
							.getFlightNumber());
					fromULDForSegVO.setFlightDate(flightAssignedContainerVO
							.getFlightDate());
					fromULDForSegVO
							.setFlightSequenceNumber(flightAssignedContainerVO
									.getFlightSequenceNumber());
					fromULDForSegVO
							.setSegmentSerialNumber(flightAssignedContainerVO
									.getSegmentSerialNumber());
					// Updating Booking Details of Current Flight
					updateBookingForFlight(fromULDForSegVO, null,
							"UPDATE_BOOKING_FROM_FLIGHT");
					// CR :AirNZ404 :Changes ENDS

					String toULDNumber =null;
					if(flightAssignedContainerVO.isBarrowToUld()){
						toULDNumber=flightAssignedContainerVO.getContainerNumber();
					}
					else{
						toULDNumber=constructBulkULDNumber(
							flightAssignedContainerVO.getFinalDestination(),
							flightAssignedContainerVO.getCarrierCode());
					}
					dsnsToReassign = constructDSNsInULDAtArpFromSeg(
							fromAssignedFlightSegment.reassignBulkContainer(
									uldForSegment, containerNumber),
							toDestinationVO.getPol(),
							flightAssignedContainerVO.isOffload());
					

					if (uldForSegment.getNumberOfBags() == 0) {
						// This method is to find the number of bulks present in
						// the flight . Added by A--5945 for ICRD-128827
						try {
							numberOfBarrowspresent = AssignedFlightSegment
									.findNumberOfBarrowsPresentinFlightorCarrier(flightAssignedContainerVO);
						} catch (SystemException e) {
							log.log(Log.FINE, e.getMessage());
						}
						log.log(Log.FINEST, "numberOfBarrowspresent-- > ",
								numberOfBarrowspresent);
						/*
						 * Earlier even if there are empty bulks present in the
						 * flights, the entry from MTKULDSEG got deleted. Hence
						 * when we try to add a new mailbag to the empty bulk ,
						 * internal server error wolud come. So here we are
						 * checking if there are more than one bulk in the
						 * flight . If there are more than one bulk in the
						 * flight, no need to remove the entry from MTKULDSEG
						 */
						if (numberOfBarrowspresent == 1) {
							log.log(Log.FINE,
									"reassing bulk  removing bulk uld since cnt 0");
							fromAssignedFlightSegment
									.removeULDForSegment(uldForSegment);
						}
					}
					log.log(Log.FINEST, "retrieved dsns for uldtype --> ",
							dsnsToReassign);
					try {
						ULDAtAirport toUldAtAirport = ULDAtAirport
								.find(constructULDArpPKFromOpFlt(
										toDestinationVO, toULDNumber));
						uldAtAirportVO = toUldAtAirport
									.retrieveVO();

						updateULDAtAirportVO(uldAtAirportVO, toDestinationVO,
								flightAssignedContainerVO);
						
						toUldAtAirport.assignBulkContainer(dsnsToReassign);
						
						

						isReassignSuccess = true;
					} catch (FinderException finderException) {
						uldAtAirportVO = new ULDAtAirportVO();
						uldAtAirportVO.setUldNumber(toULDNumber);
						uldAtAirportVO.setMailbagInULDAtAirportVOs(dsnsToReassign);

						updateULDAtAirportVO(uldAtAirportVO, toDestinationVO,
								flightAssignedContainerVO);

						log.log(Log.FINEST, "udlatarp VO --> in to filgt",
								uldAtAirportVO);
						new ULDAtAirport(uldAtAirportVO);
						isReassignSuccess = true;
					}
				}
				if (isReassignSuccess) {
					if (dsnsToReassign == null) {
						if(uldAtAirportVO.getMailbagInULDAtAirportVOs()!=null
								&&uldAtAirportVO.getMailbagInULDAtAirportVOs().size()>0){
						mailbagVOs = constructMailbagVOsFromAirport(
								uldAtAirportVO.getMailbagInULDAtAirportVOs(),
								toDestinationVO.getCompanyCode());
						// Added By Karthick V for Monitoring the Service Level
						// Activity
						updateDSNForConReassign(mailbagVOs, toDestinationVO,
								flightAssignedContainerVO,
								MailConstantsVO.DESTN_FLT,
								mailIdsForMonitorSLAs);
						// commented since mailbag updation is not there
						/*
						 * updateDSNSegment(dsnVOs, flightAssignedContainerVO,
						 * toDestinationVO, MailConstantsVO.DESTN_FLT,
						 * fromAssignedFlightSegment, null);
						 */
						if (flightAssignedContainerVO.isOffload()) {
							if(!MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD.equals(
								flightAssignedContainerVO.getMailSource()) && !flightAssignedContainerVO.isRemove()) {
							flagResditForMailbags(mailbagVOs,
									toDestinationVO.getPol(),
									MailConstantsVO.RESDIT_PENDING);
							}
							mailbagsForContainerOffload.addAll(mailbagVOs);
							mailbagsForOffload.addAll(mailbagVOs);
						} else {
							// if offload no other resdit need to be flagged
							mailbagsToFlag.addAll(mailbagVOs);
						}
						}
					}
					if (dsnsToReassign != null
							&& dsnsToReassign
									.size() > 0) {
						//for(MailbagInULDAtAirportVO dsnInULDAtAirportVO:dsnsToReassign){
//						if(dsnInULDAtAirportVO.getMailbagInULDVOs()!=null
//								&&dsnInULDAtAirportVO.getMailbagInULDVOs().size()>0){
						mailbagVOs = constructMailbagVOsFromAirport(
								dsnsToReassign,
								toDestinationVO.getCompanyCode());
						// Added By Karthick V for Monitoring the Service Level
						// Activity
						updateDSNForConReassign(mailbagVOs, toDestinationVO,
								flightAssignedContainerVO,
								MailConstantsVO.DESTN_FLT,
								mailIdsForMonitorSLAs);
						// commented since mailbag updation is not there
						/*
						 * updateDSNSegment(dsnVOs, flightAssignedContainerVO,
						 * toDestinationVO, MailConstantsVO.DESTN_FLT,
						 * fromAssignedFlightSegment, null);
						 */

						if (flightAssignedContainerVO.isOffload()) {
							flagResditForMailbags(mailbagVOs,
									toDestinationVO.getPol(),
									MailConstantsVO.RESDIT_PENDING);
							mailbagsForContainerOffload.addAll(mailbagVOs);
							mailbagsForOffload.addAll(mailbagVOs);
						} else {
							// if offload no other resdit need to be flagged
							mailbagsToFlag.addAll(mailbagVOs);
						}
						//}
						//}
					}
					//Removed as part of code quality work by A-7531 
					//if (flightAssignedContainerVO.isOffload()) {
						
					//}
					MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					if (flightAssignedContainerVO.isOffload()) {
						mailController.flagHistoryForContainerReassignment(toDestinationVO,flightAssignedContainerVO,mailbagsForOffload);
						mailController.flagAuditForContainerReassignment(toDestinationVO,flightAssignedContainerVO,mailbagsForContainerOffload);
					}else{
					mailController.flagHistoryForContainerReassignment(toDestinationVO,flightAssignedContainerVO,mailbagsToFlag);
					mailController.flagAuditForContainerReassignment(toDestinationVO,flightAssignedContainerVO,mailbagsToFlag);
					}
					/**
					 * In both offload and reassign to carrier, MRA re-import needs to be invoked
					 * For MRA trigger point M,A if the mailbags are present in MRA they will be re-imported
					 * For MRA trigger point M,A if the mailbags are not present in MRA, then no action needs to be done
					 */
					if (Objects.nonNull(mailbagVOs) && !mailbagVOs.isEmpty()) {
						new MailController().validateAndReImportMailbagsToMRA(mailbagVOs);
					}
				}
			}
			if (isReassignSuccess || (isNotAccepted && !flightAssignedContainerVO.isRemove()) ) {
				updateReassignedContainer(flightAssignedContainerVO,
						toDestinationVO, MailConstantsVO.DESTN_FLT);

				if (!flightAssignedContainerVO.isOffload()) {
					// if offload no other resdit need to be flagged
					// PENDINGRESDIT already flagged by offload method
					if (isReassignSuccess
							&& MailConstantsVO.FLAG_YES
									.equals(flightAssignedContainerVO
											.getPaBuiltFlag())) {
						paBuiltContainers.add(flightAssignedContainerVO);
					}
				}
			}
			if (isUldIntegrationEnabled
					&& !(MailConstantsVO.BULK_TYPE
							.equals(flightAssignedContainerVO.getType()))) {
				log.log(Log.INFO, "Creating ULDInFlightVO");
				uldInFlightVO = new ULDInFlightVO();
				uldInFlightVO.setUldNumber(flightAssignedContainerVO
						.getContainerNumber());
				uldInFlightVO.setPointOfLading(flightAssignedContainerVO.getAssignedPort());
				uldInFlightVO.setPointOfUnLading(flightAssignedContainerVO.getPou());
				//Modified by A-7794 as part of ICRD-226708
				uldInFlightVO.setRemark(flightAssignedContainerVO.getRemarks());
				flightDetailsVO.setRemark(flightAssignedContainerVO.getRemarks());
				uldInFlightVOs.add(uldInFlightVO);
			}
			ContainerVO containerReturnVO = new ContainerVO();
			BeanHelper.copyProperties(containerReturnVO,
					flightAssignedContainerVO);
			containerReturnVO.setFlightSequenceNumber(toDestinationVO
					.getFlightSequenceNumber());
			containerReturnVO
					.setFlightNumber(toDestinationVO.getFlightNumber());
			containerReturnVO.setLegSerialNumber(toDestinationVO
					.getLegSerialNumber());
			// containerReturnVO.setSegmentSerialNumber(toFlightSegSerialNo);
			containerReturnVO.setCarrierCode(toDestinationVO.getCarrierCode());
			containerReturnVO.setFlightDate(toDestinationVO.getFlightDate());
			containersForReturn.add(containerReturnVO);
			if ((paBuiltContainers.size() > 0 || mailbagsToFlag.size() > 0 ) && !flightAssignedContainerVO.isRemove()) {
				flagResditsForContainerReassign(mailbagsToFlag, paBuiltContainers,
						toDestinationVO, MailConstantsVO.DESTN_FLT);
			}
		}
		if (isUldIntegrationEnabled && !isRemove) {
			flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);

			flightDetailsVO.setAction(FlightDetailsVO.OFFLOADED);
			for(MailbagVO mailbagvo:mailbagsForContainerOffload){
			flightDetailsVO.setFlightNumber(mailbagvo.getFlightNumber());
			flightDetailsVO.setFlightSequenceNumber(mailbagvo.getFlightSequenceNumber());
			}
			new ULDDefaultsProxy().updateULDForOperations(flightDetailsVO);

		}
		log.exiting("ReassignController", "reassignFromFlightToDest");
		return containersForReturn;
	}

	/**
	 * This method is used for flagging various resdits. This inturn call
	 * various private methods for flagging different resdits A-1739
	 *
	 * @param dsnVOs
	 * @param eventAirport
	 * @param eventCode
	 * @throws SystemException
	 */
	private void flagResditForMailbags(Collection<MailbagVO> mailbagsToFlag,
			String eventAirport, String eventCode) throws SystemException {
		MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
		mailController.flagResditForMailbagsFromReassign(eventCode,
				eventAirport, mailbagsToFlag);
		/*String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
		if (MailConstantsVO.FLAG_YES.equals(resditEnabled)) {
			log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
			// Collection<MailbagVO> mailbagsToFlag =
			// mergeMailbagsofDSNs(dsnVOs);

			new ResditController().flagResditForMailbags(eventCode,
					eventAirport, mailbagsToFlag);
		}*/
	}

	private Collection<MailbagVO> mergeMailbagsofDSNs(Collection<DSNVO> dsnVOs) {
		Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
		for (DSNVO dsnVO : dsnVOs) {
			Collection<MailbagVO> mailbagsofDSN = dsnVO.getMailbags();

			if (mailbagsofDSN != null && mailbagsofDSN.size() > 0) {
				log.log(Log.FINE, "THE MAILBAGS IN  mergeMailbagsofDSNs",
						mailbagsofDSN);
				mailbags.addAll(mailbagsofDSN);
			}
		}
		return mailbags;
	}

	/**
	 * @author A-5991
	 * @param mailbagsInULDSeg
	 * @param airportCode
	 * @return
	 */
	public Collection<MailbagInULDAtAirportVO> constructMailbagInULDAtAirportVOs(
			Collection<MailbagInULDForSegmentVO> mailbagsInULDSeg,
			String airportCode) {
		Collection<MailbagInULDAtAirportVO> mailbagsInULDArp = new ArrayList<MailbagInULDAtAirportVO>();
		if (mailbagsInULDSeg != null && mailbagsInULDSeg.size() > 0) {

			for (MailbagInULDForSegmentVO mailbagInULDSeg : mailbagsInULDSeg) {
				MailbagInULDAtAirportVO mailbagInULDArp = new MailbagInULDAtAirportVO();
				log.log(Log.FINE, "containers mailbags", mailbagInULDSeg);
				// BeanHelper.copyProperties(mailbagInULDArp, mailbagInULDSeg);
				mailbagInULDArp.setMailId(mailbagInULDSeg.getMailId());
				mailbagInULDArp.setMailSequenceNumber(mailbagInULDSeg.getMailSequenceNumber());
				mailbagInULDArp.setMailCategoryCode(mailbagInULDSeg
						.getMailCategoryCode());
				mailbagInULDArp.setMailClass(mailbagInULDSeg.getMailClass());
				mailbagInULDArp.setMailSubclass(mailbagInULDSeg
						.getMailSubclass());
				mailbagInULDArp.setContainerNumber(mailbagInULDSeg
						.getContainerNumber());
				mailbagInULDArp.setDamageFlag(mailbagInULDSeg.getDamageFlag());
				mailbagInULDArp
						.setScannedDate(mailbagInULDSeg.getScannedDate());
				mailbagInULDArp.setSealNumber(mailbagInULDSeg.getSealNumber());
				mailbagInULDArp.setTransferFromCarrier(mailbagInULDSeg
						.getTransferFromCarrier());
				mailbagInULDArp.setWeight(mailbagInULDSeg.getWeight());
				log.log(Log.FINE, "containers mailbags copied", mailbagInULDArp);
				mailbagInULDArp.setScannedDate(new LocalDate(airportCode,
						Location.ARP, true));
				mailbagInULDArp.setMailClass(mailbagInULDSeg.getMailClass());
				mailbagsInULDArp.add(mailbagInULDArp);
				log.log(Log.FINE, "containers mailbags copied final",
						mailbagInULDArp);
			}
		}
		return mailbagsInULDArp;
	}

	public void reassignDSNsFromDestination(
			Collection<DespatchDetailsVO> dsnsToReassign)
			throws SystemException {
		log.entering("ReassignController", "reassignDSNsFromDestination");
		Map<ULDAtAirportPK, Collection<DespatchDetailsVO>> uldDespatchMap = groupULDDespatches(dsnsToReassign);
		ULDAtAirport uldArp = null;
		try {
			for (Map.Entry<ULDAtAirportPK, Collection<DespatchDetailsVO>> uldDespatch : uldDespatchMap
					.entrySet()) {
				ULDAtAirportPK uldArpPK = uldDespatch.getKey();
				Collection<DespatchDetailsVO> uldDespatches = uldDespatch
						.getValue();

				uldArp = ULDAtAirport.find(uldArpPK);

				//updateULDAtAirportCount(uldArp, uldDespatches, false);

				// uldArp.reassignDSNsFromDestination(uldDespatches);

				if (uldArp.getNumberOfBags() == 0) {
					uldArp.remove();
					log.log(Log.FINE, "uldArp for bulk removed coz cnt 0");
					if (!uldArp.getUldAtAirportPK().getUldNumber()
							.startsWith(MailConstantsVO.CONST_BULK)) {
						// Bulk Container was already removed in
						// DSNInULDAtAirport
						removeContainerForDespatch(uldArpPK, uldDespatches);
					}
				}
			}
		} catch (FinderException exception) {
			// throw new SystemException("No ULDArp found", exception);
		}
		log.exiting("ReassignController", "reassignDSNsFromDestination");
	}

	/**
	 * Removes a barrow from the ContainerMaster. This got empty when all the
	 * dsns and maiblags in it were moved from it A-1739
	 *
	 * @param uldAtAirportPK
	 * @param despatchesInCon
	 * @throws SystemException
	 */
	private void removeContainerForDespatch(ULDAtAirportPK uldAtAirportPK,
			Collection<DespatchDetailsVO> despatchesInCon)
			throws SystemException {
		log.entering("DSNInULDAtAirport", "updateContainerDetails");

		ContainerPK containerPK = constructContainerPKForDespatch(
				uldAtAirportPK, despatchesInCon);
		Container destAssignedContainer = null;
		try {
			destAssignedContainer = Container.find(containerPK);
		} catch (FinderException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		UldInFlightVO uldInFlightVO = new UldInFlightVO();
		uldInFlightVO.setCompanyCode(containerPK.getCompanyCode());
		uldInFlightVO.setUldNumber(containerPK.getContainerNumber());
		uldInFlightVO.setPou(destAssignedContainer.getPou());
		uldInFlightVO.setAirportCode(containerPK.getAssignmentPort());
		uldInFlightVO.setCarrierId(containerPK.getCarrierId());
		if (containerPK.getFlightSequenceNumber() > 0) {
			uldInFlightVO.setFlightNumber(containerPK.getFlightNumber());
			uldInFlightVO.setFlightSequenceNumber(containerPK
					.getFlightSequenceNumber());
			uldInFlightVO.setLegSerialNumber(containerPK.getLegSerialNumber());
		}
		uldInFlightVO.setFlightDirection(MailConstantsVO.OPERATION_INBOUND);
		String conType = destAssignedContainer.getContainerType();
		destAssignedContainer.remove();
		log.log(Log.FINE, "removed con");

		Collection<UldInFlightVO> operationalUlds = new ArrayList<UldInFlightVO>();
		operationalUlds.add(uldInFlightVO);

		boolean isOprUldEnabled = MailConstantsVO.FLAG_YES
				.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD));

		if (isOprUldEnabled) {
			if (operationalUlds != null && operationalUlds.size() > 0) {
				if (MailConstantsVO.ULD_TYPE.equals(conType)) {
					new OperationsFltHandlingProxy()
							.saveOperationalULDsInFlight(operationalUlds);
				}
			}
		}
		log.exiting("DSNInULDAtAirport", "updateContainerDetails");
	}

	/**
	 * A-1739
	 *
	 * @param dsnsToReassign
	 * @return
	 */
	private Map<ULDAtAirportPK, Collection<DespatchDetailsVO>> groupULDDespatches(
			Collection<DespatchDetailsVO> dsnsToReassign) {
		Map<ULDAtAirportPK, Collection<DespatchDetailsVO>> uldDespatchMap = new HashMap<ULDAtAirportPK, Collection<DespatchDetailsVO>>();
		for (DespatchDetailsVO despatchDetailsVO : dsnsToReassign) {
			ULDAtAirportPK uldArpPK = constructULDArpPKFromDSN(despatchDetailsVO);
			Collection<DespatchDetailsVO> uldDespatches = uldDespatchMap
					.get(uldArpPK);
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
	 *
	 * @param despatchDetailsVO
	 * @return
	 */
	private ULDAtAirportPK constructULDArpPKFromDSN(
			DespatchDetailsVO despatchDetailsVO) {
		ULDAtAirportPK uldArpPK = new ULDAtAirportPK();
		uldArpPK.setCompanyCode(despatchDetailsVO.getCompanyCode());
		uldArpPK.setCarrierId(despatchDetailsVO.getCarrierId());
		uldArpPK.setAirportCode(despatchDetailsVO.getAirportCode());
		uldArpPK.setUldNumber(despatchDetailsVO.getUldNumber());
		if (MailConstantsVO.BULK_TYPE.equals(despatchDetailsVO
				.getContainerType())) {
			uldArpPK.setUldNumber(constructBulkULDNumber(
					despatchDetailsVO.getDestination(),
					despatchDetailsVO.getCarrierCode()));
		}
		return uldArpPK;
	}

	private ContainerPK constructContainerPKForDespatch(
			ULDAtAirportPK uldAtAirportPK,
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
	 private void flagHistoryFormailbagReassign(Collection<MailbagVO> mailbags,
				ContainerVO toContainerVO) throws SystemException {
			log.entering("ReassignController", "flagHistoryFormailbagReassign");
			
			
			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
			mailController.flagHistoryDetailsForMailbagsFromReassign(mailbags,
					toContainerVO);
			mailController.flagAuditDetailsForMailbagsFromReassign(mailbags,
					toContainerVO);
			
			log.exiting("ReassignController", "flagHistoryFormailbagReassign");
		
	}

	private void populateMailbagVOsForResdit(Collection<MailbagVO> mailbags, Collection<MailbagVO> mailbagVOsForResdit) throws SystemException {
		if (mailbags != null && mailbags.size() > 0) {
			for (MailbagVO mailbagVO : mailbags) {
				MailbagVO mailbagForResdit = new MailbagVO();
				BeanHelper.copyProperties(mailbagForResdit, mailbagVO);
				mailbagVOsForResdit.add(mailbagForResdit);
			}
		}
	}
	private Collection<String> getAirportParameterCodes(){
		 Collection<String> parameterTypes = new ArrayList();
		  parameterTypes.add(WEIGHT_SCALE_AVAILABLE);
		  return parameterTypes;
	}

}
