/*
 * ExecutorServiceProvider.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 * Provider class which provides a dedicated thread pool.
 */
public class ExecutorServiceProvider {

	/**
	 * Provides a dedicated thread pool for dispatcher
	 * @param name
	 * @param maxPoolSize
	 * @return
	 */
	public static Executor resolveExecutor(String name, int maxPoolSize){
		//TODO Container integration 
		Executor executor = Executors.newCachedThreadPool(new JDKThreadFactory(name));
		return executor;
	}
	
	/**
	 * @author A-2394
	 *
	 */
	public static final class JDKThreadFactory implements ThreadFactory{

		private String name;
		private int count;
		
		public JDKThreadFactory(String name) {
			this.name = name;
		}

		/* (non-Javadoc)
		 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
		 */
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new ProbeThread(r);
			StringBuilder sbul = new StringBuilder();
			sbul.append(this.name).append('(').append(++count)
				.append(')');
			t.setName(sbul.toString());
			t.setUncaughtExceptionHandler(new VerboseUCExceptionHandler());
			return t;
		}
		
	}
	
	/**
	 * @author A-2394
	 * A <code>Thread</code> impl which holds reusable objects.
	 */
	public static class ProbeThread extends Thread {
		static final int STRBUL_SIZE = 100_000;// 100 thousand
		static final int MAP_SIZE = 64;
		
		private StringBuilder stringBuilder;
		private HashMap<String, Object> map;
		
		public ProbeThread(Runnable target) {
			super(target);
		}

		public StringBuilder stringBuilder(){
			if(this.stringBuilder == null)
				this.stringBuilder = new StringBuilder(STRBUL_SIZE);
			else{
				if(this.stringBuilder.capacity() > STRBUL_SIZE)
					this.stringBuilder = new StringBuilder(STRBUL_SIZE);
				else
					this.stringBuilder.setLength(0);
			}
			return this.stringBuilder;
		}
		
		public Map<String, Object> hashMap(){
			if(this.map == null)
				this.map = new HashMap<>(MAP_SIZE);
			else{
				if(this.map.size() > MAP_SIZE)
					this.map = new HashMap<>(MAP_SIZE);
				else
					this.map.clear();
			}
			return this.map;
		}
	}
	
	/**
	 * @author A-2394
	 *
	 */
	private static final class VerboseUCExceptionHandler implements Thread.UncaughtExceptionHandler{
		
		/* (non-Javadoc)
		 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
		 */
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			shoutOut(t, e);
			// restart the dispatcher ??
		}
		
		private void shoutOut(Thread t, Throwable e){
			String error = TxProbeUtils.renderException(e);
			System.err.println(error);
                        System.err.println(t);
		}
		
	}
	
}
