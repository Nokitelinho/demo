/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.event.ULDDemurrageChannel.java
 *
 *	Created on	:	24-Apr-2023
 *
 *  Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.event;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.event.AbstractChannel;
import com.ibsplc.icargo.framework.event.annotations.EventChannel;
import com.ibsplc.icargo.framework.event.annotations.EventListener;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.event.ULDDemurrageChannel.java
 *	This class is used as channel to listen to CREATE_ULD_LOAN event 
 */
@Module("uld")
@SubModule("defaults")
@EventChannel(value = "uld.defaults.ULDDemurrageChannel",
targetType = ElementType.SINGLE, 
targetClass = TransactionVO.class, 
listeners = {@EventListener(event = "SAVE_ULD_TRANSACTION")})
public class ULDDemurrageChannel extends AbstractChannel {
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.event.AbstractChannel#send(com.ibsplc.icargo.framework.event.vo.EventVO)
	 *	Added on 			: 24-Apr-2023
	 * 	Used for 	:	listening to CREATE_ULD_LOAN event
	 *	Parameters	:	@param eventVO
	 *	Parameters	:	@throws Throwable
	 */
	@Override
	public void send(EventVO eventVO) throws Throwable {
		TransactionVO transactionVO = (TransactionVO) eventVO.getPayload();
		despatchRequest("updateULDTransactionWithDemurrageDetails", transactionVO);
		
	}

}
