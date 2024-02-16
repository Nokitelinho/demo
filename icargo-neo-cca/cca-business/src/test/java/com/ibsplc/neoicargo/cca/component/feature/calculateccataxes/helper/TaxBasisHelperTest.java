package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.CCA_REFERENCE_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX_INT_VALUE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaChargeWithoutTypeAndValue;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMoney;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;


@RunWith(JUnitPlatform.class)
class TaxBasisHelperTest {

    @InjectMocks
    private TaxBasisHelper taxBasisHelper;

    @Mock
    private ContextUtil contextUtil;

    @Mock
    private LoginProfile profile;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        doReturn(new LoginProfile()).when(contextUtil).callerLoginProfile();
        doReturn(SHIPMENT_PREFIX_INT_VALUE + 5).when(profile).getOwnAirlineIdentifier();
    }

    @Test
    void getChargeDetailsMapShouldReceiveMapWithChargeParameters() {
        // Given
        var awbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER);

        // When
        var actual = taxBasisHelper.getChargeDetailsMap(awbVO);

        // Then
        assertNotNull(actual);
        assertNotNull(actual.get("MKTCHG"));
        assertNotNull(actual.get("DISC"));
        assertNotNull(actual.get("OCDA"));
        assertNotNull(actual.get("OCDC"));
        assertNotNull(actual.get("IATACHG"));
    }

    @Test
    void getChargeDetailsMapShouldEnrichOtherCarrierChargeDetailsAsZeroWhenOnlyAgent() {
        // Given
        var awbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER);
        var agentCharge = getCcaChargeWithoutTypeAndValue();
        agentCharge.setDueAgent(true);
        agentCharge.setCharge(getMoney(34.0, "USD"));
        awbVO.setAwbCharges(List.of(agentCharge));

        assertDoesNotThrow(() -> taxBasisHelper.getChargeDetailsMap(awbVO));
    }

    @Test
    void getChargeDetailsMapShouldEnrichOtherAgentChargeDetailsAsZeroWhenOnlyCarrier() {
        // Given
        var awbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER);
        var carrierCharge = getCcaChargeWithoutTypeAndValue();
        carrierCharge.setDueCarrier(true);
        carrierCharge.setCharge(getMoney(34.0, "USD"));
        awbVO.setAwbCharges(List.of(carrierCharge));

        assertDoesNotThrow(() -> taxBasisHelper.getChargeDetailsMap(awbVO));
    }
}