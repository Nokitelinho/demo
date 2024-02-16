/*
 * ViewULDCommand.java Created on Sept 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.monitoruldstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MonitorULDStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MonitorULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * ViewULDCommand screen
 * 
 * @author A-1862
 */

public class ViewULDCommand extends BaseCommand {
    
	private Log log = LogFactory.getLogger("MonitorULD Stock");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.monitoruldstock";
	private static final String SCREEN_ID ="uld.defaults.listuld";
	private static final String HEADQUARTERS="HDQ";
	private static final String AIRPORT="ARP";
	private static final String FACILITY="FAC";
	private static final String COUNTRY="CNT";
	private static final String REGION="REG";
	private static final String LEVEL_TYPE="uld.defaults.leveltype";
	private static final String HDQ_ULDCONTROLLER="uld.defaults.headquartersuldcontroller";
	private static final String REG_ULDCONTROLLER="uld.defaults.regionuldcontroller";
	private static final String ARP_ULDCONTROLLER="uld.defaults.airportuldcontroller";
	private static final String CNT_ULDCONTROLLER="uld.defaults.countryuldcontroller";
	
	private static final String VIEW_SUCCESS = "view_success";
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    
		
    	log.entering("Monitor Stock", "Send SCM Message Command");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		MonitorULDStockSession monitorULDStockSession = (MonitorULDStockSession) getScreenSession(
				MODULE, SCREENID);
		MonitorULDStockForm monitorULDStockForm = (MonitorULDStockForm) invocationContext.screenModel;

