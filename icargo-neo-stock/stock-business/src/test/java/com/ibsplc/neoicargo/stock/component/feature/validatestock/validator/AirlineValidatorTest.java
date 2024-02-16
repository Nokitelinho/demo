package com.ibsplc.neoicargo.stock.component.feature.validatestock.validator;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockDocumentFilterVO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import com.ibsplc.neoicargo.framework.orchestration.FeatureTestSupport;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class AirlineValidatorTest {

  @InjectMocks private AirlineValidator airlineValidator;

  @Mock private AirlineWebComponent airlineComponent;

  private AirlineModel airlineModel;
  private DocumentFilterVO documentFilterVO;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    documentFilterVO = getMockDocumentFilterVO();
    airlineModel = new AirlineModel();
    airlineModel.setAirlineIdentifier(1134);
    FeatureTestSupport.mockFeatureContext();
  }

  @Test
  void shouldValidateAirline() throws BusinessException {
    // When
    doReturn(airlineModel).when(airlineComponent).validateNumericCode(any(String.class));
    airlineValidator.validate(documentFilterVO);

    // Then
    Assertions.assertNotNull(
        FeatureContextUtilThreadArray.getInstance()
            .getFeatureContext()
            .getContextMap()
            .get(AirlineValidator.AIRLINE_MODEL));
  }

  @Test
  void shouldNotValidateAirlineIfShipmentPrefixIsEmpty() throws BusinessException {
    // Given
    documentFilterVO.setPrefix(null);

    // When
    airlineValidator.validate(documentFilterVO);

    // Then
    verify(airlineComponent, never()).validateNumericCode(any(String.class));
  }
}
