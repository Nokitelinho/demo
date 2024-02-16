package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1936This class is used to create an exception to be thrown when checks for the ContainerAssignment are Done.
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
	public static final String FLIGHT_STATUS_POL_OPEN = "mailtracking.defaults.polflightopen";
	public static final String CON_ASSIGNEDTO_DIFFFLT = "mailtracking.defaults.uldalreadyassignedtoflgt";
	public static final String CON_ASSIGNEDTO_DIFFDESTN = "mailtracking.defaults.uldalreadyassignedtodestn";
	public static final String DESTN_ASSIGNED = "mailtracking.defaults.uldassignedtodestn";
	public static final String INVALID_FLIGHT_SEGMENT = "mailtracking.defaults.err.invalidflightsegment";
	public static final String ULD_ALREADY_IN_USE = "mailtracking.defaults.err.uldalreadyinuse";
	public static final String ULD_NOT_RELEASED_FROM_INB_FLIGHT = "mailtracking.defaults.err.uldnotreleasedfrominboundflight";
	public static final String ULD_ALREADY_IN_USE_AT_ANOTHER_PORT = "mailtracking.defaults.err.uldalreadyinuseatanotherport";
	public static final String DIFF_CONTAINER_TYPE = "mailtracking.defaults.err.differentcontainerType";
	public static final String ULD_ASSIGNED_IN_A_CLOSED_FLIGHT_BUT_IMPORT_OPERATION_MISSING = "mailtracking.defaults.uldalreadyassignedtoanotherClosedflight";

	/** 
	* The Constructor
	*/
	public ContainerAssignmentException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param exceptionCause
	*/
	public ContainerAssignmentException(String errorCode, Object[] exceptionCause) {
		super(errorCode, errorCode);
	}

	/** 
	* @param errorCode
	*/
	public ContainerAssignmentException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* @param ex
	*/
	public ContainerAssignmentException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}
}
