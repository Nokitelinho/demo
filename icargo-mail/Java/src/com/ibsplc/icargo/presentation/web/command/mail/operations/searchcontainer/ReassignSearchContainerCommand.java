/*
 * ReassignSearchContainerCommand.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchcontainer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignContainerSession;
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
public class ReassignSearchContainerCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "reassign_success";
   private static final String TARGET_FAILURE = "reassign_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	
   private static final String SCREEN_ID_REASSIGN = "mailtracking.defaults.reassignContainer";
   
   private static final String CONST_REASSIGN = "showReassignScreen";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ReassignSearchContainerCommand","execute");
    	  
      	SearchContainerForm searchContainerForm = 
    		(SearchContainerForm)invocationContext.screenModel;
    	SearchContainerSession searchContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	ReassignContainerSession reassignContainerSession = 
    		(ReassignContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID_REASSIGN);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	Collection<ErrorVO> errors = null;
    	
       String[] primaryKey = searchContainerForm.getSelectContainer();
        	
	   int cnt=0;
	   int count = 1;
       int primaryKeyLen = primaryKey.length;
       Collection<ContainerVO> containerVOs = 
    	   			searchContainerSession.getListContainerVOs();
       
       Collection<ContainerVO> reasgnContainerVOs = 
    	   						new ArrayList<ContainerVO>();
       if (containerVOs != null && containerVOs.size() != 0) {
       	for (ContainerVO containerVO : containerVOs) {
       		String primaryKeyFromVO = containerVO.getCompanyCode()
       				+String.valueOf(count);
       		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
       				          equalsIgnoreCase(primaryKey[cnt].trim())) {
       			reasgnContainerVOs.add(containerVO);
       			cnt++;
       		}
       		count++;
       	  }
       	}
       for(ContainerVO containerVO : reasgnContainerVOs){
    	   if(logonAttributes.getAirportCode().equals(containerVO.getFinalDestination())){
    		   ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.containeratfinaldest");
    		   invocationContext.addError(errorVO);
    		   invocationContext.target = TARGET_FAILURE;
      	    	return;
    	   }
       }
       // VALIDATION FOR CONTAINERS HOLDING OFFLOADED MAILS
       int errorOfl = 0;
       String contOfl = "";
       int errorArr = 0;
       String contArr = "";
       int errorPort = 0;
       String contPort = "";
       int errorAlreadyManifest=0;
	   String contAlreadyManifest = "";
	   int errorNonPartner =0;
	   String nonPartner="";
	   String conNum="";
       
	   	for (ContainerVO selectedvo : reasgnContainerVOs) {
	   		if (selectedvo.getContainerNumber() != null ) {
	   			
	   			if(!logonAttributes.getAirportCode().equals(selectedvo.getAssignedPort())){
       				errorPort = 1;
       				if("".equals(contPort)){
       					contPort = selectedvo.getContainerNumber();
	       			}else{
	       				contPort = new StringBuilder(contPort)
   					                  .append(",")
   					                  .append(selectedvo.getContainerNumber())
   					                  .toString();	
	       			}
       			}
	   			
	   			if (selectedvo.getContainerNumber().startsWith("OFL")) {
	   				errorOfl = 1;
       				if("".equals(contOfl)){
       					contOfl = selectedvo.getContainerNumber();
	       			}else{
	       				contOfl = new StringBuilder(contOfl)
	       				              .append(",")
	       				              .append(selectedvo.getContainerNumber())
	       				              .toString();	
	       			}
	   			}
	   			if("Y".equals(selectedvo.getArrivedStatus())){
	   				errorArr = 1;
       				if("".equals(contArr)){
       					contArr = selectedvo.getContainerNumber();
	       			}else{
	       				contArr = new StringBuilder(contArr)
	       				             .append(",")
	       				             .append(selectedvo.getContainerNumber())
	       				             .toString();	
	       			}
	   			}
				if(MailConstantsVO.BULK_TYPE.equals(selectedvo.getType()) && 
						MailConstantsVO.FLAG_NO.equals(selectedvo.getTransitFlag())) {
					errorAlreadyManifest = 1;
					if("".equals(contAlreadyManifest)){
						contAlreadyManifest = selectedvo.getContainerNumber();
					}else{
						contAlreadyManifest = new StringBuilder(contAlreadyManifest)
						.append(",")
						.append(selectedvo.getContainerNumber())
						.toString();	
					}
				}
				//Added by A-5945 for ICRD-96261 starts
				if(selectedvo.getCarrierCode()!=null){
					nonPartner=	selectedvo.getCarrierCode();
					conNum=selectedvo.getContainerNumber();
				Collection<PartnerCarrierVO> partnerCarrierVOs = null;
				ArrayList<String> partnerCarriers = new ArrayList<String>();
				MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
		    		new MailTrackingDefaultsDelegate();
				String companyCode =logonAttributes.getCompanyCode();
				String ownCarrierCode = logonAttributes.getOwnAirlineCode();
				String airportCode = logonAttributes.getAirportCode();
				String CarrierCode =selectedvo.getCarrierCode();
				try {
					partnerCarrierVOs=mailTrackingDefaultsDelegate.findAllPartnerCarriers(companyCode,ownCarrierCode,airportCode);
				}catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
				log.log(Log.INFO, "partnerCarrierVOs-----------------",
						partnerCarrierVOs);
				if(partnerCarrierVOs!= null){
					for(PartnerCarrierVO partner :partnerCarrierVOs){
						String partnerCarrier =	 partner.getPartnerCarrierCode();
						partnerCarriers.add(partnerCarrier);
						} 
						partnerCarriers.add(ownCarrierCode);  
					if(!(partnerCarriers.contains(CarrierCode))){
						errorNonPartner = 1;
						
						}
					
					
				}
				}
				//Added by A-5945 for ICRD-96261 ends
				
	   		}
	   	}
	  //Added by A-5945 for ICRD-96261 starts
	    if(errorNonPartner == 1){
   	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.notHandledCarrier",new Object[]{conNum,nonPartner}));
   	    	invocationContext.target = TARGET_FAILURE;
   	        log.exiting("ReassignSearchContainerCommand","execute");
   	    	return;
        }
	  //Added by A-5945 for ICRD-96261 ends    
	    if(errorPort == 1){
   	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.differentport",new Object[]{contPort}));
   	    	invocationContext.target = TARGET_FAILURE;
   	        log.exiting("ReassignSearchContainerCommand","execute");
   	    	return;
        }
	    
	   	if(errorOfl == 1){
	   		invocationContext.addError(new ErrorVO(
	   				"mailtracking.defaults.msg.err.containersHoldOffloadedMails",new Object[]{contOfl}));
	   		invocationContext.target = TARGET_FAILURE;
	        log.exiting("ReassignSearchContainerCommand","execute");
			return;

	   	}
	   	
	   	if(errorArr == 1){
	   		invocationContext.addError(new ErrorVO(
	   				"mailtracking.defaults.msg.err.reassigncontainersarrived",new Object[]{contArr}));
	   		invocationContext.target = TARGET_FAILURE;
	        log.exiting("ReassignSearchContainerCommand","execute");
			return;
	   	}
		if(errorAlreadyManifest == 1){
  	    	invocationContext.addError(new ErrorVO(
  	    			"mailtracking.defaults.searchcontainer.err.cannnotreassigntransitcontainer",new Object[]{contAlreadyManifest}));  	    		    	
			invocationContext.target = TARGET_FAILURE;
		       log.exiting("ReassignSearchContainerCommand","execute");
			return;		
       }
	   	
	   reassignContainerSession.setSelectedContainerVOs(reasgnContainerVOs);
	   reassignContainerSession.setContainerVO(new ContainerVO());
  	   searchContainerForm.setStatus(CONST_REASSIGN);
       searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
       invocationContext.target = TARGET_SUCCESS;
       	
       log.exiting("ReassignSearchContainerCommand","execute");
    	
    }
       
}
