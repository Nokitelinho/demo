package com.ibsplc.neoicargo.stock.component.feature.allocatestock.helper;

import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_CONFIRMATION_REQUIRED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_ENABLE_STOCK_HISTORY;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STOCK_DEFAULTS_STOCK_INTRODUCTION_PERIOD;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.ibsplc.neoicargo.common.types.Pair;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;
import com.ibsplc.neoicargo.stock.vo.StockHolderParametersVO;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class SystemParameterHelperTest {

  @InjectMocks private SystemParameterHelper helper;

  @Mock private ParameterService parameterService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    helper = new SystemParameterHelper(parameterService);
  }

  @Test
  void shouldReturnSystemParameters() throws BusinessException {
    var period = 100;
    var flag = "flag";
    var systemKeys =
        Map.of(
            STOCK_DEFAULTS_ENABLE_STOCK_HISTORY,
            FLAG_YES,
            STOCK_DEFAULTS_CONFIRMATION_REQUIRED,
            FLAG_NO,
            STOCK_DEFAULTS_STOCK_INTRODUCTION_PERIOD,
            String.valueOf(period),
            STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM,
            flag);

    when(parameterService.getParameters(anyList(), any(ParameterType.class)))
        .thenReturn(Collections.emptyList());
    StockHolderParametersVO parametersVO = helper.getSystemParameters();
    assertFalse(parametersVO.isEnableStockHistory());
    assertFalse(parametersVO.isEnableConfirmationProcess());
    assertEquals(0, parametersVO.getStockIntroductionPeriod());
    assertNull(parametersVO.getAccountingFlag());
  }

  @Test
  void testGetSystemParameters_NoParametersSet() throws BusinessException {
    when(parameterService.getParameters(anyList(), any(ParameterType.class)))
        .thenReturn(Collections.emptyList());

    StockHolderParametersVO parametersVO = helper.getSystemParameters();

    assertFalse(parametersVO.isEnableStockHistory());
    assertFalse(parametersVO.isEnableConfirmationProcess());
    assertEquals(0, parametersVO.getStockIntroductionPeriod());
    assertNull(parametersVO.getAccountingFlag());
  }

  @Test
  void testGetSystemParameters_ParameterValuesNotFound() throws BusinessException {

    List<Pair> systemKeys = Arrays.asList(new Pair());

    when(parameterService.getParameters(anyList(), any(ParameterType.class)))
        .thenReturn(systemKeys);

    StockHolderParametersVO parametersVO = helper.getSystemParameters();

    assertFalse(parametersVO.isEnableStockHistory());
    assertFalse(parametersVO.isEnableConfirmationProcess());
    assertEquals(0, parametersVO.getStockIntroductionPeriod());
    assertNull(parametersVO.getAccountingFlag());
  }

  @Test
  public void testGetSystemParameters_WithBusinessException_ThrowsSystemException()
      throws BusinessException {
    when(parameterService.getParameters(anyList(), any(ParameterType.class)))
        .thenThrow(BusinessException.class);

    assertThrows(SystemException.class, () -> helper.getSystemParameters());
  }
}
