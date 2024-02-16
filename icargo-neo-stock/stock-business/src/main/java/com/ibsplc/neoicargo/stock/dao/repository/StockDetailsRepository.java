package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.StockDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDetailsRepository extends JpaRepository<StockDetails, Long> {
  Optional<StockDetails>
      findByCompanyCodeAndStockHolderCodeAndDocumentTypeAndDocumentSubTypeAndTxnDateString(
          String companyCode,
          String stockHolderCode,
          String documentType,
          String documentSubType,
          int parseInt);
}
