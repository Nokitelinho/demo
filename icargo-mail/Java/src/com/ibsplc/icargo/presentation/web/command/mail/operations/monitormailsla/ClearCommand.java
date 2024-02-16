/*
 * ClearCommand.java Created on Apr 14, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.monitormailsla;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MonitorMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MonitorMailSLAForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2122
 *
 */
public class ClearCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.monitormailsla";
	private static final String CLEAR_SUCCESS="clear_success";
	
	
	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * Execute method
	 *
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");

		MonitorMailSLAForm monitorMailSLAForm=(MonitorMailSLAForm)invocationContext.screenModel;
		MonitorMailSLASession monitorMailSLASession = (MonitorMailSLASession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		monitorMailSLAForm.setAirlineCode("");
		monitorMailSLAForm.setPaCode("");
		monitorMailSLAForm.setCarrierCode("");
		monitorMailSLAForm.setFlightNo("");
		monitorMailSLAForm.setCategory("");
		monitorMailSLAForm.setActivity("");
		monitorMailSLAForm.setSlaStatus("");
		monitorMailSLAForm.setDisplayPage("1");
		monitorMailSLAForm.setLastPageNum("0");
		monitorMailSLASession.setFilterVO(null);
		monitorMailSLASession.setMailSlaDetails(null);
		monitorMailSLAForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		invocationContext.target=CLEAR_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}	
	

}
