package com.ibsplc.neoicargo.stock.component.feature.savestockutilisation.persistor;

import com.ibsplc.neoicargo.stock.dao.entity.StockRangeUtilisation;
import com.ibsplc.neoicargo.stock.dao.repository.StockUtilisationRepository;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("stockUtilisationPersistor")
@RequiredArgsConstructor
public class StockUtilisationPersistor {
  private final StockUtilisationRepository stockUtilisationRepository;

  public StockRangeUtilisation find(StockAllocationVO stockAllocationVO, String documentNumber) {
    return stockUtilisationRepository
        .find(
            stockAllocationVO.getCompanyCode(),
            stockAllocationVO.getStockHolderCode(),
            stockAllocationVO.getDocumentType(),
            stockAllocationVO.getDocumentSubType(),
            stockAllocationVO.getAirlineIdentifier(),
            documentNumber)
        .orElse(null);
  }

  public void save(StockRangeUtilisation stockRangeUtilisation) {
    stockUtilisationRepository.save(stockRangeUtilisation);
  }
}
