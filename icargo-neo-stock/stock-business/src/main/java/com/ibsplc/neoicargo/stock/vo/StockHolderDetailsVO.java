package com.ibsplc.neoicargo.stock.vo;

import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 201310
 */
@Setter
@Getter
public class StockHolderDetailsVO extends PageableViewVO {
  private String companyCode;
  private String stockHolderCode;
  /**
   * Denotes the stockholder type. Possible values are H-headqarters, R-region, S-station,A-agent
   */
  private String stockHolderType;
  /** document type */
  private String docType;
  /** document sub type */
  private String docSubType;
  /** reorder level */
  private long reorderLevel;
  /** reorder quantity */
  private long reorderQuantity;
  /** reorder alert */
  private boolean isReorderAlert;
  /** auto stock request */
  private boolean isAutoStockRequest;
  /** approver code */
  private String approverCode;

  private ZonedDateTime lastUpdateTime;
  private String lastUpdateUser;
  private String awbPrefix;
  private int airlineIdentifier;
}
