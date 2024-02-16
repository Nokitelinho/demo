package com.ibsplc.neoicargo.stock.dao;

import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.TransitStockVO;
import java.util.List;

public interface BlacklistStockDao {

  boolean alreadyBlackListed(BlacklistStockVO blacklistStockVO);

  List<TransitStockVO> findBlackListRangesFromTransit(BlacklistStockVO blacklistStockVO);
}
