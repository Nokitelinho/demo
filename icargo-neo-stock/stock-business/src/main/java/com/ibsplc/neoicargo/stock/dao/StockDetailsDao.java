package com.ibsplc.neoicargo.stock.dao;

import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;

public interface StockDetailsDao {
  void save(StockDetailsVO stockDetailsVO);

  StockDetailsVO find(
      String companyCode,
      String stockHolderCode,
      String documentType,
      String documentSubType,
      int txnDate);
}
