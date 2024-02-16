/*
 * SqlProbeConfigMXBean.java Created on 20-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.sql;

import javax.management.MXBean;

/*
 * 
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			20-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
@MXBean
public interface SqlProbeConfigMXBean {

	String TYPE = "txProbeSqlConfig";
	
	/**
	 * @return the logErrors
	 */
	boolean isLogErrors();

	/**
	 * @param logErrors the logErrors to set
	 */
	void setLogErrors(boolean logErrors);

	/**
	 * @return the logExecute
	 */
	boolean isLogExecute();

	/**
	 * @param logExecute the logExecute to set
	 */
	void setLogExecute(boolean logExecute);

	/**
	 * @return the logExecuteUpdate
	 */
	boolean isLogExecuteUpdate();

	/**
	 * @param logExecuteUpdate the logExecuteUpdate to set
	 */
	void setLogExecuteUpdate(boolean logExecuteUpdate);

	/**
	 * @return the logExecuteBatch
	 */
	boolean isLogExecuteBatch();

	/**
	 * @param logExecuteBatch the logExecuteBatch to set
	 */
	void setLogExecuteBatch(boolean logExecuteBatch);

	/**
	 * @return the logExecuteQuery
	 */
	boolean isLogExecuteQuery();

	/**
	 * @param logExecuteQuery the logExecuteQuery to set
	 */
	void setLogExecuteQuery(boolean logExecuteQuery);

}