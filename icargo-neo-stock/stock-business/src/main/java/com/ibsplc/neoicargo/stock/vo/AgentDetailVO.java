package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AgentDetailVO extends AbstractVO {

  private static final long serialVersionUID = 1L;

  private String companyCode;
  private String agentCode;
  private String customerCode;
  private String agentName;
}
