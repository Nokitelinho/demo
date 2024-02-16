/*
 * SharedAirlineProxy.java Created on Aug 10, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import com.ibsplc.icargo.business.mail.operations.SharedProxyException;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.airline.AirlineBI;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

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
	private static final String SHARED_AIRLINE = "SHARED_AIRLINE";

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
			AirlineBI airlineBI = (AirlineBI)getService(SHARED_AIRLINE);
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
			AirlineBI airlineBI = (AirlineBI)getService(SHARED_AIRLINE);
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
	/**
	 * @author A-8353
     * @param companyCode
     * @param airlineIdentifier
     * @return AirlineVO
     * @throws ProxyException
     * @throws SystemException
     */
    public AirlineVO findAirlineDetails(String companyCode,int airlineIdentifier) throws ProxyException, SystemException {
 		try {
 			AirlineBI airlineBI = (AirlineBI)getService(SHARED_AIRLINE);

 		return   airlineBI.findAirlineDetails(companyCode,
 				airlineIdentifier);
 			}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
 				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
 			}catch(RemoteException remoteException){
 				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
 			}

 	}
    /**
     * @author A-8353
     * @param companyCode
     * @param airlineIdentifier
     * @param parameterCodes
     * @return Map<String, String>
     * @throws ProxyException
     * @throws SystemException
     */
    public Map<String, String> findAirlineParametersByCode(String companyCode,
			int airlineIdentifier, Collection<String> parameterCodes)
			throws ProxyException, SystemException {

    	try {
    		AirlineBI airlineBI = (AirlineBI)getService(SHARED_AIRLINE);
    		return  airlineBI.findAirlineParametersByCode(companyCode, airlineIdentifier , parameterCodes);
    	}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
    			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
    	}catch(RemoteException remoteException){
    			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
    	}

	}
        
	public Collection<AirlineAirportParameterVO> findAirlineAirportParameters(
			AirlineAirportParameterFilterVO airlineAirportParameterFilterVO) throws ProxyException, SystemException {

		try {
			AirlineBI airlineBI = (AirlineBI) getService(SHARED_AIRLINE);
			return airlineBI.findAirlineAirportParameters(airlineAirportParameterFilterVO);
		} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, serviceNotAccessibleException);
		} catch (RemoteException remoteException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, remoteException);
		}

	}


}
