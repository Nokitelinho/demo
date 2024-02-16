package com.ibsplc.neoicargo.stock.dao.impl;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.stockHolderLovFilterReplace;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.dao.entity.Range;
import com.ibsplc.neoicargo.stock.dao.mybatis.MonitorStockQueryMapper;
import com.ibsplc.neoicargo.stock.dao.mybatis.RangeQueryMapper;
import com.ibsplc.neoicargo.stock.dao.mybatis.StockQueryMapper;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.dao.repository.StockRepository;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.mapper.StockAgentMapper;
import com.ibsplc.neoicargo.stock.mapper.StockHolderMapper;
import com.ibsplc.neoicargo.stock.mapper.StockMapper;
import com.ibsplc.neoicargo.stock.mapper.StockRequestMapper;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.model.StockRequestModel;
import com.ibsplc.neoicargo.stock.util.PageableUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("stockDao")
@RequiredArgsConstructor
public class StockDaoImpl implements StockDao {

  private final StockHolderMapper stockHolderMapper;
  private final StockHolderRepository stockHolderRepository;
  private final StockRepository stockRepository;
  private final StockQueryMapper stockQueryMapper;
  private final RangeQueryMapper rangeQueryMapper;
  private final RangeMapper rangeMapper;
  private final StockMapper stockMapper;
  private final StockRequestMapper stockRequestMapper;
  private final MonitorStockQueryMapper monitorStockQueryMapper;
  private final StockAgentMapper stockAgentMapper;
  private final StockAgentRepository stockAgentRepository;

