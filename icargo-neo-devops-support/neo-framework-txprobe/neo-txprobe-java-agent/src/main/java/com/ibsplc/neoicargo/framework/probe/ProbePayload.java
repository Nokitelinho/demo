/*
 * ProbePayload.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbedState;

import java.io.Serializable;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class ProbePayload {

	private Probe probe;
	private String correlationId;
	private ProbedState probeState;
	private String user;
	private long startTime;
	private int elapsedTime;
	private boolean success;
	private int sequence;
	private String error;
	private String headers;
	private String body;
	private Serializable invocationId;
	private String threadName;
	private String tenant;
	private String service;
	private String version;
	private static String nodeName;

	static {
		// check if we are running in a pod - if yes then use the podname
		String k8sHostName = System.getenv("KUBERNETES_SERVICE_HOST");
		if(k8sHostName == null || k8sHostName.trim().isEmpty())
			nodeName = System.getProperty("nodeName", "unknown.localhost");
		else
			nodeName = System.getenv("HOSTNAME");
	}

	/**
	 * default
	 */
	public ProbePayload(){
		this.startTime = System.currentTimeMillis();
	}
	
	/**
	 * Copy constructor
	 * @param other
	 */
	public ProbePayload(ProbePayload other){
		this.probe = other.probe;
		this.correlationId = other.correlationId;
		this.probeState = other.probeState;
		this.user = other.user;
		this.startTime = other.startTime;
		this.elapsedTime = other.elapsedTime;
		this.success = other.success;
		this.sequence = other.sequence;
		this.invocationId = other.invocationId;
		this.threadName = other.threadName;
		this.tenant = other.tenant;
		this.service = other.service;
		this.version = other.version;
	}
	
	public int fieldCount(){
		return 17;
	}
	
	public void writeTo(Serializable[][] dupletMap){
		int x = -1;
		dupletMap[++x][0] = "probeType";
		dupletMap[x][1] = this.probe.toString();
		dupletMap[++x][0] = "correlationId";
		dupletMap[x][1] = this.correlationId;
		dupletMap[++x][0] = "probeState";
		dupletMap[x][1] = this.probeState.toString();
		dupletMap[++x][0] = "startTime";
		dupletMap[x][1] = this.startTime;
		dupletMap[++x][0] = "elapsedTime";
		dupletMap[x][1] = this.elapsedTime;
		dupletMap[++x][0] = "success";
		dupletMap[x][1] = this.success;
		dupletMap[++x][0] = "sequence";
		dupletMap[x][1] = this.sequence;
		dupletMap[++x][0] = "user";
		dupletMap[x][1] = this.user;
		dupletMap[++x][0] = "error";
		dupletMap[x][1] = this.error;
		dupletMap[++x][0] = "nodeName";
		dupletMap[x][1] = ProbePayload.nodeName;
		dupletMap[++x][0] = "invocationId";
		dupletMap[x][1] = this.invocationId;
		dupletMap[++x][0] = "body";
		dupletMap[x][1] = this.body;
		dupletMap[++x][0] = "headers";
		dupletMap[x][1] = this.headers;
		dupletMap[++x][0] = "threadName";
		dupletMap[x][1] = this.threadName;
		dupletMap[++x][0] = "tenant";
		dupletMap[x][1] = this.tenant;
		dupletMap[++x][0] = "serviceName";
		dupletMap[x][1] = this.service;
		dupletMap[++x][0] = "@version";
		dupletMap[x][1] = this.version;
	}
	
	public static ProbePayload before(Probe probe, Serializable requestId){
		ProbePayload payload = new ProbePayload();
		payload.probe = probe;
		payload.probeState= ProbedState.BEFORE;
		payload.invocationId = requestId;
		return payload; 
	}
	
	public static ProbePayload on(Probe probe, Serializable requestId){
		ProbePayload payload = new ProbePayload();
		payload.probe = probe;
		payload.probeState= ProbedState.ON;
		payload.invocationId = requestId;
		return payload; 
	}
	
	public static ProbePayload after(ProbePayload before){
		ProbePayload payload = new ProbePayload();
		payload.probe = before.probe;
		payload.correlationId = before.correlationId;
		payload.user = before.user;
		payload.invocationId = before.invocationId;
		payload.probeState = ProbedState.AFTER;
		return payload;
	}
	
	/**
	 * @return the probe
	 */
	public Probe getProbe() {
		return probe;
	}
	
	/**
	 * @param probe the probe to set
	 */
	public void setProbe(Probe probe) {
		this.probe = probe;
	}
	
	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}
	
	/**
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	/**
	 * @return the probeState
	 */
	public ProbedState getProbeState() {
		return probeState;
	}
	
	/**
	 * @param probeState the probeState to set
	 */
	public void setProbeState(ProbedState probeState) {
		this.probeState = probeState;
	}
	
	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}
	
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * @return the elapsedTime
	 */
	public int getElapsedTime() {
		return elapsedTime;
	}

	/**
	 * @param elapsedTime the elapsedTime to set
	 */
	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}
	
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the requestId
	 */
	public Serializable getInvocationId() {
		return invocationId;
	}

	/**
	 *
	 * @param invocationId
	 */
	public void setInvocationId(Serializable invocationId) {
		this.invocationId = invocationId;
	}

	/**
	 * @return the headers
	 */
	public String getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(String headers) {
		this.headers = headers;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the threadName
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 * @param threadName the threadName to set
	 */
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
