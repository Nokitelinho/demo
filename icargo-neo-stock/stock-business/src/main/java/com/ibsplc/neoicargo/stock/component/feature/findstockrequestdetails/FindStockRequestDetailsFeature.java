package com.ibsplc.neoicargo.stock.component.feature.findstockrequestdetails;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.model.StockRequestModel;
import com.ibsplc.neoicargo.stock.vo.StockRequestFilterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findStockRequestDetailsFeature")
@RequiredArgsConstructor
public class FindStockRequestDetailsFeature {

  private final StockDao stockDao;

  public StockRequestModel perform(StockRequestFilterVO stockRequestFilterVO) {
    log.info("FindStockRequestDetailsFeature Invoked");
    return stockDao.findStockRequestDetails(stockRequestFilterVO);
  }
}
