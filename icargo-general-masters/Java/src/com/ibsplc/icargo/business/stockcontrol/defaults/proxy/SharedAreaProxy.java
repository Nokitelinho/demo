/*
 * SharedAreaProxy.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.area.station.vo.StationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.area.AreaBI;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryValidationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1885
 * 
 */


public class SharedAreaProxy extends SubSystemProxy {
	
	private Log log = LogFactory.getLogger("STOCK CONTROL PROXY");
	public static final String SHARED_AREA = "SHARED_AREA";
	
	/**
	 * @param companyCode
	 * @param stationCodes
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Map<String, StationVO> validateStationCodes(String companyCode,
			Collection<String> stationCodes) throws SystemException,
			ProxyException {
		try {
			AreaBI areaBI = (AreaBI)getService("SHARED_AREA");
		log.log(Log.FINE, "--companyCode--", companyCode);
		log.log(Log.FINE, "--stationCodes--", stationCodes);
		return  areaBI.validateStationCodes(companyCode,
				stationCodes);

		}catch(com.ibsplc.icargo.business.shared.area.AreaBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param parameterCodes
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Map<String,String> findAirportParametersByCode(String companyCode,
			   String airportCode,Collection<String> parameterCodes)
			   throws SystemException, ProxyException {
		try {
			AreaBI areaBI = (AreaBI)getService("SHARED_AREA");
		log.log(Log.FINE, "--companyCode--", companyCode);
		log.log(Log.FINE, "--airportCode--", airportCode);
		log.log(Log.FINE, "--parameterCodes--", parameterCodes);
		return  areaBI.findAirportParametersByCode(companyCode,airportCode, parameterCodes);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}
	/**
	 * 
	 * 	Method		:	SharedAreaProxy.findAWBReuseRestrictionDetails
	 *	Added by 	:	A-6858 on 13-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param destination
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	CountryVO
	 */
	public Collection<CountryValidationVO> findAWBReuseRestrictionDetails(String companyCode,String destination,String origin)
			throws SystemException, ProxyException{
		log.entering("SharedAreaProxy", "findAWBReuseRestrictionDetails");
		try{
			AreaBI areaBI = (AreaBI)getService("SHARED_AREA");
			return areaBI.findAWBReuseRestrictionDetails(companyCode,destination,origin);
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	}
	
	public Collection<CountryValidationVO> findAWBReuseRestrictionDetailsEnhanced(DocumentFilterVO documentFilterVo)
			throws SystemException, ProxyException{
		log.entering("SharedAreaProxy", "findAWBReuseRestrictionDetails");
		try{
			AreaBI areaBI = (AreaBI)getService(SHARED_AREA);
			return areaBI.findAWBReuseRestrictionDetailsEnhanced(documentFilterVo.getCompanyCode(),documentFilterVo.getAwbDestination(),documentFilterVo.getAwbOrigin(),documentFilterVo.getAwbViaPoints());
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	}
}
