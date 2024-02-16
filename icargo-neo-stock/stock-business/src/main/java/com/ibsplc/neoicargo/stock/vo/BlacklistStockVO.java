package com.ibsplc.neoicargo.stock.vo;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistStockVO extends PageableViewVO {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String stockHolderCode;
  private String firstLevelStockHolder;
  private String secondLevelStockHolder;
  private String documentType;
  private String documentSubType;
  private String rangeFrom;
  private String rangeTo;
  private String remarks;
  private ZonedDateTime blacklistDate;
  private ZonedDateTime lastUpdateTime;
  private String lastUpdateUser;
  private String newRangeFrom;
  private String newRangeTo;
  private int airlineIdentifier;
  private String stationCode;
  private String status;
  private Long asciiRangeFrom;
  private Long asciiRangeTo;
  private String actionCode;
  private String agentCode;
  private StockVO stockVO;
  private TransitStockVO transitStockVO;
  private double voidingCharge;
  private String currencyCode;
  private boolean isManual;
  private boolean isBlacklistStock;
}
