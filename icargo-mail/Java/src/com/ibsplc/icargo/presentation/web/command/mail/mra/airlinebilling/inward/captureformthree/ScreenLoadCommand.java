/*
 * ScreenLoadCommand.java Created on June 11, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformthree;



import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormThreeSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormThreeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3108
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CaptureForm3 ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureformthree";
	private static final String STATUS_ONETIME = "mra.airlinebilling.formThreeStatus";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";
	//private static final String ACTION_FAILURE = "screenload_failure";
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
    	
    	 SharedDefaultsDelegate sharedDefaultsDelegate = new
		  SharedDefaultsDelegate(); 
    	 ApplicationSessionImpl applicationSession = getApplicationSession();
    	 LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	 captureFormThreeForm.setClearancePeriod("");
    	 Map<String, Collection<OneTimeVO>>
		  oneTimeValues = null; 
		  try {
		 
		  oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
		  logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
		   } catch (BusinessDelegateException businessDelegateException) {
			  log.log(Log.FINE, "*****in the exception");
			  businessDelegateException.getMessageVO().getErrors(); 
			  handleDelegateException(businessDelegateException);
		  }
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
    	captureFormThreeSession.setOneTimeValues(new HashMap<String, Collection<OneTimeVO>>(oneTimeValues));
    	captureFormThreeForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = ACTION_SUCCESS;
    	captureFormThreeForm.setLinkDisable("Y");
		log.exiting(CLASS_NAME, "execute");
    }
    /**
	 * 
	 * @return Collection<String>
	 */
	private Collection<String> getOneTimeParameterTypes() {

		ArrayList<String> parameterTypes = new ArrayList<String>();
		
		parameterTypes.add(STATUS_ONETIME);
		return parameterTypes;
	}

}
