package com.ibsplc.icargo.business.addons.mail.operations.proxy;

import com.ibsplc.icargo.business.operations.shipment.cto.vo.CTOAcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.vo.AcceptanceFilterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.addons.mail.operations.proxy.OperationalShipmentProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Ashil M N	:	23-Sep-2021	:	Draft
 */
@Module("operations")
@SubModule("shipment")
public class OperationalShipmentProxy extends ProductProxy {

	/**
	 * 
	 * 	Method		:	OperationalShipmentProxy.findCTOAcceptanceDetails
	 *	Added by 	:	Ashil M N on 23-Sep-2021
	 * 	Used for 	:
	 *	Parameters	:	@param acceptanceFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	CTOAcceptanceVO
	 */
	public CTOAcceptanceVO findCTOAcceptanceDetails(AcceptanceFilterVO acceptanceFilterVO) throws SystemException {
		try {
			return despatchRequest("findCTOAcceptanceDetails", acceptanceFilterVO);
		} catch (ProxyException ex) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, ex);
		}

	}
	
	
}
