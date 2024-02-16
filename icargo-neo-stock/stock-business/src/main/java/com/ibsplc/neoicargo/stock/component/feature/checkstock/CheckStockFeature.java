package com.ibsplc.neoicargo.stock.component.feature.checkstock;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("checkStockFeature")
@RequiredArgsConstructor
public class CheckStockFeature {

  private final StockDao stockDao;

  public void perform(String companyCode, String stockHolderCode, String docType, String docSubType)
      throws BusinessException {
    if (!stockDao.checkStock(companyCode, stockHolderCode, docType, docSubType)) {
      throw new BusinessException(NEO_STOCK_003.getErrorCode(), NEO_STOCK_003.getErrorMessage());
    }
  }
}
