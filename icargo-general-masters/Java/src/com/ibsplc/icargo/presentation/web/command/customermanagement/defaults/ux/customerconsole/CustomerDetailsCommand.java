
/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole.CustomerDetailsCommand.java
 *
 *	Created by	:	A-8227
 *	Created on	:	Sep 05, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.CustomerConsoleModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common.CustomerConsoleModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerPreferenceVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole.CustomerDetailsCommand.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 
 * 0.1 : A-8227 : Sep 05, 2018 : Draft
 */
public class CustomerDetailsCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT DEFAULTS");
	private static final String CLASS_NAME = "CustomerDetailsCommand";
	private static final String ERR_CUSTOMER_INVALID = "customermanagement.defaults.customerconsole.error.invalidcuscod";

	private static final String FIELD_TYPE_CUSTOMER_STATUS = "customer.RegisterCustomer.status";
	private static final String ACCTRESP="ACCTRESP";

	/**
	 * Overriding Method : @see	 
	 * * com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 * Added by : A-8227 on Sep 05, 2018 
	 * Used for : Parameters : @param
	 * actionContext Parameters : @throws BusinessDelegateException 
	 * Parameters
	 * : @throws CommandInvocationException
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CustomerConsoleModel customerConsoleModel = (CustomerConsoleModel) actionContext.getScreenModel();
		LogonAttributes logonAttribute = getLogonAttribute();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		UserVO userVO = null;
		CustomerFilterVO filterVO = new CustomerFilterVO();
		filterVO.setCustomerCode(customerConsoleModel.getCustomerCode().toUpperCase().trim());
		filterVO.setCompanyCode(logonAttribute.getCompanyCode());

		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();

		try {

			CustomerVO customerVO = delegate.listCustomer(filterVO);

			if (null != customerVO) {
				SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();

				try {
					oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(logonAttribute.getCompanyCode(),
							getOneTimeParameterTypes());
				} catch (BusinessDelegateException e) {
					actionContext.addAllError(handleDelegateException(e));
				}

				if (null != customerVO.getCustomerPreferences()) {
					for (CustomerPreferenceVO customerPreferenceVO : customerVO.getCustomerPreferences()) {
						if (customerPreferenceVO.getPreferenceCode().equalsIgnoreCase(ACCTRESP)) {
							try {
								userVO = delegate.findUserDetails(logonAttribute.getCompanyCode(),
										customerPreferenceVO.getPreferenceValue());
							} catch (BusinessDelegateException e) {
								actionContext.addAllError(handleDelegateException(e));
							}

						}
					}
				}
				customerConsoleModel.setCustomerDetails(
						CustomerConsoleModelConverter.constructCustomerDetails(customerVO, oneTimeValues, userVO));

			}
			else{
				actionContext.addError(new ErrorVO(ERR_CUSTOMER_INVALID));
			}

		} catch (BusinessDelegateException e) {
			actionContext.addError(new ErrorVO(ERR_CUSTOMER_INVALID));
		}

		ResponseVO responseVO = new ResponseVO();
		List<CustomerConsoleModel> results = new ArrayList<CustomerConsoleModel>();
		results.add(customerConsoleModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
		log.exiting(CLASS_NAME, "execute");

	}

	private Collection<String> getOneTimeParameterTypes() {
		Collection<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(FIELD_TYPE_CUSTOMER_STATUS);
		return parameterTypes;
	}

}
