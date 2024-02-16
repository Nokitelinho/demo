/*
 * MailMLDBusniessException.java Created on 07-Nov-2014
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
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4803	:	07-Nov-2014	:	Draft
 *@author A-4803
 */
public class MailMLDBusniessException extends BusinessException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	public static final String ULDBULK_ANOTHER_FLIGHT = 
		"mailtracking.defaults.uldbulkpresentinanotherflight";
	/** The Constant INVALID_FLIGHT. */
	public static final String CONTAINER_CANNOT_ASSIGN_IN_FLIGHT = "mailtracking.defaults.containercannotassigninthisflight";
	public static final String CONTAINER_CANNOT_ASSIGN = "mailtracking.defaults.containercannotassign";
	public static final String CONTAINER_ALREADY_ARRIVEDT = "mailtracking.defaults.containerarrived";
	/** The Constant INVALID_MAILIDFORMAT. */
	public static final String INVALID_MAILTAG = "mailtracking.defaults.invalidmailtag";
	public static final String ULD_VALID_FAILED = "mailtracking.defaults.uldvalidationfailed";
	/** The Constant DUPLICATE_MailBAG_EXCEPTION. */
	public static final String DUPLICATE_MAILBAG_EXCEPTION = "mailtracking.defaults.duplicatemail";
	public static final String FLIGHT_TBA_TBC = "mailtracking.defaults.flighttbatbc";
//Added ass part of CR ICRD-89077 starts
	public static final String MAILBAG_PRESENT ="mailtracking.defaults.mailbagpresent"; 
	public static final String MAILBAG_NOT_PRESENT ="mailtracking.defaults.mailbagnotpresent";
	public static final String MAILBAG_NOT_PRESENT_UPL ="mailtracking.defaults.mailbagnotpresentforupl";
	
	
