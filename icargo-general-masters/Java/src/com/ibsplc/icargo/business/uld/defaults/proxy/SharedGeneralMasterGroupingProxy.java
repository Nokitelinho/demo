package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.generalmastergrouping.GeneralMasterGroupBI;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SharedGeneralMasterGroupingProxy extends SubSystemProxy {
	
	private Log log=  LogFactory.getLogger("ULD_DEFAULTS");
	
	public Collection<String> findEntitiesForGroups(
			GeneralMasterGroupFilterVO generalMasterGroupFilterVO) throws SystemException {
		try {
			GeneralMasterGroupBI generalMasterGroupBI = getService("SHARED_GENERALMASTERGROUPING");		
			log.entering("SharedGeneralMasterGroupingProxy","findGroupNamesofGroupEntity");		
			return  generalMasterGroupBI.findEntitiesForGroups(generalMasterGroupFilterVO);
		} catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
		
		
	}

}
