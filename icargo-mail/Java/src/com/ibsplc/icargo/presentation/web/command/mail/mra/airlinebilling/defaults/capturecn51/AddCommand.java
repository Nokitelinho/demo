/*
 * AddCommand.java Created on Feb 21, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import java.util.ArrayList;


import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2105
 * 
 */
public class AddCommand extends BaseCommand {

	private Log log = LogFactory
			.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");

	private static final String CLASS_NAME = "AddCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";

	private static final String ADD_SUCCESS = "add_success";

	//private static final String ADD_FAILURE = "add_failure";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		//CaptureCN51Form captureCN51Form = (CaptureCN51Form) invocationContext.screenModel;
		if (invocationContext.getErrors() == null
				|| invocationContext.getErrors().size() == 0) {
		AirlineCN51SummaryVO airlineCN51SummaryVO = null;
		if (captureCN51Session.getCn51Details() == null) {
			airlineCN51SummaryVO = new AirlineCN51SummaryVO();
			airlineCN51SummaryVO.setCompanycode(getApplicationSession()
					.getLogonVO().getCompanyCode());
			
			// airlineCN51SummaryVO.setClearanceperiod()
		} else {
			airlineCN51SummaryVO = captureCN51Session.getCn51Details();
			
		}
		if(airlineCN51SummaryVO.getCn51DetailsPageVOs() == null ||
				airlineCN51SummaryVO.getCn51DetailsPageVOs().size() ==0) {
			Page<AirlineCN51DetailsVO> airlineCN51DetailsVOs = new Page<AirlineCN51DetailsVO>(
					new ArrayList<AirlineCN51DetailsVO>(), 0, 0, 0, 0, 0,false);
			airlineCN51SummaryVO.setCn51DetailsPageVOs(airlineCN51DetailsVOs);
		}
		AirlineCN51DetailsVO airlineCN51DetailsVO = new AirlineCN51DetailsVO();
		airlineCN51DetailsVO.setCompanycode(getApplicationSession()
				.getLogonVO().getCompanyCode());
			airlineCN51DetailsVO.setMailsubclass("N");
			airlineCN51DetailsVO.setInvoicenumber(airlineCN51SummaryVO.getInvoicenumber());
			airlineCN51DetailsVO.setClearanceperiod(airlineCN51SummaryVO.getClearanceperiod());
			airlineCN51DetailsVO.setAirlineidr(airlineCN51SummaryVO.getAirlineidr());
			airlineCN51DetailsVO.setInterlinebillingtype(airlineCN51SummaryVO.getInterlinebillingtype());
			airlineCN51DetailsVO.setOperationFlag(AirlineCN51DetailsVO.OPERATION_FLAG_INSERT);
			airlineCN51SummaryVO.getCn51DetailsPageVOs().add(airlineCN51DetailsVO);
		captureCN51Session.setCn51Details(airlineCN51SummaryVO);
		}
		invocationContext.target = ADD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

}
