package com.ibsplc.neoicargo.stock.proxy.impl;

import static com.ibsplc.neoicargo.stock.util.StockConstant.INVALID_RANGE_FORMAT_INDICATOR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.proxy.DocumentTypeEProxy;
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
class DocumentTypeProxyTest {

  @InjectMocks private DocumentTypeProxy proxy;

  @Mock private DocumentTypeEProxy eProxy;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateRangesWithoutThrows() {
    // When
    doNothing().when(eProxy).validateRange(any(DocumentVO.class));

    // Then
    Assertions.assertDoesNotThrow(() -> proxy.validateRanges(new DocumentVO()));
  }

  @Test
  void shouldThrowExceptionWhenValidateRanges() {
    // When
    doThrow(ServiceException.class).when(eProxy).validateRange(any(DocumentVO.class));

    // Then
    assertThrows(SystemException.class, () -> proxy.validateRanges(new DocumentVO()));
  }

  @Test
  void shouldThrowInvalidAlphanumericRange() {
    // Given
    var errorCode = INVALID_RANGE_FORMAT_INDICATOR;
    var serviceException = new ServiceException(errorCode);
    serviceException.setDeveloperMessage(errorCode);

    // When
    doThrow(serviceException).when(eProxy).validateRange(any(DocumentVO.class));

    // Then
    assertThrows(StockBusinessException.class, () -> proxy.validateRanges(new DocumentVO()));
  }

  @Test
  void shouldMergeRanges() {
    // Given
    var sharedRangeVO = List.of(new SharedRangeVO());

    // When
    doReturn(sharedRangeVO).when(eProxy).mergeRanges(any(DocumentVO.class));

    // Then
    Assertions.assertDoesNotThrow(() -> proxy.mergeRanges(new DocumentVO()));
  }

  @Test
  void shouldThrowExceptionWhenMergeRanges() {
    // When
    doThrow(ServiceException.class).when(eProxy).mergeRanges(any(DocumentVO.class));

    // Then
    assertThrows(SystemException.class, () -> proxy.mergeRanges(new DocumentVO()));
  }

  @Test
  void shouldSplitRanges() {
    // Given
    var sharedRangeVO = List.of(new SharedRangeVO());

    // When
    doReturn(sharedRangeVO).when(eProxy).splitRanges(anyCollection(), anyCollection());

    // Then
    Assertions.assertDoesNotThrow(() -> proxy.splitRanges(sharedRangeVO, sharedRangeVO));
  }

  @Test
  void shouldThrowExceptionWhenSplitRanges() {
    // Given
    var sharedRangeVO = List.of(new SharedRangeVO());

    // When
    doThrow(ServiceException.class).when(eProxy).splitRanges(anyCollection(), anyCollection());

    // Then
    assertThrows(SystemException.class, () -> proxy.splitRanges(sharedRangeVO, sharedRangeVO));
  }
}
