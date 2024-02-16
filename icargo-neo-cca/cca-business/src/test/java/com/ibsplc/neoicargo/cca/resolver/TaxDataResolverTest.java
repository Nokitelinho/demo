package com.ibsplc.neoicargo.cca.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaTaxDetailsVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CGST;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CUSTOMER_CODE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.IGST;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_CLASS_IATA;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_CLASS_MARKET;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SERVICETAX_COMMISSION;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SERVICETAX_DISCOUNT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SERVICETAX_FREIGHTCHARGE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SERVICETAX_OCDA;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SERVICETAX_OCDC;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SGST;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.TDS_DUEAGENT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.TDS_DUECARRIER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnitPlatform.class)
class TaxDataResolverTest {

    @InjectMocks
    private TaxDataResolver taxDataResolver;

    @Mock
    private ObjectMapper objectMapper;

    private CcaAwbVO ccaAwbVO;

    private Collection<CcaTaxDetailsVO> awbTaxDetailsVOs;

    private ObjectNode jsonData;

    private ObjectNode jsonDataValue;

    private ObjectNode rateJsonDataValue;

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);
        jsonData = new ObjectMapper().createObjectNode();
        jsonDataValue = new ObjectMapper().createObjectNode();

        jsonDataValue.put(SERVICETAX_FREIGHTCHARGE, 10.0);
        jsonDataValue.put(SERVICETAX_OCDC, 10.0);
        jsonDataValue.put(SERVICETAX_OCDA, 10.0);
        jsonDataValue.put(SERVICETAX_COMMISSION, 10.0);
        jsonDataValue.put(SERVICETAX_DISCOUNT, 10.0);
        jsonDataValue.put(SGST, 10.0);
        jsonDataValue.put(CGST, 10.0);
        jsonDataValue.put(IGST, 10.0);
        jsonDataValue.put(TDS_DUECARRIER, 10.0);
        jsonDataValue.put(TDS_DUEAGENT, 10.0);

        rateJsonDataValue = new ObjectMapper().createObjectNode();
        rateJsonDataValue.put(CcaConstants.RATE_CLASS, "B");

        ccaAwbVO = new CcaAwbVO();
        ccaAwbVO.setShipmentPrefix("134");
        ccaAwbVO.setMasterDocumentNumber("95872365");
        ccaAwbVO.setOrigin("CDG");
        ccaAwbVO.setDestination("DXB");

        awbTaxDetailsVOs = new ArrayList<>();
        var taxDetailsVO = new CcaTaxDetailsVO();
        taxDetailsVO.setConfigurationType("TAX");
        taxDetailsVO.setTaxDetails("12.0");
        ObjectNode jsonDataValue1 = new ObjectMapper().createObjectNode();
        jsonDataValue1.put(SERVICETAX_FREIGHTCHARGE, 10.0);
        jsonDataValue1.put(SERVICETAX_OCDC, 10.0);
        jsonDataValue1.put(SERVICETAX_OCDA, 10.0);
        jsonDataValue1.put(SERVICETAX_COMMISSION, 10.0);
        jsonDataValue1.put(SERVICETAX_DISCOUNT, 10.0);
        jsonDataValue1.put(SGST, 10.0);
        jsonDataValue1.put(CGST, 10.0);
        jsonDataValue1.put(IGST, 10.0);
        jsonDataValue1.put(CUSTOMER_CODE, "AIEDXB");
        taxDetailsVO.setTaxDetails(jsonDataValue1);
        awbTaxDetailsVOs.add(taxDetailsVO);
        taxDetailsVO = new CcaTaxDetailsVO();
        taxDetailsVO.setConfigurationType("TDS");
        taxDetailsVO.setTaxDetails("12.0");
        ObjectNode jsonDataValue2 = new ObjectMapper().createObjectNode();
        jsonDataValue2.put(TDS_DUECARRIER, 10.0);
        jsonDataValue2.put(TDS_DUEAGENT, 10.0);
        taxDetailsVO.setTaxDetails(jsonDataValue2);
        awbTaxDetailsVOs.add(taxDetailsVO);

        ccaAwbVO.setAwbTaxes(awbTaxDetailsVOs);
    }

    @Test
    void shouldPopulateObjectNode() {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(jsonDataValue).when(objectMapper).convertValue(any(), eq(JsonNode.class));
        ccaAwbVO.setPayType("PP");
        ccaAwbVO.setInboundCustomerCode("AIEDXB");
        //When
        jsonData = taxDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertEquals(jsonDataValue.get(SERVICETAX_FREIGHTCHARGE), jsonData.get(SERVICETAX_FREIGHTCHARGE));
    }

    @Test
    void shouldPopulateObjectNodeWithTdsOnly() {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(jsonDataValue).when(objectMapper).convertValue(any(), eq(JsonNode.class));

        awbTaxDetailsVOs = new ArrayList<>();
        var taxDetailsVO = new CcaTaxDetailsVO();
        taxDetailsVO.setConfigurationType("TDS");
        taxDetailsVO.setTaxDetails("12.0");
        var jsonDataValue2 = new ObjectMapper().createObjectNode();
        jsonDataValue2.put(TDS_DUECARRIER, 10.0);
        jsonDataValue2.put(TDS_DUEAGENT, 10.0);
        taxDetailsVO.setTaxDetails(jsonDataValue2);
        awbTaxDetailsVOs.add(taxDetailsVO);
        ccaAwbVO.setAwbTaxes(awbTaxDetailsVOs);
        //When
        jsonData = taxDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertEquals(jsonDataValue.get(TDS_DUECARRIER), jsonData.get(TDS_DUECARRIER));
    }

    @Test
    void shouldPopulateObjectNodeWithTaxOnly() {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(jsonDataValue).when(objectMapper).convertValue(any(), eq(JsonNode.class));

        awbTaxDetailsVOs = new ArrayList<>();
        var taxDetailsVO = new CcaTaxDetailsVO();
        taxDetailsVO.setConfigurationType("TAX");
        taxDetailsVO.setTaxDetails("12.0");
        var jsonDataValue1 = new ObjectMapper().createObjectNode();
        jsonDataValue1.put(SERVICETAX_FREIGHTCHARGE, 10.0);
        jsonDataValue1.put(SERVICETAX_OCDC, 10.0);
        jsonDataValue1.put(SERVICETAX_OCDA, 10.0);
        jsonDataValue1.put(SERVICETAX_COMMISSION, 10.0);
        jsonDataValue1.put(SERVICETAX_DISCOUNT, 10.0);
        jsonDataValue1.put(SGST, 10.0);
        jsonDataValue1.put(CGST, 10.0);
        jsonDataValue1.put(IGST, 10.0);
        taxDetailsVO.setTaxDetails(jsonDataValue1);
        awbTaxDetailsVOs.add(taxDetailsVO);

        ccaAwbVO.setAwbTaxes(awbTaxDetailsVOs);
        //When
        jsonData = taxDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertEquals(jsonDataValue.get(SERVICETAX_FREIGHTCHARGE), jsonData.get(SERVICETAX_FREIGHTCHARGE));
    }

    @Test
    void shouldPopulateObjectNodeWithZeroValue() {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(jsonDataValue).when(objectMapper).convertValue(any(), eq(JsonNode.class));

        ccaAwbVO.setAwbTaxes(null);
        //When
        jsonData = taxDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertNotEquals(jsonDataValue.get(TDS_DUECARRIER), jsonData.get(TDS_DUECARRIER));
    }

    @Test
    void shouldPopulateObjectNodeWithZeroValueForEmptyAwbTax() {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(jsonDataValue).when(objectMapper).convertValue(any(), eq(JsonNode.class));

        ccaAwbVO.setAwbTaxes(new ArrayList<>());
        //When
        jsonData = taxDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertNotEquals(jsonDataValue.get(TDS_DUECARRIER), jsonData.get(TDS_DUECARRIER));
    }

    @Test
    void shouldPopulateEmptyRateClassForEmptyAWBRates() {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(jsonDataValue).when(objectMapper).convertValue(any(), eq(JsonNode.class));

        ccaAwbVO.setAwbTaxes(new ArrayList<>());
        ccaAwbVO.setAwbRates(new ArrayList<>());
        //When
        jsonData = taxDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertNotEquals(jsonDataValue.get(RATE_CLASS_IATA), jsonData.get(RATE_CLASS_IATA));
    }

    @Test
    void shouldPopulateIataRateClassForAWBRates() {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(rateJsonDataValue).when(objectMapper).convertValue(any(), eq(JsonNode.class));

        ccaAwbVO.setAwbTaxes(new ArrayList<>());
        Collection<CcaRateDetailsVO> awbRates = new ArrayList<>();
        var rate1 = new CcaRateDetailsVO();
        rate1.setRateType("IATA");
        ObjectNode jsonDataValue1 = new ObjectMapper().createObjectNode();
        jsonDataValue1.put(CcaConstants.RATE_CLASS, "B");
        rate1.setRateDetails(jsonDataValue1);
        awbRates.add(rate1);
        var rate2 = new CcaRateDetailsVO();
        rate2.setRateType("RECIATA");
        ObjectNode jsonDataValue2 = new ObjectMapper().createObjectNode();
        jsonDataValue2.put(CcaConstants.RATE_CLASS, "B");
        rate2.setRateDetails(jsonDataValue2);
        awbRates.add(rate2);
        var rate3 = new CcaRateDetailsVO();
        rate3.setRateType("IATA");
        var jsonDataValue3 = new ObjectMapper().createObjectNode();
        jsonDataValue3.put(CcaConstants.RATE_CLASS, "C");
        rate3.setRateDetails(jsonDataValue3);
        awbRates.add(rate3);
        ccaAwbVO.setAwbRates(awbRates);
        //When
        jsonData = taxDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertEquals("B,B", jsonData.get(RATE_CLASS_IATA).asText());
    }

    @Test
    void shouldPopulateMarketRateClassForAWBRates() {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(rateJsonDataValue).when(objectMapper).convertValue(any(), eq(JsonNode.class));

        ccaAwbVO.setAwbTaxes(new ArrayList<>());
        Collection<CcaRateDetailsVO> awbRates = new ArrayList<>();
        var rate1 = new CcaRateDetailsVO();
        rate1.setRateType("MKT");
        var jsonDataValue1 = new ObjectMapper().createObjectNode();
        jsonDataValue1.put(CcaConstants.RATE_CLASS, "B");
        rate1.setRateDetails(rate1);
        awbRates.add(rate1);
        ccaAwbVO.setAwbRates(awbRates);
        //When
        jsonData = taxDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertEquals(rateJsonDataValue.get(CcaConstants.RATE_CLASS), jsonData.get(RATE_CLASS_MARKET));
    }

    @Test
    void shouldPopulateEmptyCustomer() {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(jsonDataValue).when(objectMapper).convertValue(any(), eq(JsonNode.class));
        ccaAwbVO.setAwbTaxes(new ArrayList<>());
        ccaAwbVO.setAwbRates(new ArrayList<>());
        ccaAwbVO.setPayType("PP");
        ccaAwbVO.setOutboundCustomerCode(null);
        jsonData = taxDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertTrue(jsonData.get(CUSTOMER_CODE).isEmpty());
    }

    @Test
    void shouldThrowBusinessExceptionOnInvalidJsonData() throws JsonProcessingException {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doThrow(JsonProcessingException.class).when(objectMapper).readTree(any(String.class));
        jsonData = taxDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertTrue(jsonData.get(TDS_DUECARRIER).isEmpty());
    }

    @Test
    void shouldThrowBusinessExceptionOnInvalidJsonDataForRateDetail() {
        //Given
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doThrow(RuntimeException.class).when(objectMapper).convertValue(any(), eq(JsonNode.class));
        Collection<CcaRateDetailsVO> awbRates = new ArrayList<>();
        var rate1 = new CcaRateDetailsVO();
        rate1.setRateType("IATA");
        var jsonDataValue1 = new ObjectMapper().createObjectNode();
        jsonDataValue1.put(CcaConstants.RATE_CLASS, "B");
        rate1.setRateDetails(jsonDataValue1);
        awbRates.add(rate1);
        ccaAwbVO.setAwbRates(awbRates);
        // When + Then
        assertDoesNotThrow(() ->taxDataResolver.populateRateClass(jsonDataValue, ccaAwbVO));
    }
}