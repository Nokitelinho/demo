/*
 * ClearCommand.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reserveawb;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReserveAWBSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReserveAWBForm;

/**
 * @author A-1619
 *
 */
public class ClearCommand extends BaseCommand {

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ReserveAWBForm form = (ReserveAWBForm) invocationContext.screenModel;
		ReserveAWBSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.cto.reservestock");

		session.removeAllAttributes();
		form.setAfterCloseList("");
		form.setAfterReserve("");
		form.setAirline("");
		form.setCustCode("");
		form.setExpiryDate("");
		form.setTotAwb("");
		form.setAwbType("");
		form.setGeneral("");
		form.setSpecific("");
		form.setRemarks("");
		session.setReserveAWBVO(null);
		session.setAWBTypes(null);
		invocationContext.target = "clearscreen_success";
	}

}
