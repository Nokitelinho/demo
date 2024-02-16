/*
 * MailTrackingDefaultsServiceImpl.java Created on 20-03-2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.errorhandling;






import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

public class MailHHTBusniessException extends BusinessException {
	
	    public static final String INVALID_FLIGHT ="Invalid Flight";
	    public static final String INVALID_MAILFORAMT ="The MailBag format is invalid";
	  	public static final String DUPLICATE_MailBAG_EXCEPTION ="Duplicate Mailbag";
		public static final String FLIGHT_CLOSED_EXCEPTION ="The flight is closed for all mail operations";
		public static final String INB_FLIGHT_CLOSED_EXCEPTION ="Import flight is closed for all mail operations";
		public static final String INVALID_FLIGHT_SEGMENT_EXCEPTION ="Invalid flightSegment";
		public static final String DUPLICATE_DSN_EXCEPTION ="Duplicate DSN";
		public static final String CONTAINER_ARRIVED_EXCEPTION ="The Container is already arrived";
		public static final String CONTAINER_NOT_PRESENT_EXCEPTION ="The container is not present in the flight";
		public static final String MAILBAG_NOT_ARRIVED_EXCEPTION ="The mailbag is not arrived";
		public static final String INVALID_CARRIERCODE_EXCEPTION ="Invalid carrier code";
		public static final String INVALID_TRANSACTION_EXCEPTION ="Invalid Transaction/Process point";
		public static final String MAILBAG_RETUEN_EXCEPTION ="The mailbag is returned one";
		public static final String INVALID_LOGIN_PORT_EXCEPTION ="Current port for this mailbag is different from login port";
		public static final String MAILBAG_DELIVERED_EXCEPTION ="The mailbag is already delivered ";
		public static final String FLIGHT_NOT_CLOSED_EXCEPTION ="The flight is not closed";
		public static final String INVALID_DELIVERY_EXCEPTION ="The mailbag can't be delivered at this port";
		public static final String MAILBAG_TRANSFERRED_EXCEPTION ="The mailbag is already transferred ";
		public static final String INVALID_OFFICEOFEXCHANGE ="Invalid office of exchange";
		public static final String INVALID_SUBCLASS ="Invalid Subclass";
		public static final String MAILBAG_NOT_FOUND_EXCEPTION ="Mailbag doesn't exist";
		public static final String ULD_NOT_FOUND_EXCEPTION ="ULD not manifested in any flight";  
		public static final String INVALID_FLIGHT_SEGMENT ="Export flight details not present for the mailbag.";
		public static final String CONTAINER_ASSIGNMENT_EXCEPTION ="Invalid Flight segment";  
		public static final String ULD_DEFAULT_PROXY_EXCEPTION ="ULD Default proxy exception";
		public static final String CAPACITY_BOOKING_EXCEPTION ="Capacity booking proxy exception";
		public static final String MAIL_BOOKING_EXCEPTION ="Mail booking proxy exception";
		public static final String INVALIDFLIGHT_SEG_EXCEPTION ="Invalid Flight segment";
		
		public static final String FLIGHT_DEPARTED_EXCEPTION ="Flight is departed";
		public static final String REASSIGNMENT_EXCEPTION ="Mailbag is reassigned";
		public static final String INVALID_ARRIVAL_EXCEPTION ="Invalid Arrival ";
		public static final String RETURN_NOT_POSSIBLE_EXCEPTION ="Return not possible ";
		public static final String MAILBAG_ALREADY_ARRIVAL_EXCEPTION ="Mailbag is already arrived";
		public static final String MAILBAG_ALREADY_OFFLOADED_EXCEPTION ="Mailbag is already offloaded";
		public static final String MAILBAG_NOT_AVAILABLE_EXCEPTION ="Mailbag is not available";
		public static final String MAILBAG_ALREADY_TRANSFERRED_EXCEPTION ="Mailbag is already transferred";
		public static final String EXCHANGE_OFFICE_SAME_EXCEPTION ="Orgin office of exchange can't be same as Destination office of exchange";
		public static final String INVALID_MAIL_CATEGORY_EXCEPTION ="Invalid mail Category";
		public static final String INVALID_HNI_EXCEPTION ="Invalid Highest Number indicator value. HNI can be either 0,1 or 9";
		public static final String INVALID_RI_EXCEPTION ="Invalid Registered/Insured Indicator value. RI can be either 0,1 or 9";
		public static final String INVALID_OFFLOAD_FROM_PA_BUILT_ULD ="Mailbag cannot be offloaded from a PA built ULD.";
		public static final String INVALID_DESTINATION ="Invalid Destination";
		public static final String INVALID_POU ="Invalid POU";
		 public static final String INVALID_ULD_FORMAT= "ULD format is invalid";    
		public static final String ULD_NOT_EXIST= "ULD Does'nt exist in the system";
		public static final String MAIL_ALREADY_MANIFESTED ="Mailbag already manifested in another flight" ;
		public static final String MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT = "Cannot Reassign DefaultStorageUnit:SINSTR";
		public static final String INVALID_TRANSFER_EXCEPTION ="Transfer is possible only for arrived mailbags. POU for Mailbags must be the current port.";
		//Added as part of BUg ICRD-95014
		public static final String INVALID_TRANSFER_SCENARIO_EXCEPTION ="Invalid transfer.Destination same as current airport";    
		public static final String CONTAINER_CANNOT_ASSIGN ="This container is not assigned in any flight";
		public static final String UPLOAD_EXCEPT_CONT_ALRDY_EXST_IN_SAME_FLT_WIT_DIFF_CONTYPE ="This container is already present in the same flight with different container type";
		public static final String INVALID_CONTAINER_REUSE ="There are recepticles assigned in the container in another flight.";
		public static final String INVALID_ACCEPTANCETO_ARRIVEDCONTAINER ="This Container is already Arrived at destination.";
		//Added as part of bug ICRD-95657 by A-5526 starts
		//Modified as part of bug ICRD-141152 by A-5526
		public static final String INVALID_DESTINATION_UPDATION ="Container destination cannot be changed ";
		public static final String INVALID_POU_UPDATION ="Wrong POU given";
		//Added as part of bug ICRD-95657 by A-5526 ends
		public static final String DESTN_ASSIGNED ="Container is Assigned to another destination";
		public static final String OFFLOAD_FROM_CARRIER_EXCEPTION ="Mailbag from carrier cannot be offloaded";
		public static final String NON_PARTNER_CARRIER="Cannot reassign from a non Partner Carrier";
		//added for ICRD-117307 
		public static final String RETURN_NOT_POSSIBLE_AT_IMPORT="Mailbag can be returned only from its accepted port";
		//Added by A-5945 for ICRD-113473 starts
		public static final String INVALID_TRANSFERCARRIERCODE_EXCEPTION = "Invalid Transfer Carrier";
		//Added by A-5945 for ICRD-113473 ends
		public static final String INVALID_FLIGHT_POL = "Wrong POL entered, POL of the flight would be ";
		public static final String CONTAINER_ALREADY_EXISTS = "The container mentioned already exists in the flight with POL ";
		public static final String INVALID_POL = "Invalid POL for the container";
		public static final String INVALID_MAIL_COMPANY_CODE = "Invalid Mail company code";
		
		public static final String WRONG_CONTAINER_TYPE_GIVEN_ULD = "Container type is given wrong as ULD";
		public static final String WRONG_CONTAINER_TYPE_GIVEN_BULK = "Container type is given wrong as Bulk";
		public static final String CONTAINER_NOT_AVAILABLE_AT_AIRPORT = "Container is not available at this airport";
		public static final String MAIL_RETURN_CODE_IS_MANDATORY = "Return Code is mandatory For return";
		public static final String CANNOT_ASSIGN_CONTAINER = "Only Containers assigned to a flight can be offloaded";
		public static final String INVALID_OFFLOAD_REASON_CODE = "Invalid Offload reason code";  
		//Added by A-7531 as part of ICRD-211308 starts
		public static final String MAIL_DAMAGE_CODE_IS_MANDATORY = "Damage Code is mandatory For damaged mail bags";
		public static final String INVALID_RETURN_REASON_CODE= "Invalid Return reason code";
		public static final String INVALID_DAMAGE_REASON_CODE= "Invalid Damage reason code";
    	//Added by A-7531 as part of ICRD-211308 ends
		public static final String CANCELLED_FLIGHT= "The Flight is in To be actioned/To be cancelled/cancelled status";
		
		
		//Added by A-7540
		public static final String LAT_VIOLATED_ERR = "mailtracking.defaults.err.latvalidation";
		public static final String LAT_VIOLATED_WAR = "mailtracking.defaults.war.latvalidation";
		
	     //added by a-7871 for ICRD-227884
        public static final String CONTAINERPOU_MAILBAGDOE_DIFFERENT="POU/destination of the container is different from the airport mapped to the ‘destination office of exchange’ of the mailbag";
       //Modified the warning message by A-7540 for ICRD-344222
        public static final String NOT_A_COTERMINUSAIRPORT="Invalid delivery  airport";//added A-8353 for ICRD-335689
        public static final String LAT_ERROR ="Latest acceptance time is crossed for the flight" ;
        public static final String LAT_WARNING ="Latest acceptance time is crossed for the flight.Do you want to continue?";
        public static final String INVALIDACPAIRPOT_RCV_FRM_TRUCK_WARNING ="Invalid acceptance airport";//Added by a-7871 for ICRD-240184
        public static final String FLIGHT_CLOSED_WARNING= "The flight is closed for all mail operations";
        public static final String FLIGHT_NOT_EXIST="The specified Flight does not exist";  
	    
        //Added by A-7540
		public static final String INVALID_BULK_FORMAT = "mailtracking.defaults.mailacceptance.invalidbulkformat";
		public static final String IMPORT_FLIGHT_MANDATORY = "Import flight details are mandatory for mailbag arrival";//Added by A-8164 for ICRD-330543
		public static final String OOE_NOT_CONFIGURED_FOR_GPA_AGAINST_ORG = "Office of Exchange is not configured for GPA against Airport";//Added for ICRD-331381
		public static final String DOE_NOT_CONFIGURED_FOR_GPA_AGAINST_DEST = "Office of Exchange is not configured for GPA against Airport";//Added for ICRD-331381
		public static final String RETURN_NOT_POSSIBLE_AT_THIS_PORT="Return is not possible from this port";//added by A-8553 for ICRD-333442
		public static final String RETURN_NOT_POSSIBLE_AT_DEST="Return is not possible from Destination or its Co-Terminus port";//added by A-8553 for ICRD-334511
		public static final String OFFLOAD_OF_ULD_AS_BARROW_NOT_POSSIBLE="Manifested trolley/barrow cannot be offloaded";//added by A-8553 for ICRD-346821
		public static final String ULD_AS_BULK_IN_SAME_CONTAINER="ULD is already available in the flight with different container type";//added by A-8353 for ICRD-346821
		//Added by A-7540
		public static final String PLAN_ROUTE_MISSING = "Plan Route missing";
		public static final String INVALID_POU_DESTINATION ="Invalid POU/Destination";
		public static final String OFFLOAD_NON_ACCEPTED_MAILBAG="Only accepted mailbags can be offloaded";
		
		public static final String MALBAG_OBSCAN_NOT_ALLOWED_DST ="Outbound scan not allowed at Destination airport";
		public static final String INVALID_CONTAINERLEVEL_FOR_ARR_OR_DLV="The container contains mailbags already undergone inbound transactions";//added as part of IASCB-47333
		public static final String ULD_IS_NOT_IN_AIRPORT= "The ULD is not present at this port";
        public static final String CONTAINER_REASSIGN_FLIGHT="Container is already assigned to flight ";
        public static final String CONTAINER_REASSIGN_ERROR="Data already modified by user.";
		public static final String ULD_IS_NOT_OPERATIONAL = " The ULD is not operational ";
        public static final String CONTAINER_INBOUND_OPERATION_SUCESSS="Container level arrival/delivery is sucessfully saved";
        public static final String INVALID_PA ="Invalid Postal Authority";
        public static final String FLIGHT_MANDATORY_FOR_ARRIVAL_SCAN_CODE ="mail.operations.flightmandatoryforarrival";
        public static final String FLIGHT_MANDATORY_FOR_ARRIVAL_SCAN_DESCRIPTION ="Flight details are mandatory for arrival scan";
        public static final String PLAN_ROUTE_MISSING_FOR_DLV ="Planned route file is missing";
        public static final String ULD_NOT_RELEASED_FROM_INBFLIHGT="ULD is not yet released";
        public static final String CONTAINER_NOTIN_INVENTORY="ULD Not in Inventory";
        public static final String MAIL_OPERATIONS_ATD_MISSING ="ATD is missing from the preceding airport";
        public static final String MAILTRACKING_DEFAULTS_ERROR_CONTAINERIDNOTAVAILABLE = "Container ID not available in the system";
        public static final String MAILTRACKING_INVALID_ULD_INDICATOR = "Invalid ULD Indicator";
        public static final String APPLICABLE_REGULATION_ERROR_DESC="Expected AR Flag value missing for EU customs applicable consignment";
        public static final String MAIL_STORAGE_UNIT_NOT_FOUND_ERR_CODE="Storage Unit not Valid";
        
    	public static final String ULD_NOT_IN_CURRENT_AIRPORT = "mailtracking.defaults.mld.uldnotincurrentairport";
    	public static final String PLAN_ROUTE_MISSING_FOR_MAILBAG_SCREENING = "Screening cannot captured as CARDIT/Plan Route file is missing";
	    /**
		 * @param errorCode
		 * @param exceptionCause
		 */
		public MailHHTBusniessException(String errorCode, Object[] exceptionCause) {
			super(errorCode, exceptionCause);
			// To be reviewed Auto-generated constructor stub
		}

		/**
		 * @param errorCode
		 */
		public MailHHTBusniessException(String errorCode) {
			super(errorCode);
			// To be reviewed Auto-generated constructor stub
		}

		public Collection<MailbagVO> getErrorsForGhs() {
			return this.errorsForGhs;
		}
		public void setErrorsForGhs(Collection<MailbagVO> errorsForGhs) {
			this.errorsForGhs = errorsForGhs;
		}
		/**
		 * @param businessException
		 */
		public MailHHTBusniessException(BusinessException businessException) {
			super(businessException);
			// To be reviewed Auto-generated constructor stub
		}
		private Collection<MailbagVO> errorsForGhs;

	public MailHHTBusniessException(String errorCode,  String errorDescription) {

		super(errorCode, new Object[0] ,errorDescription);
		// To be reviewed Auto-generated constructor stub
	}
}