  @Override
  @Transactional(readOnly = true)
  public StockHolderVO findStockHolderDetails(String companyCode, String stockHolderCode) {
    return stockHolderRepository
        .findByCompanyCodeAndStockHolderCode(companyCode, stockHolderCode)
        .map(stockHolderMapper::mapEntityToVo)
        .orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public StockVO findStockWithRanges(
      String companyCode,
      String stockHolderCode,
      int airlineIdentifier,
      String documentType,
      String documentSubType) {
    return stockRepository
        .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
            companyCode, stockHolderCode, airlineIdentifier, documentType, documentSubType)
        .map(stockMapper::mapEntityToVoWithRanges)
        .orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public String findApproverCode(
      String companyCode,
      String stockHolderCode,
      // Integer airlineId,
      String docType,
      String docSubType) {
    var approverCodes =
        stockRepository.findStockApproverCode(companyCode, stockHolderCode, docType, docSubType);
    return CollectionUtils.isNotEmpty(approverCodes) ? approverCodes.get(0) : null;
  }

  @Override
  @Transactional(readOnly = true)
  public StockVO findStockForStockHolder(StockAllocationVO stockAllocationVO) {
    return stockQueryMapper.findStockForStockHolder(stockAllocationVO);
  }

  @Override
  public void addRanges(StockVO stockVO, List<RangeVO> rangeVos) {
    stockRepository
        .findById(stockVO.getStockSerialNumber())
        .ifPresent(
            stock -> {
              stock.addRanges(
                  rangeVos.stream().map(rangeMapper::mapVoToEntity).toArray(Range[]::new));
              stockRepository.save(stock);
            });
  }

  @Override
  public MonitorStockVO findMonitoringStockHolderDetails(StockFilterVO stockFilterVO) {
    var privilegeLevelValueList = BaseMapper.toArray(stockFilterVO.getPrivilegeLevelValue());

    return monitorStockQueryMapper.findMonitorStockDetailsForStockHolder(
        stockFilterVO, privilegeLevelValueList);
  }

  @Override
  public List<StockHolderPriorityVO> findStockHolderTypes(String companyCode) {
    return stockQueryMapper.findStockHolderTypes(companyCode);
  }

  @Override
  public List<StockHolderPriorityVO> findPriorities(
      String companyCode, List<String> stockHolderCodes) {
    return stockQueryMapper.findPriorities(companyCode, stockHolderCodes);
  }

  @Override
  public List<String> findStockHolderTypeCode(StockRequestVO stockRequestVO) {
    return stockQueryMapper.findStockHolderTypeCode(
        stockRequestVO.getCompanyCode(),
        stockRequestVO.getStockHolderCode(),
        stockRequestVO.getStockHolderType(),
        stockRequestVO.getDocumentType(),
        stockRequestVO.getDocumentSubType());
  }

  @Override
  public List<MonitorStockVO> findMonitorStock(StockFilterVO stockFilterVO) {
    var privilegeLevelValueList = BaseMapper.toArray(stockFilterVO.getPrivilegeLevelValue());
    var offset = PageableUtil.getPageOffset(stockFilterVO.getPageNumber(), DEFAULT_PAGE_SIZE);

    return monitorStockQueryMapper.findMonitorStock(
        stockFilterVO, privilegeLevelValueList, DEFAULT_PAGE_SIZE, offset);
  }

  @Override
  public List<StockRequestVO> findStockRequests(
      StockRequestFilterVO stockRequestFilterVO, PageRequest pageable) {
    final var limit = pageable.getPageSize();
    final var offset = pageable.getPageNumber() * pageable.getPageSize();

    return stockQueryMapper.findStockRequests(stockRequestFilterVO, limit, offset);
  }

  @Override
  public List<StockHolderLovVO> findStockHolderLov(StockHolderLovFilterVO filterVO) {
    var offset = PageableUtil.getPageOffset(filterVO.getPageNumber(), DEFAULT_PAGE_SIZE);
    filterVO.setDocumentType(stockHolderLovFilterReplace(filterVO.getDocumentType()));
    filterVO.setDocumentSubType(stockHolderLovFilterReplace(filterVO.getDocumentSubType()));
    filterVO.setStockHolderType(stockHolderLovFilterReplace(filterVO.getStockHolderType()));
    filterVO.setStockHolderCode(stockHolderLovFilterReplace(filterVO.getStockHolderCode()));
    filterVO.setStockHolderName(stockHolderLovFilterReplace(filterVO.getStockHolderName()));
    filterVO.setApproverCode(stockHolderLovFilterReplace(filterVO.getApproverCode()));

    return stockQueryMapper.findStockHolderLov(filterVO, DEFAULT_PAGE_SIZE, offset);
  }

  @Override
  public List<RangeVO> findAvailableRanges(StockFilterVO stockFilterVO, PageRequest pageable) {
    final var limit = pageable.getPageSize();
    final var offset = pageable.getPageNumber() * pageable.getPageSize();

    return rangeQueryMapper.findAvailableRanges(stockFilterVO, limit, offset);
  }

  @Override
  public String findAutoProcessingQuantityAvailable(
      String companyCode, String stockHolderCode, String documentType, String documentSubType) {
    return stockRepository.findAutoProcessingQuantityAvailable(
        companyCode, stockHolderCode, documentType, documentSubType);
  }

  @Override
  public boolean checkStock(
      String companyCode, String stockHolderCode, String docType, String docSubType) {
    return stockRepository.existsByCompanyCodeAndStockHolderCodeAndDocumentTypeAndDocumentSubType(
        companyCode, stockHolderCode, docType, docSubType);
  }

  @Override
  public List<StockAgentVO> findStockAgentMappings(StockAgentFilterVO stockAgentFilterVO) {
    var offset = PageableUtil.getPageOffset(stockAgentFilterVO.getPageNumber(), DEFAULT_PAGE_SIZE);
    return stockQueryMapper.findStockAgentMappings(stockAgentFilterVO, DEFAULT_PAGE_SIZE, offset);
  }

  @Override
  public StockRequestModel findStockRequestDetails(StockRequestFilterVO stockRequestFilterVO) {
    var stockRequestVO =
        Optional.ofNullable(stockQueryMapper.findStockRequestDetails(stockRequestFilterVO))
            .orElse(new StockRequestVO());
    return stockRequestMapper.mapVoToModel(stockRequestVO);
  }

  @Override
  public List<BlacklistStockVO> findBlacklistedStock(
      StockFilterVO stockFilterVO, PageRequest pageable) {
    final var limit = pageable.getPageSize();
    final var offset = pageable.getPageNumber() * pageable.getPageSize();

    return stockQueryMapper.findBlacklistedStock(stockFilterVO, limit, offset);
  }

  @Override
  public StockRequestVO findDocumentDetails(
      String companyCode, int airlineIdentifier, long documentNumber) {
    return stockQueryMapper.findDocumentDetails(companyCode, airlineIdentifier, documentNumber);
  }

  @Override
  public Boolean checkForBlacklistedDocument(
      String companyCode, String doctype, String documentNumber, Long airlineIdentifier) {
    return rangeQueryMapper
        .checkForBlacklistedDocument(
            companyCode, doctype, toLong(documentNumber), airlineIdentifier)
        .isPresent();
  }

  @Override
  public List<StockVO> findBlacklistRanges(BlacklistStockVO blacklistStockVO) {
    return rangeQueryMapper
        .findBlacklistRanges(
            blacklistStockVO.getCompanyCode(),
            blacklistStockVO.getAirlineIdentifier(),
            blacklistStockVO.getDocumentType(),
            blacklistStockVO.getDocumentSubType(),
            toLong(blacklistStockVO.getRangeFrom()),
            toLong(blacklistStockVO.getRangeTo()))
        .stream()
        .map(
            rangeVO -> {
              var ranges = new ArrayList<RangeVO>();
              ranges.add(rangeVO);
              var stockVO = new StockVO();
              stockVO.setCompanyCode(rangeVO.getCompanyCode());
              stockVO.setStockHolderCode(rangeVO.getStockHolderCode());
              stockVO.setRanges(ranges);
              return stockVO;
            })
        .collect(Collectors.toList());
  }

  @Override
  public Boolean isStockDetailsExists(DocumentFilterVO vo) {
    return stockQueryMapper.findStockDetails(vo) != null;
  }

  @Override
  public StockAgentVO findStockAgent(String companyCode, String agentCode) {
    return stockAgentRepository
        .findByCompanyCodeAndAgentCode(companyCode, agentCode)
        .map(stockAgentMapper::mapEntityToVo)
        .orElse(null);
  }

  @Override
  public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO) {
    return stockQueryMapper.findAutoPopulateSubtype(documentFilterVO);
  }

