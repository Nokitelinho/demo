/*
 * ProcessCommand.java Created on Feb 14, 2006
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
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
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
 * @author A-2449
 *
 */
public class ProcessCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MRA flown");
	private static final String ACTION_SUCCESS = "process_success";
	private static final String ACTION_FAILURE = "process_failure";
	private static final String CLASS_NAME = "ProcessCommand";
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
		ErrorVO error = null;  
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		FlownMailFilterVO filterVO = new FlownMailFilterVO();
		FlightValidationVO flightValidationVO = session.getFlightDetails();
		
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		filterVO.setCompanyCode(companyCode);
		filterVO.setFlightCarrierCode(form.getCarrierCode());
		filterVO.setFlightNumber(form.getFlightNumber());
		filterVO.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
		filterVO.setFlightSequenceNumber((int)flightValidationVO.getFlightSequenceNumber());
		filterVO.setSegmentSerialNumber(Integer.parseInt(form.getSegment()));
		
		
		LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		if(form.getFlightDate() != null && 
				form.getFlightDate().trim().length() > 0){
			flightDate.setDate(form.getFlightDate());
			filterVO.setFlightDate(flightDate);
		}
		
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		try{
			delegate.processFlight(filterVO);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		
		
		if (errors != null && errors.size() > 0) {	
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_FAILURE; 
		}
		else{
			log.log(1,"Process Success.....");
			form.setProcessFlag("Y");
		}
		
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
}
