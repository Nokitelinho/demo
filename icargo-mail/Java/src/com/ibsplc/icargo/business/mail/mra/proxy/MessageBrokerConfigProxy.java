/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.proxy.MessageBrokerConfigProxy.java
 *
 *	Created by	:	A-4809
 *	Created on	:	08-Jan-2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressFilterVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressVO;
import com.ibsplc.icargo.business.msgbroker.config.mode.vo.MessageModeParameterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.proxy.MessageBrokerConfigProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	08-Jan-2014	:	Draft
 */
@Module("msgbroker")
@SubModule("config")
public class MessageBrokerConfigProxy extends ProductProxy{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String CLASS_NAME="MessageBrokerConfigProxy";
	/**
	 * 
	 * 	Method		:	MessageBrokerConfigProxy.findMessageAddressDetails
	 *	Added by 	:	A-4809 on 08-Jan-2014
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Collection<MessageAddressVO>
	 */
	public Collection<MessageAddressVO> findMessageAddressDetails(MessageAddressFilterVO filterVO)
	throws SystemException,ProxyException{
		log.entering(CLASS_NAME, "findMessageAddressDetails");
		return despatchRequest("findMsgAddressDetails", filterVO);  
	}
	/**
	 * 
	 * 	Method		:	MessageBrokerConfigProxy.getSplitedAddress
	 *	Added by 	:	A-4809 on 08-Jan-2014
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param modeName
	 *	Parameters	:	@param address
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Collection<MessageModeParameterVO>
	 */
	public Collection<MessageModeParameterVO> getSplitedAddress(String companyCode, String modeName, String address)
	throws SystemException,ProxyException{
		log.entering(CLASS_NAME, "getSplitedAddress");
		return despatchRequest("getSplitedAddress", companyCode,modeName,address); 
	}
}
