/*
 * CancelCommand.java Created on Jul 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2518
 * 
 */
public class CancelCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "CancelCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String SCREEN_SUCCESS = "screenload_success";

	/**
	 * *
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREENID);
		String[] selectedIndexes = form.getSelectedIndexes().split("-");
		Collection<BillingLineVO> billingLineVos = new ArrayList<BillingLineVO>();
		for (String index : selectedIndexes) {
			billingLineVos.add(session.getBillingLineDetails().get(
					Integer.parseInt(index)));
		}
		try {
			new MailTrackingMRADelegate().cancelBillingLines(billingLineVos);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		invocationContext.target = SCREEN_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}