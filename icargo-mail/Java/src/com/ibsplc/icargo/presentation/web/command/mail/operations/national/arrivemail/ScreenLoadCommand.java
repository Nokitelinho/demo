/* ScreenLoadCommand.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.arrivemail;


import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ArriveDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ArriveDispatchForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-4810
 */
public class ScreenLoadCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.national.mailarrival";
   
   
   
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
   public void execute(InvocationContext invocationContext)
   throws CommandInvocationException {

	   log.entering("ScreenloadCommand","execute");

	   ArriveDispatchForm arriveDispatchForm = 
		   (ArriveDispatchForm)invocationContext.screenModel;
	   ArriveDispatchSession arriveDispatchSession = 
		   getScreenSession(MODULE_NAME,SCREEN_ID);
	   arriveDispatchForm.setOperationalStatus("");

	   ApplicationSessionImpl applicationSession = getApplicationSession();
	   LogonAttributes logonAttributes = applicationSession.getLogonVO();
	   UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
	   arriveDispatchSession.setWeightRoundingVO(unitRoundingVO);				
		setUnitComponent(logonAttributes.getStationCode(),arriveDispatchSession);	
	   MailArrivalVO mailArrivalVO = new MailArrivalVO();
	   arriveDispatchSession.setMailArrivalVO(mailArrivalVO);
	   arriveDispatchForm.setOperationalStatus("");
	   FlightValidationVO flightValidationVO = new FlightValidationVO();
	   arriveDispatchSession.setFlightValidationVO(flightValidationVO);
	   arriveDispatchForm.setInitialFocus(FLAG_YES);
	   arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	   arriveDispatchForm.setArrivalPort(logonAttributes.getAirportCode());
	   //Added for icrd-18688 by a-4810
	   arriveDispatchForm.setFlightCarrierCode(logonAttributes.getOwnAirlineCode());
	   invocationContext.target = TARGET;

	   log.exiting("ScreenloadCommand","execute");

   }
    
	
   private void setUnitComponent(String stationCode,
		   ArriveDispatchSession arriveDispatchSession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			
			unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT);		
			
			arriveDispatchSession.setWeightRoundingVO(unitRoundingVO);			
			
		   }catch(UnitException unitException) {
				unitException.getErrorCode();
		   }
	}  
       
}
