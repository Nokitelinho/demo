/*
 * RingBufferDispatcher.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.dispatcher;

import com.ibsplc.neoicargo.framework.probe.Dispatcher;
import com.ibsplc.neoicargo.framework.probe.ExecutorServiceProvider;
import com.ibsplc.neoicargo.framework.probe.PayloadHolder;
import com.ibsplc.neoicargo.framework.probe.TxProbeConfig;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class RingBufferDispatcher implements Dispatcher, EventFactory<PayloadHolder>, EventTranslatorOneArg<PayloadHolder, PayloadHolder>{

	static final Logger logger = LoggerFactory.getLogger(RingBufferDispatcher.class);
	
	private volatile boolean hasStopped = true;
	private int maxQueueDepth = 4096;
	private int preAllocateBufferSize = 8196;
	private Disruptor<PayloadHolder> disruptor;
	private RingBuffer<PayloadHolder> ringBuffer;
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.Dispatcher#dispatch(com.ibsplc.icargo.framework.probe.PayloadHolder)
	 */
	@Override
	public boolean dispatch(PayloadHolder holder) {
		if(this.hasStopped){
			logger.warn("Ring buffer has stopped accepting new requests");
			return false;
		}
		boolean answer = this.ringBuffer.tryPublishEvent(this, holder);
		return answer;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.Dispatcher#dispatchEager(com.ibsplc.icargo.framework.probe.PayloadHolder)
	 */
	@Override
	public boolean dispatchEager(PayloadHolder holder) {
		if(this.hasStopped){
			logger.warn("Ring buffer has stopped accepting new requests");
			return false;
		}
		HandlerChain.invokeEagerHandlers(holder);
		boolean answer = this.ringBuffer.tryPublishEvent(this, holder);
		return answer;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.Dispatcher#start()
	 */
	@Override
	public void start(TxProbeConfig config) {
		if(this.hasStopped) {
			this.disruptor = new Disruptor<>(this, getMaxQueueDepth(), new ExecutorServiceProvider.JDKThreadFactory("neo-txprobe"), ProducerType.MULTI, new BlockingWaitStrategy());
			HandlerChain.configureHandlerChain(disruptor, config);
			this.ringBuffer = disruptor.start();
			this.hasStopped = false;
		}
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.Dispatcher#stop()
	 */
	@Override
	public void stop() {
		if(this.hasStopped)
			return;
		this.hasStopped = true;
		try {
			this.disruptor.shutdown(10, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		this.disruptor.halt();
		HandlerChain.stop();
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.Dispatcher#isReady()
	 */
	@Override
	public boolean isReady() {
		return (this.ringBuffer != null && this.ringBuffer.remainingCapacity() > 0);
	}

	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventFactory#newInstance()
	 */
	@Override
	public PayloadHolder newInstance() {
		return new PayloadHolder(this.preAllocateBufferSize);
	}
	
	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventTranslatorOneArg#translateTo(java.lang.Object, long, java.lang.Object)
	 */
	@Override
	public void translateTo(PayloadHolder event, long sequence, PayloadHolder dispatched) {
		event.reset(this.preAllocateBufferSize);
		event.setPayload(dispatched.getPayload());
		event.setProbe(dispatched.getProbe());
		event.setProbeData(dispatched.getProbeData());
		event.setProbeState(dispatched.getProbeState());
		event.setProcessState(dispatched.getProcessState());
	}

	/**
	 * @return the maxQueueDepth
	 */
	public int getMaxQueueDepth() {
		return maxQueueDepth;
	}

	/**
	 * @param maxQueueDepth the maxQueueDepth to set
	 */
	public void setMaxQueueDepth(int maxQueueDepth) {
		this.maxQueueDepth = maxQueueDepth;
	}

	/**
	 * @return the disruptor
	 */
	public Disruptor<PayloadHolder> getDisruptor() {
		return disruptor;
	}

	/**
	 * @return the preAllocateBufferSize
	 */
	public int getPreAllocateBufferSize() {
		return preAllocateBufferSize;
	}

	/**
	 * @param preAllocateBufferSize the preAllocateBufferSize to set
	 */
	public void setPreAllocateBufferSize(int preAllocateBufferSize) {
		this.preAllocateBufferSize = preAllocateBufferSize;
	}
	
	
	
}
