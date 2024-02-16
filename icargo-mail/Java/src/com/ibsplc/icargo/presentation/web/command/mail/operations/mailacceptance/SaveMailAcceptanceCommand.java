/*
 * SaveMailAcceptanceCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ExistingMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
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
public class SaveMailAcceptanceCommand extends BaseCommand {
	
	@Override
	public boolean breakOnInvocationFailure() {		
		return true;
	}
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "save_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   private static final String CONST_FLIGHT = "FLIGHT";   
   private static final String SAVE_SUCCESS =
		"mailtracking.defaults.mailacceptance.msg.info.savesuccess";
   private static final String SCREEN_NUMERICAL_ID = "MTK002";   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SaveMailAcceptanceCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	Collection<ScannedMailDetailsVO> scannedMailDetailsVOS = null;
    	MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		String assignTo = mailAcceptanceForm.getAssignToFlight();
		if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
			mailAcceptanceVO.setFlightCarrierCode(mailAcceptanceVO.getFlightCarrierCode());			
		}else{
			if(FLAG_YES.equals(mailAcceptanceSession.getInventoryparameter())){
				mailAcceptanceVO.setInventory(true);
			}else{
				mailAcceptanceVO.setInventory(false);	
			}
		}
		if(mailAcceptanceVO.isMailModifyflag()){
			updateDeletedMailbs(mailAcceptanceVO);
		}
		mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
		if(mailAcceptanceVO.getContainerDetails()!=null && mailAcceptanceVO.getContainerDetails().size()>0){
			for (ContainerDetailsVO conDetVO : mailAcceptanceVO.getContainerDetails()){					
				if(MailConstantsVO.FLAG_YES.equals(conDetVO.getPaBuiltFlag())){
					if(conDetVO.getMailDetails()!=null && conDetVO.getMailDetails().size()>0){
						for(MailbagVO mail : conDetVO.getMailDetails()){
							mail.setPaBuiltFlag(conDetVO.getPaBuiltFlag());
						}
					}
				}
				conDetVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);	
				/*if(!CONST_FLIGHT.equals(assignTo)){
					conDetVO.setPou(conDetVO.getDestination());
				}*/ //Commented by A-6991 for ICRD-209424
			}
		}
		//Modified by A-7794 as part of ICRD-232299
		mailAcceptanceVO.setMailSource(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		mailAcceptanceVO.setMailDataSource(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		log.log(Log.FINE, "Going To Save ...in command", mailAcceptanceVO);
		mailAcceptanceSession.setExistingMailbagVO(null);
		
		  try {
			  scannedMailDetailsVOS= new MailTrackingDefaultsDelegate().saveAcceptanceDetails(mailAcceptanceVO);
          }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	  }
    	  if (errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
    		mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
    		invocationContext.target = TARGET;
    		return;
    	  }
    	if(scannedMailDetailsVOS!=null && scannedMailDetailsVOS.size()>0){
    		log.log(Log.FINE, "scannedMailDetailsVOS!=null && scannedMailDetailsVOS.size()>0");
    		ScannedMailDetailsVO scannedMailDetailsVO =
    			((ArrayList<ScannedMailDetailsVO>)scannedMailDetailsVOS).get(0);
    		if( scannedMailDetailsVO.getExistingMailbagVOS()!=null 
    				&&  (scannedMailDetailsVO.getExistingMailbagVOS().size())>0){
    			log.log(Log.FINE, "scannedMailDetailsVO.getExistingMailbagVOS()!=null ");
	    		Collection<ExistingMailbagVO> existingMailbagVOS =  scannedMailDetailsVO.getExistingMailbagVOS();
	    		mailAcceptanceForm.setExistingMailbagFlag("Y");
	        	mailAcceptanceSession.setExistingMailbagVO(existingMailbagVOS);
	        	mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
	        	invocationContext.target = TARGET;
    		}
    		
    	
    	
		}else{
			log.log(Log.FINE, "scannedMailDetailsVOS =null");
	    	MailAcceptanceVO mailAcceptVO = new MailAcceptanceVO(); 
	    	//Added for ICRD-134007 starts
	    	if(CONST_FLIGHT.equalsIgnoreCase(mailAcceptanceForm.getAssignToFlight())){
	    	mailAcceptVO.setFlightCarrierCode(mailAcceptanceForm.getFlightCarrierCode());
	    	mailAcceptVO.setFlightNumber(mailAcceptanceForm.getFlightNumber());
	    	mailAcceptVO.setStrFlightDate(mailAcceptanceForm.getDepDate());
	    	}else{
	    		mailAcceptVO.setFlightCarrierCode(mailAcceptanceForm.getFlightCarrierCode());
	    		mailAcceptVO.setDestination(mailAcceptanceForm.getDestination());
	    	}
	    	//Added for ICRD-134007 ends
	    	mailAcceptanceSession.setMailAcceptanceVO(mailAcceptVO);
	    	FlightValidationVO flightValidationVO = new FlightValidationVO();
	  		mailAcceptanceSession.setFlightValidationVO(flightValidationVO);
	  		mailAcceptanceForm.setDisableDestnFlag(FLAG_NO);
	  		mailAcceptanceForm.setDisableSaveFlag(FLAG_NO);
	  		mailAcceptanceForm.setInitialFocus(FLAG_YES);
	  		mailAcceptanceForm.setOperationalStatus("");
	  		mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	  		mailAcceptanceForm.setDeparturePort(logonAttributes.getAirportCode());
	  		//mailAcceptanceForm.setAssignToFlight(CONST_FLIGHT);
	  		mailAcceptanceForm.setWarningFlag(FLAG_NO);
	  		mailAcceptanceForm.setSaveSuccessFlag(FLAG_YES);//Added for ICRD-134007
	  		//Commented for ICRD-134007
	  		/*ErrorVO error = new ErrorVO(SAVE_SUCCESS);
			errors.add(error);
			invocationContext.addAllError(errors);*/
			invocationContext.target = TARGET;
	    	log.exiting("SaveMailAcceptanceCommand","execute");
	    	
    	}
    }
    
    /**
     * 
     * @param mailAcceptanceVO
     */
    public void updateDeletedMailbs(MailAcceptanceVO mailAcceptanceVO){
    	Collection<ContainerDetailsVO> containerDetailsVOs = mailAcceptanceVO.getContainerDetails();
		if(containerDetailsVOs != null && !containerDetailsVOs.isEmpty()){
			for(ContainerDetailsVO conVO : containerDetailsVOs){
				if(conVO.isMailModifyflag()){
					Collection<MailbagVO> mailVOs =  conVO.getMailDetails();
			    	Collection<MailbagVO> delMails = conVO.getDeletedMailDetails();
			    	if(mailVOs != null && delMails != null && !mailVOs.isEmpty() && !delMails.isEmpty()){
		    			for(MailbagVO delMail : delMails){
		    				if(MailbagVO.OPERATION_FLAG_DELETE.equals(delMail.getOperationalFlag())){
		    					mailVOs.add(delMail);
		    				}
			    		}
		    			for(MailbagVO mail : mailVOs){
		    				if(MailbagVO.OPERATION_FLAG_INSERT.equals(mail.getOperationalFlag())){
		    					mail.setMailSequenceNumber(0);
		    				}
			    		}
			    	}
				}
			}
		}	
    }
       
}
