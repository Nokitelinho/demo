/*
 * ProcessCommand.java Created on Feb 26, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.assignexceptions;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.AssignExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

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
 *  0.1         Feb 26, 2007   		A-2245			Initial draft
 *  
 *  
 *  
 */
public class ProcessCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	/*
	 * String constants for CLASS_NAME
	 */
	private static final String CLASS_NAME = "ProcessCommand";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "process_success";
	private static final String ACTION_FAILURE = "process_failure";
	
	public static final String MTK_MRA_GPAREPORTING_PROCESS_STATUS_NOTOK =
		"mailtracking.mra.gpareporting.processgpareport.procedure.status.notok";
	
	private static final String UNABLETOPROCESS=
		  "mailtracking.mra.gpareporting.assignexceptions.procedure.status.notok";
	
	/*
	 * error codes
	 */
	private static final String INFO_PROCESSED = "mailtracking.mra.gpareporting.assignexceptions.msg.info.processed";
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
		AssignExceptionsForm assignExceptionsForm = (AssignExceptionsForm)invocationContext.screenModel;
		
		Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
		/*
		 * logonAttributes defined
		 */
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		GPAReportingFilterVO gpaReportingFilterVO = new GPAReportingFilterVO();
		gpaReportingFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		gpaReportingFilterVO.setLastUpdatedUser(logonAttributes.getUserId());
		gpaReportingFilterVO.setPoaCode(assignExceptionsForm.getGpaCode());
	   	boolean isTimeNeeded = false;
	   	LocalDate localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, isTimeNeeded);
	   	LocalDate fromDate=localDate.setDate(assignExceptionsForm.getFromDate().trim());
	   	gpaReportingFilterVO.setReportingPeriodFrom(fromDate);
	   	localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, isTimeNeeded);
	   	LocalDate toDate=localDate.setDate(assignExceptionsForm.getToDate().trim());
	   	gpaReportingFilterVO.setReportingPeriodTo(toDate);
		log.log(Log.INFO, "gpaReportingFilterVO=>>", gpaReportingFilterVO);
			try{
				new MailTrackingMRADelegate().processGpaReport(gpaReportingFilterVO);
			}catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
				//handleDelegateException(businessDelegateException);
				log.log(Log.WARNING,"*********caught exception");
				//invocationContext.target = ACTION_FAILURE;
				//return;
			}
			if(errors!=null && errors.size()>0){
				log.log(Log.INFO,"errors present");
				for(ErrorVO err:errors){
					if(MTK_MRA_GPAREPORTING_PROCESS_STATUS_NOTOK.equals(err.getErrorCode())){
						ErrorVO error=new ErrorVO(UNABLETOPROCESS);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
						invocationContext.target = ACTION_FAILURE;
						//assignExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
						return;
						
					}
				}
				invocationContext.addAllError(errors);
			}
//		invocationContext.addError(new ErrorVO(INFO_PROCESSED));
		log.log(Log.INFO,"INFO_PROCESSED*******");
		errors.add(new ErrorVO(INFO_PROCESSED));
		invocationContext.addAllError(errors);
//		assignExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}	
}
