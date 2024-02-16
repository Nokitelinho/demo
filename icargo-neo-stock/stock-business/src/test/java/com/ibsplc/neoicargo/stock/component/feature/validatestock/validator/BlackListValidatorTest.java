package com.ibsplc.neoicargo.stock.component.feature.validatestock.validator;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockDocumentFilterVO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.mapper.DocumentFilterMapper;
import com.ibsplc.neoicargo.stock.model.DocumentFilter;
import com.ibsplc.neoicargo.stock.model.DocumentValidation;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import org.junit.jupiter.api.Assertions;
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
class BlackListValidatorTest {

  @InjectMocks private BlackListValidator blackListValidator;

  @Mock private ServiceProxy<Object> serviceProxy;
  @Mock private ContextUtil contextUtil;

  @Spy
  private final DocumentFilterMapper documentFilterMapper =
      Mappers.getMapper(DocumentFilterMapper.class);

  private DocumentFilterVO documentFilterVO;
  private DocumentValidation documentValidation;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    documentFilterVO = getMockDocumentFilterVO();
    documentValidation = new DocumentValidation();
    documentValidation.setStockHolderCode("DHLCDG");
    doReturn(new LoginProfile()).when(contextUtil).callerLoginProfile();
    ReflectionTestUtils.setField(
        blackListValidator,
        "eblUrl",
        "http://localhost:9052/bookings/v1/bookings/check/blacklist/09999000");
  }

  @Test
  void shouldNotThrowExceptionWhenValidatingStockAsBlacklist() {
    doReturn(documentValidation)
        .when(serviceProxy)
        .dispatch(
            isA(String.class),
            eq(HttpMethod.POST),
            isA(DocumentFilter.class),
            eq(DocumentValidation.class));
    Assertions.assertDoesNotThrow(() -> blackListValidator.validate(documentFilterVO));
  }

  @Test
  void shouldNotThrowSystemExceptionWhenValidatingStockAsBlacklist() {
    // When
    doThrow(SystemException.class)
        .when(serviceProxy)
        .dispatch(
            isA(String.class),
            eq(HttpMethod.POST),
            isA(DocumentFilter.class),
            eq(DocumentValidation.class));

    // Then
    assertThrows(SystemException.class, () -> blackListValidator.validate(documentFilterVO));
  }

  @Test
  void shouldThrowBusinessExceptionWhenValidatingStockAsBlacklist() {
    // Given
    documentValidation.setBlacklistFlag(true);

    // When
    doReturn(documentValidation)
        .when(serviceProxy)
        .dispatch(
            isA(String.class),
            eq(HttpMethod.POST),
            isA(DocumentFilter.class),
            eq(DocumentValidation.class));

    // Then
    var thrown =
        assertThrows(BusinessException.class, () -> blackListValidator.validate(documentFilterVO));
    assertEquals("AWB_BLACKLISTED", thrown.getErrorCode());
  }
}
