/*
 * ScreenLoadViewFlownMailCommand.java Created on Feb 13, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.viewflownmail;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.ViewFlownMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.ViewFlownMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2449
 *
 */

public class ScreenLoadViewFlownMailCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("ViewFlownMail ScreenloadCommand");
	
	private static final String CLASS_NAME = "ScreenLoadViewFlownMailCommand";

	private static final String MODULE_NAME = "mra.flown";
	private static final String SCREEN_ID = "mra.flown.viewflownmail";
		
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";
	//private static final String ACTION_FAILURE = "screenload_failure";
	private static final String KEY_FLOWNSEGMENTSTATUS="mailtracking.mra.flownSegmentStatus";
	private static final String FROM_ACCOUNTING_SCREEN = "from_accountingScreen";
	private static final String SYS_PARA_ACC_ENTRY="cra.accounting.isaccountingenabled";
	private static final String KEY_WEIGHT_UNIT="mail.mra.defaults.weightunit";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering(CLASS_NAME, "execute");
    	
    	ViewFlownMailSession session = (ViewFlownMailSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	
    	ViewFlownMailForm form = (ViewFlownMailForm)invocationContext.screenModel;
    	if(!FROM_ACCOUNTING_SCREEN.equals(form.getFromScreen())){
    		session.removeAllAttributes();
    	}
    	LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_FLOWNSEGMENTSTATUS);
		oneTimeActiveStatusList.add(KEY_WEIGHT_UNIT); 
				
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		if(errors!=null && errors.size()>0){
	    	invocationContext.addAllError(errors);
	    	}
		
		if(FROM_ACCOUNTING_SCREEN.equals(form.getFromScreen())){
			log.log(Log.INFO, "filterVo from session-->", session.getFilter());
			populateFormFields(session.getFilter(),form);
		}
//		 code for acc entry sys para starts
		Collection<String> systemParameterCodes = new ArrayList<String>();

		systemParameterCodes.add(SYS_PARA_ACC_ENTRY);

		Map<String, String> systemParameters = null;

		try {

			systemParameters = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			invocationContext.addAllError(handleDelegateException(e));
		}

		String accountingEnabled = (systemParameters.get(SYS_PARA_ACC_ENTRY));
		log.log(Log.INFO, "IS acc enabled--->", accountingEnabled);
		if("N".equals(accountingEnabled)){
			form.setAccEntryFlag("N");
		}else{
			form.setAccEntryFlag("Y");
		}
		form.setCarrierCode(logonAttributes.getOwnAirlineCode());
//		code for acc entry sys para ends
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");		
    }
    
    /*
     * method to populate form fileds from session when coming from accounting screen 
     */
     private void populateFormFields(FlownMailFilterVO filterVo,ViewFlownMailForm form){
    	    form.setFlightDate(filterVo.getStringFlightDate());
    	    form.setFlightNumber(filterVo.getFlightNumber());
    	    form.setCarrierCode(filterVo.getFlightCarrierCode());
    	 }

}
