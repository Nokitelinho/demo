package com.ibsplc.neoicargo.stock.component.feature.validatestock.enricher;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockDocumentFilterVO;

import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import com.ibsplc.neoicargo.framework.orchestration.FeatureTestSupport;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.stock.component.feature.validatestock.validator.AirlineValidator;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class ValidateStockHolderDetailsPersistorTest {

  @InjectMocks private ValidateStockEnricher validateStockEnricher;

  private DocumentFilterVO documentFilterVO;
  private AirlineModel airlineModel;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    documentFilterVO = getMockDocumentFilterVO();
    airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(1134);
    FeatureTestSupport.mockFeatureContext();
  }

  @Test
  void shouldEnrichAirlineDetailsFromContext() {
    // Given
    FeatureContextUtilThreadArray.getInstance()
        .getFeatureContext()
        .getContextMap()
        .put(AirlineValidator.AIRLINE_MODEL, airlineModel);

    // When
    validateStockEnricher.enrich(documentFilterVO);

    // Then
    Assertions.assertEquals(1134, documentFilterVO.getAirlineIdentifier());
  }
}
