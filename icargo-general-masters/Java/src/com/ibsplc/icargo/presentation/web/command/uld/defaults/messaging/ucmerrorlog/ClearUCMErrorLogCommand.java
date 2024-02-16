/* 
 * ClearUCMErrorLogCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class ClearUCMErrorLogCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("UCM Error Log");

	private static final String SCREENID = "uld.defaults.ucmerrorlog";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String CLEAR_SUCCESS= "clear_success";

	private static final String BLANK = "";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	UCMErrorLogForm form = (UCMErrorLogForm) invocationContext.screenModel;
    	UCMErrorLogSession session = getScreenSession(MODULE_NAME, SCREENID);
		
		form.setDisplayPage("1");
		form.setUcmerrorlogAirport("");
		//form.setCarrierCode("");
		form.setFlightDate("");
		form.setFlightNo("");
		form.setDuplicateStatus("");
		form.setMsgType("IN");
		form.setCanClose(BLANK);
		form.setFlightValidationStatus(BLANK);
		form.setFromDate("");
		form.setToDate("");
		form.setMsgType("OUT");
		form.setMsgStatus("ALL");
		form.setListStatus("");
		form.setDisplayPage("1");
		form.setLastPageNumber("0");
		
		
		session.setULDFlightMessageReconcileVOs(null);
		session.setFlightFilterMessageVOSession(null);
		
		if(logonAttributes.isAirlineUser()){    		
			form.setUcmDisableStat("airline");
    	}
    	else{
    		form.setUcmerrorlogAirport(logonAttributes.getAirportCode());
    		form.setUcmDisableStat("GHA");
    	}

		invocationContext.target =CLEAR_SUCCESS;
    }




}
