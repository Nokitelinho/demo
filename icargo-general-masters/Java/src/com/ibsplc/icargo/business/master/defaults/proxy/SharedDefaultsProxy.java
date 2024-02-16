package com.ibsplc.icargo.business.master.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.defaults.SharedDefaultsBI;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParameterConfigurationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.master.defaults.proxy.SharedDefaultsProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7797	:	24-Jul-2018	:	Draft
 */
public class SharedDefaultsProxy extends SubSystemProxy {
	public Collection<GeneralParameterConfigurationVO> findGeneralParameterConfiguration(
			GeneralParameterConfigurationVO generalParameterConfigurationVO)
			throws ProxyException, SystemException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI) getService("SHARED_DEFAULTS");
			return defaultsBI.findGeneralParameterConfiguration(generalParameterConfigurationVO);
		} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException("CON002", serviceNotAccessibleException);
		} catch (RemoteException remoteException) {
			throw new SystemException("CON002", remoteException);
		}

	}

}