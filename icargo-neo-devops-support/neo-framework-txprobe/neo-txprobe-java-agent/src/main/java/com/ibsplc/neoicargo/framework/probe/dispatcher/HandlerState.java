/*
 * HandlerState.java Created on 04-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.dispatcher;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			04-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public enum HandlerState {

	MARSHALL_PAYLOAD {
		@Override
		boolean eagerExecute() {
			return true;
		}
	},	MARSHALL_WIRE {
		@Override
		boolean eagerExecute() {
			return false;
		}
	},	SEQUENCER {
		@Override
		boolean eagerExecute() {
			return false;
		}
	},	TRANSPORT {
		@Override
		boolean eagerExecute() {
			return false;
		}
	}, REJECT {
		@Override
		boolean eagerExecute() {
			return false;
		}
	};

	/** 
	 * To be executed in the calling thread 
	 */
	abstract boolean eagerExecute();
	
}
