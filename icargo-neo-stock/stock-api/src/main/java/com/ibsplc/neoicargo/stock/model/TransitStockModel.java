package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransitStockModel extends BaseModel {
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
  private LocalDate confirmDate;
  private LocalDate lastUpdateTime;
  private String lastUpdateUser;
  private int airlineIdentifier;
  private String txnCode;
  private LocalDate txnDate;
  private boolean isManual;
  private String operationFlag;
  private String missingRemarks;
  private String txnRemarks;
  private List<MissingStockModel> missingRanges;
  private String stockHolderType;
  private String triggerPoint;
  private boolean ignoreWarnings;
}
