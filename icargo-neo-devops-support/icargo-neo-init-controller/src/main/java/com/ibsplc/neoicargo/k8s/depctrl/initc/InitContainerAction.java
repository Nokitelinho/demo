/*
 * InitContainerAction.java Created on 29/03/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.k8s.depctrl.initc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.k8s.depctrl.Action;
import com.ibsplc.neoicargo.k8s.depctrl.K8sHelper;
import com.ibsplc.neoicargo.k8s.depctrl.SpringUtils;
import com.ibsplc.neoicargo.k8s.depctrl.modal.ApplicationEnv;
import com.ibsplc.neoicargo.k8s.depctrl.modal.DeploymentUnit;
import com.ibsplc.neoicargo.k8s.depctrl.modal.K8sLabels;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

/**
 * @author jens
 */
@Service
@ConditionalOnProperty(name = "depctrl.action", havingValue = "init-container")
public class InitContainerAction implements Action, K8sLabels {

    static final Logger logger = LoggerFactory.getLogger(InitContainerAction.class);

    @Autowired
    private KubernetesClient client;

    @Autowired
    private ApplicationEnv applicationEnv;

    @Autowired
    private K8sHelper helper;

    private DeploymentUnit deploymentUnit;

    @PostConstruct
    protected void init() throws IOException {
        String deploymentUnitEnc = System.getenv("DEPCTRL_DEPLOYMENT_DEFN");
        if (deploymentUnitEnc == null)
            throw new IllegalStateException("DEPCTRL_DEPLOYMENT_DEFN environment variable is absent");
        byte[] deploymentUnitJson = Base64.getDecoder().decode(deploymentUnitEnc);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        this.deploymentUnit = mapper.readValue(deploymentUnitJson, DeploymentUnit.class);
    }

    @Override
    public boolean perform() {
        // check if config server deployment is ready
        logger.info("Checking config server is up ...");
        var configServerNamespace = StringUtils.isEmpty(applicationEnv.getConfigServerNamespace()) ? applicationEnv.getApplicationNamespace() : applicationEnv.getConfigServerNamespace();
        Map<String, String> configServerLabels = Map.of(KEY_APP, applicationEnv.getConfigServerArtifactId());
        boolean isConfigServerDs = helper.isDaemonSet(configServerNamespace, configServerLabels);
        logger.info("Config Server is daemonSet : {}", isConfigServerDs);
        boolean configServerUp = isConfigServerDs ? helper.isDaemonSetHealthy(configServerNamespace, configServerLabels) : helper.isDeploymentHealthy(configServerNamespace, configServerLabels);
        logger.info("ConfigServer ds status is {}", configServerUp);
        if (!configServerUp)
            return false;
        // check config server service is up
        boolean isConfigServerServiceUp = helper.isServiceAvailable(configServerNamespace, Map.of(KEY_APP, applicationEnv.getConfigServerArtifactId()));
        logger.info("ConfigServer service status is {}", isConfigServerServiceUp);
        if (!isConfigServerServiceUp)
            return false;
        // check infra services are up
        /*for(String tenant : this.applicationEnv.getHostedTenants().split(",")){
            tenant = tenant.toLowerCase();
            boolean infraServicesUp = verifyInfraServices(tenant);
            logger.info("Infra service status for tenant {} is {}", tenant, infraServicesUp);
            if(!infraServicesUp)
                return false;
        }*/
        // check the dependent service health
        if (this.applicationEnv.getInitDependencies() != null && !this.applicationEnv.getInitDependencies().isBlank()) {
            String[] initDependencies = this.applicationEnv.getInitDependencies().split(",");
            logger.info("Init Dependencies for service {} are {}", this.deploymentUnit.getArtifactId(), Arrays.toString(initDependencies));
            for (String dependentDeployment : initDependencies) {
                dependentDeployment = dependentDeployment.trim();
                // prevent self reference
                if (dependentDeployment.equals(deploymentUnit.getArtifactId()))
                    continue;
                boolean deploymentReady = this.helper.isReplicaSetHealthy(this.applicationEnv.getApplicationNamespace(), Map.of(KEY_APP, dependentDeployment));
                logger.info("Dependent deployment {} status {}", dependentDeployment, deploymentReady);
                if (!deploymentReady)
                    return false;
            }
        }
        if (SRVTYP_GW.equalsIgnoreCase(this.deploymentUnit.getServiceType())) {
            logger.info("Implicit dependencies for web_gw are all web_bff");
            boolean allBffsReady = this.helper.isReplicaSetHealthy(this.applicationEnv.getApplicationNamespace(), Map.of(KEY_SERVICE_TYPE, SRVTYP_BFF));
            logger.info("Aggregated Bff deployment status {}", allBffsReady);
            if (!allBffsReady)
                return false;
        }
        if (SRVTYP_FE.equalsIgnoreCase(this.deploymentUnit.getServiceType())) {
            logger.info("Implicit dependencies for web_fe are all web_gw");
            boolean allGwReady = this.helper.isReplicaSetHealthy(this.applicationEnv.getApplicationNamespace(), Map.of(KEY_SERVICE_TYPE, SRVTYP_GW));
            logger.info("Aggregated Gw deployment status {}", allGwReady);
            if (!allGwReady)
                return false;
        }
        return true;
    }

    protected boolean verifyInfraServices(String tenant) {
        ConfigurableApplicationContext context = SpringUtils.resolveApplicationContext(tenant, this.deploymentUnit.getArtifactId(),
                this.applicationEnv.getApplicationProfile(), this.applicationEnv.getConfigServerUrl());
        StringBuilder sbul = new StringBuilder();
        Arrays.stream(context.getBeanFactory().getBeanDefinitionNames()).forEach(bean -> {
            sbul.append(bean).append(" - ").append(context.getBean(bean)).append("\n");
        });
        logger.info("All Beans \n{}", sbul);
        Map<String, HealthIndicator> healthIndicatorMap = context.getBeansOfType(HealthIndicator.class);
        boolean answer = true;
        for (Map.Entry<String, HealthIndicator> e : healthIndicatorMap.entrySet()) {
            Health health = e.getValue().getHealth(true);
            logger.info("Infra Service for tenant {} of type {} health is {} , details {}", tenant, e.getKey(), health.getStatus(), health.getDetails());
            if (health.getStatus() != Status.UP)
                answer = false;
        }
        context.stop();
        return answer;
    }

}
