package com.ibsplc.neoicargo.stock.dao;

import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.BlackListFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.DuplicateRangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangesForMergeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAllocationFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockDetailsFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.util.List;

public interface RangeDao {

  RangeVO find(RangeFilterVO rangeFilterVO);

  void remove(RangeVO rangeVo);

  void removeAll(List<RangeVO> rangeVOs);

  List<RangeVO> findRangeForTransfer(StockAllocationFilterVO stockAllocationFilterVO);

  String checkBlacklistRanges(BlackListFilterVO blackListFilterVO);

  List<RangeVO> findDuplicateRanges(DuplicateRangeFilterVO duplicateRangeFilterVO);

  List<RangeVO> findRangesForMerge(RangesForMergeFilterVO filterVO);

  List<RangeVO> findRangesForViewRange(StockFilterVO stockFilterVO);

  List<RangeVO> findAllocatedRanges(StockFilterVO stockFilterVO);

  List<RangeVO> findRanges(RangeFilterVO rangeFilterVO);

  int findTotalNoOfDocuments(StockFilterVO stockFilterVO);

  List<StockVO> findBlacklistRanges(BlacklistStockVO blacklistStockVO);

  List<RangeVO> findAvailableRangesForCustomer(StockDetailsFilterVO stockDetailsFilterVO);

  List<RangeVO> findAllocatedRangesForCustomer(StockDetailsFilterVO stockDetailsFilterVO);

  List<RangeVO> findUsedRangesForCustomer(StockDetailsFilterVO stockDetailsFilterVO);
}
