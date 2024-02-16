/*
 * ScreenLoadULDMovementHistoryCommand.java Created on Jan, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc.ListULDMovementSessionImpl;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDIntMvtHistorySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * ULDMovementHistory screen
 * 
 * @author A-2122
 */
public class ScreenLoadULDMovementHistoryCommand extends BaseCommand {
	
	
	private Log log = LogFactory.getLogger("ULD MOVEMENT HISTORY");		
	private static final String SCREENID = "uld.defaults.misc.listuldmovement";
	private static final String MODULE = "uld.defaults";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String CURRENTSTATUS_ONETIME = "uld.defaults.overallStatus";
	private static final String CONTENT_ONETIME = "uld.defaults.contentcodes";
	
	private static final String SCREENID_DAMAGE = "uld.defaults.maintaindamagereport";
	private static final String SCREENID_INTMVT = "uld.defaults.misc.uldintmvthistory";
	private static final String SCREENID_TXNDETAILS = "uld.defaults.loanborrowdetailsenquiry";
	
	private static final String RECORD_ULD = "recordUldMovement";
	 private static final String PAGEURL_MAINTAINULD = "maintainuld";
	 private static final String POSITION_ONETIME = "uld.defaults.position";
	 private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
	 private static final String REPAIRSTATUS_ONETIME = "uld.defaults.repairStatus";
	  private static final String SYSPAR_BASE="system.defaults.unit.currency";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ListULDMovementSessionImpl listUldMovementSessionImpl = 
    		(ListULDMovementSessionImpl)getScreenSession(MODULE,SCREENID);
    	
    	ListULDTransactionSession listULDTransactionSession = getScreenSession(
    			MODULE, SCREENID_TXNDETAILS);
		//commented by T-1927 for the bug icrd-30600 
    	//listULDTransactionSession.setTransactionListVO(null);
		//listULDTransactionSession.setTransactionFilterVO(null);		
    	
    	ULDIntMvtHistorySession intMvtSession = getScreenSession(MODULE,
    			SCREENID_INTMVT);
    	
    	intMvtSession.setULDIntMvtHistoryFilterVO(null);
		intMvtSession.setIntULDMvtDetails(null);
    	
    	MaintainDamageReportSession damageSession = getScreenSession(MODULE,
				SCREENID_DAMAGE);
    	
    	 ListULDMovementForm listULDMovementForm = 
  			(ListULDMovementForm) invocationContext.screenModel;
    	HashMap<String,Collection<OneTimeVO>> oneTimeValues= getOneTimeValues();
    	listUldMovementSessionImpl.setOneTimeValues(oneTimeValues);
    	listULDMovementForm.setScreenStatusFlag
    			(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD); 
    	log.log(Log.INFO, "listULDMovementForm.getPageUrl()------",
				listULDMovementForm.getPageUrl());
		log.log(Log.INFO, "listULDMovementForm.getUldNumber()------",
				listULDMovementForm.getUldNumber());
		
		
		Collection<String> systemparameterCodes = new  ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		systemparameterCodes.add(SYSPAR_BASE);
		
		/**
		 * Implementing Exchange Rates according to CRINT1122
		 */	
		
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		/*
		 * Getting system parameters for Exchange Rates and
		 * base Currency
		 */
		try {
			map = sharedDefaultsDelegate.findSystemParameterByCodes(systemparameterCodes);
		}catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			
		}
		String sysparBaseCurrency = map.get(SYSPAR_BASE);
		if(sysparBaseCurrency!=null)
		{
			listULDMovementForm.setSystemCurrency(sysparBaseCurrency);
		}
		
		
		listUldMovementSessionImpl.setTotalRecords(0);
		if(!(PAGEURL_MAINTAINULD.equals(listULDMovementForm.getPageUrl()))){
    		log.log(Log.INFO, " FROM MAINTIANULD");
    		listUldMovementSessionImpl.setUldMovementDetails(null);
    		listUldMovementSessionImpl.setUldMovementFilterVO(null);  
    		listULDMovementForm.setIsUldValid(OneTimeVO.FLAG_NO);
    	}else{
    		listULDMovementForm.setIsUldValid(OneTimeVO.FLAG_YES);
    		ULDMovementFilterVO uLDMovementFilterVO = new ULDMovementFilterVO();
    		uLDMovementFilterVO.setUldNumber(listULDMovementForm.getUldNumber());    		
    		listUldMovementSessionImpl.setUldMovementFilterVO(uLDMovementFilterVO); 
    	}
    	
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
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try******",
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
    	
    	parameterTypes.add(CURRENTSTATUS_ONETIME);    	
    	parameterTypes.add(CONTENT_ONETIME);
    	parameterTypes.add(REPAIRSTATUS_ONETIME);
    	parameterTypes.add(DAMAGESTATUS_ONETIME);
    	parameterTypes.add(POSITION_ONETIME);
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
}



