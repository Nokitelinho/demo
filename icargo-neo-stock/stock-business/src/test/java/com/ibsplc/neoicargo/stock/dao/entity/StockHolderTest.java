package com.ibsplc.neoicargo.stock.dao.entity;

import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockFullStockHolderEntity;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class StockHolderTest {

  @Test
  void shouldReturnBusinessIdAsString() {
    // Given
    var stockHolder = getMockFullStockHolderEntity();

    // Then
    assertNotNull(stockHolder.getBusinessIdAsString());
  }

  @Test
  void shouldReturnIdAsString() {
    // Given
    var stockHolder = getMockFullStockHolderEntity();

    // Then
    assertNotNull(stockHolder.getIdAsString());
  }
}
