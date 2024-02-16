package com.ibsplc.neoicargo.stock.vo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AWBDocumentValidationVO extends DocumentValidationVO {

  private static final long serialVersionUID = 1L;

  private List<AgentDetailVO> agentDetails;
}
