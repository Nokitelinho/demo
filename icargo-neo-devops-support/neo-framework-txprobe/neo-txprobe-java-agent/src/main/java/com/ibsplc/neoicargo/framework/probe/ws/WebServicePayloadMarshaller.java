/*
 * WebServicePayloadMarshaller.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.ws;

import com.ibsplc.neoicargo.framework.probe.ProbePayload;
import com.ibsplc.neoicargo.framework.probe.ProbePayloadMarshaller;
import com.ibsplc.neoicargo.framework.probe.TxProbeConfig;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			13-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class WebServicePayloadMarshaller implements ProbePayloadMarshaller{

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ProbePayloadMarshaller#marshall(com.ibsplc.icargo.framework.probe.TxProbeConfig,
	 *  com.ibsplc.icargo.framework.probe.ProbePayload, java.lang.Object[])
	 */
	@Override
	public ProbePayload marshall(TxProbeConfig config, ProbePayload payload, Object... probeData) {
		// payload construction is handled at the interceptor layer
		return payload;
	}

	
}
