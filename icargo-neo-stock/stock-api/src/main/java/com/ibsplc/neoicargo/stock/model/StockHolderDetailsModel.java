package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 201310
 */
@Setter
@Getter
public class StockHolderDetailsModel extends BaseModel {
  private String companyCode;
  private String stockHolderCode;
  private String stockHolderType;
  private String docType;
  private String docSubType;
  private long reorderLevel;
  private long reorderQuantity;
  private boolean isReorderAlert;
  private boolean isAutoStockRequest;
  private String approverCode;
  private LocalDate lastUpdateTime;
  private String lastUpdateUser;
  private String awbPrefix;
}
