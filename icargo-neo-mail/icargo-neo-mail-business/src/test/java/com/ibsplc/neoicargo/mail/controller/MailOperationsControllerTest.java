package com.ibsplc.neoicargo.mail.controller;

import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.model.ConsignmentDocumentModel;
import com.ibsplc.neoicargo.mail.service.MailOperationsService;
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
class MailOperationsControllerTest {

    @InjectMocks private MailOperationsController mailOperationsController;
    @Mock private MailOperationsService mailOperationsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shuldFindConsignmentScreeningDetails() throws MailOperationsBusinessException {
        // Given
        var model = new ConsignmentDocumentModel();

        // When
        doReturn(model).when(mailOperationsService)
                .findConsignmentScreeningDetails(anyString(), anyString(), anyString());

        // Then
        assertThat(model)
                .isEqualTo(mailOperationsController
                        .findConsignmentScreeningDetails(anyString(), anyString(), anyString()));
    }
}