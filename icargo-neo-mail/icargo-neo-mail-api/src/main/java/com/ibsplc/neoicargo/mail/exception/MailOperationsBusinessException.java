package com.ibsplc.neoicargo.mail.exception;

import java.util.HashMap;
import java.util.List;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;

/** 
 * @author a-1936This class is used for all the BusinessExceptions 
 */
public class MailOperationsBusinessException extends BusinessException {
	public static final String MAILTACKING_DEFAULTS_NOREPORTDATA = "mailtracking.defaults.defaults.report.err.nodata";
	public static final String MAILTACKING_EXCEPTION_GENCSVRPT = "mailtracking.defaults.report.error.generatereport.failed";
	public static final String NO_PRE_ADV_CARDIT_FOUND = "mailtracking.defaults.nopreadvisefoundforcardit";
	public static final String INVALID_DELIVERY_AIRPORT = "mailtracking.defaults.InvalidDeliveryAirportException";
	public static final String MAILBAG_DOESNOTEXISTS = "mailtracking.defaults.nomailbagfound";
	public static final String MAILBAG_CANNOTBEDELETED = "mailtracking.defaults.mailbagcannotbedeleted";
	public static final String MAILTRACKING_NOMAILBAGSFOUNDFORDELIVERY = "mailtracking.defaults.nomailbagsFoundforDelivery";
	public static final String MAILTRACKING_NOMAILBAGSFOUNDFORARRIVAL = "mailtracking.defaults.nomailbagsFoundforArrival";
	public static final String MAILTRACKING_CONTAINERALREADYASSIGNED = "mailtracking.defaults.containerAlreadyAssignedtoSameDestination";
	public static final String MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT = "mailtracking.defaults.cannotAssignDefaultStorageUnitToFlight";
	public static final String MAILTRACKING_CONTAINERALREADYARRIVEDONE = "mailtracking.defaults.err.containerAlreadyArrived";
	public static final String MAILTRACKING_INVALIDMAILMAILBAG = "mailtracking.defaults.invalidmailformat";
	public static final String MAILTRACKING_TBATBCCANFLIGHT = "mailtracking.defaults.flighttbatbc";
	public static final String MAILTRACKING_TBATBCCANFLIGHTHHT = "The Flight is in To be actioned/To be cancelled/cancelled status";
	public static final String MAILTRACKING_INVALIDFLIGHT = "mailtracking.defaults.err.invalidFlight";
	public static final String MAILTRACKING_MAILBAGNOTAVAILABLE = "mailtracking.defaults.transfermail.mailbagnotavailableatairport";
	public static final String MAILTRACKING_MAIL_TRANSFERRED_OR_DELIVERED = "mailtracking.defaults.err.mailAlreadyTransferedOrDelivered";
	public static final String MAILTRACKING_MAIL_UNARRIVEDBAGS = "mailtracking.defaults.err.mailNotYetArrived";
	public static final String MAILTRACKING_ULD_NOTRELEASED = "mailtracking.defaults.err.uldnotreleased";
	public static final String MAILTRACKING_INVALID_ULD_INDICATOR = "mailtracking.defaults.err.invalidULDindicatorline";
	public static final String MAILTRACKING_CONTAINERID_MISSING = "mailtracking.defaults.err.containerIDMissing";
	public static final String MAIL_IMPORTED_TO_MRA = "mailtracking.defaults.mailoutbound.delete.mailbagsimportedtoMRA";
	public static final String MAILTRACKING_USPS_PA = "mailtracking.defaults.err.notUSPSPoa";
	public static final String INVALID_PAWB_STOCK = "mailtracking.defaults.err.invalidpawbstock";
	public static final String MANDATORY_FIELDS_IN_PAWB_MISSING = "mailtracking.defaults.err.mandatoryfieldsinpawbmissing";
	public static final String ORGIN_IN_PAWB_MISSING = "mailtracking.defaults.err.orgininpawbmissing";
	public static final String AGENT_IN_PAWB_MISSING = "mailtracking.defaults.err.agentinpawbmissing";
	public static final String DESTINATION_IN_PAWB_MISSING = "mailtracking.defaults.err.destinationinpawbmissing";
	public static final String CONSIGNEE_IN_PAWB_MISSING = "mailtracking.defaults.err.consigneeinpawbmissing";
	public static final String ORIGIN_DESTINATION_CONSIGNEE_IN_PAWB_MISSING = "mailtracking.defaults.err.origindestinationconsigneeinpawbmissing";
	public static final String ORIGIN_DESTINATION_AGENT_IN_PAWB_MISSING = "mailtracking.defaults.err.origindestinationagentinpawbmissing";
	public static final String DESTINATION_AGENT_CONSIGNEE_IN_PAWB_MISSING = "mailtracking.defaults.err.destinationagentconsigneeinpawbmissing";
	public static final String ORIGIN_DESTINATION_IN_PAWB_MISSING = "mailtracking.defaults.err.origindestinationinpawbmissing";
	public static final String ORIGIN_CONSIGNEE_IN_PAWB_MISSING = "mailtracking.defaults.err.originconsigneeinpawbmissing";
	public static final String ORIGIN_AGENT_IN_PAWB_MISSING = "mailtracking.defaults.err.originagentinpawbmissing";
	public static final String DESTINATION_AGENT_IN_PAWB_MISSING = "mailtracking.defaults.err.destinationagentinpawbmissing";
	public static final String DESTINATION_CONSIGNEE_IN_PAWB_MISSING = "mailtracking.defaults.err.destinationconsigneeinpawbmissing";
	public static final String AGENT_CONSIGNEE_IN_PAWB_MISSING = "mailtracking.defaults.err.agentconsigneeinpawbmissing";
	public static final String ORIGIN_AGENT_CONSIGNEE_IN_PAWB_MISSING = "mailtracking.defaults.err.originagentconsigneeinpawbmissing";
	public static final String ERROR_IN_CREATING_PAWB = "mailtracking.defaults.err.errorincreatingpawb";
	public static final String REQUIRED_INFO_FOR_CREATING_PAWB_MISSING = "mailtracking.defaults.err.requiredinfoforcreatingpawbmissing";
	public static final HashMap<String, String> errorMapping = new HashMap<String, String>();

