package com.ibsplc.neoicargo.stock.dao.mybatis;

import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.TransitStockVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BlacklistStockQueryMapper {

  String alreadyBlackListed(@Param("blacklistStockVO") BlacklistStockVO blacklistStockVO);

  List<TransitStockVO> findBlackListRangesFromTransit(
      @Param("blacklistStockVO") BlacklistStockVO blacklistStockVO);
}
