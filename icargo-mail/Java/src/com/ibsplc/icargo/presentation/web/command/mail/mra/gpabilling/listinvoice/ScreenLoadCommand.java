/*
 * ScreenLoadCommand.java Created on June 4, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
/**
 * @author A-3434
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListGPABillingInvoice ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";
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
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	
    	 ListGPABillingInvoiceForm form=( ListGPABillingInvoiceForm)invocationContext.screenModel;
   	
 		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
 		ListGPABillingInvoiceSession session = (ListGPABillingInvoiceSession) getScreenSession(
 				MODULE_NAME, SCREEN_ID);
 		
 		
 		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
 		Map<String, String> systemParameterValues = null;//Added as part of ICRD-189966
 		Map<String,String> invStaMap= new HashMap<String,String>();
 		Collection<String> parameterTypes = new ArrayList<String>();
 		parameterTypes.add("mra.gpabilling.invoicestatus");
 		parameterTypes.add("mailtracking.mra.gpabilling.paymentstatus");
 		
 		ApplicationSessionImpl applicationSession = getApplicationSession();
 		LogonAttributes logonAttributes = applicationSession.getLogonVO();
 		String companyCode = logonAttributes.getCompanyCode();
 		
 		
 		try {
 			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
 					companyCode, parameterTypes);
 			log.log(Log.FINE, " One Time Values---->>", oneTimeValues);
 			systemParameterValues=sharedDefaultsDelegate.
 					findSystemParameterByCodes(getSystemParameterTypes());//Added as part of ICRD-189966

 		} catch (BusinessDelegateException e) {
 			handleDelegateException(e);

 		}
 		
 		
		Collection<OneTimeVO> invStatusCollection = oneTimeValues.get("mra.gpabilling.invoicestatus");
		Collection<OneTimeVO> paymentStatusCollection = oneTimeValues.get("mailtracking.mra.gpabilling.paymentstatus");
		
		for(OneTimeVO invOneTimeVO : invStatusCollection){
			invStaMap.put(invOneTimeVO.getFieldValue(), invOneTimeVO.getFieldDescription());
		}
		
		for(OneTimeVO paymentOneTimeVO : paymentStatusCollection){
		if(!invStaMap.containsKey(paymentOneTimeVO.getFieldValue())){
			invStatusCollection.add(paymentOneTimeVO);
		}
		}
		
		
		//Added as part of ICRD-189966 starts
 		session.setSystemparametres((HashMap<String, String>)systemParameterValues);
		//Added as part of ICRD-189966 ends
 		session.setOneTimeMap((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
 		session.removeCN51SummaryVOs();
 		session.removeCN51SummaryFilterVO();
 		form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }
    /**
     * Added as part of ICRD-189966
     * @return systemparameterTypes
     */
    private Collection<String> getSystemParameterTypes(){
    	log.entering("RefreshCommand", "getSystemParameterTypes");
    	ArrayList<String> systemparameterTypes = new ArrayList<String>();
    	systemparameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
    	return systemparameterTypes;
      }

}
