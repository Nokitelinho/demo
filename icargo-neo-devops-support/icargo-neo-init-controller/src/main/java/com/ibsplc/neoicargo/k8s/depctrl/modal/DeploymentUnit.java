/*
 * DeploymentUnit.java Created on 05/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.k8s.depctrl.modal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jens
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeploymentUnit {

    private String artifactId;
    private String description;
    private String serviceName;
    private int port;
    private String image;
    private String contextPath;
    private String processType;
    private String serviceType;
    private String healthEndpoint;
    private String releaseTime;
    private boolean hostsPrivateApi;
    private boolean hostsPublicApi;
    private boolean hostsEnterpriseApi;
    private String branch;
    private String artifactVersion;

}
