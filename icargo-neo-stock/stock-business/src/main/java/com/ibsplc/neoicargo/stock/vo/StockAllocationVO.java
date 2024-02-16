package com.ibsplc.neoicargo.stock.vo;

import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;

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
public class StockAllocationVO extends AbstractVO {
  private static final long serialVersionUID = 1L;

  private String companyCode;
  private String stockHolderCode;
  private String airlineCode;
  private String stockControlFor;
  private String documentNumber;
  private String documentType;
  private String documentSubType;
  private String lastUpdateUser;
  private String remarks;
  private String requestRefNumber;
  private String transferMode;
  private String operationFlag;
  private String airportCode;
  private String transactionCode;
  private String currencyCode;
  private String autoAllocated;
  private String triggerPoint;
  private boolean isNewStockFlag;
  private boolean isManual;
  private boolean enableStockHistory;
  private boolean isConfirmationRequired;
  private boolean isAllocate;
  private boolean isExecuted;
  private boolean isReturned;
  private boolean isReopened;
  private boolean isConfirm;
  private boolean isBlacklist;
  private boolean isApproverDeleted;
  private boolean hasMinReorderLevel;
  private boolean isAllocatedforCreate;
  private boolean isFromConfirmStock;
  private boolean ignoreWarnings;
  private int airlineIdentifier;
  private int noOfReqDocuments;
  private int numberOfProcessedDocs;
  private long allocatedStock;
  private double voidingCharge;
  private ZonedDateTime captureDateUTC;
  private ZonedDateTime executionDate;
  private ZonedDateTime lastUpdateTime;
  private ZonedDateTime lastUpdateTimeForStockReq;
  private List<String> rejectedDocuments;
  private List<RangeVO> ranges;
  private List<StockRequestOALVO> stockForOtherAirlines;
  private String awbPrefix;

  @Override
  public String getBusinessId() {
    return companyCode
        + HYPHEN
        + stockHolderCode
        + HYPHEN
        + airlineIdentifier
        + HYPHEN
        + documentType
        + HYPHEN
        + documentSubType;
  }
}
