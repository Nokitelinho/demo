/*
 * HttpProbePayloadMarshaller.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.http;

import com.ibsplc.icargo.txprobe.api.ProbedState;
import com.ibsplc.neoicargo.framework.probe.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 * TODO - need to implement correlationId mapping
 */
@SuppressWarnings("unchecked")
public class HttpProbePayloadMarshaller implements ProbePayloadMarshaller{

	private Boolean isServlet3 = null;
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ProbePayloadMarshaller#marshall(java.lang.Object[])
	 */
	@Override
	public ProbePayload marshall(TxProbeConfig config, ProbePayload basePayload, Object... probeData) {
		HttpServletRequest request = (HttpServletRequest)probeData[0];
		HttpServletResponse response = (HttpServletResponse)probeData[1];
		Throwable error = (Throwable)probeData[2];
		HttpProbePayload payload = new HttpProbePayload(basePayload);
		payload.setRequestUrl(request.getRequestURI());
		payload.setSuccess(error == null);
		payload.setSessionId(request.getRequestedSessionId());
		if(payload.getUser() == null)
			payload.setUser(config.getHttpProbeConfig().getUserResolver().resolveUser(request));
		
		if(ProbedState.BEFORE == payload.getProbeState()){
			if(config.getHttpProbeConfig().isLogRequestParameters()){
				Map<String, Object> params = new HashMap<>(request.getParameterMap().size());
				params.putAll(request.getParameterMap());
				payload.setBody(TxProbeUtils.renderMap(params));
			}
			if(config.getHttpProbeConfig().isLogHttpHeaders())
				applyRequestHeaders(request, payload);
		}else if(ProbedState.AFTER == payload.getProbeState() && config.getHttpProbeConfig().isLogHttpHeaders() && isServlet3(request)){
				try {
					payload.setHeaders(TxProbeUtils.renderMap(getHttpResponseHeaders(response)));
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		if(error != null && config.getHttpProbeConfig().isLogErrors())
			payload.setError(TxProbeUtils.renderException(error));
		return payload;
	}
	
	protected Map<String, Object> getHttpResponseHeaders(HttpServletResponse response){
		Collection<String> headerNamesTemp = response.getHeaderNames();
		// make a defensive copy
		Collection<String> headerNames = new ArrayList<>(headerNamesTemp.size());
		headerNames.addAll(headerNamesTemp);
		int status = response.getStatus();
		if(headerNames.isEmpty()){
			Map<String, Object> map = new HashMap<>(2);
			map.put("Status", status);
			return map;
		}
		Map<String, Object> headerMap = new LinkedHashMap<>(headerNames.size() + 1);
		headerMap.put("Status", status);
		for(String header : headerNames){
			Object value = response.getHeader(header);
			headerMap.put(header, value);
		}
		return headerMap;
	}
	
	protected boolean isServlet3(HttpServletRequest request){
		if(this.isServlet3 == null){
			try {
				// check for a servlet 3 method
				request.getClass().getMethod("logout", new Class[0]);
				this.isServlet3 = Boolean.TRUE;
			} catch (Exception e) {
				this.isServlet3 = Boolean.FALSE;
			} 
		}
		return this.isServlet3;
	}
	
	protected void applyRequestHeaders(HttpServletRequest request, HttpProbePayload payload){
		List<String> headers = Collections.list(request.getHeaderNames());
		Map<String, Object> map = new HashMap<>(headers.size());
		for(int x = 0; x < headers.size() ; x++){
			String header = headers.get(x);
			map.put(header, request.getHeader(header));
		}
		payload.setHeaders(TxProbeUtils.renderMap(map));
	}
}
