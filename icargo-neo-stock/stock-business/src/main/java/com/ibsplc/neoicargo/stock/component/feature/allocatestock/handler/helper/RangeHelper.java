package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.helper;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.proxy.impl.DocumentTypeProxy;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangesForMergeFilterVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RangeHelper {

  private final DocumentTypeProxy documentTypeProxy;
  private final RangeDao rangeDao;

  public List<RangeVO> splitRanges(Set<RangeVO> originalRanges, List<RangeVO> availableRanges) {
    log.info("Invoked split ranges");
    final var originalSharedRanges =
        Optional.ofNullable(originalRanges).orElse(Collections.emptySet()).stream()
            .map(
                rangeVO -> {
                  final var sharedRangeVO = new SharedRangeVO();
                  sharedRangeVO.setFromrange(rangeVO.getStartRange());
                  sharedRangeVO.setToRange(rangeVO.getEndRange());
                  sharedRangeVO.setRangeDate(
                      LocalDateMapper.toLocalDate(rangeVO.getStockAcceptanceDate()));
                  return sharedRangeVO;
                })
            .collect(Collectors.toList());

    final var availableSharedRanges =
        Optional.ofNullable(availableRanges).orElse(Collections.emptyList()).stream()
            .map(
                rangeVO -> {
                  final var sharedRangeVO = new SharedRangeVO();
                  sharedRangeVO.setFromrange(rangeVO.getStartRange());
                  sharedRangeVO.setToRange(rangeVO.getEndRange());
                  sharedRangeVO.setRangeDate(
                      LocalDateMapper.toLocalDate(rangeVO.getStockAcceptanceDate()));
                  return sharedRangeVO;
                })
            .collect(Collectors.toList());

    // TODO: for testing purposes
    originalSharedRanges.forEach(
        rangeVO -> {
          log.info(
              "originalSharedRanges: FromRange="
                  + rangeVO.getFromrange()
                  + " ToRange="
                  + rangeVO.getToRange()
                  + " RangeDate="
                  + rangeVO.getRangeDate());
        });
    availableSharedRanges.forEach(
        rangeVO -> {
          log.info(
              "availableSharedRanges: FromRange="
                  + rangeVO.getFromrange()
                  + " ToRange="
                  + rangeVO.getToRange()
                  + " RangeDate="
                  + rangeVO.getRangeDate());
        });

    var splitRanges = documentTypeProxy.splitRanges(originalSharedRanges, availableSharedRanges);

    splitRanges.forEach(
        rangeVO -> {
          log.info(
              "splitRanges: FromRange="
                  + rangeVO.getFromrange()
                  + " ToRange="
                  + rangeVO.getToRange()
                  + " RangeDate="
                  + rangeVO.getRangeDate());
        });

    return Optional.ofNullable(splitRanges).orElse(Collections.emptyList()).stream()
        .map(
            sharedRangeVO ->
                RangeVO.builder()
                    .startRange(sharedRangeVO.getFromrange())
                    .endRange(sharedRangeVO.getToRange())
                    .stockAcceptanceDate(
                        LocalDateMapper.toZonedDateTime(sharedRangeVO.getRangeDate()))
                    .build())
        .collect(Collectors.toList());
  }

  public List<RangeVO> findRangesForMerge(List<RangeVO> rangeVos) {
    var newRangeVos = new HashSet<RangeVO>();
    for (RangeVO rangeVO : rangeVos) {
      rangeVO.setAsciiStartRange(toLong(rangeVO.getStartRange()));
      rangeVO.setAsciiEndRange(toLong(rangeVO.getEndRange()));
      findRangesForMerge(rangeVO, newRangeVos);
    }
    return new ArrayList<>(newRangeVos);
  }

  private void findRangesForMerge(RangeVO rangeVO, Set<RangeVO> newRangeVos) {
    var ascStaRng = 0L;
    var ascEndRng = 0L;
    if (rangeVO.getOperationFlag() == null || !("S").equals(rangeVO.getOperationFlag())) {
      ascStaRng = rangeVO.getAsciiStartRange();
      ascStaRng--;
      rangeVO.setOperationFlag(null);
    }
    if (rangeVO.getOperationFlag() == null || !("E").equals(rangeVO.getOperationFlag())) {
      ascEndRng = rangeVO.getAsciiEndRange();
      ascEndRng++;
      rangeVO.setOperationFlag(null);
    }
    final var rangesForMerge = rangeDao.findRangesForMerge(build(rangeVO, ascStaRng, ascEndRng));
    newRangeVos.addAll(rangesForMerge);
    if (rangesForMerge.size() > 0) {
      for (RangeVO rangevo : rangesForMerge) {
        if (rangevo.getAsciiStartRange() == ascEndRng) {
          rangevo.setOperationFlag("S");
          findRangesForMerge(rangevo, newRangeVos);
        }
        if (rangevo.getAsciiEndRange() == ascStaRng) {
          rangevo.setOperationFlag("E");
          findRangesForMerge(rangevo, newRangeVos);
        }
      }
    }
  }

  private RangesForMergeFilterVO build(RangeVO rangeVO, long ascStaRng, long ascEndRng) {
    return RangesForMergeFilterVO.builder()
        .asciiEndRange(ascEndRng)
        .asciiStartRange(ascStaRng)
        .airlineIdentifier(rangeVO.getAirlineIdentifier())
        .companyCode(rangeVO.getCompanyCode())
        .documentType(rangeVO.getDocumentType())
        .documentSubType(rangeVO.getDocumentSubType())
        .manualFlag(rangeVO.isManual() ? FLAG_YES : FLAG_NO)
        .operationFlag(rangeVO.getOperationFlag())
        .build();
  }
}
