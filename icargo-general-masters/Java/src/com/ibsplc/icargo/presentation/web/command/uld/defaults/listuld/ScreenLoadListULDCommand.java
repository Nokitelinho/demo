/*
 * ScreenLoadListULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * This command class is invoked on the start up of the ULD screen
 * 
 * @author A-1347
 */
public class ScreenLoadListULDCommand extends BaseCommand {
	/** 
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Maintain Uld Discripency");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.listuld";
	private static final String OPERATSTATUS_ONETIME = "uld.defaults.operationalStatus";
	private static final String ULDNATURE_ONETIME = "uld.defaults.uldnature";
	private static final String CLEANSTATUS_ONETIME = "uld.defaults.cleanlinessStatus";
	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
	//Added By A-6841 for CRQ ICRD-155382	
	private static final String OCCUPANCYSTATUS_ONETIME = "uld.defaults.occupiedstatus";
	private static final String INTRANSIT_ONETIME = "uld.defaults.transitstatus";
	private static final String LEVEL_TYPE="uld.defaults.leveltype";
	private static final String CONTENT_TYPE="uld.defaults.contentcodes";
	private static final String FACILITY_TYPE="uld.defaults.facilitytypes";
	private static final String HEADQUARTERS="HDQ";
	private static final String AIRPORT="ARP";
	private static final String FACILITY="FAC";
	private static final String COUNTRY="CNT";
	private static final String REGION="REG";
   
	private static final String HDQ_ULDCONTROLLER="uld.defaults.headquartersuldcontroller";
	private static final String REG_ULDCONTROLLER="uld.defaults.regionuldcontroller";
	private static final String ARP_ULDCONTROLLER="uld.defaults.airportuldcontroller";
	private static final String CNT_ULDCONTROLLER="uld.defaults.countryuldcontroller";
   // private static final String FAC_ULDCONTROLLER="uld.defaults.facilityuldcontroller";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	ListULDSession listULDSession = 
    						(ListULDSession)getScreenSession(MODULE,SCREENID);
    	ListULDForm listULDForm = (ListULDForm) invocationContext.screenModel;
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		log.log(Log.FINE, "The Onetime Values From Screenload", oneTimeValues);
		Collection<OneTimeVO> levelTypeOneTimes=oneTimeValues.get(LEVEL_TYPE);		
		Collection<OneTimeVO> levelTypeOneTimeValues=new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> sessionLevelTypeOneTimes=new ArrayList<OneTimeVO>();
	
		if(levelTypeOneTimes!=null){
					for(OneTimeVO timeVO:levelTypeOneTimes){
						if(HEADQUARTERS.equalsIgnoreCase(timeVO.getFieldValue())){
							levelTypeOneTimeValues.add(timeVO);
							log.log(Log.FINE,"From HEADQUATERS");
						}
					}
					for(OneTimeVO timeVO:levelTypeOneTimes){
						if(REGION.equalsIgnoreCase(timeVO.getFieldValue())){
							levelTypeOneTimeValues.add(timeVO);
							log.log(Log.FINE,"From REGION");
						}
					}
					for(OneTimeVO timeVO:levelTypeOneTimes){
						if(COUNTRY.equalsIgnoreCase(timeVO.getFieldValue())){
							levelTypeOneTimeValues.add(timeVO);
							log.log(Log.FINE,"From COUNTRY");
						}
					}
				
				for(OneTimeVO timeVO:levelTypeOneTimes){
					if(AIRPORT.equalsIgnoreCase(timeVO.getFieldValue())){
						levelTypeOneTimeValues.add(timeVO);
						log.log(Log.FINE,"From AIRPORT");
					}
				}
				for(OneTimeVO timeVO:levelTypeOneTimes){
					if(FACILITY.equalsIgnoreCase(timeVO.getFieldValue())){
						levelTypeOneTimeValues.add(timeVO);
					}
				}
		}
		  //Added by A-7918 for CR-ICRD-233157
		  if(logonAttributes.isGHAUser()&&!logonAttributes.isAirlineUser()&&!logonAttributes.isOwnSalesAgent()&&!logonAttributes.isOtherSalesAgent()){ 
			listULDSession.setDisplayOALULDCheckBox("N"); 
		  	}
		else{
			listULDSession.setDisplayOALULDCheckBox("Y"); 			
			}
    	
		log.log(Log.FINE, "The One Time Values before setting LevelType ",
				levelTypeOneTimeValues);
		try{
    		 if(SecurityAgent.getInstance().checkBusinessPrivilege(HDQ_ULDCONTROLLER)){
    			 sessionLevelTypeOneTimes.addAll(levelTypeOneTimeValues);
				 log.log(Log.INFO, "Check for privilege from HEAD QUARTERS",
						sessionLevelTypeOneTimes);
    		 }else if(SecurityAgent.getInstance().checkBusinessPrivilege(REG_ULDCONTROLLER)){
				// levelTypeOneTimeValues.remove(HEADQUARTERS);
    			 for(OneTimeVO headQuarters:levelTypeOneTimeValues){
    				 if(!HEADQUARTERS.equalsIgnoreCase(headQuarters.getFieldValue())){
    					 sessionLevelTypeOneTimes.add(headQuarters);
    				 }
    			 }
				 log.log(Log.INFO, "Check for privilege from REGION",
						sessionLevelTypeOneTimes);
			 }else if(SecurityAgent.getInstance().checkBusinessPrivilege(CNT_ULDCONTROLLER)){
				// levelTypeOneTimeValues.remove(HEADQUARTERS);
				 //levelTypeOneTimeValues.remove(REGION);
				 for(OneTimeVO regions:levelTypeOneTimeValues){
    				 if(!(HEADQUARTERS.equalsIgnoreCase(regions.getFieldValue()) || REGION.equalsIgnoreCase(regions.getFieldValue()))){
    					 sessionLevelTypeOneTimes.add(regions);
    				 }
    			 }
				 log.log(Log.INFO, "Check for privilege from Country",
						sessionLevelTypeOneTimes);
			 }else {
				// levelTypeOneTimeValues.remove(HEADQUARTERS);
				 //levelTypeOneTimeValues.remove(REGION);
				 //levelTypeOneTimeValues.remove(COUNTRY);
				 for(OneTimeVO regions:levelTypeOneTimeValues){
    				 if(!(HEADQUARTERS.equalsIgnoreCase(regions.getFieldValue()) ||
    						 REGION.equalsIgnoreCase(regions.getFieldValue()) || 
    						 COUNTRY.equalsIgnoreCase(regions.getFieldValue()))){
    					 sessionLevelTypeOneTimes.add(regions);
    				 }
    			 }
				 log.log(Log.INFO, "Check for privilege from Airport",
						sessionLevelTypeOneTimes);
			 }
			
    	}catch(SystemException e){
    		log.log(Log.FINE,"EXCeption in checking Action privilege   ");	
    	}
    	log.log(Log.FINE,
				"The One Time Values for LevelType after privilege check ",
				sessionLevelTypeOneTimes);
		listULDSession.setLevelTypeValues(sessionLevelTypeOneTimes);
    	listULDSession.setOneTimeValues(oneTimeValues);
    	listULDSession.setIsListed(false);
    	listULDSession.setListDisplayPage(null);
    	
    	log.log(Log.FINE, "logonAttributes.isAirlineUser()----------------->",
				logonAttributes.isAirlineUser());
		log.log(Log.FINE, "logonAttributes.isGHAUser()----------------->",
				logonAttributes.isGHAUser());
			/*if("monitorStock".equals(listULDForm.getScreenLoadStatus())) {
    		ULDListFilterVO uldListFilterVO = new ULDListFilterVO();
    		uldListFilterVO.setAirlineCode(listULDForm.getAirlineCode());
    		uldListFilterVO.setCurrentStation(listULDForm.getCurrentStation());
    		uldListFilterVO.setUldTypeCode(listULDForm.getUldTypeCode());
    		uldListFilterVO.setUldRangeFrom(-1);
    		uldListFilterVO.setUldRangeTo(-1);
    		listULDSession.setListFilterVO(uldListFilterVO);
    	}
    	else {*/
    		listULDSession.setListFilterVO(null);
    	//}
    	
    	
    	
