/*
 * SharedDefaultsProxy.java Created on Mar 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.proxy;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.defaults.SharedDefaultsBI;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParameterConfigurationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-2408
 *
 */
public class SharedDefaultsProxy extends SubSystemProxy {

	/**
	 * This method is used to retrieve the onetime values for all the given field types.
	 * The return Map has the fieldType as the Key and the Collection OneTimeVO as value.
	 * @param companyCode
	 * @param fieldTypes
	 * @return HashMap
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeValues(
			String companyCode, Collection<String> fieldTypes)
			throws SystemException, ProxyException {
		try {
			SharedDefaultsBI sharedDefaultsBI = (SharedDefaultsBI) getService("SHARED_DEFAULTS");
			return sharedDefaultsBI.findOneTimeValues(companyCode, fieldTypes);
		} catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	}


	/**
	 * This method is used to fetch the SystemParameter values
	 *
	 * @author A-3251
	 * @param systemParameterCodes
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
    public Map<String,String> findSystemParameterByCodes(
    		Collection<String> systemParameterCodes)
    throws SystemException, ProxyException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
			return   defaultsBI.findSystemParameterByCodes(systemParameterCodes);
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }

    /**
     * This method is used to generate MRA Interface File
     * @param generateVo
     * @throws ProxyException
     * @throws SystemException
     */
	public void doGenerate(FileGenerateVO generateVo)
			throws  SystemException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
			 defaultsBI.doGenerate(generateVo);
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	}

	/**
	 *
	 * @param generalParameterConfigurationVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Map<String, HashMap<String, String>> findGeneralParameterConfigurationDetails(GeneralParameterConfigurationVO generalParameterConfigurationVO)
    throws SystemException, ProxyException {
		try {
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
			return   defaultsBI.findGeneralParameterConfigurationDetails(generalParameterConfigurationVO);
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }
	/**
	 *
	 * 	Method		:	SharedDefaultsProxy.saveFileUploadExceptions
	 *	Added by 	:	A-7531 on 29-Jan-2019
	 * 	Used for 	:icrd-235819
	 *	Parameters	:	@param fileUploadErrorLogVOs
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
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

	public String generateFile(FileGenerateVO fileGenerateVO)
		throws SystemException, ProxyException, WebServiceException {
			try {
				SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
				return defaultsBI.generateFile(fileGenerateVO);
			}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
			}catch(RemoteException remoteException){
				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
			}
    }
	/**
	 *
	 * 	Method		:	SharedDefaultsProxy.updateSettlementFileUploadStatus
	 *	Added by 	:	A-5219 on 08-Jan-2021
	 * 	Used for 	:
	 *	Parameters	:	@param fileUploadFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException
	 *	Return type	: 	void
	 */
	public void updateSettlementFileUploadStatus(FileUploadFilterVO fileUploadFilterVO)
			throws SystemException, ProxyException{
		try{
			SharedDefaultsBI defaultsBI = (SharedDefaultsBI)getService("SHARED_DEFAULTS");
			defaultsBI.updateFileUploadStatus(fileUploadFilterVO);
		}catch (ServiceNotAccessibleException serviceNotAccessibleException) {
		      throw new SystemException("CON002", serviceNotAccessibleException);
		    } catch (RemoteException remoteException) {
		      throw new SystemException("CON002", remoteException);
			}
    }
}
