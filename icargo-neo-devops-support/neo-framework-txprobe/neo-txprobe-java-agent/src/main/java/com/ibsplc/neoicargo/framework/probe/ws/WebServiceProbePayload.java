/*
 * WebServiceProbePayload.java Created on 08-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.ws;

import com.ibsplc.neoicargo.framework.probe.ProbePayload;

import java.io.Serializable;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			08-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class WebServiceProbePayload extends ProbePayload{

	static final int FIELD_COUNT = 4;
	
	private String url;
	private String soapAction;
	private Boolean incoming = Boolean.FALSE;
	private boolean enableApiLogging;

	public WebServiceProbePayload() {
		super();
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
		dupletMap[++x][0] = "url";
		dupletMap[x][1] = this.url;
		dupletMap[++x][0] = "soapAction";
		dupletMap[x][1] = this.soapAction;
		dupletMap[++x][0] = "incoming";
		dupletMap[x][1] = this.incoming;
		dupletMap[++x][0] = "enableApiLogging";
		dupletMap[x][1] = this.enableApiLogging;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the soapAction
	 */
	public String getSoapAction() {
		return soapAction;
	}

	/**
	 * @param soapAction the soapAction to set
	 */
	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

	/**
	 * @return the incoming
	 */
	public Boolean getIncoming() {
		return incoming;
	}

	/**
	 * @param incoming the incoming to set
	 */
	public void setIncoming(Boolean incoming) {
		this.incoming = incoming;
	}

	public boolean isEnableApiLogging() {
		return enableApiLogging;
	}

	public void setEnableApiLogging(boolean enableApiLogging) {
		this.enableApiLogging = enableApiLogging;
	}
}
