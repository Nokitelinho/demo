package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AgentDetailModel extends BaseModel {

  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String agentCode;
  private String customerCode;
  private String agentName;
}
