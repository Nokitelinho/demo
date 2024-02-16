/*
 * ClearCommand.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.assignexceptions;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.AssignExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.AssignExceptionsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 *  
 * @author A-2245
 * 
 /*
 * 
 * Revision History
 * 
 * Version      Date          	 	Author          Description
 * 
 *  0.1         Feb 21, 2007   		A-2245			Initial draft
 *  
 *  
 *  
 */
public class ClearCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	/*
	 * String constants for CLASS_NAME
	 */
	private static final String CLASS_NAME = "ClearCommand";
	/*
	 * String constants for MODULE_NAME,SCREENID
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	private static final String SCREENID = "mailtracking.mra.gpareporting.assignexceptions";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	/*
	 * EMPTY_STRING
	 */
	private static final String EMPTY_STRING = "";
	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		/*
		 * assignExceptionsForm defined
		 */
		AssignExceptionsForm assignExceptionsForm = 
							(AssignExceptionsForm)invocationContext.screenModel;
		clearFormFields(assignExceptionsForm);
		/*
		 * assignExceptionsSession defined
		 */
		AssignExceptionsSession assignExceptionsSession = getScreenSession(MODULE_NAME,SCREENID);
		assignExceptionsSession.removeGpaReportingFilterVO();
		assignExceptionsSession.removeGpaReportingDetailVOs();
//		assignExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}	
	/**
	 * method clear FormFields
	 * @param assignExceptionsForm
	 */
	private void clearFormFields(AssignExceptionsForm assignExceptionsForm){
		log.entering(CLASS_NAME,"clearFormFields");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		assignExceptionsForm.setGpaCode(EMPTY_STRING);
		assignExceptionsForm.setGpaName(EMPTY_STRING);
		assignExceptionsForm.setCountryCode(EMPTY_STRING);
		assignExceptionsForm.setFromDate(EMPTY_STRING);
		assignExceptionsForm.setToDate(EMPTY_STRING);
		assignExceptionsForm.setExceptionCode(EMPTY_STRING);
		assignExceptionsForm.setAssignee(EMPTY_STRING);
		/*
		 * localdate defined
		 */
		LocalDate localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		String localDateString = TimeConvertor.toStringFormat(localDate,logonAttributes.getDateFormat());
		assignExceptionsForm.setFromDate(localDateString);
		assignExceptionsForm.setToDate(localDateString);
		/*hidden fields*/
		assignExceptionsForm.setSaveFlag(EMPTY_STRING);
		assignExceptionsForm.setDisplayPage("1");
		assignExceptionsForm.setLastPageNum("0");
		log.exiting(CLASS_NAME,"clearFormFields");
	}
}