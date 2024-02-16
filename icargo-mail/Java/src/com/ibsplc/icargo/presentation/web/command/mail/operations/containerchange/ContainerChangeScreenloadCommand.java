/*
 * ContainerChangeScreenloadCommand.JAVA Created on Jul 1 2016
 *
 * Copyright 2016 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.containerchange;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ContainerChangeScreenloadCommand extends BaseCommand { 
	
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	      
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	   private static final String CLASS_NAME = "ContainerChangeScreenloadCommand";

	   private static final String TARGET_SUCCESS = "success";
	   private static final String HH_MM="HH:mm";
	   
	   
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
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			MailArrivalVO mailArrivalVO = new MailArrivalVO();
			mailArrivalSession.setFlightMailArrivalVO(mailArrivalVO);
			LocalDate currentDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			String date = currentDate.toDisplayDateOnlyFormat();
			String time = currentDate.toDisplayFormat(HH_MM);
			mailArrivalForm.setFlightScanDate(date);
			mailArrivalForm.setFlightScanTime(time);
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			mailArrivalSession.setChangeFlightValidationVO(flightValidationVO);
			mailArrivalForm.setAddLinkFlag(MailConstantsVO.FLAG_NO);
			
			invocationContext.target = TARGET_SUCCESS;		 	
	       	
	    	log.exiting("CLASS_NAME","execute");
	    }

}
