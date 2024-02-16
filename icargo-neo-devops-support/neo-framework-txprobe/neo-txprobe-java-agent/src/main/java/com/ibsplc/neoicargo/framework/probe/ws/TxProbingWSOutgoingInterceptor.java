/*
 * TxProbingWSOutgoingInterceptor.java Created on 08-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.ws;

import com.ibsplc.neoicargo.framework.probe.TxProbeFacade;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			08-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class TxProbingWSOutgoingInterceptor extends TxProbeWSInterceptor{
	
	public TxProbingWSOutgoingInterceptor(TxProbeFacade facade) {
		super(facade, Phase.PRE_STREAM);
		addBefore(StaxOutInterceptor.class.getName());
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.services.dummy.test.webservices.interceptors.TxProbeWSInterceptor#handleMessage(org.apache.cxf.message.Message)
	 */
	@Override
	public void handleMessage(Message message) throws Fault {
		message.put(Message.INBOUND_MESSAGE, Boolean.FALSE);
		super.handleMessage(message);
	}
}
