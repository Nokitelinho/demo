
	/*
	 * ReloadCommand.java Created on Sep 22, 2008
	 *
	 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.prorationlog;

	
	import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
	import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProrationLogSession;
	import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProrationLogForm;
	import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


	/**
	 * @author A-3229
	 *
	 */
	public class ReloadCommand extends BaseCommand {

		private Log log = LogFactory.getLogger("Proration Log");
		private static final String SCREEN_ID = "mailtracking.mra.defaults.prorationlog";
		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		private static final String  RELOADSUCCESS="reload_success";

		/**
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	log.log(Log.FINE,"..Entered Reload command..");
	    	ProrationLogSession session = getScreenSession(MODULE_NAME,
					SCREEN_ID);
	    	MRAProrationLogForm mraProrationLogForm =
	    		(MRAProrationLogForm)invocationContext.screenModel;
	    	
	    	DSNFilterVO dsnFilterVOFromScreen=session.getDSNFilterVO();
	    	
	    	//To set values for filter 
	    	
	    	if(dsnFilterVOFromScreen!=null){
	    		if(dsnFilterVOFromScreen.getDsn()!=null && dsnFilterVOFromScreen.getDsn().trim().length()>0){
	    			mraProrationLogForm.setDsn(dsnFilterVOFromScreen.getDsn());
	    		}
	    		
	    	}
	    	log.log(Log.INFO, "Session------------------", session.getMailProrationLogVOs());
			invocationContext.target=RELOADSUCCESS;
	    	

			}




	    }



