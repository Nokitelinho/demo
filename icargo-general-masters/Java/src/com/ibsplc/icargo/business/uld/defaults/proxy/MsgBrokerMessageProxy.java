/*
 * MsgBrokerMessageProxy.java Created on Jul 8, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.proxy;
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
 *
 * @author A-1763
 *
 */
@Module("msgbroker")
@SubModule("message")
public class MsgBrokerMessageProxy extends ProductProxy {
	  private Log log = LogFactory.getLogger("ULD");
	   /**
	    * 
	    * @param baseMessageVO
	    * @throws SystemException
	    * @throws ProxyException
	    */
	  @Action("encodeAndSaveMessage")
	   public Collection<MessageVO> encodeAndSaveMessage(BaseMessageVO baseMessageVO) 
		throws SystemException, ProxyException {
		    log.entering("MsgBrokerMessageProxy","encodeAndSaveMessage");
	       	return despatchRequest("encodeAndSaveMessage",baseMessageVO);
	  }
	  /**
		 * @param baseMessageVO
		 * @return
		 * @throws SystemException
		 * @throws ProxyException
		 */
		@Action("encodeMessage")
		  public Collection<MessageVO> encodeMessage(BaseMessageVO baseMessageVO) throws
		  SystemException, ProxyException{
			  log.entering("MsgBrokerMessageProxy","encodeMessage");
		       return despatchRequest("encodeMessage",baseMessageVO);
		  }
		
		/**
		 * @param messageVo
		 * @return
		 * @throws SystemException
		 * @throws ProxyException
		 */
		@Action("sendMessage")
		public MessageVO sendMessage(MessageVO messageVo)throws
		  SystemException, ProxyException{
			 log.entering("MsgBrokerMessageProxy","sendMessage");
		       return despatchRequest("sendMessage",messageVo);
		}
		/**
		 * @author A-5125
		 * @param messageVOs
		 * @throws SystemException
		 * @throws ProxyException
		 */
		 @Action("sendMessageWithEnvelopeEncoding")
		   public void sendMessageWithEnvelopeEncoding(Collection<MessageVO> messageVOs ) 
			throws SystemException, ProxyException {
			    log.entering("MsgBrokerMessageProxy","sendMessageWithEnvelopeEncoding");
		       	despatchRequest("sendMessageWithEnvelopeEncoding",messageVOs);
		  }
}