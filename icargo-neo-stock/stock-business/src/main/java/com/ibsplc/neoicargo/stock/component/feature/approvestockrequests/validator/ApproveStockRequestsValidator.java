package com.ibsplc.neoicargo.stock.component.feature.approvestockrequests.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockRequestApproveVO;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("approveStockRequestsValidator")
@RequiredArgsConstructor
public class ApproveStockRequestsValidator extends Validator<StockRequestApproveVO> {
  private final StockDao stockDao;

  @Override
  public void validate(StockRequestApproveVO stockRequestApproveVO) throws BusinessException {
    var stockHolderVO =
        stockDao.findStockHolderDetails(
            stockRequestApproveVO.getCompanyCode(), stockRequestApproveVO.getApproverCode());
    if (Objects.isNull(stockHolderVO)) {
      throw new BusinessException(constructErrorVO(NEO_STOCK_011, ErrorType.ERROR));
    }
  }
}
