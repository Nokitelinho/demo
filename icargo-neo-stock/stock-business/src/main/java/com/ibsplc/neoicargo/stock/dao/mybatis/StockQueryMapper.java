package com.ibsplc.neoicargo.stock.dao.mybatis;

import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderDetailsVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderLovVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderPriorityVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestFilterVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockDetailsFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderLovFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeHistoryFilterVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockQueryMapper {

  List<RangeVO> findUsedRangesForMerge(@Param("rangeFilterVO") RangeFilterVO rangeFilterVO);

  List<Long> findStockRangeHistoryList(
      @Param("stockRangeHistoryFilterVOs")
          List<StockRangeHistoryFilterVO> stockRangeHistoryFilterVOs);

  StockVO findStockForStockHolder(@Param("stockAllocationVO") StockAllocationVO stockAllocationVO);

  List<StockHolderPriorityVO> findPriorities(
      @Param("companyCode") String companyCode,
      @Param("stockHolderCodes") List<String> stockHolderCodes);

  List<StockHolderPriorityVO> findStockHolderTypes(@Param("companyCode") String companyCode);

  List<String> findStockHolderTypeCode(
      @Param("companyCode") String companyCode,
      @Param("stockHolderCode") String stockHolderCode,
      @Param("stockHolderType") String stockHolderType,
      @Param("documentType") String documentType,
      @Param("documentSubType") String documentSubType);

  List<StockRequestVO> findStockRequests(
      @Param("stockRequestFilterVO") StockRequestFilterVO stockRequestFilterVO,
      @Param("limit") int limit,
      @Param("offset") int offset);

  List<StockHolderLovVO> findStockHolderLov(
      @Param("stockHolderLovFilterVO") StockHolderLovFilterVO stockHolderLovFilterVO,
      @Param("limit") int limit,
      @Param("offset") int offset);

  List<StockAgentVO> findStockAgentMappings(
      @Param("stockAgentFilterVO") StockAgentFilterVO stockAgentFilterVO,
      @Param("limit") int limit,
      @Param("offset") int offset);

  StockRequestVO findStockRequestDetails(
      @Param("stockRequestFilterVO") StockRequestFilterVO stockRequestFilterVO);

  List<BlacklistStockVO> findBlacklistedStock(
      @Param("stockFilterVO") StockFilterVO stockFilterVO,
      @Param("limit") int limit,
      @Param("offset") int offset);

  StockRequestVO findDocumentDetails(
      @Param("companyCode") String companyCode,
      @Param("airlineIdentifier") int airlineIdentifier,
      @Param("documentNumber") long documentNumber);

  String findStockDetails(@Param("documentFilterVO") DocumentFilterVO documentFilterVO);

  String findAutoPopulateSubtype(@Param("documentFilterVO") DocumentFilterVO documentFilterVO);

  List<StockHolderDetailsVO> findStockHolders(
      @Param("stockHolderFilterVO") StockHolderFilterVO stockHolderFilterVO,
      @Param("limit") int limit,
      @Param("offset") int offset);

  StockDetailsVO findCustomerStockDetails(
      @Param("stockDetailsFilterVO") StockDetailsFilterVO stockDetailsFilterVO);

  List<RangeVO> findAWBStockDetailsForPrint(@Param("stockFilterVO") StockFilterVO stockFilterVO);

  List<String> findAgentsForStockHolder(
      @Param("companyCode") String companyCode, @Param("stockHolderCode") String stockHolderCode);

  RangeVO findStockRangeDetails(
      @Param("companyCode") String companyCode,
      @Param("airlineId") int airlineId,
      @Param("documentType") String documentType,
      @Param("mstDocNumber") long mstDocNumber);

  int checkApprover(
      @Param("companyCode") String companyCode, @Param("stockHolderCode") String stockHolderCode);

  List<StockAgentVO> getStockAgentMappings(
      @Param("stockAgentFilterVO") StockAgentFilterVO stockAgentFilterVO);

  RangeVO findRangeDelete(
      @Param("companyCode") String companyCode,
      @Param("documentType") String documentType,
      @Param("documentSubType") String documentSubType,
      @Param("startRange") Long startRange);
}
