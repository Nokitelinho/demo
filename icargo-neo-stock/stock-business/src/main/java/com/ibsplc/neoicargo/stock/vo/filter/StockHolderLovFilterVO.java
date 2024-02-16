package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockHolderLovFilterVO extends AbstractVO {
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
