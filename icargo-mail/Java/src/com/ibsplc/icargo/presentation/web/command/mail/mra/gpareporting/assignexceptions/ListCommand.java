/*
 * ListCommand.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.assignexceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.AssignExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.AssignExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
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
public class ListCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	/*
	 * String constants for CLASS_NAME
	 */
	private static final String CLASS_NAME = "ListCommand";
	/*
	 * String constants for MODULE_NAME,SCREENID
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	private static final String SCREENID = "mailtracking.mra.gpareporting.assignexceptions";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "list_success";
	private static final String ACTION_FAILURE = "list_failure";
	/*
	 * EMPTY_STRING
	 */
	private static final String EMPTY_STRING = "";
	/*
	 * String ONE TIME CODES
	 */
	private static final String KEY_EXCEPTIONCODE = "mailtracking.mra.gpareporting.exceptioncodes";
	private static final String KEY_MAILCATEGORY = "mailtracking.defaults.mailcategory";
	/*
	 * error codes
	 */
	private static final String ERR_MANDGPACODE = "mailtracking.mra.gpareporting.assignexceptions.msg.err.mandgpacode";
	private static final String ERR_MANDFRMTODATES = "mailtracking.mra.gpareporting.assignexceptions.msg.err.mandfrmtodates";
	private static final String ERR_NORESULTS = "mailtracking.mra.gpareporting.assignexceptions.msg.err.noresults";
	/**
	 * CCA Status
	 */
	private static final String CCA_STATUS = "mra.defaults.ccastatus";
	
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
		 * assignExceptionsSession defined
		 */
		AssignExceptionsSession assignExceptionsSession = getScreenSession(MODULE_NAME,SCREENID);
		/*
		 * assignExceptionsForm defined
		 */
		AssignExceptionsForm assignExceptionsForm = 
							(AssignExceptionsForm)invocationContext.screenModel;
		/*
		 * getting OneTimeValues
		 */
		getOneTimeValues(assignExceptionsSession);
		if(assignExceptionsSession.getGpaReportingFilterVO()!=null && 
					(assignExceptionsForm.getWindowFlag()!=null && 
					 assignExceptionsForm.getWindowFlag().trim().length()>0)){
			assignExceptionsForm.setWindowFlag(EMPTY_STRING);
			setFilterFields(assignExceptionsSession, assignExceptionsForm);
		}else{
			validateFormFields(assignExceptionsForm, invocationContext);
			if(invocationContext.getErrors()!=null && invocationContext.getErrors().size()>0){
				assignExceptionsSession.removeGpaReportingDetailVOs();
//				assignExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.target=ACTION_FAILURE;
				return;
			}
			populateFilterVO(assignExceptionsSession, assignExceptionsForm);
		}
		GPAReportingFilterVO gpaBillingFilterVO = assignExceptionsSession.getGpaReportingFilterVO();
		if(gpaBillingFilterVO!=null){
			log.log(Log.INFO, "gpaBillingFilterVO--------->>",
					gpaBillingFilterVO);
			if(assignExceptionsForm.getDisplayPage().length()>0) {
				gpaBillingFilterVO.setPageNumber(Integer.parseInt(
									assignExceptionsForm.getDisplayPage()));
			}
			Page<GPAReportingClaimDetailsVO> resultGPAReportingClaimVOs = null;
			/*
			 * delegate call for list
			 */
			try{
				resultGPAReportingClaimVOs = new MailTrackingMRADelegate().findClaimDetails(gpaBillingFilterVO);
			}catch(BusinessDelegateException businessDelegateException){
				handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
			if(resultGPAReportingClaimVOs!=null && resultGPAReportingClaimVOs.size()>0){
				log.log(Log.INFO, "resultGPAReportingClaimVOs.size()-->>",
						resultGPAReportingClaimVOs.size());
				assignExceptionsSession.setGpaReportingDetailVOs(resultGPAReportingClaimVOs);
//				assignExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			}else{
				assignExceptionsSession.removeGpaReportingDetailVOs();
				invocationContext.addError(new ErrorVO(ERR_NORESULTS));
//				assignExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			}
		}
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}	
	/**
	 * method to validate FormFields
	 * @param assignExceptionsForm
	 * @param invocationContext
	 */
	private void validateFormFields(AssignExceptionsForm assignExceptionsForm, 
											InvocationContext invocationContext){
		log.entering(CLASS_NAME,"validateFormFields");
		/*
		 * mandatory checks
		 */
		if(assignExceptionsForm.getGpaCode()==null || 
				assignExceptionsForm.getGpaCode().trim().length()==0){
			invocationContext.addError(new ErrorVO(ERR_MANDGPACODE));
		}
		if((assignExceptionsForm.getFromDate()==null || 
				assignExceptionsForm.getFromDate().trim().length()==0)||
				(assignExceptionsForm.getToDate()==null || 
				assignExceptionsForm.getToDate().trim().length()==0)){
			invocationContext.addError(new ErrorVO(ERR_MANDFRMTODATES));
		}
		log.exiting(CLASS_NAME, "validateFormFields");
	}
	/**
	 * method to populate FilterVO 
	 * @param assignExceptionsSession
	 * @param assignExceptionsForm
	 */
	private void populateFilterVO(AssignExceptionsSession assignExceptionsSession, 
									AssignExceptionsForm assignExceptionsForm){
		log.entering(CLASS_NAME,"populateFilterVO");
		GPAReportingFilterVO gpaReportingFilterVO = new GPAReportingFilterVO();
		/*
		 * logonAttributes defined
		 */
		LogonAttributes logonAttributes  = getApplicationSession().getLogonVO();
		gpaReportingFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		gpaReportingFilterVO.setPoaCode(assignExceptionsForm.getGpaCode().trim());
		gpaReportingFilterVO.setPoaName(assignExceptionsForm.getGpaName().trim());
		gpaReportingFilterVO.setCountry(assignExceptionsForm.getCountryCode().trim());
	   	boolean isTimeNeeded = false;
	   	LocalDate localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, isTimeNeeded);
	   	LocalDate fromDate=localDate.setDate(assignExceptionsForm.getFromDate().trim());
	   	gpaReportingFilterVO.setReportingPeriodFrom(fromDate);
	   	localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, isTimeNeeded);
	   	LocalDate toDate=localDate.setDate(assignExceptionsForm.getToDate().trim());
	   	gpaReportingFilterVO.setReportingPeriodTo(toDate);
		gpaReportingFilterVO.setExceptionCode(assignExceptionsForm.getExceptionCode());		
		gpaReportingFilterVO.setAssignee(assignExceptionsForm.getAssignee().trim());	
		assignExceptionsSession.setGpaReportingFilterVO(gpaReportingFilterVO);
	   	log.exiting(CLASS_NAME, "validateFormFields");
	}
	/**
	 * method to set FilterFields
	 * @param assignExceptionsSession
	 * @param assignExceptionsForm
	 */
	private void setFilterFields(AssignExceptionsSession assignExceptionsSession, 
			AssignExceptionsForm assignExceptionsForm){
		log.entering(CLASS_NAME,"setFilterFields");
		/*
		 * logonAttributes defined
		 */
		LogonAttributes logonAttributes  = getApplicationSession().getLogonVO();
		GPAReportingFilterVO gpaReportingFilterVO = assignExceptionsSession.getGpaReportingFilterVO();
		if(gpaReportingFilterVO!=null){
			assignExceptionsForm.setGpaCode(gpaReportingFilterVO.getPoaCode()!=null?
								gpaReportingFilterVO.getPoaCode():EMPTY_STRING);
			assignExceptionsForm.setGpaName(gpaReportingFilterVO.getPoaName()!=null?
					gpaReportingFilterVO.getPoaName():EMPTY_STRING);
			assignExceptionsForm.setCountryCode(gpaReportingFilterVO.getCountry()!=null?
					gpaReportingFilterVO.getCountry():EMPTY_STRING);
			LocalDate fromDate = gpaReportingFilterVO.getReportingPeriodFrom();
			if(fromDate!=null){
				String fromDateStr = TimeConvertor.toStringFormat(fromDate,logonAttributes.getDateFormat());
				assignExceptionsForm.setFromDate(fromDateStr);
			}
			LocalDate toDate = gpaReportingFilterVO.getReportingPeriodTo();
			if(toDate!=null){
			String toDateStr = TimeConvertor.toStringFormat(toDate,logonAttributes.getDateFormat());
			assignExceptionsForm.setToDate(toDateStr);
			assignExceptionsForm.setExceptionCode(gpaReportingFilterVO.getExceptionCode()!=null?
					gpaReportingFilterVO.getExceptionCode():EMPTY_STRING);
			assignExceptionsForm.setAssignee(gpaReportingFilterVO.getAssignee()!=null?
					gpaReportingFilterVO.getAssignee():EMPTY_STRING);			
			}
		}
		log.exiting(CLASS_NAME, "setFilterFields");
	}
	/**
	 * method to getOneTimeValues
	 * @param assignExceptionsSession
	 */
	private void getOneTimeValues(AssignExceptionsSession assignExceptionsSession){
		log.entering(CLASS_NAME,"getOneTimeValues");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    Map<String,Collection<OneTimeVO>> hashMap = null;
	    Collection<String> oneTimeList = new ArrayList<String>();
	    oneTimeList.add(KEY_EXCEPTIONCODE);
	    oneTimeList.add(KEY_MAILCATEGORY);
	    oneTimeList.add(CCA_STATUS);
		try {
			log.log(Log.FINEST,"***********************************inside try");
			hashMap = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),oneTimeList);
			log.log(Log.FINEST, "hash map*****************************",
					hashMap);

		}catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
			log.log(Log.SEVERE, "status fetch exception");
		}
		if(hashMap!=null){
			assignExceptionsSession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)hashMap);
			log.log(Log.INFO, "OneTimeValues(after fetching):>",
					assignExceptionsSession.getOneTimeVOs());
		}
		log.exiting(CLASS_NAME,"getOneTimeValues");
	}
}
