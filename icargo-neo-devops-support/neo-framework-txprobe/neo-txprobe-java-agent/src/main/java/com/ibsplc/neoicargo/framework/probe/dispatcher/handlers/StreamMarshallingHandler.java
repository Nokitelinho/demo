/*
 * StreamMarshallingHandler.java Created on 30-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.dispatcher.handlers;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.neoicargo.framework.probe.PayloadHolder;
import com.ibsplc.neoicargo.framework.probe.dispatcher.HandlerState;
import com.ibsplc.neoicargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder;
import org.iq80.snappy.Snappy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.EnumMap;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			30-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 * @NotThreadsafe
 */
public class StreamMarshallingHandler implements HandlingStateAwareEventHanlder<PayloadHolder>{

	static final Logger logger = LoggerFactory.getLogger(StreamMarshallingHandler.class);
	
	static final int BUFF_SIZE_MAX = 1024 * 1024 * 5; // 5Mib
	
	// reusable buffers 
	private FasterByteArrayOutputStream objectBuffer = new FasterByteArrayOutputStream(BUFF_SIZE_MAX);
	private FasterByteArrayOutputStream compressBufferCached = new FasterByteArrayOutputStream(BUFF_SIZE_MAX);
	
	private EnumMap<Probe, Serializable[][]> dupletArrayCache = new EnumMap<>(Probe.class);
	
	/* (non-Javadoc)
	 * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
	 */
	@Override
	public void onEvent(PayloadHolder event, long sequence, boolean endOfBatch) throws Exception {
		int size = event.getPayload().fieldCount();
		Serializable[][] dupletMap = dupletArray(event.getProbe(), size);
		event.getPayload().writeTo(dupletMap);
		compactSerialize(event, dupletMap);
		resetArray(dupletMap);
		event.setPayload(null);
	}
	
	private Serializable[][] dupletArray(Probe probe, int rowSize){
		Serializable[][] array = dupletArrayCache.get(probe);
		if(array == null){
			array = new Serializable[rowSize][2];
			dupletArrayCache.put(probe, array);
		}else
			resetArray(array);
		return array;
	}
	
	private static void resetArray(Serializable[][] array){
		final int len = array.length;
		for(int x = 0; x < len; x++){
			Serializable[] col = array[x];
			col[0] = null;
			col[1] = null;
		}
	}
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder#handleState()
	 */
	@Override
	public HandlerState handleState() {
		return HandlerState.MARSHALL_WIRE;
	}

	protected void compactSerialize(PayloadHolder event, Serializable[][] dupletMap) throws IOException{
		FasterByteArrayOutputStream compressBuffer = this.compressBufferCached;
		objectBuffer.reset();
		compressBuffer.reset(); 
		ObjectOutputStream oos = new ObjectOutputStream(objectBuffer);
		oos.writeUnshared(dupletMap);
		oos.flush();
		oos.close();
		final int rawSize = objectBuffer.size();
		// ensure that we have enough buffer capacity
		final int maxCompressedSize = Snappy.maxCompressedLength(rawSize);
		if(compressBuffer.toByteArray().length < maxCompressedSize){
			logger.warn("compress buffer resized to : ", maxCompressedSize);
			compressBuffer = new FasterByteArrayOutputStream(maxCompressedSize);
		}
		final int compressedSize = Snappy.compress(objectBuffer.toByteArray(), 0, rawSize, compressBuffer.toByteArray(), 0);
		event.read(compressBuffer.toByteArray(), compressedSize);
		// shrink our buffer if it exceeds the initial allocation
		if(objectBuffer.toByteArray().length > BUFF_SIZE_MAX)
			this.objectBuffer = new FasterByteArrayOutputStream(BUFF_SIZE_MAX);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.dispatcher.HandlingStateAwareEventHanlder#stop()
	 */
	@Override
	public void stop() {
		
	}

}
