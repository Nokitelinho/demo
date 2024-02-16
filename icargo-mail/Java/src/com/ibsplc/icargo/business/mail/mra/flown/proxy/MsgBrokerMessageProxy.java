/*
 * MsgBrokerMessageProxy.java Created on Jun 22, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
@Module("msgbroker")
@SubModule("message")
public class MsgBrokerMessageProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("MAILTRACKING MRA");

	/**
	 * TODO Purpose
	 * @param baseMessageVO
	 * @throws SystemException
	 */
	public void encodeAndSaveMessage(BaseMessageVO baseMessageVO)
	throws SystemException {
		log.entering("MsgBrokerMessageProxy", "encodeAndSaveMessageAsync");
		try {
			dispatchAsyncRequest("encodeAndSaveMessage", false,baseMessageVO);
		} catch(ProxyException ex) {
			throw new SystemException(ex.getMessage(),ex);
		}
		log.exiting("MsgBrokerMessageProxy", "encodeAndSaveMessageAsync");
	}

	/**
	 * 
	 * TODO Purpose
	 * Oct 10, 2007, a-1739
	 * @param companyCode
	 * @param stationCode
	 * @param messageType
	 * @param sequenceNumber
	 * @return
	 * @throws SystemException
	 */
    public MessageVO findMessageDetails(String companyCode,
    						String stationCode,
    						String messageType,
    						int  sequenceNumber)
    			throws SystemException{
    	try{
        return despatchRequest("findMessageDetails",companyCode,
        		stationCode,messageType,sequenceNumber);
    }catch(ProxyException ex){
    	 throw new SystemException(ex.getMessage(),ex);
    }
  }

	/**
	 * 
	 * TODO Purpose
	 * Jun 22,2010,A-2270
	 * @param messageVo
	 * @throws SystemException
	 */
     public void sendMessageText(MessageVO messageVo)
       throws SystemException,ProxyException{
    	 try{
		  despatchRequest("sendMessageText",messageVo) ;
	     }catch(ProxyException ex){
		   throw  new SystemException(ex.getMessage(),ex);
	    }
     }


	/**
	 * 
	 * TODO Purpose
	 * Oct 10, 2007, a-1739
	 * @param messageVO
	 * @throws SystemException
	 */
	public void  receiveMessage(MessageVO messageVO)
	throws SystemException {
		log.entering("MsgBrokerMessageProxy", "receiveMessage");
		try {
			 dispatchAsyncRequest("receiveMessage",false, messageVO);
		} catch (ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	public Collection<MessageVO> encodeMessage(BaseMessageVO messageVo) throws SystemException
	{
		Collection<MessageVO> messageVOs= null;
		log.entering("MsgBrokerMessageProxy", "receiveMessage");
		try {
		return despatchRequest("encodeMessage", messageVo);
		} catch (ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}
	
	

}
