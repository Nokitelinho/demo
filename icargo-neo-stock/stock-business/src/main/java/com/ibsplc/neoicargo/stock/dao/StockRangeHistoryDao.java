package com.ibsplc.neoicargo.stock.dao;

import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.entity.StockRangeHistory;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeHistoryFilterVO;
import java.util.List;

public interface StockRangeHistoryDao {

  List<RangeVO> findUsedRangesForMerge(RangeVO vo, String status);

  StockAgent findByCompanyCodeAndAgentCode(String companyCode, String agentCode);

  List<Long> findStockRangeHistoryList(List<StockRangeHistoryFilterVO> stockRangeHistoryVOs);

  void save(StockRangeHistory stockRangeHistory);

  void deleteAllById(List<Long> ids);
}
