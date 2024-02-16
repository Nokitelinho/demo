/*
 * ReassignContainerCommand.java Created on Jul 1 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.InventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.InventoryListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.InventoryListForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ReassignContainerCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
      
   private static final String MODULE_NAME = "mailtracking.defaults";	
   private static final String SCREEN_ID = "mailtracking.defaults.inventorylist";
   private static final String REASSIGN_SCREEN_ID = "mailtracking.defaults.reassignContainer";    
   private static final String CONST_SHOW_REASSIGN = "showReassignPopup";   
   private static final String TARGET_SUCCESS = "reassign_container_success"; 
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ReassignContainerCommand","execute");
    	  
    	InventoryListForm inventoryListForm = 
    		(InventoryListForm)invocationContext.screenModel;
    	InventoryListSession inventoryListSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();		

		Collection<ErrorVO> errors = null;    	
    	
    	ReassignContainerSession reassignContainerSession = 
    		(ReassignContainerSession)getScreenSession(MODULE_NAME,REASSIGN_SCREEN_ID);    	
    	reassignContainerSession.setSelectedContainerVOs(null);    	
    	Collection<ContainerVO> selectedContainerVOs = new ArrayList<ContainerVO>();
    	//Collection<ContainerVO> containerVOs = ;
    	
    	InventoryListVO listvo = inventoryListSession.getInventoryListVO();
    	String[] selectedRows = inventoryListForm.getSelectContainer();  	
    	int size = selectedRows.length;
    	int row = 0;    	
		for (int j = 0; j < size; j++) {
			log.log(Log.FINE, "Selected ContainerVOs ------------> ",
					selectedRows, j);
			ContainerInInventoryListVO contvo = ((ArrayList<ContainerInInventoryListVO>)listvo.getContainerInInventoryList()).get(Integer.parseInt(selectedRows[j]));
			ContainerVO containerVO=new ContainerVO();
			containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
			containerVO.setCarrierCode(inventoryListForm.getCarrierCode());
			
    		containerVO.setCarrierId(Integer.parseInt(inventoryListForm.getCarrierID()));
    		containerVO.setCompanyCode(logonAttributes.getCompanyCode());
    		containerVO.setFlightNumber("-1");
    		containerVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
    		containerVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
    		containerVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
    		containerVO.setContainerNumber(contvo.getUldNumber());
    		containerVO.setAssignedPort(inventoryListForm.getDepPort());
    		containerVO.setType(contvo.getContainerType());		
    		selectedContainerVOs.add(containerVO);
		}   	
    	
    	log.log(Log.FINE, "Selected ContainerVOs ------------> ",
				selectedContainerVOs);
		reassignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
    	reassignContainerSession.setContainerVO(new ContainerVO());
    	inventoryListForm.setStatusFlag(CONST_SHOW_REASSIGN);    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("ReassignContainerCommand","execute");    	
    }
}
