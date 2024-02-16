package com.ibsplc.neoicargo.cca.component.feature.getccanumbers;

import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FeatureConfigSource("cca/getccareferencenumber")
@Slf4j
public class GetCCAReferenceNumberFeature extends AbstractFeature<CCAMasterVO> {

    private final CcaMasterMapper ccaMasterMapper;

    public CCAMasterData perform(CCAMasterVO ccaMasterVO) {
        return ccaMasterMapper.constructCCAMasterData(ccaMasterVO);
    }
}
