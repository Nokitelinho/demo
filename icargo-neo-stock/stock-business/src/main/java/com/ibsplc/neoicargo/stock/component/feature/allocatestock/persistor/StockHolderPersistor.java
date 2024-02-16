package com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor;

import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.sql.Timestamp;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("stockHolderPersistor")
@RequiredArgsConstructor
public class StockHolderPersistor {

  private final StockHolderRepository stockHolderRepository;

  @Transactional
  public void addStock(StockAllocationVO stockAllocationVO) {
    stockHolderRepository
        .findByCompanyCodeAndStockHolderCode(
            stockAllocationVO.getCompanyCode(), stockAllocationVO.getStockHolderCode())
        .ifPresent(
            stockHolder -> {
              stockHolder.addStock(build(stockAllocationVO));
              stockHolder.setLastUpdatedTime(
                  Timestamp.valueOf(stockAllocationVO.getLastUpdateTime().toLocalDateTime()));
              stockHolder.setLastUpdatedUser(stockAllocationVO.getLastUpdateUser());
              stockHolderRepository.save(stockHolder);
            });
  }

  private Stock build(StockAllocationVO stockAllocationVO) {
    final var stock = new Stock();
    stock.setCompanyCode(stockAllocationVO.getCompanyCode());
    stock.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
    stock.setStockHolderCode(stockAllocationVO.getStockHolderCode());
    stock.setDocumentSubType(stockAllocationVO.getDocumentSubType());
    stock.setDocumentType(stockAllocationVO.getDocumentType());
    stock.setLastUpdatedTime(
        Timestamp.valueOf(stockAllocationVO.getLastUpdateTime().toLocalDateTime()));
    stock.setLastUpdatedUser(stockAllocationVO.getLastUpdateUser());
    return stock;
  }
}
