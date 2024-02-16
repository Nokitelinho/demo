package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockAllocationModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String stockHolderCode;
  private String airlineCode;
  private String stockControlFor;
  private String documentNumber;
  private String documentType;
  private String documentSubType;
  private String lastUpdateUser;
  private String remarks;
  private String requestRefNumber;
  private String transferMode;
  private String operationFlag;
  private String airportCode;
  private String transactionCode;
  private String currencyCode;
  private String autoAllocated;
  private String triggerPoint;

  @JsonProperty("isNewStockFlag")
  private boolean isNewStockFlag;

  @JsonProperty("isManual")
  private boolean isManual;

  @JsonProperty("enableStockHistory")
  private boolean enableStockHistory;

  @JsonProperty("isConfirmationRequired")
  private boolean isConfirmationRequired;

  @JsonProperty("isAllocate")
  private boolean isAllocate;

  @JsonProperty("isExecuted")
  private boolean isExecuted;

  @JsonProperty("isReturned")
  private boolean isReturned;

  @JsonProperty("isReopened")
  private boolean isReopened;

  @JsonProperty("isConfirm")
  private boolean isConfirm;

  @JsonProperty("isBlacklist")
  private boolean isBlacklist;

  @JsonProperty("isApproverDeleted")
  private boolean isApproverDeleted;

  @JsonProperty("hasMinReorderLevel")
  private boolean hasMinReorderLevel;

  @JsonProperty("isAllocatedforCreate")
  private boolean isAllocatedforCreate;

  @JsonProperty("isFromConfirmStock")
  private boolean isFromConfirmStock;

  private boolean ignoreWarnings;
  private int noOfReqDocuments;
  private int airlineIdentifier;
  private int numberOfProcessedDocs;
  private long allocatedStock;
  private double voidingCharge;
  private GMTDate captureDateUTC;
  private LocalDate executionDate;
  private LocalDate lastUpdateTime;
  private LocalDate lastUpdateTimeForStockReq;
  private Collection<String> rejectedDocuments;
  private Collection<RangeModel> ranges;
  private Collection<StockRequestOALModel> stockForOtherAirlines;
}
