/*
 * ScreenLoadCommand.java Created on Nov 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listbillingentries;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingEntriesForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListBillingEntries ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

//	private static final String MODULE_NAME = "mailtracking.mra.defaults";
//	private static final String SCREEN_ID = "mailtracking.mra.defaults.listbillingentries";
//	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
//	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";
	//private static final String ACTION_FAILURE = "screenload_failure";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	//String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	
    	//ListBillingEntriesSession session=null;
   		//session=(ListBillingEntriesSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		//session.removeAllAttributes();
   		//session.removeBillingEntries();
   		//session.removeSelectedRows();
   		//Map oneTimeHashMap 								= null;
		//Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		//oneTimeActiveStatusList.add(KEY_BILLING_TYPE_ONETIME);
		//oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
//		try {
//			/** getting collections of OneTimeVOs */
//			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
//		} catch (BusinessDelegateException e) {
//printStackTrrace()();
//			errors=handleDelegateException( e );
//		}
//		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		//System.out.println("size****************"+oneTimeHashMap.size());
    	ListBillingEntriesForm form=(ListBillingEntriesForm)invocationContext.screenModel;
    	if(errors!=null && errors.size()>0){
    	invocationContext.addAllError(errors);
    	}
    	form.setScreenStatus("N");
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }

}
