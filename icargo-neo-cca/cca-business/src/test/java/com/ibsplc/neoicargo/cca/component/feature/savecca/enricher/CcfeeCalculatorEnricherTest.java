package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.businessrules.client.category.Action;
import com.ibsplc.neoicargo.businessrules.client.category.OutputParameter;
import com.ibsplc.neoicargo.businessrules.client.util.RuleClient;
import com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils;
import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import com.ibsplc.neoicargo.cca.util.awb.AwbParameterConfig;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaCustomerDetailVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.area.airport.AirportComponent;
import com.ibsplc.neoicargo.masters.area.airport.modal.AirportModal;
import com.ibsplc.neoicargo.masters.country.CountryComponent;
import com.ibsplc.neoicargo.masters.country.modal.CountryModal;
import com.ibsplc.neoicargo.masters.currency.CurrencyComponent;
import com.ibsplc.neoicargo.masters.currency.exceptions.ExchangeRateNotFoundException;
import com.ibsplc.neoicargo.masters.currency.modal.ExchangeRateModal;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.ibsplc.neoicargo.cca.component.feature.savecca.enricher.CcfeeCalculatorEnricher.CCFEE_FORMULA;
import static com.ibsplc.neoicargo.cca.util.awb.Parameters.Parameter;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnitPlatform.class)
class CcfeeCalculatorEnricherTest {

    @InjectMocks
    private CcfeeCalculatorEnricher ccfeeCalculatorEnricher;

    private CCAMasterVO ccaMasterVO;

    @Mock
    private AwbParameterConfig awbParameterConfig;

    @Mock
    private AirportComponent airportComponent;

    @Mock
    private CountryComponent countryComponent;

    @Mock
    private CurrencyComponent currencyComponent;

    @Mock
    private RuleClient client;

    @Mock
    private AirportUtil airportUtil;

    @Mock
    private CustomerComponent customerComponent;

