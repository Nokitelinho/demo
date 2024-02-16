/*
 * DeleteRouteCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class DeleteRouteCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.consignment";	
           
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeleteRouteCommand","execute");
  	  
    	ConsignmentForm consignmentForm = 
    		(ConsignmentForm)invocationContext.screenModel;
    	ConsignmentSession consignmentSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	/*Commented by (A-3217) since it is not is used 
    	anywhere in the code except the declaration*/
    	//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	String[] primaryKeyArr = consignmentForm.getSelectRoute();
    	int primaryKeyLen = primaryKeyArr.length;
    	ConsignmentDocumentVO consignmentDocumentVO =consignmentSession.getConsignmentDocumentVO();
    	Collection<RoutingInConsignmentVO> routingInConsignmentVOs = consignmentDocumentVO.getRoutingInConsignmentVOs();
    	
    	int count = 0;
    	int cnt = 0;
    	Collection<RoutingInConsignmentVO> newRoutingVOs = new ArrayList<RoutingInConsignmentVO>();
    	 if(routingInConsignmentVOs != null && routingInConsignmentVOs.size() != 0) {
         	for (RoutingInConsignmentVO routingVO : routingInConsignmentVOs) {
         		String primaryKeyFromVO = String.valueOf(count);
         		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
         				          equalsIgnoreCase(primaryKeyArr[cnt].trim())) {
         			if(!"I".equals(routingVO.getOperationFlag())){
         				routingVO.setOperationFlag("D");
         				newRoutingVOs.add(routingVO);
         			}
         			cnt++;	
         		}else{
         			newRoutingVOs.add(routingVO);
         		}
         		count++;
         	}
    	 }
      
    	 log.log(Log.FINE, "delete...newRoutingVOs...command", newRoutingVOs);
	consignmentDocumentVO.setRoutingInConsignmentVOs(newRoutingVOs);	
       consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);	 
       consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
       invocationContext.target = TARGET;
       	
       log.exiting("DeleteRouteCommand","execute");
    	
    }
       
}
