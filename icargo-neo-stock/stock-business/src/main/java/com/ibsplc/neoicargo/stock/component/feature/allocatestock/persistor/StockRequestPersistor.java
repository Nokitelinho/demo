package com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_007;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.getNumberOfDocuments;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_ALLOCATED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_APPROVED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_COMPLETED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_NEW;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.StockRequestDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("stockRequestPersistor")
@RequiredArgsConstructor
public class StockRequestPersistor {

  private final StockRequestDao stockRequestDao;

  public void update(StockAllocationVO stockAllocationVO) throws BusinessException {
    final var stockRequest =
        find(
            stockAllocationVO.getCompanyCode(),
            stockAllocationVO.getRequestRefNumber(),
            stockAllocationVO.getAirlineIdentifier());
    updateStockRequest(stockRequest, stockAllocationVO);
    stockRequestDao.save(stockRequest);
  }

  private void updateStockRequest(StockRequest stockRequest, StockAllocationVO stockAllocationVO) {
    final var nextAllocatedStocks = getNumberOfDocuments(stockAllocationVO.getRanges());
    long previousAllocatedStocks = stockRequest.getAllocatedStock();
    stockRequest.setAllocatedStock(previousAllocatedStocks + nextAllocatedStocks);
    if (stockAllocationVO.getLastUpdateTimeForStockReq() != null) {
      stockRequest.setLastUpdatedTime(
          Timestamp.valueOf(stockAllocationVO.getLastUpdateTimeForStockReq().toLocalDateTime()));
    }
    stockRequest.setLastUpdatedUser(stockAllocationVO.getLastUpdateUser());
    long approved = stockRequest.getApprovedStock();
    long allocated = stockRequest.getAllocatedStock();
    if (approved == 0 && allocated == 0) {
      stockRequest.setStatus(STATUS_NEW);
    } else if (approved == allocated) {
      if (approved >= stockRequest.getRequestedStock()) {
        stockRequest.setStatus(STATUS_COMPLETED);
      } else {
        stockRequest.setStatus(STATUS_ALLOCATED);
      }
    } else {
      stockRequest.setStatus(STATUS_APPROVED);
    }
  }

  private StockRequest find(String companyCode, String requestRefNumber, int airlineIdentifier)
      throws BusinessException {
    final var stockRequest =
        stockRequestDao
            .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
                companyCode, requestRefNumber, airlineIdentifier)
            .orElse(null);
    if (stockRequest == null) {
      throw new BusinessException(NEO_STOCK_007.getErrorCode(), NEO_STOCK_007.getErrorMessage());
    }
    return stockRequest;
  }
}
