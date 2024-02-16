package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockDetailsFilterVO extends AbstractVO {

  private static final long serialVersionUID = 1L;
  private boolean ignoreWarnings;
  private String triggerPoint;
  private String operationFlag;
  private String companyCode;
  /** airline Identifier */
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
}
