package com.ibsplc.neoicargo.cca.component.feature.report;

import com.ibsplc.neoicargo.cca.component.feature.getavailablereasoncodes.GetAvailableReasonCodesFeature;
import com.ibsplc.neoicargo.cca.component.feature.maintainccalist.GetCCADetailsFeature;
import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaPrintMapper;
import com.ibsplc.neoicargo.cca.modal.AvailableReasonCodeData;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAPrintFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.report.jasper.JasperReportController;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.COMPANY_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.QUANTITIES;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnitPlatform.class)
class CCAPrintFeatureTest {

	@Mock
	private GetCCADetailsFeature getCCADetailsFeature;

	@Mock
	private CcaDao ccaDao;

	@Mock
	private CcaPrintMapper ccaPrintMapper;

	@Mock
	private GetAvailableReasonCodesFeature getAvailableReasonCodesFeature;

	@Mock
	private JasperReportController jasperReportController;

	@Mock
	private AirlineWebComponent airlineWebComponent;

	@InjectMocks
	private CCAPrintFeature ccaPrintFeature;


	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void generateJasperReportShouldNotThrowErrorWithDisplayTypeK() {
		// Given
		var ccaPrintFilterVO = getCCAPrintFilterVO();
        var orgCcaAwbVO = getOriginCCAAwbVo("PP", "K");
        var revCcaAwbVO = getRevisedCCAAwbVo("CC", "K");
        var ccaMasterVO = preparedCCaMasterVo(orgCcaAwbVO, revCcaAwbVO);
        var generatedResult = new byte[10];

		// When
		doReturn(ccaMasterVO).when(ccaDao).getCCADetails(any());

		doReturn(generatedResult).when(jasperReportController).generateReport(any(), any());
		doReturn(Collections.singletonList(new AvailableReasonCodeData("C1","Test Reason"))).when(getAvailableReasonCodesFeature).perform();

		// Then
		assertDoesNotThrow(() -> ccaPrintFeature.perform(ccaPrintFilterVO));
	}

	@Test
	void generateJasperReportShouldNotThrowErrorWhenCannotGetAirlineCode() throws BusinessException {
		// Given
		var ccaPrintFilterVO = getCCAPrintFilterVO();
        var orgCcaAwbVO = getOriginCCAAwbVo("PP", "K");
        var revCcaAwbVO = getRevisedCCAAwbVo("CC", "K");
        var ccaMasterVO = preparedCCaMasterVo(orgCcaAwbVO, revCcaAwbVO);
        var generatedResult = new byte[10];

		// When
		doThrow(new BusinessException(new RuntimeException())).when(airlineWebComponent).validateNumericCode(anyString());
		doReturn(ccaMasterVO).when(ccaDao).getCCADetails(any());
		doReturn(generatedResult).when(jasperReportController).generateReport(any(), any());
		doReturn(Collections.singletonList(new AvailableReasonCodeData("C1","Test Reason"))).when(getAvailableReasonCodesFeature).perform();

		// Then
		assertDoesNotThrow(() -> ccaPrintFeature.perform(ccaPrintFilterVO));
	}

	@Test
	void generateJasperReportShouldNotThrowErrorWithDisplayTypeL() {
		// Given
		var ccaPrintFilterVO = getCCAPrintFilterVO();
        var orgCcaAwbVO = getOriginCCAAwbVo("CC", "L");
        var revCcaAwbVO = getRevisedCCAAwbVo("PP", "L");
        var ccaMasterVO = preparedCCaMasterVo(orgCcaAwbVO, revCcaAwbVO);
        var generatedResult = new byte[10];

		// When
		doReturn(ccaMasterVO).when(ccaDao).getCCADetails(any());
		doReturn(generatedResult).when(jasperReportController).generateReport(any(), any());
		doReturn(Collections.singletonList(new AvailableReasonCodeData("C1","Test Reason"))).when(getAvailableReasonCodesFeature).perform();

		// Then
		assertDoesNotThrow(() -> ccaPrintFeature.perform(ccaPrintFilterVO));
	}

	@Test
	void generateJasperReportShouldNotThrowErrorWhenDisplayTypeIsT() throws Exception {
		// Given
        var ccaPrintFilterVO = getCCAPrintFilterVO();
        var orgCcaAwbVO = getOriginCCAAwbVo("PP", "K");
        var revCcaAwbVO = getRevisedCCAAwbVo("CC", "K");
        //TODO Remove after up ntest framework dating MockQuantity
		Field displayUnit = orgCcaAwbVO.getWeight().getDisplayUnit().getClass().getDeclaredField("name");
		displayUnit.setAccessible(true);
		displayUnit.set(orgCcaAwbVO.getWeight().getDisplayUnit(),"T");

        var ccaMasterVO = preparedCCaMasterVo(orgCcaAwbVO, revCcaAwbVO);
        var generatedResult = new byte[10];

		// When
		doReturn(ccaMasterVO).when(ccaDao).getCCADetails(any());
		doReturn(generatedResult).when(jasperReportController).generateReport(any(), any());
		doReturn(Collections.singletonList(new AvailableReasonCodeData("C1","Test Reason"))).when(getAvailableReasonCodesFeature).perform();

		// Then
		assertDoesNotThrow(() -> ccaPrintFeature.perform(ccaPrintFilterVO));
	}

