/*
 * ScreenLoadCommand.java Created on Aug 8,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listinterlinebillingentries;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */
public class ScreenLoadCommand extends BaseCommand {
	private static final String MODULE = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String CLASS_NAME = "ScreenLoadCommand";
	private static final String BLANK = "";
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";
	Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException{

		
		log.entering(CLASS_NAME, "execute");
		
		
		ListInterlineBillingEntriesForm form 	= 
			(ListInterlineBillingEntriesForm)invocationContext.screenModel;
		
		ListInterlineBillingEntriesSession session = (ListInterlineBillingEntriesSession) getScreenSession(
 				MODULE, SCREEN_ID);
		Map<String, String> systemParameterValues = null;
		
 		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
 		Collection<String> parameterTypes = new ArrayList<String>();
 		parameterTypes.add("mra.airlinebilling.billingstatus");
 		parameterTypes.add("mailtracking.mra.billingtype");
 		parameterTypes.add(KEY_CATEGORY_ONETIME);
 		parameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
 		ApplicationSessionImpl applicationSession = getApplicationSession();
 		LogonAttributes logonAttributes = applicationSession.getLogonVO();
 		String companyCode = logonAttributes.getCompanyCode();
 		//session.setFromScreen("fromInterLineBilling");
 		//Added by A-7540 for ICRD-236156
 		try {
			/** getting collections of OneTimeVOs */
			systemParameterValues=sharedDefaultsDelegate.findSystemParameterByCodes(getSystemParameterTypes());
		} catch (BusinessDelegateException e) {
			handleDelegateException( e );
		}
 		
 		try {
 			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
 					companyCode, parameterTypes);
 			log.log(Log.FINE, " One Time Values---->>" + oneTimeValues);

 		} catch (BusinessDelegateException e) {
 			handleDelegateException(e);

 		}
 		session.setOneTimeMap((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
 		form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
 		form.setDisplayPage("1");
 		session.setFromScreen(BLANK);
 		session.setCloseFlag(BLANK);
 		session.removeDocumentBillingDetailsVO();
 		session.removeAirlineBillingFilterVO();
 		session.removeDocumentBillingDetailVOs();
 		session.setSystemparametres((HashMap<String, String>)systemParameterValues);
    	invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}
    /**
	 * 
	 * 	Method		:	ScreenLoadCommand.getSystemParameterTypes
	 *	Added by 	:	A-7540 on 08-Jun-2017
	 * 	Used for 	:   ICRD-236156
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<String>
	 */
    private Collection<String> getSystemParameterTypes(){
    	log.entering("RefreshCommand", "getSystemParameterTypes");
    	ArrayList<String> systemparameterTypes = new ArrayList<String>();

    	systemparameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
    	return systemparameterTypes;
	}
}

