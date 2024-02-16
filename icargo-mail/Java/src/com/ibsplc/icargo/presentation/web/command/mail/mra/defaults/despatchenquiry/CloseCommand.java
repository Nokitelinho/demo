/*
 * CloseCommand.java Created on July 10,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 *
 */
public class CloseCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS DespatchEnquiry");

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";

	private static final String CLASS_NAME = "CloseCommand";
	
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CLOSE_SUCCESS_TOGPAINVOICEENQUIRY="gpainvoiceenquiry";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID_GPA = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";
	
	private static final String CLOSE_TOUNACCDISPATCHES_SUCCESS = "closedispatchenquiry_success";
	private static final String CLOSE_TOPRORATIONEXCEPTION_SUCCESS = "closeprorationexception_success";

	
	/**
	 * Method  implementing closing of screen
	 * @author a-2270
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	DespatchEnqSession despatchEnqSession=getScreenSession(MODULE,SCREENID);
    	despatchEnqSession.removeAllAttributes();
    	
    	DespatchEnquiryForm despatchEnquiryForm=(DespatchEnquiryForm)invocationContext.screenModel;
    	GPABillingInvoiceEnquirySession gPABillingInvoiceEnquirySession=(GPABillingInvoiceEnquirySession)getScreenSession(
    			MODULE_NAME, SCREENID_GPA);
    	log.log(Log.INFO, "CloseFlag", despatchEnquiryForm.getCloseFlag());
				if("fromdispatchEnquiry".equalsIgnoreCase(despatchEnquiryForm.getCloseFlag())){
    		invocationContext.target = CLOSE_TOUNACCDISPATCHES_SUCCESS;
    		return;
    	}   
       else if("frominvoiceEnquiry".equalsIgnoreCase(despatchEnquiryForm.getCloseFlag())){
    	   despatchEnquiryForm.setFromGPABillingInvoiceEnquiry(null);
    	   invocationContext.target = CLOSE_SUCCESS_TOGPAINVOICEENQUIRY;
   		return;	
    	 }
       else if("fromlistprorationexception".equalsIgnoreCase(despatchEnquiryForm.getCloseFlag())){
    	    		invocationContext.target = CLOSE_TOPRORATIONEXCEPTION_SUCCESS;
    	    		return;
    	    	} else {
		invocationContext.target = CLOSE_SUCCESS;
	}
    	//Bug ID: ICRD-5305 - Jeena - For Navigating to previous screen when Close button is clicked in current screen
    	 despatchEnquiryForm.setCloseFlag("");
    	    	
    	}


    }


