/*
 * ScreenLoadListCustomerCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

/**
 * @author A-1496
 * 
 */
public class ScreenLoadListCustomerCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String CUSTOMER_STATUS = "customer.RegisterCustomer.status";
	// CODE ADDED BY A-5219 FOR ICRD-18283 START
	private static final String LOCATION_TYPES = "shared.agent.locationtype";
	private static final String CUSTOMER_TYPES = "shared.customer.customertype";
	// CODE ADDED BY A-5219 END
/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		 ListCustomerForm form = (ListCustomerForm)invocationContext.screenModel;
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchScreenLoadDetails(companyCode);
		Collection<OneTimeVO> statusValues = oneTimeValues.get(CUSTOMER_STATUS);
		// CODE ADDED BY A-5219 FOR ICRD-18283 START
		Collection<OneTimeVO> locationTypes = oneTimeValues.get(LOCATION_TYPES);
		Collection<OneTimeVO> customerTypes = oneTimeValues.get(CUSTOMER_TYPES);
		session.setCustomerStatus((ArrayList<OneTimeVO>) statusValues);
		session.setLocationType((ArrayList<OneTimeVO>) locationTypes);
		session.setCustomerType((ArrayList<OneTimeVO>) customerTypes);
		// CODE ADDED BY A-5219 END
		form.setScreenFlag("Screenload");
		session.setCustomerVOs(null);
		session.setListFilterVO(null);
		invocationContext.target = SCREENLOAD_SUCCESS;

	}

	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CUSTOMER_STATUS);
		// CODE ADDED BY A-5219 FOR ICRD-18283 START
		oneTimeList.add(LOCATION_TYPES);
		oneTimeList.add(CUSTOMER_TYPES);
		// CODE ADDED BY A-5219 END
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
