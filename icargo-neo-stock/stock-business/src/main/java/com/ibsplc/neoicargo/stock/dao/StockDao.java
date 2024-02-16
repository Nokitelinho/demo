package com.ibsplc.neoicargo.stock.dao;

import com.ibsplc.neoicargo.stock.model.StockRequestModel;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.MonitorStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderDetailsVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderLovVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderPriorityVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestFilterVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockDetailsFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderLovFilterVO;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface StockDao {

  StockHolderVO findStockHolderDetails(String companyCode, String stockHolderCode);

  StockVO findStockWithRanges(
      String companyCode,
      String stockHolderCode,
      int airlineIdentifier,
      String documentType,
      String documentSubType);

  StockVO findStockForStockHolder(StockAllocationVO stockAllocationVO);

  String findApproverCode(
      String companyCode,
      String stockHolderCode,
      // Integer airlineId,
      String docType,
      String docSubType);

  MonitorStockVO findMonitoringStockHolderDetails(StockFilterVO stockFilterVO);

  void addRanges(StockVO stockVO, List<RangeVO> rangeVos);

  List<StockHolderPriorityVO> findPriorities(String companyCode, List<String> stockHolderCodes);

  List<StockHolderPriorityVO> findStockHolderTypes(String companyCode);

  List<String> findStockHolderTypeCode(StockRequestVO stockRequestVO);

  List<MonitorStockVO> findMonitorStock(StockFilterVO stockFilterVO);

  List<StockRequestVO> findStockRequests(
      StockRequestFilterVO stockRequestFilterVO, PageRequest pageable);

  List<StockHolderLovVO> findStockHolderLov(StockHolderLovFilterVO stockHolderLovFilterVO);

  List<RangeVO> findAvailableRanges(StockFilterVO stockFilterVO, PageRequest pageable);

  String findAutoProcessingQuantityAvailable(
      String companyCode, String stockHolderCode, String documentType, String documentSubType);

  boolean checkStock(String companyCode, String stockHolderCode, String docType, String docSubType);

  List<StockAgentVO> findStockAgentMappings(StockAgentFilterVO stockAgentFilterVO);

  StockRequestModel findStockRequestDetails(StockRequestFilterVO stockRequestFilterVO);

  List<BlacklistStockVO> findBlacklistedStock(StockFilterVO stockFilterVO, PageRequest pageable);

  StockRequestVO findDocumentDetails(
      String companyCode, int airlineIdentifier, long documentNumber);

  Boolean checkForBlacklistedDocument(
      String companyCode, String doctype, String documentNumber, Long airlineIdentifier);

  List<StockVO> findBlacklistRanges(BlacklistStockVO blacklistStockVO);

  Boolean isStockDetailsExists(DocumentFilterVO vo);

  StockAgentVO findStockAgent(String companyCode, String agentCode);

  String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO);

  List<StockHolderDetailsVO> findStockHolders(StockHolderFilterVO stockHolderFilterVO);

  StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO);

  List<RangeVO> findAWBStockDetailsForPrint(StockFilterVO stockFilterVO);

  List<String> findAgentsForStockHolder(String companyCode, String stockHolderCode);

  RangeVO findStockRangeDetails(
      String companyCode, int airlineId, String documentType, long mstDocNumber);

  int checkApprover(String companyCode, String stockHolderCode);

  void remove(StockHolderVO stockHolderVO);

  List<StockAgentVO> getStockAgentMappings(StockAgentFilterVO stockAgentFilterVO);

  RangeVO findRangeDelete(
      String companyCode, String documentType, String documentSubType, Long startRange);
}
