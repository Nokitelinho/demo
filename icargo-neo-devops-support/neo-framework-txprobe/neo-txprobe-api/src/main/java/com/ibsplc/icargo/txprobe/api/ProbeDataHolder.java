/*
 * ProbeDataHolder.java Created on 29-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.api;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public class ProbeDataHolder {

	private ProbeData probeData;
	private boolean isErrored;
	private short pendingHandlers;
	private short totalHandlers;
	
	public ProbeDataHolder(short totalHandlers) {
		super();
		this.totalHandlers = totalHandlers;
	}

	/**
	 * @return the probeData
	 */
	public ProbeData getProbeData() {
		return probeData;
	}

	/**
	 * @param probeData the probeData to set
	 */
	public void setProbeData(ProbeData probeData) {
		this.pendingHandlers = this.totalHandlers;
		this.isErrored = false;
		this.probeData = probeData;
	}

	/**
	 * @return the isErrored
	 */
	public boolean isErrored() {
		return isErrored;
	}

	/**
	 * @param isErrored the isErrored to set
	 */
	public void setErrored(boolean isErrored) {
		this.isErrored = isErrored;
	}
	
	public void decrementPendingHandler(){
		--pendingHandlers;
		if(pendingHandlers <= 0)
			this.probeData = null;
	}
}
