/*
 * SharedDefaultsProxy Created on Sep 27, 2013
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.proxy;

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
 * The Class SharedDefaultsProxy.
 *
 * @author A-5160
 */

public class SharedDefaultsProxy extends SubSystemProxy
{

    /**
     * Instantiates a new shared defaults proxy.
     */
    public SharedDefaultsProxy()
    {
    }

    /**
     * Find system parameter by codes.
     *
     * @param parameterCodes the parameter codes
     * @return the map
     * @throws ProxyException the proxy exception
     * @throws SystemException the system exception
     * @author a-5160
     */
    public Map<String, String> findSystemParameterByCodes(
			Collection<String>parameterCodes)
		throws ProxyException,SystemException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
	    return  defaultsBI.findSystemParameterByCodes(parameterCodes);
	
	
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
    }

    /**
     * Find one time values.
     * @author a-5160
     * @param companyCode the company code
     * @param fieldType the field type
     * @return the map
     * @throws ProxyException the proxy exception
     * @throws SystemException the system exception
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


