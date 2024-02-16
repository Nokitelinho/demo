package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocumentValidationVO extends AbstractVO {

  private static final long serialVersionUID = 1L;
  private String documentType;
  private String stockHolderCode;
  private String documentSubType;
  private String documentNumber;
  private String stockHolderType;
  private String status;
}
