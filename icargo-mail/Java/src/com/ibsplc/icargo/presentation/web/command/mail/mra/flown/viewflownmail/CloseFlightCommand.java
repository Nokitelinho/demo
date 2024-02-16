/*
 * CloseFlightCommand.java Created on Feb 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.viewflownmail;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.ViewFlownMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.ViewFlownMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2449
 *
 */
public class CloseFlightCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MRA flown");
	private static final String ACTION_SUCCESS = "close_success";
	private static final String ACTION_FAILURE = "close_failure";
	private static final String CLASS_NAME = "CloseFlightCommand";
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String SCREENID = "mra.flown.viewflownmail";
	
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		ViewFlownMailSession session = 
			(ViewFlownMailSession)getScreenSession(MODULE_NAME, SCREENID);
		ViewFlownMailForm form =
			(ViewFlownMailForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		FlownMailFilterVO filterVO = new FlownMailFilterVO();
		
		FlightValidationVO flightValidationVO = session.getFlightDetails();
		
		filterVO.setCompanyCode(flightValidationVO.getCompanyCode());
		filterVO.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
		filterVO.setFlightNumber(flightValidationVO.getFlightNumber());
		filterVO.setFlightSequenceNumber((int)flightValidationVO.getFlightSequenceNumber());
		filterVO.setSegmentSerialNumber(Integer.parseInt(form.getSegment()));
		
		/*
		 * Delegate Call
		 */
		try{
			new MailTrackingMRADelegate().closeFlight(filterVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			
		}
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			
			invocationContext.target = ACTION_FAILURE;
			return;
		}
		invocationContext.target=ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		
	}
	
	
}
