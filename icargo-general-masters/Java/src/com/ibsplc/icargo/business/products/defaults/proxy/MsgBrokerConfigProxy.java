/*
 * MsgBrokerConfigProxy.java Created on Jul 4, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.proxy;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressFilterVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3331
 * 
 */
@Module("msgbroker")
@SubModule("config")
public class MsgBrokerConfigProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("PRODUCT DEFAULTS");

	/**
	 * 
	 * @param filterVO
	 * @throws SystemException
	 * @throws com.ibsplc.xibase.server.framework.exceptions.SystemException
	 * @throws ProxyException
	 */
	public HashMap<String, Collection<String>> findConfiguredUsersForMessage(
			MessageAddressFilterVO filterVO) throws SystemException,
			ProxyException {
		log.entering("MsgBrokerConfigProxy", "findConfiguredUsersForMessage");
		return despatchRequest("findConfiguredUsersForMessage", filterVO);
	}

	/**
	 * This method finds the address configured in the system
	 * 
	 * @author A-2421
	 * @param MessageAddressFilterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<MessageAddressVO> findMessageAddressDetails(
			MessageAddressFilterVO partyaddressfiltervo) throws SystemException {
		log.log(Log.FINE,
				"inside MsgBrokerConfigProxy , findMessageAddressDetails");
		Collection<MessageAddressVO> partAddressDetailsVOs = null;
		try {
			partAddressDetailsVOs = despatchRequest("findMsgAddressDetails",
					partyaddressfiltervo);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getErrors());
		}
		return partAddressDetailsVOs;
	}
}
