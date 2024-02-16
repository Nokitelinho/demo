package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockHolderPriorityVO extends AbstractVO {
  private String companyCode;
  private String stockHolderType;
  private String stockHolderDescription;
  private String stockHolderCode;
  private String triggerPoint;
  private String operationFlag;
  private long priority;
  private boolean ignoreWarnings;
}
