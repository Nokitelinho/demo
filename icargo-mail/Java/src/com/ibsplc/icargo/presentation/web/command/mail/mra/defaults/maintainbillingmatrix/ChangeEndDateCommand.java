/*
 * InactivateCommand.java Created on Jul 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.CopyBlgLineSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author 204569
 * 
 */
public class ChangeEndDateCommand extends BaseCommand {

	private static final Log LOG = LogFactory.getLogger("MRA:DEFAULTS");

	private static final String CLASS_NAME = "ChangeEndDateCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.copyblgline";
	private static final String SCREEN_SUCCESS = "screenload_success";
	

	/**
	 * *
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		LOG.entering(CLASS_NAME, "execute");

		BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(MODULE_NAME, SCREENID);
		session.getBillingMatrixVO();
		CopyBlgLineSession copyLineSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		LOG.log(Log.INFO, "form.getSelectedIndexes()", form.getSelectedIndexes());

		String[] selectedIndexes = form.getSelectedIndexes().split("-");
		LOG.log(Log.INFO, "selectedIndexes", selectedIndexes);
		ArrayList<BillingLineVO> billingLineVos = new ArrayList<>();
		for (String index : selectedIndexes) {
			billingLineVos.add(session.getBillingLineDetails().get(Integer.parseInt(index)));
		}

		LOG.log(Log.INFO, "billingLineVos", billingLineVos);
		copyLineSession.setSelectedBlgLines(billingLineVos);
		invocationContext.target = SCREEN_SUCCESS;
		LOG.exiting(CLASS_NAME, "execute");

	}
}