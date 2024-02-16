/*
 * SendResditsScreenloadCommand.java Created on Jul 1 2016
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.CarditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class SendResditsScreenloadCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.carditenquiry";	
   
 
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("SendResditsScreenloadCommand","execute");
    	CarditEnquiryForm carditEnquiryForm = 
    		(CarditEnquiryForm)invocationContext.screenModel;
    	CarditEnquirySession carditEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	CarditEnquiryVO carditEnquiryVO = carditEnquirySession.getCarditEnquiryVO();
    	String selectCont = carditEnquiryForm.getSelCont();
    	String[] selectArr = selectCont.split(",");  
    	Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
    		Collection<ContainerVO> containerCarditVOs = carditEnquiryVO.getContainerVos();
    		for(int sel=0;sel<selectArr.length;sel++){
    			String select=selectArr[sel].split("~")[1];
    			log.log(Log.FINE, "###$#$#select", select);
				containerVOs.add((ContainerVO)((ArrayList<ContainerVO>)containerCarditVOs).get(Integer.parseInt(select)));
    		}
    		log.log(Log.FINE, "################", containerVOs);
			carditEnquirySession.setContainerVOs(containerVOs);    		
    	
    	invocationContext.target = TARGET;
    	log.exiting("SendResditsScreenloadCommand","execute");
    	
    }
    
    
}
