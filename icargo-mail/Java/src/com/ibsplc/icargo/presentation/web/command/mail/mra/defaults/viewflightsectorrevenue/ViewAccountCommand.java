/*
 * ViewAccountCommand.java Created on May 25, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewflightsectorrevenue;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ListAccountingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewFlightSectorRevenueSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewMailFlightSectorRevenueForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1747
 *
 */
public class ViewAccountCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "ViewAccountCommand";

	/**
	 * MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.viewflightsectorrevenue";

	/**
	 * Target action
	 */
	private static final String ACTION_SUCCESS = "view_success";

	/**
	 * String BLANK
	 */
	private static final String BLANK = "";

	/*
	 * Strings for SCREEN_ID and MODULE_NAME of ListAccountingEntries
	 */
	private static final String LISTACCOUNTING_SCREENID = "cra.accounting.listaccountingentries";

	private static final String LISTACCOUNTING_MODULENAME = "cra.accounting";

	private static final String FROM_VIEWFLIGHTSECTOR = "frm_viewflightsectorrevenue";

	private static final String FUNCTION_PT_FLOWNMAIL = "FM";
	
	private static final String FROM_ACCOUNTING = "listaccontingentry";

	/**
	 * Method to implement passing of selected vo to details screen
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		AccountingFilterVO accountingFilterVO = new AccountingFilterVO();

		ViewMailFlightSectorRevenueForm form = (ViewMailFlightSectorRevenueForm) invocationContext.screenModel;
		SectorRevenueDetailsVO sectorRevenueDetailsVO = new SectorRevenueDetailsVO();
		ViewFlightSectorRevenueSession session = (ViewFlightSectorRevenueSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		Collection<SectorRevenueDetailsVO> flightsectorDetails = session
				.getFlightSectorRevenueDetails();
		String selectedId = form.getSelectedDsn();
		 
		if (selectedId != null
				&& (flightsectorDetails != null && flightsectorDetails.size() > 0)) {			

			if (flightsectorDetails != null && flightsectorDetails.size() > 0) {
				sectorRevenueDetailsVO = ((ArrayList<SectorRevenueDetailsVO>) flightsectorDetails)
						.get(Integer.parseInt(selectedId));
				log.log(Log.INFO, "selected sectorRevenueDetailsVO-->",
						sectorRevenueDetailsVO);
				//accountingFilterVO.setMailBillingBasis(sectorRevenueDetailsVO.getBlgbas());
				accountingFilterVO.setMasterDocumentNumber(sectorRevenueDetailsVO.getDsn());
				accountingFilterVO.setFunctionPoint(FUNCTION_PT_FLOWNMAIL);
				accountingFilterVO.setSubSystem("M");
			}
			session.setListStatus(FROM_ACCOUNTING);
			ListAccountingEntriesSession accountingEntrySession = getScreenSession(
					LISTACCOUNTING_MODULENAME, LISTACCOUNTING_SCREENID);
			accountingEntrySession.setAccountingFilterVO(accountingFilterVO);
			accountingEntrySession.setParentScreenFlag(FROM_VIEWFLIGHTSECTOR);
			invocationContext.target = ACTION_SUCCESS;
			return;
		}
	}
}
