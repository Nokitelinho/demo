/*
 * ProbedState.java Created on 29-Dec-2015
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.api;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			29-Dec-2015       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public enum ProbedState {

	/**
	 * Probe information collected before the operation.
	 */
	BEFORE,
	
	/**
	 * Probe information collected after operation completion.
	 */
	AFTER,
	
	/**
	 * Probe information collected to model typical fire and forget txns.
	 * eg : transactions which returns Future or which branches off.
	 */
	ON
	
}
