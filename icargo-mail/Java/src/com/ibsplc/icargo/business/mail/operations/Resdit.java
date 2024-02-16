/*
 * Resdit.java Created on Sep 8, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.util.StringUtils;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentValidationVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReferenceInformationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditAddressVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.GoodsItemDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  Behavioural entity for building Resdit messages
 * @author A-1739
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		  Sep 8, 2006			a-1739		TODO
 */
public class Resdit {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	private static final String MAILTRACKING_DEFAULTS = "mail.operations";

	private static final String PREP_DATE_FORMAT = "yyMMdd";

	private static final String PREP_TIME_FORMAT = "HHmm";

	private static final String KEY_RESDIT_INTERCHANGE =
		"RESDIT_INTERCHANGE";

	private static final String KEY_MESSAGE_REFERENCE=
		"RESDIT_MESSAGEREF";

	 /**
     * Indication that the total consignment has been accepted
     */
    private static final int CONSIGNMENT_TOTALLY_ACCEPTED_USPOST = 3;

    private static final int CONSIGNMENT_TOTALLY_ACCEPTED = 1;

    /**
     * Indication that consignment has been accepted only partially
     */
    private static final int CONSIGNMENT_PARTIALLY_ACCEPTED = 3;


    private static final int DOCUMENT_NOT_RECIEVED = 11;

	private static final String FLIGHTID_DELIM = "#";

	private static final String PARTY_TYPE_AIRLINE = "AR";
	
	private static final String DOMESTIC_PACODE="mailtracking.domesticmra.usps";//added by A-8464 for ICRD-286593
	private static final String PAWBASSCONENAB ="PAWBASSCONENAB";
	private static final String PAWBPARMVALYES ="YES";
	private static final String INVINFO = "INVINFO";
	/**
	 * This methods builds the ResditMessageVO for manually triggering the RESDIT
	 * Sending
	 * Feb 12, 2007, A-1739
	 * @param carditEnquiryVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ResditMessageVO> constructResditMessageVOs(CarditEnquiryVO carditEnquiryVO)
	     throws SystemException{
		 log.entering("Resdit", "constructResditMessageVOs");
         log.log(Log.FINEST, " mailbags for grouping " +
				carditEnquiryVO.getMailbagVos());
		 Collection<ResditMessageVO> resditMessageVos = new ArrayList<ResditMessageVO>();
        /*
         * The Resdit Message Vos For the Mail  Bags
         *
         */
		Collection<ResditMessageVO> resditMessageVOsForMail =
			new ArrayList<ResditMessageVO>();
		/*
		 * The Resdit MessageVos For the Container.
		 */
		Collection<ResditMessageVO> resditMessageVOsForContainer =
			new ArrayList<ResditMessageVO>();
		String resditEvent = carditEnquiryVO.getUnsendResditEvent();
		Map<String, Collection<MailbagVO>> resditMailbagMap = null;
        Map<String, Collection<ContainerVO>> resditContainerMap = null;
		/*
		 * Check if flightType is
		 */
		boolean isCarditFlight = MailConstantsVO.FLIGHT_TYP_CARDIT.
					equals(carditEnquiryVO.getFlightType());
		/*
		 * If the event is not Received or Pending then we've to group
		 * based on either the flight/reasoncode/handoverpartner
		 */
		  /*
		   * Added By Karthick V .
		   * If the Flight is an Operational Flight there is no need to group based
		   * on the Flights as there is only one Flight..so ignore the groupings ..The Only Grouping that
		   * will be done is the Groupings based on the Cardit Keys \Consignment Document Number...
		   */
		 if(isCarditFlight){
			 if(!MailConstantsVO.RESDIT_RECEIVED.equals(resditEvent) &&
					!MailConstantsVO.RESDIT_PENDING.equals(resditEvent) &&
					!MailConstantsVO.RESDIT_HANDOVER_RECEIVED.equals(resditEvent)) {
				 if(carditEnquiryVO.getMailbagVos()!=null && carditEnquiryVO.getMailbagVos().size()>0){
					   resditMailbagMap = groupMailbagResdits(carditEnquiryVO);
				 }
				 if(carditEnquiryVO.getContainerVos()!=null && carditEnquiryVO.getContainerVos().size()>0){
					   resditContainerMap=groupResditsForULD(carditEnquiryVO);
				 }

			 }
		 }else if(carditEnquiryVO.getMailbagVos()!=null &&
			  carditEnquiryVO.getMailbagVos().size()>0){
			  resditMailbagMap = groupMailbagsOfCardit(carditEnquiryVO);
		 }
		/*
		 * Constructs the Resdit Message Vos For the Mail Bags
		 *
		 *
		 */

