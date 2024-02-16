package com.ibsplc.neoicargo.stock.component.feature.rejectstockrequests;

import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.stock.dao.StockRequestDao;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FeatureConfigSource("stock/rejectstockrequests")
@RequiredArgsConstructor
public class RejectStockRequestsFeature {
  private final StockRequestDao stockRequestDao;

  public void perform(List<StockRequestVO> stockRequestVOS) {
    log.info("RejectStockRequestsFeature Invoked");
    stockRequestDao.rejectStockRequests(stockRequestVOS);
  }
}
