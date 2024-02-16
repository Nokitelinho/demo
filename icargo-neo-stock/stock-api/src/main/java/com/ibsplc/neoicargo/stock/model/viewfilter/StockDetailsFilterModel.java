package com.ibsplc.neoicargo.stock.model.viewfilter;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockDetailsFilterModel extends BaseModel {

  private static final long serialVersionUID = 1L;
  private String companyCode;
  private int airlineId;
  private String customerCode;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private String startDate;
  private String endDate;
  private int awbPrefix;
  private int pageNumber;
  private int absoluteIndex;
  private boolean isManual;
  private String stockHolderType;
  private int pageSize;
  private String operationFlag;
  private String triggerPoint;
  private boolean ignoreWarnings;
}
