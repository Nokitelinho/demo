/*
 * PayloadHolder.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbedState;
import io.netty.buffer.ByteBuf;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class PayloadHolder {

	private Probe probe;
	private ProbedState probeState;
	private ProbePayload payload;
	private Object processState;
	// composite buffer implementation
	private byte[] compressed;
	private byte[] extra;
	private int compressedSize;
	
	private Object[] probeData;
	
	public PayloadHolder(){
		
	}
	
	public PayloadHolder(int allocateSize){
		this.compressed = new byte[allocateSize];
	}
	
	public void reset(int allocateSize){
		this.probe = null;
		this.probeState = null;
		this.payload = null;
		this.compressedSize = 0;
		this.probeData = null;
		this.processState = null;
		this.extra = null;
	}
	
	public void read(byte[] src, int length){
		this.compressedSize = length;
		if(length > this.compressed.length){
			int extraSize = length - this.compressed.length;
			this.extra = new byte[extraSize];
			System.arraycopy(src, 0, this.compressed, 0, this.compressed.length);
			System.arraycopy(src, this.compressed.length, this.extra, 0, extraSize);
		}else{
			System.arraycopy(src, 0, this.compressed, 0, length);
		}
	}
	
	public void write(ByteBuf dest){
		if(extra != null){
			int extraSize = this.compressedSize - this.compressed.length;
			dest.writeBytes(this.compressed, 0, this.compressed.length);
			dest.writeBytes(this.extra, 0, extraSize);
		}else{
			dest.writeBytes(this.compressed, 0, compressedSize);
		}
	}
	
	/**
	 * @return the probe
	 */
	public Probe getProbe() {
		return probe;
	}
	
	/**
	 * @param probe the probe to set
	 */
	public void setProbe(Probe probe) {
		this.probe = probe;
	}
	
	/**
	 * @return the probeState
	 */
	public ProbedState getProbeState() {
		return probeState;
	}
	
	/**
	 * @param probeState the probeState to set
	 */
	public void setProbeState(ProbedState probeState) {
		this.probeState = probeState;
	}
	
	/**
	 * @return the payload
	 */
	public ProbePayload getPayload() {
		return payload;
	}
	
	/**
	 * @param payload the payload to set
	 */
	public void setPayload(ProbePayload payload) {
		this.payload = payload;
	}
	
	/**
	 * @return the compressed
	 */
	public byte[] getCompressed() {
		return compressed;
	}
	
	/**
	 * @return the probeData
	 */
	public Object[] getProbeData() {
		return probeData;
	}
	
	/**
	 * @param probeData the probeData to set
	 */
	public void setProbeData(Object[] probeData) {
		this.probeData = probeData;
	}

	/**
	 * @return the processState
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProcessState() {
		return (T)processState;
	}

	/**
	 * @param processState the processState to set
	 */
	public void setProcessState(Object processState) {
		this.processState = processState;
	}

	/**
	 * @return the compressedSize
	 */
	public int getCompressedSize() {
		return compressedSize;
	}

	/**
	 * @param compressedSize the compressedSize to set
	 */
	public void setCompressedSize(int compressedSize) {
		this.compressedSize = compressedSize;
	}

	/**
	 * @return the extra
	 */
	public byte[] getExtra() {
		return extra;
	}
	
}
