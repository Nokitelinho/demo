package com.ibsplc.neoicargo.cca.constants;

public final class CcaAuditConstants {

    private CcaAuditConstants() {
    }

    public static final String CCA_AUDIT_EVENT_NAME = "cca";
    public static final String AUTO_CCA_AUDIT_EVENT_NAME = "auto_cca";
    public static final String CCA_AUDIT_EVENT_LISTENER_NAME = "ccaAuditEventListener";
    public static final String CCA_MASTER_GROUP = "ccaMaster";
    public static final String CCA_AWB_GROUP = "ccaAwb";
    public static final String CCA_AWB_CHARGE_DETAIL_GROUP = "ccaAwbChargeDetail";
    public static final String CCA_AUDIT_DEFAULT_TRANSACTION = "save_cca";
    public static final String AUTO_CCA_AUDIT_TRANSACTION = "save_auto_cca";
    public static final String CCA_AUDIT_DELETED_TRANSACTION = "CCA Deleted";

    public static final String CREATED_TEMPLATE = "audit-cca-created";
    public static final String INITIATED_TEMPLATE = "audit-cca-initiated";
    public static final String RECOMMENDED_TEMPLATE = "audit-cca-recommended";
    public static final String APPROVED_TEMPLATE = "audit-cca-approved";
    public static final String REJECTED_TEMPLATE = "audit-cca-rejected";
    public static final String DELETED_TEMPLATE = "audit-cca-deleted";
    public static final String UPDATED_TEMPLATE = "audit-cca-updated";

    public static final String CCA_NUMBER = "ccaNumber";
    public static final String CCA_TYPE = "ccaType";
    public static final String CCA_STATUS = "ccaStatus";
    public static final String CCA_SOURCE = "ccaSource";
    public static final String CCA_PAYMENT_TYPE = "payType";
    public static final String CCA_PAYMENT_FREIGHT = "payTypeFreight";
    public static final String CCA_PAYMENT_OTHER = "payTypeOther";
    public static final String CCA_PAYMENT_PP = "Pre Paid";
    public static final String CCA_PAYMENT_CC = "Charge Collect";
    public static final String CCA_OTHER_CHARGES = "otherCharges";
    public static final String CCA_TAXES_AND_COMMISSION = "taxesAndCommission";
    public static final String CCA_PP_TAX = "awbTaxPP";
    public static final String CCA_CC_TAX = "awbTaxCC";
    public static final String CCA_COMMISSION = "commissionAmount";
    public static final String CCA_TOTAL_TAX = "taxAmount";
    public static final String OCDC = "ocdc";
    public static final String OCDA = "ocda";

    public static final String PAYMENT_TYPE_REGEX = "[C,P]{2}";

    public static final String DUE_CARRIER_FIELD = "dueCarrier";
    public static final String DUE_AGENT_FIELD = "dueAgent";
    public static final String CHARGE_HEAD_FIELD = "chargeHead";
    public static final String CHARGE_HEAD_CODE_FIELD = "chargeHeadCode";
    public static final String CHARGE_VALUE_FIELD = "charge";
}
