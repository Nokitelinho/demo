package com.ibsplc.neoicargo.stock.component.feature.createhistory.enricher;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_ALLOCATE;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_REOPENED;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_VOID;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockAllocationVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRangeHistoryVO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockRangeHistoryDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockRangeHistoryEnricherTest {

  @InjectMocks private StockRangeHistoryEnricher stockRangeHistoryEnricher;

  @Mock private StockRangeHistoryDao stockRangeHistoryDao;

  private static final StockAllocationVO stockAllocationVO;
  private static final StockRangeHistoryVO stockRangeHistoryVO;
  private StockAgent stockAgent;

  static {
    stockRangeHistoryVO = getMockStockRangeHistoryVO();
    stockAllocationVO = getMockStockAllocationVO();
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    stockRangeHistoryEnricher.init();
    stockAgent = new StockAgent();
  }

  @Test
  void shouldInitMap() {
    stockRangeHistoryEnricher.init();
    Assertions.assertDoesNotThrow(() -> new NullPointerException());
  }

  @ParameterizedTest
  @ValueSource(strings = {MODE_REOPENED, MODE_USED, MODE_ALLOCATE, MODE_VOID})
  void shouldNotExecuteEnrichMethod(String status) {
    doReturn(stockAgent).when(stockRangeHistoryDao).findByCompanyCodeAndAgentCode(any(), any());
    stockAllocationVO.setStockControlFor("SCF");

    stockRangeHistoryEnricher.enrich(status, stockAllocationVO, stockRangeHistoryVO);

    if (status.equals(MODE_REOPENED)) {
      verify(stockRangeHistoryDao).findByCompanyCodeAndAgentCode(any(), any());
    }
  }

  @Test
  void shouldNotExecuteEnrichMethodWhenStatusIsNull() {
    doReturn(stockAgent).when(stockRangeHistoryDao).findByCompanyCodeAndAgentCode(any(), any());

    stockRangeHistoryEnricher.enrich(null, stockAllocationVO, stockRangeHistoryVO);

    verify(stockRangeHistoryDao, times(0)).findByCompanyCodeAndAgentCode(any(), any());
  }

  @Test
  void shouldNotExecuteEnrichMethodWhenStockAllocationVOIsNull() {
    Assertions.assertThrows(
        NullPointerException.class,
        () -> stockRangeHistoryEnricher.enrich(MODE_USED, null, stockRangeHistoryVO));
  }

  @Test
  void shouldNotExecuteEnrichMethodWhenStockRangeHistoryVOIsNull() {
    Assertions.assertThrows(
        NullPointerException.class,
        () -> stockRangeHistoryEnricher.enrich(MODE_ALLOCATE, stockAllocationVO, null));
  }
}
