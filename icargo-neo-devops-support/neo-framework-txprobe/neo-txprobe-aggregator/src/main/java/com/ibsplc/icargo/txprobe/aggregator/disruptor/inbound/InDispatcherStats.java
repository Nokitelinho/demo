/*
 * InDispatcherStats.java Created on 18-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.disruptor.inbound;

import com.ibsplc.icargo.txprobe.aggregator.utils.Utils;
import com.lmax.disruptor.RingBuffer;

import javax.inject.Singleton;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			18-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@Singleton
//@ManagedResource(objectName = "com.ibsplc.icargo.txprobe:name=inDispatcherStats,type=DispatcherStats", description = "Inbound dispatcher Stats")
public class InDispatcherStats {

	private long totalMessagesReceived;
	private long totalBytesReceived;
	private int maxMessageSize;
	private int maxBufferDepth;
	private int bufferSize;
	
	private RingBuffer<?> ringBuffer;
	
	public void setRingBuffer(RingBuffer<?> ringBuffer){
		this.ringBuffer = ringBuffer;
		this.bufferSize = ringBuffer.getBufferSize();
	}
	
	public void onMessage(int size){
		totalMessagesReceived++;
		maxMessageSize = Math.max(maxMessageSize, size);
		totalBytesReceived += size; 
	}
	
	/**
	 * @return the totalMessagesReceived
	 */
	//@ManagedAttribute(description = "Total number of messages received")
	public long getTotalMessagesReceived() {
		return totalMessagesReceived;
	}
	
	/**
	 * @return the maxMessageSize
	 */
	//@ManagedAttribute(description = "Biggest messages received")
	public int getMaxMessageSize() {
		return maxMessageSize;
	}
	
	/**
	 * @return the averageMessageSize
	 */
	//@ManagedAttribute(description = "Average size of messages")
	public int getAverageMessageSize() {
		if(this.totalMessagesReceived == 0)
			return 0;
		return (int)(this.totalBytesReceived / this.totalMessagesReceived);
	}
	
	/**
	 * @return the maxBufferDepth
	 */
	//@ManagedAttribute(description = "Buffer maximum filled")
	public int getMaxBufferDepth() {
		return maxBufferDepth;
	}
	
	/**
	 * @return the currentBufferDepth
	 */
	//@ManagedAttribute(defaultValue = "0", description = "Buffer current depth")
	public int getCurrentBufferDepth() {
		return bufferSize - (int)this.ringBuffer.remainingCapacity();
	}

	/**
	 * @return the bufferDepth
	 */
	//@ManagedAttribute(description = "Buffer size")
	public int getBufferSize() {
		return bufferSize;
	}

	/**
	 * @return the totalBytesReceived
	 */
	//@ManagedAttribute(description = "Total bytes received")
	public String getTotalBytesReceived() {
		return Utils.formatBytes(this.totalBytesReceived, false);
	}
	
}
