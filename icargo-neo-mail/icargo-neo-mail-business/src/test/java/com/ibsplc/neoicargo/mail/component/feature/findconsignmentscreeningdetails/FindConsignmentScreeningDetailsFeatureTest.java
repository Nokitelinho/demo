package com.ibsplc.neoicargo.mail.component.feature.findconsignmentscreeningdetails;

import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
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
class FindConsignmentScreeningDetailsFeatureTest {
    @InjectMocks private FindConsignmentScreeningDetailsFeature findConsignmentScreeningDetailsFeature;

    @Mock private MailController mailController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnFindConsignmentScreeningDetails() throws FinderException, PersistenceException, MailOperationsBusinessException {
        // When
        doReturn(null).when(mailController).findConsignmentScreeningDetails(anyString(), anyString(), anyString());

        // Then
        assertThat(findConsignmentScreeningDetailsFeature.perform(anyString(), anyString(), anyString())).isNull();

    }
}