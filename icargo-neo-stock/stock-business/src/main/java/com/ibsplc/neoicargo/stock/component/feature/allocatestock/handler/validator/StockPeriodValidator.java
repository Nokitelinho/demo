package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_009;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DELIMITER;
import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.stock.dao.UtilisationDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import io.jsonwebtoken.lang.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockPeriodValidator {

  public static final int END_RANGE_POSITION = 2;

  private final UtilisationDao utilisationDao;

  public void validate(StockAllocationVO stockAllocationVO, int stockIntroductionPeriod)
      throws BusinessException {
    String mergeRanges = validateStockPeriod(stockAllocationVO, stockIntroductionPeriod);
    if (mergeRanges != null) {
      ErrorVO errorVo =
          constructErrorVO(
              NEO_STOCK_009.getErrorCode(),
              NEO_STOCK_009.getErrorMessage(),
              ErrorType.ERROR,
              new String[] {mergeRanges, String.valueOf(stockIntroductionPeriod)});
      throw new StockBusinessException(errorVo);
    }
  }

  private String validateStockPeriod(
      StockAllocationVO stockAllocationVO, int stockIntroductionPeriod) {
    List<Long> list =
        utilisationDao.validateStockPeriod(stockAllocationVO, stockIntroductionPeriod);
    if (!Collections.isEmpty(list)) {
      return mergeRanges(list);
    }
    return null;
  }

  private String mergeRanges(List<Long> list) {
    ArrayList<Long> sublistStarsAndEnds = new ArrayList<>();
    sublistStarsAndEnds.add(list.get(0));
    for (int i = 1; i < list.size() - 1; i++) {
      if (list.get(i) > 1 + list.get(i - 1)) {
        sublistStarsAndEnds.add(list.get(i - 1));
        sublistStarsAndEnds.add(list.get(i));
      }
    }
    sublistStarsAndEnds.add(list.get(list.size() - 1));
    var joinedRanges = new StringJoiner(DELIMITER);
    for (int i = 0; i < sublistStarsAndEnds.size() - 1; i = i + END_RANGE_POSITION) {
      joinedRanges.add(sublistStarsAndEnds.get(i) + HYPHEN + sublistStarsAndEnds.get(i + 1));
    }
    return joinedRanges.length() == 0 ? null : joinedRanges.toString();
  }
}
