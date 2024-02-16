package com.ibsplc.neoicargo.cca.util.awb;

import com.ibsplc.neoicargo.framework.core.context.tenant.config.HierarchicalResourceLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class AwbParameterConfigTest {
    @InjectMocks
    private AwbParameterConfig parameterConfig;

    @Mock
    private HierarchicalResourceLoader hierarchicalResourceLoader;

    private static final String PATH_PARAM = "config/awbparameters/";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAwbParameter() throws IOException {
        // Given
        var fileName = "parameter-setup.yml";
        var classPathResource = new ClassPathResource(PATH_PARAM + fileName);

        // When
        doReturn(List.of(classPathResource)).
                when(hierarchicalResourceLoader).discoverDeploymentResources(anyString(), anyList());
        parameterConfig.setMapOnInit();
        var parameter = parameterConfig.getAwbParameter("EXGRATBAS");

        //Then
        assertNotNull(parameter);
    }

    @Test
    void setMapOnInitShouldNotSetParameter() throws IOException {

        // When
        doReturn(null).
                when(hierarchicalResourceLoader).discoverDeploymentResources(anyString(), anyList());
        parameterConfig.setMapOnInit();
        var parameter = parameterConfig.getAwbParameter("EXGRATBAS");

        //Then
        Assertions.assertNull(parameter);

    }

}
