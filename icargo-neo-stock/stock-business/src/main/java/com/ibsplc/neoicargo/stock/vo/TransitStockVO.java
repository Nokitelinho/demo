package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransitStockVO extends AbstractVO {
  private boolean ignoreWarnings;
  private String triggerPoint;
  private String companyCode;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private String stockControlFor;
  private String actualStartRange;
  private String actualEndRange;
  private String missingStartRange;
  private String missingEndRange;
  private long asciiMissingStartRange;
  private long asciiMissingEndRange;
  private long missingNumberOfDocs;
  private long numberOfDocs;
  private String confirmStatus;
  private ZonedDateTime confirmDate;
  private ZonedDateTime lastUpdateTime;
  private String lastUpdateUser;
  private int airlineIdentifier;
  private String txnCode;
  private ZonedDateTime txnDate;
  private boolean isManual;
  private String operationFlag;
  private String missingRemarks;
  private String txnRemarks;
  private List<MissingStockVO> missingRanges;
  private String stockHolderType;
}
