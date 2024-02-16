/*
 *The Class sharedMasterGroupingProxy. 
 */
package com.ibsplc.icargo.business.products.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.generalmastergrouping.GeneralMasterGroupBI;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * Author A-6843
 */
public class SharedMasterGroupingProxy extends SubSystemProxy {
	
	/** The Constant SERVICE_NAME. */
	private static final String SERVICE_NAME = "SHARED_GENERALMASTERGROUPING";
	
	/**
	 * Find group names of group entity.
	 *
	 * @param generalMasterGroupFilterVO the general master group filter vo
	 * @return Collection<GeneralMasterGroupVO>
	 * @throws ProxyException the proxy exception
	 * @throws SystemException the system exception
	 */
	public Collection<GeneralMasterGroupVO> findGroupNamesToValidate(
			GeneralMasterGroupFilterVO generalMasterGroupFilterVO)
			throws ProxyException, SystemException {
		try {
			GeneralMasterGroupBI generalMasterGroupBI = (GeneralMasterGroupBI) getService(SERVICE_NAME);
			return generalMasterGroupBI
					.findGroupNamesToValidate(generalMasterGroupFilterVO);
		} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,
					serviceNotAccessibleException);
		} catch (RemoteException remoteException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,
					remoteException);
		}
	}
}