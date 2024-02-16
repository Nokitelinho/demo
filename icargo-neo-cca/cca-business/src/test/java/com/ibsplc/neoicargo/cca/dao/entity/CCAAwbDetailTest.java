package com.ibsplc.neoicargo.cca.dao.entity;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CCAAwbDetailTest {

    @Test
    void getParentEntityShouldReturnCcaAwb() {
        // Given
        var ccaAwb = new CCAAwb();
        var actual = new CCAAwbDetail();
        actual.setCcaawb(ccaAwb);

        // When + Then
        assertEquals(ccaAwb, actual.getParentEntity());
    }

    @Test
    void getIdAsStringShouldReturnCcaAwbStringIdAndOCDAAndChargeHeadCodeWhenDueAgentY() {
        // Given
        var ccaAwbStringId = "134-CCA000005-R";
        var ccaAwb = Mockito.mock(CCAAwb.class);
        var serialNumber = 100L;
        var actual = new CCAAwbDetail();
        actual.setCcaawb(ccaAwb);
        actual.setSerialNumber(serialNumber);
        doReturn(ccaAwbStringId)
                .when(ccaAwb).getIdAsString();
        var expectedId = ccaAwbStringId + "-" + serialNumber;

        // When + Then
        assertEquals(expectedId, actual.getIdAsString());
    }
}