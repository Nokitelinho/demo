package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_010;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DELIMITER;
import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.helper.RangeHelper;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.persistor.StockPersistor;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.proxy.impl.DocumentTypeProxy;
import com.ibsplc.neoicargo.stock.util.LoggerUtil;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.DuplicateRangeFilterVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockHandler {

  private final StockPersistor stockPersistor;
  private final RangeDao rangeDao;
  private final RangeHelper rangeHelper;
  private final DocumentTypeProxy documentTypeProxy;

  public void allocate(StockVO stockVO, StockAllocationVO stockAllocationVO)
      throws BusinessException {
    log.info("Invoked allocate");
    List<RangeVO> creatingRange = null;
    if (stockAllocationVO.isNewStockFlag() && !stockAllocationVO.isBlacklist()) {
      var availableRange = duplicateRangeCheck(stockAllocationVO);
      if (availableRange.size() == 0) {
        creatingRange = stockAllocationVO.getRanges();
      } else {
        validateDuplicateRange(availableRange);
      }
    } else {
      creatingRange = stockAllocationVO.getRanges();
    }

    var persistingRanges = mergeRanges(stockVO, creatingRange, stockAllocationVO.isManual());
    if (creatingRange != null) {
      var rangesToDelete = rangeHelper.findRangesForMerge(creatingRange);
      if (!rangesToDelete.isEmpty()) {
        rangeDao.removeAll(rangesToDelete);
      }
    }
    stockPersistor.addRange(stockVO, persistingRanges, stockAllocationVO.isManual());
  }

  private void validateDuplicateRange(Set<RangeVO> availableRange) throws StockBusinessException {
    final var rangeExists =
        availableRange.stream()
            .map(rangeVo -> rangeVo.getStartRange() + HYPHEN + rangeVo.getEndRange())
            .collect(Collectors.joining(DELIMITER));
    if (!rangeExists.isEmpty()) {
      log.error("Range {} already exists in the system", rangeExists);
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_010.getErrorCode(),
              NEO_STOCK_010.getErrorMessage(),
              ERROR,
              new String[] {rangeExists}));
    }
  }

  private Set<RangeVO> duplicateRangeCheck(StockAllocationVO stockAllocationVO) {
    var availableRange = new HashSet<RangeVO>();
    for (RangeVO rangeVo : stockAllocationVO.getRanges()) {
      availableRange.addAll(rangeDao.findDuplicateRanges(buildFilter(stockAllocationVO, rangeVo)));
    }
    return availableRange;
  }

  private DuplicateRangeFilterVO buildFilter(StockAllocationVO stockAllocationVO, RangeVO rangeVO) {
    return DuplicateRangeFilterVO.builder()
        .airlineIdentifier(stockAllocationVO.getAirlineIdentifier())
        .companyCode(stockAllocationVO.getCompanyCode())
        .documentType(stockAllocationVO.getDocumentType())
        .endRange(rangeVO.getEndRange())
        .startRange(rangeVO.getStartRange())
        .build();
  }

  private List<RangeVO> mergeRanges(StockVO stockVO, List<RangeVO> mergeRanges, boolean manual) {
    var sharedRange = new ArrayList<SharedRangeVO>();
    var outputRanges = new ArrayList<RangeVO>();
    if (mergeRanges != null) {
      for (RangeVO rangeVo : mergeRanges) {
        rangeVo.setManual(manual);
        rangeVo.setCompanyCode(stockVO.getCompanyCode());
        rangeVo.setDocumentType(stockVO.getDocumentType());
        rangeVo.setDocumentSubType(stockVO.getDocumentSubType());
        rangeVo.setAirlineIdentifier(stockVO.getAirlineIdentifier());
        rangeVo.setStockHolderCode(stockVO.getStockHolderCode());
        var sharedRangeVO = new SharedRangeVO();
        sharedRangeVO.setFromrange(rangeVo.getStartRange());
        sharedRangeVO.setRangeDate(LocalDateMapper.toLocalDate(rangeVo.getStockAcceptanceDate()));
        sharedRangeVO.setToRange(rangeVo.getEndRange());
        sharedRange.add(sharedRangeVO);
      }
      var rangesToMerge = rangeHelper.findRangesForMerge(mergeRanges);
      for (RangeVO rangeVo : rangesToMerge) {
        var sharedRangeVO = new SharedRangeVO();
        sharedRangeVO.setFromrange(rangeVo.getStartRange());
        sharedRangeVO.setRangeDate(LocalDateMapper.toLocalDate(rangeVo.getStockAcceptanceDate()));
        sharedRangeVO.setToRange(rangeVo.getEndRange());
        sharedRange.add(sharedRangeVO);
      }

      var documentVo = new DocumentVO();
      documentVo.setCompanyCode(stockVO.getCompanyCode());
      documentVo.setDocumentType(stockVO.getDocumentType());
      documentVo.setDocumentSubType(stockVO.getDocumentSubType());
      documentVo.setRange(sharedRange);
      var sharedRangeResult = documentTypeProxy.mergeRanges(documentVo);

      for (SharedRangeVO sharedVo : sharedRangeResult) {
        var rangeVo =
            RangeVO.builder()
                .startRange(sharedVo.getFromrange())
                .stockAcceptanceDate(LocalDateMapper.toZonedDateTime(sharedVo.getRangeDate()))
                .endRange(sharedVo.getToRange())
                .build();
        outputRanges.add(rangeVo);
      }
    }
    return outputRanges;
  }

  public void deplete(StockVO stockVO, StockAllocationVO stockAllocationVO, boolean isBlacklist) {
    log.info("Invoked stock deplete");
    boolean isSpecialSplit = false;
    Set<RangeVO> rangesInside = new HashSet<>();
    List<RangeVO> splitRange = new ArrayList<>();
    log.info("Ranges from DB");
    LoggerUtil.logRanges(stockVO.getRanges());
    log.info("Ranges to be blacklisted");
    LoggerUtil.logRanges(stockAllocationVO.getRanges());
    for (RangeVO rangeVo : stockAllocationVO.getRanges()) {
      var startRange = rangeVo.getStartRange();
      var endRange = rangeVo.getEndRange();
      rangesInside.addAll(findRanges(stockVO, rangeVo, stockAllocationVO.isManual()));
      rangeVo.setStartRange(startRange);
      rangeVo.setEndRange(endRange);
    }
    log.info("Ranges inside");
    LoggerUtil.logRanges(rangesInside);

    if (!rangesInside.isEmpty()) {
      splitRange = rangeHelper.splitRanges(rangesInside, stockAllocationVO.getRanges());
    }
    if (splitRange.isEmpty() && isBlacklist) {
      rangesInside = new HashSet<>();
      if (stockVO.getRanges() != null) {
        rangesInside.addAll(stockVO.getRanges());
      }
      var incomingRange = checkForNotAvailableRange(rangesInside, stockAllocationVO.getRanges());
      if (incomingRange.size() != 0) {
        splitRange = rangeHelper.splitRanges(rangesInside, incomingRange);
      }
      isSpecialSplit = true;
    }
    log.info("Ranges split");
    LoggerUtil.logRanges(splitRange);
    log.info("Ranges inside after split");
    LoggerUtil.logRanges(rangesInside);
    if (!isSpecialSplit) {
      final var rangesToRemove =
          rangesInside.stream()
              .filter(range -> stockAllocationVO.isManual() == range.isManual())
              .collect(Collectors.toList());
      stockVO.getRanges().removeAll(rangesToRemove);
      rangeDao.removeAll(rangesToRemove);
    }

    if (!splitRange.isEmpty()) {
      stockPersistor.addRange(stockVO, splitRange, stockAllocationVO.isManual());
    }
  }

  private List<RangeVO> findRanges(StockVO stockVO, RangeVO rangeVO, boolean manual) {
    var obtainedRanges = new ArrayList<RangeVO>();
    return findRanges(stockVO, obtainedRanges, rangeVO, manual);
  }

  private List<RangeVO> findRanges(
      StockVO stockVO, List<RangeVO> obtainedRanges, RangeVO rangeVO, boolean manual) {
    if (stockVO.getRanges() != null) {
      for (RangeVO range : stockVO.getRanges()) {
        var thisStartRange = range.getAsciiStartRange();
        var thisEndRange = range.getAsciiEndRange();
        var startRange = toLong(rangeVO.getStartRange());
        var endRange = toLong(rangeVO.getEndRange());
        if (((startRange >= thisStartRange && startRange <= thisEndRange)
                && (endRange >= thisStartRange && endRange <= thisEndRange))
            && manual == range.isManual()) {
          obtainedRanges.add(range);
          return obtainedRanges;
        } else {
          if ((startRange >= thisStartRange && startRange <= thisEndRange)
              && (endRange >= thisStartRange && endRange > thisEndRange)
              && manual == range.isManual()) {
            obtainedRanges.add(range);
            thisEndRange++;
            rangeVO.setStartRange(String.valueOf(thisEndRange));
            return findRanges(stockVO, obtainedRanges, rangeVO, manual);
          }
        }
      }
    }
    return obtainedRanges;
  }

  private List<RangeVO> checkForNotAvailableRange(Set<RangeVO> available, List<RangeVO> income) {
    log.info("CheckForNotAvailableRange Invoked");
    var availableStartRange = 0L;
    var availableEndRange = 0L;
    var incomeStartRange = 0L;
    var incomeEndRange = 0L;
    var outcome = new ArrayList<RangeVO>();
    var removeList = new ArrayList<RangeVO>();
    for (RangeVO rangeVO : available) {
      availableStartRange = rangeVO.getAsciiStartRange();
      availableEndRange = rangeVO.getAsciiEndRange();
      for (RangeVO inner : income) {
        incomeStartRange = toLong(inner.getStartRange());
        incomeEndRange = toLong(inner.getEndRange());
        if (incomeStartRange < availableStartRange
            && incomeEndRange > availableStartRange
            && incomeEndRange < availableEndRange) {
          inner.setStartRange(rangeVO.getStartRange());
          outcome.add(inner);
          removeList.add(rangeVO);
        }
        if (incomeStartRange > availableStartRange
            && incomeStartRange < availableEndRange
            && incomeEndRange > availableEndRange) {
          inner.setEndRange(rangeVO.getEndRange());
          outcome.add(inner);
          removeList.add(rangeVO);
        }
        if (incomeStartRange < availableStartRange && incomeEndRange > availableEndRange) {
          removeList.add(rangeVO);
        }
        if (incomeStartRange == availableStartRange && incomeEndRange == availableEndRange) {
          removeList.add(rangeVO);
        }
      }
    }
    rangeDao.removeAll(removeList);
    return outcome;
  }
}
