/*
 * ScreenLoadListInvoiceCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listinvoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ScreenLoadListInvoiceCommand  extends BaseCommand {
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Maintain Uld Discripency");
	
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.listinvoice";
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
	private static final String BASE_CURRENCY = "system.defaults.unit.currency" ;
    
        
	private static final String KEY_TXNTYPEONTIME = "uld.defaults.invoicetxntype";
    
	private static final String KEY_PARTYTYPEONTIME = "uld.defaults.PartyType";
    
	  //Added by A-7359 for ICRD-248560
    private static final String SYSPAR_ULDINVCURRENCY = "uld.defaults.uldinvoicingcurrency";
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ListInvoiceSession listInvoiceSession = (ListInvoiceSession)getScreenSession(MODULE,SCREENID);
		ListInvoiceForm listInvoiceForm = (ListInvoiceForm) invocationContext.screenModel;
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		listInvoiceSession.setOneTimeValues(oneTimeValues);
		listInvoiceSession.setChargingInvoiceFilterVO(null);
		listInvoiceSession.setULDChargingInvoiceVO(null);
    	clearform(listInvoiceForm);
    	listInvoiceForm.setAirlineBaseCurrency(getBaseCurrency());
		listInvoiceForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
    
    private void clearform(ListInvoiceForm listInvoiceForm) {
    	listInvoiceForm.setInvoiceRefNumber("");
    	listInvoiceForm.setInvoicedToCode("");
    	listInvoiceForm.setTransactionType("");
    	listInvoiceForm.setDisplayPage("1"); 
    	LocalDate dateFrom = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String dateFromString = TimeConvertor.toStringFormat(dateFrom.toCalendar(),
		"dd-MMM-yyyy");
		listInvoiceForm.setInvoicedDateFrom(dateFromString);
		
		LocalDate dateTo = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String dateToString = TimeConvertor.toStringFormat(dateTo.toCalendar(),
		"dd-MMM-yyyy");
		listInvoiceForm.setInvoicedDateTo(dateToString);
    	
		
	}
    
    private String getBaseCurrency() {
		String airlineBaseCurrency ="";
		
		/*
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			airlineValidationVO = new AirlineDelegate().findAirline(
				logonAttributes.getCompanyCode(),logonAttributes.getOwnAirlineIdentifier());
		} catch (BusinessDelegateException e) {
			error = handleDelegateException(e);
		}
		airlineBaseCurrency = airlineValidationVO.getBaseCurrencyCode();
		*/
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		Collection<String> systemparameterCodes = new ArrayList<String>();
		systemparameterCodes.add(SYSPAR_ULDINVCURRENCY);
		Map<String,String> map = null;
		try{
			 map = new SharedDefaultsDelegate().findSystemParameterByCodes(systemparameterCodes);
		}catch(BusinessDelegateException e) {
			error = handleDelegateException(e);
		}
		if(map != null){
			airlineBaseCurrency = map.get(SYSPAR_ULDINVCURRENCY);
		}
		return airlineBaseCurrency;
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
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
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
    	
    	parameterTypes.add(KEY_TXNTYPEONTIME);
    	parameterTypes.add(KEY_PARTYTYPEONTIME);
    	   	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }

}
