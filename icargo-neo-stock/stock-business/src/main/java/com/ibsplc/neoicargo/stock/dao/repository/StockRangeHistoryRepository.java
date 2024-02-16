package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.StockRangeHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockRangeHistoryRepository extends JpaRepository<StockRangeHistory, Long> {

  @Query(
      value =
          "select * from stkrngtxnhis "
              + "where cmpcod = ?1 "
              + "and arlidr = ?2 "
              + "and doctyp = ?3 "
              + "and docsubtyp = ?4 "
              + "and frmstkhldcod = ?5 "
              + "and hisseqnum = ?6",
      nativeQuery = true)
  Optional<StockRangeHistory> findByUniqueKey(
      String companyCode,
      Integer airlineId,
      String docType,
      String docSubType,
      String stockHolderCode,
      String serialNumber);
}
