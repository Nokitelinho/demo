package com.ibsplc.neoicargo.cca.component.feature.reratecca.calculators;

import com.ibsplc.neoicargo.booking.BookingWebAPI;
import com.ibsplc.neoicargo.booking.modal.BookingData;
import com.ibsplc.neoicargo.booking.modal.Flight;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbDetailMapper;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.cca.vo.CcaRoutingVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import com.ibsplc.neoicargo.pricing.PricingWebAPI;
import com.ibsplc.neoicargo.pricing.modal.CalculateAWBChargesResponse;
import com.ibsplc.neoicargo.pricing.modal.EvaluationParameter;
import com.ibsplc.neoicargo.pricing.modal.OtherChargeDetail;
import com.ibsplc.neoicargo.pricing.modal.RateDetail;
import com.ibsplc.neoicargo.pricing.utils.PricingConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getUnitOfMeasure;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getWeightQuantity;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.IATA_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.MKT_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_TYPE_RECOMMENDED_IATA;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_TYPE_RECOMMENDED_MKT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(JUnitPlatform.class)
class CcaPricingCalculatorTest {

	@Mock
	private PricingWebAPI pricingWebAPI;

	@Mock
	private PricingConverter pricingConverter;

	@Mock
	private ContextUtil contextUtil;

	@Mock
	private LoginProfile profile;

	@Mock
	private BookingWebAPI bookingWebAPI;

	@Mock
	private QuantityMapper quantityMapper;

	@Mock
	private CcaAwbDetailMapper ccaAwbDetailMapper;

	@InjectMocks
	private CcaPricingCalculator pricingCalculator;

	private CcaAwbVO shipmentDetailVO;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		// Given
		final var weight = getWeightQuantity(80);
		final var unitOfMeasure = getUnitOfMeasure("K", "B", "C", "USD");
		final var ccaRatingDetailVO = getCcaRatingDetailVO(8, weight, "GEN");
		final var businessKey = ccaRatingDetailVO.businessKey();
		shipmentDetailVO = getMockCcaAwbVOForPricingCalculator("FRA", "DXB", LocalDate.now(), "USD", 10, weight,
				SHIPMENT_PREFIX, "2323232", unitOfMeasure, new ArrayList<>(),
				List.of(ccaRatingDetailVO));
		profile.setOwnAirlineCode("AV");

		// When
		doReturn(profile).when(contextUtil).callerLoginProfile();
		doReturn("AV").when(profile).getOwnAirlineCode();

