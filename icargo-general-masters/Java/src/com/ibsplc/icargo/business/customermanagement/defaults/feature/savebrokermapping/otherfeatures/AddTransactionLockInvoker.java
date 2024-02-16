/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures.AddTransactionLockInvoker.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures;

import static com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeature.LOCK_ACTION;
import static com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeature.LOCK_CONTEXT;
import static com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeature.LOCK_DESCRIPTION;
import static com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeature.LOCK_REMARKS;
import static com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeature.SCREENID;
import static com.ibsplc.xibase.server.framework.persistence.lock.ClientType.APPLICATION;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Invoker;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures.AddTransactionLockInvoker.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-152054	 :	25-Jul-2022	:	Created
 */

@FeatureComponent("customermanagement.defaults.saveBrokerMappingFeature.invokeAddTransactionLock")
public class AddTransactionLockInvoker extends Invoker<CustomerVO> {

	@Override
	public void invoke(CustomerVO featureVO) throws BusinessException, SystemException {
		LockVO lockVO = new TransactionLockVO(LOCK_ACTION);
		lockVO.setCompanyCode(featureVO.getCompanyCode());
		lockVO.setStationCode(featureVO.getStationCode());
		lockVO.setAction(LOCK_ACTION);
		lockVO.setScreenId(SCREENID);
		lockVO.setDescription(LOCK_DESCRIPTION);
		lockVO.setRemarks(LOCK_REMARKS);
		lockVO.setClientType(APPLICATION);

		Collection<LockVO> locks = new ArrayList<>();
		locks.add(lockVO);

		addToContext(LOCK_CONTEXT, locks);

		Proxy.getInstance().get(FrameworkLockProxy.class).addLocks(locks);
	}

}
