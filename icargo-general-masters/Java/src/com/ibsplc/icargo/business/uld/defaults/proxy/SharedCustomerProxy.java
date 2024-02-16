/*
 * SharedCustomerProxy.java Created on Dec14, 2009
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author a-3278
 *
 */
@Module("shared")
@SubModule("customer")
public class SharedCustomerProxy extends ProductProxy {
	/**
	 * This method lists the customer details
	 * @param accounitngFilterVO 
	 * @throws SystemException
	 * @throws ProxyException
	 *
	 */
	 public CustomerVO listCustomer(CustomerFilterVO customerFilterVO)
	 	throws ProxyException ,SystemException{
	    	 return despatchRequest("listCustomer",customerFilterVO);
	    }

	/**
	 * 	Method		:	SharedCustomerProxy.validateCustomer
	 *	Added by 	:	A-7359 on 11-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param customerFilterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	CustomerVO
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public CustomerVO validateCustomer(CustomerFilterVO customerFilterVO) throws ProxyException, SystemException {
				return despatchRequest("validateCustomer",customerFilterVO);
	}

}
