package com.ibsplc.neoicargo.stock.model.viewfilter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockRequestFilterModel extends BaseModel {

  private String companyCode;
  private String requestRefNumber;
  private String status;
  private String stockHolderCode;
  private String stockHolderType;
  private String stockControlFor;
  private String documentType;
  private String documentSubType;
  private String approver;
  private String airlineIdentifier;
  private String operation;
  private String privilegeLevelType;
  private String privilegeLevelValue;
  private String privilegeRule;
  private String operationFlag;
  private String triggerPoint;
  private int totalRecords;
  private int pageSize;
  private boolean ignoreWarnings;
  private LocalDate fromDate;
  private LocalDate toDate;
  private List<String> requestCreatedBy;

  @JsonProperty("isManual")
  private boolean isManual;

  @JsonProperty("isAllocateCall")
  private boolean isAllocateCall;
}
