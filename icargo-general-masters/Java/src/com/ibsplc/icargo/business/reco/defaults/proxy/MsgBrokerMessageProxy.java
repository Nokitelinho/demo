/**
 *	Java file	: 	com.ibsplc.icargo.business.shared.embargo.proxy.SharedEmbargoProxy.java
 *
 *	Created by	:	A-5219
 *	Created on	:	18-Sep-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.message.vo.reco.defaults.EmbargoMasterMessageVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.reco.defaults.proxy.MsgBrokerMessageProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	18-Sep-2013	:	Draft
 */
@Module("msgbroker")
@SubModule("message")
public class MsgBrokerMessageProxy extends ProductProxy{
	
		private Log log = LogFactory.getLogger("MSGBROKER_MESSAGE_PROXY");

	/**
	 * 	Method		:	MsgBrokerMessageProxy.encodeMessages
	 *	Added by 	:	A-5219 on 18-Sep-2013
	 * 	Used for 	:
	 *	Parameters	:	@param embargoMasterMessageVOs 
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public void encodeMessages(
			Collection<EmbargoMasterMessageVO> embargoMasterMessageVOs) throws ProxyException, SystemException {
		log.entering("MsgBrokerMessageProxy", "encodeMessages");
		dispatchAsyncRequest("encodeAndSaveMessages", false, embargoMasterMessageVOs);
		
	}
	
	
	

}
