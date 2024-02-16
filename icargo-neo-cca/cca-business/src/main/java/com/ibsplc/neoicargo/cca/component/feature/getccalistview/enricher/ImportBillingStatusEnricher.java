package com.ibsplc.neoicargo.cca.component.feature.getccalistview.enricher;

import com.ibsplc.neoicargo.cca.constants.InboundBillingStatus;
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
public class ImportBillingStatusEnricher  extends Enricher<CCAListViewFilterVO> {

    @Override
    public void enrich(CCAListViewFilterVO ccaListViewFilterVO) {
        final var importBillingStatus = ccaListViewFilterVO.getImportBillingStatus();
        if (importBillingStatus != null) {
            final var frontendToDb = Arrays.stream(InboundBillingStatus.values())
                    .collect(Collectors.toMap(
                            InboundBillingStatus::getStatusValueInFrontend,
                            InboundBillingStatus::getStatusValueInDb)
                    );
            ccaListViewFilterVO.setImportBillingStatus(
                    importBillingStatus.stream()
                            .map(frontendToDb::get)
                            .collect(Collectors.toList())
            );
        }
    }
}
