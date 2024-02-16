package com.ibsplc.neoicargo.stock.model.viewfilter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRangeFilterModel extends BaseModel {
  private static final long serialVersionUID = 1L;
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
  private LocalDate startDate;
  private LocalDate endDate;
  private String startDateStr;
  private String endDateStr;
  private String awb;
  private String awp;

  @JsonProperty("isHistory")
  private boolean isHistory;

  private String station;
  private String userId;
  private Collection<String> availableStatus;
  private int pageNumber;
  private int totalRecordsCount;
  private String privilegeLevelType;
  private String privilegeLevelValue;
  private String privilegeRule;
  private int pageSize;
}
