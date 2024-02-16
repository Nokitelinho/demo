package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MissingStockModel extends BaseModel {
  private String companyCode;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private String sequenceNumber;
  private String missingStartRange;
  private String missingEndRange;
  private long asciiMissingStartRange;
  private long asciiMissingEndRange;
  private String numberOfDocs;
  private String operationFlag;
  private String missingRemarks;
  private String triggerPoint;
  private boolean ignoreWarnings;
}
