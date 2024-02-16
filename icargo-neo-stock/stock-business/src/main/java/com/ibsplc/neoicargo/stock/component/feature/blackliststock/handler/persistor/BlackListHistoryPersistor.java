package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_BLACKLIST;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.invoker.CreateHistoryInvoker;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.helper.StockRangeHistoryHelper;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.TransitStockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlackListHistoryPersistor {

  private final LocalDate localDateUtil;
  private final CreateHistoryInvoker createHistoryInvoker;
  private final BlackListStockPersistor blackListStockPersistor;
  private final StockRangeHistoryHelper stockRangeHistoryHelper;

  public void createBlacklistHistoryForUsedRanges(BlacklistStockVO blacklistStockVO) {
    log.info("Invoke createBlacklistHistoryForUsedRanges");
    var stockRangeFilterVO = new StockRangeFilterVO();
    var rangeVO = new RangeVO();
    rangeVO.setStartRange(blacklistStockVO.getRangeFrom());
    rangeVO.setEndRange(blacklistStockVO.getRangeTo());
    stockRangeFilterVO.setStatus(StockConstant.MODE_USED);
    stockRangeFilterVO.setStartRange(blacklistStockVO.getRangeFrom());
    stockRangeFilterVO.setEndRange(blacklistStockVO.getRangeTo());
    stockRangeFilterVO.setCompanyCode(blacklistStockVO.getCompanyCode());
    stockRangeFilterVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
    var utilisedHistory = stockRangeHistoryHelper.findStockRangeHistory(stockRangeFilterVO);
    for (StockRangeHistoryVO stockRangeHistoryVO : utilisedHistory) {
      var usedBlacklistedRange = new RangeVO();
      if (toLong(rangeVO.getStartRange()) > toLong(stockRangeHistoryVO.getStartRange())) {
        usedBlacklistedRange.setStartRange(rangeVO.getStartRange());
        usedBlacklistedRange.setAsciiStartRange(toLong(rangeVO.getStartRange()));
      } else {
        usedBlacklistedRange.setStartRange(stockRangeHistoryVO.getStartRange());
        usedBlacklistedRange.setAsciiStartRange(toLong(stockRangeHistoryVO.getStartRange()));
      }
      if (toLong(rangeVO.getEndRange()) > toLong(stockRangeHistoryVO.getEndRange())) {
        usedBlacklistedRange.setEndRange(stockRangeHistoryVO.getEndRange());
        usedBlacklistedRange.setAsciiEndRange((long) stockRangeHistoryVO.getAsciiEndRange());
      } else {
        usedBlacklistedRange.setEndRange(rangeVO.getEndRange());
        usedBlacklistedRange.setAsciiEndRange(toLong(rangeVO.getEndRange()));
      }
      var stkAllocationVo = new StockAllocationVO();
      stkAllocationVo.setCompanyCode(blacklistStockVO.getCompanyCode());
      stkAllocationVo.setStockHolderCode(stockRangeHistoryVO.getFromStockHolderCode());
      stkAllocationVo.setDocumentType(stockRangeHistoryVO.getDocumentType());
      stkAllocationVo.setDocumentSubType(stockRangeHistoryVO.getDocumentSubType());
      stkAllocationVo.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
      stkAllocationVo.setRemarks(blacklistStockVO.getRemarks());
      stkAllocationVo.setRanges(List.of(usedBlacklistedRange));
      stkAllocationVo.setTransactionCode(MODE_BLACKLIST);
      createHistoryInvoker.invoke(stkAllocationVo);
    }
  }

  public void updateBlackHistory(
      List<RangeVO> splitRangeResult,
      BlacklistStockVO blacklistStockVO,
      TransitStockVO transitStock)
      throws BusinessException {
    if (splitRangeResult != null) {
      for (RangeVO range : splitRangeResult) {
        blacklistStockVO.setRangeFrom(range.getStartRange());
        blacklistStockVO.setRangeTo(range.getEndRange());
        blacklistStockVO.setLastUpdateTime(blacklistStockVO.getLastUpdateTime());
        blacklistStockVO.setLastUpdateUser(blacklistStockVO.getLastUpdateUser());
        blacklistStockVO.setStatus("D");
        blacklistStockVO.setBlacklistDate(localDateUtil.getLocalDate(NO_STATION, false));
        blackListStockPersistor.persist(blacklistStockVO);
        var stockAllocationVo = new StockAllocationVO();
        stockAllocationVo.setCompanyCode(transitStock.getCompanyCode());
        stockAllocationVo.setStockHolderCode(transitStock.getStockHolderCode());
        stockAllocationVo.setDocumentType(transitStock.getDocumentType());
        stockAllocationVo.setDocumentSubType(transitStock.getDocumentSubType());
        stockAllocationVo.setAirlineIdentifier(transitStock.getAirlineIdentifier());
        stockAllocationVo.setRemarks(blacklistStockVO.getRemarks());
        stockAllocationVo.setRanges(List.of(range));
        stockAllocationVo.setTransactionCode(MODE_BLACKLIST);
        createHistoryInvoker.invoke(stockAllocationVo);
      }
    }
  }
}
