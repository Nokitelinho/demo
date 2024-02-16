package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import java.util.Collection;
import com.ibsplc.icargo.business.operations.shipment.vo.HAWBVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "operations", submodule = "shipment", name = "operationsShipmentEProxy")
public interface OperationsShipmentEProxy {
	ShipmentValidationVO saveShipmentDetails(ShipmentDetailVO shipmentDetailVO);

	ShipmentDetailVO findShipmentDetails(ShipmentDetailFilterVO shipmentDetailFilterVO);

	Collection<String> deleteAWB(ShipmentValidationVO shipmentValidationVO, String source);

	AirlineValidationVO validateNumericCode(String companyCode, String shipmentPrefix);

	Collection<ShipmentValidationVO> saveHAWBDetails(ShipmentValidationVO shipmentValidationVO,
			Collection<HAWBVO> hawbs, boolean isConsolStatusTobeChanged);
	void saveShipmentDetailsAsync(ShipmentDetailVO shipmentDetailVO);
}
