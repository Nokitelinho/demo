/*
 * ListCommand.java Created on Sep 30, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.residitrestrictonsetup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResiditRestrictonFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResiditRestrictonVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ResiditRestrictonSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ResiditRestrictonSetUpForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3108
 *
 */
public class ListCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("MailTracking,ResiditRestrictonSetUp");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.residitrestrictionsetup";
	
	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";
	
	//private static final String CARCOD_MANDATORY ="mailtracking.defaults.residitrestrictionsetup.msg.err.carriercodemandatory";
	private static final String NO_RESULTS_FOUND = "mailtracking.defaults.residitrestrictionsetup.noresultsfound";
	private static final String INVALID_CARCOD ="mailtracking.defaults.residitrestrictionsetup.msg.err.invalidcarriercode";
	private static final String INVALID_PACOD ="mailtracking.defaults.residitrestrictionsetup.msg.err.invalidpacode";
	private static final String BLANK = "";
	private static final String NO="N";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the list command----------> \n\n");
    	
    	ResiditRestrictonSetUpForm residitRestrictonSetUpForm =
							(ResiditRestrictonSetUpForm)invocationContext.screenModel;
    	ResiditRestrictonSetUpSession residitRestrictonSetUpSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	   
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    
	    ArrayList<ResiditRestrictonVO> residitRestrictonVOs = null;
	    
	    MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    
	    /*if(residitRestrictonSetUpForm.getCarrierCodeFilter()==null || (residitRestrictonSetUpForm.getCarrierCodeFilter()!=null 
	    		&& residitRestrictonSetUpForm.getCarrierCodeFilter().equals(BLANK))) {
	    	
	    	log.log(Log.FINE, "\n\n carrier Code_EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(CARCOD_MANDATORY);
	    	errors.add(error);
	    	invocationContext.addAllError(errors);
	    	residitRestrictonSetUpSession.setResiditRestrictonVOs(null);
	    	residitRestrictonSetUpForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = FAILURE;
	    	return;
	    	
	    }		*/
	    
	    //populate filter vo
    	
    	ResiditRestrictonFilterVO residitRestrictonFilterVO=new ResiditRestrictonFilterVO ();
    	
    	residitRestrictonFilterVO.setCompanyCode(companyCode);
    	
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
  
    	if(residitRestrictonSetUpForm.getCarrierCodeFilter()!=null 
	    		&& !residitRestrictonSetUpForm.getCarrierCodeFilter().equals(BLANK)) {
    		
    		AirlineValidationVO airlineValidationVO = null;
    		
	    	String carrierCodeFilter = residitRestrictonSetUpForm.getCarrierCodeFilter().toUpperCase().trim();
	    	
	    	if (carrierCodeFilter != null && !BLANK.equals(carrierCodeFilter)) {
	    		try {
	    			airlineValidationVO = airlineDelegate.validateAlphaCode(
	    					logonAttributes.getCompanyCode(),carrierCodeFilter);
	    		}catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    		}
	    		if (airlineValidationVO==null || (errors != null && errors.size() > 0) ){
	    			Object[] obj = {carrierCodeFilter};    		

	    			invocationContext.addError(new ErrorVO(INVALID_CARCOD,obj));
	    			invocationContext.target = FAILURE;
	    			return;
	    		}
	    	}
	    	if(airlineValidationVO!=null) {
				residitRestrictonFilterVO.setCarrierCodeFilter(carrierCodeFilter);
			}
	    	
  
    	}
	    if(residitRestrictonSetUpForm.getAirportCodeFilter()!=null 
	    		&& !residitRestrictonSetUpForm.getAirportCodeFilter().equals(BLANK)) {
	    
		    String airport = residitRestrictonSetUpForm.getAirportCodeFilter().toUpperCase().trim();
		    
	    	try{
	    		new AreaDelegate().validateAirportCode(companyCode,airport);
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    		invocationContext.addAllError(errors);
	    		residitRestrictonSetUpSession.setResiditRestrictonVOs(null);
	    		residitRestrictonSetUpForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = FAILURE;
		    	return;
	    	}
	    	residitRestrictonFilterVO.setAirportCodeFilter(airport);
	    }
	    
	    
	    if(residitRestrictonSetUpForm.getPaCodeFilter()!=null 
	    		&& !residitRestrictonSetUpForm.getPaCodeFilter().equals(BLANK)) {
	    	
	    
	    	String paCodeFilter = residitRestrictonSetUpForm.getPaCodeFilter().toUpperCase().trim();
	    	PostalAdministrationVO paVO=null;
	    	
	    	try{
	    		paVO = delegate.findPACode(companyCode,paCodeFilter);
	    	}
	    	catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    	}
	    	if (paVO==null || (errors != null && errors.size() > 0)) {
	    		
    			Object[] obj = {paCodeFilter};
    			invocationContext.addError(new ErrorVO(INVALID_PACOD,obj));
    			invocationContext.target = FAILURE;
    			return;
    		}
	    	if(paVO!=null) {
				residitRestrictonFilterVO.setPaCodeFilter(paCodeFilter);
			}
	    	
	    		
    	
	    }
    	
	    	
	    try {
	    	
	    	residitRestrictonVOs = (ArrayList<ResiditRestrictonVO>)delegate.findResiditRestrictions(residitRestrictonFilterVO);
	    	log.log(Log.FINE, "\n\n residitRestrictonVOs=========>",
					residitRestrictonVOs);
			if(residitRestrictonVOs == null || residitRestrictonVOs.size()==0) {
	    		log.log(Log.SEVERE,"\n\n *******no match found********** \n\n");
				ErrorVO error = new ErrorVO(NO_RESULTS_FOUND);
				errors.add(error);
				invocationContext.addAllError(errors);
				residitRestrictonSetUpSession.setResiditRestrictonVOs(null);
				residitRestrictonSetUpForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = FAILURE;
		    	return;
	    	}
	    	
	    }catch(BusinessDelegateException businessDelegateException) {
	    	
	    	errors = handleDelegateException(businessDelegateException);
	    }
	  //  residitRestrictonSetUpSession.setAirportCode(airport);
	    residitRestrictonSetUpSession.setResiditRestrictonVOs(residitRestrictonVOs);
	    residitRestrictonSetUpForm.setDisableSave(NO);
	    residitRestrictonSetUpForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    invocationContext.target = SUCCESS;

	}

}
