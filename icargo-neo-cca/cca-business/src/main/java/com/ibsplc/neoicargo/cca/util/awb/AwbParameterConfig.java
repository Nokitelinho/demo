package com.ibsplc.neoicargo.cca.util.awb;

import com.ibsplc.neoicargo.cca.util.CcaUtil;
import com.ibsplc.neoicargo.cca.util.awb.Parameters.Parameter;
import com.ibsplc.neoicargo.framework.core.context.tenant.config.HierarchicalResourceLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AwbParameterConfig {

    private static final String AWB_PARAMETERS_PATH = "awbparameters/";
    private static final String PARAMETER_SETUP_FILE = "parameter-setup.yml";

    private final HierarchicalResourceLoader hierarchicalResourceLoader;
    private Parameters parameters;
    private Map<String, Parameter> awbParameterMap;

    @PostConstruct
    public void setMapOnInit() throws IOException {
        loadProperties();
        if (parameters != null) {
            this.awbParameterMap = this.parameters.getAwbParameters().stream()
                    .collect(Collectors.toMap(Parameter::getParameterCode, parameter -> parameter));
        } else {
            this.awbParameterMap = Map.of();
        }
    }

    private void loadProperties() throws IOException {
        var resource = getParameterSetupResource();

        if (resource != null) {
            this.parameters = new Yaml(new Constructor(Parameters.class)).load(resource.getInputStream());
        }
    }

    @Nullable
    private Resource getParameterSetupResource() {
        var resources = hierarchicalResourceLoader.discoverDeploymentResources(
                AWB_PARAMETERS_PATH, List.of(PARAMETER_SETUP_FILE)
        );
        return CcaUtil.isNullOrEmpty(resources) ? null : resources.iterator().next();
    }

    public Parameter getAwbParameter(String parameterCode) {
        return this.awbParameterMap.get(parameterCode);
    }
}