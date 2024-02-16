package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_006;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class InvalidStockValidatorTest {

  private final InvalidStockValidator validator = new InvalidStockValidator();

  private StockAllocationVO stockAllocationVO;

  @BeforeEach
  public void setup() {
    stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setRanges(
        List.of(RangeVO.builder().startRange("1001000").endRange("1002000").build()));
  }

  @ParameterizedTest
  @MethodSource("provideRangeVO")
  void shouldValidateWithoutThrows(List<RangeVO> rangeVOList) {
    // Given
    var stockVO = new StockVO();
    stockVO.setRanges(rangeVOList);

    // Then
    Assertions.assertDoesNotThrow(() -> validator.validate(stockVO, stockAllocationVO));
  }

  private static Stream<List<RangeVO>> provideRangeVO() {
    return Stream.of(
        null, List.of(RangeVO.builder().asciiStartRange(1000500).asciiEndRange(1002500).build()));
  }

  @Test
  void shouldThrowRangeNotFoundException() {
    // Given
    var stockVO = new StockVO();
    stockVO.setRanges(
        List.of(RangeVO.builder().asciiStartRange(1000500).asciiEndRange(1001500).build()));

    // Then
    var thrown =
        assertThrows(
            StockBusinessException.class, () -> validator.validate(stockVO, stockAllocationVO));
    assertEquals(NEO_STOCK_006.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }
}
