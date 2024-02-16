/*
 * MsgBrokerMessageProxy.java Created on Sep 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageListFilterVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  Proxy for MessageBroker
 * @author A-1739
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		  Sep 15, 2006			a-1739		Created
 */
@Module("msgbroker")
@SubModule("message")
public class MsgBrokerMessageProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	/**
	 * TODO Purpose
	 * Sep 15, 2006, a-1739
	 * @param baseMessageVO
	 * @throws SystemException
	 */
	public Collection<MessageVO> encodeAndSaveMessage(BaseMessageVO baseMessageVO)
	throws SystemException {
		log.entering("MsgBrokerMessageProxy", "encodeAndSaveMessageAsync");
		try {
			return despatchRequest("encodeAndSaveMessage",baseMessageVO);
		} catch(ProxyException ex) {
			throw new SystemException(ex.getMessage(),ex);
		}
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
	 * This method is used to send  the Cardit Message to the Transferred
	      * Carriers wenever a Transfer of MailBags Happens
	 * TODO Purpose
	 * Oct 10, 2007, a-1739
	 * @param messageVo
	 * @throws SystemException
	 */
     public void sendMessageText(MessageVO messageVo)
       throws SystemException{
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
	/**
	 * 
	 * @param messageListFilterVO
	 * @return
	 * @throws SystemException
	 */
    public Page<MessageVO> listMessageDetails(MessageListFilterVO messageListFilterVO)
    			throws SystemException{
    	try{
        return despatchRequest("listMessageDetails",messageListFilterVO);
	    }catch(ProxyException ex){
	    	 throw new SystemException(ex.getMessage(),ex);
	    }
  }
    /**
     * @author A-8353
     * @param baseMessageVO
     * @return
     * @throws SystemException
     * @throws ProxyException 
     */
    public void encodeAndSaveMessageAsync(BaseMessageVO baseMessageVO)
    		throws SystemException, ProxyException {
    			log.entering("MsgBrokerMessageProxy", "encodeAndSaveMessageAsync");
    			dispatchAsyncRequest("encodeAndSaveMessage",true,baseMessageVO) ;
    }

}
