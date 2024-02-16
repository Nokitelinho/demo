package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getLoginProfile;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockBlacklistStockVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockTransitStockVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.helper.RangeHelper;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor.BlackListHistoryPersistor;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor.TransitStockPersistor;
import com.ibsplc.neoicargo.stock.dao.BlacklistStockDao;
import com.ibsplc.neoicargo.stock.mapper.MissingStockMapper;
import com.ibsplc.neoicargo.stock.mapper.TransitStockMapper;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.TransitStockVO;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(JUnitPlatform.class)
public class BlackListMissingStockHandlerTest {
  private static final String COMPANY_CODE = "AV";
  private static final int AIRLINE_IDENTIFIER = 1234;
  private static final String STOCK_HOLDER_CODE = "HQ";
  private static final String DOC_TYPE = "AWB";
  private static final String DOC_SUB_TYPE = "S";
  @InjectMocks private BlackListMissingStockHandler blackListMissingStockHandler;
  @Mock private BlackListHistoryPersistor blackListHistoryPersistor;
  @Mock private ContextUtil contextUtil;
  @Mock private LocalDate localDateUtil;
  @Mock private TransitStockPersistor transitStockPersistor;
  @Mock private RangeHelper rangeHelper;
  @Mock private BlacklistStockDao blacklistStockDao;
  @Spy private TransitStockMapper transitStockMapper = Mappers.getMapper(TransitStockMapper.class);
  @Spy private MissingStockMapper missingStockMapper = Mappers.getMapper(MissingStockMapper.class);

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(transitStockMapper, "missingStockMapper", missingStockMapper);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(
        blackListHistoryPersistor, blacklistStockDao, transitStockPersistor, rangeHelper);
  }

  @Test
  void blacklistMissingStock() throws BusinessException {
    // Given
    var blacklistStockVO = getMockBlacklistStockVO();
    var transitStockVO =
        getMockTransitStockVO(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);
    transitStockVO.setMissingStartRange("2000000");
    transitStockVO.setMissingEndRange("3000000");

    // When
    doReturn(getLoginProfile()).when(contextUtil).callerLoginProfile();
    doReturn(List.of(transitStockVO))
        .when(blacklistStockDao)
        .findBlackListRangesFromTransit(blacklistStockVO);
    doNothing().when(transitStockPersistor).persist(anyList());
    doReturn(List.of()).when(rangeHelper).splitRanges(anySet(), anyList());

    // Then
    assertDoesNotThrow(() -> blackListMissingStockHandler.blacklistMissingStock(blacklistStockVO));
    verify(blacklistStockDao, times(1)).findBlackListRangesFromTransit(blacklistStockVO);
    verify(blackListHistoryPersistor, times(1))
        .updateBlackHistory(anyList(), any(BlacklistStockVO.class), any(TransitStockVO.class));
    verify(transitStockPersistor, times(2)).persist(anyList());
    verify(rangeHelper, times(1)).splitRanges(anySet(), anyList());
  }
}
