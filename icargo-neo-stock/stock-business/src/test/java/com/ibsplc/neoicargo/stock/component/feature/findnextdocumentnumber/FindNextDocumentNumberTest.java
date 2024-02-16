package com.ibsplc.neoicargo.stock.component.feature.findnextdocumentnumber;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockDocumentFilterVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.stock.mapper.DocumentFilterMapper;
import com.ibsplc.neoicargo.stock.model.DocumentFilter;
import com.ibsplc.neoicargo.stock.model.DocumentValidation;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(JUnitPlatform.class)
class FindNextDocumentNumberTest {

  @InjectMocks private FindNextDocumentNumberFeature findNextDocumentNumberFeature;

  @Mock private ServiceProxy<Object> serviceProxy;

  private DocumentFilterVO documentFilterVO;

  @Spy
  private final DocumentFilterMapper documentFilterMapper =
      Mappers.getMapper(DocumentFilterMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    documentFilterVO = getMockDocumentFilterVO();
    ReflectionTestUtils.setField(
        findNextDocumentNumberFeature, "eblUrl", "http://localhost:9052/bookings/v1/find/nextawb");
  }

  @Test
  void shouldNotThrowOnFindNextDocumentNumber() {
    // When
    doReturn(new DocumentValidation())
        .when(serviceProxy)
        .dispatch(
            isA(String.class),
            eq(HttpMethod.POST),
            isA(DocumentFilter.class),
            eq(DocumentValidation.class));

    // Then
    assertDoesNotThrow(() -> findNextDocumentNumberFeature.perform(documentFilterVO));
  }

  @Test
  void shouldThrowSystemExceptionOnFindNextDocumentNumber() {
    // When
    doThrow(SystemException.class)
        .when(serviceProxy)
        .dispatch(
            isA(String.class),
            eq(HttpMethod.POST),
            isA(DocumentFilter.class),
            eq(DocumentValidation.class));

    // Then
    assertThrows(
        SystemException.class, () -> findNextDocumentNumberFeature.perform(documentFilterVO));
  }

  @Test
  void shouldFindNextDocumentNumber() {
    // When
    doReturn(null)
        .when(serviceProxy)
        .dispatch(isA(String.class), eq(HttpMethod.POST), eq(null), eq(DocumentFilterVO.class));

    // Then
    var thrown =
        assertThrows(
            BusinessException.class, () -> findNextDocumentNumberFeature.perform(documentFilterVO));
    assertEquals("EBL_BKG_015", thrown.getErrorCode());
  }
}
