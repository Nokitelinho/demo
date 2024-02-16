package com.ibsplc.neoicargo.mail.component.feature.savesecuritydetails.invoker;

import com.ibsplc.neoicargo.mail.component.feature.editscreeningdetails.EditScreeningDetailsFeature;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class EditScreeningDetailsInvoker {
    private final EditScreeningDetailsFeature editScreeningDetailsFeature;

    public void invoke(Collection<ConsignmentScreeningVO> consignmentScreeningVos) {
        editScreeningDetailsFeature.perform(consignmentScreeningVos);
    }
}
