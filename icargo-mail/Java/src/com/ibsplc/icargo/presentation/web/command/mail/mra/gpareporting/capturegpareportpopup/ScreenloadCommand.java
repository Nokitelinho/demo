/*
 * ScreenloadCommand.java Created on Feb 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareportpopup;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-2257
 * 
 /*
 * 
 * Revision History
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1         Feb 13, 2007   Meera Vijayan			Initial draft
 *  
 */
public class ScreenloadCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	private static final String CLASS_NAME = "ScreenloadCommand";
	private static final String HIGHEST_NUMBERED_MAIL = "mailtracking.defaults.highestnumbermail";
	
	private static final String REISTERED_INDICATOR = "mailtracking.defaults.registeredorinsuredcode";
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";
	
		
	/**
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		
		CaptureGPAReportSession session = 
			(CaptureGPAReportSession)getScreenSession(
													MODULE_NAME, SCREENID);
		log.log(Log.FINE, "****ONETIMES IN SESSION", session.getHeighestNum());
		ApplicationSessionImpl applicationSession = getApplicationSession();
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		session.setWeightRoundingVO(unitRoundingVO);
		
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		setUnitComponent(logonAttributes.getStationCode(),session);
		invocationContext.target = ACTION_SUCCESS;
		
		log.exiting(CLASS_NAME, "execute");
	}	
	
	/**
	 * A-5526
	 * @param stationCode
	 * @param mailAcceptanceSession
	 * @return
	 */
	private void setUnitComponent(String stationCode,
			CaptureGPAReportSession captureGPAReportSession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			
			unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT);

			captureGPAReportSession.setWeightRoundingVO(unitRoundingVO);
			

		   }catch(UnitException unitException) {
				unitException.getErrorCode();
		   }

	}
}
