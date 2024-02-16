
	/*
	 * ClearCommand.java Created on Sep 18,2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.prorationlog;

	import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
	import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProrationLogSession;
	import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProrationLogForm;
	import com.ibsplc.xibase.util.log.Log;
	import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * Command class for clearing manualproration screen  
	 *
	 * Revision History
	 *
	 * Version      Date           Author          		    Description
	 *
	 *  0.1        Sep 17,2008     A-3229					Initial draft
	 */
	public class ClearCommand extends BaseCommand {
		/**
		 * Logger and the file name
		 */
		
		private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS PRORATION LOG");
		
		private static final String CLASS_NAME = "ClearCommand";
		
		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.prorationlog";
		
		
		private static final String CLEAR_SUCCESS = "clear_success";
		

		/**
		 * Execute method
		 *
		 * @param invocationContext InvocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
												throws CommandInvocationException {
			log.entering(CLASS_NAME, "execute");
			
			ProrationLogSession  prorationLogSession = 
				(ProrationLogSession)getScreenSession(MODULE_NAME, SCREEN_ID);
			MRAProrationLogForm mraProrationLogForm=(MRAProrationLogForm)invocationContext.screenModel;

			
			mraProrationLogForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			mraProrationLogForm.setDsn("");
			mraProrationLogForm.setProrateFlag("");
			mraProrationLogForm.setFromScreen("");
			mraProrationLogForm.setShowDsnPopUp("FALSE");
			mraProrationLogForm.setCheckBoxLog(null);
			prorationLogSession.setDSNFilterVO(null);
			prorationLogSession.setMailProrationLogVOMap(null);
			
			
			invocationContext.target = CLEAR_SUCCESS;
			log.exiting(CLASS_NAME, "execute");
		}
	}

	
	



