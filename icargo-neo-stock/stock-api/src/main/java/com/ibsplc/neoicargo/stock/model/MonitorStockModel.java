package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude
public class MonitorStockModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String stockHolderCode;
  private String reference;
  private String documentType;
  private String documentSubType;
  private long availableStock;
  private long allocatedStock;
  private long requestsReceived;
  private long requestsPlaced;
  private long phyAllocatedStock;
  private long phyAvailableStock;
  private long manAllocatedStock;
  private long manAvailableStock;
  private String stockHolderType;
  private String approverCode;
  private String stockHolderName;
  private Collection<MonitorStockModel> monitorStock;
  private String operationFlag;
  private String triggerPoint;
  private boolean ignoreWarnings;
}
