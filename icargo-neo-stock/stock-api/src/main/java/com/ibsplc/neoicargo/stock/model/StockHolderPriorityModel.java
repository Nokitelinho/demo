package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockHolderPriorityModel extends BaseModel {

  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String stockHolderType;
  private String stockHolderDescription;
  private String stockHolderCode;
  private String triggerPoint;
  private long priority;
  private String operationFlag;
  private boolean ignoreWarnings;
}
