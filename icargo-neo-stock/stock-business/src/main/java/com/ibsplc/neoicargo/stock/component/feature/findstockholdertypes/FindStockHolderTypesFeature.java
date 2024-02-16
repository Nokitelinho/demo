package com.ibsplc.neoicargo.stock.component.feature.findstockholdertypes;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockHolderPriorityVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findStockHolderTypesFeature")
@RequiredArgsConstructor
public class FindStockHolderTypesFeature {

  private final StockDao stockDao;

  public List<StockHolderPriorityVO> perform(String companyCode) {
    log.info("FindStockHolderTypesFeature Invoked");
    return stockDao.findStockHolderTypes(companyCode);
  }
}
