/*
 * SharedAirlineProxy.java Created on Jul 8, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.airline.AirlineBI;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineAirportParameterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1763
 *
 */


public class SharedAirlineProxy extends SubSystemProxy {

 	private Log log=LogFactory.getLogger("SharedAirlineProxy");
	 

   /**
    * 
    * 
    * @param companyCode
    * @param airlineCode
    * @return AirlineValidationVO
    * @throws SystemException
    * @throws ProxyException
    */
  @Action("validateAlphaCode")
   public AirlineValidationVO validateAlphaCode(String companyCode,
   		String airlineCode) throws SystemException, ProxyException {
		try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
	    log.entering("INSIDE THE AIRLINE","PROXY");
	    log.entering("INSIDE THE AIRLINE","PROXY");
       	return  airlineBI.validateAlphaCode(companyCode,airlineCode);
 

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
   * @param airlineIdentifier
   * @return
   * @throws SystemException
   * @throws ProxyException
   */
  @Action("findAirline")
  public AirlineValidationVO findAirline(String companyCode , int airlineIdentifier)
  throws SystemException,ProxyException{
		try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
	  return  airlineBI.findAirline(companyCode , airlineIdentifier);


		}catch(com.ibsplc.icargo.business.shared.airline.AirlineBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }
  
  @Action("findAirlineParametersByCode")
  public Map<String,String> findAirlineParametersByCode(String companyCode,int airlineIdentifier,Collection<String> parameterCodes)
  throws SystemException,ProxyException{
		try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
			return airlineBI.findAirlineParametersByCode(companyCode, airlineIdentifier, parameterCodes);
		
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }
  //Added as part of ICRD 21184 starts
  @Action("validateAlphaCodes")
  public Map<String,AirlineValidationVO> validateAlphaCodes(String companyCode,	Collection<String> alphaCodes) 
  throws SystemException,ProxyException{
	    try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
	    log.entering("INSIDE THE AIRLINE","PROXY");
	    return  airlineBI.validateAlphaCodes(companyCode,alphaCodes);
	    
	    }catch(com.ibsplc.icargo.business.shared.airline.AirlineBusinessException e) {
			throw new ProxyException(e);
	    }catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }
  
  @Action("validateOwnerCode")
  public String validateOwnerCode(String companyCode,String twoalpha,String threealpha)
	throws SystemException,ProxyException{
	  try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
	    log.entering("INSIDE THE AIRLINE","PROXY");
	    return  airlineBI.validateOwnerCode(companyCode, twoalpha, threealpha);
	    
	    }catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	    
  }
  //Added as part of ICRD 21184 ends
  /**
   * 
   * 	Method		:	SharedAirlineProxy.findAirlineAirportParameters
   *	Added by 	:	A-6991 on 04-May-2017
   * 	Used for 	:   ICRD-77772
   *	Parameters	:	@param airlineAirportParameterFilterVO
   *	Parameters	:	@return
   *	Parameters	:	@throws SystemException 
   *	Return type	: 	Collection<AirlineAirportParameterVO>
   */
  public Collection<AirlineAirportParameterVO> findAirlineAirportParameters(AirlineAirportParameterFilterVO airlineAirportParameterFilterVO)
		    throws SystemException {
	 log.entering("INSIDE THE AIRLINE", "PROXY");
	 Collection<AirlineAirportParameterVO> airlineAirportParameterVO = null;
		try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
			airlineAirportParameterVO =  airlineBI.findAirlineAirportParameters(airlineAirportParameterFilterVO);
		} 
		catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(), e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(), e);
		}
		return airlineAirportParameterVO;
}
}
