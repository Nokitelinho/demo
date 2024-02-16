
package com.ibsplc.icargo.business.addons.mail.operations.proxy;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.addons.mail.operations.proxy.SharedAirlineProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Ashil M N	:	23-Sep-2021	:	Draft
 */
@Module("shared")
@SubModule("airline")
public class SharedAirlineProxy extends  ProductProxy {

	/**
	 * 
	 * 	Method		:	SharedAirlineProxy.findAirline
	 *	Added by 	:	Ashil M N on 23-Sep-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param carrierId
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	AirlineValidationVO
	 */
	public AirlineValidationVO findAirline(String companyCode, int carrierId) throws SystemException {
		try {
			return despatchRequest("findAirline", companyCode, carrierId);
		} catch (ProxyException  e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, e);
		}
	}

}
