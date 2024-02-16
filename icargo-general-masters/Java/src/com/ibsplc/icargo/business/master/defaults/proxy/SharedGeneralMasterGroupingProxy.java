package com.ibsplc.icargo.business.master.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.generalmastergrouping.GeneralMasterGroupBI;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupDetailsVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SharedGeneralMasterGroupingProxy  extends SubSystemProxy {
	private Log log = LogFactory.getLogger("operations.cooltool.");	
	public Collection<GeneralMasterGroupDetailsVO> findGroupsByType(GeneralMasterGroupFilterVO filterVO) 
			throws ProxyException,SystemException {
		try {
			GeneralMasterGroupBI generalMasterGroupBI = (GeneralMasterGroupBI)getService("SHARED_GENERALMASTERGROUPING");		
			log.entering("SharedGeneralMasterGroupingProxy","findGroupsByType");		
			return  generalMasterGroupBI.findGroupsByType(filterVO);
		} catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}
		catch(PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
		
	}
}
