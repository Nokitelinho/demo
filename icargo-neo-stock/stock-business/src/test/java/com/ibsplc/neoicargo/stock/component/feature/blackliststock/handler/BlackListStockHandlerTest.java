package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_020;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockBlacklistStockVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockRangeVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.StockDetailsHandler;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.StockHolderHandler;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.helper.SystemParameterHelper;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.invoker.CreateHistoryInvoker;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.helper.RearrangeRangesHelper;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor.BlackListHistoryPersistor;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor.BlackListStockPersistor;
import com.ibsplc.neoicargo.stock.component.feature.monitorstock.validator.StockFilterValidator;
import com.ibsplc.neoicargo.stock.dao.BlacklistStockDao;
import com.ibsplc.neoicargo.stock.dao.CQRSDao;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.BlackListStockMapper;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderParametersVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class BlackListStockHandlerTest {

  private static final String COMPANY_CODE = "AV";
  private static final int AIRLINE_IDENTIFIER = 1234;
  private static final String STOCK_HOLDER_CODE = "HQ";
  private static final String DOC_TYPE = "AWB";
  private static final String DOC_SUB_TYPE = "S";
  @Mock private CQRSDao cqrsDao;
  @Mock private StockDao stockDao;
  @Mock private BlacklistStockDao blacklistStockDao;
  @Mock private StockFilterValidator stockFilterValidator;
  @Mock private RearrangeRangesHelper rearrangeRangesHelper;
  @Mock private SystemParameterHelper systemParameterHelper;
  @Mock private BlackListHistoryPersistor blackListHistoryPersistor;
  @Mock private BlackListStockPersistor blackListStockPersistor;
  @Mock private BlackListMissingStockHandler blackListMissingStockHandler;
  @Mock private StockHolderHandler stockHolderHandler;
  @Mock private StockDetailsHandler stockDetailsHandler;
  @Mock private CreateHistoryInvoker createHistoryInvoker;
  @Mock private StockHolderParametersVO stockHolderParametersVO;
  @Mock private BlackListStockMapper blacklistStockMapper;
  @InjectMocks private BlackListStockHandler blackListStockHandler;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(
        blackListHistoryPersistor,
        rearrangeRangesHelper,
        cqrsDao,
        stockFilterValidator,
        systemParameterHelper,
        blacklistStockDao,
        stockDao,
        blackListMissingStockHandler,
        stockHolderHandler,
        blacklistStockMapper,
        stockDetailsHandler,
        blackListStockPersistor,
        createHistoryInvoker);
  }

  @Test
  void blacklistStockHolderStockWhenAlreadyBlackListed() throws BusinessException {
    // Given
    var vo = getMockBlacklistStockVO();
    var stockHolderVO = getMockStockHolderVO(COMPANY_CODE, STOCK_HOLDER_CODE);
    var stockVO = getMockStockVO(stockHolderVO, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);
    var stockVO1 = getMockStockVO(stockHolderVO, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);
    stockVO.setRanges(List.of(getMockRangeVO(COMPANY_CODE, DOC_TYPE, DOC_SUB_TYPE, 1, 2)));
    stockVO1.setRanges(
        List.of(getMockRangeVO(COMPANY_CODE, DOC_TYPE, DOC_SUB_TYPE, 9999998, 9999999)));

    // When
    doReturn(stockHolderParametersVO).when(systemParameterHelper).getSystemParameters();
    doReturn(false).when(blacklistStockDao).alreadyBlackListed(vo);
    doReturn(List.of(stockVO, stockVO1)).when(cqrsDao).findBlacklistRangesForBlackList(vo);
    doReturn(true).when(stockHolderParametersVO).isEnableConfirmationProcess();
    doReturn(stockVO)
        .when(stockDao)
        .findStockWithRanges(anyString(), anyString(), anyInt(), anyString(), anyString());
    doReturn(null).when(blacklistStockMapper).clone(any(BlacklistStockVO.class));
    doNothing()
        .when(blackListMissingStockHandler)
        .blacklistMissingStock(any(BlacklistStockVO.class));
    doReturn(List.of(stockVO)).when(stockDao).findBlacklistRanges(any(BlacklistStockVO.class));
    doReturn(List.of(stockVO))
        .when(rearrangeRangesHelper)
        .rearrangeRanges(anyList(), any(BlacklistStockVO.class));
    doNothing().when(stockFilterValidator).validate(any(StockFilterVO.class));
    doNothing().when(stockHolderHandler).deplete(any(StockAllocationVO.class), anyBoolean());
    doNothing().when(blackListStockPersistor).persist(any(BlacklistStockVO.class));
    doReturn("A").when(stockHolderParametersVO).getAccountingFlag();
    doNothing()
        .when(stockDetailsHandler)
        .createStockHolderStockDetails(any(StockAllocationVO.class), anyString(), anyString());
    doReturn(true).when(stockHolderParametersVO).isEnableStockHistory();
    doNothing().when(createHistoryInvoker).invoke(any(StockAllocationVO.class));
    doNothing()
        .when(blackListHistoryPersistor)
        .createBlacklistHistoryForUsedRanges(any(BlacklistStockVO.class));

    // Then
    assertDoesNotThrow(() -> blackListStockHandler.blacklistStockHolderStock(vo));
    verify(systemParameterHelper, times(1)).getSystemParameters();
    verify(blacklistStockDao, times(1)).alreadyBlackListed(vo);
    verify(cqrsDao, times(1)).findBlacklistRangesForBlackList(vo);
    verify(stockDao, times(4))
        .findStockWithRanges(anyString(), anyString(), anyInt(), anyString(), anyString());
    verify(blacklistStockMapper, times(2)).clone(any(BlacklistStockVO.class));
    verify(blackListMissingStockHandler, times(2)).blacklistMissingStock(null);
    verify(stockDao, times(2)).findBlacklistRanges(any(BlacklistStockVO.class));
    verify(rearrangeRangesHelper, times(2)).rearrangeRanges(anyList(), any(BlacklistStockVO.class));
    verify(stockFilterValidator, times(2)).validate(any(StockFilterVO.class));
    verify(stockHolderHandler, times(2)).deplete(any(StockAllocationVO.class), anyBoolean());
    verify(blackListStockPersistor, times(2)).persist(any(BlacklistStockVO.class));
    verify(stockDetailsHandler, times(2))
        .createStockHolderStockDetails(any(StockAllocationVO.class), anyString(), anyString());
    verify(createHistoryInvoker, times(2)).invoke(any(StockAllocationVO.class));
    verify(blackListHistoryPersistor, times(1))
        .createBlacklistHistoryForUsedRanges(any(BlacklistStockVO.class));
  }

  @Test
  void blacklistStockHolderStockThrowException() {
    // Given
    var vo = getMockBlacklistStockVO();
    var stockHolderVO = getMockStockHolderVO(COMPANY_CODE, STOCK_HOLDER_CODE);
    var stockVO = getMockStockVO(stockHolderVO, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);
    stockVO.setRanges(List.of(getMockRangeVO(COMPANY_CODE, DOC_TYPE, DOC_SUB_TYPE, 1, 2)));

    // When
    doReturn(stockHolderParametersVO).when(systemParameterHelper).getSystemParameters();
    doReturn(false).when(blacklistStockDao).alreadyBlackListed(vo);
    doReturn(List.of(stockVO)).when(cqrsDao).findBlacklistRangesForBlackList(vo);
    doReturn(true).when(stockHolderParametersVO).isEnableConfirmationProcess();
    doReturn(null)
        .when(stockDao)
        .findStockWithRanges(anyString(), anyString(), anyInt(), anyString(), anyString());

    // Then
    var thrown =
        assertThrows(
            BusinessException.class, () -> blackListStockHandler.blacklistStockHolderStock(vo));
    verify(blacklistStockDao, times(1)).alreadyBlackListed(vo);
    verify(systemParameterHelper, times(1)).getSystemParameters();
    assertEquals(NEO_STOCK_003.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
    verify(systemParameterHelper, times(1)).getSystemParameters();
    verify(blacklistStockDao, times(1)).alreadyBlackListed(vo);
    verify(cqrsDao, times(1)).findBlacklistRangesForBlackList(vo);
    verify(stockDao, times(1))
        .findStockWithRanges(anyString(), anyString(), anyInt(), anyString(), anyString());
  }

  @Test
  void blacklistStockHolderStockWhenThrowException() {
    // Given
    var vo = getMockBlacklistStockVO();

    // When
    doReturn(true).when(blacklistStockDao).alreadyBlackListed(vo);

    // Then
    var thrown =
        assertThrows(
            BusinessException.class, () -> blackListStockHandler.blacklistStockHolderStock(vo));
    verify(blacklistStockDao, times(1)).alreadyBlackListed(vo);
    verify(systemParameterHelper, times(1)).getSystemParameters();
    assertEquals(NEO_STOCK_020.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }
}
