/*
 * ClearCommand.java Created on jul 29,2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformone;

import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormOneForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class ClearCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MRA airlinebilling CLEARCOMMAND");
	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE = "mailtracking.mra.airlinebilling";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureformone";
	private static final String CLEAR_SUCCESS="clear_success";
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		
		CaptureMailFormOneForm captureFormOneForm=(CaptureMailFormOneForm)invocationContext.screenModel;
		CaptureFormOneSession captureFormOneSession=getScreenSession(MODULE,SCREENID);
		captureFormOneForm.setListFlag("N");
		captureFormOneForm.setClearancePeriod("");
		captureFormOneForm.setAirlineCode("");
		captureFormOneForm.setAirlineNo("");
		captureFormOneForm.setInvoiceStatus("");
		captureFormOneSession.removeFormOneInvVOs();
		captureFormOneSession.removeFormOneVO();
		captureFormOneForm.setLinkDisable("Y");
		invocationContext.target=CLEAR_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		try{
		Money money = CurrencyHelper.getMoney("USD");
		money.setAmount(0.0D);
		captureFormOneForm.setNetUsdAmountMoney(money);
		captureFormOneForm.setNetMiscAmountMoney(money);
		}
		catch(CurrencyException currencyException){
			log.entering(CLASS_NAME,"CurrencyException");
		}

	}
}
