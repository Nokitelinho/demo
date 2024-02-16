package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_005;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DELIMITER;
import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.filter.BlackListFilterVO;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlackListRangeValidator {

  private final RangeDao rangeDao;

  public void validate(StockAllocationVO stockAllocationVO) throws BusinessException {
    log.info("Invoked BlackListRangeValidator");
    StringJoiner blacklistRanges = new StringJoiner(DELIMITER);
    for (RangeVO rangeVo : stockAllocationVO.getRanges()) {
      if (StringUtils.isNotBlank(
          rangeDao.checkBlacklistRanges(build(stockAllocationVO, rangeVo)))) {
        blacklistRanges.add(rangeVo.getStartRange() + HYPHEN + rangeVo.getEndRange());
      }
    }
    if (blacklistRanges.length() > 0) {
      log.error("Range {} contains blacklisted stock.", blacklistRanges);
      throw new StockBusinessException(
          constructErrorVO(
              NEO_STOCK_005.getErrorCode(),
              NEO_STOCK_005.getErrorMessage(),
              ERROR,
              new String[] {blacklistRanges.toString()}));
    }
  }

  private BlackListFilterVO build(StockAllocationVO stockAllocationVO, RangeVO rangeVo) {
    return BlackListFilterVO.builder()
        .companyCode(stockAllocationVO.getCompanyCode())
        .documentType(stockAllocationVO.getDocumentType())
        .documentSubType(stockAllocationVO.getDocumentSubType())
        .airlineIdentifier(stockAllocationVO.getAirlineIdentifier())
        .asciiStartRange(rangeVo.getAsciiStartRange())
        .asciiEndRange(rangeVo.getAsciiEndRange())
        .build();
  }
}
