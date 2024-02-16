/*
 * UnassignEmptyULDsCommand.java
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.emptyulds;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.EmptyULDsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.EmptyULDsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class UnassignEmptyULDsCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILOPERATIONS,UnAssignEmptyULDs");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET_SUCCESS = "unassign_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.emptyulds";
	    
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("UnAssignEmptyULDs","execute");
	    	  
	    	EmptyULDsForm emptyULDsForm = 
	    						(EmptyULDsForm)invocationContext.screenModel;
	    	EmptyULDsSession emptyULDsSession = 
	    							getScreenSession(MODULE_NAME,SCREEN_ID);
	    	Collection<ErrorVO> errors = null;
	    	
	    	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    	
	    	Collection<ContainerDetailsVO> containerDetailsVOs = emptyULDsSession.getContainerDetailsVOs();
	    	Collection<ContainerDetailsVO> selectedContainers = new ArrayList<ContainerDetailsVO>();
	    	
	    	String[] selectedULDs = emptyULDsForm.getSelectULD();
	    	
	    	int index = 0;
	    	
	    	for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs){
	    		for(int i=0;i<selectedULDs.length;i++){
	    			if(index == Integer.parseInt(selectedULDs[i])){
	    				selectedContainers.add(containerDetailsVO);
	    			}
	    		}
	    		index++;
	    	}
	    	
	    	try{
	    		delegate.unassignEmptyULDs(selectedContainers);
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    	}

	    	emptyULDsSession.setContainerDetailsVOs(null);
	    	emptyULDsForm.setStatus("CLOSE");
	    	invocationContext.target = TARGET_SUCCESS;

	    }

}
