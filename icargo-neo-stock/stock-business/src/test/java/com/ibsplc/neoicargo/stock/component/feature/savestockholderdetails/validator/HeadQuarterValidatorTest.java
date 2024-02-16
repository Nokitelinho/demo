package com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_014;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class HeadQuarterValidatorTest {
  @InjectMocks private HeadQuarterValidator headQuarterValidator;
  @Mock private StockHolderRepository stockHolderRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  private static Stream<Arguments> provideTestParameters() {
    var stockVO = new StockVO();
    stockVO.setCompanyCode("AV");
    stockVO.setStockHolderCode("HQ");
    stockVO.setAirlineId(1234);
    stockVO.setDocumentType("AWB");
    stockVO.setDocumentSubType("S");
    stockVO.setOperationFlag("I");
    stockVO.setLastUpdateTime(ZonedDateTime.now());

    return Stream.of(
        Arguments.of(
            StockHolderVO.builder()
                .companyCode("AV")
                .stockHolderCode("HQ")
                .operationFlag("U")
                .stockHolderType(StockHolderType.H)
                .stock(Collections.singleton(stockVO))
                .lastUpdateTime(ZonedDateTime.now())
                .build()),
        Arguments.of(
            StockHolderVO.builder()
                .companyCode("AV")
                .stockHolderCode("HQ")
                .operationFlag("I")
                .stockHolderType(StockHolderType.R)
                .stock(Collections.singleton(stockVO))
                .lastUpdateTime(ZonedDateTime.now())
                .build()));
  }

  @ParameterizedTest
  @MethodSource("provideTestParameters")
  void shouldNotThrowException(StockHolderVO stockHolderVO) {
    // Then
    Assertions.assertDoesNotThrow(() -> headQuarterValidator.validate(stockHolderVO));
    verifyNoInteractions(stockHolderRepository);
  }

  @Test
  void shouldNotThrowExceptionWhenHeadQuartersDoesNotExist() {
    // Given
    var stockVO = new StockVO();
    stockVO.setCompanyCode("AV");
    stockVO.setStockHolderCode("HQ");
    stockVO.setAirlineId(1234);
    stockVO.setDocumentType("AWB");
    stockVO.setDocumentSubType("S");
    stockVO.setOperationFlag("I");
    stockVO.setLastUpdateTime(ZonedDateTime.now());

    var stockHolderVO = new StockHolderVO();
    stockHolderVO.setCompanyCode("AV");
    stockHolderVO.setStockHolderCode("HQ");
    stockHolderVO.setOperationFlag("I");
    stockHolderVO.setStockHolderType(StockHolderType.H);
    stockHolderVO.setStock(Collections.singleton(stockVO));
    stockHolderVO.setLastUpdateTime(ZonedDateTime.now());

    // When
    doReturn(Collections.emptyList())
        .when(stockHolderRepository)
        .findHeadQuarterDetails(
            stockHolderVO.getCompanyCode(),
            stockVO.getAirlineIdentifier(),
            stockVO.getDocumentType(),
            stockVO.getDocumentSubType());

    // Then
    Assertions.assertDoesNotThrow(() -> headQuarterValidator.validate(stockHolderVO));
    verify(stockHolderRepository, times(1))
        .findHeadQuarterDetails(
            stockHolderVO.getCompanyCode(),
            stockVO.getAirlineIdentifier(),
            stockVO.getDocumentType(),
            stockVO.getDocumentSubType());
  }

  @Test
  void shouldThrowException() {
    // Given
    var stockVO = new StockVO();
    stockVO.setCompanyCode("AV");
    stockVO.setStockHolderCode("HQ");
    stockVO.setAirlineId(1234);
    stockVO.setDocumentType("AWB");
    stockVO.setDocumentSubType("S");
    stockVO.setOperationFlag("I");
    stockVO.setLastUpdateTime(ZonedDateTime.now());

    var stockHolderVO = new StockHolderVO();
    stockHolderVO.setCompanyCode("AV");
    stockHolderVO.setStockHolderCode("HQ");
    stockHolderVO.setOperationFlag("I");
    stockHolderVO.setStockHolderType(StockHolderType.H);
    stockHolderVO.setStock(Collections.singleton(stockVO));
    stockHolderVO.setLastUpdateTime(ZonedDateTime.now());

    // When
    doReturn(List.of(""))
        .when(stockHolderRepository)
        .findHeadQuarterDetails(
            stockHolderVO.getCompanyCode(),
            stockVO.getAirlineIdentifier(),
            stockVO.getDocumentType(),
            stockVO.getDocumentSubType());

    // Then
    var thrown =
        assertThrows(BusinessException.class, () -> headQuarterValidator.validate(stockHolderVO));
    assertEquals(NEO_STOCK_014.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
    verify(stockHolderRepository, times(1))
        .findHeadQuarterDetails(
            stockHolderVO.getCompanyCode(),
            stockVO.getAirlineIdentifier(),
            stockVO.getDocumentType(),
            stockVO.getDocumentSubType());
  }
}
