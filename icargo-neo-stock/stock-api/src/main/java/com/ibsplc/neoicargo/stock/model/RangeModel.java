package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RangeModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private int airlineIdentifier;
  private String startRange;
  private String awbPrefix;
  private String endRange;
  private long asciiStartRange;
  private long asciiEndRange;
  private long numberOfDocuments;
  private String operationFlag;

  @JsonProperty("isBlackList")
  private boolean isBlackList;

  @JsonProperty("isManual")
  private boolean isManual;

  private LocalDate lastUpdateTime;
  private String lastUpdateUser;
  private String stockHolderName;
  private String avlStartRange;
  private String avlEndRange;
  private long avlNumberOfDocuments;
  private String allocStartRange;
  private String allocEndRange;
  private long allocNumberOfDocuments;
  private String fromStockHolderCode;
  private String usedStartRange;
  private String usedEndRange;
  private String allocatedRange;
  private String availableRange;
  private String usedRange;
  private long usedNumberOfDocuments;
  private LocalDate stockAcceptanceDate;
  private Collection<String> masterDocumentNumbers;
  private String triggerPoint;
  private boolean ignoreWarnings;
}
