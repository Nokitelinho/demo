package com.ibsplc.neoicargo.stock.model.viewfilter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockFilterModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private int airlineIdentifier;
  private String airlineCode;
  private int totalRecords;
  private String stockHolderCode;
  private String stockHolderType;
  private String documentType;
  private String documentSubType;

  @JsonProperty("isManual")
  private boolean isManual;

  @JsonProperty("isViewRange")
  private boolean isViewRange;

  private String rangeFrom;
  private String rangeTo;
  private LocalDate fromDate;
  private LocalDate toDate;
  private String airportCode;
  private int pageNumber;
  private int absoluteIndex;
  private String privilegeLevelType;
  private String privilegeLevelValue;
  private String privilegeRule;
  private String operationFlag;
  private String triggerPoint;
  private boolean ignoreWarnings;
}
