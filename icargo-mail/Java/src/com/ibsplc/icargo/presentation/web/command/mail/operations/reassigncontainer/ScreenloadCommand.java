/*
 * ScreenloadCommand.java Created on June 22, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassigncontainer;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1861
 *
 */
public class ScreenloadCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.reassignContainer";

   private static final String TARGET_SUCCESS = "screenload_success";
       
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadCommand","execute");
    	  
    	ReassignContainerForm reassignContainerForm = 
    		(ReassignContainerForm)invocationContext.screenModel;
    	ReassignContainerSession reassignContainerSession = 
    		(ReassignContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	
    	Collection<ContainerVO> containerVOs = reassignContainerSession.getSelectedContainerVOs();
    	log.log(Log.FINE, "Selected ContainerVOs ------------> ", containerVOs);
		for (ContainerVO containervo : containerVOs) {
    		reassignContainerForm.setDestination(containervo.getFinalDestination());
    		break;
    	}
    	
    	if("FLIGHT".equals(reassignContainerForm.getHideRadio())){
    		reassignContainerForm.setReassignedto("DESTINATION");
		}else if("CARRIER".equals(reassignContainerForm.getHideRadio())){
			reassignContainerForm.setReassignedto( "FLIGHT");
		}else{
			reassignContainerForm.setHideRadio("NONE");
			reassignContainerForm.setReassignedto( "FLIGHT");
		}
    	
    	LocalDate dat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		String date = dat.toDisplayDateOnlyFormat();
		String time = dat.toDisplayFormat("HH:mm");		
		reassignContainerForm.setScanDate(date);
		reassignContainerForm.setMailScanTime(time);
    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("ScreenloadCommand","execute");
    	
    }
    
}
