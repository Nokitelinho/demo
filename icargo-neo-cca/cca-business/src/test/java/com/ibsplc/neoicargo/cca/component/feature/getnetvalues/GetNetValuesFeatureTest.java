package com.ibsplc.neoicargo.cca.component.feature.getnetvalues;

import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMoney;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(JUnitPlatform.class)
class GetNetValuesFeatureTest {

    @InjectMocks
    private GetNetValuesFeature getNetValuesFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetNetValuesIfPresent() {
        // Given
        final var ccaAwbVO = new CcaAwbVO();
        ccaAwbVO.setNetValueExport(getMoney(1.0, "USD"));
        ccaAwbVO.setNetValueImport(getMoney(2.0, "USD"));

        final var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);

        // Then
        var netValuesData = getNetValuesFeature.perform(ccaMasterVO);
        assertNotNull(netValuesData);
        assertEquals(1.0, netValuesData.getNetValueExport());
        assertEquals(2.0, netValuesData.getNetValueImport());
    }

    @Test
    void shouldGetNetValuesWithNullsIfAbsent() {
        // Given
        final var ccaAwbVO = new CcaAwbVO();

        final var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);

        // Then
        var netValuesData = getNetValuesFeature.perform(ccaMasterVO);
        assertNotNull(netValuesData);
        assertNull(netValuesData.getNetValueExport());
        assertNull(netValuesData.getNetValueImport());
    }
}
