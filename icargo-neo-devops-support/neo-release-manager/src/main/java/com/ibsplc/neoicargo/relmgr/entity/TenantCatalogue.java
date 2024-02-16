/*
 * TenantCatalogue.java Created on 01/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author jens
 */
@Entity
@Table(name = "TNTCTG")
@NoArgsConstructor
@Getter
@Setter
public class TenantCatalogue {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "tenantId", column = @Column(name = "TNTIDR")),
            @AttributeOverride(name = "artifactId", column = @Column(name = "ARTIDR")),
            @AttributeOverride(name = "applicationId", column = @Column(name = "APPIDR")),
    })
    private PK tenantCataloguePk;

    @Column(name = "DEPGRP")
    private String deploymentGroup;

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
        private String applicationId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PK pk = (PK) o;
            return tenantId.equals(pk.tenantId) &&
                    artifactId.equals(pk.artifactId) &&
                    applicationId.equals(pk.applicationId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tenantId, artifactId, applicationId);
        }
    }

}
