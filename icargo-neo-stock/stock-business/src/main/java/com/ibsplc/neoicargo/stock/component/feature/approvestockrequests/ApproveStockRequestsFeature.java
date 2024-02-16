package com.ibsplc.neoicargo.stock.component.feature.approvestockrequests;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_007;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_ALLOCATED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_APPROVED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_COMPLETED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_NEW;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.stock.dao.StockRequestDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.vo.StockRequestApproveVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("approveStockRequestsFeature")
@FeatureConfigSource("stock/approvestockrequests")
@RequiredArgsConstructor
public class ApproveStockRequestsFeature extends AbstractFeature<StockRequestApproveVO> {

  private final StockRequestDao stockRequestDao;

  public Void perform(StockRequestApproveVO stockRequestApproveVO) throws BusinessException {
    for (StockRequestVO stockRequestVO : stockRequestApproveVO.getStockRequests()) {
      var stockRequest = approveStockRequest(findStockRequest(stockRequestVO), stockRequestVO);
      stockRequestDao.save(stockRequest);
    }

    return null;
  }

  private StockRequest findStockRequest(StockRequestVO stockRequestVO) throws BusinessException {
    return stockRequestDao
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
            stockRequestVO.getCompanyCode(),
            stockRequestVO.getRequestRefNumber(),
            Integer.parseInt(stockRequestVO.getAirlineIdentifier()))
        .orElseThrow(() -> new BusinessException(constructErrorVO(NEO_STOCK_007, ErrorType.ERROR)));
  }

  private StockRequest approveStockRequest(
      StockRequest stockRequest, StockRequestVO stockRequestVO) {
    long approved = stockRequestVO.getApprovedStock();
    long allocated = stockRequestVO.getAllocatedStock();
    long requested = stockRequestVO.getRequestedStock();
    stockRequest.setApprovedStock(approved);
    stockRequest.setApprovalRemarks(stockRequestVO.getApprovalRemarks());

    if (approved == 0 && allocated == 0) {
      stockRequest.setStatus(STATUS_NEW);
    } else if (approved == allocated) {
      if (approved >= requested) {
        stockRequest.setStatus(STATUS_COMPLETED);
      } else {
        stockRequest.setStatus(STATUS_ALLOCATED);
      }
    } else {
      stockRequest.setStatus(STATUS_APPROVED);
    }

    return stockRequest;
  }
}
