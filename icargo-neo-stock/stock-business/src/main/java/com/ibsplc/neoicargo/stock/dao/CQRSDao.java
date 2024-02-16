package com.ibsplc.neoicargo.stock.dao;

import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface CQRSDao {
  List<StockRangeHistoryVO> findStockRangeHistoryForPage(
      StockRangeFilterVO stockRangeFilterVO, PageRequest pageable);

  List<StockVO> findBlacklistRangesForBlackList(BlacklistStockVO blacklistStockVO);

  List<StockRangeHistoryVO> findAwbStockDetails(StockRangeFilterVO stockRangeFilterVO);

  List<StockRangeHistoryVO> findStockHistory(StockRangeFilterVO stockRangeFilterVO);

  List<StockRangeHistoryVO> findStockUtilisationDetailsStatusUnused(
      StockRangeFilterVO stockRangeFilterVO);

  List<StockRangeHistoryVO> findStockUtilisationDetailsStatusUsed(
      StockRangeFilterVO stockRangeFilterVO);

  List<StockRangeHistoryVO> findStockUtilisationDetailsStatusEmpty(
      StockRangeFilterVO stockRangeFilterVO);
}
