/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.otherfeatures.GetAccountStatementInvoker.java
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
package com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.otherfeatures;

import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.TRIGGER_AUTO;
import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.TRIGGER_MANUAL;
import static com.ibsplc.icargo.business.shared.customer.vo.CustomerVO.INVOICE_DISTRIBUTION_MODE_PDF;
import static com.ibsplc.icargo.business.shared.customer.vo.CustomerVO.INVOICE_DISTRIBUTION_MODE_XL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.cra.agentbilling.cass.vo.CASSFilterVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.CRAAgentBillingProxy;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Invoker;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.otherfeatures.GetAccountStatementInvoker.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-104246	 :	26-Jul-2021	:	Created
 */

@FeatureComponent("emailAccountStatementFeature.getAccountStatementInvoker")
public class GetAccountStatementInvoker extends Invoker<EmailAccountStatementFeatureVO> {

	@Override
	public void invoke(EmailAccountStatementFeatureVO featureVO) throws BusinessException, SystemException {
		String triggerPoint = featureVO.getTriggerPoint();
		Collection<List<CustomerInvoiceAWBDetailsVO>> collection = new ArrayList<>();

		switch (triggerPoint) {
		case TRIGGER_MANUAL:
			CustomerFilterVO customerVO = new CustomerFilterVO();
			customerVO.setCompanyCode(featureVO.getCompanyCode());
			customerVO.setCustomerCode(featureVO.getCustomerCode());

			collection.add(Proxy.getInstance().get(CRAAgentBillingProxy.class).findAccountStatementForPrint(customerVO));
			break;
		case TRIGGER_AUTO:
			CASSFilterVO cassVO = new CASSFilterVO();
			cassVO.setCompanyCode(featureVO.getCompanyCode());
			cassVO.setCountry(featureVO.getCountryCode());
			cassVO.setBillingReferenceNumber(featureVO.getInvoiceNumber());

			List<CustomerInvoiceAWBDetailsVO> detailsVOList = Proxy.getInstance().get(CRAAgentBillingProxy.class).findCassInvoiceAccountStatementForPrint(cassVO);

			List<String> inAptAgents = new ArrayList<>();
			Collection<String> distributionMode = Arrays.asList(INVOICE_DISTRIBUTION_MODE_PDF,
					INVOICE_DISTRIBUTION_MODE_XL);
			Map<String, List<CustomerInvoiceAWBDetailsVO>> emailVOsMap = new HashMap<>();

			detailsVOList.forEach(detail -> {
				String key = detail.getCustomerCode();

				if (emailVOsMap.containsKey(key)) {
					emailVOsMap.get(key).add(detail);
				} else {
					if (!inAptAgents.contains(key)) {
						evaluateEmailApplicability(key, detail, distributionMode, inAptAgents, emailVOsMap);
					}
				}
			});

			collection.addAll(emailVOsMap.values());
			break;
		default:
			break;
		}

		featureVO.setCustomerInvoiceAWBDetailsVOs(collection);
	}

	private void evaluateEmailApplicability(String key, CustomerInvoiceAWBDetailsVO detail,
			Collection<String> distributionMode, List<String> inAptAgents, Map<String, List<CustomerInvoiceAWBDetailsVO>> emailVOsMap) {
		Collection<String> configuredMode = Objects.nonNull(detail.getInvoiceSendMode())
				? Arrays.asList(detail.getInvoiceSendMode().split(",")): Collections.emptyList();

		if (configuredMode.isEmpty() || Collections.disjoint(configuredMode, distributionMode)) {
			inAptAgents.add(key);
		} else {
			emailVOsMap.put(key, new ArrayList<>(Arrays.asList(detail)));
		}
	}

}
