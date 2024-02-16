package com.ibsplc.icargo.business.mail.operations.proxy;


import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**

 * This is the product proxy for Shared Area
 * @author A-8353
 *
 */
@Module("shared")
@SubModule("area")
public class SharedAreaProductProxy extends ProductProxy  {

	
	/**
	 * Used for validating  a airportCode. Returns NULL if the airportCode does
	 * not exist
	 *@author A-8353
	 * @param companyCode
	 * @param airportCodes
	 * @return Collection
	 * @throws SystemException
	 */

	public AirportValidationVO validateAirportCode(
			String companyCode,String airportCode) throws ProxyException, SystemException {
		return despatchRequest("validateAirportCode", companyCode, airportCode);
	}
  
    
}
	
