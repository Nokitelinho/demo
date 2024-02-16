/*
 * ListConfigureResditCommand.java Created on July 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ResditConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditTransactionDetailVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConfigureResditSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConfigureResditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2001
 *
 */

public class ListConfigureResditCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");  
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.configureresdit";	
   
   /**
    * TARGET
    */
   private static final String LIST_SUCCESS = "list_success";
   private static final String LIST_FAILURE = "list_failure";
  
    /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListConsignmentCommand","execute");
    	  
    	ConfigureResditForm configureResditForm = 
    		(ConfigureResditForm)invocationContext.screenModel;
    	ConfigureResditSession configureResditSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AirlineValidationVO airlineValidationVO = validateForm(
    			logonAttributes.getCompanyCode(), configureResditForm, invocationContext);
    	if(invocationContext.getErrors() != null 
    			&& invocationContext.getErrors().size() > 0) {    		
    		configureResditForm.setScreenStatusFlag(
    				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    		invocationContext.target = LIST_FAILURE;
    		return;
    	}
    	
    	ResditConfigurationVO resditConfigurationVO = null;
    	ArrayList<ResditTransactionDetailVO> resditTransactionDetailVOs = null;
    	try {
    		resditConfigurationVO = new MailTrackingDefaultsDelegate().findResditConfigurationForAirline(
    				logonAttributes.getCompanyCode(), 
    				airlineValidationVO.getAirlineIdentifier());
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
    	
		log.log(Log.FINE, "resditConfigurationVO ----->>>",
				resditConfigurationVO);
		if(resditConfigurationVO != null 
    			&& resditConfigurationVO.getResditTransactionDetails().size() > 0) {
    		resditTransactionDetailVOs =
        		(ArrayList<ResditTransactionDetailVO>) resditConfigurationVO.getResditTransactionDetails();
    		setResditOperationFlags(resditTransactionDetailVOs, AbstractVO.OPERATION_FLAG_UPDATE);    		      	
    		configureResditSession.setResditConfigurationVO(resditConfigurationVO);
    	}
    	else {
    		resditConfigurationVO = configureResditSession.getResditConfigurationVO();
    		resditTransactionDetailVOs =
        		(ArrayList<ResditTransactionDetailVO>) resditConfigurationVO.getResditTransactionDetails();
    		setResditOperationFlags(resditTransactionDetailVOs, AbstractVO.OPERATION_FLAG_INSERT);      		
    	}
    	configureResditSession.getResditConfigurationVO().setCarrierId(
    			airlineValidationVO.getAirlineIdentifier());
		configureResditForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = LIST_SUCCESS;
		log.exiting("ListConsignmentCommand","execute");
	}

	private void setResditOperationFlags(
			ArrayList<ResditTransactionDetailVO> resditTransactionDetailVOs, 
			String operationFlag) {
		for(ResditTransactionDetailVO resditTransactionDetailVO : resditTransactionDetailVOs) {
			resditTransactionDetailVO.setOperationFlag(operationFlag);
		}  
	}

	private AirlineValidationVO validateForm(String companyCode, 
			ConfigureResditForm configureResditForm, InvocationContext invocationContext) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		String airlineCode = configureResditForm.getAirline();
		AirlineValidationVO airlineValidationVO = null;			
		if(airlineCode == null 
				|| airlineCode.trim().length() == 0){
			 error = new ErrorVO(
					 "mailtracking.defaults.configureresdit.msg.err.airlinemandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			 errors.add(error);
		}
		else {			
					
			try {
				airlineValidationVO =
					new AirlineDelegate().validateAlphaCode(
							companyCode, airlineCode.toUpperCase());

			} catch (BusinessDelegateException e) {
				handleDelegateException(e);			
			}

			if (airlineValidationVO == null){
				error = new ErrorVO(
					"mailtracking.defaults.configureresdit.msg.err.invalidairline",
					new Object[]{airlineCode.toUpperCase()});
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		invocationContext.addAllError(errors);
		return airlineValidationVO;
	}

	
	   
}
