/*
 * DeleteMailBagsCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class DeleteMailBagsCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeleteMailBagsCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();
    	
    	Collection<MailbagVO> mailDetailsVOs = containerDetailsVO.getMailDetails();
    	String[] primaryKey = mailAcceptanceForm.getSelectMailTag();
  	    int cnt=0;
  	    int count = 1;
  	    int errorFlag = 0;
        String bags = "";
        int primaryKeyLen = primaryKey.length;
        Collection<MailbagVO> newMailbagVOs = new ArrayList<MailbagVO>();
        if(mailDetailsVOs != null && mailDetailsVOs.size() != 0) {
        	for (MailbagVO mailbagVO : mailDetailsVOs) {
        		String primaryKeyFromVO = String.valueOf(count);
        		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
        				          equalsIgnoreCase(primaryKey[cnt].trim())) {
        			if(!"I".equals(mailbagVO.getOperationalFlag())){
        				newMailbagVOs.add(mailbagVO);
        				
        				errorFlag = 1;
        				String mlbag = new StringBuilder().append(mailbagVO.getMailbagId()).toString();
           				if("".equals(bags)){
           					bags = mlbag;
    	       			}else{
    	       				bags = new StringBuilder(bags)
       					            .append(",")
       					            .append(mlbag)
       					            .toString();	
    	       			}
        				
        			}
        			cnt++;
        		}else{
        			newMailbagVOs.add(mailbagVO);
        		}
        		count++;
        	  }
        	}
        containerDetailsVO.setMailDetails(newMailbagVOs);
        mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);
        
        if(errorFlag == 1){
   	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.cannotdelete",new Object[]{bags}));
        }
        
    	invocationContext.target = TARGET;
    	log.exiting("DeleteMailBagsCommand","execute");
    }
       
}
