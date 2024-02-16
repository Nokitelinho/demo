package com.ibsplc.neoicargo.stock.vo.filter;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockAllocationFilterVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private String companyCode;
  private String stockHolderCode;
  private int airlineIdentifier;
  private String documentType;
  private String documentSubType;
  private long startRange;
}
