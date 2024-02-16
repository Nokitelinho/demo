/*
 * MailChangeScreenloadCommand Created on Jul 1 2016
 *
 * Copyright 2016 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailchange;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

/**
 * Command class for screenload of mail change pop up
 * @author a-5991
 *
 */
public class MailChangeScreenloadCommand extends BaseCommand { 
	
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	      
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	   private static final String CLASS_NAME = "MailChangeScreenloadCommand";
	   private static final String FAILURE = "FAILURE";
	   private static final String HH_MM = "HH:mm";

	   private static final String TARGET_SUCCESS = "success";
	   
	   
		 /**
		 * This method overrides the execute method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException 
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering(CLASS_NAME,"execute");
	    	
	    	MailArrivalForm mailArrivalForm = (MailArrivalForm)invocationContext.screenModel;
	    	MailArrivalSession mailArrivalSession = 
	        		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	mailArrivalForm.setOperationalStatus("");
	    	mailArrivalSession.setFlightMessageStatus("");
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			LocalDate currentDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			String date = currentDate.toDisplayDateOnlyFormat();
			String time = currentDate.toDisplayFormat(HH_MM);
			mailArrivalForm.setFlightScanDate(date);
			mailArrivalForm.setFlightScanTime(time);
			mailArrivalForm.setAddLinkFlag(FLAG_NO);
			MailArrivalVO mailArrivalVO = new MailArrivalVO();
			mailArrivalSession.setFlightMailArrivalVO(mailArrivalVO);
			mailArrivalForm.setListFlag(FAILURE);
			mailArrivalForm.setOperationalStatus("");
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			mailArrivalSession.setChangeFlightValidationVO(flightValidationVO);
			mailArrivalSession.setContainerVOs(null);
			mailArrivalForm.setInitialFocus(FLAG_YES);
			mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			mailArrivalForm.setArrivalPort(logonAttributes.getAirportCode());
			
			
			invocationContext.target = TARGET_SUCCESS;		 	
	       	
	    	log.exiting("CLASS_NAME","execute");
	    }

}
