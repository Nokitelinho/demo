/*
 * InactivateCommand.java Created on Aug 6, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author a-2391
 * 
 */
public class InactivateCommand extends BaseCommand{
private Log log = LogFactory.getLogger("MRA DEFAULTS");

private static final String CLASS_NAME = "InactivateCommand";

private static final String MODULE_NAME = "mailtracking.mra.defaults";

private static final String SCREENID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";

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
	MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
	MaintainUPURateCardSession session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREENID);
	String[] selectedIndexes = form.getSelectedIndexes().split("-");
	Collection<RateLineVO> rateLineVOs = new ArrayList<RateLineVO>();
	for (String index : selectedIndexes) {
		rateLineVOs.add(session.getRateLineDetails().get(
				Integer.parseInt(index)));
	}
	try {
		new MailTrackingMRADelegate()
				.inActivateRateLines(rateLineVOs);
	} catch (BusinessDelegateException businessDelegateException) {
		handleDelegateException(businessDelegateException);
	}
	invocationContext.target = SCREEN_SUCCESS;
	log.exiting(CLASS_NAME, "execute");
}

}
