package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRangeFilterVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private String operationFlag;
  private String companyCode;
  private int airlineIdentifier;
  private String fromStockHolderCode;
  private String toStockHolderCode;
  private String documentType;
  private String documentSubType;
  private String serialNumber;
  private String accountNumber;
  private String rangeType;
  private String startRange;
  private String endRange;
  private String status;
  private Timestamp startDate;
  private Timestamp endDate;
  private String startDateStr;
  private String endDateStr;
  private String awb;
  private String awp;
  private boolean isHistory;
  private String station;
  private String userId;
  private Collection<String> availableStatus;
  private int pageNumber;
  private int totalRecordsCount;
  private String privilegeLevelType;
  private List<String> privilegeLevelValue;
  private String privilegeRule;
  private int pageSize;
}
