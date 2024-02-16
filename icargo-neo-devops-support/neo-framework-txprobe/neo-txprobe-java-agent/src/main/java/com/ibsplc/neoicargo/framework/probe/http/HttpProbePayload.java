/*
 * HttpProbePayload.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.http;

import com.ibsplc.neoicargo.framework.probe.ProbePayload;

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
public class HttpProbePayload extends ProbePayload{

	/** increment the field when adding/removing fields which need to be exported */
	static final int FIELD_COUNT = 2;
	
	private String sessionId;
	private String requestUrl;
	
	public HttpProbePayload() {
		super();
	}

	public HttpProbePayload(ProbePayload other) {
		super(other);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ProbePayload#fieldCount()
	 */
	@Override
	public int fieldCount() {
		return super.fieldCount() + FIELD_COUNT;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ProbePayload#writeTo(java.io.Serializable[][])
	 */
	@Override
	public void writeTo(Serializable[][] dupletMap) {
		super.writeTo(dupletMap);
		int x = super.fieldCount() - 1;
		dupletMap[++x][0] = "sessionId";
		dupletMap[x][1] = this.sessionId;
		dupletMap[++x][0] = "url";
		dupletMap[x][1] = this.requestUrl;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}
	
	/**
	 * @param requestUrl the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

}

