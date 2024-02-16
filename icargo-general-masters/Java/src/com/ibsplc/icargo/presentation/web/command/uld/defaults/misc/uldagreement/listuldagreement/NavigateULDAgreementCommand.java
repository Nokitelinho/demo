/*
 * NavigateULDAgreementCommand.java Created on Dec 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.listuldagreement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */

public class NavigateULDAgreementCommand extends BaseCommand{
	
	  private static final String MODULE_NAME = "uld.defaults";
	  private static final String SCREEN_ID = "uld.defaults.maintainuldagreement";
	  private static final String SCREENLOAD_SUCCESS = "screenload_success";
	  private static final String TRANSACTION_TYPE = "uld.defaults.TxnType";
	  private static final String AGREEMENT_STATUS = "uld.defaults.agreementstatus";
	  private static final String PARTY_TYPE = "uld.defaults.PartyType";
	  private static final String DEMURRAGE_FREQUENCY = "uld.defaults.demurragefrequency";
	    
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("ULD_MANAGEMENT");
    	log.entering("Navigate Command","-------uldmanagement");
    	MaintainULDAgreementForm form = (MaintainULDAgreementForm)invocationContext.screenModel;
    	 MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,SCREEN_ID);
    	 ApplicationSessionImpl applicationSession = getApplicationSession();
 		LogonAttributes logonAttributes = applicationSession.getLogonVO();
         String companyCode = logonAttributes.getCompanyCode();
         Map<String,Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(companyCode);
 		Collection<OneTimeVO> transactionTypes = oneTimeCollection.get(TRANSACTION_TYPE);
 		Collection<OneTimeVO> partyTypes = oneTimeCollection.get(PARTY_TYPE);
 		Collection<OneTimeVO> status = oneTimeCollection.get(AGREEMENT_STATUS);
 		Collection<OneTimeVO> demurrageFrequencies = oneTimeCollection.get(DEMURRAGE_FREQUENCY);
 		Collection<CurrencyVO> currenyCodes = findCurrencyCodes(companyCode);
 		session.setCompanyCode(companyCode);
 		session.setTransactionType((ArrayList<OneTimeVO>)transactionTypes);
 		session.setAgreementStatus((ArrayList<OneTimeVO>)status);
 		session.setPartyType((ArrayList<OneTimeVO>)partyTypes);
 		session.setDemurrageFrequency((ArrayList<OneTimeVO>)demurrageFrequencies);
 		session.setCurrency((ArrayList<CurrencyVO>)currenyCodes);
    	 ArrayList<String> agreementNumbers = session.getAgreementNumbers();
    	 log.log(Log.FINE,"Current Page--------------->"+Integer.parseInt(form.getDisplayPage()));
    	String agreementNumber = agreementNumbers.get(Integer.parseInt(form.getDisplayPage())-1);
    	form.setAgreementNumber(agreementNumber);
    	form.setCanClear("canClearTrue");
    	invocationContext.target=SCREENLOAD_SUCCESS;
    }
    /**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String,Collection<OneTimeVO>> fetchScreenLoadDetails(String companyCode){
		Map<String,Collection<OneTimeVO>> hashMap = new 
		HashMap<String,Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(TRANSACTION_TYPE);
		oneTimeList.add(AGREEMENT_STATUS);
		oneTimeList.add(PARTY_TYPE);
		oneTimeList.add(DEMURRAGE_FREQUENCY);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try{
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
			
		}catch(BusinessDelegateException exception){
			exception.getMessage();
			error = handleDelegateException(exception);
		}
		return hashMap;
	}
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Collection<CurrencyVO> findCurrencyCodes(String companyCode){
		CurrencyDelegate currencyDelegate = new CurrencyDelegate();
		Collection<CurrencyVO> currencies = new ArrayList<CurrencyVO>();
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try{
			currencies = currencyDelegate.findAllCurrencyCodes(companyCode);
		}catch(BusinessDelegateException exception){
			exception.getMessage();
			error = handleDelegateException(exception);
		}
		return currencies;
	}
    
}
