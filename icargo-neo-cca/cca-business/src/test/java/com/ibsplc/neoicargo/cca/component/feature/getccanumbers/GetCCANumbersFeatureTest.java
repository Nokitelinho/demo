package com.ibsplc.neoicargo.cca.component.feature.getccanumbers;

import com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils;
import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class GetCCANumbersFeatureTest {

    public static final String COMPANY_CODE = "AV";

    @Mock
    private CcaDao ccaDao;

    @Mock
    private CcaMasterMapper ccaMasterMapper;

    @InjectMocks
    private GetCCANumbersFeature getCCANumbersFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCcaNumbers() {
        // Given
        var ccaRefNumber = "CCA000001";

        final var ccaNumbersFilter = new CcaSelectFilter();
        ccaNumbersFilter.setLoadFromFilter(true);
        ccaNumbersFilter.setFilter(List.of(ccaRefNumber));

        final var ccaIdAndNumbers = List.of(
                Pair.of(1L, "CCA001")
        );

        var ccaNumbersDataVOs = MockModelsGeneratorUtils.getCcaNumbersDataVO(ccaIdAndNumbers);
        var ccaNumbersData = MockModelsGeneratorUtils.getCcaNumbersData(ccaIdAndNumbers);

        // When
        doReturn(ccaNumbersDataVOs).when(ccaDao).getCCANumbers(any(CcaSelectFilter.class), any(String.class));
        doReturn(ccaNumbersData).when(ccaMasterMapper).constructCcaNumbersData(ccaNumbersDataVOs);

        // Then
        assertNotNull(getCCANumbersFeature.perform(ccaNumbersFilter, COMPANY_CODE));
    }

}
