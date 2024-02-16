/*
 * AggregationServerConfig.java Created on 05-Jan-2016
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
 * 1.0   			05-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public class AggregationServerConfig{

	private boolean tcpNoDelay = true;
	private boolean tcpKeepAlive = true;
	private boolean tcpCork = true;
	private long reconnectInterval = 2000;
	private int workerCount = 2;
	private String address;
	private boolean sendAsync;
	private int flushSize = 1000;
	private String channelType = "auto";
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the tcpNoDelay
	 */
	public boolean isTcpNoDelay() {
		return tcpNoDelay;
	}

	/**
	 * @param tcpNoDelay the tcpNoDelay to set
	 */
	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}

	/**
	 * @return the tcpKeepAlive
	 */
	public boolean isTcpKeepAlive() {
		return tcpKeepAlive;
	}

	/**
	 * @param tcpKeepAlive the tcpKeepAlive to set
	 */
	public void setTcpKeepAlive(boolean tcpKeepAlive) {
		this.tcpKeepAlive = tcpKeepAlive;
	}

	/**
	 * @return the reconnectInterval
	 */
	public long getReconnectInterval() {
		return reconnectInterval;
	}

	/**
	 * @param reconnectInterval the reconnectInterval to set
	 */
	public void setReconnectInterval(long reconnectInterval) {
		this.reconnectInterval = reconnectInterval;
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
	 * @return the sendAsync
	 */
	public boolean isSendAsync() {
		return sendAsync;
	}

	/**
	 * @param sendAsync the sendAsync to set
	 */
	public void setSendAsync(boolean sendAsync) {
		this.sendAsync = sendAsync;
	}

	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}

	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * @return the tcpCork
	 */
	public boolean isTcpCork() {
		return tcpCork;
	}

	/**
	 * @param tcpCork the tcpCork to set
	 */
	public void setTcpCork(boolean tcpCork) {
		this.tcpCork = tcpCork;
	}
	/**
	 * @return the flushSize
	 */
	public int getFlushSize() {
		return flushSize;
	}
	/**
	 * @param flushSize the flushSize to set
	 */
	public void setFlushSize(int flushSize) {
		this.flushSize = flushSize;
	}
	
}
