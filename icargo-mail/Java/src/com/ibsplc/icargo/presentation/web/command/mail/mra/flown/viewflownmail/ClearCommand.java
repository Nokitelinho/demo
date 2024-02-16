/*
 * ClearCommand.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.viewflownmail;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.ViewFlownMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.ViewFlownMailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2449
 *
 */
public class ClearCommand extends BaseCommand{
	
private Log log = LogFactory.getLogger("flown ClearCommand");
	
	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String SCREEN_ID = "mra.flown.viewflownmail";
	private static final String ACTION_SUCCESS = "clear_success";
    
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	ViewFlownMailSession session = (ViewFlownMailSession)getScreenSession(MODULE_NAME,SCREEN_ID);
   		ViewFlownMailForm form=(ViewFlownMailForm)invocationContext.screenModel;
   		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
   		//session.removeAllAttributes();
   		session.removeFlightDetails();
   		session.removeFlownMailSegmentVO();
   		session.removeListFilterDetails();
   		session.removeSegmentDetails();
   		form.setCarrierCode(logonAttributes.getOwnAirlineCode());
   		//form.setCarrierCode("NZ");
   		form.setFlightNumber("");
   		form.setFlightDate("");
   		form.setFlightStatus("");
   		form.setSegment("");
   		form.setFlightSegmentStatus("");
   		form.setAccountingMonth("");
   		
   		form.setListSegmentsFlag("");
   		form.setListFlag("");
   		form.setDuplicateFlightFlag("");
   		form.setOneSegmentFlag("");
   		
   		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    	
    }

}
