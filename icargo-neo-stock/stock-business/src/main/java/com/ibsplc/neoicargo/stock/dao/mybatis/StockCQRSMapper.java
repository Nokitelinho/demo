package com.ibsplc.neoicargo.stock.dao.mybatis;

import com.ibsplc.neoicargo.framework.cqrs.config.CqrsMybatisMapper;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@CqrsMybatisMapper
public interface StockCQRSMapper {

  List<StockRangeHistoryVO> findStockUtilisationDetails(
      @Param("stockRangeFilterVO") StockRangeFilterVO stockRangeFilterVO,
      @Param("status") String status,
      @Param("startRange") Long startRange,
      @Param("endRange") Long endRange,
      @Param("limit") int limit,
      @Param("offset") int offset);

  List<StockRangeHistoryVO> findStockUtilisationDetailsUsed(
      @Param("stockRangeFilterVO") StockRangeFilterVO stockRangeFilterVO,
      @Param("status") String status,
      @Param("startRange") Long startRange,
      @Param("endRange") Long endRange,
      @Param("limit") int limit,
      @Param("offset") int offset);

  List<StockRangeHistoryVO> findStockRangeHistory(
      @Param("stockRangeFilterVO") StockRangeFilterVO stockRangeFilterVO,
      @Param("status") String status,
      @Param("startRange") Long startRange,
      @Param("endRange") Long endRange,
      @Param("limit") int limit,
      @Param("offset") int offset);

  List<StockRangeHistoryVO> findAwbStockDetails(
      @Param("stockRangeFilterVO") StockRangeFilterVO stockRangeFilterVO);

  List<StockRangeHistoryVO> findStockHistory(
      @Param("stockRangeFilterVO") StockRangeFilterVO stockRangeFilterVO,
      @Param("status") String status);

  List<StockRangeHistoryVO> findStockUtilisationDetailsStatusUnused(
      @Param("stockRangeFilterVO") StockRangeFilterVO stockRangeFilterVO);

  List<StockRangeHistoryVO> findStockUtilisationDetailsStatusUsed(
      @Param("stockRangeFilterVO") StockRangeFilterVO stockRangeFilterVO);

  List<StockRangeHistoryVO> findStockUtilisationDetailsStatusEmpty(
      @Param("stockRangeFilterVO") StockRangeFilterVO stockRangeFilterVO);

  List<RangeVO> findBlacklistRangesForBlacklist(
      @Param("companyCode") String companyCode,
      @Param("airlineId") int airlineId,
      @Param("docType") String docType,
      @Param("docSubType") String docSubType,
      @Param("startRange") long startRange,
      @Param("endRange") long endRange);
}