		// Then
		assertEquals(ccaRatingDetailVO.getCommodityCode(), businessKey);
	}

	@Test
	void shouldReturnAwbRatesFromPricing() throws BusinessException {
		// Given
		final var rateDetailVO1 = getCcaRateDetailsVO("DGR", 2.0, IATA_TYPE, "ALL");
		final var rateDetailVO2 = getCcaRateDetailsVO("GEN", 2.0, IATA_TYPE, "DA");
		final var rateDetailVO3 = getCcaRateDetailsVO("GEN", 1.0, MKT_TYPE, "Dc");
		final var rateDetailVO4 = getCcaRateDetailsVO("DGR", 1.0, MKT_TYPE, "ALL");
		shipmentDetailVO.setAwbRates(List.of(rateDetailVO1, rateDetailVO2, rateDetailVO3, rateDetailVO4));
		shipmentDetailVO.setAgentStation("CDG");

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));

		// Then
		assertFalse(pricingCalculator.findAwbRatesFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnAwbRates() throws BusinessException {
		// Given
		final var ratingDetailVO = new CcaRatingDetailVO();
		ratingDetailVO.setNumberOfPieces(8);
		ratingDetailVO.setChargeableWeight(null);

		shipmentDetailVO.setRatingDetails(List.of(ratingDetailVO));
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", "ALL")));
		profile.setStationCode("CDG");

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(profile).when(contextUtil).callerLoginProfile();
		doReturn("CDG").when(profile).getStationCode();
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));

		// Then
		assertFalse(pricingCalculator.findAwbRatesFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnFreightRatesFromPricing() throws BusinessException {
		// Given
		shipmentDetailVO.setAgentStation(null);
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", "ALL")));

		// When
		doReturn(new BookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));

		// Then
		assertFalse(pricingCalculator.findFreightRatesFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnOtherChargesFromPricing() throws BusinessException {
		// Given
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", "ALL")));

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));

		// Then
		assertFalse(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnOtherCharges() throws BusinessException {
		// Given
		final var evalParams1 = getEvaluationParameter("COM", List.of("GEN"));
		final var evalParams2 = getEvaluationParameter("COMGRP", List.of("GENGRP"));
		final var evalParams3 = getEvaluationParameter("ULDTYP", List.of("AKE"));
		final var evalParams4 = getEvaluationParameter("ULDGRP", List.of("AKEGRP"));

		final var ratingDetailVO = new CcaRatingDetailVO();
		ratingDetailVO.setNumberOfPieces(8);

		final var rateDetailVO = new CcaRateDetailsVO();
		rateDetailVO.setCommodityCode("DGR");
		rateDetailVO.setAllInAttribute("DA");
		rateDetailVO.setRateType("RECIATA");
		rateDetailVO.setMinimumChargeAppliedFlag(true);

		shipmentDetailVO.setAwbRates(List.of(rateDetailVO));
		shipmentDetailVO.setRatingDetails(List.of(ratingDetailVO));

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(evalParams1, evalParams2, evalParams3, evalParams4)).when(pricingConverter)
				.getEvaluationParameters(eq(CcaRatingDetailVO.class), any(CcaRatingDetailVO.class), any(LocalDate.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));

		// Then
		assertFalse(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldCheckTriggerPoint() throws BusinessException {
		// Given
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", "ALL")));
		shipmentDetailVO.setChargeableWeight(null);
		shipmentDetailVO.setAwbRates(null);
		shipmentDetailVO.setRatingDetails(null);
		shipmentDetailVO.setTriggerPoint("NEO");

		final var bookingData = new BookingData();
		bookingData.setFlights(new ArrayList<>());

		// When
		doReturn(bookingData).when(bookingWebAPI).findBooking(any(String.class));

		// Then
		assertTrue(pricingCalculator.findAwbRatesFromPricing(shipmentDetailVO).isEmpty());
		assertTrue(pricingCalculator.findFreightRatesFromPricing(shipmentDetailVO).isEmpty());
		assertTrue(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnOtherChargesFromPricingWithNullBookingRoute() throws BusinessException {
		// Given
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", "ALL")));

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));
		when(ccaAwbDetailMapper.constructBookingRoutes(any(List.class))).thenReturn(null);

		// Then
		assertFalse(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnOtherChargesFromPricingWithNotNullBookingRoute() throws BusinessException {
		// Given
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", "ALL")));

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));
		when(ccaAwbDetailMapper.constructBookingRoutes(any(List.class))).thenReturn(List.of(new CcaRoutingVO()));

		// Then
		assertFalse(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnOtherChargesFromPricingWithNotNullFlightCarrierCode() throws BusinessException {
		// Given
		final var ccaRoutingVO = new CcaRoutingVO();
		ccaRoutingVO.setFlightCarrierCode("flight carrier code");
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", "ALL")));

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));
		when(ccaAwbDetailMapper.constructBookingRoutes(any(List.class))).thenReturn(List.of(ccaRoutingVO));

		// Then
		assertFalse(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnOtherChargesFromPricingWithNotNullFlightNumber() throws BusinessException {
		// Given
		final var ccaRoutingVO = new CcaRoutingVO();
		ccaRoutingVO.setFlightNumber("flight number");
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", "ALL")));

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));
		when(ccaAwbDetailMapper.constructBookingRoutes(any(List.class))).thenReturn(List.of(ccaRoutingVO));

		// Then
		assertFalse(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnOtherChargesFromPricingWithFlightNumberAndFlightCarrierCode() throws BusinessException {
		// Given
		final var ccaRoutingVO = new CcaRoutingVO();
		ccaRoutingVO.setFlightNumber("flight number");
		ccaRoutingVO.setFlightCarrierCode("flight carrier code");
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", "ALL")));

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));
		when(ccaAwbDetailMapper.constructBookingRoutes(any(List.class))).thenReturn(List.of(ccaRoutingVO));

		// Then
		assertFalse(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnOtherChargesFromPricingWithNullAllInAttribute() throws BusinessException {
		// Given
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", null)));

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));
		when(ccaAwbDetailMapper.constructBookingRoutes(any(List.class))).thenReturn(List.of(new CcaRoutingVO()));

		// Then
		assertFalse(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	@ParameterizedTest
	@MethodSource("differentRateType")
	void shouldReturnOtherChargesFromPricingWithDifferentRateType(final String rateType) throws BusinessException {
		// Given
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, rateType, "ALL")));

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));
		when(ccaAwbDetailMapper.constructBookingRoutes(any(List.class))).thenReturn(List.of(new CcaRoutingVO()));

		// Then
		assertFalse(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	@Test
	void shouldReturnOtherChargesFromPricingWithNotEmptyFlight() throws BusinessException {
		// Given
		var flight = getFlight("11345", "AV", "2020-12-09", "FRA", "DXB", "Y");
		shipmentDetailVO.setAwbRates(List.of(getCcaRateDetailsVO("DGR", 2.0, "IATA", null)));

		// When
		doReturn(getBookingData()).when(bookingWebAPI).findBooking(any(String.class));
		doReturn(List.of(getListOfCalculateAWBChargesResponse()))
				.when(pricingWebAPI).calculateAWBCharges(any(String.class), any(String.class), any(String.class), any(List.class));
		when(ccaAwbDetailMapper.constructBookingRoutes(any(List.class))).thenReturn(List.of(new CcaRoutingVO()));
		when(ccaAwbDetailMapper.fromRoutingDetailsToFlight(any(List.class))).thenReturn(List.of(flight));

		// Then
		assertFalse(pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO).isEmpty());
	}

	private static Stream<String> differentRateType() {
		return Stream.of("", "   ", null, RATE_TYPE_RECOMMENDED_IATA, RATE_TYPE_RECOMMENDED_MKT);
	}

	@NotNull
	private CcaRateDetailsVO getCcaRateDetailsVO(final String commodityCode, final double rate,
												 final String rateType, final String allInAttribute) {
		final var ccaRateDetailsVO = new CcaRateDetailsVO();
		ccaRateDetailsVO.setCommodityCode(commodityCode);
		ccaRateDetailsVO.setRate(rate);
		ccaRateDetailsVO.setRateType(rateType);
		ccaRateDetailsVO.setAllInAttribute(allInAttribute);
		return ccaRateDetailsVO;
	}

	@NotNull
	private BookingData getBookingData() {
		final var bookingData = new BookingData();
		var flight = getFlight("11345", "AV", "2020-12-09", "FRA", "DXB", "Y");
		bookingData.setFlights(List.of(flight));
		return bookingData;
	}

	@NotNull
	private Flight getFlight(final String flightNumber, final String flightCarrier, final String departureDate,
							 final String departureAirport, final String arrivalAirport, final String isTruckFlight) {
		var flight = new Flight();
		flight.setFlightNumber(flightNumber);
		flight.setFlightCarrier(flightCarrier);
		flight.setDepartureDate(departureDate);
		flight.setDepartureAirport(departureAirport);
		flight.setArrivalAirport(arrivalAirport);
		flight.setIsTruckFlight(isTruckFlight);
		return flight;
	}

	@NotNull
	public static OtherChargeDetail getOtherChargeDetail(final String chargeHeadCode, final BigDecimal charge) {
		final var otherChargeDetail = new OtherChargeDetail();
		otherChargeDetail.setChargeHeadCode(chargeHeadCode);
		otherChargeDetail.setCharge(charge);
		return otherChargeDetail;
	}

	@NotNull
	public static List<CalculateAWBChargesResponse> getListOfCalculateAWBChargesResponse() {
		final var otherChargeDetail = getOtherChargeDetail("FP", BigDecimal.TEN);
		final var rateDetail = new RateDetail();
		rateDetail.setCharge(BigDecimal.TEN);

		final var calculateAWBChargesResponse1 = new CalculateAWBChargesResponse();
		calculateAWBChargesResponse1.setId("GEN");
		calculateAWBChargesResponse1.setIataCharge(rateDetail);

		final var calculateAWBChargesResponse2 = new CalculateAWBChargesResponse();
		calculateAWBChargesResponse2.setOtherCharge(List.of(otherChargeDetail));
		return List.of(calculateAWBChargesResponse1, calculateAWBChargesResponse2);
	}

	@NotNull
	private EvaluationParameter getEvaluationParameter(final String parameterName, final List<String> parameterValue) {
		var evaluationParameter = new EvaluationParameter();
		evaluationParameter.setParameterName(parameterName);
		evaluationParameter.setParameterValue(parameterValue);
		return evaluationParameter;
	}

	@NotNull
	private CcaAwbVO getMockCcaAwbVOForPricingCalculator(final String origin, final String destination, final LocalDate shippingDate,
                                                         final String currency, final int pieces, final Quantity<Weight> chargeableWeight,
                                                         final String shipmentPrefix, final String masterDocumentNumber,
                                                         final Units unitOfMeasure, final Collection<CcaRateDetailsVO> awbRates,
                                                         final Collection<CcaRatingDetailVO> ratingDetails) {
		final var shipmentDetailVO = new CcaAwbVO();
		shipmentDetailVO.setOrigin(origin);
		shipmentDetailVO.setDestination(destination);
		shipmentDetailVO.setShippingDate(shippingDate);
		shipmentDetailVO.setCurrency(currency);
		shipmentDetailVO.setPieces(pieces);
		shipmentDetailVO.setChargeableWeight(chargeableWeight);
		shipmentDetailVO.setShipmentPrefix(shipmentPrefix);
		shipmentDetailVO.setMasterDocumentNumber(masterDocumentNumber);
		shipmentDetailVO.setUnitOfMeasure(unitOfMeasure);
		shipmentDetailVO.setAwbRates(awbRates);
		shipmentDetailVO.setRatingDetails(ratingDetails);
		return shipmentDetailVO;
	}

	@NotNull
	private CcaRatingDetailVO getCcaRatingDetailVO(final int numberOfPieces, final Quantity<Weight> chargeableWeight,
												   final String commodityCode) {
		final var ratingDetailVO = new CcaRatingDetailVO();
		ratingDetailVO.setNumberOfPieces(numberOfPieces);
		ratingDetailVO.setChargeableWeight(chargeableWeight);
		ratingDetailVO.setCommodityCode(commodityCode);
		return ratingDetailVO;
	}

}
