package com.ibsplc.neoicargo.stock.model.viewfilter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocumentFilterModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String documentType;
  private String documentSubType;
  private String documentNumber;
  private int airlineIdentifier;
  private String stockOwner;

  @JsonProperty("isOtherAirline")
  private boolean isOtherAirline;

  private String status;
  //  private boolean skipStockLocking;

  //  @JsonProperty("isExecutionAfterReopen")
  //  private boolean isExecutionAfterReopen;

  //  private LocalDate executionDate;
  private String stockHolderCode;
  private String lastUpdateUser;
  private String shipmentPrefix;
  //  private String awbDestination;
  //  private String awbOrigin;
  //  private String awbViaPoints;
}
