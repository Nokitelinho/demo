/*
 * HttpHeaderUserResolver.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.http;

import com.ibsplc.neoicargo.framework.probe.UserResolver;
import org.springframework.beans.factory.InitializingBean;

import javax.servlet.http.HttpServletRequest;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class HttpHeaderUserResolver implements UserResolver<HttpServletRequest>, InitializingBean{

	private String[] headers;
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.HttpUserResolver#resolveUser(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String resolveUser(HttpServletRequest request) {
		for(int x = 0; x < headers.length ; x++){
			String header = headers[x];
			String user = request.getHeader(header);
			if(user != null && user.trim().length() > 0)
				return user;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if(this.headers == null || this.headers.length == 0)
			throw new IllegalArgumentException("HEADERS NOT SPECIFIED");
	}

	/**
	 * @return the headers
	 */
	public String[] getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	
	
}
