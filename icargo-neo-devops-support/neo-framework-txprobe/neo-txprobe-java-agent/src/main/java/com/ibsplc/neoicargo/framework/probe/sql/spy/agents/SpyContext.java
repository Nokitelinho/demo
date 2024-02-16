/*
 * SpyContext.java Created on 17-Oct-2018
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.sql.spy.agents;
/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			17-Oct-2018       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public interface SpyContext {
	public static final String EXECUTE_RESPONSE = "res";
	public static final String ROW_COUNT = "cnt";
	public static final String ERROR = "err";
	/**
	 * Applies customs attributes on the context object
	 * @param param
	 * @param value
	 */
	public void set(String param, Object value);
}