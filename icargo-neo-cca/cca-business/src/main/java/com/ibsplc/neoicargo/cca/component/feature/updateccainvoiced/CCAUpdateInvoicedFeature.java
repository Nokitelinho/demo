package com.ibsplc.neoicargo.cca.component.feature.updateccainvoiced;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CCAUpdateInvoicedFeature {

    private final CcaDao ccaDao;

    public void perform(CCAFilterVO ccaFilterVO) {
        log.info("Invoked CCA Update Invoiced Feature");
        ccaDao.updateExportAndImportBillingStatus(ccaFilterVO);
    }

}
