package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.calculators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.icargo.ebl.nbridge.types.tax.TaxConfigurationData;
import com.ibsplc.icargo.ebl.nbridge.types.tax.TaxConfigurationDetailsData;
import com.ibsplc.icargo.ebl.nbridge.types.tax.TaxData;
import com.ibsplc.icargo.ebl.nbridge.types.tax.TaxResponse;
import com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper.TaxBasisHelper;
import com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper.TaxConfigurationHelper;
import com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper.TaxParameterHelper;
import com.ibsplc.neoicargo.cca.constants.TaxFilterConstant;
import com.ibsplc.neoicargo.cca.mapper.CcaTaxMapper;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.PricingChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.TaxFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.QUANTITIES;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getLoginProfile;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getRateClassJson;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getTaxFilterVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

class TaxesAndCommissionCalculatorTest {

    @Mock
    private ServiceProxy<AbstractVO> serviceProxy;

    @Mock
    private ContextUtil contextUtil;

    @Mock
    private ObjectMapper mockObjectMapper;

    @Mock
    private TaxBasisHelper taxBasisHelper;

    @Mock
    private TaxConfigurationHelper taxConfigurationHelper;

    @Mock
    private TaxParameterHelper taxParameterHelper;

    @InjectMocks
    private TaxesAndCommissionCalculator taxCalculator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(taxCalculator, "eblUrl", "http://localhost:9591/ebl-nbridge/v1/");
        var ccaTaxMapper = MockQuantity.injectMapper(QUANTITIES, CcaTaxMapper.class);
        ReflectionTestUtils.setField(taxCalculator, "ccaTaxMapper", ccaTaxMapper);

