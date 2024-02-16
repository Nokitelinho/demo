package com.ibsplc.neoicargo.stock.model.viewfilter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RangeFilterModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private int airlineIdentifier;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private String startRange;
  private String endRange;
  private String numberOfDocuments;

  @JsonProperty("isManual")
  private boolean isManual;
}
