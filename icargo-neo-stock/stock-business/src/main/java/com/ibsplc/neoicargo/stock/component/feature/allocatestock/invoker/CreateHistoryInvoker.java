package com.ibsplc.neoicargo.stock.component.feature.allocatestock.invoker;

import com.ibsplc.neoicargo.stock.component.feature.createhistory.CreateHistoryFeature;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateHistoryInvoker {
  private final CreateHistoryFeature createHistoryFeature;

  public void invoke(StockAllocationVO stockAllocationVO) {
    createHistoryFeature.perform(stockAllocationVO, stockAllocationVO.getTransactionCode());
  }
}
