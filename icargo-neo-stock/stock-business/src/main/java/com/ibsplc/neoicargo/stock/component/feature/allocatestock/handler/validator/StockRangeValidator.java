package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockRangeValidator {

  private final RangeFormatValidator rangeFormatValidator;
  private final InvalidStockValidator invalidStockValidator;
  private final BlackListRangeValidator blackListRangeValidator;

  public void validate(StockVO stockVO, StockAllocationVO stockAllocationVO, boolean isBlacklist)
      throws BusinessException {
    log.info("Invoked StockRangeValidator");
    rangeFormatValidator.validate(stockAllocationVO);
    if (!isBlacklist) {
      blackListRangeValidator.validate(stockAllocationVO);
    }
    if (!stockAllocationVO.isNewStockFlag() && !isBlacklist) {
      invalidStockValidator.validate(stockVO, stockAllocationVO);
    }
  }
}
