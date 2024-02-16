package com.ibsplc.neoicargo.cca.component.feature.getccalistview.enricher;

import com.ibsplc.neoicargo.cca.constants.OutboundBillingStatus;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
public class ExportBillingStatusEnricherTest {

    @InjectMocks
    private ExportBillingStatusEnricher exportBillingStatusEnricher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldEnrichExportBillingStatus() {
        // Given
        var ccaListViewFilterVO = new CCAListViewFilterVO();
        ccaListViewFilterVO.setExportBillingStatus(
                List.of(
                        OutboundBillingStatus.CASS_BILLABLE.getStatusValueInFrontend(),
                        OutboundBillingStatus.CASS_BILLED.getStatusValueInFrontend()
                )
        );

        // When
        exportBillingStatusEnricher.enrich(ccaListViewFilterVO);

        // Then
        assertEquals(
                List.of(
                        OutboundBillingStatus.CASS_BILLABLE.getStatusValueInDb(),
                        OutboundBillingStatus.CASS_BILLED.getStatusValueInDb()
                ),
                ccaListViewFilterVO.getExportBillingStatus()
        );
    }
}
