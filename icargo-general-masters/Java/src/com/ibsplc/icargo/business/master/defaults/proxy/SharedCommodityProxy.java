/*
 * SharedCommodityProxy.java Created on Dec 22, 2016
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
import com.ibsplc.icargo.business.shared.commodity.CommodityBI;
import com.ibsplc.icargo.business.shared.commodity.CommodityBusinessException;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityFilterVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-3109
 *
 */


public class SharedCommodityProxy extends SubSystemProxy {

	
  /**
   * 	 
   * @param commodityFilterVO
   * @return
   * @throws ProxyException
   * @throws SystemException
   * @throws CommodityBusinessException 
   */
	public Collection<CommodityValidationVO> getAllCommoditiesForMaster(CommodityFilterVO commodityFilterVO)
			throws ProxyException,SystemException, CommodityBusinessException {
		try {
			CommodityBI commodityBI = (CommodityBI)getService("SHARED_COMMODITY");
					return  commodityBI.getAllCommoditiesForMaster(commodityFilterVO);
  
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
		
	}
	
}
