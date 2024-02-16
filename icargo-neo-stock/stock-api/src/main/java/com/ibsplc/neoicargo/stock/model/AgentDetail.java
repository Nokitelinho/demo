package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentDetail extends BaseModel {

  private static final long serialVersionUID = 1L;

  @Schema(example = "DHLCDSC", description = "")
  private String agentCode;

  @Schema(example = "DHLCDSC", description = "")
  @JsonProperty("stockCustomerCode")
  private String customerCode;

  @Schema(example = "DHL WORLDWIDE DINTERNATIONAL", description = "")
  private String agentName;
}
