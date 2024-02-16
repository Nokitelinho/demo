/*
 * ApplicationEnv.java Created on 30/03/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.k8s.depctrl.modal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author jens
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "depctrl")
public class ApplicationEnv {

    private String configServerUrl;
    private String configServerArtifactId;
    private String applicationProfile;
    private String applicationNamespace;
    private String configServerNamespace;

    private String hostedTenants;
    private String initDependencies;

    @PostConstruct
    public void onInit() throws IntrospectionException {
        StringBuilder sbul = new StringBuilder("-------------------\n");
        BeanInfo info = Introspector.getBeanInfo(ApplicationEnv.class);
        Arrays.stream(info.getPropertyDescriptors()).filter(p -> !"class".equals(p.getName())).forEach(p -> {
            try {
                Object val = p.getReadMethod().invoke(ApplicationEnv.this);
                sbul.append(p.getName()).append(" - ").append(val).append("\n");
            } catch (IllegalAccessException | InvocationTargetException e) {
                // ignored
            }
        });
        sbul.append("----------------");
        System.out.println(sbul);
    }

}
