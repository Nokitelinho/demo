package com.ibsplc.neoicargo.stock.dao.impl;

import com.ibsplc.neoicargo.stock.dao.BlacklistStockDao;
import com.ibsplc.neoicargo.stock.dao.mybatis.BlacklistStockQueryMapper;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.TransitStockVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("blacklistStockDao")
@RequiredArgsConstructor
public class BlacklistStockDaoImpl implements BlacklistStockDao {

  private final BlacklistStockQueryMapper blacklistStockQueryMapper;

  @Override
  public List<TransitStockVO> findBlackListRangesFromTransit(BlacklistStockVO blacklistStockVO) {
    return blacklistStockQueryMapper.findBlackListRangesFromTransit(blacklistStockVO);
  }

  @Override
  public boolean alreadyBlackListed(BlacklistStockVO blacklistStockVO) {
    return blacklistStockQueryMapper.alreadyBlackListed(blacklistStockVO) != null;
  }
}
