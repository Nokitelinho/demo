package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.WarehouseDefaultsEProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@Slf4j
public class WarehouseDefaultsProxy {
	@Autowired
	private WarehouseDefaultsEProxy warehouseDefaultsEProxy;

	public Collection<WarehouseVO> findAllWarehouses(String companyCode,String airportCode)
			throws SystemException {
		return warehouseDefaultsEProxy.findAllWarehouses( companyCode, airportCode);
	}
	public Map<String, Collection<String>> findWarehouseTransactionLocations(
			String companyCode, String airportCode, String warehouseCode, Collection<String> transactionCodes)
			throws SystemException{
		return warehouseDefaultsEProxy.findWarehouseTransactionLocations(
				companyCode, airportCode,warehouseCode,transactionCodes);
	}

	public LocationValidationVO validateLocation(String companyCode, String airportCode, String warehouseCode, String locationCode) {
		return warehouseDefaultsEProxy.validateLocation(companyCode, airportCode, warehouseCode, locationCode);
	}
}
