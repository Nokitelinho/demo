package com.ibsplc.neoicargo.mail.component.feature.editscreeningdetails;

import com.ibsplc.neoicargo.mail.component.feature.editscreeningdetails.persistor.EditScreeningDetailsPersistor;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import java.util.Collection;

@Slf4j
@Component("mail.operations.editScreeningdetails")
@FeatureConfigSource("feature/editscreeningdetails")
@RequiredArgsConstructor
public class EditScreeningDetailsFeature extends AbstractFeature<ConsignmentScreeningVO>  {


    public Void perform(Collection<ConsignmentScreeningVO> consignmentScreeningVOs) {
        log.debug("Invoke EditScreeningDetailsFeature");
        new EditScreeningDetailsPersistor().editscreeningDetails(consignmentScreeningVOs);
        return null;
    }
    protected Void perform(ConsignmentScreeningVO consignmentScreeningVO) {
        log.debug("Invoke SaveSecurityDetailsFeature");
        new EditScreeningDetailsPersistor().persist(consignmentScreeningVO);
        return null;
    }
}
