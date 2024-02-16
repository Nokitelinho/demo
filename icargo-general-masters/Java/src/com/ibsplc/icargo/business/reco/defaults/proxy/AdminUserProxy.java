package com.ibsplc.icargo.business.reco.defaults.proxy;
/*
 * AdminUserProxy.java Created on July 10, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.admin.user.AdminUserBI;
import com.ibsplc.icargo.business.admin.user.vo.UserRoleGroupDetailsVO;
import com.ibsplc.icargo.business.admin.user.vo.ValidUsersVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * @author A-5867
 * To call validation methods from the Module Reco
 */
public class AdminUserProxy extends SubSystemProxy{

	/**
     * This method is used to validate the UserCodes
     * @param systemParameterCodes
     * @return Map
     * @throws SystemException
     * @throws ProxyException
     */	
	public Collection<ValidUsersVO> validateUsersWithoutRoleGroup(Collection<String> userCodes,
             String companyCode)
    throws SystemException, ProxyException {
		try {
			AdminUserBI userBI = (AdminUserBI)getService("USER");
    	return   userBI.validateUsersWithoutRoleGroup(userCodes,companyCode);
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
  }
	/**
     * This method is used to validate the RoleGroups
     * @param systemParameterCodes
     * @return Map
     * @throws SystemException
     * @throws ProxyException
     */	
	public Collection<UserRoleGroupDetailsVO> validateRoleGroup(Collection<String> roleGroupCodes, 
			String companyCode)
   throws SystemException, ProxyException {
		try {
			AdminUserBI userBI = (AdminUserBI)getService("USER");
   	return   userBI.validateRoleGroup(roleGroupCodes,companyCode);
		}catch(ServiceNotAccessibleException serviceNotAccessibleException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,serviceNotAccessibleException);
		}catch(RemoteException remoteException){
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,remoteException);
		}
 }



}
