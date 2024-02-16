/**
 * ReopenFlightCommand.java Created on January 16, 2012 *  
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.assign;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
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
public class ReopenFlightCommand extends BaseCommand{ 
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	private static final String TARGET = "success";
	private static final String OUTBOUND = "O";
	private static final String FLIGHT_OPEN = "mailtracking.defaults.national.assignmailbag.info.flightreopened";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException	 * 
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ReopenFlightCommand","execute");
		AssignMailBagForm assignMailBagForm = (AssignMailBagForm)invocationContext.screenModel;
		AssignMailBagSession assignMailBagSession = getScreenSession(MODULE_NAME, SCREEN_ID);		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		assignMailBagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
		OperationalFlightVO operationalFlightVO = assignMailBagSession.getOperationalFlightVO();

		FlightValidationVO flightValidationVO = assignMailBagSession.getFlightValidationVO();
		operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
		operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		operationalFlightVO.setFlightStatus(flightValidationVO.getFlightStatus());



		try{
			mailTrackingDefaultsDelegate.reopenFlight(operationalFlightVO);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);

		}

		assignMailBagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		//adding info message
		ErrorVO errorVO = new ErrorVO(FLIGHT_OPEN);
		Object [] obj = {operationalFlightVO.getCarrierCode(),operationalFlightVO.getFlightNumber(),operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat()};
		errorVO.setErrorData(obj);
		errors.add(errorVO);
		invocationContext.addAllError(errors);	

		invocationContext.target = TARGET;

	}

}
