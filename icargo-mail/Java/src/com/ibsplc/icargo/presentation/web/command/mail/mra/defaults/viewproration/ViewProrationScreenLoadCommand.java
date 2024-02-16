/*
 * ViewProrationScreenLoadCommand.java Created on Aug 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewproration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 * @author A-2554
 *
 */
public class ViewProrationScreenLoadCommand  extends BaseCommand{


	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";
	private static final String SCREEN_SUCCESS = "screenload_success";
	private static final String DSNPOPUP_SCREENID ="mailtracking.mra.defaults.dsnselectpopup";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String SCREENID_VIEW_PRORATION ="mailtracking.mra.defaults.viewproration";
	private static final String MODULE_BILLING_ENTRIES = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID_BILLING_ENTRIES = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
	/**
	 * Added by A-6991 for ICRD-208114
	 * Parameter code for system parameter -Override Rounding value in MRA
	 */
	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		 DSNPopUpSession dSNPopUpSession = getScreenSession(MODULE_NAME,DSNPOPUP_SCREENID);
		 RateAuditDetailsSession rateAuditSession=getScreenSession(MODULE_NAME,SCREENID);
		MRAViewProrationSession session = getScreenSession(MODULE_NAME,SCREENID_VIEW_PRORATION);
		//Added by A-7794 as part of MRA revamp issue fix
		ListInterlineBillingEntriesSession billingEntriesSession = getScreenSession(MODULE_BILLING_ENTRIES,SCREEN_ID_BILLING_ENTRIES);
		//Added by A-6991
				Map<String, String> systemParameterValues = null;
				try {
					/** getting collections of OneTimeVOs */
					systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
				} catch (BusinessDelegateException e) {
					handleDelegateException( e );
				}
		log.entering(CLASS_NAME,"execute");		
		session.setProrationVOs(null);
		session.setPrimaryProrationVOs(null);
		session.setSecondaryProrationVOs(null);
		session.setSystemparametres((HashMap<String, String>)systemParameterValues);
		//Added by A-7794 as part of MRA revamp issue fix
		billingEntriesSession.setSystemparametres((HashMap<String, String>)systemParameterValues);
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	/**
	 * 
	 * 	Method		:	ViewProrationScreenLoadCommand.getSystemParameterTypes
	 *	Added by 	:	A-6991 on 08-Jun-2017
	 * 	Used for 	:   ICRD-208114
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

