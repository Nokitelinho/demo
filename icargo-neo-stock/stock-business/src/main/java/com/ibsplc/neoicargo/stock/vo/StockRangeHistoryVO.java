package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockRangeHistoryVO extends PageableViewVO {
  private static final long serialVersionUID = 1L;

  private String triggerPoint;
  private String operationFlag;
  private String companyCode;
  private String fromStockHolderCode;
  private String documentType;
  private String documentSubType;
  private String serialNumber;
  private String accountNumber;
  private String rangeType;
  private String startRange;
  private String endRange;
  private String awbRange;
  private String status;
  private String transDateStr;
  private String toStockHolderCode;
  private String lastUpdateUser;
  private String userId;
  private String remarks;
  private String currencyCode;
  private String autoAllocated;
  private boolean ignoreWarnings;
  private double voidingCharge;
  private int airlineIdentifier;
  private int checkDigit;
  private double asciiStartRange;
  private double asciiEndRange;
  private long numberOfDocuments;
  private ZonedDateTime lastUpdateTime;
  private ZonedDateTime transactionDate = BaseMapper.createCurrentZonedDateTime();
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
}
