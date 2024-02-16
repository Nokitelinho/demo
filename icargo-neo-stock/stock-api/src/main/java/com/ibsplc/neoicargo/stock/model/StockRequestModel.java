package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockRequestModel extends BaseModel {

  private String companyCode;
  private String requestRefNumber;
  private String stockHolderCode;
  private String stockHolderType;
  private String documentType;
  private String documentSubType;
  private String productName;
  private String status;
  private String remarks;
  private String approvalRemarks;
  private String operationFlag;
  private String lastUpdateUser;
  private String stockHolderName;
  private String agentCode;
  private String agentName;
  private String productCode;
  private String airlineIdentifier;
  private String requestCreatedBy;
  private long requestedStock;
  private long persistedApprovedStock;
  private long approvedStock;
  private long allocatedStock;
  private LocalDate lastUpdateDate;
  private LocalDate requestDate;
  private LocalDate lastStockHolderUpdateTime;

  @JsonProperty("isManual")
  private boolean isManual;
}
