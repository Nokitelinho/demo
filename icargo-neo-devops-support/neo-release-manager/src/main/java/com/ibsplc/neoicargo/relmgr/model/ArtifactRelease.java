/*
 * ArtifactRelease.java Created on 04/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * @author jens
 */
@Data
@NoArgsConstructor
@ToString
public class ArtifactRelease {

    @NotNull
    private String artifactId;
    @NotNull
    private String artifactVersion;
    private String branch;
    private String description;
    private String committerEmail;
    private String releaseStatus;
    private ZonedDateTime releaseTime;

}
