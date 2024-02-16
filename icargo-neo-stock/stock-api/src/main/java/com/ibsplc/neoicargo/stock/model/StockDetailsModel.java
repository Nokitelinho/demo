package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockDetailsModel extends BaseModel {

  private static final long serialVersionUID = 1L;
  private String companyCode;
  private int airlineId;
  private String approverCode;
  private String documentType;
  private String documentSubType;
  private String stockApproverName;
  private String stockHolderCode;
  private String stockHolderName;
  private String avlStartRange;
  private String avlEndRange;
  private long avlNumberDocuments;
  private String allocStartRange;
  private String allocEndRange;
  private long allocNumberDocuments;
  private String usedStartRange;
  private String usedEndRange;
  private long usedNumberDocuments;
  private String awbPrefix;
  private long totalStockAvailed;
  private long requestReceived;
  private long requestPlaced;
  private long availableStock;
  private long allocatedStock;
  private String allocatedTo;
  private long usedStock;
  private String stockHolderType;
  private List<RangeModel> customerRanges;
  private long openingBalance;
  private long blacklistedStock;
  private long receivedStock;
  private LocalDate transactionDate;
  private long transferredStock;
  private long returnedStock;
  private long utilizedStock;
  private String lastUpdatedUser;
  private LocalDate lastUpdatedTime;
  private long returnedUtilizedStock;
  private long availableBalance;
  private String operationFlag;
  private String triggerPoint;
  private boolean ignoreWarnings;
}
