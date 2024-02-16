/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.SharedGeneralMasterGroupingProxy.java
 *
 *	Created by	:	A-10647
 *	Created on	:	16-Sep-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.shared.generalmastergrouping.GeneralMasterGroupBI;
import com.ibsplc.icargo.business.shared.generalmastergrouping.GeneralMasterGroupingBusinessException;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SharedGeneralMasterGroupingProxy extends SubSystemProxy {
	private static final Log LOG = LogFactory.getLogger("SHARED_GENERAL_MASTER_GROUPING");

	public GeneralMasterGroupVO listGeneralMasterGroup(GeneralMasterGroupFilterVO filterVO)
			throws ProxyException, SystemException {

		try {
			GeneralMasterGroupBI generalMasterGroupBI = (GeneralMasterGroupBI) getService(
					"SHARED_GENERALMASTERGROUPING");
			LOG.entering("SharedGeneralMasterGroupingProxy", "listGeneralMasterGroup");
			return generalMasterGroupBI.listGeneralMasterGroup(filterVO);
		} catch (ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, serviceNotAccessibleException);
		} catch (RemoteException remoteException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, remoteException);
		} catch (GeneralMasterGroupingBusinessException e) {
			LOG.log(Log.INFO, e);
		}
		return null;

	}
 /**
	 * Find group names of group entity.
	 *
	 * @param generalMasterGroupFilterVO the general master group filter vo
	 * @return the collection
	 * @throws SystemException the system exception
	 * @throws ProxyException the proxy exception
	 * @author A-8353
	 */
	public Collection<String> findGroupNamesOfEntity(
			GeneralMasterGroupFilterVO generalMasterGroupFilterVO) throws SystemException,ProxyException{
		try {
			GeneralMasterGroupBI generalMasterGroupBI = getService("SHARED_GENERALMASTERGROUPING");		
			LOG.entering("SharedGeneralMasterGroupingProxy","findGroupNamesOfEntity");		
			return  generalMasterGroupBI.findGroupNamesOfEntity(generalMasterGroupFilterVO);
		} catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
	}
}
