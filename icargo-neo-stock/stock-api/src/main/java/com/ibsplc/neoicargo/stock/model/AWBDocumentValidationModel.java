package com.ibsplc.neoicargo.stock.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AWBDocumentValidationModel extends DocumentValidationModel {

  private static final long serialVersionUID = 1L;
  private List<AgentDetailModel> agentDetails;
}
