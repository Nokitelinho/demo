package com.ibsplc.neoicargo.cca.component.feature.getccalistview.enricher;

import com.ibsplc.neoicargo.cca.modal.viewfilter.DateRangeFilterData;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
public class LocalDateEnricherTest {

    @InjectMocks
    private LocalDateEnricher localDateEnricher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldEnrichLocalDate() {

        // Given
        var dateRangeFilterData = new DateRangeFilterData();
        dateRangeFilterData.setFrom("2022-07-12");
        dateRangeFilterData.setTo("2022-07-13");

        var ccaListViewFilterVO = new CCAListViewFilterVO();
        ccaListViewFilterVO.setCcaIssueDate(dateRangeFilterData);

        // When
        localDateEnricher.enrich(ccaListViewFilterVO);

        // Then
        assertEquals(
                LocalDate.of(2022, 7, 12),
                ccaListViewFilterVO.getCcaIssueDate().getFromLocalDate()
        );
        assertEquals(
                LocalDate.of(2022, 7, 13),
                ccaListViewFilterVO.getCcaIssueDate().getToLocalDate()
        );

    }

    @Test
    void shouldSkipEnrichingIfNoIssueDate() {
        var dateRangeFilterData = new DateRangeFilterData();
        var ccaListViewFilterVO = new CCAListViewFilterVO();
        ccaListViewFilterVO.setCcaIssueDate(dateRangeFilterData);

        // Then
        assertDoesNotThrow(() -> localDateEnricher.enrich(ccaListViewFilterVO));

        // And Then
        assertDoesNotThrow(() -> localDateEnricher.enrich(new CCAListViewFilterVO()));
    }
}
