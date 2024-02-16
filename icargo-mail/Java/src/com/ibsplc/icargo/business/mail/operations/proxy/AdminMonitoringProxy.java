/*
 * AdminMonitoringProxy.java Created on 18 Aug 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.proxy;

import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Module("admin")
@SubModule("monitoring")
public class AdminMonitoringProxy extends ProductProxy {


/**
 * @author A-5945
 */





	private Log log = LogFactory.getLogger("admin.monitoring");

	
	
	/**
	 * 
	 * @param txnId
	 * @param companyCode
	 * @throws SystemException
	 * @throws ProxyException
	 */
	
	public void resolveTransaction(String companyCode, String txnId,String remarks)
	throws SystemException, ProxyException {
log.entering("AdminMonitoringProxy" , "resolveTransaction");
despatchRequest("resolveTransaction", companyCode,txnId,remarks);
}	

	
}
