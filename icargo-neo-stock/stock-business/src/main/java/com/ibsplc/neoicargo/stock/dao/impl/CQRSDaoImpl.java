package com.ibsplc.neoicargo.stock.dao.impl;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_UNUSED;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_RECEIVE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.stock.dao.CQRSDao;
import com.ibsplc.neoicargo.stock.dao.mybatis.StockCQRSMapper;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.util.CalculationUtil;
import com.ibsplc.neoicargo.stock.util.StockConstant;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@Component("cqrsDao")
@RequiredArgsConstructor
public class CQRSDaoImpl implements CQRSDao {
  private final StockCQRSMapper stockCQRSMapper;
  private final LocalDate localDate;

  @Override
  public List<StockRangeHistoryVO> findAwbStockDetails(StockRangeFilterVO stockRangeFilterVO) {
    return stockCQRSMapper.findAwbStockDetails(stockRangeFilterVO);
  }

  public List<StockRangeHistoryVO> findStockHistory(StockRangeFilterVO stockRangeFilterVO) {
    var status = getStatusForFindStockRangeHistory(stockRangeFilterVO.getStatus());
    stockRangeFilterVO.setStartDate(buildStartTimestamp(stockRangeFilterVO.getStartDate()));
    stockRangeFilterVO.setEndDate(buildEndTimestamp(stockRangeFilterVO.getEndDate()));
    return stockCQRSMapper.findStockHistory(stockRangeFilterVO, status);
  }

  @Override
  public List<StockRangeHistoryVO> findStockUtilisationDetailsStatusUnused(
      StockRangeFilterVO stockRangeFilterVO) {
    return stockCQRSMapper.findStockUtilisationDetailsStatusUnused(stockRangeFilterVO);
  }

  @Override
  public List<StockVO> findBlacklistRangesForBlackList(BlacklistStockVO blacklistStockVO) {
    return stockCQRSMapper
        .findBlacklistRangesForBlacklist(
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
  public List<StockRangeHistoryVO> findStockUtilisationDetailsStatusUsed(
      StockRangeFilterVO stockRangeFilterVO) {
    stockRangeFilterVO.setStartDate(buildStartTimestamp(stockRangeFilterVO.getStartDate()));
    stockRangeFilterVO.setEndDate(buildEndTimestamp(stockRangeFilterVO.getEndDate()));
    return stockCQRSMapper.findStockUtilisationDetailsStatusUsed(stockRangeFilterVO);
  }

  @Override
  public List<StockRangeHistoryVO> findStockUtilisationDetailsStatusEmpty(
      StockRangeFilterVO stockRangeFilterVO) {
    stockRangeFilterVO.setStartDate(buildStartTimestamp(stockRangeFilterVO.getStartDate()));
    stockRangeFilterVO.setEndDate(buildEndTimestamp(stockRangeFilterVO.getEndDate()));
    return stockCQRSMapper.findStockUtilisationDetailsStatusEmpty(stockRangeFilterVO);
  }

  @Override
  public List<StockRangeHistoryVO> findStockRangeHistoryForPage(
      StockRangeFilterVO stockRangeFilterVO, PageRequest pageable) {
    final var limit = pageable.getPageSize();
    final var offset = pageable.getPageNumber() * pageable.getPageSize();
    var isHistory = stockRangeFilterVO.isHistory();
    var status = stockRangeFilterVO.getStatus().trim();
    var startRange = getRangeForFindStockRangeHistoryForPage(stockRangeFilterVO.getStartRange());
    var endRange = getRangeForFindStockRangeHistoryForPage(stockRangeFilterVO.getEndRange());

    List<StockRangeHistoryVO> result = new ArrayList<>();
    stockRangeFilterVO.setStartDate(buildStartTimestamp(stockRangeFilterVO.getStartDate()));
    stockRangeFilterVO.setEndDate(buildEndTimestamp(stockRangeFilterVO.getEndDate()));
    if (!isHistory) {
      if (MODE_UNUSED.equalsIgnoreCase(status) || StringUtils.isEmpty(status)) {
        result =
            stockCQRSMapper.findStockUtilisationDetails(
                stockRangeFilterVO, status, startRange, endRange, limit, offset);
      }

      if (MODE_USED.equalsIgnoreCase(status)) {
        result =
            stockCQRSMapper.findStockUtilisationDetailsUsed(
                stockRangeFilterVO, status, startRange, endRange, limit, offset);
      }
    } else {
      result =
          stockCQRSMapper.findStockRangeHistory(
              stockRangeFilterVO,
              getStatusForFindStockRangeHistory(status),
              startRange,
              endRange,
              limit,
              offset);
    }

    return result;
  }

  public Long getRangeForFindStockRangeHistoryForPage(@Nullable String value) {
    var val = isNotEmpty(value) ? value.trim() : null;
    if (Objects.nonNull(val) && val.length() > 7) {
      val = val.substring(0, val.length() - 1);
    }
    return CalculationUtil.toLongNullable(val);
  }

  private String getStatusForFindStockRangeHistory(String status) {
    if (StockConstant.RECEIVED_ALLOCATION.equals(status)) {
      status = StockConstant.MODE_ALLOCATE;
    }
    if (StockConstant.RECEIVED_TRANSFER.equals(status)) {
      status = StockConstant.MODE_TRANSFER;
    }
    if (StockConstant.RECEIVED_RETURN.equals(status)) {
      status = MODE_RECEIVE;
    }

    return status;
  }

  private Timestamp buildStartTimestamp(Timestamp value) {
    if (value != null) {
      var startDateStr =
          value.toLocalDateTime().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))
              + " "
              + "00:00:00";
      return BaseMapper.convertToTimestamp(
          LocalDate.withDateAndTime(localDate.getLocalDate(null, true), startDateStr));
    }
    return null;
  }

  private Timestamp buildEndTimestamp(Timestamp value) {
    if (value != null) {
      var endDateStr =
          value.toLocalDateTime().format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT))
              + " "
              + "23:59:59";
      return BaseMapper.convertToTimestamp(
          LocalDate.withDateAndTime(localDate.getLocalDate(null, true), endDateStr));
    }
    return null;
  }
}
