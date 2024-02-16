package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.util.CcaParameterUtil;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyUtils;
import com.ibsplc.neoicargo.masters.ParameterType;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.AUTO_CCA_SOURCE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_ACTUAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_INTERNAL;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CCAReferenceNumberEnricherTest {

	@Mock
	private KeyUtils keyUtils;

	@Mock
	private CcaParameterUtil awbParameterUtil;

	@Mock
	private AirlineWebComponent airlineComponent;

	@InjectMocks
	private CCAReferenceNumberEnricher ccaReferenceNumberEnricher;

	@BeforeEach
	void setup() throws BusinessException {
		MockitoAnnotations.openMocks(this);

		// Given
		final var airlineModel = new AirlineModel();
		airlineModel.setAirlineIdentifier(1134);

		// When
		doReturn(airlineModel).when(airlineComponent).validateNumericCode(any(String.class));
	}

	@Test
	void shouldEnrichCCAReferenceNumber() {
		// Given
		final var ccaMasterVO = getCCAMasterVO("12346548", "CCA000001", LocalDate.now());
		ccaMasterVO.setCcaType("A");

		// When
		doReturn("Y").when(awbParameterUtil).getSystemParameter(isA(String.class), any(ParameterType.class));
		doReturn("CCA000001").when(keyUtils).getKey(any());

		// Then
		assertDoesNotThrow(() -> ccaReferenceNumberEnricher.enrich(ccaMasterVO));
		assertEquals(1134, ccaMasterVO.getDocumentOwnerId());
		assertEquals("CCA000001", ccaMasterVO.getCcaReferenceNumber());
	}

	@Test
	void shouldNotEnrichCCAReferenceNumberIfAlreadyPresent() {
		// Given
		final var ccaMasterVO = getCCAMasterVO("12346548", "CCA000008", LocalDate.now());
		ccaMasterVO.setCcaType("A");

		// When
		doReturn("Y").when(awbParameterUtil).getSystemParameter(isA(String.class), any(ParameterType.class));
		doReturn("CCA000005").when(keyUtils).getKey(any());

		// Then
		assertDoesNotThrow(() -> ccaReferenceNumberEnricher.enrich(ccaMasterVO));
		assertEquals(1134, ccaMasterVO.getDocumentOwnerId());
		assertEquals("CCA000008", ccaMasterVO.getCcaReferenceNumber());
	}

	@Test
	void shouldGenerateCCAReferenceNumberWithCCAPrefixIfSystemParameterNotFound() {
		// Given
		final var ccaMasterVO = getCCAMasterVO("12346548", "CCA000001", LocalDate.now());
		ccaMasterVO.setCcaType(CCA_TYPE_ACTUAL);

		// When
		doReturn(null).when(awbParameterUtil).getSystemParameter(isA(String.class), any(ParameterType.class));
		doReturn("CCA000001").when(keyUtils).getKey(any());

		// Then
		assertDoesNotThrow(() -> ccaReferenceNumberEnricher.enrich(ccaMasterVO));
		assertEquals(1134, ccaMasterVO.getDocumentOwnerId());
		assertEquals("CCA000001", ccaMasterVO.getCcaReferenceNumber());
	}

	@Test
	void shouldGenerateCCAReferenceNumberWithCCIPrefixIfSystemParameterNotFound() {
		// Given
		final var ccaMasterVO = getCCAMasterVO("12346548", "CCI000001", LocalDate.now());
		ccaMasterVO.setCcaType(CCA_TYPE_INTERNAL);

		// When
		doReturn("Y").when(awbParameterUtil).getSystemParameter(isA(String.class), any(ParameterType.class));
		doReturn("CCI000001").when(keyUtils).getKey(any());

		// Then
		assertDoesNotThrow(() -> ccaReferenceNumberEnricher.enrich(ccaMasterVO));
		assertEquals(1134, ccaMasterVO.getDocumentOwnerId());
		assertEquals("CCI000001", ccaMasterVO.getCcaReferenceNumber());
	}

    @ParameterizedTest
    @MethodSource("ccaReferenceNumberAndCcaSource")
    void shouldEnrichCCAReferenceNumberWhenCCARefNumNullOrEmpty(final String ccaReferenceNumber, final String ccaSource) {
        // Given
        final var ccaMasterVO = getCCAMasterVO("12346548", "CCA000001", LocalDate.now());
        ccaMasterVO.setCcaSource(ccaSource);
        ccaMasterVO.setCcaType(CCA_TYPE_ACTUAL);
        ccaMasterVO.setCcaReferenceNumber(ccaReferenceNumber);

		// When
		doReturn("Y").when(awbParameterUtil).getSystemParameter(isA(String.class), any(ParameterType.class));

		// Then
		assertDoesNotThrow(() -> ccaReferenceNumberEnricher.enrich(ccaMasterVO));
		assertEquals(1134, ccaMasterVO.getDocumentOwnerId());
		assertEquals(CcaStatus.N, ccaMasterVO.getCcaStatus());
		assertNotNull(ccaMasterVO.getCcaIssueDate());
		if (ccaReferenceNumber == null) {
			assertNull(ccaMasterVO.getCcaReferenceNumber());
		} else {
			assertNotEquals(ccaMasterVO.getCcaReferenceNumber(), ccaReferenceNumber);
		}
	}

	@ParameterizedTest
	@MethodSource("internalCCASequencePossibleFlags")
	void whenGenerateCCAReferenceNumberPrefixShouldBeCCA(final String internalCCASequenceRequired) {
		// Given
		final var documentOwnerIdr = 111222;

		// When
		doReturn(internalCCASequenceRequired).when(awbParameterUtil).getSystemParameter(isA(String.class), any(ParameterType.class));
		final var criterion = ccaReferenceNumberEnricher.generateCCAReferenceNumber(documentOwnerIdr, CCA_TYPE_ACTUAL);

		// Then
		assertNotNull(criterion);
		assertNotNull(criterion.getSequenceName());
		assertFalse(criterion.getSequenceName().isBlank());
		assertNotNull(criterion.getNumberFormat());
		assertFalse(criterion.getNumberFormat().isBlank());
		assertEquals("CCA", criterion.getPrefix());
	}

	@Test
	void whenGenerateCCAReferenceNumberPrefixShouldBeCCI() {
		// Given
		final var documentOwnerIdr = 111222;

		// When
		doReturn("Y").when(awbParameterUtil).getSystemParameter(isA(String.class), any(ParameterType.class));
		final var criterion = ccaReferenceNumberEnricher.generateCCAReferenceNumber(documentOwnerIdr, CCA_TYPE_INTERNAL);

		// Then
		assertNotNull(criterion);
		assertNotNull(criterion.getSequenceName());
		assertFalse(criterion.getSequenceName().isBlank());
		assertNotNull(criterion.getNumberFormat());
		assertFalse(criterion.getNumberFormat().isBlank());
		assertEquals("CCI", criterion.getPrefix());
	}

	@Test
	void getKeyConditionShouldBeNotNull() {
		// Given
		final var documentOwnerIdr = 111222;

		// When
		doReturn("Y").when(awbParameterUtil).getSystemParameter(isA(String.class), any(ParameterType.class));
		final var criterion = ccaReferenceNumberEnricher.generateCCAReferenceNumber(documentOwnerIdr, CCA_TYPE_INTERNAL);
		final var keyConditionList = criterion.getKeyConditionList();

		// Then
		assertNotNull(criterion);
		assertNotNull(keyConditionList);
		assertFalse(keyConditionList.isEmpty());
		assertEquals(1, keyConditionList.size());
		assertNotNull(keyConditionList.get(0).getKey());
		assertFalse(keyConditionList.get(0).getKey().isBlank());
		assertEquals(String.valueOf(documentOwnerIdr), keyConditionList.get(0).getValue());
	}

    @Test
    void shouldEnrichCCAReferenceNumberAndSetApproveStatus() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("12346548", "CCA000001", LocalDate.now());
        ccaMasterVO.setCcaType(CCA_TYPE_ACTUAL);
        ccaMasterVO.setCcaStatus(CcaStatus.A);
        ccaMasterVO.setCcaReferenceNumber(null);

        // When
        doReturn("Y").when(awbParameterUtil).getSystemParameter(isA(String.class), any(ParameterType.class));

        // Then
        assertDoesNotThrow(() -> ccaReferenceNumberEnricher.enrich(ccaMasterVO));
        assertEquals(CcaStatus.A, ccaMasterVO.getCcaStatus());
    }

    private static Stream<Arguments> ccaReferenceNumberAndCcaSource() {
        return Stream.of(
                Arguments.of("", AUTO_CCA_SOURCE),
                Arguments.of("   ", "Test"),
                Arguments.of(null, "Auto Cca"));
    }

	private static Stream<String> internalCCASequencePossibleFlags() {
		return Stream.of("ANY STRING", "Y", "", "   ", null);
	}

}
