/*
 * DeleteDespatchCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class DeleteDespatchCommand extends BaseCommand {
	
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
    	
    	log.entering("DeleteDespatchCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
    	
    	Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
    	String[] primaryKey = mailArrivalForm.getSelectDespatch();
  	    int cnt=0;
  	    int count = 1;
  	    int errorFlag = 0;
        String bags = "";
        int primaryKeyLen = primaryKey.length;
        Collection<DespatchDetailsVO> newDespatchVOs = new ArrayList<DespatchDetailsVO>();
        if(despatchDetailsVOs != null && despatchDetailsVOs.size() != 0) {
        	for (DespatchDetailsVO despatchVO : despatchDetailsVOs) {
        		String primaryKeyFromVO = String.valueOf(count);
        		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
        				          equalsIgnoreCase(primaryKey[cnt].trim())) {
        			if(!"I".equals(despatchVO.getOperationalFlag())){
        				newDespatchVOs.add(despatchVO);
        				
        				errorFlag = 1;
        				String mlbag = new StringBuilder().append(despatchVO.getOriginOfficeOfExchange())
		           					.append(despatchVO.getDestinationOfficeOfExchange())
		           					//added by anitha
		           					//.append(despatchVO.getMailClass())
		           					.append(despatchVO.getMailCategoryCode())
		           					.append(despatchVO.getMailSubclass())
		           					//.append(despatchVO.getMailClass())
		           					.append(despatchVO.getDsn())
		           					.append(despatchVO.getYear()).toString();
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
        			newDespatchVOs.add(despatchVO);
        		}
        		count++;
        	  }
        	}
        containerDetailsVO.setDesptachDetailsVOs(newDespatchVOs);
        mailArrivalSession.setContainerDetailsVO(containerDetailsVO);
        
        if(errorFlag == 1){
   	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.cannotdelete",new Object[]{bags}));
        }
        
		invocationContext.target = TARGET;
    	log.exiting("DeleteDespatchCommand","execute");
    	
    }
   
}
