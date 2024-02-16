/*
 * ActivateCommand.java Created on Aug 20, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewbillingline;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2391
 * 
 */
public class ActivateCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ActivateCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewbillingline";

	private static final String SCREEN_SUCCESS = "screenload_success";
	
	private static final String DUPLICATE_EXISTS = "duplicate_exists";
	
	private static final String DUP_BILLINGLINE = "mailtracking.mra.defaults.duplicatebillingline";

	/**
	 * *
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		Collection<ErrorVO> errors = null;
		ViewBillingLineForm form = (ViewBillingLineForm) invocationContext.screenModel;
		ViewBillingLineSession session = (ViewBillingLineSession) getScreenSession(
				MODULE_NAME, SCREENID);
		String[] selectedIndexes = form.getSelectedIndexes().split("-");
		Collection<BillingLineVO> billingLineVos = new ArrayList<BillingLineVO>();
		for (String index : selectedIndexes) {
			billingLineVos.add(session.getBillingLineDetails().get(
					Integer.parseInt(index)));
		}
		try {
			new MailTrackingMRADelegate().activateBillingLines(billingLineVos);
		} catch (BusinessDelegateException businessDelegateException) {
			//errors = handleDelegateException(businessDelegateException);
			log.log(1, "Error--------->***********");
			errors=new ArrayList<ErrorVO>();
			errors.add(new ErrorVO(DUP_BILLINGLINE));
			invocationContext.addAllError(errors);
			invocationContext.target = DUPLICATE_EXISTS;
			return;
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
		}
		invocationContext.target = SCREEN_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

}
