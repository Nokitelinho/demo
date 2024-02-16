/*
 * ULDMessageProcessor.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.message;

import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.muc.MUCMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.scm.SCMMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageVO;
import com.ibsplc.icargo.business.uld.defaults.MessageConfigException;
import com.ibsplc.icargo.business.uld.defaults.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ULDMessageProcessor {
    
	private Log log = LogFactory.getLogger("ULD");
    
/*This method is used for processing the UCM Messages
 * The method will do the stock updates
 * 
 *@param UCMMessageVO ucmMessageVO
 *
 *
 * 
 *@throws SystemException 
 */    
    /**
     * @param ucmMessageVO
     * @throws SystemException
     */
  public void processUCMMessage(UCMMessageVO ucmMessageVO)
  throws SystemException{
      
  }
    
  /*This method is used for sending the UCM Messages
   * The method will do the stock updates
   *DOUBT:If the Message is having Client View then 
   *ULDMessageVO will be Obtained from the Client.
   *Other wise ULDMessageVO has to be formulated at
   *the Server Side by Querying.
   * 
   *@param UCMMessageVO ucmMessageVO
   *
   *
   * 
   *@throws SystemException 
   */    
    /***
     * @param ucmMessageVO
     * @throws SystemException
     */
    public void sendManualUCMMessage(UCMMessageVO ucmMessageVO)
    throws SystemException{
    	log.entering("ULDMessageProcessor","sendManualUCMMessage");
    	try{
    		Collection<MessageVO> messageVOs = new MsgBrokerMessageProxy().encodeAndSaveMessage(ucmMessageVO);
    	}catch(ProxyException proxyException){
    		
log
					.log(
							Log.INFO,
							"%%%%%%%%%%%% errors from msgboker(encoding may be falied) ",
							proxyException.getErrors());
    	}
    }
    /*This method is used for sending the UCM Messages
     * Automatically
     *
     * @param	String messageMode 
     * 
     *@throws SystemException 
     */    
      /***
       * @param messageMode
       * @throws SystemException
       */
      public void sendAutoUCMMessage(String messageMode)
      throws SystemException{
   
      }
      
  /**
   * 
   * @param scmMessageVO
   * @throws SystemException
   */   
          
        public void processSCMMessage(SCMMessageVO scmMessageVO)
        throws SystemException{
            
        } 
        
        /*This method is used for sending the SCM Messages
         * 
         *DOUBT:If the Message is having Client View then 
         *SCMMessageVO will be Obtained from the Client.
         *Other wise SCMMessageVO has to be formulated at
         *the Server Side by Querying.
         * 
         *@param SCMMessageVO SCMMessageVO
         *
         *
         * 
         *@throws SystemException 
         */   
        /***
         * @param scmMessageVO
         * @throws SystemException
         */
          
          public void sendManualSCMMessage(SCMMessageVO scmMessageVO)
          throws SystemException , MessageConfigException{
        	  log.entering("ULDMessageProcessor","sendManualSCMMessage");
        	  try{
        		  Collection<MessageVO> messageVOs = new MsgBrokerMessageProxy().encodeAndSaveMessage(scmMessageVO);
          	}catch(ProxyException proxyException){
          		log.log(Log.INFO, "%%%%%%%%%%%%% proxyException  ",
						proxyException.getErrors());
				throw new MessageConfigException(MessageConfigException.MESSAGE_CONFIG_EXCEPTION_FOR_SCM);
          		//throw new SystemException(proxyException.getMessage());
          		
          	}
        }
          
          /**
         * @param scmMessageVO
         * @return
         * @throws SystemException
         * @throws MessageConfigException
         *added by A-2408 for SCM CR
         */
        public Collection<MessageVO> encodeSCMMessage(SCMMessageVO scmMessageVO)
          throws SystemException , MessageConfigException{
        	  log.entering("ULDMessageProcessor","sendManualSCMMessage");
        	  try{
        		  //Added by A-3830 for Bug 103239
        		  scmMessageVO.setMessageStandard("AHM");
        		  scmMessageVO.setMessageType("SCM");
        		  //Added by A-3830 ends
          		return new MsgBrokerMessageProxy().encodeAndSaveMessage(scmMessageVO);
          	}catch(ProxyException proxyException){
          		log.log(Log.INFO, "%%%%%%%%%%%%% proxyException  ",
						proxyException.getErrors());
				throw new MessageConfigException(MessageConfigException.MESSAGE_CONFIG_EXCEPTION_FOR_SCM);
          		//throw new SystemException(proxyException.getMessage());
          		
          	}
          }
          //added by nisha on 21-11-07 for CR-15 starts
          /**
           * @param lucMessageVO
           * @return luc raw message
           * @throws SystemException
           * @throws MessageConfigException
           */
          public Collection<MessageVO> encodeLUCMessage(LUCMessageVO lucMessageVO)
            throws SystemException , MessageConfigException{
          	  log.entering("ULDMessageProcessor","encodeLUCMessage");
          	  try{
          		  return new MsgBrokerMessageProxy().encodeMessage(lucMessageVO);
          		  
          		  
          		  
            		
            	}catch(ProxyException proxyException){
            		log.log(Log.INFO, "%%%%%%%%%%%%% proxyException  ",
							proxyException.getErrors());
					throw new MessageConfigException(MessageConfigException.MESSAGE_CONFIG_EXCEPTION_FOR_LUC);
            		//throw new SystemException(proxyException.getMessage());
            		
            	}
            }
//        added by nisha on 21-11-07 for  CR-15 ends
          /**
           * 
           * @param lucMessageVO
           * @throws SystemException
           */
          public Collection<MessageVO> sendManualLUCMessage(LUCMessageVO lucMessageVO)
          throws SystemException , MessageConfigException{
        	  log.entering("ULDMessageProcessor","sendManualLUCMessage");
        	  Collection<MessageVO> messageVOs = null;
        	  try{
        		  messageVOs = Proxy.getInstance().get(MsgBrokerMessageProxy.class).encodeAndSaveMessage(lucMessageVO);
          	}catch(ProxyException proxyException){
          		log.log(Log.INFO, "%%%%%%%%%%%  proxyException ",
						proxyException.getErrors());
				log.log(Log.INFO, "%%%%%%%%%%%  proxyException ",
						proxyException.getMessage());
				throw new MessageConfigException(MessageConfigException.MESSAGE_CONFIG_EXCEPTION_FOR_LUC);
          		//throw new SystemException(proxyException.getMessage());
          	}
        	  return messageVOs;
        }
          
          /**
           * 
           * @param mucMessageVO
           * @throws SystemException
           */
          
          public void sendManualMUCMessage(MUCMessageVO mucMessageVO)
          throws SystemException{
        	  log.entering("ULDMessageProcessor","sendManualMUCMessage");
        	  try{
        		  Collection<MessageVO> messageVOs = new MsgBrokerMessageProxy().encodeAndSaveMessage(mucMessageVO);
	          }catch(ProxyException proxyException){
	          		throw new SystemException(proxyException.getMessage());
	         }
         }
      /**
       * 
       * @throws SystemException
       */  
            
            public void sendAutoSCMMessage()
            throws SystemException{
         
            }
        

}
