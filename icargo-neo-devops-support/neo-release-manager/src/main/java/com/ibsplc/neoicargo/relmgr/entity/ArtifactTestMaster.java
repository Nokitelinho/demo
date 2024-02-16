/*
 * ArtifactTestMaster.java Created on 04/10/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.entity;

import com.ibsplc.neoicargo.relmgr.model.ArtifactTest;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author jens
 */
@Entity
@Table(name = "ARTTSTMST")
@NoArgsConstructor
@Getter
@Setter
public class ArtifactTestMaster {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "tenantId", column = @Column(name = "TNTIDR")),
            @AttributeOverride(name = "artifactId", column = @Column(name = "ARTIDR")),
            @AttributeOverride(name = "testType", column = @Column(name = "TSTTYP")),
    })
    private ArtifactTestMaster.PK pk;

    @Column(name = "GITRPO")
    private String gitRepo;

    @Column(name = "GITBRH")
    private String gitBranch;

    @Column(name = "EXEJAR")
    private String executableJar;

    /**
     * Default Constructor
     * @param artifactTest
     */
    public ArtifactTestMaster(ArtifactTest artifactTest){
        this.pk = new PK();
        this.pk.artifactId = artifactTest.getArtifactId();
        this.pk.tenantId = artifactTest.getTenantId();
        this.pk.testType = artifactTest.getTestType();
        this.gitBranch = artifactTest.getGitBranch();
        this.gitRepo = artifactTest.getGitRepo();
        this.executableJar = artifactTest.getExecutableJar();
    }

    @Embeddable
    @NoArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class PK implements Serializable {

        @NonNull
        private String tenantId;
        @NonNull
        private String artifactId;
        @NonNull
        private String testType;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PK)) return false;
            PK pk = (PK) o;
            return tenantId.equals(pk.tenantId) && artifactId.equals(pk.artifactId) && testType.equals(pk.testType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tenantId, artifactId, testType);
        }
    }
}
