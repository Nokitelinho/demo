package com.ibsplc.neoicargo.stock.dao;

import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import java.util.List;
import java.util.Optional;

public interface StockRequestDao {

  void rejectStockRequests(List<StockRequestVO> stockRequestVOS);

  Optional<StockRequest> findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
      String companyCode, String requestRefNumber, int airlineIdentifier);

  void save(StockRequest stockRequest);
}
