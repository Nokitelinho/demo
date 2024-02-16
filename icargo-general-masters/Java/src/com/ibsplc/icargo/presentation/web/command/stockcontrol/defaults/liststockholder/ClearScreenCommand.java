/*
 * ClearScreenCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockholder;


import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockHolderForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1754
 *
 */
public class ClearScreenCommand extends BaseCommand {

	//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
	private static final String AWB = "AWB";
	private static final String S = "S";
	//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
							throws CommandInvocationException {
		log.entering("ClearScreenCommand","execute");
		ListStockHolderForm form = (ListStockHolderForm)invocationContext.screenModel;
		ListStockHolderSession session =
				getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.liststockholder");
		//added by A-5131 for ICRD-24891
		form.setScreenStatusFlag
		(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = "screenload_success";
		//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
		//form.setDocType("");
		//form.setDocSubType("");
		form.setDocType("");
		form.setDocSubType("");
		//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends
		form.setLevel("");
		form.setStockHolderCode("");
		form.setStockHolderType("");
		//Added by A-7364 as part of ICRD-320756 starts
		form.setPartnerAirline(false);
		form.setAwbPrefix("");
		form.setAirlineName("");
		//Added by A-7364 as part of ICRD-320756 ends
		session.setStockHolderDetails(null);
		log.exiting("ClearScreenCommand","execute");
	}


}
