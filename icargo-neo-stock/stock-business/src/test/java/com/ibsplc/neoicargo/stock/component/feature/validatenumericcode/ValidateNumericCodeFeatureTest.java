package com.ibsplc.neoicargo.stock.component.feature.validatenumericcode;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.airline.AirlineWebAPI;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.stock.mapper.AirlineMapper;
import java.time.LocalDate;
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
class ValidateNumericCodeFeatureTest {

  @InjectMocks private ValidateNumericCodeFeature validateNumericCodeFeature;

  @Mock private AirlineWebAPI arlineWebAPI;

  @Spy private AirlineMapper airlineMapper = Mappers.getMapper(AirlineMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateNumericCodeOk() throws BusinessException {
    AirlineModel arlmdl = new AirlineModel();
    arlmdl.setValidFrom(LocalDate.now());
    arlmdl.setValidTo(LocalDate.now());

    doReturn(arlmdl).when(arlineWebAPI).validateNumericCode("123");

    assertDoesNotThrow(() -> validateNumericCodeFeature.perform("IBS", "123"));

    verify(arlineWebAPI).validateNumericCode("123");
    verify(airlineMapper).mapVoToModel(any(AirlineModel.class));
  }

  /*@Test
  void shouldValidateNumericCodeInvalid() throws BusinessException {
    doThrow(BusinessException.class).when(arlineWebAPI).validateNumericCode("INVALID");

    assertThrows(
        BusinessException.class, () -> validateNumericCodeFeature.perform("IBS", "INVALID"));

    verify(arlineWebAPI).validateNumericCode("INVALID");
  }*/
}
