/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.AdminUserProxy.java
 *
 *	Created by	:	A-4809
 *	Created on	:	10-May-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.AdminUserProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	10-May-2022	:	Draft
 */
@Module("admin")
@SubModule("user")
public class AdminUserProxy extends ProductProxy{
	
	private static final String MODULE_NAME = "AdminUserProxy";
    private static final Log LOG = LogFactory.getLogger("MAIL OPERATIONS ");

    
    
    
    /**
     * 	Method		:	AdminUserProxy.findUserDetails
     *	Added by 	:	A-4809 on 10-May-2022
     * 	Used for 	:
     *	Parameters	:	@param companyCode
     *	Parameters	:	@param userCode
     *	Parameters	:	@return
     *	Parameters	:	@throws ProxyException
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	UserVO
     */
	public UserVO findUserDetails(String companyCode, String userCode) throws ProxyException, SystemException{
		LOG.entering(MODULE_NAME, "findUserDetails");
		return despatchRequest("findUserDetails", companyCode, userCode);
	}
	

}


