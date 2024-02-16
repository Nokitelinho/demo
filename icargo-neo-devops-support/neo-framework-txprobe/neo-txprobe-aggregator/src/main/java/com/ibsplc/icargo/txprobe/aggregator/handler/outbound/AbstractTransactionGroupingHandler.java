/*
 * AbstractTransactionGroupingHandler.java Created on 12-Feb-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.handler.outbound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.txprobe.api.ProbeData;
import com.ibsplc.icargo.txprobe.api.ProbeDataHolder;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			12-Feb-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public abstract class AbstractTransactionGroupingHandler<T> implements BackEndHandler{

	private Map<String, List<T>> buffer = new HashMap<>(64);

	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
	 */
	@Override
	public void onEvent(ProbeDataHolder event, long sequence, boolean endOfBatch) throws Exception {
		T publishEvent = filterAndMap(event.getProbeData());
		String correlationId = event.getProbeData().getCorrelationId();
		List<T> publishEvents = null;
		if(publishEvent != null){
			publishEvents = buffer.get(correlationId);
			if(publishEvents == null){
				publishEvents = new ArrayList<T>(5);
				buffer.put(correlationId, publishEvents);
			}
			publishEvents.add(publishEvent);
		}
		// we have received the last event
		if(event.getProbeData().isLast() && (publishEvents = buffer.remove(correlationId)) != null)
			processTransaction(publishEvents);
		
	}
	
	/**
	 * Perform filter operation on the event
	 * @param data - the event
	 * @return null if not applicable or T
	 */
	protected abstract T filterAndMap(ProbeData data);
	
	/**
	 * Method to perform processing on the events of the batched transaction
	 * @param events
	 */
	protected abstract void processTransaction(List<T> events);
	
	/**
	 * 
	 * @param correlationId
	 * @return
	 */
	protected List<T> buffer(String correlationId){
		return buffer.getOrDefault(correlationId, Collections.emptyList());
	}
}
