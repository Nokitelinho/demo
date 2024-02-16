package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineValidityDetailsModel extends BaseModel {

  private static final long serialVersionUID = 1L;

  private int airlineIdentifier;
  private int numberCodeUsed;
  private int serialNumber;

  private String operationalFlag;
  private String fourNumberCode;
  private String companyCode;

  private String threeNumberCode;
  private LocalDate validFromDate;
  private LocalDate validTillDate;
}
