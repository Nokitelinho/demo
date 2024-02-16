/*
 * ListCommandForMailArrival.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.inventorylist;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.InventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.InventoryListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.InventoryListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-5991
 *
 */
public class ListCommandForMailArrival extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MIALOPERATIONS");
   private static final String TARGET_SUCCESS = "list_success";
   private static final String TARGET_FAILURE = "list_failure";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = 
	   									"mailtracking.defaults.inventorylist";	   
	
   private static final String SCREEN_ID_MAILARRIVAL = "mailtracking.defaults.mailarrival";	
   /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ListCommandForMailArrivalToInventory","execute");
    	InventoryListForm inventoryListForm = 
    		(InventoryListForm)invocationContext.screenModel;
    	InventoryListSession inventoryListSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	//inventoryListSession.removeAllAttributes();
    	inventoryListSession.setInventoryListVO(null);
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		inventoryListForm.setDepPort(logonAttributes.getAirportCode());	

		Collection<ErrorVO> errors = null;
		Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();
		
		//tarun adds
		MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID_MAILARRIVAL);
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		log.log(Log.FINE, "...................", mailArrivalVO.getFlightCarrierCode());
		inventoryListForm.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
		inventoryListForm.setMailtracking("Enable");
		//ends
		
    	// VALIDATING FLIGHT CARRIER
    	String carrier = inventoryListForm.getCarrierCode();
    	AirlineDelegate airlineDelegate = new AirlineDelegate();  
    	AirlineValidationVO airlineValidationVO = null;
    	errors = null;
    	if (carrier != null && !"".equals(carrier)) {
    		
    		try {
    			airlineValidationVO = airlineDelegate.validateAlphaCode(
    					logonAttributes.getCompanyCode(),
    					carrier.trim().toUpperCase());

    		}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
    			errors = new ArrayList<ErrorVO>();
    			Object[] obj = {carrier.toUpperCase()};
				ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.inventorylist.invalidcarrier",obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
    		}
    		if (errors != null && errors.size() > 0) {
    			validationerrors.addAll(errors);
    		}    	
    	} 
    	
    	if (validationerrors != null && validationerrors.size() > 0) {
			invocationContext.addAllError(validationerrors);
			invocationContext.target = TARGET_FAILURE;
			inventoryListForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);  
			return;
		}    				
	
    	InventoryListVO listvo=null;
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
		try {
			listvo = mailTrackingDefaultsDelegate.findInventoryList(logonAttributes.getCompanyCode(),
					inventoryListForm.getDepPort(),airlineValidationVO.getAirlineIdentifier());

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0 ) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			inventoryListForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD); 
			return;
		}	
		if (listvo==null ) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.inventorylist.noresults");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(errorVO);
			invocationContext.target = TARGET_FAILURE;
			inventoryListForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD); 
			return;
		}	
		log.log(Log.FINE, "listvo--------->>", listvo);
		inventoryListForm.setCarrierID(String.valueOf(airlineValidationVO.getAirlineIdentifier()));
		inventoryListSession.setInventoryListVO(listvo);
		inventoryListForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);  	
    	invocationContext.target = TARGET_SUCCESS;       	
    	log.exiting("ListCommand","execute");    	
    }       
}
