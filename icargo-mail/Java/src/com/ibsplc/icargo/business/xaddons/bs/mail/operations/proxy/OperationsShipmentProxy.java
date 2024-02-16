/*
 * OperationsShipmentProxy.java Created on Nov 7, 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.operations.shipment.vo.AcceptanceFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.AcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-8061
 *
 */
@Module("operations")
@SubModule("shipment")
public class OperationsShipmentProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("OPERATIONS_SHIPMENT_PROXY");

	
	/**
	 * 
	 * @param shipmentValidationVO
	 * @return ShipmentMasterDetailsVO
	 * @throws SystemException
	 */
	public Collection<ShipmentVO> findShipments(ShipmentFilterVO shipmentFilterVO) 
	throws SystemException {
		log.entering("OperationsShipmentProxy", "findShipments");
		try {
			return despatchRequest("findShipments",
					shipmentFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
	}
	
	/**
	 * @author a-7531
	 * @param aceptanceFilterVO
	 * @return AcceptanceVO
	 * @throws SystemException
	 */
	public AcceptanceVO findAcceptanceDetails(
			AcceptanceFilterVO aceptanceFilterVO) throws SystemException
			
			{
		log.entering("OperationsShipmentProxy", "findAcceptanceDetails");
		try {
		return despatchRequest("findAcceptanceDetails",
				aceptanceFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
			}
	
	
}
