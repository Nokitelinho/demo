package com.ibsplc.neoicargo.cca.dao.entity;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_N;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_Y;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CCAAwbChargeDetailTest {
    
    @Test
    void getParentEntityShouldReturnCcaAwb() {
        // Given
        var ccaAwb = new CCAAwb();
        var actual = new CCAAwbChargeDetail();
        actual.setCcaawb(ccaAwb);

        // When + Then
        assertEquals(ccaAwb, actual.getParentEntity());
    }

    @Test
    void getIdAsStringShouldReturnCcaAwbStringIdAndOCDAAndChargeHeadCodeWhenDueAgentY() {
        // Given
        var ccaAwbStringId = "134-CCA000005-R";
        var ccaAwb = Mockito.mock(CCAAwb.class);
        var actual = new CCAAwbChargeDetail();
        actual.setCcaawb(ccaAwb);
        actual.setDueAgent(FLAG_Y);
        actual.setDueCarrier(FLAG_N);
        actual.setCharge(10);
        actual.setChargeHead("Some charge");
        actual.setChargeHeadCode("SC");
        doReturn(ccaAwbStringId)
                .when(ccaAwb).getIdAsString();
        var expectedId = ccaAwbStringId + "-OCDA-SC";

        // When + Then
        assertEquals(expectedId, actual.getIdAsString());
    }

    @Test
    void getIdAsStringShouldReturnCcaAwbStringIdAndOCDCAndChargeHeadCodeWhenDueAgentN() {
        // Given
        var ccaAwbStringId = "134-CCA000005-R";
        var ccaAwb = Mockito.mock(CCAAwb.class);
        var actual = new CCAAwbChargeDetail();
        actual.setCcaawb(ccaAwb);
        actual.setDueAgent(FLAG_N);
        actual.setDueCarrier(FLAG_Y);
        actual.setCharge(10);
        actual.setChargeHead("Some charge");
        actual.setChargeHeadCode("SC");
        doReturn(ccaAwbStringId)
                .when(ccaAwb).getIdAsString();
        var expectedId = ccaAwbStringId + "-OCDC-SC";

        // When + Then
        assertEquals(expectedId, actual.getIdAsString());
    }
}
