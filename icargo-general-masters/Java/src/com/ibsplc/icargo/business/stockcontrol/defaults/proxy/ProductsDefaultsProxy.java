/*
 * ProductsDefaultsProxy.java Created on Aug 2, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductStockVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This class represents the Proxy class for calling the business methods of the
 * Products Defaults subsystem.
 * 
 * @author A-1954
 * 
 */
@Module("products")
@SubModule("defaults")
public class ProductsDefaultsProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("STOCK CONTROL");

	/**
	 * @param companyCode
	 * @param documentType
	 * @param documentSubType
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<ProductStockVO> findProductsForStock(
			String companyCode,String documentType,String documentSubType) 
		throws SystemException, ProxyException {
		log.log(Log.FINE, "companyCode : ", companyCode);
		log.log(Log.FINE, "documentType : ", documentType);
		log.log(Log.FINE, "documentSubType : ", documentSubType);
		return despatchRequest("findProductsForStock", companyCode, 
										documentType, documentSubType);
	}
	
}