    public MailOperationsBusinessException(List<ErrorVO> errors) {
        super(errors);
    }

    /**
	* @@author A-1885
	* @param value
	* @return
	*/
	public static String returnErrorMapping(String value) {
		errorMapping.put(MailHHTBusniessException.INVALID_FLIGHT, "mailtracking.defaults.err.invalidFlight");
		errorMapping.put(MailHHTBusniessException.INVALID_MAILFORAMT, "mailtracking.defaults.invalidmailformat");
		errorMapping.put(MailHHTBusniessException.DUPLICATE_MailBAG_EXCEPTION,
				"mailtracking.defaults.err.duplicateMailbag");
		errorMapping.put(MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION,
				"mailtracking.defaults.err.flightClosedForMailOperation");
		errorMapping.put(MailHHTBusniessException.INVALID_FLIGHT_SEGMENT_EXCEPTION,
				"mailtracking.defaults.err.invalidFlightSegment");
		errorMapping.put(MailHHTBusniessException.INVALIDFLIGHT_SEG_EXCEPTION,
				"mailtracking.defaults.err.invalidFlightSegment");
		errorMapping.put(MailHHTBusniessException.DUPLICATE_DSN_EXCEPTION, "mailtracking.defaults.err.duplicateDSN");
		errorMapping.put(MailHHTBusniessException.CONTAINER_ARRIVED_EXCEPTION,
				"mailtracking.defaults.err.containerAlreadyArrived");
		errorMapping.put(MailHHTBusniessException.CONTAINER_NOT_PRESENT_EXCEPTION,
				"mailtracking.defaults.err.containerNotPresentInFlight");
		errorMapping.put(MailHHTBusniessException.MAILBAG_NOT_ARRIVED_EXCEPTION,
				"mailtracking.defaults.err.mailbagNotArrived");
		errorMapping.put(MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION,
				"mailtracking.defaults.err.shared.airline.invalidairline");
		errorMapping.put(MailHHTBusniessException.MAILBAG_RETUEN_EXCEPTION,
				"mailtracking.defaults.err.mailbagIsAlreadyReturned");
		errorMapping.put(MailHHTBusniessException.MAILBAG_DELIVERED_EXCEPTION,
				"mailtracking.defaults.err.mailbagAlreadyDelivered");
		errorMapping.put(MailHHTBusniessException.FLIGHT_NOT_CLOSED_EXCEPTION,
				"mailtracking.defaults.err.flightNotClosed");
		errorMapping.put(MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION,
				"mailtracking.defaults.err.mailbagCannotBeDelivered");
		errorMapping.put(MailHHTBusniessException.MAILBAG_TRANSFERRED_EXCEPTION,
				"mailtracking.defaults.err.mailbagIsAlreadyTransfered");
		errorMapping.put(MailHHTBusniessException.INVALID_OFFICEOFEXCHANGE,
				"mailtracking.defaults.err.invalidOfficeOfExchange");
		errorMapping.put(MailHHTBusniessException.INVALID_SUBCLASS, "mailtracking.defaults.err.invalidSubClass");
		errorMapping.put(MailHHTBusniessException.MAILBAG_NOT_FOUND_EXCEPTION,
				"mailtracking.defaults.err.mailbagNotFound");
		errorMapping.put(MailHHTBusniessException.ULD_NOT_FOUND_EXCEPTION, "mailtracking.defaults.err.uldNotFound");
		errorMapping.put(MailHHTBusniessException.CONTAINER_ASSIGNMENT_EXCEPTION,
				"mailtracking.defaults.err.invalidFlightSegment");
		errorMapping.put(MailHHTBusniessException.FLIGHT_DEPARTED_EXCEPTION,
				"mailtracking.defaults.err.flightIsDeparted");
		errorMapping.put(MailHHTBusniessException.REASSIGNMENT_EXCEPTION,
				"mailtracking.defaults.err.mailbagIsReassigned");
		errorMapping.put(MailHHTBusniessException.INVALID_ARRIVAL_EXCEPTION,
				"mailtracking.defaults.err.invalidArrival");
		errorMapping.put(MailHHTBusniessException.RETURN_NOT_POSSIBLE_EXCEPTION,
				"mailtracking.defaults.err.returnCannotbeDone");
		errorMapping.put(MailHHTBusniessException.MAILBAG_ALREADY_ARRIVAL_EXCEPTION,
				"mailtracking.defaults.err.mailbagAlreadyArrived");
		errorMapping.put(MailHHTBusniessException.MAILBAG_NOT_AVAILABLE_EXCEPTION,
				"mailtracking.defaults.err.mailbagNotFound");
		errorMapping.put(MailHHTBusniessException.MAILBAG_ALREADY_TRANSFERRED_EXCEPTION,
				"mailtracking.defaults.err.mailbagIsAlreadyTransfered");
		errorMapping.put(MailHHTBusniessException.EXCHANGE_OFFICE_SAME_EXCEPTION,
				"mailtracking.defaults.err.originOEAndDestinationOESame");
		errorMapping.put(MailHHTBusniessException.INVALID_MAIL_CATEGORY_EXCEPTION,
				"mailtracking.defaults.err.invalidMailCategory");
		errorMapping.put(MailHHTBusniessException.INVALID_HNI_EXCEPTION, "mailtracking.defaults.err.invalidHNI");
		errorMapping.put(MailHHTBusniessException.INVALID_RI_EXCEPTION, "mailtracking.defaults.err.invalidRI");
		errorMapping.put(MailHHTBusniessException.INVALID_DESTINATION, "mailtracking.defaults.err.invalidDestination");
		errorMapping.put(MailHHTBusniessException.INVALID_ULD_FORMAT, "mailtracking.defaults.err.Invalid ULD");
		errorMapping.put(MailHHTBusniessException.ULD_NOT_EXIST, "mailtracking.defaults.err.uldNotFound");
		errorMapping.put(MailHHTBusniessException.MAIL_ALREADY_MANIFESTED,
				"mailtracking.defaults.err.mailbagAlreadyManifestedOnAnotherFlight");
		errorMapping.put(MailOperationsBusinessException.MAILTRACKING_CONTAINERALREADYARRIVEDONE,
				"mailtracking.defaults.err.containerAlreadyArrived");
		errorMapping.put(MailOperationsBusinessException.MAILTRACKING_TBATBCCANFLIGHTHHT,
				"The Flight is in To be actioned/To be cancelled/cancelled status");
		errorMapping.put(MailOperationsBusinessException.MAILTRACKING_TBATBCCANFLIGHT,
				"The Flight is in To be actioned/To be cancelled/cancelled status");
		errorMapping.put(MailHHTBusniessException.INVALID_TRANSFER_EXCEPTION,
				"Transfer is possible only for arrived mailbags. POU for Mailbags must be the current port.");
		errorMapping.put(MailOperationsBusinessException.MAILTRACKING_INVALIDFLIGHT,
				"mailtracking.defaults.err.invalidFlight");
		errorMapping.put(MailHHTBusniessException.INVALID_TRANSFER_SCENARIO_EXCEPTION,
				"Invalid transfer.Destination same as current airport");
		errorMapping.put(MailHHTBusniessException.WRONG_CONTAINER_TYPE_GIVEN_ULD,
				"mailtracking.defaults.err.wrongContainerTypeULD");
		errorMapping.put(MailHHTBusniessException.WRONG_CONTAINER_TYPE_GIVEN_BULK,
				"mailtracking.defaults.err. wrongContainerTypeBulk");
		errorMapping.put(MailHHTBusniessException.CONTAINER_NOT_AVAILABLE_AT_AIRPORT,
				"mailtracking.defaults.err.containerisnotavailable");
		errorMapping.put(MailHHTBusniessException.INVALID_TRANSACTION_EXCEPTION,
				"mailtracking.defaults.err.invalidEventCode");
		errorMapping.put(MailHHTBusniessException.NON_PARTNER_CARRIER,
				"mailtracking.defaults.err.cannotReassignfromNonPartnerCarrier");
		errorMapping.put(MailHHTBusniessException.INVALID_FLIGHT_SEGMENT,
				"mailtracking.defaults.err.invalidflightsegment");
		errorMapping.put(MailHHTBusniessException.MAILTRACKING_DEFAULTSTORAGEUNITNOTASSIGNEDTOFLIGHT,
				"mailtracking.defaults.err.cannotreassignDefaultStorage");
		errorMapping.put(MailHHTBusniessException.ULD_DEFAULT_PROXY_EXCEPTION,
				"mailtracking.defaults.err.uldDefaultProxyException");
		errorMapping.put(MailHHTBusniessException.CAPACITY_BOOKING_EXCEPTION,
				"mailtracking.defaults.err.capacityBookingException");
		errorMapping.put(MailHHTBusniessException.MAIL_BOOKING_EXCEPTION,
				"mailtracking.defaults.err.mailBookingException");
		errorMapping.put(MailHHTBusniessException.DESTN_ASSIGNED,
				"mailtracking.defaults.err.containerisAssignedToAnotherDestination");
		errorMapping.put("Flight is not closed at POL", "mailtracking.defaults.err.flightIsNotClosedAtPol");
		errorMapping.put(MailHHTBusniessException.CONTAINER_CANNOT_ASSIGN,
				"mailtracking.defaults.err.containerIsNotYetAssigned");
		errorMapping.put("SystemException Exception", "mailtracking.defaults.err.undefinedError");
		errorMapping.put("RemoteException Exception", "mailtracking.defaults.err.undefinedError");
		errorMapping.put("System Exception", "mailtracking.defaults.err.undefinedError");
		errorMapping.put(MailHHTBusniessException.UPLOAD_EXCEPT_CONT_ALRDY_EXST_IN_SAME_FLT_WIT_DIFF_CONTYPE,
				"mailtracking.defaults.err.containerIsPresentInTheSameFlightWithDifferentType");
		errorMapping.put(MailHHTBusniessException.INVALID_CONTAINER_REUSE,
				"mailtracking.defaults.err.receptaclesAreThereInTheSameContainerInAnotherFlight");
		errorMapping.put(MailHHTBusniessException.INVALID_ACCEPTANCETO_ARRIVEDCONTAINER,
				"mailtracking.defaults.err.containerIsAlreadyArrived");
		errorMapping.put(MailHHTBusniessException.INVALID_OFFLOAD_FROM_PA_BUILT_ULD,
				"mailtracking.defaults.err.cannotOffloadFromPABuiltULD");
		errorMapping.put(MailHHTBusniessException.INVALID_DESTINATION_UPDATION,
				"mailtracking.defaults.err.containerDestinationCannotBeChnaged");
		errorMapping.put(MailHHTBusniessException.INVALID_POU_UPDATION, "mailtracking.defaults.err.wrongPOUgiven");
		errorMapping.put("Invalid MailTag", "mailtracking.defaults.err.invalidMailtag");
		errorMapping.put("ULD validation failed", "mailtracking.defaults.err.ULDValidationIsFailed");
		errorMapping.put("The Flight is in To be actioned/To be cancelled/cancelled status",
				"mailtracking.defaults.err.flightisTBAorTBC");
		errorMapping.put("Mailbag is arrived in another flight.So can't deliver",
				"mailtracking.defaults.err.mailbagIsArrivedInAnotherFlightSoCannotdeliver");
		errorMapping.put(MailHHTBusniessException.MAILBAG_ALREADY_OFFLOADED_EXCEPTION,
				"mailtracking.defaults.err.mailbagIsAlreadyOffloaded");
		errorMapping.put(MailHHTBusniessException.OFFLOAD_FROM_CARRIER_EXCEPTION,
				"mailtracking.defaults.err.mailbagfromCarrierCannotBeOffloaded");
		errorMapping.put("Container is already offloaded", "mailtracking.defaults.err.containerIsAlreadyOffloaded");
		errorMapping.put("Only Containers assigned to a flight can be offloaded",
				"mailtracking.defaults.err.onlyContainersAssignedToFlightCanBeOffloaded");
		errorMapping.put("Container is not available at this airport",
				"mailtracking.defaults.err.containerIsNotAvailableAtThisPort");
		errorMapping.put("Container is already exists in the same flight",
				"mailtracking.defaults.err.containerIsAlreadyExistsInTheSameFlight");
		errorMapping.put("Container is not accepted to any flight",
				"mailtracking.defaults.err.containerIsNotAcceptedToAnyFlight");
		errorMapping.put(MailHHTBusniessException.INVALID_POU, "mailtracking.defaults.err.invalidPOU");
		errorMapping.put("Invalid Pol", "mailtracking.defaults.err.invalidPOL");
		errorMapping.put(MailHHTBusniessException.INVALID_TRANSFER_EXCEPTION,
				"mailtracking.defaults.err.cannotTransferWithoutMailArrival");
		errorMapping.put("Mailbag is arrived in another flight.So can't deliver ",
				"mailtracking.defaults.err.mailbagIsArrivedInAnotherFlightSoCannotdeliver");
		errorMapping.put(MailHHTBusniessException.MAIL_RETURN_CODE_IS_MANDATORY,
				"mailtracking.defaults.err.returnCodeismandatoryForReturn");
		errorMapping.put(MailHHTBusniessException.INVALID_OFFLOAD_REASON_CODE,
				"mailtracking.defaults.err.invalidOffloadReasonCode");
		errorMapping.put(MailHHTBusniessException.MAIL_DAMAGE_CODE_IS_MANDATORY,
				"mailtracking.defaults.err.damageCodeismandatoryForDamage");
		errorMapping.put(MailHHTBusniessException.INVALID_DAMAGE_REASON_CODE,
				"mailtracking.defaults.err.invalidDamageReasonCode");
		errorMapping.put(MailHHTBusniessException.INVALID_RETURN_REASON_CODE,
				"mailtracking.defaults.err.invalidReturnReasonCode");
		errorMapping.put(MailHHTBusniessException.RETURN_NOT_POSSIBLE_AT_THIS_PORT,
				"mailtracking.defaults.err.returnNotPossibleFromThisPort");
		errorMapping.put(MailHHTBusniessException.RETURN_NOT_POSSIBLE_AT_DEST,
				"mailtracking.defaults.err.returnNotPossibleFromDestinationOrCo-terminusOfDestination");
		errorMapping.put(MailHHTBusniessException.LAT_ERROR,
				"mailtracking.defaults.err.latestAcceptanceTimeIsCrossedForTheFlight");
		errorMapping.put(MailHHTBusniessException.OOE_NOT_CONFIGURED_FOR_GPA_AGAINST_ORG,
				"mailtracking.defaults.err.OfficeOfExchangeIsNotConfiguredForGPAAgainstAirport");
		errorMapping.put(MailHHTBusniessException.DOE_NOT_CONFIGURED_FOR_GPA_AGAINST_DEST,
				"mailtracking.defaults.err.OfficeOfExchangeIsNotConfiguredForGPAAgainstAirport");
		errorMapping.put(MailHHTBusniessException.ULD_AS_BULK_IN_SAME_CONTAINER,
				"mailtracking.defaults.err.ULDIsAlreadyAvailableInTheFlightWithDifferentContainerType");
		errorMapping.put(MailHHTBusniessException.MALBAG_OBSCAN_NOT_ALLOWED_DST,
				"mailtracking.defaults.err.OutboundScanNotAllowedAtDestinationAirport");
		errorMapping.put(MailHHTBusniessException.INVALID_ACCEPTANCETO_ARRIVEDCONTAINER,
				"mailtracking.defaults.err.ThisContainerIsAlreadyArrivedAtDestination");
		errorMapping.put(MailHHTBusniessException.MAILTRACKING_INVALID_ULD_INDICATOR,
				MAILTRACKING_INVALID_ULD_INDICATOR);
		return errorMapping.get(value);
	}

	/** 
	*/
	public MailOperationsBusinessException() {
		super("NOT_FOUND", "Not Found");
	}

	/** 
	* @param errorCode
	* @param exceptionCause
	*/
	public MailOperationsBusinessException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
	}

	/** 
	* @param errorCode
	*/
	public MailOperationsBusinessException(String errorCode) {
		super(errorCode, errorCode);
	}

	public MailOperationsBusinessException(BusinessException exception) {
		super(exception);
	}
}
