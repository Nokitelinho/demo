package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentValidation extends BaseModel {

  private static final long serialVersionUID = 1L;

  @Schema(example = "AWB", description = "")
  private String documentType;

  @Schema(example = "134-23323311", description = "")
  @JsonProperty("document_number")
  private String documentNumber;

  @Schema(example = "AGT001", description = "")
  @JsonProperty("stock_holder_code")
  private String stockHolderCode;

  @Schema(example = "true", description = "")
  @JsonProperty("blacklist_flag")
  private boolean blacklistFlag;

  @JsonProperty("agent_details")
  private Collection<AgentDetail> agentDetails;

  @Schema(example = "S", description = "")
  @JsonProperty("document_subtype")
  private String documentSubType;

  @JsonProperty("product_stocks")
  private Collection<ProductStock> productStocks;
}
