/*
 * SharedDefaultsProxy.java Created on apr 23, 2006 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.proxy;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.defaults.unit.vo.UnitConversionVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
@Module("shared")
@SubModule("defaults")
public class SharedDefaultsProxy extends ProductProxy {

	private  Log log = LogFactory.getLogger("Customer Management");
	/**
	 * This method is used for converting value in one Unit to another Unit
	 * @param companyCode
	 * @param unitType
	 * @param fromUnit
	 * @param toUnit
	 * @param fromValue
	 * @return UnitConversionVO
	 * @throws SystemException
	 * @throws ProxyException
	 */
	@Action("findConvertedUnitValue")
	public UnitConversionVO findConvertedUnitValue(
			String companyCode, String unitType, String fromUnit,
											String toUnit,double fromValue)
								throws SystemException, ProxyException {
		log.entering("SharedDefaultsProxy","findConvertedUnitValue");
		return despatchRequest("findConvertedUnitValue",companyCode,
				unitType,fromUnit,toUnit,fromValue);
	}

	/**
	* @param companyCode
	* @param parameterTypes
	* @return
	* @throws ProxyException
	* @throws SystemException
	*/
   public Map<String, Collection<OneTimeVO>> findOneTimeValues(String companyCode,
		   Collection<String> parameterTypes)
   throws ProxyException, SystemException {
	   return  despatchRequest("findOneTimeValues",companyCode,parameterTypes);
  }
   
   /**
    * @author a-4789
    * Added for LHPOC
    * This method is used to fetch the SystemParameter values
    * @param systemParameterCodes
    * @return Map<String,String>
    * @throws SystemException
    * @throws ProxyException
    */
   public Map<String,String> findSystemParameterByCodes(
	   		Collection<String> systemParameterCodes)
	   		throws SystemException, ProxyException {
	   log.entering("SharedDefaultsProxy","findSystemParameterByCodes");
	   return despatchRequest("findSystemParameterByCodes", systemParameterCodes);
  }

}
