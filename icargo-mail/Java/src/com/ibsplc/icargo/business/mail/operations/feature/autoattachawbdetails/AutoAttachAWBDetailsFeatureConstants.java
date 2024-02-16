package com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails;

public class AutoAttachAWBDetailsFeatureConstants {

	private AutoAttachAWBDetailsFeatureConstants() {
	}

	public static final String MODULE_SUBMODULE = "MAIL OPERATIONS";
	public static final String AUTO_ATTACH_AWB_FEATURE = "mail.operations.autoAttachAWBDetailsFeature";
	public static final String AGENT_CODE_VALIDATOR = "mail.operations.autoAttachAWBDetails.validators.agentcodevalidator";
	public static final String SHIPMENT_DETAIL_VALIDATOR = "mail.operations.autoAttachAWBDetails.validators.documentValidation";
	public static final String PRODUCT_ENRICHER = "mail.operations.autoAttachAWBDetails.enrichers.product";
	public static final String SHIPMENT_DETAIL_ENRICHER = "mail.operations.autoAttachAWBDetails.enrichers.shipmentDetail";
	public static final String DOCUMENT_FILETR_ENRICHER = "mail.operations.autoAttachAWBDetails.enrichers.documentFilter";
	public static final String MAIL_AWB_PRODUCT = "mail.operations.productCode";
	public static final String DOCUMENT_TYPE = "AWB";
	public static final String MAIL_SERVICE_CARGO_CLASS = "F";
	public static final String MAIL_AWB_SOURCE = "MAL";
	public static final String SAVE_AGENTCODE = "mail.operations.autoAttachAWBDetails.agentcode";
	public static final String SAVE_PACODE = "mail.operations.autoAttachAWBDetails.pacode";
	public static final String PA_CODE_VALIDATOR = "mail.operations.autoAttachAWBDetails.validators.postalauthoritycodevalidator";
	public static final String DOCUMENT_FILTER_ENRICHER_LH = "LH-mail.operations.autoAttachAWBDetails.enrichers.documentFilter";
}
