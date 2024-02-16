/*
 * SharedDocumentProxy.java Created on Sep 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.document.DocumentBI;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-1954
 * 
 */


public class SharedDocumentProxy extends SubSystemProxy {
	
	private Log log = LogFactory.getLogger("STOCK CONTROLLER");
	
	/**
	 * @param documentFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<DocumentVO> findDocumentDetails(String companyCode, 
    		String documentType, String documentSubType) throws SystemException{
		
		log.log(Log.INFO, "companyCode---", companyCode);
		log.log(Log.INFO, "documentType---", documentType);
		log.log(Log.INFO, "documentSubType---", documentSubType);
		try {
			DocumentBI documentBI = (DocumentBI)getService("SHARED_DOCUMENT");
		

		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    	documentFilterVO.setCompanyCode(companyCode);
    	documentFilterVO.setDocumentCode(documentType);
    	if(documentSubType!=null && documentSubType.length()>0){
    	documentFilterVO.setDocumentSubType(documentSubType);
    	}

    	
			return  documentBI.findDocumentDetails(documentFilterVO);
		} catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

		
	}
	
}
