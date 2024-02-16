/*
 * K8sHelper.java Created on 12/04/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.k8s.depctrl;

import com.ibsplc.neoicargo.k8s.depctrl.modal.K8sLabels;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.*;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author jens
 */
@Component
public class K8sHelper {

    static final Logger logger = LoggerFactory.getLogger(K8sHelper.class);

    private final KubernetesClient kubernetesClient;
    private final int waitTimeSeconds;

    @Autowired
    public K8sHelper(KubernetesClient kubernetesClient, @Value("${depctrl.waitTime:10}") int waitTimeSeconds) {
        this.kubernetesClient = kubernetesClient;
        this.waitTimeSeconds = waitTimeSeconds;
    }

    /**
     * Waits for the service to be available
     *
     * @param namespace
     * @param labels
     * @return true if the service is pre
     * @throws InterruptedException
     */
    public boolean isServiceAvailable(String namespace, Map<String, String> labels) {
        ServiceList services = kubernetesClient.services().inNamespace(namespace).withLabels(labels).list();
        if (services == null || services.getItems() == null || services.getItems().isEmpty())
            return false;
        for (Service service : services.getItems()) {
            logger.debug("Service : name {} , spec {}", service.getMetadata().getName(), service.getSpec());
        }
        return true;
    }

    /**
     * @param namespace
     * @param labels
     * @return - deployment status
     */
    public boolean isDeploymentHealthy(String namespace, Map<String, String> labels) {
        DeploymentList deploymentList = kubernetesClient.apps().deployments().inNamespace(namespace).withLabels(labels).list();
        if (deploymentList == null || deploymentList.getItems() == null || deploymentList.getItems().isEmpty())
            return false;
        for (Deployment deployment : deploymentList.getItems()) {
            logger.debug("Deployment : {} Status {}", deployment.getMetadata().getName(), deployment.getStatus());
            // unavailable replicas indicate a restart or an upgrade in progress hence wait for the new version to come up
            if (deployment.getStatus().getUnavailableReplicas() != null && deployment.getStatus().getUnavailableReplicas() > 0)
                return false;
            // check if the service is updated with the latest replica information - by triggering a health check through the service clusterip
            Map<String, String> serviceSelectorMap = new HashMap<>(2);
            serviceSelectorMap.put(K8sLabels.KEY_APP, deployment.getSpec().getTemplate().getMetadata().getLabels().get(K8sLabels.KEY_APP));
            serviceSelectorMap.put(K8sLabels.KEY_TENANT, deployment.getSpec().getTemplate().getMetadata().getLabels().get(K8sLabels.KEY_TENANT));
            for (Container container : deployment.getSpec().getTemplate().getSpec().getContainers()) {
                if (container.getReadinessProbe() != null && container.getReadinessProbe().getHttpGet() != null) {
                    serviceSelectorMap.put(K8sLabels.KEY_APP, container.getName());
                    boolean serviceReady = isServiceHealthReady(namespace, serviceSelectorMap, container.getReadinessProbe());
                    logger.debug("Service Status for deployment {}, service {} is {}", deployment.getMetadata().getName(), serviceSelectorMap, serviceReady);
                    if (!serviceReady)
                        return false;
                }
            }
        }
        return true;
    }

    public boolean isReplicaSetHealthy(String namespace, Map<String, String> labels) {
        ReplicaSetList replicaSets = kubernetesClient.apps().replicaSets().inNamespace(namespace).withLabels(labels).list();
        if (replicaSets == null || replicaSets.getItems() == null || replicaSets.getItems().isEmpty())
            return false;
        boolean activeReplicasPresent = false;
        for (ReplicaSet rs : replicaSets.getItems()) {
            ReplicaSetStatus status = rs.getStatus();
            logger.debug("ReplicaSet : {} Status {}", rs.getMetadata().getName(), status);
            if (status == null) {
                logger.debug("ReplicaSet status not available");
                return false;
            }
            // scaled down replicas
            if (status.getReplicas() == null || status.getReplicas().intValue() == 0)
                continue;
            activeReplicasPresent = true;
            if (status.getReadyReplicas() == null || status.getReplicas().intValue() != status.getReadyReplicas().intValue()) {
                logger.debug("ReplicaSet not fully scaled.");
                return false;
            }
        }
        return activeReplicasPresent;
    }

    private boolean isServiceHealthReady(String namespace, Map<String, String> labels, Probe probe) {
        ServiceList services = kubernetesClient.services().inNamespace(namespace).withLabels(labels).list();
        // if services are not found
        if (services == null || services.getItems() == null || services.getItems().isEmpty()) {
            logger.debug("No k8s service found in namespace {} for labels {}", namespace, labels);
            return true;
        }
        for (Service service : services.getItems()) {
            // only check for type ClusterIp
            if (service.getSpec().getClusterIP() == null)
                continue;
            Optional<ServicePort> servicePortOptional = service.getSpec().getPorts().stream().filter(p -> p.getName().equals(probe.getHttpGet().getPort().getStrVal())).findFirst();
            boolean healthUp = servicePortOptional.map(sp -> HttpUtils.healthCheck(service.getSpec().getClusterIP(), sp.getPort(), probe.getHttpGet().getPath())).orElse(Boolean.TRUE);
            servicePortOptional.ifPresent(sp -> logger.debug("Service Health http://{}:{}{} is {}", service.getMetadata().getName(), sp.getPort(), probe.getHttpGet().getPath(), healthUp));
            if (!healthUp)
                return healthUp;
        }
        return true;
    }

    public boolean isDaemonSet(String namespace, Map<String, String> labels) {
        DaemonSetList dsl = kubernetesClient.apps().daemonSets().inNamespace(namespace).withLabels(labels).list();
        if (dsl == null || dsl.getItems() == null || dsl.getItems().isEmpty())
            return false;
        return true;
    }

    /**
     * Check if daemonset is ready
     *
     * @param namespace
     * @param labels
     * @return
     * @throws InterruptedException
     */
    public boolean isDaemonSetHealthy(String namespace, Map<String, String> labels) {
        DaemonSetList dsl = kubernetesClient.apps().daemonSets().inNamespace(namespace).withLabels(labels).list();
        if (dsl == null || dsl.getItems() == null || dsl.getItems().isEmpty())
            return false;
        for (DaemonSet ds : dsl.getItems()) {
            logger.debug("DaemonSet : {} Status : {}", ds.getMetadata().getName(), ds.getStatus());
            // unavailable replicas indicate a restart or an upgrade in progress hence wait for the new version to come up
            if (ds.getStatus().getNumberUnavailable() != null && ds.getStatus().getNumberUnavailable() > 0)
                return false;
        }
        return true;
    }

}
