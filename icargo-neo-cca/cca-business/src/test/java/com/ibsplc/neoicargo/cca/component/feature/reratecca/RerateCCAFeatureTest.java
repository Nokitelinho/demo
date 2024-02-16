package com.ibsplc.neoicargo.cca.component.feature.reratecca;

import com.ibsplc.neoicargo.cca.component.feature.reratecca.enricher.PricingCcaEnricher;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import com.ibsplc.neoicargo.framework.orchestration.FeatureTestSupport;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.QUANTITIES;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMoney;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.PRICING_PARAMETER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(JUnitPlatform.class)
class RerateCCAFeatureTest {

	private CcaMasterMapper ccaMasterMapper;

	private CcaAwbMapper ccaAwbMapper = Mappers.getMapper(CcaAwbMapper.class);

	private CcaAwbDetailMapper ccaAwbDetailMapper = Mappers.getMapper(CcaAwbDetailMapper.class);

	@Mock
	private PricingCcaEnricher pricingCcaEnricher;

	@InjectMocks
	private RerateCCAFeature rerateCCAFeature;

	@BeforeEach
	public void setup() {
		ccaMasterMapper = MockQuantity.injectMapper(QUANTITIES, CcaMasterMapper.class);
		ReflectionTestUtils.setField(ccaAwbDetailMapper, "moneyMapper", new MoneyMapper());
		ReflectionTestUtils.setField(ccaAwbMapper, "ccaAwbDetailMapper", ccaAwbDetailMapper);
		ReflectionTestUtils.setField(ccaAwbMapper, "moneyMapper", new MoneyMapper());
		ReflectionTestUtils.setField(ccaMasterMapper, "ccaAwbMapper", ccaAwbMapper);
		MockitoAnnotations.openMocks(this);
		FeatureTestSupport.mockFeatureContext();
	}

