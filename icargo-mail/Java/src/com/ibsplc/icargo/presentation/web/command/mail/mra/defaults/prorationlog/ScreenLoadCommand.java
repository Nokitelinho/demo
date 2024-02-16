	/*
	 * ScreenLoadCommand.java Created on Sep 17, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.prorationlog;


	import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProrationLogSession;
	import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProrationLogForm;
	import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * Command class for screenload of proration log screen  
	 *
	 * Revision History
	 *
	 * Version      Date           Author          		    Description
	 *
	 *  0.1        Sep 17,2008     A-3229					Initial draft
	 */
	public class ScreenLoadCommand extends BaseCommand {

		private Log log = LogFactory.getLogger("Proration Log ScreenloadCommand");

		private static final String CLASS_NAME = "ScreenLoadCommand";

		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.prorationlog";
		
		/*
		 * Target mappings for succes 
		 */
		private static final String ACTION_SUCCESS = "screenload_success";
		
		/**
		 * @param invocationContext
		 * @exception CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {

	    	log.entering(CLASS_NAME, "execute");
	    	
	    	ProrationLogSession prorationLogSession=getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	MRAProrationLogForm form=(MRAProrationLogForm)invocationContext.screenModel;
	   	
	    	form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	
	    	//To clear session on screenload
	    	prorationLogSession.removeDSNFilterVO();
	    	prorationLogSession.removeMailProrationLogVOMap();
	    	prorationLogSession.removeSelectedDespatchDetails();
	    	prorationLogSession.removeTriggerPoints();
	     
	    	invocationContext.target = ACTION_SUCCESS;
	    	
			log.exiting(CLASS_NAME, "execute");
	    }

	}


	

