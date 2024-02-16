package com.ibsplc.neoicargo.cca.dao.entity;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
class CCAMasterTest {

    private static final String MASTER_DOCUMENT_NUMBER = "10000000";
    private static final String CCA_000001 = "CCA000001";

    @Test
    void getIdAsString() {
        // Given
        var actual = new CcaMaster();
        actual.setShipmentPrefix(SHIPMENT_PREFIX);
        actual.setMasterDocumentNumber(MASTER_DOCUMENT_NUMBER);
        actual.setCcaReferenceNumber(CCA_000001);

        var expectedId = SHIPMENT_PREFIX + "-" + MASTER_DOCUMENT_NUMBER + "-" + CCA_000001;

        // When + Then
        assertEquals(expectedId, actual.getIdAsString());
    }

    @Test
    void getBusinessIdAsString() {
        // Given
        var actual = new CcaMaster();
        actual.setShipmentPrefix(SHIPMENT_PREFIX);
        actual.setMasterDocumentNumber(MASTER_DOCUMENT_NUMBER);
        actual.setCcaReferenceNumber(CCA_000001);

        var expectedId = SHIPMENT_PREFIX + "-" + MASTER_DOCUMENT_NUMBER + "-" + CCA_000001;

        // When + Then
        assertEquals(expectedId, actual.getBusinessIdAsString());
    }

}