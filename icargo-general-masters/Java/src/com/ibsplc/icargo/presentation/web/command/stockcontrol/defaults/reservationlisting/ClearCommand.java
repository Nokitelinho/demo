/*
 * ClearCommand.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reservationlisting;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReservationListingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class ClearCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ClearSccMasterCommand");

	private static final String CLEAR_SUCCESS =
				"clear_reservation_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID =
				"stockcontrol.defaults.reservationlisting";

	/**
	 * execute method for handling the clear action
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		log.log(Log.FINE, "inside CLEAR command");

		ReservationListingForm form =
				  (ReservationListingForm)invocationContext.screenModel;

		ReservationListingSession session =
					getScreenSession(MODULE_NAME, SCREEN_ID);

		session.removeReservationVO();
		form.setAirlineFilterCode(null);
		form.setReservationFilterFromDate(null);
		form.setReservationFilterToDate(null);
		form.setCustomerFilterCode(null);
		form.setDocumentFilterType(null);
		form.setExpiryFilterFromDate(null);
		form.setExpiryFilterToDate(null);

		invocationContext.target =CLEAR_SUCCESS;

    }

}