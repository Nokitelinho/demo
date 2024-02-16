/*
 * ListAccessoriesStockCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainaccessories;


import java.util.Collection;
import java.util.ArrayList;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;

import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;



import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainAccessoriesStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainAccessoriesStockForm;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;

/**
 * This command class is used to list the stock 
 * details of the specified accessory  
 * @author A-1347
 */
public class ListAccessoriesStockCommand extends BaseCommand {
    
	private Log log = LogFactory.getLogger("MAINTAIN ACCESSORIES");
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREEN_ID = 
							"uld.defaults.maintainaccessoriesstock";
    private static final String LIST_SUCCESS = "list_success";
    private static final String LIST_FAILURE = "list_failure";
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListAccessoriesStockCommand", "execute");
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
             	
		MaintainAccessoriesStockSession maintainAccessoriesStockSession = 
			(MaintainAccessoriesStockSession ) 
								getScreenSession(MODULE_NAME, SCREEN_ID);
    	MaintainAccessoriesStockForm maintainAccessoriesStockForm = 
    				(MaintainAccessoriesStockForm)invocationContext.screenModel;
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ErrorVO error = null;
		
    	
    	AccessoriesStockConfigVO accessoriesStockConfigVO = 
    									new AccessoriesStockConfigVO();
    	ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
    	    	 
    	String accessoryCode=
				maintainAccessoriesStockForm.getAccessoryCode().toUpperCase();
    	String companyCode=logonAttributes.getCompanyCode().toUpperCase();
    	String stationCode=maintainAccessoriesStockForm.getStationCode().toUpperCase();
    	String airlineCode=maintainAccessoriesStockForm.getAirlineCode().toUpperCase();
    	int	airlineIdentifier=0;
    	
    	errors=validateForm(maintainAccessoriesStockForm,companyCode,airlineCode,stationCode,airlineIdentifier);
    	  	
    	if (errors != null && errors.size() > 0) {	

       	 log.log(Log.INFO, "invocationCtxt.target:", invocationContext.target);
       
   		}else{
   			try{
				AirlineValidationVO airlineValidationVO = null;
				AirlineDelegate airlineDelegate = new AirlineDelegate(); 
	    		airlineValidationVO=airlineDelegate.
	    						validateAlphaCode(companyCode,airlineCode);
	    		airlineIdentifier=
	    					airlineValidationVO.getAirlineIdentifier();
	   maintainAccessoriesStockForm.setAirlineIdentifier(airlineIdentifier);    		    		
	    		log.log(Log.FINE, "airlineidentifier--->", airlineIdentifier);
	    		}catch(BusinessDelegateException businessDelegateException){
	        	errors =handleDelegateException(businessDelegateException);	
	    		}
	    	try{
	    		accessoriesStockConfigVO = 
	    		  uldDefaultsDelegate.findAccessoriesStockDetails(
	    			companyCode,accessoryCode,stationCode,airlineIdentifier);
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    	}
	    	if(accessoriesStockConfigVO == null){
	    			
	    			maintainAccessoriesStockForm.setStatusFlag("I");
	    		//	log.log(Log.FINE,"vo null...accessoriesStockConfigVO-->"+accessoriesStockConfigVO);
	    			error = new ErrorVO
	    					  ("uld.defaults.acessoriesstockconfigvonotfound");
					error.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(error);
			
			}
   		}
		if (errors != null && errors.size() > 0) {	
			invocationContext.addAllError(errors);
          	 invocationContext.target = LIST_FAILURE;
          	 
      	}else{
    		maintainAccessoriesStockSession.
    					setAccessoriesStockConfigVO(accessoriesStockConfigVO);
    		maintainAccessoriesStockForm.setModeFlag("Y");
    		maintainAccessoriesStockForm.setLovFlag("Y");
			maintainAccessoriesStockForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			maintainAccessoriesStockForm.setStatusFlag("U");
	    	invocationContext.target = LIST_SUCCESS; 
	    }    
	}
	  private Collection<ErrorVO> validateForm
	   				(MaintainAccessoriesStockForm maintainAccessoriesStockForm,String companyCode,
	   						String airlineCode,String stationCode,int airlineIdentifier){
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			log.entering("ListAccessoriesStockCommand","validateForm");
			ErrorVO error = null;
		if(maintainAccessoriesStockForm.getAccessoryCode()==null ||
			maintainAccessoriesStockForm.getAccessoryCode().trim().length()==0){
				error = new ErrorVO
				  ("uld.defaults.maintainaccessories.accessorycode.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if(maintainAccessoriesStockForm.getAirlineCode()==null||
			 maintainAccessoriesStockForm.getAirlineCode().trim().length() ==0){
				error = new ErrorVO
					("uld.defaults.maintainaccessories.airlinecode.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}else{
				try{
					AirlineValidationVO airlineValidationVO = null;
					AirlineDelegate airlineDelegate = new AirlineDelegate(); 
		    		airlineValidationVO=airlineDelegate.
		    						validateAlphaCode(companyCode,airlineCode);
		    		airlineIdentifier=
		    					airlineValidationVO.getAirlineIdentifier();
		   maintainAccessoriesStockForm.setAirlineIdentifier(airlineIdentifier);    		    		
		    		log.log(Log.FINE, "airlineidentifier--->",
							airlineIdentifier);
		    		}catch(BusinessDelegateException businessDelegateException){
		        	errors =handleDelegateException(businessDelegateException);	
		    		}
			}
			if(maintainAccessoriesStockForm.getStationCode()==null||
			  maintainAccessoriesStockForm.getStationCode().trim().length()==0){
				error = new ErrorVO
					("uld.defaults.maintainaccessories.stationcode.mandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}else{
				
				Collection<ErrorVO> errorsAirport = new ArrayList<ErrorVO>();
				try {
					new AreaDelegate().validateAirportCode(
							companyCode,stationCode);
				}
				catch(BusinessDelegateException businessDelegateException) {
			   errorsAirport=handleDelegateException(businessDelegateException);
	   			}
				if (errorsAirport != null && errorsAirport.size() > 0) {
					if(errors!= null){
						errors.addAll(errorsAirport);
					}
					else {
						errors=errorsAirport;
					}
					
				}
			}					
			log.exiting("ListAccessoriesStockCommand","validateForm");
			maintainAccessoriesStockForm.setScreenStatusFlag
			(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			return errors;
		}    
	   
	    }


