/**
*    Java file    :     com.ibsplc.icargo.business.uld.defaults.proxy.SharedAirlineProductProxy.java
*
*    Created on    :    Jan 13, 2023
*
*  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
*
*     This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
*     Use is subject to license terms.
*/
package com.ibsplc.icargo.business.uld.defaults.proxy;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Module("shared")
@SubModule("airline")
public class SharedAirlineProductProxy extends ProductProxy{
	/**
     * 
     *    Method         :   SharedAirlineProductProxy.findAirline
     *    Added on       :    13-Jan-2023
     *    Used for       :
     *    Parameters     :    @param companyCode
     *    Parameters     :    @param airlineIdentifier
     *    Parameters     :    @return 
     *    Return type    :    AirlineValidationVO
     */
	  public AirlineValidationVO findAirline(String companyCode , int airlineIdentifier)
			  throws SystemException,ProxyException{
		  return despatchRequest("findAirline",companyCode, airlineIdentifier);


	  }
	
}