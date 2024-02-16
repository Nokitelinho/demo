package com.ibsplc.neoicargo.stock.component.feature.finddocumentdetails;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestVO;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class FindDocumentDetailsFeatureTest {

  @InjectMocks private FindDocumentDetailsFeature findDocumentDetailsFeature;

  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnMonitoringStockHolderDetail() {
    // Given
    var stockRequestVO = getMockStockRequestVO();
    var companyCode = "AV";
    var airlineIdentifier = 1134;
    var documentNumber = "1234123";

    // When
    doReturn(stockRequestVO)
        .when(stockDao)
        .findDocumentDetails(companyCode, airlineIdentifier, toLong(documentNumber));

    // Then
    assertNotNull(
        findDocumentDetailsFeature.perform(companyCode, airlineIdentifier, documentNumber));
  }
}