	@ParameterizedTest
	@ValueSource(strings = {"FRT", "OTH", "AWB"})
	void shouldFetchAwbRatesIfRatingParameter(final String ratingParameter) {
		// Given
		final var shipmentDetailVO = initShipmentDetailVo(ratingParameter);
		final var ccaMasterVO = getCCAMasterVO(
				"88898832",
				"CCA000001",
				LocalDate.of(2020, 12, 3)
		);
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

		// When
		rerateCCAFeature.preInvoke(ccaMasterVO);
		final var ratingParameterFromContext = (String) FeatureContextUtilThreadArray.getInstance()
                .getFeatureContext().getContextMap().get(PRICING_PARAMETER);
		var ccaMasterData = rerateCCAFeature.perform(ccaMasterVO);

		// Then
		assertEquals(ratingParameter, ratingParameterFromContext);
		final var revisedAWBData = ccaMasterData.getRevisedAWBData();
		switch (ratingParameter) {
			case "FRT":
				assertNotNull(revisedAWBData.getAwbRates());
				break;
			case "OTH":
				assertNotNull(revisedAWBData.getAwbCharges());
				break;
			case "AWB":
				assertNotNull(revisedAWBData.getAwbRates());
				assertNotNull(revisedAWBData.getAwbCharges());
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + ratingParameter);
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"FRT", "OTH", "AWB"})
	void shouldFetchAwbRatesIfRatingParameterWhenAwbChargesIsNull(final String ratingParameter) {
		// Given
		final var shipmentDetailVO = initShipmentDetailVo(ratingParameter);
		final var ccaMasterVO = getCCAMasterVO("88898832", "CCA000001",
				LocalDate.of(2020, 12, 3));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
		ccaMasterVO.getRevisedShipmentVO().setAwbCharges(null);

		// When
		rerateCCAFeature.preInvoke(ccaMasterVO);
		final var ratingParameterFromContext = (String) FeatureContextUtilThreadArray.getInstance()
				.getFeatureContext().getContextMap().get(PRICING_PARAMETER);
		var ccaMasterData = rerateCCAFeature.perform(ccaMasterVO);

		// Then
		assertEquals(ratingParameter, ratingParameterFromContext);
		if (!ratingParameter.equals("OTH")) {
			assertNotNull(ccaMasterData.getRevisedAWBData().getAwbRates());
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"FRT", "OTH", "AWB"})
	void shouldFetchAwbRatesIfRatingParameterWhenRevisedShipmentVOChargeIsNull(final String ratingParameter) {
		// Given
		final var shipmentDetailVO = initShipmentDetailVo(ratingParameter);
		final var ccaMasterVO = getCCAMasterVO("88898832", "CCA000001",
				LocalDate.of(2020, 12, 3));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
		final var awbCharges = ccaMasterVO
				.getRevisedShipmentVO()
				.getAwbCharges()
				.stream()
				.peek(ccaChargeDetailsVO -> ccaChargeDetailsVO.setCharge(null))
				.collect(Collectors.toList());
		ccaMasterVO.getRevisedShipmentVO().setAwbCharges(awbCharges);

		// When
		rerateCCAFeature.preInvoke(ccaMasterVO);
		final var ratingParameterFromContext = (String) FeatureContextUtilThreadArray.getInstance()
				.getFeatureContext().getContextMap().get(PRICING_PARAMETER);
		var ccaMasterData = rerateCCAFeature.perform(ccaMasterVO);

		// Then
		assertEquals(ratingParameter, ratingParameterFromContext);
		switch (ratingParameter) {
			case "FRT":
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbRates());
				break;
			case "OTH":
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbCharges());
				break;
			case "AWB":
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbRates());
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbCharges());
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + ratingParameter);
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"FRT", "OTH", "AWB"})
	void shouldFetchAwbRatesIfRatingParameterWhenRevisedShipmentVORecommendedChargeIsNull(final String ratingParameter) {
		// Given
		final var shipmentDetailVO = initShipmentDetailVo(ratingParameter);
		final var ccaMasterVO = getCCAMasterVO("88898832", "CCA000001",
				LocalDate.of(2020, 12, 3));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
		final var awbCharges = new ArrayList<>(ccaMasterVO
				.getRevisedShipmentVO()
				.getAwbCharges());
		ccaMasterVO.getRevisedShipmentVO().setAwbCharges(awbCharges);

		// When
		rerateCCAFeature.preInvoke(ccaMasterVO);
		final var ratingParameterFromContext = (String) FeatureContextUtilThreadArray.getInstance()
				.getFeatureContext().getContextMap().get(PRICING_PARAMETER);
		var ccaMasterData = rerateCCAFeature.perform(ccaMasterVO);

		// Then
		assertEquals(ratingParameter, ratingParameterFromContext);
		switch (ratingParameter) {
			case "FRT":
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbRates());
				break;
			case "OTH":
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbCharges());
				break;
			case "AWB":
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbRates());
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbCharges());
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + ratingParameter);
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"FRT", "OTH", "AWB"})
	void shouldFetchAwbRatesIfRatingParameterWhenRevisedShipmentVOChargeAndRecommendedChargeAreNull(final String ratingParameter) {
		// Given
		final var shipmentDetailVO = initShipmentDetailVo(ratingParameter);
		final var ccaMasterVO = getCCAMasterVO("88898832", "CCA000001",
				LocalDate.of(2020, 12, 3));
		ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
		final var awbCharges = ccaMasterVO
				.getRevisedShipmentVO()
				.getAwbCharges()
				.stream()
				.peek(ccaChargeDetailsVO -> ccaChargeDetailsVO.setCharge(null))
				.collect(Collectors.toList());
		ccaMasterVO.getRevisedShipmentVO().setAwbCharges(awbCharges);

		// When
		rerateCCAFeature.preInvoke(ccaMasterVO);
		final var ratingParameterFromContext = (String) FeatureContextUtilThreadArray.getInstance()
				.getFeatureContext().getContextMap().get(PRICING_PARAMETER);
		var ccaMasterData = rerateCCAFeature.perform(ccaMasterVO);

		// Then
		assertEquals(ratingParameter, ratingParameterFromContext);
		switch (ratingParameter) {
			case "FRT":
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbRates());
				break;
			case "OTH":
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbCharges());
				break;
			case "AWB":
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbRates());
				assertNotNull(ccaMasterData.getRevisedAWBData().getAwbCharges());
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + ratingParameter);
		}
	}

	private CcaAwbVO initShipmentDetailVo(final String ratingParameter) {
		final var shipmentDetailVO = new CcaAwbVO();
		shipmentDetailVO.setOrigin("CDG");
		shipmentDetailVO.setDestination("DXB");
		shipmentDetailVO.setCurrency("USD");
//		shipmentDetailVO.setAgentCode("TESTAGENT");
		shipmentDetailVO.setRatingParameter(ratingParameter);
		shipmentDetailVO.setNetValueExport(getMoney(1.0, "USD"));
		shipmentDetailVO.setNetValueImport(getMoney(2.0, "USD"));

		setAwbChargeDetailsToShipmentDetail(shipmentDetailVO);
		setAwbRateToShipmentDetailVo(shipmentDetailVO);

		return shipmentDetailVO;
	}

	private void setAwbChargeDetailsToShipmentDetail(final CcaAwbVO shipmentDetailVO) {
		final var awbChargeDetailsVO = new CcaChargeDetailsVO();
		awbChargeDetailsVO.setChargeHead("Flat Charge");
		awbChargeDetailsVO.setChargeHeadCode("FC");
		awbChargeDetailsVO.setCharge(getMoney(200.0, "USD"));
		shipmentDetailVO.setAwbCharges(List.of(awbChargeDetailsVO));
	}

	private void setAwbRateToShipmentDetailVo(final CcaAwbVO shipmentDetailVO) {
		final var awbRate = new CcaRateDetailsVO();
		awbRate.setRateType("IATA");
		awbRate.setCommodityCode("GEN");
		awbRate.setRate(10.0);
		awbRate.setCharge(getMoney(15.0, "USD"));
		shipmentDetailVO.setAwbRates(List.of(awbRate));
	}

}
