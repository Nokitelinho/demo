package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockAllocationVO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.proxy.impl.DocumentTypeProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class RangeFormatValidatorTest {

  @InjectMocks private RangeFormatValidator validator;

  @Mock private DocumentTypeProxy proxy;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateWithoutThrows() throws StockBusinessException {
    // Given
    var stockAllocationVO = getMockStockAllocationVO();

    // When
    doNothing().when(proxy).validateRanges(any(DocumentVO.class));

    // Then
    Assertions.assertDoesNotThrow(() -> validator.validate(stockAllocationVO));
  }

  @Test
  void shouldThrowsServiceException() throws StockBusinessException {
    // Given
    var stockAllocationVO = getMockStockAllocationVO();
    var errorCode = "something goes wrong";
    var serviceException = new ServiceException(errorCode);
    serviceException.setDeveloperMessage(errorCode);

    // When
    doThrow(serviceException).when(proxy).validateRanges(any(DocumentVO.class));

    // Then
    var thrown = assertThrows(ServiceException.class, () -> validator.validate(stockAllocationVO));
    assertEquals(errorCode, thrown.getDeveloperMessage());
  }
}
