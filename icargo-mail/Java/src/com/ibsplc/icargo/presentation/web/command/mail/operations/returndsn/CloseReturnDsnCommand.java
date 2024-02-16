/*
 * CloseReturnDsnCommand.java Created on July 24, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returndsn;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnDsnSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnDsnForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class CloseReturnDsnCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "close_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.returndsn";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("CloseReturnDsnCommand","execute");
    	  
    	ReturnDsnForm returnDsnForm = 
    		(ReturnDsnForm)invocationContext.screenModel;
    	ReturnDsnSession returnDsnSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	returnDsnSession.setDamagedDsnVOs(null);
    	returnDsnForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);
    	
    	invocationContext.target = TARGET;
    	
       	log.exiting("CloseReturnDsnCommand","execute");
    	
    }
                  
}
