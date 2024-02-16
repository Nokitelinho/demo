package com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_002;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.mapper.StockAllocationMapper;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAllocationFilterVO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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
class RangeValidatorTest {

  @InjectMocks private RangeValidator rangeValidator;

  @Mock private RangeDao rangeDao;
  @Spy private StockAllocationMapper mapper = Mappers.getMapper(StockAllocationMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateWithoutThrows() {
    // Given
    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setNumberOfDocuments(1100);

    var rangeVOTransfer = new RangeVO();
    rangeVOTransfer.setEndRange("1002000");
    rangeVOTransfer.setAsciiStartRange(1000100);

    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setTransferMode(StockConstant.MODE_TRANSFER);
    stockAllocationVO.setRanges(List.of(rangeVO));

    // When
    doReturn(List.of(rangeVOTransfer))
        .when(rangeDao)
        .findRangeForTransfer(any(StockAllocationFilterVO.class));

    // Then
    Assertions.assertDoesNotThrow(() -> rangeValidator.validate(stockAllocationVO));
  }

  @Test
  void shouldThrowStockNotFound() {
    // Given
    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setNumberOfDocuments(800);

    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setTransferMode(StockConstant.MODE_TRANSFER);
    stockAllocationVO.setRanges(List.of(rangeVO));

    // When
    doReturn(List.of()).when(rangeDao).findRangeForTransfer(any(StockAllocationFilterVO.class));

    // Then
    var thrown =
        assertThrows(BusinessException.class, () -> rangeValidator.validate(stockAllocationVO));
    assertEquals(NEO_STOCK_003.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldThrowWrongRangeException() {
    // Given
    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setNumberOfDocuments(0);

    var rangeVOTransfer = new RangeVO();
    rangeVOTransfer.setEndRange("1002000");
    rangeVOTransfer.setAsciiStartRange(1000100);

    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setTransferMode(StockConstant.MODE_TRANSFER);
    stockAllocationVO.setRanges(List.of(rangeVO));

    // When
    doReturn(List.of(rangeVOTransfer))
        .when(rangeDao)
        .findRangeForTransfer(any(StockAllocationFilterVO.class));

    // Then
    var thrown =
        assertThrows(BusinessException.class, () -> rangeValidator.validate(stockAllocationVO));
    assertEquals(NEO_STOCK_002.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }
}
