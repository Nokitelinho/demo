package com.ibsplc.neoicargo.cca.component.feature.reratecca.enricher;

import com.ibsplc.neoicargo.cca.component.feature.reratecca.calculators.CcaPricingCalculator;
import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbMapper;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import com.ibsplc.neoicargo.framework.orchestration.FeatureTestSupport;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.pricing.modal.CalculateAWBChargesResponse;
import com.ibsplc.neoicargo.pricing.modal.OtherChargeDetail;
import com.ibsplc.neoicargo.pricing.modal.RateDetail;
import com.ibsplc.neoicargo.pricing.modal.RateLineDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.QUANTITIES;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMoney;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getUnitOfMeasure;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.IATA_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.MKT_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_TYPE_RECOMMENDED_IATA;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_TYPE_RECOMMENDED_MKT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnitPlatform.class)
class PricingCcaEnricherTest {

	@Mock
	private CcaPricingCalculator pricingCalculator;

	@Spy
	private final CcaAwbDetailMapper ccaAwbDetailMapper = Mappers.getMapper(CcaAwbDetailMapper.class);

	private CcaAwbMapper awbMapper;

	private CcaAwbVO shipmentDetailVO;

	private CCAMasterVO ccaMasterVO;

	@InjectMocks
	private PricingCcaEnricher pricingCcaEnricher;

	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(ccaAwbDetailMapper, "moneyMapper", new MoneyMapper());
		awbMapper = MockQuantity.injectMapper(QUANTITIES, CcaAwbMapper.class);
		MockitoAnnotations.openMocks(this);
		FeatureTestSupport.mockFeatureContext();

