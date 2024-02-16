/*
 * CancelReturnMailCommand.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returnmail;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnMailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class CancelReturnMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "cancel_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.returnmail";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("CancelDamageCommand","execute");
    	  
    	ReturnMailForm returnMailForm = 
    		(ReturnMailForm)invocationContext.screenModel;
    	ReturnMailSession returnMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	returnMailSession.setDamagedMailbagVOs(null);
    	returnMailForm.setSelectedContainers("CLOSE");
    	
    	invocationContext.target = TARGET;
    	
       	log.exiting("CancelDamageCommand","execute");
    	
    }
                  
}
