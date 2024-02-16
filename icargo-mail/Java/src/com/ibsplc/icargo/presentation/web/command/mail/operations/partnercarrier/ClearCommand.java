/*
 * ClearCommand.java Created on August 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.partnercarrier;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PartnerCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PartnerCarriersForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class ClearCommand extends BaseCommand {

	private static final String SUCCESS = "clear_success";
	
	private Log log = LogFactory.getLogger("MailTracking,PartnerCarrier");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.partnercarriers";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the clear command----------> \n\n");
    	
    	PartnerCarriersForm partnerCarriersForm =
							(PartnerCarriersForm)invocationContext.screenModel;
		PartnerCarrierSession partnerCarrierSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
		    	
		partnerCarriersForm.setAirport("");
		partnerCarriersForm.setDisableSave("Y");
		partnerCarrierSession.setPartnerCarrierVOs(null);
		partnerCarrierSession.setAirport(null);
		
		partnerCarriersForm.setScreenStatusFlag
					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = SUCCESS;
    	
	}


}
