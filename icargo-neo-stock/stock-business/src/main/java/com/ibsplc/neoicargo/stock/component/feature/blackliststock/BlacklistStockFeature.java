package com.ibsplc.neoicargo.stock.component.feature.blackliststock;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator.StockRangeUtilisationForRangeValidator;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.BlackListStockHandler;
import com.ibsplc.neoicargo.stock.component.feature.monitorstock.validator.StockFilterValidator;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("blacklistStockFeature")
@RequiredArgsConstructor
public class BlacklistStockFeature {

  private final ContextUtil contextUtil;
  private final StockRangeUtilisationForRangeValidator rangeUtilisationValidator;
  private final StockFilterValidator stockFilterValidator;
  private final StockDao stockDao;
  private final BlackListStockHandler blackListStockHandler;

  public void perform(BlacklistStockVO blacklistStockVO) throws BusinessException {
    log.info("Invoke BlacklistStockFeature");
    var allocationVO = createStockAllocationFromBlacklistStock(blacklistStockVO);
    rangeUtilisationValidator.validate(allocationVO);
    if (isNotBlank(blacklistStockVO.getRangeFrom()) && isNotBlank(blacklistStockVO.getRangeTo())) {
      blackListStockHandler.blacklistStockHolderStock(blacklistStockVO);
    } else {
      log.info("Income RangeFrom and RangeTo is empty");
      var stockFilterVO =
          StockFilterVO.builder()
              .companyCode(allocationVO.getCompanyCode())
              .stockHolderCode(allocationVO.getStockHolderCode())
              .build();
      stockFilterValidator.validate(stockFilterVO);
      var stockVO = findStockWithRanges(allocationVO);
      for (RangeVO range : stockVO.getRanges()) {
        blacklistStockVO.setRangeFrom(range.getStartRange());
        blacklistStockVO.setRangeTo(range.getEndRange());
        blackListStockHandler.blacklistStockHolderStock(blacklistStockVO);
      }
    }
  }

  private StockAllocationVO createStockAllocationFromBlacklistStock(
      BlacklistStockVO blacklistStockVO) {
    return StockAllocationVO.builder()
        .companyCode(blacklistStockVO.getCompanyCode())
        .airlineIdentifier(blacklistStockVO.getAirlineIdentifier())
        .stockHolderCode(blacklistStockVO.getStockHolderCode())
        .documentType(blacklistStockVO.getDocumentType())
        .documentSubType(blacklistStockVO.getDocumentSubType())
        .lastUpdateTime(blacklistStockVO.getLastUpdateTime())
        .lastUpdateUser(contextUtil.callerLoginProfile().getUserId())
        .ranges(
            List.of(
                RangeVO.builder()
                    .startRange(blacklistStockVO.getRangeFrom())
                    .endRange(blacklistStockVO.getRangeTo())
                    .build()))
        .build();
  }

  private StockVO findStockWithRanges(StockAllocationVO stockAllocationVO)
      throws StockBusinessException {
    var stockVO =
        stockDao.findStockWithRanges(
            stockAllocationVO.getCompanyCode(),
            stockAllocationVO.getStockHolderCode(),
            stockAllocationVO.getAirlineIdentifier(),
            stockAllocationVO.getDocumentType(),
            stockAllocationVO.getDocumentSubType());
    if (stockVO == null) {
      throw new StockBusinessException(
          constructErrorVO(NEO_STOCK_003.getErrorCode(), NEO_STOCK_003.getErrorMessage(), ERROR));
    }
    return stockVO;
  }
}
