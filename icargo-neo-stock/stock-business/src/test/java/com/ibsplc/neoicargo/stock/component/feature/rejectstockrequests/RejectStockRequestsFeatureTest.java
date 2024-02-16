package com.ibsplc.neoicargo.stock.component.feature.rejectstockrequests;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestVO;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.stock.dao.StockRequestDao;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class RejectStockRequestsFeatureTest {
  @InjectMocks private RejectStockRequestsFeature rejectStockRequestsFeature;
  @Mock private StockRequestDao stockRequestDao;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(stockRequestDao);
  }

  @Test
  void shouldRejectStockRequests() {
    // Given
    var stockRequestVOs = List.of(getMockStockRequestVO("AV", "1111", "remarks", "127-1253463"));

    // When
    doNothing().when(stockRequestDao).rejectStockRequests(stockRequestVOs);

    // Then
    Assertions.assertDoesNotThrow(() -> rejectStockRequestsFeature.perform(stockRequestVOs));
    verify(stockRequestDao, times(1)).rejectStockRequests(stockRequestVOs);
  }
}
