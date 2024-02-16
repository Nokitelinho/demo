package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineParametersModel extends BaseModel {

  private static final long serialVersionUID = 1L;

  private int airlineIdentifier;

  private boolean lovFlag;

  private String companyCode;
  private String parameterCode;
  private String parameterValue;
  private String operationalFlag;

  private Collection<String> oneTimeCode;
  private Collection<String> lov;
}
