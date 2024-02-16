package com.ibsplc.neoicargo.stock.vo;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockVO;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class StockVOTest {

  @Test
  public void getBusinessId() {
    // Given
    var stockHolderVO = getMockStockHolderVO("AV", "HQ");
    var stockVO = getMockStockVO(stockHolderVO, 1132, "AWB", "S");

    // Then
    assertNotNull(stockVO.getBusinessId());
  }
}
