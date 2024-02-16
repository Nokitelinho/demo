package com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification.validator;

import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.tracking.exception.TrackingBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.tracking.exception.TrackingErrors.USR_AWB_NTF_EMAIL_NOT_VALID;
import static com.ibsplc.neoicargo.tracking.exception.TrackingErrors.USR_AWB_NTF_MILESTONE_EMAIL_NOT_PROVIDED;

@Component("emailAddressValidator")
@Slf4j
public class EmailAddressValidator extends Validator<AwbUserNotificationVO> {

    @Override
    public void validate(AwbUserNotificationVO awbUserNotificationVO) throws TrackingBusinessException {
        if (awbUserNotificationVO.getNotificationMilestones().isEmpty()
                || awbUserNotificationVO.getEmails().isEmpty()) {
            throw new TrackingBusinessException(USR_AWB_NTF_MILESTONE_EMAIL_NOT_PROVIDED);
        }

        var validEmails = awbUserNotificationVO.getEmails()
                .stream()
                .filter(EmailValidator.getInstance()::isValid)
                .distinct()
                .collect(Collectors.toList());

        if (validEmails.isEmpty()) {
            throw new TrackingBusinessException(USR_AWB_NTF_EMAIL_NOT_VALID);
        }
        awbUserNotificationVO.setEmails(validEmails);
    }

}

