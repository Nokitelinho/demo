package com.ibsplc.neoicargo.stock.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockHolderLovVO extends PageableViewVO {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String stockHolderCode;
  private String description;
  private String stockHolderType;
  private String stockHolderName;
}
