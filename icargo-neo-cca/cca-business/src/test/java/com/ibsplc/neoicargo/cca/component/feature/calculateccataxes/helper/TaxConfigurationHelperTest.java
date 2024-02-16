package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper;

import com.ibsplc.icargo.ebl.nbridge.types.tax.TaxConfigurationData;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getTaxFilterVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

class TaxConfigurationHelperTest {

    @Mock
    private ServiceProxy<AbstractVO> serviceProxy;

    @InjectMocks
    private TaxConfigurationHelper taxConfigurationHelper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(taxConfigurationHelper, "eblUrl", "http://localhost:9591/ebl-nbridge/v1/");
    }

    @Test
    void populateConfigurationDetailsShouldEnrichTaxConfiguration() {
        // Given
        var taxFilterVO = getTaxFilterVO();
        var taxConfigurationData = new TaxConfigurationData();
        taxConfigurationData.setConfigurationCode("ST");
        var taxConfigurations = new TaxConfigurationData[]{taxConfigurationData};

        // When
        doReturn(taxConfigurations).when(serviceProxy)
                .dispatch(anyString(), eq(HttpMethod.POST), isA(AbstractVO.class), eq(TaxConfigurationData[].class));

        // Then
        assertDoesNotThrow(() -> taxConfigurationHelper.populateConfigurationDetails(taxFilterVO));
        var actual = taxFilterVO.getTaxConfigurationDetails();
        assertNotNull(actual);
    }

    @Test
    void populateConfigurationDetailsShouldReturnEmptyMapWhenPricingEnrichmentFails() {
        // Given
        var taxFilterVO = getTaxFilterVO();
        var exception = new SystemException("EBL_TRF_001", "No Tax Configurations Found!");

        // When
        doThrow(exception).when(serviceProxy).dispatch(isA(String.class), eq(HttpMethod.POST), isA(AbstractVO.class),
                eq(TaxConfigurationData[].class));
        taxConfigurationHelper.populateConfigurationDetails(taxFilterVO);

        // Then
        var actual = taxFilterVO.getTaxConfigurationDetails();
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

}