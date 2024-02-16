/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.proxy.AdminUserProxy.java
 *
 *	Created by	:	a-5956
 *	Created on	:	19-Oct-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.proxy;

import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.proxy.AdminUserProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-5956	:	19-Oct-2017	:	Draft
 */
@Module("admin")
@SubModule("user")
public class AdminUserProxy extends ProductProxy{
	
	private  Log log = LogFactory.getLogger("Customer Management");
	
	public UserVO  findUserDetails(String companycode, String userID)  throws
		SystemException,ProxyException {
		log.entering("AdminUserProxy", "findUserDetails");
		return  despatchRequest("findUserDetails",  companycode,userID);
	}

}
