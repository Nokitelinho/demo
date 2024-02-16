package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStock extends BaseModel {
  private static final long serialVersionUID = 1L;

  @Schema(example = "PRIORITY", description = "")
  @JsonProperty("product_code")
  private String productCode;

  @Schema(example = "", description = "")
  @JsonProperty("product_name")
  private String productName;
}