	@Test
	void generateJasperReportShouldNotThrowErrorWhenPayTypeIsPC() {
		// Given
		var ccaPrintFilterVO = getCCAPrintFilterVO();
		var orgCcaAwbVO = getOriginCCAAwbVo("PP", "K");
		var revCcaAwbVO = getRevisedCCAAwbVo("PP", "K");
		var ccaMasterVO = preparedCCaMasterVo(orgCcaAwbVO, revCcaAwbVO);
		orgCcaAwbVO.setAwbOtherChargePaymentType("CC");
		revCcaAwbVO.setAwbOtherChargePaymentType("CC");
		var generatedResult = new byte[10];

		// When
		doReturn(ccaMasterVO).when(ccaDao).getCCADetails(any());
		doReturn(generatedResult).when(jasperReportController).generateReport(any(), any());
		doReturn(Collections.singletonList(new AvailableReasonCodeData("C1","Test Reason"))).when(getAvailableReasonCodesFeature).perform();

		// Then
		assertDoesNotThrow(() -> ccaPrintFeature.perform(ccaPrintFilterVO));
	}

	@Test
	void generateJasperReportShouldThrowError() {
		// Given
		final var ccaPrintFilterVO = getCCAPrintFilterVO();

		// When
		doReturn(null).when(ccaDao).getCCADetails(any());

		// Then
		assertThrows(BusinessException.class, () -> ccaPrintFeature.perform(ccaPrintFilterVO));
	}

	@NotNull
	private CCAMasterVO preparedCCaMasterVo(CcaAwbVO orgCcaAwbVO, CcaAwbVO revCcaAwbVO) {
		var ccaMaster = getCCAMasterVO("44440000", "CCAA000001",
				LocalDate.of(2020, 12, 3), Set.of(orgCcaAwbVO, revCcaAwbVO));
		ccaMaster.setCcaReason("C1");
		return ccaMaster;

	}

	@NotNull
	private CcaAwbVO getRevisedCCAAwbVo(String payType, String displayUnit) {
		final var revCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
		revCcaAwbVO.setPayType(payType);
		revCcaAwbVO.setWeightCharge(123);
		revCcaAwbVO.setValuationCharge(123);
		revCcaAwbVO.setAwbCCTaxAmount(123);
		revCcaAwbVO.setAwbOtherChargePaymentType(payType);
		revCcaAwbVO.setTotalNonAWBCharge(345);
		revCcaAwbVO.setTotalDueCarCCFChg(567);
		revCcaAwbVO.setTotalDueAgtCCFChg(567);
		revCcaAwbVO.setWeight(QUANTITIES.getQuantity("WGT", BigDecimal.valueOf(100), BigDecimal.valueOf(100), displayUnit));
		revCcaAwbVO.setAdjustedWeight(QUANTITIES.getQuantity("WGT", BigDecimal.valueOf(200), BigDecimal.valueOf(200), displayUnit));
		revCcaAwbVO.setChargeableWeight(QUANTITIES.getQuantity("WGT", BigDecimal.valueOf(300), BigDecimal.valueOf(300), displayUnit));
		revCcaAwbVO.setVolumetricWeight(QUANTITIES.getQuantity("WGT", BigDecimal.valueOf(400), BigDecimal.valueOf(400), displayUnit));
		revCcaAwbVO.setUnitOfMeasure(new Units().weight(displayUnit).volume("B"));
		return revCcaAwbVO;
	}

	@NotNull
	private CcaAwbVO getOriginCCAAwbVo(String payType, String displayUnit) {
		final var orgCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
		orgCcaAwbVO.setPayType(payType);
		orgCcaAwbVO.setWeightCharge(123);
		orgCcaAwbVO.setValuationCharge(123);
		orgCcaAwbVO.setAwbPPTaxAmount(123);
		orgCcaAwbVO.setAwbOtherChargePaymentType(payType);
		orgCcaAwbVO.setTotalNonAWBCharge(345);
		orgCcaAwbVO.setTotalDueAgtPPDChg(567);
		orgCcaAwbVO.setTotalDueAgtPPDChg(567);
		orgCcaAwbVO.setWeight(QUANTITIES.getQuantity("WGT", BigDecimal.valueOf(100), BigDecimal.valueOf(100), displayUnit));
		orgCcaAwbVO.setAdjustedWeight(QUANTITIES.getQuantity("WGT", BigDecimal.valueOf(200), BigDecimal.valueOf(200), displayUnit));
		orgCcaAwbVO.setChargeableWeight(QUANTITIES.getQuantity("WGT", BigDecimal.valueOf(300), BigDecimal.valueOf(300), displayUnit));
		orgCcaAwbVO.setVolumetricWeight(QUANTITIES.getQuantity("WGT", BigDecimal.valueOf(400), BigDecimal.valueOf(400), displayUnit));
		orgCcaAwbVO.setUnitOfMeasure(new Units().weight(displayUnit).volume("B"));
		return orgCcaAwbVO;
	}


	@NotNull
	private CCAPrintFilterVO getCCAPrintFilterVO() {
		var ccaPrintFilterVO = new CCAPrintFilterVO();
		ccaPrintFilterVO.setCcaReferenceNumber("CCAA000001");
		ccaPrintFilterVO.setReportName("Cargo Charge Correction");
		ccaPrintFilterVO.setShipmentPrefix(SHIPMENT_PREFIX);
		ccaPrintFilterVO.setMasterDocumentNumber("44225566");
		ccaPrintFilterVO.setCompanyCode(COMPANY_CODE);
		ccaPrintFilterVO.setCcaReferenceNumber(COMPANY_CODE);
		return ccaPrintFilterVO;
	}

}
