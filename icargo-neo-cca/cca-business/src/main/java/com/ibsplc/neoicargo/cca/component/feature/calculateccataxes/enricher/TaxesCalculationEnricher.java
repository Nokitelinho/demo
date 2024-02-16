package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.enricher;

import com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.calculators.TaxesAndCommissionCalculator;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaxesCalculationEnricher extends Enricher<CCAMasterVO> {

    private final TaxesAndCommissionCalculator taxCalculator;

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) {
        log.info("Total Tax Enricher called for CCA [{}]", ccaMasterVO.getBusinessId());
        var revisedCcaAwbVO = ccaMasterVO.getRevisedShipmentVO();
        revisedCcaAwbVO.setAwbTaxes(taxCalculator.computeTax(revisedCcaAwbVO));
    }

}
