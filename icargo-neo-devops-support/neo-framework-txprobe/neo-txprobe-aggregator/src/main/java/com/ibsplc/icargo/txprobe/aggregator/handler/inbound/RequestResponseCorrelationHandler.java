/*
 * RequestResponseCorrelationHandler.java Created on 28-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.handler.inbound;

import com.ibsplc.icargo.txprobe.aggregator.Dispatcher;
import com.ibsplc.icargo.txprobe.aggregator.PayloadHolder;
import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbeData;
import com.ibsplc.icargo.txprobe.api.ProbeDataConstants;
import com.ibsplc.icargo.txprobe.api.ProbedState;
import com.lmax.disruptor.EventHandler;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.*;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			28-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@Singleton
@Named("in.requestResponseCorrelator")
public class RequestResponseCorrelationHandler implements EventHandler<PayloadHolder>, ProbeDataConstants{

	static final Logger logger = LoggerFactory.getLogger(RequestResponseCorrelationHandler.class);

	static final String DOCSIZE = "_docSize_";
	
	@ConfigProperty( name = "in.handler.correlator.pruneInterval")
	int pruneInterval;

	@ConfigProperty( name = "in.handler.correlator.expiryTimeMs")
	long expiryTimeMs; // 30 mins

	@Inject
	@Named("dispatcher.out")
	Dispatcher<ProbeData, ?> dispatcher;
	
	private final Map<String, Object> probeMD = new HashMap<>(8);
	private final Map<SequenceKey, SequenceStack> correlationMap = new HashMap<>(128);
	private long transactionCounter = 0L;
	
	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
	 */
	@Override
	public void onEvent(PayloadHolder event, long sequence, boolean endOfBatch) throws Exception {
		Object[][] payload = event.getData();
		probeMD.clear();
		retrieveMDFields(payload, probeMD);
		ProbedState state = (ProbedState) probeMD.get(PROBE_STATE);
		probeMD.put(DOCSIZE, event.getRawSize());
		switch(state){
			case AFTER:
				onAfter(payload, probeMD);
				break;
			case BEFORE:
				onBefore(payload, probeMD);
				break;
			case ON:
				ProbeData data = new ProbeData();
				data.fromStream(payload, false);
				data.setMerged(true);
				data.setDocSize(event.getRawSize());
				dispatch(data);
				break;
			default:
				break;
		}
		transactionCounter++;
		if(transactionCounter%pruneInterval == 0)
			prune();
	}

	protected void dispatch(ProbeData data){
		dispatcher.dispatch(data);
	}
	
	protected void onBefore(Object[][] payload, Map<String, Object> md){
		String correlationId = (String) md.get(CORRELATION_ID);
		SequenceKey key = SequenceKey.key(correlationId);
		SequenceStack stack = correlationMap.get(key);
		if(stack == null){
			stack = new SequenceStack();
			correlationMap.put(new SequenceKey(correlationId, (long) md.get(START_TIME)), stack);
		}
		ProbeData data = new ProbeData();
		data.fromStream(payload, false);
		data.setDocSize((Integer)md.get(DOCSIZE));
		stack.push(data);
	}
	
	protected void onAfter(Object[][] payload, Map<String, Object> md){
		String correlationId = (String) md.get(CORRELATION_ID);
		SequenceKey key = SequenceKey.key(correlationId);
		SequenceStack stack = correlationMap.get(key);
		ProbeData answer = null;
		if(stack != null){
			answer = stack.correlate((Probe) md.get(PROBE_TYPE), md.get(INVOCATIONID), payload);
			if(stack.isEmpty()){
				correlationMap.remove(key);
				if(answer != null)
					answer.setLast(true);
			}
		}
		if(answer == null){
			logger.info("unable to correlate response for event : {}", md);
			answer = new ProbeData();
			answer.fromStream(payload, true);
			answer.setMerged(false);
		}
		answer.setDocSize(answer.getDocSize() + (Integer)md.get(DOCSIZE));
		dispatch(answer);
	}
	
	protected void prune(){
		final long expiryWindow = System.currentTimeMillis() - expiryTimeMs;
		Set<SequenceKey> purgedKeys = new HashSet<>();
		for(Map.Entry<SequenceKey, SequenceStack> e : this.correlationMap.entrySet()){
			if(e.getKey().timestamp < expiryWindow)
				purgedKeys.add(e.getKey());
		}
		if(!purgedKeys.isEmpty()){
			for(SequenceKey key : purgedKeys){
				SequenceStack stack = this.correlationMap.remove(key);
				logger.info("pruning : {}", stack);
				ProbeData last = stack.getStack().getLast();
				for(ProbeData data : stack.getStack()){
					data.setMerged(false);
					data.setLast(data == last);
					dispatch(data);
				}
			}
		}
	}
	
	protected void retrieveMDFields(Object[][] payload, Map<String, Object> md){
		for(int x = 0 ; x < payload.length && md.size() < 5; x++){
			Object[] e = payload[x];
			final String key = e[0].toString();
			switch(key){
				case CORRELATION_ID :
					md.put(CORRELATION_ID, e[1].toString());
					break;
				case PROBE_STATE :
					md.put(PROBE_STATE, ProbedState.valueOf(e[1].toString()));
					break;
				case INVOCATIONID:
					String val = e[1] == null ? "-" : e[1].toString();
					md.put(INVOCATIONID, val);
					break;
				case START_TIME:
					md.put(START_TIME, e[1]);
					break;	
				case PROBE_TYPE:
					md.put(PROBE_TYPE, Probe.valueOf(e[1].toString()));
					break;		
			}
		}
	}
	
	/**
	 * @return the dispatcher
	 */
	public Dispatcher<ProbeData, ?> getDispatcher() {
		return dispatcher;
	}

	/**
	 * @param dispatcher the dispatcher to set
	 */
	public void setDispatcher(Dispatcher<ProbeData, ?> dispatcher) {
		this.dispatcher = dispatcher;
	}

	/**
	 * @author A-2394
	 *
	 */
	private static class SequenceStack{
		
		private Deque<ProbeData> stack = new ArrayDeque<ProbeData>(5);
		
		public void push(ProbeData data){
			this.stack.push(data);
		}
		
		public boolean isEmpty(){
			return this.stack.isEmpty();
		}
		
		public ProbeData correlate(Probe probe, Object invocationId, Object[][] payload){
			ProbeData head = stack.peek();
			// TODO check NPE for prod use
			if(head.getProbe() == probe && head.getInvocationId().equals(invocationId)){
				stack.pop();
				head.fromStream(payload, false);
				head.setMerged(true);
				return head;
			}
			// we have missed some sequence either due to errors or due to unhandled exceptions
			ProbeData answer = null;
			for(ProbeData se : stack){
				if(se.getProbe() == probe && se.getInvocationId().equals(invocationId)){
					answer = se;
					answer.fromStream(payload, false);
					head.setMerged(true);
					break;
				}
			}
			if(answer != null)
				stack.remove(answer);
			return answer;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder sbul = new StringBuilder();
			sbul.append("stack Size : ").append(stack.size()).append("\n")
				.append(stack);
			return sbul.toString();
		}

		/**
		 * @return the stack
		 */
		public Deque<ProbeData> getStack() {
			return stack;
		}
		
		
	}
	
	/**
	 * @author A-2394
	 *
	 */
	private static class SequenceKey{
		/* (non-Javadoc)
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		static final ThreadLocal<SequenceKey> KEY_CACHE = ThreadLocal.withInitial(() -> new SequenceKey(null, 0L));
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

}
