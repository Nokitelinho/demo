/*
 * SharedAreaProxy.java Created on Oct 08, 2010
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.area.AreaBI;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author a-3278
 *
 */
@Module("shared")
@SubModule("area")
public class SharedAreaProxy extends SubSystemProxy {
	private static final String SERVICE_NAME = "SHARED_AREA";

	/**
	 * The method is used to fetch the StationAirport parameters from <i>SHRSTNARPPARMST </i>
	 * @author A-3278
	 * @param companyCode
	 * @param airportCode
	 * @param parameterCodes
	 * @return Map<String,String>
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Map<String, String> findAirportParametersByCode(String companyCode,
			String airportCode, Collection<String> parameterCodes)
			throws SystemException, ProxyException {
		try {
			AreaBI areaBI = (AreaBI) getService(SERVICE_NAME);
			return areaBI.findAirportParametersByCode(companyCode, airportCode,
					parameterCodes);
		} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,
					serviceNotAccessibleException);
		} catch (RemoteException remoteException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,
					remoteException);
		}
	}

}
