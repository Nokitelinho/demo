package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.TRANSIT_STATUS_MISSING;
import static com.ibsplc.neoicargo.stock.util.StockConstant.TRANSIT_STATUS_NOT_CONFIRMED;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.helper.RangeHelper;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor.BlackListHistoryPersistor;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor.TransitStockPersistor;
import com.ibsplc.neoicargo.stock.dao.BlacklistStockDao;
import com.ibsplc.neoicargo.stock.mapper.TransitStockMapper;
import com.ibsplc.neoicargo.stock.util.LoggerUtil;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.MissingStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.TransitStockVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlackListMissingStockHandler {
  private final BlackListHistoryPersistor blackListHistoryPersistor;
  private final ContextUtil contextUtil;
  private final LocalDate localDateUtil;
  private final TransitStockPersistor transitStockPersistor;
  private final RangeHelper rangeHelper;
  private final TransitStockMapper transitStockMapper;
  private final BlacklistStockDao blacklistStockDao;

  public void blacklistMissingStock(BlacklistStockVO blacklistStockVO) throws BusinessException {
    log.info("Invoke blacklistMissingStock");
    var logonAttributes = contextUtil.callerLoginProfile();
    var transitStockVOs = findBlackListRangesFromTransit(blacklistStockVO);
    List<RangeVO> splitRangeResult;
    List<TransitStockVO> currentTransitStockVOs;
    var transitStockVO = new TransitStockVO();
    transitStockVO.setStockHolderCode(blacklistStockVO.getStockHolderCode());
    transitStockVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
    transitStockVO.setDocumentType(blacklistStockVO.getDocumentType());
    transitStockVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
    transitStockVO.setCompanyCode(blacklistStockVO.getCompanyCode());
    transitStockVO.setConfirmStatus(TRANSIT_STATUS_MISSING);
    var missingStockVO = new MissingStockVO();
    missingStockVO.setCompanyCode(blacklistStockVO.getCompanyCode());
    missingStockVO.setDocumentType(blacklistStockVO.getDocumentType());
    missingStockVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
    missingStockVO.setStockHolderCode(blacklistStockVO.getStockHolderCode());
    if (transitStockVOs.size() > 0) {
      for (TransitStockVO transitStock : transitStockVOs) {
        splitRangeResult = new ArrayList<>();
        missingStockVO.setMissingStartRange(blacklistStockVO.getRangeFrom());
        missingStockVO.setMissingEndRange(blacklistStockVO.getRangeTo());
        missingStockVO.setAsciiMissingStartRange(toLong(blacklistStockVO.getRangeFrom()));
        missingStockVO.setAsciiMissingEndRange(toLong(blacklistStockVO.getRangeTo()));
        transitStockVO.setAsciiMissingStartRange(toLong(transitStock.getMissingStartRange()));
        transitStockVO.setAsciiMissingEndRange(toLong(transitStock.getMissingEndRange()));
        transitStockVO.setStockHolderCode(transitStock.getStockHolderCode());
        transitStockVO.setStockControlFor(transitStock.getStockControlFor());
        transitStockVO.setTxnDate(transitStock.getTxnDate());
        transitStockVO.setMissingRemarks(transitStock.getMissingRemarks());
        transitStockVO.setTxnCode(transitStock.getTxnCode());
        var missingStockVOs = new ArrayList<MissingStockVO>();
        missingStockVOs.add(missingStockVO);
        transitStockVO.setMissingRanges(missingStockVOs);
        var rangeVOsForAllocation = getConfirmedStock(transitStockVO);
        var transitStockVOsForAllocation = new ArrayList<TransitStockVO>();
        for (RangeVO rangevo : rangeVOsForAllocation) {
          var transitStockVOForAllocation = transitStockMapper.clone(transitStockVO);
          transitStockVOForAllocation.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
          transitStockVOForAllocation.setCompanyCode(blacklistStockVO.getCompanyCode());
          transitStockVOForAllocation.setActualStartRange(rangevo.getStartRange());
          transitStockVOForAllocation.setActualEndRange(rangevo.getEndRange());
          transitStockVOForAllocation.setConfirmStatus(TRANSIT_STATUS_MISSING);
          transitStockVOForAllocation.setOperationFlag(TransitStockVO.OPERATION_FLAG_INSERT);
          transitStockVOForAllocation.setMissingStartRange(rangevo.getStartRange());
          transitStockVOForAllocation.setAsciiMissingStartRange(toLong(rangevo.getStartRange()));
          transitStockVOForAllocation.setMissingEndRange(rangevo.getEndRange());
          transitStockVOForAllocation.setAsciiMissingEndRange(toLong(rangevo.getEndRange()));
          transitStockVOForAllocation.setMissingNumberOfDocs(
              (missingStockVO.getAsciiMissingEndRange()
                      - missingStockVO.getAsciiMissingStartRange())
                  + 1);
          transitStockVOForAllocation.setLastUpdateTime(
              localDateUtil.getLocalDate(logonAttributes.getAirportCode(), true));
          transitStockVOForAllocation.setLastUpdateUser(logonAttributes.getUserId());
          transitStockVOsForAllocation.add(transitStockVOForAllocation);
        }
        transitStockPersistor.persist(transitStockVOsForAllocation);
        currentTransitStockVOs = new ArrayList<>();
        currentTransitStockVOs.add(transitStock);
        splitRangeResult.addAll(splitRanges(currentTransitStockVOs, rangeVOsForAllocation));
        blackListHistoryPersistor.updateBlackHistory(
            splitRangeResult, blacklistStockVO, transitStock);
        var transitStockVOsToRemoveFromTransit = new ArrayList<TransitStockVO>();
        var transitStockVOtoDeleteFromTransit = transitStockMapper.clone(transitStock);
        transitStockVOtoDeleteFromTransit.setOperationFlag(TransitStockVO.OPERATION_FLAG_DELETE);
        transitStockVOsToRemoveFromTransit.add(transitStockVOtoDeleteFromTransit);
        transitStockPersistor.persist(transitStockVOsToRemoveFromTransit);
      }
    }
  }

  private List<RangeVO> getConfirmedStock(TransitStockVO transitStockVO) {
    List<RangeVO> ranges = new ArrayList<>();
    List<MissingStockVO> missingStockVOs = transitStockVO.getMissingRanges();
    Long[] startRanges = new Long[missingStockVOs.size()];
    Long[] endRanges = new Long[missingStockVOs.size()];
    long startRange = 0;
    long endRange = 0;

    if (TRANSIT_STATUS_NOT_CONFIRMED.equals(transitStockVO.getConfirmStatus())) {
      startRange = toLong(transitStockVO.getActualStartRange());
      endRange = toLong(transitStockVO.getActualEndRange());
    } else if (TRANSIT_STATUS_MISSING.equals(transitStockVO.getConfirmStatus())) {
      startRange = transitStockVO.getAsciiMissingStartRange();
      endRange = transitStockVO.getAsciiMissingEndRange();
    }
    int i = 0;
    for (MissingStockVO missingStockVO : missingStockVOs) {
      startRanges[i] = missingStockVO.getAsciiMissingStartRange();
      endRanges[i] = missingStockVO.getAsciiMissingEndRange();
      i++;
    }

    Arrays.sort(startRanges);
    Arrays.sort(endRanges);

    long tempStart = startRange;
    long tempEnd = endRange;
    i = 0;
    for (Long start : startRanges) {
      if (start > tempStart && start > tempEnd) {
        return new ArrayList<>();
      }
      if (start > tempStart) {
        RangeVO rangeVO = new RangeVO();
        rangeVO.setStartRange(String.valueOf(tempStart));
        rangeVO.setEndRange(String.valueOf(start - 1));
        ranges.add(rangeVO);
      }
      tempStart = endRanges[i] + 1;
      i++;
    }
    if (tempStart <= tempEnd) {
      RangeVO rangeVO = new RangeVO();
      rangeVO.setStartRange(String.valueOf(tempStart));
      rangeVO.setEndRange(String.valueOf(tempEnd));
      ranges.add(rangeVO);
    }
    return ranges;
  }

  private List<TransitStockVO> findBlackListRangesFromTransit(BlacklistStockVO blacklistStockVO) {
    return blacklistStockDao.findBlackListRangesFromTransit(blacklistStockVO);
  }

  private List<RangeVO> splitRanges(List<TransitStockVO> transitStockVOS, List<RangeVO> rangeVOS) {
    var originalRanges =
        transitStockVOS.stream()
            .map(
                transitStockVO -> {
                  var rangeVO = new RangeVO();
                  rangeVO.setStartRange(transitStockVO.getMissingStartRange());
                  rangeVO.setEndRange(transitStockVO.getMissingEndRange());
                  return rangeVO;
                })
            .collect(Collectors.toSet());

    for (RangeVO rangevo : rangeVOS) {
      if (rangevo.getStartRange().length() < StockConstant.SUBSTRING_COUNT) {
        StringBuilder sb = new StringBuilder(EMPTY);
        sb.append(
            "0"
                .repeat(
                    Math.max(0, StockConstant.SUBSTRING_COUNT - rangevo.getStartRange().length())));
        rangevo.setStartRange(sb.append(rangevo.getStartRange()).toString());
      }
      if (rangevo.getEndRange().length() < StockConstant.SUBSTRING_COUNT) {
        StringBuilder sb = new StringBuilder(EMPTY);
        sb.append(
            "0"
                .repeat(
                    Math.max(0, StockConstant.SUBSTRING_COUNT - rangevo.getEndRange().length())));
        rangevo.setEndRange(sb.append(rangevo.getEndRange()).toString());
      }
    }
    var splitRanges = rangeHelper.splitRanges(originalRanges, rangeVOS);
    log.info("Split ranges");
    LoggerUtil.logRanges(splitRanges);
    return splitRanges;
  }
}
