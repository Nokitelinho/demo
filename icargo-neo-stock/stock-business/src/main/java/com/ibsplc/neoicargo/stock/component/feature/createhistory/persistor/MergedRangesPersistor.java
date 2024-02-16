package com.ibsplc.neoicargo.stock.component.feature.createhistory.persistor;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.countNumberOfDocuments;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;

import com.ibsplc.neoicargo.stock.dao.StockRangeHistoryDao;
import com.ibsplc.neoicargo.stock.mapper.StockRangeHistoryMapper;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MergedRangesPersistor {

  private final StockRangeHistoryMapper stockRangeHistoryMapper;
  private final StockRangeHistoryDao stockRangeHistoryDao;

  public void persist(
      StockRangeHistoryVO stockRangeHistoryVO,
      List<RangeVO> requestRanges,
      boolean isFromStockHolderCodeChecked) {
    if (!CollectionUtils.isEmpty(requestRanges) && stockRangeHistoryVO != null) {
      for (RangeVO rangeVO : requestRanges) {
        stockRangeHistoryVO.setStartRange(rangeVO.getStartRange());
        stockRangeHistoryVO.setEndRange(rangeVO.getEndRange());
        if (rangeVO.getAsciiStartRange() == 0 && rangeVO.getStartRange() != null) {
          rangeVO.setAsciiStartRange(toLong(rangeVO.getStartRange()));
        }
        if (rangeVO.getAsciiEndRange() == 0 && rangeVO.getEndRange() != null) {
          rangeVO.setAsciiEndRange(toLong(rangeVO.getEndRange()));
        }
        stockRangeHistoryVO.setAsciiStartRange(rangeVO.getAsciiStartRange());
        stockRangeHistoryVO.setAsciiEndRange(rangeVO.getAsciiEndRange());
        long numberOfDocuments =
            countNumberOfDocuments(rangeVO.getAsciiStartRange(), rangeVO.getAsciiEndRange());
        rangeVO.setNumberOfDocuments(numberOfDocuments);
        stockRangeHistoryVO.setNumberOfDocuments(numberOfDocuments);

        checkApproachToSave(stockRangeHistoryVO, isFromStockHolderCodeChecked);
      }
    }
  }

  private void checkApproachToSave(
      StockRangeHistoryVO stockRangeHistoryVO, boolean isFromStockHolderCodeChecked) {
    if (isFromStockHolderCodeChecked) {
      if (stockRangeHistoryVO.getFromStockHolderCode() != null) {
        mapAndSave(stockRangeHistoryVO);
      }
    } else {
      mapAndSave(stockRangeHistoryVO);
    }
  }

  private void mapAndSave(StockRangeHistoryVO stockRangeHistoryVO) {
    var stockRangeHistory = stockRangeHistoryMapper.mapVoToEntity(stockRangeHistoryVO);
    stockRangeHistoryDao.save(stockRangeHistory);
  }
}
