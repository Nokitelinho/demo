/*
 * ScreenLoadCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.airportfacilitymaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.AirportFacilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up uld
 * 
 * @author A-2052
 */
public class ScreenLoadCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ScreenLoadCommand");
    
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
	private static final String FACILITYTYPE_STATUS = "uld.defaults.facilitytypes";
    
	private static final String ONETIME_CONTENT = "uld.defaults.contentcodes";
    
	private static final String SCREENID = "uld.defaults.airportfacilitymaster";
	
	private static final String MODULE_NAME = "uld.defaults";
	
	private static final String SCREEN_NAME="screenLoad";
	
	private static final String LIST_ULD="listUld";
	
	private static final String MONITOR_ULD_STOCK="monitorULDStock";
	
	private static final String BLANK="";
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        
    	ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
		LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
		String companyCode=logonAttributes.getCompanyCode();

		AirportFacilityMasterForm form = 
			(AirportFacilityMasterForm) invocationContext.screenModel;
		AirportFacilityMasterSession session = 
			getScreenSession(MODULE_NAME, SCREENID);
		SharedDefaultsDelegate sharedDefaultsDelegate =
			  new SharedDefaultsDelegate();
		Map hashMap = null;
	    Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(FACILITYTYPE_STATUS);
		// added by a-3278 for QF1006 on 04Aug08
		oneTimeList.add(ONETIME_CONTENT);
		// a-3278 ends
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues
			             							  (companyCode,oneTimeList);
		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			exception = handleDelegateException(businessDelegateException);
		}
		Collection<OneTimeVO> facilityType =
			(Collection<OneTimeVO>) hashMap.get(FACILITYTYPE_STATUS);		
		session.setFacilityType(facilityType);
		// added by a-3278 for QF1006 on 04Aug08
		Collection<OneTimeVO> content = (Collection<OneTimeVO>) hashMap
				.get(ONETIME_CONTENT);
		session.setContent(content);
		
		// a-3278 ends
		//form.setAirportCode("");
		form.setFacilityType(BLANK);		
		log.log(Log.FINE, "The Screen Name before chk is null or not", form.getScreenName());
		if(form.getScreenName()==null || form.getScreenName().trim().length()==0){
			form.setScreenName(SCREEN_NAME);
			log.log(Log.FINE, "The Screen Name is", form.getScreenName());
		}
		session.setAirportCode(BLANK);
		session.removeULDAirportLocationVOs();
		//added by a-3045 for bugULD725 starts
		//added by a-3045 for bug22213 starts
		if(MONITOR_ULD_STOCK.equals(form.getScreenName())){
			log.log(log.INFO,"AirportCode Will be blank from MONITOR ULD STOCK");
			form.setAirportCode(BLANK);
			session.setAirportCode(form.getAirportCode());
			//added by a-3045 for bug22213 ends
		}else if(!(LIST_ULD.equals(form.getScreenName()))){
			log.log(Log.INFO, "Any Userrr", logonAttributes.getAirportCode());
			form.setAirportCode(logonAttributes.getAirportCode());
			session.setAirportCode(logonAttributes.getAirportCode());
		}else{
			session.setAirportCode(form.getAirportCode());
		}
		//added by a-3045 for bugULD725 ends
		form.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);  
		invocationContext.target=SCREENLOAD_SUCCESS;
		log.exiting("ScreenLoadCommand", "execute"); 
    }
}
