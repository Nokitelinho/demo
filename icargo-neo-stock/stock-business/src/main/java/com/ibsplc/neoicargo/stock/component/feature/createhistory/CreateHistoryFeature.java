package com.ibsplc.neoicargo.stock.component.feature.createhistory;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_MANUAL;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_NEUTRAL;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.component.feature.createhistory.enricher.RangesToDeleteEnricher;
import com.ibsplc.neoicargo.stock.component.feature.createhistory.enricher.StockRangeHistoryEnricher;
import com.ibsplc.neoicargo.stock.component.feature.createhistory.persistor.MergedRangesPersistor;
import com.ibsplc.neoicargo.stock.mapper.StockAllocationMapper;
import com.ibsplc.neoicargo.stock.proxy.impl.DocumentTypeProxy;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component("createHistoryFeature")
@RequiredArgsConstructor
public class CreateHistoryFeature {

  private final ContextUtil contextUtil;
  private final DocumentTypeProxy documentTypeProxy;
  private final StockRangeHistoryEnricher stockRangeHistoryEnricher;
  private final RangesToDeleteEnricher rangesToDeleteEnricher;
  private final MergedRangesPersistor mergedRangesPersistor;
  private final StockAllocationMapper stockAllocationMapper;

  public void perform(StockAllocationVO stockAllocationVO, @NotNull String status) {
    log.info("CreateHistoryFeature Invoked");

    if (stockAllocationVO != null) {
      var stockRangeHistoryVO = buildStockRangeHistoryVO(stockAllocationVO, status.toUpperCase());
      var requestRanges = stockAllocationVO.getRanges();

      if (requestRanges != null) {
        if (status.equals(MODE_USED)) {
          handleUiRangesWithModeUsedStatus(requestRanges, stockRangeHistoryVO, stockAllocationVO);
        } else {
          mergedRangesPersistor.persist(stockRangeHistoryVO, requestRanges, true);
        }
      }
    }
  }

  private StockRangeHistoryVO buildStockRangeHistoryVO(
      StockAllocationVO stockAllocationVO, String status) {
    var userId = contextUtil.callerLoginProfile().getUserId();
    var stockRangeHistoryVO =
        stockAllocationMapper.mapAllocationVoToRangeHistoryVo(stockAllocationVO);
    stockRangeHistoryVO.setStatus(status);
    stockRangeHistoryVO.setUserId(userId);
    if (stockRangeHistoryVO.getLastUpdateUser() == null) {
      stockRangeHistoryVO.setLastUpdateUser(userId);
    }
    if (stockAllocationVO.isManual()) {
      stockRangeHistoryVO.setRangeType(MODE_MANUAL);
    } else {
      stockRangeHistoryVO.setRangeType(MODE_NEUTRAL);
    }

    stockRangeHistoryEnricher.enrich(status, stockAllocationVO, stockRangeHistoryVO);
    return stockRangeHistoryVO;
  }

  private void handleUiRangesWithModeUsedStatus(
      List<RangeVO> requestRanges,
      StockRangeHistoryVO stockRangeHistoryVO,
      StockAllocationVO stockAllocationVO) {
    var newRanges = new ArrayList<>(requestRanges);
    var rangesToDelete = new HashSet<RangeVO>();

    rangesToDeleteEnricher.enrich(
        rangesToDelete, stockAllocationVO, newRanges, stockRangeHistoryVO);

    var mergedRanges =
        mergeRanges(
            stockAllocationVO.getCompanyCode(),
            stockAllocationVO.getDocumentType(),
            stockAllocationVO.getDocumentSubType(),
            newRanges);

    mergedRangesPersistor.persist(stockRangeHistoryVO, mergedRanges, false);
  }

  private List<RangeVO> mergeRanges(
      String companyCode, String docType, String docSubType, List<RangeVO> ranges) {
    List<SharedRangeVO> sharedRangeResult;
    final var sharedRanges =
        ranges.stream()
            .map(
                rangeVo -> {
                  final var sharedRange = new SharedRangeVO();
                  sharedRange.setFromrange(rangeVo.getStartRange());
                  sharedRange.setRangeDate(
                      LocalDateMapper.toLocalDate(rangeVo.getStockAcceptanceDate()));
                  sharedRange.setToRange(rangeVo.getEndRange());
                  return sharedRange;
                })
            .collect(Collectors.toList());
    if (!sharedRanges.isEmpty()) {
      var documentVo = new DocumentVO();
      documentVo.setCompanyCode(companyCode);
      documentVo.setDocumentType(docType);
      documentVo.setDocumentSubType(docSubType);
      documentVo.setRange(sharedRanges);
      sharedRangeResult = documentTypeProxy.mergeRanges(documentVo);

      if (!CollectionUtils.isEmpty(sharedRangeResult)) {
        return sharedRangeResult.stream()
            .map(
                sharedRangeVO -> {
                  RangeVO rangeVo = new RangeVO();
                  rangeVo.setStartRange(sharedRangeVO.getFromrange());
                  rangeVo.setStockAcceptanceDate(
                      LocalDateMapper.toZonedDateTime(sharedRangeVO.getRangeDate()));
                  rangeVo.setEndRange(sharedRangeVO.getToRange());
                  return rangeVo;
                })
            .collect(Collectors.toList());
      }
    }
    return Collections.emptyList();
  }
}
