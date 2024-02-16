package com.ibsplc.neoicargo.stock.component.feature.cancelstockrequest.persistor;

import static com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO.STATUS_CANCEL;
import static com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO.STATUS_NEW;
import static com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO.STATUS_REJECTED;
import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_007;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_019;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.dao.repository.StockRequestRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("cancelStockRequestPersistor")
@RequiredArgsConstructor
public class CancelStockRequestPersistor {

  private final StockRequestRepository stockRequestRepository;

  public void cancelStockRequest(StockRequestVO stockRequestVO) throws StockBusinessException {
    var stockRequestOptional =
        stockRequestRepository.findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
            stockRequestVO.getCompanyCode(),
            stockRequestVO.getRequestRefNumber(),
            Integer.parseInt(stockRequestVO.getAirlineIdentifier()));

    if (stockRequestOptional.isEmpty()) {
      throw new StockBusinessException(constructErrorVO(NEO_STOCK_007, ERROR));
    }

    StockRequest stockRequest = stockRequestOptional.get();
    if (STATUS_NEW.equals(stockRequest.getStatus())
        || STATUS_REJECTED.equals(stockRequest.getStatus())) {
      stockRequest.setStatus(STATUS_CANCEL);
    } else {
      throw new StockBusinessException(constructErrorVO(NEO_STOCK_019, ERROR));
    }
  }
}
