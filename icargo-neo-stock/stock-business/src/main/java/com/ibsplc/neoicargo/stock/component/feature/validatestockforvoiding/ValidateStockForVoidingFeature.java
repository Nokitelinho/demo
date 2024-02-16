package com.ibsplc.neoicargo.stock.component.feature.validatestockforvoiding;

import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.mapper.BlackListStockMapper;
import com.ibsplc.neoicargo.stock.model.BlacklistStockModel;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component("validateStockForVoidingFeature")
@RequiredArgsConstructor
public class ValidateStockForVoidingFeature {
  private final BlackListStockMapper blackListStockMapper;
  private final RangeDao rangeDao;
  private final StockDao stockDao;

  public BlacklistStockModel perform(BlacklistStockVO blacklistStockVO) {
    var stockVOs = rangeDao.findBlacklistRanges(blacklistStockVO);
    if (CollectionUtils.isNotEmpty(stockVOs)) {
      var stockVO = getStockVO(stockVOs);
      blacklistStockVO.setStockVO(stockVO);
      blacklistStockVO.setAgentCode(getAgentCode(blacklistStockVO, stockVO));
    }

    return blackListStockMapper.mapVoToModel(blacklistStockVO);
  }

  private StockVO getStockVO(List<StockVO> stockVOs) {
    return stockVOs.stream().findFirst().orElse(new StockVO());
  }

  private String getAgentCode(BlacklistStockVO blacklistStockVO, StockVO stockVO) {
    var stockAgentFilterVO = getStockAgentFilterVO(blacklistStockVO, stockVO);
    var stockAgents = stockDao.findStockAgentMappings(stockAgentFilterVO);

    return stockAgents.stream().findFirst().map(StockAgentVO::getAgentCode).orElse(null);
  }

  private StockAgentFilterVO getStockAgentFilterVO(
      BlacklistStockVO blacklistStockVO, StockVO stockVO) {
    var filterVO = new StockAgentFilterVO();
    filterVO.setCompanyCode(blacklistStockVO.getCompanyCode());
    filterVO.setStockHolderCode(stockVO.getStockHolderCode());
    filterVO.setPageNumber(1);
    return filterVO;
  }
}
