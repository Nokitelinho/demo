/*
 * DetailListULDCommand.java Created on Aug 1, 2005
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
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is invoked for viewing the detailed information of
 * selected ulds
 * 
 * @author A-2001
 */
public class DetailListULDCommand extends BaseCommand {
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Maintain Uld Discripency");
	private static final String MODULE_LISTULD = "uld.defaults";

	private static final String SCREENID_LISTULD =
		"uld.defaults.listuld";
	
	private static final String MODULE_MAINTAINULD = "uld.defaults";

	private static final String SCREENID_MAINTAINULD =
		"uld.defaults.maintainuld";
	private static final String DETAILSSCREENLOAD_SUCCESS = "detailsscreenload_success";
	private static final String DETAILSSCREENLOAD_FAILURE = "detailsscreenload_failure";
    
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
     * @return 
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	ListULDSession listULDSession = (ListULDSession)getScreenSession(MODULE_LISTULD,SCREENID_LISTULD);
    	log.log(Log.FINE, "____________########***********ULDFILTER------>>>>",
				listULDSession.getListFilterVO());
		MaintainULDSession maintainULDSession = (MaintainULDSession)getScreenSession(MODULE_MAINTAINULD,SCREENID_MAINTAINULD);
    	HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
    	maintainULDSession.setOneTimeValues(oneTimeValues);
    	populateCurrency(maintainULDSession);
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
    	MaintainULDForm maintainULDForm = (MaintainULDForm) invocationContext.screenModel;
    	//Commented by Manaf for INT ULD510 
    	//Page<ULDListVO> uldListVO = listULDSession.getListDisplayPage();
    	
    	/*
    	A-7655 _ 31Mar2017 starts 
    	to set default units in mantain uld when navigating from Monitor Uld stock -> List uld -> maintain uld
    	as part of _ICRD-201867 */
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
		//A-7655 _ 31Mar2017 ends
		
    	//Added by A-5265 for CR ICRD-46939 starts
    	if(maintainULDSession.getWeightVO()==null) {
			UnitRoundingVO unitRoundingVO = null;
			try {
	
				unitRoundingVO = UnitFormatter.getStationDefaultUnit(
						logonAttributes.getAirportCode(), UnitConstants.WEIGHT);
	
			} catch (UnitException unitException) {
				log.log(Log.INFO, "*****in the exception");
				unitException.getMessage();
			}
			maintainULDSession.setWeightVO(unitRoundingVO);
    	}
		//Added by A-5265 for CR ICRD-46939 ends
		
