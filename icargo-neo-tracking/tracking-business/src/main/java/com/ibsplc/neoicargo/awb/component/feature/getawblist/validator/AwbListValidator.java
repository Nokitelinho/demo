package com.ibsplc.neoicargo.awb.component.feature.getawblist.validator;

import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.ibsplc.neoicargo.tracking.model.TrackingValidationModel.AWB_FORMAT;


@Slf4j
@Transactional(readOnly = true)
@Component
public class AwbListValidator extends Validator<List<AwbRequestVO>> {

    @Override
    public void validate(List<AwbRequestVO> awbs) {
        log.info("Entering AWB request validator");
        awbs.removeIf(Objects::isNull);
        awbs.removeIf(awb -> StringUtils.isEmpty(awb.getAwb()));
        awbs.removeIf(awb -> !awb.getAwb().matches(AWB_FORMAT));
    }
}
