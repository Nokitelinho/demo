package com.ibsplc.icargo.business.addons.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.addons.mail.operations.proxy.OperationsFlightHandlingProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Ashil M N	:	23-Sep-2021	:	Draft
 */
@Module("operations")
@SubModule("flthandling")
public class OperationsFlightHandlingProxy extends ProductProxy {
	/**
	 * 
	 * 	Method		:	OperationsFlightHandlingProxy.findShipmentsInFlight
	 *	Added by 	:	Ashil M N on 23-Sep-2021
	 * 	Used for 	:
	 *	Parameters	:	@param flightFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ShipmentSummaryVO>
	 */
	public Collection<ShipmentSummaryVO> findShipmentsInFlight(FlightFilterVO flightFilterVO) throws SystemException {
		try {
			return despatchRequest("findShipmentsInFlight", flightFilterVO);
		} catch (ProxyException ex) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, ex);
		}
	}
	
}
