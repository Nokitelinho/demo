/*
 * OKMultipleULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.MaintainULDSessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to perform operations on the ok button click
 *
 * @author A-2001
 */
public class OKMultipleULDCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("OK MULTIPLE ULD");
	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
	private static final String VALIDATE_SUCCESS = "validate_success";
    
	private static final String VALIDATE_FAILURE = "validate_failure";
    
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
log.entering("OKMultipleULDCommand","new");
    	MaintainULDSessionImpl maintainULDSessionImpl = 
    		getScreenSession(MODULE, SCREENID);
    	MaintainULDForm maintainULDForm = 
    						(MaintainULDForm) invocationContext.screenModel;
    	Collection<ErrorVO> errors = validateForm(maintainULDForm);
    	//added by a-3045 for bug 36389 on 24Mar09 starts
    	if(maintainULDForm.getOwnerAirlineCode()!= null && 
				maintainULDForm.getOwnerAirlineCode().trim().length() > 0 || maintainULDForm.getUldOwnerCode()!= null &&
				maintainULDForm.getUldOwnerCode().trim().length() > 0){
	    	ULDVO uldVO = new ULDVO();
			uldVO.setOwnerAirlineCode(maintainULDForm.getOwnerAirlineCode());
			uldVO.setUldOwnerCode(maintainULDForm.getUldOwnerCode());
			maintainULDSessionImpl.setULDVO(uldVO);
    	}
    	//added by a-3045 for bug 36389 ends
    	if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				maintainULDForm.setStatusFlag("");
				invocationContext.target = VALIDATE_FAILURE;
				maintainULDForm.setScreenStatusFlag(
		  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				return;
		}
    	if(maintainULDForm.getTotalNoofUlds() == null ||
    			maintainULDForm.getTotalNoofUlds().trim().length() == 0) {
    		maintainULDForm.setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			
    	}
    	else {
    		maintainULDForm.setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);			
    	}
    	maintainULDForm.setStatusFlag("toPopup");
        invocationContext.target = VALIDATE_SUCCESS;
    }
    
    /**
	 * @param maintainULDForm
	 * @param companyCode 
	 * @return errors
	 */
    
	private Collection<ErrorVO> validateForm(MaintainULDForm maintainULDForm){
			
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		if(maintainULDForm.getOwnerAirlineCode()== null || 
				maintainULDForm.getOwnerAirlineCode().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.airlinecodemandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else {
			
			try {
				new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						maintainULDForm.getOwnerAirlineCode().toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
   			}
			try {
				if(maintainULDForm.getUldOwnerCode().trim().length()!= 0){
				new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						maintainULDForm.getUldOwnerCode().toUpperCase());
			}
			}
			catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
   			}
			//commented by nisha ....this check not required
			/*String airlineCode = "";
	    	try {
				airlineCode = new ULDDefaultsDelegate()
						.findDefaultAirlineCode(logonAttributes.getCompanyCode(),logonAttributes.getUserId());
			} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
				errors = handleDelegateException(businessDelegateException);
			}
			if(airlineCode!=null && airlineCode.trim().length()>0){
			if(!(maintainULDForm.getOwnerAirlineCode().toUpperCase().equals(airlineCode))){
			
				error =new ErrorVO("uld.defaults.maintainuld.err.cannotcreateuld");
				error.setErrorData(new Object[]{maintainULDForm.getOwnerAirlineCode()});
				errors.add(error);
			}
			}*/
			
		}
		
		if(maintainULDForm.getUldType()== null || 
				maintainULDForm.getUldType().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.uldtypemandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else {
				
			Collection<String> uldType = new ArrayList<String>();
			uldType.add(maintainULDForm.getUldType().toUpperCase());
			Collection<ErrorVO> errorsUldType = null;
			try {
				new ULDDelegate().validateULDTypeCodes(logonAttributes.getCompanyCode(),
						uldType);
			} catch (BusinessDelegateException businessDelegateException) {
				errorsUldType = handleDelegateException(businessDelegateException);
			}
			if(errorsUldType != null &&
					errorsUldType.size() > 0) {
				errors.addAll(errorsUldType);
			}
		}
		
		return errors;
	}
}
