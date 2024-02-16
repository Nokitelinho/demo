/*
 * ListExcessStockAirportsCommand.java Created on Oct 22, 2012
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listestimateduldstock;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListExcessStockAirportsSession;

import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListExcessStockAirportsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start 
 * up of the ListExcessStockAirports screen
 * 
 * @author A-2934
 */
public class ListExcessStockAirportsCommand extends BaseCommand {
	private static final String SCREEN_ID = 
							"uld.defaults.stock.findairportswithexcessstock";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String ACCESSORYCODE_ONETIME = "uld.defaults.accessoryCode";
    private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_error";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ListExcessStockAirportsCommand","ListExcessStockAirportsCommand");
    	/**
		 * Obtain the logonAttributes
		 */
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		  String companyCode=logonAttributes.getCompanyCode().toUpperCase();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ListExcessStockAirportsForm listExcessStockAirportsForm = (ListExcessStockAirportsForm)invocationContext.screenModel;
    	
    	log.log(Log.FINE, "listExcessStockAirportsForm.getUldType()",
				listExcessStockAirportsForm.getUldType());
		/*if(listExcessStockAirportsForm.getUldType()!=null){
    	
    	}*/
    
    	Page<ExcessStockAirportVO>  excessStockAirportVOs = null;
    	ListExcessStockAirportsSession listExcessStockAirportsSession =   getScreenSession(MODULE_NAME,SCREEN_ID);
    	String displayPage = listExcessStockAirportsForm.getDisplayPage();
    	String airport="";
    	ExcessStockAirportFilterVO excessStockAirportFilterVO= new ExcessStockAirportFilterVO();
    	if("List".equals(listExcessStockAirportsForm.getScreenInvokeActionStatus())){
    		listExcessStockAirportsForm.setOrigin("");    		
        	excessStockAirportFilterVO.setUldType(listExcessStockAirportsForm.getUldType());
        	excessStockAirportFilterVO.setAirlineCode(listExcessStockAirportsForm.getAirlinecode());
        	if(excessStockAirportFilterVO.getAirlineCode()!=null){
        		Collection<ErrorVO> errorsAirline = null;
        		try{
        			AirlineValidationVO airlineVO = new AirlineDelegate().validateAlphaCode(companyCode,excessStockAirportFilterVO.getAirlineCode());  
        			if(airlineVO!=null){
        				excessStockAirportFilterVO.setAirlineIdentifier(airlineVO.getAirlineIdentifier());
        			}        			
    	   		}catch(BusinessDelegateException businessDelegateException){
    	   			errorsAirline=handleDelegateException(businessDelegateException);
    	   		  if(errors == null) {
    	   		   errors = new ArrayList<ErrorVO>();
    	   		  }
    	   		  errors.addAll(errorsAirline);
    	   		}
        	}
        	excessStockAirportFilterVO.setCompanyCode(companyCode);
        	if(listExcessStockAirportsForm.getAirport()!=null){
        		airport=listExcessStockAirportsForm.getAirport();
        		excessStockAirportFilterVO.setOrigin(airport);
        		listExcessStockAirportsSession.setExcessStockAirportFilterVO(excessStockAirportFilterVO);
        	}
        	log.log(Log.FINE, "excessStockAirportFilterVO",
					excessStockAirportFilterVO);
    	}else{
    		excessStockAirportFilterVO = listExcessStockAirportsSession.getExcessStockAirportFilterVO();
    	}
		
		
		
    	
    	
    	



		
	

        if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!!!!!inside errors!= null");
		}else{
			log.log(Log.FINE,"!!!inside errors== null");
			excessStockAirportVOs = findExcessStockAirportList(
					excessStockAirportFilterVO,displayPage);
	if(excessStockAirportVOs == null || 
	   (excessStockAirportVOs != null && excessStockAirportVOs.size()==0)){
				log.log(Log.FINE,"!!!inside resultList== null");
				listExcessStockAirportsSession.setExcessStockAirportVOs(null);
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.stock.estimateduldstock.msg.err.noaccessorystockconfiglistexists");
				errorVO.setErrorDisplayType(ERROR);
				errors.add(errorVO);

			}
		}
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_FAILURE;
		}else{
			log.log(Log.FINE,"!!!inside resultList!= null");
			listExcessStockAirportsForm.
							setSelectFlag(excessStockAirportVOs.size());
			listExcessStockAirportsSession.setExcessStockAirportVOs(
					excessStockAirportVOs);
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_VIEW);
    		invocationContext.target = LIST_SUCCESS;
		
        log.exiting("ListEstimatedULDStockCommand","execute");
    }
    }
    /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ListExcessStockAirportsCommand","getOneTimeValues");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try{
			log.log(Log.FINE, "OneTimeParameterType",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		}catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"caught businessDelegateException");
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadListAccessoriesStockCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ListExcessStockAirportsCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	parameterTypes.add(ACCESSORYCODE_ONETIME);
   	
    	log.exiting("ListExcessStockAirportsCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
    private Page<ExcessStockAirportVO> findExcessStockAirportList(
    		ExcessStockAirportFilterVO excessStockAirportFilterVO,
    		String displayPage){
    	log.entering("ListExcessStockAirportsCommand","findExcessStockAirportList");
    	Page<ExcessStockAirportVO> excessStockAirportVOs = null;
    	int pageNumber = Integer.parseInt(displayPage);
    	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try{
    		log.log(Log.FINE,"before calling delegate");
    		ULDDefaultsDelegate uLDDefaultsDelegate = new ULDDefaultsDelegate();
    		excessStockAirportVOs = 
    			uLDDefaultsDelegate.findExcessStockAirportList(
    					excessStockAirportFilterVO,pageNumber);
    		log.log(Log.FINE,"after calling delegate");
    	}catch(BusinessDelegateException businessDelegateException){
    		log.log(Log.FINE,"inside findExcessStockAirportList caught busDelgExc");
        	businessDelegateException.getMessage();
        	error = handleDelegateException(businessDelegateException);
    	}
    	log.log(Log.INFO, "excessStockAirportVOs", excessStockAirportVOs);
		log.exiting("ListExcessStockAirportsCommand","findExcessStockAirportList");
    	return excessStockAirportVOs;
    }
}
