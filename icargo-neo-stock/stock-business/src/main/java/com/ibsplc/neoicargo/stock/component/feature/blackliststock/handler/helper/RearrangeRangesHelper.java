package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.helper;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;

import com.ibsplc.neoicargo.stock.util.LoggerUtil;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RearrangeRangesHelper {

  public List<StockVO> rearrangeRanges(List<StockVO> stocks, BlacklistStockVO blacklistStockVO) {
    if (!CollectionUtils.isEmpty(stocks)) {
      log.info("Before rearrange");
      LoggerUtil.logStocks(stocks);
      long maxEndRange = 0;
      long minStartRange = 0;
      RangeVO rangeMax = null;
      RangeVO rangeMin = null;
      for (int i = 0; i < stocks.size(); i++) {
        StockVO stockVo = stocks.get(i);
        ArrayList<RangeVO> ranges = (ArrayList<RangeVO>) stockVo.getRanges();
        RangeVO rangeVo = null;
        if (ranges.size() > 0) {
          rangeVo = ranges.get(0);
        }
        if (rangeVo != null && i == 0) {
          maxEndRange = rangeVo.getAsciiEndRange();
          rangeMax = rangeVo;
          minStartRange = rangeVo.getAsciiStartRange();
          rangeMin = rangeVo;
        } else {
          if (rangeVo != null && (rangeVo.getAsciiEndRange() > maxEndRange)) {
            maxEndRange = rangeVo.getAsciiEndRange();
            rangeMax = rangeVo;
          }
          if (rangeVo != null && (rangeVo.getAsciiStartRange() < minStartRange)) {
            minStartRange = rangeVo.getAsciiStartRange();
            rangeMin = rangeVo;
          }
        }
      }
      long startRange = toLong(blacklistStockVO.getRangeFrom());
      long endRange = toLong(blacklistStockVO.getRangeTo());
      if (rangeMax != null && endRange < rangeMax.getAsciiEndRange()) {
        rangeMax.setEndRange(blacklistStockVO.getRangeTo());
      }
      if (rangeMin != null && startRange > rangeMin.getAsciiStartRange()) {
        rangeMin.setStartRange(blacklistStockVO.getRangeFrom());
      }
    }
    log.info("After rearrange");
    LoggerUtil.logStocks(stocks);
    return stocks;
  }
}
