/*
 * ScreenloadCommand.java Created on Feb 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.processmanager;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProcessManagerSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-2338
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Feb 15, 2007				A-2258		Created
 */
public class ScreenloadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");
	
	private static final String CLASS_NAME = "ScreenloadCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.processmanager";
	
	private static final String PROCESS_MGR = "mailtracking.mra.defaults.processmanager";
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	//Added by A-7531 for ICRD-132508 
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	//Added by A-7531 for ICRD-132508
	private static final String KEY_SUBCLASS_ONETIME = "mailtracking.defaults.mailsubclassgroup";
	//Added by A-7531 for ICRD-132487
	private static final String KEY_PRORATION_EXCEPTION_ONETIME = "mra.proration.exceptions";
	
	private static final String KEY_FILTERMODE_ONETIME = "mailtracking.mra.defaults.importmailfiltermode";
	
	
	
	
	
	/**
	 * @author A-2270
	 * Jun 20, 2007
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		String companyCode = null;
		Collection<ErrorVO> errors = null;
		Map<String, Collection<OneTimeVO>>oneTimeValues = null;
		  ProcessManagerSession processManagerSession = 
				(ProcessManagerSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		
		companyCode = logonAttributes.getCompanyCode();
		Collection<String> oneTimeList = new ArrayList<>();
		oneTimeList.add(PROCESS_MGR);
		//Added by A-7531 for ICRD-132508 starts
		oneTimeList.add(KEY_CATEGORY_ONETIME);
        oneTimeList.add(KEY_SUBCLASS_ONETIME);
      //Added by A-7531 for ICRD-132508 ends
        //Added by A-7531 for ICRD-132487
        oneTimeList.add(KEY_PRORATION_EXCEPTION_ONETIME);
        oneTimeList.add(KEY_FILTERMODE_ONETIME);
        
        
		try {
			oneTimeValues = new SharedDefaultsDelegate()
									.findOneTimeValues(companyCode, oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		//Added by A-7531 for ICRD-132508 starts
		if(oneTimeValues != null){
			processManagerSession.setMailCategory( 
					(ArrayList<OneTimeVO>)oneTimeValues.get(KEY_CATEGORY_ONETIME));
		}
		if(oneTimeValues != null){
			processManagerSession.setMailSubclass( 
					(ArrayList<OneTimeVO>)oneTimeValues.get(KEY_SUBCLASS_ONETIME));
		}
		//Added by A-7531 for ICRD-132508 ends
		if(oneTimeValues != null){
			processManagerSession.setProcesses(
					(ArrayList<OneTimeVO>)oneTimeValues.get(PROCESS_MGR));
		}
		//Added by A-7531 for ICRD-132487
		if(oneTimeValues != null){
			processManagerSession.setException(
					(ArrayList<OneTimeVO>)oneTimeValues.get(KEY_PRORATION_EXCEPTION_ONETIME));
		}
		
		if(oneTimeValues != null){
			processManagerSession.setFilterMode(
					(ArrayList<OneTimeVO>)oneTimeValues.get(KEY_FILTERMODE_ONETIME));
		}
		if ( errors != null &&! errors.isEmpty() ) {
			invocationContext.addAllError( errors );
		}
		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}
}
