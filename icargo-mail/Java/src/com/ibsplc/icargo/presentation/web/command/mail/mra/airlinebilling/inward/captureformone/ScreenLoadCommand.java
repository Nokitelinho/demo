/*
 * ScreenLoadCommand.java Created on June 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformone;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormOneForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CaptureForm1 ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureformone";
	
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";
	//private static final String ACTION_FAILURE = "screenload_failure";
	
	/**
	 * INVOICE_STATUS one time
	 */

	//private static final String INVOICE_STATUS = "mra.airlinebilling.inward.captureformone.invoicestatus";
	
	private static final String DESPATCH_STATUS = "mailtracking.mra.despatchstatus";
	
	


	/**
	 * INVOICE_FORM1_STATUS one time
	 */
	private static final String INVOICE_FORM1_STATUS = "mra.airlinebilling.inward.captureformone.invoiceformonestatus";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	
    	//CaptureFormOneForm form=(CaptureFormOneForm)invocationContext.screenModel;
    	CaptureFormOneSession captureFormOneSession=getScreenSession(MODULE_NAME,SCREEN_ID);
    	captureFormOneSession.removeAllAttributes(); 		
    	CaptureMailFormOneForm form=(CaptureMailFormOneForm)invocationContext.screenModel;
		log.exiting(CLASS_NAME, "execute");
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(DESPATCH_STATUS);
		parameterTypes.add(INVOICE_FORM1_STATUS);
		
		
		try {
			oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(
					logonAttributes.getCompanyCode(), parameterTypes);
			log.log(Log.FINE, "One Time Values---", oneTimeValues);

		} catch (BusinessDelegateException e) {
			handleDelegateException(e);

    }
		if(oneTimeValues!=null){
			captureFormOneSession.setOneTimeMap((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
			
			
		}form.setLinkDisable("Y");
		 invocationContext.target = ACTION_SUCCESS;
    }
   
}
