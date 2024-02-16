package com.ibsplc.neoicargo.cca.component.feature.updateccainvoiced;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@RunWith(JUnitPlatform.class)
class CCAUpdateVoidedFeatureTest {

    @Mock
    private CcaDao ccaDao;

    @InjectMocks
    private CCAUpdateVoidedFeature ccaUpdateVoidedFeature;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateAfterVoidedEventWithoutError() {
        // When
        doNothing().when(ccaDao).updateExportAndImportBillingStatus(any(CCAFilterVO.class));

        // Then
        assertDoesNotThrow(() -> ccaUpdateVoidedFeature.perform(new AwbVoidedEvent()));
    }
}
