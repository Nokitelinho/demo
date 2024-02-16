/*
 * DeleteMailCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.inventorylist;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.InventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInInventoryListVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.InventoryListSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.InventoryListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */

public class DeleteMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   private static final String TARGET_SUCCESS = "delete_success";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.inventorylist";	   
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeleteMailCommand","execute");
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	InventoryListForm inventoryListForm = 
    		(InventoryListForm)invocationContext.screenModel;
    	InventoryListSession inventoryListSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();   	
		InventoryListVO inventoryListVO=inventoryListSession.getInventoryListVO();
    	Collection<ContainerInInventoryListVO> containerInInventoryList=inventoryListVO.getContainerInInventoryList();
    	Collection<MailInInventoryListVO> mailInInventoryListVOTmp=new ArrayList<MailInInventoryListVO>();
   	    log.log(Log.FINE, "\n\n reassignMailForm.getContainer()-->",
				inventoryListForm.getSelectedMails());
		String[] childStrs=inventoryListForm.getSelectedMails().split(",");
	    int size=childStrs.length;
	    for(int i=0;i<size;i++){
	    	ContainerInInventoryListVO containerInInventoryListVO = ((ArrayList<ContainerInInventoryListVO>)containerInInventoryList).get(Integer.parseInt((childStrs[i].split("~"))[0]));
			   Collection<MailInInventoryListVO> mailInInventoryListVOs = containerInInventoryListVO.getMailInInventoryList();
			   MailInInventoryListVO mailvo = ((ArrayList<MailInInventoryListVO>)mailInInventoryListVOs).get(Integer.parseInt((childStrs[i].split("~"))[1]));
			   mailvo.setCurrentAirport(logonAttributes.getAirportCode());
			   mailvo.setCarrierCode(inventoryListForm.getCarrierCode());						   
			   mailvo.setCompanyCode(logonAttributes.getCompanyCode());						  
			   mailvo.setAssignedUser(logonAttributes.getUserId());
			   mailvo.setCarrierID(Integer.parseInt(inventoryListForm.getCarrierID()));
			   mailvo.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	           mailInInventoryListVOTmp.add(mailvo);
	  }
	    
	  log.log(Log.FINE, "\n\n mailInInventoryListVOTmp for delete ------->",
			mailInInventoryListVOTmp);
	try {		    
		  new MailTrackingDefaultsDelegate().deleteMailbagsInInventory(mailInInventoryListVOTmp);		  
      }catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
	  }
      invocationContext.target = TARGET_SUCCESS;
      log.exiting("DeleteMailCommand","execute");    	
    }       
}
