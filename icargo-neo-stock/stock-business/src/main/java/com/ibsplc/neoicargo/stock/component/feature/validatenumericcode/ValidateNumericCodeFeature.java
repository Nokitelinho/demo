package com.ibsplc.neoicargo.stock.component.feature.validatenumericcode;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.airline.AirlineWebAPI;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.stock.mapper.AirlineMapper;
import com.ibsplc.neoicargo.stock.model.AirlineValidationModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("validateNumericCodeFeature")
@RequiredArgsConstructor
public class ValidateNumericCodeFeature {

  private final AirlineMapper airlineMapper;

  private final AirlineWebAPI airlineWebAPI;

  public AirlineValidationModel perform(String companyCode, String shipmentPrefix)
      throws BusinessException {
    log.info("companyCode:{}", companyCode);
    AirlineModel airlineModel = null;
    airlineModel = airlineWebAPI.validateNumericCode(shipmentPrefix);
    return airlineMapper.mapVoToModel(airlineModel);
  }
}
