/*
 * InvalidFlightSegmentException.java Created on Oct 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Oct 19, 2006			a-1739		Created
 */
public class InvalidFlightSegmentException extends BusinessException {

	public static final String FLIGHT_SEG_INVALID = 
		"mailtracking.defaults.err.invalidflightsegment";
	public InvalidFlightSegmentException(Object[] errData) {
		super(FLIGHT_SEG_INVALID, errData);
	}

}
