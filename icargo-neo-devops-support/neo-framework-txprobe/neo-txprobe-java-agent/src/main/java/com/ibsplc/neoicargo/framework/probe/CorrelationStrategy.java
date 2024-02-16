/*
 * CorrelationStrategy.java Created on 31-Dec-2015
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
 * 1.0   			31-Dec-2015       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public interface CorrelationStrategy<T> {

	CorrelationStrategy<Void> GENERATE_STRATEGY = new GenerateNewCorrelationIdStrategy();
	
	String correlationId(T arg);
	
	
	/**
	 * @author A-2394
	 *
	 * @param <T>
	 */
    class GenerateWrapStrategy<T> implements CorrelationStrategy<T>{

		/* (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.probe.CorrelationStrategy#correlationId(java.lang.Object)
		 */
		@Override
		public String correlationId(T arg) {
			return GENERATE_STRATEGY.correlationId(null);
		}
		
	}
	
	/**
	 * @author A-2394
	 * The default generation strategy
	 */
	class GenerateNewCorrelationIdStrategy implements CorrelationStrategy<Void>{
		/* (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.probe.CorrelationStrategy#correlationId(java.lang.Object)
		 */
		@Override
		public String correlationId(Void arg) {
			return TxProbeFacade.generateCorrelationId();
		}
	}
	
}
