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
public class ActiveTask {

    private String artifactId;
    private String jira;}
