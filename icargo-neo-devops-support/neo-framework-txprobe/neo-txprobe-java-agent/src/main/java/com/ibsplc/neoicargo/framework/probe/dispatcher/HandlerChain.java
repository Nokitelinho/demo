/*
 * HandlerChain.java Created on 04-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.dispatcher;

import com.ibsplc.neoicargo.framework.probe.PayloadHolder;
import com.ibsplc.neoicargo.framework.probe.TxProbeConfig;
import com.ibsplc.neoicargo.framework.probe.dispatcher.handlers.NetworkClientHandler;
import com.ibsplc.neoicargo.framework.probe.dispatcher.handlers.ProbePayloadMarshallingHandler;
import com.ibsplc.neoicargo.framework.probe.dispatcher.handlers.StreamMarshallingHandler;
import com.ibsplc.neoicargo.framework.probe.dispatcher.handlers.TxProbeEventSequencingHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;

import java.util.EnumSet;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			04-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class HandlerChain {

	private static HandlingStateAwareEventHanlder<PayloadHolder>[] EAGER_HANDLER;
	private static HandlingStateAwareEventHanlder<PayloadHolder>[] ALL_HANDLERS;

	public static void invokeEagerHandlers(PayloadHolder event){
		for(int x = 0 ; x < EAGER_HANDLER.length; x++){
			try {
				EAGER_HANDLER[x].onEvent(event, 0L, true);
			} catch (Exception e) {
				// ignored
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings({"unchecked", "unused"})
	public static void configureHandlerChain(Disruptor<PayloadHolder> disruptor, TxProbeConfig config){
		HandlingStateAwareEventHanlder<PayloadHolder> payloadMarshaller = wrap(new ProbePayloadMarshallingHandler(config));
		HandlingStateAwareEventHanlder<PayloadHolder> sequencer = wrap(new TxProbeEventSequencingHandler());
		HandlingStateAwareEventHanlder<PayloadHolder> streamMarshaller = wrap(new StreamMarshallingHandler());
		HandlingStateAwareEventHanlder<PayloadHolder> networkSender = wrap(new NetworkClientHandler(config.getAggregationServerConfig()));
		
		EventHandlerGroup<PayloadHolder> group = disruptor.handleEventsWith(payloadMarshaller).then(sequencer)
				.then(streamMarshaller).then(networkSender);
		
		EAGER_HANDLER = new HandlingStateAwareEventHanlder[1];
		EAGER_HANDLER[0] = payloadMarshaller;
		ALL_HANDLERS = new HandlingStateAwareEventHanlder[4];
		ALL_HANDLERS[0] = payloadMarshaller;
		ALL_HANDLERS[1] = sequencer;
		ALL_HANDLERS[2] = streamMarshaller;
		ALL_HANDLERS[3] = networkSender;
	}
	
	@SuppressWarnings("unchecked")
	public static void stop(){
		for(HandlingStateAwareEventHanlder<?> h : ALL_HANDLERS)
			h.stop();
		// drop the reference
		EAGER_HANDLER = new HandlingStateAwareEventHanlder[0];
		ALL_HANDLERS = new HandlingStateAwareEventHanlder[0];
	}
	
	public static HandlingStateAwareEventHanlder<PayloadHolder> wrap(HandlingStateAwareEventHanlder<PayloadHolder> handler){
		return new ExceptionEventHandler(handler);
	}
	
	/**
	 * @author A-2394
	 *
	 */
	public static class ExceptionEventHandler implements HandlingStateAwareEventHanlder<PayloadHolder>{

		private final HandlingStateAwareEventHanlder<PayloadHolder> handler;
		private boolean stopped;
		
		public ExceptionEventHandler(HandlingStateAwareEventHanlder<PayloadHolder> handler){
			this.handler = handler;
		}
		
		/* (non-Javadoc)
		 * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
		 */
		@Override
		public void onEvent(PayloadHolder event, long sequence, boolean endOfBatch) throws Exception {
			if(stopped)
				return;
			EnumSet<HandlerState> handleState = event.getProcessState();
			if(handleState == null){
				handleState = EnumSet.noneOf(HandlerState.class); 
				event.setProcessState(handleState);
			}
			boolean canExecute = handleState.add(handler.handleState()) && !handleState.contains(HandlerState.REJECT);
			if(!canExecute)
				return;
			try {
				handler.onEvent(event, sequence, endOfBatch);
			} catch (Throwable e) {
				handleState.add(HandlerState.REJECT);
				e.printStackTrace();
			}
		}

		/* (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder#handleState()
		 */
		@Override
		public HandlerState handleState() {
			return this.handler.handleState();
		}

		/* (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder#stop()
		 */
		@Override
		public void stop() {
			this.stopped = true;
			this.handler.stop();
		}
		
		
	}
	
}
