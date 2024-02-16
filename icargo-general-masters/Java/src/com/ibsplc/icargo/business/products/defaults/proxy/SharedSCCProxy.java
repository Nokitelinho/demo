/*
 * SharedSCCProxy.java Created on Jan 21, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.scc.SCCBI;
import com.ibsplc.icargo.business.shared.scc.SCCBusinessException;
import com.ibsplc.icargo.business.shared.scc.vo.SCCValidationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * 
 * @author A-5111
 *
 */
public class SharedSCCProxy extends SubSystemProxy {
	
	/**
	 * Validate scc codes.
	 *
	 * @param companyCode the company code
	 * @param sccCodes the scc codes
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws ProxyException the proxy exception
	 */
	public Collection<SCCValidationVO> validateSCCCodes(String companyCode, Collection<String> sccCodes) throws SystemException,ProxyException{
		SCCBI sccBI;
		try {
			sccBI = (SCCBI)getService("SHARED_SCC");
			return  sccBI.validateSCCCodes(companyCode, sccCodes);
		} catch (ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage());
		} catch (SCCBusinessException e) {
			throw new ProxyException(e);
		} catch (RemoteException e) {
			throw new SystemException(e.getMessage());
		}
	}
}
