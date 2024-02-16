package com.ibsplc.neoicargo.stock.component.feature.savestockagentmappings;

import static com.ibsplc.neoicargo.stock.util.StockConstant.OPERATION_FLAG_DELETE;
import static com.ibsplc.neoicargo.stock.util.StockConstant.OPERATION_FLAG_INSERT;
import static com.ibsplc.neoicargo.stock.util.StockConstant.OPERATION_FLAG_UPDATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.component.feature.savestockagentmappings.persistor.SaveStockAgentMappingsPersistor;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.StockAgentMapper;
import com.ibsplc.neoicargo.stock.model.StockAgentModel;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import java.util.List;
import java.util.Optional;
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
public class SaveStockAgentMappingsFeatureTest {
  @InjectMocks private SaveStockAgentMappingsFeature saveStockAgentMappingsFeature;
  @Mock private StockAgentRepository stockAgentRepository;
  @Mock private SaveStockAgentMappingsPersistor agentMappingsPersistor;
  @Spy private final StockAgentMapper stockAgentMapper = Mappers.getMapper(StockAgentMapper.class);

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  private StockAgentModel populateStockAgentModel(String operationFlag) {
    var stockAgentModel = new StockAgentModel();
    stockAgentModel.setCompanyCode("AV");
    stockAgentModel.setAgentCode("AGNT");
    stockAgentModel.setOperationFlag(operationFlag);

    return stockAgentModel;
  }

  @Test
  void shouldDeleteStockAgent() {
    // Given
    var stockAgentModel = populateStockAgentModel(OPERATION_FLAG_DELETE);

    var models = List.of(stockAgentModel);
    var voList = stockAgentMapper.mapModelToVo(models);
    var stockAgent = new StockAgent();

    // When
    doReturn(Optional.of(stockAgent))
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(anyString(), anyString());
    doNothing().when(agentMappingsPersistor).removeStockAgent(stockAgent);

    // Then
    Assertions.assertDoesNotThrow(() -> saveStockAgentMappingsFeature.perform(voList));
    verify(agentMappingsPersistor).removeStockAgent(stockAgent);
  }

  @Test
  void shouldInsertStockAgent() {
    // Given
    var stockAgentModel = populateStockAgentModel(OPERATION_FLAG_INSERT);
    var models = List.of(stockAgentModel);
    var voList = stockAgentMapper.mapModelToVo(models);

    // When
    doReturn(Optional.empty())
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(anyString(), anyString());
    doNothing()
        .when(agentMappingsPersistor)
        .insertStockAgent(any(StockAgent.class), any(StockAgentVO.class));

    // Then
    Assertions.assertDoesNotThrow(() -> saveStockAgentMappingsFeature.perform(voList));
    verify(agentMappingsPersistor).insertStockAgent(any(), any());
  }

  @Test
  void shouldUpdateStockAgent() {
    // Given
    var stockAgentModel = populateStockAgentModel(OPERATION_FLAG_UPDATE);

    var models = List.of(stockAgentModel);
    var voList = stockAgentMapper.mapModelToVo(models);
    var stockAgent = new StockAgent();

    // When
    doReturn(Optional.of(stockAgent))
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(anyString(), anyString());
    doNothing()
        .when(agentMappingsPersistor)
        .updateStockAgent(any(StockAgent.class), any(StockAgentVO.class));

    // Then
    Assertions.assertDoesNotThrow(() -> saveStockAgentMappingsFeature.perform(voList));
    verify(agentMappingsPersistor).updateStockAgent(any(), any());
  }

  @Test
  void shouldThrownExceptionIfDuplicatedStockAgentFound() {
    // Given
    var stockAgentModel = populateStockAgentModel(OPERATION_FLAG_INSERT);
    var models = List.of(stockAgentModel);
    var voList = stockAgentMapper.mapModelToVo(models);
    var stockAgent = new StockAgent();
    stockAgent.setAgentCode("AGNT");

    // When
    doReturn(Optional.of(stockAgent))
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(anyString(), anyString());
    doNothing()
        .when(agentMappingsPersistor)
        .insertStockAgent(any(StockAgent.class), any(StockAgentVO.class));

    // Then
    Assertions.assertThrows(
        StockBusinessException.class, () -> saveStockAgentMappingsFeature.perform(voList));
    verify(agentMappingsPersistor).insertStockAgent(any(), any());
  }

  @Test
  void shouldReturnNullIfVOsIsNull() {
    // Then
    Assertions.assertDoesNotThrow(() -> saveStockAgentMappingsFeature.perform(null));
  }
}
