package com.ibsplc.neoicargo.stock.component.feature.savestockagentmappings.persistor;

import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.mapper.StockAgentMapper;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("saveStockAgentMappingsPersistor")
@RequiredArgsConstructor
public class SaveStockAgentMappingsPersistor {
  private final StockAgentRepository stockAgentRepository;
  private final StockAgentMapper stockAgentMapper;

  public void updateStockAgent(StockAgent stockAgent, StockAgentVO stockAgentVO) {
    if (Objects.nonNull(stockAgent)) {
      stockAgent.setStockHolderCode(stockAgentVO.getStockHolderCode());
      stockAgentRepository.save(stockAgent);
    }
  }

  public void insertStockAgent(StockAgent stockAgent, StockAgentVO stockAgentVO) {
    if (Objects.isNull(stockAgent)) {
      stockAgentRepository.save(stockAgentMapper.mapVoToEntity(stockAgentVO));
    }
  }

  public void removeStockAgent(StockAgent stockAgent) {
    if (Objects.nonNull(stockAgent)) {
      stockAgentRepository.delete(stockAgent);
    }
  }
}
