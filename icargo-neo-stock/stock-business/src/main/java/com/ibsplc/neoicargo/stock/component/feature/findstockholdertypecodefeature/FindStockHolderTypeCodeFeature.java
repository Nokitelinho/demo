package com.ibsplc.neoicargo.stock.component.feature.findstockholdertypecodefeature;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findStockHolderTypeCodeFeature")
@RequiredArgsConstructor
public class FindStockHolderTypeCodeFeature {
  private final StockDao stockDao;

  public void perform(StockRequestVO stockRequestVO) throws BusinessException {
    log.info("findStockHolderTypeCodeFeature Invoked");
    var stockHolderNameList = stockDao.findStockHolderTypeCode(stockRequestVO);

    if (CollectionUtils.isEmpty(stockHolderNameList)) {
      var errorMessage = stockRequestVO.getStockHolderCode() + " for the specified type";
      var errorVo =
          constructErrorVO(
              NEO_STOCK_011.getErrorCode(),
              NEO_STOCK_011.getErrorMessage(),
              ErrorType.ERROR,
              new String[] {errorMessage});
      throw new StockBusinessException(errorVo);
    }
  }
}
