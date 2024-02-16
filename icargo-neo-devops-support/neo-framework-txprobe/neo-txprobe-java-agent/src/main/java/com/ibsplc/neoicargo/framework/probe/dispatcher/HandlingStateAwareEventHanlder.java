/*
 * HandlingStateAwareEventHanlder.java Created on 04-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.dispatcher;

import com.lmax.disruptor.EventHandler;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			04-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public interface HandlingStateAwareEventHanlder<PayloadHolder> extends EventHandler<PayloadHolder>{

	/**
	 * @return - Logical Processing State
	 */
	HandlerState handleState();
	
	/**
	 * Stop the handler and release any native resources.
	 */
	void stop();
}
