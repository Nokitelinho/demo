/*
 * ScreenLoadCommand.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.assignexceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.AssignExceptionSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.AssignExceptionsForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2401
 *
 */
public class ScreenLoadCommand extends BaseCommand{
	
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAIL TRACKING");
	private static final String CLASS_NAME = "ScreenloadCommand";
/**
 * module name
 * 
 */
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	/**
	 * screen id
	 * 
	 */
	private static final String SCREEN_ID = "mailtracking.mra.flown.assignexceptions";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String EXCEPTION_ONETIME = "maitracking.flown.exceptioncode";
	private static final String DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		log.entering(CLASS_NAME, "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//Map<String, String> parameters = getSystemParameterValues();
		AssignExceptionSession assignExceptionSession = (AssignExceptionSession)
						getScreenSession(MODULE_NAME, SCREEN_ID);
		AssignExceptionsForm assignExceptionsForm = 
			(AssignExceptionsForm)invocationContext.screenModel;
		
		LocalDate date=new LocalDate(NO_STATION,NONE,false);
		LocalDate defaultDate =new LocalDate(NO_STATION,NONE,false); 
		log.log(Log.FINE, "date is ", date.toDisplayFormat(), "default is ",
				defaultDate.toDisplayFormat());
		defaultDate.addMonths(-1);
		log.log(Log.FINE, "date is ", date.toDisplayFormat(), "default is ",
				defaultDate.toDisplayFormat());
		String frmDate = TimeConvertor.toStringFormat(
				((LocalDate)defaultDate).toCalendar(),DATEFORMAT);
		String toDate = TimeConvertor.toStringFormat(
				((LocalDate)date).toCalendar(),DATEFORMAT);
		if(!("Y".equals(assignExceptionsForm.getViewFlownMailFlag()))){
			assignExceptionsForm.setFlightCarrierCode(
				getApplicationSession().getLogonVO().getOwnAirlineCode());
			assignExceptionsForm.setFromDate(frmDate);
			assignExceptionsForm.setToDate(toDate);
		}
		assignExceptionSession.setExceptions(null);
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Collection<ErrorVO> errors = null;
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		try {
			
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
				logonAttributes.getCompanyCode(),getOneTimeParameterTypes());
			
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessageVO().getErrors();
			errors=handleDelegateException(businessDelegateException);
		}
		assignExceptionSession.setOneTimeValues((HashMap<String,
				Collection<OneTimeVO>>)oneTimeValues);
		log.log(Log.FINE, "onetime values", oneTimeValues);
		log.log(Log.FINE, "flag *******", assignExceptionsForm.getStatusFlag());
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
		}
		invocationContext.target = SCREENLOAD_SUCCESS;
		
	}
	
	 /**
     * 
     * @return Collection<String>
     */
    private Collection<String> getOneTimeParameterTypes() {
    	
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(EXCEPTION_ONETIME);
    	    	
    	return parameterTypes;    	
  }
}
