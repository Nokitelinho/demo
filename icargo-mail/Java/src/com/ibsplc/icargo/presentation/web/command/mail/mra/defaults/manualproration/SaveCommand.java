
	/*
	 * SaveCommand.java Created on Aug 10, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.manualproration;


	import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ManualProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ManualProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * @author A-3229
	 *
	 */



	public class SaveCommand extends BaseCommand {
		private  Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS "); 
		
		private static final String SAVE_SUCCESS = "save_success";	
		private static final String SAVE_FAILURE = "save_failure";
		private static final String SCREEN_ID = "mailtracking.mra.defaults.manualproration";		
		private static final String MODULE_NAME = "mailtracking.mra.defaults";

		
	    /**
	     * @param invocationContext
	     * @throws CommandInvocationException
	     */
		public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	

 			log.entering("Save Manual Proration Details","execute");
 			LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
	    	
	    	ManualProrationForm  manualProrationForm = (ManualProrationForm)invocationContext.screenModel;
	    	ManualProrationSession manualProrationSession = getScreenSession(MODULE_NAME,SCREEN_ID); 

	    	Collection<ProrationDetailsVO> prorationDetailsVOs = null;
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			
	    	prorationDetailsVOs = (ArrayList<ProrationDetailsVO>)manualProrationSession.getProrationDetailVOs();
	    	
			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			try {
				for(ProrationDetailsVO vo:prorationDetailsVOs){
					vo.setLastUpdateUser(logonAttributes.getUserId());
					log.log(Log.INFO, "proration vos IN SAVE COMMAND ", vo);
				}
				mailTrackingMRADelegate.saveProrationDetails(prorationDetailsVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
			}
			if(errors != null && errors.size() > 0) {			
				log.log(Log.FINE, "ERRORS FOUND");
				manualProrationSession.removeProrationFilterVO();
				manualProrationSession.removeProrationDetailVOs();
				manualProrationForm.setProrateFlag("N");
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
			else{
				errors = new ArrayList<ErrorVO>();
				errors.add(new ErrorVO("mailtracking.mra.defaults.manualproration.msg.info.datasavedsuccessfully"));
				invocationContext.addAllError(errors);							
				manualProrationForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = SAVE_SUCCESS;				
				return;
			}
		
		}
		
		
	}


