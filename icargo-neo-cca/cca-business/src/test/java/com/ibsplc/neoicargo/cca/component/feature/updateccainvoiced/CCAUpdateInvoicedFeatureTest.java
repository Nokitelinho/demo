package com.ibsplc.neoicargo.cca.component.feature.updateccainvoiced;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getBasicMockCcaMaster;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAFilterVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CCAUpdateInvoicedFeatureTest {

	@Mock
	private CcaDao ccaDao;

	@InjectMocks
	private CCAUpdateInvoicedFeature ccaUpdateInvoicedFeature;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldUpdateCcaExportStatus() {
		// Given
		final var ccaFilterVO = getCCAFilterVO(SHIPMENT_PREFIX, "23323311", "CCA200034");
		ccaFilterVO.setAwbIndicator("CCA");
		ccaFilterVO.setExportBillingStatus("E");
		ccaFilterVO.setImportBillingStatus("I");
		ccaFilterVO.setBillingStatus("INV");

		final var ccaMaster = getBasicMockCcaMaster("CCA200034", "23323311");
		ccaMaster.setCcaSerialNumber(3L);
		ccaMaster.setExportBillingStatus("E");
		ccaMaster.setImportBillingStatus("I");

		// When
		doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
		doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));

		// Then
		assertDoesNotThrow(() -> ccaUpdateInvoicedFeature.perform(ccaFilterVO));
	}

	@Test
	void shouldCheckCcaMasterOnEmptyOptional() {
		final var ccaFilterVO = getCCAFilterVO(SHIPMENT_PREFIX, "23323311", "CCA200034");
		ccaFilterVO.setAwbIndicator("CCA");
		ccaFilterVO.setExportBillingStatus("E");
		ccaFilterVO.setImportBillingStatus("I");
		ccaFilterVO.setBillingStatus("INV");
		// When
		doReturn(Optional.empty()).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));

		// Then
		assertDoesNotThrow(() -> ccaUpdateInvoicedFeature.perform(ccaFilterVO));
	}

}
