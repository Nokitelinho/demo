package com.ibsplc.neoicargo.stock.component.feature.validatestock.enricher;

import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.stock.component.feature.validatestock.validator.AirlineValidator;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("validateStockEnricher")
public class ValidateStockEnricher extends Enricher<DocumentFilterVO> {

  public void enrich(DocumentFilterVO documentFilterVO) {
    log.info("validateStockEnricher Invoked");
    var airlineModel =
        (AirlineModel)
            FeatureContextUtilThreadArray.getInstance()
                .getFeatureContext()
                .getContextMap()
                .get(AirlineValidator.AIRLINE_MODEL);
    documentFilterVO.setAirlineIdentifier(airlineModel.getAirlineIdentifier());
  }
}
