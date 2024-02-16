/*
 * WebServiceProbeConfigMXBean.java Created on 20-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.ws;

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
public interface WebServiceProbeConfigMXBean {

	String TYPE = "txProbeWebServiceConfig";
	
	/**
	 * @return the logFault
	 */
	boolean isLogFault();

	/**
	 * @param logFault the logFault to set
	 */
	void setLogFault(boolean logFault);

	/**
	 * @return the logRequest
	 */
	boolean isLogRequest();

	/**
	 * @param logRequest the logRequest to set
	 */
	void setLogRequest(boolean logRequest);

	/**
	 * @return the logResponse
	 */
	boolean isLogResponse();

	/**
	 * @param logResponse the logResponse to set
	 */
	void setLogResponse(boolean logResponse);

	/**
	 * @return the logHeaders
	 */
	boolean isLogHeaders();

	/**
	 * @param logHeaders the logHeaders to set
	 */
	void setLogHeaders(boolean logHeaders);

}