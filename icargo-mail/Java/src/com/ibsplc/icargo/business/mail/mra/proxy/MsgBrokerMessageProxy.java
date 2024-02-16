/*
 * MsgBrokerMessageProxy.java Created on March 9, 2007
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.proxy;

import java.util.Collection;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.framework.message.vo.GenericMessageVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1945
 *
 */
@Module("msgbroker")
@SubModule("message")
public class MsgBrokerMessageProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("MAILTRACKING MRA");

	/**
	 * 
	 * @author A-2518
	 * @param messageVO
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public void receiveMessage(MessageVO messageVO) throws ProxyException,
			SystemException {
		log.entering("MsgBrokerMessageProxy", "receiveMessage");
		despatchRequest("receiveMessage", messageVO);
		log.exiting("MsgBrokerMessageProxy", "receiveMessage");
	}

	/**
	 * For sending INVOIC Claim message
	 * @author A-2518
	 * @param baseMessageVO
	 * @throws SystemException
	 */
	public void encodeAndSaveMessage(BaseMessageVO baseMessageVO)
			throws SystemException {
		log.entering("MsgBrokerMessageProxy", "encodeAndSaveMessage");
		try {
			despatchRequest("encodeAndSaveMessage", baseMessageVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
		log.exiting("MsgBrokerMessageProxy", "encodeAndSaveMessage");
	}
	
	/**
     * @author A-2554
     * @param GenericMessageVO
     * @return
     * @throws ProxyException
     * @throws SystemException
     */
 	public void sendMessage(GenericMessageVO genericMessageVO)
 	throws  ProxyException ,SystemException {
 		Log log = LogFactory.getLogger("MAILTRACKING MRA");
 		log.entering("MsgbrokerMessageProxy","sendGenericMessage");
 		despatchRequest("sendGenericMessage",genericMessageVO);
 	}
	
 	/**
 	 * 
 	 * 	Method		:	MsgBrokerMessageProxy.sendMessageText
 	 *	Added by 	:	A-7929 on 11-Jun-2018
 	 * 	Used for 	:   ICRD-245605
 	 *	Parameters	:	@param messageVO
 	 *	Parameters	:	@throws SystemException
 	 *	Parameters	:	@throws ProxyException 
 	 *	Return type	: 	void
 	 */
 	 public void sendMessageText(MessageVO messageVO)throws SystemException,ProxyException {
     	log.entering("MsgBrokerMessageProxy","sendMessageText");
     	despatchRequest("sendMessageText", messageVO) ;
     	log.exiting("MsgBrokerMessageProxy","sendMessageText");
     }
	/**
	 * @author A-7371
	 * @param messageVO
	 * @throws SystemException
	 * @throws ProxyException
	 */
 	 public void saveMessageDetails(MessageVO messageVO)throws SystemException,ProxyException {
      	log.entering("MsgBrokerMessageProxy","sendMessageText");
      	dispatchAsyncRequest("saveMessageDetails",true, messageVO) ;
      	log.exiting("MsgBrokerMessageProxy","sendMessageText");
      }	   

 	 /**
 	  * Method		:	MsgBrokerMessageProxy.getEncodedMessage
 	  *	Added by 	:	A-4809 on May 16, 2019
 	  * Used for 	:
 	  *	Parameters	:	@param baseMessageVO
 	  *	Parameters	:	@return
 	  *	Parameters	:	@throws SystemException
 	  *	Parameters	:	@throws ProxyException 
 	  *	Return type	: 	Collection<MessageVO>
 	  */
 	 public Collection<MessageVO> getEncodedMessage(BaseMessageVO baseMessageVO) 
 			 throws SystemException,ProxyException{
 		log.entering("MsgBrokerMessageProxy","getEncodedMessage");
 		Collection<MessageVO> messageVOs = despatchRequest("getEncodedMessage", baseMessageVO);
 		return messageVOs;
     }
	/**
	 * 	Method		:	MsgBrokerMessageProxy.sendMessage
	 *	Added by 	:	A-4809 on May 23, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param messageVO  
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	MessageVO
	 */
 	 public MessageVO sendMessage(MessageVO messageVO) throws SystemException,ProxyException{
 		 MessageVO messagesVO = despatchRequest("sendMessage",messageVO);
 		 return messagesVO;
 	 }
}
