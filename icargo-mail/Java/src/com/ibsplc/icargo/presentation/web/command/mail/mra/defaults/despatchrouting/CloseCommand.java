
	/*
	 * CloseCommand.java Created on Sep 03, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchrouting;

	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
	import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNRoutingSession;

    import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm;
	import com.ibsplc.xibase.util.log.Log;
    import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * @author A-3229
	 *
	 *
	 */
	public class CloseCommand extends BaseCommand {

		private  Log log = LogFactory.getLogger("MRA DEFAULTS");

		private static final String MODULE = "mailtracking.mra.defaults";

		private static final String SCREENID = "mailtracking.mra.defaults.despatchrouting";

		private static final String CLASS_NAME = "CloseCommand";
		
		private static final String CLOSE_SUCCESS = "close_success";
		private static final String CLOSE_TOPRORATIONEXCEPTION_SUCCESS = "closeprorationexception_success";
		private static final String FROM_DSNROUTING_SCREEN = "dsnrouting";
		private static final String MODULE_GPABILLING = "mailtracking.mra.gpabilling";
		private static final String SCREENID_GPABILLING = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";

		private static final String CLOSE_TO_LISTGPABILLINGENTRIES_SUCCESS = "close_despatchrouting_screen_success";
		private static final String CLOSE_TO_GPABILLINGENQUIRY="close_listgpabilling_success";
		
		/**
		 * Method  implementing closing of screen
		 * @author a-3229
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {

	    	log.entering(CLASS_NAME,"execute");
	    	DespatchRoutingForm despatchRoutingForm=(DespatchRoutingForm)invocationContext.screenModel;
	    	DSNRoutingSession session = (DSNRoutingSession)getScreenSession(MODULE, SCREENID);
			session.removeAllAttributes();
			
			log.log(Log.INFO, "FromScreen...", despatchRoutingForm.getFromScreen());
			if("fromlistproexceptions".equals(despatchRoutingForm.getFromScreen())){
	    		invocationContext.target = CLOSE_TOPRORATIONEXCEPTION_SUCCESS;
	    		return;
	    	} else if("listgpabillingentries".equals(despatchRoutingForm.getFromScreen())){
	    		invocationContext.target = CLOSE_TO_LISTGPABILLINGENTRIES_SUCCESS;
	    		return;
	    	}else if("listgpabillingentriesux".equals(despatchRoutingForm.getFromScreen())){
	    		invocationContext.target=CLOSE_TO_GPABILLINGENQUIRY;
	    		return;
	    	}
	    		else {
	    	
				invocationContext.target = CLOSE_SUCCESS;
			}
	    	}


	    }







