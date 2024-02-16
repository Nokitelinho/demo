package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockRangeModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String stockHolderCode;
  private String reference;
  private String documentType;
  private String documentSubType;
  private boolean isManual;
  private Collection<RangeModel> availableRanges;
  private Collection<RangeModel> allocatedRanges;
  private String operationFlag;
  private String triggerPoint;
  private boolean ignoreWarnings;
}
