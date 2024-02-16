/*
 * MsgBrokerConfigProxy.java Created on APR 04, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * Author(s): A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.HashMap;

import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressFilterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  Proxy for MessageBroker Config submodule
 * @author A-3251 SREEJITH P.C.
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		         Description
 * -------------------------------------------------------------------------
 * 0.1     		  APR 04, 2008			A-3251 SREEJITH P.C.		Created
 */
@Module("msgbroker")
@SubModule("config")
/**
 * @author A-3251
 *
 */
public class MsgBrokerConfigProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	/**
	 * TODO Purpose
	 * @param filterVO
	 * @throws SystemException
	 */
	public HashMap findconfiguredusersformailtracking(MessageAddressFilterVO filterVO)
     throws SystemException{
		 log.entering("MsgBrokerConfigProxy", "findconfiguredusersformailtracking");
			try {
				 return despatchRequest("findConfiguredUsersForMessage",filterVO);
			} catch (ProxyException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}
	 	}
}
