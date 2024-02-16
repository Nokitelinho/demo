/*
 * OperationsShipmentProxy.java Created on May 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;
import java.util.ArrayList;
import com.ibsplc.icargo.business.operations.shipment.vo.HAWBVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.message.vo.MessageConfigConstants;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1883
 * The Proxy Class for the Operations -Shipment
 */
@Module("operations")
@SubModule("shipment")
public class OperationsShipmentProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("OPERATIONS_SHIPMENT_PROXY");
	public static final String OPERATIONSHIPMENTPROXY ="OperationsShipmentProxy";
	public static final String SAVESHIPMENTDETAIL = "saveShipmentDetails";
	/**
	 * @author a-1883
	 * @param shipmentDetailVO
	 * @return ShipmentValidationVO
	 * @throws SystemException
	 */
	public ShipmentValidationVO saveShipmentDetails(
			ShipmentDetailVO shipmentDetailVO) throws SystemException {
		log.entering(OPERATIONSHIPMENTPROXY, SAVESHIPMENTDETAIL);
		try {
			return despatchRequest(SAVESHIPMENTDETAIL, shipmentDetailVO);
		} catch (ProxyException proxyException) {
			
			if (proxyException.getErrors() != null && !proxyException.getErrors().isEmpty()
					&& "operations.shipment.msg.err.bookingmandatory"
							.equals(((ArrayList<ErrorVO>) proxyException.getErrors()).get(0).getErrorCode())) {
				throw new SystemException("mailtracking.defaults.err.bookingmandatory");
			} else 	if (proxyException.getErrors() != null && !proxyException.getErrors().isEmpty()
					&& ("products.defaults.productnotexists"
					.equals(((ArrayList<ErrorVO>) proxyException.getErrors()).get(0).getErrorCode())|| 
					"operations.shipment.product.invalid"
					.equals(((ArrayList<ErrorVO>) proxyException.getErrors()).get(0).getErrorCode()))) {
				throw new SystemException("mailtracking.defaults.err.productnotexists");
			}
			else {
				throw new SystemException(proxyException.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param shipmentValidationVO
	 * @return ShipmentMasterDetailsVO
	 * @throws SystemException
	 */
	public ShipmentDetailVO findShipmentDetails(
			ShipmentDetailFilterVO shipmentDetailFilterVO) throws SystemException {
		log.entering(OPERATIONSHIPMENTPROXY, "findShipmentDetails");
		try {
			return despatchRequest("findShipmentDetails",
					shipmentDetailFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
	}

	/**
	 * TODO Purpose
	 * Jan 31, 2007, A-1739
	 * @param name
	 * @throws SystemException 
	 */
	public void sendFWB(Collection<ShipmentValidationVO> shipmentVOs) 
	throws SystemException {
		log.entering(OPERATIONSHIPMENTPROXY, "sendFWB");
		try {
			despatchRequest("sendFWBs",MessageConfigConstants.TXN_FLT_FINALIZE,shipmentVOs);
		} catch(ProxyException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting(OPERATIONSHIPMENTPROXY, "sendFWB");		
	}
	
	/**
	 * 
	 * @param shipmentValidationVO
	 * @return ShipmentMasterDetailsVO
	 * @throws SystemException
	 */
	public Collection<ShipmentVO> findShipments(ShipmentFilterVO shipmentFilterVO) 
	throws SystemException {
		log.entering(OPERATIONSHIPMENTPROXY, "findShipments");
		try {
			return despatchRequest("findShipments",
					shipmentFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
	}
	
	
	
	 /**
	* @author a-1936
	* @param shipmentValidationVO
	* @param source
	* @throws SystemException
	* @throws RemoteException
	* @throws ShipmentBusinessException
	*/
	public Collection<String> deleteAWB(ShipmentValidationVO shipmentValidationVO,String source)
	throws SystemException,ProxyException{
		log.entering(OPERATIONSHIPMENTPROXY, "deleteAWB");
			return despatchRequest("deleteAWB",
					shipmentValidationVO,source);
 	 }
	
	
	
	
	
	public AirlineValidationVO validateNumericCode(String companyCode, String shipmentPrefix)
			throws SystemException, ProxyException {
		log.entering(OPERATIONSHIPMENTPROXY, "validateNumericCode");
		return despatchRequest("validateNumericCode", companyCode, shipmentPrefix);
	}
	public Collection<ShipmentValidationVO> saveHAWBDetails(ShipmentValidationVO shipmentValidationVO, Collection<HAWBVO> hawbs,
			boolean isConsolStatusTobeChanged) throws SystemException {
		log.entering(OPERATIONSHIPMENTPROXY, "saveHAWBDetails");
		 Collection<ShipmentValidationVO> shipmentValidationVOs=new ArrayList<>();
		try {
			shipmentValidationVOs= despatchRequest("saveHAWBDetails", shipmentValidationVO, hawbs, isConsolStatusTobeChanged);
		} catch (ProxyException proxyException) {
			log.log(Log.INFO, proxyException);
		}
		return shipmentValidationVOs;
	}
	
	public void saveShipmentDetailsAsync(
			ShipmentDetailVO shipmentDetailVO) throws SystemException {
		log.entering(OPERATIONSHIPMENTPROXY, SAVESHIPMENTDETAIL);
		try {
			 dispatchAsyncRequest(SAVESHIPMENTDETAIL,false, shipmentDetailVO);
		} catch (ProxyException proxyException) {
			
			throw new SystemException(proxyException.getMessage(), proxyException);
		}
	}
}
