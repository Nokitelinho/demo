/*
 * StockcontrolDefaultsProxy.java Created on 08 Jun  2008 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.customermanagement.defaults.proxy;


import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This class represents the product proxy for shared customer subsystem
 *
 * @author A-2883
 *
 *
 **/
 
@Module("stockcontrol")
@SubModule("defaults")
public class StockcontrolDefaultsProxy extends ProductProxy {
   
	private  Log log = LogFactory.getLogger("StockProxy");
	
    /**
     * @author a-2883
     * @param filterVO
     * @return StockDetailsVO
     * @throws SystemException
     * @throws ProxyException
     */
    public StockDetailsVO findCustomerStockDetails(
    StockDetailsFilterVO filterVO)
    throws SystemException,ProxyException {
	  return  despatchRequest("findCustomerStockDetails",filterVO);
    }
    
    /**
	 * 	Method		:	StockcontrolDefaultsProxy.validateStockHolders
	 *	Added by 	:	A-8154
	 * 	Used for 	: 	Calling the validateStockHolders method in StockControl module
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param stockHolderCodes
	 *	Parameters	:	@throws SystemException 
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	void
	 */
	public void validateStockHolders(String companyCode, Collection<String> stockHolderCodes) 
				throws ProxyException, SystemException{
		log.entering("StockControlDefaultsProxy", "validateStockHolders");
		despatchRequest("validateStockHolders", companyCode, stockHolderCodes);
		log.exiting("StockControlDefaultsProxy", "validateStockHolders");
	}
}
