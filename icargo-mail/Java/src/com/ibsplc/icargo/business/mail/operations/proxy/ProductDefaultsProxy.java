/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy.java
 *
 *	Created by	:	A-7531
 *	Created on	:	24-Apr-2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	24-Apr-2019	:	Draft
 */
@Module("products")
@SubModule("defaults")
public class ProductDefaultsProxy extends ProductProxy {
	
	
	
	public Collection<ProductValidationVO> findProductsByName(String companyCode,String productName)
	throws SystemException{
		
		Collection<ProductValidationVO> productValidationVOs=new ArrayList<ProductValidationVO>();
		try {
			productValidationVOs=despatchRequest("findProductsByName",companyCode,productName);
		} catch (ProxyException e) {
			
			e.getMessage();
		}
		return productValidationVOs;
	}

	/**
	 * 	Method		:	ProductDefaultsProxy.findProductDetails
	 *	Added by 	:	A-7531 on 24-Apr-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param product
	 *	Parameters	:	@return 
	 *	Return type	: 	ProductVO
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public ProductVO findProductDetails(String companyCode, String product)throws SystemException{
		
		ProductVO productVO=new ProductVO();
		try {
			productVO=despatchRequest("findProductDetails",companyCode,product);
		} catch (ProxyException proxyException) {
		
			proxyException.getMessage();
		}
		return productVO;
	}


}
