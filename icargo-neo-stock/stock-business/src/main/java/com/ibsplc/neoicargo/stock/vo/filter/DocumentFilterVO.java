package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentFilterVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private String customerCode;
  private String prefix;
  private String mstdocnum;
  private String documentNumber;
  private String documentType;
  private String documentSubType;
  private int airlineIdentifier;
  private String stockHolderCode;
  private String companyCode;
  private String stockOwner;
  private String awbDestination;
  private String awbOrigin;
}
