package com.ibsplc.neoicargo.stock.component.feature.deletestock;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_006;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.helper.RangeHelper;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.invoker.CreateHistoryInvoker;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator.StockRangeUtilisationForRangeValidator;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
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

@RunWith(JUnitPlatform.class)
class DeleteStockFeatureTest {

  @InjectMocks private DeleteStockFeature deleteStockFeature;

  @Mock private ContextUtil contextUtil;
  @Mock private RangeDao rangeDao;
  @Mock private StockDao stockDao;
  @Mock private RangeHelper rangeHelper;
  @Mock private StockRangeUtilisationForRangeValidator rangeUtilisationValidator;
  @Mock private CreateHistoryInvoker createHistoryInvoker;

  @Spy private RangeMapper rangeMapper = Mappers.getMapper(RangeMapper.class);

  private LoginProfile loginProfile;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    loginProfile = new LoginProfile();
    loginProfile.setUserId("ICO");
    loginProfile.setStationCode("TRV");
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(
        contextUtil,
        rangeDao,
        stockDao,
        rangeHelper,
        rangeUtilisationValidator,
        createHistoryInvoker);
  }

  @Test
  void shouldDoNothingWhenEmptyInput() {
    assertDoesNotThrow(() -> deleteStockFeature.perform(List.of()));
  }

  @Test
  void shouldThrowRangeUtilisationBusinessException() throws BusinessException {
    // When
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doThrow(StockBusinessException.class)
        .when(rangeUtilisationValidator)
        .validate(any(StockAllocationVO.class));

    // Then
    assertThrows(
        StockBusinessException.class, () -> deleteStockFeature.perform(List.of(new RangeVO())));

    verify(contextUtil).callerLoginProfile();
    verify(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
  }

  @Test
  void shouldThrowRangeNotFound() throws BusinessException {
    // Given
    final var rangeVO = new RangeVO();
    rangeVO.setStartRange("8899009");
    rangeVO.setEndRange("8899009");

    // When
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing().when(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    doReturn(null).when(rangeDao).find(any(RangeFilterVO.class));

    // Then
    var thrown =
        assertThrows(
            StockBusinessException.class, () -> deleteStockFeature.perform(List.of(rangeVO)));
    assertEquals(NEO_STOCK_006.getErrorCode(), thrown.getErrors().get(0).getErrorCode());

    verify(contextUtil).callerLoginProfile();
    verify(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    verify(rangeDao).find(any(RangeFilterVO.class));
  }

  @Test
  void shouldThrowStockNotFoundException() throws BusinessException {
    // Given
    var rangeVO = RangeVO.builder().startRange("1001000").endRange("1100000").build();

    var existingRange = RangeVO.builder().startRange("1001100").endRange("1100000").build();

    // When
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing().when(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    doReturn(existingRange).when(rangeDao).find(any(RangeFilterVO.class));
    doNothing().when(rangeDao).remove(existingRange);
    doReturn(List.of(rangeVO)).when(rangeHelper).splitRanges(anySet(), anyList());
    doReturn(null)
        .when(stockDao)
        .findStockWithRanges(anyString(), anyString(), anyInt(), anyString(), anyString());

    // Then
    var thrown =
        assertThrows(
            StockBusinessException.class, () -> deleteStockFeature.perform(List.of(rangeVO)));
    assertEquals(NEO_STOCK_003.getErrorCode(), thrown.getErrors().get(0).getErrorCode());

    verify(contextUtil, times(2)).callerLoginProfile();
    verify(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    verify(rangeDao).find(any(RangeFilterVO.class));
    verify(rangeDao).remove(existingRange);
    verify(rangeHelper).splitRanges(anySet(), anyList());
    verify(stockDao).findStockWithRanges(null, null, 0, null, null);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldDeleteStock(boolean isStartRange) throws BusinessException {
    // Given
    var rangeVO = RangeVO.builder().startRange("1001000").endRange("1100000").build();

    var existingRange = RangeVO.builder().startRange("1001000").endRange("1100000").build();

    if (isStartRange) {
      existingRange.setStartRange("1001100");
    } else {
      existingRange.setEndRange("1010000");
    }

    // When
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing().when(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    doReturn(existingRange).when(rangeDao).find(any(RangeFilterVO.class));
    doNothing().when(rangeDao).remove(existingRange);
    doReturn(List.of(rangeVO)).when(rangeHelper).splitRanges(anySet(), anyList());
    doReturn(new StockVO()).when(stockDao).findStockWithRanges(null, null, 0, null, null);
    doNothing().when(stockDao).addRanges(any(StockVO.class), anyList());
    doNothing().when(createHistoryInvoker).invoke(any(StockAllocationVO.class));

    // Then
    assertDoesNotThrow(() -> deleteStockFeature.perform(List.of(rangeVO)));

    verify(contextUtil, times(3)).callerLoginProfile();
    verify(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    verify(rangeDao).find(any(RangeFilterVO.class));
    verify(rangeDao).remove(existingRange);
    verify(rangeHelper).splitRanges(anySet(), anyList());
    verify(stockDao).findStockWithRanges(null, null, 0, null, null);
    verify(stockDao).addRanges(any(StockVO.class), anyList());
    verify(createHistoryInvoker).invoke(any(StockAllocationVO.class));
  }

  @Test
  void shouldNotSaveSplitRanges() throws BusinessException {
    // Given
    var rangeVO = RangeVO.builder().startRange("1001000").endRange("1100000").build();

    var existingRange = RangeVO.builder().startRange("1001000").endRange("1100000").build();

    // When
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing().when(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    doReturn(existingRange).when(rangeDao).find(any(RangeFilterVO.class));
    doNothing().when(rangeDao).remove(existingRange);
    doNothing().when(createHistoryInvoker).invoke(any(StockAllocationVO.class));

    // Then
    assertDoesNotThrow(() -> deleteStockFeature.perform(List.of(rangeVO)));

    verify(contextUtil, times(2)).callerLoginProfile();
    verify(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    verify(rangeDao).find(any(RangeFilterVO.class));
    verify(rangeDao).remove(existingRange);
    verify(createHistoryInvoker).invoke(any(StockAllocationVO.class));
  }
}
