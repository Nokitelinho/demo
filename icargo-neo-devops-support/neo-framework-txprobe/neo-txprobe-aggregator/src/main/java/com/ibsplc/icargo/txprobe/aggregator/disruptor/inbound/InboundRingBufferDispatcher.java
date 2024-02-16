/*
 * RingBufferDispatcher.java Created on 06-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.disruptor.inbound;

import com.ibsplc.icargo.txprobe.aggregator.Dispatcher;
import com.ibsplc.icargo.txprobe.aggregator.PayloadHolder;
import com.ibsplc.icargo.txprobe.aggregator.handler.inbound.ExceptionHandlerWrapper;
import com.ibsplc.icargo.txprobe.aggregator.utils.FasterByteArrayOutputStream;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.netty.buffer.ByteBuf;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.ThreadFactory;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			06-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@Singleton
@Named("dispatcher.in")
public class InboundRingBufferDispatcher implements Dispatcher<FasterByteArrayOutputStream, ByteBuf>, EventFactory<PayloadHolder>,
	EventTranslatorOneArg<PayloadHolder, FasterByteArrayOutputStream>, ThreadFactory{

	static final Logger logger = LoggerFactory.getLogger(InboundRingBufferDispatcher.class);
	
	@ConfigProperty(name = "in.ringBuffer.preAllocateSize")
	int preAllocateSize;

	@ConfigProperty(name = "in.ringBuffer.maxQueueDepth")
	int maxQueueDepth;

	@ConfigProperty(name = "in.ringBuffer.workerCount")
	int workerCount = -1;

	@ConfigProperty(name = "in.ringBuffer.blockOnFull", defaultValue = "true")
	boolean blockOnFull;
	
	@Inject
	InDispatcherStats dispatcherStats;

	@Inject
	@Named("in.payloadUnmarshaller")
	EventHandler<PayloadHolder> payloadUnmarshaller;

	@Inject
	@Named("in.loggingEventHandler")
	EventHandler<PayloadHolder> loggingHandler;
	
	@Inject
	@Named("in.requestResponseCorrelator")
	EventHandler<PayloadHolder> requestResponseCorrelator;
	
	private Disruptor<PayloadHolder> disruptor;
	private RingBuffer<PayloadHolder> ringBuffer;
	private int threadSequence;
	static final JsonEventPublisher JSON_EVENT_PUBLISHER = new JsonEventPublisher();
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@PostConstruct
	public void afterPropertiesSet(){
		logger.info("initializing inbound ring buffer queueDepth : {}", maxQueueDepth);
		this.disruptor = new Disruptor<>(this, maxQueueDepth, this, ProducerType.MULTI, new BlockingWaitStrategy());
		this.disruptor.handleEventsWith(new ExceptionHandlerWrapper(payloadUnmarshaller))
			.then(new ExceptionHandlerWrapper(loggingHandler))
			.then(new ExceptionHandlerWrapper(requestResponseCorrelator)).then(new ResourceFreeHandler());
		this.ringBuffer = disruptor.start();
		this.dispatcherStats.setRingBuffer(this.ringBuffer);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@PreDestroy
	public void destroy(){
		this.disruptor.shutdown();
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.txprobe.aggregator.Dispatcher#dispatch(byte[])
	 */
	@Override
	public boolean dispatch(FasterByteArrayOutputStream in) {
		var enqued = true;
		this.dispatcherStats.onMessage(in.size());
		if(blockOnFull)
			this.ringBuffer.publishEvent(this, in);
		else{
			enqued = this.ringBuffer.tryPublishEvent(this, in);
			if(!enqued)
				logger.error("incoming message discarded due to ring buffer overflow");
		}
		return enqued;
	}

	@Override
	public boolean dispatchJson(ByteBuf in) {
		boolean enqued = true;
		int size = in.readableBytes();
		this.dispatcherStats.onMessage(size);
		if(blockOnFull)
			this.ringBuffer.publishEvent(JSON_EVENT_PUBLISHER, in);
		else{
			enqued = this.ringBuffer.tryPublishEvent(JSON_EVENT_PUBLISHER, in);
			if(!enqued)
				logger.error("incoming message discarded due to ring buffer overflow");
		}
		return enqued;
	}

	static final class JsonEventPublisher implements EventTranslatorOneArg<PayloadHolder, ByteBuf> {

		@Override
		public void translateTo(PayloadHolder event, long sequence, ByteBuf in) {
			event.reset();
			event.setJson(true);
			event.readByteBuf(in);
		}
	}

	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventTranslatorOneArg#translateTo(java.lang.Object, long, java.lang.Object)
	 */
	@Override
	public void translateTo(PayloadHolder event, long sequence, FasterByteArrayOutputStream in) {
		event.reset();
		event.read(in.byteArray(), in.size());
	}

	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventFactory#newInstance()
	 */
	@Override
	public PayloadHolder newInstance() {
		return new PayloadHolder(this.preAllocateSize);
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 */
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		threadSequence++;
		t.setName("ring-buffer-inbound-worker-" + threadSequence);
		return t;
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
	 * @return the workerCount
	 */
	public int getWorkerCount() {
		return workerCount;
	}

	/**
	 * @param workerCount the workerCount to set
	 */
	public void setWorkerCount(int workerCount) {
		this.workerCount = workerCount;
	}

	/**
	 * @return the blockOnFull
	 */
	public boolean isBlockOnFull() {
		return blockOnFull;
	}

	/**
	 * @param blockOnFull the blockOnFull to set
	 */
	public void setBlockOnFull(boolean blockOnFull) {
		this.blockOnFull = blockOnFull;
	}

	/**
	 * @return the preAllocateSize
	 */
	public int getPreAllocateSize() {
		return preAllocateSize;
	}

	/**
	 * @param preAllocateSize the preAllocateSize to set
	 */
	public void setPreAllocateSize(int preAllocateSize) {
		this.preAllocateSize = preAllocateSize;
	}

	/**
	 * @author A-2394
	 * An end handler which eagerly releases the object handle for GC.
	 */
	private static final class ResourceFreeHandler implements EventHandler<PayloadHolder>{

		/* (non-Javadoc)
		 * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
		 */
		@Override
		public void onEvent(PayloadHolder event, long sequence, boolean endOfBatch) throws Exception {
			event.reset();
		}
	}
	
	
}
