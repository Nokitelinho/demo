package com.ibsplc.neoicargo.mail.component.feature.savesecuritydetails;

import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.component.ConsignmentScreeningDetails;
import com.ibsplc.neoicargo.mail.component.feature.savesecuritydetails.invoker.EditScreeningDetailsInvoker;
import com.ibsplc.neoicargo.mail.component.feature.savesecuritydetails.persistors.SecurityDetailPersistor;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component("mail.operations.saveSecurityDetailsFeature")
@FeatureConfigSource("feature/savesecuritydetails")
@RequiredArgsConstructor
public class SaveSecurityDetailsFeature extends AbstractFeature<ConsignmentScreeningVO> {


    protected Void perform(ConsignmentScreeningVO consignmentScreeningVO) {
        log.debug("Invoke SaveSecurityDetailsFeature");
        new SecurityDetailPersistor().persist(consignmentScreeningVO);
        return null;
    }



}
