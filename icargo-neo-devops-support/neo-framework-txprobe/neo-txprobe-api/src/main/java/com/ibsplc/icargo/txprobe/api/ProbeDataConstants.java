/*
 * ProbeDataConstants.java Created on 29-Jan-2016
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
 * 1.0   			29-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public interface ProbeDataConstants {

	String PROBE_TYPE = "probeType";
	String CORRELATION_ID = "correlationId";
	String PROBE_STATE = "probeState";
	String START_TIME = "startTime";
	String ELASPSED_TIME = "elapsedTime";
	String SUCCESS = "success";
	String SEQUENCE = "sequence";
	String USER = "user";
	String ERROR = "error";
	String ERROR_TYPE = "errorType";
	String NODE_NAME = "nodeName";
	String INVOCATIONID = "invocationId";
	String BODY = "body";
	String HEADERS = "headers";
	String THREAD_NAME = "threadName";
	
	String REQ_HEADERS = "requestHeaders";
	String REQ_BODY = "request";
	String RES_HEADERS = "responseHeaders";
	String RES_BODY = "response";
	
	String MODULE = "module";
	String SUBMODULE = "submodule";
	String ACTION = "action";
	String URL = "url";
	String LOGON_ATTRIBUTES = "logonAttributes";
	String JSESSIONID = "sessionId";
	String INTERFACESYS = "interfaceSystem";
	String SOAPACTION = "soapAction";
	String INVOCATIONID_DEFAULT = "-";
	
	String JVM_THREADCOUNT = "threadCount";
	String JVM_DAEMONTHREADCOUNT = "daemonThreadCount";
	String JVM_SYSTEMCPULOAD = "systemCpuLoad";
	String JVM_PROCESSCPULOAD = "processCpuLoad";
	String JVM_COMMITEDVIRTUALMEMORY = "commitedVirtualMemory";
	String JVM_FREEPHYSICALMEMORY = "freePhysicalMemory";
	String JVM_HEAPMEMORYUSED = "heapMemoryUsed";
	String JVM_HEAPMEMORYCOMMITED = "heapMemoryCommited";
	String JVM_NATIVEMEMORYUSED = "nativeMemoryUsed";
	String JVM_NATIVEMEMORYCOMMITED = "nativeMemoryCommited";
	String JVM_YOUNGGCCOUNT = "youngGcCount";
	String JVM_OLDGCCOUNT = "oldGcCount";
	// the size of the payload in bytes
	String DOCSIZE = "docSize";
	String INCOMING = "incoming";
	String SQL_RESPONSE = "sqlResponse";
	String SQL_ROWCOUNT = "sqlRowCount";

	String TENANT = "tenant";
	String SERVICE_APP_NAME = "serviceName";
	String TOPIC_NAME = "topic";
	String ENABLE_API_LOGGING = "enableApiLogging";
	String SQL_HASH = "sqlHash";

}
