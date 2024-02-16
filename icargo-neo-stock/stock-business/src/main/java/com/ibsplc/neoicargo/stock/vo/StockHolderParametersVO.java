package com.ibsplc.neoicargo.stock.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author A-1366
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockHolderParametersVO extends AbstractVO {
  private static final long serialVersionUID = 1L;
  private boolean enableStockHistory;
  private boolean enableConfirmationProcess;
  private int stockIntroductionPeriod;
  private String accountingFlag;
}
