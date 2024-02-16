package com.ibsplc.neoicargo.stock.model.viewfilter;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockHolderLovFilterModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String stockHolderCode;
  private String description;
  private String stockHolderType;
  private String documentType;
  private String documentSubType;
  private String stockHolderName;
  private String approverCode;
  private boolean isRequestedBy;
  private int pageNumber;
}
