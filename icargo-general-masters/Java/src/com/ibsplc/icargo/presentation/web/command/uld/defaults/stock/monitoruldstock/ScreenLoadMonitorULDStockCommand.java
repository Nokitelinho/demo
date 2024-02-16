/*
 * ScreenLoadMonitorULDStockCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.monitoruldstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MonitorULDStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MonitorULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ScreenLoadMonitorULDStockCommand  extends BaseCommand {
private Log log = LogFactory.getLogger("MonitorULD Stock");
	
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.monitoruldstock";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";     
    
	private static final String ULDNATURE_ONETIME = "uld.defaults.uldnature";
    
	// Added by A-3045 for CR QF1012 starts
	private static final String LEVELTYPE_ONETIME = "uld.defaults.leveltype";
	// Added by A-3045 for CR QF1012 ends
	private static final String HDQ_ULDCONTROLLER="uld.defaults.headquartersuldcontroller";
	private static final String REG_ULDCONTROLLER="uld.defaults.regionuldcontroller";
    //private static final String ARP_ULDCONTROLLER="uld.defaults.airportuldcontroller";
	private static final String CNT_ULDCONTROLLER="uld.defaults.countryuldcontroller";
    //private static final String FAC_ULDCONTROLLER="uld.defaults.facilityuldcontroller";
	private static final String FACILITY="FAC";
    
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	MonitorULDStockSession monitorULDStockSession = 
    					(MonitorULDStockSession)getScreenSession(MODULE,SCREENID);
    	MonitorULDStockForm monitorULDStockForm = (MonitorULDStockForm) invocationContext.screenModel;
    	monitorULDStockSession.setOneTimeValues(getOneTimeValues());
    	
		monitorULDStockSession.setULDStockConfigFilterVO(null);
		monitorULDStockSession.setULDStockListVO(null);
    	//clearform(monitorULDStockForm);
    	monitorULDStockForm.setScreenStatusFlag(
		ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	monitorULDStockForm.setLoginStation(logonAttributes.getAirportCode());
    	monitorULDStockForm.setScreenStatus("screenload");
		ULDStockConfigFilterVO uldStockConfigFilterVO = new ULDStockConfigFilterVO();
		//added by a-3045 for CR QF1012 starts
		//added for implementing LevelType combo
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
    	Collection<OneTimeVO> levelType = oneTimeValues.get(LEVELTYPE_ONETIME);
		log.log(Log.FINE, "****levelType  OneTime******", levelType);
		Collection<OneTimeVO> levelTypes =new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> sessionLevelTypeOneTimes=new ArrayList<OneTimeVO>();
		OneTimeVO  onetime= null;
		/*for(OneTimeVO vo : levelType){
			if(!uldStockConfigFilterVO.LEVELTYPE_HEADQUARTERS.equals(vo.getFieldValue())){
				levelTypes.add(vo);	
			}else{
				onetime = vo;
			}
		}
		levelTypes.add(onetime);		
		log.log(Log.FINE, "****levelTypes  OneTime after rearranging ******"+levelTypes);*/
		
		if(levelType!=null){
			for(OneTimeVO timeVO:levelType){
				if(ULDStockConfigFilterVO.LEVELTYPE_HEADQUARTERS.equalsIgnoreCase(timeVO.getFieldValue())){
					levelTypes.add(timeVO);
					log.log(Log.FINE, "From HEADQUATERS",
							ULDStockConfigFilterVO.LEVELTYPE_HEADQUARTERS);
				}
			
			}
			
			for(OneTimeVO timeVO:levelType){
				if(ULDStockConfigFilterVO.LEVELTYPE_REGION.equalsIgnoreCase(timeVO.getFieldValue())){
					levelTypes.add(timeVO);
					log.log(Log.FINE, "From REGION",
							ULDStockConfigFilterVO.LEVELTYPE_REGION);
				}
			
			}
			 
			for(OneTimeVO timeVO:levelType){
				if(ULDStockConfigFilterVO.LEVELTYPE_COUNTRY.equalsIgnoreCase(timeVO.getFieldValue())){
					levelTypes.add(timeVO);
					log.log(Log.FINE, "From COUNTRY",
							ULDStockConfigFilterVO.LEVELTYPE_COUNTRY);
				}
			
			}
			for(OneTimeVO timeVO:levelType){
				if(ULDStockConfigFilterVO.LEVELTYPE_AIRPORT.equalsIgnoreCase(timeVO.getFieldValue())){
					levelTypes.add(timeVO);
					log.log(Log.FINE,"From AIRPORT");
				}
			}
			for(OneTimeVO timeVO:levelType){
				if(FACILITY.equalsIgnoreCase(timeVO.getFieldValue())){
					levelTypes.add(timeVO);
					log.log(Log.FINE,"From Facility");
				}
			}
			for(OneTimeVO timeVO:levelType){
				if(ULDStockConfigFilterVO.LEVELTYPE_AGENT.equalsIgnoreCase(timeVO.getFieldValue())){
					levelTypes.add(timeVO);
					log.log(Log.FINE,"From Agent");
				}
			}
			//Added by A-8445 for CR IASCB-43732
			for(OneTimeVO timeVO:levelType){
				if(ULDStockConfigFilterVO.LEVELTYPE_AIRPORTGROUP.equalsIgnoreCase(timeVO.getFieldValue())){
					levelTypes.add(timeVO);
					log.log(Log.FINE,"From AIRPORT GROUP");
				}
			}
		}
		log.log(Log.FINE, "The ScreenLoad values before chking the privilege ",
				levelTypes);
		try{
   		 if(SecurityAgent.getInstance().checkBusinessPrivilege(HDQ_ULDCONTROLLER)){
   			 sessionLevelTypeOneTimes.addAll(levelTypes);
				 log.log(Log.INFO, "Check for privilege from HEAD QUARTERS",
						sessionLevelTypeOneTimes);
   		 }else if(SecurityAgent.getInstance().checkBusinessPrivilege(REG_ULDCONTROLLER)){
				// levelTypeOneTimeValues.remove(HEADQUARTERS);
   			 for(OneTimeVO headQuarters:levelTypes){
   				 if(!ULDStockConfigFilterVO.LEVELTYPE_HEADQUARTERS.equalsIgnoreCase(headQuarters.getFieldValue())){
   					 sessionLevelTypeOneTimes.add(headQuarters);
   				 }
   			 }
				 log.log(Log.INFO, "Check for privilege from REGION",
						sessionLevelTypeOneTimes);
			 }else if(SecurityAgent.getInstance().checkBusinessPrivilege(CNT_ULDCONTROLLER)){
				// levelTypeOneTimeValues.remove(HEADQUARTERS);
				 //levelTypeOneTimeValues.remove(REGION);
				 for(OneTimeVO regions:levelTypes){
   				 if(!(ULDStockConfigFilterVO.LEVELTYPE_HEADQUARTERS.equalsIgnoreCase(regions.getFieldValue()) || 
   						 ULDStockConfigFilterVO.LEVELTYPE_REGION.equalsIgnoreCase(regions.getFieldValue()))){
   					 sessionLevelTypeOneTimes.add(regions);
   				 }
   			 }
				 log.log(Log.INFO, "Check for privilege from Country",
						sessionLevelTypeOneTimes);
			 }else {
				// levelTypeOneTimeValues.remove(HEADQUARTERS);
				 //levelTypeOneTimeValues.remove(REGION);
				 //levelTypeOneTimeValues.remove(COUNTRY);
				 for(OneTimeVO regions:levelTypes){
   				 if(!(ULDStockConfigFilterVO.LEVELTYPE_HEADQUARTERS.equalsIgnoreCase(regions.getFieldValue()) ||
   						 ULDStockConfigFilterVO.LEVELTYPE_REGION.equalsIgnoreCase(regions.getFieldValue()) || 
   						 ULDStockConfigFilterVO.LEVELTYPE_COUNTRY.equalsIgnoreCase(regions.getFieldValue()))){
   					 sessionLevelTypeOneTimes.add(regions);
   				 }
   			 }
				 log.log(Log.INFO, "Check for privilege at the end",
						sessionLevelTypeOneTimes);
			 }
			
   	}catch(SystemException e){
   		log.log(Log.FINE,"EXCeption in checking Action privilege   ");	
   	}
   	log.log(Log.FINE,
			"The One Time Values for LevelType after privilege check ",
			sessionLevelTypeOneTimes);
		//	log.log(Log.FINE,"The levelType Values after chking the privileges from monitorscreenload"+levelTypes);
		monitorULDStockSession.setLevelType(sessionLevelTypeOneTimes);
		//added by a-3045 for CR QF1012 ends		
		//Added by Tarun as a part of defaulting airline code in page (ANACR - 1471)
		//Commented by Manaf for INT ULD510
		//Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	//reomved by nisha on 30Apr08

			if(logonAttributes.isAirlineUser()){
	    		uldStockConfigFilterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		//Commented by Manaf for BUG 13993
	    		//uldStockConfigFilterVO.setStationCode(logonAttributes.getAirportCode());
	    		monitorULDStockForm.setStockDisableStatus("airline");
	    	}
	    	else{
	    		uldStockConfigFilterVO.setStationCode(logonAttributes.getAirportCode());
	    		monitorULDStockForm.setStockDisableStatus("GHA");
	    	}  
			uldStockConfigFilterVO.setLevelType(ULDStockConfigFilterVO.LEVELTYPE_AIRPORT);
		
		//Added by Tarun as a part of defaulting airline code in page (ANACR - 1471) ends
    	
    	
    	log.log(Log.FINE, "ROLGRP---------------->", logonAttributes.getRoleGroupCode());
		monitorULDStockSession.setULDStockConfigFilterVO(uldStockConfigFilterVO);
    	invocationContext.target = SCREENLOAD_SUCCESS; 	
    	
    	
        
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
    	parameterTypes.add(ULDNATURE_ONETIME); 
    	parameterTypes.add(LEVELTYPE_ONETIME);
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }

}
