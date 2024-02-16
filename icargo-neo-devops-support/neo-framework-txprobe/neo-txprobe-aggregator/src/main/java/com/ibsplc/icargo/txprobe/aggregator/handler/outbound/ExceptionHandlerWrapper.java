/*
 * ExceptionHandlerWrapper.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.handler.outbound;

import com.ibsplc.icargo.txprobe.api.ProbeDataHolder;
import com.lmax.disruptor.EventHandler;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			13-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public class ExceptionHandlerWrapper implements EventHandler<ProbeDataHolder>{

	private EventHandler<ProbeDataHolder> handler;
	
	public ExceptionHandlerWrapper(EventHandler<ProbeDataHolder> handler) {
		super();
		this.handler = handler;
	}

	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
	 */
	@Override
	public void onEvent(ProbeDataHolder event, long sequence, boolean endOfBatch) throws Exception {
		if(event.isErrored())
			return;
		try{
			handler.onEvent(event, sequence, endOfBatch);
		}catch(Throwable t){
			t.printStackTrace();
			event.setErrored(true);
		}finally{
			event.decrementPendingHandler();
		}
	}

	
	
}
