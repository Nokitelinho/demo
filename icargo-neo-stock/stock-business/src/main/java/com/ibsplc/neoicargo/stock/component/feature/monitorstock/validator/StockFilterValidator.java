package com.ibsplc.neoicargo.stock.component.feature.monitorstock.validator;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("stockFilterValidator")
@RequiredArgsConstructor
public class StockFilterValidator extends Validator<StockFilterVO> {
  private final StockDao stockDao;

  public void validate(StockFilterVO stockFilterVO) throws BusinessException {
    log.info("Invoke StockFilterValidator");
    var stockHolderVO =
        stockDao.findStockHolderDetails(
            stockFilterVO.getCompanyCode(), stockFilterVO.getStockHolderCode());
    if (stockHolderVO == null) {
      throw new StockBusinessException(
          constructErrorVO(NEO_STOCK_011.getErrorCode(), NEO_STOCK_011.getErrorMessage(), ERROR));
    }
  }
}
