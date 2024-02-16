/*
 * UpdateSessionCommand.java Created on Mar 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.viewflownmail;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.ViewFlownMailSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2449
 *
 */
public class UpdateSessionCommand extends BaseCommand{
	
private Log log = LogFactory.getLogger("MRA FLOWN");
	
	private static final String CLASS_NAME = "UpdateSessionCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	
	private static final String SCREENID = "mra.flown.viewflownmail";
	
	private static final String SCREENID_DUPLICATE_FLIGHTS = "flight.operation.duplicateflight";
	
	private static final String ACTION_SUCCESS= "action_success";
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		ViewFlownMailSession session = (ViewFlownMailSession)getScreenSession(MODULE_NAME, SCREENID);
		DuplicateFlightSession duplicateFlightSession = 
			(DuplicateFlightSession)getScreenSession(MODULE_NAME,SCREENID_DUPLICATE_FLIGHTS); 
		
		session.setFlightDetails(duplicateFlightSession.getFlightValidationVO());
		
		invocationContext.target = ACTION_SUCCESS;
	}
		

}
