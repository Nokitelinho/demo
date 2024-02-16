package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockHolderRepository extends JpaRepository<StockHolder, Long> {

  Optional<StockHolder> findByCompanyCodeAndStockHolderCode(
      String companyCode, String stockHolderCode);

  Optional<StockHolder> findByCompanyCodeAndStockHolderCodeAndStockHolderType(
      String companyCode, String stockHolderCode, StockHolderType stockHolderType);

  List<StockHolder> findByCompanyCodeAndStockHolderCodeIn(
      String companyCode, List<String> stockHolderCodes);

  @Query(
      value =
          "SELECT mst.stkhldcod "
              + "FROM stkhldmst mst "
              + "INNER JOIN stkhldstk stk "
              + "ON mst.cmpcod = stk.cmpcod "
              + "AND mst.stkhldcod = stk.stkhldcod "
              + "WHERE "
              + "mst.stkhldtyp = 'H' "
              + "AND stk.cmpcod =?1 "
              + "AND stk.arlidr =?2 "
              + "AND stk.doctyp =?3 "
              + "AND stk.docsubtyp =?4",
      nativeQuery = true)
  List<String> findHeadQuarterDetails(
      String companyCode, Integer airlineId, String docType, String docSubType);
}
