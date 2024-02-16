/*
 * ScreenloadSearchContainerCommand.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchcontainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class ScreenloadSearchContainerCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String FLTTYPE_ONETIME = "mailtracking.defaults.operationtype";
   private static final String FLTTYPE_ONETIME_SEARCHMODE = "mailtracking.defaults.containersearchmode";
   private static final String FLTTYPE_ONETIME_SUBCLASSGROUP = "mailtracking.defaults.subclassgroup";
   private static final String OUTBOUND = "O";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenLoadCommand","execute");
    	  
    	SearchContainerForm searchContainerForm = 
    		(SearchContainerForm)invocationContext.screenModel;
    	SearchContainerSession searchContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		Page<ContainerVO> containerVOs = null; 
		SearchContainerFilterVO searchContainerFilterVO = 
									new SearchContainerFilterVO();
		
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		searchContainerSession.setOneTimeValues(oneTimeValues);
		searchContainerFilterVO.setOperationType(OUTBOUND);
		searchContainerSession.setSearchContainerFilterVO(searchContainerFilterVO);
		searchContainerSession.setListContainerVOs(containerVOs);
		
		searchContainerFilterVO.setStrFromDate((new LocalDate(logonAttributes.getAirportCode(),
    			Location.ARP,true)).toDisplayDateOnlyFormat());   		
		searchContainerFilterVO.setStrToDate((new LocalDate(logonAttributes.getAirportCode(),
    			Location.ARP,true)).toDisplayDateOnlyFormat());
		//Added by A-5945 for ICRd-96261
		searchContainerFilterVO.setShowEmptyContainer("Y");
    	searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	searchContainerForm.setDeparturePort(logonAttributes.getAirportCode());
    	
    	searchContainerForm.setAssignedto(CONST_FLIGHT);
    	
    	invocationContext.target = TARGET;
       	
    	log.exiting("ScreenLoadCommand","execute");
    	
    }
    
    /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ScreenLoadCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
    /**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(FLTTYPE_ONETIME);
    	parameterTypes.add(FLTTYPE_ONETIME_SEARCHMODE);   	
    	parameterTypes.add(FLTTYPE_ONETIME_SUBCLASSGROUP);
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
       
}
