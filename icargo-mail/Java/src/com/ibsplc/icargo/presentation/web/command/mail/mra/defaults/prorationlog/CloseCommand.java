	/*
	 * CloseCommand.java Created on Sep 17, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.prorationlog;

	import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
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

		private static final String SCREENID = "mailtracking.mra.defaults.prorationlog";

		private static final String CLASS_NAME = "CloseCommand";
		
		private static final String CLOSE_SUCCESS = "close_success";
		/**
		 * Method  implementing closing of screen
		 * @author a-3229
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {

	    	log.entering(CLASS_NAME,"execute");

	    	invocationContext.target = CLOSE_SUCCESS;
	    	}


	    }









