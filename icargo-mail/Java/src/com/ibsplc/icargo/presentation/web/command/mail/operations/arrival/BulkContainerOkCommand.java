/*
 * BulkContainerOkCommand.java Created on Jun 30 2016
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class BulkContainerOkCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "ok_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("BulkContainerOkCommand","execute");

    	MailArrivalForm mailArrivalForm =
    		(MailArrivalForm)invocationContext.screenModel;
    	
    	MailArrivalSession mailArrivalSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
    	OperationalFlightVO operationalFlightVO= mailArrivalSession.getOperationalFlightVO();
    	MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();

		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
    	String container = mailArrivalForm.getContainerNumber().toUpperCase();
    	String containerType = mailArrivalForm.getContainerTypes();
    	ULDDelegate uldDelegate = new ULDDelegate();
    	
    	
    	boolean isULDType = "U".equals(containerType);
		if(!isULDType && container.length()>= 3) {
			String containerPart = container.substring(0,3);
			log.log(Log.FINE, "$$$$$$containerPart------->>", containerPart);
			Collection<String> containerParts = new ArrayList<String>();
			containerParts.add(containerPart);
			try {
				uldDelegate.validateULDTypeCodes(
						logonAttributes.getCompanyCode(),containerParts);
				isULDType = true;
			}catch (BusinessDelegateException businessDelegateException) {
				isULDType = false;
			}
		}
    	
    	if(isULDType){
    	
    	try {
			
    		uldDelegate.validateULD(logonAttributes.getCompanyCode(),container);
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if (errors != null && errors.size() > 0) {      
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.invaliduldnumber",
	   				new Object[]{container}));
	  		invocationContext.target = TARGET;
	  		return;
		}
    	}
		if(!("Y").equals(mailArrivalForm.getOverrideBulkFlag())){
			ContainerVO containerVO = new ContainerVO();
			containerVO.setCompanyCode(logonAttributes.getCompanyCode());
	    	containerVO.setAssignedPort(logonAttributes.getAirportCode());
	    	log.log(Log.FINE, "&&&&&&&&&*********************container",
					container);
			containerVO.setContainerNumber(container);
	    	/*
	    	 *  Added By Karthick V as the part of the NCA Mail Tracking Bug Fix 
	    	 *  The assign Container Scren in the Mail Arrival is specific for the  
	    	 *  Carrier Assignments for the  
	    	 *  Inventory so set the   values for the Destination inside the  ContainerVO.... 
	    	 */
	    	 containerVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
	    	 containerVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
	    	 containerVO.setCarrierId(mailArrivalVO.getCarrierId());
	    	 containerVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
	    	 containerVO.setType(containerType);
	    	 //containerVO.setPou(operationalFlightVO.getPou());
	    	 containerVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
        	    	
			log.log(Log.FINE, "*****************************", containerVO);
			try {	    		
	    		containerVO = new MailTrackingDefaultsDelegate().validateContainer(logonAttributes.getAirportCode(), containerVO);
				
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				if (errors != null && errors.size() > 0) {
					for(ErrorVO vo : errors) {
						log.log(Log.FINE, "vo.getErrorCode() ------------> ",
								vo.getErrorCode());
						if (("mailtracking.defaults.openedflight").equals(vo.getErrorCode())) {
							invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.msg.err.alreadyAssigned"));
			 	   			invocationContext.target = TARGET;
			 	   			return; 
						}
						else if (("mailtracking.defaults.canreassigned").equals(vo.getErrorCode())) {							
							break;
						}
						else if(("mailtracking.defaults.warn.uldisnotinthesystem").equals(vo.getErrorCode())){
							invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.msg.warn.uldnotinthesystem"));
							invocationContext.target = TARGET;
			 	   			return; 
						}
						else{
							invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.msg.err.others"));
			 	   			invocationContext.target = TARGET;
			 	   			return; 
						}
					}
				}
			}
		}
		ArrayList<MailbagVO> mailbagVOs = null;
		
    	if("ARRIVE".equals(mailArrivalForm.getBulkFromScreen())){
    		String[] selectedContainer = mailArrivalForm.getSelectContainer();
    		
    		ArrayList<ContainerDetailsVO> containerDtlsVOs=null;
    		containerDtlsVOs = (ArrayList<ContainerDetailsVO>) mailArrivalVO.getContainerDetails();
    		String selectContainer = mailArrivalForm.getSelContainer();
    		String[] containerPrimaryKey = selectContainer.split(",");
    		if((!("").equals(selectContainer))&& containerPrimaryKey.length>0){	    		
	    		for(int i=0;i<containerPrimaryKey.length;i++){   
					containerDetailsVO =  containerDtlsVOs.get(Integer.parseInt(containerPrimaryKey[i]));
					mailbagVOs = (ArrayList<MailbagVO>) containerDetailsVO.getMailDetails();
	      			for(MailbagVO mailbagVO : mailbagVOs){
      					mailbagVO.setContainerForInventory(container);
	    				mailbagVO.setContainerTypeAtAirport(containerType);	   
	       			}      			
				}
    		}
    		else{
	    		String selectedDSNs = mailArrivalForm.getChildBulkContainer();
	    		String selectMainContainer = mailArrivalForm.getSelectMainContainer();
		   	    String[] primaryKey = selectedDSNs.split(",");
		   	    containerDetailsVO= containerDtlsVOs.get(Integer.parseInt(selectMainContainer));
		   	    log.log(Log.FINE, "########primaryKey#######",
						primaryKey.length);
				for (int i=0;i<primaryKey.length;i++){
	   	    		DSNVO dsnVO=(DSNVO) new ArrayList<DSNVO>(containerDetailsVO.getDsnVOs()).get(Integer.parseInt(primaryKey[i].split("~")[1]));
	   	    		
	   	    		String innerpk = dsnVO.getOriginExchangeOffice()
	   				+dsnVO.getDestinationExchangeOffice()
	   				+dsnVO.getMailCategoryCode()
	   				+dsnVO.getMailSubclass()
	   				+dsnVO.getDsn()
	   				+dsnVO.getYear();
	   	    		if(("Y").equalsIgnoreCase(dsnVO.getPltEnableFlag())){
	   	    			if(containerDetailsVO.getMailDetails()!=null && containerDetailsVO.getMailDetails().size()>0){
		   	    			for(MailbagVO mailbagVO : containerDetailsVO.getMailDetails()){	   	    				
		   	    			   String outerpk = mailbagVO.getOoe()
		   	    			   +mailbagVO.getDoe()
		   			           +mailbagVO.getMailCategoryCode()
		   			           +mailbagVO.getMailSubclass()
		   			           +mailbagVO.getDespatchSerialNumber()
		   			           +mailbagVO.getYear();
		   	    			   if(innerpk.equals(outerpk)){	
			   	    				mailbagVO.setContainerForInventory(container);
				    				mailbagVO.setContainerTypeAtAirport(containerType);
		   	    			   }
		   	    			}
	   	    			}
	   	    		}
	   	    		else{
	   	    			for(DespatchDetailsVO despatchDetailsVO : containerDetailsVO.getDesptachDetailsVOs()){
	   	    				String outpk = despatchDetailsVO.getOriginOfficeOfExchange()
	   	    				+despatchDetailsVO.getDestinationOfficeOfExchange()
	   	 		            +despatchDetailsVO.getMailCategoryCode()
	   	 		            +despatchDetailsVO.getMailSubclass()
	   	 		            +despatchDetailsVO.getDsn()
	   	 		            +despatchDetailsVO.getYear();
	   	     			    if(innerpk.equals(outpk)){
	   	     			    	despatchDetailsVO.setContainerForInventory(container);
	   	     			    	despatchDetailsVO.setContainerTypeAtAirport(containerType);
	   	     			    }
	   	    			}   	    			
	   	    		}
	   			}
    		}
    		
    			mailArrivalForm.setBulkFromScreen("ARRIVE_CLOSE");
    		
    	}
    	if("ARRIVE_POPUP".equals(mailArrivalForm.getBulkFromScreen())){
    		containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
    		mailbagVOs = (ArrayList<MailbagVO>) containerDetailsVO.getMailDetails();
    		String hiddenMailTag = mailArrivalForm.getHiddenMailTag();
    		String[] mailtagKey= hiddenMailTag.split(",");
    		for(int i=0;i<mailtagKey.length;i++){
    			MailbagVO mailbagVO = new MailbagVO();
    			mailbagVO = mailbagVOs.get(Integer.parseInt(mailtagKey[i])-1);
    			mailbagVO.setContainerForInventory(container);
				mailbagVO.setContainerTypeAtAirport(containerType);
				mailbagVO.setArrivedFlag("Y");
				mailbagVO.setOperationalFlag("U");
				LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				mailbagVO.setScannedDate(sd);
    		}    		
    		mailArrivalForm.setBulkFromScreen("ARRIVE_POPUP_CLOSE");   		
    		
    	}
    	
    	invocationContext.target = TARGET;
    	
       	log.exiting("BulkContainerOkCommand","execute");
    	
    }
}
