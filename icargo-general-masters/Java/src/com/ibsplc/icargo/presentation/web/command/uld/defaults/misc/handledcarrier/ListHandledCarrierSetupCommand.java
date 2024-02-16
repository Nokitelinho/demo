/*
 * ListHandledCarrierSetupCommand.java Created on Dec 05, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.handledcarrier;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.vo.AreaValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHandledCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDHandledCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.HandledCarrierSetupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * This command class is used to List   
 * @author A-2883
 *
 */

public class ListHandledCarrierSetupCommand extends BaseCommand{

	private static final String SCREEN_ID = 
		"uld.defaults.misc.handledcarriersetup";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	
	 /**
	   * execute method
	   * @param invocationContext
	   * @throws CommandInvocationException
	   */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("ListHandledCarrierSetupCommand","execute");
		
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        HandledCarrierSetupForm handledCarrierSetupForm= (HandledCarrierSetupForm)invocationContext.screenModel;
        ULDHandledCarrierSession uldCarrierSession = 
    								getScreenSession(MODULE_NAME,SCREEN_ID);
        
        ULDHandledCarrierVO handledCarrierSetupVO =new ULDHandledCarrierVO();
        handledCarrierSetupVO.setCompanyCode(logonAttributes.getCompanyCode().toUpperCase());
        
        if(handledCarrierSetupForm.getAirlineCode()!=null 
        		&& handledCarrierSetupForm.getAirlineCode().trim().length()>0){
        	handledCarrierSetupVO.setAirlineCode(handledCarrierSetupForm.getAirlineCode());
        }else{
        	handledCarrierSetupVO.setAirlineCode("");
        }
        if(handledCarrierSetupForm.getStationCode()!=null 
        		&& handledCarrierSetupForm.getStationCode().trim().length()>0){
        	handledCarrierSetupVO.setStationCode(handledCarrierSetupForm.getStationCode());
        }else{
        	handledCarrierSetupVO.setStationCode("");
        }
        Collection<ULDHandledCarrierVO> uldHandledCarrierVOs =new ArrayList<ULDHandledCarrierVO>();
				// validate
		    	Collection<ErrorVO> error=new ArrayList<ErrorVO>();
		    	if(!("").equals(handledCarrierSetupVO.getStationCode())){
		    		error=validateStationCode(handledCarrierSetupVO.getStationCode());
			    	errorVOs.addAll(error);
		    	}
		    	if(!("").equals(handledCarrierSetupVO.getAirlineCode())){
		    		error=validateAirlineCode(handledCarrierSetupVO.getAirlineCode());
			    	errorVOs.addAll(error);
		    	}
		    	if(errorVOs!=null && errorVOs.size()>0){
			    		invocationContext.addAllError(errorVOs); 
			    		invocationContext.target=SCREENLOAD_FAILURE;
			    }else{
			    		try{
					        	log.log(Log.FINE, " \n bbeefore delegate",
										handledCarrierSetupVO);
								ULDDefaultsDelegate uLDDefaultsDelegate = new ULDDefaultsDelegate();
					        	uldHandledCarrierVOs = 
					    			uLDDefaultsDelegate.findHandledCarrierSetup(
					    					handledCarrierSetupVO);
			    		}catch(BusinessDelegateException businessDelegateException){
					    		errorVOs = handleDelegateException(businessDelegateException);
					        	invocationContext.addAllError(errorVOs);
					        	invocationContext.target=SCREENLOAD_FAILURE;
			    			}
			   }	
		    	if(uldHandledCarrierVOs.size()==0){
		    		ErrorVO errorVO =new ErrorVO("uld.defaults.misc.handledcarriersetup.norecordsfound");
		    		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
		    		errorVOs.add(errorVO);
		    		invocationContext.addAllError(errorVOs);
		    		invocationContext.target=SCREENLOAD_SUCCESS;
		    	}
			   Collection<ULDHandledCarrierVO> sessiondHandledCarrierVOs =new ArrayList<ULDHandledCarrierVO>();
			    for(ULDHandledCarrierVO vo :uldHandledCarrierVOs){
			    	vo.setOperationFlag("NA");
			    	sessiondHandledCarrierVOs.add(vo);
			    }
				uldCarrierSession.setULDHandledCarrierVO(sessiondHandledCarrierVOs);
				invocationContext.target=SCREENLOAD_SUCCESS;
		}
	
	
		private Collection<ErrorVO> validateStationCode(
				String station) {
			
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
	
			AreaValidationVO areaValidationVO = null;
			try {
				AreaDelegate delegate = new AreaDelegate();
	
				areaValidationVO = delegate.validateLevel(logonAttributes
						.getCompanyCode().toUpperCase(), "STN", station
						.toUpperCase());
			} catch (BusinessDelegateException e) {
	
				errors = handleDelegateException(e);
				
			}
			
			return errors;
		}
	 
		private Collection<ErrorVO> validateAirlineCode(
					 String airlineCode) {
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				ApplicationSessionImpl applicationSession = getApplicationSession();
				LogonAttributes logonAttributes = applicationSession.getLogonVO();
				AirlineValidationVO airlineValidationVO = null;
				try {
					AirlineDelegate delegate = new AirlineDelegate();
					airlineValidationVO = delegate.validateAlphaCode(logonAttributes
							.getCompanyCode().toUpperCase(), airlineCode
							.toUpperCase());
	
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				return errors;
			}
		

}
