package com.ibsplc.neoicargo.stock.component.feature.findpriorities;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockHolderPriorityVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findPrioritiesFeature")
@RequiredArgsConstructor
public class FindPrioritiesFeature {

  private final StockDao stockDao;

  public List<StockHolderPriorityVO> perform(String companyCode, List<String> stockHolderCodes) {
    log.info("FindPriorities Invoked");
    return stockDao.findPriorities(companyCode, stockHolderCodes);
  }
}
