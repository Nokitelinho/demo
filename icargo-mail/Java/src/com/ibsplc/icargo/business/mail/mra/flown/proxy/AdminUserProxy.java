/*
 * AdminUserProxy.java Created on Feb 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.admin.user.vo.ValidUsersVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author a-2401
 *
 */
@Module("admin")
@SubModule("user")
public class AdminUserProxy extends ProductProxy{
	//private Log log = LogFactory.getLogger("CRA ACCOUNTING");
	
	/**
	 * @author A-1863
	 * @param userCodes
	 * @param companyCode
	 * @return Collection<ValidUsersVO>
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public Collection<ValidUsersVO> validateUsersWithoutRoleGroup( 
			Collection<String> userCodes, String companyCode)
			 throws ProxyException, SystemException  {
		return despatchRequest("validateUsersWithoutRoleGroup", userCodes, companyCode);
	}	

}
