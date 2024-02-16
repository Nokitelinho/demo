/*
 * OperationsShipmentProxy.java Created on Jul 6, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;

import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 * 
 */
@Module("operations")
@SubModule("shipment")
public class OperationsShipmentProxy extends ProductProxy {
	private Log log = LogFactory.getLogger("STOCK CONTROL PROXY");

	/**
	 * @author A-1883
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param ranges
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void blacklistRange(String companyCode, int airlineIdentifier,
			Collection<RangeVO> ranges) throws SystemException, ProxyException {
		log.entering("OperationsShipmentProxy", "blacklistRange");
		despatchRequest("blacklistRange", companyCode, airlineIdentifier,
				ranges);
		log.exiting("OperationsShipmentProxy", "blacklistRange");
	}

	/**
	 * This method is used to validate the ranges given for allocating the stock
	 * The return will be the collection of awbs which cannot be allocated
	 * 
	 * @param stockAllocationVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<String> validateDocumentRange(
			StockAllocationVO stockAllocationVO) throws SystemException,
			ProxyException {
		log.log(Log.INFO, "ENTRY");
		return despatchRequest("validateDocumentRange", stockAllocationVO);
	}

	/**
	 * @param stockAllocationVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<String> validateProcessedDocuments(
			StockAllocationVO stockAllocationVO) throws SystemException,ProxyException{
		log.log(Log.INFO,"ENTRY");
		return despatchRequest("validateProcessedDocuments", stockAllocationVO);
	}

	/**
	 * Added by A-8146
	 * @param companyCode
	 * @param documentOwnerId
	 * @param masterDocumentNumber
	 * @return
	 */
	public List<String> findAWBReuseRestrictionDetailsSpecific(String companyCode, int documentOwnerId,
			String masterDocumentNumber)  throws ProxyException, SystemException{
		// TODO Auto-generated method stub
		return despatchRequest("findAWBReuseRestrictionDetailsSpecific", companyCode,  documentOwnerId,
				 masterDocumentNumber);
	}
}
