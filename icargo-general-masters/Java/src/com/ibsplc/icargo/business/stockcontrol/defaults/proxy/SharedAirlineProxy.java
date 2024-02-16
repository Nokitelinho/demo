/*
 * SharedAirlineProxy.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;


import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.airline.AirlineBI;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-1885
 *
 */


public class SharedAirlineProxy extends SubSystemProxy {
	/**
	 * Method for getting the awb check digit
	 * @author A-1885
	 * @param companyCode
	 * @param airlineIdentifier
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public AirlineValidationVO findAirline(String companyCode,int airlineIdentifier)
					throws ProxyException, SystemException{
		try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
		return  airlineBI.findAirline(companyCode,airlineIdentifier);


		}catch(com.ibsplc.icargo.business.shared.airline.AirlineBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}
	
	/**
	 * @author a-1863
	 * @param companyCode
	 * @param code
	 * @return AirlineValidationVO
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public AirlineValidationVO validateAlphaCode(String companyCode,String code)
	throws ProxyException, SystemException{
		try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
		return  airlineBI.validateAlphaCode(companyCode,code);
	

		}catch(com.ibsplc.icargo.business.shared.airline.AirlineBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}	
	/**
	 * 
	 * @param companyCode
	 * @param numericCode
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public AirlineValidationVO validateNumericCode(String companyCode, String numericCode)	
	    	throws ProxyException,SystemException{
			try {
				AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
	    	return  airlineBI.validateNumericCode(companyCode, numericCode);
			}catch(com.ibsplc.icargo.business.shared.airline.AirlineBusinessException e) {
				throw new ProxyException(e);
			}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
			}catch(RemoteException remoteException){
				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
			}
	  }
        
}
