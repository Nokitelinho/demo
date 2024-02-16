/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeature.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */

/**
 * Each method of this class must detail the functionality briefly in the javadoc comments preceding the method.
 * This is to be followed in case of change of functionality also.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping;

import java.util.ArrayList;

import com.ibsplc.icargo.business.customermanagement.defaults.CustomerBusinessException;
import com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntController;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeature.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-152054	 :	25-Jul-2022	:	Created
 */

@FeatureComponent("customermanagement.defaults.saveBrokerMappingFeature")
@Feature(exception = CustomerBusinessException.class, event = "CUSTOMERMANAGEMENT_SAVEBROKERMAPPINGDOCUMENT")
public class SaveBrokerMappingFeature extends AbstractFeature<CustomerVO> {

	public static final String LOCK_CONTEXT = "SaveBrokerMappingFeature_TransactionLockInvoker";
	public static final String LOCK_ACTION = "MODIFYCUS";
	public static final String LOCK_REMARKS = "CUSTOMER LOCKING";
	public static final String LOCK_DESCRIPTION = "Customer Modifications";
	public static final String SCREENID = "customermanagement.defaults.ux.brokermapping";
	public static final String BROKERMAPPINGSAVE_SOURCE = "MNGPOA";
	
	@Override
	protected void preInvoke(CustomerVO featureVO) throws SystemException, BusinessException {
		invoke("customermanagement.defaults.saveBrokerMappingFeature.invokeAddTransactionLock", featureVO);
	}
	
	@Override
	protected FeatureConfigVO fetchFeatureConfig(CustomerVO featureVO) {
		FeatureConfigVO vo = new FeatureConfigVO();
		vo.setValidatorIds(new ArrayList<>());
		vo.setEnricherId(new ArrayList<>());
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String perform(CustomerVO featureVO) throws SystemException, BusinessException {
		CustomerMgmntController controller = (CustomerMgmntController) SpringAdapter.getInstance().getBean("customerMgmntController");
		String customerCode = controller.registerCustomer(featureVO);
		
		featureVO.setCustomerCode(customerCode != null ? customerCode : featureVO.getCustomerCode());
		
		return customerCode;
	}
	
	@Override
	protected void postInvoke(CustomerVO featureVO) throws BusinessException, SystemException {
		invoke("customermanagement.defaults.saveBrokerMappingFeature.invokeReleaseTransactionLock", featureVO);
	}

}
