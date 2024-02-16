/*
 * SharedDefaultsProxy.java Created on May 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to lice nse terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;


import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalconfig.vo.GeneralConfigurationMasterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateVO;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.business.shared.defaults.SharedDefaultsBI;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import java.rmi.RemoteException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-1936
 *
 */


public class SharedDefaultsProxy extends SubSystemProxy {
	
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String SHAREDDEFAULTS = "SHARED_DEFAULTS";

	
	
	/**
	 * @author a-1936
	 * @param systemParameterCodes
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public HashMap<String,String> findSystemParameterByCodes(Collection<String> 
						systemParameterCodes)
						throws SystemException {

		log.entering("Inside the FlightOperationsProxy","findSystemParameterByCodes");
		HashMap<String,String> systemParameterCodesMap=null;
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
		
		 systemParameterCodesMap=   (HashMap<String,String>)defaultsBI.findSystemParameterByCodes(systemParameterCodes);		
		 }catch(ServiceNotAccessibleException e) {
				throw new SystemException(e.getMessage(), e);
			}catch(RemoteException e){
				throw new SystemException(e.getMessage(), e);
			}
	  return systemParameterCodesMap;	  

		
  }
	public  HashMap<String,Collection<OneTimeVO>> findOneTimeValues(String companyCode,Collection<String> fieldTypes) 
    throws SystemException, ProxyException{
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
		return  (HashMap<String,Collection<OneTimeVO>>)defaultsBI.findOneTimeValues(companyCode,fieldTypes);

		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}

	}
	public void saveFileUploadExceptions(Collection<FileUploadErrorLogVO> fileUploadErrorLogVOs)
		    throws SystemException
		  {
		    try
		    {
		      SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
		      defaultsBI.saveFileUploadExceptions(fileUploadErrorLogVOs);
		    } catch (ServiceNotAccessibleException serviceNotAccessibleException) {
		      throw new SystemException("CON002", serviceNotAccessibleException);
		    } catch (RemoteException remoteException) {
		      throw new SystemException("CON002", remoteException);
		    }
	}
	
	//Added for ICRD-243365
	public Collection<GeneralConfigurationMasterVO> findGeneralConfigurationDetails(GeneralConfigurationFilterVO generalTimeMappingFilterVO)
		    throws SystemException
		  {
		    try
		    {
		      SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
		      return defaultsBI.findGeneralConfigurationDetails(generalTimeMappingFilterVO);
		   
		    } catch (ServiceNotAccessibleException serviceNotAccessibleException) {
		      throw new SystemException("CON002", serviceNotAccessibleException);
		    } catch (RemoteException remoteException) {
		      throw new SystemException("CON002", remoteException);
		    }
			
	}
	public void doGenerate(FileGenerateVO generateVo)
			throws  SystemException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService(SHAREDDEFAULTS);
			 defaultsBI.doGenerate(generateVo);
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	}
}
