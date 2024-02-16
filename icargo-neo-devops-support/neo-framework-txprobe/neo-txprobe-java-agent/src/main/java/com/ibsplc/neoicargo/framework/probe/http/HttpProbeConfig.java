/*
 * HttpProbeConfig.java Created on 31-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.http;

import com.ibsplc.neoicargo.framework.probe.CorrelationStrategy;
import com.ibsplc.neoicargo.framework.probe.UserResolver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			31-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class HttpProbeConfig implements HttpProbeConfigMXBean {

	private boolean logHttpHeaders = true;
	private boolean logRequestParameters = true;
	private boolean logErrors = true;
	private String sessionIdHeader = "JSESSIONID";
	private String[] disabledUrls;
	private Pattern[] disabledUrlExprs;
	private UserResolver<HttpServletRequest> userResolver;
	private CorrelationStrategy<HttpServletRequest> correlationStrategy = new CorrelationStrategy.GenerateWrapStrategy<>();

	@Autowired
	public HttpProbeConfig(UserResolver<HttpServletRequest> userResolver){
		this.userResolver = userResolver;
	}

	protected Pattern[] constructRegex(String[] exprs){
		if(exprs == null || exprs.length == 0)
			return new Pattern[0];
		Pattern[] answer = new Pattern[exprs.length];
		for(int x = 0 ; x < exprs.length; x++)
			answer[x] = Pattern.compile(exprs[x]);
		return answer;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.http.HttpProbeConfigMXBean#isLogHttpHeaders()
	 */
	@Override
	public boolean isLogHttpHeaders() {
		return logHttpHeaders;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.http.HttpProbeConfigMXBean#setLogHttpHeaders(boolean)
	 */
	@Override
	public void setLogHttpHeaders(boolean logHttpHeaders) {
		this.logHttpHeaders = logHttpHeaders;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.http.HttpProbeConfigMXBean#isLogRequestParameters()
	 */
	@Override
	public boolean isLogRequestParameters() {
		return logRequestParameters;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.http.HttpProbeConfigMXBean#setLogRequestParameters(boolean)
	 */
	@Override
	public void setLogRequestParameters(boolean logRequestParameters) {
		this.logRequestParameters = logRequestParameters;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.http.HttpProbeConfigMXBean#isLogErrors()
	 */
	@Override
	public boolean isLogErrors() {
		return logErrors;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.http.HttpProbeConfigMXBean#setLogErrors(boolean)
	 */
	@Override
	public void setLogErrors(boolean logErrors) {
		this.logErrors = logErrors;
	}
	
	/**
	 * @return the userResolver
	 */
	public UserResolver<HttpServletRequest> getUserResolver() {
		return userResolver;
	}
	
	/**
	 * @param userResolver the userResolver to set
	 */
	public void setUserResolver(UserResolver<HttpServletRequest> userResolver) {
		this.userResolver = userResolver;
	}
	
	/**
	 * @return the correlationStrategy
	 */
	public CorrelationStrategy<HttpServletRequest> getCorrelationStrategy() {
		return correlationStrategy;
	}
	
	/**
	 * @param correlationStrategy the correlationStrategy to set
	 */
	public void setCorrelationStrategy(CorrelationStrategy<HttpServletRequest> correlationStrategy) {
		this.correlationStrategy = correlationStrategy;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.http.HttpProbeConfigMXBean#getSessionIdHeader()
	 */
	@Override
	public String getSessionIdHeader() {
		return sessionIdHeader;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.http.HttpProbeConfigMXBean#setSessionIdHeader(java.lang.String)
	 */
	@Override
	public void setSessionIdHeader(String sessionIdHeader) {
		this.sessionIdHeader = sessionIdHeader;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.http.HttpProbeConfigMXBean#setDisabledUrls(java.lang.String[])
	 */
	@Override
	public void setDisabledUrls(String[] disabledUrls) {
		this.disabledUrls = disabledUrls;
		this.disabledUrlExprs = constructRegex(disabledUrls);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.http.HttpProbeConfigMXBean#getDisabledUrls()
	 */
	@Override
	public String[] getDisabledUrls() {
		return this.disabledUrls;
	}

	/**
	 * @return the disabledUrlExprs
	 */
	public Pattern[] getDisabledUrlExprs() {
		return disabledUrlExprs;
	}
	
}
