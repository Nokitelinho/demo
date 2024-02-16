package com.ibsplc.neoicargo.cca.component.feature.maintainccalist;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.List;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaDataFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class GetRelatedCCAFeatureTest {

	@Mock
	private CcaDao ccaDao;

	@InjectMocks
	private GetRelatedCCAFeature getRelatedCCAFeature;

	@Spy
	private CcaMasterMapper ccaMasterMapper = Mappers.getMapper(CcaMasterMapper.class);

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldGetRelatedCCA() {
		// Given
		final var ccaMasterVO = getCCAMasterVO("44440000", "CCA000001", LocalDate.of(2020, 12, 3));
		final var ccaDataFilter = getCcaDataFilter(SHIPMENT_PREFIX, "44440000", null, "AV");

		// When
		doReturn(List.of(ccaMasterVO)).when(ccaDao).getRelatedCCA(any(CcaDataFilter.class));
		final var relatedCCAData = getRelatedCCAFeature.perform(ccaDataFilter);

		// Then
		assertEquals("CCA000001", relatedCCAData.get(0).getCcaReferenceNumber());
	}

}
