package com.ibsplc.neoicargo.stock.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StockConstant {
  public static final String STOCK_DEFAULTS_STOCK_HOLDER_CODE =
      "stock.defaults.defaultstockholdercodeforcto";
  public static final String STOCK_DEFAULTS_CONFIRMATION_REQUIRED =
      "stockcontrol.defaults.isconfirmationprocessneeded";
  public static final String STOCK_DEFAULTS_STOCK_INTRODUCTION_PERIOD =
      "stockcontrol.defaults.stockintroductionperiod";
  public static final String STOCK_DEFAULTS_ENABLE_STOCK_HISTORY =
      "stockcontrol.defaults.enablestockhistory";
  public static final String STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM =
      "stockcontrol.defaults.stockholderstockaccountingrequired";
  public static final String MANUAL = "M";
  public static final String NOT_MANUAL = "N";
  public static final String STATUS_NEW = "N";
  public static final String STATUS_APPROVED = "A";
  public static final String STATUS_ALLOCATED = "L";
  public static final String STATUS_COMPLETED = "M";
  public static final String STATUS_REJECTED = "R";

  public static final String DOCUMENT_TYPE = "AWB";

  public static final String MODE_NORMAL = "N";
  public static final String MODE_TRANSFER = "T";
  public static final String MODE_BLACKLIST_REVOKE = "BR";
  public static final String MODE_ALLOCATE = "A";
  public static final String MODE_RETURN = "RT";
  public static final String MODE_RECEIVE = "R";
  public static final String MODE_BLACKLIST = "B";
  public static final String MODE_USED = "U";
  public static final String MODE_NEUTRAL = "N";
  public static final String MODE_MANUAL = "M";

  public static final String STRING_DATE_FORMAT = "yyyyMMdd";

  public static final String INVALID_RANGE_FORMAT_INDICATOR =
      "shared.document.invalidalphanumericrange";

  public static final String DELIMITER = ",";
  public static final String HYPHEN = "-";

  public static final int DEFAULT_PAGE_SIZE = 25;
  public static final String OPERATION_FLAG_INSERT = "I";
  public static final String OPERATION_FLAG_DELETE = "D";
  public static final String OPERATION_FLAG_UPDATE = "U";

  public static final int SUBSTRING_COUNT = 7;
  public static final int STRING_START = 0;

  public static final String RECEIVED_RETURN = "I";
  public static final String RECEIVED_TRANSFER = "H";
  public static final String RECEIVED_ALLOCATION = "G";

  public static final String TRANSIT_STATUS_NOT_CONFIRMED = "N";
  public static final String TRANSIT_STATUS_MISSING = "M";
  public static final String TRANSIT_ACTION_ALLOCATE = "A";
  public static final String TRANSIT_ACTION_TRANSFER = "T";
  public static final String TRANSIT_ACTION_RETURN = "R";
  public static final String DOC_TYP_AWB = "AWB";
  public static final String DOC_TYP_COURIER = "COU";
  public static final String DOC_TYP_EBT = "EBT";
  public static final String DOC_TYP_INV = "INVOICE";
  public static final String STATUS_INSTOCK = "INSTOCK";
  public static final String STATUS_DELETE = "D";
}
