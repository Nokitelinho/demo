package com.ibsplc.neoicargo.mail.service;

import com.ibsplc.neoicargo.mail.component.MailOperationsComponent;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.model.ConsignmentDocumentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class MailOperationsServiceTest {
    @InjectMocks private MailOperationsService mailOperationsService;
    @Mock
    private MailOperationsComponent mailOperationsComponent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindfindConsignmentScreeningDetails() throws MailOperationsBusinessException {
        // Given
        var model = new ConsignmentDocumentModel();

        // When
        doReturn(model)
                .when(mailOperationsComponent)
                .findConsignmentScreeningDetails(anyString(), anyString(), anyString());

        // Then
        assertThat(model)
                .isEqualTo(mailOperationsService
                        .findConsignmentScreeningDetails(anyString(), anyString(), anyString()));
    }

}