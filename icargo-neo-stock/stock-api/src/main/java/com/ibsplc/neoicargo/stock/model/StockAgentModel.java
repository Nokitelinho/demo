package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockAgentModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String agentCode;
  private String stockHolderCode;
  private String lastUpdateUser;
  private LocalDate lastUpdateTime;
  private String operationFlag;
  private int pageNumber;
  private String id;
}
