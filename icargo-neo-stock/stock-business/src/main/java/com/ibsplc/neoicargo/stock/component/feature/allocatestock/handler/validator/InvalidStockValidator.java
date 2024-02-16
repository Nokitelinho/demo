package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_006;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DELIMITER;
import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvalidStockValidator {

  public void validate(StockVO stockVO, StockAllocationVO stockAllocationVO)
      throws BusinessException {
    log.info("Invoked InvalidStockValidator");
    StringJoiner invalidRanges = new StringJoiner(DELIMITER);
    for (RangeVO rangeVo : stockAllocationVO.getRanges()) {
      if (checkForInvalid(stockVO, rangeVo, stockAllocationVO.isManual())) {
        invalidRanges.add(rangeVo.getStartRange() + HYPHEN + rangeVo.getEndRange());
      }
    }
    if (invalidRanges.length() > 0) {
      log.info("Range {} not found", invalidRanges);
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_006.getErrorCode(),
              NEO_STOCK_006.getErrorMessage(),
              ERROR,
              new String[] {invalidRanges.toString()}));
    }
  }

  private boolean checkForInvalid(StockVO stock, RangeVO incomingRangeVO, boolean manual) {
    log.info("Check for invalid document");
    if (isEmpty(stock.getRanges())) {
      return false;
    }
    for (RangeVO range : stock.getRanges()) {
      var startRange = range.getAsciiStartRange();
      var endRange = range.getAsciiEndRange();
      var incomingStartRange = toLong(incomingRangeVO.getStartRange());
      var incomingEndRange = toLong(incomingRangeVO.getEndRange());
      log.info("Already saved range, asciiStartRange: {}", startRange);
      log.info("Already saved range, asciiEndRange: {}", endRange);
      log.info("To be saved range, startRange: {}", incomingStartRange);
      log.info("To be saved range, endRange: {}", incomingEndRange);
      log.info("Stock allocation: manualFlag: {}", manual);

      if ((incomingStartRange >= startRange && incomingStartRange <= endRange)
          && (incomingEndRange >= startRange && incomingEndRange <= endRange)
          && manual == range.isManual()) {
        log.info("Range is valid. To be saved range between {} and {}", startRange, endRange);
        return false;

      } else {
        if ((incomingStartRange >= startRange && incomingStartRange <= endRange)
            && (incomingEndRange >= startRange && incomingEndRange > endRange)
            && manual == range.isManual()) {
          log.info("Out of range. Find rest of the ranges");
          endRange++;
          incomingRangeVO.setStartRange(String.valueOf(endRange));
          return checkForInvalid(stock, incomingRangeVO, manual);
        }
      }
    }
    return true;
  }
}
