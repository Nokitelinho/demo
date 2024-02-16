/*
 * PayloadHolder.java Created on 06-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			06-Jan-2016       		Jens J P 			First Draft
 */

import io.netty.buffer.ByteBuf;

/**
 * @author A-2394
 *
 */
public final class PayloadHolder {
	// a composite buffer implementation
	private byte[] buffer;
	private byte[] extra;
	private int size;
	private Object[][] data;
	private boolean errored;
	private int rawSize;
	private boolean isJson;
	
	public PayloadHolder(int preAllocateSize){
		this.buffer = new byte[preAllocateSize];
	}
	
	public void read(byte[] src, int length){
		this.size = length;
		if(length > buffer.length){
			int extraLength = length - buffer.length;
			this.extra = new byte[extraLength];
			System.arraycopy(src, 0, buffer, 0, buffer.length);
			System.arraycopy(src, buffer.length, this.extra, 0, extraLength);
		}else{
			System.arraycopy(src, 0, buffer, 0, length);
		}
	}

	public void readByteBuf(ByteBuf src){
		final int length = src.readableBytes();
		this.size = length;
		if(length > buffer.length){
			int extraLength = length - buffer.length;
			this.extra = new byte[extraLength];
			src.readBytes(this.buffer, 0, buffer.length);
			src.readBytes(this.extra, 0, extraLength);
		}else{
			src.readBytes(this.buffer, 0, length);
		}
	}
	
	public void write(byte[] dest){
		if(extra != null){
			int extraLength = this.size - this.buffer.length;
			System.arraycopy(this.buffer, 0, dest, 0, this.buffer.length);
			System.arraycopy(this.extra, 0, dest, this.buffer.length, extraLength);
		}else{
			System.arraycopy(this.buffer, 0, dest, 0, size);
		}
	}
	
	public void reset(){
		this.size = 0;
		this.extra = null;
		this.data = null;
		this.errored = false;
		this.rawSize = 0;
		this.isJson = false;
	}
	
	/**
	 * @return the data
	 */
	public Object[][] getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(Object[][] data) {
		this.data = data;
	}

	/**
	 * @return the errored
	 */
	public boolean isErrored() {
		return errored;
	}

	/**
	 * @param errored the errored to set
	 */
	public void setErrored(boolean errored) {
		this.errored = errored;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the buffer
	 */
	public byte[] getBuffer() {
		return buffer;
	}

	/**
	 * @return the extra
	 */
	public byte[] getExtra() {
		return extra;
	}

	/**
	 * @return the rawSize
	 */
	public int getRawSize() {
		return rawSize;
	}

	/**
	 * @param rawSize the rawSize to set
	 */
	public void setRawSize(int rawSize) {
		this.rawSize = rawSize;
	}

	public boolean isJson() {
		return isJson;
	}

	public void setJson(boolean json) {
		isJson = json;
	}
}
