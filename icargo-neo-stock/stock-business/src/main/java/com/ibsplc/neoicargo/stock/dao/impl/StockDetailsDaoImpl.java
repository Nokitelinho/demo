package com.ibsplc.neoicargo.stock.dao.impl;

import com.ibsplc.neoicargo.stock.dao.StockDetailsDao;
import com.ibsplc.neoicargo.stock.dao.repository.StockDetailsRepository;
import com.ibsplc.neoicargo.stock.mapper.StockDetailsMapper;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("stockDetailsDao")
@RequiredArgsConstructor
public class StockDetailsDaoImpl implements StockDetailsDao {

  private final StockDetailsMapper mapper;
  private final StockDetailsRepository repository;

  @Override
  public void save(StockDetailsVO stockDetailsVO) {
    repository.save(mapper.mapVoToEntity(stockDetailsVO));
  }

  @Override
  public StockDetailsVO find(
      String companyCode,
      String stockHolderCode,
      String documentType,
      String documentSubType,
      int txnDate) {
    return repository
        .findByCompanyCodeAndStockHolderCodeAndDocumentTypeAndDocumentSubTypeAndTxnDateString(
            companyCode, stockHolderCode, documentType, documentSubType, txnDate)
        .map(mapper::mapEntityToVo)
        .orElse(null);
  }
}
