/*
 * ScreenLoadCommand.java Created on Aug 07 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.proformainvoicediffreport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ProformaInvoiceDiffReportSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3271
 *
 */

public class ScreenLoadCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ProformaInvoiceDiffReport ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.proformainvoicediffreport";
	
	private static final String KEY_ONETIME_VOS = "mra.defaults.despatchenqtype";
	/*
	 * Target mappings for success
	 */
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	
    	SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
    	ProformaInvoiceDiffReportSession proformaInvoiceDiffReportSession = (ProformaInvoiceDiffReportSession) getScreenSession(
    			MODULE_NAME, SCREEN_ID);
    	
    	
    	Map<String, Collection<OneTimeVO>> oneTimeValues = null;
    	Collection<String> fieldTypes = new ArrayList<String>();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	fieldTypes.add(KEY_ONETIME_VOS);
    	try {
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(), fieldTypes);
    	    log.log(Log.INFO, "One onetinevalues ", oneTimeValues);
			proformaInvoiceDiffReportSession.setOneTimeVOs(new ArrayList<OneTimeVO>(oneTimeValues
				.get(KEY_ONETIME_VOS)));
    	} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
    	if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		log.exiting(CLASS_NAME,"execute");
    	invocationContext.target=SCREENLOAD_SUCCESS;
    }

}