//Added ass part of CR ICRD-89077 ends
	//Added as part of bug ICRD-143638 by A-5526 for MLD-ALL message starts
	public static final String CONTAINER_NOT_AVAILABLE ="mailtracking.defaults.containerdoesnotexistinairport";
	//Added as part of bug ICRD-143638 by A-5526 for MLD-ALL message ends
	/** The Constant FLIGHT_CLOSED_EXCEPTION. */
	public static final String FLIGHT_CLOSED_EXCEPTION = "mailtracking.defaults.flightclosed";
	public static final String MAILBAG_FLIGHT_CLOSED= "mailtracking.defaults.mailbagflightclosed";
	/** The Constant INVALID_FLIGHT_SEGMENT_EXCEPTION. */
	public static final String INVALID_FLIGHT_SEGMENT_EXCEPTION = "mailtracking.defaults.invalidflightsegment";
	public static final String MAIL_CANNOT_OFFLOAD = "mailtracking.defaults.cannotoffload";
	public static final String INVALID_OFFLOAD_ULD_TYPE = "mailtracking.defaults.invalidoffloaduldtype";
	public static final String INVALID_OFFLOAD_REASONCODE = "mailtracking.defaults.invalidoffloadreasoncode";
	public static final String INVALID_POU = "msgbroker.message.invalidpouforflight";
	/** The Constant DUPLICATE_DSN_EXCEPTION. */
	public static final String DUPLICATE_DSN_EXCEPTION = "mailtracking.defaults.duplicatedsn";
	public static final String INVALID_POL = "msgbroker.message.invalidpolforflight";
	public static final String MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT = 
		"mailtracking.defaults.defaultstorageunit";
	/** The Constant CONTAINER_ARRIVED_EXCEPTION. */
	//public static final String CONTAINER_ARRIVED_EXCEPTION = "mailtracking.defaults.flightarrived";
	public static final String FLIGHT_STATUS_POL_OPEN = "mailtracking.defaults.polflightopen";
	/** The Constant CONTAINER_NOT_PRESENT_EXCEPTION. */
	public static final String CONTAINER_NOT_PRESENT_EXCEPTION =  "mailtracking.defaults.containerexception";
	
	/** The Constant MAILBAG_NOT_ARRIVED_EXCEPTION. */
	//public static final String MAILBAG_NOT_ARRIVED_EXCEPTION = "mailtracking.defaults.mailbagnotarrived";
	
	/** The Constant INVALID_CARRIERCODE_EXCEPTION. */
	public static final String INVALID_CARRIERCODE_EXCEPTION = "mailtracking.defaults.invalidcarrier";
	
	/** The Constant INVALID_TRANSACTION_EXCEPTION. */
	public static final String INVALID_TRANSACTION_EXCEPTION = "mailtracking.defaults.invalidtxn";
	
	/** The Constant MAILBAG_RETUEN_EXCEPTION. */
	public static final String MAILBAG_RETURN_EXCEPTION = "mailtracking.defaults.returnedmailbag";
	
	/** The Constant INVALID_LOGIN_PORT_EXCEPTION. */
	public static final String INVALID_LOGIN_PORT_EXCEPTION = "mailtracking.defaults.invalidloginport";
	
	/** The Constant MAILBAG_DELIVERED_EXCEPTION. */
	public static final String MAILBAG_DELIVERED_EXCEPTION = "mailtracking.defualts.mailbagdelivered";
	
	/** The Constant FLIGHT_NOT_CLOSED_EXCEPTION. */
	public static final String FLIGHT_NOT_CLOSED_EXCEPTION = "mailtracking.defaults.flightnotclosed";
	
	/** The Constant INVALID_DELIVERY_EXCEPTION. */
	public static final String INVALID_DELIVERY_EXCEPTION = "mailtracking.defaults.mld.cantdeliverport";
	
	/** The Constant MAILBAG_TRANSFERRED_EXCEPTION. */
	//public static final String MAILBAG_TRANSFERRED_EXCEPTION = "mailtracking.defaults.mailbagtransfered";
	
	/** The Constant INVALID_OFFICEOFEXCHANGE. */
	public static final String INVALID_OFFICEOFEXCHANGE = "mailtracking.defaults.invalidoe";
	
	/** The Constant INVALID_SUBCLASS. */
	public static final String INVALID_SUBCLASS = "mailtracking.defaults.invalidsubclass";
	
	/** The Constant MAILBAG_NOT_FOUND_EXCEPTION. */
	public static final String MAILBAG_NOT_FOUND_EXCEPTION = "mailtracking.defaults.mailbagnotfound";
	
	/** The Constant ULD_NOT_FOUND_EXCEPTION. */
	public static final String ULD_NOT_FOUND_EXCEPTION = "mailtracking.defaults.uldnotfound";
	
	/** The Constant INVALID_FLIGHT_SEGMENT. */
	public static final String INVALID_FLIGHT_SEGMENT = "mailtracking.defaults.invalidflightsegment";
	
	/** The Constant CONTAINER_ASSIGNMENT_EXCEPTION. */
	public static final String CONTAINER_ASSIGNMENT_EXCEPTION = "mailtracking.defaults.containerassigmentexception";
	
	/** The Constant ULD_DEFAULT_PROXY_EXCEPTION. */
	public static final String ULD_DEFAULT_PROXY_EXCEPTION = "mailtracking.defaults.uldproxyexception";
	
	/** The Constant CAPACITY_BOOKING_EXCEPTION. */
	public static final String CAPACITY_BOOKING_EXCEPTION = "mailtracking.defaults.capacitybookingproxyexception";
	
	/** The Constant MAIL_BOOKING_EXCEPTION. */
	public static final String MAIL_BOOKING_EXCEPTION = "mailtracking.defaults.mailbookingproxyexception";
	
	/** The Constant FLIGHT_DEPARTED_EXCEPTION. */
	public static final String FLIGHT_DEPARTED_EXCEPTION = "mailtracking.defaults.flightdeparted";
	
	/** The Constant REASSIGNMENT_EXCEPTION. */
	public static final String REASSIGNMENT_EXCEPTION = "mailtracking.defaults.reassignedmailbag";
	
	/** The Constant INVALID_ARRIVAL_EXCEPTION. */
	public static final String INVALID_ARRIVAL_EXCEPTION = "mailtracking.defaults.invalidarrival";
	
	/** The Constant RETURN_NOT_POSSIBLE_EXCEPTION. */
	public static final String RETURN_NOT_POSSIBLE_EXCEPTION = "mailtracking.defaults.returnnotpossible";
	
	/** The Constant MAILBAG_ALREADY_ARRIVAL_EXCEPTION. */
	public static final String MAILBAG_ALREADY_ARRIVAL_EXCEPTION = "mailtracking.defaults.alreadyarrived";
	
	/** The Constant MAILBAG_ALREADY_OFFLOADED_EXCEPTION. */
	public static final String MAILBAG_ALREADY_OFFLOADED_EXCEPTION = 
		"mailtracking.defaults.alreadyoffloaded";
	
	/** The Constant MAILBAG_NOT_AVAILABLE_EXCEPTION. */
	public static final String MAILBAG_NOT_AVAILABLE_EXCEPTION = 
		"mailtracking.defaults.mailbagunavailable";
	
	/** The Constant MAILBAG_ALREADY_TRANSFERRED_EXCEPTION. */
	public static final String MAILBAG_ALREADY_TRANSFERRED_EXCEPTION = 
		"mailtracking.defaults.mailbagtransferred";
	
	/** The Constant EXCHANGE_OFFICE_SAME_EXCEPTION. */
	public static final String EXCHANGE_OFFICE_SAME_EXCEPTION = "mailtracking.defaults.oesame";
	
	/** The Constant INVALID_MAIL_CATEGORY_EXCEPTION. */
	public static final String INVALID_MAIL_CATEGORY_EXCEPTION = 
		"mailtracking.defaults.mld.invalidcategory";
	
	/** The Constant INVALID_HNI_EXCEPTION. */
	public static final String INVALID_HNI_EXCEPTION = "mailtracking.defaults.mld.invalidhni";
	
	/** The Constant INVALID_RI_EXCEPTION. */
	public static final String INVALID_RI_EXCEPTION = "mailtracking.defaults.mld.invalidri";
	
	/** The Constant INVALID_OFFLOAD_FROM_PA_BUILT_ULD. */
	public static final String INVALID_OFFLOAD_FROM_PA_BUILT_ULD = 
		"mailtracking.defaults.mld.invalidoffload";
	
	/** The Constant INVALID_DESTINATION. */
	public static final String INVALID_DESTINATION = "mailtracking.defaults.mld.invaliddestination";
	
	/** The Constant INVALID_ULD_FORMAT. */
	public static final String INVALID_ULD_FORMAT = "mailtracking.defaults.mld.invaliduld";
	
	//Added by A-7540
	public static final String INVALID_BULK_FORMAT = "mailtracking.defaults.mailacceptance.invalidbulkformat";
	
	/** The Constant ULD_NOT_EXIST. */
	//public static final String ULD_NOT_EXIST = "mailtracking.defaults.mld.uldnotpresent";
	
	/** The Constant MAIL_ALREADY_MANIFESTED. */
	public static final String MAIL_ALREADY_MANIFESTED = "mailtracking.defaults.mld.alreadymanifested";

	/** The Constant INVALID_CONTAINER_REUSE.  */
	public static final String INVALID_CONTAINER_REUSE = "mailtracking.defaults.mld.invalidcontainerreuse";
	
	/** The Constant INVALID_TRANSFER_SCENARIO_EXCEPTION. Added as part of Bug ICRD-95014 */
	public static final String INVALID_TRANSFER_SCENARIO_EXCEPTION = "mailtracking.defaults.mld.invalidtransfer";
	
	/** The Constant INVALID_DESTINATION_UPDATION. Added as part of Bug ICRD-95657 */
	public static final String INVALID_DESTINATION_UPDATION = "mailtracking.defaults.mld.invaliddestinationupdation";
	/** The Constant INVALID_POU_UPDATION. Added as part of Bug ICRD-95657 */
	public static final String INVALID_POU_UPDATION = "mailtracking.defaults.mld.invalidpouupdation";
	
	/** The Constant ARRIVED_FLIGHT_EXCEPTION. Added as part of Bug ICRD-98885 */
	public static final String ARRIVED_FLIGHT_EXCEPTION = "mailtracking.defaults.mld.arrivedflightdifferent";

	public static final String DESTN_ASSIGNED ="mailtracking.defaults.mld.destassignedcontainer";
	
	public static final String OFFLOAD_FROM_CARRIER_EXCEPTION ="mailtracking.defaults.mld.carrier.offloadexception";
	
	public static final String NON_PARTNER_CARRIER="mailtracking.defaults.mld.non_partner_carrier";
	
	public static final String WRONG_CONTAINER_TYPE_GIVEN ="mailtracking.defaults.mld.wrong.containertype";
	//Added by A-5945 for ICRD-113473 starts	
	public static final String INVALID_TRANSFERCARRIERCODE_EXCEPTION = "mailtracking.defaults.mld.invalid.transferCarrrier";
	public static final String TRANSFER_WITHOUT_ARRIVAL = "mailtracking.defaults.err.cannotTransferWithoutMailArrival";
    // Added by A-5945 for ICRD-113473 ends
	
	public static final String INVALID_MAIL_COMPANY_CODE = "mailtracking.defaults.mld.invalid.mailCompanyCode";
	public static final String MAIL_RETURN_CODE_IS_MANDATORY = "mailtracking.defaults.err.returnCodeismandatoryForReturn";
	//Added by A-7531 as part of ICRD-211308 starts
	public static final String MAIL_DAMAGE_CODE_IS_MANDATORY = "mailtracking.defaults.err.damageCodeismandatoryForDamage";
	public static final String MAIL_CANNOT_RETURN = "mailtracking.defaults.cannotreturn";
	public static final String MAIL_CANNOT_DAMAGED= "mailtracking.defaults.cannotdamaged";
	//Added by A-7531 as part of ICRD-211308 ends
	//Added as part of ICRD-229584 starts
		public static final String MAILBAG_NOT_ARRIVED="mailtracking.defaults.mailbagnotarrived";
		public static final String MANIFESTIFO_MISSING="mailtracking.defaults.manifestinfomissing";
		//Added as part of ICRD-229584 ends
	//Added by A-7540
		public static final String PLAN_ROUTE_MISSING = "Plan Route missing";
	
	//Added by A-8488 as part of ICRD-134563 starts
	public static final String MAILBAG_NOT_PRESENT_FOR_TFD ="mailtracking.defaults.mailbagnotpresentfortfd";
	
	public static final String MAILBAG_NOT_PRESENT_FOR_RCF ="mailtracking.defaults.mailbagnotpresentforrcf";
	public static final String MAILBAG_NOT_PRESENT_FOR_STG ="mailtracking.defaults.mailbagnotpresentforstg";
	public static final String MAILBAG_NOT_AVAILABLE_AT_PORT ="mailtracking.defaults.mailbagnotavailableatport";
	public static final String MAILBAG_NOT_ARRIVED_FOR_DLV ="mailtracking.defaults.cannotDeliverWithoutMailArrival";
	
	public static final String MALBAG_DLV_IMPFLT_MISSING ="mailtracking.defaults.impfltmandatorydlv";
	public static final String MALBAG_RET_IMPFLT_MISSING ="mailtracking.defaults.impfltmandatoryret";
	public static final String MALBAG_TRF_IMPFLT_MISSING ="mailtracking.defaults.impfltmandatorytrf";
	
	public static final String MALBAG_OBSCAN_NOT_ALLOWED_DST ="mailtracking.defaults.obscannotallowedatdest";
	public static final String INVALID_PA = "mailtracking.defaults.invalidpa";
	public static final String MAIL_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM = "mail.defaults.error.uldisnotinthesystem";
	public static final String MAILTRACKING_DEFAULTS_ERROR_CONTAINERIDNOTAVAILABLE = "mailtracking.defaults.err.containerIDnotavailableinsystem";
	
	public static final String ULD_NOT_IN_CURRENT_AIRPORT = "mailtracking.defaults.mld.uldnotincurrentairport";
	public static final String PLAN_ROUTE_MISSING_FOR_MAILBAG_SCREENING = "Screening cannot captured as CARDIT/Plan Route file is missing";
	//Added by A-8488 as part of ICRD-134563 ends
	/**
	 * Instantiates a new mail mld busniess exception.
	 *
	 * @param errorCode the error code
	 * @param exceptionCause the exception cause
	 */
	public MailMLDBusniessException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);

	}

	/**
	 * Instantiates a new mail mld busniess exception.
	 *
	 * @param errorCode the error code
	 */
	public MailMLDBusniessException(String errorCode) {
		super(errorCode);

	}

	/**
	 * Instantiates a new mail mld busniess exception.
	 *
	 * @param businessException the business exception
	 */
	public MailMLDBusniessException(BusinessException businessException) {
		super(businessException);

	}
	
}