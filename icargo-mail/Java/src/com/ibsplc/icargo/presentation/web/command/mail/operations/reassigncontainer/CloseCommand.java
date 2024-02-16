/*
 * CloseCommand.java Created on June 22, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassigncontainer;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1861
 *
 */
public class CloseCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.reassignContainer";
   private static final String SCREEN_EXPORTMAI_ID = "mailtracking.defaults.mailexportlist";

   private static final String TARGET_SUCCESS = "close_success";
   private static final String SCREEN_EXPORTMAIL="MAILEXPORTLIST";
       
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("CloseCommand","execute");
    	  
    	ReassignContainerForm reassignContainerForm = 
    		(ReassignContainerForm)invocationContext.screenModel;
    	ReassignContainerSession reassignContainerSession = 
    		(ReassignContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	MailExportListSession mailExportListSession = 
    		(MailExportListSession)getScreenSession(MODULE_NAME, SCREEN_EXPORTMAI_ID);
    	OperationalFlightVO operationalFlightVO=null;
    	if(SCREEN_EXPORTMAIL.equals(reassignContainerForm.getFromScreen())){
        	if(mailExportListSession.getOperationalFlightVO()!=null){
        		operationalFlightVO=mailExportListSession.getOperationalFlightVO();
        	}
	        if(operationalFlightVO!=null){
	    		reassignContainerForm.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
	    		reassignContainerForm.setFlightNumber(operationalFlightVO.getFlightNumber());
	    		reassignContainerForm.setFlightDate(String.valueOf(operationalFlightVO.getFlightDate()));
	        }
    	}

    	reassignContainerSession.removeAllAttributes();
    	reassignContainerForm.setStatus("close");
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("CloseCommand","execute");
    	
    }
    
}
