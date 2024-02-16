/*
 * TxProbeEventSequencingHandler.java Created on 30-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.dispatcher.handlers;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbedState;
import com.ibsplc.neoicargo.framework.probe.PayloadHolder;
import com.ibsplc.neoicargo.framework.probe.ProbePayload;
import com.ibsplc.neoicargo.framework.probe.dispatcher.HandlerState;
import com.ibsplc.neoicargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;



/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			30-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 * @NotThreadSafe
 */
public class TxProbeEventSequencingHandler implements HandlingStateAwareEventHanlder<PayloadHolder>{
	
	private Logger logger = LoggerFactory.getLogger(TxProbeEventSequencingHandler.class);
	
	private static final int PRUNE_INTERVAL = 500;
	private static final long EXPIRE_TIME = 1000 * 60 * 15; // 15 mins
	
	private Map<SequenceKey, SequenceStack> correlationMap = new HashMap<SequenceKey, SequenceStack>(64);
	private long transactionCounter;
	
	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
	 */
	@Override
	public void onEvent(PayloadHolder event, long sequence, boolean endOfBatch) throws Exception {
		switch(event.getProbeState()){
			case AFTER:
				onEventAfter(event);
				break;
			case BEFORE:
				onEventBefore(event);
				break;
			case ON:
				break;
			default:
				break;
		}
		transactionCounter++;
		if(transactionCounter%PRUNE_INTERVAL == 0)
			prune();
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder#handleState()
	 */
	@Override
	public HandlerState handleState() {
		return HandlerState.SEQUENCER;
	}

	protected void prune(){
		final long expiryWindow = System.currentTimeMillis() - EXPIRE_TIME;
		Set<SequenceKey> purgedKeys = new HashSet<>();
		for(Map.Entry<SequenceKey, SequenceStack> e : this.correlationMap.entrySet()){
			if(e.getKey().timestamp < expiryWindow)
				purgedKeys.add(e.getKey());
		}
		if(!purgedKeys.isEmpty()){
			for(SequenceKey key : purgedKeys)
				this.correlationMap.remove(key);
		}
	}
	
	protected void onEventAfter(PayloadHolder event){
		ProbePayload payload = event.getPayload();
		SequenceKey key = SequenceKey.key(payload.getCorrelationId());
		SequenceStack stack = this.correlationMap.get(key);
		if(stack == null){
			logger.warn("unmapped exit event for correlationId : {}", payload.getCorrelationId());
			return;
		}
		int elaspsedTime = stack.calculateElapsedTime(event.getProbe(), event.getProbeState(), payload.getStartTime());
		payload.setElapsedTime(elaspsedTime);
		payload.setSequence(stack.incrementGet());
		if(stack.isEmpty())
			this.correlationMap.remove(key);
	}
	
	protected void onEventBefore(PayloadHolder event){
		ProbePayload payload = event.getPayload();
		SequenceStack stack = correlationMap.get(SequenceKey.key(payload.getCorrelationId()));
		if(stack == null){
			stack = new SequenceStack();
			this.correlationMap.put(new SequenceKey(payload.getCorrelationId(), payload.getStartTime()), stack);
		}
		stack.push(event.getProbe(), event.getProbeState(), payload.getStartTime());
		payload.setSequence(stack.incrementGet());
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder#stop()
	 */
	@Override
	public void stop() {
		
	}

	/**
	 * @author A-2394
	 *
	 */
	private static class SequenceStack{
		
		private Deque<SequenceStackElement> stack = new ArrayDeque<SequenceStackElement>(5);
		private int sequence = 0;
		
		public int incrementGet(){
			return ++this.sequence;
		}
		
		public void push(Probe probe, ProbedState probeState, long startTime){
			this.stack.push(new SequenceStackElement(probe,  startTime));
		}
		
		public boolean isEmpty(){
			return this.stack.isEmpty();
		}
		
		public int calculateElapsedTime(Probe probe, ProbedState probeState, long startTime){
			SequenceStackElement head = stack.peek();
			// TODO check NPE for prod use
			if(head.probe == probe){
				stack.pop();
				return head.elaspsed(startTime);
			}
			// we have missed some sequence either due to errors or due to unhandled exceptions
			int answer = -1;
			SequenceStackElement toPop = null;
			for(SequenceStackElement se : stack){
				if(se.probe == probe){
					toPop = se;
					answer = se.elaspsed(startTime);
					break;
				}
			}
			if(toPop != null)
				stack.remove(toPop);
			return answer;
		}
	}
	
	/**
	 * @author A-2394
	 *
	 */
	private static class SequenceKey{
		static final ThreadLocal<SequenceKey> KEY_CACHE = new ThreadLocal<SequenceKey>(){
			/* (non-Javadoc)
			 * @see java.lang.ThreadLocal#initialValue()
			 */
			@Override
			protected SequenceKey initialValue() {
				return new SequenceKey(null, 0L);
			}
			
		};
		String correlationId;
		long timestamp;
		
		public SequenceKey(String correlationId, long timestamp) {
			super();
			this.correlationId = correlationId;
			this.timestamp = timestamp;
		}

		static SequenceKey key(String correlationId){
			SequenceKey key = KEY_CACHE.get();
			key.correlationId = correlationId;
			return key;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return this.correlationId.hashCode();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			return obj.equals(this.correlationId);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return this.correlationId;
		}
		
		
	}
	
	/**
	 * @author A-2394
	 *
	 */
	private static class SequenceStackElement {
		long startTime;
		Probe probe;
		
		public SequenceStackElement(Probe probe, long startTime) {
			super();
			this.startTime = startTime;
			this.probe = probe;
		}

		int elaspsed(long startTime){
			return (int) (startTime - this.startTime);
		}
	}
}
