package com.ibsplc.neoicargo.stock.component.feature.cancelstockrequest;

import com.ibsplc.neoicargo.stock.component.feature.cancelstockrequest.persistor.CancelStockRequestPersistor;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("cancelStockRequest")
@RequiredArgsConstructor
public class CancelStockRequestFeature {

  private final CancelStockRequestPersistor cancelStockRequestPersistor;

  public void perform(StockRequestVO stockRequestVO) throws StockBusinessException {
    cancelStockRequestPersistor.cancelStockRequest(stockRequestVO);
  }
}
