/*
 * FlightDepartedException.java Created on Jul 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * TODO Add the purpose of this class
 *
 * @author A-1739
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			Jul 18, 2006				   A-1739			First Draft
 */
public class FlightDepartedException extends BusinessException {
    /**
     * The Flight Status Departed
     */
    public static final String FLIGHT_STATUS_DEPARTED = 
        "mailtracking.defaults.warn.flightdeparted";
    /**
     * 
     * @param exceptionCause
     */
    public FlightDepartedException(Object[] exceptionCause) {
        super(FLIGHT_STATUS_DEPARTED, exceptionCause);
        // TODO Auto-generated constructor stub
    }
}
