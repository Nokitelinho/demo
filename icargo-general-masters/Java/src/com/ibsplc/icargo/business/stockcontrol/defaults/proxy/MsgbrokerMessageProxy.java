/*
 * MsgbrokerMessageProxy.java Created on Jul 6, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1358
 *
 */
@Module("msgbroker")
@SubModule("message")
public class MsgbrokerMessageProxy extends ProductProxy {
	private Log log = LogFactory.getLogger("STOCKCONTROL-MESSAGEBROKERPROXY");

    /**
	 * @author A-1885
	 * @param messageVo
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void sendMessage(MessageVO messageVo)
	throws  ProxyException ,SystemException {
		log.entering("MsgbrokerMessageProxy","sendMessage");
		despatchRequest("sendMessage",messageVo);
	}

}
