/*
 * ScreenloadCommand.java Created on Feb 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.generateoutwardbillinginvoice;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.GenerateOutwardBillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.GenerateOutwardBillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *@author Rani Rose John
 * Command class for screenload of Generate Invoice screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 14, 2007   Rani Rose John    		Initial draft
 *  1.1         Nov 5 , 2008   Sandeep.T                modified for defaulting the open clearance period..
 *
 */
public class ScreenloadCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING OUTWARD");
	private static final String CLASS_NAME = "ScreenloadCommand";
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.generateinvoice";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String CLR_HOUSE="mailtracking.mra.billingAttributes";

	/**
	 *
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		String ownAirline = getApplicationSession().getLogonVO().getOwnAirlineCode();

		GenerateOutwardBillingInvoiceSession generateOutwardBillingInvoiceSession = getScreenSession(MODULE_NAME, SCREENID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		GenerateOutwardBillingInvoiceForm generateInvoiceForm =
    		(GenerateOutwardBillingInvoiceForm)invocationContext.screenModel;

		generateInvoiceForm.setOwnAirline(ownAirline);
		Collection<ErrorVO> errors = null;
	//AirNZ CR177 ONE TIME DETAILS FOR CLEARING HOUSE

    	SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();

    	Collection<String> clearingHouseValue = new ArrayList<String>();
    	clearingHouseValue.add(CLR_HOUSE);

		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					clearingHouseValue);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}

		log.log(Log.INFO, "one time value----------", oneTimeValues);
		generateOutwardBillingInvoiceSession.setClearingHouses(new ArrayList<OneTimeVO>(oneTimeValues.get(CLR_HOUSE)));

		//String clearancePeriod = "";
		//LocalDate currentDate =new LocalDate(NO_STATION, NONE, false);
		/*CRAAirlineBillingProxy craAirlineBillingProxy = new CRAAirlineBillingProxy();
		try {
			clearancePeriod=  craAirlineBillingProxy.findCurrentIchClearencePeriod(logonAttributes.getCompanyCode(),currentDate);
		}catch(ProxyException proxyException){
			log.log(Log.INFO,"proxyException obtained while dafaulting clr Period");
		}
		catch(SystemException systemException){
			log.log(Log.INFO,"SystemException obtained while dafaulting clr Period");
		}*/
		//generateInvoiceForm.setClearancePeriod(clearancePeriod);
		generateInvoiceForm.setClearingHouse("I");

		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

}
