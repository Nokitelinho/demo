package com.ibsplc.neoicargo.stock.component.feature.checkforstockdetails;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("isStockDetailsExistsFeature")
@RequiredArgsConstructor
public class IsStockDetailsExistsFeature {
  private final StockDao stockDao;

  public Boolean perform(DocumentFilterVO vo) {
    log.info("CheckForStockDetailsFeature Invoked");
    return stockDao.isStockDetailsExists(vo);
  }
}
