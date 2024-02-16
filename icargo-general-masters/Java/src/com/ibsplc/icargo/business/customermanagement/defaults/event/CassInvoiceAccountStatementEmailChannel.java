/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.CassInvoiceAccountStatementEmailChannel.java
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */

/**
 * Each method of this class must detail the functionality briefly in the javadoc comments preceding the method.
 * This is to be followed in case of change of functionality also.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.event;

import com.ibsplc.icargo.business.cra.agentbilling.cass.vo.CASSFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.CassInvoiceAccountStatementEmailChannelMapper;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.framework.event.Channel;
import com.ibsplc.icargo.framework.event.EventMapper;
import com.ibsplc.icargo.framework.event.exceptions.ChannelCreationException;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.CassInvoiceAccountStatementEmailChannel.java
 *	Version	:	Name						 :	Date					:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-118899	 :	09-Aug-2021	:	Created
 */

@Module("customermanagement")
@SubModule("defaults")
public class CassInvoiceAccountStatementEmailChannel extends Channel {

	public static final String CHANNEL_NAME = "CassInvoiceAccountStatementEmailChannel";

	public CassInvoiceAccountStatementEmailChannel() throws ChannelCreationException {
		super(CHANNEL_NAME);
	}

	@Override
	protected EventMapper getEventMapper() {
		return new CassInvoiceAccountStatementEmailChannelMapper();
	}

	@Override
	public void send(EventVO eventVO) throws Throwable {
		Object[] args = (Object[]) eventVO.getPayload();

		if (args != null && args.length > 0) {
			for (Object obj : args) {
				CASSFilterVO filterVO = (CASSFilterVO) obj;
				EmailAccountStatementFeatureVO featureVO = CassInvoiceAccountStatementEmailChannelMapper.mapToEmailAccountStatementFeatureVO(filterVO);
				dispatchAsyncRequest("emailAccountStatement", true, featureVO);
			}
		}
	}

}
