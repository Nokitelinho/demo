package com.ibsplc.neoicargo.stock.model.viewfilter;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockAgentFilterModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String agentCode;
  private String stockHolderCode;
  private int pageNumber;
}
