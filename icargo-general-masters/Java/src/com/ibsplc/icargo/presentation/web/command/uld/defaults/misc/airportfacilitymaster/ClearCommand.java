/*
 * ClearCommand.java Created on Jan 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.airportfacilitymaster;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.AirportFacilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 *
 */
public class ClearCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ClearCommand");
	
	private static final String SCREENID = "uld.defaults.airportfacilitymaster";
	
	private static final String MODULE_NAME = "uld.defaults";

	private static final String CLEAR_SUCCESS= "Clear_success";
	
	private static final String SCREEN_NAME="screenLoad";
	
	private static final String MONITOR_ULD_STOCK="monitorULDStock";
	
	private static final String BLANK="";
	
	 /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	AirportFacilityMasterForm form = 
			(AirportFacilityMasterForm) invocationContext.screenModel;
		AirportFacilityMasterSession session = 
			getScreenSession(MODULE_NAME, SCREENID);
		//form.setAirportCode("");
		ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
		LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
		String airportCode=logonAttributes.getAirportCode();
		//form.setAirportCode(airportCode);
		form.setAfterList("");
		//log.log(Log.FINE,"From clearCommand screenName value is  "+form.getScreenName());
		/*if(SCREEN_NAME!=form.getScreenName()){
			log.log(Log.FINE,"From clearCommand screenName value from if is  "+form.getScreenName());
			session.setAirportCode(airportCode);	
			//session.setAirportCode("");
		}else{
			log.log(Log.FINE,"From clearCommand screenName value else is  "+form.getScreenName());
		session.setAirportCode("");
		//	session.setAirportCode(airportCode);
		}*/	
		//added by a-3045 for bug20352 starts
		if(form.getAirportCode() != null && form.getAirportCode().trim().length() > 0){
			session.setAirportCode(form.getAirportCode());
		}else if(MONITOR_ULD_STOCK.equals(form.getScreenName())){
			session.setAirportCode(BLANK);
		}else{
			session.setAirportCode(airportCode);
		}
		//added by a-3045 for bug20352 ends
		form.setWareHouseFlag("");
		session.removeULDAirportLocationVOs();		
		session.setFacilityTypeValue("");
		invocationContext.target =CLEAR_SUCCESS;
    }

}
