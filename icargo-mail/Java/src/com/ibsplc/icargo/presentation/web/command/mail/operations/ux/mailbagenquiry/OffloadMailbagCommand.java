/*
 * OffloadMailbagCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Revision History Revision Date Author Description 0.1 Jun 07, 2018 A-2257
 * First draft
 */


/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry.OffloadMailbagCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.2		:	A-8164	:	24-Mar-2019		:	Changed for IASCB-2596
 */
public class OffloadMailbagCommand extends AbstractCommand {

	private static final String CONST_DELIVERED_FLG = "DLV";
	private static final String CONST_ARRIVED_FLG = "ARR";
	private static final String CONST_RETURN = "RTN";
	private static final String CONST_ACCEPT_FLG = "ACP";
	private static final String OUTBOUND = "O";
	private static final String CAP_NOT_ACP_ERR = "mailtracking.defaults.err.capturedbutnotaccepted";
	private static final String RET_CANT_OFF_ERR = "mailtracking.defaults.mailbagenquiry.msg.err.returnedMailbagsCannotOffload";
	private static final String ARR_CANT_OFF_ERR = "mailtracking.defaults.mailbagenquiry.msg.err.arrivedMailbagsCannotOffload";
	private static final String DLV_CANT_OFF_ERR = "mailtracking.defaults.mailbagenquiry.msg.err.deliveredMailbagsCannotOffload";
	private static final String DIFF_PORT = "mailtracking.defaults.searchcontainer.differentport";
	private static final String NOT_FLT_ASS_ERR = "mailtracking.defaults.mailbagenquiry.msg.err.notFlightAssigned";
	private static final String NOT_SAME_FLT = "mailtracking.defaults.mailbagenquiry.msg.err.notassignedToSameFlight";
	private static final String NOT_ACP_DIFF_PORT = "mailtracking.defaults.mailbagenquiry.msg.err.notAcceptedOrScandPortDifferent";
	private static final String FLT_CLOSED = "mailtracking.defaults.err.flightclosed";

	private Log log = LogFactory.getLogger("OffloadMailbagCommand");

	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("OffloadMailbagCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();

		MailbagEnquiryModel mailbagEnquiryModel = 
				(MailbagEnquiryModel) actionContext.getScreenModel();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
				new MailTrackingDefaultsDelegate();
		ResponseVO responseVO = new ResponseVO();

		Collection<Mailbag> selectedMailbags = null;

		OffloadVO newOffloadVO = null;

		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		if (mailbagEnquiryModel != null &&
				mailbagEnquiryModel.getSelectedMailbags() != null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedMailbags() not null");

			selectedMailbags = mailbagEnquiryModel.getSelectedMailbags();
			log.log(Log.FINE, "selectedMailbags --------->>", selectedMailbags);

			
			// validation to check whether any of the selected mailbags are
			// returned/arrived/delivered ones
			for (Mailbag selectedvo : selectedMailbags) {

				if (MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(selectedvo.getLatestStatus())) {
					actionContext.addError(new ErrorVO(
							CAP_NOT_ACP_ERR));
					return;
				}
				
				if (CONST_RETURN.equals(selectedvo.getLatestStatus())) {
					actionContext.addError(new ErrorVO(
							RET_CANT_OFF_ERR));
					return;
				}
				
				if (CONST_ARRIVED_FLG.equals(selectedvo.getLatestStatus())) {
					actionContext.addError(new ErrorVO(
							ARR_CANT_OFF_ERR));
					return;
				}
				
				if (CONST_DELIVERED_FLG.equals(selectedvo.getLatestStatus())) {
					actionContext.addError(new ErrorVO(
							DLV_CANT_OFF_ERR));
					return;
				}			
			}
			
			int errorPort = 0;
			String contPort = "";
			
			for (Mailbag selectedvo : selectedMailbags) {
				
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
				actionContext.addError(new ErrorVO(
						DIFF_PORT, new Object[] {contPort}));
				return;
			}
			// Validation to check whether any mailbags are destination assigned
			for (Mailbag selectedvo : selectedMailbags) {
				
				if (selectedvo.getFlightSequenceNumber()<=0) { //Modified for ICRD-335217     
					actionContext.addError(new ErrorVO(
							NOT_FLT_ASS_ERR));
					return;
				}
			}
			
			String fltNo = "";
			String carrierCode = "";
			String scannedPort = "";
			long fltseqNo = 0;
			int carrierid = 0;
			LocalDate fltDate = null;
			
			for (Mailbag selectedvo : selectedMailbags) {
				fltNo = (selectedvo.getFlightNumber() != null) ? selectedvo.getFlightNumber() : "";
				fltseqNo = selectedvo.getFlightSequenceNumber();
				carrierid = selectedvo.getCarrierId();
				carrierCode = selectedvo.getCarrierCode();
				scannedPort = selectedvo.getScannedPort();
				
				// Added by A-8952 for ICRD-339669 start
				// included null checking to avoid exception.
				if (selectedvo.getFlightDate() != null) {
					fltDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false)
								.setDate(selectedvo.getFlightDate());
				}
				// Added by A-8952 for ICRD-339669 end
				
				break;
			}
			
			for (Mailbag selectedvo : selectedMailbags) {
				
				if (!carrierCode.equals(selectedvo.getCarrierCode()) || !fltNo.equals(
						selectedvo.getFlightNumber()) || fltseqNo != 
							selectedvo.getFlightSequenceNumber()) {
					ErrorVO errorVO = new ErrorVO(
							NOT_SAME_FLT);
					errors = new ArrayList<ErrorVO>();
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					actionContext.addAllError(errors);
					return;
				}
			}
			
			/*
			 * check whether the mailbag is Accepted to flight and scanned airport
			 * is current airport - else error
			 */
			for (Mailbag selectedvo : selectedMailbags) {
				
				if (!logonAttributes.getAirportCode().equals(selectedvo.getScannedPort()) && 
						!CONST_ACCEPT_FLG.equals(selectedvo.getLatestStatus())) {
					ErrorVO errorVO = new ErrorVO(
							NOT_ACP_DIFF_PORT);
					errors = new ArrayList<ErrorVO>();
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					actionContext.addAllError(errors);
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
				errors = (ArrayList<ErrorVO>) handleDelegateException(businessDelegateException);
			}
			
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
					errors = (ArrayList<ErrorVO>) handleDelegateException(businessDelegateException);
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
				actionContext.addError(new ErrorVO(
						ARR_CANT_OFF_ERR));
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
				
				for (Mailbag selectedMailbagVO : selectedMailbags) {
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
				errors = (ArrayList<ErrorVO>) handleDelegateException(businessDelegateException);
			}
			
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
				return;
			}
			log.log(Log.FINE, "MailbagEnquiry>OffloadMailCommand->isFlightClosed->", isFlightClosed);
			
			if (!isFlightClosed) {
				Object[] obj = {new StringBuilder(carrierCode).append("").append(fltNo).toString(), 
						fltDate.toDisplayDateOnlyFormat() };
				actionContext.addError(new ErrorVO(FLT_CLOSED, 
						obj));
				return;
			}
			
			// Setting LegSerialNumber to the selected MailbagVOs
			for (Mailbag selectedvo : selectedMailbags) {
				selectedvo.setLastUpdateTime(null);
			}


		}
		
		
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
	}

}
