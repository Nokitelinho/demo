/*
 * PopulateCarrieraNameCommand.java Created on Oct 11, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.residitrestrictonsetup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ResiditRestrictonSetUpForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3108
 *
 */
public class PopulateCarrieraNameCommand extends BaseCommand {


	private Log log = LogFactory.getLogger("MailTracking,ResiditRestrictonSetUp");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.residitrestrictionsetup";
	
	private static final String SUCCESS = "populate_success";
	private static final String FAILURE = "populate_failure";		
	
	
	private static final String FIELD_EMPTY = 
		"mailtracking.defaults.residitrestrictionsetup.msg.err.fieldEmpty";
	
	private static final String INVALID_CARCOD ="mailtracking.defaults.residitrestrictionsetup.msg.err.invalidcarriercode";
	
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
		
    	log.log(Log.FINE, "\n\n in the PopulateCarrieraNameCommand----------> \n\n");    	
		
		
    	ResiditRestrictonSetUpForm residitRestrictonSetUpForm =
						(ResiditRestrictonSetUpForm)invocationContext.screenModel;    	

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (residitRestrictonSetUpForm.getCarrierCode() != null
				&& residitRestrictonSetUpForm.getCarrierCode().length > 0) {
			int len = residitRestrictonSetUpForm.getCarrierCode().length;
			
			for (int i = 0; i < len; i++) {
				
				String carrierCode=residitRestrictonSetUpForm.getCarrierCode()[i].toUpperCase();
				
				if(carrierCode!=null && carrierCode.length() >0){
				
					
					
					AirlineValidationVO airlineValidationVO = null;
					try {
		    			airlineValidationVO = airlineDelegate.validateAlphaCode(
		    					logonAttributes.getCompanyCode(),carrierCode);
		    		}catch (BusinessDelegateException businessDelegateException) {
		    			//errors = handleDelegateException(businessDelegateException);
		    		}
		    		if(airlineValidationVO!=null){
		    			residitRestrictonSetUpForm.setCarrierNameValue(airlineValidationVO.getAirlineName());
		    		}else{
		    			Object[] obj = {carrierCode};  
		    			errors.add(new ErrorVO(INVALID_CARCOD,obj));
		    		}
				}
				else{
					ErrorVO error = new ErrorVO(FIELD_EMPTY,new String[]{"Carrier Code"});
					errors.add(error);
				}
			
			}		
		
    	
		}	
		if(errors != null && errors.size()>0){
			invocationContext.addAllError(errors);			
			invocationContext.target = FAILURE;
	    	return;
		}
		invocationContext.target = SUCCESS;	
	}
		

}
