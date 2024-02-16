package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author A-4443
 */
@Setter
@Getter
public class MissingStockVO extends AbstractVO {
  private boolean ignoreWarnings;
  private String triggerPoint;
  private String companyCode;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private String sequenceNumber;
  private String missingStartRange;
  private String missingEndRange;
  private long asciiMissingStartRange;
  private long asciiMissingEndRange;
  private String numberOfDocs;
  private String operationFlag;
  private String missingRemarks;
}
