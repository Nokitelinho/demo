/*
 * SharedAreaProxy.java Created on Jul 9, 2005
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
import com.ibsplc.icargo.business.shared.area.AreaBI;
import com.ibsplc.icargo.business.shared.area.AreaBusinessException;
import com.ibsplc.icargo.business.shared.area.station.vo.StationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-1358
 *
 */
public class SharedAreaProxy extends SubSystemProxy {

	private static final String SERVICE_NAME = "SHARED_AREA";

	/**
	 * Retrieves the Country code to which the given station belongs
	 *
	 * @param companyCode
	 * @param stationCode
	 * @return String
	 * @throws ProxyException
	 * @throws ServiceNotAccessibleException
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws InvalidStationException
	 */
	public Map<String, StationVO> validateStationCodes(String companyCode, Collection<String> stationCodes)
			throws ProxyException, SystemException {
		AreaBI areaBI=null;
		try {
			areaBI = (AreaBI) getService(SERVICE_NAME);
			return areaBI.validateStationCodes(companyCode, stationCodes);
		} catch (AreaBusinessException e) {
			throw new ProxyException(e);
		} catch (RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch (ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(), e);

		}

	}

}
