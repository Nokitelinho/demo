package com.ibsplc.neoicargo.stock.component.feature.findstockrangehistory;

import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.helper.StockRangeHistoryHelper;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findStockRangeHistoryFeature")
@RequiredArgsConstructor
public class FindStockRangeHistoryFeature {

  private final StockRangeHistoryHelper stockRangeHistoryHelper;

  public List<StockRangeHistoryVO> perform(StockRangeFilterVO stockRangeFilterVO) {
    log.info("FindStockRangeHistoryFeature Invoked");
    return stockRangeHistoryHelper.findStockRangeHistory(stockRangeFilterVO);
  }
}
