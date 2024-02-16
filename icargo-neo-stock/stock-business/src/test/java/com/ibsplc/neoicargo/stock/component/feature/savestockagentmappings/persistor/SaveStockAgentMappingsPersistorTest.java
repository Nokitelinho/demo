package com.ibsplc.neoicargo.stock.component.feature.savestockagentmappings.persistor;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.mapper.StockAgentMapper;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
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
public class SaveStockAgentMappingsPersistorTest {
  @InjectMocks private SaveStockAgentMappingsPersistor persistor;
  @Mock private StockAgentRepository stockAgentRepository;
  @Spy private final StockAgentMapper stockAgentMapper = Mappers.getMapper(StockAgentMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldUpdateStockAgent() {
    // Given
    var stockAgent = new StockAgent();
    stockAgent.setLastUpdatedTime(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
    var stockAgentVO = stockAgentMapper.mapEntityToVo(stockAgent);

    // When
    doReturn(stockAgent).when(stockAgentRepository).save(stockAgent);

    // Then
    Assertions.assertDoesNotThrow(() -> persistor.updateStockAgent(stockAgent, stockAgentVO));
  }

  @Test
  void shouldNotUpdateStockAgent() {
    // Given
    var stockAgent = new StockAgent();
    stockAgent.setLastUpdatedTime(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
    var stockAgentVO = stockAgentMapper.mapEntityToVo(stockAgent);

    // Then
    Assertions.assertDoesNotThrow(() -> persistor.updateStockAgent(null, stockAgentVO));
  }

  @Test
  void shouldInsertStockAgent() {
    // Given
    var stockAgent = new StockAgent();
    stockAgent.setLastUpdatedTime(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
    var stockAgentVO = stockAgentMapper.mapEntityToVo(stockAgent);

    // When
    doReturn(stockAgent).when(stockAgentRepository).save(stockAgent);

    // Then
    Assertions.assertDoesNotThrow(() -> persistor.insertStockAgent(null, stockAgentVO));
  }

  @Test
  void shouldNotInsertStockAgent() {
    // Given
    var stockAgent = new StockAgent();
    stockAgent.setLastUpdatedTime(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
    var stockAgentVO = stockAgentMapper.mapEntityToVo(stockAgent);

    // When
    doReturn(stockAgent).when(stockAgentRepository).save(stockAgent);

    // Then
    Assertions.assertDoesNotThrow(() -> persistor.insertStockAgent(stockAgent, stockAgentVO));
  }

  @Test
  void shouldRemoveStockAgent() {
    // Given
    var stockAgent = new StockAgent();

    // When
    doNothing().when(stockAgentRepository).delete(stockAgent);

    // Then
    Assertions.assertDoesNotThrow(() -> persistor.removeStockAgent(stockAgent));
  }

  @Test
  void shouldNotRemoveStockAgent() {

    // Then
    Assertions.assertDoesNotThrow(() -> persistor.removeStockAgent(null));
  }
}