		// Given
		ccaMasterVO = new CCAMasterVO();
		ccaMasterVO.setUnitOfMeasure(getUnitOfMeasure("K", "B", "C", "USD"));
		shipmentDetailVO = getInitialMockConfigForCcaAwbVO();
	}

	@Test
	void shouldPopulateRatesAndChargeFromPricing() throws BusinessException {
		// When
		doReturn(List.of(getCalculateAWBChargesResponse(), getCalculateAWBChargesResponseWithOtherCharges()))
				.when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		//  Then
		assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
	}

	@Test
	void shouldLeaveOrUpdateOrAppendRatesBasedOnPricingResponse() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("AWB");
		final var genPricingResp = new CalculateAWBChargesResponse();
		genPricingResp.setId("GEN");

		RateDetail iataRateDetails = new RateDetail();
		iataRateDetails.setRate(1.0);
		iataRateDetails.setRateLineDetail(getRateLineDetail());
		genPricingResp.setIataCharge(iataRateDetails);

		final var flwPricingResp = new CalculateAWBChargesResponse();
		flwPricingResp.setId("FLW");

		RateDetail mktRateDetails = new RateDetail();
		mktRateDetails.setRate(1.0);
		mktRateDetails.setRateLineDetail(getRateLineDetail());
		flwPricingResp.setIataCharge(mktRateDetails);


		// When
		doReturn( List.of( genPricingResp, flwPricingResp ) )
				.when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		// Then
		assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
		var awbRates = new ArrayList<>(ccaMasterVO.getRevisedShipmentVO().getAwbRates());
		assertEquals(5, awbRates.size());

		assertEquals("GEN", awbRates.get(0).getCommodityCode());
		assertEquals(MKT_TYPE, awbRates.get(0).getRateType());
		assertEquals(0.1, awbRates.get(0).getRate());

		assertEquals("GEN", awbRates.get(1).getCommodityCode());
		assertEquals(IATA_TYPE, awbRates.get(1).getRateType());
		assertEquals(1.0, awbRates.get(1).getRate());

        assertEquals("FLW", awbRates.get(2).getCommodityCode());
        assertEquals(RATE_TYPE_RECOMMENDED_IATA, awbRates.get(2).getRateType());
        assertEquals(1.0, awbRates.get(2).getRate());

        assertEquals("GEN", awbRates.get(3).getCommodityCode());
        assertEquals(RATE_TYPE_RECOMMENDED_IATA, awbRates.get(3).getRateType());
        assertEquals(1.0, awbRates.get(3).getRate());

		assertEquals("FLW", awbRates.get(4).getCommodityCode());
		assertEquals(IATA_TYPE, awbRates.get(4).getRateType());
		assertEquals(1.0, awbRates.get(4).getRate());
	}

	@Test
	void shouldFetchFreightRatesFromPricing() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("FRT");
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
        final var moneyObject = getMoney(0.0, "USD");

        // When
		doReturn(List.of(getCalculateAWBChargesResponse())).when(pricingCalculator).findFreightRatesFromPricing(any(CcaAwbVO.class));
        try (MockedStatic<Money> moneyMock = Mockito.mockStatic(Money.class)) {
            moneyMock.when(() -> Money.of(BigDecimal.ZERO, shipmentDetailVO.getCurrency()))
                    .thenReturn(moneyObject);
            assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
        }

		// Then
		assertEquals(IATA_TYPE, getRateType());
	}

	@Test
	void shouldFetchOtherChargesFromPricing() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("OTH");
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
		final var calculateAWBChargesResponseWithOtherCharges = getCalculateAWBChargesResponseWithOtherCharges();
		calculateAWBChargesResponseWithOtherCharges.setOtherCharge(null);

		// When
		doReturn(List.of(getCalculateAWBChargesResponse(), calculateAWBChargesResponseWithOtherCharges))
				.when(pricingCalculator).findOtherChargeFromPricing(any(CcaAwbVO.class));
		pricingCcaEnricher.enrich(ccaMasterVO);
		final var chargeHeadCode = shipmentDetailVO.getAwbCharges().stream()
				.filter(f -> Objects.equals("FP", f.getChargeHeadCode()))
				.findFirst()
				.map(CcaChargeDetailsVO::getChargeHeadCode)
				.orElseThrow();

		// Then
		assertEquals("FP", chargeHeadCode);
	}

	@Test
	void shouldFetchBothFromPricing() throws BusinessException {
		//Given
		setPricingParamToFeatureContext("AWB");
		shipmentDetailVO.setPayType(null);
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
        final var moneyObject = getMoney(0.0, "USD");

		final var calculateAWBChargesResponseWithOtherCharges = getCalculateAWBChargesResponseWithOtherCharges();
		calculateAWBChargesResponseWithOtherCharges.setOtherCharge(null);

		// When
		doReturn(List.of(getCalculateAWBChargesResponse(), calculateAWBChargesResponseWithOtherCharges))
				.when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));
        try (MockedStatic<Money> moneyMock = Mockito.mockStatic(Money.class)) {
            moneyMock.when(() -> Money.of(BigDecimal.ZERO, shipmentDetailVO.getCurrency()))
                    .thenReturn(moneyObject);
            assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
        }

		// Then
		assertEquals(IATA_TYPE, getRateType());
	}

	@Test
	void shouldCheckErrorInOtherCharge() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("AWB");
        final var moneyObject = getMoney(0.0, "USD");

		final var otherChargeDetails = getOtherChargeDetail("EBL_TRF_005");
		otherChargeDetails.setChargeHeadCode("FC");
		final var otherChargeDetails1 = getOtherChargeDetail("EBL_TRF_003");
		otherChargeDetails1.setChargeHeadCode("FP");

		final var calculateAWBChargesResponse = new CalculateAWBChargesResponse();
		calculateAWBChargesResponse.setOtherCharge(List.of(otherChargeDetails));
		calculateAWBChargesResponse.setId("OTH");

		final var calculateAWBChargesResponse1 = new CalculateAWBChargesResponse();
		calculateAWBChargesResponse1.setOtherCharge(List.of(otherChargeDetails1));

		// When
		doReturn(List.of(calculateAWBChargesResponse, calculateAWBChargesResponse1))
				.when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		try (MockedStatic<Money> moneyMock = Mockito.mockStatic(Money.class)) {
            moneyMock.when(() -> Money.of(BigDecimal.ZERO, shipmentDetailVO.getCurrency()))
					.thenReturn(moneyObject);
			// Then
			assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
		}
	}

	@Test
	void shouldCheckErrorInOtherChargeIfChargesIsNullForEBL_TRF_005() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("AWB");

		final var otherChargeDetails = getOtherChargeDetail("EBL_TRF_005");
		otherChargeDetails.setChargeHeadCode("FC");

		final var calculateAWBChargesResponse = new CalculateAWBChargesResponse();
		calculateAWBChargesResponse.setOtherCharge(List.of(otherChargeDetails));
		calculateAWBChargesResponse.setId("OTH");

		// When
		doReturn(List.of(calculateAWBChargesResponse))
				.when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));
		final var shipmentDetailVO = getInitialMockConfigForCcaAwbVO();
		shipmentDetailVO.setAwbCharges(null);
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
		pricingCcaEnricher.enrich(ccaMasterVO);
	}

	@Test
	void shouldCheckErrorInOtherChargeIfChargesIsNullForEBL_TRF_003() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("AWB");

		final var otherChargeDetails = getOtherChargeDetail("EBL_TRF_003");
		otherChargeDetails.setChargeHeadCode("FC");

		final var calculateAWBChargesResponse = new CalculateAWBChargesResponse();
		calculateAWBChargesResponse.setOtherCharge(List.of(otherChargeDetails));
		calculateAWBChargesResponse.setId("OTH");

		// When
		doReturn(List.of(calculateAWBChargesResponse))
				.when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));
		final var shipmentDetailVO = getInitialMockConfigForCcaAwbVO();
		shipmentDetailVO.setAwbCharges(null);
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
		pricingCcaEnricher.enrich(ccaMasterVO);
	}

	@Test
	void shouldCheckErrorInOtherChargeIfOtherChargeIsDC() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("AWB");
        final var moneyObject = getMoney(0.0, "USD");

		final var otherChargeDetails = getOtherChargeDetail("EBL_TRF_003");
		otherChargeDetails.setChargeHeadCode("FP");
		otherChargeDetails.setChargeType("DC");

		final var calculateAWBChargesResponse = new CalculateAWBChargesResponse();
		calculateAWBChargesResponse.setOtherCharge(List.of(otherChargeDetails));

		// When
		doReturn(List.of(calculateAWBChargesResponse))
				.when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        try (MockedStatic<Money> moneyMock = Mockito.mockStatic(Money.class)) {
            moneyMock.when(() -> Money.of(BigDecimal.ZERO, shipmentDetailVO.getCurrency()))
                    .thenReturn(moneyObject);
            // Then
            assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
        }
	}

	@Test
	void shouldCheckErrorInOtherChargeIfOtherChargeIsDA() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("AWB");
        final var moneyObject = getMoney(0.0, "USD");

        final var otherChargeDetails = getOtherChargeDetail("EBL_TRF_003");
		otherChargeDetails.setChargeHeadCode("FP");
		otherChargeDetails.setChargeType("DA");

		final var calculateAWBChargesResponse = new CalculateAWBChargesResponse();
		calculateAWBChargesResponse.setOtherCharge(List.of(otherChargeDetails));

		// When
		doReturn(List.of(calculateAWBChargesResponse))
				.when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        try (MockedStatic<Money> moneyMock = Mockito.mockStatic(Money.class)) {
            moneyMock.when(() -> Money.of(BigDecimal.ZERO, shipmentDetailVO.getCurrency()))
                    .thenReturn(moneyObject);
            // Then
            assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
        }
	}

	@Test
	void shouldNotPopulateRatesAndChargesIfRecommendedRateLineIsPresent() {
		// Given
		final var rateDetailVO = getCcaRateDetailsVO(RATE_TYPE_RECOMMENDED_MKT);
		final var rateDetailVO1 = getCcaRateDetailsVO(RATE_TYPE_RECOMMENDED_IATA);

		// When
		shipmentDetailVO.setAwbRates(Stream.of(rateDetailVO, rateDetailVO1).collect(Collectors.toList()));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		// Then
		assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
	}

	@Test
	void shouldCheckPricingResponseIsNullOrEmpty() throws BusinessException {
		//Given
		final var pricingResponse = new ArrayList<CalculateAWBChargesResponse>();

		// When
		doReturn(pricingResponse).when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));

		// Then
		assertTrue(pricingResponse.isEmpty());
	}

	@Test
	void shouldCatchBusinessExceptionForAwbRates() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("AWB");

		// When
		doThrow(BusinessException.class).when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		// Then
		assertDoesNotThrow(() -> pricingCcaEnricher.enrich(ccaMasterVO));
	}

	@Test
	void shouldCatchBusinessExceptionForOtherCharge() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("OTH");

		// When
		doThrow(BusinessException.class).when(pricingCalculator).findOtherChargeFromPricing(any(CcaAwbVO.class));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		// Then
		assertDoesNotThrow(() -> pricingCcaEnricher.enrich(ccaMasterVO));
	}

	@Test
	void shouldCatchBusinessExceptionForFreightRates() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("FRT");

		// When
		doThrow(BusinessException.class).when(pricingCalculator).findFreightRatesFromPricing(any(CcaAwbVO.class));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		// Then
		assertDoesNotThrow(() -> pricingCcaEnricher.enrich(ccaMasterVO));
	}

	@Test
	void shouldNotFetchRatesIfRatingParameterIsInvalidInContextUtil() {
		// Given
		setPricingParamToFeatureContext("FRTOTH");
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		// When
		pricingCcaEnricher.enrich(ccaMasterVO);
		final var count = shipmentDetailVO.getAwbRates().stream()
				.filter(f -> Objects.equals(RATE_TYPE_RECOMMENDED_IATA, f.getRateType()))
				.count();

		// Then
		assertEquals(0, count);
	}

	@ParameterizedTest
	@ValueSource(strings = {"","F"})
	void shouldCheckServiceCargoClass(final String serviceCargoClass) {
		// Given
		shipmentDetailVO.setServiceCargoClass(serviceCargoClass);
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		// When + Then
		assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
	}

	@Test
	void shouldCheckPayTypeBlank() {
		// Given
		final var rateDetailVO = getCcaRateDetailsVO(RATE_TYPE_RECOMMENDED_MKT);
		final var rateDetailVO1 = getCcaRateDetailsVO(RATE_TYPE_RECOMMENDED_IATA);
		shipmentDetailVO.setPayType("PP");
		shipmentDetailVO.setAwbRates(List.of(rateDetailVO, rateDetailVO1));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		// When + Then
		assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
	}

	@Test
	void shouldFetchFreightRatesFromPricingWithNullAWbRatesForFRTPricingParam() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("FRT");
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
		shipmentDetailVO.setAwbRates(null);
        final var moneyObject = getMoney(0.0, "USD");

        // When
		doReturn(List.of(getCalculateAWBChargesResponse())).when(pricingCalculator).findFreightRatesFromPricing(any(CcaAwbVO.class));
        try (MockedStatic<Money> moneyMock = Mockito.mockStatic(Money.class)) {
            moneyMock.when(() -> Money.of(BigDecimal.ZERO, shipmentDetailVO.getCurrency()))
                    .thenReturn(moneyObject);
            assertDoesNotThrow(()-> pricingCcaEnricher.enrich(ccaMasterVO));
        }

		// Then
		assertEquals(IATA_TYPE, getRateType());
	}

	@Test
	void shouldFetchFreightRatesFromPricingWithNullAWbChargesForOTHPricingParam() throws BusinessException {
		// Given
		setPricingParamToFeatureContext("OTH");
		shipmentDetailVO.setAwbCharges(null);

		// When
		doThrow(BusinessException.class).when(pricingCalculator).findAwbRatesFromPricing(any(CcaAwbVO.class));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		// Then
		assertDoesNotThrow(() -> pricingCcaEnricher.enrich(ccaMasterVO));
	}

	private String getRateType() {
		return shipmentDetailVO.getAwbRates().stream()
				.filter(f -> Objects.equals(IATA_TYPE, f.getRateType()))
				.findFirst()
				.map(CcaRateDetailsVO::getRateType)
				.orElseThrow();
	}

	private void setPricingParamToFeatureContext(final String param) {
		FeatureContextUtilThreadArray.getInstance().getFeatureContext().getContextMap().put(CcaConstants.PRICING_PARAMETER, param);
	}

	@NotNull
	private OtherChargeDetail getOtherChargeDetail(final String chargeHeadCode, final String chargeHeadName, final String chargeType) {
		OtherChargeDetail otherChargeDetail = new OtherChargeDetail();
		otherChargeDetail.setChargeHeadCode(chargeHeadCode);
		otherChargeDetail.setChargeHeadName(chargeHeadName);
		otherChargeDetail.setCharge(BigDecimal.TEN);
		otherChargeDetail.setChargeType(chargeType);
		return otherChargeDetail;
	}

	@NotNull
	private CcaChargeDetailsVO getCcaChargeDetailsVO(final String chargeHeadName, final String chargeHeadCode,
													 final boolean dueCarrier, final boolean dueAgent) {
		CcaChargeDetailsVO awbChargeDetailsVO = new CcaChargeDetailsVO();
		awbChargeDetailsVO.setChargeHead(chargeHeadName);
		awbChargeDetailsVO.setChargeHeadCode(chargeHeadCode);
		awbChargeDetailsVO.setDueCarrier(dueCarrier);
		awbChargeDetailsVO.setDueAgent(dueAgent);
		awbChargeDetailsVO.setCharge(getMoney(200.0, "USD"));
		return awbChargeDetailsVO;
	}

	@NotNull
	private CcaRateDetailsVO getCcaRateDetailsVO(String rateType) {
		CcaRateDetailsVO rateDetailVO1 = new CcaRateDetailsVO();
		rateDetailVO1.setCommodityCode("GEN");
		rateDetailVO1.setRateType(rateType);
		rateDetailVO1.setRate(0.1);
		return rateDetailVO1;
	}

	@NotNull
	private RateLineDetail getRateLineDetail() {
		RateLineDetail rateLineDetail = new RateLineDetail();
		rateLineDetail.setAllInAttributes(List.of("DA"));
		return rateLineDetail;
	}

	@NotNull
	private RateDetail getRateDetail() {
		RateDetail rateDetail = new RateDetail();
		rateDetail.setCharge(BigDecimal.TEN);
		rateDetail.setNetCharge(BigDecimal.TEN);
		rateDetail.setRate(0.0);
		rateDetail.setRatedChargeableWeight(BigDecimal.TEN);
		return rateDetail;
	}

	@NotNull
	private CcaAwbVO getInitialMockConfigForCcaAwbVO() {
		var shipmentDetailVO = new CcaAwbVO();
		shipmentDetailVO.setPayType("PP");
		shipmentDetailVO.setServiceCargoClass(null);
		shipmentDetailVO.setCurrency("USD");
		shipmentDetailVO.setUnitOfMeasure(getUnitOfMeasure("K", "B", "C", "USD"));

		final var rateDetailVO = getCcaRateDetailsVO(IATA_TYPE);
		final var rateDetailVO1 = getCcaRateDetailsVO(MKT_TYPE);
		shipmentDetailVO.setAwbRates(List.of(rateDetailVO, rateDetailVO1));

		shipmentDetailVO.setAwbCharges(List.of(
				getCcaChargeDetailsVO("Flat Charge", "FC", false, true),
				getCcaChargeDetailsVO("Flat Percentage", "FP", true, false),
				getCcaChargeDetailsVO("Security Charge", "SC", false, true),
				getCcaChargeDetailsVO("Door Delivery Charge", "DC", true, false),
				getCcaChargeDetailsVO("Flat Percentage", "FP", false, false)
		));

		return shipmentDetailVO;
	}

	@NotNull
	private CalculateAWBChargesResponse getCalculateAWBChargesResponse() {
		final var calculateAWBChargesResponse = new CalculateAWBChargesResponse();
		calculateAWBChargesResponse.setId("GEN");

		final var rateDetails1 = getRateDetail();
		final var rateDetails2 = getRateDetail();

		final var rateLineDetails1 = getRateLineDetail();
		final var rateLineDetails2 = getRateLineDetail();

		rateDetails1.setRateLineDetail(rateLineDetails1);
		calculateAWBChargesResponse.setIataCharge(rateDetails1);

		rateDetails2.setRateLineDetail(rateLineDetails2);

		calculateAWBChargesResponse.setMarketCharge(rateDetails2);
		return calculateAWBChargesResponse;
	}

	@NotNull
	private CalculateAWBChargesResponse getCalculateAWBChargesResponseWithOtherCharges() {
		final var calculateAWBChargesResponse1 = new CalculateAWBChargesResponse();
		final var otherChargeDetail = getOtherChargeDetail("FP", "Flat percentage", "DC");
		final var otherChargeDetail1 = getOtherChargeDetail("SC", "Security Charge", "DA");
		final var otherChargeDetail2 = getOtherChargeDetail("DC", "Door Delivery Charge", "DA");
		final var otherChargeDetail3 = getOtherChargeDetail("FT", "Surcharge 10 per weight unit", "DC");
		calculateAWBChargesResponse1.setOtherCharge(List.of(otherChargeDetail, otherChargeDetail1, otherChargeDetail2, otherChargeDetail3));
		calculateAWBChargesResponse1.setId("OTH");
		return calculateAWBChargesResponse1;
	}

	@NotNull
	private OtherChargeDetail getOtherChargeDetail(String ebl_trf_003) {
		final var otherChargeDetails1 = new OtherChargeDetail();
		otherChargeDetails1.setErrorCode(ebl_trf_003);
		return otherChargeDetails1;
	}

}
