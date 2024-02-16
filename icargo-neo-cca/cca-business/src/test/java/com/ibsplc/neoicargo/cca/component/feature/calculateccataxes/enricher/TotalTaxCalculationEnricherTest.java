package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.enricher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibsplc.neoicargo.businessrules.client.category.Action;
import com.ibsplc.neoicargo.businessrules.client.category.OutputParameter;
import com.ibsplc.neoicargo.businessrules.client.util.RuleClient;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaTaxDetailsVO;
import com.ibsplc.neoicargo.framework.core.context.tenant.config.HierarchicalResourceLoader;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.io.ClassPathResource;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.CCA_REFERENCE_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_N;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_Y;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RULE_FILE_EXTENSION;
import static com.ibsplc.neoicargo.cca.constants.CcaTaxCommissionConstants.TOTAL_TAX_FORMULA_NAME;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONTYPE_COMMISION;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONTYPE_TAX;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONTYPE_TDS;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
class TotalTaxCalculationEnricherTest {

    private static final String PATH_PARAM = "config/rules/";

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private HierarchicalResourceLoader hierarchicalResourceLoader;

    @Mock
    private RuleClient client;

    @InjectMocks
    private TotalTaxCalculationEnricher totalTaxCalculationEnricher;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        var totalTaxClassPathResource = new ClassPathResource(PATH_PARAM + TOTAL_TAX_FORMULA_NAME + RULE_FILE_EXTENSION);
        doReturn(List.of(totalTaxClassPathResource))
                .when(hierarchicalResourceLoader).discoverDeploymentResources(any(String.class), Mockito.anyList());
    }

    @Test
    void enrichShouldNotCalculateWhenAutoCalculateTaxIsNo() throws BusinessException {
        // Given
        var taxAmount = 100.00;
        var ccaMasterVO = getCcaMasterWithRevisedAwbAndTaxesAndAutoCalculatedTotal();
        ccaMasterVO.setAutoCalculateTax(FLAG_N);
        ccaMasterVO.getRevisedShipmentVO().setTaxAmount(taxAmount);

        // When
        totalTaxCalculationEnricher.enrich(ccaMasterVO);

        // Then
        verify(client, never()).fireRule(eq(TOTAL_TAX_FORMULA_NAME), any(), any(Boolean.class));
        verify(client, never()).fireRules(eq(TOTAL_TAX_FORMULA_NAME), any(), any(Boolean.class));
        assertEquals(taxAmount, ccaMasterVO.getRevisedShipmentVO().getTaxAmount());
    }

    @Test
    void enrichShouldNotSetTaxDetailsWhenGetEmptyParametersOfTotalTaxFormula() throws BusinessException {
        // Given
        var actions = getRuleActions("26.00", "serviceTaxFreightCharge + serviceTaxOCDC + serviceTaxOCDA + noKey");
        var ccaMasterVO = getCcaMasterWithRevisedAwbAndTaxesAndAutoCalculatedTotal();

        // When
        doReturn(actions)
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));
        doReturn(List.of())
                .when(hierarchicalResourceLoader).discoverDeploymentResources(anyString(), anyList());

        // When + Then
        assertDoesNotThrow(() -> totalTaxCalculationEnricher.enrich(ccaMasterVO));
        var revisedAwb = ccaMasterVO.getRevisedShipmentVO();
        assertEquals(26.0, revisedAwb.getTaxAmount());
        var displayTaxDetails = (ObjectNode) revisedAwb.getDisplayTaxDetails();
        assertTrue(displayTaxDetails.isEmpty());
    }

    @Test
    void enrichShouldNotSetTaxDetailsWhenCannotGetParametersOfTotalTaxFormula() throws BusinessException {
        // Given
        var actions = getRuleActions("300.00", "serviceTaxFreightCharge + serviceTaxOCDC + serviceTaxOCDA");
        var ccaMasterVO = getCcaMasterWithRevisedAwbAndTaxesAndAutoCalculatedTotal();

        // When
        doReturn(actions)
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));
        doThrow(new RuntimeException())
                .when(hierarchicalResourceLoader).discoverDeploymentResources(anyString(), anyList());

        // When + Then
        assertDoesNotThrow(() -> totalTaxCalculationEnricher.enrich(ccaMasterVO));
        var revisedAwb = ccaMasterVO.getRevisedShipmentVO();
        assertEquals(300.0, revisedAwb.getTaxAmount());
        var displayTaxDetails = (ObjectNode) revisedAwb.getDisplayTaxDetails();
        assertTrue(displayTaxDetails.isEmpty());
    }

    @Test
    void enrichShouldNotSetTaxDetailsWhenCannotReadTaxDetails() throws BusinessException {
        // Given
        var actions = getRuleActions("26.00", "serviceTaxFreightCharge + serviceTaxOCDC + serviceTaxOCDA");
        var ccaMasterVO = getCcaMasterWithRevisedAwbAndTaxesAndAutoCalculatedTotal();

        // When
        doReturn(actions)
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));
        doThrow(new RuntimeException())
                .when(objectMapper).convertValue(any(), eq(TreeMap.class));

        // When + Then
        assertDoesNotThrow(() -> totalTaxCalculationEnricher.enrich(ccaMasterVO));
        var revisedAwb = ccaMasterVO.getRevisedShipmentVO();
        assertEquals(26.0, revisedAwb.getTaxAmount());
        var displayTaxDetails = (ObjectNode) revisedAwb.getDisplayTaxDetails();
        assertTrue(displayTaxDetails.isEmpty());
    }

    @Test
    void enrichShouldNotSetTaxDetailsWhenRuleActionsHasNoSecondOutputParameter() throws BusinessException {
        // Given
        var firstOutput = new OutputParameter();
        firstOutput.setName("formula");
        firstOutput.setValue("26.00");
        var action = new Action();
        action.setName("TotalTax");
        action.setParams(List.of(firstOutput));

        var ccaMasterVO = getCcaMasterWithRevisedAwbAndTaxesAndAutoCalculatedTotal();

        // When
        doReturn(List.of(action))
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));

        // When + Then
        assertDoesNotThrow(() -> totalTaxCalculationEnricher.enrich(ccaMasterVO));
        var revisedAwb = ccaMasterVO.getRevisedShipmentVO();
        assertEquals(26.0, revisedAwb.getTaxAmount());
        var displayTaxDetails = (ObjectNode) revisedAwb.getDisplayTaxDetails();
        assertTrue(displayTaxDetails.isEmpty());
    }

    @Test
    void enrichShouldNotSetTotalTaxAndTaxDetailsWhenRuleActionsAreEmpty() throws BusinessException {
        // Given
        var ccaMasterVO = getCcaMasterWithRevisedAwbAndTaxesAndAutoCalculatedTotal();
        var taxAmount = 100.00;
        ccaMasterVO.getRevisedShipmentVO().setTaxAmount(taxAmount);

        // When
        doReturn(null)
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));

        // When + Then
        assertDoesNotThrow(() -> totalTaxCalculationEnricher.enrich(ccaMasterVO));
        var revisedAwb = ccaMasterVO.getRevisedShipmentVO();
        assertEquals(0.0, revisedAwb.getTaxAmount());
        assertNull(revisedAwb.getDisplayTaxDetails());
    }

    @Test
    void enrichShouldSetTotalTaxAndTaxDetails() throws BusinessException {
        // Given
        var actions = getRuleActions("26.00", "serviceTaxFreightCharge + serviceTaxOCDC + serviceTaxOCDA + noKey");
        var ccaMasterVO = getCcaMasterWithRevisedAwbAndTaxesAndAutoCalculatedTotal();

        // When
        doReturn(actions)
                .when(client).fireRule(any(String.class), any(CcaAwbVO.class), any(Boolean.class));

        assertDoesNotThrow(() -> totalTaxCalculationEnricher.enrich(ccaMasterVO));
        var revisedAwb = ccaMasterVO.getRevisedShipmentVO();
        assertEquals(26.0, revisedAwb.getTaxAmount());
        var displayTaxDetails = (ObjectNode) revisedAwb.getDisplayTaxDetails();
        assertEquals(3, displayTaxDetails.size());
    }

    private CCAMasterVO getCcaMasterWithRevisedAwbAndTaxesAndAutoCalculatedTotal() {
        var ccaAwbTaxes = getCcaAwbTaxDetailsVOs();
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        ccaAwbVO.setAwbTaxes(ccaAwbTaxes);
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);
        ccaMasterVO.setAutoCalculateTax(FLAG_Y);

        return ccaMasterVO;
    }

    private List<CcaTaxDetailsVO> getCcaAwbTaxDetailsVOs() {
        var awbTaxCOM = new CcaTaxDetailsVO();
        awbTaxCOM.setConfigurationType(CONFIGURATIONTYPE_COMMISION);

        var awbTaxTDS = new CcaTaxDetailsVO();
        awbTaxTDS.setConfigurationType(CONFIGURATIONTYPE_TDS);

        var taxValue = 0.0;
        var taxDetails = new TreeMap<String, Double>();
        taxDetails.put("serviceTaxFreightCharge", taxValue);
        taxDetails.put("serviceTaxOCDC", taxValue);
        taxDetails.put("serviceTaxOCDA", taxValue);
        taxDetails.put("serviceTaxFreightChargeDiscount", taxValue);
        taxDetails.put("serviceTaxFreight_TaxPercentage", taxValue);
        taxDetails.put("serviceTaxFreight_TaxDueFlag", taxValue);

        var awbTaxTAX = new CcaTaxDetailsVO();
        awbTaxTAX.setConfigurationType(CONFIGURATIONTYPE_TAX);
        awbTaxTAX.setTaxDetails(taxDetails);

        return List.of(awbTaxCOM, awbTaxTDS, awbTaxTAX);
    }

    private List<Action> getRuleActions(String taxValue, String taxFormula) {
        var firstOutput = new OutputParameter();
        firstOutput.setName("formula");
        firstOutput.setValue(taxValue);

        var secondOutput = new OutputParameter();
        secondOutput.setName("evaluatedFormula");
        secondOutput.setValue(taxFormula);

        var action = new Action();
        action.setName("TotalTax");
        action.setParams(List.of(firstOutput, secondOutput));

        return List.of(action);
    }
}