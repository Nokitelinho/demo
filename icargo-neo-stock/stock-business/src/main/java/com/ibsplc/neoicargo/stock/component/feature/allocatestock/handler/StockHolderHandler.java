package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOCUMENT_TYPE;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.persistor.StockPersistor;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator.StockPeriodValidator;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator.StockRangeValidator;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.exception.StockErrors;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderParametersVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockHolderHandler {

  private final StockDao stockDao;
  private final StockPersistor stockPersistor;
  private final StockRangeValidator stockRangeValidator;
  private final StockPeriodValidator stockPeriodValidator;
  private final StockHandler stockHandler;

  public void allocate(
      StockAllocationVO stockAllocationVO, StockHolderParametersVO systemParameters)
      throws BusinessException {
    final var stockHolderVO =
        stockDao.findStockHolderDetails(
            stockAllocationVO.getCompanyCode(), stockAllocationVO.getStockHolderCode());
    if (stockHolderVO == null) {
      throw new StockBusinessException(
          constructErrorVO(
              StockErrors.NEO_STOCK_008.getErrorCode(),
              StockErrors.NEO_STOCK_008.getErrorMessage(),
              ERROR));
    }
    if (systemParameters.isEnableStockHistory()) {
      stockAllocationVO.setEnableStockHistory(true);
    }

    if (stockAllocationVO.isNewStockFlag()
        && !stockAllocationVO.isBlacklist()
        && DOCUMENT_TYPE.equals(stockAllocationVO.getDocumentType())) {
      stockPeriodValidator.validate(
          stockAllocationVO, systemParameters.getStockIntroductionPeriod());
    }

    final var stockVO =
        findStockWithRanges(
            stockAllocationVO.getCompanyCode(),
            stockAllocationVO.getStockHolderCode(),
            stockAllocationVO.getAirlineIdentifier(),
            stockAllocationVO.getDocumentType(),
            stockAllocationVO.getDocumentSubType());

    if (stockAllocationVO.isNewStockFlag() && !stockAllocationVO.isBlacklist()) {
      stockRangeValidator.validate(stockVO, stockAllocationVO, false);
    }
    stockHandler.allocate(stockVO, stockAllocationVO);

    stockPersistor.updateStock(stockVO, stockAllocationVO, false);
  }

  public void deplete(StockAllocationVO stockAllocationVO, boolean isBlacklist)
      throws BusinessException {
    log.info("Invoked stock holder deplete");
    final var stockVO =
        findStockWithRanges(
            stockAllocationVO.getCompanyCode(),
            stockAllocationVO.getStockControlFor(),
            stockAllocationVO.getAirlineIdentifier(),
            stockAllocationVO.getDocumentType(),
            stockAllocationVO.getDocumentSubType());
    stockRangeValidator.validate(stockVO, stockAllocationVO, isBlacklist);
    stockHandler.deplete(stockVO, stockAllocationVO, isBlacklist);

    stockPersistor.updateStock(stockVO, stockAllocationVO, true);
  }

  private StockVO findStockWithRanges(
      String companyCode,
      String stockHolderCode,
      int airlineIdentifier,
      String documentType,
      String documentSubType)
      throws StockBusinessException {
    var stockVO =
        stockDao.findStockWithRanges(
            companyCode, stockHolderCode, airlineIdentifier, documentType, documentSubType);
    if (stockVO == null) {
      throw new StockBusinessException(
          constructErrorVO(NEO_STOCK_003.getErrorCode(), NEO_STOCK_003.getErrorMessage(), ERROR));
    }
    return stockVO;
  }
}
