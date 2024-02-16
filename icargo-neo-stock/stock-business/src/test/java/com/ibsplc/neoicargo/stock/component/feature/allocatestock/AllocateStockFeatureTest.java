package com.ibsplc.neoicargo.stock.component.feature.allocatestock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.StockDetailsHandler;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.StockHolderHandler;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.helper.SystemParameterHelper;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.invoker.CreateHistoryInvoker;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor.StockRequestOALPersistor;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor.StockRequestPersistor;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator.StockHolderCodeValidator;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.mapper.StockAllocationMapper;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderParametersVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(JUnitPlatform.class)
class AllocateStockFeatureTest {

  @InjectMocks private AllocateStockFeature allocateStockFeature;

  @Mock private ContextUtil contextUtil;
  @Mock private StockDao stockDao;
  @Mock private CreateHistoryInvoker createHistoryInvoker;
  @Mock private StockRequestPersistor stockRequestPersistor;
  @Mock private StockRequestOALPersistor stockRequestOALPersistor;
  @Mock private StockHolderCodeValidator stockHolderCodeValidator;

  @Mock private StockHolderHandler stockHolderHandler;
  @Mock private StockDetailsHandler stockDetailsHandler;

  @Mock private SystemParameterHelper systemParameterHelper;

  @Spy
  private StockAllocationMapper stockAllocationMapper =
      Mappers.getMapper(StockAllocationMapper.class);

  private LoginProfile loginProfile;
  private StockHolderParametersVO systemParameters;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    var rangeMapper = Mappers.getMapper(RangeMapper.class);
    ReflectionTestUtils.setField(stockAllocationMapper, "rangeMapper", rangeMapper);
    loginProfile = new LoginProfile();
    loginProfile.setAirportCode("FRA");
    systemParameters = StockHolderParametersVO.builder().enableStockHistory(true).build();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(
        contextUtil,
        stockDao,
        stockRequestPersistor,
        stockRequestOALPersistor,
        stockHolderCodeValidator,
        stockHolderHandler,
        stockDetailsHandler,
        systemParameterHelper,
        createHistoryInvoker);
  }

  @Test
  void shouldReturnDepleteOnlyStock() throws BusinessException {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder()
            .isConfirmationRequired(false)
            .stockControlFor("HQ")
            .isNewStockFlag(false)
            .isFromConfirmStock(false)
            .isApproverDeleted(false)
            .ranges(List.of(new RangeVO()))
            .build();

    var stockVO = StockVO.builder().totalStock(10).reorderLevel(20).build();

    // When
    doReturn(new StockHolderParametersVO()).when(systemParameterHelper).getSystemParameters();
    doReturn(true)
        .when(stockHolderCodeValidator)
        .isRelatedToStockControlFor(
            loginProfile.getAirportCode(), stockAllocationVO.getStockControlFor());
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing().when(stockHolderHandler).deplete(stockAllocationVO, false);
    doReturn(stockVO).when(stockDao).findStockForStockHolder(stockAllocationVO);

    // Then
    var result = allocateStockFeature.perform(stockAllocationVO);

    assertEquals("HQ", result.getStockControlFor());
    assertTrue(result.isHasMinReorderLevel());

    verify(systemParameterHelper).getSystemParameters();
    verify(stockHolderCodeValidator)
        .isRelatedToStockControlFor(
            loginProfile.getAirportCode(), stockAllocationVO.getStockControlFor());
    verify(contextUtil, times(1)).callerLoginProfile();
    verify(stockHolderHandler).deplete(stockAllocationVO, false);
    verify(stockDao).findStockForStockHolder(stockAllocationVO);
  }

  @Test
  void shouldReturnNullAsApproverDeleted() throws BusinessException {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder()
            .isConfirmationRequired(false)
            .isNewStockFlag(false)
            .isFromConfirmStock(false)
            .isApproverDeleted(true)
            .build();

    // When
    doReturn(new StockHolderParametersVO()).when(systemParameterHelper).getSystemParameters();
    doReturn(true)
        .when(stockHolderCodeValidator)
        .isRelatedToStockControlFor(
            loginProfile.getAirportCode(), stockAllocationVO.getStockControlFor());
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing().when(stockHolderHandler).deplete(stockAllocationVO, false);

    // Then
    var result = allocateStockFeature.perform(stockAllocationVO);
    assertNotNull(result);

    verify(systemParameterHelper).getSystemParameters();
    verify(stockHolderCodeValidator)
        .isRelatedToStockControlFor(
            loginProfile.getAirportCode(), stockAllocationVO.getStockControlFor());
    verify(contextUtil, times(1)).callerLoginProfile();
    verify(stockHolderHandler).deplete(stockAllocationVO, false);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldReturnNullAfterFullFlow(boolean isBlacklist) throws BusinessException {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder()
            .isConfirmationRequired(false)
            .isNewStockFlag(true)
            .isBlacklist(isBlacklist)
            .isAllocate(true)
            .transactionCode("TR")
            .stockForOtherAirlines(List.of())
            .build();

    // When
    doReturn(systemParameters).when(systemParameterHelper).getSystemParameters();
    doReturn(false)
        .when(stockHolderCodeValidator)
        .isRelatedToStockControlFor(
            loginProfile.getAirportCode(), stockAllocationVO.getStockControlFor());
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing().when(stockHolderHandler).allocate(stockAllocationVO, systemParameters);
    doNothing().when(stockRequestPersistor).update(stockAllocationVO);
    doNothing().when(createHistoryInvoker).invoke(stockAllocationVO);
    doNothing().when(stockRequestOALPersistor).update(stockAllocationVO.getStockForOtherAirlines());
    doNothing().when(stockDetailsHandler).createStockHolderStockDetails(any(), any(), any());

    // Then
    var result = allocateStockFeature.perform(stockAllocationVO);
    assertNotNull(result);

    verify(systemParameterHelper).getSystemParameters();
    verify(stockHolderCodeValidator)
        .isRelatedToStockControlFor(
            loginProfile.getAirportCode(), stockAllocationVO.getStockControlFor());
    verify(contextUtil, times(1)).callerLoginProfile();
    verify(stockHolderHandler).allocate(stockAllocationVO, systemParameters);
    verify(stockRequestPersistor).update(stockAllocationVO);
    verify(createHistoryInvoker).invoke(stockAllocationVO);
    verify(stockRequestOALPersistor).update(stockAllocationVO.getStockForOtherAirlines());
    verify(stockDetailsHandler).createStockHolderStockDetails(any(), any(), any());
  }
}
