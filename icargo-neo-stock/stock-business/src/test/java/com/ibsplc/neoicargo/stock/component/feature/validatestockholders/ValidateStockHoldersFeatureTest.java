package com.ibsplc.neoicargo.stock.component.feature.validatestockholders;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockStockHolderEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class ValidateStockHoldersFeatureTest {

  @InjectMocks private ValidateStockHoldersFeature validateStockHoldersFeature;

  @Mock private StockHolderRepository stockHolderRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldNotThrowExceptionWhenValidateStockHolders() throws StockBusinessException {
    // Given
    var stockHolderCodes = List.of("HQ");

    // When
    doReturn(stockHolderCodes)
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCodeIn("AV", stockHolderCodes);

    // Then
    assertDoesNotThrow(() -> validateStockHoldersFeature.perform("AV", stockHolderCodes));
  }

  @Test
  void shouldThrowExceptionWhenValidateStockHolders() {
    // Given
    var incomeStockHolderCodes = new ArrayList<String>();
    incomeStockHolderCodes.add("HQ");
    incomeStockHolderCodes.add("NONAME");

    var stockHolderCodes = new ArrayList<StockHolder>();
    stockHolderCodes.add(getMockStockHolderEntity("AV", "HQ"));

    // When
    doReturn(stockHolderCodes)
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCodeIn("AV", incomeStockHolderCodes);

    // Then
    StockBusinessException exception =
        assertThrows(
            StockBusinessException.class,
            () -> validateStockHoldersFeature.perform("AV", incomeStockHolderCodes));
    assertThat(NEO_STOCK_011.getErrorCode()).isEqualTo(exception.getErrors().get(0).getErrorCode());
  }
}
