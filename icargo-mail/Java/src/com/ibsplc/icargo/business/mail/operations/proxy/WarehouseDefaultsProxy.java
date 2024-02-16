/*
 * WarehouseDefaultsProxy.java Created on May 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to lice nse terms.
 */

package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.storageunit.vo.StorageUnitValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-1936
 * 
 */
@Module("warehouse")
@SubModule("defaults")
public class WarehouseDefaultsProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("MailTracking_Defaults");

	/**
	 * @author a-1936 This method finds all the warehouses in an airport
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<WarehouseVO> findAllWarehouses(String companyCode,
			String airportCode) throws SystemException {
		log.entering("WarehouseDefaultsProxy", "findAllWarehouses");
		log.entering("WarehouseDefaultsProxy", "findAllWarehouses");
		try {
			return despatchRequest("findAllWarehouses", companyCode,
					airportCode);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}

	/**
	 * @author a-1936 THis methodis used to get the Location Of the WreHouse for
	 *         the AcceptMail
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param transactionCodes
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Map<String, Collection<String>> findWarehouseTransactionLocations(String companyCode,
			String airportCode,String warehouseCode,Collection<String> transactionCodes) throws SystemException {
		log.entering("WarehouseDefaultsProxy",
				"findWarehouseTransactionLocations");
		try {
			return despatchRequest("findWarehouseTransactionLocations",
					companyCode,
							airportCode,warehouseCode,transactionCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}

	/**
	 * @author a-1936 This method is used to validate the Location of the
	 *         WareHouse
	 * @param companyCode
	 * @param airportCode
	 * @param warehouseCode
	 * @param locationCode
	 * @throws SystemException
	 * @return
	 */
	public LocationValidationVO validateLocation(String companyCode,
			String airportCode, String warehouseCode, String locationCode)
			throws SystemException {
		log.entering("WarehouseDefaultsProxy", "validateLocation");
		log.entering("WarehouseDefaultsProxy", "validateLocation");
		try {
			return despatchRequest("validateLocation", companyCode,
					airportCode, warehouseCode, locationCode);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}
    //added as part of IASCB-57385
	public StorageUnitValidationVO  validateStorageUnit(String companyCode, String airportCode, String storageUnit)
			throws SystemException{
		log.entering("WarehouseDefaultsProxy", "validateStorageUnit");
		try {
			return despatchRequest("validateStorageUnit", companyCode,
					airportCode, storageUnit);
		} catch (ProxyException proxyException) {
			log.log(Log.SEVERE, proxyException.getMessage(), proxyException);
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
	}

}
