package com.ibsplc.neoicargo.stock.component.feature.allocatestock.invoker;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockAllocationVO;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.component.feature.createhistory.CreateHistoryFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class CreateHistoryInvokerTest {
  @InjectMocks private CreateHistoryInvoker invoker;

  @Mock private CreateHistoryFeature feature;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldInvokeFeature() {
    // Given
    var vo = getMockStockAllocationVO();

    // When
    doNothing().when(feature).perform(vo, vo.getTransactionCode());

    // Then
    Assertions.assertDoesNotThrow(() -> invoker.invoke(vo));
    verify(feature, times(1)).perform(vo, vo.getTransactionCode());
  }
}
