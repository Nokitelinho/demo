package com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_002;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_TRANSFER;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.StockAllocationMapper;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import io.jsonwebtoken.lang.Collections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("rangeValidator")
@RequiredArgsConstructor
public class RangeValidator extends Validator<StockAllocationVO> {

  public static final int RANGE_LENGTH = 7;

  private final RangeDao rangeDao;
  private final StockAllocationMapper stockAllocationMapper;

  public void validate(StockAllocationVO stockAllocationVO) throws BusinessException {
    final var ranges = new ArrayList<RangeVO>();
    if (MODE_TRANSFER.equals(stockAllocationVO.getTransferMode())) {
      for (RangeVO rangeVO : stockAllocationVO.getRanges()) {
        if (rangeVO.getEndRange() == null) {
          ranges.addAll(
              findRangeForTransfer(
                  stockAllocationVO,
                  rangeVO.getNumberOfDocuments(),
                  toLong(rangeVO.getStartRange())));
          if (ranges.isEmpty()) {
            throw new StockBusinessException(
                constructErrorVO(
                    NEO_STOCK_003.getErrorCode(), NEO_STOCK_003.getErrorMessage(), ERROR));
          }
        }
      }
      if (ranges.size() > 0) {
        stockAllocationVO.setRanges(ranges);
      }
    }
    for (RangeVO rangeVO : stockAllocationVO.getRanges()) {
      if (toLong(rangeVO.getStartRange()) > toLong(rangeVO.getEndRange())) {
        throw new StockBusinessException(
            constructErrorVO(NEO_STOCK_002.getErrorCode(), NEO_STOCK_002.getErrorMessage(), ERROR));
      }
    }
  }

  private List<RangeVO> findRangeForTransfer(
      StockAllocationVO stockAllocationVO, long numberOfDocuments, long startRange) {
    final var stockAllocationFilterVO =
        stockAllocationMapper.constructStockAllocationFilterVO(stockAllocationVO, startRange);
    final var rangeForTransfer = rangeDao.findRangeForTransfer(stockAllocationFilterVO);
    return groupRanges(rangeForTransfer, numberOfDocuments, startRange);
  }

  private List<RangeVO> groupRanges(
      Collection<RangeVO> ranges, long numberOfDocuments, long startRange) {
    List<RangeVO> groupedRanges = new ArrayList<>();
    long count = 0;
    long documents = 0;
    if (!Collections.isEmpty(ranges)) {
      for (RangeVO rangevo : ranges) {
        rangevo.setAsciiEndRange(toLong(rangevo.getEndRange()));
        if (startRange > rangevo.getAsciiStartRange() && startRange <= rangevo.getAsciiEndRange()) {
          rangevo.setAsciiStartRange(startRange);
          rangevo.setStartRange(Long.toString(startRange));
          count += rangevo.getAsciiEndRange() - rangevo.getAsciiStartRange() + 1;
        } else {
          count += rangevo.getNumberOfDocuments();
        }
        if (count <= numberOfDocuments) {
          documents += rangevo.getAsciiEndRange() - rangevo.getAsciiStartRange() + 1;
          groupedRanges.add(rangevo);
          if (count == numberOfDocuments) {
            break;
          }
        } else {
          long endRange = rangevo.getAsciiStartRange() + (numberOfDocuments - documents) - 1;
          rangevo.setEndRange(String.valueOf(endRange));
          int avlLength = rangevo.getEndRange().length();
          int totLength = RANGE_LENGTH - avlLength;
          String endStr = "0".repeat(Math.max(0, totLength)) + rangevo.getEndRange();
          rangevo.setEndRange(endStr);
          groupedRanges.add(rangevo);
          break;
        }
      }
    }
    return groupedRanges;
  }
}
