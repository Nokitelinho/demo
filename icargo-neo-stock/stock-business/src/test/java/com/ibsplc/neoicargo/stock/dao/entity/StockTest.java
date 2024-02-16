package com.ibsplc.neoicargo.stock.dao.entity;

import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockFullStockHolderEntity;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockStockEntity;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class StockTest {

  @Test
  void shouldReturnBusinessIdAsString() {
    // Given
    var stockHolder = getMockFullStockHolderEntity();
    var stock = getMockStockEntity(stockHolder, 1123, "AWB", "S");

    // Then
    assertNotNull(stock.getBusinessIdAsString());
  }

  @Test
  void shouldReturnIdAsString() {
    // Given
    var stockHolder = getMockFullStockHolderEntity();
    var stock = getMockStockEntity(stockHolder, 1123, "AWB", "S");

    // Then
    assertNotNull(stock.getIdAsString());
  }

  @Test
  void shouldReturnParentEntity() {
    // Given
    var stockHolder = getMockFullStockHolderEntity();
    var stock = getMockStockEntity(stockHolder, 1123, "AWB", "S");

    // Then
    assertNotNull(stock.getParentEntity());
  }

  @Test
  void shouldAddRange() {
    // Given
    var stockHolder = getMockFullStockHolderEntity();
    var stock = getMockStockEntity(stockHolder, 1123, "AWB", "S");

    // Then
    stock.addRanges(new Range());
    assertFalse(stock.getRanges().isEmpty());
  }
}
