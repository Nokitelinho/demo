/*
 * SharedAirlineProxy.java Created on Sep 22, 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.oz.mail.operations.proxy;

import java.rmi.RemoteException;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.airline.AirlineBI;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.oz.mail.operations.proxy.SharedAirlineProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	22-Sep-2017	:	Draft
 */
public class SharedAirlineProxy extends SubSystemProxy {
	private Log log = LogFactory.getLogger("SharedAirlineProxy ");

	/**
	 * 
	 * 	Method		:	SharedAirlineProxy.findAirline
	 *	Added by 	:	a-7779 on 22-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param carrierId
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws SharedProxyException 
	 *	Return type	: 	AirlineValidationVO
	 */
	public AirlineValidationVO findAirline(String companyCode,
			int carrierId) throws SystemException {

		log.entering("INSIDE THE AIRLINE", "PROXY");
		AirlineValidationVO airlineValidationVO = null;
		try {
			AirlineBI airlineBI = (AirlineBI)getService("SHARED_AIRLINE");
			airlineValidationVO =  airlineBI.findAirline(companyCode, carrierId);
		} catch(com.ibsplc.icargo.business.shared.airline.AirlineBusinessException e) {
			for (ErrorVO error : e.getErrors()) {
				throw new SystemException(
						"shared.airline.invalidairline",
						new Object[] { error.getErrorData() });
			}
		}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(), e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(), e);
		}
		return airlineValidationVO;

		

	}


}
