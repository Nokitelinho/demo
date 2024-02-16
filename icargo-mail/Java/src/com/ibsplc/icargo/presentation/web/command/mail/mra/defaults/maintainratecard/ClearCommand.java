/*
 * ClearCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ClearCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("Maintain UPURateCard ClearCommand");
	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String KEY_BILLING_TYPE_ONETIME = "mra.gpabilling.ratestatus";
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
   		
   		MaintainUPURateCardSession session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		
   		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	
   		Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();

		oneTimeActiveStatusList.add(KEY_BILLING_TYPE_ONETIME);
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
			
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		if(errors != null && errors.size() > 0  ){
   			invocationContext.addAllError(errors);
   			invocationContext.target = ACTION_SUCCESS;
   			
   			return;
   		}
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
   		form.setRateCardId("");
   		form.setStatus("");
   		form.setDescription("");
   		form.setValidFrom("");
   		form.setValidTo("");
   		form.setMialDistFactor("");
   		form.setSvTkm("");
   		form.setSalTkm("");
   		//form.setCpTkm("");
   		form.setAirmialTkm("");
   		//form.setExchangeRate("");
   		session.removeRateLineDetails();
   		form.setScreenStatus("screenload");
   		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }

}