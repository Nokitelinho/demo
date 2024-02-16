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
public class StockRequestVO extends PageableViewVO {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String requestRefNumber;
  private String stockHolderCode;
  private String stockHolderType;
  private ZonedDateTime requestDate;
  private String documentType;
  private String documentSubType;
  private String isManual;
  private String productName;
  private String status;
  private long requestedStock;
  private long persistedApprovedStock;
  private long approvedStock;
  private long allocatedStock;
  private String remarks;
  private String approvalRemarks;
  private String operationFlag;
  private ZonedDateTime lastUpdateDate;
  private String lastUpdateUser;
  private String stockHolderName;
  private String agentCode;
  private String agentName;
  private String productCode;
  private ZonedDateTime lastStockHolderUpdateTime;
  private String airlineIdentifier;
  private String requestCreatedBy;
  private String awbPrefix;
}
