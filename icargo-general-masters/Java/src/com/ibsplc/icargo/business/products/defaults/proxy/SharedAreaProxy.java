/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.proxy.SharedAreaProxy.java
 *
 *	Created by	:	Prashant Behera
 *	Created on	:	Jun 29, 2022
 *
 *  Copyright 2022 Copyright  IBS Software  (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright  IBS Software  (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.area.AreaBI;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.proxy.SharedAreaProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Prashant Behera	:	Jun 29, 2022	:	Draft
 */
public class SharedAreaProxy extends SubSystemProxy {
	
	/**
	 * 
	 * 	Method		:	SharedAreaProxy.validateCountryCodes
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param countryCodes
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Map<String,CountryVO>
	 */
	public Map<String, CountryVO> validateCountryCodes(String companyCode, Collection<String> countryCodes)
			throws SystemException, ProxyException {
		try {
			AreaBI areaBI = (AreaBI) getService("SHARED_AREA");
			return areaBI.validateCountryCodes(companyCode, countryCodes);

		} catch (com.ibsplc.icargo.business.shared.area.AreaBusinessException e) {
			throw new ProxyException(e);

		} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, serviceNotAccessibleException);
		} catch (RemoteException remoteException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, remoteException);
		}
	}
}
