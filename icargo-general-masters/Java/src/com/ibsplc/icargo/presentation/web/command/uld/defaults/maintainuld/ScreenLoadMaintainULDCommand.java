/*
 * ScreenLoadMaintainULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the ULD screen
 * 
 * @author A-2001
 */
public class ScreenLoadMaintainULDCommand extends BaseCommand {
    
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Maintain Uld Discripency");
	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen 
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String WEIGHTUNIT_ONETIME = "shared.defaults.weightUnitCodes";
	private static final String DIMENTIONUNIT_ONETIME = "shared.defaults.dimensionUnitCodes";
	private static final String OPERATSTATUS_ONETIME = "uld.defaults.operationalStatus";
	private static final String ULDNATURE_ONETIME = "uld.defaults.uldnature";
	private static final String CLEANSTATUS_ONETIME = "uld.defaults.cleanlinessStatus";
	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
	private static final String CONTOUR_ONETIME = "operations.flthandling.uldcontour";
	private static final String FACILITYTYPE_ONETIME = "uld.defaults.facilitytypes";
    
    										   
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	//Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471)
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471)
    	MaintainULDSession maintainULDSession = getScreenSession(MODULE, SCREENID);
    	maintainULDSession.setULDMultipleVOs(null);
    	maintainULDSession.setUldNosForNavigation(null);
    	maintainULDSession.setUldNumbers(null);
    	maintainULDSession.setUldNumbersSaved(null);
    	maintainULDSession.setULDVO(null);
    	maintainULDSession.setPageURL(null);
    	maintainULDSession.setULDFlightMessageReconcileDetailsVO(null);
    	HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
    	maintainULDSession.setOneTimeValues(oneTimeValues);
    	populateCurrency(maintainULDSession);
    	MaintainULDForm maintainULDForm = (MaintainULDForm) invocationContext.screenModel;
    	maintainULDForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	
    	//Added by A-5265 for CR ICRD-46939 starts
		UnitRoundingVO unitRoundingVO = null;
		try {

			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					logonAttributes.getAirportCode(), UnitConstants.WEIGHT);

		} catch (UnitException unitException) {
			log.log(Log.INFO, "*****in the exception");
			unitException.getMessage();
		}
		maintainULDSession.setWeightVO(unitRoundingVO);
		maintainULDForm.setDisplayTareWeightUnit(unitRoundingVO.getUnitCode());
		maintainULDForm.setDisplayStructuralWeightUnit(unitRoundingVO.getUnitCode());
		//Added by A-5265 for CR ICRD-46939 ends
		//Added by A-3415 for ICRD-113953 Starts
		Collection<String> systemparameterCodes = new  ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		systemparameterCodes.add(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS);
		try {
			map = new SharedDefaultsDelegate().findSystemParameterByCodes(systemparameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
		} 
		if(map!=null){
			maintainULDSession.setNonOperationalDamageCodes(map.get(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS));
		}
		//Added by A-3415 for ICRD-113953 Ends
		
    	//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471)
    	//reomved by nisha on 29Apr08
    	
    	log.log(Log.FINE, "logonAttributes.getCompanyCode()------->",
				logonAttributes.getCompanyCode());
		log.log(Log.FINE, "logonAttributes.getUserId()     ------->",
				logonAttributes.getUserId());
			maintainULDForm.setOperationalAirlineCode("");
	
    	//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471) ends
    	
			AreaDelegate areaDelegate = new AreaDelegate();
	    	String defCur="";
			try {
				defCur = areaDelegate.defaultCurrencyForStation(
						getApplicationSession().getLogonVO().getCompanyCode(),
						getApplicationSession().getLogonVO().getStationCode());
			} catch (BusinessDelegateException e) {
				e.getMessage();
			}
			maintainULDForm.setUldPriceUnit(defCur);
			maintainULDForm.setIataReplacementCostUnit(defCur);
			maintainULDForm.setCurrentValueUnit(defCur);
			log.log(Log.FINE, "\n ,uiuhuhuhuh", maintainULDForm.getCurrentValueUnit());
		maintainULDForm.setOverallStatus("O");
    	maintainULDForm.setDamageStatus("N");
    	maintainULDForm.setCleanlinessStatus("C");
    	maintainULDForm.setSaveIndFlag(""); 
    	maintainULDForm.setStatusFlag("screenload");
    	invocationContext.addAllError(error);
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
    	
    	parameterTypes.add(WEIGHTUNIT_ONETIME);
    	parameterTypes.add(DIMENTIONUNIT_ONETIME);
    	parameterTypes.add(OPERATSTATUS_ONETIME);
    	parameterTypes.add(ULDNATURE_ONETIME);    	
    	parameterTypes.add(CLEANSTATUS_ONETIME);
    	parameterTypes.add(DAMAGESTATUS_ONETIME);
    	parameterTypes.add(CONTOUR_ONETIME);
    	parameterTypes.add(FACILITYTYPE_ONETIME);  	
    	
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
    
    private void populateCurrency(MaintainULDSession maintainULDSession) {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ArrayList<CurrencyVO> currencies = null;
    	try {
    		currencies = (ArrayList<CurrencyVO>)new CurrencyDelegate().
    					findAllCurrencyCodes(logonAttributes.getCompanyCode());
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		maintainULDSession.setCurrencies(currencies);
		
	}
}
