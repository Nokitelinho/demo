package com.ibsplc.neoicargo.stock.component.feature.findstockagentmappings;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.StockAgentMapper;
import com.ibsplc.neoicargo.stock.model.StockAgentModel;
import com.ibsplc.neoicargo.stock.util.PageableUtil;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findStockAgentMappingsFeature")
@RequiredArgsConstructor
public class FindStockAgentMappingsFeature {
  private final StockAgentMapper stockAgentMapper;
  private final StockDao stockDao;

  public Page<StockAgentModel> perform(StockAgentFilterVO stockAgentFilterVO) {
    var stockAgentVOs = stockDao.findStockAgentMappings(stockAgentFilterVO);
    var stockAgentModels = stockAgentMapper.mapVoToModel(stockAgentVOs);
    var totalRecordCount = PageableUtil.getTotalRecordCount(stockAgentVOs);

    return stockAgentMapper.mapVosToPageView(
        stockAgentModels,
        stockAgentFilterVO.getPageNumber(),
        stockAgentModels.size(),
        totalRecordCount,
        DEFAULT_PAGE_SIZE);
  }
}
