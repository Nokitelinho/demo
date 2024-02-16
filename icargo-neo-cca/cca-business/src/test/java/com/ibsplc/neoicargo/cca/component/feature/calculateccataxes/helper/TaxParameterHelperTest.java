package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.cca.constants.CcaTaxCommissionConstants;
import com.ibsplc.neoicargo.cca.constants.TaxFilterConstant;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerBusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getRateClassJson;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getTaxFilterVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

class TaxParameterHelperTest {

    @InjectMocks
    private TaxParameterHelper taxParameterHelper;

    @Mock
    private CustomerComponent customerComponent;

    @Spy
    private ObjectMapper objectMapperMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionIfJsonProcessingFails() throws BusinessException, JsonProcessingException {
        // Given
        var ccaAwbVO = getCcaAwbVO();
        var taxFilterVO = getTaxFilterVO();

        // When
        doReturn(List.of(getCustomerModel()))
                .when(customerComponent).getCustomerDetails(any(String.class));
        doThrow(JsonProcessingException.class)
                .when(objectMapperMock).readTree(any(String.class));

        // Then
        assertDoesNotThrow(() -> taxParameterHelper.populateParameters(ccaAwbVO, taxFilterVO));
    }

    @Test
    void shouldNotSetCustomerDataWhenCustomerNotFound() throws JsonProcessingException, BusinessException {
        // Given
        var ccaAwbVO = getCcaAwbVO();
        var taxFilterVO = getTaxFilterVO();

        // When
        doReturn(getRateClassJson())
                .when(objectMapperMock).readTree(any(String.class));
        doThrow(CustomerBusinessException.class)
                .when(customerComponent).getCustomerDetails(any(String.class));

        // Then
        taxParameterHelper.populateParameters(ccaAwbVO, taxFilterVO);
        assertNull(taxFilterVO.getCountryCode());
        var actualParams = taxFilterVO.getParameterMap();
        assertNull(actualParams.get(TaxFilterConstant.PARAMETERCODE_AGTCOD));
        assertNull(actualParams.get(TaxFilterConstant.PARAMETERCODE_STNCOD));
        assertNull(actualParams.get(TaxFilterConstant.PARAMETERCODE_CTLCUSCOD));
        assertNull(actualParams.get(TaxFilterConstant.PARAMETERCODE_OWNSAL));
        assertNull(actualParams.get(TaxFilterConstant.PARAMETERCODE_AGTCAT));
    }

    @Test
    void shouldEnrichParametersForNullCustomerAndAwbRatesValues() throws BusinessException, JsonProcessingException {
        // Given
        var ccaAwbVO = getCcaAwbVO();
        var taxFilterVO = getTaxFilterVO();
        var json = getRateClassJson();

        // When
        doReturn(List.of(new CustomerModel()))
                .when(customerComponent).getCustomerDetails(any(String.class));
        doReturn(json)
                .when(objectMapperMock).readTree(any(String.class));

        ccaAwbVO.setAwbRates(null);
        ccaAwbVO.setExportBillingStatus(CcaTaxCommissionConstants.BLG_STA_IMP);

        assertDoesNotThrow(() -> taxParameterHelper.populateParameters(ccaAwbVO, taxFilterVO));
    }

    @Test
    void shouldEnrichParametersForNullValues() throws BusinessException {
        // Given
        var ccaAwbVO = getCcaAwbVO();
        var taxFilterVO = getTaxFilterVO();
        var json = getRateClassJson();

        ccaAwbVO.setChargeableWeight(null);
        ccaAwbVO.setExportBillingStatus(null);
        ccaAwbVO.setImportBillingStatus(null);
        ccaAwbVO.setSpotRateStatus(null);
        ccaAwbVO.setAgentCode(null);

        // When
        doReturn(List.of(new CustomerModel()))
                .when(customerComponent).getCustomerDetails(any(String.class));
        doReturn(json)
                .when(objectMapperMock).convertValue(any(), eq(JsonNode.class));

        // Then
        assertDoesNotThrow(() -> taxParameterHelper.populateParameters(ccaAwbVO, taxFilterVO));
        assertNotNull(taxFilterVO.getParameterMap());
        assertNotNull(taxFilterVO.getParameterMap().get(TaxFilterConstant.PARAMETERCODE_MINRATFLG));
        assertNotNull(taxFilterVO.getParameterMap().get(TaxFilterConstant.UNIQUEREFERENCE_ONE));
    }

    private CcaAwbVO getCcaAwbVO() {
        return getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
    }

    private CustomerModel getCustomerModel() {
        var customerModel = new CustomerModel();
        customerModel.setAgentCode("AIEDXB");
        customerModel.setCustomerType("AGT");
        customerModel.setCountryCode("AE");
        customerModel.setStationCode("DXB");
        customerModel.setControllingLocationCustomerCode("AIEDXB");
        customerModel.setOwnSales("Y");

        return customerModel;
    }

}