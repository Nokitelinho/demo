package com.ibsplc.neoicargo.stock.component.feature.findmonitoringstockholderdetails;

import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.MonitorStockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findMonitoringStockHolderDetailsFeature")
@FeatureConfigSource("stock/findmonitoringstockholderdetails")
@RequiredArgsConstructor
public class FindMonitoringStockHolderDetailsFeature extends AbstractFeature<StockFilterVO> {
  private final StockDao stockDao;

  @Override
  public MonitorStockVO perform(StockFilterVO stockFilterVO) {
    log.info("findMonitoringStockHolderDetailsFeature Invoked");
    return stockDao.findMonitoringStockHolderDetails(stockFilterVO);
  }
}
