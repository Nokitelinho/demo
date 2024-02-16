/*
 * ScreenLoadCommand.java Created on Mar 12, 2007
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewbillingline;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2398
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewbillingline";

	private static final String SCREEN_SUCCESS = "screenload_success";
	
	private static final String SCREEN_FAILURE = "screenload_failure";
	
	/**
	 * Key value for onetime values of Billing line status
	 */
	private static final String KEY_RATE_STATUS_ONETIME =
		"mra.gpabilling.ratestatus";

	/**
	 * Key value for onetime values of Billed Sector.
	 */
	private static final String KEY_BILLED_SECTOR_ONETIME =
        "mailtracking.mra.billingSector";

	private static final String KEY_UNTCOD ="mail.mra.gpabilling.untcod";
	
	/**
	 * 	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");    	
			
    	//ViewBillingLineForm form = (ViewBillingLineForm) invocationContext.screenModel;
		ViewBillingLineSession session =
			(ViewBillingLineSession) getScreenSession(
				MODULE_NAME, SCREENID);
		session.setBillingLineDetails(null);
		session.setIndexMap(null);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		/**
		 * To obtain Company Code from logon atrributes
		 */
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		
		/** adding attributes to map for passing to SharedDefaultsDelegate */
		oneTimeActiveStatusList.add(KEY_RATE_STATUS_ONETIME);
		oneTimeActiveStatusList.add(KEY_BILLED_SECTOR_ONETIME);
		oneTimeActiveStatusList.add(KEY_UNTCOD);
				
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		
		log.log(Log.INFO, "  the oneTimeHashMap after server call is ",
				oneTimeHashMap);
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		
    	
        if(errors.size() > 0){   
        	invocationContext.addAllError(errors);  
        	invocationContext.target = SCREEN_FAILURE;
        	log.log(1,"Errors present...screen load failed....");
        }
        else{
        	invocationContext.target = SCREEN_SUCCESS;
        	log.log(1,"...Screen load Success....");
        }
        	
		
    }

    }
