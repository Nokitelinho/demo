package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.StockRangeUtilisation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockUtilisationRepository extends JpaRepository<StockRangeUtilisation, Long> {

  @Query(
      value =
          "select * from stkrngutl "
              + "where cmpcod = :companyCode "
              + "and stkhldcod = :stockHolderCode "
              + "and doctyp = :documentType "
              + "and docsubtyp = :documentSubType "
              + "and arlidr = :airlineIdentifier "
              + "and mstdocnum = :documentNumber",
      nativeQuery = true)
  Optional<StockRangeUtilisation> find(
      @Param("companyCode") String companyCode,
      @Param("stockHolderCode") String stockHolderCode,
      @Param("documentType") String documentType,
      @Param("documentSubType") String documentSubType,
      @Param("airlineIdentifier") int airlineIdentifier,
      @Param("documentNumber") String documentNumber);
}
