/*
 * SharedCommodityProxy.java Created on Sep 11 , 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;


import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.commodity.CommodityBI;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-1936
 *
 */


public class SharedCommodityProxy extends SubSystemProxy {

	/**
	 * 
	 * @author A-1936
	 * @param companyCode
	 * @param commodites
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 * @since j2se1.5
	 * @since j2ee1.4
	 */
	public HashMap<String,CommodityValidationVO> validateCommodityCodes( String companyCode,Collection<String> 
						commodites)
						throws SystemException,ProxyException{
		try {
			CommodityBI commodityBI = (CommodityBI)getService("SHARED_COMMODITY");
        return  (HashMap<String,CommodityValidationVO>) commodityBI.validateCommodityCodes(companyCode,
        		commodites);		

		}catch(com.ibsplc.icargo.business.shared.commodity.CommodityBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}
}
