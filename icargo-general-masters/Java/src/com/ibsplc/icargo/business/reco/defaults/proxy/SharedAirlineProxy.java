/*
 * SharedAirlineProxy.java Created on Aug 12, 2013
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.proxy;

import java.rmi.RemoteException;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.airline.AirlineBI;
import com.ibsplc.icargo.business.shared.airline.AirlineBusinessException;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-5186
 * 
 */
public class SharedAirlineProxy extends SubSystemProxy {

	private static final String SERVICE_NAME = "SHARED_AIRLINE";

	/**
	 * Retrieves the Country code to which the given station belongs
	 * 
	 * @param companyCode
	 * @param airlineCode
	 * @return AirlineValidationVO
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public AirlineValidationVO validateAlphaCode(String companyCode,
			String airlineCode) throws ProxyException, SystemException {
		AirlineBI airlineBI = null;

		try {
			airlineBI = (AirlineBI) getService(SERVICE_NAME);
			return airlineBI.validateAlphaCode(companyCode, airlineCode);
		} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,
					serviceNotAccessibleException);
		} catch (AirlineBusinessException e) {
			throw new ProxyException(e);
		} catch (RemoteException remoteException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,
					remoteException);
		}

	}

}
