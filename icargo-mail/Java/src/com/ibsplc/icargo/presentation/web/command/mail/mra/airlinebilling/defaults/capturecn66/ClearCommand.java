/*
 * ClearCommand.java Created on Jan 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.util.log.factory.LogFactory;





/**
 * @author A-2408
 *
 */
public class ClearCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("AirLineBillingInward ClearCommand");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String EMPTY_STRING="";
	private static final String SCREENSTATUS_SCREENLOAD="screenload";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	CaptureCN66Session session=null;
   		session=(CaptureCN66Session) getScreenSession(MODULE_NAME,SCREEN_ID);
   		CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
   		String dou="0.00";
   		session.removeCn66Details();
   		session.removeCn66DetailsMap();
   		session.removeAirlineCN66DetailsVOs();
   		form.setInvoiceRefNo(EMPTY_STRING);
   		form.setClearancePeriod(EMPTY_STRING);
   		form.setAirlineCode(EMPTY_STRING);
   		form.setCategory(EMPTY_STRING);
   		form.setCarriageFromFilter(EMPTY_STRING);
   		form.setCarriageToFilter(EMPTY_STRING);
   		form.setDespatchStatusFilter(EMPTY_STRING);
   		form.setBillingType(EMPTY_STRING);
   		form.setLinkStatus(EMPTY_STRING);
   		form.setScreenStatus(SCREENSTATUS_SCREENLOAD);
   		form.setNetChargeMoneyDisp(dou);
   		try{
   			Money money = CurrencyHelper.getMoney("USD");
   			money.setAmount(0.0D);
   			form.setNetSummaryWeight(dou);
   			form.setNetChargeMoney(money);
   			form.setNetCPWeight(dou);
   			form.setNetLCWeight(dou);
   			form.setNetUldWeight(dou);
   			//form.setNetSalWeight(dou);
   			form.setNetSVWeight(dou);
   			form.setNetEMSWeight(dou);
   			form.setBlgCurCode("");
   			}
   			catch(CurrencyException currencyException){
   				log.entering(CLASS_NAME,"CurrencyException");
   			}
   		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

    }

}
