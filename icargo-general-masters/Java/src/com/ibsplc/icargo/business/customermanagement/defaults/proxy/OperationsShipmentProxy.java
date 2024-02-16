/*
 * OperationsShipmentProxy.java Created on 23/10/2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.customermanagement.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;

//import java.util.Collection;

//import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
//import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
//import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
//import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2593
 * 
 */
@Module("operations")
@SubModule("shipment")
public class OperationsShipmentProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("CLAIMS_DEFAULTS");
	
	
	/**
	 * Method will return all the shipments with the same prefix and awb number
	 * It wil return empty Set if no awbs exist with the same prefix and awb
	 * number
	 *
	 * @param shipmentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	/*public Collection<ShipmentVO> findShipments(
			ShipmentFilterVO shipmentFilterVO) throws SystemException,ProxyException {
		log.entering("ShipmentDelegate", "findShipments");
		log.exiting("ShipmentDelegate", "findShipments");
		return despatchRequest("findShipments", shipmentFilterVO);
	}*/
	public Collection<ShipmentVO> validateShipmentDetails(ShipmentFilterVO shipmentFilterVO)
    	    throws ProxyException, SystemException {
	    return despatchRequest("validateShipmentDetails", shipmentFilterVO);
    }
   
   public Collection<ShipmentHistoryVO> findShipmentHandlingHistory(ShipmentHistoryFilterVO shipmentHistoryFilterVO)
    	    throws ProxyException, SystemException {
	    return despatchRequest("findShipmentHandlingHistory", shipmentHistoryFilterVO);
    }
	

}