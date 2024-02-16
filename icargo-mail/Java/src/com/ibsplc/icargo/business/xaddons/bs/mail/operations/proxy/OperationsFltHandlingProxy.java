/*
 * OperationsFltHandlingProxy.java Created on Oct 04, 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.operations.flthandling.cto.vo.CTOShipmentManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy.OperationsFltHandlingProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	04-Oct-2017	:	Draft
 */
@Module("operations")
@SubModule("flthandling")
public class OperationsFltHandlingProxy extends ProductProxy {
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	
	 
	 
	public Collection<UldManifestVO> findManifestShipmentDetails(
			ManifestFilterVO manifestFilterVO) throws SystemException {
		Collection<UldManifestVO> uldManifestVOs = null;

		log.entering("OperationsFltHandlingProxy", "validateFlightForAirport");

		try {
			uldManifestVOs = despatchRequest("findManifestShipmentDetails",
					manifestFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		} 

		return uldManifestVOs;
	}
	public CTOShipmentManifestVO findShipmentForImportManifest(
			ManifestFilterVO manifestFilterVO,
			ShipmentValidationVO shipmentValidationVO)
	throws SystemException {
		log.entering("OperationsFltHandlingProxy", "findShipmentForImportManifest");
		CTOShipmentManifestVO manifestVO = null;
				try{
					manifestVO =  despatchRequest("findShipmentForImportManifest",
							manifestFilterVO,shipmentValidationVO);
				}catch(ProxyException e){
					log.log(Log.FINE, e.getMessage());
				}
				return manifestVO;
	}
	 
}
