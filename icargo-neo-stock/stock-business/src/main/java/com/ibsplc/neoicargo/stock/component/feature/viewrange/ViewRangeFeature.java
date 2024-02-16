package com.ibsplc.neoicargo.stock.component.feature.viewrange;

import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.vo.StockRangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("viewRangeFeature")
@RequiredArgsConstructor
public class ViewRangeFeature {
  private final RangeDao rangeDao;

  public StockRangeVO perform(StockFilterVO stockFilterVO) {
    log.info("ViewRangeFeature Invoked");
    var stockRangeVO = new StockRangeVO();

    if (stockFilterVO.isViewRange()) {
      stockRangeVO.setAllocatedRanges(rangeDao.findAllocatedRanges(stockFilterVO));
    }
    stockRangeVO.setAvailableRanges(rangeDao.findRangesForViewRange(stockFilterVO));
    return stockRangeVO;
  }
}
