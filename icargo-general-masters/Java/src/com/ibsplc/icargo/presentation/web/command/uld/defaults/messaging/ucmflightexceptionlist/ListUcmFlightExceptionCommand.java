/*
 * ListUcmFlightExceptionCommand.java Created on Jul 20,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmflightexceptionlist;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMFlightExceptionListSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMFlightExceptionListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to list the UCM error logs
 * @author A-1862
 */

public class ListUcmFlightExceptionCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListUcmFlightException Error Logs");
	private static final String MODULE = "uld.defaults";
	/** 
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID =
		"uld.defaults.ucmflightexceptionlist";
    
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
 
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  companyCode = logonAttributes.getCompanyCode();
		
		UCMFlightExceptionListForm ucmFlightExceptionListForm = 
			(UCMFlightExceptionListForm) invocationContext.screenModel;
		UCMFlightExceptionListSession ucmFlightExceptionListSession = 
			(UCMFlightExceptionListSession)getScreenSession(MODULE,SCREENID);
		Collection<ErrorVO> errors = validateForm(ucmFlightExceptionListForm,
				companyCode);
		if(errors != null &&
				errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			ucmFlightExceptionListForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			return;
		}
		String pol = ucmFlightExceptionListForm.getAirportCode().toUpperCase();
		ArrayList<UCMExceptionFlightVO> ucmExceptionFlightVOs = 
			(ArrayList<UCMExceptionFlightVO>)findExceptionFlights(companyCode,pol);
		ErrorVO err = null;
		ucmFlightExceptionListForm.setListStatus("");
		if(ucmExceptionFlightVOs == null || ucmExceptionFlightVOs.size()==0) {
			ucmExceptionFlightVOs = new ArrayList<UCMExceptionFlightVO>(); 
			//added by jisha for solving bud 12636 
			ucmFlightExceptionListForm.setListStatus("N");
			err = new ErrorVO("uld.defaults.ucmflightexceptionlist.norecordsfound");
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
			invocationContext.addAllError(errors);
			ucmFlightExceptionListForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_FAILURE;
			
		}else{
			ucmFlightExceptionListForm.setListStatus("N");
		}
		ucmFlightExceptionListSession.setUcmExceptionFlightVOs(
				ucmExceptionFlightVOs);
		ucmFlightExceptionListForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		
		invocationContext.target = LIST_SUCCESS;
        
    }
    
    private Collection<UCMExceptionFlightVO> findExceptionFlights(String companyCode, String pol) {
    	Collection<UCMExceptionFlightVO> ucmExceptionFlightVOs = null;
    	try {
    		ucmExceptionFlightVOs =
    			new ULDDefaultsDelegate().findExceptionFlights(
					companyCode, pol);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
    	return ucmExceptionFlightVOs;		
	}
	/**
     * 
     * @param ucmErrorLogForm
     * @param companyCode
     * @return
     */
    
	private Collection<ErrorVO> validateForm
	(UCMFlightExceptionListForm ucmFlightExceptionListForm, String companyCode){
		log.entering("ListUCMErorLogCommand", "validateForm");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(ucmFlightExceptionListForm.getAirportCode()== null || 
				ucmFlightExceptionListForm.getAirportCode().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.ucmflightexceptionlist.airportmandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else {
			if(validateAirportCodes(ucmFlightExceptionListForm.getAirportCode().toUpperCase(),companyCode)!=null){
				errors.add(new ErrorVO("uld.defaults.ucmflightexceptionlist.airportcodeinvalid",null));
			}					
			
		}		
		log.exiting("ListUCMErrorLogCommand", "validateForm");
		return errors;
	}

	/**
	 * 
	 * @param station
	 * @param companyCode
	 * @return
	 */	
	public Collection<ErrorVO> validateAirportCodes(
		String station,
		String companyCode){
		log.entering("ListCommand", "validateAirportCodes");	
		Collection<ErrorVO> errors = null;    	
		try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateAirportCode(
					companyCode,station);			
	
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		log.exiting("ListCommand", "validateAirportCodes");
		return errors;        
	}
	
	
}