		ULDStockConfigFilterVO uldStockConfigFilterVO =  monitorULDStockSession.getULDStockConfigFilterVO();
		log.log(Log.FINE, "uldStockConfigFilterVO----@@@@----->",
				uldStockConfigFilterVO);
		Page<ULDStockListVO> stockListVOs = monitorULDStockSession
				.getULDStockListVO();		
		//added by a-3045 for bug 18954 starts
		//added for navigating to LIST ULD screen by checking child rows also.
		//checked gives index of the parentrows and childchecked gives the index of the childrows
		String[] checked = null;
		String[] childchecked = null;
		if(monitorULDStockForm.getSelectedChildRows() != null && monitorULDStockForm.getSelectedChildRows().length > 0){
			int size = monitorULDStockForm.getSelectedChildRows().length;
			checked = new String[size];
			childchecked = new String[size];
			String[] splitString = new String[2];			
			for(int i = 0;i < size;i++){				
				log
						.log(
								Log.FINE,
								"monitorULDStockForm.getSelectedChildRows()[i]------------>",
								monitorULDStockForm.getSelectedChildRows(), i);
				if(monitorULDStockForm.getSelectedChildRows()[i] != null){					
					splitString = monitorULDStockForm.getSelectedChildRows()[i].split("-");					
					checked[i] = splitString[0];
					childchecked[i] = splitString[1];
					log.log(Log.FINE, "checked[i]------------>", checked, i);
					log.log(Log.FINE, "childchecked[i]------------>",
							childchecked, i);
				}				
			}			
		}else if(monitorULDStockForm.getSelectedRows() != null && monitorULDStockForm.getSelectedRows().length > 0){
			checked = monitorULDStockForm.getSelectedRows();			
		}
		log.log(Log.FINE, "stockListVOs----------->", stockListVOs);
		//added by a-3045 for bug 18954 ends
	   	if (stockListVOs != null && stockListVOs.size() > 0) {
	   		ListULDSession listULDSession = 
				(ListULDSession)getScreenSession(MODULE,SCREEN_ID);
	   		ULDStockListVO stockListVO = new ULDStockListVO();
			ULDListFilterVO uldListFilterVO = new ULDListFilterVO();
			//added by a-3045 for bug 18954 starts
			//On selecting childrow uldnature and uldtypecode must be passed as filters,
			//On selecting parentrow uldgroupcode must be passed.
			if(monitorULDStockForm.getSelectedChildRows() != null && monitorULDStockForm.getSelectedChildRows().length > 0){
				ArrayList<ULDStockListVO> uldStockListVOs = new ArrayList<ULDStockListVO>();				
				uldStockListVOs = (ArrayList<ULDStockListVO>)stockListVOs.get(Integer
						.parseInt(checked[0])).getUldStockLists();
				stockListVO = uldStockListVOs.get(Integer
						.parseInt(childchecked[0]));
				uldListFilterVO.setUldNature(stockListVO.getUldNature());
	    		uldListFilterVO.setUldTypeCode(stockListVO.getUldTypeCode());
	    		uldListFilterVO.setLevelType(monitorULDStockForm.getLevelType());
	    		//Added for Bug 108362 by Vivek.Perla on 16Feb2011
	    		uldListFilterVO.setLevelValue(monitorULDStockForm.getLevelValue());
			}else if(monitorULDStockForm.getSelectedRows() != null && monitorULDStockForm.getSelectedRows().length > 0){
				stockListVO = stockListVOs.get(Integer
						.parseInt(checked[0]));
				uldListFilterVO.setUldGroupCode(stockListVO.getUldGroupCode());
				//Added for Bug 108362 by Vivek.Perla on 16Feb2011
	    		uldListFilterVO.setLevelValue(monitorULDStockForm.getLevelValue());				
				uldListFilterVO.setLevelType(monitorULDStockForm.getLevelType());
				//added by a-3278 for bug 48050 on 26May09
				//On selecting parentrow if ULDNature and ULDTypeCode should be passed if present in filter
				if(uldStockConfigFilterVO.getUldNature()!= null &&
						uldStockConfigFilterVO.getUldNature().trim().length() > 0){
					uldListFilterVO.setUldNature(uldStockConfigFilterVO.getUldNature());
				}
				if(uldStockConfigFilterVO.getUldTypeCode() != null &&
						uldStockConfigFilterVO.getUldTypeCode().trim().length() > 0){
					uldListFilterVO.setUldTypeCode(uldStockConfigFilterVO.getUldTypeCode());
				}
				//added by a-3278 for bug 48050 on 26May09 ends
			}
			//added by a-3045 for bug 18954 ends
			uldListFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			uldListFilterVO.setAirlineCode(stockListVO.getAirlineCode());
			uldListFilterVO.setCurrentStation(stockListVO.getStationCode());			
			uldListFilterVO.setUldRangeFrom(-1);
			uldListFilterVO.setUldRangeTo(-1);
			int airlineId = 
				validateAirline(logonAttributes.getCompanyCode(),uldListFilterVO);
			uldListFilterVO.setAirlineidentifier(airlineId);			
			// Added as part of ICRD-334152
			if(uldStockConfigFilterVO.getOwnerAirlineIdentifier()!=0 && uldStockConfigFilterVO.getOwnerAirline()!=null) {
				uldListFilterVO.setOwnerAirlineidentifier(uldStockConfigFilterVO.getOwnerAirlineIdentifier());	
				uldListFilterVO.setOwnerAirline(uldStockConfigFilterVO.getOwnerAirline());
			}/*else {
				uldListFilterVO.setOwnerAirlineidentifier(airlineId);	
				uldListFilterVO.setOwnerAirline(stockListVO.getAirlineCode());
			}*/
			// Added as part of ICRD-334152 ends
			listULDSession.setListFilterVO(uldListFilterVO);
			HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();	
			//Added by A-4072 for Bug ICRD-201843 Starts
			listULDSession.setLevelTypeValues(getSessionLevelTypeOneTimes(oneTimeValues));
			//Added by A-4072 for Bug ICRD-201843 End
			listULDSession.setOneTimeValues(monitorULDStockSession.getOneTimeValues());
			log.log(Log.FINE, "uldListFilterVOe------------>", uldListFilterVO);
			listULDSession.setIsListed(true);
			//listULDSession.setOnetImeValues();
			listULDSession.setListStatus("monitorStock");
		}		
		invocationContext.target = VIEW_SUCCESS;
        
    }
 
    private int validateAirline(String companyCode,ULDListFilterVO uldListFilterVO) {
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;		
		AirlineValidationVO airlineVO = null;
		if(uldListFilterVO.getAirlineCode() != null 
				&& uldListFilterVO.getAirlineCode().trim().length() > 0) {
				
					Collection<ErrorVO> errorsAirline = null;
					try {
						airlineVO = new AirlineDelegate().validateAlphaCode(
								companyCode,
								uldListFilterVO.getAirlineCode().toUpperCase());
					}
					catch(BusinessDelegateException businessDelegateException) {
						log.log(Log.FINE, "InvalidAlphaCode------------>" );
	       			}
									
		}
		int airlineIdentifier=0;
		if(airlineVO != null) {
			airlineIdentifier = airlineVO.getAirlineIdentifier();			
		}
		return airlineIdentifier;
	}
    
    /**
	 * Method to populate the collection of leveltype
	 * onetime parameters
	 * Added by A-4072 for Bug ICRD-201843
     * @return oneTimeValues
     */
    private Collection<OneTimeVO> getSessionLevelTypeOneTimes(Map<String, Collection<OneTimeVO>> oneTimeValues){    	
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
    	return sessionLevelTypeOneTimes;
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
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
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
    	    	
    	parameterTypes.add(LEVEL_TYPE);
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
	}
}
