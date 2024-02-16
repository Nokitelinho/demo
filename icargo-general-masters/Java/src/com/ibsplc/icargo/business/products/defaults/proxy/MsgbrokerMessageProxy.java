/*
 * CapacityProxy.java Created on Jul 6, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.proxy;


import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Action;
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
	private Log log = LogFactory.getLogger("PRODUCTS-MESSAGEBROKERPROXY");

    /**
	 * @author A-1885
	 * @param messageVo
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	@Action("sendMessage")
	public void sendMessage(MessageVO messageVo)
	throws  ProxyException ,SystemException {
		log.entering("MsgbrokerMessageProxy","sendMessage");
		despatchRequest("sendMessage",messageVo);
	}
	
	/**
	 * 
	 * @param baseMessageVO
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void encodeMessage(BaseMessageVO baseMessageVO)
			throws SystemException, ProxyException {
		// dispatchAsyncRequest(baseMessageVO.getCompanyCode(),
		// "encodeAndSaveMessage",false,baseMessageVO) ;*/
		dispatchAsyncRequest("encodeAndSaveMessage", true, baseMessageVO);
	}

	// added by A-2399 for ANA CR 1521
	/**
	 * @param baseMessageVOs
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void encodeMessages(
			Collection<? extends BaseMessageVO> baseMessageVOs)
			throws SystemException, ProxyException {
		dispatchAsyncRequest("encodeAndSaveMessages", true, baseMessageVOs);
	}

	/*
	 * Added method encodeMessagesInOrder By Latish for Bug 14335 on 17th jul
	 * 2008 Messages are setting in the queue in order
	 */
	/**
	 * @param baseMessageVOs
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void encodeMessagesInOrder(
			Collection<? extends BaseMessageVO> baseMessageVOs)
			throws SystemException, ProxyException {
		dispatchAsyncRequest("encodeAndSaveMessages", true, baseMessageVOs);
	}

}
