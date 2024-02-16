package com.ibsplc.neoicargo.stock.component.feature.validatestock.validator;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("airlineValidator")
@RequiredArgsConstructor
public class AirlineValidator extends Validator<DocumentFilterVO> {

  public static final String AIRLINE_MODEL = "AIRLINE_MODEL";

  private final AirlineWebComponent airlineComponent;

  public void validate(DocumentFilterVO documentFilterVO) throws BusinessException {
    log.info("airlineValidator Invoked");

    if (Objects.nonNull(documentFilterVO.getPrefix())) {
      var airlineModel = airlineComponent.validateNumericCode(documentFilterVO.getPrefix());
      FeatureContextUtilThreadArray.getInstance()
          .getFeatureContext()
          .getContextMap()
          .put(AIRLINE_MODEL, airlineModel);
    }
  }
}
