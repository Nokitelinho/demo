/*
 * ClearULDErrorLogCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ulderrorlog;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class ClearULDErrorLogCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD Error Log");

	private static final String SCREENID = "uld.defaults.ulderrorlog";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String CLEAR_SUCCESS= "clear_success";

	private static final String BLANK = "";

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
		
    	ULDErrorLogForm form = (ULDErrorLogForm) invocationContext.screenModel;
    	ULDErrorLogSession session = getScreenSession(MODULE_NAME, SCREENID);
		
		form.setDisplayPage("1");
		form.setUlderrorlogAirport("");
		form.setUlderrorlogULDNo("");
		//form.setCarrierCode("");
		form.setFlightDate("");
		form.setFlightNo("");
		form.setMessageType("OUT");
		form.setUcmNo("");
		form.setScreenFlag("screenload");
		session.setFlightFilterMessageVOSession(null);
		session.setULDFlightMessageReconcileDetailsVOs(null);
		session.setPouValues(null);
		session.setUcmNumberValues(null);
		if(logonAttributes.isAirlineUser()){    		
			form.setUldDisableStat("airline");
    	}
    	else{
    		form.setUlderrorlogAirport(logonAttributes.getAirportCode());
    		form.setUldDisableStat("GHA");
    	}

		invocationContext.target =CLEAR_SUCCESS;
    }




}
