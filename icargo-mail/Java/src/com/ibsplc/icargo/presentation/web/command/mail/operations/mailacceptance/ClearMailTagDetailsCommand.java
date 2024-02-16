/*
 * ClearMailTagDetailsCommand Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ClearMailTagDetailsCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "clear_success";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
   
    
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearMailTagDetailsCommand","execute");
        //Modified the whole command as clear was not working 
    	//Modified by A-7540
    	MailAcceptanceSession mailAcceptanceSession = 
        		getScreenSession(MODULE_NAME,SCREEN_ID);
    	MailbagVO mailbagVO = mailAcceptanceSession.getCurrentMailDetail();
    	mailbagVO.setOoe("");
    	mailbagVO.setDoe("");
    	mailbagVO.setCarrierCode("");
    	mailbagVO.setMailCategoryCode("");
    	mailbagVO.setMailSubclass("");
    	mailbagVO.setYear(0); 
    	mailbagVO.setDespatchSerialNumber("");
    	mailbagVO.setReceptacleSerialNumber("");
    	mailbagVO.setHighestNumberedReceptacle("");
    	mailbagVO.setRegisteredOrInsuredIndicator("");
		mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,0));
    	double volume = 0.0D;
     	mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,volume));  
    	mailbagVO.setSealNumber("");
    	mailbagVO.setScannedDate(null);
    	mailbagVO.setBellyCartId("");
		//added as a part of ICRD-197419
    	mailbagVO.setMailRemarks("");
		
		invocationContext.target = TARGET;
       	
    	log.exiting("ClearMailTagDetailsCommand","execute");
    	
    }
       
}
