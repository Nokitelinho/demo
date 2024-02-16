/*
 * RingBufferDispatcher.java Created on 06-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.disruptor.outbound;

import com.ibsplc.icargo.txprobe.aggregator.Dispatcher;
import com.ibsplc.icargo.txprobe.aggregator.handler.outbound.BackEndHandler;
import com.ibsplc.icargo.txprobe.aggregator.handler.outbound.ExceptionHandlerWrapper;
import com.ibsplc.icargo.txprobe.api.ProbeData;
import com.ibsplc.icargo.txprobe.api.ProbeDataHolder;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Instance;
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
 */
@Singleton
@Named("dispatcher.out")
public class OutboundRingBufferDispatcher implements Dispatcher<ProbeData, Object>, EventFactory<ProbeDataHolder>,
        EventTranslatorOneArg<ProbeDataHolder, ProbeData>, ThreadFactory {

    static final Logger logger = LoggerFactory.getLogger(OutboundRingBufferDispatcher.class);

    @ConfigProperty(name = "out.ringBuffer.maxQueueDepth")
    int maxQueueDepth;

    @ConfigProperty(name = "out.ringBuffer.blockOnFull")
    boolean blockOnFull;

    @Inject
    @Named("out.loggingEventHandler")
    EventHandler<ProbeDataHolder> loggingHandler;

    @Inject
    Instance<BackEndHandler> backEndHandlers;

    private Disruptor<ProbeDataHolder> disruptor;
    private RingBuffer<ProbeDataHolder> ringBuffer;
    private int threadSequence;
    private int numberOfHandlers;

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @PostConstruct
    public void afterPropertiesSet() {
        logger.info("initializing outbound ring buffer queueDepth : {}" , maxQueueDepth);
        final int backendHandlerSize = (int)this.backEndHandlers.stream().count();
        this.numberOfHandlers = 1 + backendHandlerSize;
        this.disruptor = new Disruptor<>(this, maxQueueDepth, this, ProducerType.SINGLE, new BlockingWaitStrategy());
        EventHandlerGroup<ProbeDataHolder> handlerGroup = this.disruptor.handleEventsWith(new ExceptionHandlerWrapper(loggingHandler));
        if (backendHandlerSize == 0) {
            logger.warn("no backend handlers activated !");
        } else {
            for (BackEndHandler beh : backEndHandlers) {
                logger.info("configuring backend handler : {}", beh);
                handlerGroup.handleEventsWith(new ExceptionHandlerWrapper(beh));
            }
        }
        this.ringBuffer = disruptor.start();
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @PreDestroy
    public void destroy() {
        this.disruptor.shutdown();
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.Dispatcher#dispatch(byte[])
     */
    @Override
    public boolean dispatch(ProbeData in) {
        boolean enqued = true;
        if (blockOnFull)
            this.ringBuffer.publishEvent(this, in);
        else {
            enqued = this.ringBuffer.tryPublishEvent(this, in);
            if (!enqued)
                logger.error("outgoing message discarded due to ring buffer overflow");
        }
        return enqued;
    }

    @Override
    public boolean dispatchJson(Object payload) {
        throw new UnsupportedOperationException("Outbound dispatcher does not support dispatchJson operation");
    }

    /* (non-Javadoc)
     * @see com.lmax.disruptor.EventTranslatorOneArg#translateTo(java.lang.Object, long, java.lang.Object)
     */
    @Override
    public void translateTo(ProbeDataHolder event, long sequence, ProbeData in) {
        event.setProbeData(in);
    }

    /* (non-Javadoc)
     * @see com.lmax.disruptor.EventFactory#newInstance()
     */
    @Override
    public ProbeDataHolder newInstance() {
        return new ProbeDataHolder((short) this.numberOfHandlers);
    }

    /* (non-Javadoc)
     * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
     */
    @Override
    public Thread newThread(Runnable r) {
        var t = new Thread(r);
        threadSequence++;
        t.setName("ring-buffer-outbound-worker-" + threadSequence);
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

}
