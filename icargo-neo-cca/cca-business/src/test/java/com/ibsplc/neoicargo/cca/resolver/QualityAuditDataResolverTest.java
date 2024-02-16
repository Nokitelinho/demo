package com.ibsplc.neoicargo.cca.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.masters.area.airport.AirportBusinessException;
import com.ibsplc.neoicargo.masters.area.airport.AirportComponent;
import com.ibsplc.neoicargo.masters.area.airport.modal.AirportModal;
import com.ibsplc.neoicargo.masters.country.CountryComponent;
import com.ibsplc.neoicargo.masters.country.modal.CountryModal;
import com.ibsplc.neoicargo.masters.customer.CustomerBusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnitPlatform.class)
class QualityAuditDataResolverTest {

    @InjectMocks
    private QualityAuditDataResolver qualityAuditDataResolver;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CustomerComponent customerComponent;

    @Mock
    private AirportComponent airportComponent;

    @Mock
    private CountryComponent countryComponent;

    @Mock
    private ContextUtil contextUtil;

    private ObjectNode jsonData;

    private ObjectNode jsonDataValue;

    private List<CustomerModel> customerList;

    private AirportModal aiportModal;

    private CountryModal countryModal;

    private LoginProfile profile;

    private CcaAwbVO ccaAwbVO;

    private CustomerModel customerModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jsonData = new ObjectMapper().createObjectNode();
        jsonDataValue = new ObjectMapper().createObjectNode();
        fillJsonDataValue();

        customerList = getCustomerList();

        profile = new LoginProfile();
        profile.setCompanyCode("AV");
        profile.setOwnAirlineIdentifier(1134);

        aiportModal = new AirportModal();
        aiportModal.setCountryCode("IN");
        aiportModal.setCityCode("PAR");

        countryModal = new CountryModal();
        countryModal.setRegionCode("SAS");
        countryModal.setCountryCode("IN");

        ccaAwbVO = getMockCcaAwb();
    }

    @Test
    void shouldPopulateObjectNode() throws BusinessException {
        //Given
        doReturn(aiportModal).when(airportComponent).validateAirport(any(String.class));
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(customerList).when(customerComponent).getCustomerDetails(any(String.class));
        doReturn(countryModal).when(countryComponent).findCountry(any(String.class));
        doReturn(profile).when(contextUtil).callerLoginProfile();
        //When
        jsonData = qualityAuditDataResolver.find(ccaAwbVO);
        //Then
        Assertions.assertEquals(jsonDataValue, jsonData);
    }

    @Test
    void shouldPopulateAgentProperties() throws BusinessException {
        //Given
        customerModel.setOwnSales("Y");
        var customerList = new ArrayList<>();
        customerList.add(customerModel);
        doReturn(aiportModal).when(airportComponent).validateAirport(any(String.class));
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(customerList).when(customerComponent).getCustomerDetails(any(String.class));
        doReturn(countryModal).when(countryComponent).findCountry(any(String.class));
        doReturn(profile).when(contextUtil).callerLoginProfile();
        //Then
        assertDoesNotThrow(() -> qualityAuditDataResolver.find(ccaAwbVO));
        ccaAwbVO.setAgentCode("");
        assertDoesNotThrow(() -> qualityAuditDataResolver.find(ccaAwbVO));
        ccaAwbVO.setAgentCode(null);
        ccaAwbVO.setShipmentPrefix("176");
        assertDoesNotThrow(() -> qualityAuditDataResolver.find(ccaAwbVO));
    }

    @Test
    void shouldCatchBusinessExceptionOnInvalidCustomer() throws BusinessException {
        //Given
        doReturn(aiportModal).when(airportComponent).validateAirport(any(String.class));
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doThrow(new CustomerBusinessException(new ErrorVO())).when(customerComponent).getCustomerDetails(any(String.class));
        doReturn(countryModal).when(countryComponent).findCountry(any(String.class));
        doReturn(profile).when(contextUtil).callerLoginProfile();
        //Then
        assertDoesNotThrow(() -> qualityAuditDataResolver.find(ccaAwbVO));
    }

    @Test
    void shouldCatchAirportBusinessExceptionOnInvalidAirport() throws BusinessException {
        //Given
        ccaAwbVO.setAwbCharges(null);
        ccaAwbVO.setPayType("PP");
        doThrow(new AirportBusinessException(new ErrorVO())).when(airportComponent).validateAirport(any(String.class));
        doReturn(jsonData).when(objectMapper).createObjectNode();
        doReturn(customerList).when(customerComponent).getCustomerDetails(any(String.class));
        doReturn(countryModal).when(countryComponent).findCountry(any(String.class));
        doReturn(profile).when(contextUtil).callerLoginProfile();
        //Then
        assertDoesNotThrow(() -> qualityAuditDataResolver.find(ccaAwbVO));
    }

    private CcaAwbVO getMockCcaAwb() {
        var ccaAwbVO = getFullMockCcaAwbVO("134", "95872365", "R");
        ccaAwbVO.setOrigin("CDG");
        ccaAwbVO.setDestination("DXB");
        ccaAwbVO.setPayType("PP");
        ccaAwbVO.setAgentCode("AIEDXB");
        return ccaAwbVO;
    }

    @NotNull
    private List<CustomerModel> getCustomerList() {
        customerList = new ArrayList<>();
        customerModel = new CustomerModel();
        customerModel.setStationCode("TRV");
        customerModel.setCustomerType("AG");
        customerList.add(customerModel);
        return customerList;
    }

    private void fillJsonDataValue() {
        jsonDataValue.put("originAirportCode", "CDG");
        jsonDataValue.put("originCityCode", "PAR");
        jsonDataValue.put("originCountryCode", "IN");
        jsonDataValue.put("originRegionCode", "SAS");
        jsonDataValue.put("destinationAirportCode", "DXB");
        jsonDataValue.put("destinationCityCode", "PAR");
        jsonDataValue.put("destinationCountryCode", "IN");
        jsonDataValue.put("destinationRegionCode", "SAS");
        jsonDataValue.put("agentAirportCode", "TRV");
        jsonDataValue.put("agentCityCode", "PAR");
        jsonDataValue.put("agentCountryCode", "IN");
        jsonDataValue.put("agentRegionCode", "SAS");
        jsonDataValue.put("agentStationCode", "TRV");
        jsonDataValue.put("customerType", "AG");
        jsonDataValue.put("isAirlineCounterStaff", false);
        jsonDataValue.put("awbOwner", "OWN");
    }
}
