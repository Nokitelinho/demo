/*
 * HttpProbeEnableEvaluator.java Created on 30-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.http;

import com.ibsplc.neoicargo.framework.probe.ProbePayload;
import com.ibsplc.neoicargo.framework.probe.TxProbeConfig;
import com.ibsplc.neoicargo.framework.probe.TxProbeEnabledEvaluator;
import com.ibsplc.neoicargo.framework.probe.TxProbeUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			30-Dec-2015       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class HttpProbeEnableEvaluator implements TxProbeEnabledEvaluator{

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.TxProbeEnabledEvaluator#isProbeEnabled(com.ibsplc.icargo.framework.probe.TxProbeConfig, java.lang.Object[])
	 */
	@Override
	public boolean isProbeEnabled(TxProbeConfig config, ProbePayload probePayload, Object... probeData) {
		HttpServletRequest request = (HttpServletRequest)probeData[0];
		String user = null;
		if(probePayload.getUser() == null){
			user = config.getHttpProbeConfig().getUserResolver().resolveUser(request);
			probePayload.setUser(user);
		}else
			user = probePayload.getUser();
		boolean answer = TxProbeUtils.isIncluded(user, config.getEnabledUsers(), config.getDisabledUsers());
		// check if the url is disabled
		answer = answer && !isUrlDisabled(config.getHttpProbeConfig().getDisabledUrlExprs(), request.getRequestURI());
		if(answer)
			probePayload.setCorrelationId(config.getHttpProbeConfig().getCorrelationStrategy().correlationId(request));
		return answer && probePayload.getCorrelationId() != null;
	}

	protected boolean isUrlDisabled(Pattern[] disabledUrls, String uri){
		if(disabledUrls == null || disabledUrls.length == 0)
			return false;
		boolean isDisabled = false;
		for(int x = 0 ; !isDisabled && x < disabledUrls.length ; x++){
			Pattern expr = disabledUrls[x];
			isDisabled = expr.matcher(uri).matches();
		}
		return isDisabled;
	}

}
