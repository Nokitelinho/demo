/*
 * ScreenLoadCommand.java Created on Dec 15, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("GPABillingEntries ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";
	//private static final String KEY_BILLING_TYPE_ONETIME = "mra.gpabilling.gpabillingstatus";
	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	
	/*
	 * Parameter code for system parameter - level for data import to mra
	 */
	private static final String LEVEL_FOR_DATA_IMPORT_TO_MRA="mailtracking.defaults.DsnLevelImportToMRA";
	/**
	 * Added as part of ICRD-189966
	 * Parameter code for system parameter -Override Rounding value in MRA
	 */
	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";
	//private static final String ACTION_FAILURE = "screenload_failure";
	private static final String PRVILIGE_CODE_AUTOMCA="mailtracking.mra.defaults.automcaprivilege"; //Added by A-7929 as part od icrd-132548
	private static final String USPS_PERFORMED="mailtracking.mra.gpabilling.uspsperformed"; //Added by A-7871 for ICRD-232381
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	GPABillingEntriesSession session=null;
   		session=(GPABillingEntriesSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		 
   		//Added by A-7929 as part of ICRD-132548
   		boolean hasAutoMCAPrivilege = false ;
   		try {
   			SecurityAgent agent = SecurityAgent.getInstance();
			 hasAutoMCAPrivilege = agent.checkPrivilegeForAction(PRVILIGE_CODE_AUTOMCA);
		} catch (SystemException e1) {
   			e1.getMessage();
		}  
   		
   		//session.removeAllAttributes();
   		session.removeGpaBillingDetails();
   		session.removeSelectedRows();
   		Map oneTimeHashMap 								= null;
   		Map<String, String> systemParameterValues = null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_BILLING_TYPE_ONETIME);
		oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		oneTimeActiveStatusList.add(USPS_PERFORMED); //Added by A-7871 for ICRD-232381
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		
		
		
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		session.setSystemparametres((HashMap<String, String>)systemParameterValues);
		
		//System.out.println("size****************"+oneTimeHashMap.size());
    	GPABillingEntriesForm form=(GPABillingEntriesForm)invocationContext.screenModel;
    	if(errors!=null && errors.size()>0){
    	invocationContext.addAllError(errors);
    	}
    	
    	for (Map.Entry<String, String> entry : systemParameterValues.entrySet()) { 
    		form.setParameterValue(entry.getValue());
    		
    		}
    	form.setScreenStatus("screenload");
   //Added by A-6991 for ICRD-139019 Starts
    	form.setContractRate(OneTimeVO.FLAG_YES);
    	form.setUPURate(OneTimeVO.FLAG_YES);
    	if(hasAutoMCAPrivilege){
    	form.setHasPrivilege("YES"); //Added by A-7929 as part if ICRD-132548
    	}
    	else{
    	form.setHasPrivilege("NO");	
    	}
   //Added by A-6991 for ICRD-139019 Starts
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }
    private Collection<String> getSystemParameterTypes(){
    	log.entering("RefreshCommand", "getSystemParameterTypes");
    	ArrayList<String> systemparameterTypes = new ArrayList<String>();
    	
    	systemparameterTypes.add(LEVEL_FOR_DATA_IMPORT_TO_MRA);
    	systemparameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
    	return systemparameterTypes;
      }
}
