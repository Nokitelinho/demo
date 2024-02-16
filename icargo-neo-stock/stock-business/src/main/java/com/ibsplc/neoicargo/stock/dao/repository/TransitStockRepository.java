package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.TransitStock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransitStockRepository extends JpaRepository<TransitStock, Long> {
  Optional<TransitStock>
      findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
          String companyCode,
          String stockHolderCode,
          Integer airlineId,
          String docType,
          String docSubType);
}
