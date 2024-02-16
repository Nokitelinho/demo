/*
 * ClearCommand.java created on July 15,2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateaudit;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListRateAuditSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListRateAuditForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3108
 *
 */
public class ClearCommand  extends BaseCommand{
	
	
	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID =
		"mailtracking.mra.defaults.listrateaudit";

	private static final String SCREEN_SUCCESS = "clear_success";
	private static final String BLANK = "";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		ListRateAuditForm listRateAuditForm=(ListRateAuditForm)invocationContext.screenModel;
		ListRateAuditSession listRateAuditSession = getScreenSession(
				MODULE_NAME, SCREENID);
		clearForm(listRateAuditForm);
		listRateAuditForm.setCarrierCode("NZ");
		listRateAuditSession.removeRateAuditVOs();
		listRateAuditSession.removeRateAuditFilterVO();
		listRateAuditSession.setFromScreen(null);
		
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	private void clearForm(ListRateAuditForm form){
		
		form.setDsn(BLANK);
		form.setDsnDate(BLANK);		
		form.setCarrierCode(BLANK);
		form.setFlightNo(BLANK);
		form.setFlightDate(BLANK);
		form.setFromDate(BLANK);
		form.setToDate(BLANK);
		form.setGpaCode(BLANK);
		form.setSubClass(BLANK);
		form.setDsnStatus(BLANK);
		form.setRateauditFlag(BLANK);
	}

}
