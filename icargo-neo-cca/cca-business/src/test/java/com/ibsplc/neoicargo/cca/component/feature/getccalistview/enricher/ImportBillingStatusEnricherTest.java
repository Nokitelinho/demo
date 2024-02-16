package com.ibsplc.neoicargo.cca.component.feature.getccalistview.enricher;

import com.ibsplc.neoicargo.cca.constants.InboundBillingStatus;
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
public class ImportBillingStatusEnricherTest {

    @InjectMocks
    private ImportBillingStatusEnricher importBillingStatusEnricher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldEnrichImportBillingStatus() {
        // Given
        var ccaListViewFilterVO = new CCAListViewFilterVO();
        ccaListViewFilterVO.setImportBillingStatus(
                List.of(
                        InboundBillingStatus.IMPORT_BILLED.getStatusValueInFrontend(),
                        InboundBillingStatus.IMPORT_BILLABLE.getStatusValueInFrontend()
                )
        );

        // When
        importBillingStatusEnricher.enrich(ccaListViewFilterVO);

        // Then
        assertEquals(
                List.of(
                        InboundBillingStatus.IMPORT_BILLED.getStatusValueInDb(),
                        InboundBillingStatus.IMPORT_BILLABLE.getStatusValueInDb()
                ),
                ccaListViewFilterVO.getImportBillingStatus()
        );
    }
}
