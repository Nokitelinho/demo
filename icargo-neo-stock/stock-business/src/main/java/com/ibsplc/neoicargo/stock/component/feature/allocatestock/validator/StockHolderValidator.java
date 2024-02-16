package com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_004;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor.StockHolderPersistor;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("stockHolderValidator")
@RequiredArgsConstructor
public class StockHolderValidator extends Validator<StockAllocationVO> {

  private final StockDao stockDao;
  private final StockHolderPersistor stockHolderPersistor;

  public void validate(StockAllocationVO stockAllocationVO) throws BusinessException {
    if (stockAllocationVO.isNewStockFlag()) {
      var stockHolderVO =
          stockDao.findStockHolderDetails(
              stockAllocationVO.getCompanyCode(), stockAllocationVO.getStockHolderCode());
      if (stockHolderVO == null) {
        throw new StockBusinessException(
            constructErrorVO(NEO_STOCK_004.getErrorCode(), NEO_STOCK_004.getErrorMessage(), ERROR));
      }
      if (StockAllocationVO.OPERATION_FLAG_INSERT.equals(stockAllocationVO.getOperationFlag())) {
        stockHolderPersistor.addStock(stockAllocationVO);
      }
    }
  }
}
