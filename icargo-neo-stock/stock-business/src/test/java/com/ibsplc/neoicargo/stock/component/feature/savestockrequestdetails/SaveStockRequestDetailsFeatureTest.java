package com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestVO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.persistor.StockRequestDetailsPersistor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class SaveStockRequestDetailsFeatureTest {
  @InjectMocks private SaveStockRequestDetailsFeature saveStockRequestDetailsFeature;
  @Mock private StockRequestDetailsPersistor stockRequestDetailsPersistor;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(stockRequestDetailsPersistor);
  }

  @Test
  void shouldReturnRequestRefNumberWhileInsertOperation() throws BusinessException {
    // Given
    var requestRefNumber = "123";
    var stockRequestVO = getMockStockRequestVO();
    stockRequestVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);

    // When
    doReturn(requestRefNumber).when(stockRequestDetailsPersistor).save(stockRequestVO);

    // Then
    var actualRequestRefNumber = saveStockRequestDetailsFeature.perform(stockRequestVO);
    assertEquals(requestRefNumber, actualRequestRefNumber);
    verify(stockRequestDetailsPersistor, times(1)).save(stockRequestVO);
  }

  @Test
  void shouldReturnRequestRefNumberWhileUpdateOperation() throws BusinessException {
    // Given
    var requestRefNumber = "123";
    var stockRequestVO = getMockStockRequestVO();
    stockRequestVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);

    // When
    doReturn(requestRefNumber).when(stockRequestDetailsPersistor).update(stockRequestVO);

    // Then
    var actualRequestRefNumber = saveStockRequestDetailsFeature.perform(stockRequestVO);
    assertEquals(requestRefNumber, actualRequestRefNumber);
    verify(stockRequestDetailsPersistor, times(1)).update(stockRequestVO);
  }

  @Test
  void shouldReturnNullWhileOtherOperation() throws BusinessException {
    // Given
    var stockRequestVO = getMockStockRequestVO();
    stockRequestVO.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);

    // Then
    var actualRequestRefNumber = saveStockRequestDetailsFeature.perform(stockRequestVO);
    assertNull(actualRequestRefNumber);
  }
}
