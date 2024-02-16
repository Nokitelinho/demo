package com.ibsplc.neoicargo.cca.constants;

public final class CcaConstants {

    private CcaConstants() {
    }

    public static final String CCA_NEW = "N";
    public static final String CCA_INITIATED = "I";
    public static final String CCA_APPROVED = "A";
    public static final String CCA_RECOMMENDED = "C";
    public static final String CCA_REJECTED = "R";
    public static final String CCA_SYSTEM_REJECTED = "S";
    public static final String CCA_DELETED = "D";
    public static final String APPROVED = "Approved";

    public static final String CCA_TYPE_INTERNAL = "I";
    public static final String CCA_TYPE_ACTUAL = "A";
    public static final String AUTO_CCA_SOURCE = "Auto CCA";
    public static final String CRA_SOURCE = "CRA";
    public static final String INTERNAL = "Internal";
    public static final String ACTUAL = "Actual";

    public static final String REJECTED_AS_PART_OF_VOIDING_CCA_REASON = "RD";

    public static final String CCA_RECORD_TYPE_ORIGINAL = "O";
    public static final String CCA_RECORD_TYPE_REVISED = "R";

    public static final String SLASH = "/";
    public static final String COMMA = ",";
    public static final String FLAG_Y = "Y";
    public static final String FLAG_N = "N";
    public static final String RULE_FILE_EXTENSION = ".yaml";
    public static final String RULE_DIRECTORY = "rules/";

    public static final String PRICING_PARAMETER = "PRICING";
    public static final String USE_ORIGINAL_AWB_VALUES = "USE_ORIGINAL_AWB_VALUES";
    public static final String RATE_TYPE_RECOMMENDED_MKT = "RECMKT";
    public static final String RATE_TYPE_RECOMMENDED_IATA = "RECIATA";
    public static final String MKT_TYPE = "MKT";
    public static final String IATA_TYPE = "IATA";
    public static final String DUE_CARRIER = "DC";
    public static final String DUE_AGENT = "DA";
    public static final String NEO = "NEO";
    public static final String QUALITY_AUDITED = "QAD";
    public static final String TRIGGER_QUALITY_AUDIT = "QADAWB";
    public static final String OTHER_CHARGE_DUE_AGENT = "OCDA";
    public static final String OTHER_CHARGE_DUE_CARRIER = "OCDC";
    public static final String PAYMENT_TYPE_CHARGE_COLLECT = "CC";
    public static final String PAYMENT_TYPE_PRE_PAID = "PP";
    public static final String CUSTOMER_TYPE_CC_COLLECTOR = "CC";


    public static final String UPDATE_OPERATION = "U";
    public static final String INSERT_OPERATION = "I";

    public static final String SERVICETAX_FREIGHTCHARGE = "serviceTaxFreightCharge";
    public static final String SERVICETAX_OCDC = "serviceTaxOCDC";
    public static final String SERVICETAX_OCDA = "serviceTaxOCDA";
    public static final String SERVICETAX_COMMISSION = "serviceTaxCommission";
    public static final String SERVICETAX_DISCOUNT = "serviceTaxDiscount";
    public static final String TDS_DUECARRIER = "tdsDueCarrier";
    public static final String TDS_DUEAGENT = "tdsDueAgent";
    public static final String SGST = "sgst";
    public static final String CGST = "cgst";
    public static final String IGST = "igst";
    public static final String CUSTOMER_CODE = "customerCode";
    public static final String RATE_CLASS_IATA = "rateClassIata";
    public static final String RATE_CLASS_MARKET = "rateClassMarket";
    public static final String RATE_CLASS = "rate_class";

    public static final String SYSTEM_PARAMETER_INTERNAL_CCA_SEQUENCE_REQUIRED = "cra.defaults.internalccasequence";
    public static final String SYSTEM_PARAMETER_WALK_IN_CUSTOMER = "common.customer.walkincustomer";

    public static final String INBOUND_FOP_CREDIT = "CREDIT";
    public static final String INBOUND_FOP_CASH = "CASH";

    public static final String SPOT_RATE_APPROVED_STATUS = "APR";
    public static final String UTC = "UTC";

}
