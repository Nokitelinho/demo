package com.ibsplc.neoicargo.cca.component.feature.getnetvalues;

import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.modal.NetValuesData;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@FeatureConfigSource("cca/getnetvalues")
@AllArgsConstructor
public class GetNetValuesFeature extends AbstractFeature<CCAMasterVO> {

    @Override
    protected void preInvoke(CCAMasterVO ccaMasterVO) {
        FeatureContextUtilThreadArray
                .getInstance()
                .getFeatureContext()
                .getContextMap()
                .put(CcaConstants.USE_ORIGINAL_AWB_VALUES, true);
    }

    @Override
    public NetValuesData perform(CCAMasterVO ccaMasterVO) {
        log.info("Invoked GetNetValues feature");
        return new NetValuesData(
                Optional.ofNullable(ccaMasterVO.getRevisedShipmentVO().getNetValueExport())
                        .map(v -> v.getAmount().doubleValue())
                        .orElse(null),
                Optional.ofNullable(ccaMasterVO.getRevisedShipmentVO().getNetValueImport())
                        .map(v -> v.getAmount().doubleValue())
                        .orElse(null)
        );
    }
}
