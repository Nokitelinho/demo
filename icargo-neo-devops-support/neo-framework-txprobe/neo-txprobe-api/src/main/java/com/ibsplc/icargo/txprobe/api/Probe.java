/*
 * Probe.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.api;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 * The different types of probes.
 */
public enum Probe {

	HTTP,
	
	SERVICE,
	
	WEBSERVICE_HTTP,
	
	WEBSERVICE_JMS,
	
	SQL,
	
	INTERFACE_MESSAGE,
	
	JVM,
	
	BPMN,

    KAFKA,

	LOG
}
