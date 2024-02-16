package com.ibsplc.neoicargo.stock.dao.impl;

import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_REJECTED;

import com.ibsplc.neoicargo.stock.dao.StockRequestDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.dao.repository.StockRequestRepository;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("stockRequestDao")
@RequiredArgsConstructor
public class StockRequestDaoImpl implements StockRequestDao {
  private final StockRequestRepository stockRequestRepository;

  @Override
  public void rejectStockRequests(List<StockRequestVO> stockRequestVOS) {
    for (StockRequestVO vo : stockRequestVOS) {
      stockRequestRepository.rejectStockRequest(
          vo.getCompanyCode(),
          vo.getRequestRefNumber(),
          Integer.parseInt(vo.getAirlineIdentifier()),
          vo.getApprovalRemarks(),
          STATUS_REJECTED,
          Timestamp.valueOf(LocalDateTime.now()));
    }
  }

  @Override
  public Optional<StockRequest> findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
      String companyCode, String requestRefNumber, int airlineIdentifier) {
    return stockRequestRepository.findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
        companyCode, requestRefNumber, airlineIdentifier);
  }

  @Override
  public void save(StockRequest stockRequest) {
    stockRequestRepository.save(stockRequest);
  }
}
