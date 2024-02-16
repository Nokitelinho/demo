/*
 * MailChangeClearCommand.java Created on Jul 1 2016
 *
 * Copyright 2016 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailchange;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author a-5991
 *
 */
public class MailChangeClearCommand extends BaseCommand {
	
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
    	
    	log.entering("MailChangeClearCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
        		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	MailArrivalVO mailArrivalVO = new MailArrivalVO();
		mailArrivalSession.setFlightMailArrivalVO(mailArrivalVO);
		
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		mailArrivalSession.setChangeFlightValidationVO(flightValidationVO);
		mailArrivalSession.setFlightMailArrivalFilterVO(null);
		mailArrivalForm.setFltCarrierCode("");
		mailArrivalForm.setFltNumber("");
		mailArrivalForm.setArrDate("");
		mailArrivalForm.setDuplicateFlightStatus(FLAG_NO);
		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		mailArrivalForm.setAddLinkFlag(FLAG_NO);
		mailArrivalSession.setContainerVOs(null);
		invocationContext.target = TARGET;
       	
    	log.exiting("MailChangeClearCommand","execute");
    	
    }
       
}