        doReturn(getLoginProfile()).when(contextUtil).callerLoginProfile();
        doReturn(getRateClassJson()).when(mockObjectMapper).createObjectNode();
    }

    @Test
    void computeTaxShouldReturnComputedTaxes() {
        // Given
        var taxFilterVO = getTaxFilterVO();
        var taxResponse = constructTaxData(taxFilterVO);
        var ccaAwbVO = getCcaAwbVO();

        // When
        doReturn(taxResponse)
                .when(serviceProxy).dispatch(isA(String.class), eq(HttpMethod.POST), isA(AbstractVO.class), eq(TaxResponse.class));
        doReturn(new HashMap<String, PricingChargeDetailsVO>())
                .when(taxBasisHelper).getChargeDetailsMap(ccaAwbVO);
        doNothing().when(taxConfigurationHelper).populateConfigurationDetails(taxFilterVO);
        doNothing().when(taxParameterHelper).populateParameters(ccaAwbVO, taxFilterVO);

        // Then
        var taxDetailsCollection = assertDoesNotThrow(() -> taxCalculator.computeTax(ccaAwbVO));
        assertNotNull(taxDetailsCollection);

    }

    @Test
    void computeTaxShouldReturnEmptyTaxCollection() {
        // Given
        var taxFilterVO = getTaxFilterVO();
        var taxResponse = constructTaxData(taxFilterVO);
        var ccaAwbVO = getCcaAwbVO();
        taxFilterVO.setMasterDocumentNumber("66660001");
        ccaAwbVO.setMasterDocumentNumber("66660001");

        // When
        doReturn(taxResponse)
                .when(serviceProxy).dispatch(isA(String.class), eq(HttpMethod.POST), isA(AbstractVO.class), eq(TaxResponse.class));
        doReturn(new HashMap<String, PricingChargeDetailsVO>())
                .when(taxBasisHelper).getChargeDetailsMap(ccaAwbVO);
        doNothing().when(taxConfigurationHelper).populateConfigurationDetails(taxFilterVO);
        doNothing().when(taxParameterHelper).populateParameters(ccaAwbVO, taxFilterVO);

        var taxDetailsCollection = taxCalculator.computeTax(ccaAwbVO);

        // Then
        assertTrue(taxDetailsCollection.isEmpty());
    }

    @Test
    void computeTaxShouldProcessedTaxMapReceivedWithoutSpotDetails() {
        // Given
        var taxFilterVO = getTaxFilterVO();
        var taxResponse = constructTaxData(taxFilterVO);
        var ccaAwbVO = getCcaAwbVO();

        // When
        doReturn(taxResponse).when(serviceProxy).dispatch(isA(String.class), eq(HttpMethod.POST), isA(AbstractVO.class),
                eq(TaxResponse.class));
        doReturn(new HashMap<String, PricingChargeDetailsVO>()).when(taxBasisHelper)
                .getChargeDetailsMap(ccaAwbVO);
        doNothing().when(taxConfigurationHelper).populateConfigurationDetails(taxFilterVO);
        doNothing().when(taxParameterHelper).populateParameters(ccaAwbVO, taxFilterVO);

        // When + Then
        ccaAwbVO.setSpotRateStatus("N");
        assertDoesNotThrow(() -> taxCalculator.computeTax(ccaAwbVO));
        ccaAwbVO.setSpotRateStatus("APR");
        assertDoesNotThrow(() -> taxCalculator.computeTax(ccaAwbVO));
        ccaAwbVO.setSpotRateStatus(null);
        assertDoesNotThrow(() -> taxCalculator.computeTax(ccaAwbVO));
        ccaAwbVO.setSpotRateId(null);
        assertDoesNotThrow(() -> taxCalculator.computeTax(ccaAwbVO));
    }

    @Test
    void computeTaxShouldConstructTaxFilterBasedOnDate() {
        // Given
        var taxFilterVO = getTaxFilterVO();
        var taxResponse = constructTaxData(taxFilterVO);
        var ccaAwbVO = getCcaAwbVO();

        // When
        doReturn(taxResponse).when(serviceProxy).dispatch(isA(String.class), eq(HttpMethod.POST), isA(AbstractVO.class),
                eq(TaxResponse.class));
        doReturn(new HashMap<String, PricingChargeDetailsVO>()).when(taxBasisHelper)
                .getChargeDetailsMap(ccaAwbVO);
        doNothing().when(taxConfigurationHelper).populateConfigurationDetails(taxFilterVO);
        doNothing().when(taxParameterHelper).populateParameters(ccaAwbVO, taxFilterVO);

        // When + Then
        ccaAwbVO.setCaptureDate(LocalDateTime.now());
        assertDoesNotThrow(() -> taxCalculator.computeTax(ccaAwbVO));
        ccaAwbVO.setExecutionDate(LocalDate.now());
        assertDoesNotThrow(() -> taxCalculator.computeTax(ccaAwbVO));
    }

    @Test
    void computeTaxShouldReturnEmptyTaxesWhenThrowsSystemExceptionBecauseTaxEnrichmentFails() {
        // Given
        var taxFilterVO = getTaxFilterVO();
        var ccaAwbVO = getCcaAwbVO();
        var exception = new SystemException("EBL_TRF_002", "Unable to compute Tax!");

        // When
        doReturn(new HashMap<String, PricingChargeDetailsVO>()).when(taxBasisHelper)
                .getChargeDetailsMap(ccaAwbVO);
        doNothing().when(taxConfigurationHelper).populateConfigurationDetails(taxFilterVO);
        doNothing().when(taxParameterHelper).populateParameters(ccaAwbVO, taxFilterVO);
        doThrow(exception).when(serviceProxy).dispatch(isA(String.class), eq(HttpMethod.POST), isA(AbstractVO.class),
                eq(TaxResponse.class));

        // Then
        var taxDetailsVOCollection = taxCalculator.computeTax(ccaAwbVO);
        assertTrue(taxDetailsVOCollection.isEmpty());
    }

    private CcaAwbVO getCcaAwbVO() {
        return getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
    }

    private TaxResponse constructTaxData(TaxFilterVO taxFilterVO) {
        var taxMap = new HashMap<String, Collection<TaxData>>();

        var configurationTAXCodes = List.of(TaxFilterConstant.CONFIGURATIONCODE_STOCDC,
                TaxFilterConstant.CONFIGURATIONCODE_STOCDA_DA, TaxFilterConstant.CONFIGURATIONCODE_STOCDA_DC,
                TaxFilterConstant.CONFIGURATIONCODE_STEXPOCDA, TaxFilterConstant.CONFIGURATIONCODE_STRECDISC,
                TaxFilterConstant.CONFIGURATIONCODE_STEXPDISC, TaxFilterConstant.CONFIGURATIONCODE_STCOM,
                TaxFilterConstant.CONFIGURATIONCODE_STDISC, TaxFilterConstant.CONFIGURATIONCODE_ST,
                TaxFilterConstant.CONFIGURATIONCODE_STEXPCOM, TaxFilterConstant.CONFIGURATIONCODE_STFC,
                TaxFilterConstant.CONFIGURATIONCODE_STRECOCDA, TaxFilterConstant.CONFIGURATIONCODE_STRECCOM);

        var taxDataTAX = new ArrayList<TaxData>();
        configurationTAXCodes.forEach(configurationCode -> {
            var taxData1 = new TaxData();
            taxData1.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TAX);
            taxData1.setConfigurationCode(configurationCode);
            taxData1.setCcTaxableAmount(3100.0);
            taxData1.setPpTaxableAmount(200.0);
            taxData1.setPpTaxAmount(100.0);
            taxData1.setCcTaxAmount(200.0);
            taxData1.setTaxableAmount(3300.0);

            var taxConfigurationData1 = new TaxConfigurationData();
            var taxConfigurationDetailsData1 = new TaxConfigurationDetailsData();
            taxConfigurationDetailsData1.setPercentageValue("2.5");
            taxConfigurationData1.setTaxDetails(taxConfigurationDetailsData1);
            taxConfigurationData1.setTaxCodeType("ST");
            taxConfigurationData1.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION);
            taxData1.setTaxConfigurationData(taxConfigurationData1);
            taxDataTAX.add(taxData1);

            var taxData2 = new TaxData();
            taxData2.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TAX);
            taxData2.setConfigurationCode(configurationCode);
            taxData2.setCcTaxableAmount(3100.0);
            taxData2.setPpTaxableAmount(200.0);
            taxData2.setPpTaxAmount(100.0);
            taxData2.setCcTaxAmount(200.0);
            taxData2.setTaxableAmount(3300.0);
            taxData2.setTaxConfigurationData(null);
            taxDataTAX.add(taxData2);

            var taxData3 = new TaxData();
            taxData3.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TAX);
            taxData3.setConfigurationCode(configurationCode);
            taxData3.setCcTaxableAmount(3100.0);
            taxData3.setPpTaxableAmount(200.0);
            taxData3.setPpTaxAmount(100.0);
            taxData3.setCcTaxAmount(200.0);
            taxData3.setTaxableAmount(3300.0);

            var taxConfigurationData3 = new TaxConfigurationData();
            taxData3.setTaxConfigurationData(taxConfigurationData3);
            taxDataTAX.add(taxData3);

            var taxData4 = new TaxData();
            taxData4.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TAX);
            taxData4.setConfigurationCode(configurationCode);
            taxData4.setCcTaxableAmount(3100.0);
            taxData4.setPpTaxableAmount(200.0);
            taxData4.setPpTaxAmount(100.0);
            taxData4.setCcTaxAmount(200.0);
            taxData4.setTaxableAmount(3300.0);

            var taxConfigurationDetailsData4 = new TaxConfigurationDetailsData();
            taxConfigurationDetailsData4.setPercentageValue(null);
            var taxConfigurationData4 = new TaxConfigurationData();
            taxConfigurationData4.setTaxDetails(taxConfigurationDetailsData4);
            taxData4.setTaxConfigurationData(taxConfigurationData4);

            taxDataTAX.add(taxData4);
        });

        taxMap.put(TaxFilterConstant.CONFIGURATIONTYPE_TAX, taxDataTAX);
        var taxDataTDS = new ArrayList<TaxData>();

        var configurationTDSCodes = List.of(TaxFilterConstant.CONFIGURATIONCODE_TDSDUEAGT, TaxFilterConstant.CONFIGURATIONCODE_TDSDUECAR);

        configurationTDSCodes.forEach(configurationCode -> {
            var taxData1 = new TaxData();
            taxData1.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS);
            taxData1.setConfigurationCode(configurationCode);
            taxData1.setCcTaxableAmount(3100.0);
            taxData1.setPpTaxableAmount(200.0);
            taxData1.setPpTaxAmount(100.0);
            taxData1.setCcTaxAmount(200.0);
            taxData1.setTaxableAmount(0.0);
            var taxConfigurationData1 = new TaxConfigurationData();
            var taxConfigurationDetailsData1 = new TaxConfigurationDetailsData();
            taxConfigurationDetailsData1.setPercentageValue(null);
            taxConfigurationData1.setTaxDetails(taxConfigurationDetailsData1);
            taxConfigurationData1.setTaxCodeType("ST");
            taxConfigurationData1.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION);
            taxData1.setTaxConfigurationData(taxConfigurationData1);
            taxDataTDS.add(taxData1);

            var taxData2 = new TaxData();
            taxData2.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS);
            taxData2.setConfigurationCode(configurationCode);
            taxData2.setCcTaxableAmount(3100.0);
            taxData2.setPpTaxableAmount(200.0);
            taxData2.setPpTaxAmount(100.0);
            taxData2.setCcTaxAmount(200.0);
            taxData2.setTaxableAmount(3300.0);

            var taxConfigurationData2 = new TaxConfigurationData();
            var taxConfigurationDetailsData2 = new TaxConfigurationDetailsData();
            taxConfigurationDetailsData2.setPercentageValue("");
            taxConfigurationData2.setTaxDetails(taxConfigurationDetailsData2);
            taxConfigurationData2.setTaxCodeType("ST");
            taxConfigurationData2.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS);
            taxData2.setTaxConfigurationData(taxConfigurationData2);
            taxDataTDS.add(taxData2);
        });

        taxMap.put(TaxFilterConstant.CONFIGURATIONTYPE_TDS, taxDataTDS);

        var taxDataTDSEXMP = new ArrayList<TaxData>();

        configurationTDSCodes.forEach(configurationCode -> {
            var taxData1 = new TaxData();
            taxData1.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION);
            taxData1.setConfigurationCode(configurationCode);
            taxData1.setCcTaxableAmount(3100.0);
            taxData1.setPpTaxableAmount(200.0);
            taxData1.setPpTaxAmount(100.0);
            taxData1.setCcTaxAmount(200.0);
            taxData1.setTaxableAmount(3300.0);

            var taxConfigurationData1 = new TaxConfigurationData();
            var taxConfigurationDetailsData1 = new TaxConfigurationDetailsData();
            taxConfigurationDetailsData1.setPercentageValue("");
            taxConfigurationData1.setTaxDetails(taxConfigurationDetailsData1);
            taxConfigurationData1.setTaxCodeType("ST");
            taxConfigurationData1.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION);
            taxData1.setTaxConfigurationData(taxConfigurationData1);
            taxDataTDSEXMP.add(taxData1);

            var taxData2 = new TaxData();
            taxData2.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION);
            taxData2.setConfigurationCode(configurationCode);
            taxData2.setCcTaxableAmount(3100.0);
            taxData2.setPpTaxableAmount(200.0);
            taxData2.setPpTaxAmount(100.0);
            taxData2.setCcTaxAmount(200.0);
            taxData2.setTaxableAmount(3300.0);
            taxData2.setTaxConfigurationData(null);
            taxDataTDSEXMP.add(taxData2);

            var taxData3 = new TaxData();
            taxData3.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION);
            taxData3.setConfigurationCode(configurationCode);
            taxData3.setCcTaxableAmount(3100.0);
            taxData3.setPpTaxableAmount(200.0);
            taxData3.setPpTaxAmount(100.0);
            taxData3.setCcTaxAmount(200.0);
            taxData3.setTaxableAmount(3300.0);
            taxData3.setTaxConfigurationData(new TaxConfigurationData());
            taxDataTDSEXMP.add(taxData3);

            var taxData4 = new TaxData();
            taxData4.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION);
            taxData4.setConfigurationCode(configurationCode);
            taxData4.setCcTaxableAmount(3100.0);
            taxData4.setPpTaxableAmount(200.0);
            taxData4.setPpTaxAmount(100.0);
            taxData4.setCcTaxAmount(200.0);
            taxData4.setTaxableAmount(3300.0);
            var taxConfigurationData4 = new TaxConfigurationData();
            var taxConfigurationDetailsData4 = new TaxConfigurationDetailsData();
            taxConfigurationDetailsData4.setPercentageValue("2.5");
            taxConfigurationData4.setTaxDetails(taxConfigurationDetailsData4);
            taxData4.setTaxConfigurationData(taxConfigurationData4);
            taxDataTDSEXMP.add(taxData4);

            var taxData5 = new TaxData();
            taxData5.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION);
            taxData5.setConfigurationCode(configurationCode);
            taxData5.setCcTaxableAmount(3100.0);
            taxData5.setPpTaxableAmount(200.0);
            taxData5.setPpTaxAmount(100.0);
            taxData5.setCcTaxAmount(200.0);
            taxData5.setTaxableAmount(3300.0);
            var taxConfigurationData5 = new TaxConfigurationData();
            var taxConfigurationDetailsData5 = new TaxConfigurationDetailsData();
            taxConfigurationDetailsData5.setPercentageValue(null);
            taxConfigurationData5.setTaxDetails(taxConfigurationDetailsData5);
            taxData5.setTaxConfigurationData(taxConfigurationData5);
            taxDataTDSEXMP.add(taxData5);

            var taxData6 = new TaxData();
            taxData6.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION);
            taxData6.setConfigurationCode(configurationCode);
            taxData6.setCcTaxableAmount(3100.0);
            taxData6.setPpTaxableAmount(200.0);
            taxData6.setPpTaxAmount(100.0);
            taxData6.setCcTaxAmount(200.0);
            taxData6.setTaxableAmount(0.0);
            var taxConfigurationData6 = new TaxConfigurationData();
            var taxConfigurationDetailsData6 = new TaxConfigurationDetailsData();
            taxConfigurationDetailsData6.setPercentageValue("");
            taxConfigurationData6.setTaxDetails(taxConfigurationDetailsData6);
            taxData6.setTaxConfigurationData(taxConfigurationData6);
            taxDataTDSEXMP.add(taxData6);
        });

        taxMap.put(TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION, taxDataTDSEXMP);
        taxMap.put(TaxFilterConstant.CONFIGURATIONTYPE_COMMISION, taxDataTDSEXMP);
        taxMap.put("ERR", taxDataTDS);

        var key = String.join("-", taxFilterVO.getCompanyCode(),
                taxFilterVO.getShipmentPrefix(), taxFilterVO.getMasterDocumentNumber());

        var taxKey = new HashMap<String, Map<String, Collection<TaxData>>>();
        taxKey.put(key, taxMap);

        var taxResponse = new TaxResponse();
        taxResponse.setTaxMap(taxKey);

        return taxResponse;
    }
}