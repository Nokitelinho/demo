/*
 * ClearCommand.java Created on July 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformthree;

import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormThreeSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormThreeForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
/**
 * @author A-3108
 *
 */
public class ClearCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CaptureForm3 ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureformthree";
	
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "clear_success";
	
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	
    	CaptureMailFormThreeForm captureFormThreeForm=(CaptureMailFormThreeForm)invocationContext.screenModel;
    	CaptureFormThreeSession captureFormThreeSession = (CaptureFormThreeSession)
		getScreenSession(MODULE_NAME, SCREEN_ID);
    	captureFormThreeForm.setClearancePeriod("");
    	Money creditMoney=null;
		Money miscMoney =null;
		Money totMoney=null;
		Money netMoney=null;
		try{
		creditMoney = CurrencyHelper.getMoney("USD");
		creditMoney.setAmount(0.0D);
		miscMoney = CurrencyHelper.getMoney("USD");
		miscMoney.setAmount(0.0D);
		totMoney = CurrencyHelper.getMoney("USD");
		totMoney.setAmount(0.0D);
		netMoney = CurrencyHelper.getMoney("USD");
		netMoney.setAmount(0.0D);
		}
		catch(CurrencyException e){
			   e.getErrorCode();
		   }
    	captureFormThreeForm.setCreditTotalAmountInBillingMoney(creditMoney);
		captureFormThreeForm.setMiscTotalAmountInBillingMoney(miscMoney);
		captureFormThreeForm.setGrossTotalAmountInBillingMoney(totMoney);
		captureFormThreeForm.setNetTotalValueInBillingMoney(netMoney);
    	captureFormThreeSession.setAirlineForBillingVOs(null);  
    	captureFormThreeForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
    	captureFormThreeForm.setLinkDisable("Y");
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }
}
