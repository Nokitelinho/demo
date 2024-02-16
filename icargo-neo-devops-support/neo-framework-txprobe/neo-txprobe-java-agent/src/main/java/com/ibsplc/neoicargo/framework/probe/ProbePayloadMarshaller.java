/*
 * ProbePayloadMarshaller.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 * @ThreadSafe - implementations should be thread safe
 */
public interface ProbePayloadMarshaller {

	ProbePayload marshall(TxProbeConfig config, ProbePayload payload, Object ... probeData);
	
}
