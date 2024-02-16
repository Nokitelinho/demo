/*
 * TxProbeWSFeature.java Created on 11-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.ws;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.neoicargo.framework.probe.TxProbeFacade;
import com.ibsplc.neoicargo.framework.probe.TxProbeUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.cxf.Bus;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.interceptor.InterceptorProvider;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			11-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
@Getter
@Setter
public class TxProbeWSFeature extends AbstractFeature{

	private boolean isJms;
	private String correlationIdHeader = TxProbeUtils.TXPROBE_CORRELATION_HEADER;
	private boolean generateIfAbsent = true;
	private boolean useMdc = true;
	private final TxProbeFacade facade;

	/**
	 * Default Constructor
	 */
	public TxProbeWSFeature(TxProbeFacade facade, boolean isJms) {
		super();
		this.facade = facade;
		this.isJms = isJms;
	}

	/* (non-Javadoc)
	 * @see org.apache.cxf.feature.AbstractFeature#initializeProvider(org.apache.cxf.interceptor.InterceptorProvider, org.apache.cxf.Bus)
	 */
	@Override
	protected void initializeProvider(InterceptorProvider provider, Bus bus) {
		TxProbingWSIncomingInterceptor incomingInterceptor = new TxProbingWSIncomingInterceptor(this.facade);
		incomingInterceptor.setProbe(isJms ? Probe.WEBSERVICE_JMS : Probe.WEBSERVICE_HTTP);
		TxProbingWSOutgoingInterceptor outgoingInterceptor = new TxProbingWSOutgoingInterceptor(this.facade);
		outgoingInterceptor.setProbe(isJms ? Probe.WEBSERVICE_JMS : Probe.WEBSERVICE_HTTP);
		incomingInterceptor.setCorrelationIdHeader(this.correlationIdHeader);
		incomingInterceptor.setGenerateIfAbsent(this.generateIfAbsent);
		incomingInterceptor.setUseMdc(this.useMdc);
		outgoingInterceptor.setCorrelationIdHeader(this.correlationIdHeader);
		outgoingInterceptor.setGenerateIfAbsent(this.generateIfAbsent);
		outgoingInterceptor.setUseMdc(this.useMdc);
		provider.getInFaultInterceptors().add(incomingInterceptor);
		provider.getInInterceptors().add(incomingInterceptor);
		provider.getOutFaultInterceptors().add(outgoingInterceptor);
		provider.getOutInterceptors().add(outgoingInterceptor);
	}


}
