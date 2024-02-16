package com.ibsplc.neoicargo.stock.vo;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorStockVO extends PageableViewVO {
  private boolean ignoreWarnings;
  private String triggerPoint;
  private String operationFlag;
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
  private Collection<MonitorStockVO> monitorStock;
}
