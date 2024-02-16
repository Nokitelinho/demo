package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1366
 */
@Setter
@Getter
@Builder
public class RangesForMergeFilterVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private int airlineIdentifier;
  private String documentType;
  private String documentSubType;
  private String stockHolderCode;
  private String manualFlag;
  private String operationFlag;
  private long asciiStartRange;
  private long asciiEndRange;
}
