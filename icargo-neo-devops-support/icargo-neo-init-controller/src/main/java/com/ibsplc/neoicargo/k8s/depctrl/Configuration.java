/*
 * Configuration.java Created on 29/03/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.k8s.depctrl;

import com.ibsplc.neoicargo.k8s.depctrl.modal.ApplicationEnv;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author jens
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(value = {ApplicationEnv.class})
public class Configuration {

    @Bean
    public KubernetesClient kubernetesClient(){
        return new DefaultKubernetesClient();
    }

}