		 if(carditEnquiryVO.getMailbagVos()!=null &&
				  carditEnquiryVO.getMailbagVos().size()>0){
			 if(resditMailbagMap != null) {
					 for(Collection<MailbagVO> mailbags : resditMailbagMap.values()) {
						constructResditMessagesForMail(mailbags,
								resditMessageVOsForMail, carditEnquiryVO);
					 }
			 }else{
				    constructResditMessagesForMail(carditEnquiryVO.getMailbagVos(),
							 resditMessageVOsForMail, carditEnquiryVO);
			 }
		 }
		/*
		 * Constructs the Resdit Message Vos for the Containers.
		 *
		 */
		if(carditEnquiryVO.getContainerVos()!=null && carditEnquiryVO.getContainerVos().size()>0){
			if(resditContainerMap!=null && resditContainerMap.size()>0){
				for(Collection<ContainerVO> containerVos:resditContainerMap.values()){
					constructResditMessagesForULD(containerVos,resditMessageVOsForContainer,carditEnquiryVO);
				}
			}else{
				constructResditMessagesForULD(carditEnquiryVO.getContainerVos(),resditMessageVOsForContainer,carditEnquiryVO);
			}
		}
		if(resditMessageVOsForContainer.size()>0 ){
				resditMessageVos.addAll(resditMessageVOsForContainer);
		}
		if(resditMessageVOsForMail!=null && resditMessageVOsForMail.size()>0){
				 resditMessageVos.addAll(resditMessageVOsForMail);
		}
		log.exiting("Resdit", "constructResditMessageVOs");
		return resditMessageVos;
	}




	 private void  constructResditMessagesForULD(Collection<ContainerVO> containerVos,
			   Collection<ResditMessageVO> resditMessageVos,CarditEnquiryVO carditEnquiryVo)
	     throws SystemException{
		   log.entering("Resdit", "groupResditMessages");
			/*
			 * In future if multiple events need to be send this part will
			 * go inside the loop
			 */
		   if(containerVos!=null && containerVos.size()>0){
				ResditMessageVO resditMessageVO = new ResditMessageVO();
				resditMessageVO.setCompanyCode(carditEnquiryVo.getCompanyCode());
				resditMessageVO.setMessageType(ResditMessageVO.MSG_TYP);
				resditMessageVO.setMessageStandard(ResditMessageVO.EDIFACT);
		        //TODO getindicator thru query resditMessageVO.setTestIndicator("1");
				resditMessageVO.setLocNeeded(true);
				LocalDate preparationDate =
					new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
				resditMessageVO.setPreparationDate(
						preparationDate.toDisplayFormat(PREP_DATE_FORMAT));
				resditMessageVO.setPreparationTime(
						preparationDate.toDisplayFormat(PREP_TIME_FORMAT));

				String resditEvent = carditEnquiryVo.getUnsendResditEvent();
				boolean hasTransportInfo = false;

		        if(!MailConstantsVO.RESDIT_RECEIVED.equals(resditEvent) &&
		       		!MailConstantsVO.RESDIT_RETURNED.equals(resditEvent) &&
		       		!MailConstantsVO.RESDIT_PENDING.equals(resditEvent)) {
		       	hasTransportInfo = true;
		        }

		        boolean hasHandedoverInfo = false;
		        if(!resditEvent.equals(MailConstantsVO.RESDIT_UPLIFTED) &&
		       		!resditEvent.equals(MailConstantsVO.RESDIT_PENDING) &&
		       		!resditEvent.equals(MailConstantsVO.RESDIT_ASSIGNED)) {
		       	hasHandedoverInfo = true;
		        }

		        boolean hasReasonCode = false;
		        if(resditEvent.equals(MailConstantsVO.RESDIT_PENDING) ||
		       		resditEvent.equals(MailConstantsVO.RESDIT_RETURNED)) {
		       	hasReasonCode = true;
		        }

				 Map<String, ConsignmentInformationVO> consignmentMap =
					new HashMap<String, ConsignmentInformationVO>();
				/*
				 * This loop does the grouping of mailbags into different RESDITs
				 */
				 for(ContainerVO containerVo : containerVos){
					 containerVo.setCompanyCode(carditEnquiryVo.getCompanyCode());
					String consignmentKey= containerVo.getConsignmentDocumentNumber();
					ConsignmentInformationVO consignInfoVO =
						consignmentMap.get(consignmentKey);
					 if(consignInfoVO == null) {
						 consignInfoVO = constructConsignInfoVOForULD(containerVo,
								resditMessageVO, carditEnquiryVo);
				         consignmentMap.put(consignmentKey,consignInfoVO);
					 }
				 updateConsignInfoVOForULD(resditMessageVO,consignInfoVO,containerVo,
							hasTransportInfo, hasHandedoverInfo,
							hasReasonCode);
				 }
				resditMessageVO.setConsignmentInformationVOs(
						new ArrayList<ConsignmentInformationVO>(consignmentMap.values()));
				generateSequencesForResdit(resditMessageVO);
				resditMessageVos.add(resditMessageVO);
		   }
			log.exiting("Resdit", "groupResditMessages");

	   }

		/**
		 * This method generates INTREF and MSGREF sequences for RESDIT
		 * Sep 11, 2006, a-1739
		 * @param resditMessageVO
		 * @throws SystemException
		 */
		private void generateSequencesForResdit(ResditMessageVO resditMessageVO)
		throws SystemException {
			log.entering("Resdit", "generateSequencesForResdit");
			Criterion interchangeCriterion = KeyUtils.getCriterion(
					resditMessageVO.getCompanyCode(),
					KEY_RESDIT_INTERCHANGE, resditMessageVO.getRecipientID());

			resditMessageVO.setInterchangeControlReference(
					KeyUtilInstance.getInstance().getKey(interchangeCriterion));

			Criterion mesrefCriterion = KeyUtils.getCriterion(
					resditMessageVO.getCompanyCode(),
					KEY_MESSAGE_REFERENCE, resditMessageVO.getRecipientID());

			resditMessageVO.setMessageReferenceNumber(
					KeyUtilInstance.getInstance().getKey(mesrefCriterion));

			log.exiting("Resdit", "generateSequencesForResdit");
		}


	    /**
	     * @author a-1936
	     * This method is used to construct the Container Information Vo
	     * @param containerVo
	     * @return
	     */
		private  ContainerInformationVO constructContainerInformationVo(
				ContainerVO containerVo){
			 log.entering("Resdit","constructContainerInformationVo");
			   ContainerInformationVO containerInformationVo = new ContainerInformationVO();
				containerInformationVo.setCarrierCode(containerVo.getCarrierCode());
				containerInformationVo.setCodeListResponsibleAgency(containerVo.getCodeListResponsibleAgency());
				containerInformationVo.setContainerNumber(containerVo.getContainerNumber());
				//containerInformationVo.setContainerWeight(containerVo.getContainerWeight());added by A-7371
				containerInformationVo.setContainerWeight(String.valueOf(containerVo.getContainerWeight().getSystemValue()));
				containerInformationVo.setEquipmentQualifier(containerVo.getEquipmentQualifier());
				containerInformationVo.setMeasurementDimension(containerVo.getMeasurementDimension());
				containerInformationVo.setSealNumber(containerVo.getContainerSealNumber());
				containerInformationVo.setTypeCodeListResponsibleAgency(containerVo.getTypeCodeListResponsibleAgency());
				containerInformationVo.setEventDate(containerVo.getEventTime().toGMTDate());
				containerInformationVo.setContainerType(containerVo.getTypeCode());
				containerInformationVo.setEventSequenceNumber(containerVo.getEventSequenceNumber());
				containerInformationVo.setPartnerId(containerVo.getPaCode());
			 log.exiting("Resdit","constructContainerInformationVo");
			return containerInformationVo;
		}


	    /**
	     * Utilty for finding syspar
	     * Mar 23, 2007, A-1739
	     * @param syspar
	     * @return
	     * @throws SystemException
	     */
	    private String findSystemParameterValue(String syspar)
	        throws SystemException {
	        String sysparValue = null;
	        ArrayList < String> systemParameters = new ArrayList<String>() ;
	        systemParameters.add(syspar) ;
	        HashMap < String, String> systemParameterMap =
	            new SharedDefaultsProxy().
	            findSystemParameterByCodes(systemParameters) ;
	        log.log(Log.FINE, " systemParameterMap " + systemParameterMap);
	        if(systemParameterMap != null) {
	            sysparValue = systemParameterMap.get(syspar);
	        }
	        return sysparValue;
	    }

		/**
		 * TODO Purpose
		 * Sep 28, 2006, a-1739
		 * @param companyCode
		 * @param carrierId
		 * @param flightNumber
		 * @param flightSequenceNumber
		 * @param transportInformationVO
		 * @param segmentSerialNumber
		 * @throws SystemException
		 */
		private void populateSegmentDetails(String companyCode, int carrierId,
				String flightNumber, long flightSequenceNumber,
				TransportInformationVO transportInformationVO, int segmentSerialNumber)
		throws SystemException {
			log.entering("Resdit", "populateSegmentDetails");
			Collection<FlightSegmentSummaryVO> segmentSummaryVOs =
				new FlightOperationsProxy().findFlightSegments(
					companyCode, carrierId, flightNumber, flightSequenceNumber);
			String segOrigin = "";
			String segDestination = "";

			FlightSegmentSummaryVO segmentSummaryVO = null;
			if(segmentSummaryVOs != null && segmentSummaryVOs.size() > 0) {
				for(FlightSegmentSummaryVO segmentSumaryVO : segmentSummaryVOs) {
					segOrigin = segmentSumaryVO.getSegmentOrigin();
					segDestination = segmentSumaryVO.getSegmentDestination();
					if(segmentSumaryVO.getSegmentSerialNumber() ==
						segmentSerialNumber) {
						segmentSummaryVO = segmentSumaryVO;
					}
				}
			}

			if(segmentSummaryVO != null) {
				FlightSegmentFilterVO segmentFilter = new FlightSegmentFilterVO();
				segmentFilter.setCompanyCode(companyCode);
				segmentFilter.setFlightCarrierId(carrierId);
				segmentFilter.setFlightNumber(flightNumber);
				segmentFilter.setSequenceNumber(flightSequenceNumber);
				segmentFilter.setOrigin(segmentSummaryVO.getSegmentOrigin());
				segmentFilter.setDestination(
						segmentSummaryVO.getSegmentDestination());

				FlightSegmentValidationVO segmentValidationVO =
					new FlightOperationsProxy().validateFlightSegment(segmentFilter);

				log.log(Log.FINEST, "segmentdata from flt " + segmentValidationVO);

				transportInformationVO.setArrivalPlace(
						segmentSummaryVO.getSegmentDestination());
				transportInformationVO.setDeparturePlace(
						segmentSummaryVO.getSegmentOrigin());

				boolean isAtd = MailConstantsVO.FLAG_YES.equals(
					findSystemParameterValue(MailConstantsVO.RESDIT_ATD_REQD));
				if(isAtd) {
					if(segmentValidationVO.getActualTimeOfDeparture() != null) {
						transportInformationVO.setDepartureTime(//ATD set
							segmentValidationVO.getActualTimeOfDeparture());
					} else {
						//not departed
						transportInformationVO.setDepartureTime(//STD set
							segmentValidationVO.getScheduleTimeOfDeparture());
					}
				} else {
					transportInformationVO.setDepartureTime(//STD set
						segmentValidationVO.getScheduleTimeOfDeparture());
				}

				transportInformationVO.setArrivalTime(
						segmentValidationVO.getScheduleTimeOfArrival());

				LocalDate departureDate =
					new LocalDate(transportInformationVO.getDeparturePlace(),
						Location.ARP, false);
				departureDate.setDate(
						transportInformationVO.getDepartureTime().toDisplayDateOnlyFormat());
				transportInformationVO.setDepartureDate(departureDate);
			}else {
				/*
				 * This case is written as a fix to QF Bug 69653
				 * The bug description says that,
				 * If the flight segment is deleted after marking the ATD,
				 * UPLIFT RESDIT marked should be send with the Actual event Date and time.
				 * This change is well known to the QF business team.
				 *
				 * The Arrival & Departure Place is set as the POU & POL respectively
				 * And the Arrival & Departure Time is set as the EventDateUTC
				 *
				 * This code is changed after the discussion with Srijith B
				 */
				AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
				assignedFlightSegmentPK.setCompanyCode(companyCode);
				assignedFlightSegmentPK.setCarrierId(carrierId);
				assignedFlightSegmentPK.setFlightNumber(flightNumber);
				assignedFlightSegmentPK.setFlightSequenceNumber(flightSequenceNumber);
				assignedFlightSegmentPK.setSegmentSerialNumber(segmentSerialNumber);

				AssignedFlightSegment asgFltSeg = null;
				try {
					asgFltSeg = AssignedFlightSegment.find(assignedFlightSegmentPK);
				} catch (FinderException finderException) {
					log.log(Log.INFO, " error msg \n\n ", finderException.getMessage());
				}
				if(asgFltSeg != null) {
					transportInformationVO.setArrivalPlace(asgFltSeg.getPou());
					transportInformationVO.setDeparturePlace(asgFltSeg.getPol());
				}
				/** Added by A-4809 as a temporary fix for Uplift resdit issue found in TK
				 * When departure time is not for uplift resdit null pointer exception thrown 
				 * in case of flight route update
				 */
				if(segOrigin!=null && segDestination !=null && segOrigin.trim().length()>0 && segDestination.trim().length()>0){
				FlightSegmentFilterVO segmentFilter = new FlightSegmentFilterVO();
				segmentFilter.setCompanyCode(companyCode);
				segmentFilter.setFlightCarrierId(carrierId);
				segmentFilter.setFlightNumber(flightNumber);
				segmentFilter.setSequenceNumber(flightSequenceNumber);
				segmentFilter.setOrigin(segOrigin);
				segmentFilter.setDestination(segDestination);
				FlightSegmentValidationVO segmentValidationVO =
					new FlightOperationsProxy().validateFlightSegment(segmentFilter);
				log.log(Log.FINEST, "segmentdata from flt " + segmentValidationVO);
				boolean isAtd = MailConstantsVO.FLAG_YES.equals(
						findSystemParameterValue(MailConstantsVO.RESDIT_ATD_REQD));
					if(isAtd) {
						if(segmentValidationVO.getActualTimeOfDeparture() != null) {
							transportInformationVO.setDepartureTime(//ATD set
								segmentValidationVO.getActualTimeOfDeparture());
						} else {
							//not departed
							transportInformationVO.setDepartureTime(//STD set
								segmentValidationVO.getScheduleTimeOfDeparture());
						}
					} else {
						transportInformationVO.setDepartureTime(//STD set
							segmentValidationVO.getScheduleTimeOfDeparture());
					}
				}
				// Added by A-4809 ends..
			}
			log.exiting("Resdit", "populateSegmentDetails");

		}

		/**
		 *The string in conveyance reference in split into different parts
		 * Sep 13, 2006, a-1739
		 * @param companyCode
		 * @param transportInformationVO
		 * @throws SystemException
		 */
		private void updateTransportInformationDetails(
				String companyCode, TransportInformationVO transportInformationVO)
			throws SystemException {
			log.entering("Resdit", "updateTransportInformationDetails");

			String conveyanceRef = transportInformationVO.getConveyanceReference();
			String[] flightDetails = conveyanceRef.split(FLIGHTID_DELIM);
			String carrierCode = flightDetails[0];
			int carrierId = Integer.parseInt(flightDetails[1]);
			String flightNumber = flightDetails[2];
			long flightSequenceNumber = Long.parseLong(flightDetails[3]);
			int segmentSerialNumber = Integer.parseInt(flightDetails[4]);

			transportInformationVO.setCarrierCode(carrierCode);
			transportInformationVO.setCarrierID(carrierId);
			transportInformationVO.setFlightNumber(flightNumber);
			transportInformationVO.setFlightSequenceNumber(flightSequenceNumber);
			transportInformationVO.setSegmentSerialNumber(segmentSerialNumber);
			transportInformationVO.setConveyanceReference(
					carrierCode.concat(flightNumber));

			//get flight arrival details
			populateSegmentDetails(
					companyCode, carrierId, flightNumber, flightSequenceNumber,
					transportInformationVO, segmentSerialNumber);

			transportInformationVO.setModeOfTransport(
					ResditMessageVO.AIR_TRANSPORT);
			transportInformationVO.setTransportStageQualifier(
					ResditMessageVO.MAIN_CARRIAGE_TRT);
			log.exiting("Resdit", "updateTransportInformationDetails");

		}



		/**
		 * @a-1739
		 * @param companyCode
		 * @param consignInfoVO
		 * @param containerVo
		 * @throws SystemException
		 */
		private void populateContainerTransportInformation(
				String companyCode, ConsignmentInformationVO consignInfoVO,
				ContainerVO containerVo) throws SystemException {
			log.entering("Resdit", "populateContainerTransportInformation");
			Collection<TransportInformationVO> transportInfoVOs =
				consignInfoVO.getTransportInformationVOs();
			if(transportInfoVOs == null) {
				transportInfoVOs = new ArrayList<TransportInformationVO>();
				consignInfoVO.setTransportInformationVOs(transportInfoVOs);
			}
			TransportInformationVO transportInfoVO =
				new TransportInformationVO();
			transportInfoVO.setDeparturePlace(
					containerVo.getResditEventPort());
			transportInfoVO.setDepartureLocationQualifier(
					ResditMessageVO.PLACE_OF_DEPARTURE);
			String conveyRefNo = new StringBuilder().
				append(containerVo.getCarrierCode()).
				append(FLIGHTID_DELIM).
				append(containerVo.getCarrierId()).
				append(FLIGHTID_DELIM).
				append(containerVo.getFlightNumber()).
				append(FLIGHTID_DELIM).
				append(containerVo.getFlightSequenceNumber()).
				append(FLIGHTID_DELIM).
				append(containerVo.getSegmentSerialNumber()).
				toString();
			log.log(Log.FINE,"THE CONVEYANCE REFERENCE NUMBER"+conveyRefNo);
			transportInfoVO.setConveyanceReference(conveyRefNo);
			updateTransportInformationDetails(
					companyCode, transportInfoVO);

			if(MailConstantsVO.RESDIT_UPLIFTED.equals(
				consignInfoVO.getConsignmentEvent())) {
				consignInfoVO.setEventDate(
					transportInfoVO.getDepartureDate().toGMTDate());
			}

			transportInfoVOs.add(transportInfoVO);
			log.exiting("Resdit", "populateContainerTransportInformation");
		}



		private static MailTrackingDefaultsDAO constructDAO()
				throws SystemException {
			        try {
			            return MailTrackingDefaultsDAO.class.cast(
			            		PersistenceController.getEntityManager().getQueryDAO(
			                    MAILTRACKING_DEFAULTS));
			        } catch(PersistenceException exception) {
			            throw new SystemException("No dao impl found", exception);
			        }
			    }



		/**
		 * TODO Purpose
		 * @param eventCode
		 * @param consignInfoVO
		 * @param resditMessageVO
		 * @throws SystemException
		 */
		private String findPartyName(String companyCode,
				String partyCode) throws SystemException {
			String partyName="";
			try {
				partyName= constructDAO().findPartyName(companyCode,partyCode);
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				log.log(Log.INFO, " error msg \n\n ", e.getMessage());
			}
			return partyName;
		}


		/**
		 * Populates the HandedOver Information for the RESDIT
		 * Sep 13, 2006, a-1739
		 * @param consignInfoVO
		 * @param resditMessageVO
		 * @throws SystemException
		 */
		private void populateHandedOverInformation(
				ConsignmentInformationVO consignInfoVO,
				ResditMessageVO resditMessageVO) throws SystemException {
			log.entering("Resdit", "populateHandedOverInformation");
			String eventCode = consignInfoVO.getConsignmentEvent();
			String partyName="";
			Collection<ReceptacleInformationVO> receptacleInfoVOs =
				consignInfoVO.getReceptacleInformationVOs();
			if(receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
				//If receptacles are present take info from them
				ReceptacleInformationVO receptacleInfoVO = null;
				for(ReceptacleInformationVO receptacleInfo : receptacleInfoVOs) {
					receptacleInfoVO = receptacleInfo;
					break;
				}

//				if(MailConstantsVO.RESDIT_RECEIVED.equals(eventCode)) {
//					//Incase of RECEIVED partner id is the carriercod
//					consignInfoVO.setPartnerID(receptacleInfoVO.getCarrierCode());
//				} else {
					consignInfoVO.setPartnerID(receptacleInfoVO.getPartnerId());
					consignInfoVO.setOriginExgOffice(receptacleInfoVO.getOriginExgOffice());
					if(consignInfoVO.getPartnerID()!=null){
					partyName=findPartyName(resditMessageVO.getCompanyCode(),consignInfoVO.getPartnerID());
					if(partyName!=null){
						consignInfoVO.setPartyName(partyName);
					  }
					}
////				}
			} else {
				//take from Containers
				Collection<ContainerInformationVO> containerInfoVOs =
					consignInfoVO.getContainerInformationVOs();
				if(containerInfoVOs != null && containerInfoVOs.size() > 0) {
					ContainerInformationVO containerInfoVO = null;
					for(ContainerInformationVO containerInfo : containerInfoVOs) {
						containerInfoVO = containerInfo;
						break;
					}

//					if(MailConstantsVO.RESDIT_RECEIVED.equals(eventCode)) {
//						consignInfoVO.setPartnerID(containerInfoVO.getCarrierCode());
//					} else {
						consignInfoVO.setPartnerID(containerInfoVO.getPartnerId());
						if(consignInfoVO.getPartnerID()!=null){
							partyName=findPartyName(resditMessageVO.getCompanyCode(),consignInfoVO.getPartnerID());
							if(partyName!=null){
								consignInfoVO.setPartyName(partyName);
							  }
							}
//					}
				}
			}
			populateResponsibleAgencyCodes(eventCode, consignInfoVO, resditMessageVO);
			populatePartyQualifier(eventCode, consignInfoVO, resditMessageVO);
			log.exiting("Resdit", "populateHandedOverInformation");

		}




		/**
		 * @a-1739
		 * @param eventCode
		 * @param consignInfoVO
		 * @param resditMessageVO
		 * @return
		 * @throws SystemException
		 */
		private String getPartyQualifier(String eventCode,
				ConsignmentInformationVO consignInfoVO,
				ResditMessageVO resditMessageVO) throws SystemException {
			log.entering("Resdit", "getPartyQualifier");
	//Modified as part of CRQ ICRD-111886 by A-5526
			 if(eventCode.equals(MailConstantsVO.RESDIT_DELIVERED) ||
					 eventCode.equals(MailConstantsVO.RESDIT_READYFOR_DELIVERY ) ) {
		            //Handed over to administration of
		            //destination of consignment
		            return ResditMessageVO.CONSIGNMENT_DESTINATION;
		        }
		        else if(eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_OFFLINE)
		        		|| eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_RECEIVED)
		        			||eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_ONLINE)
		        				|| eventCode.equals(MailConstantsVO.RESDIT_UPLIFTED)) {
		            //Handed over to connecting carrier
		            return ResditMessageVO.CONNECTING_CARRIER;
		        }
		        else if(eventCode.equals(MailConstantsVO.RESDIT_RECEIVED)) {
		                return ResditMessageVO.CONSIGNMENT_ORIGIN;
		        }
		        else if(eventCode.equals(MailConstantsVO.RESDIT_RETURNED)){
		        	if(resditMessageVO.getMessageType()!=null && ResditMessageVO.MSG_TYPB.equalsIgnoreCase(resditMessageVO.getMessageType())){
		        		//Modified as part of CRQ ICRD-111886 by A-5526
		        		//return ResditMessageVO.AGENT;
		        		return ResditMessageVO.CONSIGNMENT_ORIGIN;
					    }else{
					        	log.log(Log.FINEST, "partnerId --" + consignInfoVO.getPartnerID());
					            if(consignInfoVO.getPartnerID().equals(
					            		resditMessageVO.getRecipientID())) {
					            	return ResditMessageVO.CONSIGNMENT_ORIGIN;
					            } else if(consignInfoVO.getPartnerID().equals(
					            		new MailController().findPAForOfficeOfExchange(
					            				resditMessageVO.getCompanyCode(),
					            				constructDOEForMail(consignInfoVO)))) {
					            	return ResditMessageVO.CONSIGNMENT_DESTINATION;
					            }
					        }
		            }
