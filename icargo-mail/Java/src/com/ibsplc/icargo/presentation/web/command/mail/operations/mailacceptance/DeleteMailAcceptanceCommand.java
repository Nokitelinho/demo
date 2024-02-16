/*
 * DeleteMailAcceptanceCommand.java Created on Jul 1 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class DeleteMailAcceptanceCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "success";
   private static final String TARGET_FAILURE = "failure";
   
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeleteMailAcceptanceCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

		
		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
		Collection<ContainerDetailsVO> contDetailsVOs = mailAcceptanceVO.getContainerDetails();
		Collection<ContainerVO> deleteBulkEntries = new ArrayList<ContainerVO>();
		Collection<ContainerDetailsVO> deleteUldEntries = new ArrayList<ContainerDetailsVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String[] selected = mailAcceptanceForm.getSelectMail();
		String selectedMails = selected[0];
		String[] primaryKey = selectedMails.split(",");
	    
		int cnt=0;
	    int count = 1;
        int primaryKeyLen = primaryKey.length;
        
        int errorFlag = 0;
        String bagaccepted = "";
        
        Collection<ContainerDetailsVO> delContainerVOs = new ArrayList<ContainerDetailsVO>();
        if (contDetailsVOs != null && contDetailsVOs.size() != 0) {
         	for (ContainerDetailsVO contDetailsVO : contDetailsVOs) {
         	 	int mailBagcount=0;//added by a-7871 for ICRD-257316
         	 	ContainerVO containervo=new ContainerVO();
       		String primaryKeyFromVO = contDetailsVO.getCompanyCode()
       				+String.valueOf(count);
       		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
       				          equalsIgnoreCase(primaryKey[cnt].trim())) {
       		//added by a-7871 for ICRD-257316 to fetch mailbag count from server starts--
       			containervo.setCompanyCode(contDetailsVO.getCompanyCode());
       			containervo.setFlightNumber(contDetailsVO.getFlightNumber());
       			containervo.setFlightSequenceNumber(contDetailsVO.getFlightSequenceNumber());
       			containervo.setSegmentSerialNumber(contDetailsVO.getSegmentSerialNumber());
       			containervo.setContainerNumber(contDetailsVO.getContainerNumber());
       			containervo.setFlightDate(contDetailsVO.getFlightDate());
       			containervo.setCarrierCode(contDetailsVO.getCarrierCode());
       			containervo.setCarrierId(contDetailsVO.getCarrierId());
       			
       			try {
       				mailBagcount=new MailTrackingDefaultsDelegate().findMailbagcountInContainer(containervo);
				} catch (BusinessDelegateException e) {
					// TODO Auto-generated catch block
					
				}
       			//added by a-7871 for ICRD-257316 to fetch mailbag count from server ends--
       			if (mailBagcount==0) {
       				if((!"I".equals(contDetailsVO.getContainerOperationFlag()) ||(contDetailsVO.getContainerOperationFlag()!=null)) &&  (contDetailsVO.getMailDetails()!=null)){//modified by a-7871 for ICRD-257316
           				errorFlag = 2;
           				if("".equals(bagaccepted)){
           					bagaccepted = contDetailsVO.getContainerNumber();
    	       			}else{
    	       				bagaccepted = new StringBuilder().append(bagaccepted).append(",").append(contDetailsVO.getContainerNumber()).toString();
    	       			}
           			}
       				else{
       				delContainerVOs.add(contDetailsVO);
       				}
       			}else{
       				errorFlag = 1;
       				if("".equals(bagaccepted)){
       					bagaccepted = contDetailsVO.getContainerNumber();
	       			}else{
	       				bagaccepted = new StringBuilder().append(bagaccepted).append(",").append(contDetailsVO.getContainerNumber()).toString();
	       			}
       			}
       			cnt++;
       		}
       		count++;
       	  }
       	}
             //Added as part of ICRD-130739 starts 
        if (delContainerVOs != null && delContainerVOs.size() > 0) {
        	for(ContainerDetailsVO containerDetails:delContainerVOs){
        		 if(MailConstantsVO.BULK_TYPE.equals(containerDetails.getContainerType())){
        	ContainerVO contVO=constructContainerFromContainerDetails(containerDetails,logonAttributes);
        	deleteBulkEntries.add(contVO);
        		 }
        	}
        }
        
        if (delContainerVOs != null && delContainerVOs.size() > 0) {
        	for(ContainerDetailsVO containerDetails:delContainerVOs){
        		 if(MailConstantsVO.ULD_TYPE.equals(containerDetails.getContainerType())){
        	deleteUldEntries.add(containerDetails);
        		 }
        	}
        }
      //Added as part of ICRD-130739 ends
        if(errorFlag == 1){
    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.cannotdelete",new Object[]{bagaccepted}));
    	    invocationContext.target = TARGET_FAILURE;
        }
        else  if(errorFlag == 2){
    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.cannotdeletecontainers",new Object[]{bagaccepted}));
    	    invocationContext.target = TARGET_FAILURE;
        }else{
        	//Changed as part of ICRD-130739 starts
        	if(deleteUldEntries!=null && deleteUldEntries.size()>0){
    	   try {
   			    log.log(Log.FINE, "Going to Delete...");
   		    	new MailTrackingDefaultsDelegate().unassignEmptyULDs(deleteUldEntries);
   		   } catch (BusinessDelegateException businessDelegateException) {
   			    businessDelegateException.getMessageVO().getErrors();
   			 errors = handleDelegateException(businessDelegateException);
   		   }
   		   }
   		   
   			if(deleteBulkEntries!=null && deleteBulkEntries.size()>0){
   				try{
   					new MailTrackingDefaultsDelegate().deleteContainers(deleteBulkEntries);    
   		   } catch (BusinessDelegateException businessDelegateException) {
			    businessDelegateException.getMessageVO().getErrors();
			 errors = handleDelegateException(businessDelegateException);
		   }
   		   }
   		//Changed as part of ICRD-130739 ends
   		   mailAcceptanceForm.setSelectMail(null);
   		   invocationContext.addAllError(errors);
    	   invocationContext.target = TARGET_SUCCESS;
        }
        mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
        log.exiting("DeleteMailAcceptanceCommand","execute");
    }
    /**
     * Construct ContainerVO From ContainerDetailsVO
     * @param containerDetailsVO
     * @return
     */
    private ContainerVO constructContainerFromContainerDetails(ContainerDetailsVO containerDetailsVO,LogonAttributes logonAttributes){
    	ContainerVO containerVO= new ContainerVO();
    	containerVO.setCompanyCode(containerDetailsVO.getCompanyCode());
    	containerVO.setCarrierId(containerDetailsVO.getCarrierId());
    	containerVO.setFlightNumber(containerDetailsVO.getFlightNumber());
    	containerVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
    	containerVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
    	containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
    	containerVO.setAssignedPort(containerDetailsVO.getPol());
    	containerVO.setPou(containerDetailsVO.getPou());
    	containerVO.setAcceptanceFlag(containerDetailsVO.getAcceptedFlag());
    	containerVO.setType(containerDetailsVO.getContainerType());
    	containerVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
    	containerVO.setLastUpdateTime(containerDetailsVO.getLastUpdateTime());
    	//Modified as part of Bug ICRD-144099 by A-5526
    	containerVO.setLastUpdateUser(logonAttributes.getUserId());          
    	return containerVO;
    }
       
}
