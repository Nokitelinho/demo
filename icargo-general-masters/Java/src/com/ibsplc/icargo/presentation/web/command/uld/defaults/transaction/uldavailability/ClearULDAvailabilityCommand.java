/*
 * ClearULDAvailabilityCommand.java Created on Mar 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.uldavailability;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ULDAvailabilityForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3278
 *
 */
public class ClearULDAvailabilityCommand extends BaseCommand {
	
	private static final String CLEAR = "clear";
    private static final String MODULE_NAME = "uld.defaults";
    private static final String SCREEN_ID = "uld.defaults.uldavailability";
    private static final String BLANK = "";
    private static final String ALL = "ALL";

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("ULD_AVAIALBILITY");
    	log.entering("ClearULDAvailabilityCommand","ULD");
    	ULDAvailabilityForm form = (ULDAvailabilityForm)invocationContext.screenModel;
    	    	
    	form.setStationCode(BLANK);
    	form.setUldTypeCode(BLANK);
    	form.setPartyType(ALL);
    	form.setPartyCode(BLANK);  
    	invocationContext.target=CLEAR;

	}

}
