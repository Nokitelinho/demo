package com.ibsplc.neoicargo.tracking.component.feature.emailnotification;

import com.ibsplc.neoicargo.notif.NotificationWebAPI;
import com.ibsplc.neoicargo.notif.model.EmailRequestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailClientTest {

    @Mock
    private NotificationWebAPI notificationWebAPI;
    @InjectMocks
    private EmailClient client;


    @Test
    void shouldSendEmail() {
        var emailRequest = new EmailRequestData();
        client.sendEmail(emailRequest);

        verify(notificationWebAPI).sendEmail(emailRequest);
    }
}