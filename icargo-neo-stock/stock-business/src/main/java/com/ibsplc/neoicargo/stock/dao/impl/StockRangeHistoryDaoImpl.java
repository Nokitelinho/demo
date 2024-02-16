package com.ibsplc.neoicargo.stock.dao.impl;

import com.ibsplc.neoicargo.stock.dao.StockRangeHistoryDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.entity.StockRangeHistory;
import com.ibsplc.neoicargo.stock.dao.mybatis.StockQueryMapper;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.dao.repository.StockRangeHistoryRepository;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeHistoryFilterVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("stockRangeHistoryDao")
@RequiredArgsConstructor
public class StockRangeHistoryDaoImpl implements StockRangeHistoryDao {

  private final RangeMapper rangeMapper;
  private final StockQueryMapper stockQueryMapper;
  private final StockAgentRepository stockAgentRepository;
  private final StockRangeHistoryRepository stockRangeHistoryRepository;

  @Override
  @Transactional(readOnly = true)
  public List<RangeVO> findUsedRangesForMerge(RangeVO vo, String status) {
    var filter = rangeMapper.mapVoToFilter(vo);
    filter.setStatus(status);
    return stockQueryMapper.findUsedRangesForMerge(filter);
  }

  @Override
  @Transactional(readOnly = true)
  public StockAgent findByCompanyCodeAndAgentCode(String companyCode, String agentCode) {
    return stockAgentRepository.findByCompanyCodeAndAgentCode(companyCode, agentCode).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Long> findStockRangeHistoryList(
      List<StockRangeHistoryFilterVO> stockRangeHistoryVOs) {
    return stockQueryMapper.findStockRangeHistoryList(stockRangeHistoryVOs);
  }

  @Override
  public void save(StockRangeHistory stockRangeHistory) {
    stockRangeHistoryRepository.save(stockRangeHistory);
  }

  @Override
  public void deleteAllById(List<Long> ids) {
    stockRangeHistoryRepository.deleteAllById(ids);
  }
}
