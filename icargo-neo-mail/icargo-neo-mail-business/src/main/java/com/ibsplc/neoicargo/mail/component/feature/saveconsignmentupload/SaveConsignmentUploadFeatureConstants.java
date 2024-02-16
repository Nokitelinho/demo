package com.ibsplc.neoicargo.mail.component.feature.saveconsignmentupload;

public class SaveConsignmentUploadFeatureConstants {
	
	private SaveConsignmentUploadFeatureConstants() {
	}
	
	public static final String MODULE_SUBMODULE = "MAIL OPERATIONS";
	public static final String QUERY_MODULE_SUBMODULE = "mail.operations";
	public static final String SAVE_CONSIGNMENT_UPLOAD_FEATURE = "mail.operations.saveconsignmentuploadfeature";
	public static final String PA_CODE_VALIDATOR = "mail.operations.saveconsignmentupload.validators.pacode";
	public static final String FLIGHT_DETAILS_VALIDATOR = "mail.operations.saveconsignmentupload.validators.flightdetails";
	public static final String CONSIGNMENT_DETAILS = "mail.operations.saveconsignmentupload.enrichers.consignmentdetails";
	public static final String EXISTING_CONSIGNMENT_DETAILS = "mail.operations.saveconsignmentupload.enrichers.existingconsignmentdetails";
	public static final String DOCUMENT_CONTROLLER_BEAN_ID = "documentController";
	public static final String PERFORM_SAVE_CONSIGNMENT_ID = "mail.operation.performsaveconsignment";
	public static final String MAIL_OPERATIONS_SAVEMAILBAGHISTORY_CONSIGNMENT_UPLOAD = "MAIL_OPERATIONS_SAVEMAILBAGHISTORY_CONSIGNMENT_UPLOAD";
	public static final String SAVE_CONSIGNMENT_UPLOAD_PACODE = "mail.operations.saveconsignmentupload.pacode";
	public static final String SAVE_CONSIGNMENT_UPLOAD_ORIGIN = "mail.operations.saveconsignmentupload.origin";
	public static final String SAVE_CONSIGNMENT_UPLOAD_DESTINATION = "mail.operations.saveconsignmentupload.destination";
	public static final String SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP = "mail.operations.saveconsignmentupload.flightvalidationmap";
	public static final String POSTAL_AUTHORITY = "PA";
	public static final String ACTIVE = "ACTIVE";
	public static final String ERROR_NO_PA_CODE_EXISTS_FOR_MAILBOX ="No PA code exists for the mailbox id";
	public static final String ERROR_INVALID_FLIGHT_DETAILS ="Invalid Flight Details";
	public static final String ERROR_EXISTING_CONSIGNMENT ="Error during removing existing consignment";
	public static final String ERROR_NEW_CONSIGNMENT ="Error during creating new consignment";

}
