/*
 * ProductsDefaultsProxy.java Created on DEC 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */



package com.ibsplc.icargo.business.master.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



@Module("products")
 @SubModule("defaults")

 public class ProductsDefaultsProxy extends ProductProxy{

	 private Log log =
	    	LogFactory.getLogger("OPERATIONS SHIPMENT");
	
	/**
	 * 
	 * @author
	 * @param productLovFilterVO  
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<ProductLovVO> findProductForMaster(ProductLovFilterVO productLovFilterVO)
		throws SystemException, ProxyException{
			return despatchRequest("findProductForMaster",productLovFilterVO);
	}
	
	

	/** 
	 * 
	 * 	Method		:	SharedSccProxy.findProductDetails
	 *	Added by 	:	A-7548 on 24-Jan-2018
	 * 	Used for 	:
	 *	Parameters	:	@param productFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ServiceNotAccessibleException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	Collection<ProductVO>
	 */
	public Collection<ProductVO> findProducts(ProductFilterVO productFilterVO) 
			throws SystemException, ServiceNotAccessibleException, RemoteException {
		try{
			return despatchRequest("findProducts",productFilterVO);
		}catch(ProxyException proxyException){
			throw new SystemException(proxyException.getErrors());
		}
	}
}


