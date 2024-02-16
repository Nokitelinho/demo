package com.ibsplc.neoicargo.stock.vo;

import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import java.time.ZonedDateTime;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private Long stockSerialNumber;
  private String companyCode;
  private int airlineIdentifier;
  private String stockHolderCode;
  private String documentType;
  private String documentSubType;
  private long reorderLevel;
  private int reorderQuantity;
  private String stockApproverCompany;
  private String stockApproverCode;
  private boolean isReorderAlertFlag;
  private boolean isAutoRequestFlag;
  private boolean isAutoPopulateFlag;
  private String remarks;
  private int autoprocessQuantity;
  private Collection<RangeVO> ranges;
  private String lastUpdateUser;
  private ZonedDateTime lastUpdateTime;
  private long physicalAvailableStock;
  private long physicalAllocatedStock;
  private long manualAvailableStock;
  private long manualAllocatedStock;
  // not used fields
  private boolean ignoreWarnings;
  private String triggerPoint;
  private String airline;
  private int airlineId;
  private String operationFlag;
  private double totalStock;

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
