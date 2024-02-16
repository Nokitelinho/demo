/*
 * SharedDefaultsProxy.java Created on Jul 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.defaults.SharedDefaultsBI;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParametersVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.defaults.unit.vo.UnitConversionVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * This class represents the product proxy for shared defaults subsystem
 *
 * @author A-1307
 *
 *
 **/



public class SharedDefaultsProxy extends SubSystemProxy {

    /**
	 * Field 	: SHARED_DEFAULTS	of type : String
	 * Used for :
	 */
	private static final String SHARED_DEFAULTS = "SHARED_DEFAULTS";

	/**
 	 * This method is used for converting the system unit value.
 	 *
 	 * @param companyCode
 	 * @param unitType
 	 * @param fromUnit
 	 * @param fromValue
 	 * @throws SystemException
 	 *
 	 */
 	@Action("findSystemUnitValue")
    public UnitConversionVO findSystemUnitValue(
		String companyCode, String unitType, String fromUnit,
		double fromValue) throws ProxyException, SystemException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService(SHARED_DEFAULTS);

       	return   defaultsBI.findSystemUnitValue(companyCode, unitType, fromUnit, fromValue);
 

		}catch(com.ibsplc.icargo.business.shared.defaults.unit.UnitBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }

 	public Map<String, Collection<OneTimeVO>> findOneTimeValues(String companyCode,Collection<String> oneTimeList)
 			throws SystemException,ProxyException{
	try {
		SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService(SHARED_DEFAULTS);
		return  defaultsBI.findOneTimeValues(companyCode, oneTimeList);
	} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
		throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
	}catch (RemoteException remoteException) {
		throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
	}
 	}
 	
 	/**
 	 * 
 	 * 	Method		:	SharedDefaultsProxy.findGeneralParameterValues
 	 *	Added by 	:	Prashant Behera on Jul 5, 2022
 	 * 	Used for 	:
 	 *	Parameters	:	@param companyCode
 	 *	Parameters	:	@param transactionType
 	 *	Parameters	:	@return
 	 *	Parameters	:	@throws SystemException 
 	 *	Return type	: 	Collection<GeneralParametersVO>
 	 */
 	public Collection<GeneralParametersVO> findGeneralParameterValues(
			String companyCode,String transactionType)
			throws SystemException{
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService(SHARED_DEFAULTS);
			return  defaultsBI.findGeneralParameterValues(companyCode, transactionType);
	
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	}
}
