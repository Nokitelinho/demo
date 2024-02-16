package com.ibsplc.neoicargo.cca.component.feature.reratecca;

import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FeatureConfigSource("cca/reratecca")
@AllArgsConstructor
public class RerateCCAFeature extends AbstractFeature<CCAMasterVO> {

    private final CcaMasterMapper ccaMasterMapper;

    @Override
    protected void preInvoke(CCAMasterVO ccaMasterVO) {
        FeatureContextUtilThreadArray
                .getInstance()
                .getFeatureContext()
                .getContextMap()
                .put(CcaConstants.PRICING_PARAMETER, ccaMasterVO.getRevisedShipmentVO().getRatingParameter());
    }

    @Override
    public CCAMasterData perform(CCAMasterVO ccaMasterVO) {
        final var ccaAwbVo = ccaMasterVO.getRevisedShipmentVO();
        if (ccaAwbVo.getRatingParameter().equals("FRT")) {
            ccaAwbVo.setAwbCharges(null);
        } else if (ccaAwbVo.getRatingParameter().equals("OTH")) {
            ccaAwbVo.setAwbRates(null);
        }
        return ccaMasterMapper.constructCCAMasterData(ccaMasterVO);
    }

}
