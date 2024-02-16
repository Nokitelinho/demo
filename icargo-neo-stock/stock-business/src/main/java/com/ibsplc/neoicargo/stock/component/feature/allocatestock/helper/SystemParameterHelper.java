package com.ibsplc.neoicargo.stock.component.feature.allocatestock.helper;

import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_CONFIRMATION_REQUIRED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_ENABLE_STOCK_HISTORY;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_STOCK_INTRODUCTION_PERIOD;

import com.ibsplc.neoicargo.common.types.Pair;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;
import com.ibsplc.neoicargo.stock.vo.StockHolderParametersVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SystemParameterHelper {

  private final ParameterService parameterService;

  private static final List<String> SYSTEM_KEYS =
      List.of(
          STOCK_DEFAULTS_ENABLE_STOCK_HISTORY,
          STOCK_DEFAULTS_CONFIRMATION_REQUIRED,
          STOCK_DEFAULTS_STOCK_INTRODUCTION_PERIOD,
          STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM);

  public StockHolderParametersVO getSystemParameters() {
    try {
      final var valuesByKey =
          parameterService.getParameters(SYSTEM_KEYS, ParameterType.SYSTEM_PARAMETER);

      var parametersVO =
          StockHolderParametersVO.builder()
              .enableStockHistory(
                  FLAG_YES.equals(
                      getParameterValues(STOCK_DEFAULTS_ENABLE_STOCK_HISTORY, valuesByKey)))
              .enableConfirmationProcess(
                  FLAG_YES.equals(
                      getParameterValues(STOCK_DEFAULTS_CONFIRMATION_REQUIRED, valuesByKey)))
              .stockIntroductionPeriod(
                  getIntroductionPeriod(
                      getParameterValues(STOCK_DEFAULTS_STOCK_INTRODUCTION_PERIOD, valuesByKey)))
              .accountingFlag(
                  getParameterValues(STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM, valuesByKey))
              .build();
      log.info("StockHolderParametersVO: {}", parametersVO.toString());
      return parametersVO;

    } catch (BusinessException exception) {
      throw new SystemException(SystemException.UNEXPECTED_SERVER_ERROR, exception);
    }
  }

  private String getParameterValues(String parameterCode, List<Pair> valuesByKey) {

    for (Pair pair : CollectionUtils.emptyIfNull(valuesByKey)) {

      if (parameterCode.equals(pair.getName())) {

        return pair.getValue().toString();
      }
    }
    return null;
  }

  private int getIntroductionPeriod(String value) {
    if (value == null) {
      return 0;
    }
    return Integer.parseInt(value.trim());
  }
}
