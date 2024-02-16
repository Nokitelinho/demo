package com.ibsplc.neoicargo.stock.component.feature.cancelstockrequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.component.feature.cancelstockrequest.persistor.CancelStockRequestPersistor;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class CancelStockRequestFeatureTest {

  @Mock private CancelStockRequestPersistor cancelStockRequestPersistor;

  @InjectMocks private CancelStockRequestFeature cancelStockRequestFeature;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void perform() throws StockBusinessException {
    // When
    doNothing().when(cancelStockRequestPersistor).cancelStockRequest(any(StockRequestVO.class));
    cancelStockRequestFeature.perform(new StockRequestVO());

    // Then
    verify(cancelStockRequestPersistor).cancelStockRequest(any(StockRequestVO.class));
  }
}
