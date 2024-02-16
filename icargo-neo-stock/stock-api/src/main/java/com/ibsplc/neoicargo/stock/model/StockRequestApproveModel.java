package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockRequestApproveModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String approverCode;
  private String documentType;
  private String documentSubType;
  private Collection<StockRequestModel> stockRequests;

  @JsonProperty("isStockAvailableCheck")
  private boolean isStockAvailableCheck;

  private LocalDate lastUpdateTime;
}
