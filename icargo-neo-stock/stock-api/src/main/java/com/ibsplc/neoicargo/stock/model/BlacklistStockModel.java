package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
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
@JsonInclude
public class BlacklistStockModel extends BaseModel {
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
  private LocalDate blacklistDate;
  private LocalDate lastUpdateTime;
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
  private StockModel stockVO;
  private TransitStockModel transitStockVO;
  private String currencyCode;
  private String operationFlag;
  private String triggerPoint;
  private boolean ignoreWarnings;
  private double voidingCharge;

  @JsonProperty("isManual")
  private boolean isManual;

  @JsonProperty("isBlacklistStock")
  private boolean isBlacklistStock;

  @JsonProperty("isRevokeBlacklist")
  private boolean isRevokeBlacklist;
}
