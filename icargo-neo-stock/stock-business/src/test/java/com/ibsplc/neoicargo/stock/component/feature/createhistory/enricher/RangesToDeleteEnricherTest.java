package com.ibsplc.neoicargo.stock.component.feature.createhistory.enricher;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockStockRangeHistory;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockRangeVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockAllocationVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRangeHistoryVO;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockRangeHistoryDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockRangeHistory;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class RangesToDeleteEnricherTest {

  @InjectMocks private RangesToDeleteEnricher rangesToDeleteEnricher;

  @Mock private StockRangeHistoryDao stockRangeHistoryDao;

  private static final StockAllocationVO stockAllocationVO;
  private static final List<RangeVO> newRanges = new ArrayList<>();
  private static final List<RangeVO> usedRanges = new ArrayList<>();
  private static final StockRangeHistory stockRangeHistory;
  private static final StockRangeHistoryVO stockRangeHistoryVO;
  private static final RangeVO rangeVO;
  private static final Set<RangeVO> rangesToDelete = new HashSet<>();

  static {
    stockAllocationVO = getMockStockAllocationVO();
    rangeVO = getMockRangeVO("IBS", "AWB", "S", 0L, 99999L);
    newRanges.add(rangeVO);
    newRanges.add(getMockRangeVO("AV", "AWB", "M", 0L, 99999L));
    usedRanges.add(rangeVO);
    usedRanges.add(getMockRangeVO("AV", "AA", "L", 0L, 99999L));
    stockRangeHistory = getMockStockRangeHistory();
    stockRangeHistoryVO = getMockStockRangeHistoryVO();
  }

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private static Stream<Arguments> provideTestParameters() {
    return Stream.of(
        Arguments.of(null, stockAllocationVO, newRanges, stockRangeHistoryVO),
        Arguments.of(rangesToDelete, null, newRanges, stockRangeHistoryVO),
        Arguments.of(rangesToDelete, stockAllocationVO, null, stockRangeHistoryVO),
        Arguments.of(rangesToDelete, stockAllocationVO, newRanges, null),
        Arguments.of(rangesToDelete, stockAllocationVO, new ArrayList<>(), stockRangeHistoryVO));
  }

  @Test
  void shouldRemoveRanges() {
    doReturn(usedRanges).when(stockRangeHistoryDao).findUsedRangesForMerge(rangeVO, MODE_USED);
    doReturn(List.of(1L)).when(stockRangeHistoryDao).findStockRangeHistoryList(anyList());
    doNothing().when(stockRangeHistoryDao).deleteAllById(anyList());

    rangesToDeleteEnricher.enrich(
        rangesToDelete, stockAllocationVO, newRanges, stockRangeHistoryVO);

    verify(stockRangeHistoryDao).findUsedRangesForMerge(rangeVO, MODE_USED);
    verify(stockRangeHistoryDao).findStockRangeHistoryList(anyList());
    verify(stockRangeHistoryDao).deleteAllById(anyList());
  }

  @ParameterizedTest
  @MethodSource("provideTestParameters")
  void shouldNotRemoveRanges(
      Set<RangeVO> rangesToDelete,
      StockAllocationVO stockAllocationVO,
      List<RangeVO> newRanges,
      StockRangeHistoryVO stockRangeHistoryVO) {

    rangesToDeleteEnricher.enrich(
        rangesToDelete, stockAllocationVO, newRanges, stockRangeHistoryVO);

    verify(stockRangeHistoryDao, never()).findUsedRangesForMerge(rangeVO, MODE_USED);
    verify(stockRangeHistoryDao, never()).findStockRangeHistoryList(anyList());
    verify(stockRangeHistoryDao, never()).deleteAllById(anyList());
  }
}
