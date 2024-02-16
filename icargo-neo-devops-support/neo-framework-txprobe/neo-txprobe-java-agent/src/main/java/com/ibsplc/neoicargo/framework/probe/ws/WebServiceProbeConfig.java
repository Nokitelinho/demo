/*
 * WebServiceProbeConfig.java Created on 08-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.ws;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			08-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public class WebServiceProbeConfig implements WebServiceProbeConfigMXBean {

	private boolean logRequest = true;
	private boolean logResponse = true;
	private boolean logHeaders = true;
	private boolean logFault = true;
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ws.WebServiceProbeConfigMXBean#isLogFault()
	 */
	@Override
	public boolean isLogFault() {
		return logFault;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ws.WebServiceProbeConfigMXBean#setLogFault(boolean)
	 */
	@Override
	public void setLogFault(boolean logFault) {
		this.logFault = logFault;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ws.WebServiceProbeConfigMXBean#isLogRequest()
	 */
	@Override
	public boolean isLogRequest() {
		return logRequest;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ws.WebServiceProbeConfigMXBean#setLogRequest(boolean)
	 */
	@Override
	public void setLogRequest(boolean logRequest) {
		this.logRequest = logRequest;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ws.WebServiceProbeConfigMXBean#isLogResponse()
	 */
	@Override
	public boolean isLogResponse() {
		return logResponse;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ws.WebServiceProbeConfigMXBean#setLogResponse(boolean)
	 */
	@Override
	public void setLogResponse(boolean logResponse) {
		this.logResponse = logResponse;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ws.WebServiceProbeConfigMXBean#isLogHeaders()
	 */
	@Override
	public boolean isLogHeaders() {
		return logHeaders;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ws.WebServiceProbeConfigMXBean#setLogHeaders(boolean)
	 */
	@Override
	public void setLogHeaders(boolean logHeaders) {
		this.logHeaders = logHeaders;
	}
	
	
	
}