    private Collection<Action> actionsCCFee;
    private List<CustomerModel> listccCollector;
    @BeforeEach
    public void setup() throws ExchangeRateNotFoundException, CustomerBusinessException {
        MockitoAnnotations.openMocks(this);
        var ccaAwbVO = createAwbVo();
        ccaAwbVO.setCcaCustomerDetails(Collections.singleton(MockModelsGeneratorUtils.getCcaCustomerDetailVO(CustomerType.I)));
        createMockParameters();
        ExchangeRateModal exchangeRateModal = new ExchangeRateModal();
        exchangeRateModal.setConversionFactor(1.0);
        doReturn(exchangeRateModal).when(currencyComponent).findCurrencyExchangeRate(any(String.class),
                any(String.class), any(String.class), any(String.class));
        doReturn(true).when(airportUtil).isLastOwnFlownRoute(any(CcaAwbVO.class));
        actionsCCFee = createActionFee();
        ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);
        CustomerModel customerModel=new CustomerModel();
        customerModel.setCcfeeDueGhaFlag("N");
        listccCollector=new ArrayList<>();
        listccCollector.add(customerModel);
        doReturn(listccCollector).when(customerComponent).validateCCCollector(any(CustomerModel.class));
    }

    @Test
    void shouldCalculateCcfee() throws BusinessException {
        //Given
        var airportModel = getAirportModel();
        var countryModel = getCountryModel();
        var ccaAwbVO = ccaMasterVO.getRevisedShipmentVO();
        ccaAwbVO.setPayType("CP");
        doReturn(airportModel).when(airportComponent).validateAirport(any(String.class));
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        doReturn(actionsCCFee).when(client).fireRule(CCFEE_FORMULA, ccaAwbVO, false);
        ccfeeCalculatorEnricher.enrich(ccaMasterVO);
        Assertions.assertNotNull(ccaAwbVO.getCcfeeCurrency());
        ccaAwbVO.setExecutionDate(LocalDate.of(2020, 12, 3));
        ccfeeCalculatorEnricher.enrich(ccaMasterVO);

        countryModel.setMinimumCCfee(2);
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        //When
        ccfeeCalculatorEnricher.enrich(ccaMasterVO);
        //Then
        Assertions.assertNotNull(ccaAwbVO.getCcfeeCurrency());
    }
    @Test
    void shouldCalculateWhenCcfeeIsNull() throws BusinessException {
        //Given
        var airportModel = getAirportModel();
        var countryModel = getCountryModel();
        var ccaAwbVO = ccaMasterVO.getRevisedShipmentVO();
        ccaAwbVO.setPayType("CP");
        doReturn(airportModel).when(airportComponent).validateAirport(any(String.class));
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        doReturn(null).when(client).fireRule(CCFEE_FORMULA, ccaAwbVO, false);
        ccfeeCalculatorEnricher.enrich(ccaMasterVO);
        Assertions.assertNotNull(ccaAwbVO.getCcfeeCurrency());
        ccaAwbVO.setExecutionDate(LocalDate.of(2020, 12, 3));
        ccfeeCalculatorEnricher.enrich(ccaMasterVO);

        countryModel.setMinimumCCfee(2);
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        //When
        ccfeeCalculatorEnricher.enrich(ccaMasterVO);
        //Then
        Assertions.assertNotNull(ccaAwbVO.getCcfeeCurrency());
    }

    @Test
    void shouldCalculateCcfeeForForExchangeRateDateBasisM() throws BusinessException {
        //Given
        var airportModel = getAirportModel();
        var countryModel = getCountryModel();
        var ccaAwbVO = ccaMasterVO.getRevisedShipmentVO();
        ccaAwbVO.setPayType("CP");
        doReturn(airportModel).when(airportComponent).validateAirport(any(String.class));
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        doReturn(actionsCCFee).when(client).fireRule(CCFEE_FORMULA, ccaAwbVO, false);
        var exchangerateDatebasisM = new Parameter();
        exchangerateDatebasisM.setParameterCode("EXGRATDATBAS");
        exchangerateDatebasisM.setParameterValue("M");
        doReturn(exchangerateDatebasisM).when(awbParameterConfig).getAwbParameter("EXGRATDATBAS");
        ccfeeCalculatorEnricher.enrich(ccaMasterVO);
        Assertions.assertNotNull(ccaAwbVO.getCcfeeCurrency());
        countryModel.setMinimumCCfee(2);
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        ccfeeCalculatorEnricher.enrich(ccaMasterVO);
        countryModel.setMinimumCCfeeCurrency(null);
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        //When
        ccfeeCalculatorEnricher.enrich(ccaMasterVO);
        //Then
        Assertions.assertNotNull(ccaAwbVO.getCcfeeCurrency());
    }

    @Test
    void shouldstampQualityAuditedException() throws BusinessException {
        //Given
        var airportModel = getAirportModel();
        var countryModel = getCountryModel();
        var ccaAwbVO = ccaMasterVO.getRevisedShipmentVO();
        ccaAwbVO.setPayType("CP");
        doReturn(airportModel).when(airportComponent).validateAirport(any(String.class));
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        doThrow(ExchangeRateNotFoundException.class).when(currencyComponent).findCurrencyExchangeRate(any(String.class),
                any(String.class), any(String.class), any(String.class));
        doReturn(actionsCCFee).when(client).fireRule(CCFEE_FORMULA, ccaAwbVO, false);
        var exchangeratebasis = new Parameter();
        exchangeratebasis.setParameterCode("EXGRATBAS");
        exchangeratebasis.setParameterValue("D");
        //Then
        assertThrows(BusinessException.class, () -> ccfeeCalculatorEnricher.enrich(ccaMasterVO));
    }

    @Test
    void shouldNotThrowExceptionWhenExchangeRateIsPresent() throws BusinessException {
        // Given
        var airportModel = getAirportModel();
        var countryModel = getCountryModel();
        var ccaAwbVO = ccaMasterVO.getRevisedShipmentVO();
        ccaAwbVO.setPayType("CC");
        ccaAwbVO.setCcfeeCurrency("EUR");
        doReturn(airportModel).when(airportComponent).validateAirport(any(String.class));
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        doReturn(actionsCCFee).when(client).fireRule(CCFEE_FORMULA, ccaAwbVO, false);
        var exchangerateDatebasisM = new Parameter();
        exchangerateDatebasisM.setParameterCode("EXGRATDATBAS");
        exchangerateDatebasisM.setParameterValue("M");
        doReturn(exchangerateDatebasisM).when(awbParameterConfig).getAwbParameter("EXGRATDATBAS");
        // Then
        assertDoesNotThrow(() -> ccfeeCalculatorEnricher.enrich(ccaMasterVO));
    }

    @Test
    void shouldThrowExceptionWhenExchangeRateNotFound() throws BusinessException {
        // Given
        var airportModel = getAirportModel();
        var countryModel = getCountryModel();
        var ccaAwbVO = ccaMasterVO.getRevisedShipmentVO();
        ccaAwbVO.setPayType("CC");
        CcaCustomerDetailVO ccaCustomerDetailVO = MockModelsGeneratorUtils.getCcaCustomerDetailVO(CustomerType.I);
        ccaCustomerDetailVO.setBillingCurrencyCode("EUR");
        ccaAwbVO.setCcaCustomerDetails(Collections.singleton(ccaCustomerDetailVO));
        doReturn(airportModel).when(airportComponent).validateAirport(any(String.class));
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        doReturn(actionsCCFee).when(client).fireRule(CCFEE_FORMULA, ccaAwbVO, false);
        var exchangerateDatebasisM = new Parameter();
        exchangerateDatebasisM.setParameterCode("EXGRATDATBAS");
        exchangerateDatebasisM.setParameterValue("M");
        doReturn(exchangerateDatebasisM).when(awbParameterConfig).getAwbParameter("EXGRATDATBAS");
        doThrow(ExchangeRateNotFoundException.class).when(currencyComponent)
                .findCurrencyExchangeRate(eq("EUR"), eq("USD"), anyString(), eq("F"));
        // Then
        var thrownException = assertThrows(
                CcaBusinessException.class,
                () -> ccfeeCalculatorEnricher.enrich(ccaMasterVO)
        );
        var error = thrownException.getErrors().get(0);
        assertEquals(CcaErrors.NEO_CCA_018.getErrorCode(), error.getErrorCode());
        assertArrayEquals(new String[]{"EUR", "USD"}, error.getErrorData() );
    }


    @NotNull
    private AirportModal getAirportModel() {
        AirportModal airportModel = new AirportModal();
        airportModel.setCountryCode("IN");
        return airportModel;
    }

    @NotNull
    private CcaAwbVO createAwbVo() {
        CcaAwbVO ccaAwbVO = new CcaAwbVO();
        ccaAwbVO.setQualityAudited(true);
        ccaAwbVO.setShipmentPrefix("134");
        ccaAwbVO.setMasterDocumentNumber("11112200");
        ccaAwbVO.setShippingDate(LocalDate.of(2020, 12, 1));
        ccaAwbVO.setWeightCharge(100);
        ccaAwbVO.setDestination("BOM");
        ccaAwbVO.setCurrency("INR");
        return ccaAwbVO;
    }

    @NotNull
    private CountryModal getCountryModel() {
        CountryModal countryModel = new CountryModal();
        countryModel.setCcfeePercentage(5);
        countryModel.setMinimumCCfee(100);
        countryModel.setMinimumCCfeeCurrency("USD");
        countryModel.setCurrencyCode("INR");
        return countryModel;
    }

    private void createMockParameters() {
        Parameter exchangeratebasis = new Parameter();
        exchangeratebasis.setParameterCode("EXGRATBAS");
        exchangeratebasis.setParameterValue("F");
        Parameter exchangerateDatebasis = new Parameter();
        exchangerateDatebasis.setParameterCode("EXGRATDATBAS");
        exchangerateDatebasis.setParameterValue("A");
        doReturn(exchangeratebasis).when(awbParameterConfig).getAwbParameter("EXGRATBAS");
        doReturn(exchangerateDatebasis).when(awbParameterConfig).getAwbParameter("EXGRATDATBAS");
    }

    private Collection<Action> createActionFee() {
        var actionsCCFee = new ArrayList<Action>();
        Collection<OutputParameter> outparamList = new ArrayList<>();
        var action = new Action();
        OutputParameter outparam = new OutputParameter();
        outparam.setName("totalDueAgtCCFChg");
        outparam.setValue("2");
        outparam.setDescription("CC OCDA");
        outparamList.add(outparam);
        action.setParams(outparamList);
        actionsCCFee.add(action);
        return actionsCCFee;
    }
    @Test
    void shouldNotCalculateCcfeeForCcfeeDueGhaFlagY() throws BusinessException {
        //Given
        var airportModel = getAirportModel();
        var countryModel = getCountryModel();
        var ccaAwbVO = ccaMasterVO.getRevisedShipmentVO();
        ccaAwbVO.setPayType("CP");
        doReturn(airportModel).when(airportComponent).validateAirport(any(String.class));
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        doReturn(actionsCCFee).when(client).fireRule(CCFEE_FORMULA, ccaAwbVO, false);
        ccaAwbVO.setExecutionDate(LocalDate.of(2020, 12, 3));
        doReturn(countryModel).when(countryComponent).findCountry(any(String.class));
        CustomerModel customerModel=new CustomerModel();
        customerModel.setCcfeeDueGhaFlag("Y");
        listccCollector=new ArrayList<>();
        listccCollector.add(customerModel);
        doReturn(listccCollector).when(customerComponent).validateCCCollector(any(CustomerModel.class));
        //When
        ccfeeCalculatorEnricher.enrich(ccaMasterVO);
        //Then
        Assertions.assertEquals(0,ccaAwbVO.getCcfee());
    }
}