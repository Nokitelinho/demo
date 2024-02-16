/*
 * ViewMailBagCommand.java Created on July 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ViewMailBagCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILOPERATONS");
		
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
	   
	   private static final String TARGET_SUCCESS = "viewmails_success";
	   private static final String TARGET_FAILURE = "viewmails_failure";
	
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ViewMailBagCommand","execute");
	    	Collection<ErrorVO> errors = null;
	    	
	    	AssignContainerForm assignContainerForm = 
	    					(AssignContainerForm)invocationContext.screenModel;
	    	AssignContainerSession assignContainerSession = 
	    								getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	Collection<ContainerVO> containerVOs = assignContainerSession.getContainerVOs();
	    	Collection<ContainerVO> selectedContainerVOs = new ArrayList<ContainerVO>();
	    	
	    	String[] selectedRows = assignContainerForm.getSubCheck();    	
	    	int row = 0;
	    	for (ContainerVO containerVO : containerVOs) {
	    		if (row == Integer.parseInt(selectedRows[0])) {
	    			selectedContainerVOs.add(containerVO);
				}
	    		row++;
	    	}
	    	log.log(Log.FINE, "selectedContainerVOs --------->>",
					selectedContainerVOs);
			log.log(Log.FINE, "Selected ContainerVOs ------------> ",
					selectedContainerVOs);
			// VALIDATE WHETHER ANY UNSAVED CONTAINERS PRESENT--added by anitha--START
	    	for (ContainerVO selectedvo : selectedContainerVOs) {
	    		if (ContainerVO.OPERATION_FLAG_INSERT.equals(selectedvo.getOperationFlag())) {	    			
	    			errors = new ArrayList<ErrorVO>();
	    			ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.containersNotSaved");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = TARGET_FAILURE;
					return;	    			
	    		}
	    	}
	    	// VALIDATE WHETHER ANY UNSAVED CONTAINERS PRESENT--added by anitha--END
	    	 	
	    	assignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
	    	
	    	assignContainerForm.setStatus("viewmail");
	    	
	    	invocationContext.target = TARGET_SUCCESS;
	       	
	    	log.exiting("ViewMailBagCommand","execute");

	}

}
