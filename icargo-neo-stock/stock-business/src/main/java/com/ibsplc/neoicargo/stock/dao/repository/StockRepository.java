package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends JpaRepository<Stock, Long> {

  @EntityGraph(
      attributePaths = {"ranges"},
      type = EntityGraph.EntityGraphType.LOAD)
  Optional<Stock>
      findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
          String companyCode,
          String stockHolderCode,
          Integer airlineId,
          String docType,
          String docSubType);

  @Query(
      "SELECT s.stockApproverCode FROM Stock s where s.companyCode=:companyCode "
          + "and s.stockHolderCode=:stockHolderCode "
          // + "and s.airlineIdentifier=:airlineId "
          + "and s.documentType=:docType "
          + "and s.documentSubType=:docSubType")
  List<String> findStockApproverCode(
      @Param("companyCode") String companyCode,
      @Param("stockHolderCode") String stockHolderCode,
      // @Param("airlineId") Integer airlineId,
      @Param("docType") String docType,
      @Param("docSubType") String docSubType);

  @Query(
      "SELECT s.stockHolderCode FROM Stock s where s.companyCode=:companyCode "
          + "and s.stockHolderCode=:stockHolderCode "
          + "and s.documentType=:docType "
          + "and s.documentSubType=:docSubType "
          + "and s.autoprocessQuantity > 0")
  String findAutoProcessingQuantityAvailable(
      @Param("companyCode") String companyCode,
      @Param("stockHolderCode") String stockHolderCode,
      @Param("docType") String docType,
      @Param("docSubType") String docSubType);

  boolean existsByCompanyCodeAndStockHolderCodeAndDocumentTypeAndDocumentSubType(
      String companyCode, String stockHolderCode, String docType, String docSubType);
}
