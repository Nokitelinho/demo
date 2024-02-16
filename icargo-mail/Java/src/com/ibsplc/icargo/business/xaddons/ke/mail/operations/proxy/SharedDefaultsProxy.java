package com.ibsplc.icargo.business.xaddons.ke.mail.operations.proxy;

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
 * @author A-1417
 *
 */	


public class SharedDefaultsProxy extends SubSystemProxy {
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
        throws SystemException {
		try {
			SharedDefaultsBI defaultsBI = getService("SHARED_DEFAULTS");
        return defaultsBI.findOneTimeValues(companyCode,fieldType);
  

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }
  
}
