/*
 * DeploymentBom.java Created on 05/10/20
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

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author jens
 */
@Getter
@Setter
@NoArgsConstructor
public class DeploymentBom {

    private Map<String, DeploymentUnit> deployments = new TreeMap<>();
    private String tenantId;
    private String applicationId;
    private ZonedDateTime versionTimestamp;
    private String buildNumber;
    private Set<String> deploymentGroups;

}
