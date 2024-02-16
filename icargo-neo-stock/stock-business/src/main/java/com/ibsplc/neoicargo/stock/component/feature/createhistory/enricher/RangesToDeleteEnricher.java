package com.ibsplc.neoicargo.stock.component.feature.createhistory.enricher;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;

import com.ibsplc.neoicargo.stock.dao.StockRangeHistoryDao;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeHistoryFilterVO;
import io.jsonwebtoken.lang.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RangesToDeleteEnricher {

  private final StockRangeHistoryDao stockRangeHistoryDao;

  public void enrich(
      Set<RangeVO> rangesToDelete,
      StockAllocationVO stockAllocationVO,
      List<RangeVO> newRanges,
      StockRangeHistoryVO stockRangeHistoryVO) {
    if (rangesToDelete != null
        && newRanges != null
        && stockAllocationVO != null
        && stockRangeHistoryVO != null) {
      for (RangeVO rangeVO : newRanges) {
        rangeVO.setCompanyCode(stockAllocationVO.getCompanyCode());
        rangeVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
        rangeVO.setDocumentType(stockAllocationVO.getDocumentType());
        rangeVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
        rangeVO.setStockHolderCode(stockAllocationVO.getStockHolderCode());
        rangeVO.setAsciiStartRange(
            rangeVO.getStartRange() != null ? toLong(rangeVO.getStartRange()) : -1);
        rangeVO.setAsciiEndRange(
            rangeVO.getEndRange() != null ? toLong(rangeVO.getEndRange()) : -1);
        if (rangeVO.getStockHolderCode() == null) {
          rangeVO.setStockHolderCode(stockAllocationVO.getStockControlFor());
        }

        findUsedRanges(rangesToDelete, rangeVO);
      }
      assembleAndRemoveRanges(rangesToDelete, newRanges, stockRangeHistoryVO);
    }
  }

  private void findUsedRanges(Set<RangeVO> rangesToDelete, RangeVO rangeVO) {
    var usedRanges = stockRangeHistoryDao.findUsedRangesForMerge(rangeVO, MODE_USED);
    if (!CollectionUtils.isEmpty(usedRanges)) {
      rangesToDelete.addAll(usedRanges);
    }
  }

  private void assembleAndRemoveRanges(
      Set<RangeVO> rangesToDelete,
      List<RangeVO> newRanges,
      StockRangeHistoryVO stockRangeHistoryVO) {
    if (!rangesToDelete.isEmpty()) {
      newRanges.addAll(rangesToDelete);
      removeRangesIfExist(rangesToDelete, stockRangeHistoryVO);
    }
  }

  private void removeRangesIfExist(
      Set<RangeVO> rangesToDelete, StockRangeHistoryVO stockRangeHistoryVO) {
    var historyFilterVOS = new ArrayList<StockRangeHistoryFilterVO>();
    for (RangeVO rangeVO : rangesToDelete) {
      stockRangeHistoryVO.setCompanyCode(rangeVO.getCompanyCode());
      stockRangeHistoryVO.setAirlineIdentifier(rangeVO.getAirlineIdentifier());
      stockRangeHistoryVO.setDocumentType(rangeVO.getDocumentType());
      stockRangeHistoryVO.setDocumentSubType(rangeVO.getDocumentSubType());
      stockRangeHistoryVO.setFromStockHolderCode(rangeVO.getStockHolderCode());
      stockRangeHistoryVO.setStartRange(rangeVO.getStartRange());
      stockRangeHistoryVO.setEndRange(rangeVO.getEndRange());
      stockRangeHistoryVO.setAsciiStartRange(rangeVO.getAsciiStartRange());
      stockRangeHistoryVO.setAsciiEndRange(rangeVO.getAsciiEndRange());

      historyFilterVOS.add(
          StockRangeHistoryFilterVO.builder()
              .companyCode(rangeVO.getCompanyCode())
              .fromStockHolderCode(rangeVO.getStockHolderCode())
              .airlineId(rangeVO.getAirlineIdentifier())
              .docType(rangeVO.getDocumentType())
              .docSubType(rangeVO.getDocumentSubType())
              .build());
    }

    var historyIdsForRemove = stockRangeHistoryDao.findStockRangeHistoryList(historyFilterVOS);
    if (!Collections.isEmpty(historyIdsForRemove)) {
      stockRangeHistoryDao.deleteAllById(historyIdsForRemove);
    }
  }
}
