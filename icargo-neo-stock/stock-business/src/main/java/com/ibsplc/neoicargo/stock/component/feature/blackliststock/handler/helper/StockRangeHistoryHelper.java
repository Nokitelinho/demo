package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.helper;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_UNUSED;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.ibsplc.neoicargo.stock.dao.CQRSDao;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockRangeHistoryHelper {
  private final CQRSDao cqrsDao;

  public List<StockRangeHistoryVO> findStockRangeHistory(StockRangeFilterVO stockRangeFilterVO) {
    boolean isHistory = stockRangeFilterVO.isHistory();
    String awbNumber = stockRangeFilterVO.getAwb();
    if (!isHistory) {
      if (awbNumber != null && awbNumber.trim().length() > 0) {
        return findAwbStockDetails(stockRangeFilterVO);
      } else {
        return findStockUtilisationHistory(stockRangeFilterVO);
      }
    } else {
      return findStockHistory(stockRangeFilterVO);
    }
  }

  private List<StockRangeHistoryVO> findStockHistory(StockRangeFilterVO stockRangeFilterVO) {
    return cqrsDao.findStockHistory(stockRangeFilterVO);
  }

  private List<StockRangeHistoryVO> findStockUtilisationHistory(
      StockRangeFilterVO stockRangeFilterVO) {
    var status = stockRangeFilterVO.getStatus();
    if ((MODE_UNUSED).equalsIgnoreCase(status)) {
      return cqrsDao.findStockUtilisationDetailsStatusUnused(stockRangeFilterVO);
    }
    if ((EMPTY).equals(status)) {
      return cqrsDao.findStockUtilisationDetailsStatusEmpty(stockRangeFilterVO);
    }
    if ((MODE_USED).equalsIgnoreCase(status)) {
      return cqrsDao.findStockUtilisationDetailsStatusUsed(stockRangeFilterVO);
    }
    return List.of();
  }

  private List<StockRangeHistoryVO> findAwbStockDetails(StockRangeFilterVO stockRangeFilterVO) {
    return cqrsDao.findAwbStockDetails(stockRangeFilterVO);
  }
}
