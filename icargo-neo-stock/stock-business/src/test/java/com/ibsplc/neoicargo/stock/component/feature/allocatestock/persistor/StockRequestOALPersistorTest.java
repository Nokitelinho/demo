package com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.stock.dao.StockRequestOALDao;
import com.ibsplc.neoicargo.stock.vo.StockRequestOALVO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockRequestOALPersistorTest {

  @InjectMocks private StockRequestOALPersistor persistor;

  @Mock private StockRequestOALDao dao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldUpdateWithoutThrows() {
    // Given
    var stockRequestOALVO = new StockRequestOALVO();
    stockRequestOALVO.setOperationFlag(StockRequestOALVO.OPERATION_FLAG_UPDATE);
    stockRequestOALVO.setCompanyCode("HQ");
    stockRequestOALVO.setAirportCode("FRA");
    stockRequestOALVO.setDocumentType("T1");
    stockRequestOALVO.setDocumentSubType("S1");
    stockRequestOALVO.setAirlineIdentifier(123);
    stockRequestOALVO.setSerialNumber(1);

    // When
    doReturn(new StockRequestOALVO())
        .when(dao)
        .find(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt());
    doNothing().when(dao).saveAll(anyList());

    // Then
    Assertions.assertDoesNotThrow(() -> persistor.update(List.of(stockRequestOALVO)));
    verify(dao, times(1))
        .find(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt());
    verify(dao, times(1)).saveAll(anyList());
  }

  @Test
  void shouldThrowsSystemException() {
    // Given
    var stockRequestOALVO = new StockRequestOALVO();
    stockRequestOALVO.setOperationFlag(StockRequestOALVO.OPERATION_FLAG_UPDATE);
    stockRequestOALVO.setCompanyCode("HQ");
    stockRequestOALVO.setAirportCode("FRA");
    stockRequestOALVO.setDocumentType("T1");
    stockRequestOALVO.setDocumentSubType("S1");
    stockRequestOALVO.setAirlineIdentifier(123);
    stockRequestOALVO.setSerialNumber(1);

    // When
    doReturn(null)
        .when(dao)
        .find(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt());

    // Then
    var thrown =
        assertThrows(SystemException.class, () -> persistor.update(List.of(stockRequestOALVO)));
    assertEquals("OP_FAILED", thrown.getErrorCode());
    verify(dao, never()).saveAll(anyList());
  }
}
