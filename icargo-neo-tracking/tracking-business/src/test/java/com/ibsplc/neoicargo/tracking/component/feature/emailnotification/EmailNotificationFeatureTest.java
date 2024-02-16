package com.ibsplc.neoicargo.tracking.component.feature.emailnotification;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.notif.model.EmailRequestData;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import com.samskivert.mustache.Mustache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailNotificationFeatureTest {

    @Mock
    private EmailClient emailClient;
    @Mock
    private TrackingMapper mapper;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Mustache.Compiler mustacheCompiler;

    @InjectMocks
    private EmailNotificationFeature feature;

    @Test
    void shouldPerformEmailSending() throws BusinessException {
        var emailVO = MockDataHelper.constructEmailMilestoneNotificationVO();
        var body = "body";
        var subject = "AWB Tracking 123-45678910 Departure Notification";
        var emailRequest = new EmailRequestData();

        when(mustacheCompiler.loadTemplate("milestone-notification-email").execute(emailVO)).thenReturn(body);
        when(mapper.constructEmailRequestData(null, emailVO.getNotifications().get(0).getEmails(), "AWB Tracking 123-45678910 Departure Notification", body)).thenReturn(emailRequest);

        assertNull(feature.perform(emailVO));
        verify(emailClient).sendEmail(emailRequest);
    }


}