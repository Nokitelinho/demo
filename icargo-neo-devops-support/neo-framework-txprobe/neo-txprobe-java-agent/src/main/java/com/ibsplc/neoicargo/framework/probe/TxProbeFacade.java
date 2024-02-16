/*
 * TxProbeFacade.java Created on 28-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe;



/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			28-Dec-2015       		Jens J P 			First Draft
 */

import com.ibsplc.icargo.txprobe.api.Probe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author A-2394
 *
 */
public class TxProbeFacade{

	static final Logger logger = LoggerFactory.getLogger(TxProbeFacade.class);

	public enum ProbeMode {
		LAZY,
		EAGER,
		FORCED
	}
	
	private final TxProbeProcessor processor;

	/**
	 *
	 * @param processor
	 */
	public TxProbeFacade(TxProbeProcessor processor){
		 this.processor = processor;
	}
	
	public boolean doEagerProbe(ProbePayload payload, Object... probeData){
		TxProbeProcessor delegate = this.processor;
		if(delegate == null){
			return false;
		}
		try{
			return delegate.doDispatchEager(payload, probeData);
		}catch(Throwable t){
			logger.error("Error performing doEagerProbe", t);
			return false;
		}
	}
	
	public boolean doEagerProbeForced(ProbePayload payload, Object... probeData){
		TxProbeProcessor delegate = this.processor;
		if(delegate == null){
			return false;
		}
		try{
			return delegate.doDispatchEagerForced(payload, probeData);
		}catch(Throwable t){
			logger.error("Error performing doEagerProbeForced", t);
			return false;
		}
	}
	
	public boolean doProbe(ProbePayload payload, Object... probeData){
		TxProbeProcessor delegate = this.processor;
		if(delegate == null){
			return false;
		}
		try{
			return delegate.doDispatch(payload, probeData);
		}catch(Throwable t){
			logger.error("Error performing doProbe", t);
			return false;
		}
	}
	
	public boolean doProbeForced(ProbePayload payload, Object... probeData){
		TxProbeProcessor delegate = this.processor;
		if(delegate == null){
			return false;
		}
		try{
			return delegate.doDispatchForced(payload, probeData);
		}catch(Throwable t){
			logger.error("Error performing doProbeForced", t);
			return false;
		}
	}
	
	public boolean isProbeEnabled(Probe probe){
		TxProbeProcessor delegate = this.processor;
		if(delegate == null){
			return false;
		}
		return delegate.isProbeEnabled(probe);
	}
	
	public static String generateCorrelationId(){
		return TxProbeProcessor.generateUniqueId();
	}
	
	synchronized TxProbeConfig config(){
		if(this.processor == null)
			return null;
		return this.processor.getConfig();
	}

}
