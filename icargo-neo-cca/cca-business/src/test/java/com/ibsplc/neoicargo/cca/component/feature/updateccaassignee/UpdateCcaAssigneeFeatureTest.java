package com.ibsplc.neoicargo.cca.component.feature.updateccaassignee;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneeData;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class UpdateCcaAssigneeFeatureTest {

    @Mock
    private CcaDao ccaDao;

    @Mock
    private CcaMasterMapper ccaMasterMapper;

    @InjectMocks
    private UpdateCcaAssigneeFeature updateCcaAssigneeFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateCcaMaster() throws CcaBusinessException {
        // Given

        // When
        doNothing().when(ccaDao).updateCcaMasterAssignee(any(CcaAssigneeData.class), anyString());
        doReturn(new CcaValidationData()).when(ccaMasterMapper)
                .constructCcaValidationData(any(CcaAssigneeData.class), anyString());

        // Then
        assertNotNull(updateCcaAssigneeFeature.perform(new CcaAssigneeData(), "AV"));
    }
}
