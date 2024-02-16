/*
 * ShowDamageCommand.java Created on July 01, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dsnenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DamagedDSNSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnEnquiryForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ShowDamageCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET_SUCCESS = "show_damage_success";
	   private static final String TARGET_FAILURE = "show_damage_failure";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";	
	   private static final String DAMAGE_SCREEN_ID = "mailtracking.defaults.damageddsn";
	  
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ShowDamageCommand","execute");
	    	  
	    	DsnEnquiryForm dsnEnquiryForm = 
	    						(DsnEnquiryForm)invocationContext.screenModel;
	    	DsnEnquirySession dsnEnquirySession = 
	    							getScreenSession(MODULE_NAME,SCREEN_ID);
	    	DamagedDSNSession damagedDSNSession = 
								getScreenSession(MODULE_NAME,DAMAGE_SCREEN_ID);
	    	    	
	    	Collection<DespatchDetailsVO> despatchDetailsVOs = 
	    							dsnEnquirySession.getDespatchDetailsVOs();
	    	DespatchDetailsVO selectedvo = null;
	    	
	    	Collection<ErrorVO> errors = null;
	    
	    	String[] selectedRows = dsnEnquiryForm.getSubCheck();    	
	    	int row = 0;
	    	for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
	    		if (row == Integer.parseInt(selectedRows[0])) {
					selectedvo = despatchDetailsVO;
					break;
				}
	    		row++;
	    	}
	    	log.log(Log.FINE, "selectedvo --------->>", selectedvo);
			/*
	    	 * Mail Captured through Capture Consignemnt,But not Accedpted to the system
	    	 * Done for ANZ CR AirNZ1039
	    	 */
			if(MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(selectedvo.getCapNotAcceptedStatus())) {
	    		ErrorVO errorVO = new ErrorVO(
	    		"mailtracking.defaults.err.capturedbutnotaccepted");	
	    		invocationContext.addError(errorVO);			
	    		invocationContext.target = TARGET_FAILURE;
	    		return;
			}
			
			boolean isPltSelected = false;
			
			if (Boolean.valueOf(selectedvo.getPltEnabledFlag())) {
				isPltSelected = true;
			}
			
			
			/*
			 * VALIDATE WHETHER SELECTED DESPATCH IS PLT ENABLED - 
			 * PLT enabled despatches cannot view damage details
			 */ 
			if (isPltSelected) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.dsnenquiry.msg.err.pltEnabledDamage");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);			
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			else {
				dsnEnquiryForm.setStatus("ShowDamagePopup");
			}
	    	damagedDSNSession.setDespatchDetailsVO(selectedvo);
	        	
	    	invocationContext.target = TARGET_SUCCESS;
	       	
	    	log.exiting("ShowDamageCommand","execute");
	    	
	    }     

}

