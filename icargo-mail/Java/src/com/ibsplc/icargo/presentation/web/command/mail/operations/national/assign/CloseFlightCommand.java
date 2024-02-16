/**
 * CloseFlightCommand.java Created on January 14, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.assign;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.AssignMailBagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class CloseFlightCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	private static final String CLOSE_SUCCESS = "success";
	private static final String CLOSE_FAILURE = "failure";
	private static final String OUTBOUND = "O";
	private static final String FLIGHT_CLOSED = "mailtracking.defaults.national.assignmailbag.info.flightclosed";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("CloseFlightCommand","execute");
		AssignMailBagForm assignMailBagForm = (AssignMailBagForm)invocationContext.screenModel;
		AssignMailBagSession assignMailBagSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		/*ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();*/
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		assignMailBagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//String fromScreen = assignMailBagForm.getFromScreen();
		MailManifestVO mailManifestVO = assignMailBagSession.getMailManifestVO();
		OperationalFlightVO  operationalFlightVO = assignMailBagSession.getOperationalFlightVO();
		log.log(Log.INFO, "operationalFlightVO flight route:------------>>",
				operationalFlightVO.getFlightRoute());
		FlightValidationVO flightValidationVO = assignMailBagSession.getFlightValidationVO();
		operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
		operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		operationalFlightVO.setFlightStatus(flightValidationVO.getFlightStatus());
		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
		operationalFlightVO.setDirection(OUTBOUND);

		/**
		 * To check whether flight is already closed.
		 */

		boolean isFlightClosed = false;
		try {

			isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}		
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = CLOSE_SUCCESS;			
			return;
		}
		log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
		try{
			mailTrackingDefaultsDelegate.closeFlightManifest(operationalFlightVO, mailManifestVO);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);

			invocationContext.target = CLOSE_FAILURE;

			return;
		}

		//adding info message
		ErrorVO errorVO = new ErrorVO(FLIGHT_CLOSED);
		Object [] obj = {operationalFlightVO.getCarrierCode(),operationalFlightVO.getFlightNumber(),operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat()};
		errorVO.setErrorData(obj);
		errors.add(errorVO);
		invocationContext.addAllError(errors);

		invocationContext.target = CLOSE_SUCCESS;
	}


}
