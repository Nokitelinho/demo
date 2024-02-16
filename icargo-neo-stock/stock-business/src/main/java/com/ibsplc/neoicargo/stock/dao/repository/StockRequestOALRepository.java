package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.StockRequestOAL;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRequestOALRepository extends JpaRepository<StockRequestOAL, Long> {

  Optional<StockRequestOAL>
      findByCompanyCodeAndAirportCodeAndDocumentTypeAndDocumentSubTypeAndAirlineIdentifierAndSerialNumber(
          String companyCode,
          String airportCode,
          String documentType,
          String documentSubType,
          int airlineIdentifier,
          int serialNumber);
}
