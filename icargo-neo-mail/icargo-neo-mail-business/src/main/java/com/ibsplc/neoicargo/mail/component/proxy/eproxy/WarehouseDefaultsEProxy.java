package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

import java.util.Collection;
import java.util.Map;

@EProductProxy(module = "warehouse", submodule = "defaults", name = "warehouseDefaultsEProxy")
public interface WarehouseDefaultsEProxy {

	Collection<WarehouseVO> findAllWarehouses(String companyCode,
													 String airportCode);
	Map<String, Collection<String>> findWarehouseTransactionLocations(
			String companyCode, String airportCode, String warehouseCode, Collection<String> transactionCodes);


    LocationValidationVO validateLocation(String companyCode, String airportCode, String warehouseCode, String locationCode);
}
