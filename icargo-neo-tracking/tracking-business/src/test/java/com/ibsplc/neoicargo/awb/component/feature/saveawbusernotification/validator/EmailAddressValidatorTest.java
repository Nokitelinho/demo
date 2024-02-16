package com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification.validator;

import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.tracking.exception.TrackingBusinessException;
import org.apache.commons.collections.ListUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.tracking.exception.TrackingErrors.USR_AWB_NTF_EMAIL_NOT_VALID;
import static com.ibsplc.neoicargo.tracking.exception.TrackingErrors.USR_AWB_NTF_MILESTONE_EMAIL_NOT_PROVIDED;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.RCS;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailAddressValidatorTest {

    private EmailAddressValidator validator = new EmailAddressValidator();

    private final static List<String> INVALID_EMAILS = List.of("abcd@", "fdshfdghdfh.com", "@asdf@gmail.com");

    @Test
    void shouldValidateWhenThereIsAtLeastOneValidEmails() throws BusinessException {
        var oneValidEmails = new AwbUserNotificationVO();
        oneValidEmails.setNotificationMilestones(List.of(RCS.getLabel()));
        oneValidEmails.setEmails(ListUtils.union(List.of("abc@domain.com"), INVALID_EMAILS));

        validator.validate(oneValidEmails);

        assertEquals(List.of("abc@domain.com"), oneValidEmails.getEmails());
    }

    @Test
    void shouldThrowExceptionWhenThereIsNoValidEmails() {
        var noValidEmails = new AwbUserNotificationVO();
        noValidEmails.setNotificationMilestones(List.of(RCS.getLabel()));
        noValidEmails.setEmails(INVALID_EMAILS);

        var exception = assertThrows(TrackingBusinessException.class, () -> validator.validate(noValidEmails));

        assertEquals(USR_AWB_NTF_EMAIL_NOT_VALID.getErrorMessage(), exception.getMessage());
        assertEquals(USR_AWB_NTF_EMAIL_NOT_VALID.getErrorCode(), exception.getErrorCode());
    }

    @ParameterizedTest
    @MethodSource("noEmailOrNoMilestones")
    void shouldThrowExceptionWhenNoEmailsOrNo(AwbUserNotificationVO awbUserNotificationVO) {

        var exception = assertThrows(TrackingBusinessException.class, () -> validator.validate(awbUserNotificationVO));

        assertEquals(USR_AWB_NTF_MILESTONE_EMAIL_NOT_PROVIDED.getErrorMessage(), exception.getMessage());
        assertEquals(USR_AWB_NTF_MILESTONE_EMAIL_NOT_PROVIDED.getErrorCode(), exception.getErrorCode());
    }

    private static Stream<AwbUserNotificationVO> noEmailOrNoMilestones() {
        var noEmails = new AwbUserNotificationVO();
        noEmails.setNotificationMilestones(List.of(RCS.getLabel()));
        noEmails.setEmails(List.of());
        var noMilestones = new AwbUserNotificationVO();
        noMilestones.setEmails(List.of("my@gmail.com"));
        noMilestones.setNotificationMilestones(List.of());

        return Stream.of(
                noEmails, noMilestones);
    }
}