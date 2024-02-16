/*
 * SharedULDProxy.java Created on Jul 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationFilterVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.business.shared.uld.ULDBI;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 */


public class SharedULDProxy extends SubSystemProxy {
	
	private Log log=  LogFactory.getLogger("ULD_DEFAULTS");
    /**
     * This method validates the ULD type specified
     * @param companyCode
     * @param uldType
     * @return boolean
     * @throws SystemException
     */
	  /**
	 * Method returns a collection of valid uld codes 
	 * while passing a collection of uld codes
	 *
	 * @param companyCode
	 * @param uldTypeCodes
	 * @return Map<String,ULDTypeValidationVO>
	 * @throws BusinessDelegateException
	 */

	
	/**
	 * @param companyCode
	 * @param uldTypeCodes
	 * @return Map<String,ULDTypeValidationVO>
	 * @throws SystemException
	 * @throws ProxyException
	 */
	@Action("validateULDTypeCodes")
    public Map<String,ULDTypeValidationVO> 
	  validateULDTypeCodes(String companyCode, Collection<String> uldTypeCodes)
	      throws SystemException,ProxyException{
		try {
			ULDBI uldBI = (ULDBI)getService("SHARED_ULD");
		log.entering("INSIDE THE SHAREDULDPROXY","INSIDE THE SHAREDULDPROXY");
	 return   uldBI.validateULDTypeCodes(companyCode,uldTypeCodes);

		}catch(com.ibsplc.icargo.business.shared.uld.ULDBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}	
	
	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
    @Action("findULDTypes")
   public Collection<ULDTypeVO> findULDTypes(ULDTypeFilterVO filterVO)
   		 throws SystemException,ProxyException {
		try {
			ULDBI uldBI = (ULDBI)getService("SHARED_ULD");
    	log.entering("INSIDE THE PROXY","INSIDE THE PROXY");
    	log.entering("INSIDE THE PROXY","INSIDE THE PROXY");
         return   uldBI.findULDTypes(filterVO);
 

		}catch(com.ibsplc.icargo.business.shared.uld.ULDBusinessException e) {
			throw new ProxyException(e);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }

    /**
     * 
     * @param uldValidationFilterVO
     * @return Measure
     * @throws SystemException
	 * @throws ProxyException
     */
	public Measure findULDTareWeight(ULDValidationFilterVO uldValidationFilterVO) throws SystemException,ProxyException {
		try {
			ULDBI uldBI = (ULDBI)getService("SHARED_ULD");						
			return   uldBI.findULDTareWeight(uldValidationFilterVO);
		}
		catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	}
   
}
