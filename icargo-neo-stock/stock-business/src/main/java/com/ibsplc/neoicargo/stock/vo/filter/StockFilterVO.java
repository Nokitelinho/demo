package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.time.LocalDate;
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
public class StockFilterVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private boolean ignoreWarnings;
  private String triggerPoint;
  private String operationFlag;
  private String companyCode;
  private int airlineIdentifier;
  private String airlineCode;
  private int totalRecords;
  private String stockHolderCode;
  private String stockHolderType;
  private String documentType;
  private String documentSubType;
  private String manualFlag;
  private boolean isViewRange;
  private String rangeFrom;
  private String rangeTo;
  private Long asciiRangeFrom;
  private Long asciiRangeTo;
  private LocalDate fromDate;
  private LocalDate toDate;
  private String airportCode;
  private int pageNumber;
  private int absoluteIndex;
  private String privilegeLevelType;
  private String privilegeLevelValue;
  private String privilegeRule;
}
