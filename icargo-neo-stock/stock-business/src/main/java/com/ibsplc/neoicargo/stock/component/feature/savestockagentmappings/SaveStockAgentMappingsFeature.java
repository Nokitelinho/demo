package com.ibsplc.neoicargo.stock.component.feature.savestockagentmappings;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_015;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.savestockagentmappings.persistor.SaveStockAgentMappingsPersistor;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component("saveStockAgentMappingsFeature")
@RequiredArgsConstructor
public class SaveStockAgentMappingsFeature {
  private final StockAgentRepository stockAgentRepository;
  private final SaveStockAgentMappingsPersistor agentMappingsPersistor;

  public void perform(Collection<StockAgentVO> stockAgentVOs) throws BusinessException {
    List<String> duplicateAgents = new ArrayList<>();

    if (CollectionUtils.isNotEmpty(stockAgentVOs)) {
      for (StockAgentVO stockAgentVO : stockAgentVOs) {
        var stockAgent = getStockAgent(stockAgentVO);
        switch (stockAgentVO.getOperationFlag()) {
          case OPERATION_FLAG_DELETE:
            agentMappingsPersistor.removeStockAgent(stockAgent);
            break;
          case OPERATION_FLAG_INSERT:
            if (Objects.nonNull(stockAgent)) {
              duplicateAgents.add(stockAgent.getAgentCode());
            }
            agentMappingsPersistor.insertStockAgent(stockAgent, stockAgentVO);
            break;
          case OPERATION_FLAG_UPDATE:
            agentMappingsPersistor.updateStockAgent(stockAgent, stockAgentVO);
            break;
        }
      }

      checkDuplicateAgents(duplicateAgents);
    }
  }

  private StockAgent getStockAgent(StockAgentVO stockAgentVO) {
    return stockAgentRepository
        .findByCompanyCodeAndAgentCode(stockAgentVO.getCompanyCode(), stockAgentVO.getAgentCode())
        .orElse(null);
  }

  private void checkDuplicateAgents(List<String> duplicateAgents) throws StockBusinessException {
    if (CollectionUtils.isNotEmpty(duplicateAgents)) {
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_015.getErrorCode(),
              NEO_STOCK_015.getErrorMessage(),
              ERROR,
              new String[] {StringUtils.join(duplicateAgents, ", ")}));
    }
  }
}
