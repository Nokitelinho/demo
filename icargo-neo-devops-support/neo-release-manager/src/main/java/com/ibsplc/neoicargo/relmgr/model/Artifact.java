/*
 * Artifact.java Created on 04/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jens
 */
@Data
@NoArgsConstructor
public class Artifact {

    private String artifactId;
    private String domain;
    private int port;
    private String healthEndpoint;
    private ServiceType serviceType;
    private ServiceTechnology serviceTechnology;
    private String contextPath;
    private String description;
    private boolean hostsPrivateApi;
    private boolean hostsPublicApi;
    private boolean hostsEnterpriseApi;
    private String projectName;
    private String projectRepositoryName;

}
