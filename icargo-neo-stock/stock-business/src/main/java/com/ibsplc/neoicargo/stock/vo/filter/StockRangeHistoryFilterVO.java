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
public class StockRangeHistoryFilterVO extends AbstractVO {
  private static final long serialVersionUID = 1L;

  private String companyCode;
  private Integer airlineId;
  private String docType;
  private String docSubType;
  private String fromStockHolderCode;
}
