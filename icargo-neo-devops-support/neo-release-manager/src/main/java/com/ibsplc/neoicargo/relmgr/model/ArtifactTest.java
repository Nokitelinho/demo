/*
 * ArtifactTest.java Created on 04/10/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.model;

import com.ibsplc.neoicargo.relmgr.entity.ArtifactTestMaster;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author jens
 */
@Getter
@Setter
@NoArgsConstructor
public class ArtifactTest {

    private String tenantId;
    private String artifactId;
    private String testType;
    private String gitRepo;
    private String gitBranch;
    private String executableJar;

    public ArtifactTest(ArtifactTestMaster master){
       this.tenantId = master.getPk().getTenantId();
       this.artifactId = master.getPk().getArtifactId();
       this.testType = master.getPk().getTestType();
       this.gitRepo = master.getGitRepo();
       this.gitBranch = master.getGitBranch();
       this.executableJar = master.getExecutableJar();
    }

}
