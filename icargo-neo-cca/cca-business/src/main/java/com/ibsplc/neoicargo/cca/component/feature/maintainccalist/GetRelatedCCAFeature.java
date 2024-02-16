package com.ibsplc.neoicargo.cca.component.feature.maintainccalist;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.RelatedCCAData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class GetRelatedCCAFeature {

    private final CcaDao ccaDao;
    private final CcaMasterMapper ccaMasterMapper;

    public List<RelatedCCAData> perform(CcaDataFilter ccaDataFilter) {
        log.info("GetRelatedCCAFeature perform");
        final var masterVO = ccaDao.getRelatedCCA(ccaDataFilter);
        return ccaMasterMapper.constructRelatedCCAData(masterVO);
    }

}
