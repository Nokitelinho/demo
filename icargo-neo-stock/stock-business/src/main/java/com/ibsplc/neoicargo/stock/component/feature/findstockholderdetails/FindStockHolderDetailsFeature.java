package com.ibsplc.neoicargo.stock.component.feature.findstockholderdetails;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findStockHolderDetailsFeature")
@RequiredArgsConstructor
public class FindStockHolderDetailsFeature {

  private final StockDao stockDao;

  public StockHolderVO perform(String companyCode, String stockHolderCode) {
    log.info("FindStockHolderDetailsFeature Invoked");
    return stockDao.findStockHolderDetails(companyCode, stockHolderCode);
  }
}
