package com.ibsplc.neoicargo.cca.dao.entity;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CCAAwbTest {

    @Test
    void getParentEntityShouldReturnCcaMaster() {
        // Given
        var ccaMaster = new CcaMaster();
        var actual = new CCAAwb();
        actual.setCcaMaster(ccaMaster);

        // When + Then
        assertEquals(ccaMaster, actual.getParentEntity());
    }

    @Test
    void getIdAsString() {
        // Given
        var ccaMasterStringId = "134-CCA000005";
        long serialNumber = 1000L;
        var ccaMaster = Mockito.mock(CcaMaster.class);
        ccaMaster.setCcaSerialNumber(serialNumber);
        var pk = new CCAAwbPk();
        pk.setCcaSerialNumber(serialNumber);
        pk.setRecordType(CCA_RECORD_TYPE_REVISED);
        var actual = new CCAAwb();
        actual.setCcaMaster(ccaMaster);
        actual.setCcaAwbPk(pk);
        doReturn(ccaMasterStringId)
                .when(ccaMaster).getIdAsString();

        var expectedId = ccaMasterStringId + "-" + CCA_RECORD_TYPE_REVISED;

        // When + Then
        assertEquals(expectedId, actual.getIdAsString());
    }

}