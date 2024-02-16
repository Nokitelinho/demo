/*
 * DiscrepancyAlreadyCaughtException.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * 
 * @author A-1936
 * 
 */


public class DiscrepancyAlreadyCaughtException extends BusinessException {
	/**
	 * 
	 */
	public static final String DISCREPANCY_ALREADY_CAUGHT = "uld.defaults.discrepancyalreadycaught";

	/**
	 * 
	 */
	public static final String DISCREPANCY_TOBE_SOLVED = "uld.defaults.discrepancytobesolved";

	/**
	 * 
	 */
	public static final String ULD_IS_NOT_IN_THE_STOCK = "uld.defaults.uldisnotinthestock";

	/**
	 * 
	 */
	public static final String ULD_IS_THERE_IN_THE_STOCK = "uld.defaults.uldisthereinthestock";

	/**
	 * 
	 */
	public static final String ULD_ALREADY_IN_THE_SAME_LOCATION = "uld.defaults.uldalreadyinthesamelocation";

	/**
	 * 
	 */
	public static final String ULD_CANNOT_BE_MISSING = "uld.defaults.uldcannotbemissing";
	/**
	 * 
	 */
	public static final String ULD_INTERNALLY_MOVED = "uld.defaults.uldinternallymoved";
	/**
	 * 
	 */
	public static final String DISCREPANCY_AT_SAME_LOCATION = "uld.defaults.discrepancyatsamelocation"; 
	

	public DiscrepancyAlreadyCaughtException() {
		super();
	}
	
	/**
	 * @param errorCode
	 * @param exceptionCause
	 * @author A-2667
	 */
	public DiscrepancyAlreadyCaughtException(String errorCode,
			Object[] exceptionCause) {
		super(errorCode, exceptionCause);		
	}

}
