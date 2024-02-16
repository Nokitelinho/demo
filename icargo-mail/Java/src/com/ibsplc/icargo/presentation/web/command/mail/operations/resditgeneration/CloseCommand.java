/* CloseCommand.java Created on Oct 08, 2010
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.resditgeneration;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ResditGenerationSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Ruby Abraham
 * Command class for closing the ResditGeneration screen
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1			Oct 08,2010	  Ruby Abraham				Initial draft
 *
 */
public class CloseCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	private static final String CLASS_NAME = "CloseCommand";
	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.resditgeneration";
	//Target String for going to MailbagEnquiry screen
	private static final String CLOSERESDITGEN_SUCCESS = "closeresditgen_success";
	
		
	private static final String CLOSE_SUCCESS= "close_success";
	
	
	
		/** 
	 * The execute method for CloseCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(
	 * com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */ 
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	//ResditGenerationForm resditGenerationForm =(ResditGenerationForm) invocationContext.screenModel;
   
    	ResditGenerationSession resditGenerationSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);    
    	resditGenerationSession.removeAllAttributes();
    	/*if(("frommailbagenquiry").equals(resditGenerationForm.getCloseFlag())){ 
    		//resditGenerationForm.setFromScreenFlag("fromenquiry");
    		invocationContext.target = CLOSERESDITGEN_SUCCESS;    		
    	}    	
    	else{ */      	
    		invocationContext.target = CLOSE_SUCCESS;      	
    	log.log(Log.FINE, "target", invocationContext.target);
		log.exiting(CLASS_NAME, "execute");
    }
    	
    
}

	
	
	
