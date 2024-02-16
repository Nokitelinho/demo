/*
 * OffloadMailCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OffloadSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class OffloadMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "offload_success";
   private static final String TARGET_FAILURE = "offload_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";	
   private static final String OFFLOAD_SCREEN_ID = "mailtracking.defaults.offload";	
   
   private static final String CONST_RETURN = "RTN";
   private static final String CONST_ACCEPT_FLG = "ACP";
   private static final String CONST_ARRIVED_FLG = "ARR";
   private static final String CONST_DELIVERED_FLG = "DLV";
   private static final String OUTBOUND = "O";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering("OffloadMailCommand", "execute");
		MailBagEnquiryForm mailBagEnquiryForm = 
			(MailBagEnquiryForm) invocationContext.screenModel;
		MailBagEnquirySession mailBagEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID);
		OffloadSession offloadSession = getScreenSession(MODULE_NAME, OFFLOAD_SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
			new MailTrackingDefaultsDelegate();
		Collection<MailbagVO> mailbagVOs = mailBagEnquirySession.getMailbagVOs();
		Page<MailbagVO> selectedMailbagVOs = new Page<MailbagVO>(
				new ArrayList<MailbagVO>(), 0, 0, 0, 0, 0, false);
		// Getting the selected MailBags
		String[] selectedRows = mailBagEnquiryForm.getSubCheck();
		int size = selectedRows.length;
		int row = 0;
		
		for (MailbagVO mailbagvo : mailbagVOs) {
			
			for (int j = 0; j < size; j++) {
				
				if (row == Integer.parseInt(selectedRows[j])) {
					selectedMailbagVOs.add(mailbagvo);
				}
			}
			row++;
		}

		// validation to check whether any of the selected mailbags are
		// returned/arrived/delivered ones
		for (MailbagVO selectedvo : selectedMailbagVOs) {

			if (MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(selectedvo.getLatestStatus())) {
				invocationContext.addError(new ErrorVO(
						"mailtracking.defaults.err.capturedbutnotaccepted"));
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			
			if (CONST_RETURN.equals(selectedvo.getLatestStatus())) {
				invocationContext.addError(new ErrorVO(
				"mailtracking.defaults.mailbagenquiry.msg.err.returnedMailbagsCannotOffload"));
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			
			if (CONST_ARRIVED_FLG.equals(selectedvo.getLatestStatus())) {
				invocationContext.addError(new ErrorVO(
				"mailtracking.defaults.mailbagenquiry.msg.err.arrivedMailbagsCannotOffload"));
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			
			if (CONST_DELIVERED_FLG.equals(selectedvo.getLatestStatus())) {
				invocationContext.addError(new ErrorVO(
				"mailtracking.defaults.mailbagenquiry.msg.err.deliveredMailbagsCannotOffload"));
				invocationContext.target = TARGET_FAILURE;
				return;
			}			
		}
		/*
		 * VALIDATE PORT
		 */
		int errorPort = 0;
		String contPort = "";
		
		for (MailbagVO selectedvo : selectedMailbagVOs) {
			
			if (!logonAttributes.getAirportCode().equals(selectedvo.getScannedPort())) {
				errorPort = 1;
				
				if ("".equals(contPort)) {
					contPort = selectedvo.getMailbagId();
				} else {
					contPort = new StringBuilder(contPort).append(",").append(
							selectedvo.getMailbagId()).toString();
				}
			}
		}
		
		if (errorPort == 1) {
			invocationContext.addError(new ErrorVO(
					"mailtracking.defaults.searchcontainer.differentport", new Object[] {contPort}));
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		// Validation to check whether any mailbags are destination assigned
		for (MailbagVO selectedvo : selectedMailbagVOs) {
			
			if ("-1".equals(selectedvo.getFlightNumber())) {
				invocationContext.addError(new ErrorVO(
						"mailtracking.defaults.mailbagenquiry.msg.err.notFlightAssigned"));
				invocationContext.target = TARGET_FAILURE;
				return;
			}
		}

		String fltNo = "";
		String carrierCode = "";
		String scannedPort = "";
		long fltseqNo = 0;
		int carrierid = 0;
		LocalDate fltDate = null;
		
		for (MailbagVO selectedvo : selectedMailbagVOs) {
			fltNo = (selectedvo.getFlightNumber() != null) ? selectedvo.getFlightNumber() : "";
			fltseqNo = selectedvo.getFlightSequenceNumber();
			carrierid = selectedvo.getCarrierId();
			carrierCode = selectedvo.getCarrierCode();
			scannedPort = selectedvo.getScannedPort();
			fltDate = selectedvo.getFlightDate();
			break;
		}
		// Validating whether asigned to different flight
		for (MailbagVO selectedvo : selectedMailbagVOs) {
			
			if (!carrierCode.equals(selectedvo.getCarrierCode()) || !fltNo.equals(
					selectedvo.getFlightNumber()) || fltseqNo != 
						selectedvo.getFlightSequenceNumber()) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.mailbagenquiry.msg.err.notassignedToSameFlight");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
		}
		/*
		 * check whether the mailbag is Accepted to flight and scanned airport
		 * is current airport - else error
		 */
		for (MailbagVO selectedvo : selectedMailbagVOs) {
			
			if (!logonAttributes.getAirportCode().equals(selectedvo.getScannedPort()) && 
					!CONST_ACCEPT_FLG.equals(selectedvo.getLatestStatus())) {
				ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.mailbagenquiry.msg.err.notAcceptedOrScandPortDifferent");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
		}
		// Validating Flight to obtain the LegSerialNumber
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(fltNo);
		flightFilterVO.setStation(scannedPort);
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setStringFlightDate(String.valueOf(fltDate));
		flightFilterVO.setFlightDate(fltDate);
		flightFilterVO.setCarrierCode(carrierCode);
		flightFilterVO.setFlightCarrierId(carrierid);
		Collection<FlightValidationVO> flightValidationVOs = null;
		FlightValidationVO flightValidationVO = null;

		try {
			log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
			flightValidationVOs = mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		/* Added by A-5160 to find the flights from mail table when listed from a different 
		 * airport due to route change */
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			
			try {
				log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
				flightValidationVOs = mailTrackingDefaultsDelegate.validateMailFlight(
						flightFilterVO);
				
				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
					
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						flightValidVO.setFlightStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
						flightValidVO.setLegStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
					}
				}
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		
		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
			log.log(Log.FINE, "SIZE ------------> ", flightValidationVOs.size());
			
			if (flightValidationVOs.size() == 1) {
				
				for (FlightValidationVO validationVO : flightValidationVOs) {
					flightValidationVO = validationVO;
					break;
				}
			} else if (flightValidationVOs.size() > 1) {
				
				for (FlightValidationVO validationVO : flightValidationVOs) {
					
					if (validationVO.getFlightSequenceNumber() == fltseqNo) {
						flightValidationVO = validationVO;
						break;
					}
				}
			}
		} else {
			invocationContext.addError(new ErrorVO(
				"mailtracking.defaults.mailbagenquiry.msg.err.arrivedMailbagsCannotOffload"));
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		log.log(Log.FINE, "flightValidationVO ------------> ", flightValidationVO);
		// Validating Flight Closure
		boolean isFlightClosed = false;
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
		operationalFlightVO.setDirection(OUTBOUND);
		operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
		operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		
		if (FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())) {
			
			for (MailbagVO selectedMailbagVO : selectedMailbagVOs) {
				operationalFlightVO.setLegSerialNumber(selectedMailbagVO.getLegSerialNumber());
			}
		} else{
			operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		}
		operationalFlightVO.setPol(logonAttributes.getAirportCode());
		
		try {
			isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(
					operationalFlightVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		log.log(Log.FINE, "MailbagEnquiry>OffloadMailCommand->isFlightClosed->", isFlightClosed);
		
		if (!isFlightClosed) {
			Object[] obj = {new StringBuilder(carrierCode).append("").append(fltNo).toString(), 
					fltDate.toDisplayDateOnlyFormat() };
			invocationContext.addError(new ErrorVO("mailtracking.defaults.err.flightclosed", 
					obj));
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		// Setting LegSerialNumber to the selected MailbagVOs
		for (MailbagVO selectedvo : selectedMailbagVOs) {
			selectedvo.setLastUpdateTime(null);
		}
		// Creating the OffloadVO for offloading
		OffloadVO offloadVO = new OffloadVO();
		offloadVO.setCompanyCode(logonAttributes.getCompanyCode());
		offloadVO.setFlightNumber(fltNo);
		offloadVO.setFlightSequenceNumber(fltseqNo);
		offloadVO.setFlightDate(fltDate);
		offloadVO.setCarrierCode(carrierCode);
		offloadVO.setCarrierId(carrierid);
		offloadVO.setOffloadMailbags(selectedMailbagVOs);
		offloadVO.setPol(logonAttributes.getAirportCode());
		offloadVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		offloadSession.setOffloadVO(offloadVO);
		offloadSession.setFlightValidationVO(flightValidationVO);
		invocationContext.target = TARGET_SUCCESS;
		log.exiting("OffloadMailCommand", "execute");
	}

}