package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private int airlineIdentifier;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private long reorderLevel;
  private int reorderQuantity;
  private String stockApproverCompany;
  private String stockApproverCode;

  @JsonProperty("isReorderAlertFlag")
  private boolean isReorderAlertFlag;

  @JsonProperty("isAutoRequestFlag")
  private boolean isAutoRequestFlag;

  @JsonProperty("isAutoPopulateFlag")
  private boolean isAutoPopulateFlag;

  private String remarks;
  private int autoprocessQuantity;
  private Collection<RangeModel> ranges;
  private String lastUpdateUser;
  private LocalDate lastUpdateTime;
  private String operationFlag;
  private String triggerPoint;
  private boolean ignoreWarnings;
}
