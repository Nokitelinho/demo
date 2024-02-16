package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockAgentNeoModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String agentCode;
  private String stockHolderCode;
  private String lastUpdateUser;
  private ZonedDateTime lastUpdateTime;
  private String operationFlag;
}
