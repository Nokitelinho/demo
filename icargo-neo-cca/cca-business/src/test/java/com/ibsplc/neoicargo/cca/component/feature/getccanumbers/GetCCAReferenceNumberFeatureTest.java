package com.ibsplc.neoicargo.cca.component.feature.getccanumbers;

import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaMasterData;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class GetCCAReferenceNumberFeatureTest {

    @Mock
    private CcaMasterMapper ccaMasterMapper;

    @InjectMocks
    private GetCCAReferenceNumberFeature getCCAReferenceNumberFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCcaReferanceNumber() {

        final var ccaMasterVO = getCCAMasterVO("12346548", "CCA000001", LocalDate.now());
        final var ccaMasterData=getCcaMasterData();
        // When
        doReturn(ccaMasterData).when(ccaMasterMapper).constructCCAMasterData(any(CCAMasterVO.class));

        // Then
        assertNotNull(getCCAReferenceNumberFeature.perform(ccaMasterVO));
    }

}
