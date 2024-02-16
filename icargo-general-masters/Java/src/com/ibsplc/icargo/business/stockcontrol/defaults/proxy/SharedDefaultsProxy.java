/*
 * SharedDefaultsProxy.java Created on Feb 09, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.defaults.SharedDefaultsBI;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-1954
 * 
 */


public class SharedDefaultsProxy extends SubSystemProxy {  

    /**
 	 * This method is used for getting the details of system parameter codes.
 	 *
	 * @param systemparameterCodes
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public Map<String, String> findSystemParameterByCodes(
				Collection<String> systemparameterCodes)
			throws ProxyException, SystemException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
		return  defaultsBI.findSystemParameterByCodes(systemparameterCodes);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}
	/**
     * This method will check the onetime value
     * @param companyCode
     * @param fieldType collection
     * @return HashMap
     * @throws ProxyException 
     * @throws SystemException
     */
    public Map<String, Collection<OneTimeVO>> findOneTimeValues(
        String companyCode, Collection<String> fieldType)
        throws ProxyException,SystemException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
        return  defaultsBI.findOneTimeValues(companyCode,fieldType);
  

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }    
	
	
	

}
