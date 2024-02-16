/*
 * AutoAttachAWBCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class AutoAttachAWBCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "attach_success";
   private static final String TARGET_FAILURE = "attach_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
   private static final String NO_MAILS_TO_ATTACH ="mailtracking.defaults.attachawb.msg.err.nomailstoattach";
   

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AutoAttachAWBCommand","execute");
    	  
    	MailManifestForm mailManifestForm = 
    		(MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	Collection<ErrorVO> errors = null;
    	
    	mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		/*
		 * Shipment Prefix
		 */
    	/*String shpmentDescFieldValue = mailManifestForm.getShipmentDesc();
    	String shipmentDescription = null;
		Collection<OneTimeVO> shipmentDescOneTime = mailManifestSession.getShipmentDescription();
		if(shipmentDescOneTime != null && shipmentDescOneTime.size() > 0 && 
				shpmentDescFieldValue != null && shpmentDescFieldValue.trim().length() > 0) {
			for(OneTimeVO oneTime : shipmentDescOneTime) {
				if(oneTime.getFieldValue().equalsIgnoreCase(shpmentDescFieldValue)) {
					shipmentDescription = oneTime.getFieldDescription();
					break;
				}
			}
		}*/
    	MailManifestVO mailManifestVO = mailManifestSession.getMailManifestVO();
    	Collection<ContainerDetailsVO> containerDetailsVOs = mailManifestVO.getContainerDetails();
    	/*if(("YES").equals(mailManifestForm.getAutoAttach())){
    		
    		containerDetailsVOs = mailManifestVO.getContainerDetails();*/
    		/*
    		 * Updating all the DSNs With the selected Shipment description
    		 * For all the DSNs in All the Containers.
    		 */
    		/*if(containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
    			for(ContainerDetailsVO containerDtlsVO : containerDetailsVOs) {
    				if(containerDtlsVO.getDsnVOs() != null && containerDtlsVO.getDsnVOs().size() > 0) {
    					for(DSNVO dsns : containerDtlsVO.getDsnVOs()) {
    						dsns.setShipmentDescription(shipmentDescription);
    					}
    				}
    			}
    		}    		
    	}else{
    		String[] parentCont = mailManifestForm.getParentContainer().split("-");
    		int parentSize=parentCont.length;
    		for(int i=0;i<parentSize;i++){
    			if(!("").equals(parentCont[i])){
    				ContainerDetailsVO containerDtlsVO = 
    	        	((ArrayList<ContainerDetailsVO>)mailManifestVO.getContainerDetails()).get(Integer.parseInt(parentCont[i]));
    		
	    	    	if(!containerDetailsVOs.contains(containerDtlsVO)){
	    	    			containerDetailsVOs.add(containerDtlsVO);
	    	    	}
    	    	}
    		}*/
    		/*
    		 * Updating all the DSNs With the selected Shipment description
    		 * For all the DSNs in the Selected Containers.
    		 */
    		/*if(containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
    			for(ContainerDetailsVO containerDtlsVO : containerDetailsVOs) {
    				if(containerDtlsVO.getDsnVOs() != null && containerDtlsVO.getDsnVOs().size() > 0) {
    					for(DSNVO dsns : containerDtlsVO.getDsnVOs()) {
    						dsns.setShipmentDescription(shipmentDescription);
    					}
    				}
    			}
    		}    		
    		String[] childDSN=mailManifestForm.getSelectChild().split(",");
    		
    		int childSize=childDSN.length;
    		ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
    		ContainerDetailsVO containerDtlsDummyVO = new ContainerDetailsVO();
    		Collection<DSNVO> dsnVOs = null;
    		for(int i=0;i<childSize;i++){
    			if(!("").equals(childDSN[i]) &&  !("").equals(childDSN[i].split("-")[0])){
    				
    				containerDtlsVO = 
    	        	((ArrayList<ContainerDetailsVO>)mailManifestVO.getContainerDetails()).get(Integer.parseInt(childDSN[i].split("-")[0]));
    				if(containerDtlsVO != null &&
    						(!containerDtlsVO.getContainerNumber().equals(containerDtlsDummyVO.getContainerNumber()))) {
    					containerDtlsDummyVO = new ContainerDetailsVO();
    					containerDtlsDummyVO.setCompanyCode(containerDtlsVO.getCompanyCode());
    					containerDtlsDummyVO.setContainerNumber(containerDtlsVO.getContainerNumber());
    					containerDtlsDummyVO.setContainerType(containerDtlsVO.getContainerType());  	    	    	
    					dsnVOs = new ArrayList<DSNVO>();
    					containerDtlsDummyVO.setDsnVOs(dsnVOs);
    			    	containerDetailsVOs.add(containerDtlsDummyVO);
    				}
    				if((childDSN[i].split("-")[1]).trim().length() > 0 && containerDtlsVO != null) {
    					DSNVO dsn = ((ArrayList<DSNVO>)containerDtlsVO.getDsnVOs()).get(
    							Integer.parseInt(childDSN[i].split("-")[1]));
    					dsn.setCompanyCode(containerDtlsVO.getCompanyCode());
    					dsn.setContainerNumber(containerDtlsVO.getContainerNumber());
    					dsn.setSegmentSerialNumber(containerDtlsVO.getSegmentSerialNumber());
    					dsn.setShipmentDescription(shipmentDescription);
    					
    					dsnVOs.add(dsn);
    				}
    	    	}
    		} 		
    	}*/
		if (containerDetailsVOs == null || containerDetailsVOs.size() ==0) {
			invocationContext.addError(new ErrorVO(NO_MAILS_TO_ATTACH));
			invocationContext.target = TARGET_FAILURE;
			return;
    	}
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
    	
	    boolean isFlightClosed = false;
    	try {
    		
    		isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(
    				mailManifestSession.getOperationalFlightVO());
			
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
		}
		
		if(isFlightClosed) {
			Object[] obj = {mailManifestVO.getFlightCarrierCode(),
					mailManifestVO.getFlightNumber(),
					mailManifestVO.getDepDate().toString().substring(0,11)};
			invocationContext.addError(
					new ErrorVO("mailtracking.defaults.err.flightclosed", obj));
			invocationContext.target = TARGET_FAILURE;
			return;
		}
    	/*for(ContainerDetailsVO vo:containerDetailsVOs){
    		for(DSNVO dsnvo:vo.getDsnVOs()){
    			dsnvo.setCompanyCode(logonAttributes.getCompanyCode());
    			if(dsnvo.getSegmentSerialNumber() == 0) {
    				dsnvo.setSegmentSerialNumber(vo.getSegmentSerialNumber());
    			}
    			
    		}
    		
    	}*/
    	log.log(Log.INFO, "containerDetailsVOs---->>", containerDetailsVOs);
		log.log(Log.INFO, "flightVO---->>", mailManifestSession.getOperationalFlightVO());
		/* Added for bug 54375     
    	  obtain the  stock holder
    	*/
		//String agentCode = mailManifestSession.getAgentCode();

		/*if (agentCode == null || agentCode.trim().length()==0) {
			ErrorVO error =
				new ErrorVO("mailtracking.defaults.attachawb.msg.err.noAgentCode");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = TARGET_FAILURE;
			return;
		}*/
    	    	
    	Collection<ContainerDetailsVO> resultVOs = new ArrayList<ContainerDetailsVO>();
    	if(containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
    		try {
    			resultVOs = mailTrackingDefaultsDelegate.autoAttachAWBDetails(containerDetailsVOs,mailManifestSession.getOperationalFlightVO());

    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0 ) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;			
    			return;
    		}

    		log.log(Log.INFO, "resultVOs---->>", resultVOs);
			mailManifestForm.setAutoAttachAWB(TARGET_SUCCESS);		
    	}	   	
		mailManifestVO.setContainerDetails(resultVOs);
		mailManifestSession.setMailManifestVO(mailManifestVO);
		invocationContext.target = TARGET_SUCCESS;
       	
    	log.exiting("AutoAttachAWBCommand","execute");
    	
    }
    
}
