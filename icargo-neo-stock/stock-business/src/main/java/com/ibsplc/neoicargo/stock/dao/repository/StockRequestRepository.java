package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRequestRepository extends JpaRepository<StockRequest, Long> {

  Optional<StockRequest> findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
      String companyCode, String requestRefNumber, int airlineIdentifier);

  Optional<StockRequest> findByCompanyCodeAndRequestRefNumber(
      String companyCode, String requestRefNumber);

  @Modifying
  @Query(
      "update StockRequest sr "
          + "set sr.status = :status, "
          + "sr.approvalRemarks = :approvalRemarks, "
          + "sr.lastUpdatedTime = :lastUpdatedTime "
          + "where sr.companyCode = :companyCode "
          + "and sr.airlineIdentifier = :airlineIdentifier "
          + "and sr.requestRefNumber = :requestRefNumber")
  void rejectStockRequest(
      @Param("companyCode") String companyCode,
      @Param("requestRefNumber") String requestRefNumber,
      @Param("airlineIdentifier") Integer airlineIdentifier,
      @Param("approvalRemarks") String approvalRemarks,
      @Param("status") String status,
      @Param("lastUpdatedTime") Timestamp lastUpdatedTime);

  @Query(value = "SELECT nextval('request_ref_number_seq')", nativeQuery = true)
  Long getNextValOfReqRefNumber();
}
