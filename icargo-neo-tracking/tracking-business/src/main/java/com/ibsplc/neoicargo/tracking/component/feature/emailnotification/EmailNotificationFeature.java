package com.ibsplc.neoicargo.tracking.component.feature.emailnotification;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import com.ibsplc.neoicargo.tracking.vo.EmailMilestoneNotificationVO;
import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component("emailNotificationFeature")
@FeatureConfigSource("feature/shipment/emailnotification")
public class EmailNotificationFeature extends AbstractFeature<EmailMilestoneNotificationVO> {

    private final EmailClient emailClient;
    private final TrackingMapper mapper;
    private final Mustache.Compiler mustacheCompiler;

    @Value("${spring.mail.from}")
    private String fromAddress;
    private static final String BODY_TEMPLATE = "milestone-notification-email";
    private static final String SUBJECT_TEMPLATE = "AWB Tracking %s %s Notification";


    @Override
    protected Void perform(EmailMilestoneNotificationVO emailVO) throws BusinessException {
        log.info("Sending email notification for a milestone: {}", emailVO.getMilestoneCode().getDescription());
        var body = mustacheCompiler.loadTemplate(BODY_TEMPLATE).execute(emailVO);
        var subject = String.format(SUBJECT_TEMPLATE, emailVO.getShipmentKey(), emailVO.getMilestoneCode().getDescription());
        emailVO.getNotifications().stream()
                .map(notification -> mapper.constructEmailRequestData(fromAddress, notification.getEmails(), subject, body))
                .forEach(requestData -> emailClient.sendEmail(requestData));

        return null;
    }
}
