			  /*
 * ContainerAssignmentException.java Created on May 30, 2005
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1936
 * This class is used to create an exception to be thrown when checks for the
 * ContainerAssignment are Done.
 */
public class ContainerAssignmentException extends BusinessException {
	/**
	 * The ErrorCode FINALDESTINATION_POU_INVALID
	 */
	public static final String FINALDESTINATION_POU_INVALID = "mailtracking.defaults.finaldestinationinvalid";

	/**
	 * The ErrorCode For Reassign
	 */
	public static final String CARRIER_CONTAINER_REASSIGN = "mailtracking.defaults.canreassigned";

	/**
	 * The ErrorCode For Status Closed
	 */
	public static final String FLIGHT_STATUS_CLOSED = "mailtracking.defaults.statusclosed";

	/**
	 * The ErrorCode For Status Open
	 */
	public static final String FLIGHT_STATUS_OPEN = "mailtracking.defaults.openedflight";

	/**
	 * The ErrorCode For Container Already Assigned to Same Flight
	 */
	public static final String ALREADY_ASSIGNED_TOSAMEFLIGHT = "mailtracking.defaults.sameflight";

	/**
	 * The ErrorCode For CONFIGUREDTIME_DEPARTED
	 */
	public static final String CONFIGUREDTIME_DEPARTED = "mailtracking.defaults.departed";

	/**
	 * The ErrorCode For CONFIGUREDTIME_NONDEPARTED
	 */
	public static final String CONFIGUREDTIME_NONDEPARTED = "mailtracking.defaults.notdeparted";

	/**
	 * The ErrorCode For CarrierAlreadyAssigned
	 */
	public static final String CARRIER_ALREADY_ASSIGNED = "mailtracking.defaults.carrieralreadyassigned";

	public static final String FLIGHT_STATUS_POL_OPEN = 
		"mailtracking.defaults.polflightopen";

	public static final String CON_ASSIGNEDTO_DIFFFLT = 
		"mailtracking.defaults.uldalreadyassignedtoflgt";

	public static final String CON_ASSIGNEDTO_DIFFDESTN = 
		"mailtracking.defaults.uldalreadyassignedtodestn";

	public static final String DESTN_ASSIGNED = 
		"mailtracking.defaults.uldassignedtodestn";

	public static final String INVALID_FLIGHT_SEGMENT = 
		"mailtracking.defaults.err.invalidflightsegment";

	public static final String ULD_ALREADY_IN_USE = 
		"mailtracking.defaults.err.uldalreadyinuse";
	
	public static final String ULD_NOT_RELEASED_FROM_INB_FLIGHT = 
		"mailtracking.defaults.err.uldnotreleasedfrominboundflight";

	public static final String ULD_ALREADY_IN_USE_AT_ANOTHER_PORT = 
		"mailtracking.defaults.err.uldalreadyinuseatanotherport";
	
	//Added by A-7540 for ICRD-307735
	public static final String DIFF_CONTAINER_TYPE = 
			"mailtracking.defaults.err.differentcontainerType";
	
	public static final String ULD_ASSIGNED_IN_A_CLOSED_FLIGHT_BUT_IMPORT_OPERATION_MISSING = 
			"mailtracking.defaults.uldalreadyassignedtoanotherClosedflight";
	
	/** 
	 * The Constructor
	 */
	public ContainerAssignmentException() {
		super();
	}

	/**
	 * @param errorCode
	 * @param exceptionCause
	 */
	public ContainerAssignmentException(String errorCode,
			Object[] exceptionCause) {
		super(errorCode, exceptionCause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 */
	public ContainerAssignmentException(String errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param ex
	 */
	public ContainerAssignmentException(BusinessException ex) {
		super(ex);
		// TODO Auto-generated constructor stub
	}
}
