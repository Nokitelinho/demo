package com.ibsplc.neoicargo.stock.component.feature.viewrange;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getStockFilterVO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class ViewRangeFeatureTest {
  @InjectMocks private ViewRangeFeature viewRangeFeature;

  @Mock private RangeDao rangeDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnAvailableRanges() {
    // Given
    var availableRanges = List.of(new RangeVO());
    var stockFilterVO = getStockFilterVO(false, "N", "S", "AWB", 1234, "HQ", "AV");

    // When
    doReturn(availableRanges).when(rangeDao).findRangesForViewRange(stockFilterVO);

    // Then
    var result = viewRangeFeature.perform(stockFilterVO);
    assertFalse(result.getAvailableRanges().isEmpty());
    assertNull(result.getAllocatedRanges());
  }

  @Test
  void shouldReturnAvailableAndAllocatedRanges() {
    // Given
    var availableRanges = List.of(new RangeVO());
    var allocatedRange = List.of(new RangeVO());
    var stockFilterVO = getStockFilterVO(true, "N", "S", "AWB", 1234, "HQ", "AV");

    // When
    doReturn(availableRanges).when(rangeDao).findRangesForViewRange(stockFilterVO);
    doReturn(allocatedRange).when(rangeDao).findAllocatedRanges(stockFilterVO);

    // Then
    var result = viewRangeFeature.perform(stockFilterVO);
    assertFalse(result.getAvailableRanges().isEmpty());
    assertFalse(result.getAllocatedRanges().isEmpty());
  }
}
