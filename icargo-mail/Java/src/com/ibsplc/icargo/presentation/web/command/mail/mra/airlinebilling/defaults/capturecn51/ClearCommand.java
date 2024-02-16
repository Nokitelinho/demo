/*
 * ClearCommand.java Created on Feb 21, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2105
 *
 */
public class ClearCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	private static final String CLEAR_SUCCESS="clear_success";
	private static final String  ZERO="0.00";
	
	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		CaptureCN51Form captureCN51Form=(CaptureCN51Form)invocationContext.screenModel;
		captureCN51Form.setInvoiceRefNo("");
		captureCN51Form.setClearancePeriod("");
		captureCN51Form.setBillingType("");
		captureCN51Form.setAirlineCode("");
		captureCN51Form.setCategory("");
		captureCN51Form.setCn51Period("");
		captureCN51Form.setLinkStatusFlag("disable");
		captureCN51Form.setCarriageFrom("");
		captureCN51Form.setCarriageTo("");
		captureCN51Form.setDeleteFlag("");
		captureCN51Session.setFilterDetails(null);
		captureCN51Session.setCn51Details(null); 
		captureCN51Form.setNetCharge("");
		captureCN51Form.setNetWeight(0.00);
		captureCN51Form.setScreenFlg("");
		/**
		 * @author a-3447 for Bug 29672
		 */
		captureCN51Form.setNetWeight(0.00);
		captureCN51Form.setNetCP(0.00);
	    captureCN51Form.setNetLC(0.00);
		captureCN51Form.setNetUld(0.00);
		captureCN51Form.setNetSal(0.00);
		captureCN51Form.setNetSV(0.00);
		captureCN51Form.setNetEMS(0.00);
		captureCN51Form.setNetChargeMoneyDisp("0.0");
		
		/**
		 * @author a-3447 for Bug 29672
		 */
		try{
   			Money money = CurrencyHelper.getMoney("USD");
   			money.setAmount(0.0D);
   		
   			captureCN51Form.setNetChargeMoney(money);
   			captureCN51Form.setNetSummaryMoney(money);
   			captureCN51Form.setNetChargeMoneyDisp("0.0");
		}
			catch(CurrencyException currencyException){
				log.entering(CLASS_NAME,"CurrencyException");
			}
		invocationContext.target=CLEAR_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}
	
}