  @Override
  public List<StockHolderDetailsVO> findStockHolders(StockHolderFilterVO stockHolderFilterVO) {
    var offset = PageableUtil.getPageOffset(stockHolderFilterVO.getPageNumber(), DEFAULT_PAGE_SIZE);
    return stockQueryMapper.findStockHolders(stockHolderFilterVO, DEFAULT_PAGE_SIZE, offset);
  }

  @Override
  public StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO) {
    return stockQueryMapper.findCustomerStockDetails(stockDetailsFilterVO);
  }

  @Override
  public List<RangeVO> findAWBStockDetailsForPrint(StockFilterVO stockFilterVO) {
    return stockQueryMapper.findAWBStockDetailsForPrint(stockFilterVO);
  }

  @Override
  public List<String> findAgentsForStockHolder(String companyCode, String stockHolderCode) {
    return stockQueryMapper.findAgentsForStockHolder(companyCode, stockHolderCode);
  }

  @Override
  public RangeVO findStockRangeDetails(
      String companyCode, int airlineId, String documentType, long mstDocNumber) {
    return stockQueryMapper.findStockRangeDetails(
        companyCode, airlineId, documentType, mstDocNumber);
  }

  @Override
  public int checkApprover(String companyCode, String stockHolderCode) {
    return stockQueryMapper.checkApprover(companyCode, stockHolderCode);
  }

  @Override
  public void remove(StockHolderVO stockHolderVO) {
    stockHolderRepository.deleteById(stockHolderVO.getStockHolderSerialNumber());
    stockHolderRepository.flush();
  }

  @Override
  public List<StockAgentVO> getStockAgentMappings(StockAgentFilterVO stockAgentFilterVO) {
    return stockQueryMapper.getStockAgentMappings(stockAgentFilterVO);
  }

  @Override
  public RangeVO findRangeDelete(
      String companyCode, String documentType, String documentSubType, Long startRange) {
    return stockQueryMapper.findRangeDelete(companyCode, documentType, documentSubType, startRange);
  }
}
