/*
 * MsgbrokerMessageProxy.java Created on 08 Jun  2008 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.customermanagement.defaults.proxy;


import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageHistoryVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageListFilterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * This class represents the product proxy for shared customer subsystem
 *
 * @author A-2883
 *
 *
 **/
 
@Module("msgbroker")
@SubModule("message")
public class MsgbrokerMessageProxy extends ProductProxy {

	public Page<MessageHistoryVO> findMessageForCustomers(MessageListFilterVO messageListFilterVO,
			Collection<String> address) throws SystemException, ProxyException {
		return despatchRequest("findMessageForCustomers", messageListFilterVO, address);
	}

	public void encodeAndSaveMessages(Collection<BaseMessageVO> baseMessageVOs) throws SystemException, ProxyException {
		despatchRequest("encodeAndSaveMessages", baseMessageVOs);
	}
	
	public void encodeAndSaveMessagesAsync(Collection<BaseMessageVO> messageVOs) throws SystemException, ProxyException {
		dispatchAsyncRequest("encodeAndSaveMessages", false, messageVOs);
	}

}
