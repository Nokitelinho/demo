/*
 * SharedCustomerProxy.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;



import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerValidationVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.customer.CustomerBI;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-1885
 *
 */


public class SharedCustomerProxy extends SubSystemProxy {
	/**
	 * @param companyCode
	 * @param customerCodes
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Map<String,CustomerValidationVO> 
		validateCustomers(String companyCode,Collection<String> customerCodes)
								throws SystemException,ProxyException{
		try {
			CustomerBI customerBI = (CustomerBI)getService("SHARED_CUSTOMER");
		   return  customerBI.validateCustomers(companyCode,customerCodes);

		}catch(com.ibsplc.icargo.business.shared.customer.CustomerBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}

        
}
