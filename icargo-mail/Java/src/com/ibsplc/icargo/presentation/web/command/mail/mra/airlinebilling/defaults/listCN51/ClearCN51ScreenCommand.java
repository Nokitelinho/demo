/*
 * ClearCN51ScreenCommand.java Created on Mar 16,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listCN51;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListCN51ScreenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListCN51ScreenForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2049
 *
 */
public class ClearCN51ScreenCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MailTracking:Mra:Defaults");
	
	private static final String CLEAR_CN51_SUCESS = "clearCN51_success";
			
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listCN51s";
	
	private static final String BLANK_VALUE = "";
	
	private static final String SCREENLOAD="screenload";
	
	
	public void execute(InvocationContext invContext)
			throws CommandInvocationException {
		log.entering("ClearCN51ScreenCommand","execute");
		ListCN51ScreenForm listCN51ScreenForm 
							= (ListCN51ScreenForm)invContext.screenModel;
		ListCN51ScreenSession listCN51ScreenSession
							= (ListCN51ScreenSession)this.getScreenSession(MODULE_NAME,SCREEN_ID);
		clearFormAttributes(listCN51ScreenForm);
		//listCN51ScreenForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD); 
		listCN51ScreenForm.setScreenStatus(SCREENLOAD);
		listCN51ScreenSession.setAirlineCN51FilterVO(null);
		listCN51ScreenSession.setAirlineCN51SummaryVOs(null);
		//listCN51ScreenSession.removeAllAttributes();
		invContext.target = CLEAR_CN51_SUCESS;
		log.exiting("ClearCN51ScreenCommand","execute");
	}
	
	private void clearFormAttributes(ListCN51ScreenForm listCN51ScreenForm) {
		listCN51ScreenForm.setAirlineCode(BLANK_VALUE);
		listCN51ScreenForm.setBlgFromDateStr(BLANK_VALUE);
		listCN51ScreenForm.setBlgToDateStr(BLANK_VALUE);
		listCN51ScreenForm.setInvoiceNo(BLANK_VALUE);
	}

}
