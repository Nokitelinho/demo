/*
 * SharedULDProxy.java Created on Mar 16, 2007	
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.mail.operations.SharedProxyException;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.shared.uld.ULDBI;
import com.ibsplc.icargo.business.shared.uld.ULDBusinessException;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1883
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		   Mar 16, 2007			  	 A-1883		Created
 */


public class SharedULDProxy extends SubSystemProxy {
	private Log log = LogFactory.getLogger("MAIL TRACKING ");
	
	/**
	 * The ErrorCode in Mail, when the ULDType  is invalid
	 */
	private  static final String INVALID_ULDTYPE= "mailtracking.defaults.invaliduldType";
	private static final String SHAREDULD="SHARED_ULD";
	
	/**@author A-1883
	 * Method returns a collection of valid uld codes
	 * while passing a collection of uld codes
	 *
	 * @param companyCode
	 * @param uldTypeCodes
	 * @return Map<String,ULDTypeValidationVO>
	 * @throws SystemException
	 * @throws SharedProxyException
	 */
    public Map<String,ULDTypeValidationVO>
    		validateULDTypeCodes(String companyCode,
    							 Collection<String> uldTypeCodes)
    	throws SystemException,SharedProxyException{

    	log.entering("SharedULDProxy","validateULDTypeCodes");
    	Map<String,ULDTypeValidationVO> uldMap = null;
    	
		try {
			ULDBI uldBI = (ULDBI)getService("SHARED_ULD");
    	//try{
    	uldMap =  uldBI.validateULDTypeCodes(companyCode,uldTypeCodes);
    	}catch(com.ibsplc.icargo.business.shared.uld.ULDBusinessException ex){
    		Collection<ErrorVO> errors = ex.getErrors();
			for(ErrorVO errorVO : errors) {
				if(SharedProxyException.INVALID_ULDTYPE.
								equals(errorVO.getErrorCode())){
					throw new SharedProxyException(INVALID_ULDTYPE);
				}
			}
    	}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(), e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(), e);
		}
		
    	log.exiting("SharedULDProxy","validateULDTypeCodes");
    	return uldMap ;
    	
    }
    
	/**
	 * Validates whether a uld is a valid type or not
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return ULDValidationVO
	 * @throws SystemException
	 * @throws SharedProxyException
	 */
    public ULDValidationVO validateULD(String companyCode,String uldNumber) 
    throws SystemException,SharedProxyException{

    	log.entering("SharedULDProxy","validateULD");
    	ULDValidationVO uldValidationVO = null;
    	
		try {
			ULDBI uldBI = (ULDBI)getService("SHARED_ULD");
    	//try{
			uldValidationVO =  uldBI.validateULD(companyCode,uldNumber);
    	}catch(com.ibsplc.icargo.business.shared.uld.ULDBusinessException ex){
    		Collection<ErrorVO> errors = ex.getErrors();
			for(ErrorVO errorVO : errors) {
				if(SharedProxyException.INVALID_ULDTYPE.
								equals(errorVO.getErrorCode())){
					throw new SharedProxyException(INVALID_ULDTYPE);
				}
			}
    	}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(), e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(), e);
		}
		
    	log.exiting("SharedULDProxy","validateULD");
    	return uldValidationVO ;
    }
  public Measure  findULDTareWeight(ULDValidationFilterVO  ULDValidationFilterVO )
		  throws SystemException,SharedProxyException{
	  Measure tareWeight=null;
	  try {
			ULDBI uldBI = (ULDBI)getService("SHARED_ULD");
			 tareWeight =  uldBI.findULDTareWeight(ULDValidationFilterVO );
    	}catch(ServiceNotAccessibleException e) {
			throw new SystemException(e.getMessage(), e);
		}catch(RemoteException e){
			throw new SystemException(e.getMessage(), e);
		}
	  
	  return  tareWeight;
  }
  /**
	 * @author A-5526
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 */
	 public Collection<ULDPositionVO> findULDPosition(Collection<ULDPositionFilterVO> filterVOs)
   throws SystemException,SharedProxyException {
		 Collection<ULDPositionVO> uldPositionVos=null;
		
			 try {
					ULDBI uldBI = (ULDBI)getService("SHARED_ULD");
					uldPositionVos=uldBI.findULDPosition(filterVOs);
					
		    	}catch(ServiceNotAccessibleException e) {
					throw new SystemException(e.getMessage(), e);
				}catch(RemoteException e){
					throw new SystemException(e.getMessage(), e);
				}
			 catch(com.ibsplc.icargo.business.shared.uld.ULDBusinessException ex){
		    		Collection<ErrorVO> errors = ex.getErrors();
					for(ErrorVO errorVO : errors) {
						if("shared.uld.aircraftincompatible".
										equals(errorVO.getErrorCode())){
							throw new SharedProxyException("shared.uld.aircraftincompatible");
						}
					}
		    	}
			return uldPositionVos;
	}
	public Collection<ULDTypeVO> findULDTypes(ULDTypeFilterVO uldTypeFilterVO) throws ProxyException, SystemException {
		try {
			ULDBI uldBI = (ULDBI) getService(SHAREDULD);
			return uldBI.findULDTypes(uldTypeFilterVO);
		} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, serviceNotAccessibleException);
		} catch (RemoteException remoteException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, remoteException);
		} catch (ULDBusinessException e) {
			throw new ProxyException(e);
		}
  }

}
