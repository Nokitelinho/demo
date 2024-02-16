package com.ibsplc.neoicargo.cca.component.feature.getccalistview.enricher;

import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalDateEnricher  extends Enricher<CCAListViewFilterVO> {

    @Override
    public void enrich(CCAListViewFilterVO ccaListViewFilterVO) {
        final var ccaIssueDate = ccaListViewFilterVO.getCcaIssueDate();
        if (ccaIssueDate != null) {
            final var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (ccaIssueDate.getFrom() != null) {
                ccaIssueDate.setFromLocalDate(LocalDate.parse(ccaIssueDate.getFrom(), dateTimeFormatter));
            }
            if (ccaIssueDate.getTo() != null) {
                ccaIssueDate.setToLocalDate(LocalDate.parse(ccaIssueDate.getTo(), dateTimeFormatter));
            }
        }
    }
}
