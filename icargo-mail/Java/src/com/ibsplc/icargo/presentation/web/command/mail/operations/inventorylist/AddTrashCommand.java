/*
 * AddTrashCommand.java Created on Jul 1 2016
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

import com.ibsplc.icargo.business.mail.operations.vo.MailInInventoryListVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.InventoryListSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.InventoryListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @ author
 * AddTrashCommand
 * extends BaseCommand
*/
public class AddTrashCommand extends BaseCommand{
	
	 private Log log = LogFactory.getLogger("MAILOPERATIONS");
	 private static final String MODULE_NAME = "mail.operations";	
	 private static final String SCREEN_ID_INV = 
			"mailtracking.defaults.inventorylist";
	 private static final String TARGET = "save_success";
	 
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		 log.entering("AddTrashCommand","execute");
		 
		 InventoryListForm inventoryListForm = 
	    		(InventoryListForm)invocationContext.screenModel;
		 InventoryListSession inventoryListSession = 
     		getScreenSession(MODULE_NAME,SCREEN_ID_INV);
		    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();   
		 	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		 	InventoryListVO inventoryListVO =inventoryListSession.getInventoryListVO();
		 	Collection<ContainerInInventoryListVO> containersInInventoryListVos=inventoryListVO.getContainerInInventoryList();
		 	Collection<MailInInventoryListVO> mailinv=new ArrayList<MailInInventoryListVO>();
		 	 log.log(Log.FINE, ".......................", inventoryListForm.getContainer());
			ContainerVO containerVO=new ContainerVO();
		 	 if(containersInInventoryListVos!=null && containersInInventoryListVos.size()>0){
		 		 for(ContainerInInventoryListVO container :containersInInventoryListVos ){
		 			 if(container.getUldNumber().equals(inventoryListForm.getContainer())){
		 				 containerVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
		 				 containerVO.setCarrierId(container.getCarrierID());
		 				 containerVO.setCompanyCode(container.getCompanyCode());
		 				 containerVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
		 				 containerVO.setAssignedPort(logonAttributes.getAirportCode());
		 				 containerVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
		 				 containerVO.setContainerNumber(container.getUldNumber());
		 				 containerVO.setType(container.getContainerType());
		 				 containerVO.setCarrierCode(inventoryListForm.getCarrierCode());
		 				 containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		 				 log.log(Log.FINE, "THE TO CONTAINER VO", containerVO);
		 			 }
		 		 }
		 	 }
		 	ContainerInInventoryListVO trashInInventoryListVO=inventoryListVO.getTrashInInventoryListVO();
		 	
		 	 String[] checked=inventoryListForm.getChildContainer();
		 	 /*
		 	  * 
		 	  * 
		 	  * Added By Karthick V as the  part of the Mail Tracking Bug Fix 
		 	  */
		 	 if(checked!=null && checked.length>0){
		 	 
		 	 log.log(Log.FINE, "checked sizeee", checked.length);
			for(int i=0;i<checked.length;i++){
		 		 for(MailInInventoryListVO mailInInventoryListVO :trashInInventoryListVO.getMailInInventoryList()){
		 			String pk=mailInInventoryListVO.getMailCategoryCode()+mailInInventoryListVO.getDestinationCity();
		 			 if(checked[i].equals(pk)){
		 				mailInInventoryListVO.setCurrentAirport("");
		 				mailInInventoryListVO.setCarrierCode(inventoryListForm.getCarrierCode());
		 				mailInInventoryListVO.setCarrierID(Integer.parseInt(inventoryListForm.getCarrierID()));
		 				mailInInventoryListVO.setCurrentAirport(logonAttributes.getAirportCode());
						mailinv.add(mailInInventoryListVO);
		 			 }
		 			
		 		 }
		 	 }
		 	
		 	 }		
		 	 
		 	 try {
				  
		 		  log.log(Log.FINE, "THE MAIL  IN INVENTORY ", mailinv);
				new MailTrackingDefaultsDelegate().reassignMailbagsForInventory(mailinv,containerVO);
				 
			       }catch (BusinessDelegateException businessDelegateException) {
	 			 errors = handleDelegateException(businessDelegateException);
	 			
	 	  }
		 	 
		 	
			
		 		inventoryListForm.setScreenStatusFlag("INVENTORY_TRASH_CLOSE");
		       invocationContext.target = TARGET;
		       log.exiting("AddTrashCommand","execute");
		 
		 
	 }

}
