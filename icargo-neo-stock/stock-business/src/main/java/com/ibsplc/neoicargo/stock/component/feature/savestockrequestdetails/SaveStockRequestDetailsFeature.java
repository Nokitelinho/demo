package com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.persistor.StockRequestDetailsPersistor;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("saveStockRequestDetailsFeature")
@RequiredArgsConstructor
public class SaveStockRequestDetailsFeature {
  private final StockRequestDetailsPersistor stockRequestDetailsPersistor;

  public String perform(StockRequestVO stockRequestVO) throws BusinessException {
    log.info("SaveStockRequestDetailsFeature Invoked");
    if (StockHolderVO.OPERATION_FLAG_INSERT.equals(stockRequestVO.getOperationFlag())) {
      return stockRequestDetailsPersistor.save(stockRequestVO);
    }
    if (StockHolderVO.OPERATION_FLAG_UPDATE.equals(stockRequestVO.getOperationFlag())) {
      return stockRequestDetailsPersistor.update(stockRequestVO);
    }
    return null;
  }
}
