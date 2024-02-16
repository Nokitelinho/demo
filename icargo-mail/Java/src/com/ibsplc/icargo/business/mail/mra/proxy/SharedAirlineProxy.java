/*
 * SharedAirlineProxy.java Created on Aug 10, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.proxy;

import com.ibsplc.icargo.business.mail.mra.SharedProxyException;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.airline.AirlineBI;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1617
 * 
 */


public class SharedAirlineProxy extends SubSystemProxy {
	private Log log = LogFactory.getLogger("SharedAirlineProxy ");

	/**
	 * This method is used to validate the AlphaCode
	 * 
	 * @author A-1936
	 * @param companyCode
	 * @param airlineCode
	 * @return
	 * @throws SystemException
	 * @throws SharedProxyException
	 */
	public AirlineValidationVO validateAlphaCode(String companyCode,
			String airlineCode) throws SystemException, SharedProxyException {

		log.entering("INSIDE THE AIRLINE", "PROXY");
		AirlineValidationVO airlineValidationVO = null;
		try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
			airlineValidationVO =  airlineBI.validateAlphaCode(companyCode, airlineCode);
		} catch(com.ibsplc.icargo.business.shared.airline.AirlineBusinessException e) {
			for (ErrorVO error : e.getErrors()) {
				throw new SharedProxyException(
						SharedProxyException.INVALID_AIRLINE,
						new Object[] { error.getErrorData() });
			}
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(), e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(), e);
		}
		return airlineValidationVO;

		

	}
	public AirlineValidationVO findAirline(String companyCode,
			int carrierId) throws SystemException, SharedProxyException {

		log.entering("INSIDE THE AIRLINE", "PROXY");
		AirlineValidationVO airlineValidationVO = null;
		try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
			airlineValidationVO =  airlineBI.findAirline(companyCode, carrierId);
		} catch(com.ibsplc.icargo.business.shared.airline.AirlineBusinessException e) {
			for (ErrorVO error : e.getErrors()) {
				throw new SharedProxyException(
						SharedProxyException.INVALID_AIRLINE,
						new Object[] { error.getErrorData() });
			}
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(), e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(), e);
		}
		return airlineValidationVO;

		

	}


}
