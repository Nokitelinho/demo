/*
 * SqlProbePayloadMarshaller.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql;

import com.ibsplc.icargo.txprobe.api.ProbedState;
import com.ibsplc.neoicargo.framework.probe.*;
import com.ibsplc.neoicargo.framework.probe.sql.spy.Spy;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			13-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class SqlProbePayloadMarshaller implements ProbePayloadMarshaller{

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.ProbePayloadMarshaller#marshall(com.ibsplc.icargo.framework.probe.TxProbeConfig,
	 *  com.ibsplc.icargo.framework.probe.ProbePayload, java.lang.Object[])
	 */
	@Override
	public ProbePayload marshall(TxProbeConfig config, ProbePayload payload, Object... probeData) {
		Spy spy = (Spy) probeData[0];
		String method = (String)probeData[1];
		Exception error = (Exception)probeData[2];
		SqlProbePayload sqlPayload = (SqlProbePayload)payload;
		sqlPayload.setSuccess(true);
		if(payload.getProbeState() == ProbedState.BEFORE || payload.getProbeState() == ProbedState.ON){
			StringBuilder sbul = new StringBuilder(50);
			sbul.append("{ \"connectionId\" : \"").append(spy.getConnectionNumber()).append("\", \"type\" : \"")
				.append(spy.getClassType()).append("\", \"method\" : \"").append(method).append("\" }");
			sqlPayload.setHeaders(sbul.toString());
			if(error != null){
				sqlPayload.setError(TxProbeUtils.renderException(error));
				sqlPayload.setSuccess(false);
			}
		}
		return sqlPayload;
	}

	
}
