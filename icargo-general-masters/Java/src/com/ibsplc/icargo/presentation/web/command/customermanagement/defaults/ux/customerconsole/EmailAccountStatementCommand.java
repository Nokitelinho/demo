/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole.EmailAccountStatementCommand.java
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */

/**
 * Each method of this class must detail the functionality briefly in the javadoc comments preceding the method.
 * This is to be followed in case of change of functionality also.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole;

import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.TRIGGER_MANUAL;
import static com.ibsplc.icargo.business.shared.customer.vo.CustomerVO.INVOICE_DISTRIBUTION_MODE_PDF;
import static com.ibsplc.icargo.business.shared.customer.vo.CustomerVO.INVOICE_DISTRIBUTION_MODE_XL;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.customer.CustomerDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.CustomerConsoleModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole.EmailAccountStatementCommand.java
 *	Version	:	Name						  :	 Date			:	Updation
 * -------------------------------------------------------------------------------------
 *		0.1		:	for  IASCB-104246  :	 23-Jul-2021	:	Created
 */

public class EmailAccountStatementCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT DEFAULTS");
	private static final String RESPONSE_FAILURE = "ERROR";
	private static final String RESPONSE_SUCCESS = "INFO";

	@Override
	public void execute(ActionContext context) throws BusinessDelegateException, CommandInvocationException {
		log.entering(this.getClass().getSimpleName(), "execute");
		CustomerConsoleModel model = (CustomerConsoleModel) context.getScreenModel();
		LogonAttributes logonAttribute = getLogonAttribute();
		ResponseVO responseVO = new ResponseVO();

		CustomerFilterVO filterVO = new CustomerFilterVO();
		filterVO.setCustomerCode(model.getCustomerCode().toUpperCase().trim());
		filterVO.setCompanyCode(logonAttribute.getCompanyCode());

		CustomerVO customerVO = new CustomerDelegate().validateCustomer(filterVO);

		Collection<String> distributionMode = Arrays.asList(INVOICE_DISTRIBUTION_MODE_PDF, INVOICE_DISTRIBUTION_MODE_XL);
		Collection<String> configuredMode = Objects.nonNull(customerVO.getInvoiceSendingMode())
				? Arrays.asList(customerVO.getInvoiceSendingMode().split(",")) : Collections.emptyList();

		if (configuredMode.isEmpty() || Collections.disjoint(configuredMode, distributionMode)) {
			responseVO.setStatus(RESPONSE_FAILURE);
		} else {
			EmailAccountStatementFeatureVO featureVO = new EmailAccountStatementFeatureVO();
			featureVO.setCompanyCode(logonAttribute.getCompanyCode());
			featureVO.setStationCode(logonAttribute.getStationCode());
			featureVO.setCustomerCode(customerVO.getCustomerCode());
			featureVO.setCustomerBillingEmailOne(customerVO.getCustomerBillingDetailsVO().getEmail());
			featureVO.setCustomerBillingEmailTwo(customerVO.getCustomerBillingDetailsVO().getEmailOne());
			featureVO.setCustomerBillingEmailThree(customerVO.getCustomerBillingDetailsVO().getEmailTwo());
			featureVO.setInvoiceDistributionMode(configuredMode);
			featureVO.setTriggerPoint(TRIGGER_MANUAL);
			new CustomerMgmntDefaultsDelegate().emailAccountStatement(featureVO);

			responseVO.setStatus(RESPONSE_SUCCESS);
		}

		context.setResponseVO(responseVO);
		log.exiting(this.getClass().getSimpleName(), "execute");
	}

}
