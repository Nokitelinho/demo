package com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator;

import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_STOCK_HOLDER_CODE;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.startsWith;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.area.airport.AirportWebAPI;
import com.ibsplc.neoicargo.masters.area.airport.modal.AirportParameterModel;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("stockHolderCodeValidator")
@RequiredArgsConstructor
public class StockHolderCodeValidator {

  private final AirportWebAPI airportWebAPI;

  public boolean isRelatedToStockControlFor(String airportCode, String stockControlFor)
      throws BusinessException {
    String stockHolderCode = null;
    Set<AirportParameterModel> airportParameterModel =
        airportWebAPI.findAirportParametersByCode(
            airportCode, Collections.singletonList(STOCK_DEFAULTS_STOCK_HOLDER_CODE));
    if (Objects.nonNull(airportParameterModel) && !airportParameterModel.isEmpty()) {
      stockHolderCode = airportParameterModel.stream().findFirst().get().getParameterValue();
    }
    return ofNullable(stockHolderCode).map(code -> startsWith(code, stockControlFor)).orElse(false);
  }
}
