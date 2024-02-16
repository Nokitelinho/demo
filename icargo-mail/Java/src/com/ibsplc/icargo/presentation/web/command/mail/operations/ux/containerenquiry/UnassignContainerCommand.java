/*
 * UnassignContainerCommand.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1876
 *
 */
public class UnassignContainerCommand extends AbstractCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "list_success";
   private static final String TARGET_FAILURE = "list_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(ActionContext actionContext)
            throws CommandInvocationException {  	  
    	
    	List<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();
    	Collection<ContainerDetails> containerActionData = listContainerModel.getSelectedContainerData(); 
    	    
    	
    	LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		
      // String[] primaryKey = searchContainerForm.getSelectContainer();
   	   MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();
        	
	  
      
     
       
       int errorFlag = 0;
       String bagaccepted = "";
       int errorArr = 0;
       String contArr = "";
       int errorPort = 0;
       String contPort = "";
       
       Collection<ContainerVO> delContainerVOs = new ArrayList<ContainerVO>();
       if (containerActionData != null && containerActionData.size() != 0) {
       	for (ContainerDetails containerVO : containerActionData) {      		
       			
       			if(!logonAttributes.getAirportCode().equals(containerVO.getAirportCode())){
       				errorPort = 1;
       				if("".equals(contPort)){
       					contPort = containerVO.getContainerNumber();
	       			}else{
	       				contPort = new StringBuilder(contPort)
   					                  .append(",")
   					                  .append(containerVO.getContainerNumber())
   					                  .toString();	
	       			}
       			}
       			//Changed by A-5945 for ICRD-110575 for unassigning already accepted bulk/ULD
       			if(containerVO.getBags() == 0 
       					&& (MailConstantsVO.FLAG_NO.equals(containerVO.getAcceptanceFlag())|| MailConstantsVO.FLAG_YES.equals(containerVO.getAcceptanceFlag()))){
       				delContainerVOs.add(MailOperationsModelConverter.constructContainerVO(containerVO, logonAttributes)); 
       			}else{
       				errorFlag = 1;
       				if("".equals(bagaccepted)){
       					bagaccepted = containerVO.getContainerNumber();
	       			}else{
   					    bagaccepted = new StringBuilder(bagaccepted)
   					                  .append(",")
   					                  .append(containerVO.getContainerNumber())
   					                  .toString();	
	       			}
       			}
       			
       			
       			if("Y".equals(containerVO.getArrivedStatus())){
	   				errorArr = 1;
       				if("".equals(contArr)){
       					contArr = containerVO.getContainerNumber();
	       			}else{
	       				contArr = new StringBuilder(contArr)
	       				          .append(",")
	       				          .append(containerVO.getContainerNumber())
	       				          .toString();	
	       			}
	   			}       			
       	  }
       	}
       
       if(errorPort == 1){
   	    	actionContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.differentport",new Object[]{contPort}));
   	    	return;	
       }else if(errorFlag == 1){
    	   actionContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.unassignbagaccepted",new Object[]{bagaccepted}));
    	   return;	
       }else if(errorArr == 1){
    	   actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.unassigncontainersarrived",new Object[]{contArr}));
    	   return;	
       }else{
    	   if(delContainerVOs != null && delContainerVOs.size()> 0){
    		   for(ContainerVO ContainerVO:delContainerVOs){
    			   ContainerVO.setFlightClosureCheckNeeded(true);
    		   }
    	   }
      	   //Added by A-5945 For deleting empty ULDs from MTKULDSEG as part of ICRD-116673
    	   ContainerDetailsVO detailsVO=null;
    	   Collection<ContainerDetailsVO> unAssignContainers = new ArrayList<ContainerDetailsVO>();  
    	   for(ContainerVO containerVO:delContainerVOs){
    		   if(MailConstantsVO.ULD_TYPE.equals(containerVO.getType())){
    			    detailsVO=constructConatinerDetailsFromContainer(containerVO);
		   		   unAssignContainers.add(detailsVO) ;
    		   }
       }
    	   log.log(Log.FINE, "Going to Delete from MTLULDSEG...",unAssignContainers);  
    	   try {
    		if(unAssignContainers!=null && unAssignContainers.size()>0){
				mailTrackingDefaultsDelegate.unassignEmptyULDs(unAssignContainers);
			}
    	   }catch (BusinessDelegateException businessDelegateException) {
       		errors = handleDelegateException(businessDelegateException);
   		}
    	   Collection<ContainerVO> deleteBulkentries = new ArrayList<ContainerVO>(); 
    	   for(ContainerVO container :delContainerVOs){
    		if(MailConstantsVO.BULK_TYPE.equals(container.getType()))  {
    			deleteBulkentries.add(container);
    		}
    	   }
    	   if(deleteBulkentries!=null && deleteBulkentries.size()>0){
    		   try {
   			    log.log(Log.FINE, "Going to Delete...");
      			 mailTrackingDefaultsDelegate.deleteContainers(deleteBulkentries);
   		   } catch (BusinessDelegateException businessDelegateException) {
   			    businessDelegateException.getMessageVO().getErrors();
   			 errors = handleDelegateException(businessDelegateException);
   		   }
    	   }
    	   if (errors!=null && errors.size()>0){
    	       actionContext.addAllError(errors); 
    	       return;	
    	   }else{
    		   
    	   ResponseVO responseVO = new ResponseVO();	  
           responseVO.setStatus("success");
           actionContext.setResponseVO(responseVO);       	
           log.exiting("ReassignSearchContainerCommand","execute");
           
           log.exiting("UnassignSearchContainerCommand","execute");
    	   }
    	   
       }
       
      
    	
    }
    public ContainerDetailsVO constructConatinerDetailsFromContainer(ContainerVO containerVO){
    	ContainerDetailsVO containerDetails= new ContainerDetailsVO();    	
    	containerDetails.setCompanyCode(containerVO.getCompanyCode());
    	containerDetails.setCarrierId(containerVO.getCarrierId());
    	containerDetails.setFlightNumber(containerVO.getFlightNumber());
    	containerDetails.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
    	containerDetails.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
    	containerDetails.setContainerNumber(containerVO.getContainerNumber());
    	containerDetails.setPol(containerVO.getAssignedPort());
    	containerDetails.setAcceptedFlag(containerVO.getAcceptanceFlag());
    	containerDetails.setContainerType(containerVO.getType());
    	return containerDetails;    
    }
       
}
