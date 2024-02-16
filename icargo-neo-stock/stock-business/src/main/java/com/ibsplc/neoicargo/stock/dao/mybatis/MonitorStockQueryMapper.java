package com.ibsplc.neoicargo.stock.dao.mybatis;

import com.ibsplc.neoicargo.stock.vo.MonitorStockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MonitorStockQueryMapper {
  MonitorStockVO findMonitorStockDetailsForStockHolder(
      @Param("stockFilterVO") StockFilterVO stockFilterVO,
      @Param("privilegeLevelValueList") List<String> privilegeLevelValueList);

  List<MonitorStockVO> findMonitorStock(
      @Param("stockFilterVO") StockFilterVO stockFilterVO,
      @Param("privilegeLevelValueList") List<String> privilegeLevelValueList,
      @Param("limit") int limit,
      @Param("offset") int offset);
}
