/*
 * NextArriveMailCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class NextArriveMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("NextArriveMailCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	int displayPage = Integer.parseInt(mailArrivalForm.getSuggestContainerValue());
        Collection<ContainerDetailsVO> addContainerDetailsVOs = mailArrivalSession.getContainerDetailsVOs();
        ContainerDetailsVO addContainerDetailsVO = ((ArrayList<ContainerDetailsVO>)addContainerDetailsVOs).get(displayPage);
        mailArrivalSession.setContainerDetailsVO(addContainerDetailsVO);
        mailArrivalSession.setContainerDetailsVOs(addContainerDetailsVOs);
        mailArrivalForm.setContainerNo(addContainerDetailsVO.getContainerNumber());
   		mailArrivalForm.setDisableFlag(FLAG_YES);

   		invocationContext.target = TARGET;
       	
    	log.exiting("NextArriveMailCommand","execute");
    	
    }
       
}