//		        		The party is an agent or a
	            //representative handling agent
	            return ResditMessageVO.AGENT;
		}


		/**
		 * TODO Purpose
		 * Sep 19, 2006, a-1739
		 * @param consignInfoVO
		 * @return
		 */
		private String constructDOEForMail(
				ConsignmentInformationVO consignInfoVO) {

			Collection<ReceptacleInformationVO> receptacleInfos =
				consignInfoVO.getReceptacleInformationVOs();
			for(ReceptacleInformationVO receptacleVO : receptacleInfos) {
				return receptacleVO.getReceptacleID().substring(5, 10);
			}

			return null;
		}


		/**
		 * TODO Purpose
		 * Sep 14, 2006, a-1739
		 * @param eventCode
		 * @param consignInfoVO
		 * @param resditMessageVO
		 * @throws SystemException
		 */
		private void populatePartyQualifier(String eventCode,
				ConsignmentInformationVO consignInfoVO,
				ResditMessageVO resditMessageVO) throws SystemException {
			log.entering("Resdit", "populatePartyQualifier");
	            consignInfoVO.setPartyQualifier(
	                getPartyQualifier(eventCode, consignInfoVO, resditMessageVO));
	            consignInfoVO.setMutuallyDefined(false);
			log.exiting("Resdit", "populatePartyQualifier");

		}


		/**
		 * TODO Purpose
		 * @param eventCode
		 * @param consignInfoVO
		 * @param resditMessageVO
		 * @throws SystemException
		 */
		private void populateResponsibleAgencyCodes(String eventCode,
				ConsignmentInformationVO consignInfoVO,
				ResditMessageVO resditMessageVO) throws SystemException {
			log.entering("Resdit", "populateResponsibleAgencyCodes");
	        /*
	          As confirmed by naveen jacob
	                NAD+CZ+US101:160:139'---- 74
					NAD+CN+AT001:160:139'----- 21
					NAD+AG+US101:160:139'------- 82

					NAD+CH+QF:160:3' --- 43
					NAD+CH+QF:160:3' --------- 41
					NAD+CH+UL:160:3  ---------- 42

					NAD - 24 None
					NAD - 57 None
					NAD - 6 None

	         */

			/*if(eventCode.equals(MailConstantsVO.RESDIT_RECEIVED)||
					eventCode.equals(MailConstantsVO.RESDIT_RETURNED)||
					eventCode.equals(MailConstantsVO.RESDIT_DELIVERED)||
					eventCode.equals(MailConstantsVO.RESDIT_READYFOR_DELIVERY)){
				consignInfoVO.setResponsibleAgencyCodes(ResditMessageVO.AGY_UPU);

				if (MailConstantsVO.AU_POST.equals(resditMessageVO.getRecipientID())){
					consignInfoVO.setResponsibleAgencyCodesForLoc (ResditMessageVO.AGY_IATA);
				}else {
					consignInfoVO.setResponsibleAgencyCodesForLoc (ResditMessageVO.AGY_UPU);
				}
			}else */
			if (eventCode.equals(MailConstantsVO.RESDIT_RECEIVED)
					|| eventCode.equals(MailConstantsVO.RESDIT_RETURNED)
					|| eventCode.equals(MailConstantsVO.RESDIT_DELIVERED)
					|| eventCode.equals(MailConstantsVO.RESDIT_READYFOR_DELIVERY)
					|| eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_ONLINE)
					|| eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_OFFLINE)
					|| eventCode.equals(MailConstantsVO.RESDIT_HANDOVER_RECEIVED)
					|| eventCode.equals(MailConstantsVO.RESDIT_UPLIFTED)
					){
					consignInfoVO.setResponsibleAgencyCodes(ResditMessageVO.AGY_UPU);
					consignInfoVO.setResponsibleAgencyCodesForLoc(ResditMessageVO.AGY_UPU);
			}else if(eventCode.equals(MailConstantsVO.RESDIT_LOADED)){
				consignInfoVO.setResponsibleAgencyCodes(ResditMessageVO.AGY_IATA);
				consignInfoVO.setResponsibleAgencyCodesForLoc(ResditMessageVO.AGY_IATA);
			}
			log.exiting("Resdit", "populateResponsibleAgencyCodes");

		}



	 /**
		 * @a-1739
		 * @param resditMessageVO
		 * @param consignInfoVO
		 * @param containerVo
		 * @param hasTransportInfo
		 * @param hasHandedoverInfo
		 * @param hasReasonCode
		 * @throws SystemException
		 */
		private void updateConsignInfoVOForULD(ResditMessageVO resditMessageVO,
				ConsignmentInformationVO consignInfoVO,
				ContainerVO containerVo, boolean hasTransportInfo,
				boolean hasHandedoverInfo, boolean hasReasonCode)
			throws SystemException {
			log.entering("Resdit", "updateConsignInfoVOForULD");
			Collection<ContainerInformationVO> containerCollection =
				consignInfoVO.getContainerInformationVOs();
			if(containerCollection == null) {
				containerCollection = new ArrayList<ContainerInformationVO>();
				consignInfoVO.setContainerInformationVOs(containerCollection);
			}
			containerCollection.add(constructContainerInformationVo(containerVo));

			//TODO add SB ULD
			log.log(Log.FINE,"THE TRANSPORT INFO"+hasTransportInfo);
			log.log(Log.FINE,"THE HANDED OVER  INFO"+hasHandedoverInfo);
			log.log(Log.FINE," HAS REASON CODE "+hasReasonCode);

			if((consignInfoVO.getTransportInformationVOs() == null ||
					consignInfoVO.getTransportInformationVOs().size() == 0) &&
					hasTransportInfo) {
				populateContainerTransportInformation(containerVo.getCompanyCode(),consignInfoVO, containerVo);
			}

			if(hasHandedoverInfo) {
				populateHandedOverInformation(consignInfoVO,
	    			resditMessageVO);
			}

			if(consignInfoVO.getEventReason() == null && hasReasonCode) {

				// The Station Code is same as the ResditEventPort
				populateReasonCodes(consignInfoVO,
						resditMessageVO.getCompanyCode(),resditMessageVO.getStationCode());
			}
	    	log.log(Log.FINE,"THE RESDIT MESSAGE VO IS "+resditMessageVO);
	        computeResditEventDate(consignInfoVO);
			log.exiting("Resdit", "updateConsignInfoVOForULD");


		}


		/**
		 * TODO Purpose
		 * Sep 14, 2006, a-1739
		 * @param receptacleInfoVOs
		 * @return
		 */
		private GMTDate getLatestReceptacleEventDate(
				Collection<ReceptacleInformationVO> receptacleInfoVOs) {
			log.entering("Resdit", "getLatestReceptacleEventDate");
			GMTDate latestEventDate = null;
			for(ReceptacleInformationVO receptacleInfoVO : receptacleInfoVOs) {
				GMTDate eventDate = receptacleInfoVO.getEventDate();
				if(latestEventDate == null) {
					latestEventDate = eventDate;
				} else if(latestEventDate.isLesserThan(eventDate)) {
					latestEventDate = eventDate;
				}
			}
			log.exiting("Resdit", "getLatestReceptacleEventDate");
			return latestEventDate;
		}



		/**
		 * Finds the RESDIT event date from ULD & RCP event dates
		 * Sep 14, 2006, a-1739
		 * @param consignInfoVO
		 */
		private void computeResditEventDate(ConsignmentInformationVO consignInfoVO) {
			log.entering("Resdit", "computeResditEventDate");
			GMTDate resditEventDate = null;
			Collection<ReceptacleInformationVO> receptacleInfoVOs =
				consignInfoVO.getReceptacleInformationVOs();
			GMTDate receptacleEventDate = null;
			if(receptacleInfoVOs !=  null && receptacleInfoVOs.size() > 0) {
				 receptacleEventDate =
					getLatestReceptacleEventDate(receptacleInfoVOs);
			}
			Collection<ContainerInformationVO> containerInfoVOs =
				consignInfoVO.getContainerInformationVOs();
			GMTDate containerEventDate = null;
			if(containerInfoVOs != null && containerInfoVOs.size() > 0) {
				containerEventDate =
					getLatestContainerEventDate(containerInfoVOs);
			}
			if(receptacleEventDate == null) {
				resditEventDate = containerEventDate;
			} else if(containerEventDate == null) {
				resditEventDate = receptacleEventDate;
			} else if(receptacleEventDate.isGreaterThan(containerEventDate)) {
				resditEventDate = receptacleEventDate;
			} else {
				resditEventDate = containerEventDate;
			}

			consignInfoVO.setEventDate(resditEventDate);
			log.exiting("Resdit", "computeResditEventDate");

		}



		/**
		 * Finds the latest event date of ULD
		 * Sep 15, 2006, a-1739
		 * @param containerInfoVOs
		 * @return
		 */
		private GMTDate getLatestContainerEventDate(
				Collection<ContainerInformationVO> containerInfoVOs) {
			log.entering("Resdit", "getLatestContainerEventDate");
			GMTDate latestEventDate = null;
			for(ContainerInformationVO containerInfoVO : containerInfoVOs) {
				GMTDate eventDate = containerInfoVO.getEventDate();
				if(latestEventDate == null) {
					latestEventDate = eventDate;
				} else  if(latestEventDate.isLesserThan(eventDate)) {
					latestEventDate = eventDate;
				}
			}
			log.exiting("Resdit", "getLatestContainerEventDate");
			return latestEventDate;
		}

		/**
		 * Finds the offload reason code
		 * Sep 14, 2006, a-1739
		 * @param resditEventVO
		 * @param consignInfoVO
		 * @param companyCode
		 * @param eventPort
		 * @throws SystemException
		 */
		private void populateReasonCodes(ConsignmentInformationVO consignInfoVO,
				String companyCode, String eventPort) throws SystemException {
			log.entering("Resdit", "populateOffloadReasonCode");
			Collection<ReceptacleInformationVO> receptacleInfoVOs =
				consignInfoVO.getReceptacleInformationVOs();

			String eventCode = consignInfoVO.getConsignmentEvent();
			if(receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
				ReceptacleInformationVO receptacleVO = null;
				for(ReceptacleInformationVO receptacleInfoVO : receptacleInfoVOs) {
					receptacleVO = receptacleInfoVO;
					break;
				}
				if(eventCode.equals(MailConstantsVO.RESDIT_PENDING)) {
					consignInfoVO.setEventReason(
							MailOffloadDetail.findOffloadReasonForMailbag(
									companyCode,
									receptacleVO.getReceptacleID()));
					} else {
						consignInfoVO.setEventReason(
								DamagedMailbag.findDamageReason(
										companyCode,
										receptacleVO.getReceptacleID(),
										eventPort));
					}
			} else {
				//then container must be present

				Collection<ContainerInformationVO> containerInfoVOs =
					consignInfoVO.getContainerInformationVOs();

				//its a work around to identify the issue of populate reason codes coming null in some cases..
				if(containerInfoVOs==null){
					log.log(Log.FINE, "populate reason codes issue Check This consignment --->>>" + consignInfoVO.getConsignmentID());
				}

				ContainerInformationVO containerInformationVO = null;
				for(ContainerInformationVO containerInfoVO : containerInfoVOs) {
					containerInformationVO = containerInfoVO;
					break;
				}
				consignInfoVO.setEventReason(
						MailOffloadDetail.findOffloadReasonForULD(
							companyCode,
							containerInformationVO.getContainerNumber()));
			}
			log.exiting("Resdit", "populateOffloadReasonCode");

		}

	 /**
		 * @author a-1739
		 * @param containerVo
		 * @param resditMessageVO
		 * @param carditEnquiryVO
		 * @return
		 */
		private ConsignmentInformationVO constructConsignInfoVOForULD(ContainerVO containerVo,ResditMessageVO resditMessageVO,
				CarditEnquiryVO carditEnquiryVO){
			 //set Header details
			  if(resditMessageVO.getStationCode()== null){
				  resditMessageVO.setStationCode(carditEnquiryVO.getResditEventPort());
				  resditMessageVO.setSenderID(containerVo.getCarditRecipientId());
				  resditMessageVO.setRecipientID(containerVo.getPaCode());
			  }
			  ConsignmentInformationVO consignInfoVO = new ConsignmentInformationVO();
			  consignInfoVO.setConsignmentID(containerVo.getConsignmentDocumentNumber());
	          consignInfoVO.setMessageSequenceNumber(containerVo.getResditEventSeqNum());
	          //CONSIGNMENT EVENT INFORMATION
	          //SG4-SG5-STS-C555-9011 consignment event
	          consignInfoVO.setConsignmentEvent(carditEnquiryVO.getResditEventCode());
	          //SG4-SG5-DTM-C507-2379
	          consignInfoVO.setDateFormatQualifier(201);
	          //SG4-SG5-DTM-C507-2005
	          consignInfoVO.setDateQualifier(7);
	          //HAND-OVER-INFORMATION
	          //SG4-SG5-LOC-C517-3225 place of handover
	          consignInfoVO.setTransferLocation(carditEnquiryVO.getResditEventPort());
	          consignInfoVO.setEventDate(containerVo.getEventTime().toGMTDate());
	          //SG4-CNI-1373 consignment response
	          //if  (isPartialResdit(resditEventVO, consignInfoVO)) {
	      	  consignInfoVO.setConsignmentResponse(
	      			CONSIGNMENT_PARTIALLY_ACCEPTED);
	      	  log.log(Log.FINE,"THE PARTNER ID "+containerVo.getPaCode());
	      	  consignInfoVO.setPartnerID(containerVo.getPaCode());
			  return consignInfoVO;
		 }


	/**
	 * @a-1739
	 * @param mailbagVOs
	 * @param resditMessageVOs
	 * @param carditEnquiryVO
	 * @throws SystemException
	 */
	private void constructResditMessagesForMail(
			Collection<MailbagVO> mailbagVOs,
			Collection<ResditMessageVO> resditMessageVOs,
			CarditEnquiryVO carditEnquiryVO) throws SystemException {
		log.entering("Resdit", "groupResditMessages");

		/*
		 * In future if multiple events need to be send this part will
		 * go inside the loop
		 */
		ResditMessageVO resditMessageVO = new ResditMessageVO();
		resditMessageVO.setCompanyCode(carditEnquiryVO.getCompanyCode());
		resditMessageVO.setMessageType(ResditMessageVO.MSG_TYP);
		resditMessageVO.setMessageStandard(ResditMessageVO.EDIFACT);
//		TODO getindicator thru query resditMessageVO.setTestIndicator("1");

		resditMessageVO.setLocNeeded(true);
		LocalDate preparationDate =
			new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		resditMessageVO.setPreparationDate(
				preparationDate.toDisplayFormat(PREP_DATE_FORMAT));
		resditMessageVO.setPreparationTime(
				preparationDate.toDisplayFormat(PREP_TIME_FORMAT));

		String resditEvent = carditEnquiryVO.getUnsendResditEvent();

        boolean hasTransportInfo = false;

        if(!MailConstantsVO.RESDIT_RECEIVED.equals(resditEvent) &&
        		!MailConstantsVO.RESDIT_RETURNED.equals(resditEvent) &&
        		!MailConstantsVO.RESDIT_PENDING.equals(resditEvent)) {
        	hasTransportInfo = true;
        }

        boolean hasHandedoverInfo = false;
        if(!resditEvent.equals(MailConstantsVO.RESDIT_UPLIFTED) &&
        		!resditEvent.equals(MailConstantsVO.RESDIT_PENDING) &&
        		!resditEvent.equals(MailConstantsVO.RESDIT_ASSIGNED)) {
        	hasHandedoverInfo = true;
        }

        boolean hasReasonCode = false;
        if(resditEvent.equals(MailConstantsVO.RESDIT_PENDING) ||
        		resditEvent.equals(MailConstantsVO.RESDIT_RETURNED)) {
        	hasReasonCode = true;
        }

		Map<String, ConsignmentInformationVO> consignmentMap =
			new HashMap<String, ConsignmentInformationVO>();
		/*
		 * This loop does the grouping of mailbags into different RESDITs
		 */
		for(MailbagVO mailbagVO : mailbagVOs){
			mailbagVO.setCompanyCode(carditEnquiryVO.getCompanyCode());
			String consignmentKey= mailbagVO.getConsignmentNumber();
			ConsignmentInformationVO consignInfoVO =
				consignmentMap.get(consignmentKey);
			if(consignInfoVO == null) {
				consignInfoVO = constructConsignInfoVOForMail(mailbagVO,
						resditMessageVO, carditEnquiryVO);
		        consignmentMap.put(consignmentKey, consignInfoVO);
			}
			updateConsignInfoVOForMail(resditMessageVO,consignInfoVO,mailbagVO,
					hasTransportInfo, hasHandedoverInfo,
					hasReasonCode);
			
			//A-8061 Added for ICRD-82434 starts
			if(mailbagVO.getCarditKey()!=null&&mailbagVO.getCarditKey().trim().length() > 0){
				
				resditMessageVO.setCarditExist(MailConstantsVO.FLAG_YES);
			}
			else{
				resditMessageVO.setCarditExist(MailConstantsVO.FLAG_NO);
			}
			//A-8061 Added for ICRD-82434 end
		}
		resditMessageVO.setConsignmentInformationVOs(
				new ArrayList<ConsignmentInformationVO>(consignmentMap.values()));
		generateSequencesForResdit(resditMessageVO);
		resditMessageVOs.add(resditMessageVO);
		log.exiting("Resdit", "groupResditMessages");
	}




	/**
	 * TODO Purpose
	 * Feb 12, 2007, A-1739
	 * @param resditMessageVO
	 * @param consignInfoVO
	 * @param mailbagVO
	 * @param hasTransportInfo
	 * @param hasHandedoverInfo
	 * @param hasReasonCode
	 * @throws SystemException
	 */
	private void updateConsignInfoVOForMail(ResditMessageVO resditMessageVO,
			ConsignmentInformationVO consignInfoVO,
			MailbagVO mailbagVO, boolean hasTransportInfo,
			boolean hasHandedoverInfo, boolean hasReasonCode)
		throws SystemException {

		Collection<ReceptacleInformationVO> receptacleColln =
			consignInfoVO.getReceptacleInformationVOs();
		if(receptacleColln == null) {
			receptacleColln = new ArrayList<ReceptacleInformationVO>();
			consignInfoVO.setReceptacleInformationVOs(receptacleColln);
		}
		receptacleColln.add(constructReceptVO(mailbagVO));

		//TODO add SB ULD

		if((consignInfoVO.getTransportInformationVOs() == null ||
				consignInfoVO.getTransportInformationVOs().size() == 0) &&
				hasTransportInfo) {
			populateMailbagTransportInformation(
					resditMessageVO.getCompanyCode(),
					consignInfoVO, mailbagVO);
		}

		if(consignInfoVO.getPartnerID() == null && hasHandedoverInfo) {
			populateHandedOverInformation(consignInfoVO,
    			resditMessageVO);
		}

		if(consignInfoVO.getEventReason() == null && hasReasonCode) {
			populateReasonCodes(consignInfoVO,
					mailbagVO.getCompanyCode(),
					mailbagVO.getResditEventPort());
		}

        computeResditEventDate(consignInfoVO);

	}




	/**
	 * TODO Purpose
	 * Feb 28, 2007, a-1739
	 * @param companyCode
	 * @param consignInfoVO
	 * @param mailbagVO
	 * @throws SystemException
	 */
	private void populateMailbagTransportInformation(
			String companyCode, ConsignmentInformationVO consignInfoVO,
			MailbagVO mailbagVO) throws SystemException {

		Collection<TransportInformationVO> transportInfoVOs =
			consignInfoVO.getTransportInformationVOs();
		if(transportInfoVOs == null) {
			transportInfoVOs = new ArrayList<TransportInformationVO>();
			consignInfoVO.setTransportInformationVOs(transportInfoVOs);
		}
		TransportInformationVO transportInfoVO =
			new TransportInformationVO();
		transportInfoVO.setDeparturePlace(
				mailbagVO.getResditEventPort());
		transportInfoVO.setDepartureLocationQualifier(
				ResditMessageVO.PLACE_OF_DEPARTURE);
		String conveyRefNo = new StringBuilder().
			append(mailbagVO.getCarrierCode()).
			append(FLIGHTID_DELIM).
			append(mailbagVO.getCarrierId()).
			append(FLIGHTID_DELIM).
			append(mailbagVO.getFlightNumber()).
			append(FLIGHTID_DELIM).
			append(mailbagVO.getFlightSequenceNumber()).
			append(FLIGHTID_DELIM).
			append(mailbagVO.getSegmentSerialNumber()).
			toString();
		transportInfoVO.setConveyanceReference(conveyRefNo);

		updateTransportInformationDetails(
				companyCode, transportInfoVO);
		if(MailConstantsVO.RESDIT_UPLIFTED.equals(
			consignInfoVO.getConsignmentEvent())) {
			consignInfoVO.setEventDate(transportInfoVO.getDepartureDate().toGMTDate());
		}

		transportInfoVOs.add(transportInfoVO);


	}



	/**
	 * TODO Purpose
	 * Feb 9, 2007, A-1739
	 * @param resditMailbagVO
	 * @return
	 * @throws SystemException 
	 */
	private ReceptacleInformationVO constructReceptVO(
			MailbagVO resditMailbagVO) throws SystemException {
		ReceptacleInformationVO receptVO =
			new ReceptacleInformationVO();
		if(findSystemParameterValue(DOMESTIC_PACODE).equals(resditMailbagVO.getPaCode()))//added by A-8464 for ICRD-286593
		{
			receptVO.setReceptacleID(resditMailbagVO.getMailbagId().substring(0, 10));
		}
		else{
			receptVO.setReceptacleID(resditMailbagVO.getMailbagId());
		}
//		receptVO.setContainerNumber(rs.getString("CONNUM"));
		receptVO.setPartnerId(resditMailbagVO.getHandoverPartner());
		receptVO.setCarrierCode(resditMailbagVO.getCarrierCode());
		receptVO.setEventDate(resditMailbagVO.getResditEventUTCDate());
		receptVO.setEventSequenceNumber(
				resditMailbagVO.getResditEventSeqNum());
		return receptVO;
	}


	/**
	 * TODO Purpose
	 * Feb 12, 2007, A-1739
	 * @param mailbagVO
	 * @param resditMessageVO
	 * @param carditEnquiryVO
	 * @return
	 */
	private ConsignmentInformationVO constructConsignInfoVOForMail(
			MailbagVO mailbagVO, ResditMessageVO resditMessageVO,
			CarditEnquiryVO carditEnquiryVO) {

		//set Header details
		if(resditMessageVO.getStationCode() == null) {
			resditMessageVO.setStationCode(mailbagVO.getResditEventPort());
			resditMessageVO.setSenderID(mailbagVO.getCarditRecipientId());
			resditMessageVO.setRecipientID(mailbagVO.getPaCode());
		}

		ConsignmentInformationVO consignInfoVO =
			new ConsignmentInformationVO();

		consignInfoVO.setConsignmentID(
				mailbagVO.getConsignmentNumber());
        consignInfoVO.setMessageSequenceNumber(
        		mailbagVO.getResditEventSeqNum());

        //CONSIGNMENT EVENT INFORMATION
        //SG4-SG5-STS-C555-9011 consignment event
        consignInfoVO.setConsignmentEvent(
        		carditEnquiryVO.getUnsendResditEvent());

        //SG4-SG5-DTM-C507-2379
        consignInfoVO.setDateFormatQualifier(201);

        //SG4-SG5-DTM-C507-2005
        consignInfoVO.setDateQualifier(7);

        //HAND-OVER-INFORMATION
        //SG4-SG5-LOC-C517-3225 place of handover
        consignInfoVO.setTransferLocation(mailbagVO.getResditEventPort());

//      SG4-CNI-1373 consignment response
//      if  (isPartialResdit(resditEventVO, consignInfoVO)) {

        //to be changed for version1.1 later
      	consignInfoVO.setConsignmentResponse(
      			CONSIGNMENT_PARTIALLY_ACCEPTED);
/*        }
      else {
      	consignInfoVO.setConsignmentResponse(
      			CONSIGNMENT_TOTALLY_ACCEPTED);
      }
*/
		return consignInfoVO;
	}




	/**
	 * TODO Purpose
	 * Mar 6, 2007, a-1739
	 * @param mailbagResditMap
	 * @param mailbagVOs
	 */
	private void groupHandedoverMailbags(
			Map<String, Collection<MailbagVO>> mailbagResditMap,
			Collection<MailbagVO> mailbagVOs) {
		String carditHndovrPartner = null;
		for(MailbagVO mailbagVO : mailbagVOs) {
			carditHndovrPartner = mailbagVO.getHandoverPartner().
												concat(mailbagVO.getConsignmentNumber());
			Collection<MailbagVO> resditMailbags =
				mailbagResditMap.get(carditHndovrPartner);
			if(resditMailbags == null) {
				resditMailbags = new ArrayList<MailbagVO>();
				mailbagResditMap.put(carditHndovrPartner, resditMailbags);
			}
			resditMailbags.add(mailbagVO);
		}
	}


	/**
	 * TODO Purpose
	 * Mar 2, 2007, a-1739
	 * @param carditEnquiryVO
	 * @return
	 * @throws SystemException
	 */
	private Map<String, Collection<MailbagVO>> groupMailbagsOfCardit(
			CarditEnquiryVO carditEnquiryVO) throws SystemException {
		log.entering("Resdit", "groupMailbagsOfCardit");
		Map<String, Collection<MailbagVO>> resditMailbagMap =
			new HashMap<String, Collection<MailbagVO>>();
		String resditEvent = carditEnquiryVO.getUnsendResditEvent();
		Collection<MailbagVO> mailbagVOs =
			carditEnquiryVO.getMailbagVos();

		/*
		 * Sort by returnreason and handoverpartner for the
		 * same carditmailbags
		 */
		if(MailConstantsVO.RESDIT_RETURNED.equals(resditEvent)) {
			groupReturnedMailbags(resditMailbagMap, mailbagVOs,
						carditEnquiryVO);

		} else if(MailConstantsVO.RESDIT_DELIVERED.equals(resditEvent) ||
				MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(resditEvent)) {
			groupHandedoverMailbags(resditMailbagMap, mailbagVOs);
		} else {
			//sort by carditKey alone
			String carditKey = null;
			for(MailbagVO mailbagVO : mailbagVOs) {
				carditKey = mailbagVO.getConsignmentNumber();
				Collection<MailbagVO> carditMails =
					resditMailbagMap.get(carditKey);
				if(carditMails == null) {
					carditMails = new ArrayList<MailbagVO>();
					resditMailbagMap.put(carditKey, carditMails);
				}
				carditMails.add(mailbagVO);
			}
		}
		log.exiting("Resdit", "groupMailbagsOfCardit");
		return resditMailbagMap;
	}


	/**
	 * TODO Purpose
	 * Mar 6, 2007, a-1739
	 * @param mailbagResditMap
	 * @param mailbagVOs
	 * @param carditEnquiryVO
	 * @throws SystemException
	 */
	private void groupReturnedMailbags(
			Map<String, Collection<MailbagVO>> mailbagResditMap,
			Collection<MailbagVO> mailbagVOs, CarditEnquiryVO carditEnquiryVO)
	throws SystemException {
		String cdtdmgReason = null;
		for(MailbagVO mailbagVO : mailbagVOs) {
			Collection<DamagedMailbagVO> damagedMails =
				Mailbag.findMailbagDamages(
						carditEnquiryVO.getCompanyCode(),
						mailbagVO.getMailbagId());
			cdtdmgReason =
				damagedMails.iterator().next().getDamageCode().
											concat(mailbagVO.getConsignmentNumber());
			Collection<MailbagVO> resditMailbags =
				mailbagResditMap.get(cdtdmgReason);
			if(resditMailbags == null) {
				resditMailbags = new ArrayList<MailbagVO>();
				mailbagResditMap.put(cdtdmgReason, resditMailbags);
			}
			resditMailbags.add(mailbagVO);
		}
	}



	/**
	 * @author a-1936
	 *
	 * This method is used to group the Resdits for the Shipper Built ULDs
	 * @param carditEnquiryVO
	 * @return
	 * @throws SystemException
	 */
	private Map<String,Collection<ContainerVO>> groupResditsForULD(
			CarditEnquiryVO carditEnquiryVO) throws SystemException{
		log.entering("Resdit", "groupResditsForULD");
		 Collection<ContainerVO>  containerVos =
			carditEnquiryVO.getContainerVos();
		 String resditEvent = carditEnquiryVO.getUnsendResditEvent();
		 Map<String, Collection<ContainerVO>> resditContainerMap =
			new HashMap<String, Collection<ContainerVO>>();

		 if(containerVos!=null && containerVos.size()>0){
			  if(MailConstantsVO.RESDIT_ASSIGNED.equals(resditEvent) ||
						MailConstantsVO.RESDIT_UPLIFTED.equals(resditEvent) ||
						MailConstantsVO.RESDIT_LOADED.equals(resditEvent) ||
						MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(resditEvent)) {
					String flightId = null;
					for(ContainerVO containerVO : containerVos) {
								flightId = new StringBuilder().
										append(containerVO.getCarrierId()).
										append(containerVO.getFlightSequenceNumber()).
										append(containerVO.getFlightNumber())
										.append(containerVO.getConsignmentDocumentNumber())
										.toString();
								Collection<ContainerVO> resditContainers =
									resditContainerMap.get(flightId);
								if(resditContainers == null) {
									resditContainers = new ArrayList<ContainerVO>();
									resditContainerMap.put(flightId, resditContainers);
								}
								resditContainers.add(containerVO);
					}
				} else if(MailConstantsVO.RESDIT_RETURNED.equals(resditEvent)) {
					/*
					 * Note Currently the  Returned Eventy is Not handled..
					 *
					 */
					groupReturnedResditsForULD(resditContainerMap, containerVos,
							carditEnquiryVO);
				} else if(MailConstantsVO.RESDIT_DELIVERED.equals(resditEvent) ||
						MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(resditEvent)) {
					groupHandedoverResditsForULD(resditContainerMap, containerVos);
				}
		 }
		 log.exiting("Resdit", "groupResditsForULD");
		return resditContainerMap;
	}


	 /**
	  * @author a-1936
	  * This method is used to group the Handed Over Resdits For ULD..
	  * @param resditContainerMap
	  * @param containerVos
	  */
	 private  void groupHandedoverResditsForULD( Map<String, Collection<ContainerVO>> resditContainerMap,
			 Collection<ContainerVO> containerVos){
		  log.entering("RESDIT","groupHandedoverResditsForULD");
		  String carditHndovrPartner = null;
			for(ContainerVO contianerVO : containerVos) {
				 carditHndovrPartner = contianerVO.getHandedOverPartner().
													concat(contianerVO.getConsignmentDocumentNumber());
				 Collection<ContainerVO> resditContainers =
					resditContainerMap.get(carditHndovrPartner);
				if(resditContainers == null) {
					 resditContainers = new ArrayList<ContainerVO>();
					 resditContainerMap.put(carditHndovrPartner, resditContainers);
				}
				resditContainers.add(contianerVO);
			}
		 log.exiting("RESDIT","groupHandedoverResditsForULD");
	 }



	 /**
	  * @author a-1936
	  * This method is used to group the Returned Resdits for the ULD.
	  * @param resditContainerMap
	  * @param containerVos
	  * @param carditEnquiryVo
	  */
	 private  void groupReturnedResditsForULD( Map<String, Collection<ContainerVO>> resditContainerMap,
			 Collection<ContainerVO> containerVos,
			 CarditEnquiryVO carditEnquiryVo){
	        //TODO
	 }


	/**
	 * Groups mailbags to diff messages according to each message
	 * copy con of spr_rdt_msg_controller db proc
	 * Feb 26, 2007, a-1739
	 * @param carditEnquiryVO
	 * @return
	 * @throws SystemException
	 */
	private Map<String, Collection<MailbagVO>> groupMailbagResdits(
			CarditEnquiryVO carditEnquiryVO) throws SystemException {
		log.entering("Resdit", "groupMailbagResdits");
		Collection<MailbagVO> mailbagVOs =
			carditEnquiryVO.getMailbagVos();
		String resditEvent = carditEnquiryVO.getUnsendResditEvent();

		Map<String, Collection<MailbagVO>> mailbagResditMap =
			new HashMap<String, Collection<MailbagVO>>();

		if(MailConstantsVO.RESDIT_ASSIGNED.equals(resditEvent) ||
				MailConstantsVO.RESDIT_UPLIFTED.equals(resditEvent) ||
				MailConstantsVO.RESDIT_LOADED.equals(resditEvent) ||
				MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(resditEvent)) {
			String flightId = null;
			for(MailbagVO mailbagVO : mailbagVOs) {
				/*
				 * The Consignment Number is also included in the Flight Key .
				 */
				flightId = new StringBuilder().
								append(mailbagVO.getCarrierId()).
								append(mailbagVO.getFlightSequenceNumber()).
								append(mailbagVO.getFlightNumber()).
								append(mailbagVO.getConsignmentNumber()).
								toString();
						Collection<MailbagVO> resditMailbags =
							mailbagResditMap.get(flightId);
						if(resditMailbags == null) {
							resditMailbags = new ArrayList<MailbagVO>();
							mailbagResditMap.put(flightId, resditMailbags);
						}
						resditMailbags.add(mailbagVO);
			}
		} else if(MailConstantsVO.RESDIT_RETURNED.equals(resditEvent)) {
			 groupReturnedMailbags(mailbagResditMap, mailbagVOs,
					carditEnquiryVO);
		} else if(MailConstantsVO.RESDIT_DELIVERED.equals(resditEvent) ||
				MailConstantsVO.RESDIT_HANDOVER_OFFLINE.equals(resditEvent)) {
			 groupHandedoverMailbags(mailbagResditMap, mailbagVOs);
		}

		log.exiting("Resdit", "groupMailbagResdits");
		return mailbagResditMap;
	}


    /**
     * Added for Mail 4
     * @param ConsignmentInformationVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
	public static Collection<CarditReferenceInformationVO> findCCForSendResdit(ConsignmentInformationVO consgmntInfo)
					throws SystemException{
		Collection<CarditReferenceInformationVO> carditRefInfoVo = null;
		try {
			 carditRefInfoVo= new Resdit().constructDAO().findCCForSendResdit(consgmntInfo);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return carditRefInfoVo;
	}


	/**
	 * Method to retrieve the recepient party for XX resdit .
	 * Depending on the contract with AA or USPS direct business recepient will be AA or USPS.
	 * @param consignmentInformationVOsForXX
	 * @return
	 * @throws SystemException
	 */
	public static HashMap<String, String>  findRecepientForXXResdits (
			Collection<ConsignmentInformationVO> consignmentInformationVOsForXX ) throws SystemException{

		try {
			return constructDAO().findRecepientForXXResdits(consignmentInformationVOsForXX);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}

	}

	/**
	 * Invokes the EVT_RCR proc
	 * A-1739
	 * @param companyCode
	 * @throws SystemException
	 */
	public void invokeResditReceiver(String companyCode)
	throws SystemException {
		log.entering("Resdit", "invokeResditReceiver");
		try {
			constructDAO().invokeResditReceiver(companyCode);
		} catch(PersistenceException exception) {
            throw new SystemException(exception.getMessage(), exception);
        }
		log.exiting("Resdit", "invokeResditReceiver");
	}

	/**
	 * Starts the resditbuilding
	 * Sep 8, 2006, a-1739
	 * @param companyCode
	 * @throws SystemException
	 */
	public Collection<ResditEventVO> findResditEvents(String companyCode)
	throws SystemException {
		log.entering("Resdit", "findResditEvents");
		try {
			return constructDAO().findResditEvents(companyCode);
		} catch(PersistenceException exception) {
            throw new SystemException("No dao impl found", exception);
        }
	}
	   /**
     *
     * @param carditEnquiryFilterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public static HashMap<String, String>  findCarditDetailsForResdit (
    			Collection<ResditEventVO> resditEvents) throws SystemException{
    	HashMap<String, String> carditDetailsMap = new HashMap<String, String>();
    	try {
			Collection<CarditVO> carditVOs =
				new Resdit().constructDAO().findCarditDetailsForResdit(resditEvents);
			if (carditVOs != null && carditVOs.size() > 0){
				for (CarditVO carditVO : carditVOs){
					carditDetailsMap.put(carditVO.getConsignmentNumber(),
											carditVO.getContractReferenceNumber());
				}
			}
		} catch (PersistenceException e) {
			//e.printStackTrace();
		}
    	return carditDetailsMap;
    }
    /**
     *
     * @param resditEvents
     * @return
     * @throws SystemException
     */
    public ResditMessageVO buildResditMessageVO(
			Collection<ResditEventVO> resditEvents) throws SystemException {
		log.entering("Resdit", "buildResditMessageVO");

		String resditVersion="";
		ResditEventVO firstResditEvent = resditEvents.iterator().next();

		log.log(Log.FINE, "------ResditEventVO--------"+resditEvents);
		resditVersion = firstResditEvent.getResditVersion();

		ResditMessageVO resditMessageVO = new ResditMessageVO();
		resditMessageVO.setCompanyCode(firstResditEvent.getCompanyCode());
		resditMessageVO.setStationCode(firstResditEvent.getEventPort());

		/*
		 * all event vos will be grouped for a different PAs
		 * and for a single PA again gorupin is done on version
		 * for version 1.0 MessageType is IFTSTA
		 * for version 1.1 MessageType is IFTSTB
		 */
		if(("1.0").equals(resditVersion)){
		resditMessageVO.setMessageType(ResditMessageVO.MSG_TYP);
		}else if(("1.1").equals(resditVersion)){
		resditMessageVO.setMessageType(ResditMessageVO.MSG_TYPB);
		}else if((ResditMessageVO.MSG_VER_DOM).equals(resditVersion)){
		resditMessageVO.setMessageType(ResditMessageVO.MSG_TYPDOM);
		}

		resditMessageVO.setMessageStandard(ResditMessageVO.EDIFACT);


		populateHeaderDetails(firstResditEvent, resditMessageVO);

		//added for CR QF1200
		String resditFileName="";
		//String resditFileFormat="";
		//TODO FOR BaseM39
		/*try {
			PostalAdministration postalAdministration = PostalAdministration.find(firstResditEvent.getCompanyCode(), firstResditEvent.getPaCode());
			resditFileFormat=postalAdministration.getResditFileFormat();
		} catch (FinderException e) {
			log.log(Log.SEVERE, "Check!!!!!!!!!! No POAMST Configuration For PA"+firstResditEvent.getPaCode());
			e.printStackTrace();
		}*/

		/*if(resditFileFormat!=null && resditFileFormat.length()>0){
			log.log(Log.FINE, "\n\nResdit filename format enabled in PA master an format is "+resditFileFormat);
			resditFileName=FileNameFormatter.format(resditFileFormat, firstResditEvent.getConsignmentNumber(), resditMessageVO.getInterchangeControlReference());
			log.log(Log.FINE, "\n\nFile name generated :"+resditFileName);
		}else{*/
			LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
			//Updated file name for FTP mode.Included milli seconds and status of carditExists for bug ICRD-231376
			resditFileName = new StringBuilder().append("RESDIT_").append(date.toDisplayFormat("yyyyMMddHHmmssSSS")).append(firstResditEvent.getPaCode()).append(firstResditEvent.getCarditExist()).append(firstResditEvent.getEventPort()).toString();
			
			log.log(Log.FINE, "\n\nDefault Resdit file name format selected file name generated : "+resditFileName);
		//}


		resditMessageVO.setResditFileName(resditFileName);

		log.log(Log.FINE, "*******resditMessageVO--------"+resditMessageVO);

		boolean isPartialResditEnabled = false;
//commented for IASCB-27875 begin		
//		//PostalAdministrationVO postalAdministrationVO = null;
//		if(resditMessageVO!=null && resditMessageVO.getRecipientID()!=null &&
//				firstResditEvent!=null && firstResditEvent.getCompanyCode()!=null){
//	    try{
//        	/*collects PartialResdit Flag and MessageEventLocation Flag */
//        	postalAdministrationVO = constructDAO().findPartialResditFlagForPA(
//        		firstResditEvent.getCompanyCode(),resditMessageVO.getRecipientID());
//        }catch(PersistenceException persistenceException){
//        	throw new SystemException(persistenceException.getErrorCode());
//        }
//		}
//	    if(postalAdministrationVO != null) {
//	        resditMessageVO.setLocNeeded(
//	        		postalAdministrationVO.isMsgEventLocationNeeded());
//	    	isPartialResditEnabled = postalAdministrationVO.isPartialResdit();
//	    }
//commented for IASCB-27875 ends			
	    
	    resditMessageVO.setLocNeeded(firstResditEvent.isMsgEventLocationEnabled());//IASCB-27875
	    isPartialResditEnabled= firstResditEvent.isPartialResdit();


        boolean tempPartFlag = isPartialResditEnabled;
        StringBuilder addtnAdreesIDs = new StringBuilder();

		for(ResditEventVO resditEventVO : resditEvents) {
			if(MailConstantsVO.RESDIT_XX.equals(resditEventVO.getResditEventCode())){
				resditEventVO.setActualResditEvent(MailConstantsVO.RESDIT_XX);
				resditEventVO.setResditEventCode(MailConstantsVO.RESDIT_LOADED);
				tempPartFlag = true;
			} else {
				tempPartFlag = isPartialResditEnabled;
			}
			if(resditEventVO.getAdditionlAddressID()!=null&&!("0").equalsIgnoreCase(resditEventVO.getAdditionlAddressID())) {
				addtnAdreesIDs=addtnAdreesIDs.append(resditEventVO.getAdditionlAddressID()+",");
			}

			 log.log(Log.FINE, "\n\n\n Going to Construct Consignment information and all relevant infos for constructing resdit message");
			 log.log(Log.FINE, "\n\n\n For Event Code ::"+resditEventVO.getResditEventCode());
		     log.log(Log.FINE, "\n\n\n Consignment id ::"+resditEventVO.getConsignmentNumber());

			populateConsignmentInformation(resditEventVO, resditMessageVO,
				tempPartFlag,resditVersion);
		}
		if(addtnAdreesIDs!=null&&addtnAdreesIDs.length()!=0) {
			populateAddtnAddressDeapatchDetails(resditMessageVO,addtnAdreesIDs.toString());
		}
		log.log(Log.FINE, "\n\n\n\n ----------------final resditMessageVO from builder--------"+resditMessageVO);
		log.exiting("Resdit", "buildResditMessageVO");
		return resditMessageVO;
	}
	private void populateAddtnAddressDeapatchDetails(
			ResditMessageVO resditMessageVO, String addtnAdreesIDs) {
		List<Long> addtnAddres = Arrays.asList(addtnAdreesIDs.split(StringUtils.COMMA_SEPARATOR)).stream().map(Long::valueOf).collect(Collectors.toList());
		try {
			Collection<MailResditAddressVO> mailResditAddressVO = constructDAO()
					.findMailResditAddtnlAddressDetails(resditMessageVO.getCompanyCode(), addtnAddres);
			Collection<MessageDespatchDetailsVO> despatchDetails = new ArrayList<>();
			Collection<String> airports;
			for (MailResditAddressVO resditAddressVO : mailResditAddressVO) {
				airports = new ArrayList<>();
				MessageDespatchDetailsVO msgDespDeta = new MessageDespatchDetailsVO();
				msgDespDeta.setStation(resditAddressVO.getAirportCode());
				msgDespDeta.setCountry(resditAddressVO.getCountryCode());
				msgDespDeta.setEnvelopeAddress(resditAddressVO.getEnvelopeAddress());
				msgDespDeta.setEnvelopeCode(resditAddressVO.getEnvelopeCode());
				msgDespDeta.setPartyType(resditAddressVO.getParticipantType());
				msgDespDeta.setParty(resditAddressVO.getParticipantName());
				msgDespDeta.setInterfaceSystem(resditAddressVO.getInterfaceSystem());
				msgDespDeta.setAddress(resditAddressVO.getAddress());
				msgDespDeta.setMode(resditAddressVO.getMode());
				msgDespDeta.setVersion(resditAddressVO.getVersion());
				if(resditAddressVO.getAirportCode()!=null&&resditAddressVO.getAirportCode().trim().length()>0){
					airports.add(resditAddressVO.getAirportCode());
					msgDespDeta.setPous(airports);
				}
				despatchDetails.add(msgDespDeta);
			}
			resditMessageVO.setDespatchDetails(despatchDetails);
			resditMessageVO.setTransactionId("FROMSCREEN");
			resditMessageVO.setEnvelopeAddress(null);
		} catch (PersistenceException e) {
			log.log(Log.FINE, e);
		} catch (SystemException e) {
			log.log(Log.FINE, e);
		}
	}
    /**
     *
     * @param resditEventVO
     * @param resditMessageVO
     * @throws SystemException
     */
    private void populateHeaderDetails(ResditEventVO resditEventVO,
			ResditMessageVO resditMessageVO) throws SystemException {
		log.entering("Resdit", "buildHeaderDetails");

		if(!MailConstantsVO.RESDIT_XX.equals(
			resditEventVO.getResditEventCode())){
			CarditVO carditVO = Cardit.findCarditDetailsForResdit(
				resditEventVO.getCompanyCode(), resditEventVO.getConsignmentNumber());
			if(carditVO!=null){
				resditMessageVO.setTestIndicator(String.valueOf(carditVO.getTstIndicator()));
			}
		}

		resditMessageVO.setRecipientID(resditEventVO.getPaCode());	   
        resditMessageVO.setEnvelopeAddress(resditEventVO.getReciever());
		LocalDate preparationDate =
			new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		resditMessageVO.setPreparationDate(
				preparationDate.toDisplayFormat(PREP_DATE_FORMAT));
		resditMessageVO.setPreparationTime(
				preparationDate.toDisplayFormat(PREP_TIME_FORMAT));

		generateSequencesForResdit(resditMessageVO);
		log.exiting("Resdit", "buildHeaderDetails");
	}

    /**
     *
     * @param resditEventVO
     * @param resditMessageVO
     * @param isPartialResditEnabled
     * @param resditVersion
     * @throws SystemException
     */
    private void populateConsignmentInformation(
			ResditEventVO resditEventVO, ResditMessageVO resditMessageVO,
			boolean isPartialResditEnabled,String resditVersion)
	throws SystemException {
		log.entering("Resdit", "buildConsignmentInformation");

		log.log(Log.FINE, "---------------resditEventVO Coming for populateConsignmentInformation----------"+resditEventVO);

		ConsignmentInformationVO consignInfoVO = new ConsignmentInformationVO();
		CarditEnquiryFilterVO carditEnqFilterVO = new CarditEnquiryFilterVO();
		Collection<ConsignmentRoutingVO> consignmentRoutingVOs = null;
		Map<String, String> connectionARLsMap = null;
		ArrayList<String> conArlList = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		PostalAdministrationVO postalAdministrationVO = null;
		StringBuilder pAWBNo = new StringBuilder();
		if(resditEventVO != null ) {

			String eventCode = resditEventVO.getResditEventCode();
			String consignmentNumber = resditEventVO.getConsignmentNumber();
			if(resditEventVO.getShipmentPrefix()!=null &&resditEventVO.getMasterDocumentNumber()!=null){
			String shipmentPrefix = resditEventVO.getShipmentPrefix();
			String masterDocumentNumber = resditEventVO.getMasterDocumentNumber();
			pAWBNo.append(shipmentPrefix).append("-").append(masterDocumentNumber);
			}

			/*
			 * Consturcting Despatch Details (for sending RESDIT) CC List
			 * in case routing info is available
			 */
			carditEnqFilterVO.setCompanyCode(resditEventVO.getCompanyCode());
			carditEnqFilterVO.setPaoCode(resditEventVO.getPaCode());
			carditEnqFilterVO.setConsignmentDocument(consignmentNumber);
			String companyCode=resditEventVO.getCompanyCode();
			try {
				consignmentRoutingVOs = constructDAO().findConsignmentRoutingDetails(carditEnqFilterVO);
			} catch (PersistenceException exp) {
				/* CONSIGNMENT ROUTING DETAILS NOT FOUND*/
				log.log(Log.FINE, "---------------PersistenceException----------Caught-----!!!!!!!!");
				log.log(Log.FINE, "---------------carditEnqFilterVO----------"+carditEnqFilterVO);
			}
			if(consignmentRoutingVOs != null && consignmentRoutingVOs.size() > 0) {
				for(ConsignmentRoutingVO csgRoutingVO : consignmentRoutingVOs) {
					if(connectionARLsMap==null){
						connectionARLsMap = new HashMap<String, String>();
						if(logonAttributes.getOwnAirlineIdentifier() != csgRoutingVO.getFlightCarrierId()){
									connectionARLsMap.put(csgRoutingVO.getFlightCarrierCode(), csgRoutingVO.getFlightCarrierCode());
								}
					}else{
						if((!(connectionARLsMap.containsKey(csgRoutingVO.getFlightCarrierCode())))&&
						   (logonAttributes.getOwnAirlineIdentifier() != csgRoutingVO.getFlightCarrierId())){
							connectionARLsMap.put(csgRoutingVO.getFlightCarrierCode(), csgRoutingVO.getFlightCarrierCode());
						}
					}
				}
			}
			consignInfoVO.setConnectionAirlines(new ArrayList<String>());

			if(connectionARLsMap!=null && connectionARLsMap.size()>0){
				conArlList = new ArrayList<String>();
				for(String ConnectionArl : connectionARLsMap.values()){
					conArlList.add(ConnectionArl);
				}

				consignInfoVO.setConnectionAirlines(conArlList);
			}

			log.log(Log.FINE, "---------------consignInfoVO.getConnectionAirlines----------"+consignInfoVO.getConnectionAirlines());


			//for handling piority of listmailbags on PA master screen

			consignInfoVO.setListMailbagsChecked(isPartialResditEnabled);
			log.log(Log.FINE, "---------------isPartialResditEnabled-----!!!!!!!!"+isPartialResditEnabled);
			log.log(Log.FINE, "---------------consignInfoVO.isListMailbagsChecked-----!!!!!!!!"+consignInfoVO.isListMailbagsChecked());

			/*
			 * Constructing RESDIT Message
			 */
			consignInfoVO.setConsignmentID(consignmentNumber);

			//CONSIGNMENT EVENT INFORMATION
			//SG4-SG5-STS-C555-9011 consignment event
			if(resditEventVO.isM49Resdit() && MailConstantsVO.RESDIT_PENDING.equals(eventCode)){
				eventCode = MailConstantsVO.RESDIT_PENDING_M49;
			}
			consignInfoVO.setConsignmentEvent(eventCode);

			//SG4-SG5-DTM-C507-2379
			consignInfoVO.setDateFormatQualifier(201);

			//SG4-SG5-DTM-C507-2005
			consignInfoVO.setDateQualifier(7);

			//HAND-OVER-INFORMATION
			//SG4-SG5-LOC-C517-3225 place of handover
			consignInfoVO.setTransferLocation(resditEventVO.getEventPort());
			consignInfoVO.setLocation(resditEventVO.getEventPortName());
			boolean flag = false;
			String senderID = resditEventVO.getPaCode();
	     if (pAWBNo.length()> 0){
			postalAdministrationVO = new MailController().findPACode(resditEventVO.getCompanyCode(), senderID);
			if (!postalAdministrationVO.getPostalAdministrationDetailsVOs().isEmpty()) {
				Collection<PostalAdministrationDetailsVO> paDetails = postalAdministrationVO
						.getPostalAdministrationDetailsVOs().get(INVINFO);
				if (!paDetails.isEmpty()) {
					for (PostalAdministrationDetailsVO paDetail : paDetails) {
						if (paDetail.getParCode().equals(PAWBASSCONENAB)
								&& paDetail.getParameterValue().equalsIgnoreCase(PAWBPARMVALYES)) {
							flag = true;
							break;
						}
					}
				}
			}
			if(flag){
			consignInfoVO.setConsignmentPAWBNo(pAWBNo.toString());
			}
			}
			boolean isTransportInfoNeeded = false;

			if(//!MailConstantsVO.RESDIT_RECEIVED.equals(eventCode) &&//Commented as part of ICRD-348102
					!MailConstantsVO.RESDIT_RETURNED.equals(eventCode) &&
					!MailConstantsVO.RESDIT_PENDING.equals(eventCode) &&
					!MailConstantsVO.RESDIT_HANDOVER_RECEIVED.equals(eventCode)) {
				isTransportInfoNeeded = true;
			}

			populateContainerInformation(resditEventVO,
					consignInfoVO, isTransportInfoNeeded);
			populateMailbagInformation(resditEventVO, consignInfoVO,
					isTransportInfoNeeded);


			/*
			 * just to prevent issues in some cases null pointer comes
			 * mainly in offload populate reason codes and construction of DTM from localdate
			 */
			 /*
			 Remove before checkin
			 */

			if(consignInfoVO.getReceptacleInformationVOs()==null && consignInfoVO.getContainerInformationVOs()==null){
					log.log(Log.SEVERE, "\n\n\n\n\t\t\t CHECK----->>>>Consignment with no ReceptacleInformation and ContainerInformation !!!!!!!! So removing from resdit list ");
					log.log(Log.SEVERE, "\n\n\n\n\t\t\t    EVENT CODE :: "+consignInfoVO.getConsignmentEvent());
					log.log(Log.SEVERE, "\n\n\n\n\t\t\t ConsignmentID :: "+consignInfoVO.getConsignmentID());
					log.log(Log.SEVERE, "\n\n\n\n\t\t\t Message Seqnum :: "+consignInfoVO.getMessageSequenceNumber());
					log.log(Log.SEVERE, "\n\n\n\n consignInfoVO skiped :: \n"+consignInfoVO);
					if(consignInfoVO!=null)
					{
					logError(consignInfoVO);
					}
			}else{
				resditMessageVO.setMailboxId(consignInfoVO.getMailboxID());
				computeResditEventDate(consignInfoVO);

					if("N".equals(resditEventVO.getCarditExist())){
						consignInfoVO.setConsignmentResponse(DOCUMENT_NOT_RECIEVED);
					 }else{
						// if  (isPartialResdit(resditEventVO, consignInfoVO)) {//commented as part of  IASCB-42954 starts
							 consignInfoVO.setConsignmentResponse(CONSIGNMENT_PARTIALLY_ACCEPTED);
						 /*}
						else {
							if(MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.SYSPAR_USPS_ENHMNT))&&
									MailConstantsVO.USPOST.equals(resditEventVO.getPaCode())){
								consignInfoVO.setConsignmentResponse(CONSIGNMENT_TOTALLY_ACCEPTED_USPOST);
							}else{
								consignInfoVO.setConsignmentResponse(CONSIGNMENT_TOTALLY_ACCEPTED);
							}
						}*///commented as part of  IASCB-42954 ends
				  }
				//for second LOC segment after TDT BUG 53006
				populateLocationQualifier(companyCode,consignInfoVO);


				if(	!eventCode.equals(MailConstantsVO.RESDIT_PENDING) &&
						!eventCode.equals(MailConstantsVO.RESDIT_ASSIGNED)) {
					populateHandedOverInformation(consignInfoVO,
							resditMessageVO);
				}

				if(eventCode.equals(MailConstantsVO.RESDIT_PENDING) ||
						eventCode.equals(MailConstantsVO.RESDIT_RETURNED)) {
					populateReasonCodes(consignInfoVO, resditEventVO.getCompanyCode(),
							resditEventVO.getEventPort());
				}

				Collection<ConsignmentInformationVO> consignmentVOs =
					resditMessageVO.getConsignmentInformationVOs();
				if(consignmentVOs == null) {
					consignmentVOs = new ArrayList<ConsignmentInformationVO>();
					resditMessageVO.setConsignmentInformationVOs(consignmentVOs);
				}
			log.log(Log.FINE, "\n\n\n\n consignInfoVO constructed and included for resdit :: \n "+consignInfoVO);
				consignmentVOs.add(consignInfoVO);
		  }
		}
		log.exiting("Resdit", "buildConsignmentInformation");

	}
    /**
     *
     * @param consignInfoVO
     */
    private void logError(ConsignmentInformationVO consignInfoVO){
		try{
		String fileName = System.getProperty("mailtracking.error.file","/data1/logs/icoprddomain/app")+File.separator+"ErrorLog.txt";

		File file = new File(fileName);
		LocalDate lc= new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		Writer output = new BufferedWriter(new FileWriter(file,true));

		try{
			output.write("@ "+ lc);
			output.write("\n ErrorVO :> "+ consignInfoVO.toString() );
			}catch (Exception e){
				//ignore
			}finally {
			 output.close();
			}
		}catch(Exception e) {
			//ignore
		}
	}

    /**
     *
     * @param companyCode
     * @param consignInfoVO
     */
    private void  populateLocationQualifier(String companyCode,ConsignmentInformationVO consignInfoVO){

    	String destinationOE="";
    	String destinationCity="";
    	String ooe ="";
    	if(consignInfoVO.getReceptacleInformationVOs()!=null&&consignInfoVO.getReceptacleInformationVOs().size()>0){
    		destinationOE=((ArrayList<ReceptacleInformationVO>)consignInfoVO.getReceptacleInformationVOs()).get(0).getReceptacleID();
    		ooe = ((ArrayList<ReceptacleInformationVO>)consignInfoVO.getReceptacleInformationVOs()).get(0).getOriginExgOffice();
    		consignInfoVO.setOriginExgOffice(ooe);
    	}
    }

    /**
     *
     * @param resditEventVO
     * @param consignInfoVO
     * @return
     * @throws SystemException
     */
    private boolean isPartialResdit(
			ResditEventVO resditEventVO, ConsignmentInformationVO consignInfoVO)
	throws SystemException {
		log.entering("Resdit", "isPartialResdit");


		Collection<ReceptacleInformationVO> receptacles =
			consignInfoVO.getReceptacleInformationVOs();
		boolean isPartialResditForMailbag =
				isPartialResditForMailbag(resditEventVO, receptacles);

		Collection<ContainerInformationVO> containerInfoVOs =
			consignInfoVO.getContainerInformationVOs();
		boolean isPartialResditForULD =
				isPartialResditForULD(resditEventVO, containerInfoVOs);
		log.exiting("Resdit", "isPartialResdit");
		return isPartialResditForMailbag || isPartialResditForULD;
	}

    /**
     *
     * @param resditEventVO
     * @param receptacles
     * @return
     * @throws SystemException
     */
    private boolean isPartialResditForMailbag(ResditEventVO resditEventVO,
			Collection<ReceptacleInformationVO> receptacles)
		throws SystemException {
		log.entering("Resdit", "isPartialResditForMailbag");
		int resditReceptacleCount = 0;
		if(receptacles != null) {
			resditReceptacleCount = receptacles.size();
		}

		int carditReceptacleCount = Cardit.findCarditReceptacleCount(
				resditEventVO.getCompanyCode(),
				resditEventVO.getConsignmentNumber());
		log.exiting("Resdit", "isPartialResditForMailbag");
		return resditReceptacleCount != carditReceptacleCount;
	}

    /**
     *
     * @param resditEventVO
     * @param containerInfoVOs
     * @return
     * @throws SystemException
     */
    private boolean isPartialResditForULD(ResditEventVO resditEventVO,
			Collection<ContainerInformationVO> containerInfoVOs)
	throws SystemException {
		log.entering("Resdit", "isPartialResditForULD");
		int resditCountForULD = 0;
		if(containerInfoVOs != null) {
			resditCountForULD = containerInfoVOs.size();
		}

		int carditCountForULD = Cardit.findCarditContainerCount(
				resditEventVO.getCompanyCode(),
				resditEventVO.getConsignmentNumber());
		log.exiting("Resdit", "isPartialResditForULD");
		return (resditCountForULD != carditCountForULD);
	}

    /**
     *
     * @param resditEventVO
     * @param consignInfoVO
     * @param isTransportInfoNeeded
     * @throws SystemException
     */
    private void populateMailbagInformation(ResditEventVO resditEventVO,
			ConsignmentInformationVO consignInfoVO,
			boolean isTransportInfoNeeded) throws SystemException {
		log.entering("Resdit", "populateMailbagInformation");
		Collection<ReceptacleInformationVO> receptacleInfoVOs = null;
		Collection<GoodsItemDetailsVO> groupReceptaclesUnderGid = null;

		log.log(Log.FINE, "CHECK ----resditEventVO.getCarditExist()=======>>>"+resditEventVO.getCarditExist());

		if(MailConstantsVO.RESDIT_XX.equals(resditEventVO.getActualResditEvent())){
			//call for fetching mailbag info for XX resdit
			receptacleInfoVOs = MailResdit.findMailbagDetailsForXXResdit(resditEventVO);

		}else{
			//it can be either resdit with cardit or resdit without cardit ie v1.1 for XX resditEventVO.getCarditExist() will be null
			if("N".equals(resditEventVO.getCarditExist())){
				//call for fetching mailbag info for resdit without cardit
				receptacleInfoVOs = MailResdit.findMailbagDetailsForXXResdit(resditEventVO);

			}else if("Y".equals(resditEventVO.getCarditExist())){

				//call for fetching mailbag info for resdit with cardit
				receptacleInfoVOs = MailResdit.findMailbagDetailsForResdit(resditEventVO);

				/*
				 * Logic Note ::
				 * In case if loose mailbags come with a belly cart-id for assigned event {74}:
				 * group all ReceptacleInformationVOs with equipmentQualifier as TE and with same  JourneyID
				 */

				Integer i=0;
				Map<Integer,String> bellyCartIDs = null;
				ArrayList<ReceptacleInformationVO> pciReceptaclesUnderTE = new ArrayList<ReceptacleInformationVO>();
				ArrayList<ReceptacleInformationVO> pciReceptaclesToRemove = new ArrayList<ReceptacleInformationVO>();
				  GMTDate receptacleEventDate = null;
					if(resditEventVO.getResditEventCode()!=null && "74".equals(resditEventVO.getResditEventCode())){

						    if(receptacleInfoVOs != null && receptacleInfoVOs.size() > 0){

									 receptacleEventDate =	getLatestReceptacleEventDate(receptacleInfoVOs);

							for(ReceptacleInformationVO receptacleInfo : receptacleInfoVOs) {
									if(receptacleInfo.getEquipmentQualifier()!=null && "TE".equals(receptacleInfo.getEquipmentQualifier())){
										pciReceptaclesToRemove.add(receptacleInfo);
										if(bellyCartIDs==null){
											bellyCartIDs = new HashMap<Integer, String>();
											bellyCartIDs.put(i,receptacleInfo.getJourneyID());
											i=1+1;
										}else{
											if(!(bellyCartIDs.containsValue(receptacleInfo.getJourneyID()))){
												bellyCartIDs.put(i,receptacleInfo.getJourneyID());
												i=1+1;
											}
										}
									}
							 }
						    }

								ReceptacleInformationVO receptacleUnderTE = null;
								if(bellyCartIDs!=null && bellyCartIDs.size()>0){
									int siz = bellyCartIDs.size();
									for(int j = 0;j< siz;j++){
										receptacleUnderTE = new ReceptacleInformationVO();
										receptacleUnderTE.setEventDate(receptacleEventDate);
										receptacleUnderTE.setReceptacleID(bellyCartIDs.get(j));
										pciReceptaclesUnderTE.add(receptacleUnderTE);
									}
								}
								if(pciReceptaclesToRemove!=null && pciReceptaclesToRemove.size()>0){
									receptacleInfoVOs.removeAll(pciReceptaclesToRemove);
								}
								if(pciReceptaclesUnderTE!=null && pciReceptaclesUnderTE.size()>0){
									receptacleInfoVOs.addAll(pciReceptaclesUnderTE);
								}
					}else{
						if(receptacleInfoVOs != null && receptacleInfoVOs.size() > 0){
							for(ReceptacleInformationVO receptacleInfo : receptacleInfoVOs) {
								if(receptacleInfo.getEquipmentQualifier()!=null && "TE".equals(receptacleInfo.getEquipmentQualifier())){
									receptacleInfo.setJourneyID(null);

								}

						    }
						}
					}
			}
		}


		if(receptacleInfoVOs != null && receptacleInfoVOs.size() > 0){
			for(ReceptacleInformationVO receptacleInfo : receptacleInfoVOs) {


					consignInfoVO.setPartyIdentifier(receptacleInfo.getPartyIdentifier());
					consignInfoVO.setMailboxID(receptacleInfo.getMailboxID());
					consignInfoVO.setMailBagDestination(receptacleInfo.getMailBagDestination());

		    }
		}
			//just for safety : if receptacle info was constructed form container details because of business
			if(consignInfoVO.getReceptacleInformationVOs()!=null && consignInfoVO.getReceptacleInformationVOs().size()>0){
				if(receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {
				receptacleInfoVOs.addAll(consignInfoVO.getReceptacleInformationVOs());
				}else{
					receptacleInfoVOs = new ArrayList<ReceptacleInformationVO>();
					receptacleInfoVOs.addAll(consignInfoVO.getReceptacleInformationVOs());
				}
			}

			log.log(Log.FINE, "\n\n Check !!! ReceptacleInfoVOs before grouping=======>>>"+receptacleInfoVOs);
			log.log(Log.FINE, "\n\n Check !!! GidVos before grouping under GID=======>>>"+consignInfoVO.getGIDVOs());

			if(receptacleInfoVOs != null && receptacleInfoVOs.size() > 0) {

			groupReceptaclesUnderGid =	groupReceptaclesUnderGid((ArrayList<ReceptacleInformationVO>)receptacleInfoVOs);
			if(groupReceptaclesUnderGid!=null && groupReceptaclesUnderGid.size()>0){
				consignInfoVO.setGIDVOs(groupReceptaclesUnderGid);
				}

			consignInfoVO.setReceptacleInformationVOs(receptacleInfoVOs);

			log.log(Log.FINE, "\n\n Check !!! ReceptacleInfoVOs After grouping=======>>>"+consignInfoVO.getReceptacleInformationVOs());
			log.log(Log.FINE, "\n\n Check !!! GidVos After grouping under GID=======>>>"+consignInfoVO.getGIDVOs());

			if(isTransportInfoNeeded) {
				Collection<TransportInformationVO> consignTransportInfoVOs =
					consignInfoVO.getTransportInformationVOs();
				if(consignTransportInfoVOs == null) {
						consignTransportInfoVOs =
							new ArrayList<TransportInformationVO>();
					consignInfoVO.setTransportInformationVOs(
							consignTransportInfoVOs);
				}
				if(consignTransportInfoVOs.size() == 0) {
					Collection<TransportInformationVO> transportInfoVOs =
						populateTransportInformationFromMailbag(resditEventVO);
					if(transportInfoVOs != null) {
						for(TransportInformationVO transportVO : transportInfoVOs) {
							if(MailConstantsVO.RESDIT_UPLIFTED.equals(
								consignInfoVO.getConsignmentEvent())) {
								if(transportVO.getDepartureTime()!=null){
								consignInfoVO.setEventDate(
									transportVO.getDepartureTime().toGMTDate());
								}else{
									/*Added as part of ICRD-181309
									*Added to handle case when flight segment deleted after ATD is captured
									*Departure/Arrival Time will be used during DTM segment construction
									*For Resdit 24 DTM+189,DTM+232 to be ATD/STD as Arrival not captured at
									*event time
									*/
									consignInfoVO.setEventDate(
											resditEventVO.getEventDate().toGMTDate());
									transportVO.setDepartureTime(resditEventVO.getEventDate());
									transportVO.setArrivalTime(resditEventVO.getEventDate());
								}
							}
						}
					}
					consignTransportInfoVOs.addAll(transportInfoVOs);
				}
			}
		}
		log.exiting("Resdit", "populateMailbagInformation");

	}

    /**
     *
     * @param resditEventVO
     * @return
     * @throws SystemException
     */
    private Collection<TransportInformationVO> populateTransportInformationFromMailbag(
			ResditEventVO resditEventVO) throws SystemException {
		log.entering("Resdit", "populateTransportInformationFromMailbag");
		Collection<TransportInformationVO> transportInfoVOs = new ArrayList<TransportInformationVO>();

		Collection<TransportInformationVO> transportInfoVOsFromCardit = null;
		CarditEnquiryFilterVO carditEnqFilterVO = new CarditEnquiryFilterVO();

		// transportInfoVOs =	MailResdit.findTransportDetailsForMailbag(resditEventVO);// commented as part of IASCB-42954
		transportInfoVOs= constructTransportDetailsForMailbag(resditEventVO,transportInfoVOs);
			if(transportInfoVOs != null && transportInfoVOs.size() > 0) {
				if(resditEventVO.getFlightNumber()!=null && resditEventVO.getFlightNumber().trim().length()>0){
					updateTransportInformationDetails(
							resditEventVO.getCompanyCode(), transportInfoVOs.iterator().next());
				}
			}

		if(MailConstantsVO.RESDIT_UPLIFTED.equals(resditEventVO.getResditEventCode())){
			if(MailConstantsVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.SYSPAR_USPS_ENHMNT))&&
			  MailConstantsVO.USPOST.equals(resditEventVO.getPaCode())){
			carditEnqFilterVO.setCompanyCode(resditEventVO.getCompanyCode());
			carditEnqFilterVO.setPaoCode(resditEventVO.getPaCode());
			carditEnqFilterVO.setConsignmentDocument(resditEventVO.getConsignmentNumber());
			String airport=resditEventVO.getEventPort();
			try {
				transportInfoVOsFromCardit = constructDAO().findRoutingDetailsFromCardit(carditEnqFilterVO,airport);
			} catch (PersistenceException exp) {
				/* CONSIGNMENT ROUTING DETAILS NOT FOUND*/
				log.log(Log.FINE, "---------------PersistenceException----------Caught-----!!!!!!!!");
				log.log(Log.FINE, "---------------carditEnqFilterVO----------"+carditEnqFilterVO);
			}


				if(transportInfoVOsFromCardit!=null && transportInfoVOsFromCardit.size()>0){
					for(TransportInformationVO tranVOCardit:transportInfoVOsFromCardit){
						if(!resditEventVO.getEventPort().equals(tranVOCardit.getDeparturePlace())){
							// only routings with orgin other than resdit event port need to be added.
							transportInfoVOs.add(tranVOCardit);
						}

					}
				}
		  }
		}




		log.exiting("Resdit", "populateTransportInformationFromMailbag");
		return transportInfoVOs;
	}

    /**
     *
     * @param receptacleInfoVOs
     * @return
     */
    private Collection<GoodsItemDetailsVO> groupReceptaclesUnderGid(ArrayList<ReceptacleInformationVO> receptacleInfoVOs){
		log.entering("Resdit", "groupReceptaclesUnderGid");

		/*
	     * Logic for grouping 10 recepacles in 1 PCI and 99 PCI in 1 GID
	    */
		Collection<GoodsItemDetailsVO> groupReceptaclesUnderGids = new ArrayList<GoodsItemDetailsVO>();
		log.entering("ResditMessageEncoder", "groupReceptaclesForPCI");
		GoodsItemDetailsVO goodsItemDetailsVO = new GoodsItemDetailsVO();
		ArrayList<ArrayList<ReceptacleInformationVO>> pciReceptacles = new ArrayList<ArrayList<ReceptacleInformationVO>>();

		goodsItemDetailsVO.setPciReceptacles(pciReceptacles);
		ArrayList<ReceptacleInformationVO> tenReceptacles = new ArrayList<ReceptacleInformationVO>();
		int idx = 0;
		boolean isDerived = false;
		for(ReceptacleInformationVO receptacleInfo : receptacleInfoVOs) {

			if(receptacleInfo.getJourneyID() != null){
				isDerived = true;
				}
				tenReceptacles.add(receptacleInfo);

				if(tenReceptacles.size() == 10) {

//					Commented as part of BUg 51730
					pciReceptacles.add(tenReceptacles);
					goodsItemDetailsVO.setPciReceptacles(pciReceptacles);
					tenReceptacles = new ArrayList<ReceptacleInformationVO>();
				}

				if(pciReceptacles.size() == 99) {

					goodsItemDetailsVO.setPciReceptacles(pciReceptacles);
					if(isDerived){
						goodsItemDetailsVO.setRecepOrContainerScanned(0);
					}else{
						goodsItemDetailsVO.setRecepOrContainerScanned(1);
					}
					isDerived = false;

					groupReceptaclesUnderGids.add(goodsItemDetailsVO);

					pciReceptacles = new ArrayList<ArrayList<ReceptacleInformationVO>>();
					goodsItemDetailsVO = new GoodsItemDetailsVO();
				}

				idx++;
		}

		if((tenReceptacles.size() > 0 && tenReceptacles.size() < 10)){

		    pciReceptacles.add(tenReceptacles);
		    //Commneted as part of BUg 51730
//		    goodsItemDetailsVO = new GoodsItemDetailsVO();
		    goodsItemDetailsVO.setPciReceptacles(pciReceptacles);
		    if(isDerived){
				goodsItemDetailsVO.setRecepOrContainerScanned(0);
			}else{
				goodsItemDetailsVO.setRecepOrContainerScanned(1);
			}

		    // Commented for bug 51730
//		    groupReceptaclesUnderGids.add(goodsItemDetailsVO);

		}

		groupReceptaclesUnderGids.add(goodsItemDetailsVO);
		log.exiting("Resdit", "groupReceptaclesUnderGid");
			return groupReceptaclesUnderGids;

		}

    /**
     *
     * @param resditEventVO
     * @param consignInfoVO
     * @param isTransportInfoNeeded
     * @throws SystemException
     */
    private void populateContainerInformation(
			ResditEventVO resditEventVO, ConsignmentInformationVO consignInfoVO,
			boolean isTransportInfoNeeded)
		throws SystemException {
		log.entering("Resdit", "populateContainerInformation");

		Collection<ContainerInformationVO> containerInformationVOs = null;

		if("N".equals(resditEventVO.getCarditExist())){
			//call for fetching ULD details for resdit with out cardit
			containerInformationVOs = UldResdit.findULDDetailsForResditWithoutCardit(resditEventVO);

			/*
			 * Logic Note :: for SB ULD With out cardit
			 * 3 cases can come
			 * 1 : SB ULD alone is captured then journeyid field will be null so the container info is to be shown in EQD section
			 * 2 : SB + J43 id or
			 * 3 : SB + mailtag    in that case journeyid field will not be null so the container info MUST NOT be shown in EQD section
			 * 	   instead the journeyid is shown in PCI section ie receptacle info
			 */
			ReceptacleInformationVO receptacleInformationVO = null;
			Collection<ContainerInformationVO> containerInformationToRemove = new ArrayList<ContainerInformationVO>();
			Collection<ReceptacleInformationVO> newReceptacleInformationVOs =  new ArrayList<ReceptacleInformationVO>();

			if(containerInformationVOs!=null && containerInformationVOs.size()>0){
				GMTDate containerEventDate = null;
				containerEventDate = getLatestContainerEventDate(containerInformationVOs);

				for(ContainerInformationVO containerInformationVO : containerInformationVOs){
					if(containerInformationVO.getJourneyID()!=null){
						containerInformationToRemove.add(containerInformationVO);
						receptacleInformationVO = new ReceptacleInformationVO();
						receptacleInformationVO.setEventDate(containerEventDate);
							receptacleInformationVO.setPartnerId(containerInformationVO.getPartnerId());
						receptacleInformationVO.setReceptacleID(containerInformationVO.getJourneyID());
						newReceptacleInformationVOs.add(receptacleInformationVO);
					}
				}
				if(containerInformationToRemove!=null && containerInformationToRemove.size()>0){
					containerInformationVOs.removeAll(containerInformationToRemove);
				}
				if(newReceptacleInformationVOs!=null && newReceptacleInformationVOs.size()>0){
					if(consignInfoVO.getReceptacleInformationVOs()!=null && consignInfoVO.getReceptacleInformationVOs().size()>0){
						consignInfoVO.getReceptacleInformationVOs().addAll(newReceptacleInformationVOs);
					}else{
						consignInfoVO.setReceptacleInformationVOs(newReceptacleInformationVOs);
					}
				}

			}

		} else{
			//call for fetching ULD details for resdit with cardit
		    containerInformationVOs = UldResdit.findULDDetailsForResdit(resditEventVO);
		}
		if(containerInformationVOs != null && containerInformationVOs.size() > 0) {
			consignInfoVO.setContainerInformationVOs(containerInformationVOs);

			if(isTransportInfoNeeded) {
				Collection<TransportInformationVO> consignTransportInfoVOs =
					consignInfoVO.getTransportInformationVOs();
				if(consignTransportInfoVOs == null) {
					consignTransportInfoVOs =
						new ArrayList<TransportInformationVO>();
					consignInfoVO.setTransportInformationVOs(
							consignTransportInfoVOs);
				}
				if(consignTransportInfoVOs.size() == 0) {
					Collection<TransportInformationVO> transportInfoVOs =
						populateTransportInformationFromULDs(resditEventVO);
					if(transportInfoVOs != null) {
						for(TransportInformationVO transportVO : transportInfoVOs) {
							if(MailConstantsVO.RESDIT_UPLIFTED.equals(
								consignInfoVO.getConsignmentEvent())) {
								consignInfoVO.setEventDate(
									transportVO.getDepartureTime().toGMTDate());
							}
						}
					}
					consignTransportInfoVOs.addAll(transportInfoVOs);
				}
			}
		}
		log.exiting("Resdit", "populateContainerInformation");
	}

    /**
     *
     * @param resditEventVO
     * @return
     * @throws SystemException
     */
    private Collection<TransportInformationVO> populateTransportInformationFromULDs(
			ResditEventVO resditEventVO) throws SystemException {
		log.entering("Resdit", "populateTransportInformationFromULDs");
		Collection<TransportInformationVO>  containerTransports =
			UldResdit.findTransportDetailsForULD(resditEventVO);
		if(containerTransports != null && containerTransports.size() > 0) {
			for(TransportInformationVO transportInformationVO : containerTransports) {
				if(resditEventVO.getEventPort()!=null){
				transportInformationVO.setDepartureTime(new LocalDate(resditEventVO.getEventPort(),Location.ARP,true));
				} 
				updateTransportInformationDetails(resditEventVO.getCompanyCode(),
						transportInformationVO);
			}
		}
		log.exiting("Resdit", "populateTransportInformationFromULDs");
		return containerTransports;
	}

	private Collection<TransportInformationVO> constructTransportDetailsForMailbag(ResditEventVO resditEventVO,Collection<TransportInformationVO> transportInfoVOs ) {
		log.entering("Resdit", "constructTransportDetailsForMailbag");
		TransportInformationVO transportInfoVO=new TransportInformationVO();
		transportInfoVO.setDeparturePlace(resditEventVO.getEventPort());
		transportInfoVO.setDepartureLocationQualifier(
				ResditMessageVO.PLACE_OF_DEPARTURE);
		String conveyRefNo = new StringBuilder().
				append(resditEventVO.getCarrierCode()!=null?resditEventVO.getCarrierCode():"").
				append(FLIGHTID_DELIM).
				append(resditEventVO.getCarrierId()).
				append(FLIGHTID_DELIM).
				append(resditEventVO.getFlightNumber()!=null?resditEventVO.getFlightNumber():"").
				append(FLIGHTID_DELIM).
				append(resditEventVO.getFlightSequenceNumber()).
				append(FLIGHTID_DELIM).
				append(resditEventVO.getSegmentSerialNumber()).
				toString();
		transportInfoVO.setConveyanceReference(conveyRefNo);
		transportInfoVO.setCarrierName(resditEventVO.getPartyName());
		transportInfoVOs.add(transportInfoVO);
		log.exiting("Resdit", "constructTransportDetailsForMailbag");
		return transportInfoVOs;
	}

}
