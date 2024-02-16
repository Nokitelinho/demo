/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.proxy.SharedBillingSiteProxy.java
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.proxy;

import com.ibsplc.icargo.business.shared.billingsite.vo.BillingSiteFilterVO;
import com.ibsplc.icargo.business.shared.billingsite.vo.BillingSiteVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.proxy.SharedBillingSiteProxy.java
 *	Version	:	Name						 :	Date					:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-118899	 :	18-Aug-2021	:	Created
 */

@Module("shared")
@SubModule("billingsite")
public class SharedBillingSiteProxy extends ProductProxy {

	/**
	 * Method	:	SharedBillingSiteProxy.displayBillingSiteDetails()
	 * Used for 	:	to get billing site of station or the default
	 */
	public BillingSiteVO findBillingSiteDetails(BillingSiteFilterVO filterVO) throws ProxyException, SystemException {
		return despatchRequest("findBillingSiteDetails", filterVO);
	}

}
