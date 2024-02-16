/*
 * CustomsMessageProxy.java Created on Aug 12, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import com.ibsplc.icargo.business.customs.defaults.vo.CustomsDefaultsVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3342
 *
 */
@Module("customs")
@SubModule("message")
public class CustomsMessageProxy extends ProductProxy {
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/**
	 * @author A-8083
	 * @param customsDefaultsVO
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void sendCustomsMessages(CustomsDefaultsVO customsDefaultsVO)
			throws SystemException, ProxyException {
		despatchRequest("sendCustomsMessages",customsDefaultsVO);
	}
	
}
