package com.ibsplc.neoicargo.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockRequestOALModel extends BaseModel {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String airportCode;
  private String documentType;
  private String documentSubType;
  private String modeOfCommunication;
  private String content;
  private String airlineCode;
  private String address;
  private String operationFlag;
  private String triggerPoint;
  private LocalDate requestedDate;
  private LocalDate actionTime;
  private int airlineId;
  private int serialNumber;

  @JsonProperty("isRequestCompleted")
  private boolean isRequestCompleted;

  private boolean ignoreWarnings;
}
