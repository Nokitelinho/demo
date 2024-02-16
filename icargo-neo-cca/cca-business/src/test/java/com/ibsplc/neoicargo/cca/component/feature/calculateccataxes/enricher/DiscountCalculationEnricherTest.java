package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.enricher;

import com.ibsplc.neoicargo.businessrules.client.category.Action;
import com.ibsplc.neoicargo.businessrules.client.category.OutputParameter;
import com.ibsplc.neoicargo.businessrules.client.util.RuleClient;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.CCA_REFERENCE_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class DiscountCalculationEnricherTest {

    @Mock
    private RuleClient client;

    @InjectMocks
    private DiscountCalculationEnricher discountCalculationEnricher;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enrichShouldSetDiscountFromRuleActions() throws BusinessException {
        // Given
        var actions = getRuleActions("50.00");
        var ccaMasterVO = getCcaMasterWithRevisedAwb();

        // When
        doReturn(actions)
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));

        // When + Then
        assertDoesNotThrow(() -> discountCalculationEnricher.enrich(ccaMasterVO));
        assertEquals(50.0, ccaMasterVO.getRevisedShipmentVO().getDiscountAmount());
    }

    @Test
    void enrichShouldSetZeroDiscountWhenRuleActionsEmpty() throws BusinessException {
        // Given
        var ccaMasterVO = getCcaMasterWithRevisedAwb();

        // When
        doReturn(List.of())
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));

        // When + Then
        assertDoesNotThrow(() -> discountCalculationEnricher.enrich(ccaMasterVO));
        assertEquals(0.0, ccaMasterVO.getRevisedShipmentVO().getDiscountAmount());
    }

    @Test
    void enrichShouldSetZeroDiscountWhenRuleActionsNull() throws BusinessException {
        // Given
        var ccaMasterVO = getCcaMasterWithRevisedAwb();

        // When
        doReturn(null)
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));

        // When + Then
        assertDoesNotThrow(() -> discountCalculationEnricher.enrich(ccaMasterVO));
        assertEquals(0.0, ccaMasterVO.getRevisedShipmentVO().getDiscountAmount());
    }

    @Test
    void enrichShouldSetZeroDiscountWhenRuleActionWithoutOutputParameters() throws BusinessException {
        // Given
        var ccaMasterVO = getCcaMasterWithRevisedAwb();

        // When
        doReturn(List.of(new Action()))
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));

        // When + Then
        assertDoesNotThrow(() -> discountCalculationEnricher.enrich(ccaMasterVO));
        assertEquals(0.0, ccaMasterVO.getRevisedShipmentVO().getDiscountAmount());
    }

    @Test
    void enrichShouldSetZeroDiscountWhenRuleActionsValueIsWrong() throws BusinessException {
        // Given
        var actions = getRuleActions("not_double");
        var ccaMasterVO = getCcaMasterWithRevisedAwb();

        // When
        doReturn(actions)
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));

        // When + Then
        assertDoesNotThrow(() -> discountCalculationEnricher.enrich(ccaMasterVO));
        assertEquals(0.0, ccaMasterVO.getRevisedShipmentVO().getDiscountAmount());
    }

    private CCAMasterVO getCcaMasterWithRevisedAwb() {
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);

        return ccaMasterVO;
    }

    private List<Action> getRuleActions(String discountValue) {
        var firstOutput = new OutputParameter();
        firstOutput.setName("formula");
        firstOutput.setValue(discountValue);

        var action = new Action();
        action.setName("discount");
        action.setParams(List.of(firstOutput));

        return List.of(action);
    }
}