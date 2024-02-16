package com.ibsplc.neoicargo.stock.component.feature.getstockagentmappingsfeature;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockAgentNeoModelMapper;
import com.ibsplc.neoicargo.stock.model.StockAgentNeoModel;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("getStockAgentMappingsFeature")
@RequiredArgsConstructor
public class GetStockAgentMappingsFeature {

  private final StockAgentNeoModelMapper mapper;
  private final StockDao stockDao;

  public List<StockAgentNeoModel> perform(StockAgentFilterVO stockAgentFilterVO) {
    var stockAgentVOs = stockDao.getStockAgentMappings(stockAgentFilterVO);
    return mapper.mapVoToModel(stockAgentVOs);
  }
}
