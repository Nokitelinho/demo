package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockTransitStockVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.stock.dao.entity.TransitStock;
import com.ibsplc.neoicargo.stock.dao.repository.TransitStockRepository;
import com.ibsplc.neoicargo.stock.mapper.TransitStockMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
public class TransitStockPersistorTest {
  private static final String COMPANY_CODE = "AV";
  private static final int AIRLINE_IDENTIFIER = 1234;
  private static final String STOCK_HOLDER_CODE = "HQ";
  private static final String DOC_TYPE = "AWB";
  private static final String DOC_SUB_TYPE = "S";
  @InjectMocks private TransitStockPersistor transitStockPersistor;
  @Mock private TransitStockRepository transitStockRepository;
  @Spy private TransitStockMapper transitStockMapper = Mappers.getMapper(TransitStockMapper.class);

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(transitStockRepository);
  }

  @Test
  void shouldDeleteTransitStock() {
    // Given
    var transitStockVO =
        getMockTransitStockVO(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);
    transitStockVO.setOperationFlag("D");

    // When
    doReturn(Optional.of(new TransitStock()))
        .when(transitStockRepository)
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);

    // Then
    assertDoesNotThrow(() -> transitStockPersistor.persist(List.of(transitStockVO)));
    verify(transitStockRepository, times(1)).delete(any(TransitStock.class));
    verify(transitStockRepository, times(1))
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);
  }

  @Test
  void shouldSaveTransitStock() {
    // Given
    var transitStockVO =
        getMockTransitStockVO(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);
    transitStockVO.setOperationFlag("I");

    // When
    doReturn(Optional.empty())
        .when(transitStockRepository)
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);

    // Then
    assertDoesNotThrow(() -> transitStockPersistor.persist(List.of(transitStockVO)));
    verify(transitStockRepository, times(1)).save(any(TransitStock.class));
  }

  @Test
  void shouldThrowWhileDeleteTransitStock() {
    // Given
    var transitStockVO =
        getMockTransitStockVO(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);
    transitStockVO.setOperationFlag("D");

    // When
    doReturn(Optional.empty())
        .when(transitStockRepository)
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);

    // Then
    var thrown =
        assertThrows(
            SystemException.class, () -> transitStockPersistor.persist(List.of(transitStockVO)));
    verify(transitStockRepository, times(1))
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);
    assertEquals("OP_FAILED", thrown.getErrors().get(0).getErrorCode());
  }
}
