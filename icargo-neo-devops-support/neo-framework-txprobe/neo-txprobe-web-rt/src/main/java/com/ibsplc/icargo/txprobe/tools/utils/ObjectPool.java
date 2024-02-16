/*
 * ObjectPool.java Created on 31-Mar-2014
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			31-Mar-2014       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 * 
 */
public abstract class ObjectPool<T> {

	private int maxSize;
	
	private BlockingQueue<T> pool;
	
	/**
	 * 
	 * @param poolMaxSize - max size of the pool
	 * @param growable - can create new instance in case maxSize of objects used
	 *                   if false the thread will wait for a new object to return to pool
	 * @param enableGCCollect
	 *  				- the references are not hard but soft so that they will be GCed in case of 
	 *  				  low heap  
	 */
	public ObjectPool(int poolMaxSize){
		this.maxSize = poolMaxSize;
		this.pool = new ArrayBlockingQueue<T>(this.maxSize);
	}
	
	/**
	 * DefaultConstructor 
	 * pool size of 10
	 */
	public ObjectPool(){
		this(10);
	}
	
	public T getFromPool(){
		T t = this.pool.poll();
		if(t == null)
			t = createNew();
		return t;
	}
	
	public void returnToPool(T t){
		if(!this.pool.offer(t)){
			onOverflow(t);
		}
	}
	
	/**
	 * <ethod to create a new object to be added to the pool.
	 * @return
	 */
	public abstract T createNew();
	
	/**
	 * Callback method called the pool is full
	 * @param t
	 */
	protected void onOverflow(T t){
		// NOOP
	}
	
	/**
	 * Method to execute on the pooled object.
	 * @param callback - the execution impl which needs to be performed on the object. 
	 * @throws Exception 
	 */
	public <K> K execute(ObjectPoolExecutionCallback<T, K> callback) throws Exception{
		T t = getFromPool();
		try {
			return callback.execute(t);
		} finally{
			returnToPool(t);
		}
	}
	
	/**
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @author A-2394
	 *
	 * @param <T>
	 */
	public static interface ObjectPoolExecutionCallback<T, K> {
		
		/**
		 * Method to execute on the pooled object reference.
		 * @param t the pooled object 
		 */
		public K execute(T t) throws Exception;
	}
}
