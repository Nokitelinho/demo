package com.ibsplc.neoicargo.stock.component.feature.createhistory.persistor;

import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockStockRangeHistory;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockRangeVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRangeHistoryVO;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockRangeHistoryDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockRangeHistory;
import com.ibsplc.neoicargo.stock.mapper.StockRangeHistoryMapper;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
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
class MergedRangesPersistorTest {

  @InjectMocks private MergedRangesPersistor mergedRangesPersistor;

  @Mock private StockRangeHistoryMapper stockRangeHistoryMapper;
  @Mock private StockRangeHistoryDao stockRangeHistoryDao;

  private final StockRangeHistory stockRangeHistory = getMockStockRangeHistory();
  private final StockRangeHistoryVO stockRangeHistoryVO = getMockStockRangeHistoryVO();
  private final List<RangeVO> ranges = List.of(getMockRangeVO("IBS", "AWB", "S", 0L, 99999L));

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldSaveStockRangeHistoryObjects(boolean isFromStockHolderCodeChecked) {
    doReturn(stockRangeHistory).when(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    doNothing().when(stockRangeHistoryDao).save(stockRangeHistory);

    mergedRangesPersistor.persist(stockRangeHistoryVO, ranges, isFromStockHolderCodeChecked);

    verify(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao).save(stockRangeHistory);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldSaveStockRangeHistoryObjectsAsciiEndRangeIsZeroAndEndRangeIsNull(
      boolean isFromStockHolderCodeChecked) {
    doReturn(stockRangeHistory).when(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    doNothing().when(stockRangeHistoryDao).save(stockRangeHistory);

    ranges.forEach(
        rangeVO -> {
          rangeVO.setAsciiEndRange(0);
          rangeVO.setEndRange(null);
        });
    mergedRangesPersistor.persist(stockRangeHistoryVO, ranges, isFromStockHolderCodeChecked);

    verify(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao).save(stockRangeHistory);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldSaveStockRangeHistoryObjectsAsciiStartRangeIsZeroAndStartRangeIsNull(
      boolean isFromStockHolderCodeChecked) {
    doReturn(stockRangeHistory).when(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    doNothing().when(stockRangeHistoryDao).save(stockRangeHistory);

    ranges.forEach(
        rangeVO -> {
          rangeVO.setAsciiStartRange(0);
          rangeVO.setStartRange(null);
        });
    mergedRangesPersistor.persist(stockRangeHistoryVO, ranges, isFromStockHolderCodeChecked);

    verify(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao).save(stockRangeHistory);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldSaveStockRangeHistoryObjectsAsciiStartRangeIsZero(
      boolean isFromStockHolderCodeChecked) {
    doReturn(stockRangeHistory).when(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    doNothing().when(stockRangeHistoryDao).save(stockRangeHistory);

    ranges.forEach(
        rangeVO -> {
          rangeVO.setAsciiStartRange(0);
          rangeVO.setStartRange("222");
        });
    mergedRangesPersistor.persist(stockRangeHistoryVO, ranges, isFromStockHolderCodeChecked);

    verify(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao).save(stockRangeHistory);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldSaveStockRangeHistoryObjectsAsciiEndRangeIsZero(boolean isFromStockHolderCodeChecked) {
    doReturn(stockRangeHistory).when(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    doNothing().when(stockRangeHistoryDao).save(stockRangeHistory);

    ranges.forEach(
        rangeVO -> {
          rangeVO.setAsciiEndRange(0);
          rangeVO.setEndRange("222");
        });
    mergedRangesPersistor.persist(stockRangeHistoryVO, ranges, isFromStockHolderCodeChecked);

    verify(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao).save(stockRangeHistory);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldSaveStockRangeHistoryObjectsStartRangeIsNull(boolean isFromStockHolderCodeChecked) {
    doReturn(stockRangeHistory).when(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    doNothing().when(stockRangeHistoryDao).save(stockRangeHistory);

    ranges.forEach(
        rangeVO -> {
          rangeVO.setAsciiStartRange(5);
          rangeVO.setStartRange(null);
        });
    mergedRangesPersistor.persist(stockRangeHistoryVO, ranges, isFromStockHolderCodeChecked);

    verify(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao).save(stockRangeHistory);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldSaveStockRangeHistoryObjectsEndRangeIsNull(boolean isFromStockHolderCodeChecked) {
    doReturn(stockRangeHistory).when(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    doNothing().when(stockRangeHistoryDao).save(stockRangeHistory);

    ranges.forEach(
        rangeVO -> {
          rangeVO.setAsciiEndRange(5);
          rangeVO.setEndRange(null);
        });
    mergedRangesPersistor.persist(stockRangeHistoryVO, ranges, isFromStockHolderCodeChecked);

    verify(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao).save(stockRangeHistory);
  }

  @Test
  void shouldNotSaveStockRangeHistoryObjectsWhenFromStockHolderCodeIsNullAndChecked() {
    stockRangeHistoryVO.setFromStockHolderCode(null);
    doReturn(stockRangeHistory).when(stockRangeHistoryMapper).mapVoToEntity(stockRangeHistoryVO);
    doNothing().when(stockRangeHistoryDao).save(stockRangeHistory);

    mergedRangesPersistor.persist(stockRangeHistoryVO, ranges, true);

    verify(stockRangeHistoryMapper, times(0)).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao, times(0)).save(stockRangeHistory);
  }

  @Test
  void shouldNotDoAnythingWhenRangesListIsEmpty() {
    mergedRangesPersistor.persist(stockRangeHistoryVO, List.of(), false);

    verify(stockRangeHistoryMapper, times(0)).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao, times(0)).save(stockRangeHistory);
  }

  @Test
  void shouldNotDoAnythingWhenRangesListIsNull() {
    mergedRangesPersistor.persist(stockRangeHistoryVO, null, false);

    verify(stockRangeHistoryMapper, times(0)).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao, times(0)).save(stockRangeHistory);
  }

  @Test
  void shouldNotDoAnythingWhenStockRangeHistoryVOIsNull() {
    mergedRangesPersistor.persist(null, ranges, false);

    verify(stockRangeHistoryMapper, times(0)).mapVoToEntity(stockRangeHistoryVO);
    verify(stockRangeHistoryDao, times(0)).save(stockRangeHistory);
  }
}
