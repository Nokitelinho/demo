package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentValidationModel extends BaseModel {

  private static final long serialVersionUID = 1L;
  private String documentType;
  private String stockHolderCode;
  private String documentSubType;
  private String documentNumber;
  private String stockHolderType;
  private String status;
}
