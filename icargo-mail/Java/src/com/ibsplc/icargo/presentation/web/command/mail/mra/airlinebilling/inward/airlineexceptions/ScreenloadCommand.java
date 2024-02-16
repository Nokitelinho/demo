/*
 * ScreenloadCommand.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.airlineexceptions;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.AirlineExceptionsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 *
 * Command class for screenload of airlineexceptions screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 13, 2007   Rani Rose John    		Initial draft
 *
 */
public class ScreenloadCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	private static final String CLASS_NAME = "ScreenloadCommand";
	
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.airlineexceptions";
	/**
	 * 
	 */
	private static final String MODULE_NAME_INVEXCEPTION = "mailtracking.mra";
	/**
	 * screen id
	 *
	 */
	private static final String SCREENID_INVEXCEPTION = "mailtracking.mra.airlinebilling.inward.invoiceexceptions";
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
	private static final String EXCEPTION_CODES = "mailtracking.mra.airlinebilling.exceptioncodes";	
	
	private static final String MEMO_STATUS = "mra.airlinebilling.billingstatus";	
	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
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

		AirlineExceptionsForm airlineExceptionsForm = 
			(AirlineExceptionsForm)invocationContext.screenModel;
		
		AirlineExceptionsSession airLineExceptionsSession = 
			(AirlineExceptionsSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		airLineExceptionsSession.removeAirlineExceptionsVOs();
		InvoiceExceptionsSession  session=	(InvoiceExceptionsSession)getScreenSession(
				MODULE_NAME, SCREENID_INVEXCEPTION);
		
		Map oneTimeHashMap 							= null;
		Collection<String> oneTimeActiveStatusList 	= new ArrayList<String>();
		
		oneTimeActiveStatusList.add(EXCEPTION_CODES);
		oneTimeActiveStatusList.add(MEMO_STATUS);
		oneTimeActiveStatusList.add(KEY_BILLING_TYPE_ONETIME);
		oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	
    	try {
			
    		oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(
    										companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			handleDelegateException( e );
		}
		
		airLineExceptionsSession.setExceptionsOneTime(
				(HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		
		/**
		 * formvalus set here from ExceptionInInvoiceFilterVO
		 */
		String frmdate="";
		String todate="";
		if(session.getExceptionInInvoiceFilterVO()!=null){
			ExceptionInInvoiceFilterVO filtervo= session.getExceptionInInvoiceFilterVO();
			log.log(Log.FINE, "filtervo----->", filtervo);
			airlineExceptionsForm.setAirlineCode(filtervo.getAirlineCode());
			airlineExceptionsForm.setExceptionCode(filtervo.getExceptionStatus());
//			airlineExceptionsForm.setInvoiceRefNo(filtervo.getInvoiceNumber());	
			if(filtervo.getFromdate()!=null){
				frmdate=TimeConvertor.toStringFormat(filtervo.getFromdate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
			}
			airlineExceptionsForm.setFromDate(frmdate);
			if(filtervo.getTodate()!=null){
				todate=TimeConvertor.toStringFormat(filtervo.getTodate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
			}
			
			airlineExceptionsForm.setToDate(todate);
			//airlineExceptionsForm.setFromScreenFlag("invoiceexceptions");
		}else{
			
			airlineExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		}
		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

}
