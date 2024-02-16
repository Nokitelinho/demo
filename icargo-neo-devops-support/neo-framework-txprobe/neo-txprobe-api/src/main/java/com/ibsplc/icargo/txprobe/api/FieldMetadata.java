/*
 * FieldMetadata.java Created on 01-Feb-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.api;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			01-Feb-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public class FieldMetadata implements ProbeDataConstants{

	private String name;
	private String type;
	private boolean indexed;
	private boolean uniqueKey;
	private boolean analyzed;
	
	private static Map<String, FieldMetadata> METADATA = null;
	
	/**
	 * Default Constructor
	 * @param name
	 * @param type
	 * @param indexed
	 * @param uniqueKey
	 * @param analyzed
	 */
	public FieldMetadata(String name, String type, boolean indexed, boolean uniqueKey, boolean analyzed) {
		super();
		this.name = name;
		this.type = type;
		this.indexed = indexed;
		this.uniqueKey = uniqueKey;
		this.analyzed = analyzed;
	}

	public static final synchronized Map<String, FieldMetadata> getFieldMetadata(){
		if(METADATA != null)
			return METADATA;
		Map<String, FieldMetadata> fmd = new HashMap<String, FieldMetadata>(64);
		fmd.put(PROBE_TYPE, new FieldMetadata(PROBE_TYPE, String.class.getName(), true, false, false));
		fmd.put(CORRELATION_ID, new FieldMetadata(CORRELATION_ID, String.class.getName(), true, false, false));
		fmd.put(START_TIME, new FieldMetadata(START_TIME, Date.class.getName(), true, false, false));
		fmd.put(ELASPSED_TIME, new FieldMetadata(ELASPSED_TIME, Integer.class.getName(), true, false, false));
		fmd.put(SUCCESS, new FieldMetadata(SUCCESS, Boolean.class.getName(), true, false, false));
		fmd.put(SEQUENCE, new FieldMetadata(SEQUENCE, Integer.class.getName(), true, false, false));
		fmd.put(USER, new FieldMetadata(USER, String.class.getName(), true, false, false));
		fmd.put(ERROR, new FieldMetadata(ERROR, String.class.getName(), true, false, true));
		fmd.put(NODE_NAME, new FieldMetadata(NODE_NAME, String.class.getName(), true, false, false));
		fmd.put(INVOCATIONID, new FieldMetadata(INVOCATIONID, String.class.getName(), true, false, false));
		fmd.put(REQ_BODY, new FieldMetadata(REQ_BODY, String.class.getName(), true, false, true));
		fmd.put(REQ_HEADERS, new FieldMetadata(REQ_HEADERS, String.class.getName(), true, false, true));
		fmd.put(RES_BODY, new FieldMetadata(RES_BODY, String.class.getName(), true, false, true));
		fmd.put(RES_HEADERS, new FieldMetadata(RES_HEADERS, String.class.getName(), true, false, true));
		fmd.put(MODULE, new FieldMetadata(MODULE, String.class.getName(), true, false, false));
		fmd.put(SUBMODULE, new FieldMetadata(SUBMODULE, String.class.getName(), true, false, false));
		fmd.put(URL, new FieldMetadata(URL, String.class.getName(), true, false, true));
		fmd.put(LOGON_ATTRIBUTES, new FieldMetadata(LOGON_ATTRIBUTES, String.class.getName(), true, false, false));
		fmd.put(ACTION, new FieldMetadata(ACTION, String.class.getName(), true, false, false));
		fmd.put(JSESSIONID, new FieldMetadata(JSESSIONID, String.class.getName(), true, false, false));
		fmd.put(SOAPACTION, new FieldMetadata(SOAPACTION, String.class.getName(), true, false, true));
		fmd.put(INTERFACESYS, new FieldMetadata(INTERFACESYS, String.class.getName(), true, false, false));
		fmd.put(ERROR_TYPE, new FieldMetadata(ERROR_TYPE, String.class.getName(), true, false, false));
		fmd.put(THREAD_NAME, new FieldMetadata(THREAD_NAME, String.class.getName(), true, false, false));
		/*jvm fields */
		fmd.put(JVM_THREADCOUNT, new FieldMetadata(JVM_THREADCOUNT, Integer.class.getName(), true, false, false));
		fmd.put(JVM_DAEMONTHREADCOUNT, new FieldMetadata(JVM_DAEMONTHREADCOUNT, Integer.class.getName(), true, false, false));
		fmd.put(JVM_SYSTEMCPULOAD, new FieldMetadata(JVM_SYSTEMCPULOAD, Integer.class.getName(), true, false, false));
		fmd.put(JVM_PROCESSCPULOAD, new FieldMetadata(JVM_PROCESSCPULOAD, Integer.class.getName(), true, false, false));
		fmd.put(JVM_COMMITEDVIRTUALMEMORY, new FieldMetadata(JVM_COMMITEDVIRTUALMEMORY, Integer.class.getName(), true, false, false));
		fmd.put(JVM_FREEPHYSICALMEMORY, new FieldMetadata(JVM_FREEPHYSICALMEMORY, Integer.class.getName(), true, false, false));
		fmd.put(JVM_HEAPMEMORYUSED, new FieldMetadata(JVM_HEAPMEMORYUSED, Integer.class.getName(), true, false, false));
		fmd.put(JVM_HEAPMEMORYCOMMITED, new FieldMetadata(JVM_HEAPMEMORYCOMMITED, Integer.class.getName(), true, false, false));
		fmd.put(JVM_NATIVEMEMORYUSED, new FieldMetadata(JVM_NATIVEMEMORYUSED, Integer.class.getName(), true, false, false));
		fmd.put(JVM_NATIVEMEMORYCOMMITED, new FieldMetadata(JVM_NATIVEMEMORYCOMMITED, Integer.class.getName(), true, false, false));
		fmd.put(JVM_YOUNGGCCOUNT, new FieldMetadata(JVM_YOUNGGCCOUNT, Integer.class.getName(), true, false, false));
		fmd.put(JVM_OLDGCCOUNT, new FieldMetadata(JVM_OLDGCCOUNT, Integer.class.getName(), true, false, false));
		
		fmd.put(DOCSIZE, new FieldMetadata(DOCSIZE, Integer.class.getName(), true, false, false));
		fmd.put(INCOMING, new FieldMetadata(INCOMING, Boolean.class.getName(), true, false, false));
		fmd.put(SQL_RESPONSE, new FieldMetadata(SQL_RESPONSE, Boolean.class.getName(), true, false, false));
		fmd.put(SQL_ROWCOUNT, new FieldMetadata(SQL_ROWCOUNT, Integer.class.getName(), true, false, false));
		fmd.put(SQL_HASH, new FieldMetadata(SQL_HASH, Integer.class.getName(), true, false, false));
		fmd.put(TENANT, new FieldMetadata(TENANT, String.class.getName(), true, false, false));
		fmd.put(SERVICE_APP_NAME, new FieldMetadata(SERVICE_APP_NAME, String.class.getName(), true, false, false));
		fmd.put(TOPIC_NAME, new FieldMetadata(TOPIC_NAME, String.class.getName(), true, false, false));
		METADATA = Collections.unmodifiableMap(fmd);
		return METADATA;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the indexed
	 */
	public boolean isIndexed() {
		return indexed;
	}
	
	/**
	 * @param indexed the indexed to set
	 */
	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}
	
	/**
	 * @return the uniqueKey
	 */
	public boolean isUniqueKey() {
		return uniqueKey;
	}
	
	/**
	 * @param uniqueKey the uniqueKey to set
	 */
	public void setUniqueKey(boolean uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	
	/**
	 * @return the analyzed
	 */
	public boolean isAnalyzed() {
		return analyzed;
	}
	
	/**
	 * @param analyzed the analyzed to set
	 */
	public void setAnalyzed(boolean analyzed) {
		this.analyzed = analyzed;
	}

}
