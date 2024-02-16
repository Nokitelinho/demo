package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentFilter extends BaseModel {

  private static final long serialVersionUID = 1L;

  @JsonProperty("company_code")
  private String companyCode;

  @JsonProperty("airline_identifier")
  private int airlineIdentifier;

  @JsonProperty("stock_owner")
  private String stockOwner;

  @JsonProperty("awb_destination")
  private String awbDestination;

  @JsonProperty("awb_origin")
  private String awbOrigin;

  @Schema(example = "TRVDXB12345678", description = "Customer code")
  @JsonProperty("customer_code")
  private String customerCode;

  @JsonProperty("document_type")
  private String documentType;

  @JsonProperty("document_number")
  private String documentNumber;

  @JsonProperty("prefix")
  private String prefix;

  @JsonProperty("master_document_number")
  private String mstdocnum;

  @JsonProperty("document_sub_type")
  private String documentSubType;

  @JsonProperty("stock_holder_code")
  private String stockHolderCode;
}
