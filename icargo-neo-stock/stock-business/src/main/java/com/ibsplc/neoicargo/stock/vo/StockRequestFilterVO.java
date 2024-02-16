package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockRequestFilterVO extends AbstractVO {
  private String triggerPoint;
  private String operationFlag;
  private String companyCode;
  private String requestRefNumber;
  private String status;
  private List<String> statuses;
  private String stockHolderCode;
  private String stockHolderType;
  private String stockControlFor;
  private String documentType;
  private String documentSubType;
  private String approver;
  private Long airlineIdentifier;
  private String operation;
  private String privilegeLevelType;
  private String privilegeLevelValue;
  private List<String> levelValues;
  private String privilegeRule;
  private int pageSize;
  private int totalRecords;
  private boolean ignoreWarnings;
  private String isManual;
  private boolean isAllocateCall;
  private LocalDate fromDate;
  private LocalDate toDate;
  private List<String> requestCreatedBy;
}
