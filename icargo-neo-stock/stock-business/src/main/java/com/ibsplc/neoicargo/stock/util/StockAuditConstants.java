package com.ibsplc.neoicargo.stock.util;

public final class StockAuditConstants {
  private StockAuditConstants() {}

  public static final String STOCK_AUDIT_EVENTS_LISTENER_NAME = "stockAuditEventsListener";
  public static final String BUSINESS_ID = "businessId";
  public static final String STOCK_AUDIT_EVENT_NAME = "stock";
  public static final String STOCK_HOLDER_AUDIT_EVENT_NAME = "stockHolder";
  public static final String STOCK_RANGE_UTILISATION_AUDIT_EVENT_NAME = "stockRangeUtilisation";
  public static final String OLD_SUFFIX = "Old";
  public static final String NEW_SUFFIX = "New";

  // TEMPLATE
  public static final String CREATED_STOCK_HOLDER_TEMPLATE = "audit-stock-holder-created";
  public static final String UPDATED_STOCK_HOLDER_TEMPLATE = "audit-stock-holder-updated";
  public static final String CREATED_STOCK_TEMPLATE = "audit-stock-created";
  public static final String UPDATED_STOCK_TEMPLATE = "audit-stock-updated";
  public static final String REMOVED_STOCK_TEMPLATE = "audit-stock-removed";
  public static final String REMOVED_STOCK_HOLDER_TEMPLATE = "audit-stock-holder-removed";

  // TRANSACTION
  public static final String STOCK_HOLDER_AUDIT_UPDATE_TRANSACTION = "Stock Holder Updated";
  public static final String STOCK_HOLDER_AUDIT_CREATE_TRANSACTION = "Stock Holder Created";
  public static final String STOCK_AUDIT_UPDATE_TRANSACTION = "Stock Updated";
  public static final String STOCK_AUDIT_CREATE_TRANSACTION = "Stock Created";
  public static final String STOCK_AUDIT_REMOVED_TRANSACTION = "Stock Removed";
  public static final String STOCK_HOLDER_AUDIT_REMOVED_TRANSACTION = "Stock Holder Removed";

  // GROUP
  public static final String GROUP_SUFFIX = "-group";
  public static final String STOCK_HOLDER_GROUP = STOCK_HOLDER_AUDIT_EVENT_NAME + GROUP_SUFFIX;
  public static final String STOCK_GROUP = STOCK_AUDIT_EVENT_NAME + GROUP_SUFFIX;
}
