package com.ibsplc.neoicargo.stock.component.feature.createhistory;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_ALLOCATE;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_REOPENED;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_VOID;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockDocumentVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockSharedRangeVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockAllocationVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRangeHistoryVO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.component.feature.createhistory.enricher.RangesToDeleteEnricher;
import com.ibsplc.neoicargo.stock.component.feature.createhistory.enricher.StockRangeHistoryEnricher;
import com.ibsplc.neoicargo.stock.component.feature.createhistory.persistor.MergedRangesPersistor;
import com.ibsplc.neoicargo.stock.mapper.StockAllocationMapper;
import com.ibsplc.neoicargo.stock.proxy.impl.DocumentTypeProxy;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import java.util.ArrayList;
import java.util.List;
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
class CreateHistoryFeatureTest {

  @InjectMocks private CreateHistoryFeature createHistoryFeature;

  @Mock private ContextUtil contextUtil;
  @Mock private DocumentTypeProxy documentTypeProxy;
  @Mock private StockRangeHistoryEnricher stockRangeHistoryEnricher;
  @Mock private RangesToDeleteEnricher rangesToDeleteEnricher;
  @Mock private MergedRangesPersistor mergedRangesPersistor;
  @Mock private StockAllocationMapper stockAllocationMapper;

  private static final StockAllocationVO stockAllocationVO;
  private static final StockRangeHistoryVO stockRangeHistoryVO;
  private static final DocumentVO documentVO;
  private static final LoginProfile loginProfile;
  private static final List<SharedRangeVO> sharedRangeResult;

  static {
    stockAllocationVO = getMockStockAllocationVO();
    stockRangeHistoryVO = getMockStockRangeHistoryVO();
    loginProfile = new LoginProfile();
    loginProfile.setUserId("1");
    sharedRangeResult = new ArrayList<>();
    sharedRangeResult.add(getMockSharedRangeVO());
    documentVO = getMockDocumentVO();
    documentVO.setDocumentType(null);
    documentVO.setDocumentSubType("AWB");
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @ValueSource(strings = {MODE_REOPENED, MODE_USED, MODE_ALLOCATE, MODE_VOID})
  void shouldExecutePerformMethod(String status) {
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing()
        .when(stockRangeHistoryEnricher)
        .enrich(status, stockAllocationVO, stockRangeHistoryVO);
    doNothing().when(rangesToDeleteEnricher).enrich(any(), any(), any(), any());
    doNothing().when(mergedRangesPersistor).persist(any(), any(), anyBoolean());
    doReturn(sharedRangeResult).when(documentTypeProxy).mergeRanges(any());
    doReturn(stockRangeHistoryVO)
        .when(stockAllocationMapper)
        .mapAllocationVoToRangeHistoryVo(stockAllocationVO);

    createHistoryFeature.perform(stockAllocationVO, status);

    verify(contextUtil).callerLoginProfile();
    verify(stockRangeHistoryEnricher).enrich(status, stockAllocationVO, stockRangeHistoryVO);
    verify(stockAllocationMapper).mapAllocationVoToRangeHistoryVo(stockAllocationVO);

    if (status.equals(MODE_USED)) {
      verify(documentTypeProxy).mergeRanges(any());
      verify(mergedRangesPersistor).persist(any(), any(), anyBoolean());
      verify(rangesToDeleteEnricher).enrich(any(), any(), any(), any());
    }
  }

  @Test
  void shouldNotExecutePerformMethod() {
    createHistoryFeature.perform(null, "any");

    verify(contextUtil, times(0)).callerLoginProfile();
    verify(stockRangeHistoryEnricher, times(0))
        .enrich("any", stockAllocationVO, stockRangeHistoryVO);
    verify(stockAllocationMapper, times(0)).mapAllocationVoToRangeHistoryVo(stockAllocationVO);
    verify(documentTypeProxy, times(0)).mergeRanges(any());
    verify(mergedRangesPersistor, times(0)).persist(any(), any(), anyBoolean());
    verify(rangesToDeleteEnricher, times(0)).enrich(any(), any(), any(), any());
  }
}
