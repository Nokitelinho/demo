/**
 *	Java file	: 	com.ibsplc.icargo.business.reco.defaults.proxy.ProductDefaultsProxy.java
 *
 *	Created by	:	A-5867
 *	Created on	:	06-Oct-2014
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.proxy;

import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.products.defaults.InvalidProductException;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-5867
 *
 */
@Module("products")
@SubModule("defaults")
public class ProductDefaultsProxy extends ProductProxy{
	/**
	 * 
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */

	public String validateProduct(String companyCode, String productName, LocalDate startDate, LocalDate endDate)
	throws SystemException, ProxyException
	{
		return despatchRequest("validateProduct", 
				companyCode, productName, startDate, endDate
		);
	}
	public Map<String, ProductVO> validateProductNames(String companyCode,
    		Collection<String> productNames) throws SystemException,
    		InvalidProductException,ProxyException
    		{
		   return despatchRequest("validateProductNames", 
				companyCode,productNames
				);
    		}

}
