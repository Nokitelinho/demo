/*
 * SendResditCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-5991
 *
 */
public class SendResditCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";	
   private static final String TARGET_SUCCESS = "send_success";
   private static final String TARGET_FAILURE = "send_failure";
   private static final String UNPROCESSED_RESDIT="mailtracking.defaults.carditenquiry.msg.err.unprocessedresdits";
   public static final String CONST_RESDIT_STATUS_UNPROCESSED = "UP"; 
   public static final String CONST_MESSAGE_INFO_SEND_SUCCESS = "mailtracking.defaults.carditenquiry.msg.info.sendsuccessfully";
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SendResditCommand","execute");    	  
		SearchConsignmentForm carditEnquiryForm = 
	    		(SearchConsignmentForm)invocationContext.screenModel;
			SearchConsignmentSession carditEnquirySession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	
    	
    	Collection<MailbagVO> mailbagVOs = carditEnquirySession.getMailbagVOsCollection();
    	Collection<MailbagVO> selectedMailbagVOs = new ArrayList<MailbagVO>();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	String[] selectedRows = carditEnquiryForm.getSelectMail();

    	CarditEnquiryVO carditEnquiryVO = new CarditEnquiryVO();
    	carditEnquiryVO.setUnsendResditEvent(carditEnquiryForm.getResdit());
    	carditEnquiryVO.setSearchMode(MailConstantsVO.CARDITENQ_MODE_DOC);

     	int size = selectedRows.length;
    	int row = 0;
    	for (MailbagVO mailbagvo : mailbagVOs) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				selectedMailbagVOs.add(mailbagvo);
    			}    			
			}
    		row++;
    	}
    	
    	Collection<MailbagHistoryVO> mailbagHistoryVOs=null;
    	
    	if(selectedMailbagVOs!=null && selectedMailbagVOs.size() > 0){
    		for(MailbagVO mailbagVO : selectedMailbagVOs){
    			boolean processFlag=false;
    			mailbagHistoryVOs=mailbagVO.getMailbagHistories();
    			if(mailbagHistoryVOs!=null && mailbagHistoryVOs.size() > 0){
    				for(MailbagHistoryVO mailbagHistoryVO :mailbagHistoryVOs){
    					if(!CONST_RESDIT_STATUS_UNPROCESSED.equals(mailbagHistoryVO.getMailStatus())){
    						processFlag=true;
    					}
    				}
    			}
    			else{
    				
    				ErrorVO error = new ErrorVO(UNPROCESSED_RESDIT);
					errors.add(error);
					invocationContext.addAllError(errors);   
		            invocationContext.target = TARGET_FAILURE;
					return;
    				
    			}
    			if(!processFlag){
    				
    				ErrorVO error = new ErrorVO(UNPROCESSED_RESDIT);
					errors.add(error);
					invocationContext.addAllError(errors);   
		            invocationContext.target = TARGET_FAILURE;
					return;
    			}
    		}
    	}
    		carditEnquiryVO.setMailbagVos(selectedMailbagVOs);
    		carditEnquiryVO.setContainerVos(null);
    		carditEnquiryVO.setCompanyCode(logonAttributes.getCompanyCode());
    		carditEnquiryVO.setResditEventPort(logonAttributes.getAirportCode());
    		
  
    	log.log(Log.INFO, "carditEnquiryVO....in SendResditCommand....",
				carditEnquiryVO);
		try{
    	     new MailTrackingDefaultsDelegate().sendResdit(carditEnquiryVO);

    	}catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		
		
		ErrorVO error = new ErrorVO(CONST_MESSAGE_INFO_SEND_SUCCESS);
	    	error.setErrorDisplayType(ErrorDisplayType.INFO);
	    	invocationContext.addError(error); 
	    	
	    	carditEnquirySession.removeCarditEnquiryVO();
			carditEnquirySession.setMailbagVOsCollection(null);
			carditEnquirySession.setMailBagVOsForListing(null);
			carditEnquirySession.setTotalPcs(0);
			carditEnquirySession.setTotalWeight(null);
	    	carditEnquiryForm.setCarrierCode("");
	    	carditEnquiryForm.setConsignmentDocument("");
	    	carditEnquiryForm.setDespatchSerialNumber("");
	    	carditEnquiryForm.setDoe("");
	    	carditEnquiryForm.setFlightDate("");
	    	carditEnquiryForm.setFlightNumber("");
	    	carditEnquiryForm.setFromDate("");
	    	carditEnquiryForm.setMailCategoryCode("");
	    	carditEnquiryForm.setMailSubclass("");
	    	carditEnquiryForm.setOoe("");
	    	carditEnquiryForm.setReceptacleSerialNumber("");
	    	carditEnquiryForm.setResdit("");
	    	carditEnquiryForm.setSearchMode("");
	    	carditEnquiryForm.setToDate("");
	    	carditEnquiryForm.setYear("");	
	    	carditEnquiryForm.setPao("");
	    	carditEnquiryForm.setPort("");
	    	carditEnquiryForm.setDeparturePort("");
	    	carditEnquiryForm.setPol("");
	    	carditEnquiryForm.setFlightType("");
	    	carditEnquiryForm.setYear(""); 
	    	carditEnquiryForm.setUldNumber(""); 
	    	carditEnquiryForm.setMailStatus(""); 
	    	carditEnquiryForm.setMailbagId(""); 
	    	carditEnquiryForm.setReqDeliveryDate("");
	    	carditEnquiryForm.setReqDeliveryTime("");
            carditEnquiryForm.setShipmentPrefix("");
	    	carditEnquiryForm.setDocumentNumber("");
	    	carditEnquiryForm.setIsPendingResditChecked("");
	    	carditEnquiryForm.setConsignmentDate("");
	    	carditEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		
   
    	invocationContext.target = TARGET_SUCCESS;
    	log.exiting("SendResditCommand","execute");
    	
    }
       
}
