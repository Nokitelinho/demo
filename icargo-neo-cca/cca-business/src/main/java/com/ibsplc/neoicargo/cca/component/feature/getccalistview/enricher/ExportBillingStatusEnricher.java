package com.ibsplc.neoicargo.cca.component.feature.getccalistview.enricher;

import com.ibsplc.neoicargo.cca.constants.OutboundBillingStatus;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExportBillingStatusEnricher extends Enricher<CCAListViewFilterVO> {

    @Override
    public void enrich(CCAListViewFilterVO ccaListViewFilterVO) {
        final var exportBillingStatus = ccaListViewFilterVO.getExportBillingStatus();
        if (exportBillingStatus != null) {
            final var frontendToDb = Arrays.stream(OutboundBillingStatus.values())
                    .collect(Collectors.toMap(
                            OutboundBillingStatus::getStatusValueInFrontend,
                            OutboundBillingStatus::getStatusValueInDb)
                    );
            ccaListViewFilterVO.setExportBillingStatus(
                    exportBillingStatus.stream()
                            .map(frontendToDb::get)
                            .collect(Collectors.toList())
            );
        }
    }
}
