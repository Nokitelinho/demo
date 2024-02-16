/*
 * ScreenLoadCommand.java created on Jun 25, 2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchEnquiryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class ScreenLoadCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";

	private static final String SCREEN_SUCCESS = "screenload_success";

	private static final String DESPATCHENQTYPE_ONETIME = "mra.defaults.despatchenqtype";
	
	private static final String BILLINGSTATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String INVOICESTATUS_ONETIME = "mra.gpabilling.invoicestatus";
	
	private static final String AIRLINE_BILLINGSTATUS_ONETIME = "mra.airlinebilling.billingstatus";
	
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
		DespatchEnquiryForm despatchEnquiryForm=(DespatchEnquiryForm)invocationContext.screenModel;
		DespatchEnqSession despatchEnqSession=getScreenSession(MODULE_NAME,SCREENID);
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());		
		despatchEnqSession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
		despatchEnquiryForm.setDespatchEnqTyp("G");
		despatchEnquiryForm.setListed("NO");
		despatchEnqSession.setDespatchEnquiryVO(new DespatchEnquiryVO());
		despatchEnqSession.removeGPABillingDtls();
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		
		
	}
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
		log.entering(CLASS_NAME,"fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList=new ArrayList<String>();
		oneTimeList.add(DESPATCHENQTYPE_ONETIME);
		oneTimeList.add(BILLINGSTATUS_ONETIME);
		oneTimeList.add(INVOICESTATUS_ONETIME);
		oneTimeList.add(AIRLINE_BILLINGSTATUS_ONETIME);
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {			
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME,"fetchOneTimeDetails");
		return hashMap;
	}

}
