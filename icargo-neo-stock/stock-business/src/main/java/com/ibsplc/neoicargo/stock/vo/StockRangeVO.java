package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockRangeVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private boolean ignoreWarnings;
  private String triggerPoint;
  private String operationFlag;
  private String companyCode;
  private String stockHolderCode;
  private String reference;
  private String documentType;
  private String documentSubType;
  private boolean isManual;
  private Collection<RangeVO> availableRanges;
  private Collection<RangeVO> allocatedRanges;
}
