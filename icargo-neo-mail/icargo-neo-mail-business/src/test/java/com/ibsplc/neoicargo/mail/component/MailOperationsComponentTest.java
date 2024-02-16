package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.mail.component.feature.findconsignmentscreeningdetails.FindConsignmentScreeningDetailsFeature;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class MailOperationsComponentTest {
    @InjectMocks private MailOperationsComponent mailOperationsComponent;

    @Mock private FindConsignmentScreeningDetailsFeature findConsignmentScreeningDetailsFeature;

    @Spy
    private final MailOperationsMapper mailOperationsMapper = Mappers.getMapper(MailOperationsMapper.class);
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindConsignmentScreeningDetails() throws MailOperationsBusinessException {
        // When
        doReturn(null)
                .when(findConsignmentScreeningDetailsFeature)
                .perform(anyString(), anyString(), anyString());

        // Then
        assertThat(mailOperationsComponent.findConsignmentScreeningDetails(anyString(), anyString(), anyString())).isNull();
    }


}