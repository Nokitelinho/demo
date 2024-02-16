package com.ibsplc.neoicargo.stock.dao;

import com.ibsplc.neoicargo.stock.vo.StockRequestOALVO;
import java.util.List;

public interface StockRequestOALDao {

  StockRequestOALVO find(
      String companyCode,
      String airportCode,
      String documentType,
      String documentSubType,
      int airlineIdentifier,
      int serialNumber);

  void saveAll(List<StockRequestOALVO> stockRequestOALVOS);
}
