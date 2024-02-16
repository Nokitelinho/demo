package com.ibsplc.neoicargo.cca.component.feature.maintainccalist;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.modal.CCAWorkflowData;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_ORIGINAL;

@Slf4j
@Component
@AllArgsConstructor
public class GetCCADetailsFeature {

    private final CcaDao ccaDao;
    private final CcaMasterMapper ccaMasterMapper;

    public CCAMasterData perform(CcaDataFilter ccaDataFilter) {
        log.info("getCCADetails Feature ->: {}", ccaDataFilter);
        return Optional.ofNullable(ccaDao.getCCADetails(ccaDataFilter))
                .map(ccaMasterVO -> {
                    ccaMasterVO.getShipmentDetailVOs().forEach(s -> {
                        if (CCA_RECORD_TYPE_ORIGINAL.equals(s.getRecordType())) {
                            ccaMasterVO.setOriginalShipmentVO(s);
                        } else {
                            ccaMasterVO.setRevisedShipmentVO(s);
                        }
                    });
                    final var ccaMasterData = ccaMasterMapper.constructCCAMasterData(ccaMasterVO);
                    return populateData(ccaMasterData);
                })
                .orElse(null);
    }

    /**
     * Creating dummy data for UI Styling and other requirements
     * Should be removed after billing User stories are done
     **/
    private CCAMasterData populateData(CCAMasterData ccaMasterData) {
        final var dtf = DateTimeFormatter.ofPattern("dd MMM yy");

        var formattedDateNow = LocalDate.now().format(dtf);
        final var billingPeriod = formattedDateNow.concat("-").concat(formattedDateNow);
        ccaMasterData.setOutboundBillingPeriod(billingPeriod);
        ccaMasterData.setInboundBillingPeriod(billingPeriod);
        ccaMasterData.setNonAWBChargesBillingPeriod(billingPeriod);
        return ccaMasterData;
    }

}
