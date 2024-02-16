/*
 * TxProbeEnabledEvaluator.java Created on 30-Dec-2015
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
 * 1.0   			30-Dec-2015       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public interface TxProbeEnabledEvaluator {

	boolean isProbeEnabled(TxProbeConfig config, ProbePayload probePayload, Object... probeData);
	
}