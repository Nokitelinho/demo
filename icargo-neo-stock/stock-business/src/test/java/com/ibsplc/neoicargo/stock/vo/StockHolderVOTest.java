package com.ibsplc.neoicargo.stock.vo;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderVO;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class StockHolderVOTest {

  @Test
  public void getBusinessId() {
    // Given
    var stockHolderVO = getMockStockHolderVO("AV", "HQ");

    // Then
    assertNotNull(stockHolderVO.getBusinessId());
  }
}
