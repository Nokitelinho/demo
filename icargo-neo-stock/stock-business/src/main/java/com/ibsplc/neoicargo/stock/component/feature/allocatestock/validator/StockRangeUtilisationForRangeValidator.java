package com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_001;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static java.util.Objects.nonNull;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.stock.dao.UtilisationDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.UtilisationFilterVO;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("stockRangeUtilisationForRangeValidator")
@RequiredArgsConstructor
public class StockRangeUtilisationForRangeValidator extends Validator<StockAllocationVO> {

  private final UtilisationDao utilisationDao;

  public void validate(StockAllocationVO stockAllocationVO) throws BusinessException {
    log.info("stockRangeUtilisationForRangeValidator Invoked");
    long count = utilisationDao.findStockUtilisationForRange(buildFilter(stockAllocationVO));
    if (count > 0) {
      throw new StockBusinessException(
          constructErrorVO(NEO_STOCK_001.getErrorCode(), NEO_STOCK_001.getErrorMessage(), ERROR));
    }
  }

  private UtilisationFilterVO buildFilter(StockAllocationVO stockAllocationVO) {
    return UtilisationFilterVO.builder()
        .airlineIdentifier(stockAllocationVO.getAirlineIdentifier())
        .companyCode(stockAllocationVO.getCompanyCode())
        .documentSubType(stockAllocationVO.getDocumentSubType())
        .documentType(stockAllocationVO.getDocumentType())
        .stockHolderCode(stockAllocationVO.getStockControlFor())
        .ranges(
            stockAllocationVO.getRanges().stream()
                .filter(
                    rangeVO -> nonNull(rangeVO.getStartRange()) && nonNull(rangeVO.getEndRange()))
                .map(
                    rangeVO ->
                        RangeFilterVO.builder()
                            .asciiStartRange(toLong(rangeVO.getStartRange()))
                            .asciiEndRange(toLong(rangeVO.getEndRange()))
                            .build())
                .collect(Collectors.toList()))
        .build();
  }
}
