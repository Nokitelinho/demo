/*
 * DeliveryMailCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.inventorylist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.InventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInInventoryListVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.InventoryListSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.InventoryListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class DeliveryMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   private static final String TARGET_SUCCESS = "delivery_success";
   private static final String TARGET_FAILURE = "delivery_failure";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.inventorylist";	   
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeliveryMailCommand","execute");
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	InventoryListForm inventoryListForm = 
    		(InventoryListForm)invocationContext.screenModel;
    	InventoryListSession inventoryListSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();   	
		InventoryListVO inventoryListVO = inventoryListSession.getInventoryListVO();
    	Collection<ContainerInInventoryListVO> containerInInventoryList = inventoryListVO.getContainerInInventoryList();
   	    log.log(Log.FINE, "\n\n reassignMailForm.getContainer()-->",
				inventoryListForm.getSelectedMails());
		Collection<MailInInventoryListVO> newMailInInventoryListVOs = new ArrayList<MailInInventoryListVO>();
   	    Collection<ContainerInInventoryListVO> newContainerInInventoryListVOs = new ArrayList<ContainerInInventoryListVO>();
   	    Collection<MailInInventoryListVO> toDeliverMailInInventoryListVOs = new ArrayList<MailInInventoryListVO>();
   	    String content = inventoryListForm.getSelectMode();
   	    String[] contentArr = content.split("/");
   	    String st=""; 
   	    if("C".equals(contentArr[0])){
   	    	String[] containers = (contentArr[1]).split(",");
   	    	int size = containers.length;
   		    for(int i=0;i<size;i++){
   		    	if (containerInInventoryList != null && containerInInventoryList.size() > 0) {
   		    		ContainerInInventoryListVO contvo =null;
   		    	    if(("Y").equals(contentArr[2])){
   		    	    	st = contentArr[1].split("~")[0];
   		    	    	contvo= ((ArrayList<ContainerInInventoryListVO>)containerInInventoryList).get(Integer.parseInt(st));
   		    	    }
   		    	    else{   		    	    	
   		    	    	contvo= ((ArrayList<ContainerInInventoryListVO>)containerInInventoryList).get(Integer.parseInt(containers[i])); 
   		    	    	if(!"Y".equals(contvo.getPaBuiltFlag())&& (contvo.getMailInInventoryList()==null || contvo.getMailInInventoryList().size()==0)){
   		    	    		invocationContext.addError(new ErrorVO("mailtracking.defaults.inventorylist.cantdeliveremptyulds"));
   		    	 	    	invocationContext.target = TARGET_FAILURE;
   		    	 	    	return;
   		    	    	}
   		    	    }
	   					   Collection<MailInInventoryListVO> mailInInventoryListVOs = contvo.getMailInInventoryList();
	   					   if (mailInInventoryListVOs != null && mailInInventoryListVOs.size() > 0) {
		   					   for(MailInInventoryListVO mailvo:mailInInventoryListVOs){
		   						   mailvo.setCurrentAirport(logonAttributes.getAirportCode());
		   						   mailvo.setCarrierCode(inventoryListForm.getCarrierCode());						   
		   						   mailvo.setCompanyCode(logonAttributes.getCompanyCode());						  
		   						   mailvo.setAssignedUser(logonAttributes.getUserId());
		   						   mailvo.setCarrierID(Integer.parseInt(inventoryListForm.getCarrierID()));
		   						   mailvo.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		   						   
		   						   LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			 					   mailvo.setOperationTime(sd.setDateAndTime(contentArr[3],false));
			 					   mailvo.setScannedUser(logonAttributes.getUserId().toUpperCase());
			 					   
			 					   newMailInInventoryListVOs.add(mailvo);
		   					   }
	   					   }
	   					newContainerInInventoryListVOs.add(contvo);
   			   }
   		   }
   	    }else{
   	    	String[] childStrs = (contentArr[1]).split(",");
   	       int size=childStrs.length;
 	       for(int i=0;i<size;i++){
	 		   if (containerInInventoryList != null && containerInInventoryList.size() > 0) {
	 			  ContainerInInventoryListVO contvo = ((ArrayList<ContainerInInventoryListVO>)containerInInventoryList).get(Integer.parseInt((childStrs[i].split("~"))[0]));	 			
		  		  Collection<MailInInventoryListVO> mailInInventoryListVOs = contvo.getMailInInventoryList();
 				  if (mailInInventoryListVOs != null && mailInInventoryListVOs.size() > 0) {			 				   
					   MailInInventoryListVO mailvo = ((ArrayList<MailInInventoryListVO>)mailInInventoryListVOs).get(Integer.parseInt((childStrs[i].split("~"))[1]));
					   mailvo.setCurrentAirport(logonAttributes.getAirportCode());	 						   
 					   mailvo.setCarrierCode(inventoryListForm.getCarrierCode());						   
 					   mailvo.setCompanyCode(logonAttributes.getCompanyCode());						  
 					   mailvo.setAssignedUser(logonAttributes.getUserId());
 					   mailvo.setCarrierID(Integer.parseInt(inventoryListForm.getCarrierID()));
 					   mailvo.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
 					   mailvo.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
 					   
 					   LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
 					   mailvo.setOperationTime(sd.setDateAndTime(contentArr[3],false));
 					   mailvo.setScannedUser(logonAttributes.getUserId().toUpperCase());
 					   
 					   newMailInInventoryListVOs.add(mailvo);
 					   toDeliverMailInInventoryListVOs.add(mailvo);	 					    				   
 			      }			   
 		       }
 	        }
   	    }
   	  
   	  if (newMailInInventoryListVOs != null && newMailInInventoryListVOs.size() > 0) {
   		  
   		Collection<String> originColl = new ArrayList<String>();
         for (MailInInventoryListVO mailInInventoryListVO : newMailInInventoryListVOs) {
         	if(!originColl.contains(mailInInventoryListVO.getDestinationCity())){
         		originColl.add(mailInInventoryListVO.getDestinationCity());
         	}
         }
         
         Map<String,CityVO> cityMap = new HashMap<String,CityVO>();
         
 	    try {
         	cityMap = new AreaDelegate().validateCityCodes(
         			logonAttributes.getCompanyCode(),originColl);
         }catch (BusinessDelegateException businessDelegateException) {
         	handleDelegateException(businessDelegateException);
 			log.log(Log.INFO,"ERROR--SERVER------validateCityCodes---->>");
 	  	}
         
         int errorFlag = 0;
         String airport = logonAttributes.getAirportCode();
         if(cityMap != null && cityMap.size() > 0){
         	for(String Key:cityMap.keySet()){
         		CityVO cityVO = cityMap.get(Key);
       			if(!airport.equals(cityVO.getNearestAirport())){
       				errorFlag = 1;
       				break;
         		}
         	}
         }
   		  
         if(errorFlag == 1){
 	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.inventorylist.differdestinationairport"));
 	    	invocationContext.target = TARGET_FAILURE;
 	    	return;
         }
         
//         else{
//        	 if(!"Y".equals(inventoryListForm.getDeliverWarning())){
//	        	 invocationContext.addError(new ErrorVO("mailtracking.defaults.inventorylist.wanttodeliver"));
//	  	    	 invocationContext.target = TARGET_FAILURE;
//	  	    	 return;
//        	 }else{
//        		 inventoryListForm.setDeliverWarning("");
//        	 }
//         }
   	  }
		  log.log(Log.FINE,
				"\n\n newMailInInventoryListVOs for delivery ------->",
				toDeliverMailInInventoryListVOs);
		if("C".equals(contentArr[0])){
	    	  try {		    
	    		  new MailTrackingDefaultsDelegate().deliverContainersFromInventory(newContainerInInventoryListVOs);		  
		 	  }catch (BusinessDelegateException businessDelegateException) {
		 				errors = handleDelegateException(businessDelegateException);
		 				invocationContext.addAllError(errors);
		 	  }
	      }
	      else{
	    	  try {		    
				  new MailTrackingDefaultsDelegate().deliverMailBagsFromInventory(toDeliverMailInInventoryListVOs);		  
		      }catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
					invocationContext.addAllError(errors);
			  }
	      }
   	 
      invocationContext.target = TARGET_SUCCESS;
      log.exiting("DeliveryMailCommand","execute");    	
    }       
}
