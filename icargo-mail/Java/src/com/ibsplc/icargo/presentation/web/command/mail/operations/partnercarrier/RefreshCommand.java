/*
 * RefreshCommand.java Created on MAR 18, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.partnercarrier;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PartnerCarriersForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class RefreshCommand extends BaseCommand {

	private static final String SUCCESS = "success";
	private Log log = LogFactory.getLogger("MailTracking,PartnerCarrier");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.partnercarriers";

	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("PartnerCarrier","RefreshCommand");

		log.log(Log.FINE, "\n\n in RefreshCommand----------> \n\n");

		PartnerCarriersForm partnerCarriersForm =(PartnerCarriersForm)invocationContext.screenModel;
	/*	PartnerCarrierSession partnerCarrierSession = getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();*/
		
		

		partnerCarriersForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = SUCCESS;	
		log.exiting("PartnerCarrier","RefreshCommand");
	}

}
