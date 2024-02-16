/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures.ReleaseTransactionLockInvoker.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures;

import static com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeature.LOCK_CONTEXT;

import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Invoker;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.otherfeatures.ReleaseTransactionLockInvoker.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-152054	 :	25-Jul-2022	:	Created
 */

@FeatureComponent("customermanagement.defaults.saveBrokerMappingFeature.invokeReleaseTransactionLock")
public class ReleaseTransactionLockInvoker extends Invoker<CustomerVO> {

	@Override
	public void invoke(CustomerVO featureVO) throws BusinessException, SystemException {
		@SuppressWarnings("unchecked")
		Collection<LockVO> locks = (Collection<LockVO>) getFromConext(LOCK_CONTEXT);

		Proxy.getInstance().get(FrameworkLockProxy.class).releaseLocks(locks);
	}

}
