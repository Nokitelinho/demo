/*
 * SharedDefaultsProxy.java Created on Jul 8, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.util.Map;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParameterConfigurationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.defaults.unit.vo.UnitConversionVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.defaults.SharedDefaultsBI;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
 
/**
 * 
 * @author A-1347
 *
 */


public class SharedDefaultsProxy extends SubSystemProxy {

 private Log  log = LogFactory.getLogger("ULD_DEFAULTS");

/**
 * 
 * @param systemparameterCodes
 * @return
 * @throws SystemException
 * @throws ProxyException
 */
    @Action("findSystemParameterByCodes")    
    public Map findSystemParameterByCodes(Collection<String> systemparameterCodes)
    	throws SystemException,ProxyException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
         log.entering("SharedDefaultsProxy","findSystemParameterByCodes");
         return   defaultsBI.findSystemParameterByCodes(systemparameterCodes); 


		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }
    
    
    /**
     * This method is used for converting the system unit value.
     * 
     * @param companyCode
     * @param unitType
     * @param fromUnit
     * @param fromValue
     * @return
     * @throws ProxyException
     * @throws SystemException
     */
  @Action("findSystemUnitValue")
    public UnitConversionVO findSystemUnitValue(
  String companyCode, String unitType, String fromUnit,
  double fromValue) throws ProxyException, SystemException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
	  log.entering("INSIDE THE PROXY","SHARED DEFAULTS");
	  log.entering("INSIDE THE PROXY","SHARED DEFAULTS");
        return   defaultsBI.findSystemUnitValue(companyCode, unitType, fromUnit, fromValue);
 

		}catch(com.ibsplc.icargo.business.shared.defaults.unit.UnitBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }
  /**
	 * This method is used to retrieve the onetime values for all the given field types.
	 * The return Map has the fieldType as the Key and the Collection OneTimeVO as value.
	 * @param companyCode
	 * @param fieldTypes
	 * @return HashMap
	 * @throws SystemException
	 * @throws ProxyException
	 */
  @Action("findOneTimeValues")
	public Map<String, Collection<OneTimeVO>> findOneTimeValues(
			String companyCode, Collection<String> fieldTypes)
			throws SystemException, ProxyException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
		return  defaultsBI.findOneTimeValues(companyCode, fieldTypes);
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	}
 /**
	
	 * Author A-7918
	 * @throws SystemException
	 * @throws ProxyException   
	 */		 

	public Collection<GeneralParameterConfigurationVO> findGeneralParameterConfigurations(
			Collection<GeneralParameterConfigurationVO> generalParameterConfigurationVOs) throws SystemException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
		return  defaultsBI.findGeneralParameterConfigurations(generalParameterConfigurationVOs);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}
}
