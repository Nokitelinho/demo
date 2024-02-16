package com.ibsplc.neoicargo.stock.dao.mybatis;

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
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RangeQueryMapper {

  List<RangeVO> findRangeForTransfer(
      @Param("stockAllocationFilterVO") StockAllocationFilterVO stockAllocationFilterVO);

  Optional<String> checkBlacklistRanges(
      @Param("blackListFilterVO") BlackListFilterVO blackListFilterVO);

  List<StockVO> findBlacklistRangesForValidateStockVoiding(
      @Param("blacklistStockVO") BlacklistStockVO blacklistStockV,
      @Param("startRange") Long startRange,
      @Param("endRange") Long endRange);

  List<RangeVO> findDuplicateRanges(
      @Param("duplicateRangeFilterVO") DuplicateRangeFilterVO duplicateRangeFilterVO);

  List<RangeVO> findRangesForMerge(
      @Param("rangesForMergeFilterVO") RangesForMergeFilterVO rangesForMergeFilterVO);

  List<RangeVO> findRangesForViewRange(@Param("stockFilterVO") StockFilterVO stockFilterVO);

  List<RangeVO> findAllocatedRanges(@Param("stockFilterVO") StockFilterVO stockFilterVO);

  List<RangeVO> findRanges(
      @Param("rangeFilterVO") RangeFilterVO rangeFilterVO,
      @Param("startRange") Long startRange,
      @Param("endRange") Long endRange,
      @Param("numberOfDocuments") Integer numberOfDocuments);

  List<RangeVO> findAvailableRanges(
      @Param("stockFilterVO") StockFilterVO stockFilterVO,
      @Param("limit") int limit,
      @Param("offset") int offset);

  Optional<Integer> findTotalNoOfDocuments(@Param("stockFilterVO") StockFilterVO stockFilterVO);

  Optional<String> checkForBlacklistedDocument(
      @Param("companyCode") String companyCode,
      @Param("doctype") String doctype,
      @Param("documentNumber") Long documentNumber,
      @Param("airlineIdentifier") Long airlineIdentifier);

  List<RangeVO> findBlacklistRanges(
      @Param("companyCode") String companyCode,
      @Param("airlineId") int airlineId,
      @Param("docType") String docType,
      @Param("docSubType") String docSubType,
      @Param("startRange") long startRange,
      @Param("endRange") long endRange);

  List<RangeVO> findAvailableRangesForCustomer(
      @Param("stockDetailsFilterVO") StockDetailsFilterVO stockDetailsFilterVO);

  List<RangeVO> findAllocatedRangesForCustomer(
      @Param("stockDetailsFilterVO") StockDetailsFilterVO stockDetailsFilterVO);

  List<RangeVO> findUsedRangesForCustomer(
      @Param("stockDetailsFilterVO") StockDetailsFilterVO stockDetailsFilterVO);
}
