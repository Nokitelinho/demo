/*
 * DeploymentUnit.java Created on 05/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

/**
 * @author jens
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeploymentUnit {

    public static final String DEFAULT_DEP_GROUP = "default";

    private String artifactId;
    private String description;
    private String serviceName;
    private int port;
    private String image;
    private String contextPath;
    private ServiceTechnology processType;
    private ServiceType serviceType;
    private String healthEndpoint;
    private ZonedDateTime releaseTime;
    private boolean hostsPrivateApi;
    private boolean hostsPublicApi;
    private boolean hostsEnterpriseApi;
    private String branch;
    private String artifactVersion;
    private String deploymentGroup = DEFAULT_DEP_GROUP; // default value for backward compatibility


}
