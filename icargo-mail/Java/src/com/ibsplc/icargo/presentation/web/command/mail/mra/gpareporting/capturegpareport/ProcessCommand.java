/*
 * ProcessCommand.java Created on Mar 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Mar 16, 2007			a-2257		Created
 */
public class ProcessCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "ProcessCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";
	
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	
	private static final String ACTION_FAILURE = "action_failure";
	/**
	 * 
	 * TODO Purpose
	 * Mar 16, 2007, a-2257
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		
		CaptureGPAReportSession session = 
			(CaptureGPAReportSession)getScreenSession(
													MODULE_NAME, SCREENID);	
		
		GPAReportingFilterVO gpaReportingFilterVO = session.getGPAReportingFilterVO();
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();		
			
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
				
		gpaReportingFilterVO.setLastUpdatedUser(logonAttributesVO.getUserId());
		gpaReportingFilterVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		try {			
			log.log(Log.FINE, "filterVO", gpaReportingFilterVO);
			log.log(Log.FINE,"Going to call delegate");
			mailTrackingMRADelegate.processGpaReport(gpaReportingFilterVO);
			log.log(Log.FINE,"return from server");
			
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.INFO,"**************Inside Catch**************");	
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(errors != null && errors.size()>0){
			
			log.log(Log.INFO,"**************Inside Error**************");							
			invocationContext.addAllError(errors);	
			invocationContext.target = ACTION_FAILURE;
			log.exiting("CLASS_NAME", "execute");
			return;
			
		}
		errors.add(new ErrorVO("mailtraking.mra.gpareport.capturegpareport.err.processok"));
		invocationContext.addAllError(errors);	
		
		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {			
			log.log(Log.FINE,"Inside errors");	
		}
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

}

