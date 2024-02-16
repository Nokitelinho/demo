/*
 * AddRouteCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;

import java.util.ArrayList;
import java.util.Collection;   

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class AddRouteCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.consignment";	
   private static final String TARGET = "success";
   
	 /**     
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AddRouteCommand","execute");
    	    
    	/**
		 * Obtain the logonAttributes
		 */
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		/*String  compCode = logonAttributes.getCompanyCode();*/		
		
		Collection<ErrorVO> errors = null;
    	  
    	ConsignmentForm consignmentForm = 
    		(ConsignmentForm)invocationContext.screenModel;
    	ConsignmentSession consignmentSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();    	
    	
    	
    	ConsignmentDocumentVO consignmentDocumentVO = consignmentSession.getConsignmentDocumentVO();
    	Collection<RoutingInConsignmentVO> routingInConsignmentVOs = consignmentDocumentVO.getRoutingInConsignmentVOs();
    	
//		Check for same POL and POU
		int sameAP = 0;
		if(routingInConsignmentVOs != null && routingInConsignmentVOs.size() > 0) {
		    for(RoutingInConsignmentVO routingVO:routingInConsignmentVOs) {
		    	if(!"D".equals(routingVO.getOperationFlag())){ 
		    		if(routingVO.getPol() != null && !"".equals(routingVO.getPol())
		    			&& routingVO.getPou() != null && !"".equals(routingVO.getPou())){
		    	String pol = routingVO.getPol().toUpperCase();	 
		    	String pou = routingVO.getPou().toUpperCase();	
	        		if (pol.equals(pou)) {  
	        			sameAP = 1;
	        			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.sameairport",
	    	   				new Object[]{new StringBuilder(routingVO.getOnwardCarrierCode())
	        				.append("").append(routingVO.getOnwardFlightNumber()).toString()}));
		    		}
		    	}
		    		
//		   		 VALIDATE FLIGHT CARRIER CODE
		    		AirlineDelegate airlineDelegate = new AirlineDelegate();
		    		AirlineValidationVO airlineValidationVO = null;
		    		String flightCarrierCode = routingVO.getOnwardCarrierCode();        	
		        	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
		        		try {        			
		        			airlineValidationVO = airlineDelegate.validateAlphaCode(
		        					logonAttributes.getCompanyCode(),
		        					flightCarrierCode.trim().toUpperCase());

		        		}catch (BusinessDelegateException businessDelegateException) {
		        			errors = handleDelegateException(businessDelegateException);
		        		}
		        		if (errors != null && errors.size() > 0) {            			
		        			errors = new ArrayList<ErrorVO>();
		        			Object[] obj = {flightCarrierCode.toUpperCase()};
		    				  			sameAP = 1;
		    				  			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidCarrier"
		        					,obj));
		        		}
		        	}
		        	
//		        	 VALIDATE POL & POU 
		        	Collection<ErrorVO> polErrors = new ArrayList<ErrorVO>();
		        	Collection<ErrorVO> pouErrors = new ArrayList<ErrorVO>();
	            	String pol = routingVO.getPol();   
	            	String pou = routingVO.getPou();
	            	AreaDelegate areaDelegate = new AreaDelegate();
	            	AirportValidationVO airportValidationVO = null;	            	
	            	if (pol != null && !"".equals(pol)) {
	            		try {
	            			airportValidationVO = areaDelegate.validateAirportCode(
	            					logonAttributes.getCompanyCode(),
	            					pol.toUpperCase());
	            			            			
	            		}catch (BusinessDelegateException businessDelegateException) {
	                		errors = handleDelegateException(businessDelegateException);
	            		}
	            		if (errors != null && errors.size() > 0) {
	            			polErrors.addAll(errors);
	            			}
	            	if (polErrors != null && polErrors.size() > 0) {
	            		Object[] obj = {pol.toUpperCase()};
			  			sameAP = 1;
			  			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidPOL"
	        					,obj));
	        		}}
	            	if (pou != null && !"".equals(pou)) {
	            		try {
	            			airportValidationVO = areaDelegate.validateAirportCode(
	            					logonAttributes.getCompanyCode(),
	            					pou.toUpperCase());
	            			            			
	            		}catch (BusinessDelegateException businessDelegateException) {
	                		errors = handleDelegateException(businessDelegateException);
	            		}
	            		if (errors != null && errors.size() > 0) {
	            			pouErrors.addAll(errors);
	            			}
	            	if (pouErrors != null && pouErrors.size() > 0) {
	            		Object[] obj = {pou.toUpperCase()};
			  			sameAP = 1;
			  			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidPOU"
	        					,obj));
	        		}}
		      }
		    }
		}
		
		
		if(sameAP != 1){

          RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
    	  routingInConsignmentVO.setOperationFlag("I");
    	  if (routingInConsignmentVOs == null) {
    		   routingInConsignmentVOs =  new ArrayList<RoutingInConsignmentVO>();
    	  }
    	  routingInConsignmentVOs.add(routingInConsignmentVO);
    	  consignmentDocumentVO.setRoutingInConsignmentVOs(routingInConsignmentVOs);	
    	  consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);
	   }
	   consignmentForm.setTableFocus("R");	
       consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
       if(formErrors.size()>0){
    	   invocationContext.addAllError(formErrors);
       }
       invocationContext.target = TARGET;
       	
       log.exiting("AddRouteCommand","execute");
    	
    }
       
}
