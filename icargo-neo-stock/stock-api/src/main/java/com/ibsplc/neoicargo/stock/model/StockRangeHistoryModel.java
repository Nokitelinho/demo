package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRangeHistoryModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private int airlineIdentifier;
  private String fromStockHolderCode;
  private String documentType;
  private String documentSubType;
  private String serialNumber;
  private String accountNumber;
  private String rangeType;
  private String startRange;
  private String endRange;
  private String awbRange;
  private int checkDigit;
  private long asciiStartRange;
  private long asciiEndRange;
  private long numberOfDocuments;
  private String status;
  private Calendar transactionDate;
  private String transDateStr;
  private String toStockHolderCode;
  private LocalDate lastUpdateTime;
  private String lastUpdateUser;
  private Calendar startDate;
  private Calendar endDate;
  private String userId;
  private String remarks;
  private double voidingCharge;
  private String currencyCode;
  private String autoAllocated;
}
