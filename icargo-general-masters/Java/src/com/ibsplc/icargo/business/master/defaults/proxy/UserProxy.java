/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.warehouse.defaults.handlingareaassignment.ListCommand.java
 *
 *	Created by	:	A-6783
 *	Created on	:	June 23, 2017
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.master.defaults.proxy;
/**
 *	Java file	: 	com.ibsplc.icargo.business.master.defaults.proxy.UserProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:   A-6783  :	June 23, 2017	: 	Draft
 */
import java.util.List;

import com.ibsplc.icargo.business.admin.user.vo.UserEnquiryFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
@Module("admin")
@SubModule("user")

public class UserProxy extends ProductProxy{
	
	public UserProxy(){
	}
	public List<UserVO> findAllHandlingAreaUsers(UserEnquiryFilterVO userEnquiryFilterVO) throws ProxyException, SystemException
	{	
		return despatchRequest("findAllHandlingAreaUsers",userEnquiryFilterVO);
	}
	/**
	 * added by E-1289 for ICRD-225469
	 * @param companyCode
	 * @param userId
	 * @return LogonAttributes
	 */
	public LogonAttributes getUserParametersValueMap(String companyCode , String userId) throws ProxyException, SystemException {
		return despatchRequest("getUserDetailsForLogon",companyCode,userId);
	}
}
