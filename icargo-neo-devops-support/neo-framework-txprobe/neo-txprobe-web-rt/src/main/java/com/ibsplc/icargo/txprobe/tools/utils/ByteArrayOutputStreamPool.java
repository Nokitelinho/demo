/*
 * ByteArrayInputStreamPool.java Created on 14-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.utils;

import org.slf4j.LoggerFactory;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			14-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public class ByteArrayOutputStreamPool extends ObjectPool<FasterByteArrayOutputStream>{

	public static final ByteArrayOutputStreamPool IOBUFFER_POOL = new ByteArrayOutputStreamPool(16);
	
	private static final int PREALLOCATE_SIZE = 1024 * 1024 * 15; // 15 Mib
	
	/**
	 * Default constructor
	 * @param poolMaxSize
	 */
	public ByteArrayOutputStreamPool(int poolMaxSize) {
		super(poolMaxSize);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.txprobe.aggregator.utils.ObjectPool#createNew()
	 */
	@Override
	public FasterByteArrayOutputStream createNew() {
		return new FasterByteArrayOutputStream(PREALLOCATE_SIZE);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.txprobe.aggregator.utils.ObjectPool#onOverflow(java.lang.Object)
	 */
	@Override
	protected void onOverflow(FasterByteArrayOutputStream t) {
		LoggerFactory.getLogger(ByteArrayOutputStreamPool.class).warn("overflow in ByteArrayOutputStreamPool current pool size : " + super.getMaxSize());
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.txprobe.aggregator.utils.ObjectPool#returnToPool(java.lang.Object)
	 */
	@Override
	public void returnToPool(FasterByteArrayOutputStream t) {
		// if the size exceeds the allocation then drop it and create new
		if(t.byteArray().length > PREALLOCATE_SIZE)
			t = createNew();
		super.returnToPool(t);
	}


}
