package com.ibsplc.neoicargo.cca.component.feature.updateattachments;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.modal.AttachmentsData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;

class UpdateCcaMasterAttachmentsTest {

    @Mock
    private CcaDao ccaDao;

    @InjectMocks
    private UpdateCcaMasterAttachments ccaMasterAttachments;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateCcaMasterAttachments() {
        // Given
        final var attachmentsData = new AttachmentsData();

        // When
        doNothing().when(ccaDao).updateCcaMasterAttachments(attachmentsData);

        // Then
        assertDoesNotThrow(() -> ccaMasterAttachments.perform(attachmentsData));
    }
}