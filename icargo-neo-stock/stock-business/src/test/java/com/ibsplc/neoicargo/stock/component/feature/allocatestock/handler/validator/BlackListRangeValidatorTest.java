package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_005;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.filter.BlackListFilterVO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class BlackListRangeValidatorTest {

  @InjectMocks private BlackListRangeValidator validator;

  @Mock private RangeDao rangeDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateWithoutThrows() {
    // Given
    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");

    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setRanges(List.of(rangeVO));

    // When
    doReturn("").when(rangeDao).checkBlacklistRanges(any(BlackListFilterVO.class));

    // Then
    Assertions.assertDoesNotThrow(() -> validator.validate(stockAllocationVO));
  }

  @Test
  void shouldThrowBlacklistedRangeException() {
    // Given
    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1001100");

    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setRanges(List.of(rangeVO));

    // When
    doReturn("123").when(rangeDao).checkBlacklistRanges(any(BlackListFilterVO.class));

    // Then
    var thrown = assertThrows(BusinessException.class, () -> validator.validate(stockAllocationVO));
    assertEquals(NEO_STOCK_005.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }
}
