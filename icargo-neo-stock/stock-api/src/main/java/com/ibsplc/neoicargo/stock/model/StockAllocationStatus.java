package com.ibsplc.neoicargo.stock.model;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@Getter
@UtilityClass
public final class StockAllocationStatus {
  public static final String MODE_RETURN = "R";
  public static final String MODE_BLACKLIST = "B";
  public static final String MODE_ALLOCATE = "A";
  public static final String MODE_CREATE = "C";
  public static final String MODE_USED = "U";
  public static final String MODE_UNUSED = "F";
  public static final String MODE_MANUAL = "M";
  public static final String MODE_NEUTRAL = "N";
  public static final String MODE_TRANSFER = "T";
  public static final String MODE_BLACKLISTREVOKE = "BR";
  public static final String MODE_UTILIZED_RETURNED = "UR";
  public static final String MODE_REOPENED = "P";
  public static final String MODE_DELETED = "D";
  public static final String MODE_REVOKE = "V";
  public static final String MODE_NORMAL = "N";
  public static final String MODE_EXECUTE = "E";
  public static final String MODE_ALLOCATE_TRANSIT = "L";
  public static final String MODE_RETURN_TRANSIT = "N";
  public static final String MODE_TRANSFER_TRANSIT = "E";
  public static final String MODE_VOID = "O";
}
