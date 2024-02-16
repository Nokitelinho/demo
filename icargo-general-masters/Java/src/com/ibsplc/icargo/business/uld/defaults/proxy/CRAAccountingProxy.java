/*
 * CRAAccountingProxy.java Created on Sep 11, 2009
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;

import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3278
 *
 */
@Module("cra")
@SubModule("accounting")
public class CRAAccountingProxy extends ProductProxy {
	private Log log = LogFactory.getLogger("ULD DEFAULTS");
	
	private static final String CLASS_NAME = CRAAccountingProxy.class.getName();
	
	/**
	 * 
	 * 	Method		:	CRAAccountingProxy.generateULDAccountingEntries
	 *	Added by 	:	A-7656 on 18-Dec-2017
	 * 	Used for 	:
	 *	Parameters	:	@param accountingFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	void
	 */
	public void generateULDAccountingEntries(AccountingFilterVO accountingFilterVO) throws SystemException, ProxyException {
		log.entering(CLASS_NAME, "generateULDAccountingEntries");
		dispatchAsyncRequest("generateULDAccountingEntries", true, accountingFilterVO);
		log.exiting(CLASS_NAME, "generateULDAccountingEntries");
	}
}
