package com.ibsplc.icargo.business.reco.defaults.proxy;

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
 * @author A-5867
 * 
 */
public class SharedMasterGroupingProxy extends SubSystemProxy {
	private static final String SERVICE_NAME = "SHARED_GENERALMASTERGROUPING";

	/**
	 *
	 * @param generalMasterGroupFilterVO
	 * @return Collection<GeneralMasterGroupVO>
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public Collection<GeneralMasterGroupVO> findGroupNamesofGroupEntity(
			GeneralMasterGroupFilterVO generalMasterGroupFilterVO)
			throws ProxyException, SystemException {
		try {
			GeneralMasterGroupBI generalMasterGroupBI = (GeneralMasterGroupBI) getService(SERVICE_NAME);
			return generalMasterGroupBI
					.findGroupNamesofGroupEntity(generalMasterGroupFilterVO);

		} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,
					serviceNotAccessibleException);
		} catch (RemoteException remoteException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,
					remoteException);
		}
	}
}
