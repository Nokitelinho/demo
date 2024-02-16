/*
 * SharedCustomerProxy.java Created on OCT 01, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerLovVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-3227 RENO K ABRAHAM
 */
@Module("shared")
@SubModule("customer")
public class SharedCustomerProxy  extends ProductProxy{

	/**
	 * @author A-3227  RENO K ABRAHAM on 01-OCT-08
	 * @param companyCode
	 * @param customerCodes
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Map<String,CustomerValidationVO> validateCustomers(String companyCode,Collection<String> customerCodes )
	throws SystemException , ProxyException{		
		return despatchRequest("validateCustomers",companyCode,customerCodes);				
	}
	/**
	 * Added for ICRD-266032
	 * @param customerFilterVO
	 * @return CustomerVO
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public CustomerVO findCustomerDetails(CustomerFilterVO customerFilterVO)
			throws SystemException , ProxyException{	
		return despatchRequest("listCustomer",customerFilterVO);				
	}

	/**
	 * Added for IASCB-35031
	 * @param customerFilterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<CustomerLovVO> findCustomers(CustomerFilterVO customerFilterVO)
			throws SystemException, ProxyException {
		return despatchRequest("findCustomerLov", customerFilterVO);
	}
	/**
	 * 
	 * 	Method		:	SharedCustomerProxy.getAllCustomerDetails
	 * 	Used for 	:	IASCB-51778
	 *	Parameters	:	@param customerFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
    public Collection<CustomerVO> getAllCustomerDetails(CustomerFilterVO customerFilterVO)
			throws SystemException, ProxyException {
		return despatchRequest("getAllCustomerDetails", customerFilterVO);
    }
}
