/*
 * ClearCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 * @author A-2521
 */
public class ClearCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51s";

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String CLEAR_FAILURE = "clear_failure";

	private static final String BLANK = "";

	/**
	 * Method to implement the clear operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListCN51Form listCN51Form = (ListCN51Form)invocationContext.screenModel;

    	ListCN51Session listCN51Session =
    		(ListCN51Session)getScreenSession(MODULE_NAME, SCREENID);

    	// clearing vos in session
    	listCN51Session.removeCN51SummaryVOs();

    	// clearing form fields
    	listCN51Form.setGpacode(BLANK);
    	listCN51Form.setGpaname(BLANK);
    	listCN51Form.setListcn51frmdat(new LocalDate(NO_STATION, NONE, false)
				.addDays(-10).toDisplayFormat());
		listCN51Form.setListcn51todat(new LocalDate(NO_STATION, NONE, false).toDisplayFormat());
    	listCN51Form.setViewFlag(BLANK);

    	invocationContext.target = CLEAR_SUCCESS;
    }

}
