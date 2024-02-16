package com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.exception.StockErrors;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("headQuarterValidator")
@RequiredArgsConstructor
public class HeadQuarterValidator extends Validator<StockHolderVO> {
  private final StockHolderRepository stockHolderRepository;

  @Override
  public void validate(StockHolderVO stockHolderVO) throws BusinessException {
    if (StockHolderVO.OPERATION_FLAG_INSERT.equals(stockHolderVO.getOperationFlag())
        && StockHolderType.H.equals(stockHolderVO.getStockHolderType())) {
      for (StockVO stockVO : stockHolderVO.getStock()) {
        log.info("HeadQuarterValidator Invoked");
        if (!stockHolderRepository
            .findHeadQuarterDetails(
                stockHolderVO.getCompanyCode(),
                stockVO.getAirlineIdentifier(),
                stockVO.getDocumentType(),
                stockVO.getDocumentSubType())
            .isEmpty()) {
          throw new StockBusinessException(
              constructErrorVO(StockErrors.NEO_STOCK_014, ErrorType.ERROR));
        }
      }
    }
  }
}
