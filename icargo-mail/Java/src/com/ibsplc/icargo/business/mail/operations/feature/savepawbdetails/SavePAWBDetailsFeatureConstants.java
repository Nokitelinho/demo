package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails;


public class SavePAWBDetailsFeatureConstants {

	private SavePAWBDetailsFeatureConstants() {

	}

	public static final String MODULE_SUBMODULE = "MAIL OPERATIONS";
	public static final String SAVE_PAWB_FEATURE = "mail.operations.savepawbdetails.savePAWBDetailsFeature";
	public static final String SAVE_POSTAL_SHIPMENT_INVOKER = "mail.operations.savepawbdetails.preinvokers.savepostalshipmentinvoker";
	public static final String CARDIT_PAWB_PERSISTOR = "mail.operations.savepawbdetails.persistors.savepawbdetailspersistor";
	public static final String SAVE_POSTAL_HAWB_INVOKER = "mail.operations.savepawbdetails.preinvokers.savepostalhawbinvoker";
	public static final String DOCUMENT_IN_STOCK_VALIDATOR ="mail.operations.feature.savepawbdetails.validators.documentinstockvalidator";
	public static final String OWNER_ID ="mail.operations.savepawbdetails.ownerid";
	public static final String MANDATORY_FIELDS_IN_PAWB_VALIDATOR ="mail.operations.feature.savepawbdetails.validators.mandatoryfieldsinpawbvalidato";
	public static final String OPERATION_SHIPMENT_STOCK_CHECK = "operations.shipment.stockcheckrequired";
	public static final String DOCUMENT_IN_STOCK_VALIDATORLH ="LH-mail.operations.feature.savepawbdetails.validators.documentinstockvalidator";
	public static final String POSTAL_MAIL ="Postal Mail";
	public static final String SECURITY_REASON_CODE_EXCEMPTION = "SE";
	public static final String APPLICABLE_REGULATION = "AR";
}
