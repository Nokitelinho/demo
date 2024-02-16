/*
 * ScreenLoadCustomerEnquiryCommand.java Created on Jul 02, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customerenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.CustomerEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.CustomerEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2883
 *
 */
public class ScreenLoadCustomerEnquiryCommand extends BaseCommand{

	private static final String SCREENID = "customermanagement.defaults.customerenquiry";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String CLAIM_REASON = "claims.defaults.reasoncode";
	private static final String CLAIM_NATURE = "claims.defaults.nature";
	private static final String CLAIM_STATUS = "claims.defaults.status";
	private static final String CAPACITY_STATUS = "capacity.booking.status";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		Log log = LogFactory.getLogger("CUSTOMER");
		log.entering("CUSTOMER", "ScreenLoadCustomerEnquiryCommand");
		CustomerEnquiryForm form = (CustomerEnquiryForm)invocationContext.screenModel;
		CustomerEnquirySession session = 
			(CustomerEnquirySession)getScreenSession(MODULENAME,SCREENID);
		Map<String, Collection<OneTimeVO>> oneTimeValues = 
			fetchScreenLoadDetails(getApplicationSession().getLogonVO()
				.getCompanyCode());
		
		Collection<OneTimeVO> claimReason = oneTimeValues.get(CLAIM_REASON);
		session.setClaimReason((ArrayList<OneTimeVO>) claimReason);
		Collection <OneTimeVO> claimNature =oneTimeValues.get(CLAIM_NATURE);
		session.setClaimNature((ArrayList<OneTimeVO>)claimNature);
		Collection <OneTimeVO> claimStatus =oneTimeValues.get(CLAIM_STATUS);
		session.setClaimStatus((ArrayList<OneTimeVO>)claimStatus);
		Collection <OneTimeVO> capacityStatus =oneTimeValues.get(CAPACITY_STATUS);
		session.setCapacityStatus((ArrayList<OneTimeVO>)capacityStatus);
		form.setEnquiryScreen("N");
		invocationContext.target = "success";
		
	}

	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CLAIM_REASON);
		oneTimeList.add(CLAIM_NATURE);
		oneTimeList.add(CLAIM_STATUS);
		oneTimeList.add(CAPACITY_STATUS);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();

		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException exception) {
//printStackTrrace()();
			handleDelegateException(exception);
		}
		return hashMap;
	}
	
}
