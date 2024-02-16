/*
 * UnassignSearchContainerCommand.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchcontainer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1876
 *
 */
public class UnassignSearchContainerCommand extends BaseCommand {
	
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
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("UnassignSearchContainerCommand","execute");
    	  
    	SearchContainerForm searchContainerForm = 
    		(SearchContainerForm)invocationContext.screenModel;
    	SearchContainerSession searchContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
       String[] primaryKey = searchContainerForm.getSelectContainer();
   	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();
        	
	   int cnt=0;
	   int count = 1;
       int primaryKeyLen = primaryKey.length;
       Collection<ContainerVO> containerVOs = 
    	   searchContainerSession.getListContainerVOs();
       
       int errorFlag = 0;
       String bagaccepted = "";
       int errorArr = 0;
       String contArr = "";
       int errorPort = 0;
       String contPort = "";
       
       Collection<ContainerVO> delContainerVOs = new ArrayList<ContainerVO>();
     
       if (containerVOs != null && containerVOs.size() != 0) {
       	for (ContainerVO containerVO : containerVOs) {
       	int mailBagcount=0;//added by a-7871 for ICRD-257316
       		String primaryKeyFromVO = containerVO.getCompanyCode()
       				+String.valueOf(count);
       		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
       				          equalsIgnoreCase(primaryKey[cnt].trim())) {
       			
       			if(!logonAttributes.getAirportCode().equals(containerVO.getAssignedPort())){
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
       			
       			//added by a-7871 for ICRD-257316 to fetch mailbag count from server starts--
       			try {
       				mailBagcount=mailTrackingDefaultsDelegate.findMailbagcountInContainer(containerVO);
				} catch (BusinessDelegateException e) {
					// TODO Auto-generated catch block
					
				}
       			//added by a-7871 for ICRD-257316 to fetch mailbag count from server ends--
       			if(mailBagcount==0
       					&& (MailConstantsVO.FLAG_NO.equals(containerVO.getAcceptanceFlag())|| MailConstantsVO.FLAG_YES.equals(containerVO.getAcceptanceFlag()))){//modified by a-7871
       				delContainerVOs.add(containerVO);
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
       			cnt++;
       		}
       		count++;
       	  }
       	}
       
       if(errorPort == 1){
   	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.differentport",new Object[]{contPort}));
   	    	searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
   	    	invocationContext.target = TARGET_FAILURE;
       }else if(errorFlag == 1){
    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.unassignbagaccepted",new Object[]{bagaccepted}));
    	    searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	    invocationContext.target = TARGET_FAILURE;
       }else if(errorArr == 1){
    	   invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.unassigncontainersarrived",new Object[]{contArr}));
    	   searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	   	   invocationContext.target = TARGET_FAILURE;
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
      			 mailTrackingDefaultsDelegate.deleteContainers(delContainerVOs);
   		   } catch (BusinessDelegateException businessDelegateException) {
   			    businessDelegateException.getMessageVO().getErrors();
   			 errors = handleDelegateException(businessDelegateException);
   		   }
    	   }
   		invocationContext.addAllError(errors);
   		searchContainerForm.setDisplayPage("1");
   		searchContainerForm.setLastPageNum("0");
   		
   		searchContainerForm.setStatus("unassignContainer");
   		searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    invocationContext.target = TARGET;
    	   
       }
       
       log.exiting("UnassignSearchContainerCommand","execute");
    	
    }
    public ContainerDetailsVO constructConatinerDetailsFromContainer(ContainerVO containerVO){
    	ContainerDetailsVO containerDetails= new ContainerDetailsVO();
    	/*try {
			BeanHelper.copyProperties(containerDetails, containerVO);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
