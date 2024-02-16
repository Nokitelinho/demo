/*
 * HttpHeaderCorrelationStrategy.java Created on 31-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.http;

import com.ibsplc.neoicargo.framework.probe.CorrelationStrategy;
import org.springframework.beans.factory.InitializingBean;

import javax.servlet.http.HttpServletRequest;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			31-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class HttpHeaderCorrelationStrategy implements CorrelationStrategy<HttpServletRequest>, InitializingBean{

	private String[] corrleationHeaders;
	private boolean generateIfAbsent;
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.CorrelationStrategy#correlationId(java.lang.Object)
	 */
	@Override
	public String correlationId(HttpServletRequest request) {
		for(int x = 0 ; x < this.corrleationHeaders.length ; x++){
			String answer = request.getHeader(corrleationHeaders[x]);
			if(answer != null && answer.trim().length() > 0)
				return answer;
		}
		if(this.generateIfAbsent)
			return CorrelationStrategy.GENERATE_STRATEGY.correlationId(null);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if(this.corrleationHeaders == null || this.corrleationHeaders.length == 0)
			throw new IllegalArgumentException("corrleationHeaders is absent");
	}

	/**
	 * @return the corrleationHeaders
	 */
	public String[] getCorrleationHeaders() {
		return corrleationHeaders;
	}

	/**
	 * @param corrleationHeaders the corrleationHeaders to set
	 */
	public void setCorrleationHeaders(String[] corrleationHeaders) {
		this.corrleationHeaders = corrleationHeaders;
	}

	/**
	 * @return the generateIfAbsent
	 */
	public boolean isGenerateIfAbsent() {
		return generateIfAbsent;
	}

	/**
	 * @param generateIfAbsent the generateIfAbsent to set
	 */
	public void setGenerateIfAbsent(boolean generateIfAbsent) {
		this.generateIfAbsent = generateIfAbsent;
	}
	
}
