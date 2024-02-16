/*
 * SharedSignatureProxy.java Created on Oct 25, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;

import com.ibsplc.icargo.business.shared.signature.CaptureSignatureBI;
import com.ibsplc.icargo.business.shared.signature.vo.CaptureSignatureVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */

public class SharedSignatureProxy extends SubSystemProxy {

	private Log log=LogFactory.getLogger("SharedSignatureProxy");
	
	public Collection<CaptureSignatureVO> findSignatureDatas(String companyCode,Collection<Integer> signatureIds)
	throws SystemException , ProxyException{
		log.entering("SharedSignatureProxy", "findSignatureDatas");		
		try {
			CaptureSignatureBI signatureBI = (CaptureSignatureBI)getService("SHARED_SIGNATURE");
       	return  signatureBI.findSignatureDatas(companyCode,signatureIds);
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	}
		
}
