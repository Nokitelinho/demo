/*
 * HttpProbeConfigMXBean.java Created on 20-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.http;

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
public interface HttpProbeConfigMXBean {

	String TYPE = "txProbeHttpConfig";
	
	/**
	 * @return the logHttpHeaders
	 */
	boolean isLogHttpHeaders();

	/**
	 * @param logHttpHeaders the logHttpHeaders to set
	 */
	void setLogHttpHeaders(boolean logHttpHeaders);

	/**
	 * @return the logRequestParameters
	 */
	boolean isLogRequestParameters();

	/**
	 * @param logRequestParameters the logRequestParameters to set
	 */
	void setLogRequestParameters(boolean logRequestParameters);

	/**
	 * @return the logErrors
	 */
	boolean isLogErrors();

	/**
	 * @param logErrors the logErrors to set
	 */
	void setLogErrors(boolean logErrors);

	/**
	 * @return the sessionIdHeader
	 */
	String getSessionIdHeader();

	/**
	 * @param sessionIdHeader the sessionIdHeader to set
	 */
	void setSessionIdHeader(String sessionIdHeader);

	/**
	 * @param disabledUrls - urls for which probing is to be disabled.
	 */
	void setDisabledUrls(String[] disabledUrls);
	
	/**
	 * 
	 * @return - urls for which probing is to be disabled.
	 */
	String[] getDisabledUrls();
}