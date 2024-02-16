package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes;

import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FeatureConfigSource("cca/calculateccataxes")
@AllArgsConstructor
public class CalculateCCATaxesFeature extends AbstractFeature<CCAMasterVO> {

    @Override
    public Void perform(CCAMasterVO ccaMasterVO) {
        log.info("Calculate CCA Feature -> Invoked");
        log.info("Tax, Discount, Commission are re-calculated for CCA [{}]", ccaMasterVO.getBusinessId());
        return null;
    }
}