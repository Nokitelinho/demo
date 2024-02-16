package com.ibsplc.neoicargo.cca.dao.repository;

import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CcaMasterRepository extends JpaRepository<CcaMaster, Long> {

    @EntityGraph(
            attributePaths = {
                    "ccaAwb",
                    "ccaAwb.awbDetails",
                    "ccaAwb.awbDetails.dimensions",
                    "ccaAwb.awbRates",
                    "ccaAwb.awbCharges",
                    "ccaAwb.ccaAwbRoutingDetails"
            },
            type = EntityGraph.EntityGraphType.LOAD)
    Optional<CcaMaster> findByShipmentPrefixAndMasterDocumentNumberAndCcaReferenceNumber(String shipmentPrefix,
                                                                                         String masterDocumentNumber,
                                                                                         String ccaReferenceNumber);

    Optional<CcaMaster> findByMasterDocumentNumberAndShipmentPrefixAndCcaReferenceNumber(String masterDocumentNumber,
                                                                                         String shipmentPrefix,
                                                                                         String ccaReferenceNumber);

    Optional<CcaMaster> findByShipmentPrefixAndMasterDocumentNumberAndCcaReferenceNumberAndCompanyCode(
            String shipmentPrefix, String masterDocumentNumber, String ccaReferenceNumber, String companyCode);

    Collection<CcaMaster> deleteByCcaSerialNumberIn(List<Long> ccaSerialNumbers);

    List<CcaMaster> findByCcaSerialNumberIn(List<Long> ccaSerialNumbers);

    List<CcaMaster> findByShipmentPrefixAndMasterDocumentNumberAndCcaStatusIn(String shipmentPrefix,
                                                                              String masterDocumentNumber,
                                                                              Set<CcaStatus> ccaStatuses);

}