		//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471)
    		// Commented by Manaf for INT UlD510
    	//Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	//removed by nisha on 29Apr08
    	
    	log.log(Log.FINE, "logonAttributes.getCompanyCode()------->",
				logonAttributes.getCompanyCode());
		log.log(Log.FINE, "logonAttributes.getUserId()     ------->",
				logonAttributes.getUserId());
		listULDSession.setListStatus("");
    	ULDListFilterVO uldListFilterVO = new ULDListFilterVO();
    	
    		if(logonAttributes.isAirlineUser()){
    			//Commented by A-3415 for ICRD-141280
    			//listULDForm.setAirlineCode(logonAttributes.getOwnAirlineCode());
        		//uldListFilterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
        		listULDForm.setDisableStatus("airline");
        	}
        	else{
        		//uldListFilterVO.setCurrentStation(logonAttributes.getAirportCode());
        		listULDForm.setDisableStatus("GHA");
        	}
    		uldListFilterVO.setCurrentStation(logonAttributes.getAirportCode());
    		//Added by a-3069 for CR1503
        //Commented by a-3045,To date must not be defaulted
    	/*LocalDate dateTo = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String toDateToString = TimeConvertor.toStringFormat(dateTo.toCalendar(),"dd-MMM-yyyy");
		listULDForm.setToDate(toDateToString);*/
		/*log.log(log.FINE,"dateTo"+dateTo);
		log.log(log.FINE,"toDateToString"+listULDForm.getToDate());*/
    	
    	listULDSession.setListFilterVO(uldListFilterVO);
    	listULDForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
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
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
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
    	
    	
    	parameterTypes.add(OPERATSTATUS_ONETIME);
    	parameterTypes.add(CLEANSTATUS_ONETIME);
    	parameterTypes.add(DAMAGESTATUS_ONETIME);
    	parameterTypes.add(ULDNATURE_ONETIME);    	
    	parameterTypes.add(LEVEL_TYPE);
    	parameterTypes.add(CONTENT_TYPE);
    	parameterTypes.add(FACILITY_TYPE);
   
		//Added By A-6841 for CRQ ICRD-155382
    	parameterTypes.add(OCCUPANCYSTATUS_ONETIME);
		parameterTypes.add(INTRANSIT_ONETIME);
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
}
