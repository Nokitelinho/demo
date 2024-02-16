package com.ibsplc.neoicargo.stock.dao.impl;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toIntegerNullable;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLongNullable;

import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.dao.mybatis.RangeQueryMapper;
import com.ibsplc.neoicargo.stock.dao.repository.RangeRepository;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.util.CalculationUtil;
import com.ibsplc.neoicargo.stock.util.LoggerUtil;
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
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("rangeDao")
@RequiredArgsConstructor
public class RangeDaoImpl implements RangeDao {

  private final RangeRepository repository;
  private final RangeQueryMapper queryMapper;
  private final RangeMapper rangeMapper;

  @Override
  @Transactional(readOnly = true)
  public RangeVO find(RangeFilterVO rangeFilterVO) {
    return repository.findRange(rangeFilterVO).map(rangeMapper::mapEntityToVo).orElse(null);
  }

  @Override
  public void remove(RangeVO rangeVo) {
    repository.deleteById(rangeVo.getRangeSerialNumber());
    repository.flush();
  }

  @Override
  public void removeAll(List<RangeVO> rangeVOs) {
    repository.deleteAllByIdInBatch(
        rangeVOs.stream()
            .filter(Objects::nonNull)
            .map(RangeVO::getRangeSerialNumber)
            .collect(Collectors.toList()));
    log.info("Removed ranges");
    LoggerUtil.logRanges(rangeVOs);
  }

  @Override
  @Transactional(readOnly = true)
  public List<RangeVO> findRangeForTransfer(StockAllocationFilterVO stockAllocationFilterVO) {
    return queryMapper.findRangeForTransfer(stockAllocationFilterVO);
  }

  @Override
  @Transactional(readOnly = true)
  public String checkBlacklistRanges(BlackListFilterVO blackListFilterVO) {
    return queryMapper.checkBlacklistRanges(blackListFilterVO).orElse(StringUtils.EMPTY);
  }

  @Override
  @Transactional(readOnly = true)
  public List<RangeVO> findDuplicateRanges(DuplicateRangeFilterVO duplicateRangeFilterVO) {
    return queryMapper.findDuplicateRanges(duplicateRangeFilterVO);
  }

  @Override
  @Transactional(readOnly = true)
  public List<RangeVO> findRangesForMerge(RangesForMergeFilterVO filterVO) {
    return queryMapper.findRangesForMerge(filterVO);
  }

  @Override
  @Transactional(readOnly = true)
  public List<RangeVO> findRangesForViewRange(StockFilterVO stockFilterVO) {
    return queryMapper.findRangesForViewRange(stockFilterVO);
  }

  @Override
  @Transactional(readOnly = true)
  public List<RangeVO> findAllocatedRanges(StockFilterVO stockFilterVO) {
    return queryMapper.findAllocatedRanges(stockFilterVO);
  }

  @Override
  public List<RangeVO> findRanges(RangeFilterVO rangeFilterVO) {
    var startRange = toLongNullable(rangeFilterVO.getStartRange());
    var endRange = toLongNullable(rangeFilterVO.getEndRange());
    var numberOfDocuments = toIntegerNullable(rangeFilterVO.getNumberOfDocuments());

    return queryMapper.findRanges(rangeFilterVO, startRange, endRange, numberOfDocuments);
  }

  @Override
  public int findTotalNoOfDocuments(StockFilterVO stockFilterVO) {
    return queryMapper.findTotalNoOfDocuments(stockFilterVO).orElse(0);
  }

  @Override
  public List<StockVO> findBlacklistRanges(BlacklistStockVO blacklistStockVO) {
    var startRange = CalculationUtil.toLong(blacklistStockVO.getRangeFrom());
    var endRange = CalculationUtil.toLong(blacklistStockVO.getRangeTo());
    return queryMapper.findBlacklistRangesForValidateStockVoiding(
        blacklistStockVO, startRange, endRange);
  }

  @Override
  public List<RangeVO> findAvailableRangesForCustomer(StockDetailsFilterVO stockDetailsFilterVO) {
    return queryMapper.findAvailableRangesForCustomer(stockDetailsFilterVO);
  }

  @Override
  public List<RangeVO> findAllocatedRangesForCustomer(StockDetailsFilterVO stockDetailsFilterVO) {
    return queryMapper.findAllocatedRangesForCustomer(stockDetailsFilterVO);
  }

  @Override
  public List<RangeVO> findUsedRangesForCustomer(StockDetailsFilterVO stockDetailsFilterVO) {
    return queryMapper.findUsedRangesForCustomer(stockDetailsFilterVO);
  }
}
