/*
 * SharedAreaProxy.java Created on May 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to lice nse terms.
 */

package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.SharedProxyException;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.area.AreaBI;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This is the product proxy for Shared Area
 * 
 * @author A-1936
 * 
 */


public class SharedAreaProxy extends SubSystemProxy {

	private Log log = LogFactory.getLogger("SHARED_AREA_PROXY");
	private static final String SHARED_AREA ="SHARED_AREA";
	/**
	 * @author a-1936 This method is used to validate the City
	 * @param companyCode
	 * @param cityCodes
	 * @return
	 * @throws SystemException
	 * @throws SharedProxyException
	 */
	public Map<String, CityVO> validateCityCodes(String companyCode,
			Collection<String> cityCodes) throws SystemException,
			SharedProxyException {

		log.entering("SHARED_AREA_PROXY", "validateCityCodes");
		Map<String, CityVO> cityMap = null;
		try {
			AreaBI areaBI = (AreaBI)getService(SHARED_AREA);
		
			cityMap =  areaBI.validateCityCodes(companyCode,
					cityCodes);
		}catch(com.ibsplc.icargo.business.shared.area.AreaBusinessException e) {
			for (ErrorVO error : e.getErrors()) {
				throw new SharedProxyException(
						SharedProxyException.INVALID_CITY, new Object[] { error
								.getErrorData() });
			}
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(),e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(),e);
		}
		return cityMap;


	}

	/**
	 * @author a-1936 This method is used to validate the Country
	 * @param companyCode
	 * @param countryCodes
	 * @return
	 * @throws SystemException
	 * @throws SharedProxyException
	 */
	public Map<String, CountryVO> validateCountryCodes(String companyCode,
			Collection<String> countryCodes) throws SystemException,
			SharedProxyException {

		log.entering("AreaDelegate", "validateCountryCodes");
		Map<String, CountryVO> countryMap = null;
	
		try {
			AreaBI areaBI = (AreaBI)getService(SHARED_AREA);	
			countryMap =  areaBI.validateCountryCodes(companyCode,
					countryCodes);
		} catch(com.ibsplc.icargo.business.shared.area.AreaBusinessException e) {
			for (ErrorVO error : e.getErrors()) {
				throw new SharedProxyException(
						SharedProxyException.INVALID_COUNTRY,
						new Object[] { error.getErrorData() });
			}
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(),e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(),e);
		}
		return countryMap;


	}
	/**
	 * @author a-1883
	 * @param companyCode
	 * @param airportCode
	 * @param parameterCodes
	 * @return
	 * @throws SystemException
	 */
	public Map<String, String> findAirportParametersByCode(String companyCode,
			   String airportCode,Collection<String> parameterCodes)throws 
			   SystemException {
		try {
			AreaBI areaBI = (AreaBI)getService(SHARED_AREA);
		log.entering("SharedAreaProxy", "findAirportParametersByCode");		
		
			return  areaBI.findAirportParametersByCode(companyCode,
					airportCode,parameterCodes);
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(),e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(),e);
		}

	}
	/**
	 * Used for validating  a collection of airportCodes. Returns NULL if the airportCode does
	 * not exist
	 *@author A-2246
	 * @param companyCode
	 * @param airportCodes
	 * @return Collection
	 * @throws SystemException
	 */

    public Map<String,AirportValidationVO> validateAirportCodes(String companyCode,
    	Collection<String> airportCodes) throws SystemException{

    	log.entering("SharedAreaProxy", "findAirportParametersByCode");
    	Map<String, AirportValidationVO> arpMap = null;
		try {
			AreaBI areaBI = (AreaBI)getService(SHARED_AREA);
			
			arpMap=   areaBI.validateAirportCodes(companyCode,airportCodes);
    	} catch(com.ibsplc.icargo.business.shared.area.AreaBusinessException e) {
    		log.log(Log.FINE,  "AreaBusinessException");
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(),e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(),e);
		}
		return arpMap;
  }
    /**Used for getting all the details of any particular Airport
     * @author A-2553
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws BusinessDelegateException
	 */
    public AirportVO findAirportDetails(String companyCode, String airportCode)
    		throws SystemException{
	  log.entering("SharedAreaProxy", "findAirportDetails");
	  AirportVO airportVO = null;
		try {
			AreaBI areaBI = (AreaBI)getService(SHARED_AREA);
			
			airportVO =   areaBI.findAirportDetails(companyCode, airportCode);
    	} catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(),e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(),e);
		}
		return airportVO;
	  
    }
	/**
	 * Used for validating  a airportCode. Returns NULL if the airportCode does
	 * not exist
	 *@author A-2246
	 * @param companyCode
	 * @param airportCodes
	 * @return Collection
	 * @throws SystemException
	 */

    public AirportValidationVO validateAirportCode(String companyCode,
    	String airportCode) throws SystemException{

    	log.entering("SharedAreaProxy", "validateAirportCode");
    	AirportValidationVO airportValidationVO = null;
		try {
			AreaBI areaBI = (AreaBI)getService(SHARED_AREA);
			
			airportValidationVO=   areaBI.validateAirportCode(companyCode,airportCode);
    	} catch(com.ibsplc.icargo.business.shared.area.AreaBusinessException e) {
    		log.log(Log.FINE,  "AreaBusinessException");
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(),e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(),e);
		}
		return airportValidationVO;
  }
      /**
       * @author A-8353
       * @param companyCode
       * @param airportCode
       * @param parameterCodes
       * @return
       * @throws SystemException
       */
    public Map<String, String> findStationParametersByCode(String companyCode,
			   String stationCode,Collection<String> parameterCodes)throws 
			   SystemException {
		try {
			AreaBI areaBI = (AreaBI)getService(SHARED_AREA);
		log.entering("SharedAreaProxy", "findStationParametersByCode");		
		
			return  areaBI.findStationParametersByCode(companyCode,
					stationCode,parameterCodes);
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(),e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(),e);
		}
	}
    public String getDefaultCurrencyForStation(String companyCode,String stationCode)
			throws ProxyException,SystemException{
				try {
					AreaBI areaBI = (AreaBI)getService(SHARED_AREA);
				return  areaBI.defaultCurrencyForStation(companyCode,stationCode);
				}catch(com.ibsplc.icargo.business.shared.area.AreaBusinessException e) {
					throw new ProxyException(e);
				}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
					throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
				}catch(RemoteException remoteException){
					throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}
}
