package com.ibsplc.icargo.business.xaddons.ke.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Module("operations")
@SubModule("shipment")
public class OperationsShipmentProxy extends ProductProxy{

	/**
	 * 
	 * @param ShipmentFilterVO
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public Collection<ShipmentVO>  findShipments(ShipmentFilterVO shipmentFilterVO) throws ProxyException, SystemException {
		return despatchRequest("findShipments",shipmentFilterVO);
	}
	
   
}
