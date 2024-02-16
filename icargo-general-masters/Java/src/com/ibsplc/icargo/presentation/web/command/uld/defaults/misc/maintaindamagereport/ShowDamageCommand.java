/*
 * ShowDamageCommand.java Created on Jul 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class ShowDamageCommand  extends BaseCommand {
    
	/**
	 * Logger for Maintain Damage Report
	 */
	private Log log = LogFactory.getLogger("ShowDamageCommand");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
    private static final String SCREEN_ID =
		"uld.defaults.maintaindamagereport";
       
    private static final String MODULE_NAME = "uld.defaults";
    private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
    private static final String OVERALLSTATUS_ONETIME = "uld.defaults.overallStatus";
    private static final String REPAIRSTATUS_ONETIME = "uld.defaults.repairStatus";
    private static final String DAMAGECODE_ONETIME = "uld.defaults.damagecode";
    private static final String POSITION_ONETIME = "uld.defaults.position";
    private static final String SEVERITY_ONETIME = "uld.defaults.damageseverity";
    private static final String REPAIRHEAD_ONETIME = "uld.defaults.repairhead";
   
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("View Command","-------ViewMaintainDamageCommand");
    	
    	MaintainDamageReportForm form = (MaintainDamageReportForm)invocationContext.screenModel;
    	MaintainDamageReportSession session = getScreenSession(MODULE_NAME,SCREEN_ID);
       
        ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
        String companyCode = logonAttributes.getCompanyCode();
        
        SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
		oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
				companyCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
		e.getMessage();
		exception = handleDelegateException(e);
		}
		session.setOneTimeValues(
		(HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
		populateCurrency(session);
		
		
        String uldnos = form.getUldNumbersSelected();
       
        String[] selectedULDNo = uldnos.split(",");
        ArrayList<String> selectedULDNumbers = new ArrayList<String>();
        
        for(int i=0;i<selectedULDNo.length;i++){
          
        	selectedULDNumbers.add(selectedULDNo[i]);
        	log.log(Log.FINE, "selectedULDNo[i] ---> ", selectedULDNo, i);
            }
        log.log(Log.FINE, "selectedULDNumbers ---> ", selectedULDNumbers);
		session.setUldNumbers(selectedULDNumbers);
        String uldNumber = selectedULDNumbers.get(0);
        log.log(Log.FINE, "uldNumber ---> ", uldNumber);
		form.setUldNumber(uldNumber);
        log.log(Log.FINE, "maintainDamageReportForm.getPageURL() ---> ", form.getPageURL());
		session.setPageURL(form.getPageURL());
        invocationContext.target=SCREENLOAD_SUCCESS;
    }
    
    /**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(DAMAGESTATUS_ONETIME);
    	parameterTypes.add(OVERALLSTATUS_ONETIME);
    	parameterTypes.add(REPAIRSTATUS_ONETIME);
    	parameterTypes.add(DAMAGECODE_ONETIME);
    	parameterTypes.add(POSITION_ONETIME);
    	parameterTypes.add(SEVERITY_ONETIME);
    	parameterTypes.add(REPAIRHEAD_ONETIME);
    	    	
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }

    private void populateCurrency
			(MaintainDamageReportSession maintainDamageReportSession) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ArrayList<CurrencyVO> currencies = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			currencies = (ArrayList<CurrencyVO>)new CurrencyDelegate()
			.findAllCurrencyCodes(logonAttributes.getCompanyCode());
		} catch (BusinessDelegateException businessDelegateException) {
			exception = handleDelegateException(businessDelegateException);
		}
		
		maintainDamageReportSession.setCurrencies(currencies);
		
	}
}
