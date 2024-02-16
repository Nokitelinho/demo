/*
 * ScreenLoadCommand.java Created on Aug 01, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.generatescm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * Generate SCM screen
 * 
 * @author A-1862
 */

public class ScreenLoadCommand extends BaseCommand {
    
	/**
	 * Logger for Generate SCM
	 */
	private Log log = LogFactory.getLogger("Generate SCM");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of ucm error log
	 */
	private static final String SCREENID =
		"uld.defaults.generatescm";
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
    private static final String BLANK = "";
    
    private static final String GHA_CONSTANT="GHA";
    
    private static final String FACILITY_TYPE = "uld.defaults.facilitytypes";
    private static final String SCM_ULD_STATUS = "uld.defaults.scmuldstatus";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();		
		GenerateSCMSession generateSCMSession = 
			(GenerateSCMSession)getScreenSession(MODULE,SCREENID);
		generateSCMSession.removeAllAttributes();
		
		GenerateSCMForm generateSCMForm = 
			(GenerateSCMForm) invocationContext.screenModel;
		
		generateSCMForm.setPageURL(BLANK);
		generateSCMForm.setScmAirline(BLANK);		
		generateSCMForm.setScmAirport(BLANK);
		
		Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes.getCompanyCode());
		ArrayList<OneTimeVO> facilityTypes = (ArrayList<OneTimeVO>) oneTimeCollection.get(FACILITY_TYPE);
		generateSCMSession.setFacilityType(facilityTypes);
		ArrayList<OneTimeVO> uldStatusList = (ArrayList<OneTimeVO>) oneTimeCollection.get(SCM_ULD_STATUS);
		generateSCMSession.setUldStatusList(uldStatusList);
		
		log.log(Log.FINE, "---facilityTypes----------------->",
				generateSCMSession.getFacilityType());
		//Added by A-3045 as a part of defaulting airline code in page starts
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	log.log(Log.FINE, "logonAttributes.getCompanyCode()------->",
				logonAttributes.getCompanyCode());
		log.log(Log.FINE, "logonAttributes.getUserId()     ------->",
				logonAttributes.getUserId());
			/*try {
			airlineCode = new ULDDefaultsDelegate()
					.findDefaultAirlineCode(logonAttributes.getCompanyCode(),logonAttributes.getUserId());
		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			error = handleDelegateException(businessDelegateException);
		}
		if(airlineCode != null && airlineCode.trim().length() > 0){
			generateSCMForm.setScmAirline(airlineCode); 
		}
		else{*/
			if(logonAttributes.isAirlineUser()){
				generateSCMForm.setScmAirline(logonAttributes.getOwnAirlineCode()); 
			}
	//	}
        //Added by A-3045 as a part of defaulting airline code in page ends
		
		generateSCMForm.setScmAirport(logonAttributes.getAirportCode());
		LocalDate stockCheckDate = new LocalDate(generateSCMForm.getScmAirport(),Location.ARP,true);
		log.log(Log.FINE, "stockCheckDate ------------------>", stockCheckDate);
		generateSCMForm.setScmStockCheckDate(stockCheckDate.toDisplayDateOnlyFormat());
		
		//Commented by a-3045 for bug 26529 
		//generateSCMForm.setAirportDisable(GHA_CONSTANT);  
		//added by a-3045 for bug20936 starts
		generateSCMForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		//added by a-3045 for bug20936 ends
		invocationContext.addAllError(error);
		invocationContext.target = SCREENLOAD_SUCCESS;     
    }    
    
    /**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(FACILITY_TYPE);
		oneTimeList.add(SCM_ULD_STATUS);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		return hashMap;
	}
}