    	ArrayList<String> uldNumbersForNavigation = new ArrayList<String>();
    	ULDVO uldVO = null;
    	Collection<ErrorVO> errors = null;
    	if(maintainULDForm.getUldNumbersSelected() != null &&
    			maintainULDForm.getUldNumbersSelected().length() > 0) {
    		String[] uldNumbersSelected = maintainULDForm.getUldNumbersSelected().split(",");
    		for(int i = 0; i < uldNumbersSelected.length ; i++) {
    			uldNumbersForNavigation.add(uldNumbersSelected[i]);
    		}
    		try {
    			uldVO = new ULDDefaultsDelegate().findULDDetails(
    					 logonAttributes.getCompanyCode(),
    					 uldNumbersForNavigation.get(0)) ;
    		}
    		catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    	   if(errors != null &&
					errors.size() > 0 ) {
					invocationContext.addAllError(errors);
					invocationContext.target = DETAILSSCREENLOAD_FAILURE;
					return;
			}
	    	//uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_UPDATE);
	    	HashMap<String,ULDVO> uldMultipleVOs = new HashMap<String,ULDVO>();
	    	if(uldNumbersForNavigation.size() > 0) {
	    		uldMultipleVOs.put(uldNumbersForNavigation.get(0),uldVO);
	    	}
	    	uldVO.setUldNumber(uldNumbersForNavigation.get(0));	    	  	
	    	loadMaintainULDForm(uldVO,maintainULDForm);
	    	maintainULDForm.setUldNumber(uldNumbersForNavigation.get(0));
	     	maintainULDSession.setUldNosForNavigation(uldNumbersForNavigation);
	     	maintainULDSession.setULDMultipleVOs(uldMultipleVOs);
	     	maintainULDForm.setDisplayPage("1");
	     	maintainULDForm.setCurrentPage("1");
	     	/*maintainULDForm.setOverallStatus("O");
	    	maintainULDForm.setDamageStatus("N");
	    	maintainULDForm.setCleanlinessStatus("C");*/
	     	maintainULDForm.setLastPageNum(Integer.toString(uldNumbersForNavigation.size()));
	     	maintainULDForm.setTotalRecords(Integer.toString(uldNumbersForNavigation.size()));
	    	maintainULDForm.setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	log.log(Log.FINE,
					"____________########***********ULDFILTER------>>>>",
					listULDSession.getListFilterVO());
	    	
    	}
    	else {
    		log
					.log(
							Log.FINE,
							"____________########***********inside details failure ULDFILTER------>>>>",
							listULDSession.getListFilterVO());
			listULDSession.setListStatus("noListForm");
    		invocationContext.target = DETAILSSCREENLOAD_FAILURE;
    		return;
    	}
    	invocationContext.target = DETAILSSCREENLOAD_SUCCESS;
    }
    
    private void loadMaintainULDForm(ULDVO uldVO, MaintainULDForm maintainULDForm) {
		/*if(uldVO.getUldType() != null) {
			maintainULDForm.setUldType(uldVO.getUldType());
		}
		if(uldVO.getOwnerAirlineCode() != null) {
			maintainULDForm.setOwnerAirlineCode(uldVO.getOwnerAirlineCode());
		}*/
		if(uldVO.getUldContour() != null) {
			maintainULDForm.setUldContour(uldVO.getUldContour());
		}
		if(uldVO.getOperationalOwnerAirlineCode() != null) {
			maintainULDForm.setOperationalOwnerAirlineCode(uldVO.getOperationalOwnerAirlineCode());
		}
		maintainULDForm.setDisplayTareWeight(Double.toString(uldVO.getTareWeight()!=null?uldVO.getTareWeight().getRoundedDisplayValue():0.0));
		maintainULDForm.setTareWtMeasure(uldVO.getTareWeight());
		if(uldVO.getTareWeight() != null) {
			maintainULDForm.setDisplayTareWeightUnit(uldVO.getTareWeight().getDisplayUnit());
		}
		maintainULDForm.setDisplayStructuralWeight(Double.toString(uldVO.getStructuralWeight()!=null?uldVO.getStructuralWeight().getRoundedDisplayValue():0.0));
		maintainULDForm.setStructWeightMeasure(uldVO.getStructuralWeight());
		if(uldVO.getStructuralWeight() != null) {
			maintainULDForm.setDisplayStructuralWeightUnit(uldVO.getStructuralWeight().getDisplayUnit());
		}else {
			maintainULDForm.setDisplayStructuralWeightUnit("");
		}
		
		maintainULDForm.setBaseLengthMeasure(uldVO.getBaseLength());
		maintainULDForm.setDisplayBaseLength(Double.toString(uldVO.getBaseLength()!=null?uldVO.getBaseLength().getRoundedDisplayValue():0.0));
		maintainULDForm.setBaseWidthMeasure(uldVO.getBaseWidth());
		maintainULDForm.setDisplayBaseWidth(Double.toString(uldVO.getBaseWidth()!=null?uldVO.getBaseWidth().getRoundedDisplayValue():0.0));
		maintainULDForm.setBaseHeightMeasure(uldVO.getBaseHeight());
		maintainULDForm.setDisplayBaseHeight(Double.toString(uldVO.getBaseHeight()!=null?uldVO.getBaseHeight().getRoundedDisplayValue():0.0));
		//added by A-7815 as part of ICRD-345123
		maintainULDForm.setDisplayDimensionUnit(uldVO.getBaseLength() != null ? uldVO.getBaseLength().getDisplayUnit() : "");

		if(uldVO.getOperationalAirlineCode() != null) {
			maintainULDForm.setOperationalAirlineCode(uldVO.getOperationalAirlineCode());
		}
		if(uldVO.getCurrentStation() != null) {
			maintainULDForm.setCurrentStation(uldVO.getCurrentStation());
		}
		if(uldVO.getOverallStatus() != null) {
			maintainULDForm.setOverallStatus(uldVO.getOverallStatus());
		}
		if(uldVO.getCleanlinessStatus() != null) {
			maintainULDForm.setCleanlinessStatus(uldVO.getCleanlinessStatus());
		}
		if(uldVO.getOwnerStation() != null) {
			maintainULDForm.setOwnerStation(uldVO.getOwnerStation());
		}
		if(uldVO.getLocation() != null) {
			maintainULDForm.setLocation(uldVO.getLocation());
		}
		if(uldVO.getUldNature() != null) {
			maintainULDForm.setUldNature(uldVO.getUldNature());
		}
		if(uldVO.getFacilityType() != null) {
			maintainULDForm.setFacilityType(uldVO.getFacilityType());
		}
		if(uldVO.getDamageStatus() != null) {
			maintainULDForm.setDamageStatus(uldVO.getDamageStatus());
		}
		if(uldVO.getVendor() != null) {
			maintainULDForm.setVendor(uldVO.getVendor());
		}
		maintainULDForm.setTransitStatus(uldVO.getTransitStatus());
		if(uldVO.getManufacturer() != null) {
			maintainULDForm.setManufacturer(uldVO.getManufacturer());
		}
		if(uldVO.getUldSerialNumber() != null) {
			maintainULDForm.setUldSerialNumber(uldVO.getUldSerialNumber());
		}
		if(uldVO.getPurchaseDate() != null) {
			maintainULDForm.setPurchaseDate(
					TimeConvertor.toStringFormat(
						uldVO.getPurchaseDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT));
		}
		if(uldVO.getPurchaseInvoiceNumber() != null) {
			maintainULDForm.setPurchaseInvoiceNumber(uldVO.getPurchaseInvoiceNumber());
		}
		
		maintainULDForm.setUldPrice(Double.toString(uldVO.getDisplayUldPrice()));
		
		if(uldVO.getUldPriceUnit() != null) {
			maintainULDForm.setUldPriceUnit(uldVO.getUldPriceUnit());
		}
		
		maintainULDForm.setIataReplacementCost(Double.toString(uldVO.getDisplayIataReplacementCost()));
		
		if(uldVO.getDisplayIataReplacementCostUnit() != null) {
			maintainULDForm.setIataReplacementCostUnit(uldVO.getDisplayIataReplacementCostUnit());
		}
		
		
		maintainULDForm.setCurrentValue(Double.toString(uldVO.getDisplayCurrentValue()));
		
		
		
		if(uldVO.getCurrentValueUnit() != null) {
			maintainULDForm.setCurrentValueUnit(uldVO.getCurrentValueUnit());
		}
		if(uldVO.getRemarks() != null) {
			maintainULDForm.setRemarks(uldVO.getRemarks());
		}
		if(uldVO.getTsoNumber() != null) {
			maintainULDForm.setTsoNumber(uldVO.getTsoNumber());
		}
		if(uldVO.getManufactureDate() != null) {
			maintainULDForm.setManufactureDate(
					TimeConvertor.toStringFormat(
						uldVO.getManufactureDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT));
		}
		if(uldVO.getLifeSpan() > 0) {
			maintainULDForm.setLifeSpan(""+uldVO.getLifeSpan());
		}
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
    		currencies = (ArrayList<CurrencyVO>)new CurrencyDelegate().findAllCurrencyCodes(
    									logonAttributes.getCompanyCode());
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		maintainULDSession.setCurrencies(currencies);
		
	}
}
