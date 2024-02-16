/*
 * UpdateSessionAssignExceptionsCommand.java Created on Apr 2, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.AssignExceptionsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 *  
 * @author A-2245
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Apr 2, 2007				A-2245		Created
 */
public class UpdateSessionAssignExceptionsCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "UpdateSessionAssignExceptionsCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";	
	/*
	 * String constants for MODULE_NAME,SCREENID
	 */
	private static final String ASSIGNEXPTNS_MODULE_NAME = "mailtracking.mra";
	private static final String ASSIGNEXPTNS_SCREENID = "mailtracking.mra.gpareporting.assignexceptions";
	/**
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	/*
	 * one time codes
	 */
	private static final String MAILSTATUS_ONETIME = "mailtracking.mra.gpareporting.mailstatus";
	
	private static final String MAILCATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	
	private static final String HIGHESTNUM_ONETIME = "mailtracking.defaults.highestnumbermail";
	
	private static final String REGORINSIND_ONETIME = "mailtracking.defaults.registeredorinsuredcode";
	/**
	 * 
	 * @author A-2245
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
					throws CommandInvocationException {
		log.entering("UpdateSessionCommand", "execute");
		log.entering(CLASS_NAME, "execute");
		
		/*
		 * getting session
		 */
		AssignExceptionsSession assignExceptionsSession = getScreenSession(
								ASSIGNEXPTNS_MODULE_NAME,ASSIGNEXPTNS_SCREENID);
		CaptureGPAReportForm captureGPAReportForm = 
							(CaptureGPAReportForm)invocationContext.screenModel;
		/*
		 * Setting form fields
		 */
		setFilterFields(assignExceptionsSession, captureGPAReportForm);
		/*
		 * getting CaptureGPAReportSession
		 */
		CaptureGPAReportSession captureGPAReportSession = 
				(CaptureGPAReportSession)getScreenSession(MODULE_NAME, SCREENID);	
		
		GPAReportingFilterVO gpaReportingFilterVO = updateFilter(captureGPAReportForm);
		
		captureGPAReportSession.setGPAReportingFilterVO(gpaReportingFilterVO);	
		captureGPAReportSession.setParentScreenFlag(ASSIGNEXPTNS_SCREENID);
		/*
		 * Setting Onetimevalues for MailStatus
		 */
		Map<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		
		captureGPAReportSession.setMailStatus(
					(ArrayList<OneTimeVO>) oneTimeValues.get(MAILSTATUS_ONETIME));
		captureGPAReportSession.setMailCategory(
				(ArrayList<OneTimeVO>) oneTimeValues.get(MAILCATEGORY_ONETIME));
		captureGPAReportSession.setHeighestNum(
				(ArrayList<OneTimeVO>) oneTimeValues.get(HIGHESTNUM_ONETIME));
		captureGPAReportSession.setRegOrInsInd(
				(ArrayList<OneTimeVO>) oneTimeValues.get(REGORINSIND_ONETIME));
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		return;
		
	}
	
	/**
	 * method to set FilterFields
	 * @param assignExceptionsSession
	 * @param assignExceptionsForm
	 */
	private void setFilterFields(AssignExceptionsSession assignExceptionsSession, 
									CaptureGPAReportForm captureGPAReportForm){
		log.entering(CLASS_NAME,"setFilterFields");
		/*
		 * logonAttributes defined
		 */
		LogonAttributes logonAttributes  = getApplicationSession().getLogonVO();
		GPAReportingFilterVO gpaReportingFilterVO = assignExceptionsSession.getGpaReportingFilterVO();
		if(gpaReportingFilterVO!=null){
			captureGPAReportForm.setGpaCode(gpaReportingFilterVO.getPoaCode()!=null?
								gpaReportingFilterVO.getPoaCode():"");
			captureGPAReportForm.setGpaName(gpaReportingFilterVO.getPoaName()!=null?
					gpaReportingFilterVO.getPoaName():"");
			LocalDate fromDate = gpaReportingFilterVO.getReportingPeriodFrom();
			if(fromDate!=null){
				String fromDateStr = TimeConvertor.toStringFormat(fromDate,logonAttributes.getDateFormat());
				captureGPAReportForm.setFrmDate(fromDateStr);
			}
			LocalDate toDate = gpaReportingFilterVO.getReportingPeriodTo();
			if(toDate!=null){
			String toDateStr = TimeConvertor.toStringFormat(toDate,logonAttributes.getDateFormat());
			captureGPAReportForm.setToDate(toDateStr);
			}
		}
		log.exiting(CLASS_NAME, "setFilterFields");
	}
	
	/**
	 * 
	 * A-2245
	 * @param form
	 * @return gpaReportingFilterVO
	 */
	private GPAReportingFilterVO updateFilter(CaptureGPAReportForm form ){
		
		log.entering(CLASS_NAME, "updateFilter");
		
		GPAReportingFilterVO gpaReportingFilterVO = new GPAReportingFilterVO();
		
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		
		LocalDate toDate =  new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		
		if(form.getGpaCode() != null && 
				form.getGpaCode().trim().length() > 0){
			gpaReportingFilterVO.setPoaCode(
					form.getGpaCode().toUpperCase());
		    }else{
		    	gpaReportingFilterVO.setPoaCode(null);
		    }
		
		if (form.getFrmDate() != null && form.getFrmDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getFrmDate(), "dd-MMM-yyyy")) {

				fromDate.setDate(form.getFrmDate());
				gpaReportingFilterVO.setReportingPeriodFrom(fromDate);
			}
		} else {
			gpaReportingFilterVO.setReportingPeriodFrom(null);
		}
		
		if (form.getToDate() != null && form.getToDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getToDate(), "dd-MMM-yyyy")) {

				toDate.setDate(form.getToDate());
				gpaReportingFilterVO.setReportingPeriodTo(toDate);
			}
		} else {
			gpaReportingFilterVO.setReportingPeriodTo(null);
		}
		log.exiting(CLASS_NAME, "updateFilter");
		
		
		return gpaReportingFilterVO;
	}
	/**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private Map<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ScreenLoadCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
			log.log(Log.FINE, "****inside try*", getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			 businessDelegateException.getMessage();
			 handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return oneTimeValues;
	}

	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();
		
		parameterTypes.add(MAILSTATUS_ONETIME); 		
		parameterTypes.add(MAILCATEGORY_ONETIME);
		parameterTypes.add(HIGHESTNUM_ONETIME);
		parameterTypes.add(REGORINSIND_ONETIME);
		log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
		return parameterTypes;    	
	}
}
